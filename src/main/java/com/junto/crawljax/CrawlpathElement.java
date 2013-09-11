package com.junto.crawljax;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang.StringEscapeUtils;

import com.crawljax.core.CrawlerContext;
import com.crawljax.core.state.CrawlPath;
import com.crawljax.core.state.Eventable;
import com.crawljax.core.state.Identification;
import com.crawljax.forms.FormInput;
import com.google.common.collect.ImmutableSortedSet;

public class CrawlpathElement {
	private final int size;
	List<Integer> loop;
	private LinkedList<String> by;
	private LinkedList<CopyOnWriteArrayList<FormInputElement>> relatedFormInputsList;
	private final String url;
	private CrawlPath path;
	private final ImmutableSortedSet<String> filterAttributes;
	private final static String DOUBLEQUOTATION = "\"";

	public CrawlpathElement(CrawlerContext context) {
		this.url = context.getConfig().getUrl().toString();
		this.path = context.getCrawlPath();
		this.filterAttributes =
		        context.getConfig().getCrawlRules().getPreCrawlConfig().getFilterAttributeNames();
		this.size = path.size();
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
			relatedFormInputsList.add(temporaryList);
			path = path.immutableCopyWithoutLast();
			by.add(WebDriverUtils.idToString(ev.getIdentification()));
		}
	}

	private SortedSet<String> checkEscapeSequence(ImmutableSortedSet<String> attributes) {
		SortedSet<String> set = new TreeSet<String>();
		for (String str : attributes) {
			set.add(StringEscapeUtils.escapeJava(str));
		}
		return set;
	}

	public SortedSet<String> getFilterAttributes() {
		return checkEscapeSequence(filterAttributes);
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

	public List<Integer> getLoop() {
		return loop;
	}

	public String popBy() {
		return by.removeLast();
	}

	public String getUrl() {
		return DOUBLEQUOTATION + url + DOUBLEQUOTATION;
	}


}
