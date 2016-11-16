package com.gistone.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Border;
import jxl.write.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.Label;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gistone.MyBatis.config.GetBySqlMapper;
import com.gistone.util.QRCodeUtil;
import com.gistone.util.Tool;

@RestController
@RequestMapping
public class WyApp_y2 {
	@Autowired
	private GetBySqlMapper getBySqlMapper;
	
	/**
	 * 显示瀑布流数据
	 */
	@RequestMapping("getWyApp_y2_1.do")
	public void getWyApp_y2_1(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		String pageSize = request.getParameter("pageSize");
		String pageNumber = request.getParameter("pageNumber");
		
		String level = request.getParameter("level");
		String xzqh = request.getParameter("xzqh");
		String pkid = request.getParameter("pkid");
		
		int size = Integer.parseInt(pageSize);
		int number = Integer.parseInt(pageNumber)*size;
		int page = number == 0 ? 1 : (number/size)+1;
		
//		System.out.println(number+"--"+(number+size));
		
		String whereSQL = "";
		
		if(level.equals("1")){
			whereSQL = "select t1.pkid from da_household t1 where t1.v1='"+xzqh+"'";
		}else if(level.equals("2")){
			whereSQL = "select t1.pkid from da_household t1 where t1.v2='"+xzqh+"'";
		}else if(level.equals("3")){
			whereSQL = "select t1.pkid from da_household t1 where t1.v3='"+xzqh+"'";
		}else if(level.equals("4")){
			whereSQL = "select t1.pkid from da_household t1 where t1.v4='"+xzqh+"'";
		}else if(level.equals("5")){
			whereSQL = "select t1.pkid from da_household t1 join (select w1.com_name as v4,w2.com_name as v5 from SYS_COMPANY w1 join SYS_COMPANY w2 "
					+ "on w1.pkid=w2.com_f_pkid where w2.pkid="+pkid+") t2 on t1.v4=t2.v4 and t1.v5=t2.v5";
		}
		
		String sql ="SELECT * FROM (select ROWNUM AS rowno,t4.col_name as title,t2.v3 as intro,t2.v1 as v1date,t3.pic_path as src,t0.BASIC_ADDRESS as writer "
				+ "from (select * from da_pic where pic_type=2) t3 "
				+ "join da_help_visit t2 on t3.pic_pkid = t2.pkid "
				+ "join SYS_PERSONAL t4 on t2.SYS_PERSONAL_ID=t4.pkid "
				+ "join ("+whereSQL+") t1 on t1.pkid=t2.da_household_id "
				+ "join da_household_basic t0 on t0.da_household_id=t2.da_household_id where ROWNUM <= "+(number+size)+" order by t2.v1 desc ) "
				+ "table_alias WHERE table_alias.rowno > "+number+" order by v1date desc";
		
		String count_sql = " select count(*) from (select * from da_pic where pic_type=2) t3 join da_help_visit t2 on t3.pic_pkid = t2.pkid "
				+ "join ("+whereSQL+") t1 on t1.pkid=t2.da_household_id";
		
		String sheji_count_sql = "select count(*) from(select t2.da_household_id from (select * from da_pic where pic_type=2) t3 "
				+ "join da_help_visit t2 on t3.pic_pkid = t2.pkid join ("+whereSQL+") t1 on t1.pkid=t2.da_household_id group by t2.da_household_id) aa";
//		
//		System.out.println(sql);
//		String rizhi_sql = "select * from(";
//		rizhi_sql += " select count(*) as ri from  ";
//		rizhi_sql += " (select t2.v1 from (select * from da_pic where pic_type=2) t3 join da_help_visit t2 on t3.pic_pkid = t2.pkid ";
//		rizhi_sql += " join ("+whereSQL+") t1 on t1.pkid=t2.da_household_id) y2 ";
//		rizhi_sql += "  where v1=to_char(sysdate,'yyyy-mm-dd')) w1,( ";
//		rizhi_sql += " select count(*) as zhou from  ";
//		rizhi_sql += " (select t2.v1 from (select * from da_pic where pic_type=2) t3 join da_help_visit t2 on t3.pic_pkid = t2.pkid ";
//		rizhi_sql += " join ("+whereSQL+") t1 on t1.pkid=t2.da_household_id) y2 ";
//		rizhi_sql += "  where v1 between to_char(trunc(sysdate,'d')+1,'yyyy-mm-dd') and to_char(trunc(sysdate,'d')+7,'yyyy-mm-dd') ";
//		rizhi_sql += " ) w2,(select count(*) as yue from  ";
//		rizhi_sql += " (select t2.v1 from (select * from da_pic where pic_type=2) t3 join da_help_visit t2 on t3.pic_pkid = t2.pkid ";
//		rizhi_sql += " join ("+whereSQL+") t1 on t1.pkid=t2.da_household_id) y2 ";
//		rizhi_sql += "  where v1 between to_char(trunc(sysdate,'mm'),'yyyy-mm-dd') and to_char(last_day(trunc(sysdate)),'yyyy-mm-dd')) w3 ";
		
		int count = this.getBySqlMapper.findrows(count_sql);//总记录条数
		int pinkuncount = this.getBySqlMapper.findrows(whereSQL.replaceAll("t1.pkid","count(*)"));//总贫困户树
		int shejicount = this.getBySqlMapper.findrows(sheji_count_sql);//涉及的贫困户数
		
//		List<Map> rizhi_List = this.getBySqlMapper.findRecords(rizhi_sql);
//		int ri = Integer.parseInt(rizhi_List.get(0).get("RI").toString());
//		int zhou = Integer.parseInt(rizhi_List.get(0).get("ZHOU").toString());
//		int yue = Integer.parseInt(rizhi_List.get(0).get("YUE").toString());
		
		List<Map> Patient_st_List = this.getBySqlMapper.findRecords(sql);
		if(Patient_st_List.size()>0){
			JSONArray jsa=new JSONArray();
			for(int i = 0;i<Patient_st_List.size();i++){
				Map Patient_st_map = Patient_st_List.get(i);
				JSONObject val = new JSONObject();
				
				val.put("title", Patient_st_map.get("TITLE"));
				val.put("intro", Patient_st_map.get("INTRO"));
				val.put("src", Patient_st_map.get("SRC"));
				val.put("writer", Patient_st_map.get("WRITER"));
				val.put("date", Patient_st_map.get("V1DATE"));
				
				jsa.add(val);
			}
			
			JSONObject val_ret = new JSONObject();
			val_ret.put("data1", jsa);
			val_ret.put("data2", count);//日记数量
			val_ret.put("data3", shejicount);//涉及到的贫困户数
			val_ret.put("data4", pinkuncount);//贫困户的总数
//			val_ret.put("ri", ri);
//			val_ret.put("zhou", zhou);
//			val_ret.put("yue", yue);
			response.getWriter().write(val_ret.toString());
		}else{
			JSONObject val_ret = new JSONObject();
			val_ret.put("error", "本地区暂无帮扶日记！");
			response.getWriter().write(val_ret.toString());
		}
	}
}


