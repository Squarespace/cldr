package com.squarespace.cldr.codegen;

import static com.squarespace.cldr.codegen.CodeGenerator.PACKAGE_CLDR_NUMBERS;
import static com.squarespace.cldr.codegen.CodeGenerator.PACKAGE_CLDR_PLURALS;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.google.common.base.Splitter;
import com.squarespace.cldr.codegen.parse.PluralRulePrinter;
import com.squarespace.cldr.codegen.parse.PluralType;
import com.squarespace.cldr.codegen.reader.DataReader;
import com.squarespace.cldr.codegen.reader.PluralData;
import com.squarespace.compiler.parse.Atom;
import com.squarespace.compiler.parse.Node;
import com.squarespace.compiler.parse.Struct;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;


/**
 * Emits classes to perform plural category assignment by language.
 */
public class PluralCodeGenerator {

  private static final ClassName TYPE_BASE = ClassName.get(PACKAGE_CLDR_PLURALS, "PluralRulesBase");
  private static final ClassName TYPE_NUMBER_OPERANDS = ClassName.get(PACKAGE_CLDR_NUMBERS, "NumberOperands");
  private static final ClassName TYPE_PLURAL_CATEGORY = ClassName.get(PACKAGE_CLDR_PLURALS, "PluralCategory");
  private static final TypeName TYPE_CONDITION = ClassName.get("", "Condition");

  public static void main(String[] args) throws Exception {
    Path outputDir = Paths.get("/Users/phensley/dev/squarespace-cldr/runtime/src/generated/java");
    new PluralCodeGenerator().generate(outputDir, DataReader.get());
  }
  
  /**
   * Generate a class for plural rule evaluation.
   *
   * This will build several Condition fields which evaluate specific AND conditions,
   * and methods which call these AND conditions joined by an OR operator.
   */
  public void generate(Path outputDir, DataReader reader) throws IOException {
    String className = "_PluralRules";
    TypeSpec.Builder type = TypeSpec.classBuilder(className)
        .addModifiers(PUBLIC)
        .superclass(TYPE_BASE);

    Map<String, FieldSpec> fieldMap = buildConditionFields(reader.cardinals(), reader.ordinals());
    for (Map.Entry<String, FieldSpec> entry : fieldMap.entrySet()) {
      type.addField(entry.getValue());
    }

    buildPluralMethod(type, "Cardinal", reader.cardinals(), fieldMap);
    buildPluralMethod(type, "Ordinal", reader.ordinals(), fieldMap);

    CodeGenerator.saveClass(outputDir, PACKAGE_CLDR_PLURALS, className, type.build());
  }

  /**
   * Creates a method to evaluate the plural rules for a specific language and plural type
   * (cardinal, ordinal).
   */
  private void buildPluralMethod(
      TypeSpec.Builder type, String pluralType, Map<String, PluralData> pluralMap, Map<String, FieldSpec> fieldMap) {

    MethodSpec.Builder method = MethodSpec.methodBuilder("eval" + pluralType)
        .addModifiers(PUBLIC)
        .addParameter(String.class, "language")
        .addParameter(TYPE_NUMBER_OPERANDS, "o")
        .returns(TYPE_PLURAL_CATEGORY);

    method.beginControlFlow("switch (language)");

    List<MethodSpec> ruleMethods = new ArrayList<>();
    for (Map.Entry<String, PluralData> entry : pluralMap.entrySet()) {
      String language = entry.getKey();
      String methodName = String.format("eval%s%s", pluralType, language.toUpperCase());
      method.addStatement("case $S:\n  return $L(o)", language, methodName);

      MethodSpec ruleMethod = buildRuleMethod(methodName, entry.getValue(), fieldMap);
      ruleMethods.add(ruleMethod);
    }

    method.addStatement("default:\n  return null");

    method.endControlFlow();
    type.addMethod(method.build());

    for (MethodSpec ruleMethod : ruleMethods) {
      type.addMethod(ruleMethod);
    }
  }

  /**
   * Builds a method that when called evaluates the rule and returns a PluralCategory.
   */
  private MethodSpec buildRuleMethod(String methodName, PluralData data, Map<String, FieldSpec> fieldMap) {
    MethodSpec.Builder method = MethodSpec.methodBuilder(methodName)
        .addModifiers(PRIVATE, STATIC)
        .addParameter(TYPE_NUMBER_OPERANDS, "o")
        .returns(TYPE_PLURAL_CATEGORY);

    for (Map.Entry<String, PluralData.Rule> entry : data.rules().entrySet()) {
      String category = entry.getKey();

      // Other is always the last condition in a set of rules.
      if (category.equals("other")) {
        // Last condition.
        method.addStatement("return PluralCategory.OTHER");
        break;
      }

      // Create a representation of the full rule for commenting.
      PluralData.Rule rule = entry.getValue();
      String ruleRepr = PluralRulePrinter.print(rule.condition);

      // Append all of the lambda methods we'll be invoking to evaluate this rule.
      List<String> fields = new ArrayList<>();
      for (Node<PluralType> condition : rule.condition.asStruct().nodes()) {
        String repr = PluralRulePrinter.print(condition);
        fields.add(fieldMap.get(repr).name);
      }

      // Header comment to indicate which conditions are evaluated.
      method.addComment("  $L", ruleRepr);
      if (!Objects.equals(rule.sample, "")) {
        List<String> samples = Splitter.on("@").splitToList(rule.sample);
        for (String sample : samples) {
          method.addComment("  $L", sample);
        }
      }

      // Emit the chain of OR conditions. If one is true we return the current category.
      int size = fields.size();
      String stmt = "if (";
      for (int i = 0; i < size; i++) {
        if (i > 0) {
          stmt += " || ";
        }
        stmt += fields.get(i) + ".eval(o)";
      }
      stmt += ")";

      // If the rule evaluates to true, return the associated plural category.
      method.beginControlFlow(stmt);
      method.addStatement("return PluralCategory." + category.toUpperCase());
      method.endControlFlow();
      method.addCode("\n");
    }

    return method.build();
  }

