package com.gistone.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gistone.MyBatis.config.GetBySqlMapper;
/**
 * 识别与退出
 * @author chendong
 *
 */
@RestController
@RequestMapping
public class SH4_Controller{
	
//	@Autowired
//	private GetBySqlMapper getBySqlMapper;
//	
//	/**
//	 * 国家标准退出名单初始化
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("getSbtc_Country_init.do")
//	public void getSbtc_Country_init(HttpServletRequest request,HttpServletResponse response) throws Exception{
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		String pageSize = request.getParameter("pageSize");
//		String pageNumber = request.getParameter("pageNumber");
//		String search = "";
//		
//		if(request.getParameter("search")!=null&&!request.getParameter("search").equals("")){
//			search = request.getParameter("search").trim();
//		}
//		int size = Integer.parseInt(pageSize);
//		int number = Integer.parseInt(pageNumber);
//		
//		int page = number == 0 ? 1 : (number/size)+1;
//		
//		HttpSession session = request.getSession();
//		if(session.getAttribute("Login_map")!=null){//验证session不为空
//			Map<String,String> company = (Map)session.getAttribute("company");//用户所属单位表，加前台显示时用的上下级关联名称
//			
//			JSONObject company_json = new JSONObject();
//			for(String key : company.keySet()){
//				company_json.put(key, company.get(key));
//			}
//			
//			String str = "";
//			if(company_json.get("com_level").toString().equals("1")){
//				
//			}else if(company_json.get("com_level").toString().equals("2")){
//				str = " and v3='"+company_json.get("xian")+"'";
//			}else if(company_json.get("com_level").toString().equals("3")){
//				str = " and v3='"+company_json.get("xian")+"' and v4="+company_json.get("xiang")+"'";
//			}else if(company_json.get("com_level").toString().equals("4")){
//				str = " and v3='"+company_json.get("xian")+"' and v4="+company_json.get("xiang")+"' and v5="+company_json.get("cun")+"'";
//			}
//			
//			String count_g_sql = "select count(*) from da_household where sys_standard='国家级贫困人口' and (v3 like '%"+search+"%' or v4 like '%"+search+"%' or v5 like '%"+search+"%' or v6 like '%"+search+"%' or v9 like '%"+search+"%') "+str;
//			int total = this.getBySqlMapper.findrows(count_g_sql);
//			
//			
//			String Metadata_g_sql = "select pkid,v3,v4,v5,v6,v9 from da_household where sys_standard='国家级贫困人口' and (v3 like '%"+search+"%' or v4 like '%"+search+"%' or v5 like '%"+search+"%' or v6 like '%"+search+"%' or v9 like '%"+search+"%') "+str+" limit "+number+","+size;
//			List<Map> Metadata_g_List = this.getBySqlMapper.findRecords(Metadata_g_sql);
//			
//			if(Metadata_g_List.size()>0){
//				JSONArray jsa=new JSONArray();
//				for(int i = 0;i<Metadata_g_List.size();i++){
//					Map Metadata_g_map = Metadata_g_List.get(i);
//					JSONObject val = new JSONObject();
//					for (Object key : Metadata_g_map.keySet()) {
//						val.put(key, Metadata_g_map.get(key));
//					}
//					jsa.add(val);
//				}
//				
//				JSONObject json = new JSONObject();
//				json.put("page", page);
//				json.put("total", total);
//				json.put("rows", jsa); //这里的 rows 和total 的key 是固定的 
//				response.getWriter().write(json.toString());
//			}
//			
//		}
//	}
//	
//
//	/**
//	 * 市级标准退出名单初始化
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("getSbtc_City_init.do")
//	public void getSbtc_City_init(HttpServletRequest request,HttpServletResponse response) throws Exception{
//		
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		String pageSize = request.getParameter("pageSize");
//		String pageNumber = request.getParameter("pageNumber");
//		String search = "";
//		
//		if(request.getParameter("search")!=null&&!request.getParameter("search").equals("")){
//			search = request.getParameter("search").trim();
//		}
//		int size = Integer.parseInt(pageSize);
//		int number = Integer.parseInt(pageNumber);
//		
//		int page = number == 0 ? 1 : (number/size)+1;
//		
//		HttpSession session = request.getSession();
//		if(session.getAttribute("Login_map")!=null){//验证session不为空
//			Map<String,String> company = (Map)session.getAttribute("company");//用户所属单位表，加前台显示时用的上下级关联名称
//			
//			JSONObject company_json = new JSONObject();
//			for(String key : company.keySet()){
//				company_json.put(key, company.get(key));
//			}
//			
//			String str = "";
//			if(company_json.get("com_level").toString().equals("1")){
//				
//			}else if(company_json.get("com_level").toString().equals("2")){
//				str = " and v3='"+company_json.get("xian")+"'";
//			}else if(company_json.get("com_level").toString().equals("3")){
//				str = " and v3='"+company_json.get("xian")+"' and v4="+company_json.get("xiang")+"'";
//			}else if(company_json.get("com_level").toString().equals("4")){
//				str = " and v3='"+company_json.get("xian")+"' and v4="+company_json.get("xiang")+"' and v5="+company_json.get("cun")+"'";
//			}
//			
//			String count_s_sql = "select count(*) from da_household where sys_standard='市级低收入人口' and (v3 like '%"+search+"%' or v4 like '%"+search+"%' or v5 like '%"+search+"%' or v6 like '%"+search+"%' or v9 like '%"+search+"%') "+str;
//			int total = this.getBySqlMapper.findrows(count_s_sql);
//			
//			
//			String Metadata_s_sql = "select pkid,v3,v4,v5,v6,v9 from da_household where sys_standard='市级低收入人口' and (v3 like '%"+search+"%' or v4 like '%"+search+"%' or v5 like '%"+search+"%' or v6 like '%"+search+"%' or v9 like '%"+search+"%') "+str+" limit "+number+","+size;
//			List<Map> Metadata_s_List = this.getBySqlMapper.findRecords(Metadata_s_sql);
//			
//			if(Metadata_s_List.size()>0){
//				JSONArray jsa=new JSONArray();
//				for(int i = 0;i<Metadata_s_List.size();i++){
//					Map Metadata_s_map = Metadata_s_List.get(i);
//					JSONObject val = new JSONObject();
//					for (Object key : Metadata_s_map.keySet()) {
//						val.put(key, Metadata_s_map.get(key));
//					}
//					jsa.add(val);
//				}
//				
//				JSONObject json = new JSONObject();
//				json.put("page", page);
//				json.put("total", total);
//				json.put("rows", jsa); //这里的 rows 和total 的key 是固定的 
//				response.getWriter().write(json.toString());
//			}
//			
//		}
//	}
}
