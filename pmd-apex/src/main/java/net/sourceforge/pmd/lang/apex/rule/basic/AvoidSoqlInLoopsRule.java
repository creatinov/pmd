/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd.lang.apex.rule.basic;

import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.apex.ast.ASTSoqlExpression;
import net.sourceforge.pmd.lang.apex.ast.ASTDoLoopStatement;
import net.sourceforge.pmd.lang.apex.ast.ASTForLoopStatement;
import net.sourceforge.pmd.lang.apex.ast.ASTForEachStatement;
import net.sourceforge.pmd.lang.apex.ast.ASTReturnStatement;
import net.sourceforge.pmd.lang.apex.ast.ASTWhileLoopStatement;
import net.sourceforge.pmd.lang.apex.rule.AbstractApexRule;

public class AvoidSoqlInLoopsRule extends AbstractApexRule {

	@Override
	public Object visit(ASTSoqlExpression node, Object data) {
		if (insideLoop(node) && parentNotReturn(node) && parentNotForEach(node)) {
			addViolation(data, node);
		}
		return data;
	}

	private boolean parentNotReturn(ASTSoqlExpression node) {
		return !(node.jjtGetParent() instanceof ASTReturnStatement);
	}

	private boolean parentNotForEach(ASTSoqlExpression node) {
		return !(node.jjtGetParent() instanceof ASTForEachStatement);
	}

	private boolean insideLoop(ASTSoqlExpression node) {
		Node n = node.jjtGetParent();

		while (n != null) {
			if (n instanceof ASTDoLoopStatement || n instanceof ASTWhileLoopStatement
					|| n instanceof ASTForLoopStatement || n instanceof ASTForEachStatement) {
				return true;
			}
			n = n.jjtGetParent();
		}
		
		return false;
	}
}
