package org.example;

import javax.persistence.Embeddable;

/**
 * @author pilseong.ko
 */
@Embeddable
public class Address {
    private String city;

    private String street;

    private String zipcode;
}
