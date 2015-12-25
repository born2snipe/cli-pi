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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ServiceLoader;

public class CliCommandLocator {
    public CliCommand locateCommand(String commandName) {
        for (CliCommand cliCommand : locateAll()) {
            if (cliCommand.getName().equalsIgnoreCase(commandName)) {
                return cliCommand;
            }
        }
        return null;
    }

    public Collection<CliCommand> locateAll() {
        ArrayList<CliCommand> commands = new ArrayList<CliCommand>();
        for (CliCommand cliCommand : ServiceLoader.load(CliCommand.class)) {
            commands.add(cliCommand);
        }
        Collections.sort(commands, new Alphabetized());
        return commands;
    }

    private class Alphabetized implements Comparator<CliCommand> {
        @Override
        public int compare(CliCommand o1, CliCommand o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
}
