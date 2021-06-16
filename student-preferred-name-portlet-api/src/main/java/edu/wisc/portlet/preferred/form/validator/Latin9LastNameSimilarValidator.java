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

    String validAChars = "ÀÁÂÃÄÅàáâãäå";
    preferredLastName = normalizeCharacterFamily(preferredLastName, validAChars, "a");
    legalLastName = normalizeCharacterFamily(legalLastName, validAChars, "a");

    String validUChars = "ÙÚÛÜùúûü";
    preferredLastName = normalizeCharacterFamily(preferredLastName, validUChars, "u");
    legalLastName = normalizeCharacterFamily(legalLastName, validUChars, "u");

    String validYChars = "Ýýÿ";
    preferredLastName = normalizeCharacterFamily(preferredLastName, validYChars, "y");
    legalLastName = normalizeCharacterFamily(legalLastName, validYChars, "y");

    if (! preferredLastName.equalsIgnoreCase(legalLastName)) {
      errors.rejectValue("lastName", "error.notSimilarToLegalName");
    }

  }

  /**
   * Return a String that is name except with any instances of normalizeFrom within name replaced with normalizeTo.
   * Intended usage is to normalize accented characters to their un-accented versions to create strings suitable for
   * comparison.
   *
   * @param name
   * @param normalizeFrom
   * @param normalizeTo
   * @return String that is name with characters in normalizeFrom replaced with normalizeTo
   */
  public static String normalizeCharacterFamily(String name, String normalizeFrom, String normalizeTo) {

    for (int i = 0; i < normalizeFrom.length(); i++) {
      name = name.replace(String.valueOf(normalizeFrom.charAt(i)), normalizeTo);
    }

    return name;
  }
}
