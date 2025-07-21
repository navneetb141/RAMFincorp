Feature: Diagnostics
  
  #RM Tool Box - Diagnostics
 
  Scenario: RMQA_369_RM Tool Box - Diagnostics
   Given Launch browser
    When Open the RM Tool Box URL
    Then Enter the valid username and password and click on SignIn button 
    And In RM Tool Box navigate to Diagnostics
		Then User should see True for isResearchMonitorinstalled and isResearchMonitorrunning
