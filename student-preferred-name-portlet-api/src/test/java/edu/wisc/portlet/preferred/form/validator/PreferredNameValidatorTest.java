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
  
  private PreferredNameExtended pne;
  
  @Before
  public void generatePreferredNameStub() {
    pne = new PreferredNameExtended(new PreferredName("Shaquille","Danger","O'Neal"),"ONEAL");
  }

  
  @Test
  public void testLastNameRegexValid() {
    
    BindingResult bn = new MapBindingResult(new HashMap<String, String>(), "pn");
    
    ValidationUtils.invokeValidator(new PreferredNameValidator(), pne, bn);
    
    if(bn.hasErrors()) {
      fail(bn.getAllErrors().toString());
    }
  }
  
  @Test
  public void testLastNameRegexBadQuotes() {
    
    BindingResult bn = new MapBindingResult(new HashMap<String, String>(), "pn");
    
    pne.setLastName("O`Neal");//backtick not valid
    
    ValidationUtils.invokeValidator(new PreferredNameValidator(), pne, bn);
    
    if(!bn.hasErrors() || bn.getAllErrors().size() != 1) {
      fail("This should error due to the wrong single quote being used");
    }
  }
  
  @Test
  public void testLastNameTryingToChange() {
    
    BindingResult bn = new MapBindingResult(new HashMap<String, String>(), "pn");
    
    pne.setFirstName("Opera");
    pne.setLastName("Winfrey");
    
    ValidationUtils.invokeValidator(new PreferredNameValidator(), pne, bn);
    
    if(!bn.hasErrors() || bn.getAllErrors().size() != 1) {
      fail("This should error due to ONEAL trying to become Winfrey");
    }
  }
  
  @Test
  public void testLastNameCaseChangeOnly() {
    
    BindingResult bn = new MapBindingResult(new HashMap<String, String>(), "pn");
    
    pne.setLastName("ONeal");
    
    ValidationUtils.invokeValidator(new PreferredNameValidator(), pne, bn);
    
    if(bn.hasErrors()) {
      //shouldn't have failed
      fail(bn.getAllErrors().toString());
    }
  }
}
