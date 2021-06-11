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
 * This validator implements the specifically restrictive rules.
 * It is intended to be used after the basic validator validates basic rules.
 *
 * Implements validation rules that
 * 1. Preferred last name can differ from "legal" last name only by casing, spacing, single quote, and hyphens.
 * 2. Preferred first and middle names can contain only allowable characters.
 */
public class RestrictivePreferredNameValidator implements Validator {

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
        Matcher fnameMatcher = ptrn.matcher(pn.getFirstName());
        Matcher mnameMatcher = ptrn.matcher(pn.getMiddleName());

        final String lnameregx = "^[A-Za-z \\\\'-]*$";
        Pattern lnameptrn = Pattern.compile(lnameregx);
        Matcher lnameMatcher = lnameptrn.matcher(pn.getLastName());

        if (!fnameMatcher.find() || !mnameMatcher.find() || !lnameMatcher.find()) {
            errors.rejectValue("firstName", "error.invalidCharacter");
        } else if (!StringUtils.isBlank(pn.getLastName())) {
            //can only change the last name casing, spacing, and punctuation
            //strip out all the spacing, ', and -
            String comparableLastName =
                pn.getLastName().replaceAll(" ", "").replaceAll("\'", "").replaceAll("-", "");
            //do the same op to the legal name just in case HRS starts to get fancy
            String comparableLegalLastName =
                pn.getLegalLastName().replaceAll(" ", "").replaceAll("\'", "").replaceAll("-", "");
            if (!comparableLastName.equalsIgnoreCase(comparableLegalLastName)) {
                errors.rejectValue("lastName", "error.lastNameWeirdLogicError");
            }

        }


    }
}
