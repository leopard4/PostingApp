package com.leopard4.postingapp.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.leopard4.postingapp.MainActivity;
import com.leopard4.postingapp.R;
import com.leopard4.postingapp.api.NetworkClient;
import com.leopard4.postingapp.api.PostingApi;
import com.leopard4.postingapp.config.Config;
import com.leopard4.postingapp.model.Posting;
import com.leopard4.postingapp.model.Res;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class PostingAdapter extends RecyclerView.Adapter<PostingAdapter.ViewHolder>{
    Context context;
    ArrayList<Posting> postingList;
    SimpleDateFormat sdf;
    SimpleDateFormat df;
    Posting selectedPosting;

    public PostingAdapter(Context context, ArrayList<Posting> postingList) {
        this.context = context;
        this.postingList = postingList;

        // 2023-01-17T00:38:36 => 2023-01-17 00:38:36
        // UTC => 로컬타임으로 변경
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        df.setTimeZone(TimeZone.getDefault()); // 내 핸드폰에 셋팅된 타임으로 변경하라는 뜻

    }

    @NonNull
    @Override
    public PostingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.posting_row, parent, false);
        return new PostingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostingAdapter.ViewHolder holder, int position) {
        Posting posting = postingList.get(position);

        holder.txtContent.setText(posting.getContent());
        holder.txtEmail.setText(posting.getEmail());

        try {
            Date date = sdf.parse(posting.getCreatedAt());
            holder.txtCreatedAt.setText(df.format(date));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }



        // 좋아요 이미지 설정
        if (posting.getIsLike() == 1){
            holder.imgLike.setImageResource(R.drawable.ic_thumb_up_2);
        }else {
            holder.imgLike.setImageResource(R.drawable.ic_thumb_up_1);
        }

        Glide.with(context)
                .load(posting.getImageUrl())
                .placeholder(R.drawable.ic_outline_add_photo_alternate_24)
                .into(holder.imgPhoto);

    }

    @Override
    public int getItemCount() {
        return postingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView txtContent, txtCreatedAt, txtEmail;
        ImageView imgLike, imgPhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            txtContent = itemView.findViewById(R.id.txtContent);
            txtCreatedAt = itemView.findViewById(R.id.txtCreatedAt);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            imgLike = itemView.findViewById(R.id.imgLike);
            imgPhoto = itemView.findViewById(R.id.imgPhoto);

            imgLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 1. 어느번째의 데이터의 좋아요를 누른것인지 확인
                    int index = getAdapterPosition();
                    ((MainActivity)context).likeProcess(index);

                }
            });

        }
    }
}
