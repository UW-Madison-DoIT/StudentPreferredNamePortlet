package edu.wisc.portlet.preferred.form.validator;

import edu.wisc.portlet.preferred.form.PreferredName;
import edu.wisc.portlet.preferred.form.PreferredNameExtended;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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

  }
}