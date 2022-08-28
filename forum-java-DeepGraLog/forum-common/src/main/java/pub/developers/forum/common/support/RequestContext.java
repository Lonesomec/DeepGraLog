package pub.developers.forum.common.support;

import org.springframework.util.ObjectUtils;

/**
 * @author Qiangqiang.Bian
 * @create 2020/10/30
 * @desc
 **/
public class RequestContext {

    /**
     * trace id
     */
    private static final ThreadLocal<String> REQUEST_TRACE_ID = new ThreadLocal<>();

    /**
     * Generate the trade ID for the current request
     * @param
     */
    public static void init() {
        if (ObjectUtils.isEmpty(REQUEST_TRACE_ID.get())) {
            REQUEST_TRACE_ID.set(StringUtil.generateUUID());
        }
    }

    /**
     * Gets the current request trade ID
     * @return
     */
    public static String getTraceId() {
        return ObjectUtils.isEmpty(REQUEST_TRACE_ID.get()) ? "" : REQUEST_TRACE_ID.get();
    }

    public static void removeAll() {
        REQUEST_TRACE_ID.remove();
    }

}
