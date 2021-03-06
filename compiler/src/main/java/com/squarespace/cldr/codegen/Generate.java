package com.squarespace.cldr.codegen;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Generates all CLDR-related code.
 */
public class Generate {

  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      System.err.println("You must pass a path, absolute or relative to current directory");
      System.exit(1);
    }
    Path outputDir = Paths.get(args[0]);
    if (!outputDir.isAbsolute()) {
      outputDir = Paths.get(".").toAbsolutePath().resolve(outputDir);
    }
    new Generate().generate(outputDir.toFile());
  }

  public void generate(File outputDir) throws IOException {
    CodeGenerator.generate(outputDir.toPath());
  }
  
}
