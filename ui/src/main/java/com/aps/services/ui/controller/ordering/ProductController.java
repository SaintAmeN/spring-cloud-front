package com.aps.services.ui.controller.ordering;

import com.aps.services.model.dto.ordering.request.ProductCodeRequestDto;
import com.aps.services.model.dto.ordering.request.ProductRequestDto;
import com.aps.services.model.dto.ordering.request.UrlRequestDto;
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

    @PostMapping("/add")
    public ModelAndView add(Authentication auth, ProductRequestDto dto) {
        ModelAndView model = new ModelAndView("redirect:/ordering/product/list");
        dto.setUser(user(auth));
        orderingMS.addProduct(dto);
        return model;
    }

    @GetMapping("/approve/{id}")
    public ModelAndView approve(@PathVariable(name = "id") Long id){
        ModelAndView model = new ModelAndView("redirect:/ordering/product/list");
        orderingMS.approveProduct(id);
        return model;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable(name = "id") Long id){
        ModelAndView model = new ModelAndView("redirect:/ordering/product/list");
        orderingMS.deleteProduct(id);
        return model;
    }

    @GetMapping("/edit/{id}/alternatives")
    public ModelAndView editAlternatives(@PathVariable(name = "id") Long id) {
        ModelAndView model = new ModelAndView("ordering/product/edit/alternatives");
        model.addObject("availableAlternatives", orderingMS.findAvailableProductAlternatives(id).getBody());
        model.addObject("currentAlternatives", orderingMS.findCurrentProductAlternatives(id).getBody());
        model.addObject("productId", id);
        return model;
    }

    @GetMapping("/edit/alternatives/add/{id}/{alternativeId}")
    public ModelAndView addAlternative(@PathVariable(name = "id") Long id, @PathVariable(name = "alternativeId") Long alternativeId) {
        orderingMS.addAlternativeToProduct(id, alternativeId);
        return new ModelAndView("redirect:/ordering/product/edit/" + id + "/alternatives");
    }

    @GetMapping("/edit/alternatives/delete/{id}/{alternativeId}")
    public ModelAndView deleteAlternative(@PathVariable(name = "id") Long id, @PathVariable(name = "alternativeId") Long alternativeId) {
        orderingMS.deleteAlternativeFromProduct(id, alternativeId);
        return new ModelAndView("redirect:/ordering/product/edit/" + id + "/alternatives");
    }

    @GetMapping("/edit/{id}/codes")
    public ModelAndView editCodes(@PathVariable(name = "id") Long id) {
        ModelAndView model = new ModelAndView("ordering/product/edit/codes");
        model.addObject("currentCodes", orderingMS.findCurrentProductCodes(id).getBody());
        model.addObject("productCodeRequest", new ProductCodeRequestDto());
        model.addObject("productId", id);
        return model;
    }

    @PostMapping("/edit/codes/add/{id}")
    public ModelAndView addCode(@PathVariable(name = "id") Long id, ProductCodeRequestDto dto) {
        orderingMS.addCodeToProduct(id, dto);
        return new ModelAndView("redirect:/ordering/product/edit/" + id + "/codes");
    }

    @GetMapping("/edit/codes/delete/{id}/{codeId}")
    public ModelAndView deleteCode(@PathVariable(name = "id") Long id, @PathVariable(name = "codeId") Long codeId) {
        orderingMS.deleteCodeFromProduct(id, codeId);
        return new ModelAndView("redirect:/ordering/product/edit/" + id + "/codes");
    }

    @GetMapping("/edit/{id}/shops")
    public ModelAndView editShops(@PathVariable(name = "id") Long id) {
        ModelAndView model = new ModelAndView("ordering/product/edit/shops");
        model.addObject("availableShops", orderingMS.findAvailableProductShops(id).getBody());
        model.addObject("currentShops", orderingMS.findCurrentProductShops(id).getBody());
        model.addObject("productId", id);
        return model;
    }

    @GetMapping("/edit/shops/add/{id}/{shopId}")
    public ModelAndView addShop(@PathVariable(name = "id") Long id, @PathVariable(name = "shopId") Long shopId) {
        orderingMS.addShopToProduct(id, shopId);
        return new ModelAndView("redirect:/ordering/product/edit/" + id + "/shops");
    }

    @GetMapping("/edit/shops/delete/{id}/{shopId}")
    public ModelAndView deleteShop(@PathVariable(name = "id") Long id, @PathVariable(name = "shopId") Long shopId) {
        orderingMS.deleteShopFromProduct(id, shopId);
        return new ModelAndView("redirect:/ordering/product/edit/" + id + "/shops");
    }

    @GetMapping("/edit/{id}/urls")
    public ModelAndView editUrls(@PathVariable(name = "id") Long id) {
        ModelAndView model = new ModelAndView("ordering/product/edit/urls");
        model.addObject("currentUrls", orderingMS.findCurrentProductUrls(id).getBody());
        model.addObject("urlRequestDto", new UrlRequestDto());
        model.addObject("productId", id);
        return model;
    }

    @PostMapping("/edit/urls/add/{id}")
    public ModelAndView addUrl(@PathVariable(name = "id") Long id, UrlRequestDto dto) {
        orderingMS.addUrlToProduct(id, dto);
        return new ModelAndView("redirect:/ordering/product/edit/" + id + "/urls");
    }

    @GetMapping("/edit/urls/delete/{id}/{urlId}")
    public ModelAndView deleteUrl(@PathVariable(name = "id") Long id, @PathVariable(name = "urlId") Long urlId) {
        orderingMS.deleteUrlFromProduct(id, urlId);
        return new ModelAndView("redirect:/ordering/product/edit/" + id + "/urls");
    }

    @GetMapping("/details/{id}")
    public ModelAndView getProductDetails(@PathVariable(name = "id") Long id){
        ModelAndView model = new ModelAndView("ordering/product/details");
        model.addObject("productResponseDto", orderingMS.findProductById(id).getBody());
        model.addObject("alternatives", orderingMS.findCurrentProductAlternatives(id).getBody());
        model.addObject("codes", orderingMS.findCurrentProductCodes(id).getBody());
        model.addObject("shops", orderingMS.findCurrentProductShops(id).getBody());
        model.addObject("urls", orderingMS.findCurrentProductUrls(id).getBody());
        return model;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getEditForm(@PathVariable(name = "id") Long id) {
        ModelAndView model = new ModelAndView("ordering/product/edit");
        model.addObject("productResponseDto", orderingMS.findProductById(id).getBody());
        model.addObject("productRequestDto", new ProductRequestDto());
        return model;
    }

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
        model.addObject("shops", orderingMS.findAllShops().getBody());
        model.addObject("productRequestDto", new ProductRequestDto());
        return model;
    }
}
