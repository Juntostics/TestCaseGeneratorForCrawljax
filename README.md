# TestCase generator plugin for crawljax

This [crawljax](http://crawljax.com/) plugin is for generating JUnit-format test case from the certain context obtained during auto-crawling over web sites. 

For instance, you can write program to do followings:

1. Automatically crawls (e.g., clicking links, inputing text to forms) over your web site (This functionality is provided by Crawljax, backed by [Selenium WebDriver](http://docs.seleniumhq.org/projects/webdriver/)).
2. If crawler finds something violates an invariant you defined, our plugin generates test case to reproduce the problem. You can refer this to debug your site, and when debug is done, you'll have a nice regression test :)

## How to install
Download the project to your environment, and _mvn install_. Then, add following to your dependencies.

```
    <dependency>
        <groupId>com.junto.crawljax</groupId>
        <artifactId>testcase-generator-plugin</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
```

## How to use by example
Let's take [InvariantViolatingTestGeneratorPlugin](https://github.com/Juntostics/TestCaseGeneratorForCrawljax/blob/master/src/main/java/com/junto/crawljax/InvariantViolatingTestGeneratorPlugin.java) as an example. This works as plugin for [crawljax](http://crawljax.com/) and invoked when crawljax finds any invariant violation.

To start a crawling with our plugin, you'll write something like:

```Java
public class GenerateTest {
  private static final String TEST_TARGET_URL 
			= "http://juntostics.github.io/TestCaseGeneratorForCrawljax/login_example/login.html";	

	public static void main(String[] args) {
		new GenerateTest().start();	
	}

	private start() {
		CrawljaxConfigurationBuilder builder 
				= CrawljaxConfiguration.builderFor(TEST_TARGET_URL);
		builder.crawlRules().addInvariant(genInvariant());
		
		// Our test generater will refer to this file in order to reuse part of source code
		// you wrote, so you need to pass the file path to this file to test case generator. 
		builder.addPlugin(new InvariantViolatingTestGeneratorPlugin(pathToThisFile));

		// start crawling
		CrawljaxRunner crawljax = new CrawljaxRunner(builder.build());
		crawljax.call();
	}

	// Method with this annotation will be extracted to test code, and used in assertion. 
	@GenerateInvariant
	private Invariant genInvariant() {
		// This invariant claims that there must not be string '404' in any DOM.
		return new Invariant(errorMsg("404"), new NotRegexCondition("404"));
	}

	// Methods with thit annotation will be extracted to test code, so you can put this 
	// annotation to your helper methods.
	@ExtractToTestCode
	private String errorMsg(String errorCode) {
		return "Error code " + errorCode + " is observed!";
	}
}
```
