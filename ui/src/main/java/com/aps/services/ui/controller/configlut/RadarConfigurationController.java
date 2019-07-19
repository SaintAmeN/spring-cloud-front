package com.aps.services.ui.controller.configlut;

import com.aps.services.model.dto.configlut.request.CommentRequestDto;
import com.aps.services.model.dto.configlut.response.CommentResponseDto;
import com.aps.services.model.dto.configlut.response.RadarConfigurationResponseDto;
import com.aps.services.model.pagination.OwnPageImpl;
import com.aps.services.model.pagination.PagerModel;
import com.aps.services.ui.apiclients.ConfiglutMS;
import com.aps.services.ui.controller.BaseAbstractController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static com.aps.services.ui.config.Constants.*;

@Controller
@RequestMapping("/radar/{radar_id}/configuration")
@RequiredArgsConstructor
public class RadarConfigurationController extends BaseAbstractController {
    private final ConfiglutMS configlutMS;

    private Integer evalPageSize = INITIAL_PAGE_SIZE;
    private Integer evalPageSizeComment = INITIAL_PAGE_SIZE;
    private static final String VIEW_CONFIG_REDIRECT = "redirect:/radar/%d/configuration/";

    @GetMapping
    public ModelAndView listConfigsByRadar(@PathVariable(name = "radar_id") Long radarId, HttpSession session,
                                           @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                           @RequestParam(value = "page", required = false) Integer page,
                                           @RequestParam(value = "pageSizeComment", required = false) Integer pageSizeComment,
                                           @RequestParam(value = "pageComment", required = false) Integer pageComment) {

        ModelAndView model = new ModelAndView("radarConfig/configList");
        model.addObject("pageSizes", PAGE_SIZES);

        evalPageSize = pageSize != null ? pageSize : evalPageSize;
        int evalPage = page != null ? page - 1 : INITIAL_PAGE;

        OwnPageImpl<RadarConfigurationResponseDto> configurations = configlutMS.getConfigurationsByRadar(radarId,
                evalPageSize, evalPage);

        PagerModel pager = new PagerModel(configurations.getTotalPages(), configurations.getNumber(), BUTTONS_TO_SHOW);
        model.addObject("configurations", configurations);
        model.addObject("radarId", radarId);
        model.addObject("selectedPageSize", evalPageSize);
        model.addObject("pager", pager);

        evalPageSizeComment = pageSizeComment != null ? pageSizeComment : evalPageSizeComment;
        int evalPageComment = pageComment != null ? pageComment - 1 : INITIAL_PAGE;

        OwnPageImpl<CommentResponseDto> comments = configlutMS.getCommentsByRadar(radarId,
                evalPageSizeComment, evalPageComment);
        PagerModel pagerComment = new PagerModel(comments.getTotalPages(), comments.getNumber(), BUTTONS_TO_SHOW);
        model.addObject("selectedPageSizeComment", evalPageSizeComment);
        model.addObject("pagerComment", pagerComment);
        model.addObject("comments", comments);
        model.addObject("newComment", new CommentRequestDto());

        return model;
    }

    @PostMapping("/add_comment")
    public ModelAndView postComment(@PathVariable(name = "radar_id") Long radarId, CommentRequestDto newComment) {
        ModelAndView model = new ModelAndView(String.format(VIEW_CONFIG_REDIRECT, radarId));
        configlutMS.addComment(radarId, newComment);
        return model;
    }


}
