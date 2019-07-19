package com.aps.services.ui.apiclients.ordering;

import com.aps.services.model.dto.OwnPageImpl;
import com.aps.services.model.dto.ordering.request.ProductRequestDto;
import com.aps.services.model.dto.ordering.request.ShopRequestDto;
import com.aps.services.model.dto.ordering.response.EntityResponseDto;
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
    ResponseEntity<OwnPageImpl<EntityResponseDto>> getProductList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                                  @RequestParam(value = "page", required = false) Integer page);

    @PostMapping("/product/add")
    ResponseEntity<Long> addProduct(@RequestBody ProductRequestDto dto);


    @GetMapping("/shop/list")
    ResponseEntity<OwnPageImpl<EntityResponseDto>> getShopList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                               @RequestParam(value = "page", required = false) Integer page);

    @PostMapping("/shop/add")
    ResponseEntity<Long> addShop(@RequestBody ShopRequestDto dto);

    @GetMapping("/shop/get_all")
    ResponseEntity<List<EntityResponseDto>> findAllShops();

    @GetMapping("/order/list")
    ResponseEntity<OwnPageImpl<EntityResponseDto>> gerOrderList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                                @RequestParam(value = "page", required = false) Integer page);
}
