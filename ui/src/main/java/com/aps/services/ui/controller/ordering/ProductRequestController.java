package com.aps.services.ui.controller.ordering;

import com.aps.services.model.dto.ordering.model.attributes.RequestPriority;
import com.aps.services.model.dto.ordering.request.ProductRequestRequestDto;
import com.aps.services.ui.apiclients.ordering.OrderingMS;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
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
    public ModelAndView getProductRequestForm(@PathVariable(name = "orderId") Long orderId) {
        ModelAndView model = new ModelAndView("ordering/request/form");
        model.addObject("units", orderingMS.findAllUnits().getBody());
        model.addObject("products", orderingMS.findAllProducts().getBody());
        model.addObject("priorities", RequestPriority.values());
        model.addObject("requestDto",new ProductRequestRequestDto());
        return model;
    }

    @PostMapping("/add/{orderId}")
    public ModelAndView createProductRequest(@PathVariable(name = "orderId") Long orderId, @RequestBody ProductRequestRequestDto dto) {
        ModelAndView model = new ModelAndView("redirect:/ordering/order/manage/" + orderId);
        orderingMS.addProductRequest(orderId, dto);
        return model;
    }
}
