package com.betassistant.resource.response;

import com.betassistant.domain.MatchResult;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author zenind
 */
public final class TotalStats implements Consumer<List<MatchResult>> {

    private int total;

    private int totalHomeScore;

    private int totalAwayScore;

    @Override
    public void accept(List<MatchResult> value) {
        total += value.size();
        totalHomeScore += value.stream().mapToInt(MatchResult::getScoreHome).sum();
        totalAwayScore += value.stream().mapToInt(MatchResult::getScoreAway).sum();
    }

    public void combine(TotalStats other) {
        total += other.total;
        totalHomeScore += other.totalHomeScore;
        totalAwayScore += other.totalAwayScore;
    }

    public int getTotal() {
        return total;
    }

    public int getTotalScore() {
        return getTotalHomeScore() + getTotalAwayScore();
    }

    public int getTotalHomeScore() {
        return medium(totalHomeScore);
    }

    public int getTotalAwayScore() {
        return medium(totalAwayScore);
    }

    private Integer medium(int value) {
        Double result = value > 0 ? ((double) value) / 2 : 0;
        return result.intValue();
    }
}
