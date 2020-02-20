package org.zee.app.zeemon.extensions.expression;

import org.springframework.expression.spel.support.StandardEvaluationContext;

public class RuleContext extends StandardEvaluationContext {
	public RuleContext(){
        super();
    }
	public RuleContext(Object root) {
		super(root);
	}
}
