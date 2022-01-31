package pers.prover07.boot.custom;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author by Prover07
 * @classname MyListener
 * @description TODO
 * @date 2022/1/31 17:38
 */
@Slf4j
//@WebListener
public class MyListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
      log.info("ServletContext init...");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("ServletContext destory...");
    }
}
