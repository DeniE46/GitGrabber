package axp.denie.gitgrabber

import axp.denie.gitgrabber.interactors.loadAll.LoadReposUseCaseImpl
import axp.denie.gitgrabber.interactors.loadDetails.LoadDetailsUseCaseImpl
import axp.denie.gitgrabber.models.GitListModel
import axp.denie.gitgrabber.repositories.loadRepos.LoadReposRepositoryImpl
import axp.denie.gitgrabber.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class UseCaseTests {

    @ExperimentalCoroutinesApi
    @Test
    fun whenDataIsReceived_thenReturnSuccessState(){
        //given
        val repo: LoadReposRepositoryImpl = mock(LoadReposRepositoryImpl::class.java)
        val loadAllUseCase = LoadReposUseCaseImpl(repo)
        val gitTestModel = GitListModel(1, "Kotlin", "Test Repo", "Test user", 46)
        val dataList = listOf(gitTestModel)
        val resource = Resource.success(dataList)
        val expectedState = MutableStateFlow(resource)

        //then
        runBlocking {
            `when`(repo.getRepos()).thenReturn(expectedState)
            assertEquals(loadAllUseCase.getRepos().value, resource)
        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun whenDataIsNotReceived_thenReturnErrorState(){
        //given
        val repo: LoadReposRepositoryImpl = mock(LoadReposRepositoryImpl::class.java)
        val loadAllUseCase = LoadReposUseCaseImpl(repo)
        val resource: Resource<List<GitListModel>> = Resource.error("error occurred", null)
        val expectedState = MutableStateFlow(resource)

        //then
        runBlocking {
            `when`(repo.getRepos()).thenReturn(expectedState)
            assertEquals(loadAllUseCase.getRepos().value, resource)
        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun whenDataPerRepoIsReceived_thenReturnSuccessState(){
        //given
        val repo: LoadReposRepositoryImpl = mock(LoadReposRepositoryImpl::class.java)
        val loadAllUseCase = LoadDetailsUseCaseImpl(repo)
        val gitTestModel = GitListModel(1, "Kotlin", "Test Repo", "Test user", 46)
        val resource = Resource.success(gitTestModel)

        //then
        runBlocking {
            `when`(repo.getGitRepoById(1)).thenReturn(resource)
            assertEquals(loadAllUseCase.getRepoById(1), resource)
        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun whenDataPerRepoIsNotReceived_thenReturnErrorState(){
        //given
        val repo: LoadReposRepositoryImpl = mock(LoadReposRepositoryImpl::class.java)
        val loadAllUseCase = LoadDetailsUseCaseImpl(repo)
        val resource: Resource<GitListModel> = Resource.error("error occurred", null)

        //then
        runBlocking {
            `when`(repo.getGitRepoById(1)).thenReturn(resource)
            assertEquals(loadAllUseCase.getRepoById(1), resource)
        }
    }

}