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
public class PKC_1_2_Controller {
	@Autowired
	private GetBySqlMapper getBySqlMapper;
	
	
	private JSONArray getPaixu(JSONArray jsa,String name){
		if(name.equals("内蒙古自治区")){
			JSONArray val=new JSONArray();
			String str[] = {"呼和浩特市","包头市","呼伦贝尔市","兴安盟","通辽市","赤峰市","锡林郭勒盟","乌兰察布市","鄂尔多斯市","巴彦淖尔市","乌海市","阿拉善盟"};
			for(int k = 0;k<str.length;k++){
				for(int i = 0;i<jsa.size();i++){
					JSONObject jo = JSONObject.fromObject(jsa.get(i));
					if(jo.get("name").toString().equals(str[k])){
						val.add(jo);
					}
				}
			}
			return val;
		}else{
			return jsa;
		}
	}
	
	
	/**
	 * 地图显示：下辖行政区划所选类型贫困户的数量
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getpkc.do")
	public void getpkc(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name = request.getParameter("name");
		String src="";
		String q1 = request.getParameter("q1");
		String q2 = request.getParameter("q2");
		String q3 = request.getParameter("q3");
		String q4 = request.getParameter("q4");
		String q5 = request.getParameter("q5");
		String t1 = request.getParameter("t1");//脱贫标志-返贫
		String t2 = request.getParameter("t2");//脱贫标志-现贫
		if(name.equals("全部盟市")){
			name="内蒙古自治区";
		}
 		String sql = "select v1,v2,v3,v4 from PKC_1_3_1 c join (select * from SYS_COMPANY where COM_F_PKID=(SELECT PKID from SYS_COMPANY where COM_NAME='"+name+"') ) b on c.V1=b.COM_NAME";
		if(t1.equals("0")||t2.equals("1")){
			src += " WHERE  COM_PIN = '0' ";
		}else{
			src += " WHERE  COM_PIN = '1' ";
		}

		if(!name.equals("内蒙古自治区")){
			if(q1.equals("1")||q2.equals("1")||q3.equals("1")||q4.equals("1")||q5.equals("1")){
				src += "AND(";
				
				if(q1.equals("1")){
					src += "GJZDQX ='1' ";
				}
				if(q2.equals("1")){
					if(q1.equals("1")){
						src += " OR ";
					}
					src += " ZZQZDQX ='1'";
				}
				if(q3.equals("1")){
					if(q1.equals("1")||q2.equals("1")){
						src += " OR ";
					}
					src += " GMLQQX ='1'";
				}
				if(q4.equals("1")){
					if(q1.equals("1")||q2.equals("1")||q3.equals("1")){
						src += " OR ";
					}
					src += " MYQX ='1'";
				}
				if(q5.equals("1")){
					if(q1.equals("1")||q2.equals("1")||q3.equals("1")||q4.equals("1")){
						src += " OR ";
					}
					src += " BJQX ='1'";
				}
				src += ")";
			}
		}
		sql = sql+src; 
		List<Map> sql_list = this.getBySqlMapper.findRecords(sql);
		JSONObject val = new JSONObject();
		if(sql_list.size()>0){
			JSONArray jsa=new JSONArray();
			for(Map asmap:sql_list){
				val.put("name",asmap.get("V1")==null?"":asmap.get("V1"));
				val.put("b1",asmap.get("V2")==null?"0":asmap.get("V2"));
				val.put("b2",asmap.get("V3")==null?"0":asmap.get("V3"));
				val.put("b3",asmap.get("V4")==null?"0":asmap.get("V4"));
				jsa.add(val);
			}
			response.getWriter().write(getPaixu(jsa,name).toString());
		}else{
			response.getWriter().write("0");
		}
	}
	@RequestMapping("getwtp.do")
	public void getwtp(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name = request.getParameter("name");
		String src="";
		String q1 = request.getParameter("q1");
		String q2 = request.getParameter("q2");
		String q3 = request.getParameter("q3");
		String q4 = request.getParameter("q4");
		String q5 = request.getParameter("q5");
		String t1 = request.getParameter("t1");
		String t2 = request.getParameter("t2");
		if(name.equals("全部盟市")){
			name="内蒙古自治区";
		}
		String sql = "select vw1,vw2,vw3,vw4,vw5,vw6,vw7 from PKC_1_3_2 c join (select * from SYS_COMPANY where COM_F_PKID=(SELECT PKID from SYS_COMPANY where COM_NAME='"+name+"') ) b on c.VW1=b.COM_NAME";
		
		if(t1.equals("0")||t2.equals("1")){
			src += " WHERE  COM_PIN = '0' ";
		}else{
			src += " WHERE  COM_PIN = '1' ";
		}

		if(!name.equals("内蒙古自治区")){
			if(q1.equals("1")||q2.equals("1")||q3.equals("1")||q4.equals("1")||q5.equals("1")){
				src += "AND(";
				
				if(q1.equals("1")){
					src += "GJZDQX ='1' ";
				}
				if(q2.equals("1")){
					if(q1.equals("1")){
						src += " OR ";
					}
					src += " ZZQZDQX ='1'";
				}
				if(q3.equals("1")){
					if(q1.equals("1")||q2.equals("1")){
						src += " OR ";
					}
					src += " GMLQQX ='1'";
				}
				if(q4.equals("1")){
					if(q1.equals("1")||q2.equals("1")||q3.equals("1")){
						src += " OR ";
					}
					src += " MYQX ='1'";
				}
				if(q5.equals("1")){
					if(q1.equals("1")||q2.equals("1")||q3.equals("1")||q4.equals("1")){
						src += " OR ";
					}
					src += " BJQX ='1'";
				}
				src += ")";
			}
		}
		sql = sql+src; 
		
		List<Map> sql_list = this.getBySqlMapper.findRecords(sql);
		JSONObject val = new JSONObject();
		if(sql_list.size()>0){
			JSONArray jsa=new JSONArray();
			for(Map asmap:sql_list){
				val.put("name",asmap.get("VW1")==null?"":asmap.get("VW1"));
				val.put("h1",asmap.get("VW2")==null?"0":asmap.get("VW2"));
				val.put("h2",asmap.get("VW4")==null?"0":asmap.get("VW4"));
				val.put("h3",asmap.get("VW6")==null?"0":asmap.get("VW6"));
				val.put("r1",asmap.get("VW3")==null?"0":asmap.get("VW3"));
				val.put("r2",asmap.get("VW5")==null?"0":asmap.get("VW5"));
				val.put("r3",asmap.get("VW7")==null?"0":asmap.get("VW7"));
				jsa.add(val);
			}
			response.getWriter().write(jsa.toString());
		}else{
			response.getWriter().write("0");
		}
	}
	@RequestMapping("getfsl.do")
	public void getfsl(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name = request.getParameter("name");
		String src="";
		String q1 = request.getParameter("q1");
		String q2 = request.getParameter("q2");
		String q3 = request.getParameter("q3");
		String q4 = request.getParameter("q4");
		String q5 = request.getParameter("q5");
		String t1 = request.getParameter("t1");
		String t2 = request.getParameter("t2");
		String order = request.getParameter("order");
		if(name.equals("全部盟市")){
			name="内蒙古自治区";
		}
		String sql = "select vf1,vf2,vf3,vf4,NVL(vf41, '0') vf41,vf5 from PKC_1_3_3 c join (select * from SYS_COMPANY where COM_F_PKID=(SELECT PKID from SYS_COMPANY where COM_NAME='"+name+"') ) b on c.VF1=b.COM_NAME ";
		if(t1.equals("0")||t2.equals("1")){
			src += " WHERE  COM_PIN = '0' ";
		}else{
			src += " WHERE  COM_PIN = '1' ";
		}
		
		if(!name.equals("内蒙古自治区")){
			if(q1.equals("1")||q2.equals("1")||q3.equals("1")||q4.equals("1")||q5.equals("1")){
				src += "AND(";
				
				if(q1.equals("1")){
					src += "GJZDQX ='1' ";
				}
				if(q2.equals("1")){
					if(q1.equals("1")){
						src += " OR ";
					}
					src += " ZZQZDQX ='1'";
				}
				if(q3.equals("1")){
					if(q1.equals("1")||q2.equals("1")){
						src += " OR ";
					}
					src += " GMLQQX ='1'";
				}
				if(q4.equals("1")){
					if(q1.equals("1")||q2.equals("1")||q3.equals("1")){
						src += " OR ";
					}
					src += " MYQX ='1'";
				}
				if(q5.equals("1")){
					if(q1.equals("1")||q2.equals("1")||q3.equals("1")||q4.equals("1")){
						src += " OR ";
					}
					src += " BJQX ='1'";
				}
				src += ")";
			}
		}
		sql = sql+src; 
		
		if(order==null||order.trim().equals("")){
			sql+=" order by c.PKID";
		}else{
			sql+=" order by to_number("+order+") DESC";
		}
		List<Map> sql_list = this.getBySqlMapper.findRecords(sql);
		JSONObject val = new JSONObject();
		if(sql_list.size()>0){
			JSONArray jsa=new JSONArray();
			for(Map asmap:sql_list){
				val.put("name",asmap.get("VF1")==null?"":asmap.get("VF1"));
				val.put("f1",asmap.get("VF2")==null?"0":asmap.get("VF2"));
				val.put("f2",asmap.get("VF3")==null?"0":asmap.get("VF3"));
				val.put("f3",asmap.get("VF4")==null?"0":asmap.get("VF4"));
				val.put("f4",asmap.get("VF41")==null?"0":asmap.get("VF41"));
				val.put("f5",asmap.get("VF5")==null?"0":asmap.get("VF5"));
				jsa.add(val);
			}
			response.getWriter().write(jsa.toString());
		}else{
			response.getWriter().write("0");;
		}
	}
	@RequestMapping("getFccs.do")
	public void getFccs(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name = request.getParameter("name");
		String q1 = request.getParameter("q1");
		String q2 = request.getParameter("q2");
		String q3 = request.getParameter("q3");
		String q4 = request.getParameter("q4");
		String q5 = request.getParameter("q5");
		String t1 = request.getParameter("t1");
		String t2 = request.getParameter("t2");
		
		String desc=request.getParameter("desc");
		String src="";
		if(name.equals("全部盟市")){
			name="内蒙古自治区";
		}
		String sql = "select fc1,fc2,fc3,fc4,fc4,fc5,fc6,fc7,fc8 from PKC_2_1_1 c join (select * from SYS_COMPANY where COM_F_PKID=(SELECT PKID from SYS_COMPANY where COM_NAME='"+name+"') ) b on c.FC1=b.COM_NAME";
		if(desc==null){
			desc="";
		}
		if(t1.equals("0")||t2.equals("1")){
			src += " WHERE  COM_PIN = '0' ";
		}else{
			src += " WHERE  COM_PIN = '1' ";
		}

		if(!name.equals("内蒙古自治区")){
			if(q1.equals("1")||q2.equals("1")||q3.equals("1")||q4.equals("1")||q5.equals("1")){
				src += "AND(";
				
				if(q1.equals("1")){
					src += "GJZDQX ='1' ";
				}
				if(q2.equals("1")){
					if(q1.equals("1")){
						src += " OR ";
					}
					src += " ZZQZDQX ='1'";
				}
				if(q3.equals("1")){
					if(q1.equals("1")||q2.equals("1")){
						src += " OR ";
					}
					src += " GMLQQX ='1'";
				}
				if(q4.equals("1")){
					if(q1.equals("1")||q2.equals("1")||q3.equals("1")){
						src += " OR ";
					}
					src += " MYQX ='1'";
				}
				if(q5.equals("1")){
					if(q1.equals("1")||q2.equals("1")||q3.equals("1")||q4.equals("1")){
						src += " OR ";
					}
					src += " BJQX ='1'";
				}
				src += ")";
			}
		}
		sql = sql+src; 
		
		List<Map> sql_list = this.getBySqlMapper.findRecords(sql);
		JSONObject val = new JSONObject();
		if(sql_list.size()>0){
			JSONArray jsa=new JSONArray();
			for(Map asmap:sql_list){
				val.put("name",asmap.get("FC1")==null?"":asmap.get("FC1"));
				val.put("fc1",asmap.get("FC2")==null?"0":asmap.get("FC2"));
				val.put("fc2",asmap.get("FC3")==null?"0":asmap.get("FC3"));
				val.put("fc3",asmap.get("FC4")==null?"0":asmap.get("FC4"));
				val.put("fc4",asmap.get("FC5")==null?"0":asmap.get("FC5"));
				val.put("fc5",asmap.get("FC6")==null?"0":asmap.get("FC6"));
				val.put("fc6",asmap.get("FC7")==null?"0":asmap.get("FC7"));
				val.put("fc7",asmap.get("FC8")==null?"0":asmap.get("FC8"));
				jsa.add(val);
			}
			response.getWriter().write(getPaixu(jsa,name).toString());
		}else{
			response.getWriter().write("0");
		}
	}
	@RequestMapping("getFcss.do")
	public void getFcss(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name = "内蒙古自治区";
		
		String q1 = request.getParameter("q1");
		String q2 = request.getParameter("q2");
		String q3 = request.getParameter("q3");
		String q4 = request.getParameter("q4");
		String q5 = request.getParameter("q5");
		
		String desc=request.getParameter("desc");
		String tiaojian="";
		
		if(desc==null){
			desc="";
		}
		
		String sql = "select fc1,fc2,fc3,fc4,fc4,fc5,fc6,fc7,fc8 from PKC_2_1_1 c join (select * from SYS_COMPANY where COM_F_PKID=(SELECT PKID from SYS_COMPANY where COM_NAME='内蒙古自治区') ) b on c.FC1=b.COM_NAME "+tiaojian+" order by c.PKID ";
		List<Map> sql_list = this.getBySqlMapper.findRecords(sql);
		JSONObject val = new JSONObject();
		if(sql_list.size()>0){
			JSONArray jsa=new JSONArray();
			for(Map asmap:sql_list){
				val.put("name",asmap.get("FC1")==null?"":asmap.get("FC1"));
				val.put("fc1",asmap.get("FC2")==null?"0":asmap.get("FC2"));
				val.put("fc2",asmap.get("FC3")==null?"0":asmap.get("FC3"));
				val.put("fc3",asmap.get("FC4")==null?"0":asmap.get("FC4"));
				val.put("fc4",asmap.get("FC5")==null?"0":asmap.get("FC5"));
				val.put("fc5",asmap.get("FC6")==null?"0":asmap.get("FC6"));
				val.put("fc6",asmap.get("FC7")==null?"0":asmap.get("FC7"));
				val.put("fc7",asmap.get("FC8")==null?"0":asmap.get("FC8"));
				jsa.add(val);
			}
			response.getWriter().write(getPaixu(jsa,name).toString());
		}else{
			response.getWriter().print("0");
		}
	}
	/**
	 * 十个全覆盖
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getSdcsdh.do")
	public void getSdcsdh(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name = request.getParameter("name");
		String q1 = request.getParameter("q1");
		String q2 = request.getParameter("q2");
		String q3 = request.getParameter("q3");
		String q4 = request.getParameter("q4");
		String q5 = request.getParameter("q5");
		String tiaojian="";
		if(name.equals("全部盟市")){
			name="内蒙古自治区";
		}
		String order =request.getParameter("order");
		String src ="VS1";
		if(!name.equals("内蒙古自治区")){
			if(!q1.equals("0")||!q2.equals("0")||!q3.equals("0")||!q4.equals("0")||!q5.equals("0")){
				tiaojian="where (";
				if(q1.equals("1")){
					tiaojian+=" GJZDQX ='"+q1+"' or";
				}
				if(q2.equals("1")){
					tiaojian+=" ZZQZDQX ='"+q2+"' or";
				}
				if(q3.equals("1")){
					tiaojian+=" GMLQQX ='"+q3+"' or";
				}
				if(q4.equals("1")){
					tiaojian+=" MYQX ='"+q4+"' or";
				}
				if(q5.equals("1")){
					tiaojian+=" BJQX ='"+q5+"' or";
				}
				tiaojian=tiaojian.substring(0, tiaojian.length()-3)+")";
			}
			src="VS2";
		}
		
		String sql = "select c."+src+", sum(c.VS3) as VS3,sum(c.VS4) as VS4 from PKC_2_3_1 c join (select * from SYS_COMPANY where COM_F_PKID=(SELECT PKID from SYS_COMPANY where COM_NAME='"+name+"') ) b on c."+src+"=b.COM_NAME "+tiaojian+"  GROUP BY c."+src+"";
//		if(order==null||order.trim().equals("")){
		
//			sql+=" order by c.PKID";
//		}else{
//			sql+=" order by to_number(c."+order+") DESC";
//		}
		List<Map> sql_list = this.getBySqlMapper.findRecords(sql);
		JSONObject val = new JSONObject();
		if(sql_list.size()>0){
			JSONArray jsa=new JSONArray();
			for(Map asmap:sql_list){
				val.put("name",asmap.get(src)==null?"":asmap.get(src));
				val.put("vs2",asmap.get("VS3")==null?"0":asmap.get("VS3"));//贫困村数量（个）
				val.put("vs3",asmap.get("VS4")==null?"0":asmap.get("VS4"));//贫困村总户数（户）
				
				
				
				
				jsa.add(val);
			}
			response.getWriter().write(getPaixu(jsa,name).toString());
		}else{
			response.getWriter().write("0");
		}
	}
	@RequestMapping("getBfzrr.do")
	public void getBfzrr(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name = request.getParameter("name");
		String q1 = request.getParameter("q1");
		String q2 = request.getParameter("q2");
		String q3 = request.getParameter("q3");
		String q4 = request.getParameter("q4");
		String q5 = request.getParameter("q5");
		String t1 = request.getParameter("t1");
		String t2 = request.getParameter("t2");
		String src="";
		String desc=request.getParameter("desc");
		if(name.equals("全部盟市")){
			name="内蒙古自治区";
		}
		String sql = "select c.VB1,c.VB2,c.VB3,c.VB4,a1.vh1,a1.vh2,a1.vh3,a1.vh4,a1.vh5,a1.vh6,a1.vh7,a1.vh8,a1.vh9,a1.vh10, round(VB3/(case when VB2='0' then '1' else VB2 end),4)*100 AS b5 from PKC_3_1_1 c join" +
					" (select * from SYS_COMPANY where COM_F_PKID=(SELECT PKID from SYS_COMPANY where COM_NAME='"+name+"') ) b on c.VB1=b.COM_NAME "+
					" LEFT JOIN PKC_3_1_2 a1 ON  a1.vh0=c.vb1 ";
		
		
		if(t1.equals("0")||t2.equals("1")){
			src += " WHERE  c.COM_PIN = '0' ";
		}else{
			src += " WHERE  c.COM_PIN = '1' ";
		}

		if(!name.equals("内蒙古自治区")){
			if(q1.equals("1")||q2.equals("1")||q3.equals("1")||q4.equals("1")||q5.equals("1")){
				src += "AND(";
				
				if(q1.equals("1")){
					src += "GJZDQX ='1' ";
				}
				if(q2.equals("1")){
					if(q1.equals("1")){
						src += " OR ";
					}
					src += " ZZQZDQX ='1'";
				}
				if(q3.equals("1")){
					if(q1.equals("1")||q2.equals("1")){
						src += " OR ";
					}
					src += " GMLQQX ='1'";
				}
				if(q4.equals("1")){
					if(q1.equals("1")||q2.equals("1")||q3.equals("1")){
						src += " OR ";
					}
					src += " MYQX ='1'";
				}
				if(q5.equals("1")){
					if(q1.equals("1")||q2.equals("1")||q3.equals("1")||q4.equals("1")){
						src += " OR ";
					}
					src += " BJQX ='1'";
				}
				src += ")";
			}
		}
		sql = sql+src; 
		List<Map> sql_list = this.getBySqlMapper.findRecords(sql);
		JSONObject val = new JSONObject();
		if(sql_list.size()>0){
			JSONArray jsa=new JSONArray();
			for(Map asmap:sql_list){
				val.put("name",asmap.get("VB1")==null?"":asmap.get("VB1"));
				val.put("vb1",asmap.get("VB2")==null?"0":asmap.get("VB2"));
				val.put("vb2",asmap.get("VB3")==null?"0":asmap.get("VB3"));
				val.put("vb3",asmap.get("VB4")==null?"0":asmap.get("VB4"));
				val.put("vh1",asmap.get("VH1")==null?"0":asmap.get("VH1"));//现有贫困户数
				val.put("vh2",asmap.get("VH1")==null?"0":asmap.get("VH2"));//落实帮扶责任总人数
				val.put("vh3",asmap.get("VH1")==null?"0":asmap.get("VH3"));//省级领导
				val.put("vh4",asmap.get("VH1")==null?"0":asmap.get("VH4"));//帮扶户数
				val.put("vh5",asmap.get("VH1")==null?"0":asmap.get("VH5"));//市级领导
				val.put("vh6",asmap.get("VH1")==null?"0":asmap.get("VH6"));//帮扶户数
				val.put("vh7",asmap.get("VH1")==null?"0":asmap.get("VH7"));//县级领导
				val.put("vh8",asmap.get("VH1")==null?"0":asmap.get("VH8"));//帮扶户数
				val.put("vh9",asmap.get("VH1")==null?"0":asmap.get("VH9"));//县级以下干部
				val.put("vh10",asmap.get("VH1")==null?"0":asmap.get("VH10"));//帮扶户数
				val.put("b5",asmap.get("B5")==null?"0":asmap.get("B5"));
				
				
				jsa.add(val);
			}
			response.getWriter().write(getPaixu(jsa,name).toString());
		}else{
			response.getWriter().print("0");
		}
	}
	@RequestMapping("getWfgz.do")
	public void getWfgz(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name =request.getParameter("name");
		String q1 = request.getParameter("q1");
		String q2 = request.getParameter("q2");
		String q3 = request.getParameter("q3");
		String q4 = request.getParameter("q4");
		String q5 = request.getParameter("q5");
		String t1 = request.getParameter("t1");
		String t2 = request.getParameter("t2");
		String src="";
		String order =request.getParameter("order");
		if(name.equals("全部盟市")){
			name="内蒙古自治区";
		}
		String sql = "select c.*,decode(c.VG51,0,0,round(c.VG51/c.VG5,4)*100) AS b5,decode(c.VG71,0,0,round(c.VG71/c.VG7,4)*100) AS b7,decode(c.VG81,0,0,round(c.VG81/c.VG8,4)*100) AS b8,decode(c.VG91,0,0,round(c.VG91/c.VG9,4)*100) AS b9,decode(c.VG141,0,0,round(c.VG141/c.VG14,4)*100) AS b14,decode(c.VG151,0,0,round(c.VG151/c.VG15,4)*100) AS b15,decode(c.VG161,0,0,round(c.VG161/c.VG16,4)*100) AS b16 from PKC_2_2_1 c join (select * from SYS_COMPANY where COM_F_PKID=(SELECT PKID from SYS_COMPANY where COM_NAME='"+name+"') ) b on c.VG1=b.COM_NAME";
		if(t1.equals("0")||t2.equals("1")){
			src += " WHERE  COM_PIN = '0' ";
		}else{
			src += " WHERE  COM_PIN = '1' ";
		}

		if(!name.equals("内蒙古自治区")){
			if(q1.equals("1")||q2.equals("1")||q3.equals("1")||q4.equals("1")||q5.equals("1")){
				src += "AND(";
				
				if(q1.equals("1")){
					src += "GJZDQX ='1' ";
				}
				if(q2.equals("1")){
					if(q1.equals("1")){
						src += " OR ";
					}
					src += " ZZQZDQX ='1'";
				}
				if(q3.equals("1")){
					if(q1.equals("1")||q2.equals("1")){
						src += " OR ";
					}
					src += " GMLQQX ='1'";
				}
				if(q4.equals("1")){
					if(q1.equals("1")||q2.equals("1")||q3.equals("1")){
						src += " OR ";
					}
					src += " MYQX ='1'";
				}
				if(q5.equals("1")){
					if(q1.equals("1")||q2.equals("1")||q3.equals("1")||q4.equals("1")){
						src += " OR ";
					}
					src += " BJQX ='1'";
				}
				src += ")";
			}
		}
		sql = sql+src; 
		
		
		if(order==null||order.trim().equals("")){
			sql+=" order by c.PKID";
		}else{
			sql+=" order by to_number(c."+order+") DESC";
		}
		List<Map> sql_list = this.getBySqlMapper.findRecords(sql);
		JSONObject val = new JSONObject();
		if(sql_list.size()>0){
			JSONArray jsa=new JSONArray();
			for(Map asmap:sql_list){
				val.put("name",asmap.get("VG1")==null?"":asmap.get("VG1"));
				val.put("vg2",asmap.get("VG2")==null?"0":asmap.get("VG2"));//贫困村数量（个）
				val.put("vg3",asmap.get("VG3")==null?"0":asmap.get("VG3"));//贫困村总户数（户）
				val.put("vg4",asmap.get("VG4")==null?"0":asmap.get("VG4"));//贫困户总户数
				val.put("vg5",asmap.get("VG5")==null?"0":asmap.get("VG5"));//危房改造（户）小计
				val.put("vg51",asmap.get("VG51")==null?"0":asmap.get("VG51"));// 危房改造（户） 其中贫困户
				val.put("vg6",asmap.get("VG6")==null?"0":asmap.get("VG6")); //街巷硬化（米）
				val.put("vg7",asmap.get("VG7")==null?"0":asmap.get("VG7")); //解决饮水安全（户） 小计
				val.put("vg71",asmap.get("VG71")==null?"0":asmap.get("VG71")); //解决饮水安全（户）  其中贫困户
				val.put("vg8",asmap.get("VG8")==null?"0":asmap.get("VG8")); //解决通电和农网改造（户） 小计
				val.put("vg81",asmap.get("VG81")==null?"0":asmap.get("VG81"));//解决通电和农网改造（户） 其中贫困户
				val.put("vg9",asmap.get("VG9")==null?"0":asmap.get("VG9")); //解决通广播电视和通讯（户） 小计
				val.put("vg91",asmap.get("VG91")==null?"0":asmap.get("VG91")); //解决通广播电视和通讯（户） 其中贫困户
				val.put("vg10",asmap.get("VG10")==null?"0":asmap.get("VG10")); //校舍建设或改造（平方米）
				val.put("vg11",asmap.get("VG11")==null?"0":asmap.get("VG11"));//建标准卫生室（个）
				val.put("vg12",asmap.get("VG12")==null?"0":asmap.get("VG12"));//建文化活动室（个）
				val.put("vg13",asmap.get("VG13")==null?"0":asmap.get("VG13"));//建便民超市（个）
				val.put("vg14",asmap.get("VG14")==null?"0":asmap.get("VG14")); //新增养老 小计
				val.put("vg141",asmap.get("VG141")==null?"0":asmap.get("VG141"));//新增养老 其中贫困户
				val.put("vg15",asmap.get("VG15")==null?"0":asmap.get("VG15")); //新增医保 小计
				val.put("vg151",asmap.get("VG151")==null?"0":asmap.get("VG151"));//新增医保 其中贫困户
				val.put("vg16",asmap.get("VG16")==null?"0":asmap.get("VG16")); //新增低保 小计
				val.put("vg161",asmap.get("VG161")==null?"0":asmap.get("VG161"));//新增低保 其中贫困户
				val.put("b5",asmap.get("B5")==null?"0":asmap.get("B5"));//新增低保 其中贫困户
				val.put("b7",asmap.get("B7")==null?"0":asmap.get("B7"));//新增低保 其中贫困户
				val.put("b8",asmap.get("B8")==null?"0":asmap.get("B8"));//新增低保 其中贫困户
				val.put("b9",asmap.get("B9")==null?"0":asmap.get("B9"));//新增低保 其中贫困户
				val.put("b14",asmap.get("B14")==null?"0":asmap.get("B14"));//新增低保 其中贫困户
				val.put("b15",asmap.get("B15")==null?"0":asmap.get("B15"));//新增低保 其中贫困户
				val.put("b16",asmap.get("B16")==null?"0":asmap.get("B16"));//新增低保 其中贫困户
				
				
				
				
				jsa.add(val);
			}
			response.getWriter().write(getPaixu(jsa,name).toString());
		}else{
			response.getWriter().print("0");
		}
	}
	/**
	 * 地图显示：下辖行政区划所选类型贫困户的数量
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getBfdx_home.do")
	public void getBfdx_home(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String dqname = request.getParameter("dqname");
		String dqcode = request.getParameter("dqcode");
		String sql = "";
		String sql1 = "";
		int ar = 0;
		int v98 = 0;
		if(dqname.equals("V2")){
			
			sql = "SELECT NUM,num1,AAR002 xz FROM ("+ 
				" select  COUNT(*) NUM,aar002 from NEIMENG0117_AC01  where AAR100= '1' and aar002='150000000000' and AAR040='2016' and  AAR010 in ('0','3') and aar002 is not null GROUP BY AAR002   "+
					" )AA"+
					" LEFT JOIN ("+

					" SELECT SUM(V2) NUM1,'150000000000' v10 FROM PKC_1_1_0 WHERE V98='2' "+
					" )B  ON AA.AAR002=B.V10";
			sql1 = "select count（*）num from (select AAC001 from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and AAR002='150000000000'"+
				" ) a left join (select * from AC08)  b on a.AAC001=b.AAC001 where SUBSTR(b.AAR020, 0, 4) <='2016' AND SUBSTR(b.AAR021, 0, 4) >='2016'";
		}else {
			ar = Integer.parseInt(dqname.substring(1,2));
			if(ar == 4){
				v98 =2 ;
				ar = 3;
			}else{
				v98 = 3;
				ar = 4;
			}
			
			sql = "SELECT NUM,num1,AAR00"+ar+" xz FROM ( "+
						" select  COUNT(*) NUM,AAR00"+ar+" from NEIMENG0117_AC01  where AAR100= '1' and AAR00"+ar+"='"+dqcode+"' and AAR040='2016' and  AAR010 in ('0','3') and AAR00"+ar+" is not null GROUP BY AAR00"+ar+"    "+
						" )AA "+
						" LEFT JOIN ( "+

						" SELECT V10,SUM(V2) NUM1 FROM PKC_1_1_0 WHERE V98='"+v98+"' GROUP BY V10 "+
						" )B ON AA.AAR00"+ar+"=B.V10 ";
			sql1="select count（*）num from (select AAC001 from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' and AAR00"+ar+"='"+dqcode+"'"+
				") a left join (select * from AC08)  b on a.AAC001=b.AAC001 where SUBSTR(b.AAR020, 0, 4) <='2016' AND SUBSTR(b.AAR021, 0, 4) >='2016'";
		}
		
		/* sql="SELECT NUM,num1,AAR00"+ar+" xz FROM ( "+
				" select  COUNT(*) NUM,AAR00"+ar+" from NEIMENG0117_AC01  where AAR100= '1' and AAR00"+ar+"='"+dqcode+"' and AAR040='2016' and  AAR010 in ('0','3') and AAR00"+ar+" is not null GROUP BY AAR00"+ar+"    "+
				" )AA "+
				" LEFT JOIN ( "+

				" SELECT V10,SUM(V2) NUM1 FROM PKC_1_1_0 WHERE V98='"+v98+"' GROUP BY V10 "+
				" )B ON AA.AAR00"+ar+"=B.V10 ";
				
				
				
				"SELECT NUM,NUM1,AAR00"+ar+" xz FROM ( "+
					" select  COUNT(*) NUM,AAR00"+ar+" from NEIMENG0117_AC01  where AAR100= '1' and AAR00"+ar+"='"+dqcode+"' and AAR040='2016' and  AAR010 in ('0','3') and AAR00"+ar+" is not null GROUP BY AAR00"+ar+"  "+  
					" )AA  "+

					" LEFT JOIN (  "+
					" select  NUM1,AAR00"+ar+" xz from ( "+
					" SELECT COUNT(*) NUM1,AAR00"+ar+" FROM ( "+
					" SELECT * FROM ( "+
					" select AAC001 a1,AAB001 from NEIMENG0117_AB01 where AAR040='2016' and AAB015 IN ('1','4') "+
					" )a LEFT JOIN (  "+
					" select AAC001,AAR002,AAR003,AAR004,AAR005,AAR006 from NEIMENG0117_AC01  where AAR100= '1' and AAR040='2016' and AAR010 in ('0','3'))b on a.a1=B.AAC001  "+
					" ) GROUP BY AAR00"+ar+")AA  "+
					" ) ff ON FF.XZ=aa.AAR00"+ar+" ";*/
				
				
				
				
		List<Map> sql_list = this.getBySqlMapper.findRecords(sql);
		List<Map> sql_list1 = this.getBySqlMapper.findRecords(sql1);
		JSONObject object = new JSONObject();
		
		object.put("bfr",sql_list1.get(0).get("NUM") == null?"":sql_list1.get(0).get("NUM"));
		if( sql_list.size() > 0){
			JSONArray jsa = new JSONArray();
			for(Map asmap:sql_list){
				object.put("pkh", asmap.get("NUM")==null?"":asmap.get("NUM"));
				object.put("pkr", asmap.get("NUM1")==null?"":asmap.get("NUM1"));
				jsa.add(object);
			}
			response.getWriter().write(jsa.toString());
		}
	}
}
