package com.leopard4.postingapp.api;

import com.leopard4.postingapp.config.Config;
import com.leopard4.postingapp.model.PostingList;
import com.leopard4.postingapp.model.Res;

import java.io.Serializable;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PostingApi extends Serializable {

    // 포스팅 생성 api
    @Multipart // 파일을 업로드할 때 사용 // 파일이 크니까 쪼개서 보낸다
    @POST("/posting")
    Call<Res> addPosting(@Header ("Authorization") String token,
                         @Part MultipartBody.Part photo, // 파일
                         @Part("content") RequestBody content); // 내용

    // 친구들의 포스팅 가져오는 api
    @GET("/posting")
    Call<PostingList> getPosting(@Header ("Authorization") String token, // 헤더에 토큰을 넣어서 보낸다
                                 @Query ("offset") int offset,
                                 @Query ("limit") int limit);
    // 좋아요 하는 api
    @POST("/posting/{postingId}/like")
    Call<Res> setLike(@Header ("Authorization") String token,
                          @Path("postingId") int postingId);
    // 좋아요 취소하는 api
    @DELETE("/posting/{postingId}/like")
    Call<Res> deleteLike(@Header ("Authorization") String token,
                         @Path("postingId") int postingId);

}
