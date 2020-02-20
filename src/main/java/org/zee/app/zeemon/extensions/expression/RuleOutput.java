package org.zee.app.zeemon.extensions.expression;

public class RuleOutput {
	private Object value;
	
	public RuleOutput(Object out) {
		this.value = out;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "RuleOutput [value=" + value + "]";
	}
	
	
}
