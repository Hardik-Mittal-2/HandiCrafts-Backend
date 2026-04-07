package com.backend.handicrafts.dto;

import com.backend.handicrafts.entity.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SetRoleRequest {

    @NotNull
    private Role role;
}