package github.library.parser;

import github.library.utils.Error;

class UnknowExceptionParser extends ExceptionParser {

    /**
     * must return true
     */
    @Override
    protected boolean handler(Throwable e, IHandler handler) {
        handler.onHandler(Error.UnKnow, getMessageFromThrowable(e));
        return true;
    }
}
