package axp.denie.gitgrabber.interactors.loadAll

import axp.denie.gitgrabber.models.GitListModel
import axp.denie.gitgrabber.repositories.loadRepos.LoadReposRepository
import axp.denie.gitgrabber.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class LoadReposUseCaseImpl @Inject constructor(private val getAllRepository: LoadReposRepository) : LoadReposUseCase {

    @ExperimentalCoroutinesApi
    override suspend fun getRepos(): MutableStateFlow<Resource<List<GitListModel>>> = getAllRepository.getRepos()
}