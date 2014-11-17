package com.betassistant.service;

import com.betassistant.config.TestConfig;
import com.betassistant.domain.MatchResult;
import com.betassistant.domain.Team;
import com.betassistant.resource.response.TotalStats;
import com.google.common.collect.Lists;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tests for {@link com.betassistant.service.DefaultCompetitionService}.
 *
 * @author zenind
 */
@Test
@ContextConfiguration(classes = {TestConfig.class})
public class DefaultCompetitionServiceTest extends AbstractTestNGSpringContextTests {

    DefaultCompetitionService competitionService = new DefaultCompetitionService(new ArrayList<>());

    @BeforeClass
    public static void init() {
    }

    @Test
    public void testTotalStatsCalculation() {
        Team t1 = Mockito.mock(Team.class);
        Team t2 = Mockito.mock(Team.class);
        Map<Team, List<MatchResult>> results = new HashMap<>();
        results.put(t1, Lists.newArrayList(new MatchResult(true, 50, 40), new MatchResult(false, 48, 30)));
        results.put(t2, Lists.newArrayList(new MatchResult(true, 48, 30), new MatchResult(true, 50, 40)));

        TotalStats stats = competitionService.calculateTotalStats(results);
        Assert.assertNotNull(stats);
        Assert.assertEquals(stats.getTotal(), 4);
        Assert.assertEquals(stats.getTotalScore(), 168);
        Assert.assertEquals(stats.getTotalHomeScore(), 98);
        Assert.assertEquals(stats.getTotalAwayScore(), 70);
    }
}
