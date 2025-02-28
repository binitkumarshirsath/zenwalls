package com.binit.zenwalls.data.network

import com.binit.zenwalls.domain.networkUtil.NetworkError
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import com.binit.zenwalls.domain.networkUtil.Result
import responseToResult

suspend inline fun <reified T> safeCall(
    execute: ()-> HttpResponse
):Result<T, NetworkError>{
    val response = try{
        execute()
    }catch (e:UnresolvedAddressException){
        return  Result.Error(NetworkError.NO_INTERNET_CONNECTION)
    }catch(e: SerializationException){
        return Result.Error(NetworkError.SERIALISATION_ERROR)
    }catch (e:Exception){
        coroutineContext.ensureActive()
        return Result.Error(NetworkError.UNKNOWN_ERROR)
    }

    return  responseToResult(response)
}