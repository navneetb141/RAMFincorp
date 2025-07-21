package com.saucedemo.Test.utils;

import java.util.List;

import com.saucedemo.Test.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;



public class HandleDropDowns extends BaseClass {
	
	public void dropDownFunction(String locator1,String locator2,String option) {
		
		WebElement dd=driver.findElement(By.xpath(locator1));
		dd.click();
		
		List<WebElement> ddList=driver.findElements(By.xpath(locator2));
		
		for(int i=0;i<ddList.size();i++) {
			
			String val=option;
			
			if(ddList.get(i).getText().equalsIgnoreCase(val)) {
				
				ddList.get(i).click();
				break;
				
			}
			
			}
		}

}
