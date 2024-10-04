package lildwagz.com.hollyquran

import android.app.Application
import android.os.Bundle
import com.lzx.starrysky.StarrySky
import com.lzx.starrysky.StarrySky.apply
import com.lzx.starrysky.StarrySky.setIsOpenNotification
import com.lzx.starrysky.notification.INotification
import com.lzx.starrysky.notification.NotificationConfig

class QuranApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        val notificationConfig = NotificationConfig.create {
            targetClass { "lildwagz.com.hollyquran.NotificationReceiver" }
            targetClassBundle {
                val bundle = Bundle()
                bundle.putString("title", "我是点击通知栏转跳带的参数")
                bundle.putString("targetClass", "lildwagz.com.hollyquran.MainActivity")
                //参数自带当前音频播放信息，不用自己传
                return@targetClassBundle bundle
            }
            pendingIntentMode { NotificationConfig.MODE_BROADCAST }
        }


        StarrySky.init(this@QuranApplication).apply {
            setIsOpenNotification(true)
            setNotificationSwitch(true)
            setNotificationConfig(notificationConfig)
            setNotificationType(INotification.SYSTEM_NOTIFICATION)
            apply()
        }


    }


}