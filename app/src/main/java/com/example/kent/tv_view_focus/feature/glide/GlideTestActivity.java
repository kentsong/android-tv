package com.example.kent.tv_view_focus.feature.glide;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.kent.tv_view_focus.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;


/**
 * Created by Kent Song on 2018/11/7.
 */
public class GlideTestActivity extends AppCompatActivity {

    @BindView(R.id.target_img)
    ImageView targetImg;
    @BindView(R.id.get_img1)
    Button getImg1;
    @BindView(R.id.get_img2)
    Button getImg2;
    @BindView(R.id.get_img3)
    Button getImg3;
    @BindView(R.id.clear_img)
    Button clearImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.get_img1, R.id.get_img2, R.id.get_img3, R.id.clear_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_img1:
                loadImage("http://thyrsi.com/t6/414/1541580867x1822611359.jpg");
                break;
            case R.id.get_img2:
                loadImage("http://thyrsi.com/t6/414/1541581000x1822611431.jpg");
                break;
            case R.id.get_img3:
                loadImage("");
                break;
            case R.id.clear_img:
                clearImage();
                break;
        }
    }


    private void loadImage(String url) {



        RequestOptions options = new RequestOptions()
                .centerCrop()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
        Glide.with(this).load(url).apply(options).into(targetImg);
    }

    private void clearImage() {
        Drawable drawable = targetImg.getDrawable();
        if (drawable != null) {
            if (drawable instanceof BitmapDrawable) {
//                Glide.get(this).clearMemory();
//                Glide.with(this).clear(targetImg);
                Timber.d(">> release/BitmapDrawable = %s", drawable);
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                bitmap.recycle();

                targetImg.setImageResource(0);
            } else if (drawable instanceof TransitionDrawable) {
                Timber.d(">> /release/TransitionDrawable not recycler ");
            }
        }
    }


    public static void launch(Context context) {
        Intent intent = new Intent(context, GlideTestActivity.class);
        context.startActivity(intent);
    }


}
