package org.jbehave.web.flex;

import junit.framework.TestCase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class ColorsTest extends TestCase {

	private FlashWebDriver flashApp;
	private WebDriver driver;

	private final static String GREEN = "GREEN";
	private final static String BLUE = "BLUE";
	private final static String RED = "RED";
	private final static String URL = "http://localhost:8080/flex/colors.html";
	
	public void setUp() {
		driver = new FirefoxDriver();	
		flashApp = new FlashWebDriver(driver, "coloredSquare", URL);
		assertEquals(100, flashApp.percentLoaded());
	}

	public void tearDown() {
		driver.close();
	}
	
	public void testColorTransition() {
		assertEquals("Clicking Colors", driver.getTitle());
		assertEquals(GREEN, flashApp.call("getColor"));
		assertEquals("(Click here)", flashApp.call("getSquareLabel"));
		flashApp.call("click");
		assertEquals(BLUE, flashApp.call("getColor"));
		assertEquals(BLUE, flashApp.call("getSquareLabel"));
		flashApp.call("click");
		assertEquals(RED, flashApp.call("getColor"));
		assertEquals(RED, flashApp.call("getSquareLabel"));
		flashApp.call("click");
		assertEquals(GREEN, flashApp.call("getColor"));
		assertEquals(GREEN, flashApp.call("getSquareLabel"));
	}
	
	public void testRectangleLabel() {
		assertEquals("(Click here)", flashApp.call("getSquareLabel"));
		flashApp.call("setSquareLabel", "Dummy Label");
		assertEquals("Dummy Label", flashApp.call("getSquareLabel"));
	}


}
