package com.leyifu.phoneplayer.util;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by hahaha on 2018/12/13 0013.
 */

public class RxBus {

    private static Subject<Object> mBus;

    private static RxBus defaultInstance;

    private RxBus() {
        mBus = PublishSubject.create().toSerialized();
    }

    public static RxBus getDefault() {
        if (defaultInstance == null) {
            synchronized (RxBus.class) {
                if (defaultInstance == null) {
                    defaultInstance = new RxBus();
                }
            }
        }
        return defaultInstance;
    }

    public static void post(Object object) {
        mBus.onNext(object);
    }

    public <T> Observable<T> tObservable(Class<T> tClass) {
        return mBus.ofType(tClass);
    }

}
