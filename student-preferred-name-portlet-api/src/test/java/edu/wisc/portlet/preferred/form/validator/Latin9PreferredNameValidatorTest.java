package edu.wisc.portlet.preferred.form.validator;

import edu.wisc.portlet.preferred.form.PreferredName;
import edu.wisc.portlet.preferred.form.PreferredNameExtended;
import org.junit.Test;
import org.springframework.validation.*;

import java.util.HashMap;

import static org.junit.Assert.*;

public class Latin9PreferredNameValidatorTest {

  Validator validator = new Latin9PreferredNameValidator();

  /**
   * Test that the validator supports validating PreferredNameExtended.
   */
  @Test
  public void supportsPreferredNameExtended() {
    assertTrue(validator.supports(PreferredNameExtended.class));
  }

  /**
   * Test that the validator does not support validating PreferredName.
   */
  @Test
  public void doesNotSupportPreferredName() {
    assertFalse(validator.supports(PreferredName.class));
  }

  /**
   * Test that attempt to validate a PreferredName (not PreferredNameExtended) throws IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void illegalArgOnValidatingPreferredName() { ;
    BindingResult bn = new MapBindingResult(new HashMap<String, String>(), "pn");
    validator.validate(new PreferredName(), bn);
  }

  @Test
  public void requiresPreferredFirstName() {
    PreferredNameExtended pne = new PreferredNameExtended();

    BindingResult emptyStringFirstNameBindingResult = new MapBindingResult(new HashMap<String, String>(), "pn");
    pne.setFirstName("");
    validator.validate(pne, emptyStringFirstNameBindingResult);

    assertTrue(
      "firstName should have error.required when empty string",
      ValidatorTestSupport.fieldHasError("firstName", "error.required", emptyStringFirstNameBindingResult));

    BindingResult nullFirstNameBindingResult = new MapBindingResult(new HashMap<String, String>(), "pn");
    pne.setFirstName(null);
    validator.validate(pne, nullFirstNameBindingResult);

    assertTrue(
      "firstName should have error.required when empty string",
      ValidatorTestSupport.fieldHasError("firstName", "error.required", nullFirstNameBindingResult));
  }

  @Test
  public void rejectsPreferredFirstNamesLongerThan30Characters() {
    Validator validator = new Latin9PreferredNameValidator();
    BindingResult bn = new MapBindingResult(new HashMap<String, String>(), "pn");
    PreferredNameExtended pne = new PreferredNameExtended();
    pne.setFirstName("abcdefghijklmnopqrstuvwxyzabcde"); // 26 + 5 = 31 characters
    validator.validate(pne, bn);

    assertTrue("Should set error.toolong on firstName when firstName 31 characters.",
      ValidatorTestSupport.fieldHasError("firstName", "error.toolong", bn));
  }

  @Test
  public void allowsNoPreferredMiddleName() {
    Validator validator = new Latin9PreferredNameValidator();
    BindingResult bn = new MapBindingResult(new HashMap<String, String>(), "pn");
    PreferredNameExtended pne = new PreferredNameExtended();
    pne.setMiddleName(null);

    assertFalse(bn.hasFieldErrors("middleName"));
  }

}
