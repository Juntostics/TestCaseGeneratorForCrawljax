package com.junto.crawljax;

import com.crawljax.condition.invariant.Invariant;
import com.crawljax.core.CrawlerContext;
import com.crawljax.core.plugin.OnInvariantViolationPlugin;

public class InvariantViolatingTestGeneratorPlugin implements OnInvariantViolationPlugin {

	private TestCaseGenerator generator;

	public InvariantViolatingTestGeneratorPlugin(TestCaseGenerator generator) {
		this.generator = generator;
	}

	@Override
	public void onInvariantViolation(Invariant invariant, CrawlerContext context) {
		// TODO Auto-generated method stub
		generator.generateTest(context);
	}

}