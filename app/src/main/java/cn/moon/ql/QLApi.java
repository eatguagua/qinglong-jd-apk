package cn.moon.ql;

import static cn.moon.ql.Config.QL_URL;
import static cn.moon.ql.Config.CLIENT_ID;
import static cn.moon.ql.Config.CLIENT_SECRET;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class QLApi {


    private String token;
    OkHttpClient client = new OkHttpClient();


    public void login() throws Exception {
        JSONObject data = (JSONObject) this.send("/open/auth/token?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET, "GET", null);

        String tokenType = data.getString("token_type");
        String tokenValue = data.getString("token");

        token = tokenType + " " + tokenValue;
    }

    public JSONObject delete(int id) throws Exception {
        JSONArray arr = new JSONArray();
        arr.put(id);

        return (JSONObject) this.send("/open/envs", "DELETE", arr.toString());
    }

    public JSONArray list() throws Exception {
        return (JSONArray) this.send("/open/envs", "GET", null);
    }

    public void add(JSONArray obj) throws Exception {
        this.send("/open/envs", "POST", obj.toString());
    }


    private Object send(String uri, String method, String content) throws Exception {
        RequestBody body = null;
        if (content != null) {
            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
            body = RequestBody.create(mediaType, content);
        }

        Request.Builder builder = new Request.Builder();
        if (token != null) {
            builder.addHeader("Authorization", token);
        }
        Request request = builder
                .url(QL_URL + uri)
                .method(method, body)
                .build();
        Response response = client.newCall(request).execute();

        String rsBody = response.body().string();
        JSONObject rs = new JSONObject(rsBody);

        if (!response.isSuccessful()) {

            throw new IllegalStateException(uri + " " +rs.getString("message"));
        }


        System.out.println(rs);
        if (rs.getInt("code") != 200) {
            String message = rs.getString("message");
            throw new IllegalStateException(message);
        }
        if (rs.has("data")) {
            return rs.get("data");
        }

        return null;

    }


}
