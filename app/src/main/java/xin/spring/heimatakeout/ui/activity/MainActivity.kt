package xin.spring.heimatakeout.ui.activity

import android.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import xin.spring.heimatakeout.R
import xin.spring.heimatakeout.ui.fragment.HomeFragment
import xin.spring.heimatakeout.ui.fragment.MoreFragment
import xin.spring.heimatakeout.ui.fragment.OrderFragment
import xin.spring.heimatakeout.ui.fragment.UserFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val main_bottom_bar = findViewById<LinearLayout>(R.id.main_bottom_bar)

        initBottomBar()

        changeIndex(0)
    }

    // 初始化页面
    val fragments: List<Fragment> = listOf<Fragment>(HomeFragment(), OrderFragment(), UserFragment(), MoreFragment())


    private fun initBottomBar() {

        for(i in 0 until main_bottom_bar.childCount){
//            main_bottom_bar.getChildAt(i).setOnClickListener(object : View.OnClickListener{
//                override fun onClick(p0: View?) {
//
//                }
//            })
            main_bottom_bar.getChildAt(i).setOnClickListener { view -> changeIndex(i) }
        }
    }

    private fun changeIndex(index: Int) {
        for(i in 0 until main_bottom_bar.childCount){
            val child = main_bottom_bar.getChildAt(i)
            if(i == index){
                // 选中的， 禁用效果
                // child.isEnabled = false
                // 禁用子孙
                setEnable(child, false)
            }else{
                // 没选中的， enable = true
                // child.isEnabled = true
                // 启用子孙
                setEnable(child, true)
            }
        }
        fragmentManager.beginTransaction().replace(R.id.main_content, fragments.get(index)).commit()

    }

    private fun setEnable(child: View, isEnable: Boolean) {
        child.isEnabled = isEnable
        if(child is ViewGroup){
            for(i in 0 until child.childCount){
                child.getChildAt(i).isEnabled = isEnable
            }
        }
    }

}
