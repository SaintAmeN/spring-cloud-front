package com.amen.services.ui.model.dto.common;

import lombok.*;

/**
 * @author ajarmolkowicz
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserInfoRequest {
    private Long id;
    private String name;
    private boolean isAdmin;
}
