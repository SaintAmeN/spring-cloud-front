package com.aps.services.ui.apiclients;

import com.aps.services.apiclients.IConfiglutMS;
import com.aps.services.model.dto.OwnPageImpl;
import com.aps.services.model.dto.configlut.response.RadarResponseDto;
import com.aps.services.ui.util.CustomPageImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("configlut-ms")
public interface ConfiglutMS extends IConfiglutMS {
    @GetMapping("/radar/")
    ResponseEntity<OwnPageImpl<RadarResponseDto>> getRadarList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                               @RequestParam(value = "page", required = false) Integer page);

}
