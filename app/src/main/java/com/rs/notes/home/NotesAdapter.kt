package com.rs.notes.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.rs.notes.databinding.LayoutNotesBinding

class NotesAdapter(
    private val context: Context,
) : RecyclerView.Adapter<NotesAdapter.MyViewHolder>()  {


    lateinit var list: ArrayList<*>
    lateinit var receivedData: String
    var keys = arrayOf("k1", "k2", "k3", "k4", "k5")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(LayoutNotesBinding.inflate(inflater, parent, false))
    }


    override fun getItemCount(): Int {
        return if (::list.isInitialized) list.size else 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) =  holder.bind(position)

    fun setData(
        list: ArrayList<*>,
        receivedData: String,
    ) {
        this.list = list
        this.receivedData = receivedData
        notifyDataSetChanged()
    }


    inner class MyViewHolder(val binding: LayoutNotesBinding) :
        RecyclerView.ViewHolder(binding.root){

        init {

        }

        fun bind(pos: Int) {

            val model = list.get(pos) as java.util.HashMap<*, *>
            with(binding) {


               var title= model[keys[1]] as String?
                var desc= model[keys[2]] as String?

                ivEdit.setOnClickListener {
                    if ( tvUpdatedAt.context is HomeActivity) {
                        (tvUpdatedAt.context as HomeActivity).edit(model[keys[0]] as String?,
                            model[keys[1]] as String?,
                            model[keys[2]] as String?, model[keys[3]] as String?, model[keys[4]] as String?
                        )
                    }
                }

                ivDelete.setOnClickListener {
                    if ( tvUpdatedAt.context is HomeActivity) {
                        (tvUpdatedAt.context as HomeActivity).delete(model[keys[0].toString()] as String?)
                    }
                }

//                if ( model!=null){
//                 //   val hm2 = list.get(0) as java.util.HashMap<*, *>
//
//                    tvTitle.text = model[keys[1]] as CharSequence?
//                    tvDesc.text = model[keys[2]] as CharSequence?
//                    tvCreateAt.text = "Created At \n"+model[keys[3]] as CharSequence?
//
//                  if (!model[keys[4]]?.equals("")!!){
//                      tvUpdatedAt.text ="Updated At \n"+model[keys[4]] as CharSequence?
//                  }
//
//
//
//
//                }


                if (!receivedData.equals("")){
                    if (title!!.contains(receivedData) || desc!!.contains(receivedData)){

                        tvTitle.text = model[keys[1]] as CharSequence?
                        tvDesc.text = model[keys[2]] as CharSequence?
                        tvCreateAt.text = "Created At \n"+model[keys[3]] as CharSequence?

                        if (!model[keys[4]]?.equals("")!!){
                            tvUpdatedAt.text ="Updated At \n"+model[keys[4]] as CharSequence?
                        }
                    }
                    else {

                        root.visibility = View.GONE
                        val params: ViewGroup.LayoutParams = root.getLayoutParams()
                        params.height = 0
                        params.width = 0
                        root.setLayoutParams(params)
                    }
                }
                else {

                    tvTitle.text = model[keys[1]] as CharSequence?
                    tvDesc.text = model[keys[2]] as CharSequence?
                    tvCreateAt.text = "Created At \n"+model[keys[3]] as CharSequence?

                    if (!model[keys[4]]?.equals("")!!){
                        tvUpdatedAt.text ="Updated At \n"+model[keys[4]] as CharSequence?
                    }
                }



            }
        }

    }


}