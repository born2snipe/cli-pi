package cli.pi.mock;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MockCliLogTest {
    private MockCliLog log;

    @Before
    public void setUp() throws Exception {
        log = new MockCliLog();
    }

    @Test
    public void shouldAllowClearingTheLoggedLines() {
        log.info("info");
        log.clear();

        assertTrue(log.getLines().isEmpty());
        assertTrue(log.getLinesWithColorInfo().isEmpty());
    }

    @Test
    public void shouldByDefaultCaptureLinesAndRemoveTheColorInformation() {
        log.info("info");
        log.warn("warn-{0}", "1");

        assertEquals(Arrays.asList("info", "warn-1"), log.getLines());
    }

    @Test
    public void shouldAllowQueryingForTheLinesAndIncludingTheColorInformation() {
        log.info("info");
        log.warn("warn-{0}", "1");

        assertEquals(Arrays.asList("info", "@|yellow,bold warn-1|@"), log.getLinesWithColorInfo());
    }
}