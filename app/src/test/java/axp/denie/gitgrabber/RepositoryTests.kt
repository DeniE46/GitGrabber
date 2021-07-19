package axp.denie.gitgrabber

import axp.denie.gitgrabber.api.ApiHelperImpl
import axp.denie.gitgrabber.dao.DaoHelper
import axp.denie.gitgrabber.models.GitListModel
import axp.denie.gitgrabber.repositories.loadRepos.LoadReposRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.*
import axp.denie.gitgrabber.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals


class RepositoryTests {

    @ExperimentalCoroutinesApi
    @Test
    fun whenAllReposSuccessReturned_thenDataSavedAndReturned(){
        //given
        val daoHelper: DaoHelper = mock(DaoHelper::class.java)
        val apiHelperImpl = mock(ApiHelperImpl::class.java)
        val repo = LoadReposRepositoryImpl(apiHelperImpl, daoHelper)
        val gitTestModel = GitListModel(1, "Kotlin", "Test Repo", "Test user", 46)
        val list = listOf(gitTestModel)
        val resource = Resource.success(list)
        val resList = listOf<Long>(1,2,3)

        //when
        runBlocking {
            `when`(apiHelperImpl.getRepos()).thenReturn(resource)
        }
        `when`(daoHelper.getAllRepositories()).thenReturn(list)
        runBlocking {
            `when`(daoHelper.insertAll(resource.data!!)).thenReturn(resList)
        }

        //then
        runBlocking {
        assertEquals(repo.getRepos().value, resource)
        }
    }


    @Test
    fun whenAllReposErrorReturned_thenErrorStateReturned(){
        //given
        val daoHelper: DaoHelper = mock(DaoHelper::class.java)
        val apiHelperImpl = mock(ApiHelperImpl::class.java)
        val repo = LoadReposRepositoryImpl(apiHelperImpl, daoHelper)
        val resource = Resource.error("data not fetched", null)

        //when
        runBlocking {
            `when`(apiHelperImpl.getRepos()).thenReturn(resource)
        }
        `when`(daoHelper.getAllRepositories()).thenReturn(emptyList())
        val expectedRes = Resource.error("unexpected error, db is empty", emptyList<GitListModel>())

        //then
        runBlocking {
            assertEquals(repo.getRepos().value, expectedRes)
        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun whenSingleRepoSuccessReturned_thenDataSavedAndReturned(){
        //given
        val daoHelper: DaoHelper = mock(DaoHelper::class.java)
        val apiHelperImpl = mock(ApiHelperImpl::class.java)
        val repo = LoadReposRepositoryImpl(apiHelperImpl, daoHelper)
        val gitTestModel = GitListModel(1, "Kotlin", "Test Repo", "Test user", 46)
        val resource = Resource.success(gitTestModel)

        //when
        `when`(daoHelper.getRepositoryById(1)).thenReturn(gitTestModel)

        //then
        runBlocking {
            assertEquals(repo.getGitRepoById(1), resource)
        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun whenSingleRepoErrorReturned_thenErrorStateReturned(){
        //given
        val daoHelper: DaoHelper = mock(DaoHelper::class.java)
        val apiHelperImpl = mock(ApiHelperImpl::class.java)
        val repo = LoadReposRepositoryImpl(apiHelperImpl, daoHelper)
        val expectedRes = Resource.error("e.message!!", null)

        //when
        `when`(daoHelper.getRepositoryById(0)).thenReturn(null)

        //then
        runBlocking {
            assertEquals(repo.getGitRepoById(0), expectedRes)
        }
    }
}