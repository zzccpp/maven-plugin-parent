package cn.zcp.mave.plugin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhongchunping
 * @version 1.0
 * @Time 2019-08-15 10:48
 * @describe maven-plugin-parent <描述>
 */
@Controller
@RequestMapping(value="/hello")
public class HelloController {

    public HelloController() {
        System.out.println("HelloController init.....");
    }

    @RequestMapping(value="/hi",method={RequestMethod.GET,RequestMethod.POST})
    public String hello(HttpServletRequest request){
        System.out.println("hello.....");
        request.setAttribute("name","zcp");
        return "hello";
    }

}
