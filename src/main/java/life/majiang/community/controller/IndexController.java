package life.majiang.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 谭俊彦
 * @version 1.0 2020-03-19 11:42
 * @description
 */
@RestController
public class IndexController {

    /*
    * 注释@RestController 跳转页面就不能直接返回“index”。需要通过ModelAndView来转
    * 有文件夹前面加xx/xxxx
    * */
    @GetMapping("/")
    public ModelAndView index(){
        return new ModelAndView("index");
    }
}
