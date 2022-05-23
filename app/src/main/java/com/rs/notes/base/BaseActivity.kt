package com.rs.notes.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.icubixx.customer.base.BaseViewModel



abstract class BaseActivity<D : ViewDataBinding, V : BaseViewModel> : AppCompatActivity() {

    abstract val layoutRes: Int
    abstract val viewModelClass: Class<V>
    lateinit var viewModel: V
    abstract fun bindViewModel()
    abstract fun init()
    abstract fun initControl()
    lateinit var binding: D


    @SuppressLint("StaticFieldLeak")
    companion object {
        lateinit var mContext: Context
        lateinit var mActivity: Activity
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutRes)
        viewModel = ViewModelProvider(this).get(viewModelClass)
        mContext = this
        mActivity = this

    }



    override fun onResume() {
        super.onResume()
        mContext = this
        mActivity = this
    }

    protected fun safelyShowBottomSheet(bottomSheet: BottomSheetDialogFragment, tag: String) {
        if (isFinishing) return
        if (!supportFragmentManager.isDestroyed && !supportFragmentManager.isStateSaved)
            bottomSheet.show(supportFragmentManager, tag)
    }
}