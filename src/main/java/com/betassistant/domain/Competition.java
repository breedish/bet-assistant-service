package com.betassistant.domain;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

import java.util.Set;

/**
 * @author zenind
 */
public enum Competition {

    NBA(
        3463L,
        "nba",
        "NBA",
        ImmutableSet.<Team>builder()
        .add(new Team(590L, "BOSTON"))
        .add(new Team(42723L, "BROOKLYN"))
        .add(new Team(201L, "NEW_YORK"))
        .add(new Team(211L, "PHILADELPHIA"))
        .add(new Team(588L, "TORONTO"))
        .add(new Team(593L, "CHICAGO"))
        .add(new Team(589L, "CLEVELAND"))
        .add(new Team(585L, "DETROIT"))
        .add(new Team(355L, "INDIANA"))
        .add(new Team(586L, "MILWAUKEE"))
        .add(new Team(584L, "ATLANTA"))
        .add(new Team(587L, "CHARLOTTE"))
        .add(new Team(358L, "MIAMI"))
        .add(new Team(196L, "ORLANDO"))
        .add(new Team(591L, "WASHINGTON"))
        .add(new Team(605L, "DENVER"))
        .add(new Team(601L, "MINNESOTA"))
        .add(new Team(18277L, "OKLAHOMA"))
        .add(new Team(595L, "PORTLAND"))
        .add(new Team(596L, "UTAH"))
        .add(new Team(603L, "GOLDEN_STATE"))
        .add(new Team(5351L, "NEW_ORLEANS"))
        .add(new Team(607L, "MEMPHIS"))
        .add(new Team(598L, "HOUSTON"))
        .add(new Team(604L, "DALLAS"))
        .add(new Team(599L, "SACRAMENTO"))
        .add(new Team(600L, "PHOENIX"))
        .add(new Team(597L, "LA_LAKERS"))
        .add(new Team(606L, "LA_CLIPPERS"))
        .add(new Team(594L, "SAN_ANTONIO"))
        .build()
    );

    private final Long id;

    private final String name;

    private final String niceName;

    private final Set<Team> teams;

    Competition(Long id, String name, String niceName, Set<Team> teams) {
        Preconditions.checkNotNull(id);
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(niceName);
        this.id = id;
        this.name = name;
        this.niceName = niceName;
        this.teams = teams;
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

    public Set<Team> getTeams() {
        return teams;
    }

    public static Competition fromString(String niceName) {
        try {
            return Enum.valueOf(Competition.class, niceName.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("No %s competition is registered", niceName));
        }
    }

}
