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
		
		sql1="SELECT * FROM zhang_05 WHERE pkid='"+code+"'";
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
		String pkid = request.getParameter("pkid");
		
		String sql = "SELECT a1.AAC001 acid,pkid,v6,v21,v22,v23 FROM("+
				" SELECT AAC001,AAR010 v21,AAC006 v22,AAC007 v23 FROM NM09_AC01 WHERE AAD001=(SELECT COM_CODE FROM SYS_COMPANY WHERE PKID='"+pkid+"') AND AAR040='2015' AND AAR010='0'"+
				" )a1 LEFT JOIN ("+
				"SELECT AAB001 pkid,AAC001,AAB002 v6 FROM NM09_AB01 WHERE  AAR040='2015' AND AAB006=01 )a2 ON a1.AAC001=A2.AAC001"; //sql语句替换的
				
				/*"select pkid,v6,v9,v21,v22,v23 from (select t1.com_name v4,t2.com_name v5 from SYS_COMPANY t1 join SYS_COMPANY t2 on t1.pkid=t2.com_f_pkid"
				+ " where t2.pkid="+pkid+") t3 join DA_HOUSEHOLD t4 on t3.v4=t4.v4 and t3.v5=t4.v5  ";
		*/
		
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
				json.add(obj);
			}
			response.getWriter().write(json.toString());
		}
	}
}
