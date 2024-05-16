package org.demo.kafka.kafkaconsumer;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.demo.kafka.kafkaconsumer.model.Employee;
import org.demo.kafka.kafkaconsumer.service.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@Testcontainers
@TestPropertySource(locations = "classpath:application-local.yml")
public class TestKafkaConsumerApplication {

    @Container
    public static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("employeedb")
            .withUsername("root")
            .withPassword("root");

    @Container
    public static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
    @Autowired
    private EmployeeService employeeService;

    @DynamicPropertySource
    static void registerKafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", () -> kafkaContainer.getBootstrapServers());
    }

    @Bean
    public ProducerFactory<String, Employee> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaContainer.getBootstrapServers());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }
    private final KafkaTemplate<String, Employee> kafkaTemplate = new KafkaTemplate<>(producerFactory());


    @BeforeEach
    void setup() {
        kafkaContainer.start();
        mysql.start();
    }


    @Test
    void testKafkaListener() throws Exception {
        Employee employee = Employee.builder().id(1).name("Arjav").build();

        kafkaTemplate.send("new-employee-registration", employee);
        Thread.sleep(2000);

        var receivedEmployee = employeeService.getEmployee(1);
        Assertions.assertEquals(employee, receivedEmployee);
    }

    @AfterEach
    void cleanUp() {
        kafkaContainer.stop();
        mysql.stop();
    }
}
