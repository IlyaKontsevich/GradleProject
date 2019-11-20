package com.internship.utils;

import com.internship.service.interfaces.IInfoService;
import com.internship.service.interfaces.IUserService;
import io.swagger.models.auth.In;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@Aspect
@Component
public class AspectForProject {
    private static final Logger logger = Logger.getLogger(AspectForProject.class);
    @Autowired
    IInfoService infoService;
    @Autowired
    IUserService userService;

    @Before("execution(public * com.internship.service.implementation.GenericService.getPage(..)))")
    public void beforeGetPage(JoinPoint jp) {
        logger.info("Get " + jp.getTarget().toString() + " page");
    }

    @Before("execution(public * com.internship.service.implementation.GenericService.get(..)))")
    public void beforeGetEntity(JoinPoint jp) {
        logger.info("Get " + jp.getTarget().toString());
    }

    @Before("execution(public * com.internship.service.implementation.GenericService.add(..)))")
    public void beforeAddEntity(JoinPoint jp) {
        logger.info("Add " + jp.getTarget().toString());
    }

    @Before("execution(public * com.internship.service.implementation.GenericService.delete(..)))")
    public void beforeDelete(JoinPoint jp) {
        logger.info("Delete " + jp.getTarget().toString());
    }


    @Pointcut("@annotation(Security) && args(id,..)")
    public void callAtMyServiceSecurityAnnotation(Integer id) {
    }

    @Around("callAtMyServiceSecurityAnnotation(id)")
    public Object aroundCallAtSecurity(ProceedingJoinPoint pjp, Integer id) throws Throwable {
        if(infoService.getCurrentUser().equals(userService.get(id)) ||
                infoService.getCurrentUser().getEmail().equals("admin@mail.ru"))
            return pjp.proceed();
        return "redirect:/accessDenied/";
    }
}
