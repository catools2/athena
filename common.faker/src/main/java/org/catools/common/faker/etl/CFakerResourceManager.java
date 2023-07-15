package org.catools.common.faker.etl;

import com.mifmif.common.regex.Generex;
import org.catools.common.faker.exception.CFakerCountryNotFoundException;
import org.catools.common.faker.model.CRandomCities;
import org.catools.common.faker.model.CRandomCity;
import org.catools.common.faker.model.CRandomCountry;
import org.catools.common.faker.model.CRandomState;
import org.catools.common.faker.provider.*;
import org.catools.common.utils.CResourceUtil;
import org.catools.common.utils.CStringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * To load exists data from Ruby Faker resources
 */
public class CFakerResourceManager {
  public static synchronized CFakerCountryProvider getCountry(String countryCode3) {
    List<String> lines = readResource("data/country_info.txt");
    // remove header
    lines.remove(0);

    for (String line : lines) {
      String[] vals = CStringUtil.split(line, "\t");
      if (countryCode3.equalsIgnoreCase(vals[1])) {
        return new CFakerCountryProvider(
            new CRandomCountry(
                vals[1], // ISO
                vals[0], // ISO3
                vals[2], // Country
                vals.length > 3 ? vals[3] : "", // CurrencyCode
                vals.length > 4 ? vals[4] : "", // CurrencyName
                vals.length > 5 ? vals[5] : "", // Phone
                vals.length > 6 ? vals[6] : "", // Postal Code Format
                vals.length > 7 ? vals[7] : ""), // Postal Code Regex
            getStateProviders(vals[0]),
            getNameProvider(vals[0]),
            getCompanyProvider(vals[0]),
            getAddressProvider(vals[0]));
      }
    }
    throw new CFakerCountryNotFoundException(countryCode3);
  }

  private static CFakerStateProviders getStateProviders(String countryCode) {
    Map<String, CRandomCities> stateCitiesMap = getStateCitiesMap(countryCode);
    List<String> lines = readResource("states.txt", countryCode);

    // remove header
    lines.remove(0);

    return new CFakerStateProviders(
        lines.stream()
            .map(
                s -> {
                  String[] vals = s.split("\t");
                  return new CFakerStateProvider(
                      new CRandomState(vals[1], vals[0]), stateCitiesMap.get(vals[0]));
                })
            .collect(Collectors.toSet()));
  }

  private static Map<String, CRandomCities> getStateCitiesMap(String countryCode) {
    List<String[]> lines =
        readResource("cities.txt", countryCode).stream()
            .map(l -> l.split("\t"))
            .filter(v -> v.length == 3)
            .collect(Collectors.toList());

    // remove header
    lines.remove(0);

    Map<String, CRandomCities> output = new HashMap<>();

    lines.forEach(
        vals -> {
          String stateCode = vals[2];
          output.putIfAbsent(stateCode, new CRandomCities());
          output.get(stateCode).add(new CRandomCity(vals[1], vals[0]));
        });

    return output;
  }

  private static CFakerNameProvider getNameProvider(String countryCode) {
    return new CFakerNameProvider(
        readResource("male_name.txt", countryCode),
        readResource("female_name.txt", countryCode),
        readResource("male_middle_name.txt", countryCode),
        readResource("female_middle_name.txt", countryCode),
        readResource("male_surname.txt", countryCode),
        readResource("female_surname.txt", countryCode),
        readResource("male_prefix.txt", countryCode),
        readResource("female_prefix.txt", countryCode),
        readResource("male_suffix.txt", countryCode),
        readResource("female_suffix.txt", countryCode));
  }

  private static CFakerStreetAddressProvider getAddressProvider(String countryCode) {
    return new CFakerStreetAddressProvider(
        readResource("street_name.txt", countryCode),
        readResource("street_suffix.txt", countryCode),
        readResource("street_prefix.txt", countryCode),
        readResource("street_number_regex.txt", countryCode),
        readResource("building_number_regex.txt", countryCode));
  }

  private static CFakerCompanyProvider getCompanyProvider(String countryCode) {
    return new CFakerCompanyProvider(
        readResource("company_name.txt", countryCode),
        readResource("company_prefix.txt", countryCode),
        readResource("company_suffix.txt", countryCode));
  }

  private static List<String> readResource(String resourceName, String countryCode) {
    return readResource(String.format("data/%s/" + resourceName, countryCode.toLowerCase()));
  }

  private static List<String> readResource(String resourceFullName) {
    String RESOURCE_MARKER = "<<";
    String REGEX_MARKER = "@Reg=";
    List<String> output = new ArrayList<>();
    for (String line :
        CResourceUtil.readLines(resourceFullName.trim(), CFakerResourceManager.class).stream()
            .filter(CStringUtil::isNotBlank)
            .collect(Collectors.toList())) {
      if (line.startsWith(RESOURCE_MARKER)) {
        output.addAll(readResource(CStringUtil.substringAfter(line, RESOURCE_MARKER)));
      } else if (line.startsWith(REGEX_MARKER)) {
        new Generex(CStringUtil.substringAfter(line, REGEX_MARKER).trim())
            .getAllMatchedStrings()
            .forEach(s -> output.add(s.trim()));
      } else {
        output.add(line.trim());
      }
    }
    return output;
  }
}
