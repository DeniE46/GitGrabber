package axp.denie.gitgrabber

import axp.denie.gitgrabber.models.GitListModel

class GitRepoTestFactory {

    companion object {
        fun createRepoObject(): GitListModel{
            return GitListModel(1, "Kotlin", "Test Repo", "Test user", 46)
        }

        fun createListOfRepoObjects(count: Int): List<GitListModel>{
            val list = mutableListOf<GitListModel>()
            repeat(count){ index ->
                list.add(GitListModel(index, "Kotlin", "Test Repo", "Test user", 46))
            }
            return list
        }
    }
}