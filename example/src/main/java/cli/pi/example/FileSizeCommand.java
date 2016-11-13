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
import net.sourceforge.argparse4j.impl.Arguments;
import org.openide.util.lookup.ServiceProvider;

import java.io.File;

@ServiceProvider(service = CliCommand.class)
public class FileSizeCommand extends CliCommand {
    public FileSizeCommand() {
        argsParser.addArgument("FILE")
                .dest("file")
                .type(Arguments.fileType())
                .help("The file to display the size of");
    }

    @Override
    public String getName() {
        return "file-size";
    }

    @Override
    public String getDescription() {
        return "Display the size of the provide file";
    }

    @Override
    protected void executeParsedArgs(CommandContext context) {
        File file = context.getNamespace().get("file");
        context.getLog().info(String.valueOf(file.length()));
    }
}
