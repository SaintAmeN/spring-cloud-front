package com.aps.services.ui.controller.configlut;

import com.aps.services.model.dto.OwnPageImpl;
import com.aps.services.model.dto.configlut.response.RadarResponseDto;
import com.aps.services.ui.apiclients.ConfiglutMS;
import com.aps.services.ui.util.CustomPageImpl;
import com.aps.services.ui.util.PagerModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.data.domain.Page;

import java.util.List;

@Controller
@RequestMapping("/radar")
@RequiredArgsConstructor
public class RadarController {
    private static final Integer BUTTONS_TO_SHOW = 3;
    private static final Integer INITIAL_PAGE_SIZE = 5;
    private final ConfiglutMS configlutMS;
    private final ObjectMapper objectMapper;

    private Integer evalPageSize = INITIAL_PAGE_SIZE;
    public static final int[] PAGE_SIZES = {5, 10, 15, 20};

    @GetMapping("")
    public ModelAndView list(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                             @RequestParam(value = "page", required = false) Integer page) {
        ModelAndView model = new ModelAndView("radar/radarList");


        OwnPageImpl<RadarResponseDto> radars = configlutMS.getRadarList(pageSize, page).getBody();
//        OwnPageImpl<RadarResponseDto> radars = objectMapper.convertValue(configlutMS.getRadarList(pageSize, page).getBody(), new TypeReference<OwnPageImpl<RadarResponseDto>>(){});
//        List<RadarResponseDto> radars = configlutMS.getRadarList(pageSize, page).getBody();
//        ResponseEntity<PagedResources<RadarResponseDto>> radars = configlutMS.getRadarList(pageSize, page);
        PagerModel pager = new PagerModel(4, 0, BUTTONS_TO_SHOW);
        model.addObject("radars", radars);
        model.addObject("selectedPageSize", evalPageSize);
        model.addObject("pageSizes", PAGE_SIZES);
        model.addObject("pager", pager);
        return model;
    }
}
