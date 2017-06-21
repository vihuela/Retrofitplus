package github.library.parser;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import github.library.utils.Error;

/**
 * only json error check
 */
class InternalExceptionParser extends ExceptionParser {


    @Override
    protected boolean handler(Throwable e, IHandler handler) {
        if (e != null) {
            if (
                    NumberFormatException.class.isAssignableFrom(e.getClass()) ||
                    JsonParseException.class.isAssignableFrom(e.getClass()) ||
                    JsonSyntaxException.class.isAssignableFrom(e.getClass()) ||
                    JSONException.class.isAssignableFrom(e.getClass())) {
                handler.onHandler(Error.Internal, getMessageFromThrowable(e));
                return true;
            }
        }
        return false;
    }
}
