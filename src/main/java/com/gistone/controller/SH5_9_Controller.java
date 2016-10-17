package com.gistone.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.write.Alignment;
import jxl.write.Label;
import jxl.write.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gistone.MyBatis.config.GetBySqlMapper;
/**
 * 系统管理-帮扶干部管理
 * @author chendong
 *
 */
@RestController
@RequestMapping
public class SH5_9_Controller{

	@Autowired
	private GetBySqlMapper getBySqlMapper;
	/**
	 * @method 添加帮扶单位
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @author 呼树明，张晓翔
	 * @date 2016年9月11日
	 */
	@RequestMapping("addbfdw.do")
	public void addbfdw(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String bfdw_mc = request.getParameter("bfdw_mc");
		String bfdw_ldxm = request.getParameter("bfdw_ldxm");
		String bfdw_lddh = request.getParameter("bfdw_lddh");
		String bfdw_dz = request.getParameter("bfdw_dz");
		String json_level = request.getParameter("json_level");

		String add_sql = "insert into da_company(sys_company_id,v1,v2,v3,v4) values('"+json_level+"','"+bfdw_mc+"','"+bfdw_dz+"','"+bfdw_ldxm+"','"+bfdw_lddh+"')";
		try {
			this.getBySqlMapper.insert(add_sql);
			response.getWriter().write("1");
		} catch (Exception e) {
			response.getWriter().write("0");
		}

	}
	/**
	 * @method 修改帮扶单位
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @author 张晓翔
	 * @date 2016年9月11日
	 */
	@RequestMapping("xiugai_bfdw.do")
	public void xiugai_bfdw(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String pkid = request.getParameter("pkid");
		String bfdw_mc = request.getParameter("bfdw_mc");
		String bfdw_ldxm = request.getParameter("bfdw_ldxm");
		String bfdw_lddh = request.getParameter("bfdw_lddh");
		String bfdw_dz = request.getParameter("bfdw_dz");
//		String json_level = request.getParameter("json_level");
		
		String add_sql = "update da_company set v1='"+bfdw_mc+"',v2='"+bfdw_dz+"',v3='"+bfdw_ldxm+"',v4='"+bfdw_lddh+"' where pkid="+pkid;
		try {
			this.getBySqlMapper.findRecords(add_sql);
			response.getWriter().write("1");
		} catch (Exception e) {
			response.getWriter().write("0");
		}
		
	}
	/**
	 * @method 添加帮扶人
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @author 张晓翔
	 * @date 2016年9月11日
	 */
	@RequestMapping("add_bfr.do")
	public void add_bfr(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String dw_pkid = request.getParameter("dw_pkid");
		String col_name = request.getParameter("col_name");
		String telephone = request.getParameter("telephone");
		String v1 = request.getParameter("v1");
		String v3 = request.getParameter("v3");
		String col_post = request.getParameter("col_post");
		
		String add_sql = "insert into sys_personal(da_company_id,v1,col_name,v3,col_post,telephone) values('"+dw_pkid+"','"+v1+"','"+col_name+"','"+v3+"','"+col_post+"','"+telephone+"')";
		try {
			this.getBySqlMapper.findRecords(add_sql);
			response.getWriter().write("1");
		} catch (Exception e) {
			response.getWriter().write("0");
		}
		
	}
	
	
	
	
	
