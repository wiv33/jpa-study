package org.psawesome;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

/**
 * @author pilseong.ko
 */
@Embeddable
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class Address {
    private String city;

    private String street;

    private String zipcode;
}
