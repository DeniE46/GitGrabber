package axp.denie.gitgrabber.ui.list

import android.content.Context
import axp.denie.gitgrabber.BuildConfig
import axp.denie.gitgrabber.R

class ListResourceManager(val context: Context) {

    val loadTestResources: Boolean = true

    fun getRepoOwner(): String{
        return if(BuildConfig.DEBUG && loadTestResources){
            "testOwner"
        } else {
            context.getString(R.string.repo_owner)
        }
    }

    fun getRepoSize(): String{
        return if(BuildConfig.DEBUG && loadTestResources){
            "testSize"
        } else {
            context.getString(R.string.repo_size)
        }
    }

    fun getRepoLanguage(): String{
        return if(BuildConfig.DEBUG && loadTestResources){
            "testLanguage"
        } else {
            context.getString(R.string.repo_language)
        }
    }

    fun getRepoName(): String{
        return if(BuildConfig.DEBUG && loadTestResources){
            "testRepo"
        } else {
            context.getString(R.string.repo_name)
        }

    }



}