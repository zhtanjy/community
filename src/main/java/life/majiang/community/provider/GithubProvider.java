package life.majiang.community.provider;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithubUser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author 谭俊彦
 * @version 1.0 2020-04-09 16:32
 * @description
 */
@Component
@Slf4j
public class GithubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request)
                .execute()) {
            String result = response.body().string();
            log.info(result);
            return result;
        }catch (IOException e){

        }

        //HashMap<String, Object> paramMap = new HashMap<>();
//        HashMap<String, Object> paramMap=JSON.parseObject(JSON.toJSONString(accessTokenDTO), HashMap.class);
//
//        String result= HttpUtil.post("https://github.com/login/oauth/access_token", paramMap);
//        log.info(result);
//        return result;
        return null;
    }

    public GithubUser getGithubUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 当无法识别页面编码的时候，可以自定义请求页面的编码
//       String result= HttpUtil.get("https://api.github.com/user?access_token=" + accessToken, CharsetUtil.CHARSET_UTF_8);
//       if(result!=null){
//           GithubUser githubUser = JSON.parseObject(result, GithubUser.class);
//           return githubUser;
//       }
        return null;
    }
}
