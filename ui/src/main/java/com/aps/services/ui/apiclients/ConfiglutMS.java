package com.aps.services.ui.apiclients;

import com.aps.services.apiclients.IConfiglutMS;
import com.aps.services.model.dto.configlut.response.RadarConfigurationResponseDto;
import com.aps.services.model.pagination.OwnPageImpl;
import com.aps.services.model.dto.configlut.response.RadarResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("configlut-ms")
public interface ConfiglutMS extends IConfiglutMS {
    @GetMapping("/radar")
    OwnPageImpl<RadarResponseDto>
    getRadarList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                 @RequestParam(value = "page", required = false) Integer page);

    @GetMapping("/radar/{radar_id}/configuration")
    OwnPageImpl<RadarConfigurationResponseDto>
    getAllByRadar(@PathVariable(name = "radar_id") Long radarId,
                  @RequestParam(name = "pageSize", required = false) Integer pageSize,
                  @RequestParam(name = "page", required = false) Integer page);
}