	/**
	 * @method 查看帮扶单位
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @author 呼树明，张晓翔
	 * @date 2016年9月7日
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping("selectAll.do")
	public void selectAll(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String pkid = request.getParameter("pkid"); // 获取用户名id
		String cx_tj=""; // 查询条件
		if (!pkid.equals("null")) {
			cx_tj="where sys_company_id='"+pkid+"'";
		}
		String cx_sql = "SELECT * from da_company "+cx_tj;
		List<Map> cx_List=this.getBySqlMapper.findRecords(cx_sql);
		if(cx_List.size()>0){
			JSONArray jsa =new JSONArray();

			for(Map val:cx_List){
				JSONObject obj=new JSONObject ();
				obj.put("PKID",val.get("PKID")==null?"":val.get("PKID"));
				obj.put("V1", val.get("V1")==null?"":val.get("V1"));
				obj.put("V2",val.get("V2")==null?"":val.get("V2"));
				obj.put("V3",val.get("V3")==null?"":val.get("V3"));
				obj.put("V4",val.get("V4")==null?"":val.get("V4"));
				obj.put("SYS_COMPANY_ID",val.get("SYS_COMPANY_ID")==null?"":val.get("SYS_COMPANY_ID"));
				jsa.add(obj);
			}
			response.getWriter().write(jsa.toString());
		}else {
			response.getWriter().write("0");
		}

	}
	/**
	 * @method 删除帮扶单位之前要判断是否有了帮扶干部
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @author 呼树明，张晓翔
	 * @date 2016年9月7日
	 */
	@RequestMapping("deleteBfdw_panduan.do")
	public void deleteBfdw_panduan(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String pkid = request.getParameter("pkid");
		String select_sql = "SELECT * FROM sys_personal WHERE da_company_id="+pkid;
		try {
			List<Map> cha_list = this.getBySqlMapper.findRecords(select_sql);
			if(cha_list.size()>0){
				response.getWriter().write("1");
			}else{
				response.getWriter().write("0");
			}
		} catch (Exception e) {
			response.getWriter().write("2");
		}
	}

	/**
	 * @method 删除帮扶人
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @author 张晓翔
	 * @date 2016年9月8日
	 */
	@RequestMapping("deleteBfr.do")
	public void deleteBfr(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String pkid = request.getParameter("pkid");
		String bfdw_del_sql = "delete from sys_personal where pkid = "+pkid+"";
		try {
			this.getBySqlMapper.delete(bfdw_del_sql);
			response.getWriter().write("1");
		} catch (Exception e) {
			response.getWriter().write("0");
		}
	}

	/**
	 * @method 查看帮扶人
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @author 张晓翔
	 * @date 2016年9月7日
	 */
	@RequestMapping("chakan_bfr.do")
	public void chakan_bfr(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String da_company_id = request.getParameter("da_company_id");
		String cha_sql = "SELECT pkid,col_name,col_post,telephone,v1,v3 FROM sys_personal WHERE da_company_id="+da_company_id;
		List<Map> cha_sql_List = this.getBySqlMapper.findRecords(cha_sql);
		if(cha_sql_List.size()>0){
			JSONArray jsa =new JSONArray();

			for(Map val:cha_sql_List){
				JSONObject obj=new JSONObject ();
				obj.put("pkid",val.get("PKID")==null?"":val.get("PKID"));
				obj.put("col_post", val.get("COL_POST")==null?"":val.get("COL_POST"));
				obj.put("col_name", val.get("COL_NAME")==null?"":val.get("COL_NAME"));
				obj.put("telephone",val.get("TELEPHONE")==null?"":val.get("TELEPHONE"));
				obj.put("v1",val.get("V1")==null?"":val.get("V1"));
				obj.put("v3",val.get("V3")==null?"":val.get("V3"));
				obj.put("da_company_id",da_company_id);
				jsa.add(obj);
			}
			response.getWriter().write(jsa.toString());
		}else {
			response.getWriter().write("0");
		}
	}
	/**
	 * @method 修改帮扶人
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @author 张晓翔
	 * @date 2016年9月11日
	 */
	@RequestMapping("xiugai_bfr.do")
	public void xiugai_bfr(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String pkid = request.getParameter("pkid");
		String col_name = request.getParameter("col_name");
		String v1 = request.getParameter("v1");
		String v3 = request.getParameter("v3");
		String telephone = request.getParameter("telephone");
		String col_post = request.getParameter("col_post");
		
		String up_sql = "update sys_personal set v1='"+v1+"',v3='"+v3+"',col_name='"+col_name+"',telephone='"+telephone+"',col_post='"+col_post+"' where pkid="+pkid;
		try {
			this.getBySqlMapper.findRecords(up_sql);
			response.getWriter().write("1");
		} catch (Exception e) {
			response.getWriter().write("0");
		}
		
	}
}
