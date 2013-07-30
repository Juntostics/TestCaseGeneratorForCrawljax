package com.junto.crawljax;

import com.crawljax.condition.NotRegexCondition;
import com.crawljax.condition.invariant.Invariant;
import com.crawljax.core.CrawljaxRunner;
import com.crawljax.core.configuration.CrawljaxConfiguration;
import com.crawljax.core.configuration.CrawljaxConfiguration.CrawljaxConfigurationBuilder;

public class UserCodeTemplate implements OurInterface {
	public UserCodeTemplate() {
		CrawljaxConfigurationBuilder builder =
		        CrawljaxConfiguration.builderFor("http://www.ahref.org/hinagata/form_all.html");
		builder.crawlRules().addInvariant(genInvariant());
		builder.addPlugin(
		        new InvariantViolatingTestGeneratorPlugin(
		                "/Users/Junto/workspace/testcase-generator-plugin/src/main/java/com/junto/crawljax/UserCodeTemplate.java"));
		CrawljaxRunner crawljax = new CrawljaxRunner(builder.build());
		crawljax.call();
	}

	@GenerateInvariant
	public Invariant genInvariant() {
		// complicated
		return new Invariant("Invariant", new NotRegexCondition(
		        "acmailer（エーシーメーラー）は"));
	}

	@ExtractToTestCode
	public String helperMethod() {
		return "some string";
	}

	public static void main(String[] args) {
		new UserCodeTemplate();
	}
}
