package edu.wisc.portlet.preferred.form.validator;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ValidationUtils;

import edu.wisc.portlet.preferred.form.PreferredName;
import edu.wisc.portlet.preferred.form.PreferredNameExtended;

public class PreferredNameValidatorTest {

  @Test
  public void testLastNameRegexValid() {
    PreferredNameExtended pne =
      new PreferredNameExtended(new PreferredName("Shaquille","Danger","O'Neal"),"ONEAL");

    BindingResult bn = new MapBindingResult(new HashMap<String, String>(), "pn");

    ValidationUtils.invokeValidator(new PreferredNameValidator(), pne, bn);

    if(bn.hasErrors()) {
      fail(bn.getAllErrors().toString());
    }
  }

  @Test
  public void testLastNameRegexBadQuotes() {

    PreferredNameExtended pne =
      new PreferredNameExtended(new PreferredName("Shaquille","Danger","O'Neal"),"ONEAL");

    BindingResult bn = new MapBindingResult(new HashMap<String, String>(), "pn");

    pne.setLastName("O`Neal");//backtick not valid

    ValidationUtils.invokeValidator(new PreferredNameValidator(), pne, bn);

    if(!bn.hasErrors() || bn.getAllErrors().size() != 1) {
      fail("This should error due to the wrong single quote being used");
    }
  }

  @Test
  public void testLastNameTryingToChange() {

    PreferredNameExtended pne =
      new PreferredNameExtended(new PreferredName("Shaquille","Danger","O'Neal"),"ONEAL");

    BindingResult bn = new MapBindingResult(new HashMap<String, String>(), "pn");

    pne.setFirstName("Opera");
    pne.setLastName("Winfrey");

    ValidationUtils.invokeValidator(new PreferredNameValidator(), pne, bn);

    if(!bn.hasErrors() || bn.getAllErrors().size() != 1) {
      fail("This should error due to ONEAL trying to become Winfrey");
    }
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

    ValidationUtils.invokeValidator(new PreferredNameValidator(), pne, bn);

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

    ValidationUtils.invokeValidator(new PreferredNameValidator(), pne, bn);

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

    ValidationUtils.invokeValidator(new PreferredNameValidator(), andrewLloydWebber, bn);

    if(bn.hasErrors()) {
      //shouldn't have failed
      fail(bn.getAllErrors().toString());
    }
  }
}
