@RequiredArgsConstructor
    @Aspect
    @Component
    public static class MyAspect {

        private static final Logger LOGGER = Logger.getLogger(MyAspect.class.getName());

        private static final ThreadLocal<String> codeThreadLocal = new ThreadLocal<>();

        private final ObjectMapper objectMapper;

        @Around("@annotation(logExecution)")
        public Object logExecutionAspect(final ProceedingJoinPoint joinPoint, final LogExecution logExecution) throws Throwable {
            final String message = logExecution.value();

            final Object[] args = joinPoint.getArgs();
            final String argsString = toString(args);

            codeThreadLocal.set(logExecution.code());

            try {
                LOGGER.log(Level.INFO, "EXECUTANDO {0}...: {1}", new Object[]{message, argsString});

                Object result = joinPoint.proceed();

                codeThreadLocal.remove();

                LOGGER.log(Level.INFO, "SUCESSO ao executar {0}!: {1}", new Object[]{message, argsString});

                return result;
            } catch (Exception exception) {
                Throwable rootCause = ExceptionUtils.getRootCause(exception);
                LOGGER.log(Level.SEVERE, "ERRO de {0} ao executar {1}.: {2}", new Object[]{rootCause.getClass().getSimpleName(), message, argsString});

                throw exception;
            }
        }

//        @Around("execution(* *..toJson*(..))")
        @Around("execution(* br.com.sylviomartins..*toJson*(..))")
        public Object toJsonAspect(ProceedingJoinPoint joinPoint) throws Throwable {
            final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            final Class<?> inputType = getInputType(methodSignature);
            final Class<?> outputType = getOutputType(methodSignature);

            final String message = buildConversionMessage(inputType, outputType);

            try {
                Object result = joinPoint.proceed();

                final String codeFromLogExecution = codeThreadLocal.get();
                System.out.println(codeFromLogExecution);

                LOGGER.log(Level.INFO, message, result);

                return result;

            } catch (Exception exception) {
                throw exception;
            }
        }

        private Class<?> getInputType(MethodSignature methodSignature) {
            return methodSignature.getParameterTypes().length > 0 ? methodSignature.getParameterTypes()[0] : null;
        }

        private Class<?> getOutputType(MethodSignature methodSignature) {
            return methodSignature.getReturnType() != void.class ? methodSignature.getReturnType() : null;
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
                return "null";
            }
        }

    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface LogExecution {

        String code() default "";

        String value() default "";

    }
