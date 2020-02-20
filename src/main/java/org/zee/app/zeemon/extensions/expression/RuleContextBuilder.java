package org.zee.app.zeemon.extensions.expression;

public class RuleContextBuilder {
    private RuleContext ruleContext;

    private RuleContextBuilder(){
        super();
        this.ruleContext = new RuleContext();
    }
    
    private RuleContextBuilder(Object root){
        super();
        this.ruleContext = new RuleContext(root);
    }

    public RuleContextBuilder setVariable(String name, Object value){
        ruleContext.setVariable(name, value);
        return this;
    }

    public RuleContext build(){
        return ruleContext;
    }
    
    public static RuleContextBuilder instance() {
    	return new RuleContextBuilder();
    }
    
    public static RuleContextBuilder instance(Object root) {
    	return new RuleContextBuilder(root);
    }
}
