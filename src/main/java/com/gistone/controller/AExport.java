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
	String V99 = "2016";

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
				String sql ="select XZQH,NUM,AAB013,AAB014,'"+j+"' CC,XZ,'"+v98+"' SS,'2016' VV from( "+
						" select  NUM,AAR00"+ar+" xz,xzqh from ( SELECT COUNT(*) NUM,AAR00"+ar+" FROM (SELECT * FROM (select AAC001 a1,AAB001 from NEIMENG0117_AB01 where AAR040='2016' and AAB015 IN ('1','4')"+
						")a LEFT JOIN (select AAC001,AAR002,AAR003,AAR004,AAR005,AAR006 from NEIMENG0117_AC01  where AAR100= '1' and AAR040='2016'";
				if ( j == 0 ){
					str = "0";
				}else {
					str = "3";
				}
				sql += " and AAR010 ='"+str+"')b on a.a1=B.AAC001 ) GROUP BY AAR00"+ar+")AA left join (select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" )q1";
				sql += " left join ( select w1.AAR00"+ar+",AAB013,AAB014 from ("+
						" select t1.AAR00"+ar+",count(t1.AAB014) as AAB014 from (select AAR00"+ar+",b.AAB001,AAB014 from "+
						" (select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and AAR010 = '"+str+"') a "+
						"LEFT JOIN (select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='2016' and AAB015 IN ('1','4')) b on a.AAC001=b.AAC001 "+
						"LEFT JOIN (select AAB001,AAB014 from NEIMENG0117_AB02 where AAR040='2016') c on b.AAB001=c.AAB001 "+
						") t1 where t1.AAB001 is not null and AAB014='1' group by t1.AAR00"+ar+",t1.AAB014 ) w1 join ("+
						" select t1.AAR00"+ar+",count(t1.AAB013) as AAB013 from ( select AAR00"+ar+",b.AAB001,AAB013 from "+
						" (select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and AAR010 ='"+str+"') a "+
						"LEFT JOIN (select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='2016' and AAB015 IN ('1','4')) b on a.AAC001=b.AAC001 "+
						" LEFT JOIN (select AAB001,AAB013 from NEIMENG0117_AB02 where AAR040='2016') c on b.AAB001=c.AAB001  "+
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
					
					double V5_1 = 0.00;
					double V6_1 = 0.00;
					if(!V2.equals("")){
						if(!V5.equals("")){
							V5_1 = (Double.parseDouble(V5)/Double.parseDouble(V2))*100;
						}
						if(!V6.equals("")){
							V6_1 = (Double.parseDouble(V6)/Double.parseDouble(V2))*100;
						}
					}
					
					String insql = "insert into PKC_1_1_0 (V1,V2,V5,V6,v9,V10,V98,V99,V5_1,V6_1) VALUES ("+
							"'"+V1+"','"+V2+"','"+V5+"','"+V6+"','"+V9+"','"+V10+"','"+V98+"','"+V99+"','"+V5_1+"','"+V6_1+"')";
					this.getBySqlMapper.insert(insql);
				}
			}
		}
		System.out.println("PKC_1_1_0结束");
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
				sql +="select AAC001 a1,AAB001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')";
				sql +=")a LEFT JOIN (";
				sql +="select AAC001,AAR002,AAR003,AAR004,AAR005,AAR006 from NEIMENG0117_AC01  where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=")b on a.a1=b.AAC001";
				sql +=") WHERE AAR00"+ar+" IS NOT NULL  GROUP BY AAR00"+ar;
				sql +=")AA left join (";
				sql +="select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+"  WHERE XZQH IS NOT NULL)Q1 left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as AA16 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)>=2000";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q2 ON Q1.XZ=Q2.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as AA30 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)<2000 AND \"SUBSTR\"(AAB005, 0, 4)>=1986";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q3 ON Q1.XZ=Q3.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as AA40 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)<1986 AND \"SUBSTR\"(AAB005, 0, 4)>=1976";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q4 ON Q1.XZ=Q4.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as AA50 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)<1976 AND \"SUBSTR\"(AAB005, 0, 4)>=1966";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q5 ON Q1.XZ=Q5.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as AA60 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)<1966 AND \"SUBSTR\"(AAB005, 0, 4)>=1956";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q6 ON Q1.XZ=Q6.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as AA70 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)<1956";
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
					String insql = "insert into PKC_1_1_1 (V1,V2,V3,V4,V5,V6,V7,V8,V8_1,V9,V10,V98,V99) VALUES ('"+V1+"','"+V2+"','"+V3+"','"+V4+"','"+V5+"','"+V6+"','"+V7+"','"+V8+"','"+V8_1+"','"+j+"','"+V10+"','"+v98+"','"+V99+"')";
					this.getBySqlMapper.insert(insql);
				}
			}
		}
		System.out.println("PKC_1_1_1结束");
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
				sql +="select AAC001 a1,AAB001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')";
				sql +=")a LEFT JOIN (";
				sql +="select AAC001,AAR002,AAR003,AAR004,AAR005,AAR006 from NEIMENG0117_AC01  where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=")b on a.a1=b.AAC001";
				sql +=") WHERE AAR00"+ar+" IS NOT NULL  GROUP BY AAR00"+ar;
				sql +=")AA left join (";
				sql +="select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+"  WHERE XZQH IS NOT NULL)Q1 left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK01 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB017 = '01'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q2 ON Q1.XZ=Q2.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK02 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB017 = '02'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q3 ON Q1.XZ=Q3.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK03 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB017 = '03'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q4 ON Q1.XZ=Q4.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK04 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB017 = '04'";
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
					String insql = "insert into PKC_1_1_2 (V1,V2,V3,V4,V5,V6,V9,V10,V98,V99) VALUES ('"+V1+"','"+V2+"','"+V3+"','"+V4+"','"+V5+"','"+V6+"','"+j+"','"+V10+"','"+v98+"','"+V99+"')";
					this.getBySqlMapper.insert(insql);
				}
			}
		}
		System.out.println("PKC_1_1_2结束");
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
				sql +="select AAC001 a1,AAB001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')";
				sql +=")a LEFT JOIN (";
				sql +="select AAC001,AAR002,AAR003,AAR004,AAR005,AAR006 from NEIMENG0117_AC01  where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=")b on a.a1=b.AAC001";
				sql +=") WHERE AAR00"+ar+" IS NOT NULL  GROUP BY AAR00"+ar;
				sql +=")AA left join (";
				sql +="select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+"  WHERE XZQH IS NOT NULL)Q1 left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK01 from (";//文盲半文盲
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB008 = '01'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q2 ON Q1.XZ=Q2.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK02 from (";//小学
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB008 = '02'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q3 ON Q1.XZ=Q3.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK03 from (";//初中
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB008 = '03'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q4 ON Q1.XZ=Q4.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK04 from (";//高中
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB008 = '04'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q5 ON Q1.XZ=Q5.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK05 from (";//大专
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB008 = '05'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q6 ON Q1.XZ=Q6.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK06 from (";//学龄前
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB008 = '06'";
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
					
					double V3_1 = 0.00;
					double V4_1 = 0.00;
					double V5_1 = 0.00;
					double V6_1 = 0.00;
					double V7_1 = 0.00;
					double V8_1 = 0.00;
					if(!V2.equals("")){
						if(!V3.equals("")){
							V3_1 = (Double.parseDouble(V3)/Double.parseDouble(V2))*100;
						}
						if(!V4.equals("")){
							V4_1 = (Double.parseDouble(V4)/Double.parseDouble(V2))*100;
						}
						if(!V5.equals("")){
							V5_1 = (Double.parseDouble(V5)/Double.parseDouble(V2))*100;
						}
						if(!V6.equals("")){
							V6_1 = (Double.parseDouble(V6)/Double.parseDouble(V2))*100;
						}
						if(!V7.equals("")){
							V7_1 = (Double.parseDouble(V7)/Double.parseDouble(V2))*100;
						}
						if(!V8.equals("")){
							V8_1 = (Double.parseDouble(V8)/Double.parseDouble(V2))*100;
						}
					}
					
					String insql = "insert into PKC_1_1_3 (V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,V98,V99,V3_1,V4_1,V5_1,V6_1,V7_1,V8_1) "
							+ "VALUES ('"+V1+"','"+V2+"','"+V3+"','"+V4+"','"+V5+"','"+V6+"','"+V7+"','"+V8+"','"+j+"','"+V10+"','"+v98+"','"+V99+"','"+V3_1+"','"+V4_1+"','"+V5_1+"','"+V6_1+"','"+V7_1+"','"+V8_1+"')";
					this.getBySqlMapper.insert(insql);
				}
			}
		}
		System.out.println("PKC_1_1_3结束");
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
				sql +="select AAC001 a1,AAB001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')";
				sql +=")a LEFT JOIN (";
				sql +="select AAC001,AAR002,AAR003,AAR004,AAR005,AAR006 from NEIMENG0117_AC01  where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=")b on a.a1=b.AAC001";
				sql +=") WHERE AAR00"+ar+" IS NOT NULL  GROUP BY AAR00"+ar;
				sql +=")AA left join (";
				sql +="select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+"  WHERE XZQH IS NOT NULL)Q1 left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK01 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB010 = '01'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q2 ON Q1.XZ=Q2.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK02 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB010 = '02'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q3 ON Q1.XZ=Q3.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK03 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB010 = '03'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q4 ON Q1.XZ=Q4.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK04 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB010 = '04'";
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
					
					double V3_1 = 0.00;
					double V4_1 = 0.00;
					double V5_1 = 0.00;
					double V6_1 = 0.00;
					if(!V2.equals("")){
						if(!V3.equals("")){
							V3_1 = (Double.parseDouble(V3)/Double.parseDouble(V2))*100;
						}
						if(!V4.equals("")){
							V4_1 = (Double.parseDouble(V4)/Double.parseDouble(V2))*100;
						}
						if(!V5.equals("")){
							V5_1 = (Double.parseDouble(V5)/Double.parseDouble(V2))*100;
						}
						if(!V6.equals("")){
							V6_1 = (Double.parseDouble(V6)/Double.parseDouble(V2))*100;
						}
					}
					
					String insql = "insert into PKC_1_1_4 (V1,V2,V3,V4,V5,V6,V9,V10,V98,V99,V3_1,V4_1,V5_1,V6_1)"
							+ " VALUES ('"+V1+"','"+V2+"','"+V3+"','"+V4+"','"+V5+"','"+V6+"','"+j+"','"+V10+"','"+v98+"','"+V99+"','"+V3_1+"','"+V4_1+"','"+V5_1+"','"+V6_1+"')";
					this.getBySqlMapper.insert(insql);
				}
			}
		}
		System.out.println("PKC_1_1_4结束");
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
				sql +="select AAC001 a1,AAB001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')";
				sql +=")a LEFT JOIN (";
				sql +="select AAC001,AAR002,AAR003,AAR004,AAR005,AAR006 from NEIMENG0117_AC01  where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=")b on a.a1=b.AAC001";
				sql +=") WHERE AAR00"+ar+" IS NOT NULL  GROUP BY AAR00"+ar;
				sql +=")AA left join (";
				sql +="select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+"  WHERE XZQH IS NOT NULL)Q1 left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK01 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB011 = '01'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q2 ON Q1.XZ=Q2.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK02 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB011 = '02'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q3 ON Q1.XZ=Q3.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK03 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB011 = '03'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q4 ON Q1.XZ=Q4.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK04 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB011 = '04'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q5 ON Q1.XZ=Q5.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK05 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB011 = '99'";
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
					
					double V3_1 = 0.00;
					double V4_1 = 0.00;
					double V5_1 = 0.00;
					double V6_1 = 0.00;
					double V7_1 = 0.00;
					if(!V2.equals("")){
						if(!V3.equals("")){
							V3_1 = (Double.parseDouble(V3)/Double.parseDouble(V2))*100;
						}
						if(!V4.equals("")){
							V4_1 = (Double.parseDouble(V4)/Double.parseDouble(V2))*100;
						}
						if(!V5.equals("")){
							V5_1 = (Double.parseDouble(V5)/Double.parseDouble(V2))*100;
						}
						if(!V6.equals("")){
							V6_1 = (Double.parseDouble(V6)/Double.parseDouble(V2))*100;
						}
						if(!V7.equals("")){
							V7_1 = (Double.parseDouble(V7)/Double.parseDouble(V2))*100;
						}
					}
					
					String insql = "insert into PKC_1_1_5 (V1,V2,V3,V4,V5,V6,V7,V9,V10,V98,V99,V3_1,V4_1,V5_1,V6_1,V7_1)"
							+ " VALUES ('"+V1+"','"+V2+"','"+V3+"','"+V4+"','"+V5+"','"+V6+"','"+V7+"','"+j+"','"+V10+"','"+v98+"','"+V99+"','"+V3_1+"','"+V4_1+"','"+V5_1+"','"+V6_1+"','"+V7_1+"')";
					this.getBySqlMapper.insert(insql);
				}
			}
		}
		System.out.println("PKC_1_1_5结束");
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
				sql +="select AAC001 a1,AAB001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')";
				sql +=")a LEFT JOIN (";
				sql +="select AAC001,AAR002,AAR003,AAR004,AAR005,AAR006 from NEIMENG0117_AC01  where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=")b on a.a1=b.AAC001";
				sql +=") WHERE AAR00"+ar+" IS NOT NULL  GROUP BY AAR00"+ar;
				sql +=")AA left join (";
				sql +="select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+"  WHERE XZQH IS NOT NULL)Q1 left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK01 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB012 = '1'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q2 ON Q1.XZ=Q2.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK02 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB012 = '2'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q3 ON Q1.XZ=Q3.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK03 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB012 = '3'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q4 ON Q1.XZ=Q4.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK04 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB012 = '4'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q5 ON Q1.XZ=Q5.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK05 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB012 = '5'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q6 ON Q1.XZ=Q6.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK06 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB012 = '6'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q7 ON Q1.XZ=Q7.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK07 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB012 = '7'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q8 ON Q1.XZ=Q8.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK08 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB012 = '8'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q9 ON Q1.XZ=Q9.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK09 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB012 = '9'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q10 ON Q1.XZ=Q10.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK10 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB012 = '10'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q11 ON Q1.XZ=Q11.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK11 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB012 = '11'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q12 ON Q1.XZ=Q12.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK12 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB012 = '12'";
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
					
					double V3_1 = 0.00;
					double V4_1 = 0.00;
					double V5_1 = 0.00;
					double V6_1 = 0.00;
					double V7_1 = 0.00;
					double V8_1 = 0.00;
					double V11_1 = 0.00;
					double V12_1 = 0.00;
					double V13_1 = 0.00;
					double V14_1 = 0.00;
					double V15_1 = 0.00;
					double V16_1 = 0.00;
					if(!V2.equals("")){
						if(!V3.equals("")){
							V3_1 = (Double.parseDouble(V3)/Double.parseDouble(V2))*100;
						}
						if(!V4.equals("")){
							V4_1 = (Double.parseDouble(V4)/Double.parseDouble(V2))*100;
						}
						if(!V5.equals("")){
							V5_1 = (Double.parseDouble(V5)/Double.parseDouble(V2))*100;
						}
						if(!V6.equals("")){
							V6_1 = (Double.parseDouble(V6)/Double.parseDouble(V2))*100;
						}
						if(!V7.equals("")){
							V7_1 = (Double.parseDouble(V7)/Double.parseDouble(V2))*100;
						}
						if(!V8.equals("")){
							V8_1 = (Double.parseDouble(V8)/Double.parseDouble(V2))*100;
						}
						if(!V11.equals("")){
							V11_1 = (Double.parseDouble(V11)/Double.parseDouble(V2))*100;
						}
						if(!V12.equals("")){
							V12_1 = (Double.parseDouble(V12)/Double.parseDouble(V2))*100;
						}
						if(!V13.equals("")){
							V13_1 = (Double.parseDouble(V13)/Double.parseDouble(V2))*100;
						}
						if(!V14.equals("")){
							V14_1 = (Double.parseDouble(V14)/Double.parseDouble(V2))*100;
						}
						if(!V15.equals("")){
							V15_1 = (Double.parseDouble(V15)/Double.parseDouble(V2))*100;
						}
						if(!V16.equals("")){
							V16_1 = (Double.parseDouble(V16)/Double.parseDouble(V2))*100;
						}
					}
					
					String insql = "insert into PKC_1_1_6 (V1,V2,V3,V4,V5,V6,V7,V8,V16,V11,V12,V13,V14,V15,V9,V10,V98,V99,V3_1,V4_1,V5_1,V6_1,V7_1,V8_1,V11_1,V12_1,V13_1,V14_1,V15_1,V16_1) "
							+ "VALUES ('"+V1+"','"+V2+"','"+V3+"','"+V4+"','"+V5+"','"+V6+"','"+V7+"','"+V8+"','"+V16+"','"+V11+"','"+V12+"','"+V13+"','"+V14+"','"+V15+"','"+j+"','"+V10+"','"+v98+"','"+V99+"','"+V3_1+"','"+V4_1+"','"+V5_1+"','"+V6_1+"','"+V7_1+"','"+V8_1+"','"+V11_1+"','"+V12_1+"','"+V13_1+"','"+V14_1+"','"+V15_1+"','"+V16_1+"')";
					this.getBySqlMapper.insert(insql);
				}
			}
		}
		System.out.println("PKC_1_1_6结束");
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
				sql +="select AAC001 a1,AAB001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')";
				sql +=")a LEFT JOIN (";
				sql +="select AAC001,AAR002,AAR003,AAR004,AAR005,AAR006 from NEIMENG0117_AC01  where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=")b on a.a1=b.AAC001";
				sql +=") WHERE AAR00"+ar+" IS NOT NULL  GROUP BY AAR00"+ar;
				sql +=")AA left join (";
				sql +="select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+"  WHERE XZQH IS NOT NULL)Q1 left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK01 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB009 = '01'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q2 ON Q1.XZ=Q2.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK02 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB009 = '02'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q3 ON Q1.XZ=Q3.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK03 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND AAB009 = '03'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q4 ON Q1.XZ=Q4.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK04 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB009 = '04'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q5 ON Q1.XZ=Q5.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK05 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB009 = '05'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q6 ON Q1.XZ=Q6.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK06 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB009 = '06'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q7 ON Q1.XZ=Q7.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK07 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB009 = '07'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q8 ON Q1.XZ=Q8.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK08 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB009 = '08'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q9 ON Q1.XZ=Q9.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK09 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB009 = '09'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q10 ON Q1.XZ=Q10.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK10 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB009 = '10'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q11 ON Q1.XZ=Q11.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK11 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB009 = '11'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q12 ON Q1.XZ=Q12.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK12 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB009 = '12'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q13 ON Q1.XZ=Q13.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK13 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB009 = '13'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q14 ON Q1.XZ=Q14.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK14 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB009 = '14'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q15 ON Q1.XZ=Q15.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK15 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB009 = '15'";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q16 ON Q1.XZ=Q16.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK16 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')  AND AAB009 = '16'";
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
					
					double V3_1 = 0.00;
					double V4_1 = 0.00;
					double V5_1 = 0.00;
					double V6_1 = 0.00;
					double V7_1 = 0.00;
					double V8_1 = 0.00;
					double V11_1 = 0.00;
					double V12_1 = 0.00;
					double V13_1 = 0.00;
					double V14_1 = 0.00;
					double V15_1 = 0.00;
					double V16_1 = 0.00;
					double V17_1 = 0.00;
					double V18_1 = 0.00;
					double V19_1 = 0.00;
					double V20_1 = 0.00;
					if(!V2.equals("")){
						if(!V3.equals("")){
							V3_1 = (Double.parseDouble(V3)/Double.parseDouble(V2))*100;
						}
						if(!V4.equals("")){
							V4_1 = (Double.parseDouble(V4)/Double.parseDouble(V2))*100;
						}
						if(!V5.equals("")){
							V5_1 = (Double.parseDouble(V5)/Double.parseDouble(V2))*100;
						}
						if(!V6.equals("")){
							V6_1 = (Double.parseDouble(V6)/Double.parseDouble(V2))*100;
						}
						if(!V7.equals("")){
							V7_1 = (Double.parseDouble(V7)/Double.parseDouble(V2))*100;
						}
						if(!V8.equals("")){
							V8_1 = (Double.parseDouble(V8)/Double.parseDouble(V2))*100;
						}
						if(!V11.equals("")){
							V11_1 = (Double.parseDouble(V11)/Double.parseDouble(V2))*100;
						}
						if(!V12.equals("")){
							V12_1 = (Double.parseDouble(V12)/Double.parseDouble(V2))*100;
						}
						if(!V13.equals("")){
							V13_1 = (Double.parseDouble(V13)/Double.parseDouble(V2))*100;
						}
						if(!V14.equals("")){
							V14_1 = (Double.parseDouble(V14)/Double.parseDouble(V2))*100;
						}
						if(!V15.equals("")){
							V15_1 = (Double.parseDouble(V15)/Double.parseDouble(V2))*100;
						}
						if(!V16.equals("")){
							V16_1 = (Double.parseDouble(V16)/Double.parseDouble(V2))*100;
						}
						if(!V17.equals("")){
							V17_1 = (Double.parseDouble(V17)/Double.parseDouble(V2))*100;
						}
						if(!V18.equals("")){
							V18_1 = (Double.parseDouble(V18)/Double.parseDouble(V2))*100;
						}
						if(!V19.equals("")){
							V19_1 = (Double.parseDouble(V19)/Double.parseDouble(V2))*100;
						}
						if(!V20.equals("")){
							V20_1 = (Double.parseDouble(V20)/Double.parseDouble(V2))*100;
						}
					}
					
					String insql = "insert into PKC_1_1_7 (V1,V2,V3,V4,V5,V6,V7,V8,V20,V11,V12,V13,V14,V15,V16,V17,V18,V19,V9,V10,V98,V99,V3_1,V4_1,V5_1,V6_1,V7_1,V8_1,V11_1,V12_1,V13_1,V14_1,V15_1,V16_1,V17_1,V18_1,V19_1,V20_1) "
							+ "VALUES ('"+V1+"','"+V2+"','"+V3+"','"+V4+"','"+V5+"','"+V6+"','"+V7+"','"+V8+"','"+V20+"','"+V11+"','"+V12+"','"+V13+"','"+V14+"','"+V15+"','"+V16+"','"+V17+"','"+V18+"','"+V19+"','"+j+"','"+V10+"','"+v98+"','"+V99+"','"+V3_1+"','"+V4_1+"','"+V5_1+"','"+V6_1+"','"+V7_1+"','"+V8_1+"','"+V11_1+"','"+V12_1+"','"+V13_1+"','"+V14_1+"','"+V15_1+"','"+V16_1+"','"+V17_1+"','"+V18_1+"','"+V19_1+"','"+V20_1+"')";
					this.getBySqlMapper.insert(insql);
				}
			}
		}
		System.out.println("PKC_1_1_7结束");
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
				sql +="select AAC001 a1,AAB001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')";
				sql +=")a LEFT JOIN (";
				sql +="select AAC001,AAR002,AAR003,AAR004,AAR005,AAR006 from NEIMENG0117_AC01  where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=")b on a.a1=b.AAC001";
				sql +=") WHERE AAR00"+ar+" IS NOT NULL  GROUP BY AAR00"+ar;
				sql +=")AA left join (";
				sql +="select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+"  WHERE XZQH IS NOT NULL)Q1 left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK01 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'  AND AAC006='01'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q2 ON Q1.XZ=Q2.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK02 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'  AND AAC006='04'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q3 ON Q1.XZ=Q3.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK03 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'  AND AAC006='06'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')";
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
					
					double V3_1 = 0.00;
					double V4_1 = 0.00;
					double V5_1 = 0.00;
					if(!V2.equals("")){
						if(!V3.equals("")){
							V3_1 = (Double.parseDouble(V3)/Double.parseDouble(V2))*100;
						}
						if(!V4.equals("")){
							V4_1 = (Double.parseDouble(V4)/Double.parseDouble(V2))*100;
						}
						if(!V5.equals("")){
							V5_1 = (Double.parseDouble(V5)/Double.parseDouble(V2))*100;
						}
					}
					
					String insql = "insert into PKC_1_1_8 (V1,V2,V3,V4,V5,V9,V10,V98,V99,V3_1,V4_1,V5_1) "
							+ "VALUES ('"+V1+"','"+V2+"','"+V3+"','"+V4+"','"+V5+"','"+j+"','"+V10+"','"+v98+"','"+V99+"','"+V3_1+"','"+V4_1+"','"+V5_1+"')";
					this.getBySqlMapper.insert(insql);
				}
			}
		}
		System.out.println("PKC_1_1_8结束");
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
				sql +="select AAC001 a1,AAB001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4')";
				sql +=")a LEFT JOIN (";
				sql +="select AAC001,AAR002,AAR003,AAR004,AAR005,AAR006 from NEIMENG0117_AC01  where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=")b on a.a1=b.AAC001";
				sql +=") WHERE AAR00"+ar+" IS NOT NULL  GROUP BY AAR00"+ar;
				sql +=")AA left join (";
				sql +="select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+"  WHERE XZQH IS NOT NULL)Q1 left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK01 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)<=2013  AND \"SUBSTR\"(AAB005, 0, 4)>2010";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q2 ON Q1.XZ=Q2.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK02 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)<=2010  AND \"SUBSTR\"(AAB005, 0, 4)>2001";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q3 ON Q1.XZ=Q3.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK03 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)<=2001  AND \"SUBSTR\"(AAB005, 0, 4)>1998";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q4 ON Q1.XZ=Q4.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK04 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)<=1998  AND \"SUBSTR\"(AAB005, 0, 4)>1994";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q5 ON Q1.XZ=Q5.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK05 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)<=1994  AND \"SUBSTR\"(AAB005, 0, 4)>1956";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR00"+ar+"";
				sql +=")Q6 ON Q1.XZ=Q6.AAR00"+ar+" ";
				sql +="left join (";
				sql +="select t1.AAR00"+ar+",count(t1.AAR00"+ar+") as JK06 from (";
				sql +="select AAR00"+ar+",b.AAB001 from(";
				sql +="select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='"+V99+"' and AAR010='"+str+"'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NEIMENG0117_AB01 where AAR040='"+V99+"' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)<=1956";
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
					
					double V3_1 = 0.00;
					double V4_1 = 0.00;
					double V5_1 = 0.00;
					double V6_1 = 0.00;
					double V7_1 = 0.00;
					double V8_1 = 0.00;
					if(!V2.equals("")){
						if(!V3.equals("")){
							V3_1 = (Double.parseDouble(V3)/Double.parseDouble(V2))*100;
						}
						if(!V4.equals("")){
							V4_1 = (Double.parseDouble(V4)/Double.parseDouble(V2))*100;
						}
						if(!V5.equals("")){
							V5_1 = (Double.parseDouble(V5)/Double.parseDouble(V2))*100;
						}
						if(!V6.equals("")){
							V6_1 = (Double.parseDouble(V6)/Double.parseDouble(V2))*100;
						}
						if(!V7.equals("")){
							V7_1 = (Double.parseDouble(V7)/Double.parseDouble(V2))*100;
						}
						if(!V8.equals("")){
							V8_1 = (Double.parseDouble(V8)/Double.parseDouble(V2))*100;
						}
					}
					String insql = "insert into PKC_1_1_9 (V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,V98,V99,V3_1,V4_1,V5_1,V6_1,V7_1,V8_1) "
							+ "VALUES ('"+V1+"','"+V2+"','"+V3+"','"+V4+"','"+V5+"','"+V6+"','"+V7+"','"+V8+"','"+j+"','"+V10+"','"+v98+"','"+V99+"','"+V3_1+"','"+V4_1+"','"+V5_1+"','"+V6_1+"','"+V7_1+"','"+V8_1+"')";
					this.getBySqlMapper.insert(insql);
				}
			}
		}
		System.out.println("PKC_1_1_9结束");
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
					b_sql = "(AAR010='3' and NVL(AAC016,AAr040)='2016')";
				}
				String sql="select NUM,ren,xz,xzqh, v1,v5,v9, '"+j+"' type,'2016' V99 ,'"+v98+"' v98 from ( ";
						sql += "SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NEIMENG0117_AC01  where AAR100= '1' ";
						sql += " and AAR040='2016' and "+b_sql+" GROUP BY AAR00"+ar+"  )AA left join ( ";
						sql += " select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where xzqh is not null)w0 left join (";
						
						sql += "select  NUM1 ren,AAR00"+ar+" renzx,xzqh xx from ( SELECT COUNT(*) NUM1,AAR00"+ar+" FROM";
						sql += " (SELECT * FROM (select AAC001 a1,AAB001 from NEIMENG0117_AB01 where AAR040='2016' and AAB015 IN ('1','4'))a LEFT JOIN (";
						sql += " select AAR00"+ar+",AAC001 from NEIMENG0117_AC01  where AAR100= '1' and AAR040='2016' and "+b_sql+")b";
						sql += " on a.a1=B.AAC001 ) GROUP BY AAR00"+ar+")AA left join ( select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+")ww on w0.xz=ww.renzx";
						sql += " left join (";
						
						sql += " select AAR00"+ar+", count(AAC006) v1 from  (select AAR00"+ar+",AAC006 from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+" ";
						sql += ") where AAC006='01' group by AAR00"+ar+") w1  ON w0.xz=W1.AAR00"+ar+"  left join ( select AAR00"+ar+", count(*) v5 from  (select AAR00"+ar+",AAC006";
						sql += "  from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+" ) where AAC006='04' group by AAR00"+ar+")";
						sql += "w2 on w0.xz=W2.AAR00"+ar+" left join (select AAR00"+ar+", count(*) v9 from   (select AAR00"+ar+",AAC006  from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016'"+
								" and "+b_sql+") where AAC006='06' group by AAR00"+ar+")w3 on w0.xz=W3.AAR00"+ar+"";
