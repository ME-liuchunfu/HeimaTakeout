package xin.spring.heimatakeout.model.net

import retrofit2.Call
import retrofit2.http.GET

/**
 * @author glsite.com
 * @version $
 * @des
 * @updateAuthor $
 * @updateDes
 */
interface TakeoutService{

    @GET("home")
    fun getHomeInfo(): Call<ResponseInfo>

}
