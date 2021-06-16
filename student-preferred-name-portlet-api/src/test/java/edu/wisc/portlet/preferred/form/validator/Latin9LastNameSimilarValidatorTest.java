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


  /**
   * Test that people may prefer a different capitalization of their last name.
   */
  @Test
  public void differentCapitalizationIsValid() {

    PreferredNameExtended pne = new PreferredNameExtended();
    pne.setLastName("Badger"); // a person would likely prefer this typical capitalization
    pne.setLegalLastName("BADGER"); // but legal names are typically in SHOUTY CASE

    BindingResult br = new MapBindingResult(new HashMap<String, String>(), "pn");

    validator.validate(pne, br);

    assertFalse (br.hasErrors());
  }

  @Test
  public void addingAHyphenMinusIsValid() {

    PreferredNameExtended pne = new PreferredNameExtended();
    pne.setLastName("Dunning-Kruger"); // hyphen-minus, the most common hyphen, is permissible in a preferred last name
    pne.setLegalLastName("DUNNINGKRUGER"); // even if not present in the legal last name

    BindingResult br = new MapBindingResult(new HashMap<String, String>(), "pn");

    validator.validate(pne, br);

    assertFalse ("A preferred last name differing from a legal last name for having added a hyphen should be valid.", br.hasErrors());
  }

  @Test
  public void removingAHyphenMinusIsValid() {

    PreferredNameExtended pne = new PreferredNameExtended();
    pne.setLastName("Lloyd Webber"); // this last name does not have a hyphen
    pne.setLegalLastName("LLOYD-WEBBER"); // though the formal title for the right honourable lord does

    BindingResult br = new MapBindingResult(new HashMap<String, String>(), "pn");

    validator.validate(pne, br);

    assertFalse ("A preferred last name differing from a legal last name for having removed a hyphen in favor of a space should be valid", br.hasErrors());
  }

  /**
   * Test that a preferred last name may differ from a legal last name by having added spaces.
   */
  @Test
  public void addingSpaceIsValid() {
    PreferredNameExtended pne = new PreferredNameExtended();
    pne.setLastName("Lloyd Webber"); // preferring an embedded space is permissible
    pne.setLegalLastName("LLOYDWEBBER");

    BindingResult br = new MapBindingResult(new HashMap<String, String>(), "pn");

    validator.validate(pne, br);

    assertFalse ("A preferred last name differing from a legal last name for having added a space should be valid", br.hasErrors());
  }

  /**
   * Test that a preferred last name may differ from a legal last name by removing spaces.
   */
  @Test
  public void removingSpaceIsValid() {
    PreferredNameExtended pne = new PreferredNameExtended();
    pne.setLastName("Lloyd Webber"); // preferring an embedded space is permissible
    pne.setLegalLastName("LLOYDWEBBER");

    BindingResult br = new MapBindingResult(new HashMap<String, String>(), "pn");

    validator.validate(pne, br);

    assertFalse ("A preferred last name differing from a legal last name for having added a space should be valid", br.hasErrors());
  }

  /**
   * Test that a preferred last name may differ from a legal last name by removing single quotes.
   */
  @Test
  public void removingSingleQuoteIsValid() {
    PreferredNameExtended pne = new PreferredNameExtended();

    pne.setFirstName("Maureen");
    pne.setLegalFirstName("Maureen");

    pne.setLastName("OHara"); // preferring to remove a quote character is permissible
    pne.setLegalLastName("O'Hara");

    BindingResult br = new MapBindingResult(new HashMap<String, String>(), "pn");

    validator.validate(pne, br);

    assertFalse ("A preferred last name differing from a legal last name for having removed a quote should be valid", br.hasErrors());
  }

  /**
   * Test that a preferred last name may differ from a legal last name by adding single quotes.
   */
  @Test
  public void addingSingleQuoteIsValid() {
    PreferredNameExtended pne = new PreferredNameExtended();

    pne.setFirstName("Maureen");
    pne.setLegalFirstName("Maureen");

    pne.setLastName("O'Hara"); // preferring to add a quote character is permissible
    pne.setLegalLastName("OHara");

    BindingResult br = new MapBindingResult(new HashMap<String, String>(), "pn");

    validator.validate(pne, br);

    assertFalse ("A preferred last name differing from a legal last name for having added a quote should be valid", br.hasErrors());
  }

  /**
   * Tests that u is interchangeable with Ù,Ú,Û,Ü and their lowercase versions.
   */
  @Test
  public void substitutingAccentedUIsValid() {
    PreferredNameExtended pne = new PreferredNameExtended();

    pne.setLastName("ÙÚÛÜUùúûüu"); // preferring any version of U in place of any other version of U is permitted

    pne.setLegalLastName("uÙûÛùÜUÚúü");

    BindingResult br = new MapBindingResult(new HashMap<String, String>(), "pn");

    validator.validate(pne, br);

    assertFalse ("A preferred last name differing from a legal last name for using different accents on U", br.hasErrors());
  }

  @Test
  public void substitutingAccentedYIsValid() {
    PreferredNameExtended pne = new PreferredNameExtended();

    pne.setLastName("ÝýyýÝyÿ"); // preferring any version of Y in place of any other version of Y is permitted
    pne.setLegalLastName("YýyÝýÝy");

    BindingResult br = new MapBindingResult(new HashMap<String, String>(), "pn");

    validator.validate(pne, br);

    assertFalse ("A preferred last name differing from a legal last name for using different accents on Y should be valid", br.hasErrors());
  }

  @Test
  public void normalizeToReplacesCharacters() {
    String someString = "Wölfgàng Ämãdéùs Mozart";

    someString = Latin9LastNameSimilarValidator.normalizeCharacterFamily(someString, "àãÄ", "a");
    someString = Latin9LastNameSimilarValidator.normalizeCharacterFamily(someString, "é", "e");
    someString = Latin9LastNameSimilarValidator.normalizeCharacterFamily(someString, "ö", "o");
    someString = Latin9LastNameSimilarValidator.normalizeCharacterFamily(someString, "ù", "u");
    assertEquals("Wolfgang amadeus Mozart", someString);
  }
}
