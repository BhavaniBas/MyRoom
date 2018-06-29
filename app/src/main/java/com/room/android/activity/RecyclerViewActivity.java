package com.room.android.activity;

import android.app.Dialog;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.room.android.PeristanceDatabase.Database.AppDatabase;
import com.room.android.PeristanceDatabase.Entity.models.City;
import com.room.android.PeristanceDatabase.Entity.models.ContactResponse;
import com.room.android.R;
import com.room.android.helper.ConnectionHelper;
import com.room.android.helper.CustomDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecyclerViewActivity extends AppCompatActivity implements
        RecyclerAdapter.selectionLongListener {

    private RecyclerView mRecyclerView;
    private TextView mEmptyRecords;
    private RecyclerAdapter mRecyclerAdapter;
    private AppDatabase mContactDatabase;
    private List<City> mCityList = new ArrayList<>();
    private ConnectionHelper helper;
    private Boolean isInternet;
    private int mCountryCount;
    private CustomDialog customDialog;
    private int count = 0;
    private EditText mIdEdit;
    private EditText mCityIdEdit;
    private EditText mCityNameEdit;
    private final MutableLiveData<List<City>> openSourceItemsLiveData;

    public RecyclerViewActivity(MutableLiveData<List<City>> openSourceItemsLiveData) {
        this.openSourceItemsLiveData = openSourceItemsLiveData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        helper = new ConnectionHelper(this);
        isInternet = helper.isConnectingToInternet();
        mCountryCount = getIntent().getIntExtra("clickValue", 0);
        mContactDatabase = Room.databaseBuilder(this,
                AppDatabase.class, "Room_database")
                .allowMainThreadQueries().build();
        initView();
        if(!SharedHelper.getKey(RecyclerViewActivity.this,"loggedIn").
                equalsIgnoreCase(getResources().getString(R.string.true_value)))
            if (isInternet) {
                apiHit();
            } else {
                displayMessage(getString(R.string.something_went_wrong_net));
        }

    }

    private void apiHit() {
        customDialog = new CustomDialog(this);
        customDialog.setCancelable(false);
        if (customDialog != null)
            customDialog.show();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(18000, TimeUnit.SECONDS);
        httpClient.connectTimeout(18000, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);  // <— this is the important line!
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://174.138.4.169")
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface retrofitInterface = retrofit.create(ApiInterface.class);
        Call<List<ContactResponse>> contactResponseCall = retrofitInterface.doContactResponseDetails();
        contactResponseCall.enqueue(new Callback<List<ContactResponse>>() {
            @Override
            public void onResponse(Call<List<ContactResponse>> call, Response<List<ContactResponse>> response) {
                if ((customDialog != null) && (customDialog.isShowing()))
                    customDialog.dismiss();
                if (response.isSuccessful()) {
                    SharedHelper.putKey(RecyclerViewActivity.this,"loggedIn", getResources().getString(R.string.true_value));
                    List<ContactResponse> contactResponses = response.body();
                    if (contactResponses != null && !contactResponses.isEmpty()) {
                        mContactDatabase.getContactResponseDao().insertCountryWithCity(contactResponses);
                        mCityList.clear();
                        mCityList = mContactDatabase.getContactResponseDao().getSelectCityCount();
                        openSourceItemsLiveData.setValue(mCityList);
                        if (mCityList != null && !mCityList.isEmpty()) {
                            if (mCountryCount == 0) {
                                setAdapter();
                            } else if (mCountryCount == 1) {
                                mContactDatabase.getContactResponseDao().deleteAll(contactResponses);
                                setAdapter();
                            } else if (mCountryCount == 2) {
                                mCityList.clear();
                                mCityList.addAll(mContactDatabase.getContactResponseDao().getDescCity());
                                setAdapter();
                            } else if (mCountryCount == 3) {
                                mCityList.clear();
                                mCityList.addAll(mContactDatabase.getContactResponseDao().getCityLimit());
                                setAdapter();
                            } else if (mCountryCount == 4) {
                                //  setAdapter();
                            }
                        }
                    }
                } else {
                    Toast.makeText(RecyclerViewActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ContactResponse>> call, Throwable t) {
                if ((customDialog != null) && (customDialog.isShowing()))
                    customDialog.dismiss();
                Toast.makeText(RecyclerViewActivity.this, "Please try again !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter() {
        if (mCityList != null && !mCityList.isEmpty()) {
            mRecyclerAdapter = new RecyclerAdapter(this, openSourceItemsLiveData.getValue());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setHasFixedSize(true);
            mRecyclerAdapter.setNavItems(this);
            mRecyclerView.setVisibility(View.VISIBLE);
            mRecyclerView.setAdapter(mRecyclerAdapter);
            mRecyclerAdapter.notifyDataSetChanged();
            mEmptyRecords.setVisibility(View.GONE);
        } else {
            mRecyclerView.setVisibility(View.GONE);
            mEmptyRecords.setVisibility(View.VISIBLE);
            //  apiHit();
        }
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mEmptyRecords = findViewById(R.id.emptyRecords);
    }

    public void displayMessage(String toastString) {
        try {
            Snackbar snackbar = Snackbar.make(getCurrentFocus(), toastString, Snackbar.LENGTH_LONG).setDuration(Snackbar.LENGTH_LONG);
            View snackbarView = snackbar.getView();
            TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            tv.setMaxLines(3);
            snackbar.show();
        } catch (Exception e) {
            try {
                Toast.makeText(RecyclerViewActivity.this, "" + toastString, Toast.LENGTH_SHORT).show();
            } catch (Exception ee) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSelectionItemsClick(City city, int position) {
        mCityList = mContactDatabase.getContactResponseDao().getSelectCityCount();
        showAlertDialog(city).show();
    }

    private Dialog showAlertDialog(City city) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.activity_alert_dialog);
        mIdEdit = dialog.findViewById(R.id.idEdit);
        mCityIdEdit = dialog.findViewById(R.id.cityIdEdit);
        mCityNameEdit = dialog.findViewById(R.id.cityNameEdit);
        Button mUpdateButton = dialog.findViewById(R.id.updateButton);
        Button mDeleteButton = dialog.findViewById(R.id.deleteButton);
        if (mCityList != null && !mCityList.isEmpty()) {
            mIdEdit.setText(String.valueOf(city.getId()));
            mCityIdEdit.setText(String.valueOf(city.getCountryID()));
            mCityNameEdit.setText(city.getCityName());
        }
        Window window = dialog.getWindow();
        assert window != null;
        WindowManager.LayoutParams param = window.getAttributes();
        param.gravity = Gravity.CENTER | Gravity.CENTER_HORIZONTAL;
        window.setAttributes(param);
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mUpdateButton.setOnClickListener(view -> {
            dialog.dismiss();
            count = 1;
            showCount(count, city);
        });
        mDeleteButton.setOnClickListener(view -> {
            dialog.dismiss();
            count = 2;
            showCount(count, city);
        });
        return dialog;
    }

    private void showCount(int count, City city) {
        if (count == 1) {
            mContactDatabase
                    .getContactResponseDao()
                    .updateCityUsers(
                            mCityIdEdit.getText().toString(),
                            mCityNameEdit.getText().toString(),
                            city.getId());
            mCityList.clear();
            mCityList.addAll(mContactDatabase.getContactResponseDao().getSelectCityCount());
            refreshAdapter();
        } else {
            mContactDatabase.getContactResponseDao().deleteUsers(city.getId());
            mCityList = mContactDatabase.getContactResponseDao().getSelectCityCount();
            refreshAdapter();
        }
    }

    private void refreshAdapter() {
        if (mCityList != null && !mCityList.isEmpty()) {
            mRecyclerAdapter.cityList = mCityList;
            mRecyclerAdapter.notifyDataSetChanged();
        } else {
            mRecyclerView.setVisibility(View.GONE);
            mEmptyRecords.setVisibility(View.VISIBLE);
            // apiHit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            mCityList.clear();
            mCityList.addAll(mContactDatabase.getContactResponseDao().getCity());
            setAdapter();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
