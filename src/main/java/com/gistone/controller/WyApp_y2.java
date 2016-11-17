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
		
		int size = Integer.parseInt(pageSize);
		int number = Integer.parseInt(pageNumber)*size;
		int page = number == 0 ? 1 : (number/size)+1;
		
		System.out.println(number+"--"+(number+size));
		System.out.println();
		
		String whereSQL = "select v10 from SYS_COM where v"+(Integer.parseInt(level)*2)+"='"+xzqh+"' group by v10";
		
//		if(level.equals("1")){
//			whereSQL = "select v10 from SYS_COM where v"+Integer.parseInt(level)*2+"='"+xzqh+"' group by v10";
//		}else if(level.equals("2")){
//			whereSQL = "select v10 from SYS_COM where v4='"+xzqh+"' group by v10";
//		}else if(level.equals("3")){
//			whereSQL = "select v10 from SYS_COM where v6='"+xzqh+"' group by v10";
//		}else if(level.equals("4")){
//			whereSQL = "select v10 from SYS_COM where v8='"+xzqh+"' group by v10";
//		}else if(level.equals("5")){
//			whereSQL = "select v10 from SYS_COM where v10='"+xzqh+"' group by v10";
//		}
		
		String sql = "SELECT * FROM (select ROWNUM AS rowno,HOUSEHOLD_NAME,PERSONAL_NAME,PERSONAL_PHONE,V1,V3,LNG,LAT,ADDRESS,PIC_PATH from ( "
				+ " select d1.*,d2.pic_path from DA_HELP_VISIT d1 join DA_PIC_VISIT d2 on d1.random_number=d2.random_number "
				+ " where AAR008 in("+whereSQL+") order by v1 desc "
				+ " ) t1 where ROWNUM <= "+(number+size)+") table_alias WHERE table_alias.rowno > "+number+" ";
		
		String count_sql = " select count(*) from DA_HELP_VISIT d1 join DA_PIC_VISIT d2 on d1.random_number=d2.random_number where AAR008 in("+whereSQL+")";
		
//		String pingkun_count_sql = "select count(*) from (select AAC001 from NM09_AC01 where AAR040='2015' AND AAR010='0' and AAR008 in("+whereSQL+") group by AAC001) t1";
		
		String sheji_count_sql = "select count(*) from (select HOUSEHOLD_NAME,HOUSEHOLD_CARD from DA_HELP_VISIT d1 "
				+ "join DA_PIC_VISIT d2 on d1.random_number=d2.random_number where AAR008 in("+whereSQL+") group by HOUSEHOLD_NAME,HOUSEHOLD_CARD) t1 ";
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
//		int pinkuncount = this.getBySqlMapper.findrows(pingkun_count_sql);//总贫困户数
		int shejicount = this.getBySqlMapper.findrows(sheji_count_sql);//涉及的贫困户数
		
//		List<Map> rizhi_List = this.getBySqlMapper.findRecords(rizhi_sql);
//		int ri = Integer.parseInt(rizhi_List.get(0).get("RI").toString());
//		int zhou = Integer.parseInt(rizhi_List.get(0).get("ZHOU").toString());
//		int yue = Integer.parseInt(rizhi_List.get(0).get("YUE").toString());
		
		List<Map> Patient_st_List = this.getBySqlMapper.findRecords(sql);
		if(Patient_st_List.size()>0){
			JSONArray jsa=new JSONArray();
			for(int i = 0;i<Patient_st_List.size();i++){
				Map st_map = Patient_st_List.get(i);
				JSONObject val = new JSONObject();
				
				val.put("title", "".equals(st_map.get("PERSONAL_NAME")) || st_map.get("PERSONAL_NAME") == null ? "" : st_map.get("PERSONAL_NAME").toString());
				val.put("intro", "".equals(st_map.get("V3")) || st_map.get("V3") == null ? "" : st_map.get("V3").toString());
				val.put("src", "".equals(st_map.get("PIC_PATH")) || st_map.get("PIC_PATH") == null ? "" : st_map.get("PIC_PATH").toString());
				val.put("writer", "".equals(st_map.get("ADDRESS")) || st_map.get("ADDRESS") == null ? "" : st_map.get("ADDRESS").toString());
				val.put("date", "".equals(st_map.get("V1")) || st_map.get("V1") == null ? "" : st_map.get("V1").toString());
				val.put("lng", "".equals(st_map.get("LNG")) || st_map.get("LNG") == null ? "" : st_map.get("LNG").toString());
				val.put("lat", "".equals(st_map.get("LAT")) || st_map.get("LAT") == null ? "" : st_map.get("LAT").toString());
				val.put("phone", "".equals(st_map.get("PERSONAL_PHONE")) || st_map.get("PERSONAL_PHONE") == null ? "" : st_map.get("PERSONAL_PHONE").toString());
				val.put("house", "".equals(st_map.get("HOUSEHOLD_NAME")) || st_map.get("HOUSEHOLD_NAME") == null ? "" : st_map.get("HOUSEHOLD_NAME").toString());
				
				jsa.add(val);
			}
			
			JSONObject val_ret = new JSONObject();
			val_ret.put("data1", jsa);
			val_ret.put("data2", count);//日记数量
			val_ret.put("data3", shejicount);//涉及到的贫困户数
//			val_ret.put("data4", pinkuncount);//贫困户的总数
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


