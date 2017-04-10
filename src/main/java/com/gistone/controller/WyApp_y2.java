package com.gistone.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String pageSize = request.getParameter("pageSize");
		String pageNumber = request.getParameter("pageNumber");
		
		String level = request.getParameter("level");
		String xzqh = request.getParameter("xzqh");
		String bfrname = request.getParameter("bfrname").trim();
		String pkhname = request.getParameter("pkhname").trim();
		String zfT = request.getParameter("zftype");
		
		int size = Integer.parseInt(pageSize);
		int number = Integer.parseInt(pageNumber)*size;
		int page = number == 0 ? 1 : (number/size)+1;
		
//		System.out.println(number+"--"+(number+size));
//		System.out.println();
		
		String whereSQL = "select v10 from SYS_COM where v"+(Integer.parseInt(level)*2)+"='"+xzqh+"' group by v10";
		
		String sql = "SELECT * FROM (select ROWNUM AS rowno,HOUSEHOLD_NAME,PERSONAL_NAME,PERSONAL_PHONE,V1,V3,LNG,LAT,ADDRESS,PIC_PATH,AAR008,registertime,ZFTYPE from ( "
				+ " select d1.*,d2.pic_path from DA_HELP_VISIT d1 join (select RANDOM_NUMBER,max(pic_path) pic_path from DA_PIC_VISIT d2 group by RANDOM_NUMBER) d2 "
				+ " on d1.random_number=d2.random_number where AAR008 in("+whereSQL+") and  d1.zftype in("+zfT+") order by v1 desc,registertime desc, d1.PKID desc "
				+ " ) t1 where ROWNUM <= "+(number+size)+") table_alias WHERE table_alias.rowno > "+number;
		
	
		String count_sql = " select count(*) from DA_HELP_VISIT d1 join (select RANDOM_NUMBER,max(pic_path) pic_path from DA_PIC_VISIT d2 group by RANDOM_NUMBER) d2 on d1.random_number=d2.random_number where AAR008 in("+whereSQL+")  and  d1.zftype in("+zfT+")";
		
		String sheji_count_sql = "select count(*) from (select HOUSEHOLD_NAME,HOUSEHOLD_CARD from DA_HELP_VISIT d1 "
				+ "join DA_PIC_VISIT d2 on d1.random_number=d2.random_number where AAR008 in("+whereSQL+") group by HOUSEHOLD_NAME,HOUSEHOLD_CARD) t1 ";
		 //不跟随传递的时间变，当天日记数、本周日记数、本月日记数 DA_HELP_VISIT
	    String bangfu_count_sql = "SELECT count(case when to_char(to_date(registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd') then 'a00' end)day,	count(	CASE when to_char(to_date(registertime,'yyyy-mm-dd hh24:mi:ss'),'iw')=to_char(sysdate,'iw') and TO_NUMBER(sysdate-to_date(registertime,'yyyy-mm-dd hh24:mi:ss'))<10 THEN 'a01' end)week,	count(	CASE when to_char(to_date(registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm')=to_char(sysdate,'yyyy-mm') THEN 'a02' end)month	FROM	DA_HELP_VISIT  WHERE 1=1";
	
	    List<Map> bangfus = null;

	    //帮扶日记统计 日 周 月帮扶人数统计
	    	//如果使用搜索条件进行查询
	    	String str = xzqh.substring(0, 2+Integer.valueOf(level));
	    	bangfu_count_sql += " AND AAR008  LIKE '"+str+"%' ";
	    	bangfus = this.getBySqlMapper.findRecords(bangfu_count_sql);
	   
		
		
		
		int count = this.getBySqlMapper.findrows(count_sql);//总记录条数
//		int pinkuncount = this.getBySqlMapper.findrows(pingkun_count_sql);//总贫困户数
		int shejicount = this.getBySqlMapper.findrows(sheji_count_sql);//涉及的贫困户数
		
