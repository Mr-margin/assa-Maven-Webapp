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
public class WyApp_y3{
	/**
	 * W3地图
	 */
	@Autowired
	private GetBySqlMapper getBySqlMapper;
	
	 /**
	 * @method 地图中获取数据
	 * @param request
	 * @param response
	 * @throws IOException
	 * @author 张晓翔
	 * @date 2016年9月11日上午11:20:41
	 */
	@RequestMapping("getW3map.do")
	public void getW3map(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String code = request.getParameter("code");
		String sql1="";
		
		sql1="SELECT * FROM zhang_06 WHERE com_code='"+code+"'";
		List<Map> list = this.getBySqlMapper.findRecords(sql1);
		JSONArray json = new JSONArray();
		if(list.size() > 0){
			for(int i = 0 ; i < list.size(); i ++){
				JSONObject obj = new JSONObject ();
				obj.put("com_name", list.get(i).get("COM_NAME"));
				obj.put("csr", "".equals(list.get(i).get("CSR")) || list.get(i).get("CSR") == null ? "" : list.get(i).get("CSR").toString());//村集体收入
				obj.put("zcgzd", "".equals(list.get(i).get("ZCGZD"))|| list.get(i).get("ZCGZD") == null ? "" : list.get(i).get("ZCGZD").toString());//驻村工作队
				obj.put("hushu", "".equals(list.get(i).get("HUSHU")) || list.get(i).get("HUSHU")== null ? "0" : list.get(i).get("HUSHU").toString());//贫困户数
				obj.put("renshu", "".equals(list.get(i).get("RENSHU")) || list.get(i).get("RENSHU")== null ? "0" : list.get(i).get("RENSHU").toString());//贫困 人数
				obj.put("aad001", "".equals(list.get(i).get("AAD001")) || list.get(i).get("AAD001")== null ? "" : list.get(i).get("AAD001").toString());//贫困村编号
				obj.put("aar011", "".equals(list.get(i).get("AAR011")) || list.get(i).get("AAR011")== null ? "" : list.get(i).get("AAR011").toString());//负责人
				obj.put("aar012", "".equals(list.get(i).get("AAR012")) || list.get(i).get("AAR012")== null ? "" : list.get(i).get("AAR012").toString());//电话
				obj.put("aad301", "".equals(list.get(i).get("AAD301")) || list.get(i).get("AAD301")== null ? "" : list.get(i).get("AAD301").toString());//农民人均纯收入
				obj.put("zirancun", "".equals(list.get(i).get("ZIRANCUN")) || list.get(i).get("ZIRANCUN")== null ? "" : list.get(i).get("ZIRANCUN").toString());//自然村
				json.add(obj);
			}
			response.getWriter().write(json.toString());
		}else{
			response.getWriter().write("0");
		}
	}
	
	/**
	 * 按照村级显示贫困户信息
	 */
	@RequestMapping("getWyApp_y3_1.do")
	public void getWyApp_y3_1(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String code = request.getParameter("pkid");
		String sql = "SELECT a1.AAC001 acid,a1.AAR008 code,pkid,v6,v21,v22,v23 FROM("+
				" SELECT AAC001,AAR008,AAR010 v21,AAC006 v22,AAC007 v23 FROM NEIMENG0117_AC01 WHERE AAD001='"+code+"' AND AAR040='2016' AND AAR010='0'"+
				" )a1 LEFT JOIN ("+
				"SELECT AAB001 pkid,AAC001,AAB002 v6 FROM NEIMENG0117_AB01 WHERE  AAR040='2016' AND AAB006=01 )a2 ON a1.AAC001=A2.AAC001 ORDER BY v6"; //sql语句替换的
		SW4_Controller sw4=new SW4_Controller();
		JSONArray json = new JSONArray();
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		if (list.size() > 0) {
			for ( int i = 0 ; i < list.size() ; i ++){
				JSONObject obj = new JSONObject();
				obj.put("acid", "".equals(list.get(i).get("ACID")) || list.get(i).get("ACID") == null ? "" : list.get(i).get("ACID").toString());
				obj.put("pkid", "".equals(list.get(i).get("PKID")) || list.get(i).get("PKID") == null ? "" : list.get(i).get("PKID").toString());
				obj.put("v6", "".equals(list.get(i).get("V6")) || list.get(i).get("V6") == null ? "" : list.get(i).get("V6").toString());
//				obj.put("v9", "".equals(list.get(i).get("V9")) || list.get(i).get("V9") == null ? "" : list.get(i).get("V9").toString());
				obj.put("v21", "".equals(list.get(i).get("V21")) || list.get(i).get("V21") == null ? "" : sw4.mianZhuan(list.get(i).get("V21").toString(), "21"));
				obj.put("v22", "".equals(list.get(i).get("V22")) || list.get(i).get("V22") == null ? "" : sw4.mianZhuan(list.get(i).get("V22").toString(), "22"));
				obj.put("v23", "".equals(list.get(i).get("V23")) || list.get(i).get("V23") == null ? "" : sw4.mianZhuan(list.get(i).get("V23").toString(), "23"));
				obj.put("code", "".equals(list.get(i).get("CODE")) || list.get(i).get("CODE") == null ? "" : list.get(i).get("CODE").toString());
				json.add(obj);
			}
			response.getWriter().write(json.toString());
		}else{
			response.getWriter().write("0");
		}
	}
	
	
	//地图中村的统计数据与详细数据用的不是一张表，下面是更新数据的语句
//	create table ZHANG_07 as
//	select t1.*,t2.h1,t3.r1 from ZHANG_06 t1 join (select AAD001,count(*) as h1 from NEIMENG0117_AC01 where AAR040='2016' AND AAR010='0' group by AAD001) t2
//	on t1.AAD001=t2.AAD001 join(
//	SELECT AAD001,count(*) as r1 from NEIMENG0117_AC01 a1 join NEIMENG0117_AB01 a2 on a1.AAC001=A2.AAC001 
//	where a1.AAR040='2016' AND a1.AAR010='0' and a2.AAR040='2016' group by a1.AAD001
//	) t3 on t1.AAD001=t3.AAD001
//
//	update ZHANG_06 set HUSHU=H1,RENSHU=R1
//
//	CREATE INDEX ZHANG_06_COM_CODE
//	ON ZHANG_06(COM_CODE);
}
