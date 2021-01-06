package axp.denie.gitgrabber.interactors.loadAll

import axp.denie.gitgrabber.models.GitListModel
import axp.denie.gitgrabber.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow

interface LoadReposUseCase {
   @ExperimentalCoroutinesApi
   suspend fun getRepos(): MutableStateFlow<Resource<List<GitListModel>>>
}