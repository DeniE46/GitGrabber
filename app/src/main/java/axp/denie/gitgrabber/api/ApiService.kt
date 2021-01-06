package axp.denie.gitgrabber.api

import axp.denie.gitgrabber.models.GitListModel
import axp.denie.gitgrabber.models.GitRemoteModel
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {
    @GET("users/{user}/repos")
    suspend fun getRepos(@Path("user") user: String): Response<List<GitRemoteModel>>
}