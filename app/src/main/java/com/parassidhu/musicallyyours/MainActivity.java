package com.parassidhu.musicallyyours;

import android.content.ContentUris;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private Toast mToast;
    private ImageView bhangra;
    private Handler handler;
    private TextView mDescription;
    private TextView mCounter;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int currentValue = Integer.valueOf(mCounter.getText().toString());
                    if (currentValue != 5) {
                        currentValue++;
                        mCounter.setText(String.valueOf(currentValue));
                    }
                }
            });

            decreaseValue();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        mCounter = findViewById(R.id.counter_tv);
        bhangra = findViewById(R.id.imageView);
        mDescription = findViewById(R.id.description);

        mCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentValue = Integer.valueOf(mCounter.getText().toString());

                if (currentValue !=0){
                    currentValue--;
                    mCounter.setText(String.valueOf(currentValue));
                    showToast(currentValue);
                }

                // Intentionally not put in else block
                if (currentValue==0){
                    showContents();
                    handler.removeCallbacks(runnable);
                    mCounter.setVisibility(View.GONE);
                    mDescription.setVisibility(View.VISIBLE);
                }
            }
        });

        handler = new Handler(Looper.getMainLooper());
        decreaseValue();
    }

    private void decreaseValue(){
        handler.postDelayed(runnable, 1000);
    }

    private void showToast(int currentValue) {
        if (mToast == null){
            mToast = Toast.makeText(this,"",Toast.LENGTH_SHORT);
            mToast.setText("Click " + currentValue + " times more to reveal!");
            mToast.show();
        }else {
            Log.e("", "showToast: " );
            mToast.cancel();
            mToast = Toast.makeText(this,"",Toast.LENGTH_SHORT);
            if (currentValue!=0) {
                mToast.setText("Click " + currentValue + " times more to reveal!");
            }else{
                mToast.setText("Revealed!");
            }

            mToast.show();
        }
    }

    private void showContents(){
        Glide.with(this)
                .load(R.drawable.bhangra)
                .into(bhangra);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

         if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
