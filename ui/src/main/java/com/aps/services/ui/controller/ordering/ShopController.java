package com.aps.services.ui.controller.ordering;

import com.aps.services.model.dto.ordering.request.ProductRequestDto;
import com.aps.services.model.dto.ordering.request.ShopRequestDto;
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
@RequestMapping("/ordering/shop/")
@RequiredArgsConstructor
@Slf4j
public class ShopController extends BaseAbstractController {
    private final OrderingMS orderingMS;

    @GetMapping("/list")
    public ModelAndView getShopList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                       @RequestParam(value = "page", required = false) Integer page) {
        int selectedPageSize = pageSize != null ? pageSize : DEFAULT_PAGE_SIZE;
        int currentPage = page != null ? page - 1 : INITIAL_PAGE;

        ModelAndView model = new ModelAndView("ordering/shop/list");
        model.addObject("products", orderingMS.getShopList(selectedPageSize, currentPage).getBody());
        model.addObject("selectedPageSize", selectedPageSize);
        model.addObject("pageSizes", PAGE_SIZES);

        return model;
    }

    @GetMapping("/add")
    public ModelAndView getShopForm(Authentication auth) {
        ModelAndView model = new ModelAndView("ordering/shop/form");
        model.addObject("shopRequestDto", ShopRequestDto.builder().userId(Long.parseLong(userId(auth))).build());
        return model;
    }

    @PostMapping("/add")
    public ModelAndView add(ShopRequestDto dto) {
        ModelAndView model = new ModelAndView("redirect:/ordering/shop/list");
        orderingMS.addShop(dto);
//        String message = messageSource.getMessage("product.add.success", null, LocaleContextHolder.getLocale());
//        model.addObject(MESSAGE, message);
        return model;
    }
}
