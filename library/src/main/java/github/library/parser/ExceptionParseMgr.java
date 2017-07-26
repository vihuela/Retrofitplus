package github.library.parser;

import java.util.ArrayList;
import java.util.List;

import github.library.utils.Error;
import github.library.utils.ExceptionMatcher;
import io.reactivex.exceptions.CompositeException;

public enum ExceptionParseMgr {
    Instance;

    private List<ExceptionParser> parsers = new ArrayList<>();
    private boolean isOnlyOneCallback = true;

    ExceptionParseMgr() {

        resetParses();
        connectionParse();
    }

    private void resetParses() {
        parsers.clear();
        parsers.add(new NetExceptionParser());
        parsers.add(new ServerExceptionParser());
        parsers.add(new InternalExceptionParser());
        parsers.add(new UnknowExceptionParser());//last item must be UnknowExceptionParser
    }

    private void connectionParse() {
        for (int i = 0; i < parsers.size(); i++) {

            final ExceptionParser item = parsers.get(i);

            if (i + 1 < parsers.size()) {
                item.setNextParser(parsers.get(i + 1));
            }
        }
    }

    public void parseException(Throwable e, ExceptionParser.IHandler iHandler) {
        //rx multiple exception
        if (e != null && CompositeException.class.isAssignableFrom(e.getClass())) {
            List<Throwable> tEExceptions = ((CompositeException) e).getExceptions();
            if (tEExceptions != null) {
                for (Throwable t : tEExceptions) {
                    parsers.get(0).handleException(t, iHandler);
                    if (isOnlyOneCallback) break;
                }
            }
        } else {
            //only one
            parsers.get(0).handleException(e, iHandler);
        }
    }

    //Rx CompositeException 下仅回调首个错误
    public void isOnlyOneCallback(boolean isOnlyOneCallback) {
        this.isOnlyOneCallback = isOnlyOneCallback;
    }

    //添加自定义异常解析
    public void addCustomerParser(ExceptionParser parser) {

        if (parser != null) {
            parsers.add(0, parser);//customer exception priority
            connectionParse();
        }
    }

    //单独提供外部使用的解析方法
    public Error isMatchException(Throwable e) {
        return ExceptionMatcher.isMatchException(e);
    }

}
