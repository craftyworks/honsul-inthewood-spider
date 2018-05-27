package com.honsul.inthewood.core.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandUtils {
  private final static Logger logger = LoggerFactory.getLogger(CommandUtils.class);
  
  private static String executeCommand(CommandLine commandline) throws IOException {
    logger.debug("executing command : {}", commandline);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    DefaultExecutor exec = new DefaultExecutor();
    PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
    exec.setStreamHandler(streamHandler);
    exec.execute(commandline);

    String output = outputStream.toString();
    logger.debug("executed command : {}, result : {}", commandline, output);
    return output;
  }

  private static CommandLine getLinuxCommandLine(String command) {
    CommandLine cmdLine = new CommandLine("/bin/bash");
    cmdLine.addArgument("-c");
    cmdLine.addArgument(command, false);
    return cmdLine;
  }

  private static CommandLine getWindowCommandLine(String command) {
    return CommandLine.parse("cmd /c " + command);
  }

  public static String executeCommand(String command) throws IOException {
    if (isWindows()) {
      return executeCommand(getWindowCommandLine(command));
    } else {
      return executeCommand(getLinuxCommandLine(command));
    }
  }

  private static boolean isWindows() {
    String osName = System.getProperty("os.name");
    return StringUtils.containsIgnoreCase(osName, "win");
  }
}
