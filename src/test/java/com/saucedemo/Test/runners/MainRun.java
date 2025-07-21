package com.saucedemo.Test.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
	    features = {
	    		"src/test/resources/features/",
	    		
	    },
		glue = {
				"com.saucedemo.Test.stepDefs",
				"com.saucedemo.Test.hooks"
		},
		monochrome = true,
		plugin = {
				"pretty",
				"html:target/Cucumber-Report/testReport.html",
				"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
				"summary",
				"rerun:target/failedScenarios.txt"
		}
	)

public class MainRun extends  AbstractTestNGCucumberTests {

}
