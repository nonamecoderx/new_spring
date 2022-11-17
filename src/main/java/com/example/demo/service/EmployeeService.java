//создали сервис, служит для работы с базой сотрудников.
package com.example.demo.service;

import com.example.demo.exception.InvalidEmployeeRequestException;
import com.example.demo.model.Employee;
import com.example.demo.record.EmployeeRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final Map<Integer, Employee> employees = new HashMap<>();

    public Collection<Employee> getAllEmployees() {
        return this.employees.values();
    }

    public Employee addEmployee(EmployeeRequest employeeRequest) {
        if (!StringUtils.isAlpha(employeeRequest.getFirstName()) ||
                !StringUtils.isAlpha(employeeRequest.getLastName())) {
            throw new InvalidEmployeeRequestException();
        }
        Employee employee = new Employee(employeeRequest.getFirstName(),
                employeeRequest.getLastName(),
                employeeRequest.getDepartment(),
                employeeRequest.getSalary());
        this.employees.put(employee.getId(), employee);
        return employee;
    }

    public int getSalarySum() {
        return employees.values().stream()
                .mapToInt(e -> e.getSalary())
                .sum();
    }

    public Employee getSalaryMax() {
        return employees.values()
                .stream()
                .max(Comparator.comparingInt(Employee::getSalary)).get();
    }

    public Employee getSalaryMin() {
        return employees.values()
                .stream()
                .min(Comparator.comparingInt(Employee::getSalary)).get();
    }

    public Set<Employee> getHigherAverage() {
        if (employees.size() == 0) {
            throw new RuntimeException("Добавьте хоть одного сотрудника");
        }
        int average = getSalarySum() / employees.size();
        return employees.values().stream().filter(s -> s.getSalary() > average).collect(Collectors.toSet());
    }
}



