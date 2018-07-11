package com.example.lg.day6

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
import com.example.lg.day6.mainFragment.adapter.MyCategoryRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_issue_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [CategoryFragment.OnListFragmentInteractionListener] interface.
 */
class CategoryFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private var mDatas: MutableList<RnDInfo> = mutableListOf()
    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
         return inflater.inflate(R.layout.fragment_category_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the adapter
        if (list is RecyclerView) {
            with(list) {
                layoutManager = LinearLayoutManager(context)
                adapter = MyCategoryRecyclerViewAdapter( mDatas, listener)
            }
        }
        swipe_refresh_layout.setOnRefreshListener(this)
        requestData() //실행이 안돼서 주석처리했는데 실행은 됨 근데 issue랑 동일한데 왜 얘만 안되지
    }

    override fun onRefresh() {//refreshing 했을 때 실행되는 함수
        requestData()
    }

    private fun requestData() {
        ApiUtil.mICategoryService!!.getRnDCategorList().enqueue(object : Callback<RnDInfoRes> {
            override fun onResponse(call: Call<RnDInfoRes>, response: Response<RnDInfoRes>) {
                try {
                    mDatas.clear()
                    response.body()?.list?.forEach { mDatas.add(it) }
                    list.adapter?.notifyDataSetChanged()

                    Toast.makeText(this@CategoryFragment.context, "onResponse_category", Toast.LENGTH_SHORT).show()
                } catch (ex: Exception) {
                } finally { swipe_refresh_layout.setRefreshing(false) }
            }

            override fun onFailure(call: Call<RnDInfoRes>, t: Throwable) {
                try {
                    mDatas.clear()
                    list.adapter?.notifyDataSetChanged()

                    Toast.makeText(this@CategoryFragment.context, "onFailure", Toast.LENGTH_SHORT).show()
                } catch (ex: Exception) {
                } finally { swipe_refresh_layout.setRefreshing(false) }
            }
        })
    }

    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: RnDInfo)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                CategoryFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}