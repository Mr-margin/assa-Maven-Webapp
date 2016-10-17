package com.gistone.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.gistone.MyBatis.config.GetBySqlMapper;
/**
 * 系统管理-数据统计
 * @author chendong
 *
 */
@RestController
@RequestMapping
public class SH5_6_Controller{
	
	@Autowired
	private GetBySqlMapper getBySqlMapper;
	/**
	 * 国家标准数据统计
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getSStj_Country_Stat.do")
	public void getSStj_Country_Stat(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String shi_g = ""; //市
		String qu_g = ""; //旗区
		String sum_g = "";	//苏木乡
		String type = request.getParameter("type").trim(); //贫困人口类型
		String order=request.getParameter("order");	//正序
		String sort=request.getParameter("sort");	//倒序
		String qu_name = "";//获取旗区名称
		String sum_name = "";//获取苏木乡名称
		if(type.equals("国家级贫困人口")){
			if(request.getParameter("shi_g")!=null&&!request.getParameter("shi_g").equals("")){ //获取市的值
				shi_g = request.getParameter("shi_g").trim();
			}
			if(request.getParameter("qu_g")!=null&&!request.getParameter("qu_g").equals("请选择")){//旗区
				qu_g = request.getParameter("qu_g").trim();
			}
			if(request.getParameter("sum_g")!=null&&!request.getParameter("sum_g").equals("请选择")){//旗区
				sum_g = request.getParameter("sum_g").trim();
			}
		}else{
			if(request.getParameter("shi_s")!=null&&!request.getParameter("shi_s").equals("")){
				shi_g = request.getParameter("shi_s").trim();
			}
			if(request.getParameter("qu_s")!=null&&!request.getParameter("qu_s").equals("请选择")){//旗区
				qu_g = request.getParameter("qu_s").trim();
			}
			if(request.getParameter("sum_s")!=null&&!request.getParameter("sum_s").equals("请选择")){//旗区
				sum_g = request.getParameter("sum_s").trim();
			}
		}
		String pkid = ""; //的ID
		String ziduan = ""; //字段名称
		String cun_duyou = "";  //村级别的查询语句
		if(shi_g.equals("1")){//显示内蒙古
			pkid = shi_g;
			ziduan = "v0";
		}else{
			if(qu_g!=""){
				if(sum_g!=""){//显示选中的苏木乡
					pkid = sum_g;
					sum_name=request.getParameter("sum_name");//
					cun_duyou="and v2='"+sum_name+"'";//
					ziduan = "v3";
				}else{//显示选中的旗县
					pkid = qu_g;
					qu_name=request.getParameter("qu_name");//
					cun_duyou="and v1='"+qu_name+"'";//
					ziduan = "v2";
				}
			}else{//显示选中的市
				pkid = shi_g;
				ziduan = "v1";
			}
		}
		//只能显示全自治区
		pkid = "1";
		ziduan = "v0";
		
		String sql = "SELECT * from (select "+ziduan+" as b1,SUM(b2) as b2,SUM(b3) as b3, SUM(b4) as b4,SUM(b6)as b6,SUM(b7)as b7 ,SUM(b8) as b8,SUM(b9) as b9,SUM(b10) as b10,SUM(b11) as b11,SUM(b12) as b12,SUM(b13) as b13,b14 from da_statistics "
				 +" where b14='"+ type +"' "+ cun_duyou +" GROUP BY "+ziduan+",b14) b join sys_company c on b.b1=c.com_name";//
		if(sort==""||sort==null){
			sql += " where com_f_pkid="+pkid+"   and b2>0 order by b2 desc ";
		}else{
			sql += " where com_f_pkid="+pkid+"   and b2>0 order by "+sort+" "+order+" ";
		}
		List<Map> Metadata_g_List = this.getBySqlMapper.findRecords(sql);
		int total = Metadata_g_List.size();
		DecimalFormat df = new DecimalFormat("0.00");
		String str1;
		if(Metadata_g_List.size()>0){
			JSONArray jsa=new JSONArray();
			for(Map val:Metadata_g_List){
				JSONObject obj = new JSONObject ();
				if(val.get("B2")!=null){
					obj.put("b1",val.get("B1")==null?"":val.get("B1"));
					obj.put("b2",val.get("B2")==null?"":val.get("B2"));
					obj.put("b3",val.get("B3")==null?"<code>0</code>":getData(val.get("B2").toString(),val.get("B3").toString()));
					if("".equals(val.get("B4"))||val.get("B4")==null){
						obj.put("b4",val.get("B4")==null?"<code>0</code>":getData(val.get("B2").toString(),val.get("B4").toString()));
					}else{
						str1=df.format((Double.parseDouble(val.get("B4").toString()))/(Double.parseDouble(val.get("B2").toString()))*100)+"%";
						obj.put("b4",val.get("B4")==null?"<code>0</code>":getData(val.get("B2").toString(),val.get("B4").toString())+"("+str1+")"+"<div class=\"progress progress-mini\"><div style=\"width:"+str1+";\" class=\"progress-bar\"></div></div>");
					}
					if("".equals(val.get("B6"))||val.get("B6")==null){
						obj.put("b6",val.get("B6")==null?"<code>0</code>":getData(val.get("B2").toString(),val.get("B6").toString()));
					}else{
						str1=df.format((Double.parseDouble(val.get("B6").toString()))/(Double.parseDouble(val.get("B2").toString()))*100)+"%";
						obj.put("b6",val.get("B6")==null?"<code>0</code>":getData(val.get("B2").toString(),val.get("B6").toString())+"("+str1+")"+"<div class=\"progress progress-mini\"><div style=\"width:"+str1+";\" class=\"progress-bar\"></div></div>");
					}
					
					if("".equals(val.get("B7"))||val.get("B7")==null){
						obj.put("b7",val.get("B7")==null?"<code>0</code>":getData(val.get("B2").toString(),val.get("B7").toString()));
					}else{
						str1=df.format((Double.parseDouble(val.get("B7").toString()))/(Double.parseDouble(val.get("B2").toString()))*100)+"%";
						obj.put("b7",val.get("B7")==null?"<code>0</code>":getData(val.get("B2").toString(),val.get("B7").toString())+"("+str1+")"+"<div class=\"progress progress-mini\"><div style=\"width:"+str1+";\" class=\"progress-bar\"></div></div>");
					}
					if("".equals(val.get("B8"))||val.get("B8")==null){
						obj.put("b8",val.get("B8")==null?"<code>0</code>":getData(val.get("B2").toString(),val.get("B8").toString()));
					}else{
						str1=df.format((Double.parseDouble(val.get("B8").toString()))/(Double.parseDouble(val.get("B2").toString()))*100)+"%";
						obj.put("b8",val.get("B8")==null?"<code>0</code>":getData(val.get("B2").toString(),val.get("B8").toString())+"("+str1+")"+"<div class=\"progress progress-mini\"><div style=\"width:"+str1+";\" class=\"progress-bar\"></div></div>");
					}
					if("".equals(val.get("B9"))||val.get("B9")==null){
						obj.put("b9",val.get("B9")==null?"<code>0</code>":getData(val.get("B2").toString(),val.get("B9").toString()));
					}else{
						str1=df.format((Double.parseDouble(val.get("B9").toString()))/(Double.parseDouble(val.get("B2").toString()))*100)+"%";
						obj.put("b9",val.get("B9")==null?"<code>0</code>":getData(val.get("B2").toString(),val.get("B9").toString())+"("+str1+")"+"<div class=\"progress progress-mini\"><div style=\"width:"+str1+";\" class=\"progress-bar\"></div></div>");
					}
					if("".equals(val.get("B10"))||val.get("B10")==null){
						obj.put("b10",val.get("B10")==null?"<code>0</code>":getData(val.get("B2").toString(),val.get("B10").toString()));
					}else{
						str1=df.format((Double.parseDouble(val.get("B10").toString()))/(Double.parseDouble(val.get("B2").toString()))*100)+"%";
						obj.put("b10",val.get("B10")==null?"<code>0</code>":getData(val.get("B2").toString(),val.get("B10").toString())+"("+str1+")"+"<div class=\"progress progress-mini\"><div style=\"width:"+str1+";\" class=\"progress-bar\"></div></div>");
					}
					if("".equals(val.get("B11"))||val.get("B11")==null){
						obj.put("b11",val.get("B11")==null?"<code>0</code>":getData(val.get("B2").toString(),val.get("B11").toString()));
					}else{
						str1=df.format((Double.parseDouble(val.get("B11").toString()))/(Double.parseDouble(val.get("B2").toString()))*100)+"%";
						obj.put("b11",val.get("B11")==null?"<code>0</code>":getData(val.get("B2").toString(),val.get("B11").toString())+"("+str1+")"+"<div class=\"progress progress-mini\"><div style=\"width:"+str1+";\" class=\"progress-bar\"></div></div>");
					}
					if("".equals(val.get("B12"))||val.get("B12")==null){
						obj.put("b12",val.get("B12")==null?"<code>0</code>":getData(val.get("B2").toString(),val.get("B12").toString()));
					}else{
						str1=df.format((Double.parseDouble(val.get("B12").toString()))/(Double.parseDouble(val.get("B2").toString()))*100)+"%";
						obj.put("b12",val.get("B12")==null?"<code>0</code>":getData(val.get("B2").toString(),val.get("B12").toString())+"("+str1+")"+"<div class=\"progress progress-mini\"><div style=\"width:"+str1+";\" class=\"progress-bar\"></div></div>");
					}
					if("".equals(val.get("B13"))||val.get("B13")==null){
						obj.put("b13",val.get("B13")==null?"<code>0</code>":getData(val.get("B2").toString(),val.get("B13").toString()));
					}else{
						str1=df.format((Double.parseDouble(val.get("B13").toString()))/(Double.parseDouble(val.get("B2").toString()))*100)+"%";
						obj.put("b13",val.get("B13")==null?"<code>0</code>":getData(val.get("B2").toString(),val.get("B13").toString())+"("+str1+")"+"<div class=\"progress progress-mini\"><div style=\"width:"+str1+";\" class=\"progress-bar\"></div></div>");
					}
					jsa.add(obj);
				}
			}
			JSONObject json = new JSONObject();
			json.put("page", "");
			json.put("total", total);
			json.put("rows", jsa); //这里的 rows 和total 的key 是固定的 
			response.getWriter().write(json.toString());
		}
	}
	
	@RequestMapping("time_data.do")
	public void time_data(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String sql = "select b15 from da_statistics where pkid=1";
		List<Map> time_list = this.getBySqlMapper.findRecords(sql);
		String time= time_list.get(0).get("B15").toString();
		JSONObject json_time= new JSONObject();
		json_time.put("B15", time);
		response.getWriter().write(time);
	}
	
	public String getData(String b2, String str){
		if(Integer.parseInt(str)<Integer.parseInt(b2)){
			return "<code>"+str+"</code>";
		}else{
			return str;
		}
	}
	
	 /**
	 * @method 点击刷新按钮后，将数据库查询到的结果保存到另一张表中
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author 张晓翔
	 * @date 2016年8月16日下午5:53:14
	 */
	@RequestMapping("updata_xinbiao.do")
	public void updata_xinbiao(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String sfcg="0";//作为是否成功清除表da_statistics的判断依据
		Date now= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String time = dateFormat.format( now );//获取当前系统时间time
		String del_sql="truncate table da_statistics;";//清空表da_statistics
		try {
			this.getBySqlMapper.findRecords(del_sql);
			sfcg="1";//清除成功为1
		} catch (Exception e) {
			sfcg="0";//清除失败为0
		}
		if(sfcg.equals("1")){//如果清除成功，执行插入数据语句
			String insert_sql="INSERT INTO da_statistics(v0,v1,v2,v3,b2,b3,b4,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15) "
					+ " select t1.v2 v0, t1.v3 v1,t1.v4 v2,t1.v5 v3,b2,b3,b4,b6,b7,b8,b9,b10,b11,b12,b13,t1.sys_standard b14,'up_time' b15 from("
					+ " select v2,v3,v4,v5,COUNT(*) as b2,sum(v9) as b3,sys_standard from da_household group by v3,v4,v5,sys_standard) t1 "
					+ " left join (select v2,v3,v4,v5,COUNT(*) as b4 ,sys_standard from b4_t group by v2,v3,v4,v5,sys_standard) t4 on t1.v2=t4.v2 and t1.v3=t4.v3 and t1.v4=t4.v4 and t1.v5=t4.v5 and t1.sys_standard=t4.sys_standard "
					+ " left join (select v2,v3,v4,v5,COUNT(*) as b6 ,sys_standard from b6_t group by v2,v3,v4,v5,sys_standard) t6 on t1.v2=t6.v2 and t1.v3=t6.v3 and t1.v4=t6.v4 and t1.v5=t6.v5 and t1.sys_standard=t6.sys_standard "
					+ " left join (select v2,v3,v4,v5,COUNT(*) as b7 ,sys_standard from b7_t group by v2,v3,v4,v5,sys_standard) t7 on t1.v2=t7.v2 and t1.v3=t7.v3 and t1.v4=t7.v4 and t1.v5=t7.v5 and t1.sys_standard=t7.sys_standard "
					+ " left join (select v2,v3,v4,v5,COUNT(*) as b8 ,sys_standard from b8_t group by v2,v3,v4,v5,sys_standard) t8 on t1.v2=t8.v2 and t1.v3=t8.v3 and t1.v4=t8.v4 and t1.v5=t8.v5 and t1.sys_standard=t8.sys_standard "
					+ " left join (select v2,v3,v4,v5,COUNT(*) as b9 ,sys_standard from b9_t group by v2,v3,v4,v5,sys_standard) t9 on t1.v2=t9.v2 and t1.v3=t9.v3 and t1.v4=t9.v4 and t1.v5=t9.v5  and t1.sys_standard=t9.sys_standard "
					+ " left join (select v2,v3,v4,v5,COUNT(*) as b10 ,sys_standard from b10_t group by v2,v3,v4,v5,sys_standard) t10 on t1.v2=t10.v2 and t1.v3=t10.v3 and t1.v4=t10.v4 and t1.v5=t10.v5 and t1.sys_standard=t10.sys_standard "
					+ " left join (select v2,v3,v4,v5,COUNT(*) as b11 ,sys_standard from b11_t group by v2,v3,v4,v5,sys_standard) t11 on t1.v2=t11.v2 and t1.v3=t11.v3 and t1.v4=t11.v4 and t1.v5=t11.v5 and t1.sys_standard=t11.sys_standard "
					+ " left join (select v2,v3,v4,v5,COUNT(*) as b12 ,sys_standard from b12_t group by v2,v3,v4,v5,sys_standard) t12 on t1.v2=t12.v2 and t1.v3=t12.v3 and t1.v4=t12.v4 and t1.v5=t12.v5 and t1.sys_standard=t12.sys_standard "
					+ " left join (select v2,v3,v4,v5,COUNT(*) as b13 ,sys_standard from b13_t group by v2,v3,v4,v5,sys_standard) t13 on t1.v2=t13.v2 and t1.v3=t13.v3 and t1.v4=t13.v4 and t1.v5=t13.v5 and t1.sys_standard=t13.sys_standard";
			try {
				this.getBySqlMapper.findRecords(insert_sql);
				String uptime_sql="UPDATE da_statistics SET b15 ='"+time+"'";//插入da_statistics的b15为当前系统时间
				this.getBySqlMapper.update(uptime_sql);
				response.getWriter().write("1");//插入数据成功,返回1
			} catch (Exception e) {
				response.getWriter().write("0");//插入失败，返回0
			}
		}else{
			response.getWriter().write("0");//插入失败，返回0
		}
	}
	
