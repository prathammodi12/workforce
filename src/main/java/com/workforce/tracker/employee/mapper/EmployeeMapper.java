package com.workforce.tracker.employee.mapper;

import com.workforce.tracker.employee.dto.EmployeeRequestDTO;
import com.workforce.tracker.employee.dto.EmployeeResponseDTO;
import com.workforce.tracker.employee.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    //Entity-> Response DTO
    EmployeeResponseDTO toDTO(Employee employee);

    //Request DTO -> Entity
    Employee toEntity(EmployeeRequestDTO dto);
}
