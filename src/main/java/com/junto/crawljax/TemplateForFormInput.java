package com.junto.crawljax;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class TemplateForFormInput {

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
		// -----------------------------------------------------------for roop
		// text
		driver.findElement(By.name("hoge")).sendKeys("value");

		// checkbox
		driver.findElement(By.name("hoge")).click();

		// radio
		driver.findElement(By.xpath("")).click();

		// select
		Select selectElement = new Select(driver.findElement(By.name("pulldown")));
		selectElement.selectByVisibleText("パンダ");
		selectElement.selectByValue("パんだ");

		// --------------------------------------------------------------for roop

		WebElement webelement =
		        driver.findElement(By
		                .xpath("/HTML[1]/BODY/TABLE[1]/TBODY[1]/TR[1]/TD[1]/DIV[1]/UL[2]/LI[3]/A[1]"));
		webelement.click();

		assertTrue(driver.getTitle().equals("�uselenium�v�̌������� - Yahoo!����"));
	}
}
