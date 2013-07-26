package com.junto.crawljax;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebapplicationTest {

	private static WebDriver driver;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driver = new FirefoxDriver();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.quit();
	}

	@Test
	public void replicate() throws Exception {
		driver.get("http://demo.crawljax.com/");

		WebElement webelement =
		        driver.findElement(By
		                .xpath("/HTML[1]/BODY/TABLE[1]/TBODY[1]/TR[1]/TD[1]/DIV[1]/UL[2]/LI[3]/A[1]"));
		webelement.click();

		assertTrue(driver.getTitle().equals("�uselenium�v�̌������� - Yahoo!����"));
	}
}