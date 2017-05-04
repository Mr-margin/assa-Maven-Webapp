package com.gistone.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;


import java.io.ByteArrayInputStream;  

 
  




import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gistone.MyBatis.config.GetBySqlMapper;
import com.gistone.util.CaptchaUtil;
import com.gistone.util.OverallSituation;
import com.gistone.util.Tool;

@RestController
@RequestMapping
public class Index_Controller{
	@Autowired
	private GetBySqlMapper getBySqlMapper;
	
	private ByteArrayInputStream inputStream; 
	
	//获取验证码的方法
	@RequestMapping("img.do")
	@ResponseBody
	public void execute(HttpServletRequest request , HttpServletResponse response) throws Exception {  
       
		CaptchaUtil.outputCaptcha(request, response);
        
    }  
	@RequestMapping("yzm.do")
	public void yzm(HttpServletRequest request , HttpServletResponse response) throws Exception {  
	       
		String zhi= request.getParameter("zhi");
		HttpSession session = request.getSession();//取session
		String randomString=session.getAttribute("randomString").toString();
		zhi = zhi.toUpperCase();
//		System.out.println(zhi+"----"+randomString);
		if(zhi.equals(randomString)){
			response.getWriter().write("1");
		}else{
			response.getWriter().write("0");
		}
    }  
	

	
	//判断单位层级，生成单位树结构
	public Object[] get_company_tree(String com_level, String sys_company_id, String pkid, String com_name){
		Object[] obj = new Object[2];
		JSONObject company_tree = new JSONObject();
		if(com_level.equals("1")){
			Map Com = new HashMap();
			Com.put("id_1", pkid);
			Com.put("name_1", com_name);
			obj[0] = Com;
			
			company_tree = OverallSituation.comp;
			
		}else if(com_level.equals("2")){
			
			String sql_3 = "select t2.pkid id_1,t2.com_name name_1,t1.pkid id_2,t1.com_name name_2 from sys_company t1 "
					+ "join sys_company t2 on t1.com_f_pkid=t2.pkid where t1.pkid="+sys_company_id;
			Map Com = this.getBySqlMapper.findRecords(sql_3).get(0);
			obj[0] = Com;
			
			JSONArray ja = JSONArray.fromObject(OverallSituation.comp.get("nodes"));//所有的市
			for (int i = 0; i < ja.size(); i++) {
				JSONObject val = JSONObject.fromObject(ja.get(i));
				if(val.get("com_name").toString().equals(Com.get("NAME_2").toString())){
					company_tree = val;
					break;
				}
			}
			
		}else if(com_level.equals("3")){
			
			String sql_3 = "select t3.pkid id_1,t3.com_name name_1,t2.pkid id_2,t2.com_name name_2,t1.pkid id_3,t1.com_name name_3 from sys_company t1 "
					+ "join sys_company t2 on t1.com_f_pkid=t2.pkid join sys_company t3 on t2.com_f_pkid=t3.pkid where t1.pkid="+sys_company_id;
			Map Com = this.getBySqlMapper.findRecords(sql_3).get(0);
			obj[0] = Com;
			
			JSONArray ja = JSONArray.fromObject(OverallSituation.comp.get("nodes"));//所有的市
			for (int i = 0; i < ja.size(); i++) {
				JSONObject val = JSONObject.fromObject(ja.get(i));
				if(val.get("com_name").toString().equals(Com.get("NAME_2").toString())){
					JSONArray jb = JSONArray.fromObject(val.get("nodes"));//所有的县
					for (int j = 0; j < jb.size(); j++) {
						JSONObject val_3 = JSONObject.fromObject(jb.get(j));
						if(val_3.get("com_name").toString().equals(Com.get("NAME_3").toString())){
							company_tree = val_3;
							break;
						}
					}
					break;
				}
			}
			
		}else if(com_level.equals("4")){
			
			String sql_3 = "select t4.pkid id_1,t4.com_name name_1,t3.pkid id_2,t3.com_name name_2,t2.pkid id_3,t2.com_name name_3,t1.pkid id_4,t1.com_name name_4 from "
					+ "sys_company t1 join sys_company t2 on t1.com_f_pkid=t2.pkid join sys_company t3 on t2.com_f_pkid=t3.pkid "
					+ "join sys_company t4 on t3.com_f_pkid=t4.pkid where t1.pkid="+sys_company_id;
			Map Com = this.getBySqlMapper.findRecords(sql_3).get(0);
			obj[0] = Com;
			
			JSONArray ja = JSONArray.fromObject(OverallSituation.comp.get("nodes"));//所有的市
			for (int i = 0; i < ja.size(); i++) {
				JSONObject val = JSONObject.fromObject(ja.get(i));
				if(val.get("com_name").toString().equals(Com.get("NAME_2").toString())){
					JSONArray jb = JSONArray.fromObject(val.get("nodes"));//所有的县
					for (int j = 0; j < jb.size(); j++) {
						JSONObject val_3 = JSONObject.fromObject(jb.get(j));
						if(val_3.get("com_name").toString().equals(Com.get("NAME_3").toString())){
							JSONArray jc = JSONArray.fromObject(val_3.get("nodes"));//所有的乡
							for (int k = 0; k < jc.size(); k++) {
								JSONObject val_4 = JSONObject.fromObject(jc.get(k));
								if(val_4.get("com_name").toString().equals(Com.get("NAME_4").toString())){
									company_tree = val_4;
									break;
								}
							}
							break;
						}
					}
					break;
				}
			}
			
		}else if(com_level.equals("5")){
			
			String sql_3 = "select t4.pkid id_1,t4.com_name name_1,t3.pkid id_2,t3.com_name name_2,t2.pkid id_3,t2.com_name name_3,t1.pkid id_4,t1.com_name name_4 from "
					+ "sys_company t1 join sys_company t2 on t1.com_f_pkid=t2.pkid join sys_company t3 on t2.com_f_pkid=t3.pkid "
					+ "join sys_company t4 on t3.com_f_pkid=t4.pkid where t1.pkid="+sys_company_id;
			Map Com = this.getBySqlMapper.findRecords(sql_3).get(0);
			obj[0] = Com;
			
			JSONArray ja = JSONArray.fromObject(OverallSituation.comp.get("nodes"));//所有的市
			for (int i = 0; i < ja.size(); i++) {
				JSONObject val = JSONObject.fromObject(ja.get(i));
				if(val.get("com_name").toString().equals(Com.get("NAME_2").toString())){
					JSONArray jb = JSONArray.fromObject(val.get("nodes"));//所有的县
					for (int j = 0; j < jb.size(); j++) {
						JSONObject val_3 = JSONObject.fromObject(jb.get(j));
						if(val_3.get("com_name").toString().equals(Com.get("NAME_3").toString())){
							JSONArray jc = JSONArray.fromObject(val_3.get("nodes"));//所有的乡
							for (int k = 0; k < jc.size(); k++) {
								JSONObject val_4 = JSONObject.fromObject(jc.get(k));
								if(val_4.get("com_name").toString().equals(Com.get("NAME_4").toString())){
									JSONArray jd = JSONArray.fromObject(val_4.get("nodes"));//所有的乡
									for (int m = 0; m < jd.size(); m++) {
										JSONObject val_5 = JSONObject.fromObject(jd.get(m));
										if(val_5.get("com_name").toString().equals(Com.get("NAME_5").toString())){
											company_tree = val_5;
											break;
										}
									}
									break;
								}
							}
							break;
						}
					}
					break;
				}
			}
			
		}
		JSONArray val = new JSONArray();
		val.add(company_tree);
		obj[1] = company_tree;
		return obj;
	}
	
