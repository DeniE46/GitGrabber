package axp.denie.gitgrabber.mappers

import axp.denie.gitgrabber.models.GitListModel
import axp.denie.gitgrabber.models.GitRemoteModel

object ReposMapper {

    fun convertRemoteToLocal(remote: GitRemoteModel): GitListModel{
        return GitListModel(remote.id!!, remote.name!!, remote.owner!!.login!!, remote.language!!, remote.size!!)
    }
}