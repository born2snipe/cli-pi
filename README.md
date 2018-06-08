# Cli-Pi
Because sometimes making a CLI can be a real pain in the a$$

#### What does it do?
 - combine some libraries that work: [argparse4j][3], [jansi][4], [cli-progress][5]
 - easily make a CLI app that is extensible
 - remove some boilerplate code

#### How does it work?
This library uses Java's [ServiceLoader][1] to gather up all the [CliCommand][2]s in the application's classpath.
This makes it really easy for anyone to add a new command they "wish" they had in your application.

### How do I get started?
I would suggest checking out the `example` module. This is an example of how to setup an application with maven.

**Base requirements**
 - Make the `cli.pi.CliApp` your `Main-Class` in your `MANIFEST.MF` so your commands will be picked up and exceptions will be handled
 - Create file `app-info.properties` in the root directory of your application. This file expects to have one property `app.version`
 and this is used when a user wants to know the version of our application.
 - All your commands are expected to extend the class `CliCommand` and to have an entry of each command class
    in the `META-INF/services/cli.pi.command.CliCommand` file. This can be auto-generated for you if you look in the **FAQ**.

#### FAQ
 - Is there anyway to auto-generate the files that get put in `META-INF/services` for my `CliCommand`s ?
    - I usually annotate my `CliCommand` implementations with the `@MetaInfServices` annotation provided in the `metainf-services` module.
     This module contains an annotation processor that will generate the service file at compile time based on what `service` you declared in the annotation.
     So you only need this module at compile time and not runtime.

[1]: https://docs.oracle.com/javase/7/docs/api/java/util/ServiceLoader.html "serviceloader"
[2]: https://github.com/born2snipe/cli-pi/blob/master/src/main/java/cli/pi/command/CliCommand.java "cli-command"
[3]: http://argparse4j.sourceforge.net "argparse4j"
[4]: https://github.com/fusesource/jansi "jansi"
[5]: https://github.com/born2snipe/cli-progress "cli-progress"