	//登录验证
	@RequestMapping("loginin.do")
	public void loginin(HttpServletRequest request,HttpServletResponse response) throws Exception{

		String username = request.getParameter("add_account");//获取用户名 
		String password = request.getParameter("add_password");//获取密码
		String people_sql = "select t1.pkid,t1.col_account,t1.col_password,t1.sys_com_code,t1.login_count,t1.LOGIN_TIME,t1.com_vd,t1.com_vs,t3.role_name,t3.pkid as role_id，t4.com_code ,t4.com_level,t4.pkid   from sys_user t1 "
				+ "LEFT JOIN sys_user_role_many t2 on t1.SYS_ROLE_ID=t2.user_id "
				+ "LEFT JOIN sys_role t3 on t2.role_id=t3.pkid "
				+"left JOIN SYS_COMPANY t4 on t1.SYS_COMPANY_ID=t4.pkid "
				+ "WHERE t1.col_account = '"+username+"'";
		List<Map> Login = this.getBySqlMapper.findRecords(people_sql);
		if(Login.size()>0){//查询到用户记录
			//登录时间
			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yy-MM-dd HH:mm:ss ");
			String login_time =  sf.format(date);
			//登录次数+1
			int login_count =1;
			if(Login.get(0).get("LOGIN_COUNT")!=null){
				login_count = Integer.valueOf(Login.get(0).get("LOGIN_COUNT").toString())+1;
			}
			
			Date date2 = new Date();
			String last_time =  sf.format(date2);
	
			//获取最后一次登录时间 上次的登陆时间  就是本次的最后登录时间
			if(Login.get(0).get("LOGIN_TIME")!=null){
				last_time = Login.get(0).get("LOGIN_TIME").toString();
				Date time = sf.parse(last_time);
				last_time = sf.format(time);
			}
/*			String last_time = userInfo.get(0).get("LOGIN_TIME")==null?"":userInfo.get(0).get("LOGIN_TIME").toString();*/
			//获取ip地址
			String ip_Address = Index_Controller.getIpAddress(request);
			
			String update_sql = "update SYS_USER u set u.LAST_TIME =  '"+last_time+"',u.LOGIN_TIME = '"+login_time+"',LOGIN_COUNT = "+login_count+",u.IP_ADDRESS ='"+ip_Address+"' where u.COL_ACCOUNT = '"+username+"'"; 
			this.getBySqlMapper.update(update_sql);
			
			Map Login_map = Login.get(0);
			if(Tool.md5(password).equals(Login_map.get("COL_PASSWORD")  )==true || password.equals(Login_map.get("COL_PASSWORD")) == true){//密码正确
				HttpSession session = request.getSession();
				Login_map.remove("COL_PASSWORD");
				
				String sql_com = "";
				if(Login_map.get("COM_VD").equals("V1")){
					sql_com = "select v1,v2 from SYS_COM where v2='"+Login_map.get("SYS_COM_CODE")+"' GROUP BY v1,v2";
				}else if(Login_map.get("COM_VD").equals("V3")){
					sql_com = "select v1,v2,v3,v4 from SYS_COM where v4='"+Login_map.get("SYS_COM_CODE")+"' GROUP BY v1,v2,v3,v4";
				}else if(Login_map.get("COM_VD").equals("V5")){
					sql_com = "select v1,v2,v3,v4,v5,v6 from SYS_COM where v6='"+Login_map.get("SYS_COM_CODE")+"' GROUP BY v1,v2,v3,v4,v5,v6";
				}else if(Login_map.get("COM_VD").equals("V7")){
					sql_com = "select v1,v2,v3,v4,v5,v6,v7,v8 from SYS_COM where v8='"+Login_map.get("SYS_COM_CODE")+"' GROUP BY v1,v2,v3,v4,v5,v6,v7,v8";
				}else if(Login_map.get("COM_VD").equals("V9")){
					sql_com = "select * from SYS_COM where v10='"+Login_map.get("SYS_COM_CODE")+"'";
				}
//				System.out.println(sql_com);
				if(!sql_com.equals("")){
					List<Map> sql_zong_list = this.getBySqlMapper.findRecords(sql_com);
					Map zpong = sql_zong_list.get(0);
					Login_map.put("COM_NAME", zpong.get(Login_map.get("COM_VD")));//取单位名称
					
					session.setAttribute("Login_map", Login_map);//用户信息，包括角色
					session.setAttribute("company", zpong);//登录用户的单位信息
					
					response.getWriter().print("1");//成功
				}else{
					response.getWriter().print("3");//没有单位
				}
				
//				//判断单位层级，生成单位树结构
//				Object[] obj = this.get_company_tree(zpong.get("COM_LEVEL").toString(), Login_map.get("SYS_COMPANY_ID").toString(), zpong.get("PKID").toString(), zpong.get("COM_NAME").toString());
//				session.setAttribute("company_tree", JSONObject.fromObject(obj[1]));//登录用户的单位信息--树结构
//				session.setAttribute("company_uppe", JSONObject.fromObject(obj[0]));//登录单位的单位上下级关系
			}else{
				response.getWriter().print("0");//密码不正确
			}
		}else{
			response.getWriter().print("2");//没有此用户
		}
	}

