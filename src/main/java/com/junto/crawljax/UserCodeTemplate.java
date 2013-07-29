package com.junto.crawljax;

import com.crawljax.condition.RegexCondition;
import com.crawljax.condition.invariant.Invariant;
import com.crawljax.core.CrawljaxRunner;
import com.crawljax.core.configuration.CrawljaxConfiguration;
import com.crawljax.core.configuration.CrawljaxConfiguration.CrawljaxConfigurationBuilder;

public class UserCodeTemplate implements OurInterface {
	public void main(String args[]) {
		TestCaseGenerator generator = new TestCaseGenerator("path_To_this_file");
		generator.setOutputDir("output_dir");

		CrawljaxConfigurationBuilder builder =
		        CrawljaxConfiguration.builderFor("http://www.ahref.org/hinagata/form_all.html");
		builder.crawlRules().addInvariant(genInvariant());
		builder.addPlugin(
		        new InvariantViolatingTestGeneratorPlugin(generator));
		CrawljaxRunner crawljax = new CrawljaxRunner(builder.build());
		crawljax.call();
	}

	@GenerateInvariant
	public Invariant genInvariant() {
		// complicated
		return new Invariant("Invariant", new RegexCondition("regex"));
	}

	@ExtractToTestCode
	public String helperMethod() {
		return "some string";
	}
}
