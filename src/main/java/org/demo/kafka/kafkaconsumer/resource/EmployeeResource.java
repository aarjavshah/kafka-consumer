package org.demo.kafka.kafkaconsumer.resource;

import lombok.RequiredArgsConstructor;
import org.demo.kafka.kafkaconsumer.model.Employee;
import org.demo.kafka.kafkaconsumer.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmployeeResource {

    private final EmployeeService employeeService;

    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable(name = "employeeId") Integer employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    @PostMapping("/employees")
    public void createEmployee(@RequestBody Employee employee) {
        employeeService.createEmployee(employee);
    }
}
