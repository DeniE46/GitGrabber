package axp.denie.gitgrabber.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import axp.denie.gitgrabber.interactors.loadDetails.LoadDetailsUseCaseImpl
import axp.denie.gitgrabber.models.GitListModel
import axp.denie.gitgrabber.utils.Resource
import kotlinx.coroutines.launch

class DetailsViewModel @ViewModelInject constructor(
    private val loadDetailsUseCase: LoadDetailsUseCaseImpl
) : ViewModel() {

    private val _setRepo: MutableLiveData<Resource<GitListModel>> = MutableLiveData<Resource<GitListModel>>()

    fun getRepoById(repoId: Int) : LiveData<Resource<GitListModel>> {
        viewModelScope.launch {
            _setRepo.value = loadDetailsUseCase.getRepoById(repoId)
        }
        return _setRepo
    }
}