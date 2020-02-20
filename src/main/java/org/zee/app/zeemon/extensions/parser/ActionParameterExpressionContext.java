package org.zee.app.zeemon.extensions.parser;

import org.zee.app.zeemon.domain.ActionParam;
import org.zee.app.zeemon.extensions.execution.CheckScriptExecutionContext;
import org.zee.app.zeemon.extensions.expression.ExpressionContext;

public class ActionParameterExpressionContext extends ExpressionContext<ActionParam, CheckScriptExecutionContext>{
	public ActionParameterExpressionContext(ActionParam value, CheckScriptExecutionContext ctx) {
		super(value, ctx);
	}
}
