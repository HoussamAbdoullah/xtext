/*
* generated by Xtext
*/
package org.eclipse.xtext.parser.assignments.parser.antlr;

import com.google.inject.Inject;

import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.assignments.services.Bug288432TestLanguageGrammarAccess;

public class Bug288432TestLanguageParser extends org.eclipse.xtext.parser.antlr.AbstractAntlrParser {
	
	@Inject
	private Bug288432TestLanguageGrammarAccess grammarAccess;
	
	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
	}
	
	@Override
	protected org.eclipse.xtext.parser.assignments.parser.antlr.internal.InternalBug288432TestLanguageParser createParser(XtextTokenStream stream) {
		return new org.eclipse.xtext.parser.assignments.parser.antlr.internal.InternalBug288432TestLanguageParser(stream, getGrammarAccess());
	}
	
	@Override 
	protected String getDefaultRuleName() {
		return "Body";
	}
	
	public Bug288432TestLanguageGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}
	
	public void setGrammarAccess(Bug288432TestLanguageGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
	
}
