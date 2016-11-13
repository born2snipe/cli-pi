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
package cli.pi.example;

import cli.pi.command.CliCommand;
import cli.pi.command.CommandContext;
import org.openide.util.lookup.ServiceProvider;

import java.text.SimpleDateFormat;
import java.util.Date;

@ServiceProvider(service = CliCommand.class)
public class CurrentTimeCommand extends CliCommand {
    @Override
    public String getName() {
        return "current-time";
    }

    @Override
    public String getDescription() {
        return "Display the current time";
    }

    @Override
    protected void executeParsedArgs(CommandContext context) {
        context.getLog().info(new SimpleDateFormat("HH:mm a").format(new Date()));
    }
}
