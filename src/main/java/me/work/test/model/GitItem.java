package me.work.test.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GitItem {
    private String id;
    private String name;
    @JsonProperty("stargazers_count")
    private Integer stargazersCount;
    private GitOwner owner;
    @JsonProperty("created_at")
    private String createdTime;
    private String language;
}
