package com.Kapture.Employee.controller;

import com.Kapture.Employee.entity.Employee;
import com.Kapture.Employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/get-employee-by-id/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable int id){
        ResponseEntity<?> employee=employeeService.getEmployeeById(id);
        return employee;
    }

    @GetMapping("/get-all-employee-by-clientId/{clientId}")
    public ResponseEntity<?> getAllEmployeeByClientId(@PathVariable int clientId){
        ResponseEntity<?> employees=employeeService.getAllEmployeeByClientId(clientId);
        return employees;
    }
    @GetMapping("get-all-employee-by-desiginaion/{designation}")
    public ResponseEntity<?> getAllEmployeeByDesignation(@PathVariable String designation){
        ResponseEntity<?> employees=employeeService.getAllEmployeeByDesignation(designation);
        return employees;
    }

    @PostMapping("/add-or-update-employee/{id}")
    public ResponseEntity<?> AddOrUpdateEmployee(@PathVariable int id, @RequestBody Employee employee){
        ResponseEntity<?> response = employeeService.addOrUpdateEmployee(id,employee);
        return response;
    }
}
