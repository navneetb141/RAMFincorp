Feature: Registry

#RM Tool Box - Registry - View (Table/Text view)

 Scenario: RMQA_366_RM Tool Box - Registry - Table view and Text view
   Given Launch browser
    When Open the RM Tool Box URL
    Then Enter the valid username and password and click on SignIn button 
    And In RM Tool Box navigate to Registry module
    Then Choose Settings from the View drop down
    And Scroll down to the bottom of the page
    And Select Table option
    And Scroll down to the bottom of the page
    Then Select Text option
    And Scroll down to the bottom of the page
 