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
 * 系统管理-查询录入情况
 * @author chendong
 *
 */
@RestController
@RequestMapping
public class SH5_7_Controller{
//	
//	@Autowired
//	private GetBySqlMapper getBySqlMapper;
//	/**
//	 * 查询未完整和完整的贫困户
//	 * @author 呼树明
//	 * @date 2016年8月5日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException 
//	 */
//	@RequestMapping("getCxlrqk_1.do")
//	public void getCxlrqk_1(HttpServletRequest request,HttpServletResponse response) throws IOException {
//		String form_name = request.getParameter("form_name");//获取复选框中的名称
//		String form_val = request.getParameter("form_val");//获取复选框中的值
//		String danxuan_val = request.getParameter("danxuan_val");//获取单选框中的值
//		String json_level = request.getParameter("jsonlevel");//获取树形旗区的层级
//		String pageSize = request.getParameter("pageSize");
//		String pageNumber = request.getParameter("pageNumber");
//		String level = request.getParameter("level");//获取树形旗区的层级
//		String jsonname = request.getParameter("jsonname");//获取区域名称
//		JSONObject form_json = JSONObject.fromObject(form_val);//表单数据
//		JSONObject danxuan_json = JSONObject.fromObject(danxuan_val);//表单数据
//		int size = Integer.parseInt(pageSize);
//		int number = Integer.parseInt(pageNumber);
//		int page = number == 0 ? 1 : (number/size)+1;
//		int shilevel;
//		String shi_name = "";
//		String term = "";
//		
//		String left_sql = "";
//		String shilevel_sql = "";
//		if (json_level.equals("undefined")) {
//			String shi_sql ="select * from sys_company c where c.com_level='"+ level +"'";
//			List<Map> shi_listmap = this.getBySqlMapper.findRecords(shi_sql);
//			shilevel = (Integer) shi_listmap.get(0).get("com_level");
//			shi_name = jsonname;
//		}else{
//			String shi_sql ="select * from sys_company c where c.pkid='"+ json_level +"'";
//			List<Map> shi_listmap = this.getBySqlMapper.findRecords(shi_sql);
//			shilevel = (Integer) shi_listmap.get(0).get("com_level");
//			shi_name = (String) shi_listmap.get(0).get("com_name");
//		}
//		
//		if (shilevel==1) {										//判断区级
//			shilevel_sql+=" a.v2 like '%"+ shi_name +"%' ";
//		}else if (shilevel==2) {
//			shilevel_sql+=" a.v3 like '%"+ shi_name +"%' ";
//		}else if (shilevel==3) {
//			shilevel_sql+=" a.v4 like '%"+ shi_name +"%' ";
//		}else if (shilevel==4) {
//			shilevel_sql+=" a.v5 like '%"+ shi_name +"%' ";
//		}
//		
//		if (form_name.equals("jinben_form")) {
//			if (danxuan_json.get("a").equals("0")) {  //基本情况未完成
//				if (form_json.get("v6")!=null&&!form_json.get("v6").equals("")) {
//					term+="a.v6 is NULL  or a.v6 = ''  or";
//				}
//				if (form_json.get("v7")!=null&&!form_json.get("v7").equals("")) {
//					term+=" a.v7 is NULL  or a.v7 = ''  or";
//				}
//				if (form_json.get("v11")!=null&&!form_json.get("v11").equals("")) {
//					term+=" a.v11 is NULL  or a.v11 = ''  or";
//				}
//				if (form_json.get("v25")!=null&&!form_json.get("v25").equals("")) {
//					term+=" a.v25 is NULL  or a.v25 = ''  or";
//				}
//				if (form_json.get("v8")!=null&&!form_json.get("v8").equals("")) {
//					term+=" a.v8 is NULL  or a.v8 = ''  or";
//				}
//				if (form_json.get("v28")!=null&&!form_json.get("v28").equals("")) {
//					term+=" a.v28 is NULL  or a.v28 = ''  or";
//				} 
//				if (form_json.get("v26")!=null&&!form_json.get("v26").equals("")) {
//					term+=" a.v26 is NULL  or a.v26 = ''  or";
//				}
//				if (form_json.get("v27")!=null&&!form_json.get("v27").equals("")) {
//					term+=" a.v27 is NULL  or a.v27 = ''  or";
//				}
//				if (form_json.get("hz_jtzz")!=null&&!form_json.get("hz_jtzz").equals("")) {
//					left_sql=" join da_household_basic r on a.pkid = r.da_household_id";
//					term+=" r.basic_address is NULL  or r.basic_address = ''  or";
//				}
//				if (form_json.get("sys_standard")!=null&&!form_json.get("sys_standard").equals("")) {
//					term+=" a.sys_standard is NULL  or a.sys_standard = ''  or";
//				}
//				if (form_json.get("v22")!=null&&!form_json.get("v22").equals("")) {
//					term+=" a.v22 is NULL  or a.v22 = ''  or";
//				}
//				if (form_json.get("v12")!=null&&!form_json.get("v12").equals("")) {
//					term+=" a.v12 is NULL  or a.v12 = ''  or";
//				}
//				if (form_json.get("v13")!=null&&!form_json.get("v13").equals("")) {
//					term+=" a.v13 is NULL  or a.v13 = ''  or";
//				}
//				if (form_json.get("v14")!=null&&!form_json.get("v14").equals("")) {
//					term+=" a.v14 is NULL  or a.v14 = ''  or";
//				}
//				if (form_json.get("v15")!=null&&!form_json.get("v15").equals("")) {
//					term+=" a.v15 is NULL  or a.v15 = ''  or";
//				} 
//				if (form_json.get("v16")!=null&&!form_json.get("v16").equals("")) {
//					term+=" a.v16 is NULL  or a.v16 = ''  or";
//				}
//				if (form_json.get("v17")!=null&&!form_json.get("v17").equals("")) {
//					term+=" a.v17 is NULL  or a.v17 = ''  or";
//				}
//				if (form_json.get("v18")!=null&&!form_json.get("v18").equals("")) {
//					term+=" a.v18 is NULL  or a.v18 = ''  or";
//				}
//				if (form_json.get("v19")!=null&&!form_json.get("v19").equals("")) {
//					term+=" a.v19 is NULL  or a.v19 = ''  or";
//				}
//				if (form_json.get("v29_div")!=null&&!form_json.get("v29_div").equals("")) {
//					term+=" a.v29 is NULL  or a.v29 = ''  or";
//				}
//				if (form_json.get("v32")!=null&&!form_json.get("v32").equals("")) {
//					term+=" a.v32 is NULL  or a.v32 = ''  or";
//				}
//				if (form_json.get("v30")!=null&&!form_json.get("v30").equals("")) {
//					term+=" a.v30 is NULL  or a.v30 = ''  or";
//				}
//				if (form_json.get("hz_zpyy")!=null&&!form_json.get("hz_zpyy").equals("")) {
//					left_sql=" join da_household_basic r on a.pkid = r.da_household_id";
//					term+=" r.basic_explain is NULL  or r.basic_explain = ''  or";
//				}
//				
//			}else {									  //基本情况已完成	
//				if (form_json.get("v6")!=null&&!form_json.get("v6").equals("")) {
//					term+=" a.v6 != '' and";
//				}
//				if (form_json.get("v7")!=null&&!form_json.get("v7").equals("")) {
//					term+=" a.v7 != '' and";
//				}
//				if (form_json.get("v11")!=null&&!form_json.get("v11").equals("")) {
//					term+=" a.v11 != '' and";
//				}
//				if (form_json.get("v25")!=null&&!form_json.get("v25").equals("")) {
//					term+=" a.v25 != '' and";
//				}
//				if (form_json.get("v8")!=null&&!form_json.get("v8").equals("")) {
//					term+=" a.v8 != '' and";
//				}
//				if (form_json.get("v28")!=null&&!form_json.get("v28").equals("")) {
//					term+=" a.v28 != '' and";
//				}
//				if (form_json.get("v26")!=null&&!form_json.get("v26").equals("")) {
//					term+=" a.v26 != '' and";
//				}
//				if (form_json.get("v27")!=null&&!form_json.get("v27").equals("")) {
//					term+=" a.v27 != '' and";
//				}
//				if (form_json.get("hz_jtzz")!=null&&!form_json.get("hz_jtzz").equals("")) {
//					left_sql=" join da_household_basic r on a.pkid = r.da_household_id";
//					term+=" r.basic_address != '' and";
//				}
//				if (form_json.get("sys_standard")!=null&&!form_json.get("sys_standard").equals("")) {
//					term+=" a.sys_standard != '' and";
//				}
//				if (form_json.get("v22")!=null&&!form_json.get("v22").equals("")) {
//					term+=" a.v22 != '' and";
//				}
//				if (form_json.get("v12")!=null&&!form_json.get("v12").equals("")) {
//					term+=" a.v12 != '' and";
//				}
//				if (form_json.get("v13")!=null&&!form_json.get("v13").equals("")) {
//					term+=" a.v13 != '' and";
//				}
//				if (form_json.get("v14")!=null&&!form_json.get("v14").equals("")) {
//					term+=" a.v14 != '' and";
//				}
//				if (form_json.get("v15")!=null&&!form_json.get("v15").equals("")) {
//					term+=" a.v15 != '' and";
//				}
//				if (form_json.get("v16")!=null&&!form_json.get("v16").equals("")) {
//					term+=" a.v16 != '' and";
//				}
//				if (form_json.get("v17")!=null&&!form_json.get("v17").equals("")) {
//					term+=" a.v17 != '' and";
//				}
//				if (form_json.get("v18")!=null&&!form_json.get("v18").equals("")) {
//					term+=" a.v18 != '' and";
//				}
//				if (form_json.get("v19")!=null&&!form_json.get("v19").equals("")) {
//					term+=" a.v19 != '' and";
//				}
//				if (form_json.get("v29_div")!=null&&!form_json.get("v29_div").equals("")) {
//					term+=" a.v29 != '' and";
//				}
//				if (form_json.get("v32")!=null&&!form_json.get("v32").equals("")) {
//					term+=" a.v32 != '' and";
//				}
//				if (form_json.get("v30")!=null&&!form_json.get("v30").equals("")) {
//					term+=" a.v30 != '' and";
//				}
//				if (form_json.get("v23_div")!=null&&!form_json.get("v23_div").equals("")) {
//					term+=" a.v23 != '' and";
//				}
//				if (form_json.get("v33_div")!=null&&!form_json.get("v33_div").equals("")) {
//					term+=" a.v33 != '' and";
//				}
//				if (form_json.get("hz_zpyy")!=null&&!form_json.get("hz_zpyy").equals("")) {
//					left_sql=" join da_household_basic r on a.pkid = r.da_household_id";
//					term+=" r.basic_explain != '' and";
//				}
//			}
//		}else if (form_name.equals("shengchan_form")) {
//			if (danxuan_json.get("a").equals("0")) {  //生产条件未完成
//				left_sql=" join da_production c on a.pkid = c.da_household_id ";
//				if (form_json.get("v1-1")!=null&&!form_json.get("v1-1").equals("")) {
//					term+=" c.v1 is NULL  or ";
//				}
//				if (form_json.get("v2-1")!=null&&!form_json.get("v2-1").equals("")) {
//					term+=" c.v2 is NULL  or ";
//				}
//				if (form_json.get("v3-1")!=null&&!form_json.get("v3-1").equals("")) {
//					term+=" c.v3 is NULL  or ";
//				}
//				if (form_json.get("v5-1")!=null&&!form_json.get("v5-1").equals("")) {
//					term+=" c.v5 is NULL  or ";
//				}
//				if (form_json.get("v6-1")!=null&&!form_json.get("v6-1").equals("")) {
//					term+=" c.v6 is NULL  or ";
//				}
//				if (form_json.get("v13-1")!=null&&!form_json.get("v13-1").equals("")) {
//					term+=" c.v13 is NULL  or ";
//				}
//				if (form_json.get("v14-1")!=null&&!form_json.get("v14-1").equals("")) {
//					term+=" c.v14 is NULL  or ";
//				}
//				if (form_json.get("v7-1")!=null&&!form_json.get("v7-1").equals("")) {
//					term+=" c.v7 is NULL  or c.v7 = ''  or";
//				}
//				if (form_json.get("v8-1")!=null&&!form_json.get("v8-1").equals("")) {
//					term+=" c.v8 is NULL  or c.v8 = ''  or";
//				}
//			}else {									  //生产条件已完成	
//				left_sql=" join da_production c on a.pkid = c.da_household_id ";
//				if (form_json.get("v1-1")!=null&&!form_json.get("v1-1").equals("")) {
//					term+=" c.v1 is not NULL and";
//				}
//				if (form_json.get("v2-1")!=null&&!form_json.get("v2-1").equals("")) {
//					term+=" c.v2 is not NULL and";
//				}
//				if (form_json.get("v3-1")!=null&&!form_json.get("v3-1").equals("")) {
//					term+=" c.v3 is not NULL and";
//				}
//				if (form_json.get("v5-1")!=null&&!form_json.get("v5-1").equals("")) {
//					term+=" c.v5 is not NULL and";
//				}
//				if (form_json.get("v6-1")!=null&&!form_json.get("v6-1").equals("")) {
//					term+=" c.v6 is not NULL and";
//				}
//				if (form_json.get("v13-1")!=null&&!form_json.get("v13-1").equals("")) {
//					term+=" c.v13 is not NULL and";
//				}
//				if (form_json.get("v14-1")!=null&&!form_json.get("v14-1").equals("")) {
//					term+=" c.v14 is not NULL and";
//				}
//				if (form_json.get("v7-1")!=null&&!form_json.get("v7-1").equals("")) {
//					term+=" c.v7 != '' and";
//				}
//				if (form_json.get("v8-1")!=null&&!form_json.get("v8-1").equals("")) {
//					term+=" c.v8 != '' and";
//				}
//			}
//		}else if (form_name.equals("shenghuo_form")) {
//			if (danxuan_json.get("a").equals("0")) {  //生活条件未完成
//				left_sql=" join da_life b on a.pkid=b.da_household_id";
//				if (form_json.get("v1-2")!=null&&!form_json.get("v1-2").equals("")) {
//					term+=" b.v1 is NULL  or ";
//				}
//				if (form_json.get("v2-2")!=null&&!form_json.get("v2-2").equals("")) {
//					term+=" b.v2 is NULL  or b.v2 = ''  or";
//				}
//				if (form_json.get("v3-2")!=null&&!form_json.get("v3-2").equals("")) {
//					term+=" b.v3 is NULL  or b.v3 = ''  or";
//				}
//				if (form_json.get("v4-2")!=null&&!form_json.get("v4-2").equals("")) {
//					term+=" b.v4 is NULL  or b.v4 = ''  or";
//				}
//				if (form_json.get("v8-2")!=null&&!form_json.get("v8-2").equals("")) {
//					term+=" b.v8 is NULL  or b.v8 = ''  or";
//				}
//				if (form_json.get("v9-2")!=null&&!form_json.get("v9-2").equals("")) {
//					term+=" b.v9 is NULL  or b.v9 = ''  or";
//				}
//				if (form_json.get("v5-2")!=null&&!form_json.get("v5-2").equals("")) {
//					term+=" b.v5 is NULL  or b.v5 = ''  or";
//				}
//				if (form_json.get("v6-2")!=null&&!form_json.get("v6-2").equals("")) {
//					term+=" b.v6 is NULL  or b.v6 = ''  or ";
//				}
//				if (form_json.get("v7-2")!=null&&!form_json.get("v7-2").equals("")) {
//					term+=" b.v7 is NULL  or ";
//				}
//				if (form_json.get("v10-2")!=null&&!form_json.get("v10-2").equals("")) {
//					term+=" b.v10 is NULL  or b.v10 = ''  or";
//				}
//				if (form_json.get("v11-2")!=null&&!form_json.get("v11-2").equals("")) {
//					term+=" b.v11 is NULL  or b.v11 = ''  or";
//				}
//				if (form_json.get("v12-2")!=null&&!form_json.get("v12-2").equals("")) {
//					term+=" b.v12 is NULL  or b.v12 = ''  or";
//				}
//			}else {									  //生活条件已完成	
//				left_sql=" join da_life b on a.pkid=b.da_household_id";
//				if (form_json.get("v1-2")!=null&&!form_json.get("v1-2").equals("")) {
//					term+=" b.v1 is not NULL and";
//				}
//				if (form_json.get("v2-2")!=null&&!form_json.get("v2-2").equals("")) {
//					term+=" b.v2 != '' and";
//				}
//				if (form_json.get("v3-2")!=null&&!form_json.get("v3-2").equals("")) {
//					term+=" b.v3 != '' and";
//				}
//				if (form_json.get("v4-2")!=null&&!form_json.get("v4-2").equals("")) {
//					term+=" b.v4 != '' and";
//				}
//				if (form_json.get("v8-2")!=null&&!form_json.get("v8-2").equals("")) {
//					term+=" b.v8 != '' and";
//				}
//				if (form_json.get("v9-2")!=null&&!form_json.get("v9-2").equals("")) {
//					term+=" b.v9 != '' and";
//				}
//				if (form_json.get("v5-2")!=null&&!form_json.get("v5-2").equals("")) {
//					term+=" b.v5 != '' and";
//				}
//				if (form_json.get("v6-2")!=null&&!form_json.get("v6-2").equals("")) {
//					term+=" b.v6 != '' and";
//				}
//				if (form_json.get("v7-2")!=null&&!form_json.get("v7-2").equals("")) {
//					term+=" b.v7 is not NULL and";
//				}
//				if (form_json.get("v10-2")!=null&&!form_json.get("v10-2").equals("")) {
//					term+=" b.v10 != '' and";
//				}
//				if (form_json.get("v11-2")!=null&&!form_json.get("v11-2").equals("")) {
//					term+=" b.v11 != '' and";
//				}
//				if (form_json.get("v12-2")!=null&&!form_json.get("v12-2").equals("")) {
//					term+=" b.v12 != '' and";
//				}
//			}
//		}else if (form_name.equals("jiating_form")) {
//			if (danxuan_json.get("a").equals("0")) {  //家庭成员未完成
//				left_sql=" join da_member d on a.pkid=d.da_household_id";
//				if (form_json.get("v6-3")!=null&&!form_json.get("v6-3").equals("")) {//
//					term+=" d.v6 is NULL  or d.v6 = ''  or";
//				}
//				if (form_json.get("v7-3")!=null&&!form_json.get("v7-3").equals("")) {
//					term+=" d.v7 is NULL  or d.v7 = ''  or";
//				}
//				if (form_json.get("v8-3")!=null&&!form_json.get("v8-3").equals("")) {
//					term+=" d.v8 is NULL  or d.v8 = ''  or";
//				}
//				if (form_json.get("v10-3")!=null&&!form_json.get("v10-3").equals("")) {
//					term+=" d.v10 is NULL  or d.v10 = ''  or";
//				}
//				if (form_json.get("v11-3")!=null&&!form_json.get("v11-3").equals("")) {
//					term+=" d.v11 is NULL  or d.v11 = ''  or";
//				}
//				if (form_json.get("v28-3")!=null&&!form_json.get("v28-3").equals("")) {
//					term+=" d.v28 is NULL  or d.v28 = ''  or";
//				}
//				if (form_json.get("v32-3")!=null&&!form_json.get("v32-3").equals("")) {
//					term+=" d.v32 is NULL  or d.v32 = ''  or";
//				}
//				if (form_json.get("v12-3")!=null&&!form_json.get("v12-3").equals("")) {
//					term+=" d.v12 is NULL  or d.v12 = ''  or";
//				}
//				if (form_json.get("v13-3")!=null&&!form_json.get("v13-3").equals("")) {
//					term+=" d.v13 is NULL  or d.v13 = ''  or";
//				}
//				if (form_json.get("v14-3")!=null&&!form_json.get("v14-3").equals("")) {
//					term+=" d.v14 is NULL  or d.v14 = ''  or";
//				}
//				if (form_json.get("v15-3")!=null&&!form_json.get("v15-3").equals("")) {
//					term+=" d.v15 is NULL  or d.v15 = ''  or";
//				}
//				if (form_json.get("v16-3")!=null&&!form_json.get("v16-3").equals("")) {
//					term+=" d.v16 is NULL  or d.v16 = ''  or";
//				}
//				if (form_json.get("v17-3")!=null&&!form_json.get("v17-3").equals("")) {
//					term+=" d.v17 is NULL  or d.v17 = ''  or";
//				}
//				if (form_json.get("v18-3")!=null&&!form_json.get("v18-3").equals("")) {
//					term+=" d.v18 is NULL  or d.v18 = ''  or";
//				}
//				if (form_json.get("v19-3")!=null&&!form_json.get("v19-3").equals("")) {
//					term+=" d.v19 is NULL  or d.v19 = ''  or";
//				}
//			}else {									  //家庭成员已完成	
//				left_sql=" join da_member d on a.pkid=d.da_household_id";
//				if (form_json.get("v6-3")!=null&&!form_json.get("v6-3").equals("")) {//
//					term+=" d.v6 != '' and";
//				}
//				if (form_json.get("v7-3")!=null&&!form_json.get("v7-3").equals("")) {
//					term+=" d.v7 != '' and";
//				}
//				if (form_json.get("v11-3")!=null&&!form_json.get("v11-3").equals("")) {
//					term+=" d.v11 != '' and";
//				}
//				if (form_json.get("v8-3")!=null&&!form_json.get("v8-3").equals("")) {
//					term+=" d.v8 != '' and";
//				}
//				if (form_json.get("v28-3")!=null&&!form_json.get("v28-3").equals("")) {
//					term+=" d.v28 != '' and";
//				}
//				if (form_json.get("v32-3")!=null&&!form_json.get("v32-3").equals("")) {
//					term+=" d.v32 != '' and";
//				}
//				if (form_json.get("v10-3")!=null&&!form_json.get("v10-3").equals("")) {
//					term+=" d.v10 != '' and";
//				}
//				if (form_json.get("v12-3")!=null&&!form_json.get("v12-3").equals("")) {
//					term+=" d.v12 != '' and";
//				}
//				if (form_json.get("v13-3")!=null&&!form_json.get("v13-3").equals("")) {
//					term+=" d.v13 != '' and";
//				}
//				if (form_json.get("v14-3")!=null&&!form_json.get("v14-3").equals("")) {
//					term+=" d.v14 != '' and";
//				}
//				if (form_json.get("v15-3")!=null&&!form_json.get("v15-3").equals("")) {
//					term+=" d.v15 != '' and";
//				}
//				if (form_json.get("v16-3")!=null&&!form_json.get("v16-3").equals("")) {
//					term+=" d.v16 != '' and";
//				}
//				if (form_json.get("v17-3")!=null&&!form_json.get("v17-3").equals("")) {
//					term+=" d.v12 != '' and";
//				}
//				if (form_json.get("v18-3")!=null&&!form_json.get("v18-3").equals("")) {
//					term+=" d.v12 != '' and";
//				}
//			}
//		}else if (form_name.equals("shouru_form")) {
//			if (danxuan_json.get("a").equals("0")) {  //当前收入未完成
//				left_sql=" join da_current_income e on a.pkid = e.da_household_id";
//				if (form_json.get("v2")!=null&&!form_json.get("v2").equals("")) {
//					term+=" e.v2 is NULL or ";
//				}
//				if (form_json.get("v4")!=null&&!form_json.get("v4").equals("")) {
//					term+=" e.v4 is NULL or ";
//				}
//				if (form_json.get("v6")!=null&&!form_json.get("v6").equals("")) {
//					term+=" e.v6 is NULL or ";
//				}
//				if (form_json.get("v8")!=null&&!form_json.get("v8").equals("")) {
//					term+=" e.v8 is NULL or ";
//				}
//				if (form_json.get("v12")!=null&&!form_json.get("v12").equals("")) {
//					term+=" e.v12 is NULL or ";
//				}
//				if (form_json.get("v14")!=null&&!form_json.get("v14").equals("")) {
//					term+=" e.v14 is NULL or ";
//				}
//				if (form_json.get("v16")!=null&&!form_json.get("v16").equals("")) {
//					term+=" e.v16 is NULL or ";
//				}
//				if (form_json.get("v18")!=null&&!form_json.get("v18").equals("")) {
//					term+=" e.v18 is NULL or ";
//				}
//				if (form_json.get("v43")!=null&&!form_json.get("v43").equals("")) {
//					term+=" e.v43 is NULL or ";
//				}
//				if (form_json.get("v41")!=null&&!form_json.get("v41").equals("")) {
//					term+=" e.v41 is NULL or ";
//				}
//				if (form_json.get("v26")!=null&&!form_json.get("v26").equals("")) {
//					term+=" e.v26 is NULL or ";
//				}
//				if (form_json.get("v24")!=null&&!form_json.get("v24").equals("")) {
//					term+=" e.v24 is NULL or ";
//				}
//				if (form_json.get("v28")!=null&&!form_json.get("v28").equals("")) {
//					term+=" e.v28 is NULL or ";
//				}
//				if (form_json.get("v32")!=null&&!form_json.get("v32").equals("")) {
//					term+=" e.v32 is NULL or ";
//				}
//			}else {									  //当前收入已完成	
//				left_sql=" join da_current_income e on a.pkid = e.da_household_id";
//				if (form_json.get("v2")!=null&&!form_json.get("v2").equals("")) {
//					term+=" e.v2 is not NULL and";
//				}
//				if (form_json.get("v4")!=null&&!form_json.get("v4").equals("")) {
//					term+=" e.v4 is not NULL and";
//				}
//				if (form_json.get("v6")!=null&&!form_json.get("v6").equals("")) {
//					term+=" e.v6 is not NULL and";
//				}
//				if (form_json.get("v8")!=null&&!form_json.get("v8").equals("")) {
//					term+=" e.v8 is not NULL and";
//				}
//				if (form_json.get("v12")!=null&&!form_json.get("v12").equals("")) {
//					term+=" e.v12 is not NULL and";
//				}
//				if (form_json.get("v14")!=null&&!form_json.get("v14").equals("")) {
//					term+=" e.v14 is not NULL and";
//				}
//				if (form_json.get("v16")!=null&&!form_json.get("v16").equals("")) {
//					term+=" e.v16 is not NULL and";
//				}
//				if (form_json.get("v18")!=null&&!form_json.get("v18").equals("")) {
//					term+=" e.v18 is not NULL and";
//				}
//				if (form_json.get("v43")!=null&&!form_json.get("v43").equals("")) {
//					term+=" e.v43 is not NULL and";
//				}
//				if (form_json.get("v41")!=null&&!form_json.get("v41").equals("")) {
//					term+=" e.v41 is not NULL and";
//				}
//				if (form_json.get("v26")!=null&&!form_json.get("v26").equals("")) {
//					term+=" e.v26 is not NULL and";
//				}
//				if (form_json.get("v24")!=null&&!form_json.get("v24").equals("")) {
//					term+=" e.v24 is not NULL and";
//				}
//				if (form_json.get("v28")!=null&&!form_json.get("v28").equals("")) {
//					term+=" e.v28 is not NULL and";
//				}
//				if (form_json.get("v32")!=null&&!form_json.get("v32").equals("")) {
//					term+=" e.v32 is not NULL and";
//				}
//			}
//		}else if (form_name.equals("zhichu_form")) {
//			if (danxuan_json.get("a").equals("0")) {  //当前支出未完成
//				left_sql=" join da_current_expenditure f on a.pkid = f.da_household_id";
//				if (form_json.get("zc-v2")!=null&&!form_json.get("zc-v2").equals("")) {
//					term+=" f.v2 is NULL or ";
//				}
//				if (form_json.get("zc-v4")!=null&&!form_json.get("zc-v4").equals("")) {
//					term+=" f.v4 is NULL or ";
//				}
//				if (form_json.get("zc-v6")!=null&&!form_json.get("zc-v6").equals("")) {
//					term+=" f.v6 is NULL or ";
//				}
//				if (form_json.get("zc-v8")!=null&&!form_json.get("zc-v8").equals("")) {
//					term+=" f.v8 is NULL or ";
//				}
//				if (form_json.get("zc-v10")!=null&&!form_json.get("zc-v10").equals("")) {
//					term+=" f.v10 is NULL or ";
//				}
//				if (form_json.get("zc-v12")!=null&&!form_json.get("zc-v12").equals("")) {
//					term+=" f.v12 is NULL or ";
//				}
//				if (form_json.get("zc-v14")!=null&&!form_json.get("zc-v14").equals("")) {
//					term+=" f.v14 is NULL or ";
//				}
//				if (form_json.get("zc-v16")!=null&&!form_json.get("zc-v16").equals("")) {
//					term+=" f.v16 is NULL or ";
//				}
//				if (form_json.get("zc-v18")!=null&&!form_json.get("zc-v18").equals("")) {
//					term+=" f.v18 is NULL or ";
//				}
//				if (form_json.get("zc-v20")!=null&&!form_json.get("zc-v20").equals("")) {
//					term+=" f.v20 is NULL or ";
//				}
//				if (form_json.get("zc-v27")!=null&&!form_json.get("zc-v27").equals("")) {
//					term+=" f.v27 is NULL or ";
//				}
//			}else {									  //当前支出已完成	
//				left_sql=" join da_current_expenditure f on a.pkid = f.da_household_id";
//				if (form_json.get("zc-v2")!=null&&!form_json.get("zc-v2").equals("")) {
//					term+=" f.v2 is not NULL and";
//				}
//				if (form_json.get("zc-v4")!=null&&!form_json.get("zc-v4").equals("")) {
//					term+=" f.v4 is not NULL and";
//				}
//				if (form_json.get("zc-v6")!=null&&!form_json.get("zc-v6").equals("")) {
//					term+=" f.v6 is not NULL and";
//				}
//				if (form_json.get("zc-v8")!=null&&!form_json.get("zc-v8").equals("")) {
//					term+=" f.v8 is not NULL and";
//				}
//				if (form_json.get("zc-v10")!=null&&!form_json.get("zc-v10").equals("")) {
//					term+=" f.v10 is not NULL and";
//				}
//				if (form_json.get("zc-v12")!=null&&!form_json.get("zc-v12").equals("")) {
//					term+=" f.v12 is not NULL and";
//				}
//				if (form_json.get("zc-v14")!=null&&!form_json.get("zc-v14").equals("")) {
//					term+=" f.v14 is not NULL and";
//				}
//				if (form_json.get("zc-v16")!=null&&!form_json.get("zc-v16").equals("")) {
//					term+=" f.v16 is not NULL and";
//				}
//				if (form_json.get("zc-v18")!=null&&!form_json.get("zc-v18").equals("")) {
//					term+=" f.v18 is not NULL and";
//				}
//				if (form_json.get("zc-v20")!=null&&!form_json.get("zc-v20").equals("")) {
//					term+=" f.v20 is not NULL and";
//				}
//				if (form_json.get("zc-v27")!=null&&!form_json.get("zc-v27").equals("")) {
//					term+=" f.v27 is not NULL and";
//				}
//			}
//		}else if (form_name.equals("bfr_form")) {
//			if (danxuan_json.get("a").equals("0")) {  //帮扶人情况未完成
//				left_sql=" join sys_personal_household_many g on g.da_household_id=a.pkid join sys_personal h on h.pkid=g.sys_personal_id";
//				if (form_json.get("col_name")!=null&&!form_json.get("col_name").equals("")) {
//					term+=" h.col_name is NULL or h.col_name = ''  or";
//				}
//				if (form_json.get("col_post")!=null&&!form_json.get("col_post").equals("")) {
//					term+=" h.col_post is NULL or h.col_post = ''  or";
//				}
//				if (form_json.get("telephone")!=null&&!form_json.get("telephone").equals("")) {
//					term+=" h.telephone is NULL or h.telephone = ''  or";
//				}
//				if (form_json.get("com_name")!=null&&!form_json.get("com_name").equals("")) {
//					left_sql+="  join da_company j on j.pkid = h.da_company_id";
//					term+=" j.v1 is NULL or j.v1 = ''  or";
//				}
//			}else {									  //帮扶人情况已完成	
//				left_sql=" join sys_personal_household_many g on g.da_household_id=a.pkid join sys_personal h on h.pkid=g.sys_personal_id";
//				if (form_json.get("col_name")!=null&&!form_json.get("col_name").equals("")) {
//					term+=" h.col_name != '' and";
//				}
//				if (form_json.get("col_post")!=null&&!form_json.get("col_post").equals("")) {
//					term+=" h.col_post != '' and";
//				}
//				if (form_json.get("telephone")!=null&&!form_json.get("telephone").equals("")) {
//					term+=" h.telephone != '' and";
//				}
//				if (form_json.get("com_name")!=null&&!form_json.get("com_name").equals("")) {
//					left_sql+="  join da_company j on j.pkid = h.da_company_id";
//					term+=" j.v1 != '' and";
//				}
//			}
//		}else if (form_name.equals("bfmb_form")) {
//			if (danxuan_json.get("a").equals("0")) {  //帮扶成效未完成
//				left_sql=" join da_help_info i on a.pkid = i.da_household_id";
//				if (form_json.get("bf_v1")!=null&&!form_json.get("bf_v1").equals("")) {
//					term+=" i.v1 is NULL or i.v1 = ''  or";
//				}
//				if (form_json.get("bf_v2")!=null&&!form_json.get("bf_v2").equals("")) {
//					term+=" i.v2 is NULL or i.v2 = ''  or";
//				}
//				if (form_json.get("bf_v3")!=null&&!form_json.get("bf_v3").equals("")) {
//					term+=" i.v3 is NULL or i.v3 = ''  or";
//				}
//			}else {									  //帮扶成效已完成	
//				left_sql=" join da_help_info i on a.pkid = i.da_household_id";
//				if (form_json.get("bf_v1")!=null&&!form_json.get("bf_v1").equals("")) {
//					term+=" i.v1 != '' and";
//				}
//				if (form_json.get("bf_v2")!=null&&!form_json.get("bf_v2").equals("")) {
//					term+=" i.v2 != '' and";
//				}
//				if (form_json.get("bf_v3")!=null&&!form_json.get("bf_v3").equals("")) {
//					term+=" i.v3 != '' and";
//				}
//			}
//		}else if (form_name.equals("zoufang_form")) {
//			if (danxuan_json.get("a").equals("0")) {  //走访情况未完成
//				left_sql=" join da_help_visit k on k.da_household_id=a.pkid";
//				if (form_json.get("zf-v1")!=null&&!form_json.get("zf-v1").equals("")) {
//					term+=" k.v1 is NULL or k.v1 = ''  or";
//				}
//				if (form_json.get("zf-v2")!=null&&!form_json.get("zf-v2").equals("")) {
//					term+=" k.v2 is NULL or k.v2 = ''  or";
//				}
//				if (form_json.get("zf-v3")!=null&&!form_json.get("zf-v3").equals("")) {
//					term+=" k.v3 is NULL or k.v3 = ''  or";
//				}
//			}else {									  //走访情况已完成	
//				left_sql=" join da_help_visit k on k.da_household_id=a.pkid";
//				if (form_json.get("zf-v1")!=null&&!form_json.get("zf-v1").equals("")) {
//					term+=" k.v1 != '' and";
//				}
//				if (form_json.get("zf-v2")!=null&&!form_json.get("zf-v2").equals("")) {
//					term+=" k.v2 != '' and";
//				}
//				if (form_json.get("zf-v3")!=null&&!form_json.get("zf-v3").equals("")) {
//					term+=" k.v3 != '' and";
//				}
//			}
//		}else if (form_name.equals("chengxiao_form")) {
//			if (danxuan_json.get("a").equals("0")) {  //帮扶成效未完成
//				left_sql=" join da_help_results l on l.da_household_id=a.pkid ";
//				if (form_json.get("cx-v1")!=null&&!form_json.get("cx-v1").equals("")) {
//					term+=" l.v1 is NULL or l.v1 = ''  or";
//				}
//				if (form_json.get("cx-v2")!=null&&!form_json.get("cx-v2").equals("")) {
//					term+=" l.v2 is NULL or l.v2 = ''  or";
//				}
//				if (form_json.get("cx-v3")!=null&&!form_json.get("cx-v3").equals("")) {
//					term+=" l.v3 is NULL or l.v3 = ''  or";
//				}
//			}else {
//				left_sql=" left join da_help_results l on l.da_household_id=a.pkid ";
//				if (form_json.get("cx-v1")!=null&&!form_json.get("cx-v1").equals("")) {
//					term+=" l.v1 != '' and";
//				}
//				if (form_json.get("cx-v2")!=null&&!form_json.get("cx-v2").equals("")) {
//					term+=" l.v2 != '' and";
//				}
//				if (form_json.get("cx-v3")!=null&&!form_json.get("cx-v3").equals("")) {
//					term+=" l.v3 != '' and";
//				}
//			}
//		}else if (form_name.equals("cuoshi_form")) {
//			if (danxuan_json.get("a").equals("0")) {  //帮扶成效未完成
//				left_sql+=" join da_help_tz_measures m on m.da_household_id=a.pkid";
//				if (form_json.get("cs_v1")!=null&&!form_json.get("cs_v1").equals("")) {
//					term+=" m.v1 is NULL or m.v1 = ''  or";
//				}
//				if (form_json.get("cs_v2")!=null&&!form_json.get("cs_v2").equals("")) {
//					term+=" m.v2 is NULL or m.v2 = ''  or";
//				}
//				if (form_json.get("cs_v3")!=null&&!form_json.get("cs_v3").equals("")) {
//					term+=" m.v3 is NULL or m.v3 = ''  or";
//				}
//			}else {
//				left_sql+=" left join da_help_tz_measures m on m.da_household_id=a.pkid";
//				if (form_json.get("cs_v1")!=null&&!form_json.get("cs_v1").equals("")) {
//					term+=" m.v1 != '' and";
//				}
//				if (form_json.get("cs_v2")!=null&&!form_json.get("cs_v2").equals("")) {
//					term+=" m.v2 != '' and";
//				}
//				if (form_json.get("cs_v3")!=null&&!form_json.get("cs_v3").equals("")) {
//					term+=" m.v3 != '' and";
//				}
//			}
//		}else if (form_name.equals("hshouru_form")) {
//			if (danxuan_json.get("a").equals("0")) {  //帮扶后收入未完成
//				left_sql="  join da_helpback_income n on n.da_household_id = a.pkid";
//				if (form_json.get("v2")!=null&&!form_json.get("v2").equals("")) {
//					term+=" n.v2 is NULL or ";
//				}
//				if (form_json.get("v4")!=null&&!form_json.get("v4").equals("")) {
//					term+=" n.v4 is NULL or ";
//				}
//				if (form_json.get("v6")!=null&&!form_json.get("v6").equals("")) {
//					term+=" n.v6 is NULL or ";
//				}
//				if (form_json.get("v8")!=null&&!form_json.get("v8").equals("")) {
//					term+=" n.v8 is NULL or ";
//				}
//				if (form_json.get("v12")!=null&&!form_json.get("v12").equals("")) {
//					term+=" n.v12 is NULL or ";
//				}
//				if (form_json.get("v14")!=null&&!form_json.get("v14").equals("")) {
//					term+=" n.v14 is NULL or ";
//				}
//				if (form_json.get("v16")!=null&&!form_json.get("v16").equals("")) {
//					term+=" n.v16 is NULL or ";
//				}
//				if (form_json.get("v18")!=null&&!form_json.get("v18").equals("")) {
//					term+=" n.v18 is NULL or ";
//				}
//				if (form_json.get("v43")!=null&&!form_json.get("v43").equals("")) {
//					term+=" n.v43 is NULL or ";
//				}
//				if (form_json.get("v41")!=null&&!form_json.get("v41").equals("")) {
//					term+=" n.v41 is NULL or ";
//				}
//				if (form_json.get("v26")!=null&&!form_json.get("v26").equals("")) {
//					term+=" n.v26 is NULL or ";
//				}
//				if (form_json.get("v24")!=null&&!form_json.get("v24").equals("")) {
//					term+=" n.v24 is NULL or ";
//				}
//				if (form_json.get("v28")!=null&&!form_json.get("v28").equals("")) {
//					term+=" n.v28 is NULL or ";
//				}
//				if (form_json.get("v32")!=null&&!form_json.get("v32").equals("")) {
//					term+=" n.v32 is NULL or ";
//				}
//			}else {									  //帮扶后收入已完成	
//				left_sql="  join da_helpback_income n on n.da_household_id = a.pkid";
//				if (form_json.get("v2")!=null&&!form_json.get("v2").equals("")) {
//					term+=" n.v2 is not NULL and";
//				}
//				if (form_json.get("v4")!=null&&!form_json.get("v4").equals("")) {
//					term+=" n.v4 is not NULL and";
//				}
//				if (form_json.get("v6")!=null&&!form_json.get("v6").equals("")) {
//					term+=" n.v6 is not NULL and";
//				}
//				if (form_json.get("v8")!=null&&!form_json.get("v8").equals("")) {
//					term+=" n.v8 is not NULL and";
//				}
//				if (form_json.get("v12")!=null&&!form_json.get("v12").equals("")) {
//					term+=" n.v12 is not NULL and";
//				}
//				if (form_json.get("v14")!=null&&!form_json.get("v14").equals("")) {
//					term+=" n.v14 is not NULL and";
//				}
//				if (form_json.get("v16")!=null&&!form_json.get("v16").equals("")) {
//					term+=" n.v16 is not NULL and";
//				}
//				if (form_json.get("v18")!=null&&!form_json.get("v18").equals("")) {
//					term+=" n.v18 is not NULL and";
//				}
//				if (form_json.get("v43")!=null&&!form_json.get("v43").equals("")) {
//					term+=" n.v43 is not NULL and";
//				}
//				if (form_json.get("v41")!=null&&!form_json.get("v41").equals("")) {
//					term+=" n.v41 is not NULL and";
//				}
//				if (form_json.get("v26")!=null&&!form_json.get("v26").equals("")) {
//					term+=" n.v26 is not NULL and";
//				}
//				if (form_json.get("v24")!=null&&!form_json.get("v24").equals("")) {
//					term+=" n.v24 is not NULL and";
//				}
//				if (form_json.get("v28")!=null&&!form_json.get("v28").equals("")) {
//					term+=" n.v28 is not NULL and";
//				}
//				if (form_json.get("v32")!=null&&!form_json.get("v32").equals("")) {
//					term+=" n.v32 is not NULL and";
//				}
//			}
//		}else if (form_name.equals("hzhichu_form")) {
//			if (danxuan_json.get("a").equals("0")) {  //帮扶后支出未完成
//				left_sql=" join da_helpback_expenditure o on o.da_household_id = a.pkid";
//				if (form_json.get("zc-v2")!=null&&!form_json.get("zc-v2").equals("")) {
//					term+=" o.v2 is NULL or ";
//				}
//				if (form_json.get("zc-v4")!=null&&!form_json.get("zc-v4").equals("")) {
//					term+=" o.v4 is NULL or ";
//				}
//				if (form_json.get("zc-v6")!=null&&!form_json.get("zc-v6").equals("")) {
//					term+=" o.v6 is NULL or ";
//				}
//				if (form_json.get("zc-v8")!=null&&!form_json.get("zc-v8").equals("")) {
//					term+=" o.v8 is NULL or ";
//				}
//				if (form_json.get("zc-v10")!=null&&!form_json.get("zc-v10").equals("")) {
//					term+=" o.v10 is NULL or ";
//				}
//				if (form_json.get("zc-v12")!=null&&!form_json.get("zc-v12").equals("")) {
//					term+=" o.v12 is NULL or ";
//				}
//				if (form_json.get("zc-v14")!=null&&!form_json.get("zc-v14").equals("")) {
//					term+=" o.v14 is NULL or ";
//				}
//				if (form_json.get("zc-v16")!=null&&!form_json.get("zc-v16").equals("")) {
//					term+=" o.v16 is NULL or ";
//				}
//				if (form_json.get("zc-v18")!=null&&!form_json.get("zc-v18").equals("")) {
//					term+=" o.v18 is NULL or ";
//				}
//				if (form_json.get("zc-v20")!=null&&!form_json.get("zc-v20").equals("")) {
//					term+=" o.v20 is NULL or ";
//				}
//				if (form_json.get("zc-v27")!=null&&!form_json.get("zc-v27").equals("")) {
//					term+=" o.v27 is NULL or ";
//				}
//			}else {									  //帮扶后支出已完成	
//				left_sql=" join da_helpback_expenditure o on o.da_household_id = a.pkid";
//				if (form_json.get("zc-v2")!=null&&!form_json.get("zc-v2").equals("")) {
//					term+=" o.v2 is not NULL and";
//				}
//				if (form_json.get("zc-v4")!=null&&!form_json.get("zc-v4").equals("")) {
//					term+=" o.v4 is not NULL and";
//				}
//				if (form_json.get("zc-v6")!=null&&!form_json.get("zc-v6").equals("")) {
//					term+=" o.v6 is not NULL and";
//				}
//				if (form_json.get("zc-v8")!=null&&!form_json.get("zc-v8").equals("")) {
//					term+=" o.v8 is not NULL and";
//				}
//				if (form_json.get("zc-v10")!=null&&!form_json.get("zc-v10").equals("")) {
//					term+=" o.v10 is not NULL and";
//				}
//				if (form_json.get("zc-v12")!=null&&!form_json.get("zc-v12").equals("")) {
//					term+=" o.v12 is not NULL and";
//				}
//				if (form_json.get("zc-v14")!=null&&!form_json.get("zc-v14").equals("")) {
//					term+=" o.v14 is not NULL and";
//				}
//				if (form_json.get("zc-v16")!=null&&!form_json.get("zc-v16").equals("")) {
//					term+=" o.v16 is not NULL and";
//				}
//				if (form_json.get("zc-v18")!=null&&!form_json.get("zc-v18").equals("")) {
//					term+=" o.v18 is not NULL and";
//				}
//				if (form_json.get("zc-v20")!=null&&!form_json.get("zc-v20").equals("")) {
//					term+=" o.v20 is not NULL and";
//				}
//				if (form_json.get("zc-v27")!=null&&!form_json.get("zc-v27").equals("")) {
//					term+=" o.v27 is not NULL and";
//				}
//			}
//		}
//		String where =" where "+shilevel_sql+" and ("+ term.substring(0,term.length()-3) +")";
//		String people_sql="select a.pkid,a.v3,a.v4,a.v5,a.v6,a.v9,a.v21,a.v22,a.v23,a.v11,a.sys_standard from da_household a ";
//		String count_sql ="select count(*) from (select a.pkid from da_household a "+ left_sql +" where "+ shilevel_sql +" and ("+ term.substring(0,term.length()-3) +") GROUP BY a.pkid) z ";
//		String fpeople_sql=people_sql+left_sql+where+"GROUP BY a.pkid order by a.v2,a.pkid limit "+number+","+size;
//		int total = this.getBySqlMapper.findrows(count_sql);
//		if ( form_name ==null && form_name.equals("") && form_val  ==null && form_val .equals("")&& danxuan_val  ==null && danxuan_val .equals("")&& json_level  ==null && json_level .equals("")) {
//			count_sql="select count(*) from (select a.pkid from da_household a  GROUP BY a.pkid) z ";
//			fpeople_sql= people_sql+ "GROUP BY a.pkid order by a.v2,a.pkid limit "+number+","+size;
//		}
//		List<Map> Patient_st_List = this.getBySqlMapper.findRecords(fpeople_sql);
//		if(Patient_st_List.size()>0){
//			JSONArray jsa=new JSONArray();
//			for(int i = 0;i<Patient_st_List.size();i++){
//				Map Patient_st_map = Patient_st_List.get(i);
//				JSONObject val = new JSONObject();
//				for (Object key : Patient_st_map.keySet()) {
//					val.put(key, Patient_st_map.get(key));
//					
//					if(key.toString().equals("col_name")){
//						if("".equals(Patient_st_map.get("col_name")+"")){
//							val.put("col_name", "否");
//						}else{
//							val.put("col_name", "是");
//						}
//					}
//					val.put("chakan","<a onclick='chakan_info(\""+Patient_st_map.get("pkid")+"\")'>查看</a>");
//				}
//				jsa.add(val);
//				
//			}
//			JSONObject json = new JSONObject();
//			json.put("page", page);
//			json.put("total", total);
//			json.put("rows", jsa); //这里的 rows 和total 的key 是固定的 
//			response.getWriter().write(json.toString());
//		}
//	}
}
