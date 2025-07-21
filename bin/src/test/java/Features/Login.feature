Feature: Login
  
 
  Scenario: The user must be able to log in and access the toolbox if the allow basic authentication policy is turned off
    Given Launch browser
    When Open the RM Tool Box URL
    Then Enter the valid username and password and click on SignIn button
		And In RM Tool Box > Commands > Click upon Trigger - Config Update
		And User should see that Operation Complete pop up without any error successfully

Scenario: The user must be able to log out from the tool box
   Given Launch browser
   When Open the RM Tool Box URL
   Then Enter the valid username and password and click on SignIn button
	 And Click on logout
	 Then The user should not able to access the Commands > Click upon Trigger - Config Update
    
   
     
 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		