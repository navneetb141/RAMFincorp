Feature: Logs
#RMQA_363_RM Tool Box - Logs - Adding description checkpoint

 Scenario: RMQA_363_RM Tool Box - Logs - Adding description checkpoint
   Given Launch browser
    When Open the RM Tool Box URL
    Then Enter the valid username and password and click on SignIn button 
    And In RM Tool Box navigate to Logs > choose rmext
    Then In the Description box enter any text and click upon Checkpoint button
    And User can see the description checkpoint is added to the logs file successfully
 
# RMQA_364_RM Tool Box-Logs-Show Multi line entries

 @WithExtension
  Scenario: RMQA_364_01_RM Tool Box-Logs-Show Multi line entries
    Given Launch Chrome with the extension from the CRX file    
    Given Open new tab in existing browser
    When Open the resource url - lexis advance
    Then logout the resource - lexis advance

  Scenario: RMQA_364_02_RM Tool Box-Logs-Show Multi line entries
   Given Launch browser
    When Open the RM Tool Box URL
    Then Enter the valid username and password and click on SignIn button 
    And In RM Tool Box navigate to Logs > choose rmext
    Then In Logs module click upon the checkbox - Show Multiline entries
    And User should see SessionGuid and Session end details of user activity