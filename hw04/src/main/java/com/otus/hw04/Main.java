package com.otus.hw04;

import com.otus.hw04.burn.MemoryBurner;
import com.otus.hw04.monitoring.GCMonitor;
import org.apache.commons.cli.*;

public class Main {

    private static CommandLine commandLine;

    public static void main(String[] args) {
        parseCommandLine(args);
        MemoryBurner burner = createBurner();
        new GCMonitor().install();

        //Rock&Roll
        burner.start();
    }

    private static void parseCommandLine(String[] args) {
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        options.addOption( "o", "objects-per-run", true, "Objects count oer iteration" );
        options.addOption( "t", "timeout", true, "Burn iteration interval (ms)" );

        try {
            commandLine = parser.parse(options, args );
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static MemoryBurner createBurner() {
        MemoryBurner burner = new MemoryBurner();
        if (commandLine.hasOption("objects-per-run")) {
            long objects = Long.valueOf(commandLine.getOptionValue("objects-per-run"));
            if (objects > 0) {
                burner.setObjectsPerRun(objects);
            }
        }

        if (commandLine.hasOption("timeout")) {
            long timeout = Long.valueOf(commandLine.getOptionValue("timeout"));
            if (timeout > 0) {
                burner.setTimeout(timeout);
            }
        }

        return burner;
    }
}
