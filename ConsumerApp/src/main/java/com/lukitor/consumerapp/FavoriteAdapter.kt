package com.lukitor.consumerapp

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lukitor.consumerapp.database.DatabaseContract
import com.lukitor.consumerapp.entity.Favorite

class FavoriteAdapter(private val listHeroes: ArrayList<Favorite>) : RecyclerView.Adapter<FavoriteAdapter.ListViewHolder>() {
    private lateinit var uriWithUsername: Uri
    var listData = ArrayList<Favorite>()
    set(listNotes) {
        if (listNotes.size > 0) {this.listData.clear()}
        this.listData.addAll(listNotes)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_cardfavorite, parent, false)
        return ListViewHolder(view)
    }
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listHeroes.get(position))
    }
    override fun getItemCount(): Int {return listHeroes.size}

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var gambar: ImageView = itemView.findViewById(R.id.gambarcard1)
        var username: TextView = itemView.findViewById(R.id.txtUsernameList1)
        var name: TextView = itemView.findViewById(R.id.txtNameList1)
        var unFav: ImageView = itemView.findViewById(R.id.imgunFav)
        fun bind(user: Favorite){
            username.text=user.username
            name.text=user.nama
            Glide.with(itemView.context).load(user.imgURL).into(gambar)
            unFav.setOnClickListener{
                uriWithUsername = Uri.parse(DatabaseContract.FavoriteColumns.CONTENT_URI.toString() + "/" + user?.id)
                itemView.context.contentResolver.delete(uriWithUsername, null, null)
                listHeroes.remove(user)
                notifyDataSetChanged()
                Toast.makeText(itemView.context, "Success Remove User From the Favorite List", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
