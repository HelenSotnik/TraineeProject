package com.task.trainee.loader.util;

public class LogJsonLoadService {

    private static final String IO_EXCEPTION = "IO EXCEPTION";
    private static final String SUCCESS = "SUCCESS";
    private static final String IN = "IN";
    private static final String DOT = ".";
    private static final String SPACE = " ";
    private static final String METHOD_NAME = "METHOD: ";

    public static String getIOExceptionMessage(String methodName) {
        StringBuilder sb = new StringBuilder();
        sb.append(IO_EXCEPTION).append(SPACE).append(IN).append(SPACE).append(methodName).append(DOT);
        return sb.toString();
    }

    public static String getInfoMessage(String methodName) {
        StringBuilder sb = new StringBuilder();
        sb.append(SUCCESS).append(SPACE).append(IN).append(SPACE).append(METHOD_NAME)
                .append(methodName).append(DOT);
        return sb.toString();
    }
}
