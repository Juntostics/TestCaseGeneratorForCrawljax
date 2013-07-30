package com.junto.crawljax;

import java.util.List;

public class AnnotatedCodeExtractor {
	private SourceCodeExtractor extractor;

	public AnnotatedCodeExtractor(String path_to_sourcefile) throws Exception {
		extractor = new SourceCodeExtractor(ExtractToTestCode.class, GenerateInvariant.class);
		extractor.parse(path_to_sourcefile);
	}

	public String getGenerateInvariantSourcecode() {
		return extractor.getInvariantGenerationMethod();
	}

	public List<String> getExtractToTestCodeSourcecode() {
		return extractor.getMethodsToExtract();
	}

	public List<String> getImportList() {
		return extractor.getImportDeclarations();
	}

}
