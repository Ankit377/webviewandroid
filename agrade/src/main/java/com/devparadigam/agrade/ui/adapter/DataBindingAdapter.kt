package com.devparadigam.agrade.ui.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.DisplayMetrics
import android.view.Gravity
import android.widget.*
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager
import com.devparadigam.agrade.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.devparadigam.agrade.model.response.SubjectModel

class DataBindingAdapter {

    companion object {

        @JvmStatic
        @BindingAdapter("bind:loadCompressimage")
        fun loadCompressimage(view: ImageView, url: String?) {
            val requestOptions:RequestOptions = RequestOptions()
            requestOptions.placeholder(R.drawable.testseries_logo)
            requestOptions.error(R.drawable.testseries_logo)
            requestOptions.dontTransform()
            requestOptions.override(480,300)
            requestOptions.override(300)
            Glide.with(view.context.applicationContext).asBitmap().load("" + url)
                    .apply(requestOptions).into(view)
        }

        @JvmStatic
        @BindingAdapter("bind:loadimage")
        fun loadimage(view: ImageView, url: String?) {
            val requestOptions:RequestOptions = RequestOptions()
            requestOptions.placeholder(R.drawable.testseries_logo)
            requestOptions.error(R.drawable.testseries_logo)
            requestOptions.dontTransform()
            Glide.with(view.context.applicationContext).load("" + url)
                .apply(requestOptions).into(view)
        }

        @BindingAdapter("bind:setCircularImage")
        fun setCircularImage(thumbnails: ImageView, url: String) {

            val requestOptions = RequestOptions()
            requestOptions.placeholder(R.drawable.testseries_logo)
            requestOptions.error(R.drawable.testseries_logo)
            // requestOptions.transforms(RequestOptions.circleCropTransform());

            Glide.with(thumbnails.context.applicationContext)
                .setDefaultRequestOptions(requestOptions).load(url)
                .apply(RequestOptions.circleCropTransform()).into(thumbnails)
        }

        @JvmStatic
        @BindingAdapter("bind:load_ResourceImage")
        public fun load_ResourceImage(@NonNull imageView : ImageView, @NonNull res : Int?){
            if (res!=-1)
                Glide.with(imageView.context).load(res).into(imageView)
        }

        @BindingAdapter("bind:setLinnearHight")
        fun setLinnearHight(linearLayout: LinearLayout, isset: Boolean) {

            if (isset) {
                val displayMetrics: DisplayMetrics
                val screenHeight: Double
                val screenWidth: Double
                val lhi: Int
                val lwi: Int

                displayMetrics = linearLayout.context.resources.displayMetrics
                screenHeight = displayMetrics.heightPixels.toDouble()
                //  screenWidth = displayMetrics.widthPixels;

                lhi = (screenHeight * 0.08).toInt()
                // lwi = (int) (screenWidth * 0.02);

                linearLayout.layoutParams.height = lhi
                //   toolbar.getLayoutParams().width = lwi;
                linearLayout.requestLayout()

            }
        }

        @BindingAdapter("bind:setRelativeHight")
        fun setRelativeHight(relativeLayout: RelativeLayout, isset: Boolean) {

            if (isset) {
                val displayMetrics: DisplayMetrics
                val screenHeight: Double
                val screenWidth: Double
                val lhi: Int
                val lwi: Int

                displayMetrics = relativeLayout.context.resources.displayMetrics
                screenHeight = displayMetrics.heightPixels.toDouble()
                //  screenWidth = displayMetrics.widthPixels;

                lhi = (screenHeight * 0.12).toInt()
                // lwi = (int) (screenWidth * 0.02);

                relativeLayout.layoutParams.height = lhi
                //   toolbar.getLayoutParams().width = lwi;
                relativeLayout.requestLayout()

            }
        }


        @JvmStatic
        @BindingAdapter("bind:setSubjectGroup")
        fun setSubjectGroup(view: RadioGroup, list:List<com.devparadigam.agrade.model.data.SubjectOperation>) {
            if (!list.isNullOrEmpty()){
                list.forEach(){
                    // can also inflat by xml file // radio button should be parent view
//RadioButton radioButtonView = (RadioButton) getLayoutInflater().inflate(R.layout.radio_button, null, false);
                    val rb = RadioButton(view.context)
                    val params = RadioGroup.LayoutParams(view.context, null)
                    params.setMargins(10, 2, 10, 2)
                    rb.layoutParams = params
                    rb.setPadding(35, 2, 35, 2)
                    rb.id = it.subjectId.toInt()
                    rb.setText(it.subjects)
                    rb.gravity = Gravity.CENTER
                    rb.background = ContextCompat.getDrawable(view.context,R.drawable.exam_radio_selector)

                    val colorStateList = ColorStateList(arrayOf(intArrayOf(-android.R.attr.state_checked), intArrayOf(android.R.attr.state_checked)), intArrayOf(
                            Color.BLACK //disabled
                            , Color.BLUE //enabled
                    ))

                    rb.setTextColor(colorStateList)
                    rb.buttonDrawable = null

                    view.addView(rb)
                }

               val rb= view.getChildAt(0) as RadioButton
                rb.isChecked=true
            }
        }

        @JvmStatic
        @BindingAdapter("bind:setQuestionAdapter")
        fun setQuestionAdapter(view: ViewPager, imageArray: List<com.devparadigam.agrade.model.response.youtube.TestQueModel>) {
            if (imageArray != null) {
                if (!imageArray.isEmpty()) {
                    /*if (imageArray.size > 1) {
                        view.clipToPadding = false
                        view.pageMargin = 40
                        view.setPadding(0, 0, 100, 0)
                        view.offscreenPageLimit = 2
                    }*/
                    view.adapter = com.devparadigam.agrade.ui.adapter.QuestionViewPagerAdapter(view.context, imageArray)
                }
            }
        }
        @JvmStatic
        @BindingAdapter("bind:setSubjects")
        fun setSubjects(view: TextView, subjects:List<SubjectModel> ) {
            if (!subjects.isNullOrEmpty()) {
                val sb=StringBuilder()
                subjects.forEach { sb.append(it.subject+"/") }
                view.text=sb
                }
            }



    }

}
