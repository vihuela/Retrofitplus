package github.retrofitplus.model.bmob;


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