//		List<Map> rizhi_List = this.getBySqlMapper.findRecords(rizhi_sql);
//		int ri = Integer.parseInt(rizhi_List.get(0).get("RI").toString());
//		int zhou = Integer.parseInt(rizhi_List.get(0).get("ZHOU").toString());
//		int yue = Integer.parseInt(rizhi_List.get(0).get("YUE").toString());
		if(bfrname!=""&&bfrname!=null&&bfrname.length()>0){
			sql +=" AND  table_alias.PERSONAL_NAME='"+bfrname+"'";
		}
		if(pkhname!=""&&pkhname!=null&&pkhname.length()>0){
			sql +=" AND  table_alias.HOUSEHOLD_NAME='"+pkhname+"'";
		}
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
				
				if ( st_map.get("ADDRESS") == null || "".equals(st_map.get("ADDRESS")) ) {
					String cha_sql = "select v9,lng,lat from sys_com where v10='"+st_map.get("AAR008")+"'";
					List<Map> cha_list = this.getBySqlMapper.findRecords(cha_sql);
					val.put("writer", "".equals(cha_list.get(0).get("V9")) || cha_list.get(0).get("V9") == null ? "" : cha_list.get(0).get("V9").toString());
				} else {
					val.put("writer", "".equals(st_map.get("ADDRESS")) || st_map.get("ADDRESS") == null ? "" : st_map.get("ADDRESS").toString());
				}
				
				if( "".equals(st_map.get("LNG")) || st_map.get("LNG") == null ) {
					String cha_sql = "select v9,lng,lat from sys_com where v10='"+st_map.get("AAR008")+"'";
					List<Map> cha_list = this.getBySqlMapper.findRecords(cha_sql);
					val.put("lng", "".equals(cha_list.get(0).get("LNG")) || cha_list.get(0).get("LNG") == null ? "" : cha_list.get(0).get("LNG").toString());
					val.put("lat", "".equals(cha_list.get(0).get("LAT")) || cha_list.get(0).get("LAT") == null ? "" : cha_list.get(0).get("LAT").toString());
				} else {
					val.put("lng", "".equals(st_map.get("LNG")) || st_map.get("LNG") == null ? "" : st_map.get("LNG").toString());
					val.put("lat", "".equals(st_map.get("LAT")) || st_map.get("LAT") == null ? "" : st_map.get("LAT").toString());
				}
				try {
					val.put("time", "".equals(st_map.get("REGISTERTIME")) || st_map.get("REGISTERTIME") == null ? "" : sdf.format(sdf.parse(st_map.get("REGISTERTIME").toString())));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				val.put("phone", "".equals(st_map.get("PERSONAL_PHONE")) || st_map.get("PERSONAL_PHONE") == null ? "" : st_map.get("PERSONAL_PHONE").toString());
				val.put("house", "".equals(st_map.get("HOUSEHOLD_NAME")) || st_map.get("HOUSEHOLD_NAME") == null ? "" : st_map.get("HOUSEHOLD_NAME").toString());
				/*1、其他帮扶活动
				2、了解基本情况
				3、填写扶贫手册
				4、制定脱贫计划
				5、落实资金项目
				6、宣传扶贫政策
				7、节日假日慰问*/
				String zftype="其他帮扶活动";
				if(st_map.get("ZFTYPE")!=null&&!"".equals(st_map.get("ZFTYPE"))){
					int j=Integer.valueOf(st_map.get("ZFTYPE").toString()); 

					switch(j) 

					{ 

					case 1: 

						zftype="其他帮扶活动";

					break; 

					case 2: 

						zftype="了解基本情况";

					break;  

					case 3: 

						zftype="填写扶贫手册";

					break;
					
					case 4: 

						zftype="制定脱贫计划";

					break;
					
					case 5: 

						zftype="落实资金项目";

					break;
					
					case 6: 

						zftype="宣传扶贫政策";

					break;
					
					case 7: 

						zftype="节日假日慰问";

					break;

					default: 

						zftype="其他帮扶活动";

					break; 

					} 

				}
				val.put("zftype", zftype);
				jsa.add(val);
			}
			//帮扶统计
			JSONObject json = new JSONObject();
			if(bangfus.size()>0){
				//将日期统计数保存为Json串 为null的时候转为0
				json.put("dayNum", bangfus.get(0).get("DAY")==null?0:bangfus.get(0).get("DAY"));
				json.put("weekNum", bangfus.get(0).get("WEEK")==null?0:bangfus.get(0).get("WEEK"));
				json.put("monthNum", bangfus.get(0).get("MONTH")==null?0:bangfus.get(0).get("MONTH"));
			}
			JSONObject val_ret = new JSONObject();
			val_ret.put("data1", jsa);
			val_ret.put("data2", count);//日记数量
			val_ret.put("data3", shejicount);//涉及到的贫困户数
			val_ret.put("riji_count",json);
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


