package com.aps.services.ui.controller.ordering;

import com.aps.services.model.dto.ordering.request.InvoiceRequestDto;
import com.aps.services.ui.apiclients.ordering.OrderingMS;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author ajarmolkowicz
 */
@RestController
@RequestMapping("/ordering/invoice/")
@RequiredArgsConstructor
@Slf4j
public class InvoiceController {
    private final OrderingMS orderingMS;

    @PostMapping("/add/{orderId}")
    public ModelAndView createInvoice(@PathVariable(name = "orderId") Long orderId, InvoiceRequestDto dto){
        ModelAndView model = new ModelAndView("redirect:/ordering/order/manage/" + orderId);
        orderingMS.addInvoiceToRequest(orderId, dto);
        return model;
    }
}
