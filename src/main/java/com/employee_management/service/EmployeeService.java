package com.employee_management.service;

import com.employee_management.entity.Employee;
import com.employee_management.exception.EmployeeNotFoundException;
import com.employee_management.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    // Method to get all employees
    public List<Employee> getAllEmployees() {
        logger.trace("Fetching all employees - trace");
        logger.debug("Fetching all employees - Debug");
        logger.info("Fetching all employees - info");
        logger.warn("Fetching all employees - warn");
        logger.error("Fetching all employees - error");
        return employeeRepository.findAll();
    }

    // Method to get employees with pagination and sorting
    public Page<Employee> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    // Method to get employee by ID
    public Optional<Employee> getByEmployeeId(Long id) {
        logger.info("Fetching employee with id: {}", id);
        return employeeRepository.findById(id);
    }

    // Method to save an employee
    public Employee saveEmployee(Employee employee) {
        logger.info("Saving employee: {}", employee);
        return employeeRepository.save(employee);
    }

    // Method to delete an employee by ID
    public void deleteEmployee(long id) {
        logger.info("Deleting employee with id {}", id);
        employeeRepository.deleteById(id);
    }

    // method to update employee partially PATCH request

    // In EmployeeService.java

    public Employee updateEmployeePartially(Long id, Map<String, Object> updates) {
        Optional<Employee> employeeOpt = getByEmployeeId(id);
        if (!employeeOpt.isPresent()) {
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        }

        Employee employee = employeeOpt.get();

        // Business logic: update only provided fields
        if (updates.containsKey("firstname")) {
            employee.setFirstname((String) updates.get("firstname"));
        }
        if (updates.containsKey("lastname")) {
            employee.setLastname((String) updates.get("lastname"));
        }
        if (updates.containsKey("email")) {
            employee.setEmail((String) updates.get("email"));
        }
        if (updates.containsKey("contact")) {
            employee.setContact((String) updates.get("contact"));
        }
        if (updates.containsKey("departmentName")) {
            employee.setDepartmentName((String) updates.get("departmentName"));
        }

        // Save and return the updated employee
        return saveEmployee(employee);
    }

}
