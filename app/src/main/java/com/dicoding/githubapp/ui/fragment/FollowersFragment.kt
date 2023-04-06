package com.dicoding.githubapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubapp.adapter.FollowerAdapter
import com.dicoding.githubapp.databinding.ActivityDetailUserBinding
import com.dicoding.githubapp.databinding.FragmentFollowersBinding
import com.dicoding.githubapp.model.FollowersGithubModelItem
import com.dicoding.githubapp.util.Utils
import com.dicoding.githubapp.viewmodel.FollowersGithubViewModel

class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!

    private var _bindingAct: ActivityDetailUserBinding? = null
    private val bindingAct get() = _bindingAct!!

    private lateinit var followersViewModel: FollowersGithubViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        followersViewModel = ViewModelProvider(
            this
        )[FollowersGithubViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        _bindingAct = ActivityDetailUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        followersViewModel = ViewModelProvider(
            this
        )[FollowersGithubViewModel::class.java]
        super.onViewCreated(view, savedInstanceState)

        val login = arguments?.getString(Utils.LOGIN_KEY_USER_FF)!!

        // get Followers
        followersViewModel.getFollowers(login).observe(viewLifecycleOwner) {
            if(it.isEmpty()){
                binding.data.visibility = View.VISIBLE
            }else{
                binding.data.visibility = View.INVISIBLE
                setDataToFragment(it!!)
            }

        }

        // get loading
        followersViewModel.loading().observe(viewLifecycleOwner) {
            Utils.showLoading(binding.progressBar2, it)
        }

        // get toast
        followersViewModel.getToastMsg().observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setDataToFragment(value: List<FollowersGithubModelItem>) {
        binding.apply {
            rvFollowers.layoutManager = LinearLayoutManager(context)

            rvFollowers.adapter = FollowerAdapter(value as ArrayList<FollowersGithubModelItem>)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}