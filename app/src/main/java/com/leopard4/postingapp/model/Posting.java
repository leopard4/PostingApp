package com.leopard4.postingapp.model;

public class Posting  {
//            "followeeId": 8,
//                    "email": "qq@naver.com",
//                    "postingId": 36,
//                    "content": "핸드폰 ",
//                    "imageUrl": "http://leopard4-ai-image-class-yh.s3.ap-northeast-2.amazonaws.com/82023-01-17T003845.994307.png",
//                    "createdAt": "2023-01-17 00:38:46",
//                    "isLike": 1
    private int followeeId;
    private String email;
    private int postingId;
    private String content;
    private String imageUrl;
    private String createdAt;
    private int isLike;

    public int getFolloweeId() {
        return followeeId;
    }

    public void setFolloweeId(int followeeId) {
        this.followeeId = followeeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPostingId() {
        return postingId;
    }

    public void setPostingId(int postingId) {
        this.postingId = postingId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }
}
