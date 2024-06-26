package org.demo.kafka.kafkaconsumer;

import org.demo.kafka.kafkaconsumer.model.Employee;
import org.demo.kafka.kafkaconsumer.service.EmployeeService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-local.yml")
public class RestAPITest {

    @Container
    public static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("employeedb")
            .withUsername("root")
            .withPassword("root");


    @Autowired
    EmployeeService employeeService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void setup() {
        mysql.start();
    }

    @AfterAll
    static void cleanUp() {
        mysql.stop();
    }

    @Test
    void testRestEndpoints() throws Exception {
        mockMvc.perform(post("/employees").content("{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Arjav Shah\"\n" +
                "}").accept("application/json").contentType("application/json")).andExpect(status().isOk());
        mockMvc.perform(get("/employees/1")).andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1,\"name\":\"Arjav Shah\"}"));

    }

    @Test
    void testCreateEndpoint() throws Exception {
        Employee employee = Employee.builder().id(2).name("Satoshi Nakamoto").build();
        mockMvc.perform(post("/employees").content("{\n" +
                "  \"id\": 2,\n" +
                "  \"name\": \"Satoshi Nakamoto\"\n" +
                "}").accept("application/json").contentType("application/json")).andExpect(status().isOk());
        assertEquals(employee, employeeService.getEmployee(2));
    }
}
