package axp.denie.gitgrabber.interactors.loadDetails

import axp.denie.gitgrabber.models.GitListModel
import axp.denie.gitgrabber.repositories.loadRepos.LoadReposRepository
import axp.denie.gitgrabber.utils.Resource
import javax.inject.Inject

class LoadDetailsUseCaseImpl @Inject constructor(
    private val getAllRepository: LoadReposRepository
): LoadDetailsUseCase {

    override suspend fun getRepoById(repoId: Int): Resource<GitListModel> {
        return getAllRepository.getGitRepoById(repoId)
    }
}