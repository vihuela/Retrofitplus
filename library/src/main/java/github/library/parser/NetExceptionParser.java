package github.library.parser;

import android.text.TextUtils;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import github.library.utils.Cons;
import github.library.utils.Error;

class NetExceptionParser extends ExceptionParser {


    @Override
    protected boolean handler(Throwable e, IHandler handler) {
        if (e != null) {
            if (!TextUtils.isEmpty(e.getMessage())) {
                if (Cons.IO_EXCEPTION.equalsIgnoreCase(e.getMessage()) || Cons.SOCKET_EXCEPTION.equalsIgnoreCase(e.getMessage())) {
                    //cancel request trigger
                    return true;
                }
            }
            if (UnknownHostException.class.isAssignableFrom(e.getClass()) ||
                    SocketException.class.isAssignableFrom(e.getClass()) ||
                    SocketTimeoutException.class.isAssignableFrom(e.getClass())) {
                handler.onHandler(Error.NetWork, getMessageFromThrowable(Error.NetWork, e));
                return true;
            }
        }
        return false;
    }
}

