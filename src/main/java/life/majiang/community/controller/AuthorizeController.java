package life.majiang.community.controller;

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithubUser;
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

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

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
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;


    @GetMapping("/callback")
    public ModelAndView callback(@RequestParam(name = "code") String code,
                                 @RequestParam(name ="state") String state,
                                 HttpServletRequest request){
        final AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        if(accessToken!=null){
            String token = accessToken.split("&")[0].split("=")[1];
            GithubUser githubUser = githubProvider.getGithubUser(token);
            log.info(githubUser.toString());
            if(githubUser!= null){
                request.getSession().setAttribute("user",githubUser);
                return new ModelAndView("redirect:/index");
            }
        }
        else{
            log.info("token is null");
        }
        return new ModelAndView("redirect:/index");
    }
}
