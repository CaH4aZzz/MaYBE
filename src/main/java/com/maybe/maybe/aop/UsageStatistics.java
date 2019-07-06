package com.maybe.maybe.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Configuration
public class UsageStatistics {
    private static Map<String, Long> methodsStatistics = new HashMap<>();

    public Map<String, Long> getMethodsStatistics() {
        return methodsStatistics;
    }

    public void setMethodsStatistics(Map<String, Long> methodsStatistics) {
        UsageStatistics.methodsStatistics = methodsStatistics;
    }

    @Before("@annotation(com.maybe.maybe.annotation.Statistic)")
    public void collectUsageStatistics(JoinPoint joinPoint) {
        Long methodCallsNumber = 1L;
        if (!methodsStatistics.containsKey(joinPoint.getSignature().getName())) {
            methodsStatistics.put(joinPoint.getSignature().getName(), methodCallsNumber);
        } else {
            methodCallsNumber = methodsStatistics.get(joinPoint.getSignature().getName()) + 1;
            methodsStatistics.put(joinPoint.getSignature().getName(), methodCallsNumber);
        }
    }
}