//						System.out.println(sql);
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
					b_sql = "(AAR010='3' and NVL(AAC016,AAr040)='2016')";
				}
				String sql ="select NUM,xz,xzqh,v1,v3,v5,v7,v9,v11,v13,v15,v17,v19,v21,'"+j+"' type,XZ com_code,'2016' V99 ,'"+v98+"' v98 from ( ";
						sql += "SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NEIMENG0117_AC01  where AAR100= '1'";
						sql += " and AAR040='2016' and "+b_sql+" GROUP BY AAR00"+ar+"  )AA left join ( ";
						sql += " select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where xzqh is not null)w0 left join (";
						
						
						sql += " (select AAR00"+ar+", count(AAC007) v1 from  (select AAR00"+ar+",AAC007 from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += ") where AAC007='01'group by AAR00"+ar+"))w1 ON w0.xz=w1.AAR00"+ar+"  LEFT JOIN (";
						sql += " select AAR00"+ar+" v3_6, count(AAC007) v3 from (select AAR00"+ar+",AAC007 from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += " ) where AAC007='02' group by AAR00"+ar+")w2 ON w0.xz=w2.v3_6 LEFT JOIN(";
						
						sql += "select AAR00"+ar+", count(AAC007) v5 from (select AAR00"+ar+",AAC007 from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += " ) where AAC007='03' group by AAR00"+ar+" ) w3 on w0.xz=w3.AAR00"+ar+" LEFT JOIN  (";
						sql += "select AAR00"+ar+", count(AAC007) v7 from (select AAR00"+ar+",AAC007 from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += " ) where AAC007='04' group by AAR00"+ar+") w4 on w0.xz=w4.AAR00"+ar+" LEFT JOIN (select AAR00"+ar+", count(AAC007) v9 from ";
						
						sql += "(select AAR00"+ar+",AAC007 from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+" ";
						sql += ") where AAC007='05' group by AAR00"+ar+" ) w5 on w0.xz=w5.AAR00"+ar+" LEFT JOIN (select AAR00"+ar+", count(AAC007) v11 from ";
						sql += " (select AAR00"+ar+",AAC007 from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += " ) where AAC007='06' group by AAR00"+ar+") w6 on w0.xz=w6.AAR00"+ar+"  LEFT JOIN (";
						sql += " select AAR00"+ar+", count(AAC007) v13 from  (select AAR00"+ar+",AAC007 from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += ") where AAC007='07' group by AAR00"+ar+" ) w7 on w0.xz=w7.AAR00"+ar+" LEFT JOIN (";
						sql += "select AAR00"+ar+", count(AAC007) v15 from (select AAR00"+ar+",AAC007 from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += " ) where AAC007='08' group by AAR00"+ar+" ) w8 on w0.xz=w8.AAR00"+ar+" LEFT JOIN (";
						sql += "select AAR00"+ar+", count(AAC007) v17 from (select AAR00"+ar+",AAC007 from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += ") where AAC007='09' group by AAR00"+ar+" ) w9 on w0.xz=w9.AAR00"+ar+"  LEFT JOIN (";
						sql += "select AAR00"+ar+", count(AAC007) v19 from (select AAR00"+ar+",AAC007 from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += ") where AAC007='10' group by AAR00"+ar+" ) w10 on w0.xz=w10.AAR00"+ar+" LEFT JOIN ( ";
						sql += "select AAR00"+ar+", count(AAC007) v21 from (select AAR00"+ar+",AAC007 from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
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
					b_sql = "(AAR010='3' and NVL(AAC016,AAr040)='2016')";
				}
				String sql ="select NUM,xz,xzqh,v1,v3,v5,v7,v9,v11,v13,v15,v17,v19,v21,V23,V25, '"+j+"' type,XZ com_code,'2016' V99 ,'"+v98+"' v98 from ( ";
						sql += "SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NEIMENG0117_AC01  where AAR100= '1'";
						sql += " and AAR040='2016' and "+b_sql+" GROUP BY AAR00"+ar+"  )AA left join ( ";
						sql += " select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where xzqh is not null)w0 left join (";
						
						
						sql += " (select AAR00"+ar+", count(AAC008) v1 from  (select AAR00"+ar+",AAC008 from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += " ) where SUBSTR(AAC008, 1, 2) = '01' or SUBSTR(AAC008, 4, 4) = '01' group by AAR00"+ar+"))w1 ON w0.xz=w1.AAR00"+ar+"  LEFT JOIN (";
						sql += " select AAR00"+ar+" v3_6, count(AAC008) v3 from (select AAR00"+ar+",AAC008 from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += ") where SUBSTR(AAC008, 1, 2) = '02' or SUBSTR(AAC008, 4, 4) = '02' group by AAR00"+ar+")w2 ON w0.xz=w2.v3_6 LEFT JOIN(";
						
						sql += "select AAR00"+ar+", count(AAC008) v5 from (select AAR00"+ar+",AAC008 from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += " ) where SUBSTR(AAC008, 1, 2) = '03' or SUBSTR(AAC008, 4, 4) = '03' group by AAR00"+ar+" ) w3 on w0.xz=w3.AAR00"+ar+" LEFT JOIN  (";
						sql += "select AAR00"+ar+", count(AAC008) v7 from (select AAR00"+ar+",AAC008 from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+" ";
						sql += " ) where SUBSTR(AAC008, 1, 2) = '04' or SUBSTR(AAC008, 4, 4) = '04' group by AAR00"+ar+") w4 on w0.xz=w4.AAR00"+ar+" LEFT JOIN (select AAR00"+ar+", count(AAC008) v9 from ";
						
						sql += "(select AAR00"+ar+",AAC008 from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+" ";
						sql += ") where SUBSTR(AAC008, 1, 2) = '05' or SUBSTR(AAC008, 4, 4) = '05' group by AAR00"+ar+" ) w5 on w0.xz=w5.AAR00"+ar+" LEFT JOIN (select AAR00"+ar+", count(AAC008) v11 from ";
						sql += " (select AAR00"+ar+",AAC008 from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+" ";
						sql += " ) where SUBSTR(AAC008, 1, 2) = '06' or SUBSTR(AAC008, 4, 4) = '06' group by AAR00"+ar+") w6 on w0.xz=w6.AAR00"+ar+"  LEFT JOIN (";
						sql += " select AAR00"+ar+", count(AAC008) v13 from  (select AAR00"+ar+",AAC008 from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += ") where SUBSTR(AAC008, 1, 2) = '07' or SUBSTR(AAC008, 4, 4) = '07' group by AAR00"+ar+" ) w7 on w0.xz=w7.AAR00"+ar+" LEFT JOIN (";
						sql += "select AAR00"+ar+", count(AAC008) v15 from (select AAR00"+ar+",AAC008 from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+" ";
						sql += ") where SUBSTR(AAC008, 1, 2) = '08' or SUBSTR(AAC008, 4, 4) = '08' group by AAR00"+ar+" ) w8 on w0.xz=w8.AAR00"+ar+" LEFT JOIN (";
						sql += "select AAR00"+ar+", count(AAC008) v17 from (select AAR00"+ar+",AAC008 from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+" ";
						sql += ") where SUBSTR(AAC008, 1, 2) = '09' or SUBSTR(AAC008, 4, 4) = '09' group by AAR00"+ar+" ) w9 on w0.xz=w9.AAR00"+ar+"  LEFT JOIN (";
						sql += "select AAR00"+ar+", count(AAC008) v19 from (select AAR00"+ar+",AAC008 from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += ") where SUBSTR(AAC008, 1, 2) = '10' or SUBSTR(AAC008, 4, 4) = '10' group by AAR00"+ar+" ) w10 on w0.xz=w10.AAR00"+ar+" LEFT JOIN ( ";
						sql += "select AAR00"+ar+", count(AAC008) v21 from (select AAR00"+ar+",AAC008 from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += ") where SUBSTR(AAC008, 1, 2) = '11' or SUBSTR(AAC008, 4, 4) = '11' group by AAR00"+ar+" ) w11 on w0.xz=w11.AAR00"+ar+" LEFT JOIN (";
						
						sql += "select AAR00"+ar+", count(AAC008) v23 from (select AAR00"+ar+",AAC008 from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += ") where SUBSTR(AAC008, 1, 2) = '12' or SUBSTR(AAC008, 4, 4) = '12' group by AAR00"+ar+" ) w12 on w0.xz=w12.AAR00"+ar+" LEFT JOIN (";
						sql += "select AAR00"+ar+", count(AAC008) v25 from (select AAR00"+ar+",AAC008 from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
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
					b_sql = "(AAR010='3' and NVL(AAC016,AAr040)='2016')";
				}
				String sql ="select NUM,xz,xzqh, v1, '"+j+"' type,XZ com_code,'2016' V99 ,'"+v98+"' v98 from ( ";
						sql += "SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NEIMENG0117_AC01  where AAR100= '1' ";
						sql += " and AAR040='2016' and "+b_sql+" GROUP BY AAR00"+ar+"  )AA left join ( ";
						sql += " select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where xzqh is not null)w0 left join (";
						
						sql += "select count(AAC001) v1 ,AAR00"+ar+" from (select a.AAC001,AAR00"+ar+" from (";
						sql += "select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += ") a left join (select * from NEIMENG0117_AC08) b on a.AAC001=b.AAC001 where ";
						sql += "SUBSTR(b.AAR020, 0, 4) <='2016' AND SUBSTR(b.AAR021, 0, 4) >='2016' )t1 group BY AAR00"+ar+")w1 ON w0.xz=w1.AAR00"+ar+" ";
						
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
					b_sql = "(AAR010='3' and NVL(AAC016,AAr040)='2016')";
				}
				String sql ="select NUM,xz,xzqh, v1,v3,v5,v11,v13,ren, '"+j+"' type,XZ com_code,'2016' V99 ,'"+v98+"' v98 from ( ";
						sql += "SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NEIMENG0117_AC01  where AAR100= '1' ";
						sql += " and AAR040='2016' and "+b_sql+" GROUP BY AAR00"+ar+"  )AA left join ( ";
						sql += " select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where xzqh is not null)w0 left join (";
						
						sql += "select  NUM1 ren,AAR00"+ar+" renzx,xzqh xx from ( SELECT COUNT(*) NUM1,AAR00"+ar+" FROM";
						sql += " (SELECT * FROM (select AAC001 a1,AAB001 from NEIMENG0117_AB01 where AAR040='2016' and AAB015 IN ('1','4'))a LEFT JOIN (";
						sql += " select AAR00"+ar+",AAC001 from NEIMENG0117_AC01  where AAR100= '1' and AAR040='2016' and "+b_sql+")b";
						sql += " on a.a1=B.AAC001 ) GROUP BY AAR00"+ar+")AA left join ( select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+")ww on w0.xz=ww.renzx";
						sql += " left join (";
						
						sql += "select count(*) v1 ,AAR00"+ar+" from (select a.AAC001,AAR00"+ar+" from (select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+" ";
						sql += ") a left join (select * from NEIMENG0117_ac31) b on a.AAC001=b.AAC001  where b.AAC311='1'";
						sql += " )t1 group BY AAR00"+ar+" )w1 ON w0.xz=w1.AAR00"+ar+" LEFT JOIN( select count(*) v3 ,AAR00"+ar+" from ( select a.AAC001,AAR00"+ar+" from (";
						sql += "select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+") a left join ";
						sql += " (select * from NEIMENG0117_ac31 ) b on a.AAC001=b.AAC001 where b.AAC312='1')t1 group BY AAR00"+ar+" )w2 ON w0.xz=w2.AAR00"+ar+" LEFT JOIN(";
						sql += " select count(*) v5 ,AAR00"+ar+" from ( select a.AAC001,AAR00"+ar+" from (";
						sql += "select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += " ) a left join ";
						sql += "(select * from NEIMENG0117_ac31) b on a.AAC001=b.AAC001  where b.AAC313='1'";
						sql += ")t1 group BY AAR00"+ar+" )w3 ON w0.xz=w3.AAR00"+ar+" LEFT JOIN (select count(*) v11 ,AAR00"+ar+" from (";
						sql += "select a.AAC001,AAR00"+ar+" from (select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += ") a left join ";
						sql += " (select * from NEIMENG0117_ac31 ) b on a.AAC001=b.AAC001 where b.AAC319='1')t1 group BY AAR00"+ar+" )w5 ON w0.xz=w5.AAR00"+ar+" LEFT JOIN(";
						sql += " select sum(AAC317) v13,AAR00"+ar+" from ( select a.AAC001,AAR00"+ar+" ,AAC317 from (select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += ") a left join (select AAC317,AAC001 from NEIMENG0117_ac31) b on a.AAC001=b.AAC001  ";
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
					b_sql = "(AAR010='3' and NVL(AAC016,AAr040)='2016')";
				}
				String sql ="select NUM,xz,xzqh, v1,v3,v5,v11,v13, '"+j+"' type,XZ com_code,'2016' V99 ,'"+v98+"' v98 from ( ";
						sql += "SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NEIMENG0117_AC01  where AAR100= '1' ";
						sql += " and AAR040='2016' and "+b_sql+" GROUP BY AAR00"+ar+"  )AA left join ( ";
						sql += " select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where xzqh is not null)w0 left join (";
						
						sql += "select AAR00"+ar+" ,sum(AAC301) v1,sum(AAC302)v3,sum(AAC303)v5,sum(AAC304)v7,sum(AAC305)v9,sum(AAC306)v11,sum(AAC307)v13 from (";
						sql += " select AAC001,AAR00"+ar+"  from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += ") a left join (select * from NEIMENG0117_AC30) b on a.AAC001=b.AAC001 group BY AAR00"+ar+" ";
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
						this.getBySqlMapper.insert(insql);
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
					b_sql = "(AAR010='3' and NVL(AAC016,AAr040)='2016')";
				}
				String sql ="select NUM,xz,xzqh, v1,v3,v5,v7,v9,v11,v13, '"+j+"' type,'2016' V99 ,'"+v98+"' v98 from ( ";
						sql += "SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NEIMENG0117_AC01  where AAR100= '1' ";
						sql += " and AAR040='2016' and "+b_sql+" GROUP BY AAR00"+ar+"  )AA left join ( ";
						sql += " select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where xzqh is not null)w0 left join (";
						
						sql += "select AAR00"+ar+",count(*) v1 from (select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += ") a left join (select AAC001,AAC082 from NEIMENG0117_AC07) b on a.AAC001=b.AAC001 where b.AAC082<500 group BY AAR00"+ar+")w1 ON w0.xz=w1.AAR00"+ar+" left join(";
						sql += " select AAR00"+ar+",count(*) v3 from(select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+" ";
						sql += ") a left join (select AAC001,AAC082 from NEIMENG0117_AC07) b on a.AAC001=b.AAC001  where b.AAC082 >= 500 AND b.AAC082 < 1000 group BY AAR00"+ar+" )w2 ON w0.xz=w2.AAR00"+ar+" left join(";
						sql += "select AAR00"+ar+",count(*) v5 from (select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += ") a left join (select AAC001,AAC082 from NEIMENG0117_AC07) b on a.AAC001=b.AAC001 where b.AAC082 >= 1000 AND b.AAC082 < 1500 group BY AAR00"+ar+" )w3 ON w0.xz=w3.AAR00"+ar+" LEFT JOIN(";
						sql += "select AAR00"+ar+",count(*) v7 from (select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += ") a left join (select AAC001,AAC082 from NEIMENG0117_AC07) b on a.AAC001=b.AAC001 where b.AAC082 >= 1500 AND b.AAC082 < 2000   group BY AAR00"+ar+"";
						sql += ")w4 ON w0.xz=w4.AAR00"+ar+" LEFT JOIN(select AAR00"+ar+",count(*) v9 from (select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += ") a left join (select AAC001,AAC082 from NEIMENG0117_AC07) b on a.AAC001=b.AAC001 where b.AAC082 >= 2000 AND b.AAC082 < 2500 group BY AAR00"+ar+" )w5 ON w0.xz=w5.AAR00"+ar+" LEFT JOIN (";
						sql += "select AAR00"+ar+",count(*) v11 from (select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += ") a left join (select AAC001,AAC082 from NEIMENG0117_AC07) b on a.AAC001=b.AAC001 where b.AAC082 >= 2500 AND b.AAC082 < 2800  group BY AAR00"+ar+")w6 ON w0.xz=w6.AAR00"+ar+" LEFT JOIN (";
						sql += "select AAR00"+ar+",count(*) v13 from (select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += ") a left join (select AAC001,AAC082 from NEIMENG0117_AC07) b on a.AAC001=b.AAC001 where b.AAC082 >= 2800 group BY AAR00"+ar+")w7 ON w0.xz=w7.AAR00"+ar+"";
						
