package com.aps.services.ui.apiclients;

import com.aps.services.apiclients.IConfiglutMS;
import com.aps.services.model.dto.configlut.request.CommentRequestDto;
import com.aps.services.model.dto.configlut.request.RadarConfigurationRequestDto;
import com.aps.services.model.dto.configlut.request.RadarRequestDto;
import com.aps.services.model.dto.configlut.response.CommentResponseDto;
import com.aps.services.model.dto.configlut.response.RadarConfigurationResponseDto;
import com.aps.services.model.dto.configlut.response.RadarResponseDto;
import com.aps.services.model.pagination.OwnPageImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("configlut-ms")
public interface ConfiglutMS extends IConfiglutMS {
    @GetMapping("/radar")
    OwnPageImpl<RadarResponseDto>
    getRadarList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                 @RequestParam(value = "page", required = false) Integer page);

    @GetMapping("/radar/{radar_id}/configuration")
    OwnPageImpl<RadarConfigurationResponseDto>
    getConfigurationsByRadar(@PathVariable(name = "radar_id") Long radarId,
                             @RequestParam(name = "pageSize", required = false) Integer pageSize,
                             @RequestParam(name = "page", required = false) Integer page);

    @GetMapping("/radar/{radar_id}/comment")
    OwnPageImpl<CommentResponseDto>
    getCommentsByRadar(@PathVariable(name = "radar_id") Long radarId,
                       @RequestParam(name = "pageSize", required = false) Integer pageSize,
                       @RequestParam(name = "page", required = false) Integer page);

    @PutMapping("/radar/{radar_id}/comment")
    CommentResponseDto addComment(@PathVariable(name = "radar_id") Long radarId,
                                  @RequestBody CommentRequestDto newComment);

    @PutMapping("/radar")
    RadarResponseDto addRadar(RadarRequestDto dto);

    @PutMapping("/radar/{radar_id}/configuration")
    RadarConfigurationResponseDto addRadarConfiguration(@PathVariable(name = "radar_id") Long radarId,
                                                        @RequestBody RadarConfigurationRequestDto dto);

    @GetMapping("/radar/{radar_id}/configuration/{configuration_id}")
    RadarConfigurationRequestDto createConfigEditForm(@PathVariable(name = "radar_id") Long radarId,
                                                      @PathVariable(name = "configuration_id") Long configurationId);

    @PostMapping("/radar/{radar_id}/configuration/{configuration_id}")
    RadarConfigurationResponseDto editConfiguration(@PathVariable(name = "radar_id") Long radarId,
                                                    @PathVariable(name = "configuration_id") Long configId,
                                                    @RequestBody RadarConfigurationRequestDto configurationDto);
}
