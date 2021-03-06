package com.example.lg.day6

import ApiUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.lg.day6.data.RnDInfo
import com.example.lg.day6.data.RnDInfoRes
import com.example.lg.day6.mainFragment.adapter.MyIssueRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_issue_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [IssueFragment.OnListFragmentInteractionListener] interface.
 */
class IssueFragment : Fragment() , SwipeRefreshLayout.OnRefreshListener {
    private var mDatas: MutableList<RnDInfo> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_issue_list, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the adapter
        if (list is RecyclerView) {
            with(list) {
                layoutManager = LinearLayoutManager(context)
                adapter = MyIssueRecyclerViewAdapter(context,mDatas)
            }
        }
        swipe_refresh_layout.setOnRefreshListener(this)
        requestData()
    }

    private fun requestData() {
        ApiUtil.mIssueService!!.getRnDIssueList().enqueue(object : Callback<RnDInfoRes> {
            override fun onResponse(call:Call<RnDInfoRes>, response: Response<RnDInfoRes>) {
                try {
                    mDatas.clear()
                    response.body()?.list?.forEach { mDatas.add(it) }
                    list.adapter?.notifyDataSetChanged()

                    Toast.makeText(this@IssueFragment.context, "onResponse_issue", Toast.LENGTH_SHORT).show()
                } catch (wx: Exception) {
                } finally { swipe_refresh_layout.setRefreshing(false) }
            }

            override fun onFailure(call:Call<RnDInfoRes>, t:Throwable){
                try{
                    mDatas.clear()
                    list.adapter?.notifyDataSetChanged()
                    Toast.makeText(this@IssueFragment.context,"onfail_issue",Toast.LENGTH_SHORT).show()
                }catch (wx:Exception){
                }finally { swipe_refresh_layout.setRefreshing(false) }
            }
        })

    }
    override fun onRefresh() {
        requestData()
}

    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: RnDInfo)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                IssueFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}