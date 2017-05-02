package com.squarespace.cldr.codegen.plugin;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

import com.squarespace.cldr.codegen.CodeGenerator;


public class CodegenTask extends DefaultTask {

  @TaskAction
  public void generate() {
    Project project = getProject();
    CodegenExt ext = project.getExtensions().getByType(CodegenExt.class);

    if (ext == null) {
      System.err.println("no extension defined");
      return;
    }

    Path outputDir = Paths.get(ext.outputDir);
    if (!outputDir.isAbsolute()) {
      outputDir = Paths.get(project.getProjectDir().toString(), ext.outputDir);
    }

    System.out.println("generating code into " + outputDir);
    try {
      CodeGenerator.generate(outputDir);
    } catch (IOException e) {
      throw new GradleException("Failed to generate code", e);
    }
  }
  
}
