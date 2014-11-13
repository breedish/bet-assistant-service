package com.betassistant.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

/**
 * @author zenind
 */
public class MatchResult {

    @JsonProperty("isHome")
    public final boolean home;
    public final int scoreHome;
    public final int scoreAway;

    public MatchResult(boolean home, int scoreHome, int scoreAway) {
        this.home = home;
        this.scoreHome = scoreHome;
        this.scoreAway = scoreAway;
    }

    public boolean getHome() {
        return home;
    }

    public int getScoreHome() {
        return scoreHome;
    }

    public int getScoreAway() {
        return scoreAway;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("getHome", home)
            .add("homeScore", scoreHome).add("awayScore", scoreAway).toString();
    }

}
