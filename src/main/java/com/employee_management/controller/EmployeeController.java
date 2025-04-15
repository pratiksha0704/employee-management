package com.employee_management.controller;

import com.employee_management.entity.Employee;
import com.employee_management.exception.EmployeeNotFoundException;
import com.employee_management.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
@SecurityRequirement(name = "basicAuth")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Operation(summary = "Get all employees - Only accessible by USER/ADMIN role", description = "Retrieve a list of all employees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employees retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access - credentials required")
    })
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @Operation(summary = "Create a new employee - Only accessible by ADMIN role", description = "Adds a new employee to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access - authentication required")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @Operation(summary = "Get employee by ID - Only accessible by USER/ADMIN role", description = "Retrieve a specific employee by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee found",
                    content = @Content(schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access - credentials required")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getByEmployeeId(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.getByEmployeeId(id);
        return employee.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
    }

    @Operation(summary = "Fully update an employee - Only accessible by ADMIN role", description = "Replaces all fields of an existing employee with new values")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Employee updated successfully, no content returned"),
            @ApiResponse(responseCode = "404", description = "Employee not found with the specified ID"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access - authentication required")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        Optional<Employee> employee = employeeService.getByEmployeeId(id);

        if (employee.isPresent()) {
            Employee existingEmployee = employee.get();
            existingEmployee.setFirstname(employeeDetails.getFirstname());
            existingEmployee.setLastname(employeeDetails.getLastname());
            existingEmployee.setEmail(employeeDetails.getEmail());
            existingEmployee.setContact(employeeDetails.getContact());
            existingEmployee.setDepartmentName(employeeDetails.getDepartmentName());

            Employee updatedEmployee = employeeService.saveEmployee(existingEmployee);
            return new ResponseEntity<>(updatedEmployee, HttpStatus.NO_CONTENT);
        } else {
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        }
    }

    @Operation(summary = "Partially update an employee - Only accessible by ADMIN role", description = "Updates one or more fields of an existing employee.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found with the specified ID"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access - authentication required")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<Employee> updateEmployeePartially(@PathVariable Long id,
                                                            @RequestBody Map<String, Object> updates) {
        Employee updatedEmployee = employeeService.updateEmployeePartially(id, updates);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    @Operation(summary = "Delete an employee - Only accessible by ADMIN role", description = "Removes an employee from the system using their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Employee deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found with the specified ID"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access - authentication required")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.getByEmployeeId(id);
        if (employee.isPresent()) {
            employeeService.deleteEmployee(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        }
    }
}
