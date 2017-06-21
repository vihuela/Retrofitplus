package github.retrofitplus.model.bmob;


//RxCache缓存没有保存泛型，所以Response为显性类型
public class FocusListResponse extends BaseResponse<FocusListResponse.FocusListItem> {

    public static class FocusListItem {
        public String audioImage;
        public String audioUrl;
        public String createdAt;
        public String name;
        public String objectId;
        public String updatedAt;
    }
}
