package ru.ulstu_team.ulstuschedule

import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide

import java.io.IOException
import java.util.Date
import java.util.Random
import java.util.Timer
import java.util.TimerTask

import javax.inject.Inject

import ru.ulstu_team.ulstuschedule.data.DataManager

class HeaderViewManager
@Inject
constructor(private val mContext: Context, private val mDataManager: DataManager) {
    private val mImageView: ImageView

    private var timer: Timer? = null
    private var isScheduled: Boolean = false
    val navHeaderView: View

    init {
        val inflater = LayoutInflater.from(mContext)
        navHeaderView = inflater.inflate(R.layout.nav_header_main, null, false)
        mImageView = navHeaderView.findViewById(R.id.headerImage) as ImageView

        val username = navHeaderView.findViewById(R.id.headerUsername) as TextView
        username.text = mDataManager.userName
    }

    fun start() {
        if (isScheduled)
            return

        timer = Timer()
        val uiHandler = Handler()
        val changeTask = object : TimerTask() {
            override fun run() {
                uiHandler.post {
                    try {
                        NavHeaderImageHelper.setRandomHeaderImage(mContext, mImageView)
                    } catch (e: Exception) {
                        stop()
                    }
                }
            }
        }
        timer!!.schedule(changeTask, 0, 15000)
        NavHeaderImageHelper.setRandomHeaderImage(mContext, mImageView)

        isScheduled = true
    }

    fun stop() {
        timer!!.cancel()
        isScheduled = false
    }


    private object NavHeaderImageHelper {

        private val TAG = "NavHeaderImageHelper"

        private var imagePaths: Array<String>? = null
        private val random = Random(Date().time)

        fun setRandomHeaderImage(context: Context, imageView: ImageView) {
            getImagePaths(context)
            val path = "file:///android_asset/headerImages/" + imagePaths!![random.nextInt(imagePaths!!.size)]
            Glide.with(context).load(path).centerCrop().skipMemoryCache(true).crossFade(1200).into(imageView)
        }

        private fun getImagePaths(context: Context): Array<String> {
            if (imagePaths != null)
                return imagePaths!!

            val manager = context.assets
            try {
                imagePaths = manager.list("headerImages")
            } catch (e: IOException) {
                // TODO: report about bug
                Log.e(TAG, "getImagePaths: " + e.message)
                imagePaths = arrayOf("0.jpg")
            }

            return imagePaths!!
        }
    }
}
