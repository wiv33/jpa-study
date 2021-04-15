package org.psawesome;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * @author pilseong.ko
 */
@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String name;

    //    @Column(name = "team_id")
    //    private Long TeamId;
    //    @ManyToOne(fetch = FetchType.LAZY)
    //    @JoinColumn(name = "team_id")
    //    private Team team;

    @OneToOne(
            fetch = FetchType.LAZY
//            , mappedBy = "member"
    )
    //    @JoinColumn(name = "locker_id")
    private Locker locker;

    //    @ManyToMany
    //    @JoinTable(name = "member_product")
    //    private List<Product> products = new ArrayList<>();

    //    public void changeTeam(Team team) {
    //        this.team = team;
    //        team.getMembers().add(this);
    //    }
}
