package github.retrofitplus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;

import github.library.parser.ExceptionParseMgr;
import github.library.parser.ExceptionParser;
import github.library.utils.Error;
import github.library.utils.IExceptionMessage;
import github.retrofitplus.api.Api;
import github.retrofitplus.api.ApiCacheProvider;
import github.retrofitplus.api.convert.CustomerGsonConverterFactory;
import github.retrofitplus.model.ImageListRequest;
import github.retrofitplus.model.NewsDetailRequest;
import github.retrofitplus.model.NewsRequest;
import github.retrofitplus.model.bmob.FocusListResponse;
import github.retrofitplus.model.bmob.RegisterRequest;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.rx_cache2.DynamicKeyGroup;
import io.rx_cache2.EvictDynamicKeyGroup;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.Reply;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Api api;
    private CompositeDisposable disposable;
    private ApiCacheProvider apiProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/api/data/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())//
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        api = retrofit.create(Api.class);
        disposable = new CompositeDisposable();
        //rxCache
        apiProvider = new RxCache.Builder()
                .persistence(getFilesDir(), new GsonSpeaker())
                .using(ApiCacheProvider.class);

    }

    public void request(View view) {
        imageListRequest();
       /* newsListRequest();*/
        /*newsDetailRequest();*/
//        focusListRequest();
//        userRegister();

    }

    private void userRegister() {
        RegisterRequest.Req req = new RegisterRequest.Req();
        req.nick = "ricky";
        req.password = "123456";
        defThreadCfg(api.register(req)).subscribe(new Observer<RegisterRequest.Res>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull RegisterRequest.Res s) {
//                RegisterRequest.Res.ResultItem resultItem = new GsonSpeaker(new Gson()).fromJson(s.result, RegisterRequest.Res.ResultItem.class);
                System.out.println();

            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private <T> Observable<T> defThreadCfg(Observable<T> t) {
        return t.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void focusListRequest() {
        defThreadCfg(apiProvider.getFocusList(api.getFocusList(), new EvictProvider(true)))
                .subscribe(new Observer<Reply<FocusListResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Reply<FocusListResponse> apiResponseReply) {
                        System.out.println();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void newsDetailRequest() {
        api.getNewForLast()
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<NewsRequest.Res, ObservableSource<NewsDetailRequest.Res>>() {
                    @Override
                    public ObservableSource<NewsDetailRequest.Res> apply(@NonNull NewsRequest.Res res) throws Exception {
                        return api.getStoryDetail(res.stories.get(0).id);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsDetailRequest.Res>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        System.out.println();
                    }

                    @Override
                    public void onNext(@NonNull NewsDetailRequest.Res res) {
                        System.out.println();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println();
                    }
                });

    }

    private void newsListRequest() {

        int dynamicKey = 5;
        int dynamicGroup = 5;
        boolean evict = false;
        apiProvider.getNewForLast(api.getNewForLast(), new DynamicKeyGroup(dynamicKey, dynamicGroup), new EvictDynamicKeyGroup(evict))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Reply<NewsRequest.Res>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        System.out.println();
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Reply<NewsRequest.Res> resReply) {
                        System.out.println(resReply.getSource());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        ExceptionParseMgr.Instance.parseException(e, new ExceptionParser.IHandler() {
                            @Override
                            public void onHandler(Error error, String message) {
                                System.out.println();
                            }
                        });
                    }

                    @Override
                    public void onComplete() {
                        System.out.println();
                    }
                });


//        Observable<NewsRequest.Res> newForLast = api.getNewForLast();
//        newForLast.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<NewsRequest.Res>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//                        System.out.println();
//                        disposable.add(d);
//                    }
//
//                    @Override
//                    public void onNext(@NonNull NewsRequest.Res res) {
//                        System.out.println();
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        System.out.println();
//                        ExceptionParseMgr.Instance.parseException(e, new ExceptionParser.IHandler() {
//                            @Override
//                            public void onHandler(Error error, String message) {
//                                System.out.println();
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        System.out.println();
//                    }
//                });
    }

    private void imageListRequest() {
        ExceptionParseMgr.Instance.addCustomerMessageParser(new IExceptionMessage() {
            @Override
            public String onParseMessage(Error error, Throwable e) {
                System.out.println();
                return null;
            }
        });
        Observable<ImageListRequest.Res> imageList = api.getImageList(10, 1);
        imageList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ImageListRequest.Res>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        System.out.println();
                    }

                    @Override
                    public void onNext(@NonNull ImageListRequest.Res res) {
                        System.out.println();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        ExceptionParseMgr.Instance.parseException(e, new ExceptionParser.IHandler() {
                            @Override
                            public void onHandler(Error error, String message) {
                                System.out.println();
                            }
                        });
                    }

                    @Override
                    public void onComplete() {
                        System.out.println();
                    }
                });
    }
}
