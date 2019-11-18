package ru.geekbrains.gb_kotlin.ui.base

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import ru.geekbrains.gb_kotlin.R
import ru.geekbrains.gb_kotlin.data.errors.NoAuthException

abstract class BaseActivity<T, S : BaseViewState<T>> : AppCompatActivity() {

    companion object {
        private const val RC_SIGN_IN = 4242
    }

    abstract val model: BaseViewModel<T, S>
    abstract val layoutRes: Int?


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutRes?.let {
            setContentView(it)
        }
        model.getViewState().observe(this, Observer<S> {
            it ?: return@Observer
            it.error?.let {
                renderError(it)
                return@Observer
            }

            renderData(it.data)
        })
    }


    protected fun renderError(error: Throwable?) = error?.let {
        when (error) {
            is NoAuthException -> startLogin()
            else -> it.message?.let { message ->
                showError(message)
            }
        }
    }

    fun startLogin() {
        val providers = listOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setLogo(R.drawable.android_robot)
                .setTheme(R.style.LoginStyle)
                .setAvailableProviders(providers)
                .build()
            , RC_SIGN_IN
        )
    }

    abstract fun renderData(data: T)

    protected fun showError(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN && resultCode != Activity.RESULT_OK) {
            finish()
        }
    }

}