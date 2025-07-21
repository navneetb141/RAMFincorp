package com.saucedemo.Test.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
	    features = {
	    		"src/test/resources/features/"
	    		
	    },
	    glue = "stepDefinitions",
	    monochrome = true,
	    plugin = {
	    		"pretty",
	        "html:target/Cucumber-Report/testReport.html",
	        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
	        "summary",
	        "rerun:target/failedScenarios_rerun2.txt",
	    }
	)

public class RerunTestRunner extends  AbstractTestNGCucumberTests {
	
	
	

}
