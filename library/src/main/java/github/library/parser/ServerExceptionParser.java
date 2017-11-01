package github.library.parser;

import github.library.utils.Error;
import retrofit2.HttpException;

//only use retrofit2
class ServerExceptionParser extends ExceptionParser {

    @Override
    protected boolean handler(Throwable e, IHandler handler) {
        if (e != null) {
            if (HttpException.class.isAssignableFrom(e.getClass())) {
                handler.onHandler(Error.Server, getMessageFromThrowable(Error.Server, e));
                return true;
            }
        }

        return false;
    }
}
