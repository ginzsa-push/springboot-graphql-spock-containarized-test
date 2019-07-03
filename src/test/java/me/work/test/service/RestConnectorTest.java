package me.work.test.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.work.test.model.GitItem;
import me.work.test.model.GitRepository;
import me.work.test.model.Item;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes= RestConnectorTest.TestRestConnectorConfiguration.class,
        loader= AnnotationConfigContextLoader.class)
public class RestConnectorTest {
    @TestConfiguration
    static class TestRestConnectorConfiguration {

        @Bean
        public RestConnector restConnector() {
            return new RestConnectorImpl();
        }

        @Bean
        public RestTemplate restTemplate(){
            return new RestTemplate();
        }

        @Bean
        public  ItemsExtractionServiceImpl itemsExtractionService(){
            return new ItemsExtractionServiceImpl();
        }
    }

    private MockRestServiceServer mockServer;

    @Autowired
    private RestConnectorImpl restConnector;

    @Autowired
    private RestTemplate restTemplate;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private ItemsExtractionServiceImpl itemsExtractionService;

    private Map<String, String> config;

    @Mock
    private ResponseEntity<GitRepository> entity;

    @Mock
    private GitRepository gitRepository;


    @Before
    public void init() {
        config = new HashMap<>();
        config.put(RestConnector.URL, "http://localhost:8080/test");
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void test() throws Exception {
        GitItem gitItem = ItemsExtractionServiceTest.generateGitItem();
        GitRepository gitRepository = new GitRepository();
        gitRepository.setItems(Arrays.asList(gitItem));

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(config.get(RestConnector.URL))))
                .andExpect(method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(gitRepository))
                );

        List<Item> items = restConnector.poll(config);
        mockServer.verify();
        assertEquals(items.get(0).getId(), gitItem.getId());
    }
}
