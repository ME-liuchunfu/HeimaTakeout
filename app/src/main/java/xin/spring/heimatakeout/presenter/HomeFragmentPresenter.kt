package xin.spring.heimatakeout.presenter

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import xin.spring.heimatakeout.Api
import xin.spring.heimatakeout.model.beans.Seller
import xin.spring.heimatakeout.model.net.ResponseInfo
import xin.spring.heimatakeout.model.net.TakeoutService
import xin.spring.heimatakeout.ui.fragment.HomeFragment

/**
 * @author glsite.com
 * @version $
 * @des
 * @updateAuthor $
 * @updateDes
 */
class HomeFragmentPresenter(val homeFragment: HomeFragment) : NetPresenter(){

    fun getHomeInfo(){
        val homeCall = takeoutService.getHomeInfo()
        homeCall.enqueue(callback)
    }

    /**
     * 解析请求到服务器的数据
     */
    override fun parserJson(json: String) {
        val gson = Gson()
        Log.e("home", gson.toJson(json))
        var jsonObject = JSONObject(json)
        var nearby = jsonObject.getString("nearbySellerList")
        Log.e("home", nearby)
        val nearBySellers : List<Seller> = gson.fromJson(nearby, object : TypeToken<List<Seller>>(){}.type)

        var other = jsonObject.getString("otherSellerList")
        Log.e("home", other)
        val otherSellers : List<Seller> = gson.fromJson(other, object : TypeToken<List<Seller>>(){}.type)
        if(nearBySellers.isNotEmpty() || otherSellers.isNotEmpty()){
            // 有数据，成功页面
            homeFragment.onHomeSuccess(nearBySellers, otherSellers)
        }else{
            // 无数据，异常页面
            homeFragment.onHomeFailed()
        }

    }

}

