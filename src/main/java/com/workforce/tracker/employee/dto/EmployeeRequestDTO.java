package com.workforce.tracker.employee.dto;

import lombok.Data;

@Data
public class EmployeeRequestDTO {
    private String employeeCode;
    private Boolean isActive;
}
