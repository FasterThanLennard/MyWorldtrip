package com.rhahn.myworldtrip.DataHandler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.pixplicity.sharp.Sharp;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Class to do request to get and set svg images from url to {@link ImageView}
 *
 * @author Robin Hahn
 */
public class ImageRequest {
    private static OkHttpClient httpClient;

    /**
     * Sets svg image to {@link ImageView}
     * @param context current context
     * @param url url of image
     * @param target {@link android.widget.TextView} to add image to
     */
    public static void fetchSvg(Context context, String url, final ImageView target) {
        if (httpClient == null) {
            // Use cache for performance and basic offline capability
            httpClient = new OkHttpClient.Builder()
                    .cache(new Cache(context.getCacheDir(), 5 * 1024 * 1014))
                    .build();
        }

        Request request = new Request.Builder().url(url).build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                assert response.body() != null;
                InputStream stream = response.body().byteStream();
                try {
                    Sharp.loadInputStream(stream).into(target);
                    stream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
