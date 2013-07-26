package com.junto.crawljax;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Test for {@link SourceCodeExtractor}.
 */
public class SourceCodeExtractorTest {
	@Test
	public void testExtractingMethods() throws Exception {
		SourceCodeExtractor extractor = new SourceCodeExtractor(
				ExtractToGeneratedCode.class, GeneratingInvariant.class);
		extractor.parse(
				this.getClass().getResourceAsStream("/ClassWithAnnotation.java"));
		assertEquals(2, extractor.getMethodsToExtract().size());
		assertTrue(extractor.getMethodsToExtract().get(0).trim().startsWith("private String date()"));
		assertTrue(extractor.getMethodsToExtract().get(1).trim().startsWith("private void someHelperMethod()"));
		assertTrue(extractor.getInvariantGenerationMethod().trim().startsWith("public Invariant generateInvariant()"));
		assertEquals("import com.crawljax.condition.RegexCondition;", extractor.getImportDeclarations().get(0).trim());
		assertEquals("import com.crawljax.condition.invariant.Invariant;", extractor.getImportDeclarations().get(1).trim());
	}

	private @interface ExtractToGeneratedCode {}
	private @interface GeneratingInvariant {}
}
