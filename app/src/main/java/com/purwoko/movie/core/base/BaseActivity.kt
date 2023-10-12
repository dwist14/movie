package com.purwoko.movie.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VM : ViewModel, VB : ViewBinding>(val bindingFactory: (LayoutInflater) -> VB) : AppCompatActivity() {
    lateinit var binding: VB
    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[getViewModelClass()]
        onViewCreated(savedInstanceState)
        observerViewModel()
    }

    private fun getViewModelClass(): Class<VM> {
        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        return type as Class<VM>
    }

    abstract fun onViewCreated(savedInstanceState: Bundle?)
    abstract fun observerViewModel()

    protected fun <T> LiveData<T>.onResult(action: (T) -> Unit) {
        observe(this@BaseActivity) { data -> data?.let(action) }
    }

    protected fun initToolbar(
        toolbar: Toolbar,
        paramTitle: String = "",
        paramSubTitle: String = "",
        paramBooleanBack: Boolean = true
    ) {
        setSupportActionBar(toolbar)
        supportActionBar?.title = paramTitle
        if (paramSubTitle.isNotEmpty())
            supportActionBar?.subtitle = paramSubTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(paramBooleanBack)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}