package com.aps.services.ui.apiclients.ordering;

import com.aps.services.model.dto.ordering.request.OrderListRequestDto;
import com.aps.services.model.dto.ordering.request.ProductRequestDto;
import com.aps.services.model.dto.ordering.request.ShopRequestDto;
import com.aps.services.model.dto.ordering.response.OrderListResponseDto;
import com.aps.services.model.dto.ordering.response.OrderTargetResponseDto;
import com.aps.services.model.dto.ordering.response.ShopResponseDto;
import com.aps.services.model.pagination.OwnPageImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ajarmolkowicz
 */
@FeignClient("ordering-ms")
public interface OrderingMS {

    @PostMapping("/product/add")
    ResponseEntity<Long> addProduct(@RequestBody ProductRequestDto dto);

    @GetMapping("/product/list")
    ResponseEntity<OwnPageImpl<ProductRequestDto>> getProductList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
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
    ResponseEntity<List<ShopResponseDto>> getAllShops();

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
}
