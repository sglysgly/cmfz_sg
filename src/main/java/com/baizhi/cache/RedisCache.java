package com.baizhi.cache;

import com.baizhi.annotation.AddRedisCache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.util.Set;

@Configuration
@Aspect
public class RedisCache {
    @Autowired
    private RedisTemplate redisTemplate;

    @Around("execution(* com.baizhi.service.*Impl.query*(..))")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //解决缓存乱码
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        //方法签名
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        boolean annotationPresent = method.isAnnotationPresent(AddRedisCache.class);
        if (annotationPresent) {
            StringBuilder stringBuilder = new StringBuilder();
            //设计一个Key（类名+方法名+实参）
            //获取全限命名
            String name = proceedingJoinPoint.getTarget().getClass().getName();
            stringBuilder.append(name);
            //返回方法的参数
            Object[] args = proceedingJoinPoint.getArgs();
            for (Object arg : args) {
                stringBuilder.append(arg);
            }
            //获取Key
            String key = stringBuilder.toString();
            //String类型的操作
            ValueOperations valueOperations = redisTemplate.opsForValue();
            Boolean aBoolean = redisTemplate.hasKey(key);
            Object result = null;
            if (aBoolean) {
                result = valueOperations.get(key);
            } else {
                result = proceedingJoinPoint.proceed();
                valueOperations.set(key, result);
            }
            return result;
        } else {
            //如果没有注解 直接放行
            Object proceed = proceedingJoinPoint.proceed();
            return proceed;

        }
    }

    @After("execution(* com.baizhi.service.*.*(..)) && !execution(* com.baizhi.service.*Impl.query*(..))")
    public void after(JoinPoint joinPoint) {
        //类的全限定名
        String name = joinPoint.getTarget().getClass().getName();
        //获取所有的key
        Set<String> keys = redisTemplate.keys("*");
        keys.forEach(key -> {
            if (key.startsWith(name)) {
                //清清除
                redisTemplate.delete(key);
            }
        });
    }
}

















