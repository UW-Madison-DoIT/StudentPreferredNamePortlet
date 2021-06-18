package edu.wisc.portlet.preferred.form.validator;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ValidationUtils;

import edu.wisc.portlet.preferred.form.PreferredName;
import edu.wisc.portlet.preferred.form.PreferredNameExtended;
import org.springframework.validation.Validator;

public class AlphaHyphenQuoteSpaceValidatorTest {

  Validator validator = new AlphaHyphenQuoteSpaceValidator();

  @Test
  public void testLastNameRegexValid() {
    PreferredNameExtended pne =
      new PreferredNameExtended(new PreferredName("Shaquille","Danger","O'Neal"),"ONEAL");

    BindingResult bn = new MapBindingResult(new HashMap<String, String>(), "pn");

    ValidationUtils.invokeValidator(new AlphaHyphenQuoteSpaceValidator(), pne, bn);

    if(bn.hasErrors()) {
      fail(bn.getAllErrors().toString());
    }
  }

  @Test
  public void testDoubleQuoteDisallowedInLastName() {

    PreferredNameExtended pne =
      new PreferredNameExtended();

    pne.setLastName("O\"Neal"); // disallowed because double quote rather than single quote

    BindingResult bn = new MapBindingResult(new HashMap<String, String>(), "pn");

    ValidationUtils.invokeValidator(validator, pne, bn);

    assertTrue(ValidatorTestSupport.fieldHasError("lastName", "error.invalidCharacter", bn));
  }

  @Test
  public void testHyphensAllowed() {

    PreferredNameExtended pne = new PreferredNameExtended();

    pne.setFirstName("Ice-T");
    pne.setMiddleName("Jean-Paul");
    pne.setLastName("Berners-Lee");

    BindingResult bn = new MapBindingResult(new HashMap<String, String>(), "pn");

    ValidationUtils.invokeValidator(validator, pne, bn);

    assertFalse("Hyphens should be allowed in preferred names.", bn.hasErrors());
  }

  @Test
  public void testAccentDisallowed() {

    PreferredNameExtended pne =
      new PreferredNameExtended();

    pne.setLastName("Ramírez"); // disallowed because accented i is not in supported character set

    BindingResult bn = new MapBindingResult(new HashMap<String, String>(), "pn");

    ValidationUtils.invokeValidator(validator, pne, bn);

    assertTrue(ValidatorTestSupport.fieldHasError("lastName", "error.invalidCharacter", bn));
  }

  @Test
  public void testFrançoisDisallowed() {
    PreferredNameExtended pne =
      new PreferredNameExtended();

    pne.setFirstName("François"); // disallowed because accented c is not in supported character set

    BindingResult bn = new MapBindingResult(new HashMap<String, String>(), "pn");

    ValidationUtils.invokeValidator(validator, pne, bn);

    assertTrue(ValidatorTestSupport.fieldHasError("firstName", "error.invalidCharacter", bn));
  }

}
