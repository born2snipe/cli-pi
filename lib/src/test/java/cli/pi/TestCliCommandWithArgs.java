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

import cli.pi.command.CliCommand;
import cli.pi.command.CommandContext;
import net.sourceforge.argparse4j.impl.Arguments;
import org.kohsuke.MetaInfServices;

@MetaInfServices(CliCommand.class)
public class TestCliCommandWithArgs extends CliCommand {
    public TestCliCommandWithArgs() {
        argsParser.addArgument("-e")
                .required(true)
                .action(Arguments.store())
                .help("example arg");
    }

    @Override
    public String getName() {
        return "test-with-args";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    protected void executeParsedArgs(CommandContext context) {

    }
}
