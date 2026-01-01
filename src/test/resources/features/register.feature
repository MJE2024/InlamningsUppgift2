Feature: Register new supporter account

  # Bakgrund körs före varje scenario (bra struktur)
  Background:
    Given I open the registration page

  Scenario: Create user - everything is valid and account is created
    When I enter first name "Klas"
    And I enter last name "Klattermus"
    And I enter date of birth "01/01/1990"
    And I enter email "klas.klattermus@example.com"
    And I confirm email "klas.klattermus@example.com"
    And I enter password "Password123!"
    And I confirm password "Password123!"
    # Steg som är gemensamma för alla scenarion skulle kunna placeras i Background
    # för att minska repetition. I denna uppgift har jag valt att ha dem explicit
    # i varje scenario för att tydligt visa vilka krav som gäller för respektive testfall.
    And I accept terms and conditions
    And I confirm that I am over 18 years old
    And I accept code of ethics and conduct
    And I submit the registration form
    Then I should see a success message

  Scenario: Create user - last name is missing
    When I enter first name "Klas"
    And I leave last name empty
    And I enter date of birth "01/01/1990"
    And I enter email "missing.lastname@example.com"
    And I confirm email "missing.lastname@example.com"
    And I enter password "Password123!"
    And I confirm password "Password123!"
    And I accept terms and conditions
    And I confirm that I am over 18 years old
    And I accept code of ethics and conduct
    And I submit the registration form
    Then I should see an error message for "lastName"

  Scenario Outline: Create user - password confirmation validation
    When I enter first name "Klas"
    And I enter last name "Klattermus"
    And I enter date of birth "01/01/1990"
    And I enter email "<email>"
    And I confirm email "<email>"
    And I enter password "<password>"
    And I confirm password "<confirmPassword>"
    And I accept terms and conditions
    And I confirm that I am over 18 years old
    And I accept code of ethics and conduct
    And I submit the registration form
    Then I should see a password mismatch error

    #Använder olika mail adresser för tydlighet och för att jag hört att man gör så...
    Examples:
      | email                         | password       | confirmPassword |
      | mismatch1@example.com         | Password123!   | Password123     |
      | mismatch2@example.com         | Password123!   | Password123!!   |

  Scenario: Create user - terms are not accepted
    When I enter first name "Klas"
    And I enter last name "Klattermus"
    And I enter date of birth "01/01/1990"
    And I enter email "terms.not.accepted@example.com"
    And I confirm email "terms.not.accepted@example.com"
    And I enter password "Password123!"
    And I confirm password "Password123!"
    And I do not accept terms and conditions
    And I confirm that I am over 18 years old
    And I accept code of ethics and conduct
    And I submit the registration form
    Then I should see a terms not accepted error