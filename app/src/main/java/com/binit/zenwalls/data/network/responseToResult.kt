import com.binit.zenwalls.domain.networkUtil.NetworkError
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import com.binit.zenwalls.domain.networkUtil.Result


/** Add inline reified
 * Suspend function 'body' should be called only from a coroutine or another suspend function
 * Cannot use 'T' as reified type parameter. Use a class instead.
 */

/**Add suspend
 * Suspend function 'body' should be called only from a coroutine or another suspend function
 */


suspend inline fun <reified T> responseToResult(response: HttpResponse): com.binit.zenwalls.domain.networkUtil.Result<T, NetworkError> {
    when (response.status.value) {
        in 200..299 -> {
            try {
                return Result.Success(response.body())
            } catch (e: NoTransformationFoundException) {
                return Result.Error(NetworkError.SERIALISATION_ERROR)
            }
        }

        400 -> return Result.Error(NetworkError.BAD_REQUEST)
        401 -> return Result.Error(NetworkError.UNAUTHORIZED)
        403 -> return Result.Error(NetworkError.FORBIDDEN)
        404 -> return Result.Error(NetworkError.FORBIDDEN)
        in 500..599 -> return Result.Error(NetworkError.SERVER_ERROR)
        else -> return Result.Error(NetworkError.UNKNOWN_ERROR)
    }
}