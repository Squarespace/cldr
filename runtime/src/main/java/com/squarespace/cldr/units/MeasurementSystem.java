package com.squarespace.cldr.units;

import com.squarespace.cldr.CLDR;


/**
 * Conversions and preferred units are determined relative to a measurement system, which is
 * determined by the territory:
 *
 *  http://www.unicode.org/reports/tr35/tr35-general.html#Measurement_System_Data
 * 
 *  <measurementSystem type="metric"  territories="001"/>
 *  <measurementSystem type="US"  territories="LR MM US"/>
 *  <measurementSystem type="metric" category="temperature" territories="LR MM"/>
 *  <measurementSystem type="US" category="temperature" territories="BS BZ KY PR PW"/>
 *  <measurementSystem type="UK"  territories="GB"/>
 */
public enum MeasurementSystem {

  METRIC,
  METRIC_WITH_US_TEMPERATURE,
  US,
  US_WITH_METRIC_TEMPERATURE,
  UK
  ;
  
  public static MeasurementSystem fromLocale(CLDR.Locale locale) {
    String territory = locale.territory();

    switch (territory) {
      case "LR":
      case "MM":
        return US_WITH_METRIC_TEMPERATURE;

      case "BS":
      case "BZ":
      case "KY":
      case "PR":
      case "PW":
        return METRIC_WITH_US_TEMPERATURE;
        
      case "GB":
        return UK;
        
      case "US":
        return US;
        
      default:
        return METRIC;
    }
  }
  
  public boolean usesMetric(UnitCategory category) {
    switch (this) {
      case US:
      case UK:
        return false;

      case METRIC_WITH_US_TEMPERATURE:
        return category != UnitCategory.TEMPERATURE;

      case US_WITH_METRIC_TEMPERATURE:
        return category == UnitCategory.TEMPERATURE;
        
      case METRIC:
        return true;
    }
    return true;
  }
  
}
