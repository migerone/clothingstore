package com.example.shop.adapters

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.shop.R
import com.example.shop.SingleActivity
import com.example.shop.models.Product

class RecyclerAdapter(var context: Context)://When you want to toast smthg without intent or activities
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){
    //View holder holds the views in single item.xml

    var productList : List<Product> = listOf() // empty product list


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
    //Note below code returns above class and pass the view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_item, parent, false)
        return ViewHolder(view)
    }
    //so far item view is same as single item
    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val title = holder.itemView.findViewById(R.id.title) as TextView
        val desc = holder.itemView.findViewById(R.id.desc) as TextView
        val cost = holder.itemView.findViewById(R.id.cost) as TextView
        val image = holder.itemView.findViewById(R.id.image) as ImageView

        //bind
        val item = productList[position]
        title.text = item.product_name
        desc.text = item.product_desc
        cost.text = item.product_cost

        Glide.with(context).load(item.image_url)
            .apply(RequestOptions().centerCrop())
            .into(image)
        //image.setImageResource(item.image)

        holder.itemView.setOnClickListener{
            val prefs: SharedPreferences = context.getSharedPreferences("shop",Context.MODE_PRIVATE)


            val editor:SharedPreferences.Editor= prefs.edit()
            editor.putString("prod_name",item.product_name)
            editor.putString("prod_desc",item.product_desc)
            editor.putString("prod_cost",item.product_cost)
            editor.putString("img_url",item.image_url)
            editor.apply()
            val i = Intent(context, SingleActivity::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)

        }

        //        holder.itemView.setOnClickListener {
        //
        //
        //
        //        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }


    //we will call this function on Loopj response
    fun setProductListItems(productList: List<Product>){
        this.productList = productList
        notifyDataSetChanged()
    }
}//end class

