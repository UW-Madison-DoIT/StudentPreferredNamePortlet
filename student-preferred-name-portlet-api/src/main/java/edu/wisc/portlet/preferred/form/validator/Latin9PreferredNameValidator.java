package edu.wisc.portlet.preferred.form.validator;

import edu.wisc.portlet.preferred.form.PreferredName;
import edu.wisc.portlet.preferred.form.PreferredNameExtended;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 * Validator implementing validation of the LATIN-9 character set.
 * Validates instances of PreferredNameExtended.
 *
 * Does NOT implement basic preferred name validation.
 * The intention is that callers use BasicPreferredNameValidator for basic validation and this validator for
 * validation specific to LATIN-9 considerations.
 *
 * Validations implemented:
 *
 * field:error:condition
 *
 * firstName : error.not-latin-9 : if contains a character outside Latin9 character set.
 * middleName : error.not-latin-9 : if contains a character outside Latin9 character set.
 * lastName : error.not-latin-9 : if contains a character outside Latin9 character set.
 *
 */
public class Latin9PreferredNameValidator
  implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return PreferredNameExtended.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {

    if (!(target instanceof PreferredNameExtended)) {
      throw new IllegalArgumentException(
        "Target must be of type " + PreferredNameExtended.class.getName() + " but was actually of type " + target.getClass());
    }

    PreferredNameExtended pne = (PreferredNameExtended) target;


    Charset iso_8859_15 = Charset.forName("ISO-8859-15");
    CharsetEncoder iso_8859_15_encoder = iso_8859_15.newEncoder();

    if (null != pne.getFirstName()) {
      if (! iso_8859_15_encoder.canEncode(pne.getFirstName())) {
        errors.rejectValue("firstName", "error.not-latin-9");
      }
    }

    if (null != pne.getMiddleName()) {
      if (! iso_8859_15_encoder.canEncode(pne.getMiddleName())) {
        errors.rejectValue("middleName", "error.not-latin-9");
      }
    }

    if (null != pne.getLastName()) {
      if (! iso_8859_15_encoder.canEncode(pne.getLastName())) {
        errors.rejectValue("lastName", "error.not-latin-9");
      }
    }

  }
}
