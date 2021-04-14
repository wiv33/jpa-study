package org.psawesome;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author pilseong.ko
 */
@Entity
public class Locker {

    @Id
    @Column(name = "locker_id")
    private Long id;

}
