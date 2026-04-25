package com.workforce.tracker.employee.service;

import com.workforce.tracker.employee.dto.EmployeeRequestDTO;
import com.workforce.tracker.employee.dto.EmployeeResponseDTO;
import com.workforce.tracker.employee.dto.UpdateStatusRequestDTO;
import com.workforce.tracker.employee.entity.Employee;
import com.workforce.tracker.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository){
        this.repository = repository;
    }

    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto){
        Employee employee= new Employee();
        employee.setEmployeeCode(dto.getEmployeeCode());
        employee.setIsActive(dto.getIsActive());

        Employee saved= repository.save(employee);

        EmployeeResponseDTO response= new EmployeeResponseDTO();
        response.setId(saved.getId());
        response.setEmployeeCode(saved.getEmployeeCode());
        response.setIsActive(saved.getIsActive());

        return response;
    }

    public List<EmployeeResponseDTO> getAllEmployees(){
        return repository.findAll().stream().map(emp -> {
            EmployeeResponseDTO dto= new EmployeeResponseDTO();
            dto.setId(emp.getId());
            dto.setEmployeeCode(emp.getEmployeeCode());
            dto.setIsActive(emp.getIsActive());
            return dto;
        }).toList();
    }

    public EmployeeResponseDTO updateEmployee(UpdateStatusRequestDTO dto){
        Employee employee= repository.findById(dto.getId()).
                orElseThrow(()->new RuntimeException("Employee not found"));
        employee.setIsActive(dto.getIsActive());
        Employee updated= repository.save(employee);

        EmployeeResponseDTO response= new EmployeeResponseDTO();
        response.setId(updated.getId());
        response.setEmployeeCode(updated.getEmployeeCode());
        response.setIsActive(updated.getIsActive());

        return response;
    }

}
