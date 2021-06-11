package edu.wisc.portlet.preferred.form.validator;

import edu.wisc.portlet.preferred.form.PreferredName;
import edu.wisc.portlet.preferred.form.PreferredNameExtended;
import org.junit.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;

import java.util.HashMap;

import static org.junit.Assert.*;

public class BasicPreferredNameValidatorTest {

  Validator validator = new BasicPreferredNameValidator();

  /**
   * Test that the validator supports validating PreferredNameExtended.
   */
  @Test
  public void supportsPreferredNameExtended() {
    assertTrue("Should support validating PreferredNameExtended.",
      validator.supports(PreferredNameExtended.class));
  }

  /**
   * Test that the validator does not support validating PreferredName.
   */
  @Test
  public void doesNotSupportPreferredName() {
    assertFalse("Should not support validating PreferredName",
      validator.supports(PreferredName.class));
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
    BindingResult bn = new MapBindingResult(new HashMap<String, String>(), "pn");
    PreferredNameExtended pne = new PreferredNameExtended();
    pne.setFirstName("abcdefghijklmnopqrstuvwxyzabcde"); // 26 + 5 = 31 characters
    validator.validate(pne, bn);

    assertTrue("Should set error.toolong on firstName when firstName 31 characters.",
      ValidatorTestSupport.fieldHasError("firstName", "error.toolong", bn));
  }

  @Test
  public void preferredMiddleNameIsOptional() {
    BindingResult bn = new MapBindingResult(new HashMap<String, String>(), "pn");
    PreferredNameExtended pne = new PreferredNameExtended();
    pne.setMiddleName(null);

    validator.validate(pne, bn);

    assertFalse(bn.hasFieldErrors("middleName"));
  }

  @Test
  public void preferredMiddleNameMustNotBeMoreThan30Characters() {

    BindingResult br = new MapBindingResult(new HashMap<String, String>(), "pn");

    PreferredNameExtended pne = new PreferredNameExtended();
    pne.setMiddleName("abcdefghijklmnopqrstuvwxyzabcde"); // 26 + 5 = 31 characters

    validator.validate(pne, br);

    assertTrue("31 characters should be too long for middle name",
      ValidatorTestSupport.fieldHasError("middleName", "error.toolong", br));
  }

}
