/**
 *
 * Copyright to the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
/**
 *
 */
package cli.pi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CliPiAppTest {
    @Mock
    private Exiter exiter;
    private PrintStream originalOutput;
    private ByteArrayOutputStream capturedOutputStream;

    @Before
    public void setUp() throws Exception {
        TestCliCommand.reset();
        OldTestCliCommand.reset();
        CliPiApp.exiter = exiter;
        originalOutput = System.out;

        capturedOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOutputStream));
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(originalOutput);
    }

    @Test
    public void shouldPassTheWorkingDirectoryWhenACommandIsExecuted() {
        CliPiApp.main("test");

        File workDirectory = TestCliCommand.getWorkDirectory();
        assertNotNull(workDirectory);
        assertTrue(workDirectory.exists());
        assertTrue(workDirectory.isDirectory());
    }

    @Test
    public void shouldSupportLegacyCommands() {
        CliPiApp.main("old-test-command");

        assertTrue(OldTestCliCommand.wasExecuted());
    }

    @Test
    public void shouldNotDisplayTheHelpTwiceWhenLookingForHelpOnASpecificCommand() {
        CliPiApp.main("test-with-args", "--help");

        assertEquals(1, getNumberOfTimesHelpWasPrinted());
    }

    @Test
    public void shouldGiveANonZeroExitCodeWhenMissingArgs() {
        CliPiApp.main("test-with-args");

        verify(exiter).exit(3);
    }

    @Test
    public void shouldNicelyDisplayAnArgsParsingException() {
        CliPiApp.main("test-with-args");

        assertFalse(capturedOutput().contains("net.sourceforge.argparse4j.inf.ArgumentParserException:"));
        assertTrue(capturedOutput().contains("argument -e is required"));
        assertEquals(1, getNumberOfTimesHelpWasPrinted());
    }

    @Test
    public void shouldDisplayHelpWhenACommandIsNotFound() {
        CliPiApp.main("doesNotExist");

        assertHelpIsDisplayed();
    }

    @Test
    public void shouldNotCareAboutCaseOfACommandName() {
        CliPiApp.main("TEST");

        assertTrue(TestCliCommand.wasExecuted());
    }

    @Test
    public void shouldNotPassTheCommandNameToTheCommand() {
        CliPiApp.main("test");

        assertEquals(Collections.emptyList(), TestCliCommand.getArgs());
    }

    @Test
    public void shouldNotPassTheCommandNameToTheCommandWhenThereAreAdditionalArgs() {
        CliPiApp.main("test", "do-something");

        assertEquals(Arrays.asList("do-something"), TestCliCommand.getArgs());
    }

    @Test
    public void shouldDefaultToHelpIfNothingIsProvidedAsArgs() {
        CliPiApp.main();

        assertHelpIsDisplayed();
    }

    @Test
    public void shouldGivenANonZeroExitCodeWhenTheCommandFails() {
        TestCliCommand.shouldExplode();

        CliPiApp.main("test");

        verify(exiter).exit(2);
    }

    @Test
    public void shouldHandleWhenACommandFails() {
        TestCliCommand.shouldExplode();

        CliPiApp.main("test");

        assertTrue(capturedOutput().contains("Command [test] failed"));
    }

    @Test
    public void shouldExitWithZeroWhenACommandExecutesSuccessfully() {
        CliPiApp.main("test");

        verify(exiter).exit(0);
    }

    @Test
    public void shouldExecuteTheCommand() {
        CliPiApp.main("test");

        assertTrue(TestCliCommand.wasExecuted());
    }

    @Test
    public void shouldGiveANonZeroExitCodeWhenACommandIsNotFound() {
        CliPiApp.main("doesNotExist");

        verify(exiter).exit(1);
    }

    @Test
    public void shouldNotBlowUpWhenGivenAValidCommand() {
        CliPiApp.main("test");

        assertEquals("", capturedOutput());
    }

    @Test
    public void shouldBlowUpWhenGivenAnUnknownCommand() {
        CliPiApp.main("doesNotExist");

        assertCommandIsNotSupported("doesNotExist");
    }

    private void assertCommandIsNotSupported(String expectedCommand) {
        assertTrue("We expected this command [" + expectedCommand + "] to not be supported",
                capturedOutput().contains("Command [" + expectedCommand + "] is not supported"));
    }

    private String capturedOutput() {
        return new String(capturedOutputStream.toByteArray());
    }

    private void assertHelpIsDisplayed() {
        assertTrue(capturedOutput().contains("Available Commands"));
    }

    private int getNumberOfTimesHelpWasPrinted() {
        return getNumberOfTimesThisWasPrinted("usage:");
    }

    private int getNumberOfTimesThisWasPrinted(String textToFind) {
        String output = capturedOutput();
        int count = 0;
        int i = output.indexOf(textToFind, -1);
        while (i > -1) {
            count++;
            i = output.indexOf(textToFind, i + 1);
        }
        return count;
    }
}
