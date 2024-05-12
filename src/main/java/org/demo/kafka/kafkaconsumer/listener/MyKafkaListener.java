package org.demo.kafka.kafkaconsumer.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.demo.kafka.kafkaconsumer.model.Employee;
import org.demo.kafka.kafkaconsumer.service.EmployeeService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class MyKafkaListener {

    private final EmployeeService employeeService;

    @KafkaListener(topics = "new-employee-registration", groupId = "registration")
    public void listenGroupFoo(Employee employee) {
        log.info("Employee object coming in => {}", employee);
        employeeService.createEmployee(employee);
    }
}
