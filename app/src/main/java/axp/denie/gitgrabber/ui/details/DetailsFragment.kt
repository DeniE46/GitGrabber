package axp.denie.gitgrabber.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import axp.denie.gitgrabber.R
import axp.denie.gitgrabber.databinding.DetailsFragmentBinding
import axp.denie.gitgrabber.databinding.FragmentItemBinding
import axp.denie.gitgrabber.databinding.FragmentListBinding
import axp.denie.gitgrabber.models.GitListModel
import axp.denie.gitgrabber.utils.Resource
import axp.denie.gitgrabber.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.details_fragment) {

    private lateinit var binding: DetailsFragmentBinding
    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: DetailsViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DetailsFragmentBinding.bind(view)
        val repoId = args.repoId
        viewModel.getRepoById(repoId).observe(viewLifecycleOwner, { res -> render(res) })
    }

    private fun render(res: Resource<GitListModel>) {
        when(res.status) {
            Status.SUCCESS -> {
                binding.repoName.text = res.data!!.name
                binding.repoOwner.text = res.data.owner
                binding.language.text = res.data.language
                binding.size.text = res.data.size.toString()
                binding.cardMiddle.visibility = View.VISIBLE
                binding.cardTop.visibility = View.VISIBLE
                binding.errorState.visibility = View.GONE
                binding.progress.visibility = View.GONE
            }
            Status.ERROR -> {
                binding.cardMiddle.visibility = View.GONE
                binding.cardTop.visibility = View.GONE
                binding.errorState.visibility = View.VISIBLE
                binding.errorMessage.text = res.message
                binding.progress.visibility = View.GONE
            }
            Status.LOADING -> {
                binding.cardMiddle.visibility = View.GONE
                binding.cardTop.visibility = View.GONE
                binding.errorState.visibility = View.GONE
                binding.progress.visibility = View.VISIBLE
            }
        }
    }



}