package xin.spring.heimatakeout.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.find
import xin.spring.heimatakeout.R

/**
 * @author glsite.com
 * @version $
 * @des
 * @updateAuthor $
 * @updateDes
 */
class HomeRvAdapter(val context : Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var mDatas : ArrayList<String> = ArrayList()

    fun setData(data : ArrayList<String>){
        this.mDatas = data
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as HomeItemHolder).bindData(mDatas[position])
    }

    override fun getItemCount(): Int {
        return mDatas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return  HomeItemHolder(View.inflate(context, R.layout.item_home_common,null))
    }

    inner class HomeItemHolder(item: View) : RecyclerView.ViewHolder(item){

        val textView : TextView

        init {
            textView = item.find(R.id.tv)
        }

        fun bindData(data : String){
            textView.text = data
        }

    }

}
