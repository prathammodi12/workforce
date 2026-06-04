package com.workforce.tracker.employee.entity;

import com.workforce.tracker.common.entity.BaseEntity;
import com.workforce.tracker.user.entity.User;
import com.workforce.tracker.department.entity.Department;
import com.workforce.tracker.designation.entity.Designation;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name= "employees")
@Data
public class Employee extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // link with user (1-1)
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "employee_code", unique = true,nullable = false)
    private String employeeCode;

    //Department relation
    @ManyToOne
    @JoinColumn(name= "department_id")
    private Department department;

    //Designation relation
    @ManyToOne
    @JoinColumn(name= "designation_id")
    private Designation designation;

    private LocalDate dateOfJoining;

    private String profileUrl;

    @Column(name = "is_active")
    private Boolean isActive;

    private Boolean isVerified;

    private String verificationCode;

    private LocalDate expireAt;

    private String hierarchyPath;

    private Integer hierarchyLevel;

    private Boolean ispasswordChanged;

}
