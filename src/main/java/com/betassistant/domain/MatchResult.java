package com.betassistant.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

/**
 * @author zenind
 */
public class MatchResult {

    public final boolean isHome;
    public final int scoreHome;
    public final int scoreAway;

    public MatchResult(boolean isHome, int scoreHome, int scoreAway) {
        Preconditions.checkNotNull(scoreAway, scoreHome);
        this.isHome = isHome;
        this.scoreHome = scoreHome;
        this.scoreAway = scoreAway;
    }

    public boolean isHome() {
        return isHome;
    }

    public int getScoreHome() {
        return scoreHome;
    }

    public int getScoreAway() {
        return scoreAway;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("isHome", isHome)
            .add("homeScore", scoreHome).add("awayScore", scoreAway).toString();
    }

}
