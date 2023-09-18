package com.Kapture.Employee.Repository;
import com.Kapture.Employee.entity.Employee;
import com.Kapture.Employee.util.QueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class EmployeeRepoImpl implements EmployeeRepo{
    @Autowired
    private QueryUtil queryUtil;
    @Override
    public Employee getEmployeeById(int id){
        String query="select * from employee where id=:id";
        Map<String,Object> parametersToSet =new HashMap<>();
        parametersToSet.put("id",id);
        List<Employee> list= queryUtil.runQueryHelper(query,parametersToSet,Employee.class,null,null);
        return list==null?null:list.get(0);
    }

    @Override
    public List<Employee> getAllEmployeeByClientId(int clientId) {
        String query="select * from employee where client_id=:clientId";
        Map<String,Object> parametersToSet =new HashMap<>();
        parametersToSet.put("clientId",clientId);
        List<Employee> list= queryUtil.runQueryHelper(query,parametersToSet,Employee.class,null,null);
        return list;
    }

    @Override
    public List<Employee> getAllEmployeeByDesigination(String designation) {
        String query="select * from employee where designation=:designation";
        Map<String,Object> parametersToSet =new HashMap<>();
        parametersToSet.put("designation",designation);
        List<Employee> list= queryUtil.runQueryHelper(query,parametersToSet,Employee.class,null,null);
        return list;
    }

    @Override
    public void saveEmployee(Employee employee) {
        int clientId=employee.getClientId();
        String name=employee.getName();
        Date lastModifiedDate=employee.getLastModifiedDate();
        String designation=employee.getDesignation();
        int empCode = employee.getEmpCode();
        int enable = employee.getEnable();
        String queryString = "Insert into employee (client_id,name,last_modified_date,designation,emp_code,enable) "+
                "VALUES(:clientId,:name,:lastModifiedDate,:designation,:empCode,:enable)";
        Map<String,Object>parametersToSet =setParameters(clientId, name, lastModifiedDate, designation, empCode, enable);
        queryUtil.SaveQueryHelper(queryString,parametersToSet);
    }

    private Map<String,Object> setParameters(int clientId, String name, Date lastModifiedDate, String designation, int empCode, int enable) {
        Map<String,Object> parametersToSet = new HashMap<>();
        parametersToSet.put("clientId",clientId);
        parametersToSet.put("name",name);
        parametersToSet.put("lastModifiedDate",new Date(System.currentTimeMillis()));
        parametersToSet.put("designation",designation);
        parametersToSet.put("empCode",empCode);
        parametersToSet.put("enable",enable);
        return parametersToSet;
    }

    @Override
    public void updateEmployee(Employee existingEmployee) {
        int clientId=existingEmployee.getClientId();
        String name=existingEmployee.getName();
        Date lastModifiedDate=existingEmployee.getLastModifiedDate();
        String designation=existingEmployee.getDesignation();
        int empCode = existingEmployee.getEmpCode();
        int enable = existingEmployee.getEnable();
        String queryString = "UPDATE employee SET client_id=:clientId, name=:name,last_modified_date=:lastModifiedDate,designation=:designation,emp_code=:empCode,enable=:enable";
        Map<String,Object>parametersToSet=setParameters(clientId, name, lastModifiedDate, designation, empCode, enable);
        queryUtil.updateQueryHelper(parametersToSet,queryString);
    }
}
