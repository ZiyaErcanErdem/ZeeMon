package org.zee.app.zeemon.extensions.parser;

import org.zee.app.zeemon.domain.ScriptParam;
import org.zee.app.zeemon.extensions.execution.CheckScriptExecutionContext;
import org.zee.app.zeemon.extensions.expression.ExpressionContext;

public class ScriptParameterExpressionContext extends ExpressionContext<ScriptParam, CheckScriptExecutionContext>{
	public ScriptParameterExpressionContext(ScriptParam value, CheckScriptExecutionContext ctx) {
		super(value, ctx);
	}
}
