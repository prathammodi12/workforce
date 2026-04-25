package com.workforce.tracker.employee.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmployeeRequestDTO {
    @NotBlank(message = "Employee code is required")
    private String employeeCode;

    @NotNull(message = "Active status required")
    private Boolean isActive;
}
