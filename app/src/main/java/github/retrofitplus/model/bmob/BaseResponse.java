package github.retrofitplus.model.bmob;

import java.util.List;


public class BaseResponse<T> {
    public int code;
    public List<T> results;
}
