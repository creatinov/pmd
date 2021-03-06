/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd.lang.apex.ast;

import java.lang.reflect.Field;

import apex.jorje.data.ast.Identifier;
import apex.jorje.semantic.ast.compilation.UserInterface;

public class ASTUserInterface extends ApexRootNode<UserInterface> {

	public ASTUserInterface(UserInterface userInterface) {
		super(userInterface);
	}

	/**
	 * Accept the visitor. Note: This needs to be in each concrete node class,
	 * as otherwise the visitor won't work - as java resolves the type "this" at
	 * compile time.
	 */
	public Object jjtAccept(ApexParserVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}

	@Override
	public String getImage() {
		try {
			Field field = node.getClass().getDeclaredField("name");
			field.setAccessible(true);
			Identifier name = (Identifier) field.get(node);
			return name.value;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return super.getImage();
	}
}
