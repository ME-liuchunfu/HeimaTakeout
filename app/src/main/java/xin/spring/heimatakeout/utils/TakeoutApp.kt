package xin.spring.heimatakeout.utils

import com.mob.MobApplication
import xin.spring.heimatakeout.model.beans.CacheSelectedInfo
import xin.spring.heimatakeout.model.beans.User
import java.util.concurrent.CopyOnWriteArrayList

/**
 * @author glsite.com
 * @version $
 * @des
 * @updateAuthor $
 * @updateDes
 */
class TakeoutApp: MobApplication(){

    override fun onCreate() {
        super.onCreate()
        sInstance = this
        sUser.id = -1 //未登录用户id=-1
        //JPushInterface.setDebugMode(true);
        //JPushInterface.init(this);
    }

    //点餐缓存集合
    val infos: CopyOnWriteArrayList<CacheSelectedInfo> = CopyOnWriteArrayList()

    fun queryCacheSelectedInfoByGoodsId(goodsId: Int): Int {
        var count = 0
        for (i in 0..infos.size - 1) {
            val (_, _, goodsId1, count1) = infos[i]
            if (goodsId1 == goodsId) {
                count = count1
                break
            }
        }
        return count
    }

    fun queryCacheSelectedInfoByTypeId(typeId: Int): Int {
        var count = 0
        for (i in 0..infos.size - 1) {
            val (_, typeId1, _, count1) = infos[i]
            if (typeId1 == typeId) {
                count = count + count1
            }
        }
        return count
    }

    fun queryCacheSelectedInfoBySellerId(sellerId: Int): Int {
        var count = 0
        for (i in 0..infos.size - 1) {
            val (sellerId1, _, _, count1) = infos[i]
            if (sellerId1 == sellerId) {
                count = count + count1
            }
        }
        return count
    }

    fun addCacheSelectedInfo(info: CacheSelectedInfo) {
        infos.add(info)
    }

    fun clearCacheSelectedInfo(sellerId: Int) {
        val temp = ArrayList<CacheSelectedInfo>()
        for (i in 0..infos.size - 1) {
            val info = infos[i]
            if (info.sellerId == sellerId) {
//                infos.remove(info)
                temp.add(info)
            }
        }
        infos.removeAll(temp)
    }

    fun deleteCacheSelectedInfo(goodsId: Int) {
        for (i in 0..infos.size - 1) {
            val info = infos[i]
            if (info.goodsId == goodsId) {
                infos.remove(info)
                break
            }
        }
    }

    fun updateCacheSelectedInfo(goodsId: Int, operation: Int) {
        for (i in 0..infos.size - 1) {
            var info = infos[i]
            if (info.goodsId == goodsId) {
                when (operation) {
                    Constants.ADD -> info.count = info.count + 1
                    Constants.MINUS -> info.count = info.count - 1
                }

            }
        }
    }

    companion object {
        var sUser: User = User()
        lateinit var sInstance: TakeoutApp
    }



}