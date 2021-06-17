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
    String ignoredCharacters = "- '";
    preferredLastName = normalizeCharacterFamily(preferredLastName, ignoredCharacters, "");
    legalLastName = normalizeCharacterFamily(legalLastName, ignoredCharacters, "");

    // normalize accented characters to their un-accented forms
    String sameAsA = "ÀÁÂÃÄÅàáâãäå";
    preferredLastName = normalizeCharacterFamily(preferredLastName, sameAsA, "a");
    legalLastName = normalizeCharacterFamily(legalLastName, sameAsA, "a");

    String sameAsE = "ÈÉÊËèéêëŒœ";
    preferredLastName = normalizeCharacterFamily(preferredLastName, sameAsE, "e");
    legalLastName = normalizeCharacterFamily(legalLastName, sameAsE, "e");

    String sameAsN = "Ññ";
    preferredLastName = normalizeCharacterFamily(preferredLastName, sameAsN, "n");
    legalLastName = normalizeCharacterFamily(legalLastName, sameAsN, "n");

    String sameASI = "ÌÍÎÏìíîï";
    preferredLastName = normalizeCharacterFamily(preferredLastName, sameASI, "i");
    legalLastName = normalizeCharacterFamily(legalLastName, sameASI, "i");

    String sameAsO = "ÒÓÔÕÖØòóôõöø";
    preferredLastName = normalizeCharacterFamily(preferredLastName, sameAsO, "o");
    legalLastName = normalizeCharacterFamily(legalLastName, sameAsO, "o");

    String sameAsS = "Šš";
    preferredLastName = normalizeCharacterFamily(preferredLastName, sameAsS, "s");
    legalLastName = normalizeCharacterFamily(legalLastName, sameAsS, "s");

    String sameAsU = "ÙÚÛÜùúûü";
    preferredLastName = normalizeCharacterFamily(preferredLastName, sameAsU, "u");
    legalLastName = normalizeCharacterFamily(legalLastName, sameAsU, "u");

    String sameAsY = "ŸÝýÿ";
    preferredLastName = normalizeCharacterFamily(preferredLastName, sameAsY, "y");
    legalLastName = normalizeCharacterFamily(legalLastName, sameAsY, "y");

    String sameAsZ = "Žž";
    preferredLastName = normalizeCharacterFamily(preferredLastName, sameAsZ, "z");
    legalLastName = normalizeCharacterFamily(legalLastName, sameAsZ, "z");

    // in Finnish and Estonian, ž may be written as zh where the accented character is unavailable.
    // however, above this validator would have mapped it to z.
    // So, map remaining instances of zh to z so they will match a ž that was mapped to z.
    preferredLastName = preferredLastName.replace("zh", "z");
    legalLastName = legalLastName.replace("zh", "z");

    // œ may be represented as e or as oe, and was mapped to e above.
    // So, map remaining instances of "oe" to "e" so that these will match.
    preferredLastName = preferredLastName.replace("oe", "e");
    legalLastName = legalLastName.replace("oe", "e");

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
