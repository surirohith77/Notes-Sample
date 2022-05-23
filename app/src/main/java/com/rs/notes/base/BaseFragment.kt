package com.icubixx.customer.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider


abstract class BaseFragment<D : ViewDataBinding, V : BaseViewModel> : Fragment() {

    abstract val layoutRes: Int
    abstract val viewModelClass: Class<V>
    lateinit var viewModel: V
    abstract fun init()
    abstract fun bindViewModel()
    protected lateinit var binding: D



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate<ViewDataBinding>(inflater, layoutRes, container, false) as D
        viewModel = ViewModelProvider(this).get(viewModelClass)

        return binding.root
    }




}