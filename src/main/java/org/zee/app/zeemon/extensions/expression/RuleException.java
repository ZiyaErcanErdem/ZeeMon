package org.zee.app.zeemon.extensions.expression;

public class RuleException extends RuntimeException{

	   public RuleException(String message){
	        super(message);
	    }

	    public RuleException(String message, Throwable throwable){
	        super(message, throwable);
	    }

	    public RuleException(Throwable cause) {
	        super(cause);
	    }
}
