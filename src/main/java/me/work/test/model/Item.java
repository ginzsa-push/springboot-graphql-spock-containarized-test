package me.work.test.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@ToString @EqualsAndHashCode
@AllArgsConstructor
public class Item {
    @Id
    private @NonNull String id;
    private @NonNull String name;
    @Column(name="msg")
    private Integer stars;
    private @NonNull String datetime;
    private String language;

    public Item(){}
}
