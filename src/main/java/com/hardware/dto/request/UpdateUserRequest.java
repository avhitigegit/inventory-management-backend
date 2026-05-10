package com.hardware.dto.request;

import com.hardware.enums.Role;
import lombok.Data;

@Data
public class UpdateUserRequest {

    private Role role;
    private Boolean active;
}
