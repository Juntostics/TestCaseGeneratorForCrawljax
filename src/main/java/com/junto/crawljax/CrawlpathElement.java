package com.junto.crawljax;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.crawljax.core.state.CrawlPath;
import com.crawljax.core.state.Eventable;
import com.crawljax.core.state.Identification;
import com.crawljax.forms.FormInput;

public class CrawlpathElement {
	private final int size;
	private ArrayList<Integer> roop;// for <foreach> of velocity
	private LinkedList<String> by;
	private LinkedList<ArrayList<String>> byForFormInput;
	private LinkedList<LinkedList<String>> formInputValue;
	private LinkedList<ArrayList<Integer>> Index;
	private final String url;
	private final static String doubleQuotation = "\"";
	private CopyOnWriteArrayList<FormInput> FormInputs;

	// private LinkedList<String> action; //click,hover

	public CrawlpathElement(CrawlPath path, String url) {
		this.size = path.size();
		this.url = url;
		roop = new ArrayList<Integer>();
		for (int i = 0; i < size; i++) {
			roop.add(i);
		}
		byForFormInput = new LinkedList<ArrayList<String>>();
		formInputValue = new LinkedList<LinkedList<String>>();
		Index = new LinkedList<ArrayList<Integer>>();
		by = new LinkedList<String>();
		while (path.size() > 0) {
			Eventable ev = path.last();
			FormInputs = ev.getRelatedFormInputs();
			int i = 0;
			for (FormInput formInput : FormInputs) {
				ArrayList<Integer> tempIndex = new ArrayList<Integer>();
				tempIndex.add(i);
				Index.add(tempIndex);
				i++;
				ArrayList<String> tempBy = new ArrayList<String>();
				tempBy.add(getWebDriverBy(formInput.getIdentification()));
				byForFormInput.add(tempBy);
				LinkedList<String> tempInputValue = new LinkedList<String>();
				tempInputValue.add(formInput.getInputValues().iterator().next().getValue());
				formInputValue.add(tempInputValue);
			}
			path = path.immutableCopyWithoutLast();
			by.add(getWebDriverBy(ev.getIdentification()));
		}
	}

	public ArrayList<Integer> getIndex() {
		return Index.removeLast();
	}

	public ArrayList<Integer> getRoop() {
		return roop;
	}

	public ArrayList<String> popByForFormInput() {
		return byForFormInput.removeLast();
	}

	public LinkedList<String> popFormInputValue() {
		return formInputValue.removeLast();
	}

	public String popBy() {
		return by.removeLast();
	}

	public String getUrl() {
		return doubleQuotation + url + doubleQuotation;
	}

	public String getWebDriverBy(Identification id) {

		switch (id.getHow()) {
			case name:
				return "By.name(" + doubleQuotation + id.getValue() + doubleQuotation + ")";

			case xpath:
				// Work around HLWK driver bug
				return "By.xpath(" + doubleQuotation
				        + id.getValue().replaceAll("/BODY\\[1\\]/", "/BODY/") + doubleQuotation
				        + ")";

			case id:
				return "By.id(" + doubleQuotation + id.getValue() + doubleQuotation + ")";

			case tag:
				return "By.tagName(" + doubleQuotation + id.getValue() + doubleQuotation + ")";

			case text:
				return "By.linkText(" + doubleQuotation + id.getValue() + doubleQuotation + ")";

			case partialText:
				return "By.partialLinkText(" + doubleQuotation + id.getValue() + doubleQuotation
				        + ")";

			default:
				return null;

		}

	}

}
