package com.junto.crawljax;

import static java.lang.System.lineSeparator;

import java.util.HashSet;
import java.util.Set;

import com.crawljax.core.state.Identification;
import com.crawljax.forms.FormInput;
import com.crawljax.forms.InputValue;

public class FormInputElement {
	private String type = "text";
	private Identification identification;
	private Set<InputValue> inputValues = new HashSet<InputValue>();
	private final String DOUBLEQUOTATION = "\"";

	public FormInputElement(FormInput forminput) {
		this.type = forminput.getType();
		this.identification = forminput.getIdentification();
		this.inputValues = forminput.getInputValues();
	}

	public String generateFormInputCode() {
		if (type.toLowerCase().startsWith("text")
		        || type.equalsIgnoreCase("password")
		        || type.equalsIgnoreCase("hidden")) {
			return "driver.findElement(" + getWebDriverBy(this.identification)
			        + ").sendKeys(" + DOUBLEQUOTATION
			        + this.inputValues.iterator().next().toString() + DOUBLEQUOTATION + ");";
		} else if (type.equals("checkbox") || type.equals("radio")) {
			return "driver.findElement(" + getWebDriverBy(this.identification)
			        + ").click();";
		} else if (type.startsWith("select")) {
			String code =
			        "selectElement = new Select(driver.findElement("
			                + getWebDriverBy(this.identification)
			                + "));" + lineSeparator();
			for (InputValue inputValue : inputValues) {
				code += generateSelectcode(inputValue);
			}
			return code;
		} else {
			return null;
		}
	}

	private String generateSelectcode(InputValue inputValue) {
		String code = "";
		code += "try{" + lineSeparator()
		        + "selectElement.selectByVisibleText(" + DOUBLEQUOTATION
		        + inputValue.getValue() + DOUBLEQUOTATION + ");" + lineSeparator()
		        + "}catch(Exception e){}" + lineSeparator();

		code += "try{" + lineSeparator()
		        + "selectElement.selectByValue(" + DOUBLEQUOTATION
		        + inputValue.getValue() + DOUBLEQUOTATION + ");" + lineSeparator()
		        + "}catch(Exception e){}" + lineSeparator();
		return code;
	}

	private String getWebDriverBy(Identification id) {
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