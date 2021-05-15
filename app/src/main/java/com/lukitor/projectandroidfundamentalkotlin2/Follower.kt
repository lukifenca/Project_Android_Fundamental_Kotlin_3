package com.lukitor.projectandroidfundamentalkotlin2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class Follower : Fragment() {
    private var username: String? =""
    private lateinit var listData :ArrayList<User>
    private lateinit var bar: ProgressBar
    private lateinit var rvHeroes: RecyclerView
    private lateinit var  adapter: FragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString("username")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bar=view.findViewById(R.id.barfollower)
        rvHeroes =view.findViewById(R.id.rvFollowing)
        listData = ArrayList()
        rvHeroes.setHasFixedSize(true)
        rvHeroes.layoutManager= LinearLayoutManager(context)
        showRecyclerCardView()
        getDataFollower(username!!)
    }

    private fun showRecyclerCardView() {
        adapter= FragmentAdapter(listData)
        rvHeroes.adapter=adapter
    }

    fun getDataFollower(username: String){
        listData = ArrayList()
        bar.visibility= View.VISIBLE
        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token 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")
        asyncClient.addHeader("User-Agent", "request")
        asyncClient.get("https://api.github.com/users/$username/followers", object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                bar.visibility= View.VISIBLE
                val result = String(responseBody)
                try {
                    val jsonArray: JSONArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        listData.clear()
                        val itemObj = jsonArray.getJSONObject(i)
                        val username = itemObj.getString("login")
                        DataUser(username)
                        bar.visibility= View.INVISIBLE
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {bar.visibility= View.INVISIBLE}
        })
    }
    private fun DataUser(uname:String) {
        bar.visibility= View.INVISIBLE
        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token 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")
        asyncClient.addHeader("User-Agent", "request")
        asyncClient.get("https://api.github.com/users/$uname", object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                val result = String(responseBody)
                try {
                    var isData:Boolean = true
                    for (i in listData.indices){if (listData[i].username.equals(uname)){isData = false}}
                    if(isData){
                        val responseObject = JSONObject(result)
                        val username=responseObject.getString("login")
                        var name=responseObject.getString("name")
                        if(name == "null"){name = "-"}
                        val imgurl =responseObject.getString("avatar_url")
                        val follower = responseObject.getString("followers_url")
                        val following = responseObject.getString("following_url")
                        var company = responseObject.getString("company")
                        var location = responseObject.getString("location")
                        var repository = responseObject.getString("html_url")
                        if (company == "null"){company = "-"}
                        if (location == "null"){location = "-"}
                        listData.add(User(username,name,follower,following,repository,location,company,imgurl))
                        showRecyclerCardView()
                    }

                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {}
        })
    }
    companion object {
        @JvmStatic
        fun newInstance(username: String) = Follower().apply {arguments = Bundle().apply {putString("username", username)}}
    }
}