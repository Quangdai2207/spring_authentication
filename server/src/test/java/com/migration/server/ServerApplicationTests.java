package com.migration.server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("dev")
@SpringBootTest(classes = ServerApplication.class)
public class ServerApplicationTests {
    @Test
    void contextLoads() {
    }
}
