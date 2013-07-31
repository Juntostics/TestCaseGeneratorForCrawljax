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

public class WebapplicationTest {

	private static WebDriver driver;
	private WebElement webelement;
	private Select selectElement;
	private static final long DEFAULT_WAIT_AFTER_RELOAD = 500;
	private static final long DEFAULT_WAIT_AFTER_EVENT = 500;

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
		driver.get("http://www.ahref.org/hinagata/form_all.html");

		driver.findElement(By.name("textfield")).sendKeys("wutVNWSO");
		driver.findElement(By.name("checkbox")).click();
		driver.findElement(By.name("checkbox1")).click();
		driver.findElement(By.name("checkbox2")).click();
		driver.findElement(By.name("使い勝手、見易さ")).click();
		driver.findElement(By.name("内容")).click();
		driver.findElement(By.name("求めていた内容でしたか？")).click();
		driver.findElement(By.name("textfield2")).sendKeys("kPvVZLlo");
		driver.findElement(By.name("コメント")).sendKeys("AoAwLmae");
		selectElement = new Select(driver.findElement(By.name("select")));
		try {
			selectElement.selectByVisibleText("test1");
		} catch (Exception e) {
		}
		try {
			selectElement.selectByValue("test1");
		} catch (Exception e) {
		}

		selectElement = new Select(driver.findElement(By.name("select2")));
		try {
			selectElement.selectByVisibleText("test1");
		} catch (Exception e) {
		}
		try {
			selectElement.selectByValue("test1");
		} catch (Exception e) {
		}

		webelement =
		        driver.findElement(By
		                .xpath("/HTML[1]/BODY/TABLE[1]/TBODY[1]/TR[1]/TD[1]/FONT[1]/A[1]"));
		webelement.click();

		WebDriverBackedEmbeddedBrowser browser =
		        WebDriverBackedEmbeddedBrowser.withDriver(
		                driver, generateFilterAttributes(), DEFAULT_WAIT_AFTER_RELOAD,
		                DEFAULT_WAIT_AFTER_EVENT);
		EmbeddedBrowser embeddedbrowser = (EmbeddedBrowser) browser;
		Invariant userInvariant = genInvariant();
		assertTrue(userInvariant.getInvariantCondition().check(embeddedbrowser));
	}

	private ImmutableSortedSet<String> generateFilterAttributes() {
		SortedSet<String> set = new TreeSet<String>();
		// set.add("closure_hashcode_(\w)*");
		set.add("jquery[0-9]+");
		return ImmutableSortedSet.copyOfSorted(set);
	}

	public Invariant genInvariant() {
		return new Invariant("Invariant", new NotRegexCondition("acmailer（エーシーメーラー）は"));
	}

	public String helperMethod() {
		return "some string";
	}

}