package github.library.parser;


import android.text.TextUtils;

import github.library.utils.Error;

public abstract class ExceptionParser {
    private ExceptionParser nextParser;

    /**
     * @param e       nullable
     * @param handler not be null
     */
    final void handleException(Throwable e, IHandler handler) {
        //every time check [e] or [e.getCause()]
        if (handler(e, handler) || handler(e != null ? e.getCause() : null, handler)) {
            return;
        }
        next(e, handler);
    }

    private void next(Throwable e, IHandler handler) {
        if (getNextParser() != null) getNextParser().handleException(e, handler);
    }

    /**
     * @return true is resume error
     */
    protected abstract boolean handler(Throwable e, IHandler handler);

    private ExceptionParser getNextParser() {
        return nextParser;
    }

    final void setNextParser(ExceptionParser nextParser) {
        this.nextParser = nextParser;
    }

    public interface IHandler {
        void onHandler(Error error, String message);
    }

    final String getMessageFromThrowable(Error error, Throwable e) {
        if (ExceptionParseMgr.Instance.iExceptionMessage != null) {
            return ExceptionParseMgr.Instance.iExceptionMessage.onParseMessage(error, e);
        } else {
            return getDefaultMessage(e);
        }
    }

    public static String getDefaultMessage(Throwable e) {
        String message = "exception message is empty";
        if (e != null)
            message = e.getClass().getSimpleName() + (!TextUtils.isEmpty(e.getMessage()) ? "：" + e.getMessage() : "");
        return message;
    }

}
