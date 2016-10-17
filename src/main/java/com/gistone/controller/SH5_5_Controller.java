package com.gistone.controller;

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
 * 系统管理-系统查看
 * @author chendong
 *
 */
@RestController
@RequestMapping
public class SH5_5_Controller{
//	
//	@Autowired
//	private GetBySqlMapper getBySqlMapper;
//	
//	/**
//	 * 查看日志首页
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("getXtck_RecordController.do")
//	public void getXtck_RecordController(HttpServletRequest request,HttpServletResponse response) throws Exception{
//		
//		String pageSize = request.getParameter("pageSize"); 
//		String pageNumber = request.getParameter("pageNumber");
//		String search = "";
//		if(request.getParameter("search")!=null&&!request.getParameter("search").equals("")){ //判断搜索框中是否有值
//			search = request.getParameter("search").trim();
//		}
//		
//		String name_sql = "select pkid from da_household WHERE v6 ='"+search+"'"; //获取用户ID
//		List<Map> name_list = this.getBySqlMapper.findRecords(name_sql);
//		String dahoseld = "";
//		if(name_list.size()>0){
//			dahoseld=name_list.get(0).get("pkid").toString();
//		}
//		
//		int size = Integer.parseInt(pageSize);
//		int number = Integer.parseInt(pageNumber);
//		int page = number == 0 ? 1 : (number/size)+1;
//		
//		String people_sql = "select * from da_record  WHERE ("+
//							"record_type LIKE '%"+search+"%' or record_p_t like '%"+search+"%' or record_phone like '%"+search+"%' or record_name like "+
//							"'%"+search+"%' or record_time like '%"+search+"%'  or record_mou_1 like '%"+search+"%'  or record_mou_2 like '%"+search+"%' or record_pkid like'%"+dahoseld+"%' ) ORDER BY record_time DESC,pkid desc limit "+number+","+size;
//		
//		String count_sql = "select count(*) from da_record  WHERE ("+
//				"record_type LIKE '%"+search+"%' or record_p_t like '%"+search+"%' or record_phone like '%"+search+"%' or record_name like "+
//				"'%"+search+"%' or record_time like '%"+search+"%'  or record_mou_1 like '%"+search+"%'  or record_mou_2 like '%"+search+"%' or record_pkid like'%"+dahoseld+"%')";
//
//		int total = this.getBySqlMapper.findrows(count_sql);
//		List<Map> Patient_st_List = this.getBySqlMapper.findRecords(people_sql);
//		if(Patient_st_List.size()>0){
//			JSONArray jsa=new JSONArray();
//			for(int i = 0;i<Patient_st_List.size();i++){
//				Map Patient_st_map = Patient_st_List.get(i);
//				JSONObject val = new JSONObject();
//				
//				String cha_sql = "";
//				String name = "";
//				if(Patient_st_map.get("record_table").toString().equals("da_household")){
//					
//					cha_sql="select v6 from da_household where pkid="+Patient_st_map.get("record_pkid").toString()+" union all "
//							+ "select v6 from da_household_del where pkid="+Patient_st_map.get("record_pkid").toString();
//					name = "户主";
//					
//				}else if(Patient_st_map.get("record_table").toString().equals("da_member")){
//					
//					cha_sql="select v6 from da_member where pkid="+Patient_st_map.get("record_pkid").toString()+" union all "
//							+ "select v6 from da_member_del where pkid="+Patient_st_map.get("record_pkid").toString();
//					name = "家庭成员";
//					
//				}else if(Patient_st_map.get("record_table").toString().equals("sys_personal")){
//					
//					cha_sql="select col_name as v6 from sys_personal where pkid="+Patient_st_map.get("record_pkid").toString();
//					name = "帮扶责任人";
//					
//				}else if(Patient_st_map.get("record_table").toString().equals("da_company")){
//					
//					cha_sql="select com_name as v6 from sys_company where pkid="+Patient_st_map.get("record_pkid").toString();
//					name = "帮扶责单位";
//					
//				}else if(Patient_st_map.get("record_table").toString().equals("da_household_del")){
//					
//					cha_sql="select v6 from da_household_del where pkid="+Patient_st_map.get("record_pkid").toString();
//					name = "户主";
//					
//				}else if(Patient_st_map.get("record_table").toString().equals("da_member_del")){
//				
//					cha_sql="select v6 from da_member_del where pkid="+Patient_st_map.get("record_pkid").toString();
//					name = "家庭成员";
//					
//				}
//				List<Map> list = this.getBySqlMapper.findRecords(cha_sql);
//				if(list.size()>0){
//					val.put("pkid",Patient_st_map.get("pkid")==null?"":Patient_st_map.get("pkid"));
//					val.put("dx", name);
//					val.put("dxname",list.get(0).get("v6"));
//					if(Patient_st_map.get("record_p_t").toString().equals("1")){
//						val.put("py", "手机操作");
//						val.put("nOp", Patient_st_map.get("record_phone")==null?"":Patient_st_map.get("record_phone"));
//					}else if(Patient_st_map.get("record_p_t").toString().equals("2")){
//						val.put("py", "电脑操作");
//						val.put("nOp", Patient_st_map.get("record_name")==null?"":Patient_st_map.get("record_name"));
//					}
//					val.put("record_type",Patient_st_map.get("record_type")==null?"":Patient_st_map.get("record_type"));
//					val.put("record_time",Patient_st_map.get("record_time")==null?"":Patient_st_map.get("record_time"));
//					val.put("record_mou_1",Patient_st_map.get("record_mou_1")==null?"":Patient_st_map.get("record_mou_1"));
//					val.put("record_mou_2",Patient_st_map.get("record_mou_2")==null?"":Patient_st_map.get("record_mou_2"));
//					jsa.add(val);
//				}else{
//					total--;
//				}
//			}
//			JSONObject json = new JSONObject();
//			json.put("page", page);
//			json.put("total", total);
//			json.put("rows", jsa); //这里的 rows 和total 的key 是固定的 
//			response.getWriter().write(json.toString());
//		}
//	}
}
