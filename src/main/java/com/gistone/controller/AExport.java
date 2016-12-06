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
//						this.getBySqlMapper.insert(insql);
					}
					System.out.println(sql);
			}
		}
	}
	/**
	 * 贫困户的基本信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("PKC_1_2_1.do")
	public  void  PKC_1_2_1 ( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		for ( int i = 0 ; i < 4 ; i ++ ) {
			int v98 = 5-i;
			int ar = 6-i;
			int v10 = 2*v98;
			int xzqh = v10-1;
			for(int j = 0 ; j < 2 ; j++ ){
				String str = "";
				if ( j == 0 ){
					str = "0";
				}else {
					str = "3";
				}
				String sql="select NUM,xz,xzqh, v1,v2,v3,v4,v5, '"+j+"' type,'2015' V99 ,'"+v98+"' v98 from ( ";
						sql += "SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NM09_AC01  where AAR100= '1' ";
						sql += " and AAR040='2015' and AAR010 ='"+str+"' and (AAR010='"+str+"' and NVL(AAC016,AAR040) = '2015') or AAR010<>'"+str+"' GROUP BY AAR00"+ar+"  )AA left join ( ";
						sql += " select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where xzqh is not null)w0 left join (";
						
						
						sql += " select AAR006, count(AAC006) v1 from  (select AAR006,AAC006 from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='3' and (AAR010='0' and NVL(AAC016,AAR040) ";
						sql += "= '2015') or AAR010<>'0' ) where AAC006='01' group by AAR006) w1  ON w0.xz=W1.AAR006  left join ( select AAR006, count(AAC006) v5 from  (select AAR006,AAC006";
						sql += "  from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='3' and (AAR010='0' and NVL(AAC016,AAR040) = '2015') or AAR010<>'0' ) where AAC006='04' group by AAR006";
						System.out.println(sql);
					List<Map> in_list = this.getBySqlMapper.findRecords(sql);
					for ( int a = 0 ; a < in_list.size() ; a ++ ) {
						String COM_NAME =  in_list.get(a).get("COM_NAME").toString();
						String Z_HU = "".equals(in_list.get(a).get("Z_HU")) || in_list.get(a).get("Z_HU") == null ? "" : in_list.get(a).get("Z_HU").toString();
						String V1 =  "".equals(in_list.get(a).get("V1")) || in_list.get(a).get("V1") == null ? "" : in_list.get(a).get("V1").toString();
						String V5 =  "".equals(in_list.get(a).get("V5")) || in_list.get(a).get("V5") == null ? "" : in_list.get(a).get("V5").toString();
						String V9 =  "".equals(in_list.get(a).get("V9")) || in_list.get(a).get("V9") == null ? "" : in_list.get(a).get("V9").toString();
						String TYPE =  "".equals(in_list.get(a).get("TYPE")) || in_list.get(a).get("TYPE") == null ? "" : in_list.get(a).get("TYPE").toString();
						String COM_CODE =  "".equals(in_list.get(a).get("COM_CODE")) || in_list.get(a).get("COM_CODE") == null ? "" : in_list.get(a).get("COM_CODE").toString();
						String V99 =  "".equals(in_list.get(a).get("V99")) || in_list.get(a).get("V99") == null ? "" : in_list.get(a).get("V99").toString();
						String V98 =  "".equals(in_list.get(a).get("V98")) || in_list.get(a).get("V98") == null ? "" : in_list.get(a).get("V98").toString();
						String insql = "insert into PKC_1_2_1 (COM_NAME,Z_HU,V1,V5,V9,TYPE,COM_CODE,V99,V98) VALUES ("+
										"'"+COM_NAME+"','"+Z_HU+"','"+V1+"','"+V5+"','"+V9+"','"+TYPE+"','"+COM_CODE+"','"+V99+"','"+V98+"')";
//						this.getBySqlMapper.insert(insql);
					}
			}
		}
	}
	/**
	 * 贫困户的主要致贫原因
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("PKC_1_2_2.do")
	public  void  PKC_1_2_2 ( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		for ( int i = 0 ; i < 4 ; i ++ ) {
			int v98 = 5-i;
			int ar = 6-i;
			int v10 = 2*v98;
			int xzqh = v10-1;
			for(int j = 0 ; j < 2 ; j++ ){
				String str = "";
				if ( j == 0 ){
					str = "0";
				}else {
					str = "3";
				}
				String sql ="select NUM,xz,xzqh,v1,v3,v5,v7,v9,v11,v13,v15,v17,v19,v21,'"+j+"' type,XZ com_code,'2015' V99 ,'"+v98+"' v98 from ( ";
						sql += "SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NM09_AC01  where AAR100= '1'";
						sql += " and AAR040='2015' and AAR010 ='0' and (AAR010='1' and NVL(AAC016,AAR040) = '2015') or AAR010<>'1' GROUP BY AAR00"+ar+"  )AA left join ( ";
						sql += " select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where xzqh is not null)w0 left join (";
						
						
						sql += " (select AAR00"+ar+", count(AAC007) v1 from  (select AAR00"+ar+",AAC007 from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and";
						sql += " (AAR010='"+str+"' and NVL(AAC016,AAR040) = 2015) or AAR010<>'"+str+"') where AAC007='01'group by AAR00"+ar+"))w1 ON w0.xz=w1.AAR00"+ar+"  LEFT JOIN (";
						sql += " select AAR00"+ar+" v3_6, count(AAC007) v3 from (select AAR00"+ar+",AAC007 from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and ";
						sql += " (AAR010='"+str+"' and NVL(AAC016,AAR040) = 2015) or AAR010<>'"+str+"' ) where AAC007='02' group by AAR00"+ar+")w2 ON w0.xz=w2.v3_6 LEFT JOIN(";
						
						sql += "select AAR00"+ar+", count(AAC007) v5 from (select AAR00"+ar+",AAC007 from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and ";
						sql += "(AAR010='"+str+"' and NVL(AAC016,AAR040) = 2015) or AAR010<>'"+str+"') where AAC007='03' group by AAR00"+ar+" ) w3 on w0.xz=w3.AAR00"+ar+" LEFT JOIN  (";
						sql += "select AAR00"+ar+", count(AAC007) v7 from (select AAR00"+ar+",AAC007 from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and ";
						sql += " (AAR010='"+str+"' and NVL(AAC016,AAR040) = 2015) or AAR010<>'"+str+"'";
						sql += " ) where AAC007='04' group by AAR00"+ar+") w4 on w0.xz=w4.AAR00"+ar+" LEFT JOIN (select AAR00"+ar+", count(AAC007) v9 from ";
						
						sql += "(select AAR00"+ar+",AAC007 from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and (AAR010='"+str+"'and NVL(AAC016,AAR040) = 2015) or AAR010<>'"+str+"'";
						sql += ") where AAC007='05' group by AAR00"+ar+" ) w5 on w0.xz=w5.AAR00"+ar+" LEFT JOIN (select AAR00"+ar+", count(AAC007) v11 from ";
						sql += " (select AAR00"+ar+",AAC007 from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and (AAR010='"+str+"' and NVL(AAC016,AAR040) = 2015) or AAR010<>'"+str+"'";
						sql += " ) where AAC007='06' group by AAR00"+ar+") w6 on w0.xz=w6.AAR00"+ar+"  LEFT JOIN (";
						sql += " select AAR00"+ar+", count(AAC007) v13 from  (select AAR00"+ar+",AAC007 from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and ";
						sql += " (AAR010='"+str+"' and NVL(AAC016,AAR040) = 2015) or AAR010<>'"+str+"' ) where AAC007='07' group by AAR00"+ar+" ) w7 on w0.xz=w7.AAR00"+ar+" LEFT JOIN (";
						sql += "select AAR00"+ar+", count(AAC007) v15 from (select AAR00"+ar+",AAC007 from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and ";
						sql += " (AAR010='"+str+"' and NVL(AAC016,AAR040) = 2015) or AAR010<>'"+str+"' ) where AAC007='08' group by AAR00"+ar+" ) w8 on w0.xz=w8.AAR00"+ar+" LEFT JOIN (";
						sql += "select AAR00"+ar+", count(AAC007) v17 from (select AAR00"+ar+",AAC007 from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and ";
						sql += " (AAR010='"+str+"' and NVL(AAC016,AAR040) = 2015) or AAR010<>'"+str+"' ) where AAC007='09' group by AAR00"+ar+" ) w9 on w0.xz=w9.AAR00"+ar+"  LEFT JOIN (";
						sql += "select AAR00"+ar+", count(AAC007) v19 from (select AAR00"+ar+",AAC007 from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and ";
						sql += " (AAR010='"+str+"' and NVL(AAC016,AAR040) = 2015) or AAR010<>'"+str+"' ) where AAC007='10' group by AAR00"+ar+" ) w10 on w0.xz=w10.AAR00"+ar+" LEFT JOIN ( ";
						sql += "select AAR00"+ar+", count(AAC007) v21 from (select AAR00"+ar+",AAC007 from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and ";
						sql += "(AAR010='"+str+"' and NVL(AAC016,AAR040) = 2015) or AAR010<>'"+str+"' ) where AAC007='11' group by AAR00"+ar+" ) w11 on w0.xz=w11.AAR00"+ar+"";
						
						System.out.println(sql);
					List<Map> in_list = this.getBySqlMapper.findRecords(sql);
					for ( int a = 0 ; a < in_list.size() ; a ++ ) {
						String COM_NAME =  in_list.get(a).get("XZQH").toString();
						String Z_HU = "".equals(in_list.get(a).get("NUM")) || in_list.get(a).get("NUM") == null ? "0" : in_list.get(a).get("NUM").toString();
						String V1 =  "".equals(in_list.get(a).get("V1")) || in_list.get(a).get("V1") == null ? "0" : in_list.get(a).get("V1").toString();
						String V3 =  "".equals(in_list.get(a).get("V3")) || in_list.get(a).get("V3") == null ? "0" : in_list.get(a).get("V3").toString();
						String V5 =  "".equals(in_list.get(a).get("V5")) || in_list.get(a).get("V5") == null ? "0" : in_list.get(a).get("V5").toString();
						String V7 =  "".equals(in_list.get(a).get("V7")) || in_list.get(a).get("V7") == null ? "0" : in_list.get(a).get("V7").toString();
						String V9 =  "".equals(in_list.get(a).get("V9")) || in_list.get(a).get("V9") == null ? "0" : in_list.get(a).get("V9").toString();
						String V11 =  "".equals(in_list.get(a).get("V11")) || in_list.get(a).get("V11") == null ? "0" : in_list.get(a).get("V11").toString();
						String V13 =  "".equals(in_list.get(a).get("V13")) || in_list.get(a).get("V13") == null ? "0" : in_list.get(a).get("V13").toString();
						String V15 =  "".equals(in_list.get(a).get("V15")) || in_list.get(a).get("V15") == null ? "0" : in_list.get(a).get("V15").toString();
						String V17 =  "".equals(in_list.get(a).get("V17")) || in_list.get(a).get("V17") == null ? "0" : in_list.get(a).get("V17").toString();
						String V19 =  "".equals(in_list.get(a).get("V19")) || in_list.get(a).get("V19") == null ? "0" : in_list.get(a).get("V19").toString();
						String V21 =  "".equals(in_list.get(a).get("V21")) || in_list.get(a).get("V21") == null ? "0" : in_list.get(a).get("V21").toString();
						String TYPE =  "".equals(in_list.get(a).get("TYPE")) || in_list.get(a).get("TYPE") == null ? "0" : in_list.get(a).get("TYPE").toString();
						String COM_CODE =  "".equals(in_list.get(a).get("XZ")) || in_list.get(a).get("XZ") == null ? "0" : in_list.get(a).get("XZ").toString();
						String V99 =  "".equals(in_list.get(a).get("V99")) || in_list.get(a).get("V99") == null ? "0" : in_list.get(a).get("V99").toString();
						String V98 =  "".equals(in_list.get(a).get("V98")) || in_list.get(a).get("V98") == null ? "0" : in_list.get(a).get("V98").toString();
						String insql = "insert into PKC_1_2_2 (COM_NAME,Z_HU,V1,V3,V5,V7,V9,V11,V13,V15,V17,V19,V21,TYPE,COM_CODE,V99,V98) VALUES ("+
										"'"+COM_NAME+"','"+Z_HU+"','"+V1+"','"+V3+"','"+V5+"','"+V7+"','"+V9+"','"+V11+"','"+V13+"','"+V15+"','"+V17+"','"+V19+"','"+V21+"','"+TYPE+"','"+COM_CODE+"','"+V99+"','"+V98+"')";
						this.getBySqlMapper.insert(insql);
					}
			}
		}
	}
	/**
	 * 贫困户的主要致贫原因
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("PKC_1_2_3.do")
	public  void  PKC_1_2_3 ( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		for ( int i = 0 ; i < 4 ; i ++ ) {
			int v98 = 5-i;
			int ar = 6-i;
			int v10 = 2*v98;
			int xzqh = v10-1;
			for(int j = 0 ; j < 2 ; j++ ){
				String str = "";
				if ( j == 0 ){
					str = "0";
				}else {
					str = "3";
				}
				String sql ="select NUM,xz,xzqh,v1,v3,v5,v7,v9,v11,v13,v15,v17,v19,v21,V23,V25, '"+j+"' type,XZ com_code,'2015' V99 ,'"+v98+"' v98 from ( ";
						sql += "SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NM09_AC01  where AAR100= '"+str+"'";
						sql += " and AAR040='2015' and AAR010 ='0' and (AAR010='"+str+"' and NVL(AAC016,AAR040) = '2015') or AAR010<>'"+str+"' GROUP BY AAR00"+ar+"  )AA left join ( ";
						sql += " select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where xzqh is not null)w0 left join (";
						
						
						sql += " (select AAR00"+ar+", count(AAC008) v1 from  (select AAR00"+ar+",AAC008 from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and";
						sql += " (AAR010='"+str+"' and NVL(AAC016,AAR040) = 2015) or AAR010<>'"+str+"') where AAC008='01'group by AAR00"+ar+"))w1 ON w0.xz=w1.AAR00"+ar+"  LEFT JOIN (";
						sql += " select AAR00"+ar+" v3_6, count(AAC008) v3 from (select AAR00"+ar+",AAC008 from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and ";
						sql += " (AAR010='"+str+"' and NVL(AAC016,AAR040) = 2015) or AAR010<>'"+str+"' ) where AAC008='02' group by AAR00"+ar+")w2 ON w0.xz=w2.v3_6 LEFT JOIN(";
						
						sql += "select AAR00"+ar+", count(AAC008) v5 from (select AAR00"+ar+",AAC008 from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and ";
						sql += "(AAR010='"+str+"' and NVL(AAC016,AAR040) = 2015) or AAR010<>'"+str+"') where AAC008='03' group by AAR00"+ar+" ) w3 on w0.xz=w3.AAR00"+ar+" LEFT JOIN  (";
						sql += "select AAR00"+ar+", count(AAC008) v7 from (select AAR00"+ar+",AAC008 from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and ";
						sql += " (AAR010='"+str+"' and NVL(AAC016,AAR040) = 2015) or AAR010<>'"+str+"'";
						sql += " ) where AAC008='04' group by AAR00"+ar+") w4 on w0.xz=w4.AAR00"+ar+" LEFT JOIN (select AAR00"+ar+", count(AAC008) v9 from ";
						
						sql += "(select AAR00"+ar+",AAC008 from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and (AAR010='"+str+"'and NVL(AAC016,AAR040) = 2015) or AAR010<>'"+str+"'";
						sql += ") where AAC008='05' group by AAR00"+ar+" ) w5 on w0.xz=w5.AAR00"+ar+" LEFT JOIN (select AAR00"+ar+", count(AAC008) v11 from ";
						sql += " (select AAR00"+ar+",AAC008 from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and (AAR010='"+str+"' and NVL(AAC016,AAR040) = 2015) or AAR010<>'"+str+"'";
						sql += " ) where AAC008='06' group by AAR00"+ar+") w6 on w0.xz=w6.AAR00"+ar+"  LEFT JOIN (";
						sql += " select AAR00"+ar+", count(AAC008) v13 from  (select AAR00"+ar+",AAC008 from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and ";
						sql += " (AAR010='"+str+"' and NVL(AAC016,AAR040) = 2015) or AAR010<>'"+str+"' ) where AAC008='07' group by AAR00"+ar+" ) w7 on w0.xz=w7.AAR00"+ar+" LEFT JOIN (";
						sql += "select AAR00"+ar+", count(AAC008) v15 from (select AAR00"+ar+",AAC008 from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and ";
						sql += " (AAR010='"+str+"' and NVL(AAC016,AAR040) = 2015) or AAR010<>'"+str+"' ) where AAC008='08' group by AAR00"+ar+" ) w8 on w0.xz=w8.AAR00"+ar+" LEFT JOIN (";
						sql += "select AAR00"+ar+", count(AAC008) v17 from (select AAR00"+ar+",AAC008 from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and ";
						sql += " (AAR010='"+str+"' and NVL(AAC016,AAR040) = 2015) or AAR010<>'"+str+"' ) where AAC008='09' group by AAR00"+ar+" ) w9 on w0.xz=w9.AAR00"+ar+"  LEFT JOIN (";
						sql += "select AAR00"+ar+", count(AAC008) v19 from (select AAR00"+ar+",AAC008 from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and ";
						sql += " (AAR010='"+str+"' and NVL(AAC016,AAR040) = 2015) or AAR010<>'"+str+"' ) where AAC008='10' group by AAR00"+ar+" ) w10 on w0.xz=w10.AAR00"+ar+" LEFT JOIN ( ";
						sql += "select AAR00"+ar+", count(AAC008) v21 from (select AAR00"+ar+",AAC008 from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and ";
						sql += "(AAR010='"+str+"' and NVL(AAC016,AAR040) = 2015) or AAR010<>'"+str+"' ) where AAC008='11' group by AAR00"+ar+" ) w11 on w0.xz=w11.AAR00"+ar+" LEFT JOIN (";
						
						sql += "select AAR00"+ar+", count(AAC008) v23 from (select AAR00"+ar+",AAC008 from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and ";
						sql += "(AAR010='"+str+"' and NVL(AAC016,AAR040) = 2015) or AAR010<>'"+str+"' ) where AAC008='12' group by AAR00"+ar+" ) w12 on w0.xz=w12.AAR00"+ar+" LEFT JOIN (";
						sql += "select AAR00"+ar+", count(AAC008) v25 from (select AAR00"+ar+",AAC008 from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and ";
						sql += "(AAR010='"+str+"' and NVL(AAC016,AAR040) = 2015) or AAR010<>'"+str+"' ) where AAC008='99' group by AAR00"+ar+" ) w13 on w0.xz=w13.AAR00"+ar+" ";
						System.out.println(sql);
					List<Map> in_list = this.getBySqlMapper.findRecords(sql);
					for ( int a = 0 ; a < in_list.size() ; a ++ ) {
						String COM_NAME =  in_list.get(a).get("XZQH").toString();
						String Z_HU = "".equals(in_list.get(a).get("NUM")) || in_list.get(a).get("NUM") == null ? "0" : in_list.get(a).get("NUM").toString();
						String V1 =  "".equals(in_list.get(a).get("V1")) || in_list.get(a).get("V1") == null ? "0" : in_list.get(a).get("V1").toString();
						String V3 =  "".equals(in_list.get(a).get("V3")) || in_list.get(a).get("V3") == null ? "0" : in_list.get(a).get("V3").toString();
						String V5 =  "".equals(in_list.get(a).get("V5")) || in_list.get(a).get("V5") == null ? "0" : in_list.get(a).get("V5").toString();
						String V7 =  "".equals(in_list.get(a).get("V7")) || in_list.get(a).get("V7") == null ? "0" : in_list.get(a).get("V7").toString();
						String V9 =  "".equals(in_list.get(a).get("V9")) || in_list.get(a).get("V9") == null ? "0" : in_list.get(a).get("V9").toString();
						String V11 =  "".equals(in_list.get(a).get("V11")) || in_list.get(a).get("V11") == null ? "0" : in_list.get(a).get("V11").toString();
						String V13 =  "".equals(in_list.get(a).get("V13")) || in_list.get(a).get("V13") == null ? "0" : in_list.get(a).get("V13").toString();
						String V15 =  "".equals(in_list.get(a).get("V15")) || in_list.get(a).get("V15") == null ? "0" : in_list.get(a).get("V15").toString();
						String V17 =  "".equals(in_list.get(a).get("V17")) || in_list.get(a).get("V17") == null ? "0" : in_list.get(a).get("V17").toString();
						String V19 =  "".equals(in_list.get(a).get("V19")) || in_list.get(a).get("V19") == null ? "0" : in_list.get(a).get("V19").toString();
						String V21 =  "".equals(in_list.get(a).get("V21")) || in_list.get(a).get("V21") == null ? "0" : in_list.get(a).get("V21").toString();
						String V23 =  "".equals(in_list.get(a).get("V23")) || in_list.get(a).get("V23") == null ? "0" : in_list.get(a).get("V23").toString();
						String V25 =  "".equals(in_list.get(a).get("V25")) || in_list.get(a).get("V25") == null ? "0" : in_list.get(a).get("V25").toString();
						String TYPE =  "".equals(in_list.get(a).get("TYPE")) || in_list.get(a).get("TYPE") == null ? "0" : in_list.get(a).get("TYPE").toString();
						String COM_CODE =  "".equals(in_list.get(a).get("XZ")) || in_list.get(a).get("XZ") == null ? "0" : in_list.get(a).get("XZ").toString();
						String V99 =  "".equals(in_list.get(a).get("V99")) || in_list.get(a).get("V99") == null ? "0" : in_list.get(a).get("V99").toString();
						String V98 =  "".equals(in_list.get(a).get("V98")) || in_list.get(a).get("V98") == null ? "0" : in_list.get(a).get("V98").toString();
						String insql = "insert into PKC_1_2_3 (COM_NAME,Z_HU,V1,V3,V5,V7,V9,V11,V13,V15,V17,V19,V21,V23,V25,TYPE,COM_CODE,V99,V98) VALUES ("+
										"'"+COM_NAME+"','"+Z_HU+"','"+V1+"','"+V3+"','"+V5+"','"+V7+"','"+V9+"','"+V11+"','"+V13+"','"+V15+"','"+V17+"','"+V19+"','"+V21+"','"+V23+"','"+V25+"','"+TYPE+"','"+COM_CODE+"','"+V99+"','"+V98+"')";
						this.getBySqlMapper.insert(insql);
					}
			}
		}
	}
	/**
	 * 贫困户的主要致贫原因
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("PKC_1_2_4.do")
	public  void  PKC_1_2_4 ( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		for ( int i = 0 ; i < 4 ; i ++ ) {
			int v98 = 5-i;
			int ar = 6-i;
			int v10 = 2*v98;
			int xzqh = v10-1;
			for(int j = 0 ; j < 2 ; j++ ){
				String str = "";
				if ( j == 0 ){
					str = "0";
				}else {
					str = "3";
				}
				String sql ="select NUM,xz,xzqh, v1, '"+j+"' type,XZ com_code,'2015' V99 ,'"+v98+"' v98 from ( ";
						sql += "SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NM09_AC01  where AAR100= '1' ";
						sql += " and AAR040='2015' and AAR010 ='"+str+"' and (AAR010='"+str+"' and NVL(AAC016,AAR040) = '2015') or AAR010<>'"+str+"' GROUP BY AAR00"+ar+"  )AA left join ( ";
						sql += " select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where xzqh is not null)w0 left join (";
						
						sql += "select count(AAC001) v1 ,AAR00"+ar+" from (select a.AAC001,AAR00"+ar+" from (";
						sql += "select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and";
						sql += "(AAR010='"+str+"' and NVL(AAC016,AAR040) = '2015') or AAR010<>'"+str+"') a left join (select * from AC08) b on a.AAC001=b.AAC001 where ";
						sql += "SUBSTR(b.AAR020, 0, 4) <='2015' AND SUBSTR(b.AAR021, 0, 4) >='2015' )t1 group BY AAR00"+ar+")w1 ON w0.xz=w1.AAR00"+ar+" ";
						
						System.out.println(sql);
					List<Map> in_list = this.getBySqlMapper.findRecords(sql);
					for ( int a = 0 ; a < in_list.size() ; a ++ ) {
						String COM_NAME =  in_list.get(a).get("XZQH").toString();
						String Z_HU = "".equals(in_list.get(a).get("NUM")) || in_list.get(a).get("NUM") == null ? "0" : in_list.get(a).get("NUM").toString();
						String V1 =  "".equals(in_list.get(a).get("V1")) || in_list.get(a).get("V1") == null ? "0" : in_list.get(a).get("V1").toString();
						double zb = (Double.parseDouble(V1)/Double.parseDouble(Z_HU))*100;
						String TYPE =  "".equals(in_list.get(a).get("TYPE")) || in_list.get(a).get("TYPE") == null ? "0" : in_list.get(a).get("TYPE").toString();
						String COM_CODE =  "".equals(in_list.get(a).get("XZ")) || in_list.get(a).get("XZ") == null ? "0" : in_list.get(a).get("XZ").toString();
						String V99 =  "".equals(in_list.get(a).get("V99")) || in_list.get(a).get("V99") == null ? "0" : in_list.get(a).get("V99").toString();
						String V98 =  "".equals(in_list.get(a).get("V98")) || in_list.get(a).get("V98") == null ? "0" : in_list.get(a).get("V98").toString();
						String insql = "insert into PKC_1_2_4 (COM_NAME,Z_HU,V1,V2,TYPE,COM_CODE,V99,V98) VALUES ("+
										"'"+COM_NAME+"','"+Z_HU+"','"+V1+"','"+zb+"','"+TYPE+"','"+COM_CODE+"','"+V99+"','"+V98+"')";
						this.getBySqlMapper.insert(insql);
					}
			}
		}
	}
	/**
	 * 贫困户的主要致贫原因
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("PKC_1_2_5.do")
	public  void  PKC_1_2_5 ( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		for ( int i = 0 ; i < 4 ; i ++ ) {
			int v98 = 5-i;
			int ar = 6-i;
			int v10 = 2*v98;
			int xzqh = v10-1;
			for(int j = 0 ; j < 2 ; j++ ){
				String str = "";
				if ( j == 0 ){
					str = "0";
				}else {
					str = "3";
				}
				String sql ="select NUM,xz,xzqh, v1,v3,v5,v11,v13, '"+j+"' type,XZ com_code,'2015' V99 ,'"+v98+"' v98 from ( ";
						sql += "SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NM09_AC01  where AAR100= '1' ";
						sql += " and AAR040='2015' and AAR010 ='"+str+"' and (AAR010='"+str+"' and NVL(AAC016,AAR040) = '2015') or AAR010<>'"+str+"' GROUP BY AAR00"+ar+"  )AA left join ( ";
						sql += " select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where xzqh is not null)w0 left join (";
						
						sql += "select count(AAC001) v1 ,AAR00"+ar+" from (select a.AAC001,AAR00"+ar+" from (select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and ";
						sql += "(AAR010='"+str+"' and NVL(AAC016,AAR040) = 2015) or AAR010<>'"+str+"') a left join (select * from NM09_ac31 where AAC311='1') b on a.AAC001=b.AAC001 ";
						sql += " )t1 group BY AAR00"+ar+" )w1 ON w0.xz=w1.AAR00"+ar+" LEFT JOIN( select count(AAC001) v3 ,AAR00"+ar+" from ( select a.AAC001,AAR00"+ar+" from (";
						sql += "select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and (AAR010='"+str+"' and NVL(AAC016,AAR040) = 2015) or AAR010<>'"+str+"') a left join ";
						sql += " (select * from NM09_ac31 where AAC312='1') b on a.AAC001=b.AAC001 )t1 group BY AAR00"+ar+" )w2 ON w0.xz=w2.AAR00"+ar+" LEFT JOIN(";
						sql += " select count(AAC001) v5 ,AAR00"+ar+" from ( select a.AAC001,AAR00"+ar+" from (";
						sql += "select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and ";
						sql += "(AAR010='"+str+"' and NVL(AAC016,AAR040) = 2015) or AAR010<>'"+str+"') a left join ";
						sql += "(select * from NM09_ac31 where AAC313='1') b on a.AAC001=b.AAC001 ";
						sql += ")t1 group BY AAR00"+ar+" )w3 ON w0.xz=w3.AAR00"+ar+" LEFT JOIN (select count(AAC001) v11 ,AAR00"+ar+" from (";
						sql += "select a.AAC001,AAR00"+ar+" from (select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and ";
						sql += "(AAR010='"+str+"' and NVL(AAC016,AAR040) = 2015) or AAR010<>'"+str+"') a left join ";
						sql += " (select * from NM09_ac31 where AAC319='1') b on a.AAC001=b.AAC001 )t1 group BY AAR00"+ar+" )w5 ON w0.xz=w5.AAR00"+ar+" LEFT JOIN(";
						sql += " select sum(AAC317) v13,AAR00"+ar+" from ( select a.AAC001,AAR00"+ar+" ,AAC317 from (select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and";
						sql += "(AAR010='"+str+"' and NVL(AAC016,AAR040) = 2015) or AAR010<>'"+str+"') a left join (select AAC317,AAC001 from NM09_ac31) b on a.AAC001=b.AAC001  ";
						sql += ")t1 group BY AAR00"+ar+" )w6 ON w0.xz=w6.AAR00"+ar+" ";
						System.out.println(sql);
					List<Map> in_list = this.getBySqlMapper.findRecords(sql);
					for ( int a = 0 ; a < in_list.size() ; a ++ ) {
						String COM_NAME =  in_list.get(a).get("XZQH").toString();
						String Z_HU = "".equals(in_list.get(a).get("NUM")) || in_list.get(a).get("NUM") == null ? "0" : in_list.get(a).get("NUM").toString();
						String V1 =  "".equals(in_list.get(a).get("V1")) || in_list.get(a).get("V1") == null ? "0" : in_list.get(a).get("V1").toString();
						int v2 = (Integer.parseInt(V1)/Integer.parseInt(Z_HU))*100;
						
						String V3 =  "".equals(in_list.get(a).get("V3")) || in_list.get(a).get("V3") == null ? "0" : in_list.get(a).get("V3").toString();
						int v4 = (Integer.parseInt(V3)/Integer.parseInt(Z_HU))*100;
						String V5 =  "".equals(in_list.get(a).get("V5")) || in_list.get(a).get("V5") == null ? "0" : in_list.get(a).get("V5").toString();
						int v6 = (Integer.parseInt(V5)/Integer.parseInt(Z_HU))*100;
						
						String V11 =  "".equals(in_list.get(a).get("V11")) || in_list.get(a).get("V11") == null ? "0" : in_list.get(a).get("V11").toString();
						int v12 = (Integer.parseInt(V11)/Integer.parseInt(Z_HU))*100;
						String V13 =  "".equals(in_list.get(a).get("V13")) || in_list.get(a).get("V13") == null ? "0" : in_list.get(a).get("V13").toString();
						
						String TYPE =  "".equals(in_list.get(a).get("TYPE")) || in_list.get(a).get("TYPE") == null ? "0" : in_list.get(a).get("TYPE").toString();
						String COM_CODE =  "".equals(in_list.get(a).get("XZ")) || in_list.get(a).get("XZ") == null ? "0" : in_list.get(a).get("XZ").toString();
						String V99 =  "".equals(in_list.get(a).get("V99")) || in_list.get(a).get("V99") == null ? "0" : in_list.get(a).get("V99").toString();
						String V98 =  "".equals(in_list.get(a).get("V98")) || in_list.get(a).get("V98") == null ? "0" : in_list.get(a).get("V98").toString();
						String insql = "insert into PKC_1_2_5 (COM_NAME,Z_HU,V1,V2,v3,v4,v5,v6,v11,v12,v13,TYPE,COM_CODE,V99,V98) VALUES ("+
										"'"+COM_NAME+"','"+Z_HU+"','"+V1+"','"+v2+"','"+V3+"','"+v4+"','"+V5+"','"+v6+"','"+V11+"','"+v12+"','"+V13+"','"+TYPE+"','"+COM_CODE+"','"+V99+"','"+V98+"')";
						this.getBySqlMapper.insert(insql);
					}
			}
		}
	}
	/**
	 * 贫困户的主要致贫原因
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("PKC_1_2_6.do")
	public  void  PKC_1_2_6 ( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		for ( int i = 0 ; i < 4 ; i ++ ) {
			int v98 = 5-i;
			int ar = 6-i;
			int v10 = 2*v98;
			int xzqh = v10-1;
			for(int j = 0 ; j < 2 ; j++ ){
				String str = "";
				if ( j == 0 ){
					str = "0";
				}else {
					str = "3";
				}
				String sql ="select NUM,xz,xzqh, v1,v3,v5,v11,v13, '"+j+"' type,XZ com_code,'2015' V99 ,'"+v98+"' v98 from ( ";
						sql += "SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NM09_AC01  where AAR100= '1' ";
						sql += " and AAR040='2015' and AAR010 ='"+str+"' and (AAR010='"+str+"' and NVL(AAC016,AAR040) = '2015') or AAR010<>'"+str+"' GROUP BY AAR00"+ar+"  )AA left join ( ";
						sql += " select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where xzqh is not null)w0 left join (";
						
						sql += "select AAR00"+ar+" ,sum(AAC301) v1,sum(AAC302)v3,sum(AAC303)v5,sum(AAC304)v7,sum(AAC305)v9,sum(AAC306)v11,sum(AAC307)v13 from (";
						sql += " select AAC001,AAR00"+ar+"  from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and ";
						sql += "(AAR010='"+str+"' and NVL(AAC016,AAR040) = 2015) or AAR010<>'"+str+"') a left join (select * from NM09_AC30) b on a.AAC001=b.AAC001 group BY AAR00"+ar+" ";
						sql += ")w1 ON w0.xz=w1.AAR00"+ar+" ";
						
						System.out.println(sql);
					List<Map> in_list = this.getBySqlMapper.findRecords(sql);
					for ( int a = 0 ; a < in_list.size() ; a ++ ) {
						String COM_NAME =  in_list.get(a).get("XZQH").toString();
						String Z_HU = "".equals(in_list.get(a).get("NUM")) || in_list.get(a).get("NUM") == null ? "0" : in_list.get(a).get("NUM").toString();
						String V1 =  "".equals(in_list.get(a).get("V1")) || in_list.get(a).get("V1") == null ? "0" : in_list.get(a).get("V1").toString();
						String V3 =  "".equals(in_list.get(a).get("V3")) || in_list.get(a).get("V3") == null ? "0" : in_list.get(a).get("V3").toString();
						String V5 =  "".equals(in_list.get(a).get("V5")) || in_list.get(a).get("V5") == null ? "0" : in_list.get(a).get("V5").toString();
						String V7 =  "".equals(in_list.get(a).get("V7")) || in_list.get(a).get("V7") == null ? "0" : in_list.get(a).get("V7").toString();
						String V9 =  "".equals(in_list.get(a).get("V9")) || in_list.get(a).get("V9") == null ? "0" : in_list.get(a).get("V9").toString();
						String V11 =  "".equals(in_list.get(a).get("V11")) || in_list.get(a).get("V11") == null ? "0" : in_list.get(a).get("V11").toString();
						String V13 =  "".equals(in_list.get(a).get("V13")) || in_list.get(a).get("V13") == null ? "0" : in_list.get(a).get("V13").toString();
						String TYPE =  "".equals(in_list.get(a).get("TYPE")) || in_list.get(a).get("TYPE") == null ? "0" : in_list.get(a).get("TYPE").toString();
						String COM_CODE =  "".equals(in_list.get(a).get("XZ")) || in_list.get(a).get("XZ") == null ? "0" : in_list.get(a).get("XZ").toString();
						String V99 =  "".equals(in_list.get(a).get("V99")) || in_list.get(a).get("V99") == null ? "0" : in_list.get(a).get("V99").toString();
						String V98 =  "".equals(in_list.get(a).get("V98")) || in_list.get(a).get("V98") == null ? "0" : in_list.get(a).get("V98").toString();
						double zong = Double.parseDouble(V1)+Double.parseDouble(V3)+Double.parseDouble(V5)+Double.parseDouble(V7)+Double.parseDouble(V9)+Double.parseDouble(V11)+Double.parseDouble(V13);
						double V2 = (Double.parseDouble(V1)/zong)*100;
						double V4 = (Double.parseDouble(V3)/zong)*100;
						double V6 = (Double.parseDouble(V5)/zong)*100;
						double V8 = (Double.parseDouble(V7)/zong)*100;
						double V10 = (Double.parseDouble(V9)/zong)*100;
						double V12 = (Double.parseDouble(V11)/zong)*100;
						double V14 = (Double.parseDouble(V13)/zong)*100;
						String insql = "insert into PKC_1_2_6 (COM_NAME,Z_HU,V1,V2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12,v13,v14,TYPE,COM_CODE,V99,V98) VALUES ("+
										"'"+COM_NAME+"','"+Z_HU+"','"+V1+"','"+V2+"','"+V3+"','"+V4+"','"+V5+"','"+V6+"','"+V7+"','"+V8+"','"+V9+"','"+V10+"','"+V11+"','"+V12+"','"+V13+"','"+V14+"','"+TYPE+"','"+COM_CODE+"','"+V99+"','"+V98+"')";
						this.getBySqlMapper.insert(insql);
					}
			}
		}
	}
	/**
	 * 贫困户的主要致贫原因
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("PKC_1_2_7.do")
	public  void  PKC_1_2_7 ( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		for ( int i = 0 ; i < 4 ; i ++ ) {
			int v98 = 5-i;
			int ar = 6-i;
			int v10 = 2*v98;
			int xzqh = v10-1;
			for(int j = 0 ; j < 2 ; j++ ){
				String str = "";
				if ( j == 0 ){
					str = "0";
				}else {
					str = "3";
				}
				String sql ="select NUM,xz,xzqh, v1,v3,v5,v7,v9,v11,v13, '"+j+"' type,'2015' V99 ,'"+v98+"' v98 from ( ";
						sql += "SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NM09_AC01  where AAR100= '1' ";
						sql += " and AAR040='2015' and AAR010 ='"+str+"' and (AAR010='"+str+"' and NVL(AAC016,AAR040) = '2015') or AAR010<>'"+str+"' GROUP BY AAR00"+ar+"  )AA left join ( ";
						sql += " select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where xzqh is not null)w0 left join (";
						
						sql += "select AAR00"+ar+",count(*) v1 from (select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and(AAR010='"+str+"' and NVL(AAC016,AAR040) = '2015')";
						sql += " or AAR010<>'"+str+"') a left join (select AAC001,AAC082 from nm09_AC07) b on a.AAC001=b.AAC001 where b.AAC082<500 group BY AAR00"+ar+")w1 ON w0.xz=w1.AAR00"+ar+" left join(";
						sql += " select AAR00"+ar+",count(*) v3 from(select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and(AAR010='"+str+"' and NVL(AAC016,AAR040) = '2015') ";
						sql += " or AAR010<>'"+str+"') a left join (select AAC001,AAC082 from nm09_AC07) b on a.AAC001=b.AAC001  where b.AAC082 >= 500 AND b.AAC082 < 1000 group BY AAR00"+ar+" )w2 ON w0.xz=w2.AAR00"+ar+" left join(";
						sql += "select AAR00"+ar+",count(*) v5 from (select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and(AAR010 ='"+str+"' and NVL(AAC016,AAR040) = '2015')";
						sql += " or AAR010<>'"+str+"') a left join (select AAC001,AAC082 from nm09_AC07) b on a.AAC001=b.AAC001 where b.AAC082 >= 1000 AND b.AAC082 < 1500 group BY AAR00"+ar+" )w3 ON w0.xz=w3.AAR00"+ar+" LEFT JOIN(";
						sql += "select AAR00"+ar+",count(*) v7 from (select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and(AAR010 ='"+str+"' and NVL(AAC016,AAR040) = '2015')";
						sql += " or AAR010<>'"+str+"') a left join (select AAC001,AAC082 from nm09_AC07) b on a.AAC001=b.AAC001 where b.AAC082 >= 1500 AND b.AAC082 < 2000   group BY AAR00"+ar+"";
						sql += ")w4 ON w0.xz=w4.AAR00"+ar+" LEFT JOIN(select AAR00"+ar+",count(*) v9 from (select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and(AAR010 ='"+str+"' and NVL(AAC016,AAR040) = '2015')";
						sql += " or AAR010<>'"+str+"') a left join (select AAC001,AAC082 from nm09_AC07) b on a.AAC001=b.AAC001 where b.AAC082 >= 2000 AND b.AAC082 < 2500 group BY AAR00"+ar+" )w5 ON w0.xz=w5.AAR00"+ar+" LEFT JOIN (";
						sql += "select AAR00"+ar+",count(*) v11 from (select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and(AAR010 ='"+str+"' and NVL(AAC016,AAR040) = '2015')";
						sql += " or AAR010<>'"+str+"') a left join (select AAC001,AAC082 from nm09_AC07) b on a.AAC001=b.AAC001 where b.AAC082 >= 2500 AND b.AAC082 < 2800  group BY AAR00"+ar+")w6 ON w0.xz=w6.AAR00"+ar+" LEFT JOIN (";
						sql += "select AAR00"+ar+",count(*) v13 from (select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and(AAR010 ='"+str+"' and NVL(AAC016,AAR040) = '2015')";
						sql += " or AAR010<>'"+str+"') a left join (select AAC001,AAC082 from nm09_AC07) b on a.AAC001=b.AAC001 where b.AAC082 >= 2800 group BY AAR00"+ar+")w7 ON w0.xz=w7.AAR00"+ar+"";
						
						System.out.println(sql);
					List<Map> in_list = this.getBySqlMapper.findRecords(sql);
					for ( int a = 0 ; a < in_list.size() ; a ++ ) {
						String COM_NAME =  in_list.get(a).get("XZQH").toString();
						String Z_HU = "".equals(in_list.get(a).get("NUM")) || in_list.get(a).get("NUM") == null ? "0" : in_list.get(a).get("NUM").toString();
						String V1 =  "".equals(in_list.get(a).get("V1")) || in_list.get(a).get("V1") == null ? "0" : in_list.get(a).get("V1").toString();
						String V3 =  "".equals(in_list.get(a).get("V3")) || in_list.get(a).get("V3") == null ? "0" : in_list.get(a).get("V3").toString();
						String V5 =  "".equals(in_list.get(a).get("V5")) || in_list.get(a).get("V5") == null ? "0" : in_list.get(a).get("V5").toString();
						String V7 =  "".equals(in_list.get(a).get("V7")) || in_list.get(a).get("V7") == null ? "0" : in_list.get(a).get("V7").toString();
						String V9 =  "".equals(in_list.get(a).get("V9")) || in_list.get(a).get("V9") == null ? "0" : in_list.get(a).get("V9").toString();
						String V11 =  "".equals(in_list.get(a).get("V11")) || in_list.get(a).get("V11") == null ? "0" : in_list.get(a).get("V11").toString();
						String V13 =  "".equals(in_list.get(a).get("V13")) || in_list.get(a).get("V13") == null ? "0" : in_list.get(a).get("V13").toString();
						String TYPE =  "".equals(in_list.get(a).get("TYPE")) || in_list.get(a).get("TYPE") == null ? "0" : in_list.get(a).get("TYPE").toString();
						String COM_CODE =  "".equals(in_list.get(a).get("XZ")) || in_list.get(a).get("XZ") == null ? "0" : in_list.get(a).get("XZ").toString();
						String V99 =  "".equals(in_list.get(a).get("V99")) || in_list.get(a).get("V99") == null ? "0" : in_list.get(a).get("V99").toString();
						String V98 =  "".equals(in_list.get(a).get("V98")) || in_list.get(a).get("V98") == null ? "0" : in_list.get(a).get("V98").toString();
						String insql = "insert into PKC_1_2_7 (COM_NAME,Z_NU,V1,v3,v5,v7,v9,v11,v13,TYPE,COM_CODE,V99,V98) VALUES ("+
										"'"+COM_NAME+"','"+Z_HU+"','"+V1+"','"+V3+"','"+V5+"','"+V7+"','"+V9+"','"+V11+"','"+V13+"','"+TYPE+"','"+COM_CODE+"','"+V99+"','"+V98+"')";
						this.getBySqlMapper.insert(insql);
					}
			}
		}
		response.getWriter().write("*****************************************************");
	}
	/**
	 * 贫困户的主要致贫原因
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("PKC_1_2_8.do")
	public  void  PKC_1_2_8 ( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		for ( int i = 0 ; i < 4 ; i ++ ) {
			int v98 = 5-i;
			int ar = 6-i;
			int v10 = 2*v98;
			int xzqh = v10-1;
			for(int j = 0 ; j < 2 ; j++ ){
				String str = "";
				if ( j == 0 ){
					str = "0";
				}else {
					str = "3";
				}
				String sql ="select NUM,xz,xzqh, v1,v3,v5,v7,v9, '"+j+"' type,'2015' V99 ,'"+v98+"' v98 from ( ";
						sql += "SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NM09_AC01  where AAR100= '1' ";
						sql += " and AAR040='2015' and AAR010 ='"+str+"' and (AAR010='"+str+"' and NVL(AAC016,AAR040) = '2015') or AAR010<>'"+str+"' GROUP BY AAR00"+ar+"  )AA left join ( ";
						sql += " select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where xzqh is not null)w0 left join (";
						
						sql += "select AAR00"+ar+",count(*) v1 from (select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and(AAR010='"+str+"' and NVL(AAC016,AAR040) = '2015')";
						sql += "  or AAR010<>'"+str+"')a left join (select AAC001,AAC320 from NM09_AC31 ) b on a.AAC001=b.AAC001 where b.AAC320='01'  group BY AAR00"+ar+"";
						sql += ")w1 ON w0.xz=w1.AAR00"+ar+" left join (";
						sql += "select AAR00"+ar+",count(*) v3 from (select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and(AAR010='"+str+"' and NVL(AAC016,AAR040) = '2015')";
						sql += " or AAR010<>'"+str+"') a left join (select AAC001,AAC320 from NM09_AC31 ) b on a.AAC001=b.AAC001 where b.AAC320='02'  group BY AAR00"+ar+"";
						sql += ")w2 ON w0.xz=w2.AAR00"+ar+" LEFT JOIN(select AAR00"+ar+",count(*) v5 from (select AAC001,AAR00"+ar+" from ";
						sql += "NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and(AAR010='"+str+"' and NVL(AAC016,AAR040) = '2015') or AAR010<>'"+str+"'";
						sql += ") a left join (select AAC001,AAC320 from NM09_AC31 ) b on a.AAC001=b.AAC001 where b.AAC320='03'  group BY AAR00"+ar+"";
						sql += ")w3 ON w0.xz=w3.AAR00"+ar+" LEFT JOIN(select AAR00"+ar+",count(*) v7 from (select AAC001,AAR00"+ar+" from ";
						sql += "NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and(AAR010='"+str+"' and NVL(AAC016,AAR040) = '2015') or AAR010<>'"+str+"'";
						sql += ") a left join (select AAC001,AAC320 from NM09_AC31 ) b on a.AAC001=b.AAC001 where b.AAC320='04'  group BY AAR00"+ar+")w4 ON w0.xz=w4.AAR00"+ar+" LEFT JOIN (";
						sql += "select AAR00"+ar+",count(*) v9 from (select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and(AAR010='"+str+"' and NVL(AAC016,AAR040) = '2015')";
						sql += " or AAR010<>'"+str+"') a left join (select AAC001,AAC320 from NM09_AC31 ) b on a.AAC001=b.AAC001 where b.AAC320='99' group BY AAR00"+ar+")w5 ON w0.xz=w5.AAR00"+ar+"";
						
						System.out.println(sql);
					List<Map> in_list = this.getBySqlMapper.findRecords(sql);
					for ( int a = 0 ; a < in_list.size() ; a ++ ) {
						String COM_NAME =  in_list.get(a).get("XZQH").toString();
						String Z_HU = "".equals(in_list.get(a).get("NUM")) || in_list.get(a).get("NUM") == null ? "0" : in_list.get(a).get("NUM").toString();
						String V1 =  "".equals(in_list.get(a).get("V1")) || in_list.get(a).get("V1") == null ? "0" : in_list.get(a).get("V1").toString();
						String V3 =  "".equals(in_list.get(a).get("V3")) || in_list.get(a).get("V3") == null ? "0" : in_list.get(a).get("V3").toString();
						String V5 =  "".equals(in_list.get(a).get("V5")) || in_list.get(a).get("V5") == null ? "0" : in_list.get(a).get("V5").toString();
						String V7 =  "".equals(in_list.get(a).get("V7")) || in_list.get(a).get("V7") == null ? "0" : in_list.get(a).get("V7").toString();
						String V9 =  "".equals(in_list.get(a).get("V9")) || in_list.get(a).get("V9") == null ? "0" : in_list.get(a).get("V9").toString();
						String TYPE =  "".equals(in_list.get(a).get("TYPE")) || in_list.get(a).get("TYPE") == null ? "0" : in_list.get(a).get("TYPE").toString();
						String COM_CODE =  "".equals(in_list.get(a).get("XZ")) || in_list.get(a).get("XZ") == null ? "0" : in_list.get(a).get("XZ").toString();
						String V99 =  "".equals(in_list.get(a).get("V99")) || in_list.get(a).get("V99") == null ? "0" : in_list.get(a).get("V99").toString();
						String V98 =  "".equals(in_list.get(a).get("V98")) || in_list.get(a).get("V98") == null ? "0" : in_list.get(a).get("V98").toString();
						String insql = "insert into PKC_1_2_8 (COM_NAME,Z_HU,V1,v3,v5,v7,v9,TYPE,COM_CODE,V99,V98) VALUES ("+
										"'"+COM_NAME+"','"+Z_HU+"','"+V1+"','"+V3+"','"+V5+"','"+V7+"','"+V9+"','"+TYPE+"','"+COM_CODE+"','"+V99+"','"+V98+"')";
						this.getBySqlMapper.insert(insql);
					}
			}
		}
		response.getWriter().write("*****************************************************");
	}
	/**
	 * 贫困户的主要致贫原因
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("PKC_1_2_9.do")
	public  void  PKC_1_2_9 ( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		for ( int i = 0 ; i < 4 ; i ++ ) {
			int v98 = 5-i;
			int ar = 6-i;
			int v10 = 2*v98;
			int xzqh = v10-1;
			for(int j = 0 ; j < 2 ; j++ ){
				String str = "";
				if ( j == 0 ){
					str = "0";
				}else {
					str = "3";
				}
				String sql ="select NUM,xz,xzqh, v1,v2,v3,v4,v5, '"+j+"' type,'2015' V99 ,'"+v98+"' v98 from ( ";
						sql += "SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NM09_AC01  where AAR100= '1' ";
						sql += " and AAR040='2015' and AAR010 ='"+str+"' and (AAR010='"+str+"' and NVL(AAC016,AAR040) = '2015') or AAR010<>'"+str+"' GROUP BY AAR00"+ar+"  )AA left join ( ";
						sql += " select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where xzqh is not null)w0 left join (";
						
						sql += "select AAR00"+ar+",count(*) v2 from (select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and(AAR010='"+str+"' and NVL(AAC016,AAR040) = '2015')";
						sql += "  or AAR010<>'"+str+"')a left join (select AAC001,AAC316 from NM09_AC31 ) b on a.AAC001=b.AAC001 where b.AAC316='01'  group BY AAR00"+ar+"";
						sql += ")w1 ON w0.xz=w1.AAR00"+ar+" left join (";
						sql += "select AAR00"+ar+",count(*) v3 from (select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and(AAR010='"+str+"' and NVL(AAC016,AAR040) = '2015')";
						sql += " or AAR010<>'"+str+"') a left join (select AAC001,AAC316 from NM09_AC31 ) b on a.AAC001=b.AAC001 where b.AAC316='02'  group BY AAR00"+ar+"";
						sql += ")w2 ON w0.xz=w2.AAR00"+ar+" LEFT JOIN(select AAR00"+ar+",count(*) v4 from (select AAC001,AAR00"+ar+" from ";
						sql += "NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and(AAR010='"+str+"' and NVL(AAC016,AAR040) = '2015') or AAR010<>'"+str+"'";
						sql += ") a left join (select AAC001,AAC316 from NM09_AC31 ) b on a.AAC001=b.AAC001 where b.AAC316='03'  group BY AAR00"+ar+"";
						sql += ")w3 ON w0.xz=w3.AAR00"+ar+" LEFT JOIN(select AAR00"+ar+",count(*) v5 from (select AAC001,AAR00"+ar+" from ";
						sql += "NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and(AAR010='"+str+"' and NVL(AAC016,AAR040) = '2015') or AAR010<>'"+str+"'";
						sql += ") a left join (select AAC001,AAC316 from NM09_AC31 ) b on a.AAC001=b.AAC001 where b.AAC316='04'  group BY AAR00"+ar+")w4 ON w0.xz=w4.AAR00"+ar+" LEFT JOIN (";
						sql += "select AAR00"+ar+",sum(AAC315) v1 from (select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 ='"+str+"' and(AAR010='"+str+"' and NVL(AAC016,AAR040) = '2015')";
						sql += " or AAR010<>'"+str+"') a left join (select AAC001,AAC315 from NM09_AC31 ) b on a.AAC001=b.AAC001  group BY AAR00"+ar+")w5 ON w0.xz=w5.AAR00"+ar+"";
						
						System.out.println(sql);
					List<Map> in_list = this.getBySqlMapper.findRecords(sql);
					for ( int a = 0 ; a < in_list.size() ; a ++ ) {
						String COM_NAME =  in_list.get(a).get("XZQH").toString();
						String Z_HU = "".equals(in_list.get(a).get("NUM")) || in_list.get(a).get("NUM") == null ? "0" : in_list.get(a).get("NUM").toString();
						String V1 =  "".equals(in_list.get(a).get("V1")) || in_list.get(a).get("V1") == null ? "0" : in_list.get(a).get("V1").toString();
						String V2 =  "".equals(in_list.get(a).get("V2")) || in_list.get(a).get("V2") == null ? "0" : in_list.get(a).get("V2").toString();
						String V3 =  "".equals(in_list.get(a).get("V3")) || in_list.get(a).get("V3") == null ? "0" : in_list.get(a).get("V3").toString();
						String V4 =  "".equals(in_list.get(a).get("V4")) || in_list.get(a).get("V4") == null ? "0" : in_list.get(a).get("V4").toString();
						String V5 =  "".equals(in_list.get(a).get("V5")) || in_list.get(a).get("V5") == null ? "0" : in_list.get(a).get("V5").toString();
						String TYPE =  "".equals(in_list.get(a).get("TYPE")) || in_list.get(a).get("TYPE") == null ? "0" : in_list.get(a).get("TYPE").toString();
						String COM_CODE =  "".equals(in_list.get(a).get("XZ")) || in_list.get(a).get("XZ") == null ? "0" : in_list.get(a).get("XZ").toString();
						String V99 =  "".equals(in_list.get(a).get("V99")) || in_list.get(a).get("V99") == null ? "0" : in_list.get(a).get("V99").toString();
						String V98 =  "".equals(in_list.get(a).get("V98")) || in_list.get(a).get("V98") == null ? "0" : in_list.get(a).get("V98").toString();
						String insql = "insert into PKC_1_2_9 (COM_NAME,Z_HU,V1,v2,V3,v4,v5,TYPE,COM_CODE,V99,V98) VALUES ("+
										"'"+COM_NAME+"','"+Z_HU+"','"+V1+"','"+V2+"','"+V3+"','"+V4+"','"+V5+"','"+TYPE+"','"+COM_CODE+"','"+V99+"','"+V98+"')";
						this.getBySqlMapper.insert(insql);
					}
			}
		}
		response.getWriter().write("*****************************************************");
	}
}
