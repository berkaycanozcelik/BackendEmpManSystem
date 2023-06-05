package com.berkay.EMS.controller;

import com.berkay.EMS.exception.ResourceNotFoundException;
import com.berkay.EMS.model.Employee;
import com.berkay.EMS.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/")
public class EmployeeController {

    private EmployeeRepository employeeRepository;
    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    //get all employees
    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return  employeeRepository.findAll();
    }

    //create employee
    @PostMapping("employee")
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

    //get Employee by id
    @GetMapping("employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
        Employee employee =  employeeRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Employye not found with id: " + id));

        return ResponseEntity.ok(employee);
    }

    //update Employee
    @PutMapping("employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,@RequestBody Employee employeeDetail){
        Employee employee =  employeeRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Employee not found with id: " + id));

        employee.setFirstName(employeeDetail.getFirstName());
        employee.setLastName(employeeDetail.getLastName());
        employee.setEmailId(employeeDetail.getEmailId());

        Employee updatedEmployee = employeeRepository.save(employee);

        return ResponseEntity.ok(updatedEmployee);

    }

    @DeleteMapping("employees/{id}")
    public ResponseEntity<Map<String , Boolean>> deleteEmployee(@PathVariable Long id){

        Employee deletedEmployee =  employeeRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Employee not found with id: " + id));

        employeeRepository.delete(deletedEmployee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);

        return ResponseEntity.ok(response);
    }

}
