package edu.wisc.portlet.preferred.form.validator;

import edu.wisc.portlet.preferred.form.PreferredName;
import edu.wisc.portlet.preferred.form.PreferredNameExtended;
import org.junit.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.HashMap;

import static org.junit.Assert.*;

public class AlphaLastNameSimilarValidatorTest {

  Validator validator = new AlphaLastNameSimilarValidator();

  @Test
  public void supportsPreferredNameExtended() {
    assertTrue(validator.supports(PreferredNameExtended.class));
  }

  @Test
  public void doesNotSupportPreferredName() {
    assertFalse(validator.supports(PreferredName.class));
  }

  @Test
  public void preferredLastNameMayAddHyphen() {
    PreferredNameExtended pne = new PreferredNameExtended();
    pne.setLastName("Ice-T");
    pne.setLegalLastName("ICET");

    BindingResult bindingResult = new MapBindingResult(new HashMap<String, String>(), "pn");

    validator.validate(pne, bindingResult);

    assertFalse("Ice-T should be allowed to prefer a dash within the name.", bindingResult.hasErrors());
  }

  @Test
  public void preferredNameMayAddSpace() {
    PreferredNameExtended pne = new PreferredNameExtended();
    pne.setLastName("Jingleheimer Schmidt");
    pne.setLegalLastName("JINGLEHEIMERSCHMIDT");

    BindingResult bindingResult = new MapBindingResult(new HashMap<String, String>(), "pn");

    validator.validate(pne, bindingResult);

    assertFalse("Adding a space within a preferred last name should be allowed.", bindingResult.hasErrors());
  }

  @Test
  public void preferredNameMayAddSingleQuote() {
    PreferredNameExtended pne = new PreferredNameExtended();
    pne.setFirstName("Sandra");
    pne.setMiddleName("Day");
    pne.setLastName("O'Connor");
    pne.setLegalLastName("OCONNOR");

    BindingResult bindingResult = new MapBindingResult(new HashMap<String, String>(), "pn");

    validator.validate(pne, bindingResult);

    assertFalse("Adding a single quote within a preferred last name should be allowed.", bindingResult.hasErrors());
  }

  @Test
  public void preferredLastNameMayNotChangeOutright() {
    PreferredNameExtended pne = new PreferredNameExtended();
    pne.setLastName("O'Malley");
    pne.setLegalLastName("OCONNOR");

    BindingResult bindingResult = new MapBindingResult(new HashMap<String, String>(), "pn");

    validator.validate(pne, bindingResult);

    assertTrue(
      "Last name changing too much should not be be valid",
      ValidatorTestSupport.fieldHasError("lastName", "error.differsBeyondSpaceHyphenQuote", bindingResult));
  }


  /**
   * Test that changes to capitalization of characters in last name are permitted.
   */
  @Test
  public void testMayPreferCapitalizationChangesInLastName() {

    PreferredNameExtended pne =
      new PreferredNameExtended(new PreferredName("Shaquille","Danger","O'Neal"),"ONEAL");

    BindingResult bn = new MapBindingResult(new HashMap<String, String>(), "pn");

    pne.setLastName("ONeal");

    ValidationUtils.invokeValidator(validator, pne, bn);

    if(bn.hasErrors()) {
      //shouldn't have failed
      fail(bn.getAllErrors().toString());
    }
  }

  /**
   * Test that allows preferred last name that differs from legal last name by inclusion of additional hyphens.
   */
  @Test
  public void testMayPreferAdditionalHyphensInLastName() {

    // prefers "Berners-Lee" with a system of record last name of "BERNERSLEE"
    PreferredNameExtended pne =
      new PreferredNameExtended(new PreferredName("Timothy","John","Berners-Lee"),"BERNERSLEE");

    BindingResult bn = new MapBindingResult(new HashMap<String, String>(), "pn");

    ValidationUtils.invokeValidator(validator, pne, bn);

    if(bn.hasErrors()) {
      //shouldn't have failed
      fail(bn.getAllErrors().toString());
    }
  }

  /**
   * Test that allows preferred last name that differs from legal last name
   * by exclusion of hyphens present in the legal name..
   */
  @Test
  public void testMayPreferFewerHypensInLastName() {
    // prefers "Lloyd Webber" with a system of record last name of "Lloyd-Webber"
    PreferredNameExtended andrewLloydWebber =
      new PreferredNameExtended(new PreferredName("Andrew","","Lloyd Webber"),"Lloyd-Webber");

    BindingResult bn = new MapBindingResult(new HashMap<String, String>(), "pn");

    ValidationUtils.invokeValidator(validator, andrewLloydWebber, bn);

    if(bn.hasErrors()) {
      //shouldn't have failed
      fail(bn.getAllErrors().toString());
    }
  }

}
