Feature: Files

  #RM Tool Box Files - ProgramFiles
  Scenario: RM Tool Box Files - ProgramFiles
    Given Launch browser
    When Open the RM Tool Box URL
    Then Enter the valid username and password and click on SignIn button
    And In RM Tool Box navigate to Files - ProgramFiles
    Then User should see all the programfiles and their details successfully

  #RM Tool Box Files - Program Data
  Scenario: RM Tool Box Files - Program Data
    Given Launch browser
    When Open the RM Tool Box URL
    Then Enter the valid username and password and click on SignIn button
    And In RM Tool Box navigate to Files - Program Data
    Then User should see all the program Data recorded and their details successfully

 #RM Tool Box Files - Logs
  Scenario: RM Tool Box Files - Logs
    Given Launch browser
    When Open the RM Tool Box URL
    Then Enter the valid username and password and click on SignIn button
    And In RM Tool Box navigate to Files - Logs
    Then User should see all the User navigated Resource Logs and their details recorded successfully    