package com.lukitor.projectandroidfundamentalkotlin2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FragmentAdapter(private val listUser:ArrayList<User>) : RecyclerView.Adapter<FragmentAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_cardview,parent,false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int { return listUser.size}

    override fun onBindViewHolder(holder: FragmentAdapter.ListViewHolder, position: Int) {holder.bind(listUser.get(position))}
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView =itemView.findViewById(R.id.gambarcard)
        var txtNama: TextView =itemView.findViewById(R.id.txtNameList)
        var txtUsername: TextView =itemView.findViewById(R.id.txtUsernameList)
        fun bind(user:User){
            txtNama.text=user.nama
            Glide.with(itemView.context).load(user.foto).into(imgPhoto)
            txtUsername.text=user.username
        }
    }
}