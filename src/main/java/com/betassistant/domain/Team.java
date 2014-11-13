package com.betassistant.domain;

import com.google.common.base.MoreObjects;

import java.util.Objects;

/**
 * @author zenind
 */
public class Team {

    private final Long id;

    private final String name;

    private final String niceName;

    public Team(Long id, String name) {
        this(id, name, name.toLowerCase());
    }

    public Team(Long id, String name, String niceName) {
        this.id = id;
        this.name = name;
        this.niceName = niceName;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNiceName() {
        return niceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Team that = (Team) o;

        return Objects.equals(id, that.id) && Objects.equals(name, that.name)
            && Objects.equals(niceName, that.niceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, niceName);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("name", name)
            .add("niceName", niceName).toString();
    }
}
