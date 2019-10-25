package xin.spring.heimatakeout.ui.adapter

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.TextSliderView
import com.daimajia.slider.library.Tricks.ViewPagerEx.OnPageChangeListener
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find
import xin.spring.heimatakeout.R
import xin.spring.heimatakeout.model.beans.Seller

/**
 * @author glsite.com
 * @version $
 * @des
 * @updateAuthor $
 * @updateDes
 */
class HomeRvAdapter(val context : Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object{
        val TYPE_TITLE = 0
        val TYPE_SELLER = 1
    }

    var mDatas : ArrayList<Seller> = ArrayList<Seller>()

    fun setData(data: ArrayList<Seller>){
        this.mDatas = data
        notifyDataSetChanged()
    }

    /**
     * 不同position对应不同类型
     */
    override fun getItemViewType(position: Int): Int {
        if(position == 0){
            return TYPE_TITLE
        }else{
            return TYPE_SELLER
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        when(viewType){
            TYPE_TITLE -> (holder as TitleItemHolder).bindData("我是头大哥------------")
            TYPE_SELLER -> (holder as SellerItemHolder).bindData(mDatas[position - 1])
        }

    }

    override fun getItemCount(): Int {
        if(mDatas.size > 0){
            return mDatas.size + 1
        }else{
            return 0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType){
            TYPE_TITLE -> return  TitleItemHolder(View.inflate(context, R.layout.item_title,null))
            TYPE_SELLER -> return  SellerItemHolder(View.inflate(context, R.layout.item_seller,null))
            else -> return  TitleItemHolder(View.inflate(context, R.layout.item_home_common,null))
        }

    }

    var url_maps : HashMap<String,String> = HashMap<String, String>()

    /**
     * 头部
     */
    inner class TitleItemHolder(item: View) : RecyclerView.ViewHolder(item){

        var sliderLayout: SliderLayout

        init {
            sliderLayout = item.findViewById(R.id.slider)
        }

        fun bindData(data : String){
            if(url_maps.size == 0){
                url_maps.put("Hannibal", "https://m.360buyimg.com/mobilecms/s700x280_jfs/t1/31162/17/1128/101786/5c46ead8E22ee9740/f66061da227c1965.jpg!cr_1125x445_0_171!q70.jpg.dpg");
                url_maps.put("Big Bang Theory", "https://m.360buyimg.com/mobilecms/s700x280_jfs/t1/57267/26/14011/144429/5daffe93E40d0f031/a348a5c703a31657.jpg!cr_1125x445_0_171!q70.jpg.dpg");
                url_maps.put("House of Cards", "https://m.360buyimg.com/mobilecms/s700x280_jfs/t1/104640/28/366/119386/5dac4860E8d9f96df/0bbf771c2a26cf1d.jpg!cr_1125x445_0_171!q70.jpg.dpg");
                url_maps.put("Game of Thrones", "https://m.360buyimg.com/mobilecms/s700x280_jfs/t1/75964/2/13749/86827/5db14d08Ecab1f68f/6e4b81003e1e904f.jpg!cr_1125x445_0_171!q70.jpg.dpg");
                for((key, value) in url_maps){
                    var textSliderView: TextSliderView = TextSliderView(context)
                    textSliderView.description(key).image(value)
                    sliderLayout.addSlider(textSliderView)
                }
            }
            sliderLayout.addOnPageChangeListener(object : OnPageChangeListener{

                override fun onPageScrollStateChanged(state: Int) {}

                override fun onPageScrolled(position: Int,positionOffset: Float,positionOffsetPixels: Int) {}

                override fun onPageSelected(position: Int) {
                    Log.w("当前页：", position.toString())
                }

            })
        }

    }

    /**
     * 商家
     */
    inner class SellerItemHolder(item: View) : RecyclerView.ViewHolder(item){

        val tvTtile : TextView
        val ivLogo : ImageView
        val ivScore : RatingBar
        val tvSale: TextView
        val tvSendPrice: TextView
        val tvDistance: TextView
        lateinit var mSeller: Seller

        init {
            tvTtile = item.find(R.id.tv_title)
            ivLogo = item.find(R.id.seller_logo);
            ivScore = item.find(R.id.ratingBar);

            tvSale = item.find(R.id.tv_home_sale)
            tvSendPrice = item.find(R.id.tv_home_send_price)
            tvDistance = item.find(R.id.tv_home_distance)
        }

        fun bindData(seller : Seller){
            Log.e("ssss", seller.name)
            tvTtile.text = seller.name
            Picasso.with(context).load(seller.icon).into(ivLogo)
            ivScore.rating = seller.score.toFloat()
            tvSale.text = "月售${seller.sale}单"
            tvSendPrice.text = "￥${seller.sendPrice}起送/配送费￥${seller.deliveryFee}"
            tvDistance.text = seller.distance
        }

    }

}
