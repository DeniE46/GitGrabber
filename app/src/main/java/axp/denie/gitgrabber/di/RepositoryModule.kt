package axp.denie.gitgrabber.di

import axp.denie.gitgrabber.repositories.loadRepos.LoadReposRepository
import axp.denie.gitgrabber.repositories.loadRepos.LoadReposRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAllReposRepository(loadAllRepository: LoadReposRepositoryImpl): LoadReposRepository
}