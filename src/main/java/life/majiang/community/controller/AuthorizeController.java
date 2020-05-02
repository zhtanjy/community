package life.majiang.community.controller;

import cn.hutool.core.convert.Convert;
import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithubUser;
import life.majiang.community.dto.User;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.provider.GithubProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author 谭俊彦
 * @version 1.0 2020-04-09 16:23
 * @description
 */
@Controller
@Slf4j
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserMapper userMapper;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;


    @GetMapping("/callback")
    public ModelAndView callback(@RequestParam(name = "code") String code,
                                 @RequestParam(name ="state") String state,
                                 HttpServletRequest request,
                                 HttpServletResponse response){
        final AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClientId(clientId);
        accessTokenDTO.setClientSecret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        if(accessToken!=null){
            String token = accessToken.split("&")[0].split("=")[1];
            GithubUser githubUser = githubProvider.getGithubUser(token);
            log.info(githubUser.toString());
            if(githubUser!= null){
                User user = userMapper.findByAccountId(githubUser.getId().toString());
                User userdb = User.createOneUser(user,githubUser);
                if(userdb.getId()==0){
                    userMapper.insert(userdb);
                }else{
                    userMapper.updateUserLoginInfo(userdb);
                }
                request.getSession().setAttribute("user",githubUser);
                Cookie cookie = new Cookie("token",userdb.getToken());
                cookie.setMaxAge(Convert.toInt(userdb.getGmtToken()));
                response.addCookie(cookie);
                return new ModelAndView("redirect:/index");
            }
        }
        else{
            log.info("token is null");
        }
        return new ModelAndView("redirect:/index");
    }
}
