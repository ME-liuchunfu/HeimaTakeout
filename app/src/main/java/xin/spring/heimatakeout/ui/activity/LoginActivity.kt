package xin.spring.heimatakeout.ui.activity

import android.annotation.SuppressLint
import android.os.*
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import cn.smssdk.EventHandler
import cn.smssdk.SMSSDK
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import xin.spring.heimatakeout.R
import xin.spring.heimatakeout.presenter.LoginActivityPresenter
import xin.spring.heimatakeout.utils.SMSUtil

/**
 * @author glsite.com
 * @version $
 * @des
 * @updateAuthor $
 * @updateDes
 */

class LoginActivity: AppCompatActivity(){

    // 创建EventHandler对象
    val eventHandler = object : EventHandler() {
        override fun afterEvent(event: Int, result: Int, data: Any) {
            if (data is Throwable) {
                val msg = data.message
                Log.e("sms", msg)
            } else {
                if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    Log.e("sms", "获取验证码成功")
                } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    Log.e("sms", "提交验证码成功。。。")
//                    var phone = et_user_phone.text.toString().trim()
//                    loginActivityPresenter.loginByPhone(phone)
                }
            }
        }
    }

    lateinit var loginActivityPresenter: LoginActivityPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginActivityPresenter = LoginActivityPresenter(this)

        initListener()

        // 注册监听器
        SMSSDK.registerEventHandler(eventHandler)
    }

    private fun initListener() {
        iv_user_back.setOnClickListener { finish() }
        tv_user_code.setOnClickListener {
            // 获取验证码
            var phone = et_user_phone.text.toString().trim()
            // 验证手机号码正确性
            if(SMSUtil.judgePhoneNums(this, phone)) {
                SMSSDK.getVerificationCode("86", phone)

                // 开启倒计时
                tv_user_code.isEnabled = false
                Thread(CutDownTask()).start()
            }
        }
        tv_login.setOnClickListener {
            // 提交验证码
            var phone = et_user_phone.text.toString().trim()
            var code = et_user_code.text.toString().trim()
//            if (SMSUtil.judgePhoneNums(this, phone) && !TextUtils.isEmpty(code)) {
//                SMSSDK.submitVerificationCode("86", phone, code)
//            }
            //登录外卖服务器
            loginActivityPresenter.loginByPhone(phone)
        }
    }

    val handler = @SuppressLint("HandlerLeak")
    object : Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg!!.what){
                TIME_MINUS -> tv_user_code.text = "剩余时间(${time})秒"
                TIME_IS_OUT -> {
                    tv_user_code.isEnabled = true
                    tv_user_code.text = "点击重发"
                    time = 60
                }
            }
        }
    }

    var time = 60

    companion object{
        val TIME_MINUS : Int = -1
        val TIME_IS_OUT : Int = 0
    }

    /**
     * 倒计时
     */
    inner class CutDownTask: Runnable{
        override fun run() {
            while (time > 0){
                // 刷新剩余时间, 当前子线程，使用handler
                handler.sendEmptyMessage(TIME_MINUS)
                SystemClock.sleep(999)
                time--
            }
            handler.sendEmptyMessage(TIME_IS_OUT)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SMSSDK.unregisterEventHandler(eventHandler)
    }

    fun onLoginSuccess() {
        finish()
        toast("登录成功")
    }

    fun onLoginFailed() {
        toast("登录失败")
    }

}