package com.android.betterway.network.image;


import com.android.betterway.utils.LogUtil;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Jackdow
 * @version 1.0
 *          BetterWay
 */

public final class ImageDownload {
    private static final String BASE_URL = "https://cn.bing.com";
    private static final String TAG = "ImageDownload";
    private ImageDownload() {
        LogUtil.v(TAG, "constructor");
    }

    /**
     * 下载图的链接
     * 记录日期
     */
    public static void downloadUrl() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://cn.bing.com/")
                .build();
        final ImageAPI imageAPI = retrofit.create(ImageAPI.class);
        LogUtil.v(TAG, "downloadUrl");
        imageAPI.getImage()
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .subscribe(new Subscriber<ImageBean>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(1);
                    }

                    @Override
                    public void onNext(ImageBean imageBean) {
                        LogUtil.v(TAG, "next");
                        LogUtil.d(TAG, BASE_URL + imageBean.getURL());
                        LogUtil.d(TAG, imageBean.getDate());
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        LogUtil.v(TAG, "error");
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.v(TAG, "complete");
                    }
                });


    }
}
