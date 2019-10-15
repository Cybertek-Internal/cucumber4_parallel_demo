@calendar_events
Feature: All Calendar Events
  As user I want to be able to see all calendar events as a table

  Scenario: Verify column names
    Given user logs in as a store manager
    When user navigates to "Activities" and "Calendar Events"
    Then following table headers should be displayed
      | TITLE             |
      | CALENDAR          |
      | START             |
      | END               |
      | RECURRENT         |
      | RECURRENCE        |
      | INVITATION STATUS |