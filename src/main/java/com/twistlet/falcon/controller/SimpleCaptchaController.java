package com.twistlet.falcon.controller;

import static nl.captcha.Captcha.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.captcha.Captcha;
import nl.captcha.backgrounds.GradiatedBackgroundProducer;
import nl.captcha.servlet.CaptchaServletUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SimpleCaptchaController {
	
	@RequestMapping(value="/registration/captcha/image")
	public void getCaptcha(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
		
        Captcha captcha = new Captcha.Builder(200, 50)
        	.addText()
        	.addBackground(new GradiatedBackgroundProducer())
            .gimp()
            .addBorder()
            .build();
        req.getSession().setAttribute(NAME, captcha);
        CaptchaServletUtil.writeImage(resp, captcha.getImage());   
    }
	
	@RequestMapping(value="/captcha/test")
	public ModelAndView getACaptcha(){
		ModelAndView mav = new ModelAndView("/error/403");
		return mav;
    }

}
