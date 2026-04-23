package com.workforce.tracker.employee.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name= "employees")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
