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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author ajarmolkowicz
 */
@FeignClient("ordering-ms")
public interface OrderingMS {
    @GetMapping("/product/list")
    ResponseEntity<OwnPageImpl<ProductRequestDto>> getProductList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                                  @RequestParam(value = "page", required = false) Integer page);

    @PostMapping("/product/add")
    ResponseEntity<Long> addProduct(@RequestBody ProductRequestDto dto);


    @GetMapping("/shop/list")
    ResponseEntity<OwnPageImpl<ShopResponseDto>> getShopList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                             @RequestParam(value = "page", required = false) Integer page);

    @PostMapping("/shop/add")
    ResponseEntity<Long> addShop(@RequestBody ShopRequestDto dto);

    @GetMapping("/shop/get_all")
    ResponseEntity<List<ShopResponseDto>> getAllShops();


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
