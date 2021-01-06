package axp.denie.gitgrabber.di

import axp.denie.gitgrabber.interactors.loadDetails.LoadDetailsUseCase
import axp.denie.gitgrabber.interactors.loadDetails.LoadDetailsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped


@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class LoadByIdModule {
    @ActivityRetainedScoped
    @Binds
    abstract fun bindDetailsUseCase(detailedUseCase: LoadDetailsUseCaseImpl): LoadDetailsUseCase
}