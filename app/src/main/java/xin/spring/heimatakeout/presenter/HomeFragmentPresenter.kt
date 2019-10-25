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
class HomeFragmentPresenter(val homeFragment: HomeFragment){

    val takeoutService: TakeoutService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        takeoutService = retrofit.create<TakeoutService>(TakeoutService::class.java)
    }

    fun getHomeInfo(){
        val homeCall = takeoutService.getHomeInfo()
        homeCall.enqueue(object : Callback<ResponseInfo>{
            override fun onFailure(call: Call<ResponseInfo>?, t: Throwable?) {
                Log.e("home","没有连上服务器")
            }

            override fun onResponse(call: Call<ResponseInfo>?, response: Response<ResponseInfo>?) {
                if(response == null){
                    Log.e("home","服务器没有成功返回")
                }else{
                    if(response.isSuccessful){
                        val responseInfo = response.body()
                        if("0".equals(responseInfo.code)){
                            val json = responseInfo.data
                            parserJson(json)
                        }else if("-1".equals(responseInfo.code)){
                            Log.e("home","空数据")
                        }
                    }else{
                        Log.e("home", "服务器代码错误")
                    }
                }
            }
        })

    }

    /**
     * 解析请求到服务器的数据
     */
    private fun parserJson(json: String) {
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
            // 无数据，异常页面
            //homeFragment.onHomeFailed()
        }

    }

}

