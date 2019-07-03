package me.work.test.service;

import me.work.test.model.GitItem;
import me.work.test.model.GitOwner;
import me.work.test.model.GitRepository;
import me.work.test.model.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ItemsExtractionServiceTest {
    @Mock
    private GitRepository gitRepository;

    private ItemsExtractionServiceImpl itemsExtractionService = new ItemsExtractionServiceImpl();

    @Test
    public void expectEmptyListWhenGitRepoReturnsNull() {
        when(gitRepository.getItems()).thenReturn(null);
        List<Item> list = itemsExtractionService.extractItemList(gitRepository);
        assertTrue(list.isEmpty());
    }

    @Test
    public void expectEmptyListWhenGitRepoReturnsEmpty() {
        when(gitRepository.getItems()).thenReturn(Collections.emptyList());
        List<Item> list = itemsExtractionService.extractItemList(gitRepository);
        assertTrue(list.isEmpty());
    }

    @Test
    public void expectEmptyItemsWhenGitRepoHaveItems() {
        GitItem gitItem = generateGitItem();
        when(gitRepository.getItems()).thenReturn(Arrays.asList(gitItem));
        List<Item> list = itemsExtractionService.extractItemList(gitRepository);
        assertFalse(list.isEmpty());
        assertTrue(list.get(0).getId() == gitItem.getId());
        assertTrue(list.get(0).getName() == gitItem.getName());
        assertTrue(list.get(0).getStars() == gitItem.getStargazersCount());
    }


    /**
     * generate git item
     * */
    public static GitItem generateGitItem() {

        GitOwner gitOwner = new GitOwner();
        gitOwner.setLogin("test_login");

        GitItem gitItem = new GitItem();
        gitItem.setId("test_id");
        gitItem.setName("test_name");
        gitItem.setCreatedTime("test_time");
        gitItem.setOwner(gitOwner);
        gitItem.setLanguage("test_lang");
        gitItem.setStargazersCount(3);
        return gitItem;
    }

}
