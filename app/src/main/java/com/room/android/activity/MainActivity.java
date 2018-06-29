package com.room.android.activity;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.room.android.R;

public class MainActivity extends AppCompatActivity {

    private Button mInsert;
    private Button mDelete;
    private Button mDescendingOrder;
    private Button mTopRecords;
    private Button mCountRecords;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        showClick();
    }

    private void showClick() {
        mInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainClick(0);
            }
        });
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   mainClick(1);
                Intent intent = new Intent(MainActivity.this,SplashActivity.class);
                startActivity(intent);
            }
        });
        mDescendingOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // mainClick(2);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(MainActivity.this, view, "transition");
                int revealX = (int) (view.getX() + view.getWidth() / 2);
                int revealY = (int) (view.getY() + view.getHeight() / 2);
                Intent intent = new Intent(MainActivity.this, CircleRevealsActivity.class);
                intent.putExtra(CircleRevealsActivity.EXTRA_CIRCULAR_REVEAL_X, revealX);
                intent.putExtra(CircleRevealsActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY);
                ActivityCompat.startActivity(MainActivity.this, intent, options.toBundle());
            }
        });
        mTopRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainClick(3);
            }
        });
        mCountRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainClick(4);
            }
        });
        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(MainActivity.this, view, "transition");
                int revealX = (int) (view.getX() + view.getWidth() / 2);
                int revealY = (int) (view.getY() + view.getHeight() / 2);
                Intent intent = new Intent(MainActivity.this, CircleRevealsActivity.class);
                intent.putExtra(CircleRevealsActivity.EXTRA_CIRCULAR_REVEAL_X, revealX);
                intent.putExtra(CircleRevealsActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY);
                ActivityCompat.startActivity(MainActivity.this, intent, options.toBundle());
            }
        });
    }

    private void mainClick(int position) {
        Intent intent = new Intent(MainActivity.this,RecyclerViewActivity.class);
        intent.putExtra("clickValue",position);
        startActivity(intent);
    }

    private void initView() {
        mInsert = findViewById(R.id.insertButton);
        mDelete = findViewById(R.id.deleteButton);
        mDescendingOrder = findViewById(R.id.descendingOrder);
        mTopRecords = findViewById(R.id.topRecords);
        mCountRecords = findViewById(R.id.countRecords);
    }

}
