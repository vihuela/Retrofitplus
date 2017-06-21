package github.retrofitplus.model.bmob;

/**
 * @author ricky.yao on 2017/6/20.
 */

public class RegisterRequest {
    public static class Req {
        public String nick;
        public String password;
    }

    public static class Res {

        //String/ResultItem
        public String result;

        public static class ResultItem {
            public int code;
            public String error;
        }
    }
}
