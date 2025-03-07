package com.binit.zenwalls.domain

import com.binit.zenwalls.domain.networkUtil.NetworkError

fun NetworkErrorToMessageMapper(error: NetworkError): String{
    val message = when (error) {
        NetworkError.REQUEST_TIMEOUT ->"Request timed out. Please try again."
        NetworkError.BAD_REQUEST -> "Bad request. Please try again."
        NetworkError.UNAUTHORIZED -> "Unauthorized access. Please log in again."
        NetworkError.FORBIDDEN -> "Access denied. You don't have permission."
        NetworkError.NOT_FOUND -> "Requested resource not found."
        NetworkError.NO_INTERNET_CONNECTION -> "No internet connection. Check your network."
        NetworkError.UNKNOWN_ERROR -> "An unknown error occurred. Please try again later."
        NetworkError.SERVER_ERROR -> "Server error. Please try again after some time."
        NetworkError.SERIALISATION_ERROR -> "Data processing error. Please report this issue."
    }
    return message
}