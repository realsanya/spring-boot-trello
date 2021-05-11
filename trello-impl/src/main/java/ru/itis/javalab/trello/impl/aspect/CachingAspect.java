package ru.itis.javalab.trello.impl.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class CachingAspect {
    private Map<String, Object> cache;

    public CachingAspect() {
        cache = new HashMap<String, Object>();
    }

    public Map<String, Object> getCache() {
        return cache;
    }

    @Pointcut("within(ru.itis.javalab.trello.impl.services.UserServiceImpl)")
    private void cache() {
    }

    @Before("cache()")
    public void cacheMethodBefore(JoinPoint joinPoint) {
        log.info("Cashing before");
    }

    @Around("@annotation(Caching)")
    public Object aroundCachedMethods(ProceedingJoinPoint thisJoinPoint)
            throws Throwable {
        log.debug("Выполнение кеширования");
        // генерация ключа под которым хранится кешированное значение
        StringBuilder keyBuff = new StringBuilder();
        keyBuff.append(thisJoinPoint.getTarget().getClass().getName());
        keyBuff.append(".").append(thisJoinPoint.getSignature().getName());
        keyBuff.append("(");
        for (final Object arg : thisJoinPoint.getArgs()) {
            keyBuff.append(arg.getClass().getSimpleName() + "=" + arg + ";");
        }
        keyBuff.append(")");
        String key = keyBuff.toString();
        log.debug("Ключ = " + key);
        Object result = cache.get(key);
        if (result == null) {
            result = thisJoinPoint.proceed();
            log.info("Сохранение вычисленного значения в кеш " + result);
            cache.put(key, result);
        } else {
            log.debug("Результат найден в кеше: " + result);
        }
        return result;
    }
}
