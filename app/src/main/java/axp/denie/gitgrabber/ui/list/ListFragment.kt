package axp.denie.gitgrabber.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import axp.denie.gitgrabber.R
import axp.denie.gitgrabber.databinding.FragmentListBinding
import axp.denie.gitgrabber.models.GitListModel
import axp.denie.gitgrabber.utils.ConnectivityListener
import axp.denie.gitgrabber.utils.Resource
import axp.denie.gitgrabber.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {

    private lateinit var binding: FragmentListBinding
    private lateinit var gitAdapter: GitModelAdapter
    private val viewModel: GitListViewModel by viewModels()
    @Inject lateinit var connectivityListener: ConnectivityListener

    @ExperimentalCoroutinesApi
    private val refreshListener = SwipeRefreshLayout.OnRefreshListener {
        binding.swipeRefreshLayout.isRefreshing = true
        // call api to reload the screen
        viewModel.getRepos()
    }


    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListBinding.bind(view)
        initAdapter()
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.acid_lime_500)
        binding.swipeRefreshLayout.setOnRefreshListener(refreshListener)
        viewModel.loadRepos.observe(viewLifecycleOwner, {res -> render(res)})
        connectivityListener.observe(viewLifecycleOwner, { isAvailable ->
            when (isAvailable) {
                true -> {
                    binding.list.visibility = View.VISIBLE
                    binding.progress.visibility = View.GONE
                    binding.errorState.visibility = View.GONE
                }
                false -> {
                    binding.list.visibility = View.GONE
                    binding.progress.visibility = View.GONE
                    binding.errorState.visibility = View.VISIBLE
                    binding.errorMessage.text = getString(R.string.connectivity_error)
                }
            }
        })
    }


    private fun initAdapter(){
        gitAdapter = GitModelAdapter({id -> onItemSelected(id)}, resManager = ListResourceManager(requireContext()) )
        binding.list.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = gitAdapter
        }
    }


    private fun render(res: Resource<List<GitListModel>>){
        binding.swipeRefreshLayout.isRefreshing = false
        when(res.status) {
            Status.SUCCESS -> {
                gitAdapter.submitList(res.data)
                binding.list.visibility = View.VISIBLE
                binding.progress.visibility = View.GONE
                binding.errorState.visibility = View.GONE
            }
            Status.LOADING -> {
                binding.list.visibility = View.GONE
                binding.progress.visibility = View.VISIBLE
                binding.errorState.visibility = View.GONE
            }
            Status.ERROR -> {
                binding.list.visibility = View.GONE
                binding.progress.visibility = View.GONE
                binding.errorState.visibility = View.VISIBLE
                binding.errorMessage.text = res.message
            }
        }
    }


    private fun onItemSelected(repoId: Int){
    navigateToDetails(repoId)
    }


    private fun navigateToDetails(repoId: Int){
        val action = ListFragmentDirections.actionListFragmentToDetailsFragment(repoId)
        findNavController().navigate(action)
    }

}