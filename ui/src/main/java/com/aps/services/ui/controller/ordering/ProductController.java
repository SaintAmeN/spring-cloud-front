package com.aps.services.ui.controller.ordering;

import com.aps.services.model.dto.ordering.request.ProductRequestDto;
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
@RequestMapping("/ordering/product/")
@RequiredArgsConstructor
@Slf4j
public class ProductController extends BaseAbstractController {
    private final OrderingMS orderingMS;

    @GetMapping("/list")
    public ModelAndView getProductList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                       @RequestParam(value = "page", required = false) Integer page) {
        int selectedPageSize = pageSize != null ? pageSize : DEFAULT_PAGE_SIZE;
        int currentPage = page != null ? page - 1 : INITIAL_PAGE;

        ModelAndView model = new ModelAndView("ordering/product/list");
        model.addObject("products", orderingMS.getProductList(selectedPageSize, currentPage).getBody());
        model.addObject("selectedPageSize", selectedPageSize);
        model.addObject("pageSizes", PAGE_SIZES);

        return model;
    }

    @GetMapping("/add")
    public ModelAndView getProductForm() {
        ModelAndView model = new ModelAndView("ordering/product/form");
        model.addObject("shops", orderingMS.getAllShops().getBody());
        model.addObject("productRequestDto", new ProductRequestDto());
        return model;
    }

    @PostMapping("/add")
    public ModelAndView add(Authentication auth, ProductRequestDto dto) {
        ModelAndView model = new ModelAndView("redirect:/ordering/product/list");
        dto.setUser(user(auth));
        orderingMS.addProduct(dto);
//        String message = messageSource.getMessage("product.add.success", null, LocaleContextHolder.getLocale());
//        model.addObject(MESSAGE, message);
        return model;
    }
}
