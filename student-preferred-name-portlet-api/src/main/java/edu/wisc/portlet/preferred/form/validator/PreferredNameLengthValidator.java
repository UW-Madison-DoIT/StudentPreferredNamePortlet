package edu.wisc.portlet.preferred.form.validator;

import edu.wisc.portlet.preferred.form.PreferredNameExtended;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Implements basic preferred name validation rules.
 *
 * Validations implemented:
 *
 * field:error:condition
 *
 * firstName : error.required : when has no text
 * firstName : error.toolong : when longer than 30 characters
 * middleName : error.toolong : when longer than 30 characters
 * lastName : error.tooLong: when longer than 30 characters
 */
public class PreferredNameLengthValidator implements Validator {

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

    if (!StringUtils.hasText(pne.getFirstName())) {
      errors.rejectValue("firstName", "error.required");
    } else if (pne.getFirstName().length() > 30) {
      errors.rejectValue("firstName", "error.toolong");
    }

    if (pne.getMiddleName() != null && pne.getMiddleName().length() > 30) {
      errors.rejectValue("middleName", "error.toolong");
    }

    if (pne.getLastName() != null && pne.getLastName().length() > 30) {
      errors.rejectValue("lastName", "error.toolong");
    }

  }
}
