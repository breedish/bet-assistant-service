package com.betassistant.service;

import com.betassistant.config.TestConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Tests for {@link com.betassistant.service.DefaultCompetitionService}.
 *
 * @author zenind
 */
@Test
@ContextConfiguration(classes = {TestConfig.class})
public class DefaultCompetitionServiceTest extends AbstractTestNGSpringContextTests {

    @BeforeClass
    public static void init() {
    }

    @Test
    public void testTotalStatsCalculation() {
    }

    private void doAction(Set<Integer> intSet) {
        Set<Integer> mapping = intSet.stream().map(i -> i * 2).collect(Collectors.toSet());
//        intSet.stream().
    }

    private static  boolean doCheck(IntPred intPred, int i) {
        return intPred.test(i);
    }

    interface IntPred {
        boolean test(Integer value);
    }

    private static boolean isIt(Integer ss)  {
        return ss > 5;
    }
}
