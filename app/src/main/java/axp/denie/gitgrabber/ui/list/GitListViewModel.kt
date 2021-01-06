package axp.denie.gitgrabber.ui.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import axp.denie.gitgrabber.interactors.loadAll.LoadReposUseCase
import axp.denie.gitgrabber.models.GitListModel
import axp.denie.gitgrabber.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class GitListViewModel @ViewModelInject constructor(
    private val loadReposUseCase: LoadReposUseCase) : ViewModel() {

    private var _setRepos: MutableLiveData<Resource<List<GitListModel>>> = MutableLiveData<Resource<List<GitListModel>>>()
    val loadRepos: LiveData<Resource<List<GitListModel>>>
        get() = _setRepos


    init {
        getRepos()
    }

    @ExperimentalCoroutinesApi
    fun getRepos() = viewModelScope.launch {
        _setRepos.postValue(Resource.loading(null))
        _setRepos.postValue(loadReposUseCase.getRepos().value)
    }
}