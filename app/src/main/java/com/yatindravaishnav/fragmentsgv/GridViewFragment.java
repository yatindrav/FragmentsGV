package com.yatindravaishnav.fragmentsgv;

import android.app.Activity;
import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Yatindra Vaishnav on 8/18/2016.
 */
public class GridViewFragment extends Fragment {
    Context mCtx;
    ImageListAdapter myImagesListAdapter;
    String targetPath;
    Activity myParentActivity;
    int currentImagePos;
    boolean imageSelected;
    int maxX, maxY;
    int gvColumnWidth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        maxX = size.x;
        maxY = size.y;

        gvColumnWidth = getMyColumnWidth(maxX);

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
        myImagesListAdapter = new ImageListAdapter(getActivity(), gvColumnWidth, gvColumnWidth);
        String ExternalStorageDirectoryPath = Environment
                .getExternalStorageDirectory()
                .getAbsolutePath();

        targetPath = ExternalStorageDirectoryPath + "/DCIM/Camera/";

        Toast.makeText(getActivity(), targetPath, Toast.LENGTH_LONG).show();
        try {
            File targetDirector = new File(targetPath);

            File[] files = targetDirector.listFiles();
            for (File file : files) {
                myImagesListAdapter.add(file.getAbsolutePath());
            }
        } catch (Exception e) {
            String fileError = e.toString();
            Toast.makeText(getActivity(), fileError, Toast.LENGTH_LONG).show();
        }

        return inflater.inflate(R.layout.image_grid_view, parent, false);
    }

    private int getMyColumnWidth(int maxX)
    {
        int imgCnt = 0;
        for(int i = 2; i < 10; i++) {
            int mod = (maxX % i);
            if (mod == 0) {
                imgCnt = i;
            }
        }
        return (maxX/imgCnt);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        GridView gridView = (GridView) view.findViewById(R.id.thumb_nail_view);
        gridView.setColumnWidth(gvColumnWidth);
        gridView.setAdapter(myImagesListAdapter);
        gridView.setOnItemClickListener(myOnItemClickListener);
    }


    AdapterView.OnItemClickListener myOnItemClickListener
            = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            currentImagePos = position;
            imageSelected = true;

            String prompt = (String)parent.getItemAtPosition(position);
            Toast.makeText(getActivity(),
                    prompt,
                    Toast.LENGTH_LONG).show();
            // Create fragment and give it an argument specifying the article it should show
            FullImageViewFragment newFragment = new FullImageViewFragment();
            Bundle args = new Bundle();
            args.putString(FullImageViewFragment.IMAGE_PATH, prompt);
            newFragment.setArguments(args);

            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.parent_layout, newFragment);
            transaction.addToBackStack(null);

            transaction.commit();
        }
    };
}
