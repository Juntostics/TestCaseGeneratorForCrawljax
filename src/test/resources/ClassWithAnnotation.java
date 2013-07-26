package com.junto.crawljax;

import com.crawljax.condition.RegexCondition;
import com.crawljax.condition.invariant.Invariant;

import java.util.Date;

/**
 * Some javadoc
 */
public class ClassWithAnnotation {
	public void method1() {
		System.out.println("hoge");
	}

	@ExtractToGeneratedCode
	private String date() {
		return new Date().toString();
	}

	@GeneratingInvariant
	public Invariant generateInvariant() {
		return new Invariant(
				"Date should be shown there", new RegexCondition(date()));
	}

	@ExtractToGeneratedCode
	private void someHelperMethod() {
		int a = 1 + 2;
	}

	private void someLocalMethod() {
		int b = 1 + 2;
	}
}
