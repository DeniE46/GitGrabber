package axp.denie.gitgrabber.api

import axp.denie.gitgrabber.models.GitListModel
import axp.denie.gitgrabber.utils.Resource

interface ApiHelper {
    suspend fun getRepos(): Resource<List<GitListModel>>
}