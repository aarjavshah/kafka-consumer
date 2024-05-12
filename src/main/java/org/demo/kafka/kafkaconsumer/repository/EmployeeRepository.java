package org.demo.kafka.kafkaconsumer.repository;

import org.demo.kafka.kafkaconsumer.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
