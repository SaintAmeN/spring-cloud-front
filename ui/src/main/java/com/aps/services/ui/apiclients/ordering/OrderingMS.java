package com.aps.services.ui.apiclients.ordering;

import com.aps.services.model.dto.ordering.request.*;
import com.aps.services.model.dto.ordering.response.*;
import com.aps.services.model.pagination.OwnPageImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author ajarmolkowicz
 */
@FeignClient("ordering-ms")
public interface OrderingMS {

    @PostMapping("/product/add")
    ResponseEntity<Long> addProduct(@RequestBody ProductRequestDto dto);

    @GetMapping("/product/get_all")
    ResponseEntity<List<ProductResponseDto>> findAllProducts();

    @GetMapping("/product/get/{id}")
    ResponseEntity<ProductResponseDto> findProductById(@PathVariable(name = "id") Long id);

    @GetMapping("/product/get_alternatives_available/{id}")
    ResponseEntity<Map<String, Long>> findAvailableProductAlternatives(@PathVariable(name = "id") Long id);

    @GetMapping("/product/get_alternatives_current/{id}")
    ResponseEntity<Map<String, Long>> findCurrentProductAlternatives(@PathVariable(name = "id") Long id);

    @GetMapping("/product/edit/alternatives/add/{id}/{alternativeId}")
    ResponseEntity<Long> addAlternativeToProduct(@PathVariable(name = "id") Long id, @PathVariable(name = "alternativeId") Long alternativeId);

    @GetMapping("/product/edit/alternatives/delete/{id}/{alternativeId}")
    ResponseEntity<Long> deleteAlternativeFromProduct(@PathVariable(name = "id") Long id, @PathVariable(name = "alternativeId") Long alternativeId);

    @GetMapping("/product/get_codes_current/{id}")
    ResponseEntity<Map<String, Long>> findCurrentProductCodes(@PathVariable(name = "id") Long id);

    @PostMapping("/product/edit/codes/add/{id}")
    ResponseEntity<Long> addCodeToProduct(@PathVariable(name = "id") Long id, @RequestBody ProductCodeRequestDto dto);

    @GetMapping("/product/edit/codes/delete/{id}/{codeId}")
    ResponseEntity<Long> deleteCodeFromProduct(@PathVariable(name = "id") Long id, @PathVariable(name = "codeId") Long codeId);

    @GetMapping("/product/get_shops_available/{id}")
    ResponseEntity<Map<String, Long>> findAvailableProductShops(@PathVariable(name = "id") Long id);

    @GetMapping("/product/get_shops_current/{id}")
    ResponseEntity<Map<String, Long>> findCurrentProductShops(@PathVariable(name = "id") Long id);

    @GetMapping("/product/edit/shops/add/{id}/{shopId}")
    ResponseEntity<Long> addShopToProduct(@PathVariable(name = "id") Long id, @PathVariable(name = "shopId") Long shopId);

    @GetMapping("/product/edit/shops/delete/{id}/{shopId}")
    ResponseEntity<Long> deleteShopFromProduct(@PathVariable(name = "id") Long id, @PathVariable(name = "shopId") Long shopId);

    @GetMapping("/product/get_urls_current/{id}")
    ResponseEntity<Map<String, Long>> findCurrentProductUrls(@PathVariable(name = "id") Long id);

    @PostMapping("/product/edit/urls/add/{id}")
    ResponseEntity<Long> addUrlToProduct(@PathVariable(name = "id") Long id, @RequestBody UrlRequestDto dto);

    @GetMapping("/product/edit/urls/delete/{id}/{urlId}")
    ResponseEntity<Long> deleteUrlFromProduct(@PathVariable(name = "id") Long id, @PathVariable(name = "urlId") Long urlId);

    @GetMapping("/product/list")
    ResponseEntity<OwnPageImpl<ProductResponseDto>> getProductList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                                   @RequestParam(value = "page", required = false) Integer page);

    @PostMapping("/shop/add")
    ResponseEntity<Long> addShop(@RequestBody ShopRequestDto dto);

    @GetMapping("/shop/approve/{id}")
    ResponseEntity<Long> approveShop(@PathVariable(name = "id") Long id);

    @GetMapping("/shop/delete/{id}")
    ResponseEntity<Long> deleteShop(@PathVariable(name = "id") Long id);

    @PostMapping("shop/edit/{id}")
    ResponseEntity<Long> editShop(@PathVariable(name = "id") Long id, @RequestBody ShopRequestDto dto);

    @GetMapping("/shop/get_all")
    ResponseEntity<List<ShopResponseDto>> findAllShops();

    @GetMapping("shop/details/{id}")
    ResponseEntity<ShopResponseDto> getShopDetails(@PathVariable(name = "id") Long id);

    @GetMapping("/shop/list")
    ResponseEntity<OwnPageImpl<ShopResponseDto>> getShopList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                             @RequestParam(value = "page", required = false) Integer page);

    @GetMapping("/order/list")
    ResponseEntity<OwnPageImpl<OrderListResponseDto>> gerOrderList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                                   @RequestParam(value = "page", required = false) Integer page);

    @GetMapping("/order/list/get_empty")
    ResponseEntity<List<OrderListResponseDto>> getEmptyOrderLists();

    @GetMapping("order/list/target/get_all")
    ResponseEntity<List<OrderTargetResponseDto>> getAllOrderTargets();

    @PostMapping("/order/add")
    ResponseEntity<Long> addOrderList(@RequestBody OrderListRequestDto dto);

    @GetMapping("/order/get/{id}")
    ResponseEntity<OrderListResponseDto> getOrderListById(@PathVariable(name = "id") Long id);

    @GetMapping("/order/get/{id}/requests")
    ResponseEntity<List<ProductRequestResponseDto>> getRequestsFromOrderList(@PathVariable(name = "id") Long id);

    @GetMapping("/order/get/{id}/invoices")
    ResponseEntity<List<InvoiceResponseDto>> getInvoicesFromOrderList(@PathVariable(name = "id") Long id);

    @PostMapping("/order/request/add/{orderId}")
    ResponseEntity<Long> addProductRequest(@PathVariable(name = "orderId") Long orderId, @RequestBody ProductRequestRequestDto dto);

    @GetMapping("/unit/get_all")
    ResponseEntity<List<UnitResponseDto>> findAllUnits();
}
