package axp.denie.gitgrabber

import androidx.room.Room

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import axp.denie.gitgrabber.dao.Database
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
open class DaoRoomTests {

    private lateinit var gitDatabase: Database

    @Before
    fun initDb() {
        gitDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().targetContext,
                Database::class.java).build()
    }

    @After
    fun closeDb() {
        gitDatabase.close()
    }


    @Test
    fun insertGitRepoSavesData() {
        val gitRepo = GitRepoTestFactory.createRepoObject()
        runBlocking { gitDatabase.gitDao().insertGitRepos(gitRepo) }
        val gitDao = gitDatabase.gitDao().getAllRepos()
        assert(gitDao.isNotEmpty())
    }

    @Test
    fun insertMultipleGitReposSavesData(){
        val gitRepos = GitRepoTestFactory.createListOfRepoObjects(5)
        runBlocking { gitDatabase.gitDao().insertAllGitRepos(gitRepos) }
        val gitDao = gitDatabase.gitDao().getAllRepos()
        assert(gitDao.isNotEmpty())
    }

    @Test
    fun getGitReposRetrievesData() {
        val cachedGitRepos = GitRepoTestFactory.createListOfRepoObjects(5)
        runBlocking {
            cachedGitRepos.forEach {
                gitDatabase.gitDao().insertGitRepos(it)
            }
        }
        val retrievedGitRepos = gitDatabase.gitDao().getAllRepos()
        assert(retrievedGitRepos == cachedGitRepos.sortedWith(compareBy({ it.id }, { it.id })))
    }


    @Test
    fun clearGitReposClearsData() {
        val cachedGitRepo = GitRepoTestFactory.createRepoObject()
        runBlocking { gitDatabase.gitDao().insertGitRepos(cachedGitRepo) }

        gitDatabase.gitDao().clearGitRepos()
        assert(gitDatabase.gitDao().getAllRepos().isEmpty())
    }
}