package github.library.parser;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import github.library.utils.Cons;
import github.library.utils.Error;

class NetExceptionParser extends ExceptionParser {


    @Override
    protected boolean handler(Throwable e, IHandler handler) {
        if (e != null) {
            String s = getMessageFromThrowable(e);
            if (Cons.IO_EXCEPTION.equalsIgnoreCase(s) || Cons.SOCKET_EXCEPTION.equalsIgnoreCase(s)) {
                //cancel request trigger
                return true;
            }
            if (UnknownHostException.class.isAssignableFrom(e.getClass()) ||
                    SocketException.class.isAssignableFrom(e.getClass()) ||
                    SocketTimeoutException.class.isAssignableFrom(e.getClass())) {
                handler.onHandler(Error.NetWork, s);
                return true;
            }
        }
        return false;
    }
}

