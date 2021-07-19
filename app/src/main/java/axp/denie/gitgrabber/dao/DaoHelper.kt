package axp.denie.gitgrabber.dao

import axp.denie.gitgrabber.models.GitListModel
import javax.inject.Inject

open class DaoHelper @Inject constructor(private val daoGitRepos: GitDao) {

    fun getAllRepositories(): List<GitListModel> = daoGitRepos.getAllRepos()

    fun getRepositoryById(repoId: Int): GitListModel? = daoGitRepos.getRepoById(repoId)

    suspend fun insertAll(repo: List<GitListModel>) = daoGitRepos.insertAllGitRepos(repo)

    suspend fun insertRepo(repo: GitListModel) = daoGitRepos.insertGitRepos(repo)
}