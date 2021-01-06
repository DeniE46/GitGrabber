package axp.denie.gitgrabber.interactors.loadDetails

import axp.denie.gitgrabber.models.GitListModel
import axp.denie.gitgrabber.utils.Resource

interface LoadDetailsUseCase {
    suspend fun getRepoById(repoId: Int): Resource<GitListModel>
}