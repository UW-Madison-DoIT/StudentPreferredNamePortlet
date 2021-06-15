package edu.wisc.portlet.preferred.form.validator;

import edu.wisc.portlet.preferred.form.PreferredName;
import edu.wisc.portlet.preferred.form.PreferredNameExtended;
import org.junit.Test;
import org.springframework.validation.Validator;

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



}
