package life.majiang.community.dto;

import lombok.Data;

import java.util.UUID;

/**
 * @author 谭俊彦
 * @version 1.0 2020-04-09 9:11
 * @description
 */
@Data
public class User {
    private Integer id;
    //在别的组织如github是id
    private String accountId;

    private String name;

    private String email;
    //用于cookie的标记
    private String token;
    //类似记言
    private String bio;

    // token的有效时间
    private Long gmtToken;

    private Long gmtCreate;

    private Long gmtModified;

    private  String avatarUrl;

    /**
     * 填充一个user对象，如果user为null new一个
     * */
    public static User createOneUser(User user,GithubUser githubUser){
        if(user==null){
            user = new User();
            user.setGmtCreate(System.currentTimeMillis());
            user.setId(0);
        }
        String cookieToken = UUID.randomUUID().toString();
        Long gt  = System.currentTimeMillis() + 60*60*24*1000L;
        user.setAccountId(githubUser.getId().toString());
        user.setName(githubUser.getName());
        user.setEmail(githubUser.getEmail());
        user.setBio(githubUser.getBio());
        user.setToken(cookieToken);
        user.setGmtToken(gt);
        user.setGmtModified(System.currentTimeMillis());
        user.setAvatarUrl(githubUser.getAvatar_url());
        return user;
    }
}
