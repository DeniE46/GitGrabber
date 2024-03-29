package axp.denie.gitgrabber.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import axp.denie.gitgrabber.dao.DaoHelper
import axp.denie.gitgrabber.dao.Database
import axp.denie.gitgrabber.dao.GitDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object DbModule {

    @Provides
    fun provideGitDao(appDatabase: Database) = appDatabase.gitDao()

    @Singleton
    @Provides
    fun provideDbInstance(@ApplicationContext context: Context): Database{
        return Room.databaseBuilder(
            context,
            Database::class.java,
            "git_grabber_db"
        ).build()
    }


    @Provides
    fun provideDaoHelper(gitDao: GitDao) = DaoHelper(gitDao)
    
}