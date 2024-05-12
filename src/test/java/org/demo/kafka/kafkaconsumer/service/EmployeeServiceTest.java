package org.demo.kafka.kafkaconsumer.service;

import org.demo.kafka.kafkaconsumer.exception.EmployeeNotFoundException;
import org.demo.kafka.kafkaconsumer.model.Employee;
import org.demo.kafka.kafkaconsumer.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @InjectMocks
    EmployeeService employeeService;

    @Mock
    EmployeeRepository employeeRepository;

    @Test
    void getEmployee_ReturnsValidEmployee() {
        var employee = Employee.builder().name("Arjav").id(1).build();
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        assertEquals(employee, employeeService.getEmployee(1));
    }

    @Test
    void getEmployee_NotFound() {
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployee(1));
    }
}