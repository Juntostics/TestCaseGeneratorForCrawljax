package com.junto.crawljax;

import static org.junit.Assert.assertTrue;

import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import com.crawljax.browser.EmbeddedBrowser;
import com.crawljax.browser.WebDriverBackedEmbeddedBrowser;
import com.crawljax.condition.NotRegexCondition;
import com.crawljax.condition.invariant.Invariant;
import com.google.common.collect.ImmutableSortedSet;

public class GeneratedTestCase {

	private static WebDriver driver;
	private WebElement webElement;
	private Select selectElement;
	private final long waitAfterReloadUrl = 500;
	private final long waitAfterEvent = 500;

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
		driver.get("file:/Users/Junto/Downloads/fromN/login.html");

		driver.findElement(By.id("name")).sendKeys("DQnEXudM");

		webElement = driver.findElement(By.xpath("/HTML[1]/BODY/FORM[1]/P[1]/INPUT[1]"));
		webElement.click();
		driver.findElement(By.id("name")).sendKeys("DQnEXudM");

		webElement = driver.findElement(By.xpath("/HTML[1]/BODY/DIV[1]/A[1]"));
		webElement.click();
		driver.findElement(By.id("name")).sendKeys("DQnEXudM");

		webElement = driver.findElement(By.xpath("/HTML[1]/BODY/DIV[2]/A[1]"));
		webElement.click();

		WebDriverBackedEmbeddedBrowser browser = WebDriverBackedEmbeddedBrowser.withDriver(
		        driver, generateFilterAttributes(), waitAfterReloadUrl, waitAfterEvent);
		EmbeddedBrowser embeddedBrowser = (EmbeddedBrowser) browser;
		Invariant userInvariant = genInvariant();
		assertTrue(userInvariant.getInvariantCondition().check(embeddedBrowser));
	}

	private ImmutableSortedSet<String> generateFilterAttributes() {
		SortedSet<String> set = new TreeSet<String>();
		set.add("closure_hashcode_(\\w)*");
		set.add("jquery[0-9]+");
		return ImmutableSortedSet.copyOfSorted(set);
	}

	public Invariant genInvariant() {
		return new Invariant("String 'Error' is detected", new NotRegexCondition("Error"));
	}

	public String helperMethod() {
		return "some string";
	}

}