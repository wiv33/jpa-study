package org.psawesome;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pilseong.ko
 */
@Entity
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Long id;


    @ManyToMany(mappedBy = "products")
    private List<Member> members = new ArrayList<>();
}
