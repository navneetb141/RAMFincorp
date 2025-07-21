Feature: COMMANDS
#RM Tool Box Commands - System - Restart RM Service 
   Scenario: Verify RM Tool Box Commands - System - Restart RM Service
    Given Launch browser
    When Open the RM Tool Box URL
    Then Enter the valid username and password and click on SignIn button
    And Navigate to Commands > Click upon System - Restart RM Service
    And User should see that Operation Complete pop up without any error successfully
#RM Tool Box Commands - System - Archive Local Cache  

		 Scenario: Verify RM Tool Box Commands - System - Archive Local Cache
    Given Launch browser
    When Open the RM Tool Box URL
    Then Enter the valid username and password and click on SignIn button
    Then Navigate to Commands > Click upon System - Archive Local Cache
    And User should see that Operation Complete message without any error successfully
   
#RM Tool Box Commands - Trigger - Config Update   
 
  Scenario: Verify RM Tool Box Commands - Trigger - Config Update
    Given Launch browser
    When Open the RM Web URL
    Then In RM web navigate to Resources > Choose any Resource > disable UM for the selected Resource > Click Save
    And User should see that UM is disabled and saved for the selected Resource successfully
		Given Open new tab in existing browser
    When Open the RM Tool Box URL
    Then Enter the valid username and password and click on SignIn button 
    And In RM Tool Box > Commands > Click upon Trigger - Config Update
		Then User should see that Operation Complete pop up without any error successfully
		
	Scenario: Verify whether UM disabled is saved successfully
  	Given Launch browser
    When Open the RM Web URL
    Then Navigate to RM web >Resources > selected Resource and verify whether UM disabled is saved successfully

#RM Tool Box Commands - Trigger - Push Data

@WithExtension
  Scenario: RMQA_354-Open RMEXT and then open the resource url
    Given Launch Chrome with the extension from the CRX file    
    Given Open new tab in existing browser
    When Open the resource url - lexis advance 
 
  Scenario: RMQA_354-RM Tool Box Commands - Trigger - Push Data   
   Given Launch browser
    When Open the RM Tool Box URL
    Then Enter the valid username and password and click on SignIn button 
    And In RM Tool Box > Commands > Click upon Trigger - Push Data
    And User should see that Operation Complete message without any error successfully
 
  Scenario: RMQA_354-In RM web navigate to Reports-Activity Meta-Data-export Report    
    Given Open new tab in existing browser
    When Open the RM Web URL
		Then In RM web navigate to Reports > Activity Meta-Data > export Report
		And User can see all the actions triggered and usage sessions within the generated Report successfully

#RM Tool Box Commands - Trigger - Delivery Modules

Scenario: Verify RM Tool Box Commands - System - Archive Local Cache
    Given Launch browser
    When Open the RM Tool Box URL
    Then Enter the valid username and password and click on SignIn button
    Then Navigate to > Commands > Click upon Trigger - Delivery Modules
    And User should see that Operation Complete message without any error successfully



		
		
		