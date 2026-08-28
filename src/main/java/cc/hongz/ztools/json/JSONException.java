package cc.hongz.ztools.json;

import cc.hongz.ztools.core.exceptions.ExceptionUtil;
import cc.hongz.ztools.core.util.StrUtil;

/**
 * JSON异常。
 *
 * @author looly
 * @since 3.0.2
 */
public class JSONException extends RuntimeException {
    private static final long serialVersionUID = 0;

    /**
     * 构造。
     *
     * @param e 异常
     */
    public JSONException(final Throwable e) {
        super(ExceptionUtil.getMessage(e), e);
    }

    /**
     * 构造。
     *
     * @param message 错误消息
     */
    public JSONException(final String message) {
        super(message);
    }

    /**
     * 构造。
     *
     * @param messageTemplate 消息模板
     * @param params 参数
     */
    public JSONException(final String messageTemplate, final Object... params) {
        super(StrUtil.format(messageTemplate, params));
    }

    /**
     * 构造。
     *
     * @param message 错误消息
     * @param cause 异常
     */
    public JSONException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造。
     *
     * @param message 错误消息
     * @param throwable 异常
     * @param enableSuppression 是否启用抑制
     * @param writableStackTrace 是否可写堆栈
     */
    public JSONException(final String message, final Throwable throwable, final boolean enableSuppression,
            final boolean writableStackTrace) {
        super(message, throwable, enableSuppression, writableStackTrace);
    }

    /**
     * 构造。
     *
     * @param throwable 异常
     * @param messageTemplate 消息模板
     * @param params 参数
     */
    public JSONException(final Throwable throwable, final String messageTemplate, final Object... params) {
        super(StrUtil.format(messageTemplate, params), throwable);
    }
}
