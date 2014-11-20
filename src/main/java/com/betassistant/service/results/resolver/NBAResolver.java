package com.betassistant.service.results.resolver;

import com.betassistant.domain.Competition;
import com.betassistant.domain.MatchResult;
import com.betassistant.domain.Team;
import com.google.common.io.CharStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zenind
 */
public class NBAResolver implements MatchResultsResolver {

    private static final Logger LOG = LoggerFactory.getLogger(NBAResolver.class);

    private static final String URL_PATTERN = "https://www.marathonbet.com/su/sportstatext.htm?nr5=8398&gmt=%s,%s";

    private final Competition competition;

    public NBAResolver(Competition competition) {
        this.competition = competition;
    }

    @Override
    public List<MatchResult> resolve(Team team) {
        return loadData(team);
    }

    public List<MatchResult> loadData(Team team) {
        String strUrl = String.format(URL_PATTERN, competition.getId(), team.getId());
        List<MatchResult> statItems = new ArrayList<>();
        try {
            URL url = new URL(strUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            List<String> items = CharStreams.readLines(reader);
            boolean isData = false;
            for (String item : items) {
                if (item.startsWith("</pre")) {
                    isData = false;
                }
                if (isData) {
                    MatchResult statItem = splitStat2(team, item);
                    if (statItem != null) {
                        statItems.add(statItem);
                    }
                }
                if (item.startsWith("—")) {
                    isData = true;
                }
            }

        } catch (Exception e) {
            LOG.error("Error while parse stats for '{}' team: {} ", team, e);
        }

        return statItems;
    }

    private MatchResult splitStat2(Team team, String stat) {
        try {
            boolean isHome = isHome(stat);

            int index = stat.indexOf("<font");
            stat = stat.substring(index);

            int startIndex = stat.indexOf(">");
            int endIndex = stat.indexOf("</font>");
            if (startIndex > -1 && endIndex > -1) {
                String score = stat.substring(startIndex + 1, endIndex);
                String[] splittedScore = score.split(":");
                if (splittedScore.length > 1) {
                    int homeScore = Integer.parseInt(splittedScore[0].trim());
                    String strScore = splittedScore[1].trim();
                    if (strScore.endsWith("*")) {
                        strScore = strScore.substring(0, strScore.length() - 1);
                    }
                    int awayScore = Integer.parseInt(strScore);
                    return new MatchResult(isHome, isHome ? homeScore : awayScore, isHome ? awayScore : homeScore);
                }
            }
        } catch (Throwable e) {
            LOG.error("Error while parse stats '{}' for {} team : {}", stat, team.getId(), e);
        }
        return null;
    }

    private boolean isHome(String stat) {
        String[] split = stat.split(" ");
        int counter = 0;
        for (String s : split) {
            if (!StringUtils.isEmpty(s)) {
                if (counter == 2) {
                    return s.startsWith("<b>");

                }
                ++counter;
            }
        }
        return false;
    }

    @Override
    public Competition getType() {
        return competition;
    }
}
