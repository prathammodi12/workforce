package com.workforce.tracker.employee.controller;

import com.workforce.tracker.employee.dto.EmployeeRequestDTO;
import com.workforce.tracker.employee.dto.EmployeeResponseDTO;
import com.workforce.tracker.employee.entity.Employee;
import com.workforce.tracker.employee.service.EmployeeService;
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
    @PostMapping
    public EmployeeResponseDTO createEmployee(@RequestBody EmployeeRequestDTO dto){
        return service.createEmployee(dto);
    }

    //Get all Api
    @GetMapping
    public List<EmployeeResponseDTO> getAllEmployees(){
        return service.getAllEmployees();
    }
}
