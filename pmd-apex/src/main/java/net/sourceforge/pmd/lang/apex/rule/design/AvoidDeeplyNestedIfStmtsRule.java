/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd.lang.apex.rule.design;

import net.sourceforge.pmd.lang.apex.ast.ASTUserClass;
import net.sourceforge.pmd.lang.apex.ast.ASTIfBlockStatement;
import net.sourceforge.pmd.lang.apex.rule.AbstractApexRule;
import net.sourceforge.pmd.lang.rule.properties.IntegerProperty;

public class AvoidDeeplyNestedIfStmtsRule extends AbstractApexRule {

	private int depth;
	private int depthLimit;

	private static final IntegerProperty PROBLEM_DEPTH_DESCRIPTOR = new IntegerProperty("problemDepth",
			"The if statement depth reporting threshold", 1, 25, 3, 1.0f);

	public AvoidDeeplyNestedIfStmtsRule() {
		definePropertyDescriptor(PROBLEM_DEPTH_DESCRIPTOR);
	}

	public Object visit(ASTUserClass node, Object data) {
		depth = 0;
		depthLimit = getProperty(PROBLEM_DEPTH_DESCRIPTOR);
		
		return super.visit(node, data);
	}

	public Object visit(ASTIfBlockStatement node, Object data) {
		depth++;

		super.visit(node, data);
		if (depth == depthLimit) {
			addViolation(data, node);
		}
		depth--;
		
		return data;
	}
}
