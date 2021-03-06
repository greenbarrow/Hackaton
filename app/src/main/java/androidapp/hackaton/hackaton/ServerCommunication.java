package androidapp.hackaton.hackaton;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServerCommunication {
    private final OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build();


    public String uploadUserPhoto(File image) throws Exception {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("FILE", "logo-square.png",
                        RequestBody.create(MediaType.parse("image/png"), image))
                .build();
        Request request = new Request.Builder()
                .url("http://djangoimghandlerenv.y39mhtmw34.us-west-2.elasticbeanstalk.com/image")
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            else if(response.code() == 204){
                throw new NoContentException();
            }
            return response.body().string();
        }
    }

}
