package edu.wisc.portlet.preferred.form.validator;

import edu.wisc.portlet.preferred.form.PreferredNameExtended;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator requiring that preferred last name be similar to legal last name, considering the LATIN-9 character set.
 *
 * When the preferred lastName is not similar to the legal last name, sets validation failure
 *
 * lastName : error.notSimilarToLegalName
 */
public class Latin9LastNameSimilarValidator  implements Validator  {
  @Override
  public boolean supports(Class<?> clazz) {
    return PreferredNameExtended.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {

    PreferredNameExtended pne = (PreferredNameExtended) target;

    String preferredLastName = pne.getLastName();
    String legalLastName = pne.getLegalLastName();

    if (preferredLastName == null) {
      return; // short circuit validation
    }

    // delete out ignored characters for purposes of evaluating similarity
    preferredLastName = preferredLastName.replace("-", ""); // ignore hyphens
    legalLastName = legalLastName.replace("-", "");
    preferredLastName = preferredLastName.replace(" ", ""); // ignore space characters
    legalLastName = legalLastName.replace(" ", "");
    preferredLastName = preferredLastName.replace("'", ""); // ignore single quote characters
    legalLastName = legalLastName.replace("'", "");

    if (! preferredLastName.equalsIgnoreCase(legalLastName)) {
      errors.rejectValue("lastName", "error.notSimilarToLegalName");
    }

  }
}
