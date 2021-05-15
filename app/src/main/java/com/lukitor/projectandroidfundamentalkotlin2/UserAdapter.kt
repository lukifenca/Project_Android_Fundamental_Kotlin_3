package com.lukitor.projectandroidfundamentalkotlin2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UserAdapter(private val listHeroes: ArrayList<User>) : RecyclerView.Adapter<UserAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {this.onItemClickCallback = onItemClickCallback}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_cardview, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listHeroes[position])
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listHeroes[holder.adapterPosition]) }
    }
    override fun getItemCount(): Int {return listHeroes.size}

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var gambar: ImageView = itemView.findViewById(R.id.gambarcard)
        var username: TextView = itemView.findViewById(R.id.txtUsernameList)
        var name: TextView = itemView.findViewById(R.id.txtNameList)
        fun bind(user:User){
            username.text=user.username
            name.text=user.nama
            Glide.with(itemView.context).load(user.foto).into(gambar)
        }
    }
    interface OnItemClickCallback {fun onItemClicked(data: User)}
}