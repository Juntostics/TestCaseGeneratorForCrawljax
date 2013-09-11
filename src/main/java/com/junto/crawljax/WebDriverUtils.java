package com.junto.crawljax;

import com.crawljax.core.state.Identification;

public class WebDriverUtils {
	private static final String DOUBLEQUOTATION = "\"";

	public static String idToString(Identification id) {
		String template = DOUBLEQUOTATION + id.getValue() + DOUBLEQUOTATION + ")";
		switch (id.getHow()) {
			case name:
				return "By.name(" + template;

			case xpath:
				// Work around HLWK driver bug
				return "By.xpath(" + DOUBLEQUOTATION
				        + id.getValue().replaceAll("/BODY\\[1\\]/", "/BODY/") + DOUBLEQUOTATION
				        + ")";

			case id:
				return "By.id(" + template;

			case tag:
				return "By.tagName(" + template;

			case text:
				return "By.linkText(" + template;

			case partialText:
				return "By.partialLinkText(" + template;

			default:
				return null;

		}

	}
}
