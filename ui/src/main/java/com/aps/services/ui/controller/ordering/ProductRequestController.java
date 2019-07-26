package com.aps.services.ui.controller.ordering;

import com.aps.services.ui.apiclients.ordering.OrderingMS;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author ajarmolkowicz
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/ordering/request/")
@Slf4j
public class ProductRequestController {
    private final OrderingMS orderingMS;

    @GetMapping("/add/{orderId}")
    public ModelAndView addProductRequestToOrderList(@PathVariable(name = "orderId") Long orderId) {
        ModelAndView model = new ModelAndView("/ordering/request/form");

        return model;
    }
}
