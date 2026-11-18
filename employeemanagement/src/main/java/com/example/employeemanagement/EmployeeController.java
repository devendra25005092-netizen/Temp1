package com.example.employeemanagement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final List<Employee> employees = new ArrayList<>();

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employees;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        Employee employee = findEmployee(id);

        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employee);
    }

    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        employees.add(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable int id,
            @RequestBody Employee updatedEmployee) {

        Employee existing = findEmployee(id);

        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        existing.setName(updatedEmployee.getName());
        existing.setDepartment(updatedEmployee.getDepartment());
        existing.setSalary(updatedEmployee.getSalary());

        return ResponseEntity.ok(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable int id) {
        Employee employee = findEmployee(id);

        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        employees.remove(employee);
        return ResponseEntity.noContent().build();
    }

    private Employee findEmployee(int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }
}