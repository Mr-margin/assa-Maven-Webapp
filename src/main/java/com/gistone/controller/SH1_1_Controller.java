package com.gistone.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gistone.MyBatis.config.GetBySqlMapper;
/**
 * 扶贫手册—扶贫手册录入
 * @author chendong
 *
 */
@RestController
@RequestMapping
public class SH1_1_Controller{
	
//	@Autowired
//	private GetBySqlMapper getBySqlMapper;
//	/**
//	 * 扶贫手册录入的默认表单
//	 * @author chendong
//	 * @date 2016年8月3日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException 
//	 */
//	@RequestMapping("getInput_1.do")
//	public void getInput_1(HttpServletRequest request,HttpServletResponse response) throws IOException{
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
//
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
//					if(key.toString().equals("SYS_STANDARD")){
//						if(Patient_st_map.get(key)!=null&&!Patient_st_map.get(key).equals("")){
//							val.put(key, Patient_st_map.get(key).toString().substring(0, 2));
//						}
//					}
//					
//					if(key.toString().equals("PKID")){
//						val.put("jbqk", "<button  type=\"button\" class=\"btn btn-primary btn-xs jbqk\" onclick=\"jbqk("+Patient_st_map.get(key)+");\"><i class=\"fa fa-pencil\"></i> 编辑 </button>");
//						val.put("bfdwr", "<button  type=\"button\" class=\"btn btn-primary btn-xs bfdwr\" onclick=\"bfdwr("+Patient_st_map.get(key)+");\"><i class=\"fa fa-pencil\"></i> 编辑 </button>");
//						val.put("bfcs", "<button  type=\"button\" class=\"btn btn-primary btn-xs bfcs\" onclick=\"bfcs("+Patient_st_map.get(key)+");\"><i class=\"fa fa-pencil\"></i> 编辑 </button>");
//						val.put("zfqk", "<button type=\"button\" class=\"btn btn-primary btn-xs zfqk\" onclick=\"zfqk("+Patient_st_map.get(key)+");\"><i class=\"fa fa-pencil\"></i> 编辑 </button>");
//						val.put("bfcx", "<button type=\"button\" class=\"btn btn-primary btn-xs bfcx\" onclick=\"bfcx("+Patient_st_map.get(key)+");\"><i class=\"fa fa-pencil\"></i> 编辑 </button>");
//						val.put("bfhsz", "<button  type=\"button\" class=\"btn btn-primary btn-xs bfhsz\" onclick=\"bfhsz("+Patient_st_map.get(key)+");\"><i class=\"fa fa-pencil\"></i> 编辑 </button>");
//						val.put("dqsz", "<button  type=\"button\" class=\"btn btn-primary btn-xs dqsz\" onclick=\"dqsz("+Patient_st_map.get(key)+");\"><i class=\"fa fa-pencil\"></i> 编辑 </button>");
//					}
//				}
//				jsa.add(val);
//			}
//			
//			JSONObject json = new JSONObject();
//			json.put("page", page);
//			json.put("total", total);
//			json.put("rows", jsa); //这里的 rows 和total 的key 是固定的 
//
//			response.getWriter().write(json.toString());
//		}
//	}
//	/**
//	 * 添加贫困户
//	 * @author chendong
//	 * @date 2016年8月3日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException 
//	 */
//	@RequestMapping("getInput_2.do")
//	public void getInput_2(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		String qx=request.getParameter("qx");
//		String xaing=request.getParameter("xaing");
//		String cun=request.getParameter("cun");
//		String huname=request.getParameter("huname");
//		
//		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");  
//        String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000);
//        int da_household_id = 0;
//        try{
//        	String add_sql = "insert into da_household(v2,v3,v4,v5,v6,v9) values('"+newFileName+"','"+qx+"','"+xaing+"','"+cun+"','"+huname+"','1')";
//    		this.getBySqlMapper.insert(add_sql);//户主信息
//    		
//    		//取得户主的ID
//    		String hou_sql = "select pkid from da_household where v2='"+newFileName+"'";
//    		da_household_id = this.getBySqlMapper.findrows(hou_sql);
//    		
//    		//户主关联下的所有表，均需要增加记录
//    		String sql_1 = "insert into da_household_basic(da_household_id) values('"+da_household_id+"')";
//    		this.getBySqlMapper.insert(sql_1);//户主信息扩展
//    		
//    		
//    		String sql_2 = "insert into da_production(da_household_id) values('"+da_household_id+"')";
//    		this.getBySqlMapper.insert(sql_2);//生产条件
//    		
//    		String sql_3 = "insert into da_life(da_household_id) values('"+da_household_id+"')";
//    		this.getBySqlMapper.insert(sql_3);//生活条件
//    		
//    		String sql_4 = "insert into da_current_income(da_household_id) values('"+da_household_id+"')";
//    		this.getBySqlMapper.insert(sql_4);//当前收入情况
//    		
//    		String sql_5 = "insert into da_current_expenditure(da_household_id) values('"+da_household_id+"')";
//    		this.getBySqlMapper.insert(sql_5);//当前支出情况
//    		
//    		String sql_6 = "insert into da_help_info(da_household_id) values('"+da_household_id+"')";
//    		this.getBySqlMapper.insert(sql_6);//帮扶目标计划等内容
//    		
//    		String sql_9 = "insert into da_helpback_income(da_household_id) values('"+da_household_id+"')";
//    		this.getBySqlMapper.insert(sql_9);//帮扶后收入情况
//    		
//    		String sql_10 = "insert into da_helpback_expenditure(da_household_id) values('"+da_household_id+"')";
//    		this.getBySqlMapper.insert(sql_10);//帮扶后支出情况
//    		
//    		
//    		HttpSession session = request.getSession();
//			JSONObject json = new JSONObject();
//			if(session.getAttribute("Login_map")!=null){//验证session不为空
//				Map<String,String> Login_map = (Map)session.getAttribute("Login_map");//用户的user表内容
//				SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
//				String hql1="insert into da_record(record_table,record_pkid,record_type,record_p_t,record_phone,record_name,record_time,record_mou_1,record_mou_2)"+
//						" VALUES ('da_household',"+da_household_id+",'添加',2,'','"+Login_map.get("col_account")+"','"+simpleDate.format(new Date())+"','扶贫手册','新增户主')";
//				this.getBySqlMapper.insert(hql1);
//			}
//    		
//    		response.getWriter().write("1");
//        }catch(Exception e){
//    		
//    		String hou_sql = "delete from da_household where pkid='"+da_household_id+"'";
//    		this.getBySqlMapper.delete(hou_sql);
//    		
//    		String sql_1 = "delete from da_household_basic where da_household_id='"+da_household_id+"'";
//    		this.getBySqlMapper.delete(sql_1);//户主信息扩展
//    		
//    		String sql_2 = "delete from da_production where da_household_id='"+da_household_id+"'";
//    		this.getBySqlMapper.delete(sql_2);//生产条件
//    		
//    		String sql_3 = "delete from da_life where da_household_id='"+da_household_id+"'";
//    		this.getBySqlMapper.delete(sql_3);//生活条件
//    		
//    		String sql_4 = "delete from da_current_income where da_household_id='"+da_household_id+"'";
//    		this.getBySqlMapper.delete(sql_4);//当前收入情况
//    		
//    		String sql_5 = "delete from da_current_expenditure where da_household_id='"+da_household_id+"'";
//    		this.getBySqlMapper.delete(sql_5);//当前支出情况
//    		
//    		String sql_6 = "delete from da_help_info where da_household_id='"+da_household_id+"'";
//    		this.getBySqlMapper.delete(sql_6);//帮扶目标计划等内容
//    		
//    		String sql_9 = "delete from da_helpback_income where da_household_id='"+da_household_id+"'";
//    		this.getBySqlMapper.delete(sql_9);//帮扶后收入情况
//    		
//    		String sql_10 = "delete from da_helpback_expenditure where da_household_id='"+da_household_id+"'";
//    		this.getBySqlMapper.delete(sql_10);//帮扶后支出情况
//    		
//        	response.getWriter().write("0");
//        }
//	}
//	/**
//	 * 删除贫困户信息
//	 * @author chendong
//	 * @date 2016年8月3日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException 
//	 */
//	@RequestMapping("getInput_3.do")
//	public void getInput_3(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		String str = request.getParameter("str");
//		
//		String hu_add_sql = "insert into da_household_del select * from da_household where pkid in("+str+")";
//		
//		String me_add_sql = "insert into da_member_del select * from da_member where da_household_id in("+str+")";
//		
//		String hu_del_sql = "delete from da_household where pkid in("+str+")";
//		
//		String me_del_sql = "delete from da_member where da_household_id in("+str+")";
//		
//		try{
//			
//			this.getBySqlMapper.insert(hu_add_sql);
//			this.getBySqlMapper.insert(me_add_sql);
//			this.getBySqlMapper.delete(hu_del_sql);
//			this.getBySqlMapper.delete(me_del_sql);
//			
//			HttpSession session = request.getSession();
//			JSONObject json = new JSONObject();
//			if(session.getAttribute("Login_map")!=null){//验证session不为空
//				Map<String,String> Login_map = (Map)session.getAttribute("Login_map");//用户的user表内容
//				SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
//				
//				if(str.indexOf(",")>-1){
//					String val[] = str.split(",");
//					for(int i = 0;i<val.length;i++){
//						String hql1="insert into da_record(record_table,record_pkid,record_type,record_p_t,record_phone,record_name,record_time,record_mou_1,record_mou_2)"+
//								" VALUES ('da_household_del',"+val[i]+",'删除',2,'','"+Login_map.get("col_account")+"','"+simpleDate.format(new Date())+"','扶贫手册','删除贫困户')";
//						this.getBySqlMapper.insert(hql1);
//					}
//				}else{
//					String hql1="insert into da_record(record_table,record_pkid,record_type,record_p_t,record_phone,record_name,record_time,record_mou_1,record_mou_2)"+
//							" VALUES ('da_household_del',"+str+",'删除',2,'','"+Login_map.get("col_account")+"','"+simpleDate.format(new Date())+"','扶贫手册','删除贫困户')";
//					this.getBySqlMapper.insert(hql1);
//				}
//				
//			}
//			
//			response.getWriter().write("1");
//		}catch(Exception e){
//			response.getWriter().write("0");
//		}
//	}
//	/**
//	 * 保存户主信息
//	 * @author chendong
//	 * @date 2016年8月3日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException 
//	 */
//	@RequestMapping("getInput_4.do")
//	public void getInput_4(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		String pkid = request.getParameter("pkid");
//		String form_val = request.getParameter("form_val");
//		JSONObject form_json = JSONObject.fromObject(form_val);//表单数据
//		String where = "";
//		String jiatingwhere = "";
//		if(form_json.get("v6")!=null&&!form_json.get("v6").equals("")){//必填项，判断为空的时候不修改数据库
//			where += "V6='"+form_json.get("v6")+"',";
//		}
//		if(form_json.get("v7")!=null&&!form_json.get("v7").equals("")){//下拉框，一定有值，但是要筛除“请选择”
//			if(form_json.get("v7").equals("请选择")){
//				where += "V7='',";
//			}else{
//				where += "V7='"+form_json.get("v7")+"',";
//			}
//		}
//		if(form_json.get("v11")!=null&&!form_json.get("v11").equals("")){//下拉框，一定有值，但是要筛除“请选择”
//			if(form_json.get("v11").equals("请选择")){
//				where += "V11='',";
//			}else{
//				where += "V11='"+form_json.get("v11")+"',";
//			}
//		}
//		
//		if(form_json.get("v12")!=null&&!form_json.get("v12").equals("")){//下拉框，一定有值，但是要筛除“请选择”
//			if(form_json.get("v12").equals("请选择")){
//				where += "V12='',";
//			}else{
//				where += "V12='"+form_json.get("v12")+"',";
//			}
//		}
//		if(form_json.get("v13")!=null&&!form_json.get("v13").equals("")){//下拉框，一定有值，但是要筛除“请选择”
//			if(form_json.get("v13").equals("请选择")){
//				where += "V13='',";
//			}else{
//				where += "V13='"+form_json.get("v13")+"',";
//			}
//		}
//		if(form_json.get("v14")!=null&&!form_json.get("v14").equals("")){//下拉框，一定有值，但是要筛除“请选择”
//			if(form_json.get("v14").equals("请选择")){
//				where += "V14='',";
//			}else{
//				where += "V14='"+form_json.get("v14")+"',";
//			}
//		}
//		if(form_json.get("v15")!=null&&!form_json.get("v15").equals("")){//下拉框，一定有值，但是要筛除“请选择”
//			if(form_json.get("v15").equals("请选择")){
//				where += "V15='',";
//			}else{
//				where += "V15='"+form_json.get("v15")+"',";
//			}
//		}
//		if(form_json.get("v28")!=null&&!form_json.get("v28").equals("")){//下拉框，一定有值，但是要筛除“请选择”
//			if(form_json.get("v28").equals("请选择")){
//				where += "V28='',";
//			}else{
//				where += "V28='"+form_json.get("v28")+"',";
//			}
//		}
//		if(form_json.get("v18")!=null&&!form_json.get("v18").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V18='"+form_json.get("v18")+"',";
//		}else{
//			where += "V18='',";
//		}
//		if(form_json.get("v19")!=null&&!form_json.get("v19").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V19='"+form_json.get("v19")+"',";
//		}else{
//			where += "V19='',";
//		}
//		
//		
//		if(form_json.get("v25")!=null&&!form_json.get("v25").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V25='"+form_json.get("v25")+"',";
//			jiatingwhere += "V25='"+form_json.get("v25")+"',";
//		}else{
//			where += "V25='',";
//			jiatingwhere += "V25='',";
//		}
//		
//		if(form_json.get("v26")!=null&&!form_json.get("v26").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V26='"+form_json.get("v26")+"',";
//			jiatingwhere += "V26='"+form_json.get("v26")+"',";
//		}else{
//			where += "V26='',";
//			jiatingwhere += "V26='',";
//		}
//		if(form_json.get("v27")!=null&&!form_json.get("v27").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V27='"+form_json.get("v27")+"',";
//			jiatingwhere += "V27='"+form_json.get("v27")+"',";
//		}else{
//			where += "V27='',";
//			jiatingwhere += "V27='',";
//		}
//		if(form_json.get("v17")!=null&&!form_json.get("v17").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V17='"+form_json.get("v17")+"',";
//		}else{
//			where += "V17='',";
//		}
//		if(form_json.get("v16")!=null&&!form_json.get("v16").equals("")){//下拉框，一定有值，但是要筛除“请选择”
//			if(form_json.get("v16").equals("请选择")){
//				where += "V16='',";
//			}else{
//				where += "V16='"+form_json.get("v16")+"',";
//			}
//		}
//		
//		if(form_json.get("v8")!=null&&!form_json.get("v8").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V8='"+form_json.get("v8")+"',";
//		}else{
//			where += "V8='',";
//		}
//		if(form_json.get("sys_standard")!=null&&!form_json.get("sys_standard").equals("")){//下拉框，一定有值，但是要筛除“请选择”
//			if(form_json.get("sys_standard").equals("请选择")){
//				where += "SYS_STANDARD='',";
//			}else{
//				where += "SYS_STANDARD='"+form_json.get("sys_standard")+"',";
//			}
//		}
//		if(form_json.get("v22")!=null&&!form_json.get("v22").equals("")){//下拉框，一定有值，但是要筛除“请选择”
//			if(form_json.get("v22").equals("请选择")){
//				where += "V22='',";
//				jiatingwhere += "V22='',";
//			}else{
//				where += "V22='"+form_json.get("v22")+"',";
//				jiatingwhere += "V22='"+form_json.get("v22")+"',";
//			}
//		}
//
//		if(form_json.get("v33")!=null&&!form_json.get("v33").equals("")){//复选框，不一定有值
//			if(form_json.get("v33").toString().indexOf(",")>-1){//多选
//				JSONArray jsonArray = JSONArray.fromObject(form_json.get("v33"));
//				String str = "";
//				for(int i = 0;i<jsonArray.size();i++){
//					str += jsonArray.getString(i)+",";
//				}
//				where += "V33='"+str.substring(0, str.length()-1)+"',";
//				jiatingwhere += "V33='"+str.substring(0, str.length()-1)+"',";
//			}else{//单选
//				where += "V33='"+form_json.get("v33")+"',";
//				jiatingwhere += "V33='"+form_json.get("v33")+"',";
//			}
//		}else{
//			where += "V33='',";
//			jiatingwhere += "V33='',";
//		}
//		
//		if(form_json.get("v29")!=null&&!form_json.get("v29").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V29='"+form_json.get("v29")+"',";
//			jiatingwhere += "V29='"+form_json.get("v29")+"',";
//		}else{
//			where += "V29='',";
//			jiatingwhere += "V29='',";
//		}
//		if(form_json.get("v30")!=null&&!form_json.get("v30").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V30='"+form_json.get("v30")+"',";
//			jiatingwhere += "V30='"+form_json.get("v30")+"',";
//		}else{
//			where += "V30='',";
//			jiatingwhere += "V30='',";
//		}
//		if(form_json.get("v31")!=null&&!form_json.get("v31").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V31='"+form_json.get("v31")+"',";
//			jiatingwhere += "V31='"+form_json.get("v31")+"',";
//		}else{
//			where += "V31='',";
//			jiatingwhere += "V31='',";
//		}
//		if(form_json.get("v32")!=null&&!form_json.get("v32").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V32='"+form_json.get("v32")+"',";
//		}else{
//			where += "V32='',";
//		}
//		if(form_json.get("v23")!=null&&!form_json.get("v23").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V23='"+form_json.get("v23")+"',";
//			jiatingwhere += "V23='"+form_json.get("v23")+"',";
//		}else{
//			where += "V23='',";
//			jiatingwhere += "V23='',";
//		}
//		
//		String bas_where = "";
//		if(form_json.get("hz_zpyy")!=null&&!form_json.get("hz_zpyy").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			
//			String dest = "";
//	        if (form_json.get("hz_zpyy").toString()!=null) {
//	            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
//	            Matcher m = p.matcher(form_json.get("hz_zpyy").toString());
//	            dest = m.replaceAll("");
//	        }
//			
//			bas_where += "BASIC_EXPLAIN='"+dest+"',";
//		}else{
//			bas_where += "BASIC_EXPLAIN='',";
//		}
//		if(form_json.get("hz_jtzz")!=null&&!form_json.get("hz_jtzz").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			bas_where += "BASIC_ADDRESS='"+form_json.get("hz_jtzz")+"',";
//		}else{
//			bas_where += "BASIC_ADDRESS='',";
//		}
//		
//		if(where.length()>0){
//			where = where.substring(0, where.length()-1);
//		}
//		
//		if(jiatingwhere.length()>0){
//			jiatingwhere = jiatingwhere.substring(0, jiatingwhere.length()-1);
//		}
//		
//		if(bas_where.length()>0){
//			bas_where = bas_where.substring(0, bas_where.length()-1);
//		}
//		
//		String hu_sql = "update da_household set "+where+" where PKID="+pkid;
//		String jiating_sql = "update da_member set "+jiatingwhere+" where DA_HOUSEHOLD_ID="+pkid;
//		String hu_bas_sql = "update da_household_basic set "+bas_where+" where DA_HOUSEHOLD_ID="+pkid;
//		
////		System.out.println(hu_sql);
////		System.out.println(hu_bas_sql);
//		try{
//			this.getBySqlMapper.update(hu_sql);
//			this.getBySqlMapper.update(jiating_sql);
//			this.getBySqlMapper.update(hu_bas_sql);
//			
//			HttpSession session = request.getSession();
//			JSONObject json = new JSONObject();
//			if(session.getAttribute("Login_map")!=null){//验证session不为空
//				Map<String,String> Login_map = (Map)session.getAttribute("Login_map");//用户的user表内容
//				SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
//				String hql1="insert into da_record(record_table,record_pkid,record_type,record_p_t,record_phone,record_name,record_time,record_mou_1,record_mou_2)"+
//						" VALUES ('da_household',"+pkid+",'修改',2,'','"+Login_map.get("col_account")+"','"+simpleDate.format(new Date())+"','基本情况','户主情况')";
//				this.getBySqlMapper.insert(hql1);
//			}
//			
//			response.getWriter().write("1");
//		}catch (Exception e){
//			response.getWriter().write("0");
//		}
//	}
//	/**
//	 * 删除家庭成员
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException 
//	 */
//	@RequestMapping("getInput_5.do")
//	public void getInput_5(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		
//		String pkid = request.getParameter("pkid");
//		String huzhuid = request.getParameter("huzhuid");
//		try{
//			this.getBySqlMapper.insert("insert into da_member_del select * from da_member where pkid="+pkid);
//			this.getBySqlMapper.delete("delete from da_member where pkid="+pkid);
//			getjiatingchengyuan(huzhuid);//更新家庭成员人数
//			
//			HttpSession session = request.getSession();
//			JSONObject json = new JSONObject();
//			if(session.getAttribute("Login_map")!=null){//验证session不为空
//				Map<String,String> Login_map = (Map)session.getAttribute("Login_map");//用户的user表内容
//				SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
//				String hql1="insert into da_record(record_table,record_pkid,record_type,record_p_t,record_phone,record_name,record_time,record_mou_1,record_mou_2)"+
//						" VALUES ('da_member_del',"+pkid+",'删除',2,'','"+Login_map.get("col_account")+"','"+simpleDate.format(new Date())+"','扶贫手册','删除家庭成员')";
//				this.getBySqlMapper.insert(hql1);
//			}
//			response.getWriter().write("1");
//		}catch(Exception e){
//			response.getWriter().write("0");
//		}
//	}
//	/**
//	 * 添加新的家庭成员
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException
//	 */
//	@RequestMapping("getInput_6.do")
//	public void getInput_6(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		
//		String pkid = request.getParameter("pkid");
//		String name = request.getParameter("name");
//		
//		String hu_bas_sql = "insert into da_member(v1,v2,v3,v4,v5,v6,v9,v21,v23,v25,v26,v27,da_household_id) select v1,v2,v3,v4,v5,'"+name+"',v9,v21,v23,v25,v26,v27,pkid from da_household where pkid="+pkid;
//		try{
//			this.getBySqlMapper.insert(hu_bas_sql);
//			getjiatingchengyuan(pkid);//更新家庭成员人数
//			
//			String hou_sql = "select pkid from da_member where da_household_id="+pkid+" and v6='"+name+"'";
//    		int da_household_id = this.getBySqlMapper.findrows(hou_sql);
//			
//			HttpSession session = request.getSession();
//			JSONObject json = new JSONObject();
//			if(session.getAttribute("Login_map")!=null){//验证session不为空
//				Map<String,String> Login_map = (Map)session.getAttribute("Login_map");//用户的user表内容
//				SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
//				String hql1="insert into da_record(record_table,record_pkid,record_type,record_p_t,record_phone,record_name,record_time,record_mou_1,record_mou_2)"+
//						" VALUES ('da_member',"+da_household_id+",'添加',2,'','"+Login_map.get("col_account")+"','"+simpleDate.format(new Date())+"','扶贫手册','新增家庭成员')";
//				this.getBySqlMapper.insert(hql1);
//			}
//			response.getWriter().write("1");
//		}catch (Exception e){
//			response.getWriter().write("0");
//		}
//	}
//	/**
//	 * 保存家庭成员信息
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException 
//	 */
//	@RequestMapping("getInput_7.do")
//	public void getInput_7(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		
//		String form_val = request.getParameter("form_val");
//		JSONObject form_json = JSONObject.fromObject(form_val);//表单数据
//		
//		String where = "";
//		if(form_json.get("v6")!=null&&!form_json.get("v6").equals("")){//必填项，判断为空的时候不修改数据库
//			where += "V6='"+form_json.get("v6")+"',";
//		}
//		if(form_json.get("v7")!=null&&!form_json.get("v7").equals("")){//下拉框，一定有值，但是要筛除“请选择”
//			if(form_json.get("v7").equals("请选择")){
//				where += "V7='',";
//			}else{
//				where += "V7='"+form_json.get("v7")+"',";
//			}
//		}
//		if(form_json.get("v8")!=null&&!form_json.get("v8").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V8='"+form_json.get("v8")+"',";
//		}else{
//			where += "V8='',";
//		}
//		if(form_json.get("v10")!=null&&!form_json.get("v10").equals("")){//下拉框，一定有值，但是要筛除“请选择”
//			if(form_json.get("v10").equals("请选择")){
//				where += "V10='',";
//			}else{
//				where += "V10='"+form_json.get("v10")+"',";
//			}
//		}
//		if(form_json.get("v11")!=null&&!form_json.get("v11").equals("")){//下拉框，一定有值，但是要筛除“请选择”
//			if(form_json.get("v11").equals("请选择")){
//				where += "V11='',";
//			}else{
//				where += "V11='"+form_json.get("v11")+"',";
//			}
//		}
//		if(form_json.get("v12")!=null&&!form_json.get("v12").equals("")){//下拉框，一定有值，但是要筛除“请选择”
//			if(form_json.get("v12").equals("请选择")){
//				where += "V12='',";
//			}else{
//				where += "V12='"+form_json.get("v12")+"',";
//			}
//		}
//		if(form_json.get("v13")!=null&&!form_json.get("v13").equals("")){//下拉框，一定有值，但是要筛除“请选择”
//			if(form_json.get("v13").equals("请选择")){
//				where += "V13='',";
//			}else{
//				where += "V13='"+form_json.get("v13")+"',";
//			}
//		}
//		if(form_json.get("v14")!=null&&!form_json.get("v14").equals("")){//下拉框，一定有值，但是要筛除“请选择”
//			if(form_json.get("v14").equals("请选择")){
//				where += "V14='',";
//			}else{
//				where += "V14='"+form_json.get("v14")+"',";
//			}
//		}
//		if(form_json.get("v15")!=null&&!form_json.get("v15").equals("")){//下拉框，一定有值，但是要筛除“请选择”
//			if(form_json.get("v15").equals("请选择")){
//				where += "V15='',";
//			}else{
//				where += "V15='"+form_json.get("v15")+"',";
//			}
//		}
//		if(form_json.get("v17")!=null&&!form_json.get("v17").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V17='"+form_json.get("v17")+"',";
//		}else{
//			where += "V17='',";
//		}
//		if(form_json.get("v16")!=null&&!form_json.get("v16").equals("")){//下拉框，一定有值，但是要筛除“请选择”
//			if(form_json.get("v16").equals("请选择")){
//				where += "V16='',";
//			}else{
//				where += "V16='"+form_json.get("v16")+"',";
//			}
//		}
//		if(form_json.get("v18")!=null&&!form_json.get("v18").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V18='"+form_json.get("v18")+"',";
//		}else{
//			where += "V18='',";
//		}
//		if(form_json.get("v19")!=null&&!form_json.get("v19").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V19='"+form_json.get("v19")+"',";
//		}else{
//			where += "V19='',";
//		}
//		if(form_json.get("v32")!=null&&!form_json.get("v32").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V32='"+form_json.get("v32")+"',";
//		}else{
//			where += "V32='',";
//		}
//		if(form_json.get("v28")!=null&&!form_json.get("v28").equals("")){//下拉框，一定有值，但是要筛除“请选择”
//			if(form_json.get("v28").equals("请选择")){
//				where += "V28='',";
//			}else{
//				where += "V28='"+form_json.get("v28")+"',";
//			}
//		}
//		
//		if(where.length()>0){
//			where = where.substring(0, where.length()-1);
//		}
//		try{
//			this.getBySqlMapper.update("update da_member set "+where+" where pkid="+form_json.get("jiating_pkid"));
//			
//			HttpSession session = request.getSession();
//			JSONObject json = new JSONObject();
//			if(session.getAttribute("Login_map")!=null){//验证session不为空
//				Map<String,String> Login_map = (Map)session.getAttribute("Login_map");//用户的user表内容
//				SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
//				String hql1="insert into da_record(record_table,record_pkid,record_type,record_p_t,record_phone,record_name,record_time,record_mou_1,record_mou_2)"+
//						" VALUES ('da_member',"+form_json.get("jiating_pkid")+",'修改',2,'','"+Login_map.get("col_account")+"','"+simpleDate.format(new Date())+"','基本情况','家庭成员')";
//				this.getBySqlMapper.insert(hql1);
//			}
//			response.getWriter().write("1");
//		}catch (Exception e){
//			response.getWriter().write("0");
//		}
//	}
//	/**
//	 * 基本情况-修改贫困户之前
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException 
//	 */
//	@RequestMapping("getInput_8.do")
//	public void getInput_8(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		String pkid=request.getParameter("pkid");
//		
//		String sql="select * from da_household a left join da_household_basic b on a.pkid=b.da_household_id where a.pkid="+pkid;
//		List<Map> list=getBySqlMapper.findRecords(sql);
//		
//		JSONObject zong = new JSONObject ();
//		for(Map val:list){
//			JSONObject obj=new JSONObject ();
//			obj.put("pkid",val.get("PKID")==null?"":val.get("PKID"));
//			obj.put("v6", val.get("V6")==null?"":val.get("V6"));//姓名
//			obj.put("v7",val.get("V7")==null?"":val.get("V7"));//性别
//			obj.put("v8",val.get("V8")==null?"":val.get("V8"));//证件号码
//			obj.put("v11",val.get("V11")==null?"":val.get("V11"));//民族
//			
//			obj.put("v12",val.get("V12")==null?"":val.get("V12"));//文化程度
//			obj.put("v13",val.get("V13")==null?"":val.get("V13"));//在校状况
//			obj.put("v14",val.get("V14")==null?"":val.get("V14"));//健康状况
//			obj.put("v15",val.get("V15")==null?"":val.get("V15"));//劳动力
//			obj.put("v18",val.get("V18")==null?"":val.get("V18"));//是否参加新农合
//			obj.put("v19",val.get("V19")==null?"":val.get("V19"));//是否参加新型养老保险
//			obj.put("v28",val.get("V28")==null?"":val.get("V28"));//政治面貌
//			
//			obj.put("v25",val.get("V25")==null?"":val.get("V25"));//联系电话
//			obj.put("v26",val.get("V26")==null?"":val.get("V26"));//开户银行
//			obj.put("v27",val.get("V27")==null?"":val.get("V27"));//银行账号
//			obj.put("v16",val.get("V16")==null?"":val.get("V16"));//务工情况
//			obj.put("v17",val.get("V17")==null?"":val.get("V17"));//务工时间
//			obj.put("sys_standard",val.get("SYS_STANDARD")==null?"":val.get("SYS_STANDARD"));//识别标准
//			obj.put("v22",val.get("V22")==null?"":val.get("V22"));//贫苦户属性
//			obj.put("v23",val.get("V23")==null?"":val.get("V23"));//致贫原因
//			obj.put("v29",val.get("V29")==null?"":val.get("V29"));//是否军烈属
//			obj.put("v30",val.get("V30")==null?"":val.get("V30"));//是否独生子女户
//			obj.put("v31",val.get("V31")==null?"":val.get("V31"));//是否双女户
//			obj.put("v32",val.get("V32")==null?"":val.get("V32"));//是否现役军人
//			obj.put("v33",val.get("V33")==null?"":val.get("V33"));//其他致贫原因
//			obj.put("basic_address",val.get("BASIC_ADDRESS")==null?"":val.get("BASIC_ADDRESS"));//家庭住址
//			obj.put("basic_explain",val.get("BASIC_EXPLAIN")==null?"":val.get("BASIC_EXPLAIN"));//致贫原因说明
////			jsonArray.add(obj);
//			zong.put("huzhu", obj);//
//		}
//		
//		String count_st_sql = "select * from da_pic where pic_type=4 and pic_pkid="+pkid;
//		List<Map> list_pic = getBySqlMapper.findRecords(count_st_sql);
//		if(list_pic.size()>0){
//			Map st_map = list_pic.get(0);
//			zong.put("huzhu_pic", st_map.get("PIC_PATH")==null?"":st_map.get("PIC_PATH"));
//		}
//		
//		//家庭成员
//		String xian_sql="select * from da_member where da_household_id="+pkid;
//		List<Map> xian_list=getBySqlMapper.findRecords(xian_sql);
//		if(xian_list.size()>0){
//			JSONArray jsonArray =new JSONArray();
//			for(Map val:xian_list){
//				JSONObject xian_obj=new JSONObject ();
//				xian_obj.put("pkid",val.get("PKID")==null?"":val.get("PKID"));
//				xian_obj.put("v6",val.get("V6")==null?"":val.get("V6"));//姓名
//				xian_obj.put("v7",val.get("V7")==null?"":val.get("V7"));//性别
//				xian_obj.put("v8",val.get("V8")==null?"":val.get("V8"));//证件号码
//				xian_obj.put("v9",val.get("V9")==null?"":val.get("V9"));//人数
//				xian_obj.put("v10",val.get("V10")==null?"":val.get("V10"));//与户主关系
//				xian_obj.put("v11",val.get("V11")==null?"":val.get("V11"));//民族
//				xian_obj.put("v12",val.get("V12")==null?"":val.get("V12"));//文化程度
//				xian_obj.put("v13",val.get("V13")==null?"":val.get("V13"));//在校状况
//				xian_obj.put("v14",val.get("V14")==null?"":val.get("V14"));//健康状况
//				xian_obj.put("v15",val.get("V15")==null?"":val.get("V15"));//劳动力
//				xian_obj.put("v16",val.get("V16")==null?"":val.get("V16"));//务工状态
//				xian_obj.put("v17",val.get("V17")==null?"":val.get("V17"));//务工时间
//				xian_obj.put("v18",val.get("V18")==null?"":val.get("V18"));//是否参加新农合
//				xian_obj.put("v19",val.get("V19")==null?"":val.get("V19"));//是否参加新型养老保险
//				xian_obj.put("v20",val.get("V20")==null?"":val.get("V20"));//是否参加城镇职工基本养老保险	
//				xian_obj.put("v21",val.get("V21")==null?"":val.get("V21"));//脱贫属性
//				xian_obj.put("v28",val.get("V28")==null?"":val.get("V28"));//政治面貌
//				xian_obj.put("v32",val.get("V32")==null?"":val.get("V32"));//是否现役军人
//				
//				String jtcy_st_sql = "select * from da_pic where pic_type=5 and pic_pkid="+val.get("PKID");
//				List<Map> jtcy_pic = getBySqlMapper.findRecords(jtcy_st_sql);
//				if(jtcy_pic.size()>0){
//					Map st_map = jtcy_pic.get(0);
//					xian_obj.put("pic", st_map.get("PIC_PATH")==null?"":st_map.get("PIC_PATH"));
//				}
//				jsonArray.add(xian_obj);
//			}
//			zong.put("jaiting", jsonArray);
//		}
//		
//		//生产条件
//		String sc_sql="select * FROM da_production where da_household_id="+pkid;
//		List<Map> sc_list=getBySqlMapper.findRecords(sc_sql);
//		for(Map val:sc_list){
//			JSONObject sc_obj=new JSONObject ();
//			sc_obj.put("pkid",val.get("PKID")==null?"":val.get("PKID"));
//			sc_obj.put("v1",val.get("V1")==null?"":val.get("V1"));//耕地面积
//			sc_obj.put("v2",val.get("V2")==null?"":val.get("V2"));//水浇地面积
//			sc_obj.put("v3",val.get("V3")==null?"":val.get("V3"));//林地面积
//			sc_obj.put("v4",val.get("V4")==null?"":val.get("V4"));//退耕还林面积
//			sc_obj.put("v5",val.get("V5")==null?"":val.get("V5"));//草牧场面积
//			sc_obj.put("v6",val.get("V6")==null?"":val.get("V6"));//生产用房面积
//			sc_obj.put("v7",val.get("V7")==null?"":val.get("V7"));//其他
//			sc_obj.put("v8",val.get("V8")==null?"":val.get("V8"));//家禽
//			sc_obj.put("v9",val.get("V9")==null?"":val.get("V9"));//牛
//			sc_obj.put("v10",val.get("V10")==null?"":val.get("V10"));//羊
//			sc_obj.put("v11",val.get("V11")==null?"":val.get("V11"));//猪
//			sc_obj.put("v12",val.get("V12")==null?"":val.get("V12"));//其他
//			sc_obj.put("v13",val.get("V13")==null?"":val.get("V13"));//林果面积（亩）
//			sc_obj.put("v14",val.get("V14")==null?"":val.get("V14"));//水面面积（亩）
//			zong.put("shenchan", sc_obj);
//		}
//		
//		//生活条件
//		String sh_sql="SELECT * FROM da_life where da_household_id="+pkid;
//		List<Map> sh_list=getBySqlMapper.findRecords(sh_sql);
//		for(Map val:sh_list){
//			JSONObject sh_obj=new JSONObject ();
//			sh_obj.put("pkid",val.get("PKID")==null?"-":val.get("PKID"));
//			sh_obj.put("v1",val.get("V1")==null?"":val.get("V1"));//住房面积
//			sh_obj.put("v2",val.get("V2")==null?"":val.get("V2"));//是否危房
//			sh_obj.put("v3",val.get("V3")==null?"":val.get("V3"));//是否纳入易地扶贫搬迁
//			sh_obj.put("v4",val.get("V4")==null?"":val.get("V4"));//饮水情况
//			sh_obj.put("v5",val.get("V5")==null?"":val.get("V5"));//通电情况
//			sh_obj.put("v6",val.get("V6")==null?"":val.get("V6"));//入户路类型
//			sh_obj.put("v7",val.get("V7")==null?"":val.get("V7"));//与村主干路距离
//			sh_obj.put("v8",val.get("V8")==null?"":val.get("V8"));//饮水是否困难
//			sh_obj.put("v9",val.get("V9")==null?"":val.get("V9"));//饮水是否安全
//			sh_obj.put("v10",val.get("V10")==null?"":val.get("V10"));//主要燃料类型
//			sh_obj.put("v11",val.get("V11")==null?"":val.get("V11"));//是否加入农民专业合作社
//			sh_obj.put("v12",val.get("V12")==null?"":val.get("V12"));//有无卫生厕所
////			jsonArray.add(sh_obj);
//			zong.put("shenhuo", sh_obj);
//		}
//		
//		
//		//易地扶贫搬迁
//		String yidi_sql="SELECT * FROM da_household_move where da_household_id="+pkid;
//		List<Map> yidi_list=getBySqlMapper.findRecords(yidi_sql);
//		for(Map val:yidi_list){
//			JSONObject yidi_obj=new JSONObject ();
//			//yidi_obj.put("pkid",val.get("pkid")==null?"-":val.get("pkid"));
//			yidi_obj.put("move_type",val.get("MOVE_TYPE")==null?"":val.get("MOVE_TYPE"));
//			yidi_obj.put("focus_info",val.get("FOCUS_INFO")==null?"":val.get("FOCUS_INFO"));
//			yidi_obj.put("dispersed_info",val.get("DISPERSED_INFO")==null?"":val.get("DISPERSED_INFO"));
//			yidi_obj.put("dispersed_address",val.get("DISPERSED_ADDRESS")==null?"":val.get("DISPERSED_ADDRESS"));
//			yidi_obj.put("dispersed_price",val.get("DISPERSED_PRICE")==null?"":val.get("DISPERSED_PRICE"));
//			yidi_obj.put("dispersed_agreement",val.get("DISPERSED_AGREEMENT")==null?"":val.get("DISPERSED_AGREEMENT"));
//			yidi_obj.put("v1",val.get("V1")==null?"":val.get("V1"));
//			yidi_obj.put("v2",val.get("V2")==null?"":val.get("V2"));
//			yidi_obj.put("v3",val.get("V3")==null?"":val.get("V3"));
//			//System.out.println(yidi_obj.toString());
//			zong.put("yidi", yidi_obj);
//		}
//				
//		response.getWriter().write(zong.toString());
//		response.getWriter().close();
//	}
//	/**
//	 * 保存生产条件
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException 
//	 */
//	@RequestMapping("getInput_9.do")
//	public void getInput_9(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		
//		String pkid = request.getParameter("pkid");
//		String form_val = request.getParameter("form_val");
//		JSONObject form_json = JSONObject.fromObject(form_val);//表单数据
//		
//		String where = "";
//		if(form_json.get("v1")!=null&&!form_json.get("v1").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V1='"+form_json.get("v1")+"',";
//		}else{
//			where += "V1=null,";
//		}
//		if(form_json.get("v2")!=null&&!form_json.get("v2").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V2='"+form_json.get("v2")+"',";
//		}else{
//			where += "V2=null,";
//		}
//		if(form_json.get("v3")!=null&&!form_json.get("v3").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V3='"+form_json.get("v3")+"',";
//		}else{
//			where += "V3=null,";
//		}
//		if(form_json.get("v4")!=null&&!form_json.get("v4").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V4='"+form_json.get("v4")+"',";
//		}else{
//			where += "V4=null,";
//		}
//		if(form_json.get("v5")!=null&&!form_json.get("v5").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V5='"+form_json.get("v5")+"',";
//		}else{
//			where += "V5=null,";
//		}
//		if(form_json.get("v6")!=null&&!form_json.get("v6").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V6='"+form_json.get("v6")+"',";
//		}else{
//			where += "V6=null,";
//		}
//		if(form_json.get("v7")!=null&&!form_json.get("v7").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			String dest = "";
//	        if (form_json.get("v7").toString()!=null) {
//	            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
//	            Matcher m = p.matcher(form_json.get("v7").toString());
//	            dest = m.replaceAll("");
//	        }
//			where += "V7='"+dest+"',";
//		}else{
//			where += "V7='',";
//		}
//		if(form_json.get("v8")!=null&&!form_json.get("v8").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V8='"+form_json.get("v8")+"',";
//		}else{
//			where += "V8='',";
//		}
//		if(form_json.get("v9")!=null&&!form_json.get("v9").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V9='"+form_json.get("v9")+"',";
//		}else{
//			where += "V9='',";
//		}
//		if(form_json.get("v10")!=null&&!form_json.get("v10").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V10='"+form_json.get("v10")+"',";
//		}else{
//			where += "V10='',";
//		}
//		if(form_json.get("v11")!=null&&!form_json.get("v11").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V11='"+form_json.get("v11")+"',";
//		}else{
//			where += "V11='',";
//		}
//		if(form_json.get("v12")!=null&&!form_json.get("v12").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			String dest = "";
//	        if (form_json.get("v12").toString()!=null) {
//	            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
//	            Matcher m = p.matcher(form_json.get("v12").toString());
//	            dest = m.replaceAll("");
//	        }
//			where += "V12='"+dest+"',";
//		}else{
//			where += "V12='',";
//		}
//		if(form_json.get("v13")!=null&&!form_json.get("v13").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V13='"+form_json.get("v13")+"',";
//		}else{
//			where += "V13=null,";
//		}
//		if(form_json.get("v14")!=null&&!form_json.get("v14").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V14='"+form_json.get("v14")+"',";
//		}else{
//			where += "V14=null,";
//		}
//		
//		if(where.length()>0){
//			where = where.substring(0, where.length()-1);
//		}
//		
//		try{
//			this.getBySqlMapper.update("update da_production set "+where+" where da_household_id="+pkid);
//			
//			HttpSession session = request.getSession();
//			JSONObject json = new JSONObject();
//			if(session.getAttribute("Login_map")!=null){//验证session不为空
//				Map<String,String> Login_map = (Map)session.getAttribute("Login_map");//用户的user表内容
//				SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
//				String hql1="insert into da_record(record_table,record_pkid,record_type,record_p_t,record_phone,record_name,record_time,record_mou_1,record_mou_2)"+
//						" VALUES ('da_household',"+pkid+",'修改',2,'','"+Login_map.get("col_account")+"','"+simpleDate.format(new Date())+"','基本情况','生产条件')";
//				this.getBySqlMapper.insert(hql1);
//			}
//			response.getWriter().write("1");
//		}catch (Exception e){
//			response.getWriter().write("0");
//		}
//	}
//	/**
//	 * 保存生活条件
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException 
//	 */
//	@RequestMapping("getInput_10.do")
//	public void getInput_10(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		
//		String pkid = request.getParameter("pkid");
//		String form_val = request.getParameter("form_val");
//		JSONObject form_json = JSONObject.fromObject(form_val);//表单数据
//		
//		String where = "";
//		if(form_json.get("v1")!=null&&!form_json.get("v1").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V1='"+form_json.get("v1")+"',";
//		}else{
//			where += "V1=null,";
//		}
//		if(form_json.get("v2")!=null&&!form_json.get("v2").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V2='"+form_json.get("v2")+"',";
//		}else{
//			where += "V2='',";
//		}
//		if(form_json.get("v3")!=null&&!form_json.get("v3").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V3='"+form_json.get("v3")+"',";
//		}else{
//			where += "V3='',";
//		}
//		
//		if(form_json.get("v4")!=null&&!form_json.get("v4").equals("")){//复选框，不一定有值
//			if(form_json.get("v4").toString().indexOf(",")>-1){//多选
//				JSONArray jsonArray = JSONArray.fromObject(form_json.get("v4"));
//				String str = "";
//				for(int i = 0;i<jsonArray.size();i++){
//					str += jsonArray.getString(i)+",";
//				}
//				where += "V4='"+str.substring(0, str.length()-1)+"',";
//			}else{//单选
//				where += "V4='"+form_json.get("v4")+"',";
//			}
//		}else{
//			where += "V4='',";
//		}
//		
//		if(form_json.get("v5")!=null&&!form_json.get("v5").equals("")){//复选框，不一定有值
//			if(form_json.get("v5").toString().indexOf(",")>-1){//多选
//				JSONArray jsonArray = JSONArray.fromObject(form_json.get("v5"));
//				String str = "";
//				for(int i = 0;i<jsonArray.size();i++){
//					str += jsonArray.getString(i)+",";
//				}
//				where += "V5='"+str.substring(0, str.length()-1)+"',";
//			}else{//单选
//				where += "V5='"+form_json.get("v5")+"',";
//			}
//		}else{
//			where += "V5='',";
//		}
//		
//		if(form_json.get("v6")!=null&&!form_json.get("v6").equals("")){//复选框，不一定有值
//			if(form_json.get("v6").toString().indexOf(",")>-1){//多选
//				JSONArray jsonArray = JSONArray.fromObject(form_json.get("v6"));
//				String str = "";
//				for(int i = 0;i<jsonArray.size();i++){
//					str += jsonArray.getString(i)+",";
//				}
//				where += "V6='"+str.substring(0, str.length()-1)+"',";
//			}else{//单选
//				where += "V6='"+form_json.get("v6")+"',";
//			}
//		}else{
//			where += "V6='',";
//		}
//		
//		if(form_json.get("v7")!=null&&!form_json.get("v7").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V7='"+form_json.get("v7")+"',";
//		}else{
//			where += "V7=null,";
//		}
//		if(form_json.get("v8")!=null&&!form_json.get("v8").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V8='"+form_json.get("v8")+"',";
//		}else{
//			where += "V8='',";
//		}
//		if(form_json.get("v9")!=null&&!form_json.get("v9").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V9='"+form_json.get("v9")+"',";
//		}else{
//			where += "V9='',";
//		}
//		if(form_json.get("v10")!=null&&!form_json.get("v10").equals("")){//下拉框，一定有值，但是要筛除“请选择”
//			if(form_json.get("v10").equals("请选择")){
//				where += "V10='',";
//			}else{
//				where += "V10='"+form_json.get("v10")+"',";
//			}
//		}
//		if(form_json.get("v11")!=null&&!form_json.get("v11").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V11='"+form_json.get("v11")+"',";
//		}else{
//			where += "V11='',";
//		}
//		if(form_json.get("v12")!=null&&!form_json.get("v12").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V12='"+form_json.get("v12")+"',";
//		}else{
//			where += "V12='',";
//		}
//		
//		
//		if(where.length()>0){
//			where = where.substring(0, where.length()-1);
//		}
//		
//		try{
//			this.getBySqlMapper.update("update da_life set "+where+" where da_household_id="+pkid);
//			
//			HttpSession session = request.getSession();
//			JSONObject json = new JSONObject();
//			if(session.getAttribute("Login_map")!=null){//验证session不为空
//				Map<String,String> Login_map = (Map)session.getAttribute("Login_map");//用户的user表内容
//				SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
//				String hql1="insert into da_record(record_table,record_pkid,record_type,record_p_t,record_phone,record_name,record_time,record_mou_1,record_mou_2)"+
//						" VALUES ('da_household',"+pkid+",'修改',2,'','"+Login_map.get("col_account")+"','"+simpleDate.format(new Date())+"','基本情况','生活条件')";
//				this.getBySqlMapper.insert(hql1);
//			}
//			response.getWriter().write("1");
//		}catch (Exception e){
//			response.getWriter().write("0");
//		}
//	}
//	
//	/**
//	 * 获取图片
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException 
//	 */
//	@RequestMapping("getInput_11.do")
//	public void getInput_11(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		String pkid = request.getParameter("pkid").trim();//数据表ID
//		String type = request.getParameter("type").trim();//类型，1：措施。2：走访。3：成效。4：户主。5：成员。6：易地搬迁。
//		JSONObject zong = new JSONObject ();
//		
//		String sql = "select * from da_pic where pic_type="+type+" and pic_pkid="+pkid;
//		List<Map> list=getBySqlMapper.findRecords(sql);
//		JSONArray jsa=new JSONArray();
//		for(Map val:list){
//			
//			JSONObject obj=new JSONObject ();
//			obj.put("pkid",val.get("PKID")==null?"":val.get("PKID"));
//			obj.put("pic_path",val.get("PIC_PATH")==null?"":val.get("PIC_PATH"));
//			jsa.add(obj);
//		}
//		zong.put("pic", jsa);
//			
//		response.getWriter().write(zong.toString());
//		response.getWriter().close();
//	}
//	/**
//	 * 删除照片
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException 
//	 */
//	@RequestMapping("getInput_12.do")
//	public void getInput_12(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		String pkid = request.getParameter("pkid").trim();//数据表ID
//		try{
//			this.getBySqlMapper.delete("delete from da_pic where pkid="+pkid);
//			response.getWriter().write("1");
//		}catch (Exception e){
//			response.getWriter().write("0");
//		}
//	}
//	/**
//	 * 易地扶贫搬迁保存
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException 
//	 */
//	@RequestMapping("getInput_13.do")
//	public void getInput_13(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		String pkid = request.getParameter("pkid");//户主ID
//		String form_val = request.getParameter("form_val");
//		JSONObject form_json = JSONObject.fromObject(form_val);//表单数据
//		
//		String move_type = "";//集中安置或者分散安置
//		String focus_info = "";//集中安置分类
//		String dispersed_info = "";//分散安置分类
//		String dispersed_address = "";//房源地
//		String dispersed_price = "null";//房价（万元）
//		String dispersed_agreement = "";//与用工企业签订就业安置协议
//		String v1 = "";//搬迁方式
//		String v2 = "";//安置地
//		String v3 = "";//搬迁可能存在的困难
//		
//		if(form_json.get("move_type")!=null&&!form_json.get("move_type").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			move_type = form_json.get("move_type").toString();
//		}
//		
//		if(form_json.get("focus_info")!=null&&!form_json.get("focus_info").equals("")){//下拉框，一定有值，但是要筛除“请选择”
//			if(!form_json.get("focus_info").equals("请选择")){
//				focus_info = form_json.get("focus_info").toString();
//			}
//		}
//		
//		if(form_json.get("dispersed_info")!=null&&!form_json.get("dispersed_info").equals("")){//下拉框，一定有值，但是要筛除“请选择”
//			if(!form_json.get("dispersed_info").equals("请选择")){
//				dispersed_info = form_json.get("dispersed_info").toString();
//			}
//		}
//		
//		if(form_json.get("dispersed_address")!=null&&!form_json.get("dispersed_address").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			dispersed_address = form_json.get("dispersed_address").toString();
//		}
//		
//		if(form_json.get("dispersed_price")!=null&&!form_json.get("dispersed_price").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			dispersed_price = form_json.get("dispersed_price").toString();
//		}
//		
//		if(form_json.get("dispersed_agreement")!=null&&!form_json.get("dispersed_agreement").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			dispersed_agreement = form_json.get("dispersed_agreement").toString();
//		}
//		
//		if(form_json.get("v1")!=null&&!form_json.get("v1").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			v1 = form_json.get("v1").toString();
//		}
//		
//		if(form_json.get("v2")!=null&&!form_json.get("v2").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			v2 = form_json.get("v2").toString();
//		}
//		
//		if(form_json.get("v3")!=null&&!form_json.get("v3").equals("")){//复选框，不一定有值
//			if(form_json.get("v3").toString().indexOf(",")>-1){//多选
//				JSONArray jsonArray = JSONArray.fromObject(form_json.get("v3"));
//				String str = "";
//				for(int i = 0;i<jsonArray.size();i++){
//					str += jsonArray.getString(i)+",";
//				}
//				v3 = str.substring(0, str.length()-1);
//			}else{//单选
//				v3 = form_json.get("v3").toString();
//			}
//		}
//		
//		String count_st_sql = "select count(*) from da_household_move where da_household_id="+pkid;
//		int total = this.getBySqlMapper.findrows(count_st_sql);
//		if(move_type.equals("集中安置")){
//			dispersed_info = "";
//			dispersed_address = "";
//			dispersed_price = "0";
//			dispersed_agreement = "";
//		}else if(move_type.equals("分散安置")){
//			focus_info = "";
//			if(!dispersed_info.equals("进城购房")){
//				dispersed_address = "";
//				dispersed_price = "0";
//				dispersed_agreement = "";
//			}
//		}
//		
//		try{
//			
//			String sql = "";
//			if(total==0){
//				sql = "insert into da_household_move(da_household_id,move_type,focus_info,dispersed_info,dispersed_address,dispersed_price,dispersed_agreement,v1,v2,v3) "
//						+ "values("+pkid+",'"+move_type+"','"+focus_info+"','"+dispersed_info+"','"+dispersed_address+"','"+dispersed_price+"','"+dispersed_agreement+"','"+v1+"','"+v2+"','"+v3+"')";
//				this.getBySqlMapper.insert(sql);
//			}else if(total>0){
//				sql = "update da_household_move set move_type='"+move_type+"',focus_info='"+focus_info+"',dispersed_info='"+dispersed_info+"',dispersed_address='"+dispersed_address+"',"
//						+ "dispersed_price='"+dispersed_price+"',dispersed_agreement='"+dispersed_agreement+"',v1='"+v1+"',v2='"+v2+"',v3='"+v3+"' where da_household_id="+pkid;
//				this.getBySqlMapper.update(sql);
//			}
//			
//			HttpSession session = request.getSession();
//			JSONObject json = new JSONObject();
//			if(session.getAttribute("Login_map")!=null){//验证session不为空
//				Map<String,String> Login_map = (Map)session.getAttribute("Login_map");//用户的user表内容
//				SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
//				String hql1="insert into da_record(record_table,record_pkid,record_type,record_p_t,record_phone,record_name,record_time,record_mou_1,record_mou_2)"+
//						" VALUES ('da_household',"+pkid+",'修改',2,'','"+Login_map.get("col_account")+"','"+simpleDate.format(new Date())+"','基本情况','易地扶贫搬迁')";
//				this.getBySqlMapper.insert(hql1);
//			}
//			
//			response.getWriter().write("1");
//		}catch (Exception e){
//			response.getWriter().write("0");
//		}
//	}
//	
//	
//	/**
//	 *  上传照片  户主照片：4	家庭成员：5
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException 
//	 */
//	@RequestMapping(value="getInput_14.do", method=RequestMethod.POST)  
//	@ResponseBody
//    public void getInput_14(@RequestParam("pkid") String pkid,  @RequestParam("type") String type, @RequestParam("filename") MultipartFile file, 
//    		HttpServletRequest request, HttpServletResponse response) throws IOException{
//		
//		if (!file.isEmpty()) {
//			// 文件保存目录路径  
//	        String savePath = request.getServletContext().getRealPath("/")+ "attached/"+type+"/";
//	        // 文件保存目录URL  
//	        String saveUrl = request.getContextPath() + "/attached/"+type+"/";
//	        
//	        // 定义允许上传的文件扩展名  
//	        HashMap<String, String> extMap = new HashMap<String, String>();  
//	        extMap.put("image", "gif,jpg,jpeg,png,bmp");
//	        extMap.put("excel", "xls");
//	        String dirName = "image";
//	        // 最大文件大小  
//            long maxSize = 1000000; 
//            
//	        // 检查目录  
//            File uploadDir = new File(savePath);  
//            if (!uploadDir.isDirectory()) {  
//            	//response.getWriter().write(getError("上传目录不存在。"));
//            	if(!uploadDir.exists()){
//            		uploadDir.mkdirs();
//            	}
//               // return null;
//            }
//            
//            // 检查目录写权限  
//            if (!uploadDir.canWrite()) {  
//            	response.getWriter().write(getMessage("1","上传目录没有写权限。",""));  
//                return ;
//            }
//            
//            //创建文件夹
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
//            String ymd = sdf.format(new Date());  
//            savePath += ymd + "\\";  
//            saveUrl += ymd + "/";
//            File dirFile = new File(savePath);  
//            if (!dirFile.exists()) {  
//                dirFile.mkdirs();  
//            }
//            
//            // 检查扩展名 
//            String filename_hout = file.getOriginalFilename();
//            String fileExt = filename_hout.substring(filename_hout.lastIndexOf(".") + 1).toLowerCase(); 
//            if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(fileExt)) {  
//            	response.getWriter().write(getMessage("1","上传文件扩展名是不允许的扩展名。\n只允许"    + extMap.get(dirName) + "格式的文件。",""));  
//                return ;
//            }
//            
//            long size = file.getSize();//文件大小
//            // 检查文件大小  
//            if (size > maxSize) {  
//            	response.getWriter().write(getMessage("1","上传文件大小超过限制。",""));  
//                return ;
//            } 
//            
//            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");  
//            String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt; 
//        
//            try {
//            	File uploadedFile = new File(savePath, newFileName);
//                byte[] bytes = file.getBytes();  
//                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));  
//                stream.write(bytes);  
//                stream.close();
//                
//                if (!pkid.equals("")) {//如果表名不为空，可以将上传的文件导入数据库中
//                	response.getWriter().write(getSave_ImportData(saveUrl+newFileName, type, pkid, size, fileExt));
//                	return ;
//                }
//            } catch (Exception e) {  
//            	response.getWriter().write(getMessage("1","上传文件失败。",""));  
//                return ;
//            }
//        } else {  
//        	response.getWriter().write(getMessage("1","请选择文件。",""));
//        	return ;
//        }
//    }
//	
//	
////	@RequestMapping("getInput_14.do")
////	public void getInput_14(HttpServletRequest request,HttpServletResponse response) throws IOException{
////		try {
////			FileItemFactory factory = new DiskFileItemFactory();  
////            ServletFileUpload upload = new ServletFileUpload(factory);  
////            upload.setHeaderEncoding("UTF-8");  
////            List items = upload.parseRequest(request);
////            
////            String type = "";
////            String pkid = "";
////            
////            Iterator itrFormField = items.iterator();
////            while (itrFormField.hasNext()) {
////                FileItem item = (FileItem) itrFormField.next();
////                if(item.isFormField()){//判断FileItem类对象封装的数据是否属于一个普通表单字段，还是属于一个文件表单字段，如果是普通表单字段则返回true，否则返回false。
////                	if(item.getFieldName().equals("type")){//getFieldName方法用于返回表单字段元素的name属性值,例如“name=p1”中的“p1”
////                		type = item.getString("UTF-8");
////                	}else if(item.getFieldName().equals("pkid")){
////                		pkid = item.getString("UTF-8");
////                	}
////                }
////            }
////			
////            PrintWriter out = response.getWriter();  
////            // 文件保存目录路径  
////            String savePath = request.getServletContext().getRealPath("/")+ "attached/"+type+"/";
////            // 文件保存目录URL  
////            String saveUrl = request.getContextPath() + "/attached/"+type+"/";
////            
////            // 定义允许上传的文件扩展名  
////            HashMap<String, String> extMap = new HashMap<String, String>();  
////            extMap.put("image", "gif,jpg,jpeg,png,bmp");
////            extMap.put("excel", "xls");
////            String dirName = "image";
////            // 最大文件大小  
////            long maxSize = 1000000;  
////            response.setContentType("text/html; charset=UTF-8");  
////            
////            if (!ServletFileUpload.isMultipartContent(request)) {  
////            	response.getWriter().write(getMessage("1","请选择文件。",""));  
////                return ;
////            }  
////            
////            // 检查目录  
////            File uploadDir = new File(savePath);  
////            if (!uploadDir.isDirectory()) {  
////            	//response.getWriter().write(getError("上传目录不存在。"));
////            	if(!uploadDir.exists()){
////            		uploadDir.mkdirs();
////            	}
////               // return null;
////            }  
////            // 检查目录写权限  
////            if (!uploadDir.canWrite()) {  
////            	response.getWriter().write(getMessage("1","上传目录没有写权限。",""));  
////                return ;
////            }
////            
////            //创建文件夹
////            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
////            String ymd = sdf.format(new Date());  
////            savePath += ymd + "\\";  
////            saveUrl += ymd + "/";
////            File dirFile = new File(savePath);  
////            if (!dirFile.exists()) {  
////                dirFile.mkdirs();  
////            }
////            
////            Iterator itr = items.iterator();
////            while (itr.hasNext()) {
////                FileItem item = (FileItem) itr.next();
////                if (!item.isFormField()) {//判断FileItem类对象封装的数据是否属于一个普通表单字段，还是属于一个文件表单字段，如果是普通表单字段则返回true，否则返回false。
////                	String fileName = item.getName();//getName方法用于获得文件上传字段中的文件名
////                    // 检查文件大小  
////                    if (item.getSize() > maxSize) {  
////                    	response.getWriter().write(getMessage("1","上传文件大小超过限制。",""));  
////                        return ;
////                    }  
////                    // 检查扩展名  
////                    String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();  
////                    if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(fileExt)) {  
////                    	response.getWriter().write(getMessage("1","上传文件扩展名是不允许的扩展名。\n只允许"    + extMap.get(dirName) + "格式。",""));  
////                        return ;
////                    }  
////  
////                    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");  
////                    String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;  
////                    try {  
////                        File uploadedFile = new File(savePath, newFileName);  
////                        item.write(uploadedFile);  
////                    } catch (Exception e) {  
////                    	response.getWriter().write(getMessage("1","上传文件失败。",""));  
////                        return ;
////                    }
////                    long size = item.getSize();//文件大小
////                    if (!pkid.equals("")) {//如果表名不为空，可以将上传的文件导入数据库中
////                    	response.getWriter().write(getSave_ImportData(saveUrl+newFileName, type, pkid, size, fileExt));
////                    	return ;
////                    }
////                }
////            }
////        } catch (FileUploadException e1) {  
////            e1.printStackTrace();  
////        } 
////	}
//	/**
//	 * 图片上传	帮扶措施:1		走访情况：2		帮扶成效：3
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws UnsupportedEncodingException 
//	 */
//	@RequestMapping(value="getInput_15.do", method=RequestMethod.POST)  
//	@ResponseBody
//    public void getInput_15(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException{
//		
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		
//		String pkid = request.getParameter("pkid").trim();
//		String type = request.getParameter("type").trim();
//		
//		if (!file.isEmpty()) {
//			// 文件保存目录路径  
//	        String savePath = request.getServletContext().getRealPath("/")+ "attached/"+type+"/";
//	        // 文件保存目录URL  
//	        String saveUrl = request.getContextPath() + "/attached/"+type+"/";
//	        
//	        // 定义允许上传的文件扩展名  
//	        HashMap<String, String> extMap = new HashMap<String, String>();  
//	        extMap.put("image", "gif,jpg,jpeg,png,bmp");
//	        extMap.put("excel", "xls");
//	        String dirName = "image";
//	        // 最大文件大小  
//            long maxSize = 1000000; 
//            
//	        // 检查目录  
//            File uploadDir = new File(savePath);  
//            if (!uploadDir.isDirectory()) {  
//            	//response.getWriter().write(getError("上传目录不存在。"));
//            	if(!uploadDir.exists()){
//            		uploadDir.mkdirs();
//            	}
//               // return null;
//            }
//            
//            // 检查目录写权限  
//            if (!uploadDir.canWrite()) {  
//            	response.getWriter().write(getMessage("1","上传目录没有写权限。",""));  
//                return ;
//            }
//            
//            //创建文件夹
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
//            String ymd = sdf.format(new Date());  
//            savePath += ymd + "\\";  
//            saveUrl += ymd + "/";
//            File dirFile = new File(savePath);  
//            if (!dirFile.exists()) {  
//                dirFile.mkdirs();  
//            }
//            
//            // 检查扩展名 
//            String filename_hout = file.getOriginalFilename();
//            String fileExt = filename_hout.substring(filename_hout.lastIndexOf(".") + 1).toLowerCase(); 
//            if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(fileExt)) {  
//            	response.getWriter().write(getMessage("1","上传文件扩展名是不允许的扩展名。\n只允许"    + extMap.get(dirName) + "格式的文件。",""));  
//                return ;
//            }
//            
//            long size = file.getSize();//文件大小
//            // 检查文件大小  
//            if (size > maxSize) {  
//            	response.getWriter().write(getMessage("1","上传文件大小超过限制。",""));  
//                return ;
//            } 
//            
//            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");  
//            String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt; 
//        
//            try {
//            	File uploadedFile = new File(savePath, newFileName);
//                byte[] bytes = file.getBytes();  
//                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));  
//                stream.write(bytes);  
//                stream.close();
//                
//                if (!pkid.equals("")) {//如果表名不为空，可以将上传的文件导入数据库中
//                	response.getWriter().write(getSave_poth(type, pkid, saveUrl+newFileName, size, fileExt));
//                	return ;
//                }
//            } catch (Exception e) {  
//            	response.getWriter().write(getMessage("1","上传文件失败。",""));  
//                return ;
//            }
//        } else {  
//        	response.getWriter().write(getMessage("1","请选择文件。",""));
//        	return ;
//        }
//    }
//	
//	
//	
//	
////	@RequestMapping("getInput_15.do")
////	public void getInput_15(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
////		
////		request.setCharacterEncoding("UTF-8");
////		response.setCharacterEncoding("UTF-8");
////		
////		String pkid = request.getParameter("pkid").trim();
////		String type = request.getParameter("type").trim();
////		//System.out.println(pkid);
////		
////		//获取磁盘文件条目工厂
////		DiskFileItemFactory factory = new DiskFileItemFactory();
////		
////		//获取文件需要上传到的路径
////		String savePath = request.getServletContext().getRealPath("/")+ "attached\\"+type+"\\"; 
////		 // 文件保存目录URL  
////        String saveUrl = request.getContextPath() + "/attached/"+type+"/"; 
////        
////        
////		// 检查目录  
////        File uploadDir = new File(savePath);  
////        if (!uploadDir.isDirectory()) {  
////        	//response.getWriter().write(getError("上传目录不存在。"));
////        	if(!uploadDir.exists()){
////        		uploadDir.mkdirs();
////        	}
////           // return null;
////        }  
////        // 检查目录写权限  
////        if (!uploadDir.canWrite()) {  
////        	//response.getWriter().write(getError("上传目录没有写权限。"));  
////            return ;
////        }
////        //创建文件夹
////        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
////        String ymd = sdf.format(new Date());  
////        savePath += ymd + "\\";  
////        saveUrl += ymd + "/";
////        File dirFile = new File(savePath);  
////        if (!dirFile.exists()) {  
////            dirFile.mkdirs();  
////        }
////        
////        //如果没有以下两行设置的话，上传大的文件，会占用很多内存
////        //设置暂时存放的存储室，这个存储室，可以和最终存储文件的目录不同
////        /**
////         * 原理 它是先存储 暂时存储室，然后再真正写到对应的目录的硬盘上，按理来说 当上传一个文件时，其实是上传了两份，第一个是以。tem格式的
////         * 然后再将其真正写到 对应目录的硬盘上
////         */
////        
////        factory.setRepository(new File(savePath));
////        //设置缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
////        factory.setSizeThreshold(1024*1024);
////        //高水平的API文件上传处理
////        ServletFileUpload upload = new ServletFileUpload(factory);
////        try{
////        	List<FileItem> list = (List<FileItem>) upload.parseRequest(request);
////        	for(FileItem item : list){
////        		//获取表单的属性名字
////        		String name = item.getFieldName();
////        		
////        		System.out.println("--"+name+"--");
//////                System.out.println("--"+pkid+"--");
//////                System.out.println("--"+type+"--");
////        		
////        		//如果获取的 表单信息是普通的文本信息
////        		if(item.isFormField()){
////        			//获取用户具体输入的字符串，因为表单提交过来的是字符串类型
////        			String value = item.getString();
////        			
////        			request.setAttribute(name, value);
////        		}else{
////        			//对于传入的非简单的字符串进行处理，比如说二进制的图片，文件
////        			
////        			//获取路径名
////        			String value = item.getName();
////        			//索引到最后一个反斜杠
////        			int start = value.lastIndexOf("\\");
////        			//截取 上传文件的 字符串名字，加1是 去掉反斜杠
////        			String fileName = value.substring(start + 1);
////        			
////        			// 检查扩展名  
////                    String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase(); 
////                    
////        			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");  
////                    String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
////        			
////        			request.setAttribute(name, newFileName);
////        			//真正写到磁盘上
////        			item.write(new File(savePath, newFileName));
////        			
////        			long size = item.getSize();//文件大小
////        			//System.out.println(savePath+newFileName);
////        			//System.out.println(saveUrl+newFileName);
////        			
////        			response.getWriter().write(getSave_poth(type, pkid, saveUrl+newFileName, size, fileExt)); 
////        		}
////        	}
////        }catch(FileUploadException e){
////        	e.printStackTrace();
////        }catch(Exception e){
////        	
////        }
////	}
//	/**
//	 * 读取当前收入与支出
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException 
//	 */
//	@RequestMapping("getInput_16.do")
//	public void getInput_16(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		String pkid=request.getParameter("pkid");
//		String type = request.getParameter("type").trim();
//		JSONObject zong = new JSONObject ();
//		
//		//收入
//		String sql = "";
//		//支出
//		String xian_sql = "";
//		if(type.equals("1")){//当前
//			sql="select * from da_current_income where da_household_id="+pkid;
//			xian_sql="select * from da_current_expenditure where da_household_id="+pkid;
//		}else if(type.equals("2")){//帮扶后
//			sql="select * from da_helpback_income where da_household_id="+pkid;
//			xian_sql="select * from da_helpback_expenditure where da_household_id="+pkid;
//		}
//		
//		List<Map> list=getBySqlMapper.findRecords(sql);
//		for(Map val:list){
//			JSONObject obj=new JSONObject ();
//			obj.put("v1",val.get("V1")==null?"":val.get("V1"));
//			obj.put("v2",val.get("V2")==null?"":val.get("V2"));
//			obj.put("v3",val.get("V3")==null?"":val.get("V3"));
//			obj.put("v4",val.get("V4")==null?"":val.get("V4"));
//			obj.put("v5",val.get("V5")==null?"":val.get("V5"));
//			obj.put("v6",val.get("V6")==null?"":val.get("V6"));
//			obj.put("v7",val.get("V7")==null?"":val.get("V7"));
//			obj.put("v8",val.get("V8")==null?"":val.get("V8"));
//			obj.put("v11",val.get("V11")==null?"":val.get("V11"));
//			obj.put("v12",val.get("V12")==null?"":val.get("V12"));
//			obj.put("v13",val.get("V13")==null?"":val.get("V13"));
//			obj.put("v14",val.get("V14")==null?"":val.get("V14"));
//			obj.put("v15",val.get("V15")==null?"":val.get("V15"));
//			obj.put("v16",val.get("V16")==null?"":val.get("V16"));
//			obj.put("v17",val.get("V17")==null?"":val.get("V17"));
//			obj.put("v18",val.get("V18")==null?"":val.get("V18"));
//			obj.put("v19",val.get("V19")==null?"":val.get("V19"));
//			obj.put("v20",val.get("V20")==null?"":val.get("V20"));
//			obj.put("v23",val.get("V23")==null?"":val.get("V23"));
//			obj.put("v24",val.get("V24")==null?"":val.get("V24"));
//			obj.put("v25",val.get("V25")==null?"":val.get("V25"));
//			obj.put("v26",val.get("V26")==null?"":val.get("V26"));
//			obj.put("v27",val.get("V27")==null?"":val.get("V27"));
//			obj.put("v28",val.get("V28")==null?"":val.get("V28"));
//			obj.put("v29",val.get("V29")==null?"":val.get("V29"));
//			obj.put("v30",val.get("V30")==null?"":val.get("V30"));
//			obj.put("v31",val.get("V31")==null?"":val.get("V31"));
//			obj.put("v32",val.get("V32")==null?"":val.get("V32"));
//			obj.put("v33",val.get("V33")==null?"":val.get("V33"));
//			obj.put("v34",val.get("V34")==null?"":val.get("V34"));
//			obj.put("v35",val.get("V35")==null?"":val.get("V35"));
//			obj.put("v36",val.get("V36")==null?"":val.get("V36"));
//			obj.put("v37",val.get("V37")==null?"":val.get("V37"));
//			obj.put("v38",val.get("V38")==null?"":val.get("38"));
//			
//			if(type.equals("1")){//当前
//				obj.put("v40",val.get("V40")==null?"":val.get("V40"));
//				obj.put("v41",val.get("V41")==null?"":val.get("V41"));
//				obj.put("v42",val.get("V42")==null?"":val.get("V42"));
//				obj.put("v43",val.get("V43")==null?"":val.get("V43"));
//			}
//			
//			zong.put("shouru", obj);
//		}
//		
//		List<Map> xian_list=getBySqlMapper.findRecords(xian_sql);
//		for(Map val:xian_list){
//			JSONObject obj=new JSONObject ();
//			obj.put("v1",val.get("V1")==null?"":val.get("V1"));
//			obj.put("v2",val.get("V2")==null?"":val.get("V2"));
//			obj.put("v3",val.get("V3")==null?"":val.get("V3"));
//			obj.put("v4",val.get("V4")==null?"":val.get("V4"));
//			obj.put("v5",val.get("V5")==null?"":val.get("V5"));
//			obj.put("v6",val.get("V6")==null?"":val.get("V6"));
//			obj.put("v7",val.get("V7")==null?"":val.get("V7"));
//			obj.put("v8",val.get("V8")==null?"":val.get("V8"));
//			obj.put("v9",val.get("V9")==null?"":val.get("V9"));
//			obj.put("v10",val.get("V10")==null?"":val.get("V10"));
//			obj.put("v11",val.get("V11")==null?"":val.get("V11"));
//			obj.put("v12",val.get("V12")==null?"":val.get("V12"));
//			obj.put("v13",val.get("V13")==null?"":val.get("V13"));
//			obj.put("v14",val.get("V14")==null?"":val.get("V14"));
//			obj.put("v15",val.get("V15")==null?"":val.get("V15"));
//			obj.put("v16",val.get("V16")==null?"":val.get("V16"));
//			obj.put("v17",val.get("V17")==null?"":val.get("V17"));
//			obj.put("v18",val.get("V18")==null?"":val.get("V18"));
//			obj.put("v19",val.get("V19")==null?"":val.get("V19"));
//			obj.put("v20",val.get("V20")==null?"":val.get("V20"));
//			obj.put("v21",val.get("V21")==null?"":val.get("V21"));
//			obj.put("v22",val.get("V22")==null?"":val.get("V22"));
//			obj.put("v23",val.get("V23")==null?"":val.get("V23"));
//			obj.put("v24",val.get("V24")==null?"":val.get("V24"));
//			obj.put("v25",val.get("V25")==null?"":val.get("V25"));
//			obj.put("v26",val.get("V26")==null?"":val.get("V26"));
//			obj.put("v27",val.get("V27")==null?"":val.get("V27"));
//			obj.put("v28",val.get("V28")==null?"":val.get("V28"));
//			obj.put("v29",val.get("V29")==null?"":val.get("V29"));
//			obj.put("v30",val.get("V30")==null?"":val.get("V30"));
//			zong.put("zhichu", obj);
//		}
//			
//		response.getWriter().write(zong.toString());
//		response.getWriter().close();
//	}
//	/**
//	 * 保存收入
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping("getInput_17.do")
//	public void getInput_17(HttpServletRequest request,HttpServletResponse response){
//		
//		String pkid = request.getParameter("pkid");
//		String form_val = request.getParameter("form_val");
//		String type = request.getParameter("type").trim();
//		JSONObject form_json = JSONObject.fromObject(form_val);//表单数据
//		
//		String where = "";
//		float xiaoji_1 = 0;
//		float xiaoji_2 = 0;
//		float zong = 0;
//		
//		if(form_json.get("v1")!=null&&!form_json.get("v1").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V1='"+form_json.get("v1")+"',";
//		}else{
//			where += "V1=null,";
//		}
//		if(form_json.get("v2")!=null&&!form_json.get("v2").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V2='"+form_json.get("v2")+"',";
//			xiaoji_1 += Float.parseFloat(form_json.get("v2").toString());
//		}else{
//			where += "V2=null,";
//		}
//		if(form_json.get("v3")!=null&&!form_json.get("v3").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V3='"+form_json.get("v3")+"',";
//		}else{
//			where += "V3=null,";
//		}
//		if(form_json.get("v4")!=null&&!form_json.get("v4").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V4='"+form_json.get("v4")+"',";
//			xiaoji_1 += Float.parseFloat(form_json.get("v4").toString());
//		}else{
//			where += "V4=null,";
//		}
//		if(form_json.get("v5")!=null&&!form_json.get("v5").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V5='"+form_json.get("v5")+"',";
//		}else{
//			where += "V5=null,";
//		}
//		if(form_json.get("v6")!=null&&!form_json.get("v6").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V6='"+form_json.get("v6")+"',";
//			xiaoji_1 += Float.parseFloat(form_json.get("v6").toString());
//		}else{
//			where += "V6=null,";
//		}
//		if(form_json.get("v7")!=null&&!form_json.get("v7").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V7='"+form_json.get("v7")+"',";
//		}else{
//			where += "V7=null,";
//		}
//		if(form_json.get("v8")!=null&&!form_json.get("v8").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V8='"+form_json.get("v8")+"',";
//			xiaoji_1 += Float.parseFloat(form_json.get("v8").toString());
//		}else{
//			where += "V8=null,";
//		}
//		
//		if(form_json.get("v11")!=null&&!form_json.get("v11").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V11='"+form_json.get("v11")+"',";
//		}else{
//			where += "V11=null,";
//		}
//		if(form_json.get("v12")!=null&&!form_json.get("v12").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V12='"+form_json.get("v12")+"',";
//			xiaoji_2 += Float.parseFloat(form_json.get("v12").toString());
//		}else{
//			where += "V12=null,";
//		}
//		if(form_json.get("v13")!=null&&!form_json.get("v13").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V13='"+form_json.get("v13")+"',";
//		}else{
//			where += "V13=null,";
//		}
//		if(form_json.get("v14")!=null&&!form_json.get("v14").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V14='"+form_json.get("v14")+"',";
//			xiaoji_2 += Float.parseFloat(form_json.get("v14").toString());
//		}else{
//			where += "V14=null,";
//		}
//		if(form_json.get("v15")!=null&&!form_json.get("v15").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V15='"+form_json.get("v15")+"',";
//		}else{
//			where += "V15=null,";
//		}
//		if(form_json.get("v16")!=null&&!form_json.get("v16").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V16='"+form_json.get("v16")+"',";
//			xiaoji_2 += Float.parseFloat(form_json.get("v16").toString());
//		}else{
//			where += "V16=null,";
//		}
//		if(form_json.get("v17")!=null&&!form_json.get("v17").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V17='"+form_json.get("v17")+"',";
//		}else{
//			where += "V17=null,";
//		}
//		if(form_json.get("v18")!=null&&!form_json.get("v18").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V18='"+form_json.get("v18")+"',";
//			xiaoji_2 += Float.parseFloat(form_json.get("v18").toString());
//		}else{
//			where += "V18=null,";
//		}
//		if(form_json.get("v19")!=null&&!form_json.get("v19").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V19='"+form_json.get("v19")+"',";
//		}else{
//			where += "V19=null,";
//		}
//		if(form_json.get("v20")!=null&&!form_json.get("v20").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V20='"+form_json.get("v20")+"',";
//			xiaoji_2 += Float.parseFloat(form_json.get("v20").toString());
//		}else{
//			where += "V20=null,";
//		}
//		if(form_json.get("v23")!=null&&!form_json.get("v23").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V23='"+form_json.get("v23")+"',";
//		}else{
//			where += "V23=null,";
//		}
//		if(form_json.get("v24")!=null&&!form_json.get("v24").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V24='"+form_json.get("v24")+"',";
//			zong += Float.parseFloat(form_json.get("v24").toString());
//		}else{
//			where += "V24=null,";
//		}
//		if(form_json.get("v25")!=null&&!form_json.get("v25").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V25='"+form_json.get("v25")+"',";
//		}else{
//			where += "V25=null,";
//		}
//		if(form_json.get("v26")!=null&&!form_json.get("v26").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V26='"+form_json.get("v26")+"',";
//			zong += Float.parseFloat(form_json.get("v26").toString());
//		}else{
//			where += "V26=null,";
//		}
//		if(form_json.get("v27")!=null&&!form_json.get("v27").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V27='"+form_json.get("v27")+"',";
//		}else{
//			where += "V27=null,";
//		}
//		if(form_json.get("v28")!=null&&!form_json.get("v28").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V28='"+form_json.get("v28")+"',";
//			zong += Float.parseFloat(form_json.get("v28").toString());
//		}else{
//			where += "V28=null,";
//		}
//		if(form_json.get("v29")!=null&&!form_json.get("v29").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V29='"+form_json.get("v29")+"',";
//		}else{
//			where += "V29=null,";
//		}
//		if(form_json.get("v30")!=null&&!form_json.get("v30").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V30='"+form_json.get("v30")+"',";
//			zong += Float.parseFloat(form_json.get("v30").toString());
//		}else{
//			where += "V30=null,";
//		}
//		if(form_json.get("v31")!=null&&!form_json.get("v31").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V31='"+form_json.get("v31")+"',";
//		}else{
//			where += "V31=null,";
//		}
//		if(form_json.get("v32")!=null&&!form_json.get("v32").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V32='"+form_json.get("v32")+"',";
//			zong += Float.parseFloat(form_json.get("v32").toString());
//		}else{
//			where += "V32=null,";
//		}
//		if(form_json.get("v33")!=null&&!form_json.get("v33").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V33='"+form_json.get("v33")+"',";
//		}else{
//			where += "V33=null,";
//		}
//		if(form_json.get("v34")!=null&&!form_json.get("v34").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V34='"+form_json.get("v34")+"',";
//			zong += Float.parseFloat(form_json.get("v34").toString());
//		}else{
//			where += "V34=null,";
//		}
//		if(form_json.get("v35")!=null&&!form_json.get("v35").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V35='"+form_json.get("v35")+"',";
//		}else{
//			where += "V35=null,";
//		}
//		if(form_json.get("v36")!=null&&!form_json.get("v36").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V36='"+form_json.get("v36")+"',";
//		}else{
//			where += "V36=null,";
//		}
//		if(form_json.get("v37")!=null&&!form_json.get("v37").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V37='"+form_json.get("v37")+"',";
//		}else{
//			where += "V37=null,";
//		}
//		if(form_json.get("v38")!=null&&!form_json.get("v38").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V38='"+form_json.get("v38")+"',";
//		}else{
//			where += "V38=null,";
//		}
//		
//		if(form_json.get("v40")!=null&&!form_json.get("v40").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V40='"+form_json.get("v40")+"',";
//		}else{
//			where += "V40=null,";
//		}
//		if(form_json.get("v41")!=null&&!form_json.get("v41").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V41='"+form_json.get("v41")+"',";
//			xiaoji_2 += Float.parseFloat(form_json.get("v41").toString());
//		}else{
//			where += "V41=null,";
//		}
//		if(form_json.get("v42")!=null&&!form_json.get("v42").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "v42='"+form_json.get("v42")+"',";
//		}else{
//			where += "V42=null,";
//		}
//		if(form_json.get("v43")!=null&&!form_json.get("v43").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V43='"+form_json.get("v43")+"',";
//			xiaoji_2 += Float.parseFloat(form_json.get("v43").toString());
//		}else{
//			where += "V43=null,";
//		}
//		
//		where += "V10="+xiaoji_1+",v22="+xiaoji_2+",v39="+(zong+xiaoji_1+xiaoji_2);
//		
//		String hu_sql = "";
//		String renjun_sql="";
//		if(type.equals("1")){//当前
//			hu_sql = "update da_current_income set "+where+" where da_household_id="+pkid;
//			renjun_sql = "update da_household vb set v24=("+
//						 " select vc.num from ("+
//						 " SELECT ROUND((a.v39-f.v31)/a.v9,2) num,a.pkid FROM"+
//						 " (select e.pkid,d.v39,e.v9 from da_current_income d join da_household e on d.da_household_id=e.pkid  where e.pkid='"+ pkid +"')a join"+
//						 " da_current_expenditure f ON f.da_household_id = a.pkid"+
//						 " ) vc where vc.pkid=vb.pkid"+
//						 " ) where vb.pkid='"+ pkid +"'";
//		
//			
//			
//		}else if(type.equals("2")){//帮扶后
//			hu_sql = "update da_helpback_income set "+where+" where da_household_id="+pkid;
//		}
//		try{
//			this.getBySqlMapper.update(hu_sql);
//			this.getBySqlMapper.update(renjun_sql);
//			
//			HttpSession session = request.getSession();
//			JSONObject json = new JSONObject();
//			if(session.getAttribute("Login_map")!=null){//验证session不为空
//				Map<String,String> Login_map = (Map)session.getAttribute("Login_map");//用户的user表内容
//				SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
//				
//				if(type.equals("1")){//当前
//					String hql1="insert into da_record(record_table,record_pkid,record_type,record_p_t,record_phone,record_name,record_time,record_mou_1,record_mou_2)"+
//							" VALUES ('da_household',"+pkid+",'修改',2,'','"+Login_map.get("col_account")+"','"+simpleDate.format(new Date())+"','当前收支','当前收入情况')";
//					this.getBySqlMapper.insert(hql1);
//				}else if(type.equals("2")){//帮扶后
//					String hql1="insert into da_record(record_table,record_pkid,record_type,record_p_t,record_phone,record_name,record_time,record_mou_1,record_mou_2)"+
//							" VALUES ('da_household',"+pkid+",'修改',2,'','"+Login_map.get("col_account")+"','"+simpleDate.format(new Date())+"','帮扶后收支分析','帮扶后收入情况')";
//					this.getBySqlMapper.insert(hql1);
//				}
//			}
//			
//			response.getWriter().write("1");
//		}catch (Exception e){
//			try {
//				response.getWriter().write("0");
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		}
//	}
//	/**
//	 * 保存支出
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException 
//	 */
//	@RequestMapping("getInput_18.do")
//	public void getInput_18(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		
//		String pkid = request.getParameter("pkid");
//		String form_val = request.getParameter("form_val");
//		String type = request.getParameter("type").trim();
//		JSONObject form_json = JSONObject.fromObject(form_val);//表单数据
//		
//		float zong = 0;
//		
//		String where = "";
//		if(form_json.get("v1")!=null&&!form_json.get("v1").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V1='"+form_json.get("v1")+"',";
//		}else{
//			where += "V1=null,";
//		}
//		if(form_json.get("v2")!=null&&!form_json.get("v2").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V2='"+form_json.get("v2")+"',";
//			zong += Float.parseFloat(form_json.get("v2").toString());
//		}else{
//			where += "V2=null,";
//		}
//		if(form_json.get("v3")!=null&&!form_json.get("v3").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V3='"+form_json.get("v3")+"',";
//		}else{
//			where += "V3=null,";
//		}
//		if(form_json.get("v4")!=null&&!form_json.get("v4").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V4='"+form_json.get("v4")+"',";
//			zong += Float.parseFloat(form_json.get("v4").toString());
//		}else{
//			where += "V4=null,";
//		}
//		if(form_json.get("v5")!=null&&!form_json.get("v5").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V5='"+form_json.get("v5")+"',";
//		}else{
//			where += "V5=null,";
//		}
//		if(form_json.get("v6")!=null&&!form_json.get("v6").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V6='"+form_json.get("v6")+"',";
//			zong += Float.parseFloat(form_json.get("v6").toString());
//		}else{
//			where += "V6=null,";
//		}
//		if(form_json.get("v7")!=null&&!form_json.get("v7").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V7='"+form_json.get("v7")+"',";
//		}else{
//			where += "V7=null,";
//		}
//		if(form_json.get("v8")!=null&&!form_json.get("v8").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V8='"+form_json.get("v8")+"',";
//			zong += Float.parseFloat(form_json.get("v8").toString());
//		}else{
//			where += "V8=null,";
//		}
//		if(form_json.get("v9")!=null&&!form_json.get("v9").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V9='"+form_json.get("v9")+"',";
//		}else{
//			where += "V9=null,";
//		}
//		if(form_json.get("v10")!=null&&!form_json.get("v10").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V10='"+form_json.get("v10")+"',";
//			zong += Float.parseFloat(form_json.get("v10").toString());
//		}else{
//			where += "V10=null,";
//		}
//		if(form_json.get("v11")!=null&&!form_json.get("v11").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V11='"+form_json.get("v11")+"',";
//		}else{
//			where += "V11=null,";
//		}
//		if(form_json.get("v12")!=null&&!form_json.get("v12").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V12='"+form_json.get("v12")+"',";
//			zong += Float.parseFloat(form_json.get("v12").toString());
//		}else{
//			where += "V12=null,";
//		}
//		if(form_json.get("v13")!=null&&!form_json.get("v13").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V13='"+form_json.get("v13")+"',";
//		}else{
//			where += "V13=null,";
//		}
//		if(form_json.get("v14")!=null&&!form_json.get("v14").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V14='"+form_json.get("v14")+"',";
//			zong += Float.parseFloat(form_json.get("v14").toString());
//		}else{
//			where += "V14=null,";
//		}
//		if(form_json.get("v15")!=null&&!form_json.get("v15").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V15='"+form_json.get("v15")+"',";
//		}else{
//			where += "V15=null,";
//		}
//		if(form_json.get("v16")!=null&&!form_json.get("v16").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V16='"+form_json.get("v16")+"',";
//			zong += Float.parseFloat(form_json.get("v16").toString());
//		}else{
//			where += "V16=null,";
//		}
//		if(form_json.get("v17")!=null&&!form_json.get("v17").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V17='"+form_json.get("v17")+"',";
//		}else{
//			where += "V17=null,";
//		}
//		if(form_json.get("v18")!=null&&!form_json.get("v18").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V18='"+form_json.get("v18")+"',";
//			zong += Float.parseFloat(form_json.get("v18").toString());
//		}else{
//			where += "V18=null,";
//		}
//		if(form_json.get("v19")!=null&&!form_json.get("v19").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V19='"+form_json.get("v19")+"',";
//		}else{
//			where += "V19=null,";
//		}
//		if(form_json.get("v20")!=null&&!form_json.get("v20").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V20='"+form_json.get("v20")+"',";
//			zong += Float.parseFloat(form_json.get("v20").toString());
//		}else{
//			where += "V20=null,";
//		}
//		if(form_json.get("v21")!=null&&!form_json.get("v21").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V21='"+form_json.get("v21")+"',";
//		}else{
//			where += "V21=null,";
//		}
//		if(form_json.get("v22")!=null&&!form_json.get("v22").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V22='"+form_json.get("v22")+"',";
//			zong += Float.parseFloat(form_json.get("v22").toString());
//		}else{
//			where += "V22=null,";
//		}
//		if(form_json.get("v23")!=null&&!form_json.get("v23").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V23='"+form_json.get("v23")+"',";
//		}else{
//			where += "V23=null,";
//		}
//		if(form_json.get("v24")!=null&&!form_json.get("v24").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V24='"+form_json.get("v24")+"',";
//		}else{
//			where += "V24=null,";
//		}
//		if(form_json.get("v25")!=null&&!form_json.get("v25").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V25='"+form_json.get("v25")+"',";
//		}else{
//			where += "V25=null,";
//		}
//		if(form_json.get("v26")!=null&&!form_json.get("v26").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V26='"+form_json.get("v26")+"',";
//		}else{
//			where += "V26=null,";
//		}
//		if(form_json.get("v27")!=null&&!form_json.get("v27").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V27='"+form_json.get("v27")+"',";
//			zong += Float.parseFloat(form_json.get("v27").toString());
//		}else{
//			where += "V27=null,";
//		}
//		if(form_json.get("v28")!=null&&!form_json.get("v28").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V28='"+form_json.get("v28")+"',";
//		}else{
//			where += "V28=null,";
//		}
//		if(form_json.get("v29")!=null&&!form_json.get("v29").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V29='"+form_json.get("v29")+"',";
//		}else{
//			where += "V29=null,";
//		}
//		if(form_json.get("v30")!=null&&!form_json.get("v30").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			where += "V30='"+form_json.get("v30")+"',";
//			zong += Float.parseFloat(form_json.get("v30").toString());
//		}else{
//			where += "V30=null,";
//		}
//		
//		where += "V31="+zong;
//		String hu_sql = "";
//		String renjun_sql="";
//		if(type.equals("1")){//当前
//			hu_sql = "update da_current_expenditure set "+where+" where da_household_id="+pkid;
//			
//			renjun_sql = "update da_household vb set v24=("+
//					 	 " select vc.num from ("+
//					 	 " SELECT ROUND((a.v39-f.v31)/a.v9,2) num,a.pkid FROM"+
//					 	 " (select e.pkid,d.v39,e.v9 from da_current_income d join da_household e on d.da_household_id=e.pkid  where e.pkid='"+ pkid +"')a join"+
//					 	 " da_current_expenditure f ON f.da_household_id = a.pkid"+
//					 	 " ) vc where vc.pkid=vb.pkid"+
//					 	 " ) where vb.pkid='"+ pkid +"'";
//			
//			
//			
//		}else if(type.equals("2")){//帮扶后
//			hu_sql = "update da_helpback_expenditure set "+where+" where da_household_id="+pkid;
//		}
//		try{
//			this.getBySqlMapper.update(hu_sql);
//			this.getBySqlMapper.update(renjun_sql);//执行插入人均年收入
//			
//			HttpSession session = request.getSession();
//			JSONObject json = new JSONObject();
//			if(session.getAttribute("Login_map")!=null){//验证session不为空
//				Map<String,String> Login_map = (Map)session.getAttribute("Login_map");//用户的user表内容
//				SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
//				
//				if(type.equals("1")){//当前
//					String hql1="insert into da_record(record_table,record_pkid,record_type,record_p_t,record_phone,record_name,record_time,record_mou_1,record_mou_2)"+
//							" VALUES ('da_household',"+pkid+",'修改',2,'','"+Login_map.get("col_account")+"','"+simpleDate.format(new Date())+"','当前收支','当前支出情况')";
//					this.getBySqlMapper.insert(hql1);
//				}else if(type.equals("2")){//帮扶后
//					String hql1="insert into da_record(record_table,record_pkid,record_type,record_p_t,record_phone,record_name,record_time,record_mou_1,record_mou_2)"+
//							" VALUES ('da_household',"+pkid+",'修改',2,'','"+Login_map.get("col_account")+"','"+simpleDate.format(new Date())+"','帮扶后收支分析','帮扶后支出情况')";
//					this.getBySqlMapper.insert(hql1);
//				}
//			}
//			
//			response.getWriter().write("1");
//		}catch (Exception e){
//			response.getWriter().write("0");
//		}
//	}
//	/**
//	 * 保存帮扶计划
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException 
//	 */
//	@RequestMapping("getInput_19.do")
//	public void getInput_19(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		
//		String pkid = request.getParameter("pkid");
//		String form_val = request.getParameter("form_val");
//		JSONObject form_json = JSONObject.fromObject(form_val);//表单数据
//		
//		String where = "";
//		
//		if(form_json.get("v1")!=null&&!form_json.get("v1").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			String dest = "";
//	        if (form_json.get("v1")!=null) {
//	            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
//	            Matcher m = p.matcher(form_json.get("v1").toString());
//	            dest = m.replaceAll("");
//	        }
//			where += "V1='"+dest+"',";
//		}else{
//			where += "V1=null,";
//		}
//		
//		if(form_json.get("v2")!=null&&!form_json.get("v2").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			String dest = "";
//	        if (form_json.get("v2")!=null) {
//	            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
//	            Matcher m = p.matcher(form_json.get("v2").toString());
//	            dest = m.replaceAll("");
//	        }
//			where += "V2='"+dest+"',";
//		}else{
//			where += "V2=null,";
//		}
//		
//		if(form_json.get("v3")!=null&&!form_json.get("v3").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//			String dest = "";
//	        if (form_json.get("v3")!=null) {
//	            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
//	            Matcher m = p.matcher(form_json.get("v3").toString());
//	            dest = m.replaceAll("");
//	        }
//			where += "V3='"+dest+"',";
//		}else{
//			where += "V3=null,";
//		}
//		
//		if(where.length()>0){
//			where = where.substring(0, where.length()-1);
//		}
//		
//		try{
//			this.getBySqlMapper.update("update da_help_info set "+where+" where da_household_id="+pkid);
//			
//			HttpSession session = request.getSession();
//			JSONObject json = new JSONObject();
//			if(session.getAttribute("Login_map")!=null){//验证session不为空
//				Map<String,String> Login_map = (Map)session.getAttribute("Login_map");//用户的user表内容
//				SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
//				String hql1="insert into da_record(record_table,record_pkid,record_type,record_p_t,record_phone,record_name,record_time,record_mou_1,record_mou_2)"+
//						" VALUES ('da_household',"+pkid+",'修改',2,'','"+Login_map.get("col_account")+"','"+simpleDate.format(new Date())+"','帮扶责任人和计划','帮扶计划')";
//				this.getBySqlMapper.insert(hql1);
//			}
//			response.getWriter().write("1");
//		}catch (Exception e){
//			response.getWriter().write("0");
//		}
//	}
//	/**
//	 * 保存脱贫计划
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException 
//	 */
//	@RequestMapping("getInput_20.do")
//	public void getInput_20(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		
//		String pkid = request.getParameter("pkid");
//		String form_val = request.getParameter("form_val");
//		JSONObject form_json = JSONObject.fromObject(form_val);//表单数据
//		
//		String returnval = "";
//		String year[] = {"2016","2017","2018","2019"};
//		for(int i = 0;i<year.length;i++){
//			String where = "";
//			String sql = "";
//			
//			if(form_json.get("v2")!=null&&!form_json.get("v2").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//				where += "V2='"+form_json.get("v2")+"',";
//			}else{
//				where += "V2=null,";
//			}
//			
//			if(form_json.get("v3_"+year[i])!=null&&!form_json.get("v3_"+year[i]).equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//				where += "V3='"+form_json.get("v3_"+year[i])+"',";
//			}else{
//				where += "V3=null,";
//			}
//			
//			if(form_json.get("v4_"+year[i])!=null&&!form_json.get("v4_"+year[i]).equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//				where += "V4='"+form_json.get("v4_"+year[i])+"',";
//			}else{
//				where += "V4=null,";
//			}
//			
//			if(form_json.get("v5")!=null&&!form_json.get("v5").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//				where += "V5='"+form_json.get("v5")+"',";
//			}else{
//				where += "V5=null,";
//			}
//			
//			if(form_json.get("v6_"+year[i])!=null&&!form_json.get("v6_"+year[i]).equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//				where += "V6='"+form_json.get("v6_"+year[i])+"',";
//			}else{
//				where += "V6=null,";
//			}
//			
//			if(form_json.get("v7_"+year[i])!=null&&!form_json.get("v7_"+year[i]).equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//				where += "V7='"+form_json.get("v7_"+year[i])+"',";
//			}else{
//				where += "V7=null,";
//			}
//			
//			if(form_json.get("v8")!=null&&!form_json.get("v8").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//				where += "V8='"+form_json.get("v8")+"',";
//			}else{
//				where += "V8=null,";
//			}
//			
//			if(form_json.get("v9_"+year[i])!=null&&!form_json.get("v9_"+year[i]).equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//				where += "V9='"+form_json.get("v9_"+year[i])+"',";
//			}else{
//				where += "V9=null,";
//			}
//			
//			if(form_json.get("v10_"+year[i])!=null&&!form_json.get("v10_"+year[i]).equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//				where += "V10='"+form_json.get("v10_"+year[i])+"',";
//			}else{
//				where += "V10=null,";
//			}
//			
//			if(form_json.get("v11")!=null&&!form_json.get("v11").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//				where += "V11='"+form_json.get("v11")+"',";
//			}else{
//				where += "V11=null,";
//			}
//			
//			if(form_json.get("v12_"+year[i])!=null&&!form_json.get("v12_"+year[i]).equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//				where += "V12='"+form_json.get("v12_"+year[i])+"',";
//			}else{
//				where += "V12=null,";
//			}
//			
//			if(form_json.get("v13_"+year[i])!=null&&!form_json.get("v13_"+year[i]).equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//				where += "V13='"+form_json.get("v13_"+year[i])+"',";
//			}else{
//				where += "V13=null,";
//			}
//			
//			if(form_json.get("v14")!=null&&!form_json.get("v14").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//				where += "V14='"+form_json.get("v14")+"',";
//			}else{
//				where += "V14=null,";
//			}
//			
//			if(form_json.get("v15_"+year[i])!=null&&!form_json.get("v15_"+year[i]).equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//				where += "V15='"+form_json.get("v15_"+year[i])+"',";
//			}else{
//				where += "V15=null,";
//			}
//			
//			if(form_json.get("v16_"+year[i])!=null&&!form_json.get("v16_"+year[i]).equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//				where += "V16='"+form_json.get("v16_"+year[i])+"',";
//			}else{
//				where += "V16=null,";
//			}
//			
//			if(form_json.get("v17")!=null&&!form_json.get("v17").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//				where += "V17='"+form_json.get("v17")+"',";
//			}else{
//				where += "V17=null,";
//			}
//			
//			if(form_json.get("v18_"+year[i])!=null&&!form_json.get("v18_"+year[i]).equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//				where += "V18='"+form_json.get("v18_"+year[i])+"',";
//			}else{
//				where += "V18=null,";
//			}
//			
//			if(form_json.get("v19_"+year[i])!=null&&!form_json.get("v19_"+year[i]).equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//				where += "V19='"+form_json.get("v19_"+year[i])+"',";
//			}else{
//				where += "V19=null,";
//			}
//			
//			if(where.length()>0){
//				where = where.substring(0, where.length()-1);
//			}
//			
//			try{
//				this.getBySqlMapper.update("update da_help_plan set "+where+" where da_household_id="+pkid+" and v1='"+year[i]+"'; ");
//				returnval = "1";
//			}catch (Exception e){
//				response.getWriter().write("0");
//			}
//		}
//		HttpSession session = request.getSession();
//		JSONObject json = new JSONObject();
//		if(session.getAttribute("Login_map")!=null){//验证session不为空
//			Map<String,String> Login_map = (Map)session.getAttribute("Login_map");//用户的user表内容
//			SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
//			String hql1="insert into da_record(record_table,record_pkid,record_type,record_p_t,record_phone,record_name,record_time,record_mou_1,record_mou_2)"+
//					" VALUES ('da_household',"+pkid+",'修改',2,'','"+Login_map.get("col_account")+"','"+simpleDate.format(new Date())+"','帮扶责任人和计划','脱贫计划')";
//			this.getBySqlMapper.insert(hql1);
//		}
//		response.getWriter().write(returnval);
//	}
//	/**
//	 * 查找帮扶责任人信息
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping("getInput_21.do")
//	public void getInput_21(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		String sys_company_id = request.getParameter("sys_company_id").trim();
//		String huid = request.getParameter("huid").trim();
//		String where = "";
//		if(sys_company_id!=null&&!sys_company_id.equals("")){
//			where = " where t2.sys_company_id="+sys_company_id+" and t3.sys_personal_id is null";
//		}
//		String st_sql = "SELECT t1.pkid,t1.col_name,t2.v1 as com_name,t1.col_post,t1.telephone from sys_personal t1 join da_company t2 on t1.da_company_id=t2.pkid "
//				+ "left join (select * from sys_personal_household_many where da_household_id="+huid+") t3 on t1.pkid=t3.sys_personal_id"+where;
//		String count_sql = "SELECT count(*) from sys_personal t1 join da_company t2 on t1.da_company_id=t2.pkid "
//				+ "left join (select * from sys_personal_household_many where da_household_id="+huid+") t3 on t1.pkid=t3.sys_personal_id"+where;
//		
//		int total = this.getBySqlMapper.findrows(count_sql);
//		List<Map> Patient_st_List = this.getBySqlMapper.findRecords(st_sql);
//		
//		JSONArray jsa=new JSONArray();
//		if(Patient_st_List.size()>0){
//			
//			for(int i = 0;i<Patient_st_List.size();i++){
//				Map Patient_st_map = Patient_st_List.get(i);
//				JSONObject val = new JSONObject();
//				for (Object key : Patient_st_map.keySet()) {
//					
//					
//					val.put("pkid", Patient_st_map.get("PKID"));
//					
//					if(Patient_st_map.get("COL_NAME").toString().indexOf("、")>-1){
//						val.put("col_name", Patient_st_map.get("COL_NAME").toString().replaceAll("、","<br>"));
//					}else{
//						val.put("col_name", Patient_st_map.get("COL_NAME"));
//					}
//					
//					
//					if(Patient_st_map.get("COM_NAME").toString().indexOf("、")>-1){
//						val.put("com_name", Patient_st_map.get("COM_NAME").toString().replaceAll("、","<br>"));
//					}else if(Patient_st_map.get("COM_NAME").toString().length()>15){
//						String s = Patient_st_map.get("COM_NAME").toString();
//						s = s.substring(0, 10)+"<br>"+s.substring(10);
//						val.put("com_name", s);
//					}else{
//						val.put("com_name", Patient_st_map.get("COM_NAME"));
//					}
//					
//					if(Patient_st_map.get("COL_POST").toString().indexOf("、")>-1){
//						val.put("col_post", Patient_st_map.get("COL_POST").toString().replaceAll("、","<br>"));
//					}else if(Patient_st_map.get("COL_POST").toString().length()>15){
//						String s = Patient_st_map.get("COL_POST").toString();
//						s = s.substring(0, 10)+"<br>"+s.substring(10);
//						val.put("col_post", s);
//					}else{
//						val.put("col_post", Patient_st_map.get("COL_POST"));
//					}
//					
//					if(Patient_st_map.get("TELEPHONE").toString().indexOf("、")>-1){
//						val.put("telephone", Patient_st_map.get("TELEPHONE").toString().replaceAll("、","<br>"));
//					}else if(Patient_st_map.get("TELEPHONE").toString().length()>15){
//						String s = Patient_st_map.get("TELEPHONE").toString();
//						s = s.substring(0, 10)+"<br>"+s.substring(11);
//						val.put("telephone", s);
//					}else{
//						val.put("telephone", Patient_st_map.get("TELEPHONE"));
//					}
//					
//				}
//				jsa.add(val);
//			}
//		}
//		
//		JSONObject json = new JSONObject();
//		json.put("message", "");
//		json.put("value", jsa);
//		json.put("code", total);
//		json.put("redirect", "");
//		
//		response.getWriter().write(json.toString());
//	}
//	/**
//	 * 保存帮扶责任人与贫困户之间的关系
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException
//	 */
//	@RequestMapping("getInput_22.do")
//	public void getInput_22(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		
//		String pkid = request.getParameter("pkid").trim();
//		String huid = request.getParameter("huid").trim();
//		
//		if(pkid!=null&&!pkid.equals("")){
//			if(huid!=null&&!huid.equals("")){
//				try{
//					this.getBySqlMapper.insert("INSERT INTO sys_personal_household_many(sys_personal_id,da_household_id) VALUES("+pkid+","+huid+")");
//					
//					HttpSession session = request.getSession();
//					JSONObject json = new JSONObject();
//					if(session.getAttribute("Login_map")!=null){//验证session不为空
//						Map<String,String> Login_map = (Map)session.getAttribute("Login_map");//用户的user表内容
//						SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
//						String hql1="insert into da_record(record_table,record_pkid,record_type,record_p_t,record_phone,record_name,record_time,record_mou_1,record_mou_2)"+
//								" VALUES ('da_household',"+huid+",'添加',2,'','"+Login_map.get("col_account")+"','"+simpleDate.format(new Date())+"','帮扶责任人和计划','指定帮扶责任人')";
//						this.getBySqlMapper.insert(hql1);
//					}
//					
//					response.getWriter().write("1");
//				}catch (Exception e){
//					response.getWriter().write("0");
//				}
//			}
//		}
//	}
//	/**
//	 *  解除责任人与贫困户的关系
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException
//	 */
//	@RequestMapping("getInput_23.do")
//	public void getInput_23(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		
//		String pkid = request.getParameter("pkid").trim();
//		
//		String hou_sql = "select da_household_id from sys_personal_household_many where pkid="+pkid;
//		int da_household_id = this.getBySqlMapper.findrows(hou_sql);
//		
//		try{
//			this.getBySqlMapper.delete("delete from sys_personal_household_many where pkid="+pkid);
//			
//			HttpSession session = request.getSession();
//			JSONObject json = new JSONObject();
//			if(session.getAttribute("Login_map")!=null){//验证session不为空
//				Map<String,String> Login_map = (Map)session.getAttribute("Login_map");//用户的user表内容
//				SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
//				String hql1="insert into da_record(record_table,record_pkid,record_type,record_p_t,record_phone,record_name,record_time,record_mou_1,record_mou_2)"+
//						" VALUES ('da_household',"+da_household_id+",'删除',2,'','"+Login_map.get("col_account")+"','"+simpleDate.format(new Date())+"','帮扶责任人和计划','解除帮扶责任人')";
//				this.getBySqlMapper.insert(hql1);
//			}
//			response.getWriter().write("1");
//		}catch (Exception e){
//			response.getWriter().write("0");
//		}
//	}
//	/**
//	 * 根据户主ID获取帮扶责任人列表
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException
//	 */
//	@RequestMapping("getInput_24.do")
//	public void getInput_24(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		String pkid = request.getParameter("pkid").trim();
//		JSONObject zong = new JSONObject ();
//		
//		//相关的帮扶责任人人
//		String sql="select x.pkid,y.col_name,y.com_name,y.col_post,y.telephone from sys_personal_household_many x,"
//				+ "(SELECT t1.pkid,t1.col_name,t2.v1 as com_name,t1.col_post,t1.telephone from sys_personal t1 join da_company t2 on t1.da_company_id=t2.pkid) y "
//				+ "where x.sys_personal_id=y.pkid and da_household_id="+pkid;
//		List<Map> list=getBySqlMapper.findRecords(sql);
//		
//		JSONArray jsa=new JSONArray();
//		for(Map val:list){
//			
//			JSONObject obj=new JSONObject ();
//			obj.put("pkid",val.get("PKID")==null?"":val.get("PKID"));
//			obj.put("col_name",val.get("COL_NAME")==null?"":val.get("COL_NAME"));
//			obj.put("com_name",val.get("COM_NAME")==null?"":val.get("COM_NAME"));
//			obj.put("col_post",val.get("COL_POST")==null?"":val.get("COL_POST"));
//			obj.put("telephone",val.get("TELEPHONE")==null?"":val.get("TELEPHONE"));
//			jsa.add(obj);
//		}
//		zong.put("bangfu", jsa);
//		
//		String da_help_info_sql = "select * from da_help_info where da_household_id="+pkid;
//		List<Map> help_list=getBySqlMapper.findRecords(da_help_info_sql);
//		JSONObject helpjsa=new JSONObject ();
//		for(Map val:help_list){
//			
//			helpjsa.put("v1",val.get("V1")==null?"":val.get("V1"));
//			helpjsa.put("v2",val.get("V2")==null?"":val.get("V2"));
//			helpjsa.put("v3",val.get("V3")==null?"":val.get("V3"));
//		}
//		zong.put("da_help_info", helpjsa);
//		
//		//帮扶目标计划等内容
//		String xian_sql="select * from da_help_plan where da_household_id="+pkid+" order by v1" ;
//		List<Map> xian_list=getBySqlMapper.findRecords(xian_sql);
//		
//		if(xian_list.size()>0){
//			JSONObject obj=new JSONObject ();
//			for(int i = 0;i<xian_list.size();i++){
//				Map val = xian_list.get(i);
//				String v1 = val.get("V1")==null?"":val.get("V1").toString();//年度
//				obj.put("v2",val.get("V2")==null?"":val.get("V2"));//贫困户人均可支配收入现状
//				obj.put("v3_"+v1,val.get("V3")==null?"":val.get("V3"));
//				obj.put("v4_"+v1,val.get("V4")==null?"":val.get("V4"));
//				obj.put("v5",val.get("V5")==null?"":val.get("V5"));//有安全住房现状
//				obj.put("v6_"+v1,val.get("V6")==null?"":val.get("V6"));
//				obj.put("v7_"+v1,val.get("V7")==null?"":val.get("V7"));
//				obj.put("v8",val.get("V8")==null?"":val.get("V8"));//无因贫辍学学生现状
//				obj.put("v9_"+v1,val.get("V9")==null?"":val.get("V9"));
//				obj.put("v10_"+v1,val.get("V10")==null?"":val.get("V10"));
//				obj.put("v11",val.get("V11")==null?"":val.get("V11"));//无因病致贫返贫现状
//				obj.put("v12_"+v1,val.get("V12")==null?"":val.get("V12"));
//				obj.put("v13_"+v1,val.get("V13")==null?"":val.get("V13"));
//				obj.put("v14",val.get("V14")==null?"":val.get("V14"));//参加新型农村合作医疗现状
//				obj.put("v15_"+v1,val.get("V15")==null?"":val.get("V15"));
//				obj.put("v16_"+v1,val.get("V16")==null?"":val.get("V16"));
//				obj.put("v17",val.get("V17")==null?"":val.get("V17"));//参加城乡居民基本养老保险现状
//				obj.put("v18_"+v1,val.get("V18")==null?"":val.get("V18"));
//				obj.put("v19_"+v1,val.get("V19")==null?"":val.get("V19"));
//			}
//			zong.put("help_info", obj);
//		}else{
//			try{
//				//没有查询到贫困户的记录，需要添加记录
//				this.getBySqlMapper.insert("insert into da_help_plan(da_household_id,v1) values("+pkid+",'2016')");
//				this.getBySqlMapper.insert("insert into da_help_plan(da_household_id,v1) values("+pkid+",'2017')");
//				this.getBySqlMapper.insert("insert into da_help_plan(da_household_id,v1) values("+pkid+",'2018')");
//				this.getBySqlMapper.insert("insert into da_help_plan(da_household_id,v1) values("+pkid+",'2019')");
//			}catch (Exception e){
//				
//			}
//		}
//		response.getWriter().write(zong.toString());
//		response.getWriter().close();
//	}
//	/**
//	 * 修改台账措施
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException
//	 */
//	@RequestMapping("getInput_25.do")
//	public void getInput_25(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		String pkid = request.getParameter("pkid");
//		String form_val = request.getParameter("form_val");
//		JSONObject form_json = JSONObject.fromObject(form_val);//表单数据
//		
//		String v1 = form_json.get("v1").toString();
//		String v2 = form_json.get("v2").toString();
//		
//		String returnval = "";
//		String year[] = {"2016","2017","2018","2019"};
//		for(int i = 0;i<year.length;i++){
//			String where = "";
//			String sql = "";
//			
//			if(form_json.get("v3")!=null&&!form_json.get("v3").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//				where += "v3='"+form_json.get("v3")+"',";
//			}else{
//				where += "v3=null,";
//			}
//			
//			if(form_json.get("v4_"+year[i])!=null&&!form_json.get("v4_"+year[i]).equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//				where += "v4='"+form_json.get("v4_"+year[i])+"',";
//			}else{
//				where += "v4=null,";
//			}
//			
//			if(form_json.get("v5_"+year[i])!=null&&!form_json.get("v5_"+year[i]).equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//				where += "v5='"+form_json.get("v5_"+year[i])+"',";
//			}else{
//				where += "v5=null,";
//			}
//			
//			if(form_json.get("v6_"+year[i])!=null&&!form_json.get("v6_"+year[i]).equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//				where += "v6='"+form_json.get("v6_"+year[i])+"',";
//			}else{
//				where += "v6=null,";
//			}
//			
//			if(where.length()>0){
//				where = where.substring(0, where.length()-1);
//			}
//			try{
//				this.getBySqlMapper.update("update da_help_tz_measures set "+where+" where da_household_id="+pkid+" and v7='"+year[i]+"' and v1='"+v1+"' and v2='"+v2+"'; ");
//				returnval = "1";
//			}catch (Exception e){
//				response.getWriter().write("0");
//			}
//		}
//		HttpSession session = request.getSession();
//		JSONObject json = new JSONObject();
//		if(session.getAttribute("Login_map")!=null){//验证session不为空
//			Map<String,String> Login_map = (Map)session.getAttribute("Login_map");//用户的user表内容
//			SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
//			String hql1="insert into da_record(record_table,record_pkid,record_type,record_p_t,record_phone,record_name,record_time,record_mou_1,record_mou_2)"+
//					" VALUES ('da_household',"+pkid+",'修改',2,'','"+Login_map.get("col_account")+"','"+simpleDate.format(new Date())+"','帮扶措施','"+v1+"-"+v2+"')";
//			this.getBySqlMapper.insert(hql1);
//		}
//		response.getWriter().write(returnval);
//	}
//	/**
//	 * 保存台账措施
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException
//	 */
//	@RequestMapping("getInput_26.do")
//	public void getInput_26(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		String pkid = request.getParameter("pkid");
//		String form_val = request.getParameter("form_val");
//		JSONObject form_json = JSONObject.fromObject(form_val);//表单数据
//		
//		String returnval = "";
//		String year[] = {"2016","2017","2018","2019"};
//		for(int i = 0;i<year.length;i++){
//			String v1 = "",v2 = "",v3 = "",v4 = "",v5 = "",v6 = "";
//			String sql = "";
//			
//			if(form_json.get("v1")!=null&&!form_json.get("v1").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//				String str[] = form_json.get("v1").toString().split("-");
//				v1 = str[0];
//				v2 = str[1];
//			}
//			
//			if(form_json.get("v3")!=null&&!form_json.get("v3").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//				v3 = form_json.get("v3").toString();
//			}
//			
//			if(form_json.get("v4_"+year[i])!=null&&!form_json.get("v4_"+year[i]).equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//				v4 = form_json.get("v4_"+year[i]).toString();
//			}
//			
//			if(form_json.get("v5_"+year[i])!=null&&!form_json.get("v5_"+year[i]).equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//				v5 = form_json.get("v5_"+year[i]).toString();
//			}
//			
//			if(form_json.get("v6_"+year[i])!=null&&!form_json.get("v6_"+year[i]).equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
//				v6 = form_json.get("v6_"+year[i]).toString();
//			}
//			
//			sql = "insert into da_help_tz_measures(da_household_id,v1,v2,v3,v4,v5,v6,v7) values('"+pkid+"','"+v1+"','"+v2+"','"+v3+"','"+v4+"','"+v5+"','"+v6+"','"+year[i]+"'); ";
//			try{
//				this.getBySqlMapper.insert(sql);
//				returnval = "1";
//			}catch (Exception e){
//				response.getWriter().write("0");
//			}
//		}
//		HttpSession session = request.getSession();
//		JSONObject json = new JSONObject();
//		if(session.getAttribute("Login_map")!=null){//验证session不为空
//			Map<String,String> Login_map = (Map)session.getAttribute("Login_map");//用户的user表内容
//			SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
//			String hql1="insert into da_record(record_table,record_pkid,record_type,record_p_t,record_phone,record_name,record_time,record_mou_1,record_mou_2)"+
//					" VALUES ('da_household',"+pkid+",'添加',2,'','"+Login_map.get("col_account")+"','"+simpleDate.format(new Date())+"','帮扶措施','"+form_json.get("v1")+"')";
//			this.getBySqlMapper.insert(hql1);
//		}
//		response.getWriter().write(returnval);
//	}
//	/**
//	 * 获取台账未使用的帮扶措施条目
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException
//	 */
//	@RequestMapping("getInput_27.do")
//	public void getInput_27(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		String pkid = request.getParameter("pkid");
//		
//		String str1[] = {"主导产业-规模种植","主导产业-设施农业","主导产业-特色种植","主导产业-规模养殖","主导产业-棚圈建设","主导产业-特色养殖","主导产业-小额贷款","主导产业-电商扶贫","主导产业-旅游扶贫","主导产业-光伏扶贫",
//				"主导产业-农业保险","主导产业-农机局购置补贴","主导产业-危房改造","主导产业-安全饮水","主导产业-易地扶贫搬迁","生态保护-实施生态项目","生态保护-安置护林员","教育扶贫-学前教育","教育扶贫-义务教育","教育扶贫-高中、中职教育",
//				"教育扶贫-高等教育","医疗救助-新农合住院报销","医疗救助-新农合参合费用补助","医疗救助-大病保险报销","社会救助和保障-农村低保","社会救助和保障-农村五保供养","社会救助和保障-农村特困家庭医疗救助","社会救助和保障-临时救助",
//				"社会救助和保障-灾害救助","社会救助和保障-城乡居民社会养老保险","社会救助和保障-高龄老人生活补贴","社会救助和保障-农村参加养老保险人员丧葬费","社会救助和保障-残疾人补贴","劳动力培训-有培训需要的劳动力参加培训","其它-其它"};
//		JSONArray jsa=new JSONArray();
//		
//		String people_sql = " select v1,v2 from da_help_tz_measures where da_household_id="+pkid+" group by v1,v2 ";
//		List<Map> Patient_st_List = this.getBySqlMapper.findRecords(people_sql);
//		if(Patient_st_List.size()>0){
//			
//			String str[] = new String[Patient_st_List.size()];
//			for(int i = 0;i<Patient_st_List.size();i++){
//				Map Patient_st_map = Patient_st_List.get(i);
//				
//				str[i] = Patient_st_map.get("v1")+"-"+Patient_st_map.get("v2");
//				
//			}
//			String[] arrResult = arrContrast(str1, str);
//			
//			if(arrResult.length>1){
//				for (String strResult : arrResult) {
//					jsa.add(strResult);
//				}
//				response.getWriter().write(jsa.toString());
//			}else{
//				response.getWriter().write("");
//			}
//		}else{
//			for (String strResult : str1) {
//				jsa.add(strResult);
//			}
//			response.getWriter().write(jsa.toString());
//		}
//	}
//	/**
//	 *  删除台账措施
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException
//	 */
//	@RequestMapping("getInput_28.do")
//	public void getInput_28(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		String pkid = request.getParameter("pkid").trim();//数据表ID
//		String v1 = request.getParameter("v1").trim();
//		String v2 = request.getParameter("v2").trim();
//		
//		try{
//			this.getBySqlMapper.delete("delete from da_help_tz_measures where da_household_id="+pkid+" and v1='"+v1+"' and v2='"+v2+"'");
//			
//			HttpSession session = request.getSession();
//			JSONObject json = new JSONObject();
//			if(session.getAttribute("Login_map")!=null){//验证session不为空
//				Map<String,String> Login_map = (Map)session.getAttribute("Login_map");//用户的user表内容
//				SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
//				String hql1="insert into da_record(record_table,record_pkid,record_type,record_p_t,record_phone,record_name,record_time,record_mou_1,record_mou_2)"+
//						" VALUES ('da_household',"+pkid+",'删除',2,'','"+Login_map.get("col_account")+"','"+simpleDate.format(new Date())+"','帮扶措施','"+v1+"-"+v2+"')";
//				this.getBySqlMapper.insert(hql1);
//			}
//			
//			response.getWriter().write("1");
//		}catch (Exception e){
//			response.getWriter().write("0");
//		}
//	}
//	/**
//	 * 台账-帮扶措施
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException
//	 */
//	@RequestMapping("getInput_29.do")
//	public void getInput_29(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		String pkid = request.getParameter("pkid").trim();//贫困户ID
//
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
//			JSONArray jsa=new JSONArray();
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
//				val.put("caozuo", "<button  type=\"button\" class=\"btn btn-primary btn-xs jbqk\" onclick=\"update_tz_cuoshi('"+v1+"','"+v2+"','"+v3+"',"
//						+ "'"+v4_2016+"','"+v5_2016+"','"+v6_2016+"','"+v4_2017+"','"+v5_2017+"','"+v6_2017+"','"+v4_2018+"','"+v5_2018+"','"+v6_2018+"','"+v4_2019+"','"+v5_2019+"','"+v6_2019+"');\"><i class=\"fa fa-pencil\"></i> 编辑 </button>&nbsp;&nbsp;&nbsp;&nbsp;<button  type=\"button\" class=\"btn btn-primary btn-xs jbqk\" onclick=\"del_tz('"+v1+"','"+v2+"','"+pkid+"');\"><i class=\"fa fa-remove\"></i> 删除 </button>");
//				jsa.add(val);
//			}
//			JSONObject json = new JSONObject();
//			json.put("rows", jsa); //这里的 rows 和total 的key 是固定的 
//			response.getWriter().write(jsa.toString());
//		}
//	}
//	/**
//	 * 初始化数据
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException
//	 */
//	@RequestMapping("getInput_30.do")
//	public void getInput_30(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		String pkid = request.getParameter("pkid").trim();//贫困户ID
//		String type = request.getParameter("type").trim();//类型，区分措施，走访，成效
//		JSONObject zong = new JSONObject ();
//		
//		String sql = "";
//		if(type.equals("1")){//帮扶措施
//			sql = "select * from da_help_measures where da_household_id="+pkid+" order by v1 desc";
//		}else if(type.equals("2")){//走访情况
//			sql = "select * from da_help_visit where da_household_id="+pkid+" order by v1 desc";
//		}else if(type.equals("3")){//帮扶成效
//			sql = "select * from da_help_results where da_household_id="+pkid+" order by v1 desc";
//		}
//		if(!sql.equals("")){
//			List<Map> list=getBySqlMapper.findRecords(sql);
//			JSONArray jsa=new JSONArray();
//			for(Map val:list){
//				JSONObject obj=new JSONObject ();
//				obj.put("pkid",val.get("pkid")==null?"":val.get("pkid"));
//				obj.put("v1",val.get("v1")==null?"":val.get("v1"));
//				obj.put("v2",val.get("v2")==null?"":val.get("v2"));
//				obj.put("v3",val.get("v3")==null?"":val.get("v3"));
//				if(type.equals("2")){
//					obj.put("sys_personal_id",val.get("sys_personal_id")==null?"":val.get("sys_personal_id"));
//				}
//				jsa.add(obj);
//			}
//			zong.put("table", jsa);
//		}
//			
//		response.getWriter().write(zong.toString());
//		response.getWriter().close();
//	}
//	/**
//	 * 保存文字信息
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException
//	 */
//	@RequestMapping("getInput_31.do")
//	public void getInput_31(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		String pkid = request.getParameter("pkid").trim();//数据表ID
//		String v1 = request.getParameter("v1").trim();
//		String v2 = request.getParameter("v2").trim();
//		String v3 = request.getParameter("v3").trim();
//		String type = request.getParameter("type").trim();
//		
//		String hu_sql = "";
//		String str = "";
//		if(type.equals("1")){
//			
//			String dest = "";
//	        if (v2!=null) {
//	            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
//	            Matcher m = p.matcher(v2);
//	            dest = m.replaceAll("");
//	        }
//			
//			hu_sql="INSERT INTO da_help_measures(da_household_id,v1,v2,v3) VALUES"+
//					"('"+pkid+"','"+v1+"','"+dest+"','"+v3+"')";
//			str = "帮扶措施";
//		}else if(type.equals("2")){
//			String dest = "";
//	        if (v3!=null) {
//	            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
//	            Matcher m = p.matcher(v3);
//	            dest = m.replaceAll("");
//	        }
//			hu_sql="INSERT INTO da_help_visit(da_household_id,v1,v2,v3) VALUES"+
//					"('"+pkid+"','"+v1+"','"+v2+"','"+dest+"')";
//			str = "走访情况";
//		}else if(type.equals("3")){
//			String dest = "";
//	        if (v2!=null) {
//	            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
//	            Matcher m = p.matcher(v2);
//	            dest = m.replaceAll("");
//	        }
//			hu_sql="INSERT INTO da_help_results(da_household_id,v1,v2,v3) VALUES"+
//					"('"+pkid+"','"+v1+"','"+dest+"','"+v3+"')";
//			str = "帮扶成效";
//		}
//		try{
//			this.getBySqlMapper.insert(hu_sql);
//			
//			HttpSession session = request.getSession();
//			JSONObject json = new JSONObject();
//			if(session.getAttribute("Login_map")!=null){//验证session不为空
//				Map<String,String> Login_map = (Map)session.getAttribute("Login_map");//用户的user表内容
//				SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
//				String hql1="insert into da_record(record_table,record_pkid,record_type,record_p_t,record_phone,record_name,record_time,record_mou_1,record_mou_2)"+
//						" VALUES ('da_household',"+pkid+",'添加',2,'','"+Login_map.get("col_account")+"','"+simpleDate.format(new Date())+"','"+str+"','')";
//				this.getBySqlMapper.insert(hql1);
//			}
//			
//			response.getWriter().write("1");
//		}catch (Exception e){
//			response.getWriter().write("0");
//		}
//	}
//	/**
//	 * 修改文字信息
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException
//	 */
//	@RequestMapping("getInput_32.do")
//	public void getInput_32(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		String pkid = request.getParameter("pkid").trim();//数据表ID
//		String v1 = request.getParameter("v1").trim();
//		String v2 = request.getParameter("v2").trim();
//		String v3 = request.getParameter("v3").trim();
//		String type = request.getParameter("type").trim();
//		
//		String hu_sql = "";
//		String str = "";
//		String hou_sql = "";
//		if(type.equals("1")){
//			hu_sql = "update da_help_measures set v1='"+v1+"',v2='"+v2+"',v3='"+v3+"' where pkid="+pkid;
//			str = "帮扶措施";
//			hou_sql = "select da_household_id from da_help_measures where pkid="+pkid;
//		}else if(type.equals("2")){
//			hu_sql = "update da_help_visit set v1='"+v1+"',v2='"+v2+"',v3='"+v3+"' where pkid="+pkid;
//			str = "走访情况";
//			hou_sql = "select da_household_id from da_help_visit where pkid="+pkid;
//		}else if(type.equals("3")){
//			hu_sql = "update da_help_results set v1='"+v1+"',v2='"+v2+"',v3='"+v3+"' where pkid="+pkid;
//			str = "帮扶成效";
//			hou_sql = "select da_household_id from da_help_results where pkid="+pkid;
//		}
//		try{
//			this.getBySqlMapper.update(hu_sql);
//			int da_household_id = this.getBySqlMapper.findrows(hou_sql);
//			
//			HttpSession session = request.getSession();
//			JSONObject json = new JSONObject();
//			if(session.getAttribute("Login_map")!=null){//验证session不为空
//				Map<String,String> Login_map = (Map)session.getAttribute("Login_map");//用户的user表内容
//				SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
//				String hql1="insert into da_record(record_table,record_pkid,record_type,record_p_t,record_phone,record_name,record_time,record_mou_1,record_mou_2)"+
//						" VALUES ('da_household',"+da_household_id+",'修改',2,'','"+Login_map.get("col_account")+"','"+simpleDate.format(new Date())+"','"+str+"','')";
//				this.getBySqlMapper.insert(hql1);
//			}
//			
//			response.getWriter().write("1");
//		}catch (Exception e){
//			response.getWriter().write("0");
//		}
//	}
//	/**
//	 * 删除文字信息
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException
//	 */
//	@RequestMapping("getInput_33.do")
//	public void getInput_33(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		String pkid = request.getParameter("pkid").trim();//数据表ID
//		String type = request.getParameter("type").trim();
//		
//		String hu_sql = "";
//		String str = "";
//		String hou_sql = "";
//		if(type.equals("1")){
//			hu_sql = "delete from da_help_measures where pkid="+pkid;
//			str = "帮扶措施";
//			hou_sql = "select da_household_id from da_help_measures where pkid="+pkid;
//		}else if(type.equals("2")){
//			hu_sql = "delete from da_help_visit where pkid="+pkid;
//			str = "走访情况";
//			hou_sql = "select da_household_id from da_help_visit where pkid="+pkid;
//		}else if(type.equals("3")){
//			hu_sql = "delete from da_help_results where pkid="+pkid;
//			str = "帮扶成效";
//			hou_sql = "select da_household_id from da_help_results where pkid="+pkid;
//		}
//		
//		try{
//			
//			int da_household_id = this.getBySqlMapper.findrows(hou_sql);
//			
//			HttpSession session = request.getSession();
//			JSONObject json = new JSONObject();
//			if(session.getAttribute("Login_map")!=null){//验证session不为空
//				Map<String,String> Login_map = (Map)session.getAttribute("Login_map");//用户的user表内容
//				SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
//				String hql1="insert into da_record(record_table,record_pkid,record_type,record_p_t,record_phone,record_name,record_time,record_mou_1,record_mou_2)"+
//						" VALUES ('da_household',"+da_household_id+",'删除',2,'','"+Login_map.get("col_account")+"','"+simpleDate.format(new Date())+"','"+str+"','')";
//				this.getBySqlMapper.insert(hql1);
//			}
//			
//			this.getBySqlMapper.delete(hu_sql);
//			
//			response.getWriter().write("1");
//		}catch (Exception e){
//			response.getWriter().write("0");
//		}
//	}
//	/**
//	 * 更新家庭人数
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param pkid
//	 */
//	public void getjiatingchengyuan(String pkid){
//		
//		String sql = "select count(*) from (select v9 from da_household where pkid="+pkid+" union ALL select v9 from da_member where da_household_id="+pkid+") t1";
//		int total = this.getBySqlMapper.findrows(sql);
//		try{
//			this.getBySqlMapper.update("update da_household set v9='"+total+"' where pkid="+pkid);
//			this.getBySqlMapper.update("update da_member set v9='"+total+"' where da_household_id="+pkid);
//		}catch (Exception e){
//		}
//	}
//	/**
//	 * 保存上传的图片信息
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param saveUrl
//	 * @param type
//	 * @param pkid
//	 * @param pic_size
//	 * @param pic_format
//	 * @return
//	 */
//	private String getSave_ImportData(String saveUrl, String type, String pkid, long pic_size, String pic_format){
//		
//		String count_st_sql = "select count(*) from da_pic where pic_type="+type+" and pic_pkid="+pkid;
//		int total = this.getBySqlMapper.findrows(count_st_sql);
//		
//		if(total>0){
//			try{
//				this.getBySqlMapper.delete("delete from da_pic where pic_type="+type+" and pic_pkid="+pkid);
//			}catch (Exception e){
//				return getMessage("1","照片替换失败","");
//			}
//		}
//		
//		String sql="INSERT INTO da_pic(pic_type,pic_pkid,pic_path,pic_size,pic_format) VALUES"+
//				"('"+type+"','"+pkid+"','"+saveUrl+"','"+pic_size+"','"+pic_format+"')";
//		try{
//			this.getBySqlMapper.insert(sql);
//	        return getMessage("0","照片保存成功",saveUrl);
//		}catch (Exception e){
//			return getMessage("1","服务器异常","");
//		}
//	}
//	/**
//	 *  错误信息处理
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param error
//	 * @param message 错误信息内容
//	 * @param path
//	 * @return 返回错误信息JSONObject字符串
//	 */
//	private String getMessage(String error, String message, String path){  
//        JSONObject obj = new JSONObject();  
//        obj.put("error", error);
//        obj.put("message", message);
//        if(error.equals("0")){
//        	obj.put("path", path);
//        }
//        return obj.toString();  
//    }
//
//	/**
//	 * 处理数组字符 
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param arr1
//	 * @param arr2
//	 * @return
//	 */
//    private String[] arrContrast(String[] arr1, String[] arr2){
//        List<String> list = new LinkedList<String>();  
//        for (String str : arr1) {//处理第一个数组,list里面的值为1,2,3,4  
//            if (!list.contains(str)) {
//                list.add(str);
//            }
//        }
//        for (String str : arr2) {//如果第二个数组存在和第一个数组相同的值，就删除  
//            if(list.contains(str)){
//                list.remove(str);
//            }
//        }
//        String[] result = {};//创建空数组  
//        return list.toArray(result);//List to Array  
//    }
//	/**
//	 * 保存图片信息
//	 * @author chendong
//	 * @date 2016年8月4日
//	 * @param pic_type
//	 * @param pic_pkid
//	 * @param pic_path
//	 * @param pic_size
//	 * @param pic_format
//	 * @return
//	 */
//	public String getSave_poth(String pic_type, String pic_pkid, String pic_path, long pic_size, String pic_format){
//		
//		String sql="INSERT INTO da_pic(pic_type,pic_pkid,pic_path,pic_size,pic_format) VALUES"+
//				"('"+pic_type+"','"+pic_pkid+"','"+pic_path+"','"+pic_size+"','"+pic_format+"')";
//		try{
//			this.getBySqlMapper.insert(sql);
//			return getMessage("0","图片上传成功","");
//		}catch (Exception e){
//			return getMessage("1","图片保存失败","");
//		}
//	}
}
