package com.junto.crawljax;

import com.crawljax.condition.NotRegexCondition;
import com.crawljax.condition.invariant.Invariant;
import com.crawljax.core.CrawljaxRunner;
import com.crawljax.core.configuration.CrawljaxConfiguration;
import com.crawljax.core.configuration.CrawljaxConfiguration.CrawljaxConfigurationBuilder;

public class UserCodeTemplate implements OurInterface {
	public UserCodeTemplate() {
		CrawljaxConfigurationBuilder builder =
		        CrawljaxConfiguration
		                .builderFor("file:///Users/Junto/Downloads/fromN/login.html");
		builder.crawlRules().addInvariant(genInvariant());
		builder.setMaximumDepth(5);
		builder.addPlugin(
		        new InvariantViolatingTestGeneratorPlugin(
		                "/Users/Junto/workspace/testcase-generator-plugin/src/main/java/com/junto/crawljax/UserCodeTemplate.java"));
		CrawljaxRunner crawljax = new CrawljaxRunner(builder.build());
		crawljax.call();
	}

	@GenerateInvariant
	public Invariant genInvariant() {
		// complicated
		return new Invariant("String 'Error' is detected",
		        new NotRegexCondition("Error"));
	}

	@ExtractToTestCode
	public String helperMethod() {
		return "some string";
	}

	public static void main(String[] args) {
		new UserCodeTemplate();
	}
}
