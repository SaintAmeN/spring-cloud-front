package com.aps.services.ui.controller.ordering;

import com.aps.services.model.dto.ordering.request.OrderListRequestDto;
import com.aps.services.ui.apiclients.ordering.OrderingMS;
import com.aps.services.ui.controller.BaseAbstractController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static com.aps.services.ui.config.Constants.INITIAL_PAGE;
import static com.aps.services.ui.config.Constants.PAGE_SIZES;
import static org.springframework.beans.support.PagedListHolder.DEFAULT_PAGE_SIZE;

/**
 * @author ajarmolkowicz
 */
@RestController
@RequestMapping("/ordering/order/")
@RequiredArgsConstructor
@Slf4j
public class OrderListController extends BaseAbstractController {
    private final OrderingMS orderingMS;

    @GetMapping("/list")
    public ModelAndView getProductList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                       @RequestParam(value = "page", required = false) Integer page) {
        int selectedPageSize = pageSize != null ? pageSize : DEFAULT_PAGE_SIZE;
        int currentPage = page != null ? page - 1 : INITIAL_PAGE;

        ModelAndView model = new ModelAndView("ordering/order/list");
        model.addObject("orders", orderingMS.gerOrderList(selectedPageSize, currentPage).getBody());
        model.addObject("orderListRequestDto", new OrderListRequestDto());
        model.addObject("emptyOrderLists",orderingMS.getEmptyOrderLists().getBody());
        model.addObject("targets", orderingMS.getAllOrderTargets().getBody());
        model.addObject("selectedPageSize", selectedPageSize);
        model.addObject("pageSizes", PAGE_SIZES);

        return model;
    }

    @PostMapping("/add")
    public ModelAndView add(Authentication auth, OrderListRequestDto dto){
        ModelAndView model = new ModelAndView("redirect:/ordering/order/list");
        dto.setUser(user(auth));
        orderingMS.addOrderList(dto);
        return model;
    }
}
