package axp.denie.gitgrabber

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer

import axp.denie.gitgrabber.interactors.loadAll.LoadReposUseCaseImpl
import axp.denie.gitgrabber.interactors.loadDetails.LoadDetailsUseCaseImpl
import axp.denie.gitgrabber.models.GitListModel
import axp.denie.gitgrabber.ui.details.DetailsViewModel
import axp.denie.gitgrabber.ui.list.GitListViewModel
import axp.denie.gitgrabber.utils.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
open class ViewModelsTest {

    @Captor
    private lateinit var argumentCaptor: ArgumentCaptor<Resource<List<GitListModel>>>
    @Captor
    private lateinit var argumentCaptorDetails: ArgumentCaptor<Resource<GitListModel>>

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock lateinit var observer: Observer<Resource<List<GitListModel>>>
    @Mock lateinit var observerDetails: Observer<Resource<GitListModel>>


    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.IO)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun fetchAllGitRepos_returnsLoading(){
        val loadReposUseCase: LoadReposUseCaseImpl = mock(LoadReposUseCaseImpl::class.java)
        val gitListViewModel = GitListViewModel(loadReposUseCase)
        val expectedRes: Resource<List<GitListModel>> = Resource.loading(null)
        val state = MutableStateFlow(expectedRes)
        runBlocking {
            `when`(loadReposUseCase.getRepos()).thenReturn(state)
        }


        gitListViewModel.loadRepos.observeForever(observer)
        gitListViewModel.getRepos()

        verify(observer, times(2)).onChanged(argumentCaptor.capture())
    }


    @ExperimentalCoroutinesApi
    @Test
    fun fetchAllGitRepos_returnsError(){
        val loadReposUseCase: LoadReposUseCaseImpl = mock(LoadReposUseCaseImpl::class.java)
        val gitListViewModel = GitListViewModel(loadReposUseCase)
        val expectedRes: Resource<List<GitListModel>> = Resource.error("test message", null)
        val state = MutableStateFlow(expectedRes)

        runBlocking {
            withContext(Dispatchers.IO){ `when`(loadReposUseCase.getRepos()).thenReturn(state) }
            gitListViewModel.loadRepos.observeForever(observer)
            gitListViewModel.getRepos()
            verify(observer, times(2)).onChanged(argumentCaptor.capture())
        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun fetchSingleGitRepo_returnsLoading(){
        val loadDetailsUseCase: LoadDetailsUseCaseImpl = mock(LoadDetailsUseCaseImpl::class.java)
        val gitDetailViewModel = DetailsViewModel(loadDetailsUseCase)
        val expectedRes: Resource<GitListModel> = Resource.loading(null)

        runBlocking { `when`(loadDetailsUseCase.getRepoById(1)).thenReturn(expectedRes) }

        gitDetailViewModel.getRepoById(1).observeForever(observerDetails)
        gitDetailViewModel.getRepoById(1)
        verify(observerDetails, times(2)).onChanged(argumentCaptorDetails.capture())
    }


    @ExperimentalCoroutinesApi
    @Test
    fun fetchSingleGitRepo_returnsError(){
        val loadDetailsUseCase: LoadDetailsUseCaseImpl = mock(LoadDetailsUseCaseImpl::class.java)
        val gitDetailViewModel = DetailsViewModel(loadDetailsUseCase)
        val expectedRes: Resource<GitListModel> = Resource.error("error occurred", null)

        runBlocking { `when`(loadDetailsUseCase.getRepoById(1)).thenReturn(expectedRes) }
        gitDetailViewModel.getRepoById(1).observeForever(observerDetails)
        gitDetailViewModel.getRepoById(1)

        verify(observerDetails, times(2)).onChanged(argumentCaptorDetails.capture())
    }
}