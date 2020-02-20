package org.zee.app.zeemon.extensions.expression;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RuleEngine {
	
	private final Logger log = LoggerFactory.getLogger(RuleEngine.class);
	private SpelParserConfiguration config;
	
	public RuleEngine() {
		this.config = new SpelParserConfiguration(true, true);
	}
	
	public static void main(String[] args) {
		RuleEngine engine = new RuleEngine();
		engine.testRuleEngine();
	}
	
	private void testRuleEngine() {
		String expression = "#out((val + 2 + ctx)* #zee)";
		
		expression = "#out(#nvl(#strToInstant(val), #now()))";
		
		
		ExpressionContext<String, Integer> expressionContext = new ExpressionContext<String, Integer>("12.03.2019 21:34:42", 5);
		RuleContext ctx = RuleContextBuilder.instance(expressionContext).setVariable("zee", 3).build();
		
		RuleOutput output = this.parse(expression, ctx);
		log.debug("RuleEngine.result {}", output);
		
		
		expressionContext = new ExpressionContext<String, Integer>("", 5);
		ctx = RuleContextBuilder.instance(expressionContext).setVariable("zee", 3).build();
		output = this.parse(expression, ctx);
		log.debug("RuleEngine.result {}", output);
	}
	
	public RuleOutput parse(String expression, RuleContext ctx) {

        ExpressionParser expressionParser = new SpelExpressionParser(this.config);

	        try {
	        	this.extendRuleContext(ctx);
	            RuleOutput ruleOutput = expressionParser.parseExpression(expression).getValue(ctx, RuleOutput.class);

	            if (ruleOutput == null || ruleOutput.getValue() == null ) {
	                throw new RuleException("Rule Engine; Rule is not valid, Rule Expression: [" + expression + "]");
	            }

	            /*
	            if (!this.isRuleResultSuitable(ruleType, ruleOutput.getValue())) {
	                throw new RuleException("Rule Engine; Rule Result is not suitable for Rule Type [" + ruleType + "], Rule Expression: [" + ruleExpression + "]");
	            }
	            */

	            return ruleOutput;
	        } catch (ParseException | IllegalStateException e) {
	            throw new RuleException(e);
	        } catch (NoSuchMethodException | SecurityException e) {
	        	throw new RuleException("Rule Engine => Error while registering Evaluation Context functions.", e);
	        }
	        
	       // return null;
	}
	
	private void extendRuleContext(RuleContext ctx) throws NoSuchMethodException, SecurityException {
	        if (ctx != null) {
	        	ctx.registerFunction("out", ZeemonUtils.class.getDeclaredMethod("out", Object.class));
	        	ctx.registerFunction("nvl", ZeemonUtils.class.getDeclaredMethod("nvl", Object.class, Object.class));
	        	ctx.registerFunction("evl", ZeemonUtils.class.getDeclaredMethod("evl", String.class, String.class));
	        	ctx.registerFunction("now", ZeemonUtils.class.getDeclaredMethod("now"));
	        	ctx.registerFunction("strToDate", ZeemonUtils.class.getDeclaredMethod("strToDate", String.class));
	        	ctx.registerFunction("strToInstant", ZeemonUtils.class.getDeclaredMethod("strToInstant", String.class));
	        	ctx.registerFunction("strToBool", ZeemonUtils.class.getDeclaredMethod("strToBool", String.class));
	        	ctx.registerFunction("strToBigDecimal", ZeemonUtils.class.getDeclaredMethod("strToBigDecimal", String.class));
	        	ctx.registerFunction("strToDouble", ZeemonUtils.class.getDeclaredMethod("strToDouble", String.class));
	        	ctx.registerFunction("strToInt", ZeemonUtils.class.getDeclaredMethod("strToInt", String.class));
	        	ctx.registerFunction("strToLong", ZeemonUtils.class.getDeclaredMethod("strToLong", String.class));
	        	
	        	/*
	        	ctx.registerFunction("outValue", RuleEngineImpl.class.getDeclaredMethod("outValue", RuleResult.class, String.class, RuleResultValueType.class));
	        	ctx.registerFunction("executionCountOf", RuleUtils.class.getDeclaredMethod("executionCountOf", Collection.class, ExecutionState.class));
	        	ctx.registerFunction("executionReasonCountOf", RuleUtils.class.getDeclaredMethod("executionReasonCountOf", Collection.class, ExecutionStateReason.class));
	        	ctx.registerFunction("executionCountOfInLastXMinutes", RuleUtils.class.getDeclaredMethod("executionCountOfInLastXMinutes", Collection.class, ExecutionState.class, Long.class));
	        	ctx.registerFunction("executionCountOfInLastXHours", RuleUtils.class.getDeclaredMethod("executionCountOfInLastXHours", Collection.class, ExecutionState.class, Long.class));
	        	ctx.registerFunction("executionCountOfInLastXDays", RuleUtils.class.getDeclaredMethod("executionCountOfInLastXDays", Collection.class, ExecutionState.class, Long.class));
	        	ctx.registerFunction("executionReasonCountOfInLastXMinutes", RuleUtils.class.getDeclaredMethod("executionReasonCountOfInLastXMinutes", Collection.class, ExecutionStateReason.class, Long.class));
	        	ctx.registerFunction("executionReasonCountOfInLastXHours", RuleUtils.class.getDeclaredMethod("executionReasonCountOfInLastXHours", Collection.class, ExecutionStateReason.class, Long.class));
	        	ctx.registerFunction("executionReasonCountOfInLastXDays", RuleUtils.class.getDeclaredMethod("executionReasonCountOfInLastXDays", Collection.class, ExecutionStateReason.class, Long.class));
	        	ctx.registerFunction("isExecutionHourIn24HourTimeZone", RuleUtils.class.getDeclaredMethod("isExecutionHourIn24HourTimeZone", Execution.class, ExecutionState.class));
	        	ctx.registerFunction("isContactInfoTypeValueOf", RuleUtils.class.getDeclaredMethod("isContactInfoTypeValueOf", CustomerContactInfo.class, ContactInfoType.class));
	        	ctx.registerFunction("areAllQuestionsAnswered", RuleUtils.class.getDeclaredMethod("areAllQuestionsAnswered", Collection.class, Collection.class));
	        	ctx.registerFunction("isReasonOfInprogressExecution", RuleUtils.class.getDeclaredMethod("isReasonOfInprogressExecution", ExecutionStateReason.class, ExecutionStateReason.class));
	        	ctx.registerFunction("activitySuccessCount", RuleUtils.class.getDeclaredMethod("activitySuccessCount", Collection.class, String.class, String.class));
	        	ctx.registerFunction("activityUnsuccessCount", RuleUtils.class.getDeclaredMethod("activityUnsuccessCount", Collection.class, String.class, String.class));
	        	ctx.registerFunction("activitySuccessRate", RuleUtils.class.getDeclaredMethod("activitySuccessRate", Collection.class, String.class, String.class));
	        	ctx.registerFunction("activityUnsuccessRate", RuleUtils.class.getDeclaredMethod("activityUnsuccessRate", Collection.class, String.class, String.class));
	        	ctx.registerFunction("activityIntouchRate", RuleUtils.class.getDeclaredMethod("activityIntouchRate", Collection.class, String.class, String.class));
	        	ctx.registerFunction("activityIntouchCount", RuleUtils.class.getDeclaredMethod("activityIntouchCount", Collection.class, String.class, String.class));
	        	ctx.registerFunction("activityNotreachedCount", RuleUtils.class.getDeclaredMethod("activityNotreachedCount", Collection.class, String.class, String.class));
	        	ctx.registerFunction("activityNotreachedRate", RuleUtils.class.getDeclaredMethod("activityNotreachedRate", Collection.class, String.class, String.class));
	        	ctx.registerFunction("isValid", RuleUtils.class.getDeclaredMethod("isValid", String.class));
	        	ctx.registerFunction("regexMatch", RuleUtils.class.getDeclaredMethod("regexMatch", String.class, String.class));
	        	ctx.registerFunction("regexTransform", RuleUtils.class.getDeclaredMethod("regexTransform", String.class, String.class, String.class));
	        	ctx.registerFunction("optimizeInputPrefix", RuleUtils.class.getDeclaredMethod("optimizeInputPrefix", String.class, String.class));
	        	ctx.registerFunction("isTimeBeforeDays", RuleUtils.class.getDeclaredMethod("isTimeBeforeDays", ZonedDateTime.class, Long.class));
	        	ctx.registerFunction("isTimeBeforeHours", RuleUtils.class.getDeclaredMethod("isTimeBeforeHours", ZonedDateTime.class, Long.class));
	        	ctx.registerFunction("isTimeBeforeMinutes", RuleUtils.class.getDeclaredMethod("isTimeBeforeMinutes", ZonedDateTime.class, Long.class));
	        	ctx.registerFunction("isTimeBeforeSeconds", RuleUtils.class.getDeclaredMethod("isTimeBeforeSeconds", ZonedDateTime.class, Long.class));

	        	ctx.registerFunction("currentDate", RuleUtils.class.getDeclaredMethod("currentDate"));
	        	ctx.registerFunction("currentTime", RuleUtils.class.getDeclaredMethod("currentTime"));
	        	ctx.registerFunction("isCurrentTimeAfter", RuleUtils.class.getDeclaredMethod("isCurrentTimeAfter", Integer.class, Integer.class));
	        	ctx.registerFunction("isCurrentTimeBefore", RuleUtils.class.getDeclaredMethod("isCurrentTimeBefore", Integer.class, Integer.class));
	        	ctx.registerFunction("isWorkingDay", RuleUtils.class.getDeclaredMethod("isWorkingDay", DayOfWeek[].class));
	        	*/
	        }
	}

}