	/**
	 * 帮扶人与单位数据统计
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getSStj_BangFu_Stat.do")
	public void getSStj_BangFu_Stat(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String order=request.getParameter("order"); //正序
		String sort=request.getParameter("sort");	//倒序
		
		String sql = " select t1.com_name as b1,b2,b3,b4,b5 from sys_company t1 ";
		sql += " left join (select sys_company_id,count(*) as b2 from sys_personal t1 join da_company t2 on t1.da_company_id=t2.pkid group by sys_company_id) t2 ";
		sql += " on t1.pkid=t2.sys_company_id LEFT JOIN (select sys_company_id,count(*) as b3 from da_company t1 ";
		sql += " join (select t1.pkid,t1.da_company_id from sys_personal t1 join sys_personal_household_many t2 on t1.pkid=t2.sys_personal_id group by t1.pkid,t1.da_company_id ";
		sql += " ) t2 on t1.pkid=t2.da_company_id group by sys_company_id) t3 on t1.pkid=t3.sys_company_id ";
		sql += " LEFT JOIN (select sys_company_id,count(*) as b4 from da_company group by sys_company_id) t4 on t1.pkid=t4.sys_company_id ";
		sql += " LEFT JOIN (select sys_company_id,count(*) as b5 from da_company where v5 is not null and v5 !='' group by sys_company_id) t5 on t1.pkid=t5.sys_company_id ";
		if(sort==null||sort==""){
			sql += " where com_f_pkid=1  order by b2 asc ";
		}else{
			sql += " where com_f_pkid=1  order by "+sort+" "+order+" ";
		}
		DecimalFormat df = new DecimalFormat("0.00");
		List<Map> Metadata_g_List = this.getBySqlMapper.findRecords(sql);
		int total = Metadata_g_List.size();
		String str1;
		if(Metadata_g_List.size()>0){
			JSONArray jsa=new JSONArray();
			for(Map val:Metadata_g_List){
				JSONObject obj=new JSONObject ();
				obj.put("b1",val.get("B1")==null?"":val.get("B1"));
				obj.put("b2",val.get("B2")==null?"<code>0</code>":val.get("B2"));
				if("".equals(val.get("B3"))||val.get("B3")==null){
					obj.put("b3",val.get("B3")==null?"<code>0</code>":getData(val.get("B2").toString(),val.get("B3").toString()));
				}else{
					str1=df.format((Double.parseDouble(val.get("B3").toString()))/(Double.parseDouble(val.get("B2").toString()))*100)+"%";
					obj.put("b3",val.get("B3")==null?"<code>0</code>":getData(val.get("B2").toString(),val.get("B3").toString())+"("+str1+")"+"<div class=\"progress progress-mini\"><div style=\"width:"+str1+";\" class=\"progress-bar\"></div></div>");
				}
					obj.put("b4",val.get("B4")==null?"<code>0</code>":val.get("B4"));
				if("".equals(val.get("B5"))||val.get("B5")==null){
					obj.put("b5",val.get("B5")==null?"<code>0</code>":getData(val.get("B2").toString(),val.get("B5").toString()));
				}else{
					str1=df.format((Double.parseDouble(val.get("b5").toString()))/(Double.parseDouble(val.get("B4").toString()))*100)+"%";
					obj.put("b5",val.get("B5")==null?"<code>0</code>":getData(val.get("B4").toString(),val.get("B5").toString())+"("+str1+")"+"<div class=\"progress progress-mini\"><div style=\"width:"+str1+";\" class=\"progress-bar\"></div></div>");
				}
				jsa.add(obj);
			}
			JSONObject json = new JSONObject();
			json.put("page", "");
			json.put("total", total);
			json.put("rows", jsa); //这里的 rows 和total 的key 是固定的 
			response.getWriter().write(json.toString());
			response.getWriter().close();
		}
	}
}
