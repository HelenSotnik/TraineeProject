package com.task.trainee.service.util;

public class LogServiceMessageUtil {

    private static final String FAIL = "FAIL";
    private static final String SUCCESS = "SUCCESS";
    private static final String METHOD_NAME = "METHOD: ";
    private static final String IN = "IN";
    private static final String DOT = ".";
    private static final String SPACE = " ";
    private static final String SERVICE_NAME = "SERVICE: ";

    public static String getFailDebugMessage(String serviceName, String methodName) {
        StringBuilder sb = new StringBuilder();
        sb.append(FAIL).append(SPACE).append(IN).append(SPACE).append(METHOD_NAME)
                .append(methodName).append(SPACE).append(IN).append(SPACE)
                .append(SERVICE_NAME).append(serviceName).append(DOT);
        return sb.toString();
    }

    public static String getSuccessInfoMessage(String serviceName, String methodName) {
        StringBuilder sb = new StringBuilder();
        sb.append(SUCCESS).append(SPACE).append(IN).append(SPACE).append(METHOD_NAME)
                .append(methodName).append(SPACE).append(IN).append(SPACE)
                .append(SERVICE_NAME).append(serviceName).append(DOT);
        return sb.toString();
    }
}
