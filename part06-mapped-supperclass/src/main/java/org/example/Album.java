package org.example;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * @author pilseong.ko
 */
@Entity
@Getter
@Setter
public class Album extends Item {
    private String artist;
}
