package com.room.android.click;

import android.view.View;

public interface ClickListener {

    public void onLongClick(View view, int position);

    public void onClick(View view,int position);
}
