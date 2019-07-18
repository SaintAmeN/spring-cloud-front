package com.aps.services.ui.controller.ordering;

import com.aps.services.ui.apiclients.OrderingMS;
import com.aps.services.ui.controller.BaseAbstractController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author ajarmolkowicz
 */
@RestController
@RequestMapping("/ordering/product/")
@RequiredArgsConstructor
@Slf4j
public class ProductController extends BaseAbstractController {
    private final OrderingMS orderingMS;

    @GetMapping("list/")
    public ModelAndView getProductList() {
        ModelAndView model = new ModelAndView("ordering/product/productList");
        model.addObject("products", orderingMS.getProductList().getBody());
        return model;
    }
}
