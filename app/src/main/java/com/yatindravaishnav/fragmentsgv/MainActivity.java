package com.yatindravaishnav.fragmentsgv;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    String [] permissionsNeeded = new String[] {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int myRequestCode=11339;
    int [] myPermCheck = new int[2];

    private boolean shouldShowReadWriteRationale()
    {
        myPermCheck[0] = this.checkSelfPermission(permissionsNeeded[0]);
        myPermCheck[1] = this.checkSelfPermission(permissionsNeeded[1]);

        return ((PackageManager.PERMISSION_GRANTED == myPermCheck[0]) &&
                (PackageManager.PERMISSION_GRANTED == myPermCheck[1]));
    }

    int askReadWritePermissions() {
        if (!shouldShowReadWriteRationale()) {
            if ((myPermCheck[0] != PackageManager.PERMISSION_GRANTED) &&
                    (myPermCheck[1] != PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(this, permissionsNeeded, myRequestCode);
            } else {
                if (PackageManager.PERMISSION_GRANTED != myPermCheck[0]) {
                    ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsNeeded[0]);
                } else {
                    ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsNeeded[1]);
                }
            }
        }

        Toast.makeText(this, new String ("askReadWritePermissions: Permission " + myPermCheck[0] + " " + myPermCheck[1]),
                Toast.LENGTH_LONG).show();

        return 0;
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] result) {
//        Toast.makeText(this, new String ("onRequestPermissionsResult: Permission " +permissions[0] + permissions[1]
//                + myPermCheck[0] + " " + myPermCheck[1] + permsRequestCode), Toast.LENGTH_LONG).show();

        switch (permsRequestCode) {
            case myRequestCode: {
                // If request is cancelled, the result arrays are empty.
                if (result[0] == PackageManager.PERMISSION_GRANTED && result[1] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("How is this app going to work if you rejected the camera permission.... DUHHHH!!")
                            .setTitle("Rejected");
                    builder.setPositiveButton("Exit App", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            System.exit(0);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                return;
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        askReadWritePermissions();
        while (!shouldShowReadWriteRationale()) {
            try {
                synchronized(this){
                    wait(1000);
                }
            } catch(InterruptedException ex){
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.parent_layout) != null) {
            if (savedInstanceState != null) {
                return;
            }
            GridViewFragment gridViewFragment = new GridViewFragment();
            gridViewFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction().
                    add(R.id.parent_layout, gridViewFragment).commit();
        }
    }
}
