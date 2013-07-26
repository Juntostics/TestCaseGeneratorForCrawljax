package com.junto.crawljax;

import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Helper class to extract part of method with given annotations.
 */
public class SourceCodeExtractor {
	private CompilationUnit compilationUnit;
	private MethodExtractVisitor visitor;

	public SourceCodeExtractor(
			Class<? extends Annotation> annotationForExtractingMethod,
			Class<? extends Annotation> annotationForGeneratingInvariant) {
		visitor = new MethodExtractVisitor(
				annotationForExtractingMethod,
				annotationForGeneratingInvariant);
	}

	/**
	 * Exception representing something go wrong during parsing Java source..
	 */
	public class ParseException extends Exception {
		public ParseException(String message) {
			super(message);
		}

		public ParseException(String message, Throwable cause) {
			super(message, cause);
		}
	}

	/**
	 * @param codeInputStream inputStream to retrieve Java source code. This
	 *						method doesn't close the given stream; Caller
	 *						should close the stream after this method by
	 *						itself.
	 * @throws ParseException when failed to parse given source code.
	 */
	public void parse(InputStream codeInputStream) throws ParseException {
		try {
			compilationUnit = JavaParser.parse(codeInputStream);
		} catch (japa.parser.ParseException e) {
			throw new ParseException("Failed to parse source code.", e);
		}
		visitor.visit(compilationUnit, null);
	}

	/**
	 *
	 * @param pathToFile Java file to be parsed.
	 * @throws ParseException ParseException when failed to parse given source code.
	 */
	public void parse(String pathToFile) throws ParseException {
		File file = new File(pathToFile);
		if (!file.exists()) {
			throw new ParseException("File '" + pathToFile + "' is not found");
		}

		try {
			InputStream stream = new FileInputStream(file);
			parse(stream);
		} catch (IOException e) {
			throw new ParseException("File was found, but cannot open it", e);
		}
	}

	public List<String> getImportDeclarations() {
		preconditionParseIsCalledCheck();
		return listToStringList(visitor.imports);
	}

	public List<String> getMethodsToExtract() {
		preconditionParseIsCalledCheck();
		return listToStringList(visitor.methodToExtract);
	}

	public String getInvariantGenerationMethod() {
		preconditionParseIsCalledCheck();
		return visitor.invariantGeneratingMethod.toString();
	}

	private void preconditionParseIsCalledCheck() {
		if (visitor == null) {
			throw new IllegalStateException("parse() method must be called.");
		}
	}

	private List<String> listToStringList(List list) {
		List<String> output = new ArrayList<>();
		for (Object o: list) {
			output.add(o.toString());
		}
		return output;
	}

	/**
	 * Visitor to extract methods with given annotation.
	 */
	private static class MethodExtractVisitor extends VoidVisitorAdapter {
		Class<? extends Annotation> annotationForExtractingMethod;
		Class<? extends Annotation> annotationForInvariantGeneratingMethod;
		MethodDeclaration invariantGeneratingMethod;
		List<MethodDeclaration> methodToExtract = new ArrayList<>();
		List<ImportDeclaration> imports = new ArrayList<>();

		private MethodExtractVisitor(
				Class<? extends Annotation> annotationForExtractingMethod,
				Class<? extends Annotation> annotationForInvariantGeneratingMethod) {
			this.annotationForExtractingMethod = annotationForExtractingMethod;
			this.annotationForInvariantGeneratingMethod = annotationForInvariantGeneratingMethod;
		}

		@Override
		public void visit(ImportDeclaration declarations, Object arg) {
			imports.add(declarations);
		}

		@Override
		public void visit(MethodDeclaration methodDeclaration, Object arg) {
			List<AnnotationExpr> annotations
					= methodDeclaration.getAnnotations();
			if (annotations == null) {
				return;
			}

			for (AnnotationExpr annotationOnMethod: annotations) {
				String annotationStr = annotationOnMethod.getName().toString();
				if (annotationStr.equals(
						annotationForInvariantGeneratingMethod.getSimpleName())) {
					if (invariantGeneratingMethod == null) {
						removeAnnotation(methodDeclaration, annotationOnMethod);
						invariantGeneratingMethod = methodDeclaration;
					} else {
						throw new IllegalStateException(
								annotationForInvariantGeneratingMethod.getSimpleName()
								+ " annotation must be put only one method"
						);
					}
					continue;
				}

				if (annotationStr.equals(
						annotationForExtractingMethod.getSimpleName())) {
					removeAnnotation(methodDeclaration, annotationOnMethod);
					methodToExtract.add(methodDeclaration);
				}
			}
		}

		private void removeAnnotation(
				MethodDeclaration methodDeclaration, AnnotationExpr annotation) {
			List<AnnotationExpr> annotations =
					new ArrayList<>(methodDeclaration.getAnnotations());
			annotations.remove(annotation);
			methodDeclaration.setAnnotations(annotations);
		}
	}
}
