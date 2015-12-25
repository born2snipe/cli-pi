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

import cli.pi.CliLog;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.internal.HelpScreenException;

public abstract class CliCommand {
    protected ArgumentParser argsParser;

    public CliCommand() {
        argsParser = ArgumentParsers.newArgumentParser(getName())
                .description(getDescription());
    }

    public abstract String getName();

    public abstract String getDescription();

    protected abstract void executeParsedArgs(CliLog log, Namespace namespace);

    public void execute(CliLog log, String... args) {
        try {
            executeParsedArgs(log, argsParser.parseArgs(args));
        } catch (HelpScreenException hse) {
            // just eat it
        } catch (ArgumentParserException e) {
            throw new ArgsParsingException(e);
        }
    }
}
