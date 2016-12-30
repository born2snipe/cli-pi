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

import cli.pi.AppInfo;
import cli.pi.CliLog;
import org.kohsuke.MetaInfServices;

import java.util.Collection;


@MetaInfServices(CliCommand.class)
public class HelpCommand extends CliCommand {
    private CliCommandLocator commandLocator = new CliCommandLocator();

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Display all the available commands";
    }

    @Override
    protected void executeParsedArgs(CommandContext context) {
        CliLog log = context.getLog();
        log.info("--------------------------------------");
        log.info("  @|" + AppInfo.getHeadingColor() + " Available Commands|@");
        log.info("--------------------------------------");
        Collection<? extends CliCommand> allCommands = commandLocator.locateAll();
        for (CliCommand cliCommand : allCommands) {
            log.info("@|"+AppInfo.getCommandColor()+" {0}|@ - {1}", cliCommand.getName(), cliCommand.getDescription());
        }
        log.info("");
    }
}
