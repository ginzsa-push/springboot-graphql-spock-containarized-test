package me.work.test.integration

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Bean

@TestConfiguration
class IntegrationTestMockingConfig {
    @Bean
    TestRestTemplate testRestTemplate() {
        return new TestRestTemplate();
    }
}
