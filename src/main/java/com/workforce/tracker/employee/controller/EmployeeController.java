package com.workforce.tracker.employee.controller;

import com.workforce.tracker.common.response.CommonResponse;
import com.workforce.tracker.employee.dto.EmployeeRequestDTO;
import com.workforce.tracker.employee.dto.EmployeeResponseDTO;
import com.workforce.tracker.employee.dto.UpdateStatusRequestDTO;
import com.workforce.tracker.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service){
        this.service= service;
    }

    //Create Api
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public CommonResponse<EmployeeResponseDTO> createEmployee(
            @RequestBody @Valid EmployeeRequestDTO dto){
        return CommonResponse.success(service.createEmployee(dto));
    }

    //Get all Api
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public CommonResponse<List<EmployeeResponseDTO>> getAllEmployees(){
        return CommonResponse.success(service.getAllEmployees());
    }

    @PatchMapping("/status")
    public CommonResponse<EmployeeResponseDTO> updateEmployee(
            @RequestBody @Valid UpdateStatusRequestDTO dto){
        return CommonResponse.success(service.updateEmployee(dto));
    }

    @GetMapping("/{id}")
    public CommonResponse<EmployeeResponseDTO> getEmployeeById(@PathVariable Long id){
        return CommonResponse.success(service.getEmployeeById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public CommonResponse<String> deleteEmployee(@PathVariable Long id){
        return CommonResponse.success(service.deleteEmployee(id));
    }
}
