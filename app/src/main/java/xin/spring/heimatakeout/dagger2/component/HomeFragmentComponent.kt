package xin.spring.heimatakeout.dagger2.component

import dagger.Component
import xin.spring.heimatakeout.dagger2.module.HomeFragmentModule
import xin.spring.heimatakeout.ui.fragment.HomeFragment

/**
 * @author glsite.com
 * @version $
 * @des
 * @updateAuthor $
 * @updateDes
 */
@Component(modules = arrayOf(HomeFragmentModule::class)) interface HomeFragmentComponent{

    fun inject(homeFragment: HomeFragment)

}
