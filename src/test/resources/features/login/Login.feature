@VYT-4121
  Feature: Login
  As user I want to login under different roles
# So hook will take care of this action
#  Hook will open main page
#  Background:
#    Given user is on the landing page


  @storemanager
  Scenario: Login as a store manager
    Then user logs in as a store manager
    And user verifies that "Dashboard" page name is displayed

  @negative
  Scenario: Verify warning message for invalid credentials
    Then user logs in with "wrong" username and "wrong" password
    And user verifies that "Invalid user name" warning message is displayed

  @driver
  Scenario: Login as a driver
    Then user logs in as a driver
    And user verifies that "Quick Launchpad" page name is displayed

  @map_example
  Scenario: Login as different users
    Given user logs in with following credentials
      | username | salesmanager101 |
      | password | UserUser123     |
    Then user navigates to "Fleet" and "Vehicles"
    And user verifies that "All Cars" page name is displayed

  @login_1
  Scenario Outline: login as different user tests
    Given user logs in as a "<user_type>"
    And user verifies that "<page name>" page name is displayed

    Examples:
      | user_type     | page name       |
      | driver        | Quick Launchpad |
      | sales manager | Dashboard       |
      | store manager | Dashboard       |

    @login_with_outline_2
    Scenario Outline: User names test
      Given user logs in with following credentials
        | username | <username> |
        | password | <password> |
      Then user name should be "<name>"

      Examples: Credentials information
        | username        | password    | name             |
        | user187         | UserUser123 | Jerel Vandervort |
        | user200         | UserUser123 | Lonzo Leuschke   |
        | storemanager52  | UserUser123 | Roma Medhurst    |
        | storemanager66  | UserUser123 | Carlos Ernser    |
        | salesmanager125 | UserUser123 | Cleveland Heller |
        | salesmanager140 | UserUser123 | Jameson Paucek   |

    @login_with_outline_3
    Scenario Outline: page titles with different users
      Given user logs in as a "<user_type>"
      Then user navigates to "<tab>" and "<module>"
      Then the page title should be "<title>"

      Examples: User and navigation info
        | user_type     | tab        | module          | title                                                              |
        | driver        | Fleet      | Vehicles        | Car - Entities - System - Car - Entities - System                  |
        | driver        | Fleet      | Vehicles Model  | Vehicles Model - Entities - System - Car - Entities - System       |
        | driver        | Customers  | Accounts        | Accounts - Customers                                               |
        | driver        | Customers  | Contacts        | Contacts - Customers                                               |
        | driver        | Activities | Calendar Events | Calendar Events - Activities                                       |
        | driver        | System     | Jobs            | Jobs - System                                                      |
        | sales manager | Fleet      | Vehicles        | All - Car - Entities - System - Car - Entities - System            |
        | sales manager | Fleet      | Vehicles Model  | All - Vehicles Model - Entities - System - Car - Entities - System |
        | sales manager | Customers  | Accounts        | All - Accounts - Customers                                         |
        | sales manager | Customers  | Contacts        | All - Contacts - Customers                                         |
        | sales manager | Activities | Calendar Events | All - Calendar Events - Activities                                 |
        | sales manager | System     | Jobs            | All - Jobs - System                                                |
        | store manager | Fleet      | Vehicles        | All - Car - Entities - System - Car - Entities - System            |
        | store manager | Fleet      | Vehicles Model  | All - Vehicles Model - Entities - System - Car - Entities - System |
        | store manager | Customers  | Accounts        | All - Accounts - Customers                                         |
        | store manager | Customers  | Contacts        | All - Contacts - Customers                                         |
        | store manager | Activities | Calendar Events | All - Calendar Events - Activities                                 |
        | store manager | System     | Jobs            | All - Jobs - System                                                |