//						System.out.println(sql);
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
					b_sql = "(AAR010='3' and NVL(AAC016,AAr040)='2016')";
				}
				String sql ="select NUM,xz,xzqh, v1,v3,v5,v7,v9, '"+j+"' type,'2016' V99 ,'"+v98+"' v98 from ( ";
						sql += "SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NEIMENG0117_AC01  where AAR100= '1' ";
						sql += " and AAR040='2016' and "+b_sql+" GROUP BY AAR00"+ar+"  )AA left join ( ";
						sql += " select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where xzqh is not null)w0 left join (";
						
						sql += "select AAR00"+ar+",count(*) v1 from (select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += " )a left join (select AAC001,AAC320 from NEIMENG0117_AC31 ) b on a.AAC001=b.AAC001 where b.AAC320='01'  group BY AAR00"+ar+"";
						sql += ")w1 ON w0.xz=w1.AAR00"+ar+" left join (";
						sql += "select AAR00"+ar+",count(*) v3 from (select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += " ) a left join (select AAC001,AAC320 from NEIMENG0117_AC31 ) b on a.AAC001=b.AAC001 where b.AAC320='02'  group BY AAR00"+ar+"";
						sql += ")w2 ON w0.xz=w2.AAR00"+ar+" LEFT JOIN(select AAR00"+ar+",count(*) v5 from (select AAC001,AAR00"+ar+" from ";
						sql += "NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += ") a left join (select AAC001,AAC320 from NEIMENG0117_AC31 ) b on a.AAC001=b.AAC001 where b.AAC320='03'  group BY AAR00"+ar+"";
						sql += ")w3 ON w0.xz=w3.AAR00"+ar+" LEFT JOIN(select AAR00"+ar+",count(*) v7 from (select AAC001,AAR00"+ar+" from ";
						sql += "NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += ") a left join (select AAC001,AAC320 from NEIMENG0117_AC31 ) b on a.AAC001=b.AAC001 where b.AAC320='04'  group BY AAR00"+ar+")w4 ON w0.xz=w4.AAR00"+ar+" LEFT JOIN (";
						sql += "select AAR00"+ar+",count(*) v9 from (select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += ") a left join (select AAC001,AAC320 from NEIMENG0117_AC31 ) b on a.AAC001=b.AAC001 where b.AAC320='99' group BY AAR00"+ar+")w5 ON w0.xz=w5.AAR00"+ar+"";
						
