package com.saucedemo.Test.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
	    features = {
	    		"src/test/resources/features/",
	    		
	    },
	    glue = "com.saucedemo.Test.stepDefs",
	    dryRun = true,
	    plugin = {
	    		"pretty",
	        "html:target/Cucumber-Report/testReport.html", "json:target/cucumber.json",
	      
	    }
	)

public class DryRun extends  AbstractTestNGCucumberTests {

}
