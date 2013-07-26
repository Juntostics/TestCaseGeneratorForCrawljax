package com.junto.crawljax;

import java.io.File;
import java.io.PrintWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.crawljax.condition.invariant.Invariant;
import com.crawljax.core.CrawlerContext;
import com.crawljax.core.plugin.OnInvariantViolationPlugin;

public class TestCasePluginGenerator implements OnInvariantViolationPlugin {
	private String url;

	@Override
	public void onInvariantViolation(Invariant invariant, CrawlerContext context) {
		System.out.println(context.getCrawlPath().size());
		System.out.println(context.getCrawlPath().toString());

		this.url = context.getConfig().getUrl().toString();

		CrawlpathElement element = new CrawlpathElement(context.getCrawlPath(), url);

		try {
			// これをinitする前にいれないと動かない
			Velocity.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
			Velocity.setProperty("classpath.resource.loader.class",
			        ClasspathResourceLoader.class.getName());

			// initialization
			Velocity.init();
			// make Velocity context

			VelocityContext velocitycontext = new VelocityContext();
			velocitycontext.put("element", element);

			File file = new File("WebapplicationTest.java");
			PrintWriter pw =
			        new PrintWriter(file);

			// StringWriter pw = new StringWriter();

			// make Template
			Template template = Velocity.getTemplate("testTemplate.vm", "UTF-8");
			// merge with template
			template.merge(velocitycontext, pw);
			// print on console
			// System.out.println(pw.toString());
			pw.flush();
			pw.close();

		} catch (ResourceNotFoundException e) {
			// when you can't find template
			System.err.println(e.getMessage());
		} catch (ParseErrorException e) {
			// when you have errors in your grammar
			System.err.println(e.getMessage());
		} catch (MethodInvocationException e) {
			// when you have errors in your template
			System.err.println(e.getMessage());
		} catch (Exception e) {
			System.err.println(e.getMessage());

		}
	}

}
