package me.work.test.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GitRepository {

    @JsonProperty("total_count")
    private String totalCount;

    @JsonProperty("items")
    private List<GitItem> items;
}
