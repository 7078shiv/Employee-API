package com.Kapture.Employee.service;

import com.Kapture.Employee.Repository.EmployeeRepo;
import com.Kapture.Employee.entity.Employee;
import com.Kapture.Employee.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "Employee-details";
    @Autowired
    private EmployeeRepo employeeRepo;
    public ResponseEntity<?> getEmployeeById(int id) {
        try{
            Employee employee=employeeRepo.getEmployeeById(id);
            if(employee!=null){
                return ResponseHandler.generateResponse("Employee Found Successfully", HttpStatus.OK,employee);
            }
            else{
                return ResponseHandler.generateResponse("Employee Not Found",HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e){
            return ResponseHandler.generateResponse("Some Exception Occur",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getAllEmployeeByClientId(int clientId) {
        try{
            List<Employee> employee=employeeRepo.getAllEmployeeByClientId(clientId);
            if(employee!=null && employee.size()!=0){
                return ResponseHandler.generateResponse("Employee Found Successfully", HttpStatus.OK,employee);
            }
            else{
                return ResponseHandler.generateResponse("Employee Not Found",HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e){
            return ResponseHandler.generateResponse("Some Exception Occur",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> addOrUpdateEmployee(Employee employee){
        try{
            int id=employee.getId();
            if(id>0) {
                Employee existingEmployee = employeeRepo.getEmployeeById(id);
                if (existingEmployee!=null){
                    existingEmployee.setClientId(employee.getClientId());
                    existingEmployee.setDesignation(employee.getDesignation());
                    existingEmployee.setName(employee.getName());
                    existingEmployee.setEmpCode(employee.getEmpCode());
                    existingEmployee.setEnable(employee.getEnable());
                    existingEmployee.setLastModifiedDate(employee.getLastModifiedDate());
                    employeeRepo.updateEmployee(existingEmployee);
                    kafkaTemplate.send(TOPIC,existingEmployee);

                    return ResponseHandler.generateResponse("Employee Updated Successfully",HttpStatus.OK,existingEmployee);
                }
                else{
                    employeeRepo.saveEmployee(employee);
                    kafkaTemplate.send(TOPIC,employee);
                    return ResponseHandler.generateResponse("Employee added successfully",HttpStatus.OK,employee);
                }
            }
            else {
                return ResponseHandler.generateResponse("Enter valid id",HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e){
            return ResponseHandler.generateResponse("Some Exception Occur",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getAllEmployeeByDesignation(String desigination) {
        try{
            List<Employee> employee=employeeRepo.getAllEmployeeByDesigination(desigination);
            if(employee!=null && employee.size()!=0){
                return ResponseHandler.generateResponse("Employee Found Successfully", HttpStatus.OK,employee);
            }
            else{
                return ResponseHandler.generateResponse("Employee Not Found",HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e){
            return ResponseHandler.generateResponse("Some Exception Occur",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
