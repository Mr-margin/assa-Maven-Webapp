package com.gistone.controller;

import java.io.IOException;
import java.text.DecimalFormat;
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
public class AExport{

	@Autowired
	private GetBySqlMapper getBySqlMapper;

	//定义查询查询年份
	String V99 = "2015";

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
				System.out.println(sql);
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
//					this.getBySqlMapper.insert(insql);
				}
				//System.out.println(sql);
			}
		}
	}
	/**
	 * 贫困人口年龄分组
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("PKC_1_1_1.do")
	public  void  PKC_1_1_1 ( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		for ( int i =0 ; i < 4 ; i ++ ) {
			for ( int j = 0 ; j < 2 ; j ++ ) {
				int v98 = 5-i;
				int ar = 6-i;
				int v10 = 2*v98;
				int xzqh = v10-1;
				String str = "";
				if ( j == 0 ){
					str = "0";
				}else {
					str = "3";
				}

				String sql ="select XZ,XZQH,NUM,AA16,AA30,AA40,AA50,AA60,AA70,round(AA70/NUM,4)*100 as AA70zb from(";
				sql +="select  NUM,AAR00"+ar+" xz,xzqh from (";
				sql +="SELECT COUNT(*) NUM,AAR00"+ar+" FROM (";
				sql +="SELECT * FROM (";
				sql +="select AAC001 a1,AAB001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')";
				sql +=")a LEFT JOIN (";
				sql +="select AAC001,AAR002,AAR003,AAR004,AAR005,AAR006 from NM09_AC01  where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=")b on a.a1=b.AAC001";
				sql +=") WHERE AAR00"+ar+" IS NOT NULL  GROUP BY AAR00"+ar;
				sql +=")AA left join (";
				sql +="select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+"  WHERE XZQH IS NOT NULL)Q1 left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as AA16 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)>=2000";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q2 ON Q1.XZ=Q2.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as AA30 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)<2000 AND \"SUBSTR\"(AAB005, 0, 4)>=1986";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q3 ON Q1.XZ=Q3.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as AA40 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)<1986 AND \"SUBSTR\"(AAB005, 0, 4)>=1976";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q4 ON Q1.XZ=Q4.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as AA50 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)<1976 AND \"SUBSTR\"(AAB005, 0, 4)>=1966";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q5 ON Q1.XZ=Q5.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as AA60 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)<1966 AND \"SUBSTR\"(AAB005, 0, 4)>=1956";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q6 ON Q1.XZ=Q6.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as AA70 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)<1956";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q7 ON Q1.XZ=Q7.AAR00"+ar+" ";

				List<Map> in_list = this.getBySqlMapper.findRecords(sql);
				System.out.println(sql);
				for( int a = 0 ; a < in_list.size(); a++ ) {
					String V1 = "".equals(in_list.get(a).get("XZQH")) || in_list.get(a).get("XZQH") == null ? "" : in_list.get(a).get("XZQH").toString();
					String V2 = "".equals(in_list.get(a).get("NUM")) || in_list.get(a).get("NUM") == null ? "" : in_list.get(a).get("NUM").toString();
					String V3 =  "".equals(in_list.get(a).get("AA16")) || in_list.get(a).get("AA16") == null ? "" : in_list.get(a).get("AA16").toString();
					String V4 =  "".equals(in_list.get(a).get("AA30")) || in_list.get(a).get("AA30") == null ? "" : in_list.get(a).get("AA30").toString();
					String V5 =  "".equals(in_list.get(a).get("AA40")) || in_list.get(a).get("AA40") == null ? "" : in_list.get(a).get("AA40").toString();
					String V6 =  "".equals(in_list.get(a).get("AA50")) || in_list.get(a).get("AA50") == null ? "" : in_list.get(a).get("AA50").toString();
					String V7 =  "".equals(in_list.get(a).get("AA60")) || in_list.get(a).get("AA60") == null ? "" : in_list.get(a).get("AA60").toString();
					String V8 =  "".equals(in_list.get(a).get("AA70")) || in_list.get(a).get("AA70") == null ? "" : in_list.get(a).get("AA70").toString();
					String V8_1 =  "".equals(in_list.get(a).get("AA70ZB")) || in_list.get(a).get("AA70ZB") == null ? "" : in_list.get(a).get("AA70ZB").toString();
					String V10 =  "".equals(in_list.get(a).get("XZ")) || in_list.get(a).get("XZ") == null ? "" : in_list.get(a).get("XZ").toString();
					String insql = "insert into PKC_1_1_1 (PKID,V1,V2,V3,V4,V5,V6,V7,V8,V8_1,V9,V10,V98,V99) VALUES (PKC_1_1_1_A.nextval,'"+V1+"','"+V2+"','"+V3+"','"+V4+"','"+V5+"','"+V6+"','"+V7+"','"+V8+"','"+V8_1+"','"+j+"','"+V10+"','"+v98+"','"+V99+"')";
//					this.getBySqlMapper.insert(insql);
				}
			}
		}
	}
	/**
	 * 身体健康状况
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("PKC_1_1_2.do")
	public void PKC_1_1_2( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		for ( int i =0 ; i < 4 ; i ++ ) {
			for ( int j = 0 ; j < 2 ; j ++ ) {
				int v98 = 5-i;
				int ar = 6-i;
				int v10 = 2*v98;
				int xzqh = v10-1;
				String str = "";
				if ( j == 0 ){
					str = "0";
				}else {
					str = "3";
				}

				String sql ="select XZ,XZQH,NUM,JK01,JK02,JK03,JK04 from(";
				sql +="select  NUM,AAR00"+ar+" xz,xzqh from (";
				sql +="SELECT COUNT(*) NUM,AAR00"+ar+" FROM (";
				sql +="SELECT * FROM (";
				sql +="select AAC001 a1,AAB001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')";
				sql +=")a LEFT JOIN (";
				sql +="select AAC001,AAR002,AAR003,AAR004,AAR005,AAR006 from NM09_AC01  where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=")b on a.a1=b.AAC001";
				sql +=") WHERE AAR00"+ar+" IS NOT NULL  GROUP BY AAR00"+ar;
				sql +=")AA left join (";
				sql +="select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+"  WHERE XZQH IS NOT NULL)Q1 left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK01 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB017 = '01'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q2 ON Q1.XZ=Q2.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK02 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB017 = '02'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q3 ON Q1.XZ=Q3.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK03 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB017 = '03'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q4 ON Q1.XZ=Q4.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK04 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB017 = '04'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q5 ON Q1.XZ=Q5.AAR00"+ar+" ";

				List<Map> in_list = this.getBySqlMapper.findRecords(sql);

				System.out.println(sql);

				for( int a = 0 ; a < in_list.size(); a++ ) {
					String V1 = "".equals(in_list.get(a).get("XZQH")) || in_list.get(a).get("XZQH") == null ? "" : in_list.get(a).get("XZQH").toString();
					String V2 = "".equals(in_list.get(a).get("NUM")) || in_list.get(a).get("NUM") == null ? "" : in_list.get(a).get("NUM").toString();
					String V3 =  "".equals(in_list.get(a).get("JK01")) || in_list.get(a).get("JK01") == null ? "" : in_list.get(a).get("JK01").toString();
					String V4 =  "".equals(in_list.get(a).get("JK02")) || in_list.get(a).get("JK02") == null ? "" : in_list.get(a).get("JK02").toString();
					String V5 =  "".equals(in_list.get(a).get("JK03")) || in_list.get(a).get("JK03") == null ? "" : in_list.get(a).get("JK03").toString();
					String V6 =  "".equals(in_list.get(a).get("JK04")) || in_list.get(a).get("JK04") == null ? "" : in_list.get(a).get("JK04").toString();
					String V10 =  "".equals(in_list.get(a).get("XZ")) || in_list.get(a).get("XZ") == null ? "" : in_list.get(a).get("XZ").toString();
					String insql = "insert into PKC_1_1_2 (PKID,V1,V2,V3,V4,V5,V6,V9,V10,V98,V99) VALUES (PKC_1_1_2_A.nextval,'"+V1+"','"+V2+"','"+V3+"','"+V4+"','"+V5+"','"+V6+"','"+j+"','"+V10+"','"+v98+"','"+V99+"')";
					//this.getBySqlMapper.insert(insql);
				}
			}
		}
	}
	/**
	 * 身体健康状况
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("PKC_1_1_3.do")
	public void PKC_1_1_3( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		for ( int i =0 ; i < 4 ; i ++ ) {
			for ( int j = 0 ; j < 2 ; j ++ ) {
				int v98 = 5-i;
				int ar = 6-i;
				int v10 = 2*v98;
				int xzqh = v10-1;
				String str = "";
				if ( j == 0 ){
					str = "0";
				}else {
					str = "3";
				}

				String sql ="select XZ,XZQH,NUM,JK01,JK02,JK03,JK04,JK05,JK06 from(";
				sql +="select  NUM,AAR00"+ar+" xz,xzqh from (";
				sql +="SELECT COUNT(*) NUM,AAR00"+ar+" FROM (";
				sql +="SELECT * FROM (";
				sql +="select AAC001 a1,AAB001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')";
				sql +=")a LEFT JOIN (";
				sql +="select AAC001,AAR002,AAR003,AAR004,AAR005,AAR006 from NM09_AC01  where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=")b on a.a1=b.AAC001";
				sql +=") WHERE AAR00"+ar+" IS NOT NULL  GROUP BY AAR00"+ar;
				sql +=")AA left join (";
				sql +="select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+"  WHERE XZQH IS NOT NULL)Q1 left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK01 from (";//文盲半文盲
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB008 = '01'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q2 ON Q1.XZ=Q2.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK02 from (";//小学
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB008 = '02'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q3 ON Q1.XZ=Q3.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK03 from (";//初中
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB008 = '03'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q4 ON Q1.XZ=Q4.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK04 from (";//高中
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB008 = '04'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q5 ON Q1.XZ=Q5.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK05 from (";//大专
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB008 = '05'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q6 ON Q1.XZ=Q6.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK06 from (";//学龄前
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB008 = '06'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q7 ON Q1.XZ=Q7.AAR00"+ar+" ";

				List<Map> in_list = this.getBySqlMapper.findRecords(sql);

				System.out.println(sql);

				for( int a = 0 ; a < in_list.size(); a++ ) {
					String V1 = "".equals(in_list.get(a).get("XZQH")) || in_list.get(a).get("XZQH") == null ? "" : in_list.get(a).get("XZQH").toString();
					String V2 = "".equals(in_list.get(a).get("NUM")) || in_list.get(a).get("NUM") == null ? "" : in_list.get(a).get("NUM").toString();
					String V4 =  "".equals(in_list.get(a).get("JK01")) || in_list.get(a).get("JK01") == null ? "" : in_list.get(a).get("JK01").toString();
					String V5 =  "".equals(in_list.get(a).get("JK02")) || in_list.get(a).get("JK02") == null ? "" : in_list.get(a).get("JK02").toString();
					String V6 =  "".equals(in_list.get(a).get("JK03")) || in_list.get(a).get("JK03") == null ? "" : in_list.get(a).get("JK03").toString();
					String V7 =  "".equals(in_list.get(a).get("JK04")) || in_list.get(a).get("JK04") == null ? "" : in_list.get(a).get("JK04").toString();
					String V8 =  "".equals(in_list.get(a).get("JK05")) || in_list.get(a).get("JK05") == null ? "" : in_list.get(a).get("JK05").toString();
					String V3 =  "".equals(in_list.get(a).get("JK06")) || in_list.get(a).get("JK06") == null ? "" : in_list.get(a).get("JK06").toString();
					String V10 =  "".equals(in_list.get(a).get("XZ")) || in_list.get(a).get("XZ") == null ? "" : in_list.get(a).get("XZ").toString();
					String insql = "insert into PKC_1_1_3 (PKID,V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,V98,V99) VALUES (PKC_1_1_3_A.nextval,'"+V1+"','"+V2+"','"+V3+"','"+V4+"','"+V5+"','"+V6+"','"+V7+"','"+V8+"','"+j+"','"+V10+"','"+v98+"','"+V99+"')";
					this.getBySqlMapper.insert(insql);
				}
			}
		}
	}
	/**
	 * 劳动能力
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("PKC_1_1_4.do")
	public void PKC_1_1_4( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		for ( int i =0 ; i < 4 ; i ++ ) {
			for ( int j = 0 ; j < 2 ; j ++ ) {
				int v98 = 5-i;
				int ar = 6-i;
				int v10 = 2*v98;
				int xzqh = v10-1;
				String str = "";
				if ( j == 0 ){
					str = "0";
				}else {
					str = "3";
				}

				String sql ="select XZ,XZQH,NUM,JK01,JK02,JK03,JK04 from(";
				sql +="select  NUM,AAR00"+ar+" xz,xzqh from (";
				sql +="SELECT COUNT(*) NUM,AAR00"+ar+" FROM (";
				sql +="SELECT * FROM (";
				sql +="select AAC001 a1,AAB001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')";
				sql +=")a LEFT JOIN (";
				sql +="select AAC001,AAR002,AAR003,AAR004,AAR005,AAR006 from NM09_AC01  where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=")b on a.a1=b.AAC001";
				sql +=") WHERE AAR00"+ar+" IS NOT NULL  GROUP BY AAR00"+ar;
				sql +=")AA left join (";
				sql +="select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+"  WHERE XZQH IS NOT NULL)Q1 left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK01 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB010 = '01'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q2 ON Q1.XZ=Q2.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK02 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB010 = '02'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q3 ON Q1.XZ=Q3.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK03 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB010 = '03'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q4 ON Q1.XZ=Q4.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK04 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB010 = '04'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q5 ON Q1.XZ=Q5.AAR00"+ar+" ";

				List<Map> in_list = this.getBySqlMapper.findRecords(sql);

				System.out.println(sql);

				for( int a = 0 ; a < in_list.size(); a++ ) {
					String V1 = "".equals(in_list.get(a).get("XZQH")) || in_list.get(a).get("XZQH") == null ? "" : in_list.get(a).get("XZQH").toString();
					String V2 = "".equals(in_list.get(a).get("NUM")) || in_list.get(a).get("NUM") == null ? "" : in_list.get(a).get("NUM").toString();
					String V3 =  "".equals(in_list.get(a).get("JK01")) || in_list.get(a).get("JK01") == null ? "" : in_list.get(a).get("JK01").toString();
					String V4 =  "".equals(in_list.get(a).get("JK02")) || in_list.get(a).get("JK02") == null ? "" : in_list.get(a).get("JK02").toString();
					String V5 =  "".equals(in_list.get(a).get("JK03")) || in_list.get(a).get("JK03") == null ? "" : in_list.get(a).get("JK03").toString();
					String V6 =  "".equals(in_list.get(a).get("JK04")) || in_list.get(a).get("JK04") == null ? "" : in_list.get(a).get("JK04").toString();
					String V10 =  "".equals(in_list.get(a).get("XZ")) || in_list.get(a).get("XZ") == null ? "" : in_list.get(a).get("XZ").toString();
					String insql = "insert into PKC_1_1_4 (PKID,V1,V2,V3,V4,V5,V6,V9,V10,V98,V99) VALUES (PKC_1_1_4_A.nextval,'"+V1+"','"+V2+"','"+V3+"','"+V4+"','"+V5+"','"+V6+"','"+j+"','"+V10+"','"+v98+"','"+V99+"')";
					//this.getBySqlMapper.insert(insql);
				}
			}
		}
	}
	/**
	 * 上年度务工情况
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("PKC_1_1_5.do")
	public void PKC_1_1_5( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		for ( int i =0 ; i < 4 ; i ++ ) {
			for ( int j = 0 ; j < 2 ; j ++ ) {
				int v98 = 5-i;
				int ar = 6-i;
				int v10 = 2*v98;
				int xzqh = v10-1;
				String str = "";
				if ( j == 0 ){
					str = "0";
				}else {
					str = "3";
				}

				String sql ="select XZ,XZQH,NUM,JK01,JK02,JK03,JK04,JK05 from(";
				sql +="select  NUM,AAR00"+ar+" xz,xzqh from (";
				sql +="SELECT COUNT(*) NUM,AAR00"+ar+" FROM (";
				sql +="SELECT * FROM (";
				sql +="select AAC001 a1,AAB001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')";
				sql +=")a LEFT JOIN (";
				sql +="select AAC001,AAR002,AAR003,AAR004,AAR005,AAR006 from NM09_AC01  where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=")b on a.a1=b.AAC001";
				sql +=") WHERE AAR00"+ar+" IS NOT NULL  GROUP BY AAR00"+ar;
				sql +=")AA left join (";
				sql +="select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+"  WHERE XZQH IS NOT NULL)Q1 left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK01 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB011 = '01'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q2 ON Q1.XZ=Q2.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK02 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB011 = '02'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q3 ON Q1.XZ=Q3.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK03 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB011 = '03'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q4 ON Q1.XZ=Q4.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK04 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB011 = '04'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q5 ON Q1.XZ=Q5.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK05 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB011 = '99'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q6 ON Q1.XZ=Q6.AAR00"+ar+" ";

				List<Map> in_list = this.getBySqlMapper.findRecords(sql);

				System.out.println(sql);

				for( int a = 0 ; a < in_list.size(); a++ ) {
					String V1 = "".equals(in_list.get(a).get("XZQH")) || in_list.get(a).get("XZQH") == null ? "" : in_list.get(a).get("XZQH").toString();
					String V2 = "".equals(in_list.get(a).get("NUM")) || in_list.get(a).get("NUM") == null ? "" : in_list.get(a).get("NUM").toString();
					String V3 =  "".equals(in_list.get(a).get("JK01")) || in_list.get(a).get("JK01") == null ? "" : in_list.get(a).get("JK01").toString();
					String V4 =  "".equals(in_list.get(a).get("JK02")) || in_list.get(a).get("JK02") == null ? "" : in_list.get(a).get("JK02").toString();
					String V5 =  "".equals(in_list.get(a).get("JK03")) || in_list.get(a).get("JK03") == null ? "" : in_list.get(a).get("JK03").toString();
					String V6 =  "".equals(in_list.get(a).get("JK04")) || in_list.get(a).get("JK04") == null ? "" : in_list.get(a).get("JK04").toString();
					String V7 =  "".equals(in_list.get(a).get("JK05")) || in_list.get(a).get("JK05") == null ? "" : in_list.get(a).get("JK05").toString();
					String V10 =  "".equals(in_list.get(a).get("XZ")) || in_list.get(a).get("XZ") == null ? "" : in_list.get(a).get("XZ").toString();
					String insql = "insert into PKC_1_1_5 (PKID,V1,V2,V3,V4,V5,V6,V7,V9,V10,V98,V99) VALUES (PKC_1_1_5_A.nextval,'"+V1+"','"+V2+"','"+V3+"','"+V4+"','"+V5+"','"+V6+"','"+V7+"','"+j+"','"+V10+"','"+v98+"','"+V99+"')";
					//this.getBySqlMapper.insert(insql);
				}
			}
		}
	}
	/**
	 * 上年度务工时间
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("PKC_1_1_6.do")
	public void PKC_1_1_6( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		for ( int i =0 ; i < 4 ; i ++ ) {
			for ( int j = 0 ; j < 2 ; j ++ ) {
				int v98 = 5-i;
				int ar = 6-i;
				int v10 = 2*v98;
				int xzqh = v10-1;
				String str = "";
				if ( j == 0 ){
					str = "0";
				}else {
					str = "3";
				}

				String sql ="select XZ,XZQH,NUM,JK01,JK02,JK03,JK04,JK05,JK06,JK07,JK08,JK09,JK10,JK11,JK12 from(";
				sql +="select  NUM,AAR00"+ar+" xz,xzqh from (";
				sql +="SELECT COUNT(*) NUM,AAR00"+ar+" FROM (";
				sql +="SELECT * FROM (";
				sql +="select AAC001 a1,AAB001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')";
				sql +=")a LEFT JOIN (";
				sql +="select AAC001,AAR002,AAR003,AAR004,AAR005,AAR006 from NM09_AC01  where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=")b on a.a1=b.AAC001";
				sql +=") WHERE AAR00"+ar+" IS NOT NULL  GROUP BY AAR00"+ar;
				sql +=")AA left join (";
				sql +="select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+"  WHERE XZQH IS NOT NULL)Q1 left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK01 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB012 = '1'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q2 ON Q1.XZ=Q2.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK02 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB012 = '2'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q3 ON Q1.XZ=Q3.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK03 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB012 = '3'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q4 ON Q1.XZ=Q4.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK04 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB012 = '4'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q5 ON Q1.XZ=Q5.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK05 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB012 = '5'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q6 ON Q1.XZ=Q6.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK06 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB012 = '6'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q7 ON Q1.XZ=Q7.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK07 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB012 = '7'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q8 ON Q1.XZ=Q8.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK08 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB012 = '8'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q9 ON Q1.XZ=Q9.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK09 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB012 = '9'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q10 ON Q1.XZ=Q10.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK10 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB012 = '10'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q11 ON Q1.XZ=Q11.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK11 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB012 = '11'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q12 ON Q1.XZ=Q12.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK12 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB012 = '12'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q13 ON Q1.XZ=Q13.AAR00"+ar+" ";

				List<Map> in_list = this.getBySqlMapper.findRecords(sql);

				System.out.println(sql);

				for( int a = 0 ; a < in_list.size(); a++ ) {
					String V1 = "".equals(in_list.get(a).get("XZQH")) || in_list.get(a).get("XZQH") == null ? "" : in_list.get(a).get("XZQH").toString();
					String V2 = "".equals(in_list.get(a).get("NUM")) || in_list.get(a).get("NUM") == null ? "" : in_list.get(a).get("NUM").toString();
					String V3 =  "".equals(in_list.get(a).get("JK01")) || in_list.get(a).get("JK01") == null ? "" : in_list.get(a).get("JK01").toString();
					String V4 =  "".equals(in_list.get(a).get("JK02")) || in_list.get(a).get("JK02") == null ? "" : in_list.get(a).get("JK02").toString();
					String V5 =  "".equals(in_list.get(a).get("JK03")) || in_list.get(a).get("JK03") == null ? "" : in_list.get(a).get("JK03").toString();
					String V6 =  "".equals(in_list.get(a).get("JK04")) || in_list.get(a).get("JK04") == null ? "" : in_list.get(a).get("JK04").toString();
					String V7 =  "".equals(in_list.get(a).get("JK05")) || in_list.get(a).get("JK05") == null ? "" : in_list.get(a).get("JK05").toString();
					String V8 =  "".equals(in_list.get(a).get("JK06")) || in_list.get(a).get("JK06") == null ? "" : in_list.get(a).get("JK06").toString();
					String V16 =  "".equals(in_list.get(a).get("JK07")) || in_list.get(a).get("JK07") == null ? "" : in_list.get(a).get("JK07").toString();
					String V11 =  "".equals(in_list.get(a).get("JK08")) || in_list.get(a).get("JK08") == null ? "" : in_list.get(a).get("JK08").toString();
					String V12 =  "".equals(in_list.get(a).get("JK09")) || in_list.get(a).get("JK09") == null ? "" : in_list.get(a).get("JK09").toString();
					String V13 =  "".equals(in_list.get(a).get("JK10")) || in_list.get(a).get("JK10") == null ? "" : in_list.get(a).get("JK10").toString();
					String V14 =  "".equals(in_list.get(a).get("JK11")) || in_list.get(a).get("JK11") == null ? "" : in_list.get(a).get("JK11").toString();
					String V15 =  "".equals(in_list.get(a).get("JK12")) || in_list.get(a).get("JK12") == null ? "" : in_list.get(a).get("JK12").toString();
					String V10 =  "".equals(in_list.get(a).get("XZ")) || in_list.get(a).get("XZ") == null ? "" : in_list.get(a).get("XZ").toString();
					String insql = "insert into PKC_1_1_6 (PKID,V1,V2,V3,V4,V5,V6,V7,V8,V16,V11,V12,V13,V14,V15,V9,V10,V98,V99) VALUES (PKC_1_1_6_A.nextval,'"+V1+"','"+V2+"','"+V3+"','"+V4+"','"+V5+"','"+V6+"','"+V7+"','"+V8+"','"+V16+"','"+V11+"','"+V12+"','"+V13+"','"+V14+"','"+V15+"','"+j+"','"+V10+"','"+v98+"','"+V99+"')";
					//this.getBySqlMapper.insert(insql);
				}
			}
		}
	}
	/**
	 * 在校生情况
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("PKC_1_1_7.do")
	public void PKC_1_1_7( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		for ( int i =0 ; i < 4 ; i ++ ) {
			for ( int j = 0 ; j < 2 ; j ++ ) {
				int v98 = 5-i;
				int ar = 6-i;
				int v10 = 2*v98;
				int xzqh = v10-1;
				String str = "";
				if ( j == 0 ){
					str = "0";
				}else {
					str = "3";
				}

				String sql ="select XZ,XZQH,NUM,JK01,JK02,JK03,JK04,JK05,JK06,JK07,JK08,JK09,JK10,JK11,JK12,JK13,JK14,JK15,JK16 from(";
				sql +="select  NUM,AAR00"+ar+" xz,xzqh from (";
				sql +="SELECT COUNT(*) NUM,AAR00"+ar+" FROM (";
				sql +="SELECT * FROM (";
				sql +="select AAC001 a1,AAB001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')";
				sql +=")a LEFT JOIN (";
				sql +="select AAC001,AAR002,AAR003,AAR004,AAR005,AAR006 from NM09_AC01  where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=")b on a.a1=b.AAC001";
				sql +=") WHERE AAR00"+ar+" IS NOT NULL  GROUP BY AAR00"+ar;
				sql +=")AA left join (";
				sql +="select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+"  WHERE XZQH IS NOT NULL)Q1 left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK01 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB009 = '01'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q2 ON Q1.XZ=Q2.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK02 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB009 = '02'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q3 ON Q1.XZ=Q3.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK03 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB009 = '03'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q4 ON Q1.XZ=Q4.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK04 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB009 = '04'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q5 ON Q1.XZ=Q5.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK05 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB009 = '05'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q6 ON Q1.XZ=Q6.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK06 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB009 = '06'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q7 ON Q1.XZ=Q7.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK07 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB009 = '07'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q8 ON Q1.XZ=Q8.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK08 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB009 = '08'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q9 ON Q1.XZ=Q9.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK09 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB009 = '09'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q10 ON Q1.XZ=Q10.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK10 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB009 = '10'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q11 ON Q1.XZ=Q11.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK11 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB009 = '11'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q12 ON Q1.XZ=Q12.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK12 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB009 = '12'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q13 ON Q1.XZ=Q13.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK13 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB009 = '13'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q14 ON Q1.XZ=Q14.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK14 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB009 = '14'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q15 ON Q1.XZ=Q15.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK15 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB009 = '15'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q16 ON Q1.XZ=Q16.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK16 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB009 = '16'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q17 ON Q1.XZ=Q17.AAR00"+ar+" ";

				List<Map> in_list = this.getBySqlMapper.findRecords(sql);

				System.out.println(sql);

				for( int a = 0 ; a < in_list.size(); a++ ) {
					String V1 = "".equals(in_list.get(a).get("XZQH")) || in_list.get(a).get("XZQH") == null ? "" : in_list.get(a).get("XZQH").toString();
					String V2 = "".equals(in_list.get(a).get("NUM")) || in_list.get(a).get("NUM") == null ? "" : in_list.get(a).get("NUM").toString();
					String V3 =  "".equals(in_list.get(a).get("JK01")) || in_list.get(a).get("JK01") == null ? "" : in_list.get(a).get("JK01").toString();
					String V4 =  "".equals(in_list.get(a).get("JK02")) || in_list.get(a).get("JK02") == null ? "" : in_list.get(a).get("JK02").toString();
					String V5 =  "".equals(in_list.get(a).get("JK03")) || in_list.get(a).get("JK03") == null ? "" : in_list.get(a).get("JK03").toString();
					String V6 =  "".equals(in_list.get(a).get("JK04")) || in_list.get(a).get("JK04") == null ? "" : in_list.get(a).get("JK04").toString();
					String V7 =  "".equals(in_list.get(a).get("JK05")) || in_list.get(a).get("JK05") == null ? "" : in_list.get(a).get("JK05").toString();
					String V8 =  "".equals(in_list.get(a).get("JK06")) || in_list.get(a).get("JK06") == null ? "" : in_list.get(a).get("JK06").toString();
					String V20 =  "".equals(in_list.get(a).get("JK07")) || in_list.get(a).get("JK07") == null ? "" : in_list.get(a).get("JK07").toString();
					String V11 =  "".equals(in_list.get(a).get("JK08")) || in_list.get(a).get("JK08") == null ? "" : in_list.get(a).get("JK08").toString();
					String V12 =  "".equals(in_list.get(a).get("JK09")) || in_list.get(a).get("JK09") == null ? "" : in_list.get(a).get("JK09").toString();
					String V13 =  "".equals(in_list.get(a).get("JK10")) || in_list.get(a).get("JK10") == null ? "" : in_list.get(a).get("JK10").toString();
					String V14 =  "".equals(in_list.get(a).get("JK11")) || in_list.get(a).get("JK11") == null ? "" : in_list.get(a).get("JK11").toString();
					String V15 =  "".equals(in_list.get(a).get("JK12")) || in_list.get(a).get("JK12") == null ? "" : in_list.get(a).get("JK12").toString();
					String V16 =  "".equals(in_list.get(a).get("JK13")) || in_list.get(a).get("JK13") == null ? "" : in_list.get(a).get("JK13").toString();
					String V17 =  "".equals(in_list.get(a).get("JK14")) || in_list.get(a).get("JK14") == null ? "" : in_list.get(a).get("JK14").toString();
					String V18 =  "".equals(in_list.get(a).get("JK15")) || in_list.get(a).get("JK15") == null ? "" : in_list.get(a).get("JK15").toString();
					String V19 =  "".equals(in_list.get(a).get("JK16")) || in_list.get(a).get("JK16") == null ? "" : in_list.get(a).get("JK16").toString();
					String V10 =  "".equals(in_list.get(a).get("XZ")) || in_list.get(a).get("XZ") == null ? "" : in_list.get(a).get("XZ").toString();
					String insql = "insert into PKC_1_1_7 (PKID,V1,V2,V3,V4,V5,V6,V7,V8,V20,V11,V12,V13,V14,V15,V16,V17,V18,V19,V9,V10,V98,V99) VALUES (PKC_1_1_7_A.nextval,'"+V1+"','"+V2+"','"+V3+"','"+V4+"','"+V5+"','"+V6+"','"+V7+"','"+V8+"','"+V20+"','"+V11+"','"+V12+"','"+V13+"','"+V14+"','"+V15+"','"+V16+"','"+V17+"','"+V18+"','"+V19+"','"+j+"','"+V10+"','"+v98+"','"+V99+"')";
					//this.getBySqlMapper.insert(insql);
				}
			}
		}
	}
	/**
	 * 贫困人口属性
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("PKC_1_1_8.do")
	public void PKC_1_1_8( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		for ( int i =0 ; i < 4 ; i ++ ) {
			for ( int j = 0 ; j < 2 ; j ++ ) {
				int v98 = 5-i;
				int ar = 6-i;
				int v10 = 2*v98;
				int xzqh = v10-1;
				String str = "";
				if ( j == 0 ){
					str = "0";
				}else {
					str = "3";
				}

				String sql ="select XZ,XZQH,NUM,JK01,JK02,JK03 from(";
				sql +="select  NUM,AAR00"+ar+" xz,xzqh from (";
				sql +="SELECT COUNT(*) NUM,AAR00"+ar+" FROM (";
				sql +="SELECT * FROM (";
				sql +="select AAC001 a1,AAB001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')";
				sql +=")a LEFT JOIN (";
				sql +="select AAC001,AAR002,AAR003,AAR004,AAR005,AAR006 from NM09_AC01  where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=")b on a.a1=b.AAC001";
				sql +=") WHERE AAR00"+ar+" IS NOT NULL  GROUP BY AAR00"+ar;
				sql +=")AA left join (";
				sql +="select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+"  WHERE XZQH IS NOT NULL)Q1 left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK01 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'  AND AAC006='01'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q2 ON Q1.XZ=Q2.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK02 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'  AND AAC006='04'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q3 ON Q1.XZ=Q3.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK03 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'  AND AAC006='06'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q4 ON Q1.XZ=Q4.AAR00"+ar+" ";


				List<Map> in_list = this.getBySqlMapper.findRecords(sql);

				System.out.println(sql);

				for( int a = 0 ; a < in_list.size(); a++ ) {
					String V1 = "".equals(in_list.get(a).get("XZQH")) || in_list.get(a).get("XZQH") == null ? "" : in_list.get(a).get("XZQH").toString();
					String V2 = "".equals(in_list.get(a).get("NUM")) || in_list.get(a).get("NUM") == null ? "" : in_list.get(a).get("NUM").toString();
					String V3 =  "".equals(in_list.get(a).get("JK01")) || in_list.get(a).get("JK01") == null ? "" : in_list.get(a).get("JK01").toString();
					String V4 =  "".equals(in_list.get(a).get("JK02")) || in_list.get(a).get("JK02") == null ? "" : in_list.get(a).get("JK02").toString();
					String V5 =  "".equals(in_list.get(a).get("JK03")) || in_list.get(a).get("JK03") == null ? "" : in_list.get(a).get("JK03").toString();
					String V10 =  "".equals(in_list.get(a).get("XZ")) || in_list.get(a).get("XZ") == null ? "" : in_list.get(a).get("XZ").toString();
					String insql = "insert into PKC_1_1_8 (PKID,V1,V2,V3,V4,V5,V9,V10,V98,V99) VALUES (PKC_1_1_8_A.nextval,'"+V1+"','"+V2+"','"+V3+"','"+V4+"','"+V5+"','"+j+"','"+V10+"','"+v98+"','"+V99+"')";
					this.getBySqlMapper.insert(insql);
				}
			}
		}
	}
	/**
	 * 适龄人口教育情况
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("PKC_1_1_9.do")
	public void PKC_1_1_9( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		for ( int i =0 ; i < 4 ; i ++ ) {
			for ( int j = 0 ; j < 2 ; j ++ ) {
				int v98 = 5-i;
				int ar = 6-i;
				int v10 = 2*v98;
				int xzqh = v10-1;
				String str = "";
				if ( j == 0 ){
					str = "0";
				}else {
					str = "3";
				}

				String sql ="select XZ,XZQH,NUM,JK01,JK02,JK03,JK04,JK05,JK06 from(";
				sql +="select  NUM,AAR00"+ar+" xz,xzqh from (";
				sql +="SELECT COUNT(*) NUM,AAR00"+ar+" FROM (";
				sql +="SELECT * FROM (";
				sql +="select AAC001 a1,AAB001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')";
				sql +=")a LEFT JOIN (";
				sql +="select AAC001,AAR002,AAR003,AAR004,AAR005,AAR006 from NM09_AC01  where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=")b on a.a1=b.AAC001";
				sql +=") WHERE AAR00"+ar+" IS NOT NULL  GROUP BY AAR00"+ar;
				sql +=")AA left join (";
				sql +="select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+"  WHERE XZQH IS NOT NULL)Q1 left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK01 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)<=2013  AND \"SUBSTR\"(AAB005, 0, 4)>2010";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q2 ON Q1.XZ=Q2.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK02 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)<=2010  AND \"SUBSTR\"(AAB005, 0, 4)>2001";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q3 ON Q1.XZ=Q3.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK03 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)<=2001  AND \"SUBSTR\"(AAB005, 0, 4)>1998";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q4 ON Q1.XZ=Q4.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK04 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)<=1998  AND \"SUBSTR\"(AAB005, 0, 4)>1994";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q5 ON Q1.XZ=Q5.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK05 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)<=1994  AND \"SUBSTR\"(AAB005, 0, 4)>1956";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q6 ON Q1.XZ=Q6.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK06 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)<=1956";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q7 ON Q1.XZ=Q7.AAR00"+ar+" ";

				List<Map> in_list = this.getBySqlMapper.findRecords(sql);

				System.out.println(sql);

				for( int a = 0 ; a < in_list.size(); a++ ) {
					String V1 = "".equals(in_list.get(a).get("XZQH")) || in_list.get(a).get("XZQH") == null ? "" : in_list.get(a).get("XZQH").toString();
					String V2 = "".equals(in_list.get(a).get("NUM")) || in_list.get(a).get("NUM") == null ? "" : in_list.get(a).get("NUM").toString();
					String V3 =  "".equals(in_list.get(a).get("JK01")) || in_list.get(a).get("JK01") == null ? "" : in_list.get(a).get("JK01").toString();
					String V4 =  "".equals(in_list.get(a).get("JK02")) || in_list.get(a).get("JK02") == null ? "" : in_list.get(a).get("JK02").toString();
					String V5 =  "".equals(in_list.get(a).get("JK03")) || in_list.get(a).get("JK03") == null ? "" : in_list.get(a).get("JK03").toString();
					String V6 =  "".equals(in_list.get(a).get("JK04")) || in_list.get(a).get("JK04") == null ? "" : in_list.get(a).get("JK04").toString();
					String V7 =  "".equals(in_list.get(a).get("JK05")) || in_list.get(a).get("JK05") == null ? "" : in_list.get(a).get("JK05").toString();
					String V8 =  "".equals(in_list.get(a).get("JK06")) || in_list.get(a).get("JK06") == null ? "" : in_list.get(a).get("JK06").toString();
					String V10 =  "".equals(in_list.get(a).get("XZ")) || in_list.get(a).get("XZ") == null ? "" : in_list.get(a).get("XZ").toString();
					String insql = "insert into PKC_1_1_9 (PKID,V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,V98,V99) VALUES (PKC_1_1_9_A.nextval,'"+V1+"','"+V2+"','"+V3+"','"+V4+"','"+V5+"','"+V6+"','"+V7+"','"+V8+"','"+j+"','"+V10+"','"+v98+"','"+V99+"')";
					this.getBySqlMapper.insert(insql);
				}
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
				String b_sql ="";
				if ( j == 0 ){
					b_sql = "AAR010='0'";
				}else {
					b_sql = "(AAR010='3' and NVL(AAC016,AAr040)='2015')";
				}
				String sql="select NUM,ren,xz,xzqh, v1,v5,v9, '"+j+"' type,'2015' V99 ,'"+v98+"' v98 from ( ";
						sql += "SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NM09_AC01  where AAR100= '1' ";
						sql += " and AAR040='2015' and "+b_sql+" GROUP BY AAR00"+ar+"  )AA left join ( ";
						sql += " select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where xzqh is not null)w0 left join (";
						
						sql += "select  NUM1 ren,AAR00"+ar+" renzx,xzqh xx from ( SELECT COUNT(*) NUM1,AAR00"+ar+" FROM";
						sql += " (SELECT * FROM (select AAC001 a1,AAB001 from NM09_AB01 where AAR040='2015' and AAB015 IN ('1','4'))a LEFT JOIN (";
						sql += " select AAR00"+ar+",AAC001 from NM09_AC01  where AAR100= '1' and AAR040='2015' and "+b_sql+")b";
						sql += " on a.a1=B.AAC001 ) GROUP BY AAR00"+ar+")AA left join ( select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+")ww on w0.xz=ww.renzx";
						sql += " left join (";
						
						sql += " select AAR00"+ar+", count(AAC006) v1 from  (select AAR00"+ar+",AAC006 from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+" ";
						sql += ") where AAC006='01' group by AAR00"+ar+") w1  ON w0.xz=W1.AAR00"+ar+"  left join ( select AAR00"+ar+", count(*) v5 from  (select AAR00"+ar+",AAC006";
						sql += "  from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+" ) where AAC006='04' group by AAR00"+ar+")";
						sql += "w2 on w0.xz=W2.AAR00"+ar+" left join (select AAR00"+ar+", count(*) v9 from   (select AAR00"+ar+",AAC006  from NM09_AC01 where AAR100= '1' and AAR040='2015'"+
								" and "+b_sql+") where AAC006='06' group by AAR00"+ar+")w3 on w0.xz=W3.AAR00"+ar+"";
						System.out.println(sql);
					List<Map> in_list = this.getBySqlMapper.findRecords(sql);
					for ( int a = 0 ; a < in_list.size() ; a ++ ) {
						String COM_NAME =  in_list.get(a).get("XZQH").toString();
						String Z_HU = "".equals(in_list.get(a).get("NUM")) || in_list.get(a).get("NUM") == null ? "" : in_list.get(a).get("NUM").toString();
						String V1 =  "".equals(in_list.get(a).get("V1")) || in_list.get(a).get("V1") == null ? "" : in_list.get(a).get("V1").toString();
						String V5 =  "".equals(in_list.get(a).get("V5")) || in_list.get(a).get("V5") == null ? "" : in_list.get(a).get("V5").toString();
						String V9 =  "".equals(in_list.get(a).get("V9")) || in_list.get(a).get("V9") == null ? "" : in_list.get(a).get("V9").toString();
						String TYPE =  "".equals(in_list.get(a).get("TYPE")) || in_list.get(a).get("TYPE") == null ? "" : in_list.get(a).get("TYPE").toString();
						String COM_CODE =  "".equals(in_list.get(a).get("XZ")) || in_list.get(a).get("XZ") == null ? "" : in_list.get(a).get("XZ").toString();
						String V99 =  "".equals(in_list.get(a).get("V99")) || in_list.get(a).get("V99") == null ? "" : in_list.get(a).get("V99").toString();
						String V98 =  "".equals(in_list.get(a).get("V98")) || in_list.get(a).get("V98") == null ? "" : in_list.get(a).get("V98").toString();
						String REN =  "".equals(in_list.get(a).get("REN")) || in_list.get(a).get("REN") == null ? "" : in_list.get(a).get("REN").toString();
						String insql = "insert into PKC_1_2_1 (COM_NAME,Z_HU,Z_REN,V1,V5,V9,TYPE,COM_CODE,V99,V98) VALUES ("+
										"'"+COM_NAME+"','"+Z_HU+"','"+REN+"','"+V1+"','"+V5+"','"+V9+"','"+TYPE+"','"+COM_CODE+"','"+V99+"','"+V98+"')";
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
	@RequestMapping("PKC_1_2_2.do")
	public  void  PKC_1_2_2 ( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		for ( int i = 0 ; i < 4 ; i ++ ) {
			int v98 = 5-i;
			int ar = 6-i;
			int v10 = 2*v98;
			int xzqh = v10-1;
			for(int j = 0 ; j < 2 ; j++ ){
				String b_sql ="";
				if ( j == 0 ){
					b_sql = "AAR010='0'";
				}else {
					b_sql = "(AAR010='3' and NVL(AAC016,AAr040)='2015')";
				}
				String sql ="select NUM,xz,xzqh,v1,v3,v5,v7,v9,v11,v13,v15,v17,v19,v21,'"+j+"' type,XZ com_code,'2015' V99 ,'"+v98+"' v98 from ( ";
						sql += "SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NM09_AC01  where AAR100= '1'";
						sql += " and AAR040='2015' and "+b_sql+" GROUP BY AAR00"+ar+"  )AA left join ( ";
						sql += " select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where xzqh is not null)w0 left join (";
						
						
						sql += " (select AAR00"+ar+", count(AAC007) v1 from  (select AAR00"+ar+",AAC007 from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += ") where AAC007='01'group by AAR00"+ar+"))w1 ON w0.xz=w1.AAR00"+ar+"  LEFT JOIN (";
						sql += " select AAR00"+ar+" v3_6, count(AAC007) v3 from (select AAR00"+ar+",AAC007 from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += " ) where AAC007='02' group by AAR00"+ar+")w2 ON w0.xz=w2.v3_6 LEFT JOIN(";
						
						sql += "select AAR00"+ar+", count(AAC007) v5 from (select AAR00"+ar+",AAC007 from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += " ) where AAC007='03' group by AAR00"+ar+" ) w3 on w0.xz=w3.AAR00"+ar+" LEFT JOIN  (";
						sql += "select AAR00"+ar+", count(AAC007) v7 from (select AAR00"+ar+",AAC007 from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += " ) where AAC007='04' group by AAR00"+ar+") w4 on w0.xz=w4.AAR00"+ar+" LEFT JOIN (select AAR00"+ar+", count(AAC007) v9 from ";
						
						sql += "(select AAR00"+ar+",AAC007 from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+" ";
						sql += ") where AAC007='05' group by AAR00"+ar+" ) w5 on w0.xz=w5.AAR00"+ar+" LEFT JOIN (select AAR00"+ar+", count(AAC007) v11 from ";
						sql += " (select AAR00"+ar+",AAC007 from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += " ) where AAC007='06' group by AAR00"+ar+") w6 on w0.xz=w6.AAR00"+ar+"  LEFT JOIN (";
						sql += " select AAR00"+ar+", count(AAC007) v13 from  (select AAR00"+ar+",AAC007 from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += ") where AAC007='07' group by AAR00"+ar+" ) w7 on w0.xz=w7.AAR00"+ar+" LEFT JOIN (";
						sql += "select AAR00"+ar+", count(AAC007) v15 from (select AAR00"+ar+",AAC007 from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += " ) where AAC007='08' group by AAR00"+ar+" ) w8 on w0.xz=w8.AAR00"+ar+" LEFT JOIN (";
						sql += "select AAR00"+ar+", count(AAC007) v17 from (select AAR00"+ar+",AAC007 from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += ") where AAC007='09' group by AAR00"+ar+" ) w9 on w0.xz=w9.AAR00"+ar+"  LEFT JOIN (";
						sql += "select AAR00"+ar+", count(AAC007) v19 from (select AAR00"+ar+",AAC007 from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += ") where AAC007='10' group by AAR00"+ar+" ) w10 on w0.xz=w10.AAR00"+ar+" LEFT JOIN ( ";
						sql += "select AAR00"+ar+", count(AAC007) v21 from (select AAR00"+ar+",AAC007 from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += " ) where AAC007='11' group by AAR00"+ar+" ) w11 on w0.xz=w11.AAR00"+ar+"";
						
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
	 * 贫困户的其他致贫原因
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
				String b_sql ="";
				if ( j == 0 ){
					b_sql = "AAR010='0'";
				}else {
					b_sql = "(AAR010='3' and NVL(AAC016,AAr040)='2015')";
				}
				String sql ="select NUM,xz,xzqh,v1,v3,v5,v7,v9,v11,v13,v15,v17,v19,v21,V23,V25, '"+j+"' type,XZ com_code,'2015' V99 ,'"+v98+"' v98 from ( ";
						sql += "SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NM09_AC01  where AAR100= '1'";
						sql += " and AAR040='2015' and "+b_sql+" GROUP BY AAR00"+ar+"  )AA left join ( ";
						sql += " select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where xzqh is not null)w0 left join (";
						
						
						sql += " (select AAR00"+ar+", count(AAC008) v1 from  (select AAR00"+ar+",AAC008 from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += " ) where SUBSTR(AAC008, 1, 2) = '01' or SUBSTR(AAC008, 4, 4) = '01' group by AAR00"+ar+"))w1 ON w0.xz=w1.AAR00"+ar+"  LEFT JOIN (";
						sql += " select AAR00"+ar+" v3_6, count(AAC008) v3 from (select AAR00"+ar+",AAC008 from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += ") where SUBSTR(AAC008, 1, 2) = '02' or SUBSTR(AAC008, 4, 4) = '02' group by AAR00"+ar+")w2 ON w0.xz=w2.v3_6 LEFT JOIN(";
						
						sql += "select AAR00"+ar+", count(AAC008) v5 from (select AAR00"+ar+",AAC008 from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += " ) where SUBSTR(AAC008, 1, 2) = '03' or SUBSTR(AAC008, 4, 4) = '03' group by AAR00"+ar+" ) w3 on w0.xz=w3.AAR00"+ar+" LEFT JOIN  (";
						sql += "select AAR00"+ar+", count(AAC008) v7 from (select AAR00"+ar+",AAC008 from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+" ";
						sql += " ) where SUBSTR(AAC008, 1, 2) = '04' or SUBSTR(AAC008, 4, 4) = '04' group by AAR00"+ar+") w4 on w0.xz=w4.AAR00"+ar+" LEFT JOIN (select AAR00"+ar+", count(AAC008) v9 from ";
						
						sql += "(select AAR00"+ar+",AAC008 from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+" ";
						sql += ") where SUBSTR(AAC008, 1, 2) = '05' or SUBSTR(AAC008, 4, 4) = '05' group by AAR00"+ar+" ) w5 on w0.xz=w5.AAR00"+ar+" LEFT JOIN (select AAR00"+ar+", count(AAC008) v11 from ";
						sql += " (select AAR00"+ar+",AAC008 from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+" ";
						sql += " ) where SUBSTR(AAC008, 1, 2) = '06' or SUBSTR(AAC008, 4, 4) = '06' group by AAR00"+ar+") w6 on w0.xz=w6.AAR00"+ar+"  LEFT JOIN (";
						sql += " select AAR00"+ar+", count(AAC008) v13 from  (select AAR00"+ar+",AAC008 from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += ") where SUBSTR(AAC008, 1, 2) = '07' or SUBSTR(AAC008, 4, 4) = '07' group by AAR00"+ar+" ) w7 on w0.xz=w7.AAR00"+ar+" LEFT JOIN (";
						sql += "select AAR00"+ar+", count(AAC008) v15 from (select AAR00"+ar+",AAC008 from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+" ";
						sql += ") where SUBSTR(AAC008, 1, 2) = '08' or SUBSTR(AAC008, 4, 4) = '08' group by AAR00"+ar+" ) w8 on w0.xz=w8.AAR00"+ar+" LEFT JOIN (";
						sql += "select AAR00"+ar+", count(AAC008) v17 from (select AAR00"+ar+",AAC008 from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+" ";
						sql += ") where SUBSTR(AAC008, 1, 2) = '09' or SUBSTR(AAC008, 4, 4) = '09' group by AAR00"+ar+" ) w9 on w0.xz=w9.AAR00"+ar+"  LEFT JOIN (";
						sql += "select AAR00"+ar+", count(AAC008) v19 from (select AAR00"+ar+",AAC008 from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += ") where SUBSTR(AAC008, 1, 2) = '10' or SUBSTR(AAC008, 4, 4) = '10' group by AAR00"+ar+" ) w10 on w0.xz=w10.AAR00"+ar+" LEFT JOIN ( ";
						sql += "select AAR00"+ar+", count(AAC008) v21 from (select AAR00"+ar+",AAC008 from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += ") where SUBSTR(AAC008, 1, 2) = '11' or SUBSTR(AAC008, 4, 4) = '11' group by AAR00"+ar+" ) w11 on w0.xz=w11.AAR00"+ar+" LEFT JOIN (";
						
						sql += "select AAR00"+ar+", count(AAC008) v23 from (select AAR00"+ar+",AAC008 from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += ") where SUBSTR(AAC008, 1, 2) = '12' or SUBSTR(AAC008, 4, 4) = '12' group by AAR00"+ar+" ) w12 on w0.xz=w12.AAR00"+ar+" LEFT JOIN (";
						sql += "select AAR00"+ar+", count(AAC008) v25 from (select AAR00"+ar+",AAC008 from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += ") where SUBSTR(AAC008, 1, 2) = '99' or SUBSTR(AAC008, 4, 4) = '99' group by AAR00"+ar+" ) w13 on w0.xz=w13.AAR00"+ar+" ";
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
	 * 贫困户——帮扶责任人落实情况
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
				String b_sql ="";
				if ( j == 0 ){
					b_sql = "AAR010='0'";
				}else {
					b_sql = "(AAR010='3' and NVL(AAC016,AAr040)='2015')";
				}
				String sql ="select NUM,xz,xzqh, v1, '"+j+"' type,XZ com_code,'2015' V99 ,'"+v98+"' v98 from ( ";
						sql += "SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NM09_AC01  where AAR100= '1' ";
						sql += " and AAR040='2015' and "+b_sql+" GROUP BY AAR00"+ar+"  )AA left join ( ";
						sql += " select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where xzqh is not null)w0 left join (";
						
						sql += "select count(AAC001) v1 ,AAR00"+ar+" from (select a.AAC001,AAR00"+ar+" from (";
						sql += "select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += ") a left join (select * from AC08) b on a.AAC001=b.AAC001 where ";
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
//						this.getBySqlMapper.insert(insql);
					}
			}
		}
	}
	/**
	 * 贫困户——生产生活
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("PKC_1_2_5.do")
	public  void  PKC_1_2_5( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		DecimalFormat df   = new DecimalFormat("######0.00");   
		for ( int i = 0 ; i < 4 ; i ++ ) {
			int v98 = 5-i;
			int ar = 6-i;
			int v10 = 2*v98;
			int xzqh = v10-1;
			for(int j = 0 ; j < 2 ; j++ ){
				String b_sql ="";
				if ( j == 0 ){
					b_sql = "AAR010='0'";
				}else {
					b_sql = "(AAR010='3' and NVL(AAC016,AAr040)='2015')";
				}
				String sql ="select NUM,xz,xzqh, v1,v3,v5,v11,v13,ren, '"+j+"' type,XZ com_code,'2015' V99 ,'"+v98+"' v98 from ( ";
						sql += "SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NM09_AC01  where AAR100= '1' ";
						sql += " and AAR040='2015' and "+b_sql+" GROUP BY AAR00"+ar+"  )AA left join ( ";
						sql += " select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where xzqh is not null)w0 left join (";
						
						sql += "select  NUM1 ren,AAR00"+ar+" renzx,xzqh xx from ( SELECT COUNT(*) NUM1,AAR00"+ar+" FROM";
						sql += " (SELECT * FROM (select AAC001 a1,AAB001 from NM09_AB01 where AAR040='2015' and AAB015 IN ('1','4'))a LEFT JOIN (";
						sql += " select AAR00"+ar+",AAC001 from NM09_AC01  where AAR100= '1' and AAR040='2015' and "+b_sql+")b";
						sql += " on a.a1=B.AAC001 ) GROUP BY AAR00"+ar+")AA left join ( select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+")ww on w0.xz=ww.renzx";
						sql += " left join (";
						
						sql += "select count(*) v1 ,AAR00"+ar+" from (select a.AAC001,AAR00"+ar+" from (select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+" ";
						sql += ") a left join (select * from NM09_ac31) b on a.AAC001=b.AAC001  where b.AAC311='1'";
						sql += " )t1 group BY AAR00"+ar+" )w1 ON w0.xz=w1.AAR00"+ar+" LEFT JOIN( select count(*) v3 ,AAR00"+ar+" from ( select a.AAC001,AAR00"+ar+" from (";
						sql += "select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+") a left join ";
						sql += " (select * from NM09_ac31 ) b on a.AAC001=b.AAC001 where b.AAC312='1')t1 group BY AAR00"+ar+" )w2 ON w0.xz=w2.AAR00"+ar+" LEFT JOIN(";
						sql += " select count(*) v5 ,AAR00"+ar+" from ( select a.AAC001,AAR00"+ar+" from (";
						sql += "select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += " ) a left join ";
						sql += "(select * from NM09_ac31) b on a.AAC001=b.AAC001  where b.AAC313='1'";
						sql += ")t1 group BY AAR00"+ar+" )w3 ON w0.xz=w3.AAR00"+ar+" LEFT JOIN (select count(*) v11 ,AAR00"+ar+" from (";
						sql += "select a.AAC001,AAR00"+ar+" from (select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += ") a left join ";
						sql += " (select * from NM09_ac31 ) b on a.AAC001=b.AAC001 where b.AAC319='1')t1 group BY AAR00"+ar+" )w5 ON w0.xz=w5.AAR00"+ar+" LEFT JOIN(";
						sql += " select sum(AAC317) v13,AAR00"+ar+" from ( select a.AAC001,AAR00"+ar+" ,AAC317 from (select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += ") a left join (select AAC317,AAC001 from NM09_ac31) b on a.AAC001=b.AAC001  ";
						sql += ")t1 group BY AAR00"+ar+" )w6 ON w0.xz=w6.AAR00"+ar+" ";
						System.out.println(sql);
					List<Map> in_list = this.getBySqlMapper.findRecords(sql);
					for ( int a = 0 ; a < in_list.size() ; a ++ ) {
						String COM_NAME =  in_list.get(a).get("XZQH").toString();
						String Z_HU = "".equals(in_list.get(a).get("NUM")) || in_list.get(a).get("NUM") == null ? "0" : in_list.get(a).get("NUM").toString();
						String V1 =  "".equals(in_list.get(a).get("V1")) || in_list.get(a).get("V1") == null ? "0" : in_list.get(a).get("V1").toString();
						double v2 = (Double.parseDouble(V1)/Double.parseDouble(Z_HU))*100;
						
						String V3 =  "".equals(in_list.get(a).get("V3")) || in_list.get(a).get("V3") == null ? "0" : in_list.get(a).get("V3").toString();
						double v4 = (Double.parseDouble(V3)/Double.parseDouble(Z_HU))*100;
						String V5 =  "".equals(in_list.get(a).get("V5")) || in_list.get(a).get("V5") == null ? "0" : in_list.get(a).get("V5").toString();
						double v6 = (Double.parseDouble(V5)/Double.parseDouble(Z_HU))*100;
						
						String V11 =  "".equals(in_list.get(a).get("V11")) || in_list.get(a).get("V11") == null ? "0" : in_list.get(a).get("V11").toString();
						int v12 = (Integer.parseInt(V11)/Integer.parseInt(Z_HU))*100;
						String V13 =  "".equals(in_list.get(a).get("V13")) || in_list.get(a).get("V13") == null ? "0" : in_list.get(a).get("V13").toString();
						String ren =  "".equals(in_list.get(a).get("REN")) || in_list.get(a).get("REN") == null ? "0" : in_list.get(a).get("REN").toString();
						double v13 = Double.parseDouble(V13)/Double.parseDouble(ren);
						
						String TYPE =  "".equals(in_list.get(a).get("TYPE")) || in_list.get(a).get("TYPE") == null ? "0" : in_list.get(a).get("TYPE").toString();
						String COM_CODE =  "".equals(in_list.get(a).get("XZ")) || in_list.get(a).get("XZ") == null ? "0" : in_list.get(a).get("XZ").toString();
						String V99 =  "".equals(in_list.get(a).get("V99")) || in_list.get(a).get("V99") == null ? "0" : in_list.get(a).get("V99").toString();
						String V98 =  "".equals(in_list.get(a).get("V98")) || in_list.get(a).get("V98") == null ? "0" : in_list.get(a).get("V98").toString();
						String insql = "insert into PKC_1_2_5 (COM_NAME,Z_HU,V1,V2,v3,v4,v5,v6,v11,v12,v13,TYPE,COM_CODE,V99,V98) VALUES ("+
										"'"+COM_NAME+"','"+Z_HU+"','"+V1+"','"+df.format(v2)+"','"+V3+"','"+df.format(v4)+"','"+V5+"','"+df.format(v6)+"','"+V11+"','"+v12+"','"+df.format(v13)+"','"+TYPE+"','"+COM_CODE+"','"+V99+"','"+V98+"')";
						this.getBySqlMapper.insert(insql);
					}
			}
		}
	}
	/**
	 * 贫困户——土地资源
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("PKC_1_2_6.do")
	public  void  PKC_1_2_6 ( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		DecimalFormat df   = new DecimalFormat("######0.00");   
		for ( int i = 0 ; i < 4 ; i ++ ) {
			int v98 = 5-i;
			int ar = 6-i;
			int v10 = 2*v98;
			int xzqh = v10-1;
			for(int j = 0 ; j < 2 ; j++ ){
				String b_sql ="";
				if ( j == 0 ){
					b_sql = "AAR010='0'";
				}else {
					b_sql = "(AAR010='3' and NVL(AAC016,AAr040)='2015')";
				}
				String sql ="select NUM,xz,xzqh, v1,v3,v5,v11,v13, '"+j+"' type,XZ com_code,'2015' V99 ,'"+v98+"' v98 from ( ";
						sql += "SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NM09_AC01  where AAR100= '1' ";
						sql += " and AAR040='2015' and "+b_sql+" GROUP BY AAR00"+ar+"  )AA left join ( ";
						sql += " select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where xzqh is not null)w0 left join (";
						
						sql += "select AAR00"+ar+" ,sum(AAC301) v1,sum(AAC302)v3,sum(AAC303)v5,sum(AAC304)v7,sum(AAC305)v9,sum(AAC306)v11,sum(AAC307)v13 from (";
						sql += " select AAC001,AAR00"+ar+"  from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += ") a left join (select * from NM09_AC30) b on a.AAC001=b.AAC001 group BY AAR00"+ar+" ";
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
										"'"+COM_NAME+"','"+Z_HU+"','"+V1+"','"+df.format(V2)+"','"+V3+"','"+df.format(V4)+"','"+V5+"','"+df.format(V6)+"','"+V7+"','"+df.format(V8)+"','"+V9+"','"+df.format(V10)+"','"+V11+"','"+df.format(V12)+"','"+V13+"','"+df.format(V14)+"','"+TYPE+"','"+COM_CODE+"','"+V99+"','"+V98+"')";
//						this.getBySqlMapper.insert(insql);
					}
			}
		}
	}
	/**
	 * 贫困户——人均收入分组情况
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
				String b_sql ="";
				if ( j == 0 ){
					b_sql = "AAR010='0'";
				}else {
					b_sql = "(AAR010='3' and NVL(AAC016,AAr040)='2015')";
				}
				String sql ="select NUM,xz,xzqh, v1,v3,v5,v7,v9,v11,v13, '"+j+"' type,'2015' V99 ,'"+v98+"' v98 from ( ";
						sql += "SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NM09_AC01  where AAR100= '1' ";
						sql += " and AAR040='2015' and "+b_sql+" GROUP BY AAR00"+ar+"  )AA left join ( ";
						sql += " select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where xzqh is not null)w0 left join (";
						
						sql += "select AAR00"+ar+",count(*) v1 from (select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += ") a left join (select AAC001,AAC082 from nm09_AC07) b on a.AAC001=b.AAC001 where b.AAC082<500 group BY AAR00"+ar+")w1 ON w0.xz=w1.AAR00"+ar+" left join(";
						sql += " select AAR00"+ar+",count(*) v3 from(select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+" ";
						sql += ") a left join (select AAC001,AAC082 from nm09_AC07) b on a.AAC001=b.AAC001  where b.AAC082 >= 500 AND b.AAC082 < 1000 group BY AAR00"+ar+" )w2 ON w0.xz=w2.AAR00"+ar+" left join(";
						sql += "select AAR00"+ar+",count(*) v5 from (select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += ") a left join (select AAC001,AAC082 from nm09_AC07) b on a.AAC001=b.AAC001 where b.AAC082 >= 1000 AND b.AAC082 < 1500 group BY AAR00"+ar+" )w3 ON w0.xz=w3.AAR00"+ar+" LEFT JOIN(";
						sql += "select AAR00"+ar+",count(*) v7 from (select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += ") a left join (select AAC001,AAC082 from nm09_AC07) b on a.AAC001=b.AAC001 where b.AAC082 >= 1500 AND b.AAC082 < 2000   group BY AAR00"+ar+"";
						sql += ")w4 ON w0.xz=w4.AAR00"+ar+" LEFT JOIN(select AAR00"+ar+",count(*) v9 from (select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += ") a left join (select AAC001,AAC082 from nm09_AC07) b on a.AAC001=b.AAC001 where b.AAC082 >= 2000 AND b.AAC082 < 2500 group BY AAR00"+ar+" )w5 ON w0.xz=w5.AAR00"+ar+" LEFT JOIN (";
						sql += "select AAR00"+ar+",count(*) v11 from (select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += ") a left join (select AAC001,AAC082 from nm09_AC07) b on a.AAC001=b.AAC001 where b.AAC082 >= 2500 AND b.AAC082 < 2800  group BY AAR00"+ar+")w6 ON w0.xz=w6.AAR00"+ar+" LEFT JOIN (";
						sql += "select AAR00"+ar+",count(*) v13 from (select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += ") a left join (select AAC001,AAC082 from nm09_AC07) b on a.AAC001=b.AAC001 where b.AAC082 >= 2800 group BY AAR00"+ar+")w7 ON w0.xz=w7.AAR00"+ar+"";
						
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
	 * 贫困户的主要燃料
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
				String b_sql ="";
				if ( j == 0 ){
					b_sql = "AAR010='0'";
				}else {
					b_sql = "(AAR010='3' and NVL(AAC016,AAr040)='2015')";
				}
				String sql ="select NUM,xz,xzqh, v1,v3,v5,v7,v9, '"+j+"' type,'2015' V99 ,'"+v98+"' v98 from ( ";
						sql += "SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NM09_AC01  where AAR100= '1' ";
						sql += " and AAR040='2015' and "+b_sql+" GROUP BY AAR00"+ar+"  )AA left join ( ";
						sql += " select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where xzqh is not null)w0 left join (";
						
						sql += "select AAR00"+ar+",count(*) v1 from (select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += " )a left join (select AAC001,AAC320 from NM09_AC31 ) b on a.AAC001=b.AAC001 where b.AAC320='01'  group BY AAR00"+ar+"";
						sql += ")w1 ON w0.xz=w1.AAR00"+ar+" left join (";
						sql += "select AAR00"+ar+",count(*) v3 from (select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += " ) a left join (select AAC001,AAC320 from NM09_AC31 ) b on a.AAC001=b.AAC001 where b.AAC320='02'  group BY AAR00"+ar+"";
						sql += ")w2 ON w0.xz=w2.AAR00"+ar+" LEFT JOIN(select AAR00"+ar+",count(*) v5 from (select AAC001,AAR00"+ar+" from ";
						sql += "NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += ") a left join (select AAC001,AAC320 from NM09_AC31 ) b on a.AAC001=b.AAC001 where b.AAC320='03'  group BY AAR00"+ar+"";
						sql += ")w3 ON w0.xz=w3.AAR00"+ar+" LEFT JOIN(select AAR00"+ar+",count(*) v7 from (select AAC001,AAR00"+ar+" from ";
						sql += "NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += ") a left join (select AAC001,AAC320 from NM09_AC31 ) b on a.AAC001=b.AAC001 where b.AAC320='04'  group BY AAR00"+ar+")w4 ON w0.xz=w4.AAR00"+ar+" LEFT JOIN (";
						sql += "select AAR00"+ar+",count(*) v9 from (select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += ") a left join (select AAC001,AAC320 from NM09_AC31 ) b on a.AAC001=b.AAC001 where b.AAC320='99' group BY AAR00"+ar+")w5 ON w0.xz=w5.AAR00"+ar+"";
						
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
	 * 贫困户的入户路的情况
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
				String b_sql ="";
				if ( j == 0 ){
					b_sql = "AAR010='0'";
				}else {
					b_sql = "(AAR010='3' and NVL(AAC016,AAr040)='2015')";
				}
				String sql ="select NUM,xz,xzqh, v1,v2,v3,v4,v5, '"+j+"' type,'2015' V99 ,'"+v98+"' v98 from ( ";
						sql += "SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NM09_AC01  where AAR100= '1' ";
						sql += " and AAR040='2015' and "+b_sql+" GROUP BY AAR00"+ar+"  )AA left join ( ";
						sql += " select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where xzqh is not null)w0 left join (";
						
						sql += "select AAR00"+ar+",count(*) v2 from (select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += ")a left join (select AAC001,AAC316 from NM09_AC31 ) b on a.AAC001=b.AAC001 where b.AAC316='01'  group BY AAR00"+ar+"";
						sql += ")w1 ON w0.xz=w1.AAR00"+ar+" left join (";
						sql += "select AAR00"+ar+",count(*) v3 from (select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += ") a left join (select AAC001,AAC316 from NM09_AC31 ) b on a.AAC001=b.AAC001 where b.AAC316='02'  group BY AAR00"+ar+"";
						sql += ")w2 ON w0.xz=w2.AAR00"+ar+" LEFT JOIN(select AAR00"+ar+",count(*) v4 from (select AAC001,AAR00"+ar+" from ";
						sql += "NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += ") a left join (select AAC001,AAC316 from NM09_AC31 ) b on a.AAC001=b.AAC001 where b.AAC316='03'  group BY AAR00"+ar+"";
						sql += ")w3 ON w0.xz=w3.AAR00"+ar+" LEFT JOIN(select AAR00"+ar+",count(*) v5 from (select AAC001,AAR00"+ar+" from ";
						sql += "NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += ") a left join (select AAC001,AAC316 from NM09_AC31 ) b on a.AAC001=b.AAC001 where b.AAC316='04'  group BY AAR00"+ar+")w4 ON w0.xz=w4.AAR00"+ar+" LEFT JOIN (";
						sql += "select AAR00"+ar+",sum(AAC315) v1 from (select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='2015' and "+b_sql+"";
						sql += ") a left join (select AAC001,AAC315 from NM09_AC31 ) b on a.AAC001=b.AAC001  group BY AAR00"+ar+")w5 ON w0.xz=w5.AAR00"+ar+"";
						
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
	/**
	 * 走访记录中修改时间
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("shijian.do")
	public void shijian(HttpServletRequest request,HttpServletResponse response ) throws IOException {
		String sql = "select pkid,v1 from  DA_HELP_VISIT where v1!='null' ORDER BY v1 desc";
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		for ( int i = 0 ; i < list.size() ; i ++) {
			String  str = list.get(i).get("V1").toString();
			if ( str .length() == 10 ) {//2016/02/12 2016.11.12
				if( str.contains("/") ){
					String sat = str.replaceAll("/", "-");
					String up = " update DA_HELP_VISIT set v1='"+sat+"' where pkid ='"+list.get(i).get("PKID")+"' ";
//					this.getBySqlMapper.update(up);
				}
				if( str.contains(".") ){
					String sat = str.replaceAll("\\.", "-");
					String up = " update DA_HELP_VISIT set v1='"+sat+"' where pkid ='"+list.get(i).get("PKID")+"' ";
//					this.getBySqlMapper.update(up);
				}
			}
			if ( str .length() == 8 ) { //20160212->2016-02-12
				StringBuffer aa = new StringBuffer (str);
				aa.insert(4,"-");
				StringBuffer bb = new StringBuffer (aa);
				bb.insert(7,"-");
				
				String up = " update DA_HELP_VISIT set v1='"+bb+"' where pkid ='"+list.get(i).get("PKID")+"' ";
//				this.getBySqlMapper.update(up);
			}
			if ( str .length() > 9 ) { //长度够长 给月份统一变两位数
				String sat = str.substring(5,7);
				if( sat.contains("-") ){
					StringBuffer aa = new StringBuffer (str);
					aa.insert(5,"0");
					String up = " update DA_HELP_VISIT set v1='"+aa+"' where pkid ='"+list.get(i).get("PKID")+"' ";
					this.getBySqlMapper.update(up);
//					System.out.println(str+"******"+aa+"*****"+list.get(i).get("PKID").toString()+"****>9");
					
				}else {
					if(str .length()==18){
						StringBuffer aa = new StringBuffer (str);
						aa.insert(8,"0");
						String up = " update DA_HELP_VISIT set v1='"+aa+"' where pkid ='"+list.get(i).get("PKID")+"' ";
						this.getBySqlMapper.update(up);
//						System.out.println(str+"******"+aa+"*****"+list.get(i).get("PKID").toString()+"****=18");
					}
				}
				
			}  else if ( str .length() == 6 ) {//只有年月的 把月变成两位数 2016-2---2016-02
				
				StringBuffer aa = new StringBuffer (str);
				aa.insert(5,"0");
				String up = " update DA_HELP_VISIT set v1='"+aa+"' where pkid ='"+list.get(i).get("PKID")+"' ";
//				this.getBySqlMapper.update(up);
//				System.out.println(str+"******"+aa+"*****"+list.get(i).get("PKID").toString()+"****=6");
				
			
			} else if ( str.length() == 9 ) {//九位长度年月日   日变成两位数  2016-02-2****2016-02-02
				StringBuffer aa = new StringBuffer (str);
				aa.insert(8,"0");
				String up = " update DA_HELP_VISIT set v1='"+aa+"' where pkid ='"+list.get(i).get("PKID")+"' ";
//				this.getBySqlMapper.update(up);
//				System.out.println(str+"******"+aa+"*****"+list.get(i).get("PKID").toString()+"****=9");
				
			} else if ( str.length() == 5 ) {//11-24---->2016-11-24
				StringBuffer aa = new StringBuffer (str);
				aa.insert(0,"2016-");//2016-2-16
				String up = " update DA_HELP_VISIT set v1='"+aa+"' where pkid ='"+list.get(i).get("PKID")+"' ";
//				this.getBySqlMapper.update(up);
//				System.out.println(str+"******"+aa+"*****"+list.get(i).get("PKID").toString()+"****=5");
			}
			
			else if (str.length() == 4 ){//2-16---->2016-02-16
				if("2016".equals(str)){
					
				}else{
					StringBuffer aa = new StringBuffer (str);
					aa.insert(0,"2016-");//2016-2-16
					String sat = aa.substring(5,7);
					
					if( sat.contains("-") ){
						StringBuffer bb = new StringBuffer (aa);
						bb.insert(5,"0");
						String up = " update DA_HELP_VISIT set v1='"+bb+"' where pkid ='"+list.get(i).get("PKID")+"' ";
//						this.getBySqlMapper.update(up);
//						System.out.println(str+"******"+bb+"*****"+list.get(i).get("PKID").toString()+"****>6");
						
					}else{
						//2016-02-7
						if ( aa.length()==9){
							StringBuffer bb = new StringBuffer (aa);
							bb.insert(8,"0");
							String up = " update DA_HELP_VISIT set v1='"+bb+"' where pkid ='"+list.get(i).get("PKID")+"' ";
//							this.getBySqlMapper.update(up);
//							System.out.println(str+"******"+bb+"*****"+list.get(i).get("PKID").toString()+"**4**=9");
						}
						
					}
					
				}
			}

		}
	}
}