  /**
   * Maps an integer to each AND condition's canonical representation.
   */
  @SafeVarargs
  private final Map<String, FieldSpec> buildConditionFields(Map<String, PluralData>... pluralMaps) {
    Map<String, FieldSpec> index = new LinkedHashMap<>();
    int seq = 0;

    for (Map<String, PluralData> pluralMap : pluralMaps) {
      for (Map.Entry<String, PluralData> entry : pluralMap.entrySet()) {
        PluralData data = entry.getValue();

        // Iterate over the rules, drilling into the OR conditions and
        // building a field to evaluate each AND condition.
        for (Map.Entry<String, PluralData.Rule> rule : data.rules().entrySet()) {
          Node<PluralType> orCondition = rule.getValue().condition;
          if (orCondition == null) {
            continue;
          }

          // Render the representation for each AND condition, using that as a
          // key to map to the corresponding lambda Condition field that
          // computes it.
          for (Node<PluralType> andCondition : orCondition.asStruct().nodes()) {
            String repr = PluralRulePrinter.print(andCondition);
            if (index.containsKey(repr)) {
              continue;
            }

            // Build the field that represents the evaluation of the AND condition.
            FieldSpec field = buildConditionField(seq, andCondition.asStruct());
            index.put(repr, field);
            seq++;
          }
        }
      }
    }
    return index;
  }

  /**
   * Constructs a lambda Condition field that represents a chain of AND conditions,
   * that together is a single branch in an OR condition.
   */
  public FieldSpec buildConditionField(int index, Struct<PluralType> branch) {
    String fieldDoc = PluralRulePrinter.print(branch);
    String name = String.format("COND_%d", index);
    FieldSpec.Builder field = FieldSpec.builder(TYPE_CONDITION, name, PRIVATE, STATIC, FINAL)
        .addJavadoc(fieldDoc + "\n");

    List<Node<PluralType>> expressions = branch.nodes();

    CodeBlock.Builder code = CodeBlock.builder();
    code.beginControlFlow("(o) ->");

    int size = expressions.size();
    for (int i = 0; i < size; i++) {
      renderExpr(i == 0, code, expressions.get(i));
    }
    code.addStatement("return true");
    code.endControlFlow();

    field.initializer(code.build());
    return field.build();
  }

  /**
   * Render the header of a branch method.
   */
  private static void renderExpr(boolean first, CodeBlock.Builder code, Node<PluralType> expr) {
    Iterator<Node<PluralType>> iter = expr.asStruct().nodes().iterator();

    // Parse out the two forms of operand expressions we support:
    //
    //    n = <rangelist>
    //    n % m = <rangelist>
    //
    Atom<PluralType> operand = iter.next().asAtom();
    Atom<PluralType> modop = null;
    Atom<PluralType> relop = iter.next().asAtom();
    if (relop.type() == PluralType.MODOP) {
      modop = relop;
      relop = iter.next().asAtom();
    }

    String var = (String)operand.value();
    List<Node<PluralType>> rangeList = iter.next().asStruct().nodes();

    boolean decimalsZero = false;
    if (var.equals("n") && modop != null) {
      // We're applying mod to the 'n' operand, we must also ensure that
      // decimal value == 0.
      decimalsZero = true;
    }
    
    // If this is the first expression, define the variable; otherwise reuse it.
    String fmt = "zz = o.$L()";
    if (first) {
      fmt = "long " + fmt;
    }

    // Mod operations modify the operand before assignment.
    if (modop != null) {
      fmt += " % $LL";
    }

    // Emit the expression.
    if (modop != null) {
      code.addStatement(fmt, var, (int)modop.value());
    } else {
      code.addStatement(fmt, var);
    }

    renderExpr(code, rangeList, relop.value().equals("="), decimalsZero);
  }

  /**
   * Render the expression body of a branch method.
   */
  private static void renderExpr(
      CodeBlock.Builder code, List<Node<PluralType>> rangeList, boolean equal, boolean decimalsZero) {
    
    int size = rangeList.size();
    String r = "";

    // Join the range list expressions with the OR operator.
    for (int i = 0; i < size; i++) {
      if (i > 0) {
        r += " || ";
      }
      r += renderRange("zz", rangeList.get(i));
    }

    // Wrap the expression in an IF.
    String fmt = "if (";
    if (decimalsZero) {
      fmt += "o.nd() != 0 || ";
    }

    if (equal) {
      fmt += size == 1 ? "!$L" : "!($L)";
    } else {
      fmt += "($L)";
    }

    fmt += ")";

    // Wrap the IF as a block.
    code.beginControlFlow(fmt, r);
    code.addStatement("return false");
    code.endControlFlow();
  }

  /**
   * Render a the range segment of an expression.
   */
  private static String renderRange(String name, Node<PluralType> node) {
    if (node.type() == PluralType.RANGE) {
      Struct<PluralType> range = node.asStruct();
      int start = (Integer) range.nodes().get(0).asAtom().value();
      int end = (Integer) range.nodes().get(1).asAtom().value();
        return String.format("(%s >= %d && %s <= %d)", name, start, name, end);
    }
    return String.format("(%s == %s)", name, node.asAtom().value());
  }

}
