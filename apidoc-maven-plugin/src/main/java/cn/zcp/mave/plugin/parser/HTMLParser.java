package cn.zcp.mave.plugin.parser;

import cn.zcp.mave.plugin.Annotation.ApiDoc;
import cn.zcp.mave.plugin.Annotation.DoMain;
import cn.zcp.mave.plugin.Annotation.ParamRule;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhongchunping
 * @version 1.0
 * @Time 2019-08-21 15:33
 * @describe maven-plugin-parent
 *
 * HTML解析器,解析成为文档
 */
public class HTMLParser {

    /**
     * 解析@Controller注解,拿到注解相关参数
     * @param targetFile 最后生产的目标文件
     * @param controllerCls 所有Controller Class对象
     *
     * [
     *     {
     *         "module":"用户管理",
     *         "methods":[
     *             {
     *                 "name":"接口名称",
     *                 "author":"作者",
     *                 "time":"发布时间",
     *                 "desc":"功能描述",
     *                 "reqType":"请求方式",
     *                 "url":"请求地址",
     *                 "params":[{
     *                         "name":"xx",
     *                         "type":"xx",
     *                         "required":"xx",
     *                         "defualtVal":"xx",
     *                         "desc":"xx"
     *                     }],
     *                 "return":"返回说明"
     *             }
     *         ]
     *     }
     * ]
     */
    public static void generate(String targetFile, List<Class> controllerCls) {
        System.out.println("--生成目标文档地址--"+targetFile);
        //System.out.println("--controllerCls--"+controllerCls);
        List<Object> list = new ArrayList();
        for (Class cls : controllerCls) {
            if(cls.isAnnotationPresent(Controller.class)){

                Map<String,Object> moduleMap = new HashMap<>();
                List<Object> methodList = new ArrayList<>();
                String path="";
                String reqType="";
                //获取父及请求地址
                if(cls.isAnnotationPresent(RequestMapping.class)){
                    RequestMapping reqAnno = (RequestMapping) cls.getAnnotation(RequestMapping.class);
                    for (RequestMethod requestMethod : reqAnno.method()) {
                        reqType+=requestMethod.name()+",";
                    }
                    path = reqAnno.value()[0];
                }
                //获取DoMain
                if(cls.isAnnotationPresent(DoMain.class)){
                    DoMain doMain = (DoMain) cls.getAnnotation(DoMain.class);
                    moduleMap.put("module",doMain.value());
                }else{
                    moduleMap.put("module","");
                }
                //循环获取Method
                Method[] methods = cls.getMethods();
                for (Method method : methods) {
                    if (Object.class.equals(method.getDeclaringClass())) {
                        continue;
                    }
                    Map<String,Object> methodMap = new HashMap<>();
                    List<Object> paramlist = new ArrayList<>();
                    Map<String,String> paramsMap = new HashMap<>();
                    if(method.isAnnotationPresent(RequestMapping.class)){
                        RequestMapping reqAnno = method.getAnnotation(RequestMapping.class);
                        if(reqType==""){
                            for (RequestMethod requestMethod : reqAnno.method()) {
                                reqType+=requestMethod.name()+",";
                            }
                            methodMap.put("reqType",reqType);
                            reqType="";
                        }else{
                            methodMap.put("reqType",reqType);
                        }
                        methodMap.put("url",path+reqAnno.value()[0]);
                    }
                    if(method.isAnnotationPresent(ApiDoc.class)){
                        ApiDoc apiAnno = method.getAnnotation(ApiDoc.class);
                        methodMap.put("name",apiAnno.name());
                        methodMap.put("author",apiAnno.author());
                        methodMap.put("time",apiAnno.time());
                        methodMap.put("desc",apiAnno.desc());

                        ParamRule[] params = apiAnno.params();
                        for (ParamRule param : params) {
                            paramsMap.put(param.name(),param.desc());
                        }
                    }
                    //一个参数可能有多个注解
                    Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                    for (Annotation[] parameterAnnotation : parameterAnnotations) {
                        Map<String,String> methodParamMap = new HashMap<>();
                        for (Annotation annotation : parameterAnnotation) {
                            if(annotation instanceof RequestParam){
                                RequestParam rqAnno = (RequestParam) annotation;
                                methodParamMap.put("name", rqAnno.value());
                                methodParamMap.put("type", "字符串");
                                methodParamMap.put("required", rqAnno.required()+"");
                                methodParamMap.put("defualtVal",
                                        StringUtils.trimToEmpty(rqAnno.defaultValue()
                                                .equals("\n\t\t\n\t\t\n\ue000\ue001\ue002\n\t\t\t\t\n")?
                                                "":rqAnno.defaultValue()));
                                methodParamMap.put("desc", paramsMap.get(rqAnno.value()));
                                paramlist.add(methodParamMap);
                            }
                        }
                    }
                    methodMap.put("params",paramlist);
                    methodList.add(methodMap);
                }
                moduleMap.put("methods",methodList);
                list.add(moduleMap);
            }
        }
        System.out.println(JSON.toJSONString(list));
    }
}
