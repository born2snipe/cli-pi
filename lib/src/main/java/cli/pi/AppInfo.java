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

import cli.pi.io.ClassPathFileReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppInfo {
    private static final String DEFAULT_ERROR_COLOR = "red";
    private static final String DEFAULT_COMMAND_COLOR = "yellow,bold";
    private static final String DEFAULT_WARN_COLOR = "yellow,bold";
    private static final String DEFAULT_HEADING_COLOR = "green";
    private static Properties properties = new Properties();
    private static final String FILENAME = "app-info.properties";

    static {
        InputStream input = null;
        try {
            input = ClassPathFileReader.read(FILENAME);
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("A problem occurred reading file:[" + FILENAME + "]", e);
        } finally {
            close(input);
        }
    }

    public static String get(String key) {
        return get(key, null);
    }

    public static String get(String key, String defaultValue) {
        String value = properties.getProperty(key);
        if (value == null || value.trim().length() == 0) {
            return defaultValue;
        }
        return value;
    }

    public static String getVersion() {
        return get("app.version");
    }

    public static String getErrorColor() {
        return get("color.error", DEFAULT_ERROR_COLOR);
    }

    public static String getWarnColor() {
        return get("color.warn", DEFAULT_WARN_COLOR);
    }

    public static String getHeadingColor() {
        return get("color.heading", DEFAULT_HEADING_COLOR);
    }

    public static String getCommandColor() {
        return get("color.command", DEFAULT_COMMAND_COLOR);
    }

    private static void close(InputStream input) {
        if (input != null) {
            try {
                input.close();
            } catch (IOException e) {

            }
        }
    }
}
