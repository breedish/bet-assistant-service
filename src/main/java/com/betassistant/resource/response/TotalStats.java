package com.betassistant.resource.response;

import com.betassistant.domain.MatchResult;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author zenind
 */
public final class TotalStats implements Consumer<List<MatchResult>> {

    private int total;

    private int totalHome;

    private int totalAway;

    @Override
    public void accept(List<MatchResult> value) {
        total += value.size();
        totalHome += value.stream().mapToInt(MatchResult::getScoreHome).sum();
        totalAway += value.stream().mapToInt(MatchResult::getScoreAway).sum();
    }

    public void combine(TotalStats other) {
        total += other.total;
        totalHome += other.totalHome;
        totalAway += other.totalAway;
    }

    public int getTotal() {
        return medium(total);
    }

    public int getTotalScore() {
        return getTotalHome() + getTotalAway();
    }

    public int getTotalHome() {
        return medium(totalHome);
    }

    public int getTotalAway() {
        return medium(totalAway);
    }

    private Integer medium(int value) {
        Double result = value > 0 ? ((double) value) / 2 : 0;
        return result.intValue();
    }
}
