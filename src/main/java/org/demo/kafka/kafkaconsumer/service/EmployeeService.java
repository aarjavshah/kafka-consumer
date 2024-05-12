package org.demo.kafka.kafkaconsumer.service;


import lombok.RequiredArgsConstructor;
import org.demo.kafka.kafkaconsumer.exception.EmployeeNotFoundException;
import org.demo.kafka.kafkaconsumer.model.Employee;
import org.demo.kafka.kafkaconsumer.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public void createEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public Employee getEmployee(Integer id) {
        return employeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);
    }
}
