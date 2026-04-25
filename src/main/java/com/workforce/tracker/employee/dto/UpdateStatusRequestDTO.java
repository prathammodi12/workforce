package com.workforce.tracker.employee.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateStatusRequestDTO {

    @NotNull(message = "Id is required")
    private Long id;

    @NotNull(message = "Status is required")
    private Boolean isActive;
}
