package com.nealford.art.parameterizedcommands;

import java.util.*;

/**
 * This class serves as a dummy model to demonstrate
 * parameterizing requests.
 *
 * @author Neal Ford
 */
public class TheModel {
    private List keywords = new Vector(10, 5);
    private List proposedKeywords = new Vector(5, 2);

    /**
     * Construct a new model object and initialize the list of
     * keywords.
     */
    public TheModel() {
        keywords.add("abstract");
        keywords.add("default");
        keywords.add("if");
        keywords.add("private");
        keywords.add("this");
        keywords.add("boolean");
        keywords.add("do");
        keywords.add("implements");
        keywords.add("protected");
        keywords.add("throw");
        keywords.add("break");
        keywords.add("double");
        keywords.add("import");
        keywords.add("public");
        keywords.add("throws");
        keywords.add("byte");
        keywords.add("else");
        keywords.add("instanceof");
        keywords.add("return");
        keywords.add("transient");
        keywords.add("case");
        keywords.add("extends");
        keywords.add("int");
        keywords.add("short");
        keywords.add("try");
        keywords.add("catch");
        keywords.add("final");
        keywords.add("interface");
        keywords.add("static");
        keywords.add("void");
        keywords.add("char");
        keywords.add("finally");
        keywords.add("long");
        keywords.add("strictfp");
        keywords.add("volatile");
        keywords.add("class");
        keywords.add("float");
        keywords.add("native");
        keywords.add("super");
        keywords.add("while");
        keywords.add("const");
        keywords.add("for");
        keywords.add("new");
        keywords.add("switch");
        keywords.add("continue");
        keywords.add("goto");
        keywords.add("package");
        keywords.add("synchronized");
    }

    /**
     * <code>keywords</code> accessor
     */
    public List getKeywords() {
        return keywords;
    }

    /**
     * <code>proposedKeywords</code> accessor
     */
    public List getProposedKeywords() {
        return proposedKeywords;
    }

    /**
     * Allows the user to add a new proposed keyword to the
     * langauge. Note that the new keywords aren't persisted
     * anywhere because a) This is a simple example and b) We
     * don't want people arbitrarily adding keywords to Java!
     */
    public void addKeyword(String newKeyword) {
        proposedKeywords.add(newKeyword);
    }
}


