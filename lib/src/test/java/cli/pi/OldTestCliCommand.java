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

import cli.pi.command.CliCommand;
import net.sourceforge.argparse4j.inf.Namespace;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = CliCommand.class)
public class OldTestCliCommand extends CliCommand {
    private static boolean executed = false;

    public static void reset() {
        executed = false;
    }

    public static boolean wasExecuted() {
        return executed;
    }

    @Override
    public String getName() {
        return "old-test-command";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    protected void executeParsedArgs(CliLog log, Namespace namespace) {
        executed = true;
    }
}
