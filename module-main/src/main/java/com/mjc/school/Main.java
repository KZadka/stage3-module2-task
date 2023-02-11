package com.mjc.school;

import com.mjc.school.config.AppConfiguration;
import com.mjc.school.controller.menu.Menu;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        Menu menu = applicationContext.getBean(Menu.class);
        menu.start();
    }


}
