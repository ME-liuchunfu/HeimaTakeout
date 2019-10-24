package xin.spring.heimatakeout.ui.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import xin.spring.heimatakeout.R


/**
 * @author glsite.com
 * @version $
 * @des
 * @updateAuthor $
 * @updateDes
 */
class HomeFragment: Fragment(){

    override fun onCreateView(inflater: LayoutInflater?,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = View.inflate(activity, R.layout.fragment_home, null);
        return view
    }

}
