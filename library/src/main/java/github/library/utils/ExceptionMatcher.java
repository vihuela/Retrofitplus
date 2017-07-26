package github.library.utils;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import io.reactivex.exceptions.CompositeException;
import retrofit2.HttpException;

public class ExceptionMatcher {

    public static Error isMatchException(Throwable e) {
        if (e != null) {
            //mul Exception
            if (CompositeException.class.isAssignableFrom(e.getClass())) {
                List<Throwable> tEExceptions = ((CompositeException) e).getExceptions();
                if (tEExceptions != null && tEExceptions.size() > 0) {
                    e = tEExceptions.get(0);
                }
            }
            if (UnknownHostException.class.isAssignableFrom(e.getClass()) ||
                    SocketException.class.isAssignableFrom(e.getClass()) ||
                    SocketTimeoutException.class.isAssignableFrom(e.getClass())) {
                return Error.NetWork;
            } else if (HttpException.class.isAssignableFrom(e.getClass())) {
                return Error.Server;
            } else if (
                    NumberFormatException.class.isAssignableFrom(e.getClass()) ||
                            JsonParseException.class.isAssignableFrom(e.getClass()) ||
                            JsonSyntaxException.class.isAssignableFrom(e.getClass()) ||
                            JSONException.class.isAssignableFrom(e.getClass())) {
                return Error.Internal;
            } else {
                return Error.UnKnow;
            }
        }
        return Error.UnKnow;
    }
}
