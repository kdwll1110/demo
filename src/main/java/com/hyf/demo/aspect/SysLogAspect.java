package com.hyf.demo.aspect;

import com.hyf.demo.annotation.OperationType;
import com.hyf.demo.entity.SysLog;
import com.hyf.demo.enums.OperationTypeEnum;
import com.hyf.demo.service.ISysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class SysLogAspect {

    @Resource
    private ISysLogService ISysLogService;
    Long startTime;
    Long endTime;

    /**
     * 1、创建切点
     */
    @Pointcut("@annotation(com.hyf.demo.annotation.OperationType)")
    public void logPointcut(){}

    /**
     * 2、前置通知
     */
    @Before("logPointcut()")
    public void before(){
        startTime = System.currentTimeMillis();
    }

    /**
     * 后置通知
     * @param joinPoint
     */
    @After("logPointcut()")
    public void after(JoinPoint joinPoint){
        //获得当前被代理的类
        Class<?> targetClass = joinPoint.getTarget().getClass();

        //被代理对象的当前方法名
        String currentMethodName = joinPoint.getSignature().getName();

        //获得被代理对象方法的参数列表
        Object[] args = joinPoint.getArgs();

        //获得被代理类的所有方法
        Method[] methods = targetClass.getMethods();
        for (Method method : methods) {
            //找到当前代理对象的方法
            if (method.getName().equals(currentMethodName)){
                //获取方法参数列表
                int parameterCount = method.getParameterCount();
                //判断是否存在注解标识&&参数列表长度是否相等
                if (parameterCount==args.length){
                    //获得request对象
                    HttpServletRequest request = getHttpServletRequest();
                    //获得session对象
                    HttpSession session = request.getSession();
                    //当前用户，暂时先存个sessionid算了（在前后端分离模式下，此处的用户应该是从token中解析出来的用户名）
                    String user = session.getId().toString();
                    //请求路径
                    String relativePath = request.getServletPath();
                    //请求方法
                    String methodName = method.getName();
                    //请求参数
                    Map<String, Object> params = encapsulationParams(joinPoint);
                    //请求ip
                    String ip = request.getRemoteAddr();
                    //执行的响应时间
                    endTime = System.currentTimeMillis();
                    long logResponseTime = endTime - startTime;
                    //业务功能描述
                    OperationTypeEnum action = method.getAnnotation(OperationType.class).action();
                    //封装成log对象
                    SysLog sysLog = new SysLog(
                            null,
                            user,
                            relativePath,
                            methodName,
                            params.toString(),
                            logResponseTime+"ms",
                            ip,
                            null,
                            action.getInfo());
                    //新增日志记录
                    ISysLogService.save(sysLog);

                }
            }
        }
    }

    /**
     * @Description: 获取request
     */
    public HttpServletRequest getHttpServletRequest(){
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes)ra;
        HttpServletRequest request = sra.getRequest();
        return request;
    }

    /**
     * 封装请求参数
     */
    public Map<String,Object> encapsulationParams(JoinPoint joinPoint){
        Map<String,Object> map = new HashMap<>();
        //获得方法的参数名列表
        String[] parameterNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        //获得方法的参数值列表
        Object[] argsValue = joinPoint.getArgs();
        //遍历后封装到map中
        for (int i = 0; i < parameterNames.length; i++) {
            map.put(parameterNames[i],argsValue[i]);
        }
        return map;
    }



}
