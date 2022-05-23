package com.rs.notes.addnotes

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.icubixx.customer.base.BaseViewModel


class AddNotesViewModel : BaseViewModel() {

    private val _onClick = MutableLiveData<View>()
    val onCLick: LiveData<View> = _onClick

    fun onClick(view: View) {
        _onClick.value = view
    }
}