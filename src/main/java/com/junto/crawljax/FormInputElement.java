package com.junto.crawljax;

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
	private final String CRLF;

	public FormInputElement(FormInput forminput) {
		this.type = forminput.getType();
		this.identification = forminput.getIdentification();
		this.inputValues = forminput.getInputValues();
		this.CRLF = System.lineSeparator();
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
			                + "));" + CRLF;
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
		code += "try{" + CRLF
		        + "selectElement.selectByVisibleText(" + DOUBLEQUOTATION
		        + inputValue.getValue() + DOUBLEQUOTATION + ");" + CRLF
		        + "}catch(Exception e){}" + CRLF;

		code += "try{" + CRLF
		        + "selectElement.selectByValue(" + DOUBLEQUOTATION
		        + inputValue.getValue() + DOUBLEQUOTATION + ");" + CRLF
		        + "}catch(Exception e){}" + CRLF;
		return code;
	}

	public String getWebDriverBy(Identification id) {

		switch (id.getHow()) {
			case name:
				return "By.name(" + DOUBLEQUOTATION + id.getValue() + DOUBLEQUOTATION + ")";

			case xpath:
				// Work around HLWK driver bug
				return "By.xpath(" + DOUBLEQUOTATION
				        + id.getValue().replaceAll("/BODY\\[1\\]/", "/BODY/") + DOUBLEQUOTATION
				        + ")";

			case id:
				return "By.id(" + DOUBLEQUOTATION + id.getValue() + DOUBLEQUOTATION + ")";

			case tag:
				return "By.tagName(" + DOUBLEQUOTATION + id.getValue() + DOUBLEQUOTATION + ")";

			case text:
				return "By.linkText(" + DOUBLEQUOTATION + id.getValue() + DOUBLEQUOTATION + ")";

			case partialText:
				return "By.partialLinkText(" + DOUBLEQUOTATION + id.getValue() + DOUBLEQUOTATION
				        + ")";

			default:
				return null;

		}

	}
}