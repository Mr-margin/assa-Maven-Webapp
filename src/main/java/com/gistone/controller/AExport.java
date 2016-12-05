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
	 * 贫困人口基本信息的导入
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("PKC_1_1_0.do")
	public  void  PKC_1_1_0 ( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		for ( int i = 0 ; i < 4 ; i ++ ) {
			int v98 = 5-i;
			int ar = 6-i;
			int v10 = 2*v98;
			int xzqh = v10-1;
			for(int j = 0 ; j < 2 ; j++ ){
				String str = "";
				String sql ="select XZQH,NUM,AAB013,AAB014,'"+j+"' CC,XZ,'"+v98+"' SS,'2015' VV from( "+
							" select  NUM,AAR00"+ar+" xz,xzqh from ( SELECT COUNT(*) NUM,AAR00"+ar+" FROM (SELECT * FROM (select AAC001 a1,AAB001 from NM09_AB01 where AAR040='2015' and AAB015 IN ('1','4')"+
							")a LEFT JOIN (select AAC001,AAR002,AAR003,AAR004,AAR005,AAR006 from NM09_AC01  where AAR100= '1' and AAR040='2015'";
				if ( j == 0 ){
					str = "0";
				}else {
					str = "3";
				}
					sql += " and AAR010 ='"+str+"')b on a.a1=B.AAC001 ) GROUP BY AAR00"+ar+")AA left join (select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" )q1";
					sql += " left join ( select w1.AAR00"+ar+",AAB013,AAB014 from ("+
							" select t1.AAR00"+ar+",count(t1.AAB014) as AAB014 from (select AAR00"+ar+",b.AAB001,AAB014 from "+
							" (select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 = '"+str+"') a "+
							"LEFT JOIN (select AAB001,AAC001 from NM09_AB01 where AAR040='2015' and AAB015 IN ('1','4')) b on a.AAC001=b.AAC001 "+
							"LEFT JOIN (select AAB001,AAB014 from NM09_AB02 where AAR040='2015') c on b.AAB001=c.AAB001 "+
							") t1 where t1.AAB001 is not null and AAB014='1' group by t1.AAR00"+ar+",t1.AAB014 ) w1 join ("+
							" select t1.AAR00"+ar+",count(t1.AAB013) as AAB013 from ( select AAR00"+ar+",b.AAB001,AAB013 from "+
							" (select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"') a "+
							"LEFT JOIN (select AAB001,AAC001 from NM09_AB01 where AAR040='2015' and AAB015 IN ('1','4')) b on a.AAC001=b.AAC001 "+
							" LEFT JOIN (select AAB001,AAB013 from NM09_AB02 where AAR040='2015') c on b.AAB001=c.AAB001  "+
							" ) t1 where t1.AAB001 is not null and AAB013='1' group by t1.AAR00"+ar+",t1.AAB013 "+
							" ) w2 on w1.AAR00"+ar+"=w2.AAR00"+ar+")q2 ON Q1.xz=Q2.AAR00"+ar+" where XZQH is not null";
					List<Map> in_list = this.getBySqlMapper.findRecords(sql);
					for( int a = 0 ; a < in_list.size(); a++ ) {
						String V1 =  in_list.get(a).get("XZQH").toString();
						String V2 = "".equals(in_list.get(a).get("NUM")) || in_list.get(a).get("NUM") == null ? "" : in_list.get(a).get("NUM").toString();
						String V5 =  "".equals(in_list.get(a).get("AAB013")) || in_list.get(a).get("AAB013") == null ? "" : in_list.get(a).get("AAB013").toString();
						String V6 =  "".equals(in_list.get(a).get("AAB014")) || in_list.get(a).get("AAB014") == null ? "" : in_list.get(a).get("AAB014").toString();
						String V9 =  "".equals(in_list.get(a).get("CC")) || in_list.get(a).get("CC") == null ? "" : in_list.get(a).get("CC").toString();
						String V10 =  "".equals(in_list.get(a).get("XZ")) || in_list.get(a).get("XZ") == null ? "" : in_list.get(a).get("XZ").toString();
						String V98 =  "".equals(in_list.get(a).get("SS")) || in_list.get(a).get("SS") == null ? "" : in_list.get(a).get("SS").toString();
						String V99 =  "".equals(in_list.get(a).get("VV")) || in_list.get(a).get("VV") == null ? "" : in_list.get(a).get("VV").toString();
						String insql = "insert into PKC_1_1_0 (V1,V2,V5,V6,v9,V10,V98,V99) VALUES ("+
										"'"+V1+"','"+V2+"','"+V5+"','"+V6+"','"+V9+"','"+V10+"','"+V98+"','"+V99+"')";
						this.getBySqlMapper.insert(insql);
					}
//					System.out.println(sql);
			}
		}
	}
	/**
	 * 贫困人口年龄分组
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("PKC_1_1_1do")
	public  void  PKC_1_1_1 ( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		
		for ( int i =0 ; i < 4 ; i ++ ) {
			
			
			for ( int j = 0 ; j < 2 ; j ++ ) {
				
				
			}
			
			
		}
		
	}
	
}