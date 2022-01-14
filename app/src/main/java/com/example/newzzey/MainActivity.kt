package com.example.newzzey

import android.app.DownloadManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest


class MainActivity : AppCompatActivity(), NewsItemClicked {

    //connecting recycler view with adapter
    private lateinit var mAdapter :NewsListAdapter  //:NewsLisAdapter shows it is par4t of this class  //anyVariablewhich is accesible anywhere we put m infront of it // //makingInstanceOfclass NewsListAdapter   "this" means object of class Inside which you are present

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         val recyclerView = findViewById<RecyclerView>(R.id.recylerView);  //points towards recycler View In XML
         recyclerView.layoutManager=LinearLayoutManager(this)  // THIS IS LINEAR LAYOUT MANAGER THERE CAN ALSO BE GRID LAYOUT OR STAGGERED LAYOUT

//        //fetching the data // Legacy Code
//         val items = fetchData()
        //connecting recycler view with adapter
 //       val adapter   = NewsListAdapter(items,this)  //makingInstanceOfclass NewsListAdapter   "this" means object of class Inside which you are present

            fetchData()
            mAdapter=NewsListAdapter(this)
            recyclerView.adapter = mAdapter


    }

    /**   INITIAL ARRAY REQUEST**/
//    private fun fetchData(): ArrayList<String>{
//
//        val arrayList = ArrayList<String>()
//
//        for (i in 0 until 100)
//        {
//            arrayList.add("this is item $i")
//        }
//        return arrayList
//
//    }


        private fun fetchData(){

            val urls = "https://newsapi.org/v2/everything?domains=wsj.com&apiKey=09385b85e315446cacc0d972044565a7"
        val jsonObjectRequest = object:JsonObjectRequest(Request.Method.GET, urls, null,
            { response ->

                //EXTRACTING THE JSON ARRAY  (ARTICLES (ARTICLES IS ARRAY OF JSON OBJECTS)) FROM THE API URL

                  val newsJsonArray =  response.getJSONArray("articles")     // you can also write val newsJsonArray = it.getJSONArray("articles")
                  val newsArray = ArrayList<News>()  //Here you are passing the the object of class news as datatype YoU CaN Also try for pasing indivisual elements
                  for( i in 0 until newsJsonArray.length())
                  {
                      val newsJsonObject = newsJsonArray.getJSONObject(i)
                      val  news = News(
                          newsJsonObject.getString("title"),
                          newsJsonObject.getString("author"),
                          newsJsonObject.getString("url"),
                          newsJsonObject.getString("urlToImage")
                       )
                      newsArray.add(news)

                  }
                mAdapter.updateNews(newsArray)
            },
            { error ->
                           Log.d("Internet","Must Be Internet Issues")
            }
        )
          //THE EXTRA CODE WHICH I HAD TO ADD TO MAKE THE API RUN
           {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }

        }
        // Adding the obtained jsonObjectRequest to the Request Queue
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)


    }

// Doing The furthur Implementation of the Interface function When Item Gets Clicked
    override fun onItemClicked(Item: News) {

    val builder = CustomTabsIntent.Builder();
    val customTabsIntent = builder.build();
    customTabsIntent.launchUrl(this, Uri.parse(Item.url));
    }

}