package axp.denie.gitgrabber.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "repos")
data class GitListModel(
    @PrimaryKey
    val id: Int,
    val name: String,
    val owner: String,
    val language: String,
    val size: Int) {
}