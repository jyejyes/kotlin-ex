package com.example.lg.day6.mainFragment.adapter

import android.content.Context
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.lg.day6.BR
import com.example.lg.day6.data.RnDInfo

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */

class MyIssueRecyclerViewAdapter(private val mContext: Context, private val mValues: List<RnDInfo>)
    : RecyclerView.Adapter<MyIssueRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ViewDataBinding = FragmentIssueBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.setBinding(item)
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(private val mBinding: ViewDataBinding) : RecyclerView.ViewHolder(mBinding.root) {
        fun onItemClick(v: View, item: RnDInfo) {
            Toast.makeText(v.context, "Issue Item Click : $item", Toast.LENGTH_SHORT).show()
        }

        fun setBinding(item: RnDInfo) {
            mBinding.setVariable(BR.item,item)
            mBinding.setVariable(BR.listener, this)
        }
    }
}
//
//class MyIssueRecyclerViewAdapter(
//        private val mValues: Context,
//        private val mListener: MutableList<RnDInfo>)
//    : RecyclerView.Adapter<MyIssueRecyclerViewAdapter.ViewHolder>() {
//
//    private val mOnClickListener: View.OnClickListener
//
//    init {
//        mOnClickListener = View.OnClickListener { v ->
//            val item = v.tag as RnDInfo
//            // Notify the active callbacks interface (the activity, if the fragment is attached to
//            // one) that an item has been selected.
//            mListener?.onListFragmentInteraction(item)
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context)
//                .inflate(R.layout.fragment_issue, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = mValues[position]
//        holder.mIdView.text = item.title
//        holder.mContentView.text = item.cat
//
//        with(holder.mView) {
//            tag = item
//            setOnClickListener(mOnClickListener)
//        }
//    }
//
//    override fun getItemCount(): Int = mValues.size
//
//    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
//        val mIdView: TextView = mView.item_number
//        val mContentView: TextView = mView.content
//
//        override fun toString(): String {
//            return super.toString() + " '" + mContentView.text + "'"
//        }
//    }
//}
