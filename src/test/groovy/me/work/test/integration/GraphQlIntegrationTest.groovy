package me.work.test.integration

import graphql.GraphQL
import me.work.test.controller.GraphqlController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@WebMvcTest(GraphqlController.class)
class GraphQlIntegrationTest extends Specification {

    @Autowired
    private MockMvc mvc

    @MockBean private GraphQL graphQL

    def "add action to an existing rule should succeed"() {

        given: "API up and running waiting for queries"


        when: "when a request a query to graphql"
        String rs = mvc.perform(post("/graphql")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content("{\"query\":\"query Item {\\n    topTenStars {\\n      id\\n      name\\n      stars\\n    }\\n}\\n\",\"variables\":null,\"operationName\":\"Item\"}")
                )

                .andExpect(status().isOk())
                .andReturn()
                .response
                .getContentAsString()

        then: "should return a list with items in it"
        assert rs == "" // not connection so it should be empty
    }
}