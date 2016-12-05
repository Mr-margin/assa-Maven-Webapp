package com.gistone.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)>2000";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q2 ON Q1.XZ=Q2.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as AA30 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)<2000 AND \"SUBSTR\"(AAB005, 0, 4)>1986";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q3 ON Q1.XZ=Q3.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as AA40 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)<1986 AND \"SUBSTR\"(AAB005, 0, 4)>1976";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q4 ON Q1.XZ=Q4.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as AA50 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)<1976 AND \"SUBSTR\"(AAB005, 0, 4)>1966";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q5 ON Q1.XZ=Q5.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as AA60 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)<1966 AND \"SUBSTR\"(AAB005, 0, 4)>1956";
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
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK01 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB008 = '01'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q2 ON Q1.XZ=Q2.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK02 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB008 = '02'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q3 ON Q1.XZ=Q3.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK03 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB008 = '03'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q4 ON Q1.XZ=Q4.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK04 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB008 = '04'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q5 ON Q1.XZ=Q5.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK05 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NM09_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB008 = '05'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q6 ON Q1.XZ=Q6.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK06 from (";
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
					String V7 =  "".equals(in_list.get(a).get("JK01")) || in_list.get(a).get("JK01") == null ? "" : in_list.get(a).get("JK01").toString();
					String V4 =  "".equals(in_list.get(a).get("JK02")) || in_list.get(a).get("JK02") == null ? "" : in_list.get(a).get("JK02").toString();
					String V5 =  "".equals(in_list.get(a).get("JK03")) || in_list.get(a).get("JK03") == null ? "" : in_list.get(a).get("JK03").toString();
					String V6 =  "".equals(in_list.get(a).get("JK04")) || in_list.get(a).get("JK04") == null ? "" : in_list.get(a).get("JK04").toString();
					String V8 =  "".equals(in_list.get(a).get("JK05")) || in_list.get(a).get("JK05") == null ? "" : in_list.get(a).get("JK05").toString();
					String V3 =  "".equals(in_list.get(a).get("JK06")) || in_list.get(a).get("JK06") == null ? "" : in_list.get(a).get("JK06").toString();
					String V10 =  "".equals(in_list.get(a).get("XZ")) || in_list.get(a).get("XZ") == null ? "" : in_list.get(a).get("XZ").toString();
					String insql = "insert into PKC_1_1_3 (PKID,V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,V98,V99) VALUES (PKC_1_1_3_A.nextval,'"+V1+"','"+V2+"','"+V3+"','"+V4+"','"+V5+"','"+V6+"','"+V7+"','"+V8+"','"+j+"','"+V10+"','"+v98+"','"+V99+"')";
//					this.getBySqlMapper.insert(insql);
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
//					this.getBySqlMapper.insert(insql);
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
//					this.getBySqlMapper.insert(insql);
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
//					this.getBySqlMapper.insert(insql);
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
					this.getBySqlMapper.insert(insql);
				}
			}
		}
	}
}
