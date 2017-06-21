package github.retrofitplus.api;

import github.retrofitplus.model.ImageListRequest;
import github.retrofitplus.model.NewsDetailRequest;
import github.retrofitplus.model.NewsRequest;
import github.retrofitplus.model.bmob.FocusListResponse;
import github.retrofitplus.model.bmob.RegisterRequest;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {

    @GET("福利/{size}/{page}")
    Observable<ImageListRequest.Res> getImageList(@Path("size") int size, @Path("page") int page);

    @GET("http://news-at.zhihu.com/api/4/news/latest")
    Observable<NewsRequest.Res> getNewForLast();

    @GET("http://news-at.zhihu.com/api/4/news/{id}")
    Observable<NewsDetailRequest.Res> getStoryDetail(@Path("id") int id);

    @GET("http://cloudweixinopen.bmob.cn/40fe9fd8dffd9d53/Test_FocuList")
    Observable<FocusListResponse> getFocusList();

    @POST("https://api.bmob.cn/1/functions/Test_Register")
    @Headers({
            "X-Bmob-Application-Id:8f264d00e7c1e8a18eece259f99cebaa",
            "X-Bmob-REST-API-Key:9fd3090e2d0d9d962b63630376ee5441",
            "Content-Type:application/json"
    })
    Observable<RegisterRequest.Res> register(@Body RegisterRequest.Req req);


}
