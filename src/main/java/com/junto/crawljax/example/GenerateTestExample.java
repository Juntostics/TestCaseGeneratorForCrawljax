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
import com.junto.crawljax.TestCaseGeneratorHost;

public class GenerateTestExample implements TestCaseGeneratorHost {
	public GenerateTestExample() {
		CrawljaxConfigurationBuilder builder =
		        CrawljaxConfiguration
		                .builderFor("http://juntostics.github.io/TestCaseGeneratorForCrawljax/login_example/login.html");
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
        return new Invariant(errorMsg("404"),
                new NotRegexCondition("404"));
    }

    @ExtractToTestCode
    public String errorMsg(String errorCode) {
        return "Error code " + errorCode + " is observed!";
    }

	public static void main(String[] args) {
		new GenerateTestExample();
	}
}
