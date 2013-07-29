package com.junto.crawljax;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crawljax.condition.NotRegexCondition;
import com.crawljax.condition.invariant.Invariant;
import com.crawljax.core.CrawljaxRunner;
import com.crawljax.core.configuration.CrawljaxConfiguration;
import com.crawljax.core.configuration.CrawljaxConfiguration.CrawljaxConfigurationBuilder;
import com.crawljax.core.plugin.OnInvariantViolationPlugin;

/**
 * Invariants are very useful in application testing. This example shows how to create an
 * {@link Invariant} that asserts that a certain string <b>never<b> in any part of the website.
 * Using the {@link OnInvariantViolationPlugin} we can report the violation of this invariant.
 */
public class InvariantExample {

	private static final Logger LOG = LoggerFactory.getLogger(InvariantExample.class);

	/**
	 * Run this method to start the crawl.
	 */
	public static void main(String[] args) {
		CrawljaxConfigurationBuilder builder =
		        CrawljaxConfiguration.builderFor("http://www.ahref.org/hinagata/form_all.html");

		// Add the invariant that checks that the string isn't present.
		builder.crawlRules().addInvariant(
		        "Detect a string",
		        new NotRegexCondition(
		                "アンケートご回答ありがとうございます。")); // "Invariants can be used to perform tests on the current state"

		// This plugin will just print the error.

		builder.addPlugin(new TestCasePluginGenerator());

		/*
		 * builder.addPlugin(new OnInvariantViolationPlugin() {
		 * @Override public void onInvariantViolation(Invariant invariant, CrawlerContext context) {
		 * LOG.error("\n\n!!! Invariant {} violated !!!\n", invariant);
		 * System.out.println("errorrrrrrrrrrrrrrrrrrrrrrrrrrrr!!!!!!!!!");
		 * System.out.println(context.getCrawlPath().size());
		 * System.out.println(context.getCrawlPath().toString()); } });
		 */

		CrawljaxRunner crawljax = new CrawljaxRunner(builder.build());
		crawljax.call();
	}
}
