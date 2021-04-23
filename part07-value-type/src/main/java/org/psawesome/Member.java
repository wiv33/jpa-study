package org.psawesome;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author pilseong.ko
 */
@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int age;

    @Embedded
    private Period workPeriod;

    @Embedded
    private Address homeAddress;

}
