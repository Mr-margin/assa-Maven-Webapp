package com.gistone.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gistone.MyBatis.config.GetBySqlMapper;

@RestController
@RequestMapping
public class AExport{
	
	@Autowired
	private GetBySqlMapper getBySqlMapper;
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("getExport.do")
	public  void  getExport ( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		String type = request.getParameter("type");//现贫为0，返贫为1
		String type_xz = request.getParameter("xz");//6、村 5、乡4、县 3、市 2、省
		//根据行政区划查询人数以及行政区划编号
		String sql = " SELECT  NUM,AAR00"+type_xz+" AAR006,XZQH FROM ( SELECT COUNT(*) NUM,AAR00"+type_xz+" FROM ( SELECT * FROM (SELECT AAC001 A1,AAB001 FROM NM09_AB01)A LEFT JOIN  "+
					" (SELECT AAC001,AAR002,AAR003,AAR004,AAR005,AAR006 FROM NM09_AC01 ";
		if ( "0".equals(type) ) {
			 sql += "WHERE AAR010='0' OR AAR010='2')B ON A.A1=B.AAC001 ";
		} else if ( "1".equals(type) ) {
			 sql += "WHERE AAR010='1' OR AAR010='3')B ON A.A1=B.AAC001 ";
		}
			   sql += ") GROUP BY AAR00"+type_xz+")AA "+
					"LEFT JOIN (SELECT V"+(2*Integer.parseInt(type_xz)-3)+" XZQH,V"+(2*Integer.parseInt(type_xz)-2)+" FROM SYS_COM)BB ON AA.AAR00"+type_xz+"=BB.V"+(2*Integer.parseInt(type_xz)-2)+" ";
		
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		for ( int i = 0 ; i < list.size() ; i++ ) {
			String v10 = "".equals(list.get(i).get("AAR006")) || list.get(i).get("AAR006") == null ? "" : list.get(i).get("AAR006").toString();//行政编嘛
			//新农合
			String xnh_sql = "select count(*) num from (select AAB013 from (select  AAR00"+type_xz+",AAC001 FROM NM09_AC01 where AAR00"+type_xz+"='"+v10+"')a LEFT JOIN  "+
							" (select AAB001,AAC001 from NM09_AB01)b  on a.AAC001=b.AAC001 LEFT JOIN (select AAB013,AAB001 from NM09_AB02 ) c ON b.AAB001=c.AAB001 where AAB013='1')group by AAB013";
			List<Map> xnh_list = this.getBySqlMapper.findRecords(xnh_sql);
			String v5 = "0" ;
			if ( xnh_list.size() > 0 ) {
				v5 = "".equals(xnh_list.get(0).get("NUM")) || xnh_list.get(0).get("NUM") == null ? "" : xnh_list.get(0).get("NUM").toString();
			}
			//新农保
			String xnb_sql = "select count(*) num from (select AAB014 from (select AAR00"+type_xz+",AAC001 FROM NM09_AC01 where  AAR00"+type_xz+"='"+v10+"')a LEFT JOIN  "+
					" (select AAB001,AAC001 from NM09_AB01)b  on a.AAC001=b.AAC001 LEFT JOIN (select AAB014,AAB001 from NM09_AB02 ) c ON b.AAB001=c.AAB001 where AAB014='1')group by AAB014";
			List<Map> xnb_list = this.getBySqlMapper.findRecords(xnb_sql);
			String v6 = "0";
			if ( xnb_list.size() > 0 ) {
				v6 = "".equals(xnb_list.get(0).get("NUM")) || xnb_list.get(0).get("NUM") == null ? "" : xnb_list.get(0).get("NUM").toString();
			}
			String v1 = "".equals(list.get(i).get("XZQH")) || list.get(i).get("XZQH") == null ? "" : list.get(i).get("XZQH").toString();
			String v2 = "".equals(list.get(i).get("NUM")) || list.get(i).get("NUM") == null ? "" : list.get(i).get("NUM").toString();
			
		
			String insert_sql = " insert into PKC_1_1_0 (V1,V2,V5,V6,v9,V10) values ("+
								"'"+v1+"','"+v2+"','"+v5+"','"+v6+"','"+type+"','"+v10+"')" ;
			this.getBySqlMapper.insert(insert_sql);
		
		
		}
		response.getWriter().write("222222222222222222222222222222222222");
	}
	
}
