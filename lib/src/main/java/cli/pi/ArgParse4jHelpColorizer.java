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
package cli.pi;

public class ArgParse4jHelpColorizer {
    private static final String HEADING_COLOR = AppInfo.getHeadingColor();

    public String colorize(String helpContent) {
        StringBuilder builder = new StringBuilder();
        for (String line : helpContent.split("\r\n|\n")) {
            String colorizedText = handleHeadings(line);
            builder.append(colorizedText).append("\n");
        }
        return builder.toString();
    }

    private String handleHeadings(String line) {
        return line.replaceAll("^[^\\s].+:", jansi(HEADING_COLOR, "$0"));
    }

    private String jansi(String colorInfo, String text) {
        return "@|" + colorInfo + " " + text + "|@";
    }
}
