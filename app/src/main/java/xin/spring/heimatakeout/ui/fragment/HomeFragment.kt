package xin.spring.heimatakeout.ui.fragment

import android.app.Fragment
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import xin.spring.heimatakeout.R
import xin.spring.heimatakeout.dagger2.component.DaggerHomeFragmentComponent
import xin.spring.heimatakeout.dagger2.component.HomeFragmentComponent
import xin.spring.heimatakeout.dagger2.module.HomeFragmentModule
import xin.spring.heimatakeout.model.beans.Seller
import xin.spring.heimatakeout.presenter.HomeFragmentPresenter
import xin.spring.heimatakeout.ui.adapter.HomeRvAdapter
import javax.inject.Inject


/**
 * @author glsite.com
 * @version $
 * @des
 * @updateAuthor $
 * @updateDes
 */
class HomeFragment: Fragment(){

    lateinit var homeRvAdapter : HomeRvAdapter
    
    lateinit var rvHome :RecyclerView

    @Inject
    lateinit var homeFragmentPresenter: HomeFragmentPresenter

    override fun onCreateView(inflater: LayoutInflater?,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = View.inflate(activity, R.layout.fragment_home, null);
        rvHome = view.find<RecyclerView>(R.id.rv_home)
        rvHome.layoutManager = LinearLayoutManager(activity) // 从上到下的列表视图
        homeRvAdapter = HomeRvAdapter(activity)
        rvHome.adapter = homeRvAdapter
        // TODO：解耦 view层和p层，通过使用dagger2（基于注解的依赖注入） 生成HomeFragmentPresenter
        // homeFragmentPresenter = HomeFragmentPresenter(this)
        DaggerHomeFragmentComponent.builder().homeFragmentModule(HomeFragmentModule(this)).build().inject(this)
        distance = 120.dp2px()
        return view
    }

    /**
     * 把转化功能添加到Int类中作为扩展函数
     */
    fun Int.dp2px(): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            toFloat(), resources.displayMetrics).toInt()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
    }

    var sum : Int = 0
    var distance : Int = 0

    var alpha : Int = 55

    private fun initData() {
        homeFragmentPresenter.getHomeInfo()
    }

    val allList : ArrayList<Seller> = ArrayList<Seller>()

    fun onHomeSuccess(nearBySellers: List<Seller>, otherSellers: List<Seller>) {
        allList.clear()
        Log.e("home", "==============================")
        Log.e("home", nearBySellers.toString())
        Log.e("home", otherSellers.toString())
        allList.addAll(nearBySellers)
        allList.addAll(otherSellers)
        homeRvAdapter.setData(allList)

        // 有数据可以滚动才可以监听滚动事件
        rvHome.setOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                sum += dy
                Log.e("home", "sum:$sum")
                if(sum > distance){
                    alpha = 255
                }else{
                    alpha = (sum * 200 / distance).toInt()
                }
                Log.e("home", "alpha:$alpha")
                ll_title_container.setBackgroundColor(Color.argb(alpha,0x31,0x90,0xe8))
            }

        })
        toast("成功拿到数据")
    }

    fun onHomeFailed() {
        toast("获取网络数据失败")
    }

}
