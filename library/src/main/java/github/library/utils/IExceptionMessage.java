package github.library.utils;


public interface IExceptionMessage {
    String onParseMessage(Error error, Throwable e);
}
