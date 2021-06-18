package edu.wisc.portlet.preferred.form.validator;

import edu.wisc.portlet.preferred.form.PreferredName;
import edu.wisc.portlet.preferred.form.PreferredNameExtended;
import org.junit.Test;
import org.springframework.validation.*;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
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
  public void allowsNoPreferredMiddleName() {
    BindingResult bn = new MapBindingResult(new HashMap<String, String>(), "pn");
    PreferredNameExtended pne = new PreferredNameExtended();
    pne.setMiddleName(null);

    validator.validate(pne, bn);

    assertFalse(bn.hasFieldErrors("middleName"));
  }

  @Test
  public void rejectsLastNameOutsideLatin9() {
    BindingResult lastNameBeyondLatin9BindingResult = new MapBindingResult(new HashMap<String, String>(), "pn");
    PreferredNameExtended pne = new PreferredNameExtended();

    pne.setFirstName("Maria");
    pne.setMiddleName("Salomea");
    pne.setLastName("Skłodowska");

    validator.validate(pne, lastNameBeyondLatin9BindingResult);

    assertTrue("Alas ł is not in LATIN-9 and so should have been rejected",
      ValidatorTestSupport.fieldHasError("lastName", "error.not-latin-9", lastNameBeyondLatin9BindingResult));
  }

  @Test
  public void allowsAndréMarieAmpère() {
    BindingResult bindingResult = new MapBindingResult(new HashMap<String, String>(), "pn");
    PreferredNameExtended pne = new PreferredNameExtended();

    pne.setFirstName("André-Marie");
    pne.setLastName("Ampère");

    validator.validate(pne, bindingResult);

    assertFalse("Should not have errored on André-Marie Ampère",
      bindingResult.hasFieldErrors());
  }

  @Test
  public void allowsDagHammarskjöld() {
    BindingResult bindingResult = new MapBindingResult(new HashMap<String, String>(), "pn");
    PreferredNameExtended pne = new PreferredNameExtended();

    pne.setFirstName("Dag");
    pne.setLastName("Hammarskjöld");

    validator.validate(pne, bindingResult);

    assertFalse("Should not have errored on Dag Hammarskjöld",
      bindingResult.hasFieldErrors());
  }

  @Test
  public void allowsIgnacioChávezSánchez() {

    BindingResult bindingResult = new MapBindingResult(new HashMap<String, String>(), "pn");
    PreferredNameExtended pne = new PreferredNameExtended();

    pne.setFirstName("Ignacio");
    pne.setMiddleName("Chávez");
    pne.setLastName("Sánchez");

    validator.validate(pne, bindingResult);

    assertFalse("Should not have errored on Ignacio Chávez Sánchez",
      bindingResult.hasFieldErrors());
  }


  @Test
  public void rejectsEduardoNύñez() {

    BindingResult bindingResult = new MapBindingResult(new HashMap<String, String>(), "pn");
    PreferredNameExtended pne = new PreferredNameExtended();

    pne.setFirstName("Eduardo");
    pne.setLastName("Nύñez");

    validator.validate(pne, bindingResult);

    assertTrue("Alas ύ is outside LATIN-9 and so the last name Nύñez should have been rejected.",
      ValidatorTestSupport.fieldHasError("lastName", "error.not-latin-9", bindingResult));
  }

  @Test
  public void rejectsJokūbasDaukša() {
    BindingResult bindingResult = new MapBindingResult(new HashMap<String, String>(), "pn");
    PreferredNameExtended pne = new PreferredNameExtended();

    pne.setFirstName("Jokūbas");
    pne.setLastName("Daukša");

    validator.validate(pne, bindingResult);

    assertTrue("Alas ū is outside LATIN-9 and so the first name Jokūbas should have been rejected.",
      ValidatorTestSupport.fieldHasError("firstName", "error.not-latin-9", bindingResult));
    assertFalse("However š is within LATIN-9 and so the last name Daukša should have been accepted.",
      bindingResult.hasFieldErrors("lastName"));
  }

  @Test
  public void rejectsNaitōTorajirō() {

    BindingResult bindingResult = new MapBindingResult(new HashMap<String, String>(), "pn");
    PreferredNameExtended pne = new PreferredNameExtended();

    pne.setFirstName("Naitō");
    pne.setLastName("Torajirō");

    validator.validate(pne, bindingResult);

    assertTrue("Alas ō is outside LATIN-9 and so the first name Naitō should have been rejected.",
      ValidatorTestSupport.fieldHasError("firstName", "error.not-latin-9", bindingResult));
    assertTrue("Alas ō is outside LATIN-9 and so the last name Torajirō should have been rejected.",
      ValidatorTestSupport.fieldHasError("lastName", "error.not-latin-9", bindingResult));

  }

  @Test
  public void rejectsNonLatin9MiddleName() {
    BindingResult bindingResult = new MapBindingResult(new HashMap<String, String>(), "pn");
    PreferredNameExtended pne = new PreferredNameExtended();

    pne.setFirstName("Bucky");
    pne.setMiddleName("emoji\uD83D\uDE00"); // smiley
    pne.setLastName("Badger");

    validator.validate(pne, bindingResult);

    assertTrue("Alas emojis are not supported in LATIN-9, so middle name validation of the smiley should have failed.",
      ValidatorTestSupport.fieldHasError("middleName", "error.not-latin-9", bindingResult));
  }

  @Test
  public void rejectsMaureenOHara() {

    // Maureen

    BindingResult bindingResult = new MapBindingResult(new HashMap<String, String>(), "pn");
    PreferredNameExtended pne = new PreferredNameExtended();

    pne.setFirstName("Maureen");
    pne.setLastName("O’Hara"); // will be rejected because uses U+2019 apostrophe rather than single quote

    validator.validate(pne, bindingResult);

    assertTrue("Alas only single quote for apostrophes are supported in LATIN-9, so last name validation of O’Hara should have failed.",
      ValidatorTestSupport.fieldHasError("lastName", "error.not-latin-9", bindingResult));
  }

}
