package com.dicoding.githubapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubapp.adapter.FollowingAdapter
import com.dicoding.githubapp.databinding.FragmentFollowingBinding
import com.dicoding.githubapp.model.FollowingGithubModelItem
import com.dicoding.githubapp.util.Utils
import com.dicoding.githubapp.viewmodel.FollowingGithubViewModel

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    private lateinit var followingViewModel: FollowingGithubViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        followingViewModel = ViewModelProvider(this)[FollowingGithubViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val login = arguments?.getString(Utils.LOGIN_KEY_USER_FF)!!

        followingViewModel.getFollowing(login).observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.data.visibility = View.VISIBLE
            } else {
                binding.data.visibility = View.INVISIBLE
                setDataToFragment(it!!)
            }
        }

        followingViewModel.loading().observe(viewLifecycleOwner) {
            Utils.showLoading(binding.progressBar2, it)
        }

        followingViewModel.getToastMsg().observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setDataToFragment(value: List<FollowingGithubModelItem>) {
        binding.apply {
            rvFollowing.layoutManager = LinearLayoutManager(context)

            rvFollowing.adapter = FollowingAdapter(value as ArrayList<FollowingGithubModelItem>)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}