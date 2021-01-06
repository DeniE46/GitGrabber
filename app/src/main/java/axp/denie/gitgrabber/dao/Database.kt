package axp.denie.gitgrabber.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import axp.denie.gitgrabber.models.GitListModel

@Database(entities = [GitListModel::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun gitDao(): GitDao

}