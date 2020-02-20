package org.zee.app.zeemon.extensions.expression;

public class ExpressionContext<V, C> {
	protected V val;
	protected C ctx;
	
	public ExpressionContext(V value, C context) {
		this.val = value;
		this.ctx = context;
	}

	public V getVal() {
		return val;
	}

	public void setVal(V val) {
		this.val = val;
	}

	public C getCtx() {
		return ctx;
	}

	public void setCtx(C ctx) {
		this.ctx = ctx;
	}
	
	
}
