package com.sitescrape.util;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.IOException;


/**
 * Created by Deslyxia on 6/22/17.
 */
public abstract class CommandLineUtil {

    private static Options options;
    private static CommandLine cmd;


    static {
        options = new Options();

        Option help = Option.builder("h")
                .longOpt("help")
                .desc("This help message.")
                .build();
        options.addOption(help);

        Option configurationDirectory = Option.builder("c")
                .longOpt("config")
                .argName("CONFIG_DIRECTORY")
                .desc("Directory to find config files.")
                .required(true)
                .type(String.class)
                .hasArg()
                .build();
        options.addOption(configurationDirectory);

    }

    protected static void addOption(Option o) {
        options.addOption(o);
    }

    protected static void addOptions(Options os) {
        for (Option o : os.getOptions()) {
            options.addOption(o);
        }
    }

    protected static CommandLine parseCommandLine(String[] args, String className) throws SecurityException, IOException {
        if (cmd != null) {
            return cmd;
        }

        HelpFormatter helpFormatter = new HelpFormatter();
        CommandLineParser parser = new DefaultParser();

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println("Error parsing command line arguments." + e);
            helpFormatter.printHelp(className, options);
            System.exit(1);
        }

        if (cmd.hasOption("help")) {
            helpFormatter.printHelp(className, options);
            System.exit(0);
        }

        String configurationDirectory = cmd.getOptionValue("config");
        RuntimeData.INSTANCE.configure(configurationDirectory);

        return cmd;
    }

    protected static boolean hasOption(String option) {
        if (cmd == null) {
            System.out.println("Command line not yet parsed. Call parseCommandLine() first.");
            System.exit(1);
        }

        return cmd.hasOption(option);
    }

}
