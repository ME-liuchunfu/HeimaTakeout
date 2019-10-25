package xin.spring.heimatakeout.dagger2.module

import dagger.Module
import dagger.Provides
import xin.spring.heimatakeout.presenter.HomeFragmentPresenter
import xin.spring.heimatakeout.ui.fragment.HomeFragment

/**
 * @author glsite.com
 * @version $
 * @des
 * @updateAuthor $
 * @updateDes
 */
@Module class HomeFragmentModule(val homeFragment: HomeFragment){

    @Provides fun provideHomeFragmentPresenter(): HomeFragmentPresenter{
        return HomeFragmentPresenter(homeFragment)
    }

}
