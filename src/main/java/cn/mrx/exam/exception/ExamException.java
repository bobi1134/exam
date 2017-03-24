package cn.mrx.exam.exception;

/**
 * @ClassName: ExamException
 * @Author: Mr.X
 * @Date: 2017/3/24 15:27
 * @Description:
 * @Version 1.0
 */
public class ExamException extends Exception {

    public ExamException() {
        super();
    }

    public ExamException(String message) {
        super(message);
    }

    public ExamException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExamException(Throwable cause) {
        super(cause);
    }

    protected ExamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
