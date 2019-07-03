package me.work.test.repository;

import me.work.test.model.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
    //order by i.stars desc limit 10
    //@Query("from Item i where i.datetime > ?1  and i.stars in (select distinct i.stars from Item i order by i.stars)")
    @Query(value = "select * from Item i where i.datetime > ?1 and i.msg in \n" +
            "(select distinct msg from public.item i order by msg desc limit ?2) order by msg desc", nativeQuery = true)
    List<Item> selectTop10StartsLimitedTo(String date, int limit);
}
