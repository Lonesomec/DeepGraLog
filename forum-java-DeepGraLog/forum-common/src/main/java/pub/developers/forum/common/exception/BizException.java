package pub.developers.forum.common.exception;

import pub.developers.forum.common.enums.ErrorCodeEn;
import lombok.Data;

/**
 * @author Qiangqiang.Bian
 * @create 20/7/23
 * @desc
 **/
@Data
public class BizException extends RuntimeException {

    private String message;
    private Integer code;

    /**
     * @desc  default system exception
     */
    public BizException() {
        this(ErrorCodeEn.SYSTEM_ERROR);
    }

    /**
     * @desc abnormal specified parameter service
     * @param errorCode Abnormal enumeration
     */
    public BizException(ErrorCodeEn errorCode) {
        this(errorCode.getCode(), errorCode.getMessage());
    }

    /**
     * @desc abnormal specified parameter service
     * @param code code
     * @param message message
     */
    public BizException(Integer code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

}