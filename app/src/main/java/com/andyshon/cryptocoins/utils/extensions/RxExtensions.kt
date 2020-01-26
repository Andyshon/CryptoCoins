package com.andyshon.cryptocoins.utils.extensions

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.applySchedulers(): Single<T> = observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
fun <T> Observable<T>.applySchedulers(): Observable<T> = observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
fun Completable.applySchedulers(): Completable = observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())