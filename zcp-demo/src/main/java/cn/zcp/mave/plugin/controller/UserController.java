package cn.zcp.mave.plugin.controller;

import cn.zcp.mave.plugin.Annotation.ApiDoc;
import cn.zcp.mave.plugin.Annotation.DoMain;
import cn.zcp.mave.plugin.Annotation.ParamRule;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhongchunping
 * @version 1.0
 * @Time 2019-08-21 16:00
 * @describe maven-plugin-parent <描述>
 */
@Controller
@RequestMapping(value = "/user",method = RequestMethod.GET)
@DoMain(value = "用户模块")
public class UserController {


    @ApiDoc(author="zcp",
            name="登录接口",
            time="2019-08-27",
            desc="登录接口描述",
            params={@ParamRule(name = "username",desc="用户名称"),
                    @ParamRule(name = "type",desc="用户类别")})
    @RequestMapping(value = "/doLogin",method = RequestMethod.GET)
    @ResponseBody
    public String login(@RequestParam(value = "username") String username, String type){
        System.out.println("username = [" + username + "], type = [" + type + "]");
        return "";
    }

    @ApiDoc(author="zcp",
            name="注册接口",
            time="2019-08-27",
            desc="注册接口描述",
            params={@ParamRule(name = "username",desc="用户名称"),
                    @ParamRule(name = "age",desc="用户类别"),
                    @ParamRule(name = "mail",desc="邮箱"),
                    @ParamRule(name = "tel",desc="用户手机号")})
    @RequestMapping(value = "/doLogin",method = RequestMethod.GET)
    @ResponseBody
    public String register(@RequestParam(value = "username",required = true) String username,
                           @RequestParam(value = "age") String age,
                           @RequestParam(value = "mail",required = false) String mail,
                           @RequestParam(value = "tel",defaultValue = "13333333333") String tel){

        System.out.println("username = [" + username + "], age = [" + age + "], mail = [" + mail + "]," +
                " tel = [" + tel + "]");
        return "";
    }

    @ApiDoc(author="zcp",
            name="授权接口",
            time="2019-08-27",
            desc="这是一个授权接口",
            params={@ParamRule(name = "uId",desc="用户Id")})
    @RequestMapping(value = "/doLogin",method = RequestMethod.GET)
    @ResponseBody
    public String auth(@RequestParam(value = "uId",required = true) String uId){

        return "";
    }
}
