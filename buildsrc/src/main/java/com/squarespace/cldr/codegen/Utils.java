package com.squarespace.cldr.codegen;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ResourceInfo;
import com.squarespace.cldr.codegen.parse.PluralType;
import com.squarespace.compiler.parse.Node;
import com.squarespace.compiler.parse.Struct;


public class Utils {

  /**
   * Recursively list all resources under the given path prefixes.
   */
  public static List<Path> listResources(String ...prefixes) throws IOException {
    List<Path> result = new ArrayList<>();
    ClassPath classPath = ClassPath.from(Utils.class.getClassLoader());
    for (ResourceInfo info : classPath.getResources()) {
      String path = info.getResourceName();
      for (String prefix : prefixes) {
        if (path.startsWith(prefix)) {
          result.add(Paths.get(path));
        }
      }
    }
    return result;
  }

  /**
   * Find a child node of a given type in a parent struct, or return null.
   */
  public static Node<PluralType> findChild(Struct<PluralType> parent, PluralType type) {
    for (Node<PluralType> n : parent.nodes()) {
      if (n.type() == type) {
        return n;
      }
    }
    return null;
  }

}
