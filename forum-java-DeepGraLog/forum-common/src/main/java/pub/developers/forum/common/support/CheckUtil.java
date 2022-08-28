package pub.developers.forum.common.support;

import pub.developers.forum.common.enums.ErrorCodeEn;
import pub.developers.forum.common.exception.BizException;
import org.springframework.util.ObjectUtils;

import java.text.MessageFormat;

/**
 * @author Qiangqiang.Bian
 * @create 20/7/23
 * @desc
 **/
public class CheckUtil {

    private CheckUtil() {
    }

    /**
     * Check whether the request path parameter is empty
     *
     * @param o
     */
    public static void checkParamToast(Object o, String message) {
        if (ObjectUtils.isEmpty(o)) {
            throw new BizException(ErrorCodeEn.PARAM_CHECK_ERROR.getCode(),
                    MessageFormat.format(ErrorCodeEn.PARAM_CHECK_ERROR.getMessage(), message));
        }
    }

    /**
     * Check whether the request path parameter is empty
     *
     * @param o
     */
    public static void checkEmptyToast(Object o, String message) {
        if (ObjectUtils.isEmpty(o)) {
            throw new BizException(ErrorCodeEn.CHECK_ERROR_TOAST.getCode(),
                    MessageFormat.format(ErrorCodeEn.CHECK_ERROR_TOAST.getMessage(), message));
        }
    }

    /**
     * @param o         examined object
     * @param errorCode exception
     * @desc Check whether it is empty. If it is empty, an exception is reported
     */
    public static void isEmpty(Object o, ErrorCodeEn errorCode) {
        if (ObjectUtils.isEmpty(o)) {
            throw new BizException(errorCode);
        }//normal
//        if (!ObjectUtils.isEmpty(o)) {
//            throw new BizException(errorCode);
//        }//condition change
    }

    public static void isNotEmpty(Object o, ErrorCodeEn errorCode) {
        if (!ObjectUtils.isEmpty(o)) {
            throw new BizException(errorCode);
        }
    }

    public static void isFalse(Boolean b, ErrorCodeEn errorCode) {
        if (!b) {
            throw new BizException(errorCode);
        }
    }

    public static void isTrue(Boolean b, ErrorCodeEn errorCode) {
        if (b) {
            throw new BizException(errorCode);
        }
    }

}