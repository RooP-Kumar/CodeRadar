package com.example.coderadar.contestTabs

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.CodeRadar.R
import com.example.CodeRadar.databinding.FragmentTab1Binding
import com.example.CodeRadar.databinding.FragmentTab4Binding
import com.example.coderadar.data.util.Resource
import com.example.coderadar.presentation.adapter.ContestAdapter
import com.example.coderadar.presentation.viewmodel.ContestsViewModel
import com.example.coderadar.ui.ContestActivity
import java.time.LocalDateTime

class TabFragment4 : Fragment() {
    private lateinit var viewmodel: ContestsViewModel
    private lateinit var  tab4Binding: FragmentTab4Binding
    private lateinit var contestAdapter: ContestAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab4, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tab4Binding = FragmentTab4Binding.bind(view)
        viewmodel = (activity as ContestActivity).viewModel
        initRecyclerView()
        viewNewsList()


    }

    private fun viewNewsList() {
        val currentDateTimeMin = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0)
        val currentDateTime = LocalDateTime.now()
        Log.d("date",""+currentDateTime)
        viewmodel.getContest(currentDateTimeMin,currentDateTime)
        viewmodel.contestDetails.observe(viewLifecycleOwner,{response->
            when(response){
                is Resource.Success->{
                    hideProgressbar()
                    response.data?.let {
                        contestAdapter.differ.submitList(it.contests)
                        Log.d("mytag2",""+it.contests)

                    }
                }

                is Resource.Error->{
                    hideProgressbar()
                    response.message?.let {
                        Toast.makeText(activity,"An error occured : $it", Toast.LENGTH_LONG).show()
                    }

                }
                is Resource.Loading->{
                    showProgressbar()

                }
            }
        })
    }

    private fun initRecyclerView() {
        contestAdapter = ContestAdapter()
        tab4Binding.contestRv4.apply{
            adapter = contestAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
    private fun showProgressbar(){
        tab4Binding.contestPb4.visibility = View.VISIBLE
    }
    private fun hideProgressbar(){
        tab4Binding.contestPb4.visibility = View.INVISIBLE
    }
}