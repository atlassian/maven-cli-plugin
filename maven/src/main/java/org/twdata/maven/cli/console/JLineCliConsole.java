package org.twdata.maven.cli.console;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.SocketException;
import jline.console.ConsoleReader;
import jline.console.completer.Completer;
import org.apache.maven.plugin.logging.Log;

public class JLineCliConsole implements CliConsole {
    private final ConsoleReader consoleReader;
    private final Log logger;

    public JLineCliConsole(InputStream in, PrintStream out, Log logger, Completer completer,
            String prompt) {
        try {
            consoleReader = new ConsoleReader(in, out);
            consoleReader.setBellEnabled(false);
            consoleReader.setPrompt(prompt + "> ");
            this.logger = logger;
            consoleReader.addCompleter(completer);
        } catch (IOException ex) {
            throw new RuntimeException("Unable to create reader to read commands.", ex);
        }
    }

    public String readLine() {
        try {
            return consoleReader.readLine();
        } catch (SocketException ex) {
            return null;
        } catch (IOException ex) {
            throw new RuntimeException("Unable to read command.", ex);
        }
    }

    public void writeInfo(String info) {
        logger.info(info);
    }

    public void writeError(String error) {
        logger.error(error);
    }

    public void writeDebug(String debug) {
        logger.debug(debug);
    }
}
