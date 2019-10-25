package xin.spring.heimatakeout

import android.app.Application
import com.lzy.okgo.OkGo

/**
 * @author glsite.com
 * @version $
 * @des
 * @updateAuthor $
 * @updateDes
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // OkGo
        OkGo.getInstance().init(this);
    }
}
