package axp.denie.gitgrabber.ui.list

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import axp.denie.gitgrabber.R
import axp.denie.gitgrabber.models.GitListModel


class GitModelAdapter(
    val onClickListener: (pos: Int)->Unit,
    val resManager: ListResourceManager
) : ListAdapter<GitListModel, GitModelAdapter.ViewHolder>(GitDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_item, parent, false)

        return ViewHolder(view, onClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(getItem(position))
    }


    inner class ViewHolder(view: View, onClickListener: (pos: Int) -> Unit) : RecyclerView.ViewHolder(view) {

        private lateinit var item: GitListModel
        private val projectName: TextView = view.findViewById(R.id.projectName)
        private val ownerName: TextView = view.findViewById(R.id.ownerName)
        private val language: TextView = view.findViewById(R.id.language)
        private val size: TextView = view.findViewById(R.id.size)
        private val repoOwner = resManager.getRepoOwner() //view.rootView.context.getString(R.string.repo_owner)
        private val repoLanguage = resManager.getRepoLanguage() //view.rootView.context.getString(R.string.repo_language)
        private val repoSize = resManager.getRepoSize() //view.rootView.context.getString(R.string.repo_size)
        init{
            onClick()
        }

        private fun onClick(){
            itemView.setOnClickListener { onClickListener.invoke(item.id) }
        }

        fun bind(gitModel: GitListModel){

            this.item = gitModel
            projectName.text = gitModel.name
            ownerName.text = String.format(repoOwner, gitModel.owner)
            language.text = String.format(repoLanguage, gitModel.language)
            size.text = String.format(repoSize, gitModel.size)
        }
    }

    class GitDiffCallback : DiffUtil.ItemCallback<GitListModel>() {
        override fun areItemsTheSame(oldItem: GitListModel, newItem: GitListModel): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: GitListModel, newItem: GitListModel): Boolean {
            return oldItem == newItem
        }
    }
}

