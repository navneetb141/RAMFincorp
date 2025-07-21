package com.saucedemo.Test.utils;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
public class keyboardTab {
	
	// Static method to press the Tab key a specified number of times
    public static void pressTab(int times) {
        try {
            Robot robot = new Robot(); // Create Robot instance

            for (int i = 0; i < times; i++) {
                robot.keyPress(KeyEvent.VK_TAB); // Press Tab key
                robot.keyRelease(KeyEvent.VK_TAB); // Release Tab key
                Thread.sleep(300); // Small delay to allow UI to react
            }

            // Press Enter after moving focus
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            System.out.println(" Pressed TAB " + times + " times and ENTER key.");

        } catch (AWTException | InterruptedException e) {
            System.out.println(" Error using Robot class: " + e.getMessage());
        }
    }
}
