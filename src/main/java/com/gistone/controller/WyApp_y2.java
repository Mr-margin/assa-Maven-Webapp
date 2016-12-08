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
		
//		System.out.println(number+"--"+(number+size));
//		System.out.println();
		
		String whereSQL = "select v10 from SYS_COM where v"+(Integer.parseInt(level)*2)+"='"+xzqh+"' group by v10";
		
		String sql = "SELECT * FROM (select ROWNUM AS rowno,HOUSEHOLD_NAME,PERSONAL_NAME,PERSONAL_PHONE,V1,V3,LNG,LAT,ADDRESS,PIC_PATH,AAR008 from ( "
				+ " select d1.*,d2.pic_path from DA_HELP_VISIT d1 join DA_PIC_VISIT d2 on d1.random_number=d2.random_number "
				+ " where AAR008 in("+whereSQL+") order by v1 desc "
				+ " ) t1 where ROWNUM <= "+(number+size)+") table_alias WHERE table_alias.rowno > "+number+" ";
		
		String count_sql = " select count(*) from DA_HELP_VISIT d1 join DA_PIC_VISIT d2 on d1.random_number=d2.random_number where AAR008 in("+whereSQL+")";
		
		String sheji_count_sql = "select count(*) from (select HOUSEHOLD_NAME,HOUSEHOLD_CARD from DA_HELP_VISIT d1 "
				+ "join DA_PIC_VISIT d2 on d1.random_number=d2.random_number where AAR008 in("+whereSQL+") group by HOUSEHOLD_NAME,HOUSEHOLD_CARD) t1 ";
		
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
				
				val.put("date", "".equals(st_map.get("V1")) || st_map.get("V1") == null ? "" : st_map.get("V1").toString());
				if( "".equals(st_map.get("LNG")) || st_map.get("LNG") == null ) {
					String cha_sql = "select v9,lng,lat from sys_com where v10='"+st_map.get("AAR008")+"'";
					List<Map> cha_list = this.getBySqlMapper.findRecords(cha_sql);
					val.put("lng", "".equals(cha_list.get(0).get("LNG")) || cha_list.get(0).get("LNG") == null ? "" : cha_list.get(0).get("LNG").toString());
					val.put("lat", "".equals(cha_list.get(0).get("LAT")) || cha_list.get(0).get("LAT") == null ? "" : cha_list.get(0).get("LAT").toString());
					val.put("writer", "".equals(cha_list.get(0).get("V9")) || cha_list.get(0).get("V9") == null ? "" : cha_list.get(0).get("V9").toString());
				} else {
					val.put("writer", "".equals(st_map.get("ADDRESS")) || st_map.get("ADDRESS") == null ? "" : st_map.get("ADDRESS").toString());
					val.put("lng", "".equals(st_map.get("LNG")) || st_map.get("LNG") == null ? "" : st_map.get("LNG").toString());
					val.put("lat", "".equals(st_map.get("LAT")) || st_map.get("LAT") == null ? "" : st_map.get("LAT").toString());
				}
				
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


