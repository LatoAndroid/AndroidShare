package com.lyb.besttimer.cameracore.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.lyb.besttimer.cameracore.CameraResultCaller;
import com.lyb.besttimer.cameracore.R;
import com.lyb.besttimer.cameracore.databinding.FragmentCameraShowBinding;

public class CameraShowFragment extends Fragment {

    public static Bundle createArg(String fileUrl, CameraResultCaller.ResultType resultType) {
        Bundle bundle = new Bundle();
        bundle.putString("fileUrl", fileUrl);
        bundle.putSerializable("resultType", resultType);
        return bundle;
    }

    private FragmentCameraShowBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.bind(inflater.inflate(R.layout.fragment_camera_show, container, false));
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        handle();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.vvVideo.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.vvVideo.pause();
    }

    private void handle() {
        String fileUrl = getArguments().getString("fileUrl");
        CameraResultCaller.ResultType resultType = (CameraResultCaller.ResultType) getArguments().getSerializable("resultType");
        if (resultType == CameraResultCaller.ResultType.PICTURE) {
            binding.getRoot().getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    binding.ivPic.setVisibility(View.VISIBLE);
                    binding.vvVideo.setVisibility(View.GONE);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(fileUrl, options);
                    // 调用上面定义的方法计算inSampleSize值
                    options.inSampleSize = calculateInSampleSize(options, binding.getRoot().getWidth(), binding.getRoot().getHeight());
                    // 使用获取到的inSampleSize值再次解析图片
                    options.inJustDecodeBounds = false;
                    binding.ivPic.setImageBitmap(BitmapFactory.decodeFile(fileUrl, options));
                    binding.getRoot().getViewTreeObserver().removeOnPreDrawListener(this);
                    return false;
                }
            });
        } else if (resultType == CameraResultCaller.ResultType.VIDEO) {
            binding.ivPic.setVisibility(View.GONE);
            binding.vvVideo.setVisibility(View.VISIBLE);
            binding.vvVideo.setVideoPath(fileUrl);
            binding.vvVideo.start();
            binding.vvVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    mp.setLooping(true);
                }
            });
            binding.vvVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    binding.vvVideo.setVideoPath(fileUrl);
                    binding.vvVideo.start();
                }
            });
        }
    }

    private int calculateInSampleSize(BitmapFactory.Options options,
                                      int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

}
