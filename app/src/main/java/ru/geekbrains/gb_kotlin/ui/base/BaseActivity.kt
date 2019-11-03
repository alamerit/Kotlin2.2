package ru.geekbrains.gb_kotlin.ui.base

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

abstract class BaseActivity<T, S : BaseViewState<T>> : AppCompatActivity() {

    abstract val viewModel: BaseViewModel<T, S>
    abstract val layoutRes: Int?


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutRes?.let {
            setContentView(it)
        }
        viewModel.getViewState().observe(this, Observer<S> {
            it ?: return@Observer
            it.error?.let {
                renderError(it)
                return@Observer
            }

            renderData(it.data)
        })
    }


    protected fun renderError(error: Throwable?) = error?.let {
        it.message?.let { message ->
            showError(message)
        }
    }

    abstract fun renderData(data: T)

    protected fun showError(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

}