//						System.out.println(sql);
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
					b_sql = "(AAR010='3' and NVL(AAC016,AAr040)='2016')";
				}
				String sql ="select NUM,xz,xzqh, v1,v2,v3,v4,v5, '"+j+"' type,'2016' V99 ,'"+v98+"' v98 from ( ";
						sql += "SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NEIMENG0117_AC01  where AAR100= '1' ";
						sql += " and AAR040='2016' and "+b_sql+" GROUP BY AAR00"+ar+"  )AA left join ( ";
						sql += " select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where xzqh is not null)w0 left join (";
						
						sql += "select AAR00"+ar+",count(*) v2 from (select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += ")a left join (select AAC001,AAC316 from NEIMENG0117_AC31 ) b on a.AAC001=b.AAC001 where b.AAC316='01'  group BY AAR00"+ar+"";
						sql += ")w1 ON w0.xz=w1.AAR00"+ar+" left join (";
						sql += "select AAR00"+ar+",count(*) v3 from (select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += ") a left join (select AAC001,AAC316 from NEIMENG0117_AC31 ) b on a.AAC001=b.AAC001 where b.AAC316='02'  group BY AAR00"+ar+"";
						sql += ")w2 ON w0.xz=w2.AAR00"+ar+" LEFT JOIN(select AAR00"+ar+",count(*) v4 from (select AAC001,AAR00"+ar+" from ";
						sql += "NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += ") a left join (select AAC001,AAC316 from NEIMENG0117_AC31 ) b on a.AAC001=b.AAC001 where b.AAC316='03'  group BY AAR00"+ar+"";
						sql += ")w3 ON w0.xz=w3.AAR00"+ar+" LEFT JOIN(select AAR00"+ar+",count(*) v5 from (select AAC001,AAR00"+ar+" from ";
						sql += "NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += ") a left join (select AAC001,AAC316 from NEIMENG0117_AC31 ) b on a.AAC001=b.AAC001 where b.AAC316='04'  group BY AAR00"+ar+")w4 ON w0.xz=w4.AAR00"+ar+" LEFT JOIN (";
						sql += "select AAR00"+ar+",sum(AAC315) v1 from (select AAC001,AAR00"+ar+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"";
						sql += ") a left join (select AAC001,AAC315 from NEIMENG0117_AC31 ) b on a.AAC001=b.AAC001  group BY AAR00"+ar+")w5 ON w0.xz=w5.AAR00"+ar+"";
						
//						System.out.println(sql);
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
	 * 上年度家庭收入统计
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("PKC_1_2_10.do")
	public  void  PKC_1_2_10 ( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		for ( int i =0 ; i < 4 ; i ++ ) {
			int v98 = 5-i;
			int ar = 6-i;
			int v10 = 2*v98;
			int xzqh = v10-1;
			
			for ( int j = 0 ; j < 2 ; j ++ ) {
				String b_sql = "" ;
				if ( j == 0 ){
					b_sql = "AAR010='0'";
				}else {
					b_sql = "(AAR010='3' and NVL(AAC016,AAr040)='2016')";
				}
				String sql = "select NUM, z.xz,z.xzqh,v1,round(v1/num, 2)  v2,round(v3/num,2) v3,round(v4/num,2) v4,round(v5/num,2) v5,round(v6/num,2) v6,round(v7/num,2) v7,round(v8/num,2) v8,round(v8/num1,2) v9,'"+j+"' CC,'"+v98+"' SS,'2016' VV from (SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NEIMENG0117_AC01  where AAR100= '1' "+
						" and AAR040='2016' and "+b_sql+" "+
						" GROUP BY AAR00"+ar+"  )AA left join (  select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where "+
						" xzqh is not null) z "+
						" LEFT JOIN ("+
						" SELECT  AAR00"+ar+",SUM(AAC081) v1 from ( "+
						" select AAC001,AAR00"+ar+" from  NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+" )a "+
						" LEFT JOIN ("+
						" SELECT AAC001,AAC081 from NEIMENG0117_AC07 )B ON A.AAC001=B.AAC001 GROUP BY AAR00"+ar+""+
						" ) W1 ON W1.AAR00"+ar+"=Z.XZ"+
						" LEFT JOIN"+
						" ("+
						
						" SELECT  AAR00"+ar+",SUM(AAC071) V3 from ("+
						" select AAC001,AAR00"+ar+" from "+
						" NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+" "+
						" )a LEFT JOIN ("+
							
						" SELECT AAC001,AAC071 FROM NEIMENG0117_AC07 "+
						
						" )B ON B.AAC001 =A.AAC001 GROUP BY AAR00"+ar+""+
						
						" )W3  ON W3.AAR00"+ar+"=Z.XZ"+
						
						" LEFT JOIN ("+
						
						" SELECT  AAR00"+ar+",SUM(AAC072) V5 from ("+
						" select AAC001,AAR00"+ar+" from "+
						" NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+""+
						" )a LEFT JOIN ("+
						
						" SELECT AAC001,AAC072 FROM NEIMENG0117_AC07"+
						
						" )B ON A.AAC001=B.AAC001 GROUP BY AAR00"+ar+""+
						
						" )W4 ON W4.AAR00"+ar+"=Z.XZ"+
						
						" LEFT JOIN ("+
						
						" SELECT  AAR00"+ar+",SUM(AAC085) V6 from ("+
						" select AAC001,AAR00"+ar+" from "+
						" NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+""+
						" )a LEFT JOIN ("+
						
						" SELECT AAC001,AAC085 FROM NEIMENG0117_AC07"+
						
						" )B ON A.AAC001=B.AAC001 GROUP BY AAR00"+ar+""+
						
						" )W5 ON W5.AAR00"+ar+"=Z.XZ"+
						
						" LEFT JOIN ("+
						" SELECT  A.AAR00"+ar+",SUM(AAC074) v7 from ("+
						"  select AAC001,AAR00"+ar+" from  NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+""+ 
						" )a LEFT JOIN ( SELECT AAC001,AAC074 FROM NEIMENG0117_AC07 )B ON A.AAC001=B.AAC001  "+
						" LEFT JOIN ( "+
						" select AAR00"+ar+",COUNT(*) ZD from  NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+" GROUP BY AAR00"+ar+" "+
						" )C ON A.AAR00"+ar+"=C.AAR00"+ar+" GROUP BY A.AAR00"+ar+" "+
						" )W6 ON W6.AAR00"+ar+"=Z.XZ"+
						
						" LEFT JOIN ("+
						" SELECT  A.AAR00"+ar+",SUM(AAC079) v8 from ( "+
						" select AAC001,AAR00"+ar+" from  NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+" )a "+
						" LEFT JOIN ( SELECT AAC001,AAC079 FROM NEIMENG0117_AC07 )B ON A.AAC001=B.AAC001  GROUP BY A.AAR00"+ar+""+
						" )W7 ON W7.AAR00"+ar+"=Z.XZ"+
						" LEFT JOIN ("+
						"  select  NUM1,AAR00"+ar+" xz,xzqh from ( SELECT COUNT(*) NUM1,AAR00"+ar+" FROM"+
						"  (SELECT * FROM (select AAC001 a1,AAB001 from NEIMENG0117_AB01 where AAR040='2016' and AAB015 IN ('1','4'))a LEFT JOIN ("+
						" select AAC001,AAR002,AAR003,AAR004,AAR005,AAR006 from NEIMENG0117_AC01  where AAR100= '1' and AAR040='2016' and "+b_sql+")b"+
						"  on a.a1=B.AAC001 ) GROUP BY AAR00"+ar+")AA left join (select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" ) ff ON FF.XZ=z.XZ"+
					
						" LEFT JOIN ("+
						" SELECT  AAR00"+ar+",SUM(AAC073) V4 from ("+
						" select AAC001,AAR00"+ar+" from "+
						" NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+""+
						" )a LEFT JOIN ("+
						
						" SELECT AAC001,AAC073 FROM NEIMENG0117_AC07"+
						
						" )B ON A.AAC001=B.AAC001 GROUP BY AAR00"+ar+""+
						
						" )W9 ON W9.AAR00"+ar+"=Z.XZ";
//				System.out.println(sql);
				List<Map> in_list = this.getBySqlMapper.findRecords(sql);
				for( int a = 0 ; a < in_list.size(); a++ ) {
					String z_hu = "".equals(in_list.get(a).get("NUM")) || in_list.get(a).get("NUM") == null ? "" : in_list.get(a).get("NUM").toString();
					String com_name =  in_list.get(a).get("XZQH").toString();
					String V1 = "".equals(in_list.get(a).get("V1")) || in_list.get(a).get("V1") == null ? "" : in_list.get(a).get("V1").toString();
					String V2 = "".equals(in_list.get(a).get("V2")) || in_list.get(a).get("V2") == null ? "" : in_list.get(a).get("V2").toString();
					String V3 =  "".equals(in_list.get(a).get("V3")) || in_list.get(a).get("V3") == null ? "" : in_list.get(a).get("V3").toString();
					String V4 =  "".equals(in_list.get(a).get("V4")) || in_list.get(a).get("V4") == null ? "" : in_list.get(a).get("V4").toString();
					String V5 =  "".equals(in_list.get(a).get("V5")) || in_list.get(a).get("V5") == null ? "" : in_list.get(a).get("V5").toString();
					String V6 =  "".equals(in_list.get(a).get("V6")) || in_list.get(a).get("V6") == null ? "" : in_list.get(a).get("V6").toString();
					String V7 =  "".equals(in_list.get(a).get("V7")) || in_list.get(a).get("V7") == null ? "" : in_list.get(a).get("V7").toString();
					String V8 =  "".equals(in_list.get(a).get("V8")) || in_list.get(a).get("V8") == null ? "" : in_list.get(a).get("V8").toString();
					String V9 =  "".equals(in_list.get(a).get("V9")) || in_list.get(a).get("V9") == null ? "" : in_list.get(a).get("V9").toString();
					
					String type =  "".equals(in_list.get(a).get("CC")) || in_list.get(a).get("CC") == null ? "" : in_list.get(a).get("CC").toString();
					String com_code =  "".equals(in_list.get(a).get("XZ")) || in_list.get(a).get("XZ") == null ? "" : in_list.get(a).get("XZ").toString();
					String V98 =  "".equals(in_list.get(a).get("SS")) || in_list.get(a).get("SS") == null ? "" : in_list.get(a).get("SS").toString();
					String V99 =  "".equals(in_list.get(a).get("VV")) || in_list.get(a).get("VV") == null ? "" : in_list.get(a).get("VV").toString();
					String insql = "insert into PKC_1_2_10 (COM_NAME,Z_HU,V1,V2,V3,V4,V5,V6,V7,V8,V9,TYPE,COM_CODE,V98,V99) VALUES ("+
									"'"+com_name+"','"+z_hu+"','"+V1+"','"+V2+"','"+V3+"','"+V4+"','"+V5+"','"+V6+"','"+V7+"','"+V8+"','"+V9+"','"+type+"','"+com_code+"','"+V98+"','"+V99+"')";
					this.getBySqlMapper.insert(insql);
					
				}
					
					
			}
			
			
		}
	}
	/**
	 * 上年度转移性收入构成
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("PKC_1_2_11.do")
	public  void  PKC_1_2_11 ( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		for ( int i =0 ; i < 4 ; i ++ ) {
			int v98 = 5-i;
			int ar = 6-i;
			int v10 = 2*v98;
			int xzqh = v10-1;
			
			for ( int j = 0 ; j < 2 ; j ++ ) {
				String b_sql = "" ;
				if ( j == 0 ){
					b_sql = "AAR010='0'";
				}else {
					b_sql = "(AAR010='3' and NVL(AAC016,AAr040)='2016')";
				}
				
				String sql="select NUM, xz,xzqh,ROUND(v1/num, 2) v1,ROUND(v2/num, 2) v2,ROUND(v4/num, 2) v4,ROUND(v6/num, 2) v6,ROUND(v8/num, 2) v8,'"+j+"' CC,'"+v98+"' SS,'2016' VV from (SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NEIMENG0117_AC01  where AAR100= '1' "+
						" and AAR040='2016' and  "+b_sql+""+
						"  GROUP BY AAR00"+ar+"  )AA left join (  select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where "+
						" xzqh is not null) z "+
						" LEFT JOIN ("+
						" SELECT  A.AAR00"+ar+",SUM(AAC085) v1 from ("+ 
						" select AAC001,AAR00"+ar+" from  NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and  "+b_sql+" )a "+
						" LEFT JOIN (SELECT AAC001,AAC085 from NEIMENG0117_AC07 )B ON A.AAC001=B.AAC001 "+
						" LEFT JOIN ( select AAR00"+ar+",COUNT(*) ZD from  NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"  GROUP BY AAR00"+ar+" "+
						" )C ON A.AAR00"+ar+"=C.AAR00"+ar+" GROUP BY A.AAR00"+ar+""+
						" ) W1 ON W1.AAR00"+ar+"=Z.XZ"+
						" LEFT JOIN ("+
						" SELECT  A.AAR00"+ar+",SUM(AAC076) v2 from ( "+
						" select AAC001,AAR00"+ar+" from  NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and  "+b_sql+" )a "+
						" LEFT JOIN (SELECT AAC001,AAC076 from NEIMENG0117_AC07 )B ON A.AAC001=B.AAC001  GROUP BY A.AAR00"+ar+""+
						" ) W2 ON W2.AAR00"+ar+"=Z.XZ"+
						" LEFT JOIN (SELECT  A.AAR00"+ar+",SUM(AAC077) v4 from ( "+
						" select AAC001,AAR00"+ar+" from  NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and  "+b_sql+" )a "+
						"　LEFT JOIN (SELECT AAC001,AAC077 from NEIMENG0117_AC07 )B ON A.AAC001=B.AAC001  GROUP BY A.AAR00"+ar+""+
						" ) W3 ON W3.AAR00"+ar+"=Z.XZ"+
						" LEFT JOIN ("+
						" SELECT  A.AAR00"+ar+",SUM(AAC087) v6 from ( "+
						" select AAC001,AAR00"+ar+" from  NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and  "+b_sql+"  )a "+
						" LEFT JOIN (SELECT AAC001,AAC087 from NEIMENG0117_AC07 )B ON A.AAC001=B.AAC001  GROUP BY A.AAR00"+ar+""+
						" ) W4 ON W4.AAR00"+ar+"=Z.XZ"+
						" LEFT JOIN ("+
						" SELECT  A.AAR00"+ar+",SUM(AAC078) v8 from ( "+
						" select AAC001,AAR00"+ar+" from  NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+"  )a "+
						" LEFT JOIN (SELECT AAC001,AAC078 from NEIMENG0117_AC07 )B ON A.AAC001=B.AAC001  GROUP BY A.AAR00"+ar+""+
						" ) W5 ON W5.AAR00"+ar+"=Z.XZ";
				
				List<Map> in_list = this.getBySqlMapper.findRecords(sql);
				for( int a = 0 ; a < in_list.size(); a++ ) {
					String z_hu = "".equals(in_list.get(a).get("NUM")) || in_list.get(a).get("NUM") == null ? "" : in_list.get(a).get("NUM").toString();
					String com_name =  in_list.get(a).get("XZQH").toString();
					String V1 = "".equals(in_list.get(a).get("V1")) || in_list.get(a).get("V1") == null ? "" : in_list.get(a).get("V1").toString();
					String V2 = "".equals(in_list.get(a).get("V2")) || in_list.get(a).get("V2") == null ? "" : in_list.get(a).get("V2").toString();
					String V4 =  "".equals(in_list.get(a).get("V4")) || in_list.get(a).get("V4") == null ? "" : in_list.get(a).get("V4").toString();
					String V6 =  "".equals(in_list.get(a).get("V6")) || in_list.get(a).get("V6") == null ? "" : in_list.get(a).get("V6").toString();
					String V8 =  "".equals(in_list.get(a).get("V8")) || in_list.get(a).get("V8") == null ? "" : in_list.get(a).get("V8").toString();
					
					String type =  "".equals(in_list.get(a).get("CC")) || in_list.get(a).get("CC") == null ? "" : in_list.get(a).get("CC").toString();
					String com_code =  "".equals(in_list.get(a).get("XZ")) || in_list.get(a).get("XZ") == null ? "" : in_list.get(a).get("XZ").toString();
					String V98 =  "".equals(in_list.get(a).get("SS")) || in_list.get(a).get("SS") == null ? "" : in_list.get(a).get("SS").toString();
					String V99 =  "".equals(in_list.get(a).get("VV")) || in_list.get(a).get("VV") == null ? "" : in_list.get(a).get("VV").toString();
					String insql = "insert into PKC_1_2_11 (COM_NAME,Z_HU,V1,V2,V4,V6,V8,TYPE,COM_CODE,V98,V99) VALUES ("+
									"'"+com_name+"','"+z_hu+"','"+V1+"','"+V2+"','"+V4+"','"+V6+"','"+V8+"','"+type+"','"+com_code+"','"+V98+"','"+V99+"')";
					this.getBySqlMapper.insert(insql);
				}
			}
		}
	}
	/**
	 * 人口规模情况
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("PKC_1_2_12.do")
	public  void  PKC_1_2_12 ( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		for ( int i =0 ; i < 4 ; i ++ ) {
			int v98 = 5-i;
			int ar = 6-i;
			int v10 = 2*v98;
			int xzqh = v10-1;
			
			for ( int j = 0 ; j < 2 ; j ++ ) {
				String b_sql = "" ;
				if ( j == 0 ){
					b_sql = "AAR010='0'";
				}else {
					b_sql = "(AAR010='3' and NVL(AAC016,AAr040)='2016')";
				}
				
				String sql ="select NUM,xz,xzqh,v1,v2,v3,v4,v5,v6,'"+j+"' CC,'"+v98+"' SS,'2016' VV from (SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NEIMENG0117_AC01  where AAR100= '1' "+ 
							" and AAR040='2016' and "+b_sql+""+
							" GROUP BY AAR00"+ar+"  )AA left join (  select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where  "+
							" xzqh is not null) z "+
							" LEFT JOIN ("+
							
							" select AAR00"+ar+", SUM(v1)v1 from ("+
							" SELECT  AAR00"+ar+",a.AAC001,count(*) v1 from ("+
							" select AAC001,AAR00"+ar+" from "+
							" NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+""+
							" )a LEFT JOIN ("+
							
							" SELECT AAC001,count(*) v1 FROM NEIMENG0117_AB01 GROUP BY AAC001"+
							
							" )B ON A.AAC001=B.AAC001  where b.v1=1  GROUP BY AAR00"+ar+",a.AAC001 "+
							" )d  GROUP BY AAR00"+ar+""+
							" ) W1 ON W1.AAR00"+ar+"=Z.XZ"+
							
							" LEFT JOIN ("+
							
							" select AAR00"+ar+", SUM(v2) v2 from ("+
							" SELECT  AAR00"+ar+",a.AAC001,count(*) v2 from ("+
							" select AAC001,AAR00"+ar+" from "+
							" NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+""+
							" )a LEFT JOIN ("+
							
							" SELECT AAC001,count(*) v2 FROM NEIMENG0117_AB01 GROUP BY AAC001"+
							
							" )B ON A.AAC001=B.AAC001  where b.v2=2  GROUP BY AAR00"+ar+",a.AAC001 "+
							" )d  GROUP BY AAR00"+ar+""+
							" ) W2 ON W2.AAR00"+ar+"=Z.XZ"+
							
							
							" LEFT JOIN ("+
							
							" select AAR00"+ar+", SUM(v3) v3 from ("+
							" SELECT  AAR00"+ar+",a.AAC001,count(*) v3 from ("+
							" select AAC001,AAR00"+ar+" from "+
							" NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+""+
							" )a LEFT JOIN ("+
							
							" SELECT AAC001,count(*) v3 FROM NEIMENG0117_AB01 GROUP BY AAC001"+
							
							" )B ON A.AAC001=B.AAC001  where b.v3=3  GROUP BY AAR00"+ar+",a.AAC001 "+
							" )d  GROUP BY AAR00"+ar+""+
							" ) W3 ON W3.AAR00"+ar+"=Z.XZ"+
							
							" LEFT JOIN ("+
							
							" select AAR00"+ar+", SUM(v4) v4 from ("+
							" SELECT  AAR00"+ar+",a.AAC001,count(*) v4 from ("+
							" select AAC001,AAR00"+ar+" from "+
							" NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+""+
							" )a LEFT JOIN ("+
							
							" SELECT AAC001,count(*) v4 FROM NEIMENG0117_AB01 GROUP BY AAC001"+
							
							" )B ON A.AAC001=B.AAC001  where b.v4=4  GROUP BY AAR00"+ar+",a.AAC001 "+
							" )d  GROUP BY AAR00"+ar+""+
							" ) W4 ON W4.AAR00"+ar+"=Z.XZ"+
							
							
							" LEFT JOIN ("+
							
							" select AAR00"+ar+", SUM(v5) v5 from ("+
							" SELECT  AAR00"+ar+",a.AAC001,count(*) v5 from ("+
							" select AAC001,AAR00"+ar+" from "+
							" NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+""+
							" )a LEFT JOIN ("+
							
							" SELECT AAC001,count(*) v5 FROM NEIMENG0117_AB01 GROUP BY AAC001"+
							
							" )B ON A.AAC001=B.AAC001  where b.v5=5  GROUP BY AAR00"+ar+",a.AAC001 "+
							" )d  GROUP BY AAR00"+ar+""+
							" ) W5 ON W5.AAR00"+ar+"=Z.XZ"+
							
							" LEFT JOIN ("+
							
							" select AAR00"+ar+", SUM(v6) v6 from ("+
							" SELECT  AAR00"+ar+",a.AAC001,count(*) v6 from ("+
							" select AAC001,AAR00"+ar+" from "+
							" NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+""+
							" )a LEFT JOIN ("+
							
							" SELECT AAC001,count(*) v6 FROM NEIMENG0117_AB01 GROUP BY AAC001"+
							
							" )B ON A.AAC001=B.AAC001  where b.v6>=6  GROUP BY AAR00"+ar+",a.AAC001 "+
							" )d  GROUP BY AAR00"+ar+""+
							" ) W6 ON W6.AAR00"+ar+"=Z.XZ";
				List<Map> in_list = this.getBySqlMapper.findRecords(sql);
				for( int a = 0 ; a < in_list.size(); a++ ) {
					
						
					
					String z_hu = "".equals(in_list.get(a).get("NUM")) || in_list.get(a).get("NUM") == null ? "" : in_list.get(a).get("NUM").toString();
					String com_name =  in_list.get(a).get("XZQH").toString();
					String V1 = "".equals(in_list.get(a).get("V1")) || in_list.get(a).get("V1") == null ? "" : in_list.get(a).get("V1").toString();
					String V3 = "".equals(in_list.get(a).get("V2")) || in_list.get(a).get("V2") == null ? "" : in_list.get(a).get("V2").toString();
					String V5 = "".equals(in_list.get(a).get("V3")) || in_list.get(a).get("V3") == null ? "" : in_list.get(a).get("V3").toString();
					String V7 = "".equals(in_list.get(a).get("V4")) || in_list.get(a).get("V4") == null ? "" : in_list.get(a).get("V4").toString();
					String V9 = "".equals(in_list.get(a).get("V5")) || in_list.get(a).get("V5") == null ? "" : in_list.get(a).get("V5").toString();
					String V11 = "".equals(in_list.get(a).get("V6")) || in_list.get(a).get("V6") == null ? "" : in_list.get(a).get("V6").toString();
					
					
					String type =  "".equals(in_list.get(a).get("CC")) || in_list.get(a).get("CC") == null ? "" : in_list.get(a).get("CC").toString();
					String com_code =  "".equals(in_list.get(a).get("XZ")) || in_list.get(a).get("XZ") == null ? "" : in_list.get(a).get("XZ").toString();
					String V98 =  "".equals(in_list.get(a).get("SS")) || in_list.get(a).get("SS") == null ? "" : in_list.get(a).get("SS").toString();
					String V99 =  "".equals(in_list.get(a).get("VV")) || in_list.get(a).get("VV") == null ? "" : in_list.get(a).get("VV").toString();
					String insql = "insert into PKC_1_2_12 (COM_NAME,Z_HU,V1,V3,V5,V7,V9,V11,TYPE,COM_CODE,V98,V99) VALUES ("+
									"'"+com_name+"','"+z_hu+"','"+V1+"','"+V3+"','"+V5+"','"+V7+"','"+V9+"','"+V11+"','"+type+"','"+com_code+"','"+V98+"','"+V99+"')";
					this.getBySqlMapper.insert(insql);
					
				}
			}
		}
	}
	
	/**
	 * 党员情况
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("PKC_1_2_13.do")
	public  void  PKC_1_2_13 ( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		for ( int i =0 ; i < 4 ; i ++ ) {
			int v98 = 5-i;
			int ar = 6-i;
			int v10 = 2*v98;
			int xzqh = v10-1;
			
			for ( int j = 0 ; j < 2 ; j ++ ) {
				String b_sql = "" ;
				if ( j == 0 ){
					b_sql = "AAR010='0'";
				}else {
					b_sql = "(AAR010='3' and NVL(AAC016,AAr040)='2016')";
				}
				String sql="select NUM,xz,xzqh,v1,ROUND(v1/num , 3)*100 bl,'"+j+"' CC,'"+v98+"' SS,'2016' VV from (SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NEIMENG0117_AC01  where AAR100= '1' "+
							" and AAR040='2016' and "+b_sql+""+
							"  GROUP BY AAR00"+ar+"  )AA left join (  select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where "+
							" xzqh is not null) z "+
							" LEFT JOIN ("+
							
							" select AAR00"+ar+", sum(v1) v1 from ("+
							" SELECT  AAR00"+ar+",a.AAC001,b.v1 from ("+
							" select AAC001,AAR00"+ar+" from "+
							" NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+""+
							" )a LEFT JOIN ("+
							
							" SELECT AAC001,count(*) v1 FROM NEIMENG0117_AB01 where AAK033='01' GROUP BY AAC001"+
							
							" )B ON A.AAC001=B.AAC001 "+
							" )d  GROUP BY AAR00"+ar+""+
							" 	) W1 ON W1.AAR00"+ar+"=Z.XZ";
				List<Map> in_list = this.getBySqlMapper.findRecords(sql);
				for( int a = 0 ; a < in_list.size(); a++ ) {
					String z_hu = "".equals(in_list.get(a).get("NUM")) || in_list.get(a).get("NUM") == null ? "" : in_list.get(a).get("NUM").toString();
					String com_name =  in_list.get(a).get("XZQH").toString();
					String V1 = "".equals(in_list.get(a).get("V1")) || in_list.get(a).get("V1") == null ? "" : in_list.get(a).get("V1").toString();
					String V2 = "".equals(in_list.get(a).get("BL")) || in_list.get(a).get("BL") == null ? "" : in_list.get(a).get("BL").toString();
					String type =  "".equals(in_list.get(a).get("CC")) || in_list.get(a).get("CC") == null ? "" : in_list.get(a).get("CC").toString();
					String com_code =  "".equals(in_list.get(a).get("XZ")) || in_list.get(a).get("XZ") == null ? "" : in_list.get(a).get("XZ").toString();
					String V98 =  "".equals(in_list.get(a).get("SS")) || in_list.get(a).get("SS") == null ? "" : in_list.get(a).get("SS").toString();
					String V99 =  "".equals(in_list.get(a).get("VV")) || in_list.get(a).get("VV") == null ? "" : in_list.get(a).get("VV").toString();
					String insql = "insert into PKC_1_2_13 (COM_NAME,Z_HU,V1,V2,TYPE,COM_CODE,V98,V99) VALUES ("+
									"'"+com_name+"','"+z_hu+"','"+V1+"','"+V2+"','"+type+"','"+com_code+"','"+V98+"','"+V99+"')";
					this.getBySqlMapper.insert(insql);
				}
			}
		}
	}
	/**
	 * 住房面积
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("PKC_1_2_14.do")
	public  void  PKC_1_2_14 ( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		for ( int i =0 ; i < 4 ; i ++ ) {
			int v98 = 5-i;
			int ar = 6-i;
			int v10 = 2*v98;
			int xzqh = v10-1;
			
			for ( int j = 0 ; j < 2 ; j ++ ) {
				String b_sql = "" ;
				if ( j == 0 ){
					b_sql = "AAR010='0'";
				}else {
					b_sql = "(AAR010='3' and NVL(AAC016,AAr040)='2016')";
				}
				String sql="select NUM,xz,xzqh,v1,v2,v3,v4,v5,v6,V7,'"+j+"' CC,'"+v98+"' SS,'2016' VV from (SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NEIMENG0117_AC01  where AAR100= '1' "+
							" and AAR040='2016' and  "+b_sql+""+
						"  GROUP BY AAR00"+ar+"  )AA left join (  select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where "+
						" xzqh is not null) z "+
						" LEFT JOIN ("+
							
							
						" select AAR00"+ar+",COUNT(*) v1 from ("+
						" SELECT  AAR00"+ar+",a.AAC001 from ("+
						" select AAC001,AAR00"+ar+" from "+
						" NEIMENG0117_AC01 where AAR100= '1' and "+b_sql+""+
						" )a LEFT JOIN ("+
							
						" SELECT AAC001,AAC317 FROM NEIMENG0117_AC31"+
						" )B ON A.AAC001=B.AAC001    where B.AAC317=0"+
							
						" )d  GROUP BY AAR00"+ar+""+
							
						" ) W1 ON W1.AAR00"+ar+"=Z.XZ"+
							
						" LEFT JOIN ("+
							
							
						" select AAR00"+ar+",COUNT(*) v2 from ("+
						" SELECT  AAR00"+ar+",a.AAC001 from ("+
						" select AAC001,AAR00"+ar+" from "+
						" NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+""+
						" )a LEFT JOIN ("+
							
						" SELECT AAC001,AAC317 FROM NEIMENG0117_AC31 "+
							
						" )B ON A.AAC001=B.AAC001   where AAC317<10"+
						" )d  GROUP BY AAR00"+ar+""+
							
						" ) W2 ON W2.AAR00"+ar+"=Z.XZ"+
							
						" LEFT JOIN ("+
							
							
						" select AAR00"+ar+",COUNT(*) v3 from ("+
						" SELECT  AAR00"+ar+",a.AAC001 from ("+
						" select AAC001,AAR00"+ar+" from "+
						" NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+""+
						" )a LEFT JOIN ("+
							
						" SELECT AAC001,AAC317 FROM NEIMENG0117_AC31 "+
							
						" )B ON A.AAC001=B.AAC001   where AAC317>=10 and AAC317<26"+
						" )d  GROUP BY AAR00"+ar+""+
							
						" ) W3 ON W3.AAR00"+ar+"=Z.XZ"+
							
						" LEFT JOIN ("+
							
							
						" select AAR00"+ar+",COUNT(*) v4 from ("+
						" SELECT  AAR00"+ar+",a.AAC001 from ("+
						" select AAC001,AAR00"+ar+" from "+
						" NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+""+
						" )a LEFT JOIN ("+
							
						" SELECT AAC001,AAC317 FROM NEIMENG0117_AC31 "+
							
						" )B ON A.AAC001=B.AAC001   where AAC317>=25 and AAC317<50"+
						" )d  GROUP BY AAR00"+ar+""+
							
						" ) W4 ON W4.AAR00"+ar+"=Z.XZ"+
							
						" LEFT JOIN ("+
							
							
						" select AAR00"+ar+",COUNT(*) v5 from ("+
						" SELECT  AAR00"+ar+",a.AAC001 from ("+
						" select AAC001,AAR00"+ar+" from "+
						" NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+""+
						" )a LEFT JOIN ("+
							
						" SELECT AAC001,AAC317 FROM NEIMENG0117_AC31 "+
							
						" )B ON A.AAC001=B.AAC001   where AAC317>=50 and AAC317<75"+
						" )d  GROUP BY AAR00"+ar+""+
							
						" ) W5 ON W5.AAR00"+ar+"=Z.XZ"+
							
						" LEFT JOIN ("+
							
							
						" select AAR00"+ar+",COUNT(*) v6 from ("+
						" SELECT  AAR00"+ar+",a.AAC001 from ("+
						" select AAC001,AAR00"+ar+" from "+
						" NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+""+
						" )a LEFT JOIN ("+
							
						" SELECT AAC001,AAC317 FROM NEIMENG0117_AC31 "+
							
						" )B ON A.AAC001=B.AAC001   where AAC317>=75 and AAC317<100"+
						" )d  GROUP BY AAR00"+ar+""+
							
						" ) W6 ON W6.AAR00"+ar+"=Z.XZ"+
							
						" LEFT JOIN ("+
							
							
						" select AAR00"+ar+",COUNT(*) v7 from ("+
						" SELECT AAR00"+ar+",a.AAC001 from ("+
						" select AAC001,AAR00"+ar+" from "+
						" NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+""+
						" )a LEFT JOIN ("+
							
						" SELECT AAC001,AAC317 FROM NEIMENG0117_AC31 "+
							
						" )B ON A.AAC001=B.AAC001   where AAC317>=100 "+
						" )d  GROUP BY AAR00"+ar+""+
							
						" ) W7 ON W7.AAR00"+ar+"=Z.XZ";
				List<Map> in_list = this.getBySqlMapper.findRecords(sql);
				for( int a = 0 ; a < in_list.size(); a++ ) {
					
						
					
					String z_hu = "".equals(in_list.get(a).get("NUM")) || in_list.get(a).get("NUM") == null ? "" : in_list.get(a).get("NUM").toString();
					String com_name =  in_list.get(a).get("XZQH").toString();
					String V1 = "".equals(in_list.get(a).get("V1")) || in_list.get(a).get("V1") == null ? "" : in_list.get(a).get("V1").toString();
					String V3 = "".equals(in_list.get(a).get("V2")) || in_list.get(a).get("V2") == null ? "" : in_list.get(a).get("V2").toString();
					String V5 = "".equals(in_list.get(a).get("V3")) || in_list.get(a).get("V3") == null ? "" : in_list.get(a).get("V3").toString();
					String V7 = "".equals(in_list.get(a).get("V4")) || in_list.get(a).get("V4") == null ? "" : in_list.get(a).get("V4").toString();
					String V9 = "".equals(in_list.get(a).get("V5")) || in_list.get(a).get("V5") == null ? "" : in_list.get(a).get("V5").toString();
					String V11 = "".equals(in_list.get(a).get("V6")) || in_list.get(a).get("V6") == null ? "" : in_list.get(a).get("V6").toString();
					String V13 = "".equals(in_list.get(a).get("V7")) || in_list.get(a).get("V7") == null ? "" : in_list.get(a).get("V7").toString();
					
					
					String type =  "".equals(in_list.get(a).get("CC")) || in_list.get(a).get("CC") == null ? "" : in_list.get(a).get("CC").toString();
					String com_code =  "".equals(in_list.get(a).get("XZ")) || in_list.get(a).get("XZ") == null ? "" : in_list.get(a).get("XZ").toString();
					String V98 =  "".equals(in_list.get(a).get("SS")) || in_list.get(a).get("SS") == null ? "" : in_list.get(a).get("SS").toString();
					String V99 =  "".equals(in_list.get(a).get("VV")) || in_list.get(a).get("VV") == null ? "" : in_list.get(a).get("VV").toString();
					double zong = Double.parseDouble(z_hu);
					Double V2,V4,V6,V8,V10,V12,V14 = null;
					if (!V1.equals("") || V1 !="") {
						 V2 = (Double.parseDouble(V1)/zong)*100;
					}else {
						 V2 = 0.0;
					}
					if (!V3.equals("")|| V3 !="") {
						 V4 = (Double.parseDouble(V3)/zong)*100;
					}else {
						 V4 =0.0;
					}
					if (!V5.equals("")|| V5 !="") {
						 V6 = (Double.parseDouble(V5)/zong)*100;
					}else {
						 V6 =0.0;
					}
					if (!V7.equals("") || V7 !="") {
						 V8 = (Double.parseDouble(V7)/zong)*100;
					}else {
						 V8 =0.0;
					}
					if (!V9.equals("")) {
						 V10 = (Double.parseDouble(V9)/zong)*100;
					}else {
						 V10 =0.0;
					}
					if (!V11.equals("")|| V11 !="") {
						 V12 = (Double.parseDouble(V11)/zong)*100;
					}else {
						 V12 =0.0;
					}
					if (!V13.equals("")|| V13 !="") {
						 V14 = (Double.parseDouble(V13)/zong)*100;
					}else {
						 V14 =0.0;
					}
					String insql = "insert into PKC_1_2_14 (COM_NAME,Z_HU,V1,V3,V5,V7,V9,V11,V13,V2,V4,V6,V8,V10,V12,V14,TYPE,COM_CODE,V98,V99) VALUES ("+
									"'"+com_name+"','"+z_hu+"','"+V1+"','"+V3+"','"+V5+"','"+V7+"','"+V9+"','"+V11+"','"+V13+"','"+V2+"','"+V4+"','"+V6+"','"+V8+"','"+V10+"','"+V12+"','"+V14+"','"+type+"','"+com_code+"','"+V98+"','"+V99+"')";
					this.getBySqlMapper.insert(insql);
				}
			
			}
		}
	}
	/**
	 * 与村主干路距离
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("PKC_1_2_15.do")
	public  void  PKC_1_2_15 ( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		for ( int i =0 ; i < 4 ; i ++ ) {
			int v98 = 5-i;
			int ar = 6-i;
			int v10 = 2*v98;
			int xzqh = v10-1;
			
			for ( int j = 0 ; j < 2 ; j ++ ) {
				String b_sql = "" ;
				if ( j == 0 ){
					b_sql = "AAR010='0'";
				}else {
					b_sql = "(AAR010='3' and NVL(AAC016,AAr040)='2016')";
				}
				String sql="select NUM,xz,xzqh,v1,v2,v3,v4,v5,v6,'"+j+"' CC,'"+v98+"' SS,'2016' VV from (SELECT NUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM,AAR00"+ar+" from NEIMENG0117_AC01  where AAR100= '1' "+
						"  and AAR040='2016' and "+b_sql+""+
						"  GROUP BY AAR00"+ar+"  )AA left join (  select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where "+
						" xzqh is not null) z "+
						" LEFT JOIN ("+
					
					
						" select AAR00"+ar+",COUNT(*) v1 from ("+
						" SELECT  AAR00"+ar+",a.AAC001 from ("+
						" select AAC001,AAR00"+ar+" from "+
						" NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+""+
						" )a LEFT JOIN ("+
					
						" SELECT AAC001,AAC315 FROM NEIMENG0117_AC31"+
						" )B ON A.AAC001=B.AAC001    where B.AAC315<1"+
					
						" )d  GROUP BY AAR00"+ar+""+
					
						" ) W1 ON W1.AAR00"+ar+"=Z.XZ"+
					
						" LEFT JOIN ("+
					
						" select AAR00"+ar+",COUNT(*) v2 from ("+
						" SELECT  AAR00"+ar+",a.AAC001 from ("+
						" select AAC001,AAR00"+ar+" from "+
						" NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+""+
						" )a LEFT JOIN ("+
						" SELECT AAC001,AAC315 FROM NEIMENG0117_AC31 "+
						" )B ON A.AAC001=B.AAC001   where AAC315>=1 and AAC315<3"+
						" )d  GROUP BY AAR00"+ar+""+
						" ) W2 ON W2.AAR00"+ar+"=Z.XZ"+
						" LEFT JOIN ("+
						" select AAR00"+ar+",COUNT(*) v3 from ("+
						" SELECT  AAR00"+ar+",a.AAC001 from ("+
						" select AAC001,AAR00"+ar+" from "+
						" NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+""+
						" )a LEFT JOIN ("+
						" SELECT AAC001,AAC315 FROM NEIMENG0117_AC31 "+
						" )B ON A.AAC001=B.AAC001   where AAC315>=3 and AAC315<5"+
						" )d  GROUP BY AAR00"+ar+""+
						" ) W3 ON W3.AAR00"+ar+"=Z.XZ"+
						" LEFT JOIN ("+
						" select AAR00"+ar+",COUNT(*) v4 from ("+
						" SELECT  AAR00"+ar+",a.AAC001 from ("+
						" select AAC001,AAR00"+ar+" from "+
						" NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+""+
						" )a LEFT JOIN ("+
						" SELECT AAC001,AAC315 FROM NEIMENG0117_AC31 "+
						" )B ON A.AAC001=B.AAC001   where AAC315>=5 and AAC315<10"+
						" )d  GROUP BY AAR00"+ar+""+
						" ) W4 ON W4.AAR00"+ar+"=Z.XZ"+
						" LEFT JOIN ("+
						" select AAR00"+ar+",COUNT(*) v5 from ("+
						" SELECT  AAR00"+ar+",a.AAC001 from ("+
						" select AAC001,AAR00"+ar+" from "+
						" NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+""+
						" )a LEFT JOIN ("+
						" SELECT AAC001,AAC315 FROM NEIMENG0117_AC31 "+
						" )B ON A.AAC001=B.AAC001   where AAC315>=10 and AAC315<20"+
						" )d  GROUP BY AAR00"+ar+""+
						" ) W5 ON W5.AAR00"+ar+"=Z.XZ"+
						" LEFT JOIN ("+
						" select AAR00"+ar+",COUNT(*) v6 from ("+
						" SELECT  AAR00"+ar+",a.AAC001 from ("+
						" select AAC001,AAR00"+ar+" from "+
						" NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and "+b_sql+""+
						" )a LEFT JOIN ("+
						" SELECT AAC001,AAC315 FROM NEIMENG0117_AC31 "+
						" )B ON A.AAC001=B.AAC001   where AAC315>=20"+
						" )d  GROUP BY AAR00"+ar+""+
						" ) W6 ON W6.AAR00"+ar+"=Z.XZ";
				List<Map> in_list = this.getBySqlMapper.findRecords(sql);
				for( int a = 0 ; a < in_list.size(); a++ ) {
					String z_hu = "".equals(in_list.get(a).get("NUM")) || in_list.get(a).get("NUM") == null ? "" : in_list.get(a).get("NUM").toString();
					String com_name =  in_list.get(a).get("XZQH").toString();
					String V1 = "".equals(in_list.get(a).get("V1")) || in_list.get(a).get("V1") == null ? "" : in_list.get(a).get("V1").toString();
					String V3 = "".equals(in_list.get(a).get("V2")) || in_list.get(a).get("V2") == null ? "" : in_list.get(a).get("V2").toString();
					String V5 = "".equals(in_list.get(a).get("V3")) || in_list.get(a).get("V3") == null ? "" : in_list.get(a).get("V3").toString();
					String V7 = "".equals(in_list.get(a).get("V4")) || in_list.get(a).get("V4") == null ? "" : in_list.get(a).get("V4").toString();
					String V9 = "".equals(in_list.get(a).get("V5")) || in_list.get(a).get("V5") == null ? "" : in_list.get(a).get("V5").toString();
					String V11 = "".equals(in_list.get(a).get("V6")) || in_list.get(a).get("V6") == null ? "" : in_list.get(a).get("V6").toString();
					String type =  "".equals(in_list.get(a).get("CC")) || in_list.get(a).get("CC") == null ? "" : in_list.get(a).get("CC").toString();
					String com_code =  "".equals(in_list.get(a).get("XZ")) || in_list.get(a).get("XZ") == null ? "" : in_list.get(a).get("XZ").toString();
					String V98 =  "".equals(in_list.get(a).get("SS")) || in_list.get(a).get("SS") == null ? "" : in_list.get(a).get("SS").toString();
					String V99 =  "".equals(in_list.get(a).get("VV")) || in_list.get(a).get("VV") == null ? "" : in_list.get(a).get("VV").toString();
					String insql = "insert into PKC_1_2_15 (COM_NAME,Z_HU,V1,V3,V5,V7,V9,V11,TYPE,COM_CODE,V98,V99) VALUES ("+
									"'"+com_name+"','"+z_hu+"','"+V1+"','"+V3+"','"+V5+"','"+V7+"','"+V9+"','"+V11+"','"+type+"','"+com_code+"','"+V98+"','"+V99+"')";
					this.getBySqlMapper.insert(insql);
				}
			}
		}
	}
	/**
	 * 贫困人口年龄分组
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("PKC_1_3_1.do")
	public  void  PKC_1_3_1 ( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		
		for ( int i =0 ; i < 4 ; i ++ ) {
			int v98 = 5-i;
			int ar = 6-i;
			int v10 = 2*v98;
			int xzqh = v10-1;
			for ( int j = 0 ; j < 2 ; j ++ ) {
				String str="";
				if ( j == 0 ){
					str = "0";
				}else {
					str = "3";
				}
				if(v98==5){
					String sql1="select  xzqh,num,aad004 pkzt,'"+j+"' CC,'"+v98+"' SS,'2016' VV,z.xz from (" +

								" SELECT NUM1,AAR006 xz,xzqh FROM ( select  COUNT(*) NUM1,AAR006 from NEIMENG0117_AC01  where AAR100= '1' " +
								"  and AAR040='2016' and AAR010='"+str+"' and (AAR010='"+str+"' and NVL(AAC016,AAR040) = '2016') or AAR010<>'"+str+"' " +
								"  GROUP BY AAR006  )AA left join (  select v9 xzqh,v10 from SYS_COM GROUP BY v9,v10)bb ON AA.AAR006=bb.v10 where " +
								" xzqh is not null " +
								" ) z  " +
								" LEFT JOIN" +
								" (" +
								"  SELECT COUNT(*) NUM,AAR006 xz,aad004  FROM ( select aar006 ,aad004 from NEIMENG0117_AD01 where AAR040='2016' and aad004 in ('01','02','03','04','05') )a GROUP BY AAR006,aad004" +
								" ) aa  ON aa.xz =z.xz";
					List<Map> in_list = this.getBySqlMapper.findRecords(sql1);
					for( int a = 0 ; a < in_list.size(); a++ ) {
						String V3 =  "01".equals(in_list.get(a).get("PKZT")) || "03".equals(in_list.get(a).get("PKZT")) || "04".equals(in_list.get(a).get("PKZT")) || "05".equals(in_list.get(a).get("PKZT"))  ?   in_list.get(a).get("NUM").toString() : "0";
						String V4 =  "02".equals(in_list.get(a).get("PKZT")) ? in_list.get(a).get("NUM").toString() : "0";
						String V1 =  in_list.get(a).get("XZQH").toString();
						String V2 = "".equals(in_list.get(a).get("NUM")) || in_list.get(a).get("NUM") == null ? "" : in_list.get(a).get("NUM").toString();
						String V9 =  "".equals(in_list.get(a).get("CC")) || in_list.get(a).get("CC") == null ? "" : in_list.get(a).get("CC").toString();
						String V10 =  "".equals(in_list.get(a).get("XZ")) || in_list.get(a).get("XZ") == null ? "" : in_list.get(a).get("XZ").toString();
						String V98 =  "".equals(in_list.get(a).get("SS")) || in_list.get(a).get("SS") == null ? "" : in_list.get(a).get("SS").toString();
						String V99 =  "".equals(in_list.get(a).get("VV")) || in_list.get(a).get("VV") == null ? "" : in_list.get(a).get("VV").toString();
						String insql = "insert into PKC_1_3_1 (V1,V2,V3,V4,com_pin,V10,V98,V99) VALUES ("+
										"'"+V1+"','"+V2+"','"+V3+"','"+V4+"','"+V9+"','"+V10+"','"+V98+"','"+V99+"')";
						this.getBySqlMapper.insert(insql);
					}
				}else {
					String sql="";
					try {
						sql="select  z.xzqh,num,NVL(num,'0')+NVL(num2,'0') zs,num2,'"+j+"' CC,'"+v98+"' SS,'2016' VV,z.xz from ("+
								" SELECT NUM1,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) NUM1,AAR00"+ar+" from NEIMENG0117_AC01  where AAR100= '1' "+
								"  and AAR040='2016' and AAR010='"+str+"' and (AAR010='"+str+"' and NVL(AAC016,AAR040) = '2016') or AAR010<>'"+str+"'"+
								"  GROUP BY AAR00"+ar+"  )AA left join (  select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where "+
								" xzqh is not null"+
								" ) z   "+
								" LEFT JOIN"+
								" (SELECT COUNT(*) NUM,AAR00"+ar+" xz  FROM ( select AAR00"+ar+"  from NEIMENG0117_AD01 where AAR040='2016' and aad004 in ('01','03','04','05'))a GROUP BY AAR00"+ar+" ) aa "+
								" ON z.xz=AA.xz"+
								" left JOIN (SELECT COUNT(*) NUM2,AAR00"+ar+" from NEIMENG0117_AD01 where  aad004 = '02' GROUP BY AAR00"+ar+" ) b ON b.AAR00"+ar+" = z.xz "+
								" where z.xzqh is not null";
								
								/*"select  xzqh,num,NVL(num,'0')+NVL(num2,'0') zs,num2,'"+j+"' CC,'"+v98+"' SS,'2016' VV,xz from ( "+
										   " SELECT COUNT(*) NUM,AAR00"+ar+" xz  FROM ("+
										   " select aar00"+ar+"  from NEIMENG0117_AD01 where AAR040='2016' and aad004 in ('01','03','04','05'))a GROUP BY AAR00"+ar+""+     //aad004有问题
										   " ) aa "+
										   " left JOIN (SELECT COUNT(*) NUM2,AAR00"+ar+" from NEIMENG0117_AD01 where  aad004 = '02' GROUP BY AAR00"+ar+" ) b ON b.AAR00"+ar+" = aa.xz"+
										   " left join (select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON aa.xz=bb.v"+v10+" where bb.xzqh is not null";*/
						List<Map> in_list = this.getBySqlMapper.findRecords(sql);
						for( int a = 0 ; a < in_list.size(); a++ ) {
							
							String V3 =  "".equals(in_list.get(a).get("NUM")) || in_list.get(a).get("NUM") == null ? "0" : in_list.get(a).get("NUM").toString();
							String V4 =  "".equals(in_list.get(a).get("NUM2")) || in_list.get(a).get("NUM2") == null ? "0" : in_list.get(a).get("NUM2").toString();
									
								
							
							String V1 =  in_list.get(a).get("XZQH").toString();
							String V2 = "".equals(in_list.get(a).get("ZS")) || in_list.get(a).get("NUM") == null ? "" : in_list.get(a).get("ZS").toString();
							String V9 =  "".equals(in_list.get(a).get("CC")) || in_list.get(a).get("CC") == null ? "" : in_list.get(a).get("CC").toString();
							String V10 =  "".equals(in_list.get(a).get("XZ")) || in_list.get(a).get("XZ") == null ? "" : in_list.get(a).get("XZ").toString();
							String V98 =  "".equals(in_list.get(a).get("SS")) || in_list.get(a).get("SS") == null ? "" : in_list.get(a).get("SS").toString();
							String V99 =  "".equals(in_list.get(a).get("VV")) || in_list.get(a).get("VV") == null ? "" : in_list.get(a).get("VV").toString();
							String insql = "insert into PKC_1_3_1 (V1,V2,V3,V4,com_pin,V10,V98,V99) VALUES ("+
											"'"+V1+"','"+V2+"','"+V3+"','"+V4+"','"+V9+"','"+V10+"','"+V98+"','"+V99+"')";
							this.getBySqlMapper.insert(insql);

						}
					} catch (Exception e) {
//						System.out.println(sql);
					}
					
					
				}
			}
			
			
		}
		
	}
	/**
	 * 未脱贫贫困人口数
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("PKC_1_3_2.do")
	public  void  PKC_1_3_2 ( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		for ( int i =0 ; i < 4 ; i ++ ) {
			int v98 = 5-i;
			int ar = 6-i;
			int v10 = 2*v98;
			int xzqh = v10-1;
			
			for ( int j = 0 ; j < 2 ; j ++ ) {
				String b_sql = "" ;
				if ( j == 0 ){
					b_sql = "AAR010='0'";
				}else {
					b_sql = "(AAR010='3' and NVL(AAC016,AAr040)='2016')";
				}
				
				String sqlString="select  BB.XZQH,HNUM,ZRS,PKH,PKRK,FPKH,FPKRK,'"+j+"' CC,'"+v98+"' SS,'2016' VV,BB.xz from (SELECT HNUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) HNUM,AAR00"+ar+" from NEIMENG0117_AC01  where AAR100= '1'  and AAR040='2016' and "+b_sql+"  GROUP BY AAR00"+ar+"  )AA "+
						" left join (  select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where  xzqh is not null)bb  "+
						" LEFT JOIN (  select  ZRS,AAR00"+ar+" xz,xzqh from ( SELECT COUNT(*) ZRS,AAR00"+ar+" FROM  ("+
						" SELECT * FROM (select AAC001 a1,AAB001 from NEIMENG0117_AB01 where AAR040='2016' and AAB015 IN ('1','4'))a "+
						" LEFT JOIN ( select AAC001,AAR002,AAR003,AAR004,AAR005,aar006 from NEIMENG0117_AC01  where AAR100= '1' and AAR040='2016' and "+b_sql+")b  on a.a1=B.AAC001 ) GROUP BY AAR00"+ar+")AA "+
						" left join (select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" ) ff ON FF.XZ=BB.XZ"+
						" LEFT JOIN ( select sum(PKH) PKH ,sum(PKRK) PKRK,AAR00"+ar+" from ( SELECT AAD007 PKH,AAD011 PKRK,T3.AAD001,T3.AAR00"+ar+" FROM (select AAD001 ,AAR00"+ar+" from NEIMENG0117_AD01 where AAR040='2016' and aad004 in ('01','03','04','05') )T3   "+
						" LEFT JOIN NEIMENG0117_AD06 T4 ON T3.AAD001=T4.AAD001)zz GROUP BY AAR00"+ar+") C ON C.AAR00"+ar+"= BB.XZ LEFT JOIN (　select sum(FPKH) FPKH ,sum(FPKRK) FPKRK,AAR00"+ar+" from ( SELECT AAD007 FPKH,AAD011 FPKRK,T3.AAD001,T3.AAR00"+ar+" FROM (select AAD001 ,AAR00"+ar+" from NEIMENG0117_AD01 where AAR040='2016' and aad004 in ('02') )T3  LEFT JOIN NEIMENG0117_AD06 T4 ON T3.AAD001=T4.AAD001)zz GROUP BY AAR00"+ar+" ) D ON D.AAR00"+ar+"= BB.XZ"; 
					List<Map> in_list = this.getBySqlMapper.findRecords(sqlString);
					for( int a = 0 ; a < in_list.size(); a++ ) {
					String VW1 =  in_list.get(a).get("XZQH").toString();
					String VW2 = "".equals(in_list.get(a).get("HNUM")) || in_list.get(a).get("HNUM") == null ? "" : in_list.get(a).get("HNUM").toString();
					String VW3 =  "".equals(in_list.get(a).get("ZRS")) || in_list.get(a).get("ZRS") == null ? "" : in_list.get(a).get("ZRS").toString();
					String VW4 =  "".equals(in_list.get(a).get("PKH")) || in_list.get(a).get("PKH") == null ? "" : in_list.get(a).get("PKH").toString();
					String VW5 =  "".equals(in_list.get(a).get("PKRK")) || in_list.get(a).get("PKRK") == null ? "" : in_list.get(a).get("PKRK").toString();
					String VW6 =  "".equals(in_list.get(a).get("FPKH")) || in_list.get(a).get("FPKH") == null ? "" : in_list.get(a).get("FPKH").toString();
					String VW7 =  "".equals(in_list.get(a).get("FPKRK")) || in_list.get(a).get("FPKRK") == null ? "" : in_list.get(a).get("FPKRK").toString();
					String COM_PIN =  "".equals(in_list.get(a).get("CC")) || in_list.get(a).get("CC") == null ? "" : in_list.get(a).get("CC").toString();
					String xz =  "".equals(in_list.get(a).get("XZ")) || in_list.get(a).get("XZ") == null ? "" : in_list.get(a).get("XZ").toString();
					String V98 =  "".equals(in_list.get(a).get("SS")) || in_list.get(a).get("SS") == null ? "" : in_list.get(a).get("SS").toString();
					String V99 =  "".equals(in_list.get(a).get("VV")) || in_list.get(a).get("VV") == null ? "" : in_list.get(a).get("VV").toString();
					String insql = "insert into PKC_1_3_2 (VW1,VW2,VW3,VW4,VW5,VW6,VW7,COM_PIN,V10,V98,V99) VALUES ("+
									"'"+VW1+"','"+VW2+"','"+VW3+"','"+VW4+"','"+VW5+"','"+VW6+"','"+VW7+"','"+COM_PIN+"','"+xz+"','"+V98+"','"+V99+"')";
					this.getBySqlMapper.insert(insql);
				}
			}
			
			
		}
	}
	
	/**
	 * 贫困发生率
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("PKC_1_3_3.do")
	public  void  PKC_1_3_3 ( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		for ( int i =0 ; i < 4 ; i ++ ) {
			int v98 = 5-i;
			int ar = 6-i;
			int v10 = 2*v98;
			int xzqh = v10-1;
			
			for ( int j = 0 ; j < 2 ; j ++ ) {
				String b_sql = "" ;
				if ( j == 0 ){
					b_sql = "AAR010='0'";
				}else {
					b_sql = "(AAR010='3' and NVL(AAC016,AAr040)='2016')";
				}
				String sql="select  BB.XZQH,HNUM,ZRS,PKH,PKRK,trunc(ZRS/PKRK, 2) BL,'"+j+"' CC,'"+v98+"' SS,'2016' VV,BB.xz from (SELECT HNUM,AAR00"+ar+" xz,xzqh FROM ( select  COUNT(*) HNUM,AAR00"+ar+" from NEIMENG0117_AC01  where AAR100= '1'  and AAR040='2016' and "+b_sql+"  GROUP BY AAR00"+ar+"  )AA "+
						" left join (  select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" where  xzqh is not null)bb  "+
						" LEFT JOIN (  select  ZRS,AAR00"+ar+" xz,xzqh from ( SELECT COUNT(*) ZRS,AAR00"+ar+" FROM  ("+
						" SELECT * FROM (select AAC001 a1,AAB001 from NEIMENG0117_AB01 where AAR040='2016' and AAB015 IN ('1','4'))a "+
						" LEFT JOIN ( select AAC001,AAR002,AAR003,AAR004,AAR005,aar006 from NEIMENG0117_AC01  where AAR100= '1' and AAR040='2016' and "+b_sql+")b  on a.a1=B.AAC001 ) GROUP BY AAR00"+ar+")AA "+
						" left join (select v"+xzqh+" xzqh,v"+v10+" from SYS_COM GROUP BY v"+xzqh+",v"+v10+")bb ON AA.AAR00"+ar+"=bb.v"+v10+" ) ff ON FF.XZ=BB.XZ"+
						" LEFT JOIN ( select sum(PKH) PKH ,sum(PKRK) PKRK,AAR00"+ar+" from ( SELECT AAD007 PKH,AAD011 PKRK,T3.AAD001,T3.AAR00"+ar+" FROM (select AAD001 ,AAR00"+ar+" from NEIMENG0117_AD01 where AAR040='2016' and aad004 in ('01','03','04','05') )T3   "+
						" LEFT JOIN NEIMENG0117_AD06 T4 ON T3.AAD001=T4.AAD001)zz GROUP BY AAR00"+ar+") C ON C.AAR00"+ar+"= BB.XZ LEFT JOIN (　select sum(FPKH) FPKH ,sum(FPKRK) FPKRK,AAR00"+ar+" from ( SELECT AAD007 FPKH,AAD011 FPKRK,T3.AAD001,T3.AAR00"+ar+" FROM (select AAD001 ,AAR00"+ar+" from NEIMENG0117_AD01 where AAR040='2016' and aad004 in ('02') )T3  LEFT JOIN NEIMENG0117_AD06 T4 ON T3.AAD001=T4.AAD001)zz GROUP BY AAR00"+ar+" ) D ON D.AAR00"+ar+"= BB.XZ";
				
					List<Map> in_list = this.getBySqlMapper.findRecords(sql);
					for( int a = 0 ; a < in_list.size(); a++ ) {
						
							
						
					
					String VF1 =  in_list.get(a).get("XZQH").toString();
					String VF2 = "".equals(in_list.get(a).get("HNUM")) || in_list.get(a).get("HNUM") == null ? "" : in_list.get(a).get("HNUM").toString();
					String VF3 =  "".equals(in_list.get(a).get("ZRS")) || in_list.get(a).get("ZRS") == null ? "" : in_list.get(a).get("ZRS").toString();
					String VF4 =  "".equals(in_list.get(a).get("PKH")) || in_list.get(a).get("PKH") == null ? "" : in_list.get(a).get("PKH").toString();
					String VF41 =  "".equals(in_list.get(a).get("BL")) || in_list.get(a).get("BL") == null ? "" : in_list.get(a).get("BL").toString();
					String VF5 = "".equals(in_list.get(a).get("PKRK")) || in_list.get(a).get("PKRK") == null ? "" : in_list.get(a).get("PKRK").toString();
					String COM_PIN =  "".equals(in_list.get(a).get("CC")) || in_list.get(a).get("CC") == null ? "" : in_list.get(a).get("CC").toString();
					String xz =  "".equals(in_list.get(a).get("XZ")) || in_list.get(a).get("XZ") == null ? "" : in_list.get(a).get("XZ").toString();
					String V98 =  "".equals(in_list.get(a).get("SS")) || in_list.get(a).get("SS") == null ? "" : in_list.get(a).get("SS").toString();
					String V99 =  "".equals(in_list.get(a).get("VV")) || in_list.get(a).get("VV") == null ? "" : in_list.get(a).get("VV").toString();
					String insql = "insert into PKC_1_3_3 (VF1,VF2,VF3,VF4,VF41,VF5,COM_PIN,V10,V98,V99) VALUES ("+
									"'"+VF1+"','"+VF2+"','"+VF3+"','"+VF4+"','"+VF41+"','"+VF5+"','"+COM_PIN+"','"+xz+"','"+V98+"','"+V99+"')";
					this.getBySqlMapper.insert(insql);
				}
			}
			
			
		}
		
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
