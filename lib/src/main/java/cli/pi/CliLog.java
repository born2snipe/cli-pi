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

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.text.MessageFormat;

public class CliLog {
    static {
        AnsiConsole.systemInstall();
    }

    public void info(String format, Object... args) {
        println(MessageFormat.format(format, args));
    }

    public void info(String format) {
        println(format);
    }

    public void error(String message) {
        println("@|" + AppInfo.getErrorColor() + " " + message + "|@");
    }

    public void error(String message, Object... args) {
        println("@|" + AppInfo.getErrorColor() + " " + MessageFormat.format(message, args) + "|@");
    }

    public void warn(String message, Object... args) {
        println("@|" + AppInfo.getWarnColor() + " " + MessageFormat.format(message, args) + "|@");
    }

    private void println(String message) {
        System.out.println(Ansi.ansi().render(message));
    }
}
