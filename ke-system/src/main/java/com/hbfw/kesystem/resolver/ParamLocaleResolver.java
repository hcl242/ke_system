package com.hbfw.kesystem.resolver;

import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@ConditionalOnNotWebApplication
public class ParamLocaleResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest req) {
        String local = req.getParameter("locale");
        String header = req.getHeader("Accept-Language");
        Locale locale = Locale.getDefault();
        boolean flag = !(local!=null&&!local.isEmpty());
        if(!flag){
            try {
                String[] split = local.split("_");
                locale = new Locale(split[0],split[1]);
            } catch (Exception e) {
                flag = true;
            }
        }
        if(flag){
            String[] split = header.split(",");
            split = split[0].split("-");
            locale = new Locale(split[0],split[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
