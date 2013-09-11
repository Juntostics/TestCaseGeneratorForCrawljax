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
	private String outputDirectoryPath;
	private static final String OUTPUT_FILENAME_DEFAULT = "GeneratedTestCase.java";
	private String outputFileName = OUTPUT_FILENAME_DEFAULT;
	private String packageName;
	private AnnotatedCodeExtractor extractor = null;
	private long waitAfterReloadUrl = 500;
	private long waitAfterEvent = 500;

	public TestCaseGenerator(String pathToSourceFile) {
		this.outputDirectoryPath = new File(".").getAbsolutePath();
		if (this.outputDirectoryPath.equals("/"))
			outputDirectoryPath = "";
		this.packageName = TestCaseGenerator.class.getPackage().getName();
		try {
			this.extractor = new AnnotatedCodeExtractor(pathToSourceFile);
		} catch (Exception e) {
			throw new IllegalStateException("Your pathToSourceFile is wrong",e);
		}
	}

	public void generateTest(CrawlerContext context) {
		this.waitAfterEvent = context.getConfig().getCrawlRules().getWaitAfterEvent();
		this.waitAfterReloadUrl = context.getConfig().getCrawlRules().getWaitAfterReloadUrl();
		CrawlpathElement element = new CrawlpathElement(context);

		try {
			Velocity.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
			Velocity.setProperty("classpath.resource.loader.class",
			        ClasspathResourceLoader.class.getName());

			Velocity.init();

			VelocityContext velocitycontext = new VelocityContext();
			velocitycontext.put("element", element);
			velocitycontext.put("extractor", extractor);
			velocitycontext.put("className",
			        outputFileName.substring(0, outputFileName.length() - 5));
			velocitycontext.put("packageName", packageName);
			velocitycontext.put("waitAfterReloadUrl", waitAfterReloadUrl);
			velocitycontext.put("waitAfterEvent", waitAfterEvent);

			File file = new File(outputDirectoryPath + "/" + outputFileName);
			PrintWriter pw =
			        new PrintWriter(file);

			Template template = Velocity.getTemplate("TemplateForTestGenerator.vm", "UTF-8");
			template.merge(velocitycontext, pw);
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

	public void setoutputDirectoryPath(String outputDirectoryPath) {
		this.outputDirectoryPath = outputDirectoryPath;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

}
