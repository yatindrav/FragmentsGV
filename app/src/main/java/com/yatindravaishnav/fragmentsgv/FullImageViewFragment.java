package com.yatindravaishnav.fragmentsgv;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Yatindra Vaishnav on 8/19/2016.
 */
public class FullImageViewFragment extends Fragment {
    Activity myParentActivity;
    public final static String IMAGE_PATH="image_path";
    String imagePath;
    int maxX, maxY;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagePath = getArguments().getString(IMAGE_PATH);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        maxX = size.x;
        maxY = size.y;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myParentActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.full_image_view, parent, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ImageView imageView = (ImageView) view.findViewById(R.id.full_image_view);
        BitmapImageHandler imgHandler = new BitmapImageHandler(imagePath, maxX, maxY);
        Bitmap bm = imgHandler.getBitmapImageWithOrientation();
        imageView.setImageBitmap(bm);
    }
}
