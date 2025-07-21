Feature: Database

  #RM Tool Box Database - Tables - Schema Info
  Scenario: RM Tool Box Database - Tables - Schema Info
    Given Launch browser
    When Open the RM Tool Box URL
    Then Enter the valid username and password and click on SignIn button
    And In RM Tool Box navigate to Database and Choose Schema_Info
    Then User should see version of RM toolbox successfully

  #RM Tool Box Database - Tables ( Session )
 
  Scenario: RM Tool Box Database - Tables ( Session )
    Given Launch browser
    When Open the RM Tool Box URL
    Then Enter the valid username and password and click on SignIn button
    And In RM Tool Box navigate to Database > Tables > Session
    Then User should see a new session recorded for the Resource user activity with all the details successfully
  
    #RM Tool Box Database - Tables - RecentUser
   
  Scenario: RM Tool Box Database - Tables - RecentUser
    Given Launch browser
    When Open the RM Tool Box URL
    Then Enter the valid username and password and click on SignIn button
    And In RM Tool Box navigate to Database > Tables > RecentUser
    Then Verify the BillingId