package org.zee.app.zeemon.extensions.expression;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.util.StringUtils;
import org.zee.app.zeemon.domain.EventTrigger;
import org.zee.app.zeemon.domain.enumeration.TimeUnit;
import org.zee.app.zeemon.domain.enumeration.TriggerType;

public class ZeemonUtils {

    public static RuleOutput out(Object value) {
        return new RuleOutput(value);
    }
    
    public static Date strToDate(String value) throws ParseException {
    	if (StringUtils.isEmpty(value)) {
    		return null;
    	}
    	return DateUtils.parseDateStrictly(value, Locale.getDefault(), "dd.MM.yyyy", "yyyy.MM.dd", "dd/MM/yyyy", "yyyy/MM/dd" , "dd.MM.yyyy HH:mm:ss", "yyyy.MM.dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss", "yyyy/MM/dd HH:mm:ss");
    }
    
    public static Object nvl(Object source, Object other) {
    	return null == source ? other : source;
    }
    
    public static Object evl(String source, String other) {
    	return StringUtils.isEmpty(source) ? other : source;
    }
    
    public static Instant now() {    	
    	return Instant.now();
    }
    
    public static Instant strToInstant(String value) throws ParseException {
    	Date d = strToDate(value);
    	return null == d ? null : d.toInstant();
    }
    
    public static Boolean strToBool(String value) {
		if (StringUtils.isEmpty(value)) {
			return Boolean.FALSE;
		}
		return "#true#yes#evet#1#yes#evet#".contains("#"+value.toLowerCase()+"#");
    }
    
    public static BigDecimal strToBigDecimal(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return new BigDecimal(value);
    }
    
    public static Double strToDouble(String value) throws ParseException {
    	BigDecimal bd = strToBigDecimal(value);
    	return null == bd ? null : bd.doubleValue();
    }
    
    public static Integer strToInt(String value) throws ParseException {
    	BigDecimal bd = strToBigDecimal(value);
    	return null == bd ? null : bd.intValue();
    }
    
    public static Long strToLong(String value) throws ParseException {
    	BigDecimal bd = strToBigDecimal(value);
    	return null == bd ? null : bd.longValue();
    }
    
    public static TimeZone parseTimeZone(String timeZoneId) {
    	if (StringUtils.isEmpty(timeZoneId)) {
    		return TimeZone.getDefault();
    	}
    	
    	TimeZone tz = TimeZone.getTimeZone(timeZoneId);
    	if (null == tz) {
    		tz = TimeZone.getDefault();
    	}
    	return tz;
    }
    
    public static Instant parseNextTime(EventTrigger eventTrigger, Instant refTime) {
    	
    	if (null == eventTrigger) {
    		return refTime;
    	}
		TriggerType triggerType = eventTrigger.getTriggerType();
		
		if (null == triggerType || TriggerType.EVENT == triggerType || TriggerType.MANUAL == triggerType) {
			return refTime;
		}
				

		Instant nextScheculeTime = refTime;
		String cronExpression = eventTrigger.getTriggerCronExpression();
		String timeZoneId = eventTrigger.getTriggerCronTimeZone();
		if (null != cronExpression) {
			cronExpression = cronExpression.trim();
		}
		
		if (TriggerType.CRON == triggerType) {
			if (!StringUtils.isEmpty(cronExpression)) {
				TimeZone timeZone = ZeemonUtils.parseTimeZone(timeZoneId);
				nextScheculeTime = ZeemonUtils.parseCronExpressionNextTime(cronExpression, timeZone);
			}
		} else {
			long period = eventTrigger.getTriggerPeriod();
			TimeUnit triggerTimeUnit = eventTrigger.getTriggerTimeUnit();
			nextScheculeTime = ZeemonUtils.parseNextTime(triggerTimeUnit, period);
			if (null == nextScheculeTime) {
				nextScheculeTime = refTime;
			}				
		}
		
		return nextScheculeTime;
    }
    
    public static Instant parseCronExpressionNextTime(String cronExpression, TimeZone timeZone) {
    	if (null == timeZone) {
    		timeZone = TimeZone.getDefault();
    	}
    	CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(cronExpression, timeZone);
    	
    	Instant now = Instant.now();
    	Date cd = Date.from(now);
    	
    	Date nd = cronSequenceGenerator.next(cd);
    	
    	return nd.toInstant();
    }

	public static Instant parseNextTime(TimeUnit triggerTimeUnit, long period) {
		
		if (null == triggerTimeUnit || period <= 0) {
			return null;
		}
		
		Instant now = Instant.now();
		Instant nextTime = null;
		
		if (TimeUnit.DAY == triggerTimeUnit) {
			nextTime = now.plus(period, ChronoUnit.DAYS);
		} else if (TimeUnit.HOUR == triggerTimeUnit) {
			nextTime = now.plus(period, ChronoUnit.HOURS);
		} else if (TimeUnit.MINUTE == triggerTimeUnit) {
			nextTime = now.plus(period, ChronoUnit.MINUTES);
		} else if (TimeUnit.SECOND == triggerTimeUnit) {
			nextTime = now.plus(period, ChronoUnit.SECONDS);
		}
		return nextTime;
		
	}
}
