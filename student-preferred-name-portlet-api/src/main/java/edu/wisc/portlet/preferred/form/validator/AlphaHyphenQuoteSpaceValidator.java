package edu.wisc.portlet.preferred.form.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.wisc.portlet.preferred.form.PreferredName;
import edu.wisc.portlet.preferred.form.PreferredNameExtended;

/**
 * Validates that preferred first, middle, and last names can contain only allowable characters
 * (a-z, A-Z, single quote, hyphen, space.)
 */
public class AlphaHyphenQuoteSpaceValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return PreferredName.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!(target instanceof PreferredNameExtended)) {
            throw new IllegalArgumentException(
                "Target must be of type " + PreferredNameExtended.class.getName());
        }

        PreferredNameExtended pn = (PreferredNameExtended) target;

        final String regx = "^[A-Za-z .-]*$";
        Pattern ptrn = Pattern.compile(regx);

        if (StringUtils.isNotEmpty(pn.getFirstName())) {
          Matcher fnameMatcher = ptrn.matcher(pn.getFirstName());
          if (!fnameMatcher.find()) {
            errors.rejectValue("firstName", "error.invalidCharacter");
          }
        }

      if (StringUtils.isNotEmpty(pn.getMiddleName())) {
        Matcher mnameMatcher = ptrn.matcher(pn.getMiddleName());
        if (!mnameMatcher.find()) {
          errors.rejectValue("middleName", "error.invalidCharacter");
        }
      }

      if (StringUtils.isNotEmpty(pn.getLastName())) {
        final String lnameregx = "^[A-Za-z \\\\'-]*$";
        Pattern lnameptrn = Pattern.compile(lnameregx);
        Matcher lnameMatcher = lnameptrn.matcher(pn.getLastName());
        if (!lnameMatcher.find()) {
          errors.rejectValue("lastName", "error.invalidCharacter");
        }
      }
    }
}
