package com.gistone.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gistone.MyBatis.config.GetBySqlMapper;
import com.gistone.util.OverallSituation;
/**
 * 缓存的存入
 * @author chendong
 * @date 2016年8月3日
 */
@RestController
@RequestMapping
public class Cache_Controller{
	
	@Autowired
	private GetBySqlMapper getBySqlMapper;
	
	/**
	 * 传参-行政区划  所有的行政区划的值下拉框的值
	 * @author chendong
	 * @date 2016年8月4日
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("cache_1.do")
	public void cache_1(HttpServletRequest request,HttpServletResponse response) throws IOException{
		if(OverallSituation.compartment == null){
			OverallSituation.compartment = this.getCompartment();
		}
//		System.out.println(OverallSituation.compartment.toString());
		response.getWriter().write(OverallSituation.compartment.toString());
		response.getWriter().close();
	}
	
	/**
	 * 行政区划的缓存
	 * @author chendong
	 * @date 2016年8月3日
	 * @return
	 */
	@Bean
	public JSONArray getCompartment(){
		String sql="select * from SYS_COM";
		List<Map> list=this.getBySqlMapper.findRecords(sql);
		JSONArray jsonArray = new JSONArray();
		JSONObject object = new JSONObject();
		
		for(int i = 0;i<list.size();i++){
			Map Patient_st_map = list.get(i);
			for (Object key : Patient_st_map.keySet()) {
				object.put(key, Patient_st_map.get(key).toString());
			}
			jsonArray.add(object);
		}
		return jsonArray;
	}
	
	@RequestMapping("getSYS_COM_V5.do")
	public void getSYS_COM_V5(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String code = request.getParameter("code");//获取选择的市级行政区划编码
		String sql="select v5,v6 from SYS_COM where V4='"+code+"' group by v5,v6 order by v6";
		List<Map> list=this.getBySqlMapper.findRecords(sql);
		JSONArray jsonArray = new JSONArray();
		JSONObject object = new JSONObject();
		
		for(int i = 0;i<list.size();i++){
			Map Patient_st_map = list.get(i);
			for (Object key : Patient_st_map.keySet()) {
				object.put(key, Patient_st_map.get(key).toString());
			}
			jsonArray.add(object);
		}
		response.getWriter().write(jsonArray.toString());
		response.getWriter().close();
	}
	
	@RequestMapping("getSYS_COM_V7.do")
	public void getSYS_COM_V7(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String code = request.getParameter("code");//获取选择的市级行政区划编码
		String sql="select v7,v8 from SYS_COM where V6='"+code+"' group by v7,v8 order by v8";
		List<Map> list=this.getBySqlMapper.findRecords(sql);
		JSONArray jsonArray = new JSONArray();
		JSONObject object = new JSONObject();
		
		for(int i = 0;i<list.size();i++){
			Map Patient_st_map = list.get(i);
			for (Object key : Patient_st_map.keySet()) {
				object.put(key, Patient_st_map.get(key).toString());
			}
			jsonArray.add(object);
		}
		response.getWriter().write(jsonArray.toString());
		response.getWriter().close();
	}
	
