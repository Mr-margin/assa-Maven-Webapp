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
				String sql ="select XZQH,NUM,AA16,AA30,AA40,AA50,AA60,AA70 from(";
				sql +="select  NUM,AAR006 xz,xzqh from (";
				sql +="SELECT COUNT(*) NUM,AAR006 FROM (";
				sql +="SELECT * FROM (";
				sql +="select AAC001 a1,AAB001 from NM09_AB01 where AAR040='2015' and AAB015 IN ('1','4')";
				sql +=")a LEFT JOIN (";
				sql +="select AAC001,AAR002,AAR003,AAR004,AAR005,AAR006 from NM09_AC01  where AAR100= '1' and AAR040='2015' and AAR010 ='0'";
				sql +=")b on a.a1=b.AAC001";
				sql +=") GROUP BY AAR006";
				sql +=")AA left join (";
				sql +="select v9 xzqh,v10 from SYS_COM GROUP BY v9,v10";
				sql +=")bb ON AA.AAR006=bb.v10";
				sql +=")Q1 left join (";
				sql +="select t1.AAR006,count(t1.AAR006) as AA16 from (";
				sql +="select AAR006,b.AAB001 from(";
				sql +="select AAC001,AAR006 from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 = '0'   ";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='2015' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)>2000";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR006";
				sql +=")Q2 ON Q1.XZ=Q2.AAR006 ";
				sql +="left join (";
				sql +="select t1.AAR006,count(t1.AAR006) as AA30 from (";
				sql +="select AAR006,b.AAB001 from(";
				sql +="select AAC001,AAR006 from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 = '0'   ";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='2015' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)<2000 AND \"SUBSTR\"(AAB005, 0, 4)>1986";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR006";
				sql +=")Q3 ON Q1.XZ=Q3.AAR006 ";
				sql +="left join (";
				sql +="select t1.AAR006,count(t1.AAR006) as AA40 from (";
				sql +="select AAR006,b.AAB001 from(";
				sql +="select AAC001,AAR006 from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 = '0'   ";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='2015' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)<1986 AND \"SUBSTR\"(AAB005, 0, 4)>1976";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR006";
				sql +=")Q4 ON Q1.XZ=Q4.AAR006 ";
				sql +="left join (";
				sql +="select t1.AAR006,count(t1.AAR006) as AA50 from (";
				sql +="select AAR006,b.AAB001 from(";
				sql +="select AAC001,AAR006 from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 = '0' ";  
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='2015' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)<1976 AND \"SUBSTR\"(AAB005, 0, 4)>1966";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR006";
				sql +=")Q5 ON Q1.XZ=Q5.AAR006 ";
				sql +="left join (";
				sql +="select t1.AAR006,count(t1.AAR006) as AA60 from (";
				sql +="select AAR006,b.AAB001 from(";
				sql +="select AAC001,AAR006 from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 = '0'";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='2015' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)<1966 AND \"SUBSTR\"(AAB005, 0, 4)>1956";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR006";
				sql +=")Q6 ON Q1.XZ=Q6.AAR006 ";
				sql +="left join (";
				sql +="select t1.AAR006,count(t1.AAR006) as AA70 from (";
				sql +="select AAR006,b.AAB001 from(";
				sql +="select AAC001,AAR006 from NM09_AC01 where AAR100= '1' and AAR040='2015' and AAR010 = '0'   ";
				sql +=") a LEFT JOIN (";
				sql +="select AAB001,AAC001 from NM09_AB01 where AAR040='2015' and AAB015 IN ('1','4') AND \"SUBSTR\"(AAB005, 0, 4)<1956 ";
				sql +=") b on a.AAC001=b.AAC001 ";
				sql +=") t1 where t1.AAB001 is not null group by t1.AAR006";
				sql +=")Q7 ON Q1.XZ=Q7.AAR006 ";
			}


		}

	}

}
