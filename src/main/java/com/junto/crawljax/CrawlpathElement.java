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
	ArrayList<Integer> loop;// for <foreach> of velocity
	private LinkedList<String> by;
	private LinkedList<CopyOnWriteArrayList<FormInputElement>> relatedFormInputsList;
	private final String url;
	private final static String DOUBLEQUOTATION = "\"";

	// private LinkedList<String> action; //click,hover

	public CrawlpathElement(CrawlPath path, String url) {
		this.size = path.size();
		this.url = url;
		loop = new ArrayList<Integer>();
		for (int i = 0; i < size; i++) {
			loop.add(i);
		}
		by = new LinkedList<String>();
		relatedFormInputsList = new LinkedList<CopyOnWriteArrayList<FormInputElement>>();
		while (path.size() > 0) {
			Eventable ev = path.last();
			CopyOnWriteArrayList<FormInputElement> temporaryList =
			        new CopyOnWriteArrayList<FormInputElement>();
			for (FormInput formInput : ev.getRelatedFormInputs()) {
				temporaryList.add(new FormInputElement(formInput));
			}
			System.out.println(ev.getRelatedFormInputs().size());
			relatedFormInputsList.add(temporaryList);
			path = path.immutableCopyWithoutLast();
			by.add(getWebDriverBy(ev.getIdentification()));
		}
	}

	public CopyOnWriteArrayList<FormInputElement> getFormInputElements() {
		return relatedFormInputsList.getLast();
	}

	public boolean FormInputIsEmpty() {
		if (relatedFormInputsList.getLast().size() == 0)
			return true;
		else
			return false;
	}

	public ArrayList<Integer> getLoop() {
		return loop;
	}

	public String popBy() {
		return by.removeLast();
	}

	public String getUrl() {
		return DOUBLEQUOTATION + url + DOUBLEQUOTATION;
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
