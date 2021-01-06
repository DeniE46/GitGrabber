package axp.denie.gitgrabber.repositories.loadRepos

import android.util.Log
import axp.denie.gitgrabber.api.ApiHelper
import axp.denie.gitgrabber.dao.GitDao
import axp.denie.gitgrabber.models.GitListModel
import axp.denie.gitgrabber.utils.Resource
import axp.denie.gitgrabber.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoadReposRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val daoGitRepos: GitDao
): LoadReposRepository {

    @ExperimentalCoroutinesApi
    override suspend fun getRepos(): MutableStateFlow<Resource<List<GitListModel>>> {
        val state = MutableStateFlow<Resource<List<GitListModel>>>(Resource.loading(null))

        try {
            val response = apiHelper.getRepos()
            if(response.status == Status.SUCCESS) {
                withContext(Dispatchers.IO) {
                    val results = daoGitRepos.insertAllGitRepos(response.data!!)
                    Log.d("Repository", "getRepos: response successful + $results")
                    val dataFromDb = daoGitRepos.getAllRepos() // returns new data
                    Log.d("Repository", "getRepos: response successful + $dataFromDb")
                    state.value = Resource.success(dataFromDb)
                }
            }
        } catch (e: Exception) {

            withContext(Dispatchers.IO) {
                val dataFromDb = daoGitRepos.getAllRepos()
                if(dataFromDb.isNotEmpty()){
                    state.value = Resource.success(dataFromDb)
                } else {
                    state.value = Resource.error("unexpected error, db is empty", dataFromDb)
                }
            }
        }
        return state
    }


    override suspend fun getGitRepoById(repoId: Int): Resource<GitListModel> {
        lateinit var state: Resource<GitListModel>
        withContext(Dispatchers.IO){
            state = try {
                val dataFromDb = daoGitRepos.getRepoById(repoId)
                Resource.success(dataFromDb)
            } catch (e: java.lang.Exception) {
                Resource.error(e.message!!, null)
            }
        }
       return state
    }
}