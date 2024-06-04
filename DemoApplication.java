package br.com.sylviomartins.spring.aop.demo.aspect.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
@ComponentScan(basePackages = "br.com.sylviomartins.spring.aop.demo.aspect.test")
public class DemoApplication {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "test");

        SpringApplication.run(DemoApplication.class, args);
    }

    @RestController
    public static class MyController {

        private final MyService myService;

        public MyController(MyService myService) {
            this.myService = myService;
        }

        @GetMapping("/person/{id}")
        public String getPerson(@PathVariable int id) {
            return myService.create(new Person(id, "Mocked", "Person", 30));
        }

    }

    @Configuration
    @EnableAspectJAutoProxy(proxyTargetClass = true)
    public class AspectConfiguration {

    }

    @RequiredArgsConstructor
    @Component
    public static class MyService {

        private final ObjectMapper objectMapper;

        public String create(Person person) {
            return toJson(person);
        }

        @LogExecution("a criacao de uma pessoa")
        public String toJson(Person person) {
            try {
                return objectMapper.writeValueAsString(person);
            } catch (JsonProcessingException e) {
                return null;
            }
        }

    }

    @RequiredArgsConstructor
    @Aspect
    @Component
    public static class MyAspect {

        private static final Logger LOGGER = Logger.getLogger(MyAspect.class.getName());

        private final ObjectMapper objectMapper;

        @Around("@annotation(logExecution)")
        public Object logExecutionAspect(final ProceedingJoinPoint joinPoint, final LogExecution logExecution) throws Throwable {
            final String message = logExecution.value();

            final Object[] args = joinPoint.getArgs();
            final String argsString = toString(args);

            try {
                LOGGER.log(Level.INFO, "EXECUTANDO {0}...: {1}", new Object[]{message, argsString});

                Object result = joinPoint.proceed();

                LOGGER.log(Level.INFO, "SUCESSO ao executar {0}!: {1}", new Object[]{message, argsString});

                return result;
            } catch (Exception exception) {
                Throwable rootCause = ExceptionUtils.getRootCause(exception);
                LOGGER.log(Level.SEVERE, "ERRO de {0} ao executar {1}.: {2}", new Object[]{rootCause.getClass().getSimpleName(), message, argsString});

                throw exception;
            }
        }

        @Around("execution(* *..toJson*(..))")
        public Object xptoAspect(ProceedingJoinPoint joinPoint) throws Throwable {
            final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            final Class<?> inputType = getInputType(methodSignature);
            final Class<?> outputType = getOutputType(methodSignature);

            String message = buildConversionMessage(inputType, outputType);

            try {
                Object result = joinPoint.proceed();

                LOGGER.log(Level.INFO, message, result);

                return result;

            } catch (Exception exception) {
                return null;
            }
        }

        private Class<?> getInputType(MethodSignature methodSignature) {
            return methodSignature.getParameterTypes().length > 0 ?
                    methodSignature.getParameterTypes()[0] : null;
        }

        private Class<?> getOutputType(MethodSignature methodSignature) {
            return methodSignature.getReturnType() != void.class ?
                    methodSignature.getReturnType() : null;
        }

        private String buildConversionMessage(Class<?> inputType, Class<?> outputType) {
            String inputTypeName = inputType != null ? inputType.getSimpleName() : "Unknown";
            String outputTypeName = outputType != null ? outputType.getSimpleName() : "Unknown";

            return inputTypeName.toUpperCase() + " convertido para " + outputTypeName.toUpperCase();
        }

        private String toString(final Object[] args) {
            try {
                if (args.length == 0) {
                    return "N/A";
                }

                return objectMapper.writeValueAsString(args);

            } catch (JsonProcessingException e) {
                return null;
            }
        }

    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface LogExecution {

        String code() default "";

        String value() default "";

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Person {

        private int id;

        private String firstName;

        private String lastName;

        private int age;

    }

}
