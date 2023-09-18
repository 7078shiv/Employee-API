package com.Kapture.Employee.Repository;
import java.util.List;
import com.Kapture.Employee.entity.Employee;

public interface EmployeeRepo {

    Employee getEmployeeById(int id);


    List<Employee> getAllEmployeeByClientId(int clientId);

    List<Employee> getAllEmployeeByDesigination(String desigination);

    void saveEmployee(Employee employee);

    void updateEmployee(Employee existingEmployee);
}
