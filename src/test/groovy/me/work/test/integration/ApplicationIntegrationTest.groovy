package me.work.test.integration

import me.work.test.controller.ApplicationController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

@AutoConfigureMockMvc
@WebMvcTest(ApplicationController.class)
class ApplicationIntegrationTest extends Specification {

    @Autowired
    private MockMvc mvc

    def "add action to an existing rule should succeed"() {

        given: "API up and running waiting for queries"


        when: "when a request ping"
        String rs = mvc.perform(get("/api/ping"))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .getContentAsString()

        then: "should return a list with items in it"
        assert rs == "pinging Ok"
    }
}