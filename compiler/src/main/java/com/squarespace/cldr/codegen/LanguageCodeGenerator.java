package com.squarespace.cldr.codegen;

import static com.squarespace.cldr.codegen.Types.HASHMAP;
import static com.squarespace.cldr.codegen.Types.HASHSET;
import static com.squarespace.cldr.codegen.Types.LANGUAGE_MATCH;
import static com.squarespace.cldr.codegen.Types.LIST_LANGUAGE_MATCH;
import static com.squarespace.cldr.codegen.Types.LIST_STRING;
import static com.squarespace.cldr.codegen.Types.MAP;
import static com.squarespace.cldr.codegen.Types.PACKAGE_CLDR;
import static com.squarespace.cldr.codegen.Types.SET;
import static com.squarespace.cldr.codegen.Types.SET_STRING;
import static com.squarespace.cldr.codegen.Types.STRING;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PROTECTED;
import static javax.lang.model.element.Modifier.STATIC;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.lang.model.element.Modifier;

import com.squarespace.cldr.LanguageMatch;
import com.squarespace.cldr.codegen.reader.DataReader;
import com.squarespace.cldr.codegen.reader.LanguageMatchingData;
import com.squarespace.compiler.parse.Pair;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;


public class LanguageCodeGenerator {

  public static void main(String[] args) throws IOException {
    Path root = Paths.get("/Users/phensley/dev/squarespace-cldr");
    new LanguageCodeGenerator().generate(root.resolve("runtime/src/generated/java"), DataReader.get());
  }

  public void generate(Path outputDir, DataReader reader) throws IOException {
    String className = "_LanguageRules";
    TypeSpec.Builder type = TypeSpec.classBuilder(className)
        .addModifiers(Modifier.PUBLIC);

    LanguageMatchingData data = reader.languageMatching();
    
    buildMatchingRules(type, data);
    buildParadigmLocales(type, data);
    buildMatchVariables(type, reader.territoryContainment(), data);
    
    CodeGenerator.saveClass(outputDir, PACKAGE_CLDR, className, type.build());
  }
  
  private static void buildMatchingRules(TypeSpec.Builder type, LanguageMatchingData data) {
    List<LanguageMatch> matches = data.languageMatches;
    int size = matches.size();
    
    CodeBlock.Builder code = CodeBlock.builder();
    code.add("$T.asList(\n", Arrays.class);
    
    for (int i = 0; i < size; i++) {
      if (i != 0) {
        code.add(",\n");
      }
      LanguageMatch match = matches.get(i);
      code.add("  new $T($S, $S, $L, $L)", LANGUAGE_MATCH,
          match.desiredRaw(), match.supportedRaw(), match.distance(), match.oneway());
    }
    code.add("\n)");
    
    type.addField(FieldSpec.builder(LIST_LANGUAGE_MATCH, "LANGUAGE_MATCHES", PROTECTED, STATIC, FINAL)
        .initializer(code.build())
        .build());
  }

  private static void buildParadigmLocales(TypeSpec.Builder type, LanguageMatchingData data) {
    CodeBlock.Builder code = CodeBlock.builder();
    addStringList(code, data.paradigmLocales, "  ");
    type.addField(FieldSpec.builder(LIST_STRING, "PARADIGM_LOCALES", PROTECTED, STATIC, FINAL)
        .initializer(code.build())
        .build());
  }
  
  /**
   * Build table containing language matching variables.
   */
  private static void buildMatchVariables(
      TypeSpec.Builder type, Map<String, Set<String>> territoryData, LanguageMatchingData data) {
    
    Map<String, Set<String>> territories = flatten(territoryData);
    
    CodeBlock.Builder code = CodeBlock.builder();
    code.add("new $T<$T, $T<$T>>() {{\n", HASHMAP, STRING, SET, STRING);
    for (Map.Entry<String, Set<String>> entry : territories.entrySet()) {
      code.add("  put($S, new $T<>(", entry.getKey(), HASHSET);
      List<String> list = new ArrayList<>(entry.getValue());
      Collections.sort(list);
      addStringList(code, list, "    ");
      code.add("));\n");
    }
    code.add("\n}}");
    
    TypeName typeName = ParameterizedTypeName.get(MAP, STRING, SET_STRING);
    type.addField(FieldSpec.builder(typeName, "TERRITORIES", PROTECTED, STATIC, FINAL)
        .initializer(code.build())
        .build());

    // Generate mapping for each variable and its inverse.
    Map<String, Set<String>> variables = new LinkedHashMap<>();
    
    for (Pair<String, String> pair : data.matchVariables) {
      String variable = pair._1;
      
      Set<String> countries = new HashSet<>();
      for (String regionId : pair._2.split("\\+")) {
        Set<String> children = territories.get(regionId);
        if (children != null) {
          countries.addAll(children);
        } else {
          countries.add(regionId);
        }
      }

      variables.put(variable, countries);
    }

    // Build the final variable tables.
    code = CodeBlock.builder();
    code.add("new $T<$T, $T<$T>>() {{\n", HASHMAP, STRING, SET, STRING);
    for (Map.Entry<String, Set<String>> entry : variables.entrySet()) {
      code.add("  put($S, new $T<>(", entry.getKey(), HASHSET);
      List<String> list = new ArrayList<>(entry.getValue());
      Collections.sort(list);
      addStringList(code, list, "    ");
      code.add("));\n");
    }
    code.add("\n}}");
    
    type.addField(FieldSpec.builder(typeName, "VARIABLES", PROTECTED, STATIC, FINAL)
        .initializer(code.build())
        .build());
  }
  
  /**
   * Flatten the territory containment hierarchy.
   */
  private static Map<String, Set<String>> flatten(Map<String, Set<String>> data) {
    Map<String, Set<String>> territories = new TreeMap<>();
    Set<String> keys = data.keySet();
    for (String key : keys) {
      Set<String> values = flatten(key, territories, data);
      territories.put(key, values);
    }
    return territories;
  }
  
  /**
   * Flatten one level of the territory containment hierarchy.
   */
  private static Set<String> flatten(
      String parent, Map<String, Set<String>> flat, Map<String, Set<String>> data) {
    
    Set<String> countries = new LinkedHashSet<>();
    Set<String> keys = data.get(parent);
    if (keys == null) {
      return countries;
    }
    for (String region : keys) {
      if (data.containsKey(region)) {
        Set<String> children = flatten(region, flat, data);
        flat.put(region, children);
        countries.addAll(children);
      } else {
        countries.add(region);
      }
    }
    return countries;
  }

  /**
   * Helper to add a list of strings to a code block.
   */
  private static void addStringList(CodeBlock.Builder code, List<String> list, String indent) {
    int size = list.size();
    code.add("$T.asList(\n$L", Arrays.class, indent);
    int width = indent.length();
    for (int i = 0; i < size; i++) {
      if (i != 0) {
        code.add(", ");
        width += 2;
      }
      if (width > 80) {
        width = indent.length();
        code.add("\n$L", indent);
      }
      String element = list.get(i);
      code.add("$S", element);
      width += element.length() + 2;
    }
    code.add("\n$L)", indent);
  }

}
