package com.junto.crawljax;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

public class VeloTest {
	private static String TEMPLATE_LOCATION = System.getProperty("user.dir");

	public VeloTest() {
		WebVelo web =
		        new WebVelo("\"http://www.yahoo.co.jp/\"", "By.name(\"p\")", "\"selenium\"",
		                "By.id(\"srchbtn\")");

		try {
			// これをinitする前にいれないと動かない
			Velocity.setProperty("file.resource.loader.path", TEMPLATE_LOCATION);

			// initialization
			Velocity.init();
			// make Velocity context

			VelocityContext context = new VelocityContext();
			context.put("web", web);

			File file = new File("VeloTester.java");
			System.out.println(file.getAbsolutePath());

			PrintWriter pw =
			        new PrintWriter(new BufferedWriter(new FileWriter(file)));

			// StringWriter pw = new StringWriter();

			// make Template
			Template template = Velocity.getTemplate("sample.vm", "UTF-8");
			// merge with template
			template.merge(context, pw);
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new VeloTest();

	}

}
