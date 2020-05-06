package com.jadhavrupesh.commitecontent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.inputmethod.EditorInfoCompat;
import androidx.core.view.inputmethod.InputConnectionCompat;
import androidx.core.view.inputmethod.InputContentInfoCompat;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "CommitContentSupport";




    MyEditText editText;
    TextView textView;
    WebView webView;
    ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.text);
        editText=findViewById(R.id.myEditText);
        imageView=findViewById(R.id.commit_content_webview);

        editText.setKeyBoardInputCallbackListener(new MyEditText.KeyBoardInputCallbackListener() {
            @Override
            public void onCommitContent(InputContentInfoCompat inputContentInfo,
                                        int flags, Bundle opts) {
                onCommitContentInternal(inputContentInfo, flags);
                editText.clearFocus();
            }
        });


    }


    private boolean onCommitContentInternal(InputContentInfoCompat inputContentInfo, int flags) {
        if ((flags & InputConnectionCompat.INPUT_CONTENT_GRANT_READ_URI_PERMISSION) != 0) {
            try {
                inputContentInfo.requestPermission();
            } catch (Exception e) {
                Log.e(TAG, "InputContentInfoCompat#requestPermission() failed.", e);
                return false;
            }
        }

        String mMimeTypes = Arrays.toString(inputContentInfo.getDescription().filterMimeTypes("*/*"));

        String mContentUri = inputContentInfo.getContentUri().toString();

        Uri linkUri = inputContentInfo.getLinkUri();

        String uriIs = linkUri.toString();
        textView.setText(uriIs);


        Glide.with(this)
                .load(linkUri)
                .into(imageView);




        Log.e(TAG, "uri "+uriIs );

        Log.e(TAG, "getGifImg mMimeTypes " + mMimeTypes + " mContentUri " + mContentUri + " uriIs " + uriIs);

        return true;
    }

    public void getText(View view) {
        Log.e(TAG, "getText: "+editText.getText().toString());
//        textView.setText(editText.getText().toString());

    }



}
