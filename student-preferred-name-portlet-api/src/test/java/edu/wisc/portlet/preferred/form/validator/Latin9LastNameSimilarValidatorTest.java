package edu.wisc.portlet.preferred.form.validator;

import edu.wisc.portlet.preferred.form.PreferredName;
import edu.wisc.portlet.preferred.form.PreferredNameExtended;
import org.junit.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;

import java.util.HashMap;

import static org.junit.Assert.*;

public class Latin9LastNameSimilarValidatorTest {

  Validator validator = new Latin9LastNameSimilarValidator();

  @Test
  public void supportsPreferredNameExtended() {
    assertTrue(validator.supports(PreferredNameExtended.class));
  }

  @Test
  public void doesNotSupportPreferredName() {
    assertFalse(validator.supports(PreferredName.class));
  }

  /**
   * Test that a preferred last name identical to the legal last name is valid.
   */
  @Test
  public void matchingLastNameIsValid() {

    PreferredNameExtended pne = new PreferredNameExtended();
    pne.setLastName("Badger");
    pne.setLegalLastName("Badger");

    BindingResult br = new MapBindingResult(new HashMap<String, String>(), "pn");

    validator.validate(pne, br);

    assertFalse (br.hasErrors());
  }

  /**
   * Test that people who do not have last name Blank can't just decide to have last name Blank.
   */
  @Test
  public void peopleNotNamedBlankCannotImpersonateTheChancellor(){

    PreferredNameExtended pne = new PreferredNameExtended();
    pne.setLastName("Blank"); // prefer to have the Chancellor's last name
    pne.setLegalLastName("Smith"); // but legally has last name Smith

    BindingResult br = new MapBindingResult(new HashMap<String, String>(), "pn");

    validator.validate(pne, br);

    assertTrue("Should fail validation because preferred last name is wildly dissimilar to legal last name.",
      ValidatorTestSupport.fieldHasError("lastName", "error.notSimilarToLegalName", br));

  }

}