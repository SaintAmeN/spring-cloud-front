package com.aps.services.ui.controller.configlut;

import com.aps.services.model.dto.configlut.response.RadarResponseDto;
import com.aps.services.model.pagination.OwnPageImpl;
import com.aps.services.model.pagination.PagerModel;
import com.aps.services.ui.apiclients.ConfiglutMS;
import com.aps.services.ui.controller.BaseAbstractController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static com.aps.services.ui.config.Constants.*;

@Controller
@RequestMapping("/radar")
@RequiredArgsConstructor
public class RadarController extends BaseAbstractController {
    private final ConfiglutMS configlutMS;

    private Integer evalPageSize = INITIAL_PAGE_SIZE;

    @GetMapping
    public ModelAndView list(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                             @RequestParam(value = "page", required = false) Integer page) {
        evalPageSize = pageSize != null ? pageSize : evalPageSize;
        int evalPage = page != null ? page - 1 : INITIAL_PAGE;
        ModelAndView model = new ModelAndView("radar/radarList");
        OwnPageImpl<RadarResponseDto> radars = configlutMS.getRadarList(evalPageSize, evalPage);
        PagerModel pager = new PagerModel(radars.getTotalPages(), radars.getNumber(), BUTTONS_TO_SHOW);
        model.addObject("radars", radars);
        model.addObject("selectedPageSize", evalPageSize);
        model.addObject("pageSizes", PAGE_SIZES);
        model.addObject("pager", pager);
        return model;
    }
}
