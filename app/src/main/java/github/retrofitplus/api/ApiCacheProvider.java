package github.retrofitplus.api;

import java.util.concurrent.TimeUnit;

import github.retrofitplus.model.ImageListRequest;
import github.retrofitplus.model.NewsRequest;
import github.retrofitplus.model.bmob.FocusListResponse;
import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.DynamicKeyGroup;
import io.rx_cache2.Encrypt;
import io.rx_cache2.EncryptKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.EvictDynamicKeyGroup;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;

/**
 * EvictDynamicKey 仅忽略DynamicKey生成的缓存
 * EvictDynamicKeyGroup 仅忽略DynamicKeyGroup生成的缓存
 * EvictProvider 在没有上述缓存生成条件时忽略生成的缓存
 */
@EncryptKey("ricky")
public interface ApiCacheProvider {

    @LifeCache(duration = 60, timeUnit = TimeUnit.SECONDS)
    Observable<Reply<ImageListRequest.Res>> getImageList(Observable<ImageListRequest.Res> resObservable, DynamicKey url, EvictDynamicKey evictDynamicKey);

    @Encrypt
    @LifeCache(duration = 3, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<NewsRequest.Res>> getNewForLast(Observable<NewsRequest.Res> resObservable, DynamicKeyGroup url, EvictDynamicKeyGroup evictDynamicKey);


    @LifeCache(duration = 3, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<NewsRequest.Res>> getNewForLast_(Observable<NewsRequest.Res> resObservable, EvictProvider evictProvider);

    @LifeCache(duration = 3, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<FocusListResponse>> getFocusList(Observable<FocusListResponse> observable, EvictProvider evictProvider);
}
