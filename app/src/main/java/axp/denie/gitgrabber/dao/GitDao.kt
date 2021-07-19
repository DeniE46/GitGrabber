package axp.denie.gitgrabber.dao

import androidx.room.*
import axp.denie.gitgrabber.models.GitListModel
import kotlinx.coroutines.flow.Flow

@Dao
interface GitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGitRepos(repo: GitListModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllGitRepos(repo: List<GitListModel>) : List<Long>


    @Query("SELECT * FROM repos")
    fun getAllRepos(): List<GitListModel>

    @Query("SELECT * FROM repos WHERE id = :repoId")
    fun getRepoById(repoId: Int): GitListModel?

    @Query("DELETE FROM repos")
    fun clearGitRepos()
}