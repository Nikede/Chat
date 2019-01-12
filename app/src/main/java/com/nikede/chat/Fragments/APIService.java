package com.nikede.chat.Fragments;

import com.nikede.chat.Notifications.MyResponse;
import com.nikede.chat.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAez1Mvrw:APA91bFRorBrcnyPRpjNnD_ri4NE4EfaVwwD__ai1RqKl5x-xCSRUeXklAuDw01710Tup0JEI9sePjcIC5HlOksczasX5rBjCgQKVI_1Gpt3aW5JphNmzUwV2KdH2TV1SPtbK4ss2YYN"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
