package com.example.newzzey

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class NewsListAdapter( private val listner : NewsItemClicked /*private val items : ArrayList<News>This sed to be here earlier when we were directly passing the values without using api since we are using pi now i=data gets loaded first then data is passed to adapter hence we have adopted diffrent technique */) : RecyclerView.Adapter<newsViewHolder>()
{
    private val items : ArrayList<News>  = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newsViewHolder {

         val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item,parent,false) // converts the XML layout file in View form

           val newsViewHolderObject = newsViewHolder(view) /***/

         //for handling button clicks and for telling activity to do what when button clicked
         view.setOnClickListener {

             //listner is the object of the MainActvity Class
             //Here we call the onItemClicked frunction just to pass the current Item In Adapter to the Item datatype this function furthur implementation is continued in MainActivity
             listner.onItemClicked(items[newsViewHolderObject.absoluteAdapterPosition])  /***/ //GIVES THE POSTION AT WHICH THE VIEWhOLDER IS CURRENTLY PRESENT
         }
         return newsViewHolderObject /*return newsViewHolder(view)* THIS IS WRONG IN THE CURRENT WAY BECAUSE WE HAVE ALREADY MADE OBJECT OF THE CLASS*/
    }



    override fun onBindViewHolder(holder: newsViewHolder, position: Int) {  // Binds the itemXML File with dataComing From MainActivity //hERE THE holder is  AN OBJECT OF CLASS newsViewHolder

        val currentItem = items[position]
        holder.titleView.text= currentItem.title  //currentItem here is equivalent to the news variable in main Activity
        holder.author.text=currentItem.author

        //Passing the Image to the image view by glide Library /**LEARN MORE ABOUT THE CONTEXT PASSED IM THE WITH FX**/
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.newsImage)

    }


    override fun getItemCount(): Int {

        return  items.size
    }

    fun updateNews(updatedNews : ArrayList<News>)
    {
        items.clear()
        items.addAll(updatedNews)

        /// Telling Adapter that now you have to modify your contents
        notifyDataSetChanged()
        // Whe this fx gets called all the 3 inbuilt fx of NewsListAdapter get recreated
    }
}



class newsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  //extending the newsViewHolder class with ViewHolder Class
{
     val titleView = itemView.findViewById<TextView>(R.id.title)  //itemview is just a reference to the xml file which now has been converted to a View
                                                                  // titleView is points towrsd the xml file
     val newsImage = itemView.findViewById<ImageView>(R.id.newsImage)
     val author = itemView.findViewById<TextView>(R.id.authorName)
}


/***/
interface NewsItemClicked {

    fun onItemClicked(Item :News)
}





// Doubts SOLVED

//1) why we had to write itemView.findViewById instead of just find view by ID
//2) is it possible to return a class like this


//
///** My  CUSTOM DECLRATION **/
//
//  class newsAdapter(val items:ArrayList<String>):RecyclerView.Adapter<newsAdapter.newsViewHolder>()  /**IN THIS CODE IT IS newsAdapter.newsViewHolder INSTEAD OF JUST
//  newsViewHolder  HRERE WE HAVE DEFINED newsViewHolder INSIDE THE newsAdapter SO TO SHOW THAT IT IS SUBCLASS THIS SYNTAX WA REQUIRED*/
//  {
//
//      class newsViewHolder(Itemview :View):RecyclerView.ViewHolder(Itemview)
//      {
//          val titleView = Itemview.findViewById<TextView>(R.id.title)
//      }
//
//      /******************************************************************************************************************************************/
//
//      /*return type is newsViewHolder is basically the  implicit object/instance of class newsViewHolder */
//      /* when we write the statement return newsViewHolder(View) we basically return the class in form of implicit instance news view holder*/
//
//      /******************************************************************************************************************************************/
//
//      override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newsViewHolder {
//          val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item,parent,false)
//          return  newsViewHolder(view)
//      }
//
//      override fun onBindViewHolder(holder: newsViewHolder, position: Int) {
//          holder.titleView.text = items[position]
//      }
//
//      override fun getItemCount(): Int {
//          return  items.size
//      }
//
//  }





































