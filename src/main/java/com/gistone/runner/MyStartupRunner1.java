package com.gistone.runner;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.gistone.MyBatis.config.GetBySqlMapper;
import com.gistone.controller.Cache_Controller;
import com.gistone.util.OverallSituation;
/**
 * 服务启动执行
 *
 * @author   
 * @myblog  
 * @create    
 */
@Component
@Order(value=1)
public class MyStartupRunner1 implements CommandLineRunner {
	
	@Autowired
	private GetBySqlMapper getBySqlMapper;
	
	@Autowired
	private Cache_Controller cache_Controller;
	
	@Override
    public void run(String... args) throws Exception {
    	

    	//System.out.println("-------------项目启动啦-----------");
//    	OverallSituation.compartment = this.cache_Controller.getCompartment();
    	OverallSituation.comp = this.cache_Controller.getComp();
		System.out.println("-------------加载完成-----------");
//		System.out.println("-------------"+OverallSituation.comp.toString()+"-----------");
//		System.out.println("-------------"+OverallSituation.compartment.toString()+"-----------");
    }
    

}