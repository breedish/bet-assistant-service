package com.betassistant.service;

import com.betassistant.domain.Competition;
import com.betassistant.domain.MatchResult;
import com.betassistant.resource.response.MatchesSummaryResult;
import com.google.common.io.CharStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zenind
 */
@Component
public class NBAMatchResolver implements MatchesResolver {

    private static final Logger LOG = LoggerFactory.getLogger(NBAMatchResolver.class);

    @Override
    public MatchesSummaryResult resolve(Competition competition) {
        return null;//TODO Implement
    }

    public List<MatchResult> loadData(Competition competition, String strUrl) {
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

                    MatchResult statItem = splitStat2(item);
//                    switch (competition) {
//                        case NBA_D_LEAGUE:
//                        case NBA:
//                        case NBL_AUSTRALIA:
//                            statItem = splitStat2(item);
//                            break;
//                        case NHL:
//                        case AHL:
//                            statItem = splitStat(item);
//                            break;
//                    }
                    if (statItem != null) {
                        statItems.add(statItem);
                    }
                }
                if (item.startsWith("—")) {
                    isData = true;
                }
            }

        } catch (Exception e) {
            LOG.error("zzz", "error while parse stat:", e);
        }

        return statItems;
    }

    private MatchResult splitStat(String stat) {
        String[] split = stat.split(" ");
        int counter = 0;
        boolean isHome = false;
        int homeScore = -1;
        int awayScore = -1;
        for (String s : split) {
            if (!StringUtils.isEmpty(s)) {
                if (counter == 2) {
                    if (s.startsWith("<b>")) {
                        isHome = true;
                    }
                }
                ++counter;

                if (s.startsWith("color")) {
                    int fisrtIndex = s.indexOf(">");
                    int secondIndex = s.indexOf(":");
                    LOG.info("zzz", "firstIndex: " + fisrtIndex + " : " + secondIndex);
                    if (fisrtIndex > -1 && secondIndex > -1) {
                        try {
                            homeScore = Integer.parseInt(s.substring(fisrtIndex + 1, secondIndex));
                            int offsetIndex = s.indexOf("*");
                            awayScore = Integer.parseInt(s.substring(secondIndex + 1, offsetIndex > 0 ? offsetIndex : s.length()));

                            return new MatchResult(isHome, isHome ? homeScore : awayScore, isHome ? awayScore : homeScore);
                        } catch (Exception e) {
                            LOG.error("zzz", "error while parse score: ", e);
                        }
                    }
                }
            }
        }
        return null;
    }

    private MatchResult splitStat2(String stat) {
        LOG.info("zzz", "split stat2");
        try {
            boolean isHome = isHome(stat);

            int index = stat.indexOf("<font");
            stat = stat.substring(index);


            int startIndex = stat.indexOf(">");
            int endIndex = stat.indexOf("</font>");
            LOG.info("zzz", "split2: " + startIndex + " ; " + endIndex);
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
            LOG.error("zzz", "error while parse stat", e);
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
        return Competition.NBA;
    }
}
