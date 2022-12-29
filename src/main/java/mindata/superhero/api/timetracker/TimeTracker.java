package mindata.superhero.api.timetracker;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeTracker {

    Logger logger = LoggerFactory.getLogger(TimeTracker.class);

    @Around("@annotation(mindata.superhero.api.timetracker.LogTimeTaken)")
    public Object logTotalTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        var startTime = System.currentTimeMillis();
        var obj = proceedingJoinPoint.proceed();
        var endTime = System.currentTimeMillis();
        var timeTaken = endTime-startTime;
        logger.info("Request for method {} took {} ms to execute", proceedingJoinPoint.getSignature().getName(), timeTaken);
        return obj;
    }
}
