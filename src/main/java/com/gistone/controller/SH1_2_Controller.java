package com.gistone.controller;

import java.io.IOException;
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
 * 扶贫手册-帮扶信息查询
 * @author chendong
 *
 */
@RestController
@RequestMapping
public class SH1_2_Controller{
	
//	@Autowired
//	private GetBySqlMapper getBySqlMapper;
//	/**
//	 * 帮扶信息查询的默认页
//	 * @author chendong
//	 * @date 2016年8月5日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException 
//	 */
//	@RequestMapping("getCx_1.do")
//	public void getCx_1(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		String pageSize = request.getParameter("pageSize");
//		String pageNumber = request.getParameter("pageNumber");
//		
//		String cha_sbbz ="";//识别标准
//		String cha_pksx ="";//贫困户属性
//		String cha_zpyy ="";//致贫原因
//		String cha_mz ="";//户主民族
//		String cha_renkou ="";//贫困户人口
//		String cha_banqian ="";//是否纳入易地扶贫搬迁 那我能说啥  
//		String cha_v6 = "";//户主姓名
//		String cha_v8 = "";//身份证号
//		String cha_v8_1 = "";//年龄范围
//		String nm_qx="";//新加的内蒙区县 新增的
//		String com_level="";//获取层级
//		String pkid="";//获取id
//		String str = "";
//		
//		com_level = request.getParameter("com_level");
//		pkid = request.getParameter("pkid");
//		nm_qx = request.getParameter("nm_qx").trim();
//		
//
//		str = "a.V"+com_level+"='"+nm_qx+"' and";
//
//
//	
//		if(request.getParameter("cha_v6")!=null&&!request.getParameter("cha_v6").equals("")){
//			cha_v6 = request.getParameter("cha_v6").trim();
//			str += " a.V6 like '%"+cha_v6+"%' and";
//		}
//		
//		if(request.getParameter("cha_v8")!=null&&!request.getParameter("cha_v8").equals("")){
//			cha_v8 = request.getParameter("cha_v8").trim();
//			str += " a.V8 like '%"+cha_v8+"%' and";
//		}
//		if(request.getParameter("cha_v8_1")!=null&&!request.getParameter("cha_v8_1").equals("请选择")){
//			cha_v8_1 = request.getParameter("cha_v8_1").trim();
//			if(cha_v8_1.equals("大于60岁")){
//				str += " LENGTH(a.v8)>=18 and year(now())- year(substr(a.v8,7,8))>=60 and";
//			}else if(cha_v8_1.equals("小于16岁")){
//				str += " LENGTH(a.v8)>=18 and year(now())- year(substr(a.v8,7,8))<=16 and";
//			}else if(cha_v8_1.equals("17岁至59岁")){
//				str += " LENGTH(a.v8)>=18 and (year(now())- year(substr(a.v8,7,8))>=17 or year(now())- year(substr(a.v8,7,8))>=59) and";
//			}
//		}
//		if(request.getParameter("cha_sbbz")!=null&&!request.getParameter("cha_sbbz").equals("请选择")){
//			cha_sbbz = request.getParameter("cha_sbbz").trim();
//			str += " a.SYS_STANDARD='"+cha_sbbz+"' and";
//		}
//		if(request.getParameter("cha_pksx")!=null&&!request.getParameter("cha_pksx").equals("请选择")){
//			cha_pksx = request.getParameter("cha_pksx").trim();
//			str += " a.V22='"+cha_pksx+"' and";
//		}
//		if(request.getParameter("cha_zpyy")!=null&&!request.getParameter("cha_zpyy").equals("请选择")){
//			cha_zpyy = request.getParameter("cha_zpyy").trim();
//			str += " a.V23 like '%"+cha_zpyy+"%' and";
//		}
//		if(request.getParameter("cha_mz")!=null&&!request.getParameter("cha_mz").equals("请选择")){
//			cha_mz = request.getParameter("cha_mz").trim();
//			str += " a.V11='"+cha_mz+"' and";
//		}
//		if(request.getParameter("cha_renkou")!=null&&!request.getParameter("cha_renkou").equals("请选择")){
//			cha_renkou = request.getParameter("cha_renkou").trim().substring(0,1);
//			if("5".equals(cha_renkou)){
//				str += " a.V9>=5 and";
//			}else{
//				str += " a.V9='"+cha_renkou+"' and";
//			}
//		}
//		//如果易地扶贫搬迁条件被选择
//		if(request.getParameter("cha_banqian")!=null&&!request.getParameter("cha_banqian").equals("请选择")){
//			cha_banqian = request.getParameter("cha_banqian").trim();
//			str += " a.V21='"+cha_banqian+"' and";
//		}
//
//		
//		String count_st_sql = "select count(*) from (select a.pkid,a.v1,a.v2,a.v3,a.v4,a.v5 from da_household a where "+str.substring(0, str.length()-3)+") a1 ";
//		String people_sql = "select rownum,a1.pkid,v2,v3,v4,v5,v6,v9,v22,v23,v11,sys_standard  from (select a.pkid,a.v2,a.v3,a.v4,a.v5,a.v6,a.v9,a.v22,a.v23,a.v11,a.sys_standard from da_household a where "+str.substring(0, str.length()-3)+") a1";
//		
//		if(com_level.equals("4") || com_level.equals("5")){
//			count_st_sql += " JOIN (select x.com_name as g_name,y.com_name as d_name from sys_company x join sys_company y on x.pkid=y.com_f_pkid where y.pkid="+pkid+" ) t1 on a1.v"+com_level+"=t1.d_name where a1.v"+(Integer.parseInt(com_level)-1)+"=t1.g_name";
//			people_sql += " JOIN (select x.com_name as g_name,y.com_name as d_name from sys_company x join sys_company y on x.pkid=y.com_f_pkid where y.pkid="+pkid+" ) t1 on a1.v"+com_level+"=t1.d_name where a1.v"+(Integer.parseInt(com_level)-1)+"=t1.g_name";
//		}
//		int size = Integer.parseInt(pageSize);
//		int number = Integer.parseInt(pageNumber);
//		int page = number == 0 ? 1 : (number/size)+1;
//		
//		//带条件，按照条件查询
//		people_sql += " where rownum>"+number+" and rownum<"+size+"  order by a1.v2,a1.pkid";
//		
//		int total = this.getBySqlMapper.findrows(count_st_sql);
//		List<Map> Patient_st_List = this.getBySqlMapper.findRecords(people_sql);
//		if(Patient_st_List.size()>0){
//			JSONArray jsa=new JSONArray();
//			for(int i = 0;i<Patient_st_List.size();i++){
//				Map Patient_st_map = Patient_st_List.get(i);
//				JSONObject val = new JSONObject();
//				for (Object key : Patient_st_map.keySet()) {
//					
//					val.put(key, Patient_st_map.get(key));
//					
//					if(key.toString().equals("V6")){
//						val.put(key, "<a onclick='chakan_info(\""+Patient_st_map.get("PKID")+"\")' data-toggle=\"modal\" >"+Patient_st_map.get(key)+"</a>");
//					}
//					
//					if(key.toString().equals("COL_NAME")){
//						if("".equals(Patient_st_map.get("COL_NAME")+"")){
//							val.put("col_name", "否");
//						}else{
//							val.put("col_name", "是");
//						}
//					}
//					//val.put("chakan","<a onclick='chakan_info(\""+Patient_st_map.get("pkid")+"\")'>查看</a>");
//				}
//				jsa.add(val);
//			}
//			
//			JSONObject json = new JSONObject();
//			json.put("page", page);
//			json.put("total", total);
//			json.put("rows", jsa); //这里的 rows 和total 的key 是固定的 
//			response.getWriter().write(json.toString());
//		}
//	}
//	/**
//	 * 查看贫困户基本信息
//	 * @author chendong
//	 * @date 2016年8月5日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException
//	 */
//	@RequestMapping("getCx_2.do")
//	public void getCx_2(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		String pkid=request.getParameter("pkid");
//		String sql="select * from da_household a left join da_household_basic b on a.pkid=b.da_household_id "+
//				"LEFT JOIN (SELECT pic_path,pic_pkid from da_pic WHERE pic_type=4 ) c ON a.pkid=c.pic_pkid  where a.pkid="+pkid;
//		List<Map> list=getBySqlMapper.findRecords(sql);
//		//户主信息
//		JSONArray jsonArray1 =new JSONArray();
//		
//		for(Map val:list){
//			JSONObject obj=new JSONObject ();
//			obj.put("pkid",val.get("PKID")==null?"":val.get("PKID"));
//			obj.put("v6", val.get("V6")==null?"":val.get("V6"));//姓名
//			obj.put("v7",val.get("V7")==null?"":val.get("V7"));//性别
//			obj.put("v8",val.get("V8")==null?"":val.get("V8"));//证件号码
//			obj.put("v9",val.get("V9")==null?"":val.get("V9"));//人数
//			obj.put("v11",val.get("V11")==null?"":val.get("V11"));//民族
//			obj.put("v12",val.get("V12")==null?"":val.get("V12"));//文化程度
//			obj.put("v13",val.get("V13")==null?"":val.get("V13"));//在校生状况
//			obj.put("v14",val.get("V14")==null?"":val.get("V14"));//健康状况
//			obj.put("v15",val.get("V15")==null?"":val.get("V15"));//劳动能力
//			obj.put("v16",val.get("V16")==null?"":val.get("V16"));//务工情况
//			obj.put("v17",val.get("V17")==null?"":val.get("V17"));//务工时间
//			obj.put("v18",val.get("V18")==null?"":val.get("V18"));//是否参加新农合
//			obj.put("v19",val.get("V19")==null?"":val.get("V19"));//是否参加新型养老保险
//			obj.put("v21",val.get("V21")==null?"":val.get("V21"));//脱贫属性
//			obj.put("v25",val.get("V25")==null?"":val.get("V25"));//联系电话
//			obj.put("v26",val.get("V26")==null?"":val.get("V26"));//开户银行名称
//			obj.put("v27",val.get("V27")==null?"":val.get("V27"));//银行卡号
//			obj.put("sys_standard",val.get("SYS_STANDARD")==null?"":val.get("SYS_STANDARD"));//识别标准
//			obj.put("v22",val.get("V22")==null?"":val.get("V22"));//贫苦户属性
//			obj.put("v23",val.get("V23")==null?"":val.get("V23"));//主要致贫原因
//			obj.put("v29",val.get("V29")==null?"":val.get("V29"));//是否军烈属
//			obj.put("v30",val.get("V30")==null?"":val.get("V30"));//是否独生子女
//			obj.put("v31",val.get("V31")==null?"":val.get("V31"));//是否双女户
//			obj.put("v32",val.get("V32")==null?"":val.get("V32"));//是否现役军人
//			obj.put("v33",val.get("V33")==null?"":val.get("V33"));//其他致贫原因
//			obj.put("v28",val.get("V28")==null?"":val.get("V28"));//其他致贫原因
//			obj.put("basic_address",val.get("basic_address")==null?"":val.get("basic_address"));//家庭住址
//			obj.put("basic_explain",val.get("basic_explain")==null?"":val.get("basic_explain"));//致贫原因说明
//			obj.put("pic_path",val.get("PIC_PATH")==null?"":val.get("PIC_PATH"));//致贫原因说明
//			jsonArray1.add(obj);
//		}
//		//家庭成员
//		JSONArray jsonArray2 =new JSONArray();
//		String xian_sql="select * from da_member a LEFT JOIN (SELECT pic_path,pic_pkid from da_pic WHERE pic_type=5 ) b ON a.pkid=b.pic_pkid  where a.da_household_id="+pkid;
//		List<Map> xian_list=getBySqlMapper.findRecords(xian_sql);
//		for(Map val:xian_list){
//			JSONObject obj=new JSONObject ();
//			obj.put("cy_pkid",val.get("PKID")==null?"":val.get("PKID"));
//			obj.put("cy_v6",val.get("V6")==null?"":val.get("V6"));//姓名
//			obj.put("cy_v7",val.get("V7")==null?"":val.get("V7"));//性别
//			obj.put("cy_v8",val.get("V8")==null?"":val.get("V8"));//证件号码
//			obj.put("cy_v9",val.get("V9")==null?"":val.get("V9"));//人数
//			obj.put("cy_v10",val.get("V10")==null?"":val.get("V10"));//与户主关系
//			obj.put("cy_v11",val.get("V11")==null?"":val.get("V11"));//民族
//			obj.put("cy_v12",val.get("V12")==null?"":val.get("V12"));//文化程度
//			obj.put("cy_v13",val.get("V13")==null?"":val.get("V13"));//在校状况
//			obj.put("cy_v14",val.get("V14")==null?"":val.get("V14"));//健康状况
//			obj.put("cy_v15",val.get("V15")==null?"":val.get("V15"));//劳动力
//			obj.put("cy_v16",val.get("V16")==null?"":val.get("V16"));//务工状态
//			obj.put("cy_v17",val.get("V17")==null?"":val.get("V17"));//务工时间
//			obj.put("cy_v18",val.get("V18")==null?"":val.get("V18"));//是否参加新农合
//			obj.put("cy_v19",val.get("V19")==null?"":val.get("V19"));//是否参加新型养老保险
//			obj.put("cy_v20",val.get("V20")==null?"":val.get("V20"));//是否参加城镇职工基本养老保险	
//			obj.put("cy_v21",val.get("V21")==null?"":val.get("V21"));//脱贫属性
//			
//			obj.put("cy_v16",val.get("V16")==null?"":val.get("V16"));//务工情况
//			obj.put("cy_v17",val.get("V17")==null?"":val.get("V17"));//务工时间
//			obj.put("cy_v32",val.get("V32")==null?"":val.get("V32"));//是否现役军人
//			obj.put("cy_v28",val.get("V28")==null?"":val.get("V28"));//政治面貌
//			obj.put("cy_pic_path",val.get("pic_path")==null?"":val.get("pic_path"));//头像
//			jsonArray2.add(obj);
//		}
//		
//		//生产条件
//		JSONArray jsonArray3 =new JSONArray();
//		String sc_sql="select * FROM da_production where da_household_id="+pkid;
//		List<Map> sc_list=getBySqlMapper.findRecords(sc_sql);
//		for(Map val:sc_list){
//			JSONObject obj=new JSONObject ();
//			obj.put("sc_pkid",val.get("PKID")==null?"":val.get("PKID"));
//			obj.put("sc_v1",val.get("V1")==null?"":val.get("V1"));//耕地面积
//			obj.put("sc_v2",val.get("V2")==null?"":val.get("V2"));//水浇地面积
//			obj.put("sc_v3",val.get("V3")==null?"":val.get("V3"));//林地面积
//			obj.put("sc_v4",val.get("V4")==null?"":val.get("V4"));//退耕还林面积
//			obj.put("sc_v5",val.get("V5")==null?"":val.get("V5"));//草牧场面积
//			obj.put("sc_v6",val.get("V6")==null?"":val.get("V6"));//生产用房面积
//			obj.put("sc_v7",val.get("V7")==null?"":val.get("V7"));//其他
//			obj.put("sc_v8",val.get("V8")==null?"":val.get("V8"));//家禽
//			obj.put("sc_v9",val.get("V9")==null?"":val.get("V9"));//牛
//			obj.put("sc_v10",val.get("V10")==null?"":val.get("V10"));//羊
//			obj.put("sc_v11",val.get("V11")==null?"":val.get("V11"));//猪
//			obj.put("sc_v12",val.get("V12")==null?"":val.get("V12"));//其他
//			obj.put("sc_v13",val.get("V13")==null?"":val.get("V13"));//林果面积
//			obj.put("sc_v14",val.get("V14")==null?"":val.get("V14"));//水面面积
//			jsonArray3.add(obj);
//			
//		}
//		
//		//生活条件
//		JSONArray jsonArray4 =new JSONArray();
//		String sh_sql="SELECT * FROM da_life where da_household_id="+pkid;
//		List<Map> sh_list=getBySqlMapper.findRecords(sh_sql);
//		for(Map val:sh_list){
//			JSONObject sh_obj=new JSONObject ();
//			sh_obj.put("sh_pkid",val.get("PKID")==null?"":val.get("PKID"));
//			sh_obj.put("sh_v1",val.get("V1")==null?"":val.get("V1"));//住房面积
//			sh_obj.put("sh_v2",val.get("V2")==null?"":val.get("V2"));//是否危房
//			sh_obj.put("sh_v3",val.get("V3")==null?"":val.get("V3"));//是否纳入易地扶贫搬迁
//			sh_obj.put("sh_v4",val.get("V4")==null?"":val.get("V4"));//饮水情况
//			sh_obj.put("sh_v5",val.get("V5")==null?"":val.get("V5"));//通电情况
//			sh_obj.put("sh_v6",val.get("V6")==null?"":val.get("V6"));//入户路类型
//			sh_obj.put("sh_v9",val.get("V9")==null?"":val.get("V9"));//饮水是否安全
//			sh_obj.put("sh_v10",val.get("V10")==null?"":val.get("V10"));//主要燃料类型
//			sh_obj.put("sh_v11",val.get("V11")==null?"":val.get("V11"));//是否加入农民专业合作社
//			sh_obj.put("sh_v12",val.get("V12")==null?"":val.get("V12"));//有无卫生厕所
//			sh_obj.put("sh_v8",val.get("V8")==null?"":val.get("V8"));//饮水是否困难
//			sh_obj.put("sh_v7",val.get("V7")==null?"":val.get("V7"));//与村主干路距离（公里）
//			jsonArray4.add(sh_obj);
//		}
//			
//		response.getWriter().write("{\"data1\":"+jsonArray1.toString() +
//				",\"data2\":"+jsonArray2.toString()+",\"data3\":"+jsonArray3.toString()+
//				",\"data4\":"+jsonArray4.toString()+"}");
//		response.getWriter().close();
//	}
//	/**
//	 * 贫困户当前收支情况
//	 * @author chendong
//	 * @date 2016年8月5日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException
//	 */
//	@RequestMapping("getCx_3.do")
//	public void getCx_3(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		String pkid=request.getParameter("pkid");
//		
//		//当前收支分析
//		JSONArray jsonArray5 =new JSONArray();
//		String dqsr_sql="SELECT * FROM da_current_income where da_household_id="+pkid;
//		String dqsrh_sql="SELECT * FROM da_helpback_income where da_household_id="+pkid;
//		List<Map> dqsr_list=getBySqlMapper.findRecords(dqsr_sql);
//		for(Map val:dqsr_list){
//			JSONObject dqsr_obj=new JSONObject ();
//			dqsr_obj.put("dqsr_pkid",val.get("PKID")==null?"":val.get("PKID"));
//			dqsr_obj.put("dqsr_v1",val.get("V1")==null?"":val.get("V1"));//农业明细
//			dqsr_obj.put("dqsr_v2",val.get("V2")==null?"":val.get("V2"));//农业金额
//			dqsr_obj.put("dqsr_v3",val.get("V3")==null?"":val.get("V3"));//畜牧业明细
//			dqsr_obj.put("dqsr_v4",val.get("V4")==null?"":val.get("V4"));//畜牧业金额
//			dqsr_obj.put("dqsr_v5",val.get("V5")==null?"":val.get("V5"));//林业明细
//			dqsr_obj.put("dqsr_v6",val.get("V6")==null?"":val.get("V6"));//林业金额
//			dqsr_obj.put("dqsr_v7",val.get("V7")==null?"":val.get("V7"));//其他明细
//			dqsr_obj.put("dqsr_v8",val.get("V8")==null?"":val.get("V8"));//其他金额
//			dqsr_obj.put("dqsr_v9",val.get("V9")==null?"":val.get("V9"));//小计明细
//			dqsr_obj.put("dqsr_v10",val.get("V10")==null?"":val.get("V10"));//小计金额
//			dqsr_obj.put("dqsr_v11",val.get("V11")==null?"":val.get("V11"));//生态补贴明细
//			dqsr_obj.put("dqsr_v12",val.get("V12")==null?"":val.get("V12"));//生态补贴金额
//			dqsr_obj.put("dqsr_v13",val.get("V13")==null?"":val.get("V13"));//养老金明细
//			dqsr_obj.put("dqsr_v14",val.get("V14")==null?"":val.get("V14"));//养老金金额	
//			dqsr_obj.put("dqsr_v15",val.get("V15")==null?"":val.get("V15"));//低保补贴明细
//			dqsr_obj.put("dqsr_v16",val.get("V16")==null?"":val.get("V16"));//低保补贴金额
//			dqsr_obj.put("dqsr_v17",val.get("V17")==null?"":val.get("V17"));//燃煤补贴明细
//			dqsr_obj.put("dqsr_v18",val.get("V18")==null?"":val.get("V18"));//燃煤补贴金额
//			dqsr_obj.put("dqsr_v19",val.get("V19")==null?"":val.get("V19"));//其他明细
//			dqsr_obj.put("dqsr_v20",val.get("V20")==null?"":val.get("V20"));//其他金额
//			dqsr_obj.put("dqsr_v21",val.get("V21")==null?"":val.get("V21"));//小计明细
//			dqsr_obj.put("dqsr_v22",val.get("V22")==null?"":val.get("V22"));//小计金额
//			dqsr_obj.put("dqsr_v23",val.get("V23")==null?"":val.get("V23"));//土地流转明细
//			dqsr_obj.put("dqsr_v24",val.get("V24")==null?"":val.get("V24"));//土地流转金额
//			dqsr_obj.put("dqsr_v25",val.get("V25")==null?"":val.get("V25"));//其他明细
//			dqsr_obj.put("dqsr_v26",val.get("V26")==null?"":val.get("V26"));//其他金额
//			
//			dqsr_obj.put("dqsr_v27",val.get("V27")==null?"":val.get("V27"));//工资明细1
//			dqsr_obj.put("dqsr_v28",val.get("V28")==null?"":val.get("V28"));//工资金额1
//			dqsr_obj.put("dqsr_v29",val.get("V29")==null?"":val.get("V29"));//工资明细2
//			dqsr_obj.put("dqsr_v30",val.get("V30")==null?"":val.get("V30"));//工资金额2
//			dqsr_obj.put("dqsr_v31",val.get("V31")==null?"":val.get("V31"));//其他明细1
//			dqsr_obj.put("dqsr_v32",val.get("V32")==null?"":val.get("V32"));//其他金额1
//			dqsr_obj.put("dqsr_v33",val.get("V33")==null?"":val.get("V33"));//其他明细2
//			dqsr_obj.put("dqsr_v34",val.get("V34")==null?"":val.get("V34"));//其他金额2
//			dqsr_obj.put("dqsr_v35",val.get("V35")==null?"":val.get("V35"));//工资项目1
//			dqsr_obj.put("dqsr_v36",val.get("V36")==null?"":val.get("V36"));//工资项目2
//			dqsr_obj.put("dqsr_v37",val.get("V37")==null?"":val.get("V37"));//其他项目1
//			dqsr_obj.put("dqsr_v38",val.get("V38")==null?"":val.get("V38"));//其他项目2
//			dqsr_obj.put("dqsr_v39",val.get("V39")==null?"":val.get("V39"));//年总收入
//			
//			dqsr_obj.put("dqsr_v40",val.get("V40")==null?"":val.get("V40"));//计划生育金明细
//			dqsr_obj.put("dqsr_v41",val.get("V41")==null?"":val.get("V41"));//计划生育金额
//			dqsr_obj.put("dqsr_v42",val.get("V42")==null?"":val.get("V42"));//五保金明细
//			dqsr_obj.put("dqsr_v43",val.get("V43")==null?"":val.get("V43"));//五保金金额
//
//			jsonArray5.add(dqsr_obj);
//		}
//		
//		//当前支出
//		JSONArray jsonArray6 =new JSONArray();
//		String dqzc_sql="SELECT * FROM da_current_expenditure where da_household_id="+pkid;
//		List<Map> dqzc_list=getBySqlMapper.findRecords(dqzc_sql);
//		for(Map val:dqzc_list){
//			JSONObject dqzc_obj=new JSONObject ();
//			dqzc_obj.put("dqzc_pkid",val.get("PKID")==null?"":val.get("PKID"));
//			dqzc_obj.put("dqzc_v1",val.get("V1")==null?"":val.get("V1"));//农资明细
//			dqzc_obj.put("dqzc_v2",val.get("V2")==null?"":val.get("V2"));//农资金额
//			dqzc_obj.put("dqzc_v3",val.get("V3")==null?"":val.get("V3"));//固定资产折旧明细
//			dqzc_obj.put("dqzc_v4",val.get("V4")==null?"":val.get("V4"));//固定资产折旧金额
//			dqzc_obj.put("dqzc_v5",val.get("V5")==null?"":val.get("V5"));//水电明细
//			dqzc_obj.put("dqzc_v6",val.get("V6")==null?"":val.get("V6"));//水电金额
//			dqzc_obj.put("dqzc_v7",val.get("V7")==null?"":val.get("V7"));//承包土地明细
//			dqzc_obj.put("dqzc_v8",val.get("V8")==null?"":val.get("V8"));//承包土地金额
//			dqzc_obj.put("dqzc_v9",val.get("V9")==null?"":val.get("V9"));//饲草料明细
//			dqzc_obj.put("dqzc_v10",val.get("V10")==null?"":val.get("V10"));//	饲草料金额
//			dqzc_obj.put("dqzc_v11",val.get("V11")==null?"":val.get("V11"));//	防疫明细
//			dqzc_obj.put("dqzc_v12",val.get("V12")==null?"":val.get("V12"));//防疫金额
//			dqzc_obj.put("dqzc_v13",val.get("V13")==null?"":val.get("V13"));//种畜明细
//			dqzc_obj.put("dqzc_v14",val.get("V14")==null?"":val.get("V14"));//种畜金额
//			dqzc_obj.put("dqzc_v15",val.get("V15")==null?"":val.get("V15"));//销售通讯明细
//			dqzc_obj.put("dqzc_v16",val.get("V16")==null?"":val.get("V16"));//销售通讯金额
//			dqzc_obj.put("dqzc_v17",val.get("V17")==null?"":val.get("V17"));//	借贷明细
//			dqzc_obj.put("dqzc_v18",val.get("V18")==null?"":val.get("V18"));//	借贷金额
//			dqzc_obj.put("dqzc_v19",val.get("V19")==null?"":val.get("V19"));//政策明细1
//			dqzc_obj.put("dqzc_v20",val.get("V20")==null?"":val.get("V20"));//政策金额1
//			dqzc_obj.put("dqzc_v21",val.get("V21")==null?"":val.get("V21"));//政策明细2
//			dqzc_obj.put("dqzc_v22",val.get("V22")==null?"":val.get("V22"));//	政策金额2
//			dqzc_obj.put("dqzc_v23",val.get("V23")==null?"":val.get("V23"));//	政策项目1
//			dqzc_obj.put("dqzc_v24",val.get("V24")==null?"":val.get("V24"));//政策项目2
//			dqzc_obj.put("dqzc_v25",val.get("V25")==null?"":val.get("V25"));//其他项目1
//			dqzc_obj.put("dqzc_v26",val.get("V26")==null?"":val.get("V26"));//其他明细1
//			dqzc_obj.put("dqzc_v27",val.get("V27")==null?"":val.get("V27"));//其他金额1
//			dqzc_obj.put("dqzc_v28",val.get("V28")==null?"":val.get("V28"));//	其他项目2
//			dqzc_obj.put("dqzc_v29",val.get("V29")==null?"":val.get("V29"));//其他明细2
//			dqzc_obj.put("dqzc_v30",val.get("V30")==null?"":val.get("V30"));//其他金额2
//			dqzc_obj.put("dqzc_v31",val.get("V31")==null?"":val.get("V31"));//年总支出
//			jsonArray6.add(dqzc_obj);
//		}
//		response.getWriter().write("{\"data5\":"+jsonArray5.toString()+",\"data6\":"+jsonArray6.toString()+"}");
//		response.getWriter().close();
//	
//	}
//	/**
//	 * 查看帮扶人情况
//	 * @author chendong
//	 * @date 2016年8月5日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException
//	 */
//	@RequestMapping("getCx_4.do")
//	public void getCx_4(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		String pkid=request.getParameter("pkid");
//		//帮扶人情况
//		JSONArray jsonArray7 =new JSONArray();
//		String bfr_sql="SELECT da_household_id,telephone ,col_post, col_name,t2.v1 FROM sys_personal_household_many a "
//				+ "LEFT JOIN sys_personal b ON a.sys_personal_id = b.pkid LEFT join da_company t2 on b.da_company_id=t2.pkid where da_household_id="+pkid;
//		List<Map> bfr_list=getBySqlMapper.findRecords(bfr_sql);
//		for(Map val:bfr_list){
//			JSONObject bfr_obj=new JSONObject ();
//			bfr_obj.put("bfr_pkid",val.get("pkid")==null?"":val.get("pkid"));
//			bfr_obj.put("bfr_col_name",val.get("col_name")==null?"":val.get("col_name"));//帮扶人姓名
//			bfr_obj.put("bfr_com_name",val.get("v1")==null?"":val.get("v1"));//帮扶人单位
//			bfr_obj.put("bfr_col_post",val.get("col_post")==null?"":val.get("col_post"));//帮扶人职务
//			bfr_obj.put("bfr_telephone",val.get("telephone")==null?"":val.get("telephone"));//帮扶人电话
//			jsonArray7.add(bfr_obj);
//		}
//		
//		JSONArray jsonArray15 =new JSONArray();
//		String bfjihua_sql="SELECT v1 mubiao,v2 shixiao,v3 jihua, da_household_id jhid from da_help_info where da_household_id="+pkid;
//		List<Map> bfjihua_list=getBySqlMapper.findRecords(bfjihua_sql);
//		for(Map val:bfjihua_list){
//			JSONObject bfr_obj=new JSONObject ();
//			bfr_obj.put("bfr_mubiao",val.get("mubiao")==null?"":val.get("mubiao"));//帮扶目标
//			bfr_obj.put("bfr_shixiao",val.get("shixiao")==null?"":val.get("shixiao"));//帮扶时效
//			bfr_obj.put("bfr_jihua",val.get("jihua")==null?"":val.get("jihua"));//帮扶计划
//			jsonArray15.add(bfr_obj);
//		}
//		
//		
//		//帮扶人走访情况
//		JSONArray jsonArray8 =new JSONArray();
//		String zf_sql="SELECT v1,v2,v3, group_concat(pic_path order by pic_path separator ',') path FROM (" +
//				"SELECT *  FROM da_help_visit a LEFT JOIN (SELECT pic_path,pic_pkid from da_pic WHERE pic_type=2 ) b ON a.pkid=b.pic_pkid "+
//				" WHERE a.da_household_id="+pkid+" )aa GROUP BY pkid ORDER BY v1 DESC";
//		List<Map> zf_list=getBySqlMapper.findRecords(zf_sql);
//		for(Map val:zf_list){
//			JSONObject zf_obj=new JSONObject ();
//			zf_obj.put("zf_v1",val.get("v1")==null?"":val.get("v1"));//走访时间
//			zf_obj.put("zf_v2",val.get("v2")==null?"":val.get("v2"));//帮扶干部
//			zf_obj.put("zf_v3",val.get("v3")==null?"":val.get("v3"));//帮扶目标
//			zf_obj.put("zf_pic",val.get("path")==null?"":val.get("path"));//帮扶目标
//			
//			jsonArray8.add(zf_obj);
//		}
//		response.getWriter().write("{\"data7\":"+jsonArray7.toString()+",\"data8\":"+jsonArray8.toString()+",\"data15\":"+jsonArray15.toString()+"}");
//		response.getWriter().close();
//	}
//	/**
//	 * 查看贫苦户的帮扶成效
//	 * @author chendong
//	 * @date 2016年8月5日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException
//	 */
//	@RequestMapping("getCx_5.do")
//	public void getCx_5(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		String pkid=request.getParameter("pkid");
//		//帮扶成效
//		JSONArray jsonArray10 =new JSONArray();
//		String bfcx_sql="SELECT v1,v2,v3, group_concat(pic_path order by pic_path separator ',') path FROM ("+
//						"SELECT *  FROM da_help_results a LEFT JOIN (SELECT pic_path,pic_pkid from da_pic WHERE pic_type=3 ) b ON a.pkid=b.pic_pkid "+
//						"WHERE a.da_household_id="+pkid+" )aa GROUP BY pkid ORDER BY v1 DESC";
//		List<Map> bfcx_list=getBySqlMapper.findRecords(bfcx_sql);
//		for(Map val:bfcx_list){
//			JSONObject bfcx_obj=new JSONObject ();
//			bfcx_obj.put("bfcx_v1",val.get("v1")==null?"":val.get("v1"));//时间
//			bfcx_obj.put("bfcx_v2",val.get("v2")==null?"":val.get("v2"));//成效内容
//			bfcx_obj.put("bfcx_v3",val.get("v3")==null?"":val.get("v3"));//贫困户签字
//			bfcx_obj.put("bfcx_pic",val.get("path")==null?"":val.get("path"));//贫困户签字
//			jsonArray10.add(bfcx_obj);
//		}
//		response.getWriter().write(jsonArray10.toString());
//		response.getWriter().close();
//	}
//	/**
//	 * 查看贫困户的帮扶措施
//	 * @author chendong
//	 * @date 2016年8月5日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException
//	 */
//	@RequestMapping("getCx_6.do")
//	public void getCx_6(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		String pkid=request.getParameter("pkid");
//		//帮扶措施
//		JSONArray jsonArray9 =new JSONArray();
//		String people_sql = " select v1,v2,v3, ";
//		people_sql += " MAX(CASE v7 WHEN '2016' THEN v4 ELSE '' END ) v4_2016, ";
//		people_sql += " MAX(CASE v7 WHEN '2016' THEN v5 ELSE '' END ) v5_2016, ";
//		people_sql += " MAX(CASE v7 WHEN '2016' THEN v6 ELSE '' END ) v6_2016, ";
//		people_sql += " MAX(CASE v7 WHEN '2017' THEN v4 ELSE '' END ) v4_2017, ";
//		people_sql += " MAX(CASE v7 WHEN '2017' THEN v5 ELSE '' END ) v5_2017, ";
//		people_sql += " MAX(CASE v7 WHEN '2017' THEN v6 ELSE '' END ) v6_2017, ";
//		people_sql += " MAX(CASE v7 WHEN '2018' THEN v4 ELSE '' END ) v4_2018, ";
//		people_sql += " MAX(CASE v7 WHEN '2018' THEN v5 ELSE '' END ) v5_2018, ";
//		people_sql += " MAX(CASE v7 WHEN '2018' THEN v6 ELSE '' END ) v6_2018, ";
//		people_sql += " MAX(CASE v7 WHEN '2019' THEN v4 ELSE '' END ) v4_2019, ";
//		people_sql += " MAX(CASE v7 WHEN '2019' THEN v5 ELSE '' END ) v5_2019, ";
//		people_sql += " MAX(CASE v7 WHEN '2019' THEN v6 ELSE '' END ) v6_2019 ";
//		people_sql += " from da_help_tz_measures where da_household_id="+pkid+" group  by v1,v2,v3 ";
//		
//		List<Map> Patient_st_List = this.getBySqlMapper.findRecords(people_sql);
//		if(Patient_st_List.size()>0){
//			for(int i = 0;i<Patient_st_List.size();i++){
//				Map Patient_st_map = Patient_st_List.get(i);
//				JSONObject val = new JSONObject();
//				String v1 = "",v2 = "",v3 = "",v4_2016 = "",v5_2016 = "",v6_2016 = "";
//				String v4_2017 = "",v5_2017 = "",v6_2017 = "";
//				String v4_2018 = "",v5_2018 = "",v6_2018 = "";
//				String v4_2019 = "",v5_2019 = "",v6_2019 = "";
//				for (Object key : Patient_st_map.keySet()) {
//					
//					val.put(key, Patient_st_map.get(key));
//					
//					if(key.toString().equals("v1")){
//						v1 = Patient_st_map.get(key).toString();
//					}
//					if(key.toString().equals("v2")){
//						v2 = Patient_st_map.get(key).toString();
//					}
//					if(key.toString().equals("v3")){
//						v3 = Patient_st_map.get(key).toString();
//					}
//					
//					if(key.toString().equals("v4_2016")){
//						v4_2016 = Patient_st_map.get(key).toString();
//					}
//					if(key.toString().equals("v5_2016")){
//						v5_2016 = Patient_st_map.get(key).toString();
//					}
//					if(key.toString().equals("v6_2016")){
//						v6_2016 = Patient_st_map.get(key).toString();
//					}
//					
//					if(key.toString().equals("v4_2017")){
//						v4_2017 = Patient_st_map.get(key).toString();
//					}
//					if(key.toString().equals("v5_2017")){
//						v5_2017 = Patient_st_map.get(key).toString();
//					}
//					if(key.toString().equals("v6_2017")){
//						v6_2017 = Patient_st_map.get(key).toString();
//					}
//					
//					if(key.toString().equals("v4_2018")){
//						v4_2018 = Patient_st_map.get(key).toString();
//					}
//					if(key.toString().equals("v5_2018")){
//						v5_2018 = Patient_st_map.get(key).toString();
//					}
//					if(key.toString().equals("v6_2018")){
//						v6_2018 = Patient_st_map.get(key).toString();
//					}
//					
//					if(key.toString().equals("v4_2019")){
//						v4_2019 = Patient_st_map.get(key).toString();
//					}
//					if(key.toString().equals("v5_2019")){
//						v5_2019 = Patient_st_map.get(key).toString();
//					}
//					if(key.toString().equals("v6_2019")){
//						v6_2019 = Patient_st_map.get(key).toString();
//					}
//				}
//				jsonArray9.add(val);
//			}
//			
//		}
//		response.getWriter().write(jsonArray9.toString());
//		response.getWriter().close();
//	}
//	/**
//	 * 查看帮扶后收支情况
//	 * @author chendong
//	 * @date 2016年8月5日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException
//	 */
//	@RequestMapping("getCx_7.do")
//	public void getCx_7(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		String pkid=request.getParameter("pkid");
//		//帮扶后收入
//		JSONArray jsonArray11 =new JSONArray();
//		String dqsrh_sql="SELECT * FROM da_helpback_income where da_household_id="+pkid;
//		List<Map> dqsrh_list=getBySqlMapper.findRecords(dqsrh_sql);
//		for(Map val:dqsrh_list){
//			JSONObject dqsrh_obj=new JSONObject ();
//			dqsrh_obj.put("dqsrh_pkid",val.get("pkid")==null?"":val.get("pkid"));
//			dqsrh_obj.put("dqsrh_v1",val.get("v1")==null?"":val.get("v1"));//农业明细
//			dqsrh_obj.put("dqsrh_v2",val.get("v2")==null?"":val.get("v2"));//农业金额
//			dqsrh_obj.put("dqsrh_v3",val.get("v3")==null?"":val.get("v3"));//畜牧业明细
//			dqsrh_obj.put("dqsrh_v4",val.get("v4")==null?"":val.get("v4"));//畜牧业金额
//			dqsrh_obj.put("dqsrh_v5",val.get("v5")==null?"":val.get("v5"));//林业明细
//			dqsrh_obj.put("dqsrh_v6",val.get("v6")==null?"":val.get("v6"));//林业金额
//			dqsrh_obj.put("dqsrh_v7",val.get("v7")==null?"":val.get("v7"));//其他明细
//			dqsrh_obj.put("dqsrh_v8",val.get("v8")==null?"":val.get("v8"));//其他金额
//			dqsrh_obj.put("dqsrh_v9",val.get("v9")==null?"":val.get("v9"));//小计明细
//			dqsrh_obj.put("dqsrh_v10",val.get("v10")==null?"":val.get("v10"));//小计金额
//			dqsrh_obj.put("dqsrh_v11",val.get("v11")==null?"":val.get("v11"));//生态补贴明细
//			dqsrh_obj.put("dqsrh_v12",val.get("v12")==null?"":val.get("v12"));//生态补贴金额
//			dqsrh_obj.put("dqsrh_v13",val.get("v13")==null?"":val.get("v13"));//养老金明细
//			dqsrh_obj.put("dqsrh_v14",val.get("v14")==null?"":val.get("v14"));//养老金金额	
//			dqsrh_obj.put("dqsrh_v15",val.get("v15")==null?"":val.get("v15"));//低保补贴明细
//			dqsrh_obj.put("dqsrh_v16",val.get("v16")==null?"":val.get("v16"));//低保补贴金额
//			dqsrh_obj.put("dqsrh_v17",val.get("v17")==null?"":val.get("v17"));//燃煤补贴明细
//			dqsrh_obj.put("dqsrh_v18",val.get("v18")==null?"":val.get("v18"));//燃煤补贴金额
//			dqsrh_obj.put("dqsrh_v19",val.get("v19")==null?"":val.get("v19"));//其他明细
//			dqsrh_obj.put("dqsrh_v20",val.get("v20")==null?"":val.get("v20"));//其他金额
//			dqsrh_obj.put("dqsrh_v21",val.get("v21")==null?"":val.get("v21"));//小计明细
//			dqsrh_obj.put("dqsrh_v22",val.get("v22")==null?"":val.get("v22"));//小计金额
//			dqsrh_obj.put("dqsrh_v23",val.get("v23")==null?"":val.get("v23"));//土地流转明细
//			dqsrh_obj.put("dqsrh_v24",val.get("v24")==null?"":val.get("v24"));//土地流转金额
//			dqsrh_obj.put("dqsrh_v25",val.get("v25")==null?"":val.get("v25"));//其他明细
//			dqsrh_obj.put("dqsrh_v26",val.get("v26")==null?"":val.get("v26"));//其他金额
//			dqsrh_obj.put("dqsrh_v27",val.get("v27")==null?"":val.get("v27"));//工资明细1
//			dqsrh_obj.put("dqsrh_v28",val.get("v28")==null?"":val.get("v28"));//工资金额1
//			dqsrh_obj.put("dqsrh_v29",val.get("v29")==null?"":val.get("v29"));//工资明细2
//			dqsrh_obj.put("dqsrh_v30",val.get("v30")==null?"":val.get("v30"));//工资金额2
//			dqsrh_obj.put("dqsrh_v31",val.get("v31")==null?"":val.get("v31"));//其他明细1
//			dqsrh_obj.put("dqsrh_v32",val.get("v32")==null?"":val.get("v32"));//其他金额1
//			dqsrh_obj.put("dqsrh_v33",val.get("v33")==null?"":val.get("v33"));//其他明细2
//			dqsrh_obj.put("dqsrh_v34",val.get("v34")==null?"":val.get("v34"));//其他金额2
//			dqsrh_obj.put("dqsrh_v35",val.get("v35")==null?"":val.get("v35"));//工资项目1
//			dqsrh_obj.put("dqsrh_v36",val.get("v36")==null?"":val.get("v36"));//工资项目2
//			dqsrh_obj.put("dqsrh_v37",val.get("v37")==null?"":val.get("v37"));//其他项目1
//			dqsrh_obj.put("dqsrh_v38",val.get("v38")==null?"":val.get("v38"));//其他项目2
//			dqsrh_obj.put("dqsrh_v39",val.get("v39")==null?"":val.get("v39"));//年总收入
//			jsonArray11.add(dqsrh_obj);
//		}
//		//帮扶后支出
//		JSONArray jsonArray12 =new JSONArray();
//		String dqzch_sql="SELECT * FROM da_helpback_expenditure where da_household_id="+pkid;
//		List<Map> dqzch_list=getBySqlMapper.findRecords(dqzch_sql);
//		for(Map val:dqzch_list){
//			JSONObject dqzch_obj=new JSONObject ();
//			dqzch_obj.put("dqzch_pkid",val.get("pkid")==null?"":val.get("pkid"));
//			dqzch_obj.put("dqzch_v1",val.get("v1")==null?"":val.get("v1"));//农资明细
//			dqzch_obj.put("dqzch_v2",val.get("v2")==null?"":val.get("v2"));//农资金额
//			dqzch_obj.put("dqzch_v3",val.get("v3")==null?"":val.get("v3"));//固定资产折旧明细
//			dqzch_obj.put("dqzch_v4",val.get("v4")==null?"":val.get("v4"));//固定资产折旧金额
//			dqzch_obj.put("dqzch_v5",val.get("v5")==null?"":val.get("v5"));//水电明细
//			dqzch_obj.put("dqzch_v6",val.get("v6")==null?"":val.get("v6"));//水电金额
//			dqzch_obj.put("dqzch_v7",val.get("v7")==null?"":val.get("v7"));//承包土地明细
//			dqzch_obj.put("dqzch_v8",val.get("v8")==null?"":val.get("v8"));//承包土地金额
//			dqzch_obj.put("dqzch_v9",val.get("v9")==null?"":val.get("v9"));//饲草料明细
//			dqzch_obj.put("dqzch_v10",val.get("v10")==null?"":val.get("v10"));//	饲草料金额
//			dqzch_obj.put("dqzch_v11",val.get("v11")==null?"":val.get("v11"));//	防疫明细
//			dqzch_obj.put("dqzch_v12",val.get("v12")==null?"":val.get("v12"));//防疫金额
//			dqzch_obj.put("dqzch_v13",val.get("v13")==null?"":val.get("v13"));//种畜明细
//			dqzch_obj.put("dqzch_v14",val.get("v14")==null?"":val.get("v14"));//种畜金额
//			dqzch_obj.put("dqzch_v15",val.get("v15")==null?"":val.get("v15"));//销售通讯明细
//			dqzch_obj.put("dqzch_v16",val.get("v16")==null?"":val.get("v16"));//销售通讯金额
//			dqzch_obj.put("dqzch_v17",val.get("v17")==null?"":val.get("v17"));//	借贷明细
//			dqzch_obj.put("dqzch_v18",val.get("v18")==null?"":val.get("v18"));//	借贷金额
//			dqzch_obj.put("dqzch_v19",val.get("v19")==null?"":val.get("v19"));//政策明细1
//			dqzch_obj.put("dqzch_v20",val.get("v20")==null?"":val.get("v20"));//政策金额1
//			dqzch_obj.put("dqzch_v21",val.get("v21")==null?"":val.get("v21"));//政策明细2
//			dqzch_obj.put("dqzch_v22",val.get("v22")==null?"":val.get("v22"));//	政策金额2
//			dqzch_obj.put("dqzch_v23",val.get("v23")==null?"":val.get("v23"));//	政策项目1
//			dqzch_obj.put("dqzch_v24",val.get("v24")==null?"":val.get("v24"));//政策项目2
//			dqzch_obj.put("dqzch_v25",val.get("v25")==null?"":val.get("v25"));//其他项目1
//			dqzch_obj.put("dqzch_v26",val.get("v26")==null?"":val.get("v26"));//其他明细1
//			dqzch_obj.put("dqzch_v27",val.get("v27")==null?"":val.get("v27"));//其他金额1
//			dqzch_obj.put("dqzch_v28",val.get("v28")==null?"":val.get("v28"));//	其他项目2
//			dqzch_obj.put("dqzch_v29",val.get("v29")==null?"":val.get("v29"));//其他明细2
//			dqzch_obj.put("dqzch_v30",val.get("v30")==null?"":val.get("v30"));//其他金额2
//			dqzch_obj.put("dqzch_v31",val.get("v31")==null?"":val.get("v31"));//年总支出
//			jsonArray12.add(dqzch_obj);
//		}
//		response.getWriter().write("{\"data11\":"+jsonArray11.toString()+"," +
//				"\"data12\":"+jsonArray12.toString()+"}");
//		response.getWriter().close();
//	}
//	/**
//	 * 查看易地搬迁
//	 * @author chendong
//	 * @date 2016年8月5日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException
//	 */
//	@RequestMapping("getCx_8.do")
//	public void getCx_8(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		String pkid=request.getParameter("pkid");
//		//异地搬迁
//		JSONArray jsonArray14 =new JSONArray();
//		String yd_sql="SELECT move_type,focus_info,dispersed_info,dispersed_address,dispersed_price,dispersed_agreement,v1,v2,v3, sf,path FROM  da_household_move a LEFT JOIN "+
//						"(SELECT v3 sf, da_household_id FROM da_life) b ON a.da_household_id=b.da_household_id LEFT JOIN "+
//						"(SELECT pic_pkid, group_concat(pic_path order by pic_path separator ',') path FROM da_pic where pic_type='6' AND pic_pkid="+pkid+") c ON"+
//						"  a.da_household_id=c.pic_pkid where a.da_household_id="+pkid+" GROUP BY a.da_household_id";
//		List<Map> yd_list=getBySqlMapper.findRecords(yd_sql);
//		JSONObject yd_obj=new JSONObject ();
//		if(yd_list.size()>0){
//			for(Map val:yd_list){
//				yd_obj.put("move_type",val.get("move_type")==null?"":val.get("move_type"));//集中安置或者分散安置
//				yd_obj.put("focus_info",val.get("focus_info")==null?"":val.get("focus_info"));//集中安置分类	
//				yd_obj.put("dispersed_info",val.get("dispersed_info")==null?"":val.get("dispersed_info"));//分散安置分类
//				yd_obj.put("dispersed_address",val.get("dispersed_address")==null?"":val.get("dispersed_address"));//房源地
//				
//				yd_obj.put("dispersed_price",val.get("dispersed_price")==null?"":val.get("dispersed_price"));//房价（万元）
//				yd_obj.put("dispersed_agreement",val.get("dispersed_agreement")==null?"":val.get("dispersed_agreement"));//与用工企业签订就业安置协议
//				yd_obj.put("yd_v1",val.get("v1")==null?"":val.get("v1"));//	搬迁方式（单选）
//				yd_obj.put("yd_v2",val.get("v2")==null?"":val.get("v2"));//安置地（单选）
//				
//				yd_obj.put("yd_v3",val.get("v3")==null?"":val.get("v3"));//搬迁可能存在的困难（可多选）
//				yd_obj.put("sf",val.get("sf")==null?"":val.get("sf"));//是否
//				if("".equals(val.get("path"))||val.get("path")==null){
//					yd_obj.put("path","");//安置地（单选）
//				}else{
//					yd_obj.put("path",val.get("path")==null?"":val.get("path"));//安置地（单选）
//				}
//				jsonArray14.add(yd_obj);
//			}
//		}
//		response.getWriter().write("{\"data14\":"+jsonArray14.toString()+"}");
//		response.getWriter().close();
//	}
//	/**
//	 * 走访、措施、成效，时间轴更新读取
//	 * @author chendong
//	 * @date 2016年8月5日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException
//	 */
//	@RequestMapping("getCx_9.do")
//	public void getCx_9(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		
//		String pkid = request.getParameter("pkid");
//		String str = request.getParameter("str");
//		JSONArray jsonArray13 =new JSONArray();
//		String sql = "select * from(";
//		
//		if(str.indexOf("走访情况")>1){
//			sql += "select pkid,v1,v2,v3,'da_help_visit' as da_type from da_help_visit where da_household_id="+pkid+" union all ";
//		}
//		
//		if(str.indexOf("帮扶措施")>1){
//			sql += "select pkid,v1,v2,v3,'da_help_measures' as da_type from da_help_measures where da_household_id="+pkid+" union all ";
//		}
//		
//		if(str.indexOf("帮扶成效")>1){
//			sql += "select pkid,v1,v2,v3,'da_help_results' as da_type from da_help_results where da_household_id="+pkid+" union all ";
//		}
//		
//		sql = sql.substring(0, sql.length()-10);
//		
//		sql += ") x order by v1 desc";
//		List<Map> sjz_list=getBySqlMapper.findRecords(sql);
//		for(Map val:sjz_list){
//			JSONObject sjz_obj=new JSONObject ();
//			sjz_obj.put("pkid",val.get("pkid")==null?"":val.get("pkid"));
//			sjz_obj.put("v1",val.get("v1")==null?"":val.get("v1"));
//			sjz_obj.put("v2",val.get("v2")==null?"":val.get("v2"));
//			sjz_obj.put("v3",val.get("v3")==null?"":val.get("v3"));
//			sjz_obj.put("da_type",val.get("da_type")==null?"":val.get("da_type"));
//			String results=val.get("da_type").toString();
//			
//			if(results.equals("da_help_visit")){
//				String hql="SELECT group_concat(pic_path order by pic_path separator ',') pie FROM da_pic WHERE pic_pkid="+val.get("pkid")+" AND pic_type=2";
//				List<Map> sjzf_list=getBySqlMapper.findRecords(hql);
//				if(null==sjzf_list.get(0)){
//					sjz_obj.put("pie","");
//				}else{
//					for(Map val1:sjzf_list){
//						sjz_obj.put("pie",val1.get("pie")==null?"":val1.get("pie"));
//					}
//				}
//			}
//			if(results.equals("da_help_measures")){
//				String hql="SELECT group_concat(pic_path order by pic_path separator ',') pie FROM da_pic WHERE pic_pkid="+val.get("pkid")+" AND pic_type=1";
//				List<Map> sjzf_list=getBySqlMapper.findRecords(hql);
//				if(null==sjzf_list.get(0)){
//					sjz_obj.put("pie","");
//				}else{
//					for(Map val1:sjzf_list){
//						sjz_obj.put("pie",val1.get("pie")==null?"":val1.get("pie"));
//					}
//				}
//				
//			}
//			if(results.equals("da_help_results")){
//				String hql="SELECT group_concat(pic_path order by pic_path separator ',') pie FROM da_pic WHERE pic_pkid="+val.get("pkid")+" AND pic_type=3";
//				List<Map> sjzf_list=getBySqlMapper.findRecords(hql);
//				if(null==sjzf_list.get(0)){
//					sjz_obj.put("pie","");
//				}else{
//					for(Map val1:sjzf_list){
//						sjz_obj.put("pie",val1.get("pie")==null?"":val1.get("pie"));
//					}
//				}
//			}
//		
//			jsonArray13.add(sjz_obj);
//		}
//		response.getWriter().write("{\"data13\":"+jsonArray13.toString()+"}");
//		response.getWriter().close();
//	}
	
}
