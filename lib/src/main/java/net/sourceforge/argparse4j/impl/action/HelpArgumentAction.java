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
package net.sourceforge.argparse4j.impl.action;

import cli.pi.ArgParse4jHelpColorizer;
import net.sourceforge.argparse4j.helper.HelpScreenException;
import net.sourceforge.argparse4j.inf.Argument;
import net.sourceforge.argparse4j.inf.ArgumentAction;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import org.fusesource.jansi.Ansi;

import java.util.Map;

/**
 * HACK: This is a copy of the real class with some modification to allow us to colorize the help output
 */
public class HelpArgumentAction implements ArgumentAction {
    private ArgParse4jHelpColorizer colorizer = new ArgParse4jHelpColorizer();

    @Override
    public void run(ArgumentParser parser, Argument arg, Map<String, Object> attrs, String flag, Object value) throws ArgumentParserException {
        String colorizedText = colorizer.colorize(parser.formatHelp());
        System.out.println(Ansi.ansi().render(colorizedText));
        throw new HelpScreenException(parser);
    }

    @Override
    public boolean consumeArgument() {
        return false;
    }

    @Override
    public void onAttach(Argument arg) {
    }
}