package life.majiang.community.controller;

import life.majiang.community.dto.GithubUser;
import life.majiang.community.dto.Question;
import life.majiang.community.dto.User;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 谭俊彦
 * @version 1.0 2020-04-12 11:47
 * @description
 */
@Controller
@Slf4j
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public ModelAndView publish(HttpServletRequest request,
                              HttpServletResponse response){

        return new ModelAndView("publish");
    }

    @PostMapping("/publish")
    public ModelAndView doPublish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tags", required = false) String tags,
            HttpServletRequest request,
            Model model){
        User user = null;
        Cookie[] cookies = request.getCookies();
        if(cookies !=null && cookies.length!=0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    //cookie token 有效期内才能使用。
                    Long currentTime = System.currentTimeMillis();
                    user = userMapper.findByToken(cookie.getValue(), currentTime);
                    if (user != null) {
                        break;
                    }
                }
            }
        }
        if(user == null){
            model.addAttribute("error","用户未登录");
            return new ModelAndView("publish");
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTags(tags);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setCreator(user.getId());
        questionMapper.create(question);
        return new ModelAndView("redirect:/index");
    }
}
