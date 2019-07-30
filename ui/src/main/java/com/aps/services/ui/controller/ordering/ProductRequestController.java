package com.aps.services.ui.controller.ordering;

import com.aps.services.model.dto.ordering.model.attributes.RequestPriority;
import com.aps.services.model.dto.ordering.request.ProductRequestRequestDto;
import com.aps.services.ui.apiclients.ordering.OrderingMS;
import com.aps.services.ui.controller.BaseAbstractController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author ajarmolkowicz
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/ordering/request/")
@Slf4j
public class ProductRequestController extends BaseAbstractController {
    private final OrderingMS orderingMS;

    @GetMapping("/add/{orderId}")
    public ModelAndView getProductRequestForm(@PathVariable(name = "orderId") Long orderId) {
        ModelAndView model = new ModelAndView("ordering/request/form");
        model.addObject("units", orderingMS.findAllUnits().getBody());
        model.addObject("products", orderingMS.findAllProducts().getBody());
        model.addObject("priorities", RequestPriority.values());
        model.addObject("requestDto", new ProductRequestRequestDto());
        model.addObject("orderId", orderId);
        return model;
    }

    @PostMapping("/add/{orderId}")
    public ModelAndView createProductRequest(Authentication auth, @PathVariable(name = "orderId") Long orderId, ProductRequestRequestDto dto) {
        ModelAndView model = new ModelAndView("redirect:/ordering/order/manage/" + orderId);
        dto.setCreator(user(auth));
        orderingMS.addProductRequest(orderId, dto);
        return model;
    }

    @GetMapping("/delete/{id}/{orderId}")
    public ModelAndView deleteProductRequest(@PathVariable(name = "id") Long id, @PathVariable(name = "orderId") Long orderId){
        ModelAndView model = new ModelAndView("redirect:/ordering/order/manage/" + orderId);
        orderingMS.deleteProductRequest(id);
        return model;
    }

    @GetMapping("/edit/{id}/{orderId}")
    public ModelAndView edit(@PathVariable(name = "id") Long id, @PathVariable(name = "orderId") Long orderId){
        ModelAndView model = new ModelAndView("ordering/request/edit");
        model.addObject("productRequestResponseDto", orderingMS.findProductRequestById(id).getBody());
        model.addObject("units", orderingMS.findAllUnits().getBody());
        model.addObject("productRequestRequestDto", new ProductRequestRequestDto());
        model.addObject("orderId", orderId);
        return model;
    }
}