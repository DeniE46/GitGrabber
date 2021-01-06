package axp.denie.gitgrabber.api

import axp.denie.gitgrabber.mappers.ReposMapper
import axp.denie.gitgrabber.models.GitListModel
import axp.denie.gitgrabber.utils.Constants
import axp.denie.gitgrabber.utils.Resource

import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    private val user = Constants.USER

    override suspend fun getRepos(): Resource<List<GitListModel>> {
        val list = mutableListOf<GitListModel>()
        val response = apiService.getRepos(user)
        return if(response.isSuccessful){
            if(response.body() != null){
                for(model in response.body()!!){
                    list.add(ReposMapper.convertRemoteToLocal(model))
                }
                Resource.success(list)
            } else {
                Resource.error(response.message(), null)
            }
        } else {
            Resource.error(response.message(), null)
        }
    }
}