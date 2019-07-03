package me.work.test.integration

import com.github.tomakehurst.wiremock.junit.WireMockClassRule
import groovy.json.JsonSlurper
import me.work.test.repository.ItemRepository
import me.work.test.service.PullTaskService
import org.junit.ClassRule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.http.HttpHeaders
import org.springframework.web.client.RestTemplate
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

import me.work.test.config.AppConfig
import me.work.test.config.GraphqlConfig
import com.github.tomjankes.wiremock.WireMockGroovy

import java.text.DateFormat
import java.text.SimpleDateFormat

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [ AppConfig, GraphqlConfig ])
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@ContextConfiguration(classes = [AppConfig, GraphqlConfig])
@Import([IntegrationTestMockingConfig])
class ConteinerizedIntegrationTest extends Specification {


    @ClassRule
    @Shared
    WireMockClassRule wireMockRule = new WireMockClassRule(9090)

    @Shared
    WireMockGroovy wireMock = new WireMockGroovy(9090)

    @Autowired
    MockMvc mockMvc


    @Autowired
    ItemRepository itemRepository

    @Autowired
    PullTaskService pullTaskService

    def Object object

    def setupSpec() {

        def today = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(new Date())

        wireMock.stub {
            request {
                method "GET"; url "/search/repositories" }
            response {
                status 200
                headers {
                    "${HttpHeaders.CONTENT_TYPE}" "${MediaType.APPLICATION_JSON_UTF8_VALUE}"
                }
                body """
                {
                    "total_count": "13131674",
                    "items" : [
                     { "id": "1", 
                       "name" : "int-test", 
                       "stargazers_count": "11", 
                       "created_at": "${->today}",
                       "language": "eng",
                       "owner": {
                        "login": "int-test-login"
                       }
                     }
                    ]
                } """
            }
        }
    }

    def cleanup() {
        itemRepository.deleteAll()
    }

    def "test alround request"() {
        given: "pull of data from github"
        pullTaskService.run()

        and:  "graphql query request is made"
        String rs = mockMvc.perform(post("/graphql")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content("{\"query\":\"query Item {\\n    topTenStars {\\n      id\\n      name\\n      stars\\n    }\\n}\\n\",\"variables\":null,\"operationName\":\"Item\"}")
        ).andExpect(status().isOk())
         .andReturn()
                .response
                .getContentAsString()

        object = createObject(rs)

        expect:
        object.data.topTenStars.size() > 0
        !itemRepository.findAll().isEmpty()
    }

    def Object createObject(String rs) {
        def jsonSlurper = new JsonSlurper()
        return jsonSlurper.parseText(rs)
    }

}
