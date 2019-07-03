package me.work.test.service;

import lombok.extern.slf4j.Slf4j;
import me.work.test.model.GitRepository;
import me.work.test.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class RestConnectorImpl implements RestConnector<List<Item>> {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ItemsExtractionServiceImpl itemsExtractionService;

    @Override
    public void start() {
    }

    @Override
    public List<Item> poll(Map<String, String> config) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-version", "2");
        HttpEntity<GitRepository> entity = new HttpEntity<>(headers);

        ResponseEntity<GitRepository> restRes = restTemplate.exchange(config.get(URL),
                HttpMethod.GET, entity, GitRepository.class);

        GitRepository res = restRes.getBody();

        return itemsExtractionService.extractItemList(res);
    }

    @Override
    public void recover(Exception e, String msg) {
        // recover or communicate error
        log.error(msg, e);
    }

    @Override
    public void stop() {

    }
}
