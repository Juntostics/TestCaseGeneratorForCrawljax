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

import com.crawljax.core.CrawlerContext;

public class TestCaseGenerator {
	private final String path_to_sourcefile;
	private String output_dir;
	private String url;
	private AnnotatedCodeExtractor extractor = null;

	public TestCaseGenerator(String path) {
		this.path_to_sourcefile = path;
	}

	public void generateTest(CrawlerContext context) {
		this.url = context.getConfig().getUrl().toString();
		CrawlpathElement element = new CrawlpathElement(context);
		try {
			extractor = new AnnotatedCodeExtractor(path_to_sourcefile);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

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
			velocitycontext.put("extractor", extractor);

			File file = new File("WebapplicationTest.java");
			PrintWriter pw =
			        new PrintWriter(file);

			// make Template
			Template template = Velocity.getTemplate("auto-generatedTest.vm", "UTF-8");
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

	public void setOutputDir(String output_dir) {
		// TODO Auto-generated method stub
		this.output_dir = output_dir;
	}

}
