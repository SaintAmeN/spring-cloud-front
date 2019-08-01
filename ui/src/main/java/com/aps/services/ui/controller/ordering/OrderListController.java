package com.aps.services.ui.controller.ordering;

import com.aps.services.model.dto.ordering.request.InvoiceRequestDto;
import com.aps.services.model.dto.ordering.request.OrderListRequestDto;
import com.aps.services.model.dto.ordering.request.RequestUpdateDto;
import com.aps.services.ui.apiclients.ordering.OrderingMS;
import com.aps.services.ui.controller.BaseAbstractController;
import com.aps.services.ui.util.ordering.ExcelWriter;
import com.aps.services.ui.util.ordering.OrderSummaryDownloader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    @PostMapping("/add")
    public ModelAndView add(Authentication auth, OrderListRequestDto dto) {
        ModelAndView model = new ModelAndView("redirect:/ordering/order/list");
        dto.setUser(user(auth));
        orderingMS.addOrderList(dto);
        return model;
    }

    @GetMapping("/archive/{id}")
    public ModelAndView archive(@PathVariable(name = "id") Long id) {
        ModelAndView model = new ModelAndView("redirect:/ordering/order/list");
        orderingMS.archiveOrderList(id);
        return model;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable(name = "id") Long id) {
        ModelAndView model = new ModelAndView("redirect:/ordering/order/list");
        orderingMS.deleteOrderList(id);
        return model;
    }

    @GetMapping("/list")
    public ModelAndView getOrderList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                     @RequestParam(value = "page", required = false) Integer page) {
        int selectedPageSize = pageSize != null ? pageSize : DEFAULT_PAGE_SIZE;
        int currentPage = page != null ? page - 1 : INITIAL_PAGE;

        ModelAndView model = new ModelAndView("ordering/order/list");
        model.addObject("orders", orderingMS.gerOrderList(selectedPageSize, currentPage).getBody());
        model.addObject("orderListRequestDto", new OrderListRequestDto());
        model.addObject("emptyOrderLists", orderingMS.getEmptyOrderLists().getBody());
        model.addObject("targets", orderingMS.getAllOrderTargets().getBody());
        model.addObject("selectedPageSize", selectedPageSize);
        model.addObject("pageSizes", PAGE_SIZES);

        return model;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getOrderListEditForm(@PathVariable(name = "id") Long id) {
        ModelAndView model = new ModelAndView("ordering/order/edit");
        model.addObject("orderResponseDto", orderingMS.getOrderListById(id).getBody());
        model.addObject("targets", orderingMS.getAllOrderTargets().getBody());
        model.addObject("editOrderDto", new OrderListRequestDto());
        return model;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView edit(Authentication auth, @PathVariable(name = "id") Long id, OrderListRequestDto dto) {
        ModelAndView model = new ModelAndView("redirect:/ordering/order/list");
        dto.setUser(user(auth));
        orderingMS.editOrderList(id, dto);
        return model;
    }


    @GetMapping("/manage/{id}")
    public ModelAndView manageOrderList(@PathVariable(name = "id") Long id) {
        ModelAndView model = new ModelAndView("ordering/order/management");
        model.addObject("orderListResponseDto", orderingMS.getOrderListById(id).getBody());
        model.addObject("requests", orderingMS.getRequestsFromOrderList(id).getBody());
        model.addObject("orderInvoices", orderingMS.getInvoicesFromOrderList(id).getBody());
        model.addObject("shops", orderingMS.findAllShops().getBody());
        model.addObject("requestUpdateDto", new RequestUpdateDto());
        model.addObject("invoiceRequestDto", new InvoiceRequestDto());
        return model;
    }

    @GetMapping("/summary/{id}")
    public void getOrderSummary(@PathVariable(name = "id") Long id, HttpServletResponse response) {
        OrderSummaryDownloader.getOrderSummary(id, response, ExcelWriter.prepareOrderSummary(orderingMS.getOrderSummary(id).getBody()));
    }
}
