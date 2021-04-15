package org.psawesome;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * @author pilseong.ko
 */
@Data
@Entity
public class Locker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "locker_id")
    private Long id;

    @Column
    private String name;

    @OneToOne(
            fetch = FetchType.LAZY
//            , mappedBy = "member_id"
    )
    @JoinColumn(name = "member_id")
    private Member member;
}
