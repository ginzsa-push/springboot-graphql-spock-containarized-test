package me.work.test.service;

import lombok.extern.slf4j.Slf4j;
import me.work.test.model.Item;
import me.work.test.repository.ItemRepository;
import me.work.test.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

@Service
@Slf4j
public class PullTaskService extends TimerTask {

    @Autowired
    private RestConnectorImpl restConnector;

    @Autowired
    private ItemRepository itemRepository;

    @Value("${poll_url:https://api.github.com/search/repositories?q=created:\">%s\"&sort=stars&order=desc}")
    private String pollUrl;
    @Value("${days_ago:7}")
    private int days;

    @Override
    //@Scheduled(cron = "${cron.expression:0 0 1 * * ?}") // every day
    @Scheduled(cron = "${cron.expression:0 * * ? * *}") // every minute
    public void run() {
        String date = AppUtils.getNDaysAgoDate(days);
        String url = extractUrl(date);

        List<Item> items = restConnector.poll(config(url));
        itemRepository.saveAll(items);
    }

    /**
     *  put all the needed configuration in a map, this could be sys.env
     * */
    public Map<String, String> config(String url) {
        Map<String, String> config = new HashMap<>();
        config.put(RestConnector.URL, url);
        return config;
    }

    public String extractUrl(String date) {
        String url = String.format(pollUrl, date);
        log.info("pulling data: {}", url);
        return url;
    }
}
