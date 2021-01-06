package axp.denie.gitgrabber.di

import axp.denie.gitgrabber.interactors.loadAll.LoadReposUseCase
import axp.denie.gitgrabber.interactors.loadAll.LoadReposUseCaseImpl
import axp.denie.gitgrabber.repositories.loadRepos.LoadReposRepository
import axp.denie.gitgrabber.repositories.loadRepos.LoadReposRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped


@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class LoadAllModule {
    @ActivityRetainedScoped
    @Binds
    abstract fun bindAllReposUseCase(loadAllUseCase: LoadReposUseCaseImpl): LoadReposUseCase

}