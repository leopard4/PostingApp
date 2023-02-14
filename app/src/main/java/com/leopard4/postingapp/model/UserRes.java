package com.leopard4.postingapp.model;

import java.io.Serializable;

public class UserRes implements Serializable {
//     포스트맨  response 부분
//    {
//        "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTY3NjMzNjU3OSwianRpIjoiZDA5OTFiNjAtMGNkZC00OTc0LWIzODAtNmUzYzQxOGYzNjMyIiwidHlwZSI6ImFjY2VzcyIsInN1YiI6MTEsIm5iZiI6MTY3NjMzNjU3OX0.oL3WMva7R5Hkl8jAPDQqOVO57w17eyLKwyfRHy5YyQ0"
//    }

    String access_token;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
