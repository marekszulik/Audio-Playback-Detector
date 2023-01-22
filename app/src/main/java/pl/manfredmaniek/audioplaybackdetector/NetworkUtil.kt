package pl.manfredmaniek.audioplaybackdetector

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

sealed class ResultWrapper<out T>(val isSuccess: Boolean) {
    data class Success<out T>(val value: T) : ResultWrapper<T>(true)
    data class GenericError(val code: Int? = null) : ResultWrapper<Nothing>(false)
}

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> T
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> ResultWrapper.GenericError(throwable.code())
                else -> ResultWrapper.GenericError(null)
            }
        }
    }
}
