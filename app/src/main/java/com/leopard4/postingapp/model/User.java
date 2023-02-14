package com.leopard4.postingapp.model;

import java.io.Serializable;

public class User implements Serializable {
    // 포스트맨 body 부분
//    {
//        "username": "TITI",
//            "email": "TTT@naver.com",
//            "password": "1234"
//    }

    private String username;
    private String email;
    private String password;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
