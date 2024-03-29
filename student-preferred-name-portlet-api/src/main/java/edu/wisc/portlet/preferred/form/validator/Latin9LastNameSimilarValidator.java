package edu.wisc.portlet.preferred.form.validator;

import edu.wisc.portlet.preferred.form.PreferredNameExtended;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator requiring that preferred last name be similar to legal last name, considering the LATIN-9 character set.
 *
 * When the preferred lastName is not similar to the legal last name, sets validation failure
 *
 * lastName : error.notSimilarToLegalName ; when preferred last name not sufficiently similar to legal last name
 * lastName : error.legalLastNameNull ; when legal last name is null
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

    if (legalLastName == null) {
      errors.rejectValue("lastName", "error.legalLastNameNull");
      return; // short circuit validation
    }

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

    String sameAsC = "Çç";
    preferredLastName = normalizeCharacterFamily(preferredLastName, sameAsC, "c");
    legalLastName = normalizeCharacterFamily(legalLastName, sameAsC, "c");

    String sameAsD = "Ðð";
    preferredLastName = normalizeCharacterFamily(preferredLastName, sameAsD, "d");
    legalLastName = normalizeCharacterFamily(legalLastName, sameAsD, "d");

    String sameAsE = "ÈÉÊËèéêëŒœ";
    preferredLastName = normalizeCharacterFamily(preferredLastName, sameAsE, "e");
    legalLastName = normalizeCharacterFamily(legalLastName, sameAsE, "e");

    String sameAsN = "Ññ";
    preferredLastName = normalizeCharacterFamily(preferredLastName, sameAsN, "n");
    legalLastName = normalizeCharacterFamily(legalLastName, sameAsN, "n");

    String sameASI = "ÌÍÎÏìíîï!";
    preferredLastName = normalizeCharacterFamily(preferredLastName, sameASI, "i");
    legalLastName = normalizeCharacterFamily(legalLastName, sameASI, "i");

    String sameAsO = "ÒÓÔÕÖØòóôõöø";
    preferredLastName = normalizeCharacterFamily(preferredLastName, sameAsO, "o");
    legalLastName = normalizeCharacterFamily(legalLastName, sameAsO, "o");

    String sameAsS = "Šš$";
    preferredLastName = normalizeCharacterFamily(preferredLastName, sameAsS, "s");
    legalLastName = normalizeCharacterFamily(legalLastName, sameAsS, "s");

    String sameAsTh = "Þþ";
    preferredLastName = normalizeCharacterFamily(preferredLastName, sameAsTh, "th");
    legalLastName = normalizeCharacterFamily(legalLastName, sameAsTh, "th");

    String sameAsU = "ÙÚÛÜùúûüµ";
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

    // ð may be represented as edh but was mapped to d above.
    // So, map remaining edh to d so that these will match.
    preferredLastName = preferredLastName.replace("edh", "d");
    legalLastName = legalLastName.replace("edh", "d");

    // ð may be represented as eð but was mapped to d above.
    // eð will have been mapped to eo above
    // So, map remaining eo to d so that these will match.
    preferredLastName = preferredLastName.replace("eo", "d");
    legalLastName = legalLastName.replace("eo", "d");

    // map ß to ss
    preferredLastName.replace("ß", "ss");
    legalLastName.replace("ß", "ss");

    // however, ß can also map to sz, so
    // convert all sz to ss so if it was differently mapped it will still match.
    preferredLastName.replace("sz", "ss");
    legalLastName.replace("sz", "ss");

    if (! preferredLastName.equalsIgnoreCase(legalLastName)) {
      errors.rejectValue("lastName", "error.notSimilarToLegalName");
    }

  }

  /**
   * Return a String that is name except with any instances of normalizeFrom within name replaced with normalizeTo.
   * Intended usage is to normalize accented characters to their un-accented versions to create strings suitable for
   * comparison.
   *
   * Given null, returns null.
   *
   * @param name the name to normalize
   * @param normalizeFrom characters that will be replaced
   * @param normalizeTo String with which each instance of the characters will be replaced
   * @return String that is name with characters in normalizeFrom replaced with normalizeTo
   */
  public static String normalizeCharacterFamily(String name, String normalizeFrom, String normalizeTo) {

    if (name == null) {
      return null;
    }

    for (int i = 0; i < normalizeFrom.length(); i++) {
      name = name.replace(String.valueOf(normalizeFrom.charAt(i)), normalizeTo);
    }

    return name;
  }
}
