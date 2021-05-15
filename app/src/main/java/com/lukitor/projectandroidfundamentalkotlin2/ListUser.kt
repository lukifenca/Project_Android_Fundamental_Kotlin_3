package com.lukitor.projectandroidfundamentalkotlin2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.lukitor.projectandroidfundamentalkotlin2.databinding.ActivityListUserBinding
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class ListUser : AppCompatActivity() {
    lateinit var userAdapter: UserAdapter
    private lateinit var binding: ActivityListUserBinding
    val listData: ArrayList<User> = ArrayList<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListUserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.progressBar.visibility = View.INVISIBLE
        binding.rvList.setHasFixedSize(true)
        showRecyclerCardView()

        SearchData("jack")
        binding.btnsearch.setOnClickListener{ view ->
            SearchData(binding.textsearch.text.toString())
            showRecyclerCardView()
        }
        binding.imgSetting.setOnClickListener{ view ->startActivity(Intent(this, SettingActivity::class.java))}
        binding.imgListFav.setOnClickListener{ view ->startActivity(Intent(this, FavoriteActivity::class.java))}
    }

    private fun SearchData(Username:String){
        binding.progressBar.visibility= View.VISIBLE
        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token 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")
        asyncClient.addHeader("User-Agent", "request")
        asyncClient.get("https://api.github.com/search/users?q=$Username", object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                binding.progressBar.visibility= View.VISIBLE
                val result = String(responseBody)
                try {
                    val responseObject = JSONObject(result)
                    val jsonArray: JSONArray = responseObject.getJSONArray("items")
                    for (i in 0 until jsonArray.length()) {
                        listData.clear()
                        val itemObj = jsonArray.getJSONObject(i)
                        val username=itemObj.getString("login")
                        ListDataUser(username)
                        binding.progressBar.visibility= View.INVISIBLE
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@ListUser, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {binding.progressBar.visibility= View.INVISIBLE}
        })
    }

    private fun ListDataUser(Username:String) {
        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token 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")
        asyncClient.addHeader("User-Agent", "request")
        asyncClient.get("https://api.github.com/users/$Username", object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                val result = String(responseBody)
                try {
                    var isData:Boolean = true
                    for (i in listData.indices){if (listData[i].username.equals(Username)){isData = false}}
                    if(isData){
                        val responseObject = JSONObject(result)
                        var name=responseObject.getString("name")
                        if(name == "null"){name = "-"}
                        val imgurl =responseObject.getString("avatar_url")
                        val follower = responseObject.getString("followers_url")
                        val following = responseObject.getString("following_url")
                        var company =responseObject.getString("company")
                        var location =responseObject.getString("location")
                        var repository =responseObject.getString("html_url")
                        if (company == "null"){company = "-"}
                        if (location == "null"){location = "-"}
                        listData.add(User(Username,name,follower,following,repository,location,company,imgurl))
                        showRecyclerCardView()
                    }
                } catch (e: Exception) {Toast.makeText(this@ListUser, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {}
        })
    }

    private fun showRecyclerCardView() {
        binding.rvList.layoutManager = LinearLayoutManager(this)
        userAdapter = UserAdapter(listData)
        binding.rvList.adapter = userAdapter
        val inte = Intent(this, DetailUser::class.java)
        userAdapter.setOnItemClickCallback(object: UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                inte.putExtra("data", data)
                inte.putExtra("tipe", "1")
                startActivity(inte)
            }
        })
    }
}