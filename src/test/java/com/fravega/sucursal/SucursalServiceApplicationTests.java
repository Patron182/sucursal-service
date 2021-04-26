package com.fravega.sucursal;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql("/database-data.sql")
@AutoConfigureMockMvc
@AutoConfigureMockRestServiceServer
public class SucursalServiceApplicationTests {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected MockRestServiceServer server;

    @Autowired
    protected ObjectMapper om;

    @Test
    void contextLoads() {
    }


}
