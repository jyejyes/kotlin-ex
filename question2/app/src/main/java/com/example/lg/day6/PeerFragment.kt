package com.example.lg.day6

import android.content.Context
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

import com.example.lg.day6.mainFragment.adapter.MyPeerRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_issue_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [PeerFragment.OnListFragmentInteractionListener] interface.
 */
class PeerFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private var mDatas: MutableList<RnDInfo> = mutableListOf()
    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.fragment_peer_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the adapter
        if (list is RecyclerView) {
            with(list) {
                layoutManager = LinearLayoutManager(context)
                adapter = MyPeerRecyclerViewAdapter(mDatas, listener)
            }
        }
        swipe_refresh_layout.setOnRefreshListener(this)
        requestData()
    }

    override fun onRefresh() {
        requestData()
        Toast.makeText(this.context, "hello Peer", Toast.LENGTH_SHORT).show()
    }

    private fun requestData() {
        ApiUtil.mPeerService!!.getRnDPeerList().enqueue(object : Callback<RnDInfoRes> {
            override fun onResponse(call: Call<RnDInfoRes>, response: Response<RnDInfoRes>) {
                try {
                    mDatas.clear()
                    response.body()?.list?.forEach { mDatas.add(it) }
                    list.adapter?.notifyDataSetChanged()

                    Toast.makeText(this@PeerFragment.context, "onResponse_peer", Toast.LENGTH_SHORT).show()
                } catch (wx: Exception) {
                } finally { swipe_refresh_layout.setRefreshing(false) }
            }

            override fun onFailure(call: Call<RnDInfoRes>, t:Throwable){
                try{
                    mDatas.clear()
                    list.adapter?.notifyDataSetChanged()
                    Toast.makeText(this@PeerFragment.context,"onfail_peer",Toast.LENGTH_SHORT).show()
                }catch (wx:Exception){
                }finally { swipe_refresh_layout.setRefreshing(false) }
            }
        })

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: RnDInfo)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                PeerFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}
