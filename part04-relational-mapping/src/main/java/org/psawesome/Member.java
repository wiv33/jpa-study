package org.psawesome;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pilseong.ko
 */
@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;

    //    @Column(name = "team_id")
    //    private Long TeamId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToOne
    @JoinColumn(name = "locker_id")
    private Locker locker;

    @ManyToMany
    @JoinTable(name = "member_product")
    private List<Product> products = new ArrayList<>();

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
