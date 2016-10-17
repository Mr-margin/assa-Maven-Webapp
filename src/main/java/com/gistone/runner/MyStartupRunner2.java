package com.gistone.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 服务启动执行
 *
 * @author   
 * @myblog  
 * @create    
 */
@Component
@Order(value=2)
public class MyStartupRunner2 implements ApplicationRunner  {
	
	@Override  
    public void run(ApplicationArguments args) throws Exception {  
//        System.out.println(args);  
//        System.out.println("这个是测试ApplicationRunner接口");
//        this.cache_Controller.getXzqh();
    }
	
}
