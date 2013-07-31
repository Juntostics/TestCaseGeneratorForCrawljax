package com.junto.crawljax.example;

import java.io.File;

import com.crawljax.condition.NotRegexCondition;
import com.crawljax.condition.invariant.Invariant;
import com.crawljax.core.CrawljaxRunner;
import com.crawljax.core.configuration.CrawljaxConfiguration;
import com.crawljax.core.configuration.CrawljaxConfiguration.CrawljaxConfigurationBuilder;
import com.junto.crawljax.ExtractToTestCode;
import com.junto.crawljax.GenerateInvariant;
import com.junto.crawljax.InvariantViolatingTestGeneratorPlugin;
import com.junto.crawljax.OurInterface;

public class GenerateTestExample implements OurInterface {
	public GenerateTestExample() {
		CrawljaxConfigurationBuilder builder =
		        CrawljaxConfiguration
		                .builderFor("file:///Users/Junto/Downloads/fromN/login.html");
		builder.crawlRules().addInvariant(genInvariant());
		builder.setMaximumDepth(5);
		builder.addPlugin(
		        new InvariantViolatingTestGeneratorPlugin(
		                new File(".").getAbsolutePath() == "/" ? "."
		                        : new File(".").getAbsolutePath()
		                                + "/src/main/java/com/junto/crawljax/example/GenerateTestExample.java"));
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
		new GenerateTestExample();
	}
}
