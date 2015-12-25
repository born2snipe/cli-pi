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

import cli.pi.CliLog;
import cli.pi.command.CliCommand;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.Argument;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.ArgumentType;
import net.sourceforge.argparse4j.inf.Namespace;
import org.openide.util.lookup.ServiceProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@ServiceProvider(service = CliCommand.class)
public class WhatDayOfTheWeekCommand extends CliCommand {
    public WhatDayOfTheWeekCommand() {
        argsParser.addArgument("-d", "--date")
                .type(new DateType())
                .action(Arguments.store())
                .setDefault(new Date())
                .dest("date")
                .help("The date to use when determining the day of the week (expected format: MM/dd/yyyy) Default: today");
    }

    @Override
    public String getName() {
        return "what-day-of-the-week";
    }

    @Override
    public String getDescription() {
        return "Determine the day of the week";
    }

    @Override
    protected void executeParsedArgs(CliLog log, Namespace namespace) {
        Date date = namespace.get("date");

        log.info(new SimpleDateFormat("E").format(date));
    }

    private class DateType implements ArgumentType<Date> {
        public Date convert(ArgumentParser parser, Argument arg, String value) throws ArgumentParserException {
            try {
                return new SimpleDateFormat("MM/dd/yyyy").parse(value);
            } catch (ParseException e) {
                throw new ArgumentParserException("A problem occurred when parsing the date", e, parser);
            }
        }
    }
}
