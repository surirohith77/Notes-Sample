package com.rs.notes.home

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.icubixx.customer.base.BaseViewModel


class HomeViewModel : BaseViewModel() {

    private val _onClick = MutableLiveData<View>()
    val onCLick: LiveData<View> = _onClick

    fun onClick(view: View) {
        _onClick.value = view
    }
}