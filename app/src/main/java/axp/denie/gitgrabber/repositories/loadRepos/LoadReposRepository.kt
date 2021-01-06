package axp.denie.gitgrabber.repositories.loadRepos

import axp.denie.gitgrabber.models.GitListModel
import axp.denie.gitgrabber.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow


interface LoadReposRepository {
    @ExperimentalCoroutinesApi
    suspend fun getRepos(): MutableStateFlow<Resource<List<GitListModel>>>

    suspend fun getGitRepoById(repoId: Int): Resource<GitListModel>
}