package com.workforce.tracker.employee.dto;

import lombok.Data;

@Data
public class EmployeeResponseDTO {
    private Long id;
    private String employeeCode;
    private Boolean isActive;
}
