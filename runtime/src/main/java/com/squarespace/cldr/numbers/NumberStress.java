package com.squarespace.cldr.numbers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.squarespace.cldr.CLDR;
import com.squarespace.cldr.CLDRLocale;


/**
 * Execute
 */
public class NumberStress {

  public static void main(String[] args) throws IOException {
    Path path = Paths.get("all-locales.txt");
    File file = path.toFile();
    if (file.exists()) {
      file.delete();
    }
    
    file = Files.createFile(path).toFile();

    String[] numbers = new String[] {
      "0",  
      "1",
      "1.0",
      "-1.224",
      "3.14159",
      "1200",
      "12345",
      "999999",
      "100000000001",
      "0.99222222111233333"
    };
    
    DecimalFormatOptions[] dopts = new DecimalFormatOptions[] {
      new DecimalFormatOptions(DecimalFormatStyle.DECIMAL),
      new DecimalFormatOptions(DecimalFormatStyle.PERCENT),
      new DecimalFormatOptions(DecimalFormatStyle.PERMILLE),
      new DecimalFormatOptions(DecimalFormatStyle.SHORT),
      new DecimalFormatOptions(DecimalFormatStyle.LONG),
    };

    CurrencyFormatOptions[] copts = new CurrencyFormatOptions[] {
      new CurrencyFormatOptions(CurrencyFormatStyle.SYMBOL),
      new CurrencyFormatOptions(CurrencyFormatStyle.ACCOUNTING),
      new CurrencyFormatOptions(CurrencyFormatStyle.NAME),
      new CurrencyFormatOptions(CurrencyFormatStyle.CODE),
      new CurrencyFormatOptions(CurrencyFormatStyle.SHORT),
    };

    CLDR cldr = CLDR.get();
    List<CLDRLocale> locales = CLDR.availableLocales();
    List<String> currencies = CLDR.availableCurrencies();

    List<BigDecimal> nums = new ArrayList<>();
    for (String num : numbers) {
      nums.add(new BigDecimal(num));
    }
    
    long start = System.currentTimeMillis();
    StringBuilder buf = new StringBuilder();
    for (CLDRLocale locale : locales) {
      NumberFormatter fmt = cldr.getNumberFormatter(locale);

      for (BigDecimal n : nums) {
        for (DecimalFormatOptions d : dopts) {
          fmt.formatDecimal(n, buf, d);
          buf.append('\n');
        }
        
        for (CurrencyFormatOptions c : copts) {
          for (String code : currencies) {
            fmt.formatCurrency(n, code, buf, c);
            buf.append('\n');
          }
        }
      }
    }
    long elapsed = System.currentTimeMillis() - start;
    System.out.println("ms: " + elapsed);
    
    FileWriter writer = new FileWriter(file);
    writer.write(buf.toString());
    writer.close();

    int dtotal = locales.size() * numbers.length * dopts.length;
    int ctotal = locales.size() * numbers.length * copts.length * currencies.size();
    System.out.println("combinations: " + (dtotal + ctotal));

  }
}
