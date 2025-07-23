package org.rater.reviewapp.global.util;

import java.util.function.BiFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.function.Supplier;

public class ApiCallUtil {
    private static final Logger log = LoggerFactory.getLogger(ApiCallUtil.class);

    public static <T, E extends RuntimeException> T handleApiCall(Supplier<T> action, String errorMsg, BiFunction<String, Throwable, E> exceptionFactory) {
        try {
            return action.get();
        } catch (Exception e) {
            log.error(errorMsg, e);
            throw exceptionFactory.apply(errorMsg, e);
        }
    }
}