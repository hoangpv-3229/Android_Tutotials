package vn.ztech.software.tut_22_mvp.data.repository
import java.lang.Exception

interface IOnResultListener<T> {
    fun onSuccess(data: T)
    fun onError(e: Exception)
}