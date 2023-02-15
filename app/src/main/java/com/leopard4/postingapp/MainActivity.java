package com.leopard4.postingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.leopard4.postingapp.adapter.PostingAdapter;
import com.leopard4.postingapp.api.NetworkClient;
import com.leopard4.postingapp.api.PostingApi;
import com.leopard4.postingapp.config.Config;
import com.leopard4.postingapp.model.Posting;
import com.leopard4.postingapp.model.PostingList;
import com.leopard4.postingapp.model.ResMessage;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;
    ProgressBar progressBar;
    String accessToken;

    RecyclerView recyclerView;
    PostingAdapter adapter;
    ArrayList<Posting> postingList = new ArrayList<>();

    // 페이징 처리를 위한 변수
    int count = 0;
    int offset = 0;
    int limit = 3;
    private Posting selectedPosting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 액세스 토큰이 있는지 확인
        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        accessToken = sp.getString(Config.ACCESS_TOKEN, ""); // 액세스 토큰이 없으면 "" 리턴
        if(accessToken.isEmpty()){
            // 액세스 토큰이 없으면 로그인 화면으로 이동
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
            return; // onCreate() 종료
        }

        btnAdd = findViewById(R.id.btnAdd);
        progressBar = findViewById(R.id.progressBar);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);

            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        getNetworkData();
    }
    void getNetworkData(){
        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);
        PostingApi api = retrofit.create(PostingApi.class);

        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        accessToken = "Bearer " + sp.getString(Config.ACCESS_TOKEN, ""); // 액세스 토큰이 없으면 "" 리턴

        offset = 0;

        Call<PostingList> call = api.getPosting(accessToken,offset,limit);


        call.enqueue(new Callback<PostingList>() {
            @Override
            public void onResponse(Call<PostingList> call, Response<PostingList> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    postingList.clear();

                    count = response.body().getCount();
                    postingList.addAll(response.body().getItems());

                    offset = offset + count;

                    adapter = new PostingAdapter(MainActivity.this, postingList);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<PostingList> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

            }
        });

    }
    public void likeProcess(int index){

        selectedPosting = postingList.get(index);

        // 2. 해당행의 좋아요가 이미 좋아요인지 아닌지 파악
        if (selectedPosting.getIsLike() == 0) {
            // 3. 해당 좋아요에 맞는 좋아요 API를 호출
            Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);
            PostingApi api = retrofit.create(PostingApi.class);
            SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, Context.MODE_PRIVATE);
            accessToken ="Bearer " +  sp.getString(Config.ACCESS_TOKEN, "");
            Call<ResMessage> call = api.setLike(accessToken, selectedPosting.getPostingId());

            call.enqueue(new Callback<ResMessage>() {
                @Override
                public void onResponse(Call<ResMessage> call, Response<ResMessage> response) {
                    if (response.isSuccessful()){
                        // 4. 화면에 결과를 표시
                        selectedPosting.setIsLike(1);
                        adapter.notifyDataSetChanged();

                    }else {

                    }
                }

                @Override
                public void onFailure(Call<ResMessage> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "서버와 통신이 원활하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            });

        }else {
            // 3. 좋아요 해제 API를 호출
            // 3. 해당 좋아요에 맞는 좋아요 API를 호출
            Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);
            PostingApi api = retrofit.create(PostingApi.class);
            SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, Context.MODE_PRIVATE);
            accessToken = "Bearer " + sp.getString(Config.ACCESS_TOKEN, "");
            Call<ResMessage> call = api.deleteLike(accessToken, selectedPosting.getPostingId());

            call.enqueue(new Callback<ResMessage>() {
                @Override
                public void onResponse(Call<ResMessage> call, Response<ResMessage> response) {
                    if (response.isSuccessful()) {
                        // 4. 화면에 결과를 표시
                        selectedPosting.setIsLike(0);
                        adapter.notifyDataSetChanged();

                    } else {

                    }
                }

                @Override
                public void onFailure(Call<ResMessage> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "서버와 통신이 원활하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}