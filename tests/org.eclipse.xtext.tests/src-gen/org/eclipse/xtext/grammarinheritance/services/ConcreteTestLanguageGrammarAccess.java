/*
* generated by Xtext
*/
package org.eclipse.xtext.grammarinheritance.services;

import com.google.inject.Singleton;
import com.google.inject.Inject;

import java.util.List;

import org.eclipse.xtext.*;
import org.eclipse.xtext.service.GrammarProvider;
import org.eclipse.xtext.service.AbstractElementFinder.*;

import org.eclipse.xtext.grammarinheritance.services.AbstractTestLanguageGrammarAccess;

@Singleton
public class ConcreteTestLanguageGrammarAccess extends AbstractGrammarElementFinder {
	
	
	public class RootRuleElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "RootRule");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cConcreteParserRuleParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cCallOverridenParserRuleParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cCallExtendedParserRuleParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final RuleCall cOverridableParserRule2ParserRuleCall_3 = (RuleCall)cAlternatives.eContents().get(3);
		
		//RootRule:
		//	ConcreteParserRule | CallOverridenParserRule | CallExtendedParserRule | OverridableParserRule2;
		public ParserRule getRule() { return rule; }

		//ConcreteParserRule | CallOverridenParserRule | CallExtendedParserRule | OverridableParserRule2
		public Alternatives getAlternatives() { return cAlternatives; }

		//ConcreteParserRule
		public RuleCall getConcreteParserRuleParserRuleCall_0() { return cConcreteParserRuleParserRuleCall_0; }

		//CallOverridenParserRule
		public RuleCall getCallOverridenParserRuleParserRuleCall_1() { return cCallOverridenParserRuleParserRuleCall_1; }

		//CallExtendedParserRule
		public RuleCall getCallExtendedParserRuleParserRuleCall_2() { return cCallExtendedParserRuleParserRuleCall_2; }

		//OverridableParserRule2
		public RuleCall getOverridableParserRule2ParserRuleCall_3() { return cOverridableParserRule2ParserRuleCall_3; }
	}

	public class ConcreteParserRuleElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "ConcreteParserRule");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cModelKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cMagicNumberAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cMagicNumberREALTerminalRuleCall_1_0 = (RuleCall)cMagicNumberAssignment_1.eContents().get(0);
		private final Keyword cColonKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cElementsAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cElementsInheritedParserRuleParserRuleCall_3_0 = (RuleCall)cElementsAssignment_3.eContents().get(0);
		
		//// call InheritedParserRule
		//ConcreteParserRule:
		//	"model" magicNumber=REAL ":" elements+=InheritedParserRule*;
		public ParserRule getRule() { return rule; }

		//"model" magicNumber=REAL ":" elements+=InheritedParserRule*
		public Group getGroup() { return cGroup; }

		//"model"
		public Keyword getModelKeyword_0() { return cModelKeyword_0; }

		//magicNumber=REAL
		public Assignment getMagicNumberAssignment_1() { return cMagicNumberAssignment_1; }

		//REAL
		public RuleCall getMagicNumberREALTerminalRuleCall_1_0() { return cMagicNumberREALTerminalRuleCall_1_0; }

		//":"
		public Keyword getColonKeyword_2() { return cColonKeyword_2; }

		//elements+=InheritedParserRule*
		public Assignment getElementsAssignment_3() { return cElementsAssignment_3; }

		//InheritedParserRule
		public RuleCall getElementsInheritedParserRuleParserRuleCall_3_0() { return cElementsInheritedParserRuleParserRuleCall_3_0; }
	}

	public class OverridableParserRuleElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "OverridableParserRule");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cOverriddenelementKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cNameIDTerminalRuleCall_1_0 = (RuleCall)cNameAssignment_1.eContents().get(0);
		
		//// override OverridableParserRule and call it
		//OverridableParserRule returns mm::AType:
		//	"overriddenelement" name=ID;
		public ParserRule getRule() { return rule; }

		//"overriddenelement" name=ID
		public Group getGroup() { return cGroup; }

		//"overriddenelement"
		public Keyword getOverriddenelementKeyword_0() { return cOverriddenelementKeyword_0; }

		//name=ID
		public Assignment getNameAssignment_1() { return cNameAssignment_1; }

		//ID
		public RuleCall getNameIDTerminalRuleCall_1_0() { return cNameIDTerminalRuleCall_1_0; }
	}

	public class CallOverridenParserRuleElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "CallOverridenParserRule");
		private final Assignment cCallAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cCallAbstractCallOverridenParserRuleParserRuleCall_0 = (RuleCall)cCallAssignment.eContents().get(0);
		
		//CallOverridenParserRule:
		//	call=AbstractCallOverridenParserRule;
		public ParserRule getRule() { return rule; }

		//call=AbstractCallOverridenParserRule
		public Assignment getCallAssignment() { return cCallAssignment; }

		//AbstractCallOverridenParserRule
		public RuleCall getCallAbstractCallOverridenParserRuleParserRuleCall_0() { return cCallAbstractCallOverridenParserRuleParserRuleCall_0; }
	}

	public class OverridableParserRule2Elements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "OverridableParserRule2");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cOverriddenOtherElementKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cNameIDTerminalRuleCall_1_0 = (RuleCall)cNameAssignment_1.eContents().get(0);
		private final Keyword cHyphenMinusKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cAgeAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cAgeINTTerminalRuleCall_3_0 = (RuleCall)cAgeAssignment_3.eContents().get(0);
		
		//// override parser rule and change return type
		//OverridableParserRule2 returns AType2:
		//	"overridden other element" name=ID "-" age=INT;
		public ParserRule getRule() { return rule; }

		//"overridden other element" name=ID "-" age=INT
		public Group getGroup() { return cGroup; }

		//"overridden other element"
		public Keyword getOverriddenOtherElementKeyword_0() { return cOverriddenOtherElementKeyword_0; }

		//name=ID
		public Assignment getNameAssignment_1() { return cNameAssignment_1; }

		//ID
		public RuleCall getNameIDTerminalRuleCall_1_0() { return cNameIDTerminalRuleCall_1_0; }

		//"-"
		public Keyword getHyphenMinusKeyword_2() { return cHyphenMinusKeyword_2; }

		//age=INT
		public Assignment getAgeAssignment_3() { return cAgeAssignment_3; }

		//INT
		public RuleCall getAgeINTTerminalRuleCall_3_0() { return cAgeINTTerminalRuleCall_3_0; }
	}

	public class ExtendableParserRuleElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "ExtendableParserRule");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cSubrule1ParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cSubrule2ParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cSubrule3ParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		
		//// override and extend ExtendableParserRule and call it
		//ExtendableParserRule returns mm::AType:
		//	Subrule1 | Subrule2 | Subrule3;
		public ParserRule getRule() { return rule; }

		//Subrule1 | Subrule2 | Subrule3
		public Alternatives getAlternatives() { return cAlternatives; }

		//Subrule1
		public RuleCall getSubrule1ParserRuleCall_0() { return cSubrule1ParserRuleCall_0; }

		//Subrule2
		public RuleCall getSubrule2ParserRuleCall_1() { return cSubrule2ParserRuleCall_1; }

		//Subrule3
		public RuleCall getSubrule3ParserRuleCall_2() { return cSubrule3ParserRuleCall_2; }
	}

	public class Subrule1Elements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "Subrule1");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cSubrule1Keyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cNameIDTerminalRuleCall_1_0 = (RuleCall)cNameAssignment_1.eContents().get(0);
		private final Assignment cSub1Assignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cSub1IDTerminalRuleCall_2_0 = (RuleCall)cSub1Assignment_2.eContents().get(0);
		
		//Subrule1:
		//	"subrule1" name=ID sub1=ID;
		public ParserRule getRule() { return rule; }

		//"subrule1" name=ID sub1=ID
		public Group getGroup() { return cGroup; }

		//"subrule1"
		public Keyword getSubrule1Keyword_0() { return cSubrule1Keyword_0; }

		//name=ID
		public Assignment getNameAssignment_1() { return cNameAssignment_1; }

		//ID
		public RuleCall getNameIDTerminalRuleCall_1_0() { return cNameIDTerminalRuleCall_1_0; }

		//sub1=ID
		public Assignment getSub1Assignment_2() { return cSub1Assignment_2; }

		//ID
		public RuleCall getSub1IDTerminalRuleCall_2_0() { return cSub1IDTerminalRuleCall_2_0; }
	}

	public class Subrule2Elements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "Subrule2");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cSubrule3Keyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cNameIDTerminalRuleCall_1_0 = (RuleCall)cNameAssignment_1.eContents().get(0);
		private final Assignment cSub2Assignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cSub2STRINGTerminalRuleCall_2_0 = (RuleCall)cSub2Assignment_2.eContents().get(0);
		
		//Subrule2:
		//	"subrule3" name=ID sub2=STRING;
		public ParserRule getRule() { return rule; }

		//"subrule3" name=ID sub2=STRING
		public Group getGroup() { return cGroup; }

		//"subrule3"
		public Keyword getSubrule3Keyword_0() { return cSubrule3Keyword_0; }

		//name=ID
		public Assignment getNameAssignment_1() { return cNameAssignment_1; }

		//ID
		public RuleCall getNameIDTerminalRuleCall_1_0() { return cNameIDTerminalRuleCall_1_0; }

		//sub2=STRING
		public Assignment getSub2Assignment_2() { return cSub2Assignment_2; }

		//STRING
		public RuleCall getSub2STRINGTerminalRuleCall_2_0() { return cSub2STRINGTerminalRuleCall_2_0; }
	}

	public class Subrule3Elements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "Subrule3");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cSubrule3Keyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cNameIDTerminalRuleCall_1_0 = (RuleCall)cNameAssignment_1.eContents().get(0);
		private final Assignment cSub1Assignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cSub1INTTerminalRuleCall_2_0 = (RuleCall)cSub1Assignment_2.eContents().get(0);
		
		//Subrule3:
		//	"subrule3" name=ID sub1=INT;
		public ParserRule getRule() { return rule; }

		//"subrule3" name=ID sub1=INT
		public Group getGroup() { return cGroup; }

		//"subrule3"
		public Keyword getSubrule3Keyword_0() { return cSubrule3Keyword_0; }

		//name=ID
		public Assignment getNameAssignment_1() { return cNameAssignment_1; }

		//ID
		public RuleCall getNameIDTerminalRuleCall_1_0() { return cNameIDTerminalRuleCall_1_0; }

		//sub1=INT
		public Assignment getSub1Assignment_2() { return cSub1Assignment_2; }

		//INT
		public RuleCall getSub1INTTerminalRuleCall_2_0() { return cSub1INTTerminalRuleCall_2_0; }
	}

	public class CallExtendedParserRuleElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "CallExtendedParserRule");
		private final Assignment cCallAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cCallAbstractCallExtendedParserRuleParserRuleCall_0 = (RuleCall)cCallAssignment.eContents().get(0);
		
		//CallExtendedParserRule:
		//	call=AbstractCallExtendedParserRule;
		public ParserRule getRule() { return rule; }

		//call=AbstractCallExtendedParserRule
		public Assignment getCallAssignment() { return cCallAssignment; }

		//AbstractCallExtendedParserRule
		public RuleCall getCallAbstractCallExtendedParserRuleParserRuleCall_0() { return cCallAbstractCallExtendedParserRuleParserRuleCall_0; }
	}
	
	
	private RootRuleElements pRootRule;
	private ConcreteParserRuleElements pConcreteParserRule;
	private OverridableParserRuleElements pOverridableParserRule;
	private CallOverridenParserRuleElements pCallOverridenParserRule;
	private OverridableParserRule2Elements pOverridableParserRule2;
	private ExtendableParserRuleElements pExtendableParserRule;
	private Subrule1Elements pSubrule1;
	private Subrule2Elements pSubrule2;
	private Subrule3Elements pSubrule3;
	private CallExtendedParserRuleElements pCallExtendedParserRule;
	
	private final Grammar grammar;

	private AbstractTestLanguageGrammarAccess gaAbstractTestLanguage;

	@Inject
	public ConcreteTestLanguageGrammarAccess(GrammarProvider grammarProvider,
		AbstractTestLanguageGrammarAccess gaAbstractTestLanguage) {
		this.grammar = internalFindGrammar(grammarProvider);
		this.gaAbstractTestLanguage = gaAbstractTestLanguage;
	}
	
	protected Grammar internalFindGrammar(GrammarProvider grammarProvider) {
		Grammar grammar = grammarProvider.getGrammar(this);
		while (grammar != null) {
			if ("org.eclipse.xtext.grammarinheritance.ConcreteTestLanguage".equals(grammar.getName())) {
				return grammar;
			}
			List<Grammar> grammars = grammar.getUsedGrammars();
			if (!grammars.isEmpty()) {
				grammar = grammars.iterator().next();
			} else {
				return null;
			}
		}
		return grammar;
	}
	
	
	public Grammar getGrammar() {
		return grammar;
	}
	

	public AbstractTestLanguageGrammarAccess getAbstractTestLanguageGrammarAccess() {
		return gaAbstractTestLanguage;
	}

	
	//RootRule:
	//	ConcreteParserRule | CallOverridenParserRule | CallExtendedParserRule | OverridableParserRule2;
	public RootRuleElements getRootRuleAccess() {
		return (pRootRule != null) ? pRootRule : (pRootRule = new RootRuleElements());
	}
	
	public ParserRule getRootRuleRule() {
		return getRootRuleAccess().getRule();
	}

	//// call InheritedParserRule
	//ConcreteParserRule:
	//	"model" magicNumber=REAL ":" elements+=InheritedParserRule*;
	public ConcreteParserRuleElements getConcreteParserRuleAccess() {
		return (pConcreteParserRule != null) ? pConcreteParserRule : (pConcreteParserRule = new ConcreteParserRuleElements());
	}
	
	public ParserRule getConcreteParserRuleRule() {
		return getConcreteParserRuleAccess().getRule();
	}

	//// override OverridableParserRule and call it
	//OverridableParserRule returns mm::AType:
	//	"overriddenelement" name=ID;
	public OverridableParserRuleElements getOverridableParserRuleAccess() {
		return (pOverridableParserRule != null) ? pOverridableParserRule : (pOverridableParserRule = new OverridableParserRuleElements());
	}
	
	public ParserRule getOverridableParserRuleRule() {
		return getOverridableParserRuleAccess().getRule();
	}

	//CallOverridenParserRule:
	//	call=AbstractCallOverridenParserRule;
	public CallOverridenParserRuleElements getCallOverridenParserRuleAccess() {
		return (pCallOverridenParserRule != null) ? pCallOverridenParserRule : (pCallOverridenParserRule = new CallOverridenParserRuleElements());
	}
	
	public ParserRule getCallOverridenParserRuleRule() {
		return getCallOverridenParserRuleAccess().getRule();
	}

	//// override parser rule and change return type
	//OverridableParserRule2 returns AType2:
	//	"overridden other element" name=ID "-" age=INT;
	public OverridableParserRule2Elements getOverridableParserRule2Access() {
		return (pOverridableParserRule2 != null) ? pOverridableParserRule2 : (pOverridableParserRule2 = new OverridableParserRule2Elements());
	}
	
	public ParserRule getOverridableParserRule2Rule() {
		return getOverridableParserRule2Access().getRule();
	}

	//// override and extend ExtendableParserRule and call it
	//ExtendableParserRule returns mm::AType:
	//	Subrule1 | Subrule2 | Subrule3;
	public ExtendableParserRuleElements getExtendableParserRuleAccess() {
		return (pExtendableParserRule != null) ? pExtendableParserRule : (pExtendableParserRule = new ExtendableParserRuleElements());
	}
	
	public ParserRule getExtendableParserRuleRule() {
		return getExtendableParserRuleAccess().getRule();
	}

	//Subrule1:
	//	"subrule1" name=ID sub1=ID;
	public Subrule1Elements getSubrule1Access() {
		return (pSubrule1 != null) ? pSubrule1 : (pSubrule1 = new Subrule1Elements());
	}
	
	public ParserRule getSubrule1Rule() {
		return getSubrule1Access().getRule();
	}

	//Subrule2:
	//	"subrule3" name=ID sub2=STRING;
	public Subrule2Elements getSubrule2Access() {
		return (pSubrule2 != null) ? pSubrule2 : (pSubrule2 = new Subrule2Elements());
	}
	
	public ParserRule getSubrule2Rule() {
		return getSubrule2Access().getRule();
	}

	//Subrule3:
	//	"subrule3" name=ID sub1=INT;
	public Subrule3Elements getSubrule3Access() {
		return (pSubrule3 != null) ? pSubrule3 : (pSubrule3 = new Subrule3Elements());
	}
	
	public ParserRule getSubrule3Rule() {
		return getSubrule3Access().getRule();
	}

	//CallExtendedParserRule:
	//	call=AbstractCallExtendedParserRule;
	public CallExtendedParserRuleElements getCallExtendedParserRuleAccess() {
		return (pCallExtendedParserRule != null) ? pCallExtendedParserRule : (pCallExtendedParserRule = new CallExtendedParserRuleElements());
	}
	
	public ParserRule getCallExtendedParserRuleRule() {
		return getCallExtendedParserRuleAccess().getRule();
	}

	//InheritedParserRule returns mm::AType:
	//	"element" name=ID;
	public AbstractTestLanguageGrammarAccess.InheritedParserRuleElements getInheritedParserRuleAccess() {
		return gaAbstractTestLanguage.getInheritedParserRuleAccess();
	}
	
	public ParserRule getInheritedParserRuleRule() {
		return getInheritedParserRuleAccess().getRule();
	}

	//AbstractCallOverridenParserRule returns mm::AModel:
	//	"overridemodel" elements+=OverridableParserRule*;
	public AbstractTestLanguageGrammarAccess.AbstractCallOverridenParserRuleElements getAbstractCallOverridenParserRuleAccess() {
		return gaAbstractTestLanguage.getAbstractCallOverridenParserRuleAccess();
	}
	
	public ParserRule getAbstractCallOverridenParserRuleRule() {
		return getAbstractCallOverridenParserRuleAccess().getRule();
	}

	//AbstractCallExtendedParserRule returns mm::AModel:
	//	"extendedmodel" elements+=ExtendableParserRule*;
	public AbstractTestLanguageGrammarAccess.AbstractCallExtendedParserRuleElements getAbstractCallExtendedParserRuleAccess() {
		return gaAbstractTestLanguage.getAbstractCallExtendedParserRuleAccess();
	}
	
	public ParserRule getAbstractCallExtendedParserRuleRule() {
		return getAbstractCallExtendedParserRuleAccess().getRule();
	}

	//terminal REAL returns ecore::EDouble:
	//	INT "." INT;
	public TerminalRule getREALRule() {
		return gaAbstractTestLanguage.getREALRule();
	} 

	//terminal ID:
	//	"^"? ("a".."z" | "A".."Z" | "�" | "�" | "�" | "_") ("a".."z" | "A".."Z" | "_" | "0".."9")*;
	public TerminalRule getIDRule() {
		return gaAbstractTestLanguage.getIDRule();
	} 

	//terminal INT returns ecore::EInt:
	//	"0".."9"+;
	public TerminalRule getINTRule() {
		return gaAbstractTestLanguage.getINTRule();
	} 

	//terminal STRING:
	//	"\"" ("\\" ("b" | "t" | "n" | "f" | "r" | "u" | "\"" | "\'" | "\\") | !("\\" | "\""))* "\"" | "\'" ("\\" ("b" | "t" |
	//	"n" | "f" | "r" | "u" | "\"" | "\'" | "\\") | !("\\" | "\'"))* "\'";
	public TerminalRule getSTRINGRule() {
		return gaAbstractTestLanguage.getSTRINGRule();
	} 

	//terminal ML_COMMENT:
	//	"/ *"->"* /";
	public TerminalRule getML_COMMENTRule() {
		return gaAbstractTestLanguage.getML_COMMENTRule();
	} 

	//terminal SL_COMMENT:
	//	"//" !("\n" | "\r")* ("\r"? "\n")?;
	public TerminalRule getSL_COMMENTRule() {
		return gaAbstractTestLanguage.getSL_COMMENTRule();
	} 

	//terminal WS:
	//	(" " | "\t" | "\r" | "\n")+;
	public TerminalRule getWSRule() {
		return gaAbstractTestLanguage.getWSRule();
	} 

	//terminal ANY_OTHER:
	//	.;
	public TerminalRule getANY_OTHERRule() {
		return gaAbstractTestLanguage.getANY_OTHERRule();
	} 
}
