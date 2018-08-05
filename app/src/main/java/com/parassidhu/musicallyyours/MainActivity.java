package com.parassidhu.musicallyyours;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private Toast mToast;
    private ImageView bhangra;
    private Handler handler;
    private TextView mDescription;
    private TextView mCounter;
    private TextView mInstructions;
    private ImageView mDiljit;

    private final Runnable runnable = new Runnable() {
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

        mCounter = findViewById(R.id.counter_tv);
        bhangra = findViewById(R.id.imageView);
        mDescription = findViewById(R.id.description);
        mInstructions = findViewById(R.id.instruction);
        mDiljit = findViewById(R.id.diljit_iv);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

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

        handler.removeCallbacks(runnable);
        mCounter.setVisibility(View.GONE);
        mDescription.setVisibility(View.VISIBLE);
        mInstructions.setVisibility(View.GONE);
        mDiljit.setVisibility(View.VISIBLE);
    }
}
