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
import org.openide.util.lookup.ServiceProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ServiceProvider(service = CliCommand.class)
public class TestCliCommand extends CliCommand {
    private static final List<String> args = new ArrayList<String>();
    private static boolean executed = false;
    private static boolean explode;

    public static void reset() {
        executed = false;
        explode = false;
        args.clear();
    }

    public static boolean wasExecuted() {
        return executed;
    }

    public static void shouldExplode() {
        explode = true;
    }

    public static List<String> getArgs() {
        return args;
    }

    @Override
    public String getName() {
        return "test";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    protected void executeParsedArgs(CommandContext context) {
        executed = true;
        if (explode) {
            throw new RuntimeException("BOOM");
        }
    }

    @Override
    public void execute(CliLog log, String[] args) {
        TestCliCommand.args.addAll(Arrays.asList(args));
        super.execute(log, args);
    }
}
