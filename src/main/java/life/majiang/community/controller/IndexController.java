package life.majiang.community.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import life.majiang.community.dto.GithubUser;
import life.majiang.community.dto.Question;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.dto.User;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 谭俊彦
 * @version 1.0 2020-03-19 11:42
 * @description
 */
@Controller
@Slf4j
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    /*
     * 注释@RestController 跳转页面就不能直接返回“index”。需要通过ModelAndView来转
     * 有文件夹前面加xx/xxxx
     * */
    @GetMapping("/index")
    public ModelAndView index(HttpServletRequest request,
                              HttpServletResponse response,
                              Model model,
                              @RequestParam(name = "page", defaultValue = "1") Integer page,
                              @RequestParam(name = "size", defaultValue = "5") Integer size) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                log.info("cookie:" + cookie.getName());
                if (cookie.getName().equals("token")) {
                    //cookie token 有效期内才能使用。
                    Long currentTime = System.currentTimeMillis();
                    User user = userMapper.findByToken(cookie.getValue(), currentTime);
                    if (user != null) {
                        GithubUser githubUser = new GithubUser();
                        //githubUser.setId(user.getAccount_id());
                        githubUser.setName(user.getName());
                        githubUser.setBio(user.getBio());
                        githubUser.setEmail(user.getEmail());
                        request.getSession().removeAttribute("user");
                        request.getSession().setAttribute("user", githubUser);
                        break;
                    }
                }
            }
        }

        List<QuestionDTO> questionList = questionService.list(page, size);
        model.addAttribute("questions", questionList);
        DateTime date = DateUtil.date(System.currentTimeMillis());
        String s = DateUtil.formatDate(date);
        log.info(s);


        return new ModelAndView("index");
    }
}
