package edu.wisc.portlet.preferred.form.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

/**
 * Parameterized tests for shared testing between preferred name validators.
 */
public class ValidatorTestSupport {


  /**
   * Answers whether a given field has the given error code in the given Errors.
   * @param fieldName
   * @param expectedErrorCode
   * @param errors
   */
  public static boolean fieldHasError(String fieldName, String expectedErrorCode, Errors errors) {

    for (FieldError fieldError: errors.getFieldErrors("firstName")) {
      for (String errorCode: fieldError.getCodes()) {
        if (expectedErrorCode.equals(errorCode)) {
          return true;
        }
      }
    }

    return false;
  }
}
