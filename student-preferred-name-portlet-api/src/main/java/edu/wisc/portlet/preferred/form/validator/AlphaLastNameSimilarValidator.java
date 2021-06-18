package edu.wisc.portlet.preferred.form.validator;

import edu.wisc.portlet.preferred.form.PreferredNameExtended;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validates that a preferred last name is "similar" to a legal last name as permitted under the historical
 * rules of only differing in capitalization, whitespace, single quote, and hyphen characters.
 *
 * lastName: error.differsBeyondSpaceHyphenQuote
 */
public class AlphaLastNameSimilarValidator  implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return PreferredNameExtended.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {

    PreferredNameExtended pne = (PreferredNameExtended) target;

    if (StringUtils.isBlank(pne.getLastName())) {
      return;
    }

    //can only change the last name casing, spacing, and punctuation

    // So, strip the ignored characters from both preferred last name and legal last name
    // then compare these ignoring case
    // if they match, then preferred last name differs from legal last name in only allowable ways.

    String strippedPreferredLastName =
      pne.getLastName().replaceAll(" ", "").replaceAll("\'", "").replaceAll("-", "");
    String strippedLegalLastName =
      pne.getLegalLastName().replaceAll(" ", "").replaceAll("\'", "").replaceAll("-", "");
    if (!strippedLegalLastName.equalsIgnoreCase(strippedPreferredLastName)) {
      errors.rejectValue("lastName", "error.differsBeyondSpaceHyphenQuote");
    }
  }
}
