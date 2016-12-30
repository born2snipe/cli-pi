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
package cli.pi.mock;

import cli.pi.CliLog;

import java.util.ArrayList;
import java.util.List;

public class MockCliLog extends CliLog {
    private ArrayList<String> lines = new ArrayList<String>();
    private ArrayList<String> cleanedLines = new ArrayList<String>();

    @Override
    public void println(String message) {
        lines.add(message);
        cleanedLines.add(stripOutColorInfoFrom(message));
    }

    public List<String> getLines() {
        return cleanedLines;
    }

    public List<String> getLinesWithColorInfo() {
        return lines;
    }

    public void clear() {
        lines.clear();
        cleanedLines.clear();
    }

    private String stripOutColorInfoFrom(String message) {
        return message.replaceAll("@\\|.+ (.+?)\\|@", "$1");
    }
}
