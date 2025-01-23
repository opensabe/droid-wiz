package com.droid.wiz.toolkits.base.kotlin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droid.wiz.toolkits.net.callback.BaseCallback
import com.droid.wiz.toolkits.net.callback.BaseResp
import com.droid.wiz.toolkits.net.exception.LoginInvalidException
import com.droid.wiz.toolkits.utils.Tools
import com.droid.wiz.toolkits.widget.status.StatusLayout
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.droid.wiz.toolkits.R

fun StatusLayout.setState(state: UIState<*>) {
    when (state) {
        is UIState.Loading -> {
            showLoading()
        }

        is UIState.Failure -> {
            showError()
        }

        is UIState.Empty -> {
            showEmpty()
        }

        is UIState.Success<*> -> {
            dismiss()
        }
    }
}

fun <T> ViewModel.request(
    block: suspend () -> BaseResp<T>,
    success: (T) -> Unit,
    failure: (code: Int, message: String?) -> Unit,
): Job {
    return viewModelScope.launch {
        try {
            val resp = block()
            if (resp.isSuccess) {
                if (resp.data == null) {
                    failure(BaseCallback.DATA_NULL_CODE, getAbnormalMessage())
                } else {
                    success(resp.data)
                }
            } else {
                failure(resp.bizCode, resp.message)
            }
        } catch (e: Exception) {
            when (e) {
                is LoginInvalidException -> {
                    failure(BaseCallback.LOGIN_INVALID_CODE, "")
                }

                is JsonDataException, is JsonEncodingException -> failure(
                    BaseCallback.FAILURE_CODE, getAbnormalMessage()
                )

                else -> failure(
                    BaseCallback.FAILURE_CODE, getNetAbnormalMessage()
                )
            }
        }
    }
}

fun getAbnormalMessage(): String = Tools.getString(R.string.abnormal)

fun getNetAbnormalMessage(): String = Tools.getString(R.string.net_error_tips)