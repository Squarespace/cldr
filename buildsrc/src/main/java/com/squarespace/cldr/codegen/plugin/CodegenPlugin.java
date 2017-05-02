package com.squarespace.cldr.codegen.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.compile.JavaCompile;


public class CodegenPlugin implements Plugin<Project> {

  private static final String CLDR_TASK = "cldr";
  
  private static final String CLDR_EXTENSION = "cldr";
  
  @Override
  public void apply(Project project) {
    project.getTasks().create(CLDR_TASK, CodegenTask.class);
    project.getExtensions().create(CLDR_EXTENSION, CodegenExt.class);  

    for (Project p : project.getAllprojects()) {
      TaskContainer tasks = p.getTasks();
      for (Task t : tasks) {
        if (t instanceof JavaCompile) {
          t.dependsOn(tasks.findByName(CLDR_TASK));
        }
      }
    }
  }
  
}
