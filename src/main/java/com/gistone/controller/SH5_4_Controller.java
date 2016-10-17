package com.gistone.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gistone.MyBatis.config.GetBySqlMapper;
/**
 * 系统管理-维护控制
 * @author chendong
 *
 */
@RestController
@RequestMapping
public class SH5_4_Controller{
//	
//	@Autowired
//	private GetBySqlMapper getBySqlMapper;
//	
//	/**
//	 * 按钮开关（打开和关闭触发这个方法）
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException 
//	 */
//	@RequestMapping("getWhkz_OpenRole.do")
//	public void getWhkz_OpenRole(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		
//		String name = request.getParameter("name");//获取模块按钮的名字 例如：走访情况按钮和帮扶措施按钮
//		String id = request.getParameter("id");//获取开关的ID 例如：0是关闭，1是开启
//		JSONArray jsonArray =new JSONArray();
//		this.getBySqlMapper.update("update sys_function set maintain='"+id+"'  where modular_name ='"+name+"'");
//		
//		response.getWriter().write(jsonArray.toString());
//		response.getWriter().close();
//	}
//	/**
//	 * 维护开关的初始化
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException
//	 */
//	@RequestMapping("getWhkz_SaveMaintain.do")
//	public void getWhkz_SaveMaintain(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		
//		response.setCharacterEncoding("UTF-8");
//		request.setCharacterEncoding("UTF-8");
//		
//		String sql="select pkid,modular_name ,maintain from sys_function"; //获取按钮的数据
//		List<Map> list=getBySqlMapper.findRecords(sql);
//		JSONArray jsonArray =new JSONArray();
//		for(Map val:list){
//			JSONObject obj=new JSONObject ();
//			obj.put("pkid",val.get("pkid")==null?"-":val.get("pkid"));
//			obj.put("modular_name", val.get("modular_name")==null?"-":val.get("modular_name"));//角色名称
//			obj.put("maintain",val.get("maintain")==null?"-":val.get("maintain")); //获取开关的ID 例如：0是关闭，1是开启
//			jsonArray.add(obj);
//		}
//		response.getWriter().write(jsonArray.toString());
//		response.getWriter().close();
//	}
}
