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
package cli.pi.io;

import cli.pi.CliLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static cli.pi.io.InputRequestor.YesOrNo.NO;
import static cli.pi.io.InputRequestor.YesOrNo.YES;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InputRequestorTest {
    private static final char[] PASSWORD = new char[]{'a', 'b', 'c'};
    @InjectMocks
    private InputRequestor inputRequestor;
    @Mock
    private ConsoleWrapper console;
    @Mock
    private CliLog log;

    @Test
    public void shouldDisplayThePromptEachTimeAskingForProtectedInformation() {
        when(console.readPassword()).thenReturn(new char[0], PASSWORD);
        inputRequestor.askForProtectedInput("question");
        verify(log, times(2)).println("@|red,bold question|@ @|yellow,bold (required)|@");
    }

    @Test
    public void shouldKeepAskingForProtectedInputWhenNothingIsGiven() {
        when(console.readPassword()).thenReturn(new char[0], new char[]{' '}, new char[]{'\t'}, PASSWORD);
        assertEquals(new String(PASSWORD), inputRequestor.askForProtectedInput("question"));
    }

    @Test
    public void shouldAllowAskingForProtectedInput() {
        when(console.readPassword()).thenReturn(PASSWORD);
        assertEquals(new String(PASSWORD), inputRequestor.askForProtectedInput("question"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldBlowUpWhenNoInputProviderIsRegisteredForClass() {
        inputRequestor.ask("question", new Object());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldBlowUpIfProvidedANullDefaultValueWhenAskingYesOrNoQueston() {
        inputRequestor.askYesOrNoQuestion("question", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldBlowUpIfProvidedANullDefaultValueWhenAskingForInput() {
        inputRequestor.askForInput("question", null);
    }

    @Test
    public void shouldKeepAskingForInputUntilTheUserGivesAValidYesOrNoAnswer() {
        when(console.readLine()).thenReturn("x", "z", "Y");
        assertEquals(YES, inputRequestor.askYesOrNoQuestion("question", YES));
        verify(log, times(3)).println("@|green,bold question|@ @|cyan YES/Y/NO/N|@ @|yellow,bold (default=YES)|@");
    }

    @Test
    public void shouldAccept_y_asValidInput() {
        when(console.readLine()).thenReturn("y");
        assertEquals(YES, inputRequestor.askYesOrNoQuestion("question", YES));
    }

    @Test
    public void shouldAccept_Y_asValidInput() {
        when(console.readLine()).thenReturn("Y");
        assertEquals(YES, inputRequestor.askYesOrNoQuestion("question", YES));
    }

    @Test
    public void shouldAccept_N_asValidInput() {
        when(console.readLine()).thenReturn("N");
        assertEquals(NO, inputRequestor.askYesOrNoQuestion("question", YES));
    }

    @Test
    public void shouldAcceptUserInputWhenAskingForYesOrNoInput() {
        when(console.readLine()).thenReturn("NO");
        assertEquals(NO, inputRequestor.askYesOrNoQuestion("question", YES));
    }

    @Test
    public void shouldDisplayAMessageWhenTheDefaultValueIsUsedWhenAskingForYesOrNoInput() {
        InOrder inOrder = inOrder(console, log);

        inputRequestor.askYesOrNoQuestion("question", YES);

        inOrder.verify(console).readLine();
        inOrder.verify(log).println("@|yellow,bold Using default: YES|@");
    }

    @Test
    public void shouldDisplayAPromptWhenAskingForYesOrNoInput() {
        InOrder inOrder = inOrder(console, log);

        inputRequestor.askYesOrNoQuestion("question", YES);

        inOrder.verify(log).println("@|green,bold question|@ @|cyan YES/Y/NO/N|@ @|yellow,bold (default=YES)|@");
        inOrder.verify(console).readLine();
    }

    @Test
    public void shouldUserTheDefaultValueWhenAskingForYesOrNoInput() {
        assertEquals(NO, inputRequestor.askYesOrNoQuestion("question", NO));
    }

    @Test
    public void shouldDisplayPromptWhenAskingForRequiredInput() {
        when(console.readLine()).thenReturn("", "input");

        inputRequestor.askForRequiredInput("question");

        verify(log, times(2)).println("@|red,bold question|@ @|yellow,bold (required)|@");
    }

    @Test
    public void shouldAllowAskingForRequiredInput() {
        when(console.readLine()).thenReturn("", "\t", "    ", "\n", "input");

        assertEquals("input", inputRequestor.askForRequiredInput("question"));
    }

    @Test
    public void shouldDisplayAMessageToTheUserWhenTheDefaultValueWasUsed() {
        InOrder inOrder = inOrder(log, console);
        inputRequestor.askForInput("question", "default");

        inOrder.verify(console).readLine();
        inOrder.verify(log).println("@|yellow,bold Using default: default|@");
    }

    @Test
    public void shouldDisplayPromptWhenAskingForInput() {
        InOrder inOrder = inOrder(log, console);
        inputRequestor.askForInput("question", "default");

        inOrder.verify(log).println("@|green,bold question|@ @|yellow,bold (default=default)|@");
        inOrder.verify(console).readLine();
    }

    @Test
    public void shouldReturnTheInputValueWhenSomethingWasProvidedByTheUser() {
        when(console.readLine()).thenReturn("value");

        assertEquals("value", inputRequestor.askForInput("question:", "default"));
    }

    @Test
    public void shouldReturnTheDefaultInputValueWhenNothingWasProvidedByTheUser() {
        when(console.readLine()).thenReturn(null);

        assertEquals("default", inputRequestor.askForInput("question:", "default"));
    }
}