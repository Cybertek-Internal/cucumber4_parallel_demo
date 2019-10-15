@navigation
Feature: Navigation menu options
  As user I want to use top menu for navigation

  Background:
    Given user is on the landing page
    When user logs in as a "driver"
#    instead of driver, I would put admin super user
#    who has access to every component of the application
#    driver has access only to certain modules

  Scenario: Login as driver and navigate to the Vehicles
    Then user navigates to "Fleet" and "Vehicles"
    And user verifies that "Cars" page name is displayed

  Scenario: Login as driver and navigate to the Vehicle Odometer
    Then user navigates to "Fleet" and "Vehicle Odometer"
    And user verifies that "Vehicles Odometers" page name is displayed