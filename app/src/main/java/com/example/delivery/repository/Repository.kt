package com.example.delivery.repository

class Repository {

    companion object {
        const val NO_INTERNET_ERROR = 1200
    }

    suspend fun createUser(nameGameshow: String): Response<String> {
        try {
            val answerRetrofit =
                RetrofitInstance.api.createGameshow(CreateGameshow(gamemaster = nameGameshow))
            with(answerRetrofit) {
                return if (isSuccessful) {
                    body()?.let {
                        Response(data = it.toGameshow().id)
                    } ?: Response(error = ErrorResponse(code()))
                } else {
                    Response(error = ErrorResponse(code()))
                }
            }
        } catch (e: Exception) {
            return Response(error = ErrorResponse(NO_INTERNET_ERROR))
        }
    }
}