package me.work.test.service;


import lombok.extern.slf4j.Slf4j;
import me.work.test.model.GitRepository;
import me.work.test.model.Item;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ItemsExtractionServiceImpl implements ItemsExtractionService<List<Item>>{
    @Override
    public List<Item> extractItemList(GitRepository gitRepository) {

        if (gitRepository.getItems() == null) {
            return Collections.emptyList();
        }

        return gitRepository.getItems().stream().map(gitItem -> {
            Item item = new Item(gitItem.getId(),
                                 gitItem.getName(),
                                 gitItem.getStargazersCount(),
                                 gitItem.getCreatedTime(),
                                 gitItem.getLanguage());

            return item;
        }).collect(Collectors.toList());
    }
}
