package org.demo.kafka.kafkaconsumer.resource;

import org.demo.kafka.kafkaconsumer.model.Employee;
import org.demo.kafka.kafkaconsumer.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeResourceTest {

    @InjectMocks
    EmployeeResource employeeResource;

    @Mock
    EmployeeService employeeService;

    @Test
    void getEmployee() {
        var employee = Employee.builder().name("Arjav").id(1).build();
        when(employeeService.getEmployee(1)).thenReturn(employee);
        assertEquals(employee, employeeResource.getEmployee(1));
    }

    @Test
    void createEmployee() {
        var employee = Employee.builder().name("Arjav").id(1).build();
        employeeResource.createEmployee(employee);
        verify(employeeService, times(1)).createEmployee(employee);
    }
}