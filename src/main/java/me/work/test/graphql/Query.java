package me.work.test.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import me.work.test.model.Item;
import me.work.test.repository.ItemRepository;
import me.work.test.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class Query implements GraphQLQueryResolver {

    private final static Integer DAYS_AGO = 7;
    private final static Integer TOP_LIST = 10;

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItems() {
        return StreamSupport.stream(itemRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<Item> getTopTenStars() {
        return StreamSupport.stream(itemRepository
                .selectTop10StartsLimitedTo(AppUtils.getNDaysAgoDate(DAYS_AGO), TOP_LIST)
                .spliterator(), false)
                .collect(Collectors.toList());
    }

    public Item getItem(String id) {
        return itemRepository.findById(Long.valueOf(id))
                .orElseThrow(EntityNotFoundException::new);
    }
}