	//session获取用户登陆信息
	@RequestMapping("getLogin_massage.do")
	public void getLogin_massage(HttpServletRequest request,HttpServletResponse response) throws Exception{

		HttpSession session = request.getSession();
		JSONObject json = new JSONObject();
		if(session.getAttribute("Login_map")!=null){//验证session不为空
			
			Map<String,String> Login_map = (Map)session.getAttribute("Login_map");//用户信息，包括角色
			Map<String,String> company = (Map)session.getAttribute("company");//登录用户的单位信息

			JSONObject Login_map_json = new JSONObject();
			for(String key : Login_map.keySet()){
				Login_map_json.put(key, Login_map.get(key));
			}
			json.put("Login_map", Login_map_json);

			JSONObject company_json = new JSONObject();
			for(String key : company.keySet()){
				company_json.put(key, company.get(key));
			}
			json.put("company", company_json);
			response.getWriter().write(json.toString());
		}else{
			response.getWriter().print("weidenglu");
		}
	}

	//销毁session
	@RequestMapping("login_out.do")
	public void login_out(HttpServletRequest request,HttpServletResponse response) throws Exception{
		HttpSession session = request.getSession();
		try{
			session.invalidate();
			response.getWriter().write("1");
		}catch (Exception e){
			response.getWriter().write("0");
		}
	}
	//获取ip
	  public static String getIpAddress(HttpServletRequest request) { 
		    String ip = request.getHeader("x-forwarded-for"); 
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		      ip = request.getHeader("Proxy-Client-IP"); 
		    } 
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		      ip = request.getHeader("WL-Proxy-Client-IP"); 
		    } 
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		      ip = request.getHeader("HTTP_CLIENT_IP"); 
		    } 
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		      ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
		    } 
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		      ip = request.getRemoteAddr(); 
		    } 
		    return ip; 
		  } 
		  
}


