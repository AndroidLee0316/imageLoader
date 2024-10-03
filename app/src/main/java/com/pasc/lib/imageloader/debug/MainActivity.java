package com.pasc.lib.imageloader.debug;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.pasc.lib.imageloader.PascCallback;
import com.pasc.lib.imageloader.PascImageLoader;

public class MainActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PascImageLoader.getInstance().init(this, PascImageLoader.GLIDE_CORE, R.color.C_EAF7FF);
        //PascImageLoader.getInstance().loadLocalImage("/storage/emulated/0/DCIM/Camera/Abc.jpg",(ImageView) findViewById(R.id.img_test),PascImageLoader.SCALE_FIT);
        //File file = new File("/storage/emulated/0/DCIM/Camera/Abc.jpg");
        //Picasso.with(this).load(file).fit().into((ImageView) findViewById(R.id.img_test));
        //PascImageLoader.getInstance().loadImageUrl("http://smt-web.pingan.com.cn/smt/appicon/home-landscape-shijiezhichuang@3x.png",(ImageView) findViewById(R.id.img_test));
        //Picasso.get().load("http://smt-web.pingan.com.cn/smt/appicon/home-landscape-shijiezhichuang@3x.png").into((ImageView) findViewById(R.id.img_test));
        //
      findViewById(R.id.img_test).setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          //PascImageLoader.getInstance()
          //    .loadImageUrl("http://smt-stg1.yun.city.pingan.com/mg/adm/admfile/downloadFileUnCheck?path=adm/fullscreenad/7cdef1427374431d9364894cd15206b0&filename=5@3x-1.png",
          //        (ImageView) findViewById(R.id.img_test2), R.color.C_EAF7FF, R.color.C_EAF7FF,
          //        PascImageLoader.SCALE_FIT, 500, new PascCallback() {
          //          @Override public void onSuccess() {
          //            Log.i("MainActivity", "onSuccess");
          //          }
          //
          //          @Override public void onError() {
          //            Log.i("MainActivity", "onError");
          //          }
          //        });
          PascImageLoader.getInstance()
              .loadImageUrl("http://smt-stg1.yun.city.pingan.com/mg/adm/admfile/downloadFileUnCheck?path=adm/fullscreenad/7cdef1427374431d9364894cd15206b0&filename=5@3x-1.png",
                  (ImageView) findViewById(R.id.img_test2),
                  PascImageLoader.SCALE_FIT, new PascCallback() {
                    @Override public void onSuccess() {
                      Log.i("MainActivity", "onSuccess");
                    }

                    @Override public void onError() {
                      Log.i("MainActivity", "onError");
                    }
                  }, 500);
        }
      });


        //PascImageLoader.getInstance().loadBitmap("http://i.imgur.com/DvpvklR.png", new Target() {
        //    @Override public void onBitmapLoaded(Bitmap bitmap) {
        //       ((ImageView) findViewById(R.id.img_test3)).setImageBitmap(bitmap);
        //    }
        //
        //    @Override public void onBitmapFailed(Drawable drawable) {
        //        ((ImageView) findViewById(R.id.img_test3)).setImageDrawable(drawable);
        //
        //    }
        //
        //    @Override public void onPrepareLoad(Drawable drawable) {
        //        ((ImageView) findViewById(R.id.img_test3)).setImageDrawable(drawable);
        //
        //    }
        //});

        //PascImageLoader.getInstance().loadImageRes(R.mipmap.ic_launcher,(ImageView)findViewById(R.id.img_test));
    }
}
