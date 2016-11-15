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

@RestController
@RequestMapping
public class WyApp_y4 {
	@Autowired
	private GetBySqlMapper getBySqlMapper;
	SW4_Controller sw4=new SW4_Controller();
	/**
	 * 按照村级显示贫困户信息
	 */
	@RequestMapping("getWyApp_y4_1.do")
	public void getWyApp_y4_1(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String pkid = request.getParameter("pkid");
		
		String sql = "select v21,v22,v23,v6,v9,pic_path from "+
					" (select NM09_AC01.AAC001,NM09_AC01.AAR008,AAR010 v21,AAC006 v22,AAC007 v23,AAB002 v6,AAB004 v8 from NM09_AC01 join NM09_AB01 on NM09_AC01.AAC001=NM09_AB01.AAC001 where NM09_AC01.AAR008 in (select COM_CODE from SYS_COMPANY  where pkid="+pkid+") AND NM09_AC01.AAR040='2015' and AAB006=01 and AAR010=0 group BY NM09_AC01.AAR008,AAR010,AAC006,AAC007,AAB002,AAB004,NM09_AC01.AAC001) t4"+
					" join DA_PIC_CODE on t4.v6=DA_PIC_CODE.HOUSEHOLD_NAME and t4.v8=DA_PIC_CODE.HOUSEHOLD_CARD join (select COUNT(*) v9,AAC001 from (SELECT aac001,aab002 FROM NM09_AB01  GROUP BY aab002,AAC001 ) GROUP BY AAC001) t5 on T4.AAC001=t5.AAC001 GROUP BY v21,v22,v23,v6,v9,pic_path";
		
		/*"select pkid,v6,v9,v21,v22,v23,com,pic_path from "
		+ "(select t1.com_name v4,t2.com_name v5 from SYS_COMPANY t1 join SYS_COMPANY t2 on t1.pkid=t2.com_f_pkid where t2.pkid="+pkid+") t3 "
		+ "join DA_HOUSEHOLD t4 on t3.v4=t4.v4 and t3.v5=t4.v5 "
		+ "left join (select DA_HOUSEHOLD_ID,count(*) as com from DA_HELP_VISIT group by DA_HOUSEHOLD_ID) t5 on t4.pkid=t5.DA_HOUSEHOLD_ID "
		+ "left join (select pic_pkid,pic_path from DA_PIC where pic_type=7) t6 on t4.pkid=t6.pic_pkid";*/
		JSONArray json = new JSONArray();
//		System.out.println(sql);
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		if (list.size() > 0) {
			for ( int i = 0 ; i < list.size() ; i ++){
				JSONObject obj = new JSONObject();
				obj.put("pkid", "".equals(list.get(i).get("PKID")) || list.get(i).get("PKID") == null ? "" : list.get(i).get("PKID").toString());
				obj.put("v6", "".equals(list.get(i).get("V6")) || list.get(i).get("V6") == null ? "" : list.get(i).get("V6").toString());
				obj.put("v9", "".equals(list.get(i).get("V9")) || list.get(i).get("V9") == null ? "" : list.get(i).get("V9").toString());
				obj.put("v21", "".equals(list.get(i).get("V21")) || list.get(i).get("V21") == null ? "" :sw4.mianZhuan(list.get(i).get("V21").toString(), "21") );
				obj.put("v22", "".equals(list.get(i).get("V22")) || list.get(i).get("V22") == null ? "" :sw4.mianZhuan(list.get(i).get("V22").toString(), "22") );
				obj.put("v23", "".equals(list.get(i).get("V23")) || list.get(i).get("V23") == null ? "" :sw4.mianZhuan(list.get(i).get("V23").toString(), "23") );
				obj.put("riji_info","");
				//obj.put("riji_info", "".equals(list.get(i).get("COM")) || list.get(i).get("COM") == null ? "0" : list.get(i).get("COM").toString());
				obj.put("erweima", "".equals(list.get(i).get("PIC_PATH")) || list.get(i).get("PIC_PATH") == null ? "0" : list.get(i).get("PIC_PATH").toString());
				json.add(obj);
			}
			response.getWriter().write(json.toString());
		}
	}
	
	
	/**
	 * 显示单个贫困户的帮扶日记
	 */
	@RequestMapping("getWyApp_y4_2.do")
	public void getWyApp_y4_2(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String pkid = request.getParameter("pkid");
		
		String sql = "select t2.v1 as danwei,t1.col_name,t3.v1,t3.v3,t3.pkid from DA_HELP_VISIT t3 join SYS_PERSONAL t1 on t3.SYS_PERSONAL_ID=t1.pkid "
				+ "join DA_COMPANY t2 on t1.DA_COMPANY_ID=t2.pkid where t3.DA_HOUSEHOLD_ID="+pkid+" order by t3.v1 desc";
		String pic = "";
		
		JSONArray json = new JSONArray();
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		if (list.size() > 0) {
			for ( int i = 0 ; i < list.size() ; i ++){
				JSONObject obj = new JSONObject();
				obj.put("danwei", "".equals(list.get(i).get("DANWEI")) || list.get(i).get("DANWEI") == null ? "" : list.get(i).get("DANWEI").toString());
				obj.put("col_name", "".equals(list.get(i).get("COL_NAME")) || list.get(i).get("COL_NAME") == null ? "" : list.get(i).get("COL_NAME").toString());
				obj.put("v1", "".equals(list.get(i).get("V1")) || list.get(i).get("V1") == null ? "" : list.get(i).get("V1").toString());
				obj.put("v3", "".equals(list.get(i).get("V3")) || list.get(i).get("V3") == null ? "" : list.get(i).get("V3").toString());
				
				pic = "select pkid,pic_path from da_pic where pic_type=2 and pic_pkid="+list.get(i).get("PKID").toString();
				List<Map> pic_list = this.getBySqlMapper.findRecords(pic);
				JSONArray nodes = new JSONArray();
				if(pic_list.size()>0){
					for ( int j = 0 ; j < pic_list.size() ; j ++){
						JSONObject pic_obj = new JSONObject();
						pic_obj.put("path", "".equals(pic_list.get(j).get("PIC_PATH")) || pic_list.get(j).get("PIC_PATH") == null ? "" : pic_list.get(j).get("PIC_PATH").toString());
						pic_obj.put("pkid", "".equals(pic_list.get(j).get("PKID")) || pic_list.get(j).get("PKID") == null ? "" : pic_list.get(j).get("PKID").toString());
						nodes.add(pic_obj);
					}
				}
				obj.put("nodes", nodes);
				json.add(obj);
			}
			response.getWriter().write(json.toString());
		}
	}
}
