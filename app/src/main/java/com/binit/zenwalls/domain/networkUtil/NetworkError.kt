package com.binit.zenwalls.domain.networkUtil


enum class NetworkError : Error {

    REQUEST_TIMEOUT,
    BAD_REQUEST,                    //The request was unacceptable, often due to missing a required parameter
    UNAUTHORIZED,                   //Invalid Access Token
    FORBIDDEN,                      //Missing permissions to perform request
    NOT_FOUND,                      //The requested resource doesn’t exist
    NO_INTERNET_CONNECTION,         //User is offline
    UNKNOWN_ERROR,                  //Throw when res.status code doest match
    SERVER_ERROR,                   //Something went wrong on sever end
    SERIALISATION_ERROR,            //Something went wrong during serialisation
}