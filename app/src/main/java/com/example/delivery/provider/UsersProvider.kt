package com.example.delivery.provider

import com.example.delivery.model.createUser.User
import com.example.delivery.model.createUser.ResponseHttp
import com.example.delivery.routes.APIRoutes
import com.example.delivery.routes.UsersRoutes
import retrofit2.Call

class UsersProvider {

    private var usersRoutes: UsersRoutes? = null

    init {
        val api = APIRoutes()
        usersRoutes = api.getUsersRoutes()
    }

    fun register(user: User): Call<ResponseHttp>?{
        return usersRoutes?.register(user)
    }

    fun login(email: String, password: String): Call<ResponseHttp>?{
        return usersRoutes?.login(email, password)
    }
}