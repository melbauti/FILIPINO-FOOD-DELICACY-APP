package com.FilipinoFoodDelicacy.app.SendNotification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAQev13gg:APA91bGZyUNH29ZTQvNCggKf856d1NwGf8zPzir0TvG11WTfXKS-bj2bJRM1XbT7i0Jf2RvscjDRrVeAR3lmSwlYIkHKazZM4_HVRtaHjjHq71o8BisJkIW1BC_D6emK8NaDZVrCrtgJ"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body NotificationSender body);
}