	@RequestMapping("getSYS_COM_V9.do")
	public void getSYS_COM_V9(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String code = request.getParameter("code");//获取选择的市级行政区划编码
		String sql="select v9,v10,lng,lat,STATUS from SYS_COM where V8='"+code+"' group by v9,v10,lng,lat,STATUS order by v10";
		System.out.println(sql);
		List<Map> list=this.getBySqlMapper.findRecords(sql);
		JSONArray jsonArray = new JSONArray();
		JSONObject object = new JSONObject();
		
		for(int i = 0;i<list.size();i++){
			Map st_map = list.get(i);
			
			object.put("V9", "".equals(st_map.get("V9")) || st_map.get("V9") == null ? "" : st_map.get("V9").toString());
			object.put("V10", "".equals(st_map.get("V10")) || st_map.get("V10") == null ? "" : st_map.get("V10").toString());
			object.put("LNG", "".equals(st_map.get("LNG")) || st_map.get("LNG") == null ? "" : st_map.get("LNG").toString());
			object.put("LAT", "".equals(st_map.get("LAT")) || st_map.get("LAT") == null ? "" : st_map.get("LAT").toString());
			object.put("STATUS", "".equals(st_map.get("STATUS")) || st_map.get("STATUS") == null ? "" : st_map.get("STATUS").toString());
			
//			for (Object key : Patient_st_map.keySet()) {
//				object.put(key, Patient_st_map.get(key).toString());
//			}
			jsonArray.add(object);
		}
		response.getWriter().write(jsonArray.toString());
		response.getWriter().close();
	}
	
	
	/**
	 * 行政区划的缓存--上下级关系
	 * @author 李永亮
	 * @date 2016年9月8日
	 * @return
	 */
//	@Bean
//	public JSONObject getComp(){
//		JSONArray val = new JSONArray();
//		JSONObject object = new JSONObject();
//		
//		List<Map> list = new ArrayList();
//		List<Map> list_2 = new ArrayList();
//		List<Map> list_3 = new ArrayList();
//		List<Map> list_4 = new ArrayList();
//		List<Map> list_5 = new ArrayList();
//		String sql="select pkid,COM_NAME,com_code,com_level,com_f_pkid,lat,lon,status from sys_company";
//		List<Map> sql_list = this.getBySqlMapper.findRecords(sql);
//		for(Map sql_map:sql_list){
//			if(sql_map.get("COM_LEVEL").toString().equals("1")){
//				list.add(sql_map);
//			}else if(sql_map.get("COM_LEVEL").toString().equals("2")){
//				list_2.add(sql_map);
//			}else if(sql_map.get("COM_LEVEL").toString().equals("3")){
//				list_3.add(sql_map);
//			}else if(sql_map.get("COM_LEVEL").toString().equals("4")){
//				list_4.add(sql_map);
//			}else if(sql_map.get("COM_LEVEL").toString().equals("5")){
//				list_5.add(sql_map);
//			}
//		}
//		
//		Map Patient_st_map = list.get(0);//直接取第一条记录，就是内蒙古自治区
//		object.put("text", Patient_st_map.get("COM_NAME").toString());
//		object.put("href", Patient_st_map.get("PKID").toString());
//		object.put("com_level", Patient_st_map.get("COM_LEVEL").toString());
//		object.put("com_f_pkid", Patient_st_map.get("COM_F_PKID").toString());
//		object.put("xzqh", Patient_st_map.get("COM_NAME").toString());
//		object.put("lat", Patient_st_map.get("LAT"));
//		object.put("lon", Patient_st_map.get("LON"));
//		object.put("status", Patient_st_map.get("STATUS"));
//		
//		JSONArray ji = new JSONArray();//保存二级数据的数组
//		
//		for(int i = 0;i<list_2.size();i++){//二级循环
//			Map st_j_map = list_2.get(i);
//			JSONObject oj = new JSONObject();
//			oj.put("text", st_j_map.get("COM_NAME").toString());
//			oj.put("href", st_j_map.get("PKID").toString());
//			oj.put("com_level", st_j_map.get("COM_LEVEL").toString());
//			oj.put("com_f_pkid", st_j_map.get("COM_F_PKID").toString());
//			oj.put("xzqh", Patient_st_map.get("COM_NAME").toString()+"-"+st_j_map.get("COM_NAME").toString());
//			oj.put("lat", st_j_map.get("LAT"));
//			oj.put("lon", st_j_map.get("LON"));
//			oj.put("status", st_j_map.get("STATUS"));
//			JSONArray jj = new JSONArray();//三级数据
//			
//			for(int k = 0;k<list_3.size();k++){
//				Map st_k_map = list_3.get(k);
//				if(st_k_map.get("COM_F_PKID").toString().equals(st_j_map.get("PKID").toString())){//三级父ID等于上级ID
//					JSONObject ok = new JSONObject();
//					ok.put("text", st_k_map.get("COM_NAME").toString());
//					ok.put("href", st_k_map.get("PKID").toString());
//					ok.put("com_level", st_k_map.get("COM_LEVEL").toString());
//					ok.put("com_f_pkid", st_k_map.get("COM_F_PKID").toString());
//					ok.put("xzqh", Patient_st_map.get("COM_NAME").toString()+"-"+st_j_map.get("COM_NAME").toString()+"-"+st_k_map.get("COM_NAME").toString());
//					ok.put("lat", st_k_map.get("LAT"));
//					ok.put("lon", st_k_map.get("LON"));
//					ok.put("status", st_k_map.get("STATUS"));
//					JSONArray jk = new JSONArray();
//					
//					for(int m = 0;m<list_4.size();m++){
//						Map st_m_map = list_4.get(m);
//						if(st_m_map.get("COM_F_PKID").toString().equals(st_k_map.get("PKID").toString())){
//							JSONObject om = new JSONObject();
//							om.put("text", st_m_map.get("COM_NAME").toString());
//							om.put("href", st_m_map.get("PKID").toString());
//							om.put("com_level", st_m_map.get("COM_LEVEL").toString());
//							om.put("com_f_pkid", st_m_map.get("COM_F_PKID").toString());
//							om.put("xzqh", Patient_st_map.get("COM_NAME").toString()+"-"+st_j_map.get("COM_NAME").toString()+"-"+st_k_map.get("COM_NAME").toString()+"-"+st_m_map.get("COM_NAME").toString());
//							om.put("lat", st_m_map.get("LAT"));
//							om.put("lon", st_m_map.get("LON"));
//							om.put("status", st_m_map.get("STATUS"));
//							JSONArray jm = new JSONArray();
//							
//							for(int n = 0;n<list_5.size();n++){
//								Map st_n_map = list_5.get(n);
//								if(st_n_map.get("COM_F_PKID").toString().equals(st_m_map.get("PKID").toString())){
//									JSONObject on = new JSONObject();
//									on.put("text", st_n_map.get("COM_NAME").toString());
//									on.put("href", st_n_map.get("PKID").toString());
//									on.put("com_level", st_n_map.get("COM_LEVEL").toString());
//									on.put("com_f_pkid", st_n_map.get("COM_F_PKID").toString());
//									on.put("xzqh", Patient_st_map.get("COM_NAME").toString()+"-"+st_j_map.get("COM_NAME").toString()+"-"+st_k_map.get("COM_NAME").toString()+"-"+st_m_map.get("COM_NAME").toString()+"-"+st_n_map.get("COM_NAME").toString());
//									on.put("lat", st_n_map.get("LAT"));
//									on.put("lon", st_n_map.get("LON"));
//									on.put("status", st_n_map.get("STATUS"));
//									jm.add(on);
//								}
//							}
//							
//							om.put("nodes", jm);
//							jk.add(om);
//						}
//					}
//					
//					
//					ok.put("nodes", jk);
//					jj.add(ok);
//				}
//			}
//			
//			
//			oj.put("nodes", jj);//保存三级数据到二级
//			ji.add(oj);
//		}
//		
//		
//		object.put("nodes", ji);//将二级添加到一级
//		val.add(object);
//		
//		return object;
//	}
}
