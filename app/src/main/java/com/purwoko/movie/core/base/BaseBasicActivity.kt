package com.purwoko.movie.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding

abstract class BaseBasicActivity<VB : ViewBinding>(val bindingFactory: (LayoutInflater) -> VB) : AppCompatActivity() {
    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
        onViewCreated(savedInstanceState)
    }

    abstract fun onViewCreated(savedInstanceState: Bundle?)

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