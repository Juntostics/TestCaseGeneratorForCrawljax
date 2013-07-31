package com.crawljax.examples;

import com.crawljax.condition.NotRegexCondition;
import com.crawljax.condition.invariant.Invariant;
import com.crawljax.core.CrawlerContext;
import com.crawljax.core.CrawljaxRunner;
import com.crawljax.core.configuration.CrawljaxConfiguration;
import com.crawljax.core.plugin.OnInvariantViolationPlugin;

/**
 * @author Kazuki Nishiura
 */
public class LocalExample {

	public static void main(String[] args) {
		CrawljaxConfiguration.CrawljaxConfigurationBuilder builder =
		        CrawljaxConfiguration
		                .builderFor("file:///Users/Junto/Downloads/fromN/login.html");

		builder.crawlRules().addInvariant(
		        "String 'Error' is detected",
		        new NotRegexCondition("Error"));
		// builder.crawlRules().waitAfterReloadUrl(2000, TimeUnit.MILLISECONDS);
		builder.setMaximumDepth(5);
		builder.addPlugin(new OnInvariantViolationPlugin() {

			@Override
			public void onInvariantViolation(Invariant invariant, CrawlerContext context) {
				System.err.println("Error");
			}

		});
		CrawljaxRunner crawljax = new CrawljaxRunner(builder.build());
		crawljax.call();
	}
}
