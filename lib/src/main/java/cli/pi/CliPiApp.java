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

import cli.pi.command.ArgsParsingException;
import cli.pi.command.CliCommand;
import cli.pi.command.CliCommandLocator;
import cli.pi.io.WorkingDirectoryLocator;

import java.io.File;

public class CliPiApp {
    public static Exiter exiter = new Exiter();
    private static CliLog log = new CliLog();
    private static CliCommandLocator commandLocator = new CliCommandLocator();
    private static WorkingDirectoryLocator workingDirectoryLocator = new WorkingDirectoryLocator();

    public static void main(String... args) {
        String commandName = "help";
        if (args.length > 0) {
            commandName = args[0];
        }

        CliCommand command = commandLocator.locateCommand(commandName);
        try {
            if (command == null) {
                commandIsNotSupported(commandName);
            } else {
                File workingDirectory = workingDirectoryLocator.locate();
                String[] commandArgs = determineArgsForCommand(args);
                command.execute(log, workingDirectory, commandArgs);
                exiter.exit(0);
            }
        } catch (ArgsParsingException ape) {
            handleCommandFailure(commandName, ape);
        } catch (Exception e) {
            handleCommandFailure(commandName, e);
        }
    }

    private static String[] determineArgsForCommand(String[] args) {
        String[] commandArgs = args;

        if (args.length > 0) {
            commandArgs = new String[args.length - 1];
            System.arraycopy(args, 1, commandArgs, 0, commandArgs.length);
        }

        return commandArgs;
    }

    private static void handleCommandFailure(String commandName, ArgsParsingException exception) {
        log.error("\nCommand [{0}] failed", commandName);
        log.error(exception.getMessage() + "\n");
        log.info(exception.getHelp());
        exiter.exit(3);
    }

    private static void handleCommandFailure(String commandName, Exception exception) {
        log.error("\nCommand [{0}] failed\n", commandName);
        exception.printStackTrace(System.out);
        exiter.exit(2);
    }

    private static void commandIsNotSupported(String commandName) throws Exception {
        log.error("\nCommand [{0}] is not supported!\n", commandName);
        commandLocator.locateCommand("help").execute(log, new File("test"));
        exiter.exit(1);
    }
}
