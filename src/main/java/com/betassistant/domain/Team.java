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

    private final Competition competition;

    public Team(Long id, String name) {
        this(id, name, null);
    }

    public Team(Long id, String name, Competition competition) {
        this(id, name, name.toLowerCase(), competition);
    }

    public Team(Long id, String name, String niceName, Competition competition) {
        this.id = id;
        this.name = name;
        this.niceName = niceName;
        this.competition = competition;
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

    public Competition getCompetition() {
        return competition;
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
            && Objects.equals(niceName, that.niceName) && Objects.equals(competition, competition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, niceName, competition);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("name", name)
            .add("niceName", niceName).add("competition", competition).toString();
    }
}
