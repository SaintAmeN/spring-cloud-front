package com.aps.services.ui.controller.configlut;

import com.aps.services.model.dto.configlut.response.RadarConfigurationResponseDto;
import com.aps.services.model.pagination.OwnPageImpl;
import com.aps.services.model.pagination.PagerModel;
import com.aps.services.ui.apiclients.ConfiglutMS;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static com.aps.services.ui.config.Constants.*;

@Controller
@RequestMapping("/radar/{radar_id}/configuration")
@RequiredArgsConstructor
public class RadarConfigurationController {
    private final ConfiglutMS configlutMS;

    private Integer evalPageSize = INITIAL_PAGE_SIZE;

    @GetMapping
    public ModelAndView listConfigurationsByRadarId(@PathVariable(name = "radar_id") Long radarId, HttpSession session,
                                                    @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                    @RequestParam(value = "page", required = false) Integer page,
                                                    @RequestParam(value = "pageSizeComment", required = false) Integer pageSizeComment,
                                                    @RequestParam(value = "pageComment", required = false) Integer pageComment) {

        ModelAndView model = new ModelAndView("radarConfig/configList");
        model.addObject("pageSizes", PAGE_SIZES);

        evalPageSize = pageSize != null ? pageSize : evalPageSize;
        int evalPage = page != null ? page - 1 : INITIAL_PAGE;

        OwnPageImpl<RadarConfigurationResponseDto> configurations = configlutMS.getAllByRadar(radarId, evalPageSize, evalPage);

        PagerModel pager = new PagerModel(configurations.getTotalPages(), configurations.getNumber(), BUTTONS_TO_SHOW);
        model.addObject("configurations", configurations);
        model.addObject("radarId", radarId);
        model.addObject("selectedPageSize", evalPageSize);
        model.addObject("pager", pager);

//        evalPageSizeComment = pageSizeComment != null ? pageSizeComment : evalPageSizeComment;
//        int evalPageComment = pageComment != null ? pageComment - 1 : INITIAL_PAGE;
//
//        Page<Comment> comments = commentService.getAllCommentsByRadar(radarId, PageRequest.of(evalPageComment, evalPageSizeComment));
//        PagerModel pagerComment = new PagerModel(comments.getTotalPages(), comments.getNumber(), BUTTONS_TO_SHOW);
//        model.addObject("selectedPageSizeComment", evalPageSizeComment);
//        model.addObject("pagerComment", pagerComment);
//        model.addObject("comments", comments);
//        model.addObject("newComment", new CommentRequestDto());


        return model;
    }


}
