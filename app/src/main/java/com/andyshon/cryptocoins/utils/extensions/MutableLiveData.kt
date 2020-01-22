package com.andyshon.cryptocoins.utils.extensions

import androidx.lifecycle.MutableLiveData

fun <T:Any?> MutableLiveData<T>.default(initialValue: T) = apply { postValue(initialValue) }