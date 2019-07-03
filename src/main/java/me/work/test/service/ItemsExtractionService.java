package me.work.test.service;

import me.work.test.model.GitRepository;

public interface ItemsExtractionService<T> {
    T extractItemList(GitRepository gitRepository);
}
