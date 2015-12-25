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
package cli.pi.command;

import cli.pi.ArgParse4jHelpColorizer;
import net.sourceforge.argparse4j.inf.ArgumentParserException;

public class ArgsParsingException extends RuntimeException {
    private ArgParse4jHelpColorizer helpColorizer = new ArgParse4jHelpColorizer();
    private ArgumentParserException realError;

    public ArgsParsingException(ArgumentParserException realError) {
        this.realError = realError;
    }

    @Override
    public String getMessage() {
        return realError.getMessage();
    }

    public String getHelp() {
        return helpColorizer.colorize(realError.getParser().formatHelp());
    }
}
