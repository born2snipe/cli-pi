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

import java.util.HashMap;
import java.util.List;

public class InputRequestor {
    private HashMap<Class, InputConverter> inputConverters = new HashMap<Class, InputConverter>();
    private CliLog log = new CliLog();
    private ConsoleWrapper console = new ConsoleWrapper();

    public InputRequestor() {
        register(YesOrNo.class, new YesOrNoInputConverter());
        register(String.class, new StringInputConverter());
    }

    public void register(Class type, InputConverter converter) {
        inputConverters.put(type, converter);
    }

    public YesOrNo askYesOrNoQuestion(String prompt, YesOrNo defaultValue) {
        return ask(prompt, defaultValue);
    }

    public String askForInput(String prompt, String defaultValue) {
        return ask(prompt, defaultValue);
    }

    public <T> T ask(String prompt, T defaultValue) {
        assertNotNull("Please provide a NOT null default value", defaultValue);

        Class<?> expectedInputType = defaultValue.getClass();
        InputConverter converter = inputConverters.get(expectedInputType);
        assertNotNull("No input converter registered for class: " + expectedInputType.getName(), converter);

        String options = convertToOptions(converter.availableValues());

        log.println("@|green,bold " + prompt + "|@ " + options + "@|yellow,bold (default=" + converter.convertToString(defaultValue) + ")|@");
        String input = console.readLine();

        if (isBlank(input)) {
            log.println("@|yellow,bold Using default: " + defaultValue + "|@");
            return defaultValue;
        }

        T result = (T) converter.convertFromInput(input);
        if (result == null) {
            return ask(prompt, defaultValue);
        }

        return result;
    }

    private String convertToOptions(List<String> values) {
        StringBuilder builder = new StringBuilder();

        if (values.size() > 0) {
            builder.append("@|cyan ");
        }

        for (int i = 0; i < values.size(); i++) {
            builder.append(values.get(i));
            if (i < values.size() - 1) {
                builder.append("/");
            }
        }

        if (values.size() > 0) {
            builder.append("|@ ");
        }

        return builder.toString();
    }

    public String askForRequiredInput(String prompt) {
        log.println("@|red,bold " + prompt + "|@ @|yellow,bold (required)|@");
        String input = console.readLine();
        while (isBlank(input)) {
            input = askForRequiredInput(prompt);
        }
        return input;
    }

    private boolean isBlank(String input) {
        return input == null || input.trim().length() == 0;
    }

    private <T> void assertNotNull(String message, T value) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public String askForProtectedInput(String prompt) {
        log.println("@|red,bold " + prompt + "|@ @|yellow,bold (required)|@");
        char[] protectedChars = console.readPassword();

        String protectedText = new String(protectedChars);
        if (isBlank(protectedText)) {
            return askForProtectedInput(prompt);
        }

        return protectedText;
    }

    public enum YesOrNo {
        YES, NO
    }
}
