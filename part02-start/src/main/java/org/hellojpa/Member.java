package org.hellojpa;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "NAME_AGE_UNIQUE", columnNames = {"NAME", "AGE"})
})
public class Member {

    @Id
    private Long id;
    @Column(nullable = false, name = "USER_NAME", length = 10)
    private String name;

    private int age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return age == member.age && Objects.equals(id, member.id) && Objects.equals(name, member.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }
}

