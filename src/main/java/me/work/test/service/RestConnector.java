package me.work.test.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface RestConnector<R> {

    String URL = "URL";

    void start();

    @Retryable(
            value = { Exception.class },
            maxAttempts = 5,
            backoff = @Backoff(delay = 5000))
    R poll(Map<String, String> config) throws Exception;

    @Recover
    void recover(Exception e, String msg);

    void stop();
}
