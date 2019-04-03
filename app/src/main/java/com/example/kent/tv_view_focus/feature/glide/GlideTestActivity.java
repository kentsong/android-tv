package com.example.kent.tv_view_focus.feature.glide;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.kent.tv_view_focus.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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

        // Create global configuration and initialize ImageLoader with this config
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
//			.build();
//        ImageLoader.getInstance().init(config);
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

//    private List<Bitmap> list = new ArrayList<Bitmap>();


    private void loadImage(String url) {
//        ImageLoader imageLoader = ImageLoader.getInstance();
//        imageLoader.loadImage(url, new SimpleImageLoadingListener(){
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                super.onLoadingComplete(imageUri, view, loadedImage);
//                Timber.d(">> loadImage onLoadingComplete");
//                targetImg.setImageBitmap(loadedImage);
//            }
//        });

//        Picasso.get().load(url)
//                .memoryPolicy(MemoryPolicy.NO_CACHE)
//                .networkPolicy(NetworkPolicy.NO_CACHE)
//                .into(targetImg);

//
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.R)
                .priority(Priority.HIGH);

        Glide.with(this)
                .asBitmap()
                .load(url)
                .apply(options)
                .into(targetImg);

//                .into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                        if(resource != null){
//                            targetImg.setImageBitmap(resource);
//                        }
//                    }
//                });
    }

    private void clearImage() {
        Timber.d(">> flag1");
        Glide.with(this).clear(targetImg);

        Drawable drawable = targetImg.getDrawable();
//        if (drawable != null) {
//            Timber.d(">> flag2");
//
//            if (drawable instanceof BitmapDrawable) {
//                Timber.d(">> flag3");
//
//                Timber.d(">> clearImage/BitmapDrawable = %s", drawable);
//                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
//                if (bitmap != null && !bitmap.isRecycled()) {
//                    Timber.d(">> recycleBitmap");
//
//                    bitmap.recycle();
//                }
//
//
//                targetImg.setImageResource(0);
////                targetImg.destroyDrawingCache();
//            } else if (drawable instanceof TransitionDrawable) {
//                Timber.d(">> /release/TransitionDrawable not recycler ");
//            }
//        }
    }


    public static void launch(Context context) {
        Intent intent = new Intent(context, GlideTestActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        clearImage();
//        Glide.get(this).clearMemory();
        Timber.d(">> onDestroy");
        unbindDrawables(findViewById(R.id.content));
        //        ImageLoader.getInstance().clearMemoryCache();
//        ImageLoader.getInstance().clearDiskCache();
    }

    private boolean isBitmapRecycled(ImageView view) {
        Drawable drawable = view.getDrawable();
        view.setImageDrawable(null);
        if (drawable != null) {
            if (drawable instanceof BitmapDrawable) {
                Timber.d(">> flag3");

                Timber.d(">> clearImage/BitmapDrawable = %s", drawable);
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                return bitmap.isRecycled();
            }

        }
        Timber.d(">> isBitmapRecycled 取不到 Bitmap");
        return false;
    }

    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }
}
