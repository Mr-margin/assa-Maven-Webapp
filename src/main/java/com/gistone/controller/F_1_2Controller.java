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
public class F_1_2Controller{
	
	@Autowired
	private GetBySqlMapper getBySqlMapper;
	private JSONArray getPaixu(JSONArray jsa,String names){
		String name="com_name";
		if(names.equals("内蒙古自治区")){
			JSONObject m = JSONObject.fromObject(jsa.get(0));
			if(m.get("com_name") == null){
				name="name";
			}
			JSONArray val=new JSONArray();
			String str[] = {"呼和浩特市","包头市","呼伦贝尔市","兴安盟","通辽市","赤峰市","锡林郭勒盟","乌兰察布市","鄂尔多斯市","巴彦淖尔市","乌海市","阿拉善盟"};
			for(int k = 0;k<str.length;k++){
				for(int i = 0;i<jsa.size();i++){
					JSONObject jo = JSONObject.fromObject(jsa.get(i));
					if(jo.get(name).toString().equals(str[k])){
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
	 * 表格显示：贫困户基本信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getBfdxHu_1.do")
	public void getBfdxHu_1(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name = request.getParameter("name");//行政区划
		String year = request.getParameter("year");//统计年度
		String q1 = request.getParameter("q1");//旗县分类-国家重点旗县
		String q2 = request.getParameter("q2");//旗县分类-自治区重点旗县
		String q3 = request.getParameter("q3");//旗县分类-革命老区旗县
		String q4 = request.getParameter("q4");//旗县分类-牧业旗县
		String q5 = request.getParameter("q5");//旗县分类-边境旗县
		String t1 = request.getParameter("t1");//脱贫标志-返贫
		String t2 = request.getParameter("t2");//脱贫标志-现贫
		String src = "";
		if(name.equals("全部盟市")){
			name="内蒙古自治区";
		}
		String desc = request.getParameter("desc");
		String sql="SELECT a1.*,GJZDQX,ZZQZDQX,GMLQQX,MYQX,BJQX FROM("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
				+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='"+name+"'"
				+ ")) a2 LEFT JOIN PKC_1_2_1 a1 ON a1.COM_CODE=A2.COM_CODE ";
		if(t1.equals("0")&&t2.equals("1")){
			src += " WHERE  TYPE = '0' ";
		}else{
			src += " WHERE  TYPE = '1' ";
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
		JSONArray json = new JSONArray();
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		if (list.size() > 0) {
			for ( int i = 0 ; i < list.size() ; i ++){
				JSONObject obj = new JSONObject();
				obj.put("com_name", "".equals(list.get(i).get("COM_NAME")) || list.get(i).get("COM_NAME") == null ? "" : list.get(i).get("COM_NAME").toString());
				obj.put("z_hu", "".equals(list.get(i).get("Z_HU")) || list.get(i).get("Z_HU") == null ? "" : list.get(i).get("Z_HU").toString());
				obj.put("z_ren", "".equals(list.get(i).get("Z_REN")) || list.get(i).get("Z_REN") == null ? "" : list.get(i).get("Z_REN").toString());
				obj.put("v1", "".equals(list.get(i).get("V1")) || list.get(i).get("V1") == null ? "0" : list.get(i).get("V1").toString());
				obj.put("v5", "".equals(list.get(i).get("V5")) || list.get(i).get("V5") == null ? "0" : list.get(i).get("V5").toString());
				obj.put("v9", "".equals(list.get(i).get("V9")) || list.get(i).get("V9") == null ? "0" : list.get(i).get("V9").toString());
				json.add(obj);
			}
			response.getWriter().write(getPaixu(json,name).toString());
		}else{
			response.getWriter().write("0");
		}
	}
	/**
	 * 地图数据：贫困户基本信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getBfdxHu_2.do")
	public void getBfdxHu_2(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name = request.getParameter("name");//行政区划
		String year = request.getParameter("year");//统计年度
		String q1 = request.getParameter("q1");//旗县分类-国家重点旗县
		String q2 = request.getParameter("q2");//旗县分类-自治区重点旗县
		String q3 = request.getParameter("q3");//旗县分类-革命老区旗县
		String q4 = request.getParameter("q4");//旗县分类-牧业旗县
		String q5 = request.getParameter("q5");//旗县分类-边境旗县
		String t1 = request.getParameter("t1");//脱贫标志-返贫
		String t2 = request.getParameter("t2");//脱贫标志-现贫
		String src = "";
		if(name.equals("全部盟市")){
			name="内蒙古自治区";
		}
		String desc = request.getParameter("desc");
		String sql="SELECT a1.*,GJZDQX,ZZQZDQX,GMLQQX,MYQX,BJQX FROM("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
				+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='"+name+"'"
				+ ")) a2 LEFT JOIN PKC_1_2_1 a1 ON a1.COM_CODE=A2.COM_CODE ";
		if(t1.equals("0")&&t2.equals("1")){
			src += " WHERE  TYPE = '0' ";
		}else{
			src += " WHERE  TYPE = '1' ";
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
		JSONArray json = new JSONArray();
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		if (list.size()>0){
			for ( int i = 0 ; i < list.size() ; i ++){
				JSONObject obj = new JSONObject();
				obj.put("name", "".equals(list.get(i).get("COM_NAME")) || list.get(i).get("COM_NAME") == null ? "" : list.get(i).get("COM_NAME").toString());
				obj.put("value", "".equals(list.get(i).get("Z_HU")) || list.get(i).get("Z_HU") == null ? "" : Integer.parseInt(list.get(i).get("Z_HU").toString()));
				json.add(obj);
			}
			response.getWriter().write(json.toString());
		}else{
			response.getWriter().write("0");
		}
		
	}
	/**
	 * 主要致贫原因-表格
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getBfdxHu_3.do")
	public void getBfdxHu_3(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name = request.getParameter("name");//行政区划
		String year = request.getParameter("year");//统计年度
		String q1 = request.getParameter("q1");//旗县分类-国家重点旗县
		String q2 = request.getParameter("q2");//旗县分类-自治区重点旗县
		String q3 = request.getParameter("q3");//旗县分类-革命老区旗县
		String q4 = request.getParameter("q4");//旗县分类-牧业旗县
		String q5 = request.getParameter("q5");//旗县分类-边境旗县
		String t1 = request.getParameter("t1");//脱贫标志-返贫
		String t2 = request.getParameter("t2");//脱贫标志-现贫
		String src = "";
		if(name.equals("全部盟市")){
			name="内蒙古自治区";
		}
		String desc = request.getParameter("desc");
		String sql="SELECT a1.*,GJZDQX,ZZQZDQX,GMLQQX,MYQX,BJQX FROM("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
				+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='"+name+"'"
				+ ")) a2 LEFT JOIN PKC_1_2_2 a1 ON a1.COM_CODE=A2.COM_CODE ";
		if(t1.equals("0")&&t2.equals("1")){
			src += " WHERE  TYPE = '0' ";
		}else{
			src += " WHERE  TYPE = '1' ";
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
		JSONArray json = new JSONArray();
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		if (list.size() > 0 ){
			for(int i = 0 ; i < list.size() ; i ++ ){
				JSONObject obj = new JSONObject();
				obj.put("com_name", "".equals(list.get(i).get("COM_NAME")) || list.get(i).get("COM_NAME") == null ? "" : list.get(i).get("COM_NAME").toString());
				obj.put("z_hu", "".equals(list.get(i).get("Z_HU")) || list.get(i).get("Z_HU") == null ? "" : list.get(i).get("Z_HU").toString());
				obj.put("v1", "".equals(list.get(i).get("V1")) || list.get(i).get("V1") == null ? "" : list.get(i).get("V1").toString());
				obj.put("v3", "".equals(list.get(i).get("V3")) || list.get(i).get("V3") == null ? "" : list.get(i).get("V3").toString());
				obj.put("v5", "".equals(list.get(i).get("V5")) || list.get(i).get("V5") == null ? "" : list.get(i).get("V5").toString());
				obj.put("v7", "".equals(list.get(i).get("V7")) || list.get(i).get("V7") == null ? "" : list.get(i).get("V7").toString());
				obj.put("v9", "".equals(list.get(i).get("V9")) || list.get(i).get("V9") == null ? "" : list.get(i).get("V9").toString());
				obj.put("v11", "".equals(list.get(i).get("V11")) || list.get(i).get("V11") == null ? "" : list.get(i).get("V11").toString());
				obj.put("v13", "".equals(list.get(i).get("V13")) || list.get(i).get("V13") == null ? "" : list.get(i).get("V13").toString());
				obj.put("v15", "".equals(list.get(i).get("V15")) || list.get(i).get("V15") == null ? "" : list.get(i).get("V15").toString());
				obj.put("v17", "".equals(list.get(i).get("V17")) || list.get(i).get("V17") == null ? "" : list.get(i).get("V17").toString());
				obj.put("v19", "".equals(list.get(i).get("V19")) || list.get(i).get("V19") == null ? "" : list.get(i).get("V19").toString());
				obj.put("v21", "".equals(list.get(i).get("V21")) || list.get(i).get("V21") == null ? "" : list.get(i).get("V21").toString());
				json.add(obj);
			}
			response.getWriter().write(getPaixu(json,name).toString());
		}else{
			response.getWriter().write("0");
		}
	}
	/**
	 * 主要致贫原因-环形图
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getBfdxHu_4.do")
	public void getBfdxHu_4(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		String name = request.getParameter("name");//行政区划
		String year = request.getParameter("year");//统计年度
		String t1 = request.getParameter("t1");//脱贫标志-返贫
		String t2 = request.getParameter("t2");//脱贫标志-现贫
		String src = "";
		if(name.equals("全部盟市")){
			name="内蒙古自治区";
		}
		String desc = request.getParameter("desc");
		String sql = "select * from PKC_1_2_2";
		if(t1.equals("0")&&t2.equals("1")){
			src += " WHERE  TYPE = '0' ";
		}else{
			src += " WHERE  TYPE = '1' ";
		}
		
		if(name.equals("内蒙古自治区")){
			src += " AND  v98='2'";
		}else{
			src += " AND  COM_NAME='"+name+"'";
		}

		sql = sql+src; 
		JSONArray json = new JSONArray();
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		int v =0;int v1 = 0;int v3 = 0;int v5 = 0;int v7 = 0;int v9 = 0;int v11 = 0;int v13 = 0;int v15 = 0;int v17 = 0;int v19= 0;int v21=0;
		if(name.equals("内蒙古自治区")){
			for ( int i = 0 ; i < list.size() ; i ++ ){
				String a = "".equals(list.get(i).get("Z_HU")) || list.get(i).get("Z_HU") == null ? "0" : list.get(i).get("Z_HU").toString();
				String a1 = "".equals(list.get(i).get("V1")) || list.get(i).get("V1") == null ? "0" : list.get(i).get("V1").toString();
				String a3 = "".equals(list.get(i).get("V3")) || list.get(i).get("V3") == null ? "0" : list.get(i).get("V3").toString();
				String a5 ="".equals(list.get(i).get("V5")) || list.get(i).get("V5") == null ? "0" : list.get(i).get("V5").toString();
				String a7 = "".equals(list.get(i).get("V7")) || list.get(i).get("V7") == null ? "0" : list.get(i).get("V7").toString();
				String a9 = "".equals(list.get(i).get("V9")) || list.get(i).get("V9") == null ? "0" : list.get(i).get("V9").toString();
				String a11 = "".equals(list.get(i).get("V11")) || list.get(i).get("V11") == null ? "0" : list.get(i).get("V11").toString();
				String a13 = "".equals(list.get(i).get("V13")) || list.get(i).get("V13") == null ? "0" : list.get(i).get("V13").toString();
				String a15 = "".equals(list.get(i).get("V15")) || list.get(i).get("V15") == null ? "0" : list.get(i).get("V15").toString();
				String a17 = "".equals(list.get(i).get("V17")) || list.get(i).get("V17") == null ? "0" : list.get(i).get("V17").toString();
				String a19 = "".equals(list.get(i).get("V19")) || list.get(i).get("V19") == null ? "0" : list.get(i).get("V19").toString();
				String a21 = "".equals(list.get(i).get("V21")) || list.get(i).get("V21") == null ? "0" : list.get(i).get("V21").toString();
				v = v+Integer.parseInt(a);
				v1 = v1 + Integer.parseInt(a1);
				v3 = v3 + Integer.parseInt(a3);
				v5 = v5 + Integer.parseInt(a5);
				v7 = v7 + Integer.parseInt(a7);
				v9 = v9 + Integer.parseInt(a9);
				v11 = v11 + Integer.parseInt(a11);
				v13 = v13 + Integer.parseInt(a13);
				v15 = v15 + Integer.parseInt(a15);
				v17 = v17 + Integer.parseInt(a17);
				v19 = v19 + Integer.parseInt(a19);
				v21 = v21 + Integer.parseInt(a21);
			}
			JSONObject obj = new JSONObject();
			obj.put("com_name","汇总");
			obj.put("z_hu",v);
			obj.put("v1", v1);
			obj.put("v3", v3);
			obj.put("v5", v5);
			obj.put("v7", v7);
			obj.put("v9", v9);
			obj.put("v11", v11);
			obj.put("v13", v13);
			obj.put("v15", v15);
			obj.put("v17", v17);
			obj.put("v19", v19);
			obj.put("v21", v21);
			json.add(obj);
			response.getWriter().write(json.toString());
		}else {
			if(list.size() > 0) {
				for ( int i = 0 ; i < list.size() ; i ++ ){
					JSONObject obj = new JSONObject();
					obj.put("com_name", "".equals(list.get(i).get("COM_NAME")) || list.get(i).get("COM_NAME") == null ? "" : list.get(i).get("COM_NAME").toString());
					obj.put("z_hu", "".equals(list.get(i).get("Z_HU")) || list.get(i).get("Z_HU") == null ? "" : list.get(i).get("Z_HU").toString());
					obj.put("v1", "".equals(list.get(i).get("V1")) || list.get(i).get("V1") == null ? "" : list.get(i).get("V1").toString());
					obj.put("v3", "".equals(list.get(i).get("V3")) || list.get(i).get("V3") == null ? "" : list.get(i).get("V3").toString());
					obj.put("v5", "".equals(list.get(i).get("V5")) || list.get(i).get("V5") == null ? "" : list.get(i).get("V5").toString());
					obj.put("v7", "".equals(list.get(i).get("V7")) || list.get(i).get("V7") == null ? "" : list.get(i).get("V7").toString());
					obj.put("v9", "".equals(list.get(i).get("V9")) || list.get(i).get("V9") == null ? "" : list.get(i).get("V9").toString());
					obj.put("v11", "".equals(list.get(i).get("V11")) || list.get(i).get("V11") == null ? "" : list.get(i).get("V11").toString());
					obj.put("v13", "".equals(list.get(i).get("V13")) || list.get(i).get("V13") == null ? "" : list.get(i).get("V13").toString());
					obj.put("v15", "".equals(list.get(i).get("V15")) || list.get(i).get("V15") == null ? "" : list.get(i).get("V15").toString());
					obj.put("v17", "".equals(list.get(i).get("V17")) || list.get(i).get("V17") == null ? "" : list.get(i).get("V17").toString());
					obj.put("v19", "".equals(list.get(i).get("V19")) || list.get(i).get("V19") == null ? "" : list.get(i).get("V19").toString());
					obj.put("v21", "".equals(list.get(i).get("V21")) || list.get(i).get("V21") == null ? "" : list.get(i).get("V21").toString());
					json.add(obj);
				}
				response.getWriter().write(json.toString());
			}else{
				response.getWriter().write("0");
			}
		}

		
	}
	/**
	 * 其他致贫原因-表格
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getBfdxHu_5.do")
	public void getBfdxHu_5(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name = request.getParameter("name");//行政区划
		String year = request.getParameter("year");//统计年度
		String q1 = request.getParameter("q1");//旗县分类-国家重点旗县
		String q2 = request.getParameter("q2");//旗县分类-自治区重点旗县
		String q3 = request.getParameter("q3");//旗县分类-革命老区旗县
		String q4 = request.getParameter("q4");//旗县分类-牧业旗县
		String q5 = request.getParameter("q5");//旗县分类-边境旗县
		String t1 = request.getParameter("t1");//脱贫标志-返贫
		String t2 = request.getParameter("t2");//脱贫标志-现贫
		String src = "";
		if(name.equals("全部盟市")){
			name="内蒙古自治区";
		}
		String desc = request.getParameter("desc");
		String sql="SELECT a1.*,GJZDQX,ZZQZDQX,GMLQQX,MYQX,BJQX FROM("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
				+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='"+name+"'"
				+ ")) a2 LEFT JOIN PKC_1_2_3 a1 ON a1.COM_CODE=A2.COM_CODE ";
		if(t1.equals("0")&&t2.equals("1")){
			src += " WHERE  TYPE = '0' ";
		}else{
			src += " WHERE  TYPE = '1' ";
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
		
		JSONArray json = new JSONArray();
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		if (list.size() > 0 ){
			for(int i = 0 ; i < list.size() ; i ++ ){
				JSONObject obj = new JSONObject();
				obj.put("com_name", "".equals(list.get(i).get("COM_NAME")) || list.get(i).get("COM_NAME") == null ? "" : list.get(i).get("COM_NAME").toString());
				obj.put("z_hu", "".equals(list.get(i).get("Z_HU")) || list.get(i).get("Z_HU") == null ? "" : list.get(i).get("Z_HU").toString());
				obj.put("v1", "".equals(list.get(i).get("V1")) || list.get(i).get("V1") == null ? "" : list.get(i).get("V1").toString());
				obj.put("v3", "".equals(list.get(i).get("V3")) || list.get(i).get("V3") == null ? "" : list.get(i).get("V3").toString());
				obj.put("v5", "".equals(list.get(i).get("V5")) || list.get(i).get("V5") == null ? "" : list.get(i).get("V5").toString());
				obj.put("v7", "".equals(list.get(i).get("V7")) || list.get(i).get("V7") == null ? "" : list.get(i).get("V7").toString());
				obj.put("v9", "".equals(list.get(i).get("V9")) || list.get(i).get("V9") == null ? "" : list.get(i).get("V9").toString());
				obj.put("v11", "".equals(list.get(i).get("V11")) || list.get(i).get("V11") == null ? "" : list.get(i).get("V11").toString());
				obj.put("v13", "".equals(list.get(i).get("V13")) || list.get(i).get("V13") == null ? "" : list.get(i).get("V13").toString());
				obj.put("v15", "".equals(list.get(i).get("V15")) || list.get(i).get("V15") == null ? "" : list.get(i).get("V15").toString());
				obj.put("v17", "".equals(list.get(i).get("V17")) || list.get(i).get("V17") == null ? "" : list.get(i).get("V17").toString());
				obj.put("v19", "".equals(list.get(i).get("V19")) || list.get(i).get("V19") == null ? "" : list.get(i).get("V19").toString());
				obj.put("v21", "".equals(list.get(i).get("V21")) || list.get(i).get("V21") == null ? "" : list.get(i).get("V21").toString());
				obj.put("v23", "".equals(list.get(i).get("V23")) || list.get(i).get("V23") == null ? "" : list.get(i).get("V23").toString());
				obj.put("v25", "".equals(list.get(i).get("V25")) || list.get(i).get("V25") == null ? "" : list.get(i).get("V25").toString());
				json.add(obj);
			}
			response.getWriter().write(getPaixu(json,name).toString());
		}else{
			response.getWriter().write("0");
		}
	}
	/**
	 * 其他致贫原因-柱图瀑布流
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getBfdxHu_6.do")
	public void getBfdxHu_6(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		String name = request.getParameter("name");//行政区划
		String year = request.getParameter("year");//统计年度
		String t1 = request.getParameter("t1");//脱贫标志-返贫
		String t2 = request.getParameter("t2");//脱贫标志-现贫
		String src = "";
		if(name.equals("全部盟市")){
			name="内蒙古自治区";
		}
		String desc = request.getParameter("desc");
		String sql = "select * from PKC_1_2_3 ";
		if(t1.equals("0")&&t2.equals("1")){
			src += " WHERE  TYPE = '0' ";
		}else{
			src += " WHERE  TYPE = '1' ";
		}
		if (name.equals("内蒙古自治区")){
			src += " AND  v98='2'";
		}else{
			src += " AND  COM_NAME='"+name+"'";
		}
		sql = sql+src; 
		JSONArray json = new JSONArray();
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		int v =0;int v1 = 0;int v3 = 0;int v5 = 0;int v7 = 0;int v9 = 0;int v11 = 0;int v13 = 0;int v15 = 0;int v17 = 0;int v19= 0;int v21=0;int v23 =0; int v25 = 0;
		if(name.equals("内蒙古自治区")){
			for ( int i = 0 ; i < list.size() ; i ++ ){
				String a = "".equals(list.get(i).get("Z_HU")) || list.get(i).get("Z_HU") == null ? "0" : list.get(i).get("Z_HU").toString();
				String a1 = "".equals(list.get(i).get("V1")) || list.get(i).get("V1") == null ? "0" : list.get(i).get("V1").toString();
				String a3 = "".equals(list.get(i).get("V3")) || list.get(i).get("V3") == null ? "0" : list.get(i).get("V3").toString();
				String a5 ="".equals(list.get(i).get("V5")) || list.get(i).get("V5") == null ? "0" : list.get(i).get("V5").toString();
				String a7 = "".equals(list.get(i).get("V7")) || list.get(i).get("V7") == null ? "0" : list.get(i).get("V7").toString();
				String a9 = "".equals(list.get(i).get("V9")) || list.get(i).get("V9") == null ? "0" : list.get(i).get("V9").toString();
				String a11 = "".equals(list.get(i).get("V11")) || list.get(i).get("V11") == null ? "0" : list.get(i).get("V11").toString();
				String a13 = "".equals(list.get(i).get("V13")) || list.get(i).get("V13") == null ? "0" : list.get(i).get("V13").toString();
				String a15 = "".equals(list.get(i).get("V15")) || list.get(i).get("V15") == null ? "0" : list.get(i).get("V15").toString();
				String a17 = "".equals(list.get(i).get("V17")) || list.get(i).get("V17") == null ? "0" : list.get(i).get("V17").toString();
				String a19 = "".equals(list.get(i).get("V19")) || list.get(i).get("V19") == null ? "0" : list.get(i).get("V19").toString();
				String a21 = "".equals(list.get(i).get("V21")) || list.get(i).get("V21") == null ? "0" : list.get(i).get("V21").toString();
				String a23 = "".equals(list.get(i).get("V23")) || list.get(i).get("V23") == null ? "0" : list.get(i).get("V23").toString();
				String a25 = "".equals(list.get(i).get("V25")) || list.get(i).get("V25") == null ? "0" : list.get(i).get("V25").toString();
				v = v+Integer.parseInt(a);
				v1 = v1 + Integer.parseInt(a1);
				v3 = v3 + Integer.parseInt(a3);
				v5 = v5 + Integer.parseInt(a5);
				v7 = v7 + Integer.parseInt(a7);
				v9 = v9 + Integer.parseInt(a9);
				v11 = v11 + Integer.parseInt(a11);
				v13 = v13 + Integer.parseInt(a13);
				v15 = v15 + Integer.parseInt(a15);
				v17 = v17 + Integer.parseInt(a17);
				v19 = v19 + Integer.parseInt(a19);
				v21 = v21 + Integer.parseInt(a21);
				v23 = v23 + Integer.parseInt(a23);
				v25 = v23 + Integer.parseInt(a25);
			}
			JSONObject obj = new JSONObject();
			obj.put("com_name","汇总");
			obj.put("z_hu",v);
			obj.put("v1", v1);
			obj.put("v3", v3);
			obj.put("v5", v5);
			obj.put("v7", v7);
			obj.put("v9", v9);
			obj.put("v11", v11);
			obj.put("v13", v13);
			obj.put("v15", v15);
			obj.put("v17", v17);
			obj.put("v19", v19);
			obj.put("v21", v21);
			obj.put("v23", v23);
			obj.put("v25", v25);
			json.add(obj);
			response.getWriter().write(json.toString());
		}else {
			if(list.size() > 0) {
				for ( int i = 0 ; i < list.size() ; i ++ ){
					JSONObject obj = new JSONObject();
					obj.put("com_name", "".equals(list.get(i).get("COM_NAME")) || list.get(i).get("COM_NAME") == null ? "" : list.get(i).get("COM_NAME").toString());
					obj.put("z_hu", "".equals(list.get(i).get("Z_HU")) || list.get(i).get("Z_HU") == null ? "" : list.get(i).get("Z_HU").toString());
					obj.put("v1", "".equals(list.get(i).get("V1")) || list.get(i).get("V1") == null ? "" : list.get(i).get("V1").toString());
					obj.put("v3", "".equals(list.get(i).get("V3")) || list.get(i).get("V3") == null ? "" : list.get(i).get("V3").toString());
					obj.put("v5", "".equals(list.get(i).get("V5")) || list.get(i).get("V5") == null ? "" : list.get(i).get("V5").toString());
					obj.put("v7", "".equals(list.get(i).get("V7")) || list.get(i).get("V7") == null ? "" : list.get(i).get("V7").toString());
					obj.put("v9", "".equals(list.get(i).get("V9")) || list.get(i).get("V9") == null ? "" : list.get(i).get("V9").toString());
					obj.put("v11", "".equals(list.get(i).get("V11")) || list.get(i).get("V11") == null ? "" : list.get(i).get("V11").toString());
					obj.put("v13", "".equals(list.get(i).get("V13")) || list.get(i).get("V13") == null ? "" : list.get(i).get("V13").toString());
					obj.put("v15", "".equals(list.get(i).get("V15")) || list.get(i).get("V15") == null ? "" : list.get(i).get("V15").toString());
					obj.put("v17", "".equals(list.get(i).get("V17")) || list.get(i).get("V17") == null ? "" : list.get(i).get("V17").toString());
					obj.put("v19", "".equals(list.get(i).get("V19")) || list.get(i).get("V19") == null ? "" : list.get(i).get("V19").toString());
					obj.put("v21", "".equals(list.get(i).get("V21")) || list.get(i).get("V21") == null ? "" : list.get(i).get("V21").toString());
					obj.put("v23", "".equals(list.get(i).get("V23")) || list.get(i).get("V23") == null ? "" : list.get(i).get("V23").toString());
					obj.put("v25", "".equals(list.get(i).get("V25")) || list.get(i).get("V25") == null ? "" : list.get(i).get("V25").toString());
					json.add(obj);
				}
				response.getWriter().write(json.toString());
			}else{
				response.getWriter().write("0");
			}
		}
		
	}
	/**
	 * 贫困户帮扶责任人落实情况-表格
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getBfdxHu_7.do")
	public void getBfdxHu_7(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name = request.getParameter("name");//行政区划
		String year = request.getParameter("year");//统计年度
		String q1 = request.getParameter("q1");//旗县分类-国家重点旗县
		String q2 = request.getParameter("q2");//旗县分类-自治区重点旗县
		String q3 = request.getParameter("q3");//旗县分类-革命老区旗县
		String q4 = request.getParameter("q4");//旗县分类-牧业旗县
		String q5 = request.getParameter("q5");//旗县分类-边境旗县
		String t1 = request.getParameter("t1");//脱贫标志-返贫
		String t2 = request.getParameter("t2");//脱贫标志-现贫
		String src = "";
		if(name.equals("全部盟市")){
			name="内蒙古自治区";
		}
		String desc = request.getParameter("desc");
		String sql="SELECT a1.COM_NAME,z_hu,v1,round(V1/Z_HU,4)*100 AS BILI,GJZDQX,ZZQZDQX,GMLQQX,MYQX,BJQX FROM("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
				+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='"+name+"'"
				+ ")) a2 LEFT JOIN PKC_1_2_4 a1 ON a1.COM_CODE=A2.COM_CODE ";
		if(t1.equals("0")&&t2.equals("1")){
			src += " WHERE  TYPE = '0' ";
		}else{
			src += " WHERE  TYPE = '1' ";
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
		if("".equals(desc) || desc == null){
			
		}else{
			 sql += " order by BILI "+desc;
		}
		JSONArray json = new JSONArray();
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		if (list.size() > 0 ){
			for(int i = 0 ; i < list.size() ; i ++ ){
				JSONObject obj = new JSONObject();
				obj.put("com_name", "".equals(list.get(i).get("COM_NAME")) || list.get(i).get("COM_NAME") == null ? "" : list.get(i).get("COM_NAME").toString());
				obj.put("z_hu", "".equals(list.get(i).get("Z_HU")) || list.get(i).get("Z_HU") == null ? "" : list.get(i).get("Z_HU").toString());
				obj.put("v1", "".equals(list.get(i).get("V1")) || list.get(i).get("V1") == null ? "" : list.get(i).get("V1").toString());
				obj.put("BILI", "".equals(list.get(i).get("BILI")) || list.get(i).get("BILI") == null ? "" : list.get(i).get("BILI").toString());
				json.add(obj);
			}
			if("".equals(desc) || desc == null){
				response.getWriter().write(getPaixu(json,name).toString());
			}else{
				response.getWriter().write(json.toString());
			}
			
		}else{
			response.getWriter().write("0");
		}
	}
	/**
	 * 贫困户生产生活条件(1.2.5)表格
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getBfdxHu_8.do")
	public void getBfdxHu_8(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name = request.getParameter("name");//行政区划
		String year = request.getParameter("year");//统计年度
		String q1 = request.getParameter("q1");//旗县分类-国家重点旗县
		String q2 = request.getParameter("q2");//旗县分类-自治区重点旗县
		String q3 = request.getParameter("q3");//旗县分类-革命老区旗县
		String q4 = request.getParameter("q4");//旗县分类-牧业旗县
		String q5 = request.getParameter("q5");//旗县分类-边境旗县
		String t1 = request.getParameter("t1");//脱贫标志-返贫
		String t2 = request.getParameter("t2");//脱贫标志-现贫
		String src = "";
		if(name.equals("全部盟市")){
			name="内蒙古自治区";
		}
		String desc = request.getParameter("desc");
		JSONArray json = new JSONArray();
		String sql="SELECT a1.*,GJZDQX,ZZQZDQX,GMLQQX,MYQX,BJQX FROM("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
				+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='"+name+"'"
				+ ")) a2 LEFT JOIN PKC_1_2_5 a1 ON a1.COM_CODE=A2.COM_CODE ";
		if(t1.equals("0")&&t2.equals("1")){
			src += " WHERE  TYPE = '0' ";
		}else{
			src += " WHERE  TYPE = '1' ";
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
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		if (list.size() > 0 ){
			for(int i = 0 ; i < list.size() ; i ++ ){
				JSONObject obj = new JSONObject();
				obj.put("com_name", "".equals(list.get(i).get("COM_NAME")) || list.get(i).get("COM_NAME") == null ? "" : list.get(i).get("COM_NAME").toString());
				obj.put("z_hu", "".equals(list.get(i).get("Z_HU")) || list.get(i).get("Z_HU") == null ? "0" : list.get(i).get("Z_HU").toString());
				obj.put("v1", "".equals(list.get(i).get("V1")) || list.get(i).get("V1") == null ? "0" : list.get(i).get("V1").toString());
				obj.put("v3", "".equals(list.get(i).get("V3")) || list.get(i).get("V3") == null ? "0" : list.get(i).get("V3").toString());
				obj.put("v5", "".equals(list.get(i).get("V5")) || list.get(i).get("V5") == null ? "0" : list.get(i).get("V5").toString());
				obj.put("v7", "".equals(list.get(i).get("V7")) || list.get(i).get("V7") == null ? "0" : list.get(i).get("V7").toString());
				obj.put("v9", "".equals(list.get(i).get("V9")) || list.get(i).get("V9") == null ? "0" : list.get(i).get("V9").toString());
				obj.put("v11", "".equals(list.get(i).get("V11")) || list.get(i).get("V11") == null ? "0" : list.get(i).get("V11").toString());
				obj.put("v13", "".equals(list.get(i).get("V13")) || list.get(i).get("V13") == null ? "0" : list.get(i).get("V13").toString());
				json.add(obj);
			}
			response.getWriter().write(getPaixu(json,name).toString());
		}else{
			response.getWriter().write("0");
		}
	}
	/**
	 * 贫困户生产生活条件(1.2.5)柱图
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getBfdxHu_9.do")
	public void getBfdxHu_9(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name = request.getParameter("name");//行政区划
		String year = request.getParameter("year");//统计年度
		String q1 = request.getParameter("q1");//旗县分类-国家重点旗县
		String q2 = request.getParameter("q2");//旗县分类-自治区重点旗县
		String q3 = request.getParameter("q3");//旗县分类-革命老区旗县
		String q4 = request.getParameter("q4");//旗县分类-牧业旗县
		String q5 = request.getParameter("q5");//旗县分类-边境旗县
		String t1 = request.getParameter("t1");//脱贫标志-返贫
		String t2 = request.getParameter("t2");//脱贫标志-现贫
		String src = "";
		if(name.equals("全部盟市")){
			name="内蒙古自治区";
		}
		String desc = request.getParameter("desc");
		JSONArray json = new JSONArray();
		String sql="SELECT a1.*,GJZDQX,ZZQZDQX,GMLQQX,MYQX,BJQX FROM("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
				+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='"+name+"'"
				+ ")) a2 LEFT JOIN PKC_1_2_6 a1 ON a1.COM_CODE=A2.COM_CODE ";
		if(t1.equals("0")&&t2.equals("1")){
			src += " WHERE  TYPE = '0' ";
		}else{
			src += " WHERE  TYPE = '1' ";
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
		List<Map>  list = this.getBySqlMapper.findRecords(sql);
		
		if ( list.size() > 0 ) {
			for ( int i = 0 ; i < list.size() ; i ++ ){
				JSONObject obj = new JSONObject();
				obj.put("com_name", "".equals(list.get(i).get("COM_NAME")) || list.get(i).get("COM_NAME") == null ? "" : list.get(i).get("COM_NAME").toString());
				obj.put("z_hu", "".equals(list.get(i).get("Z_HU")) || list.get(i).get("Z_HU") == null ? "0" : list.get(i).get("Z_HU").toString());
				obj.put("v1", "".equals(list.get(i).get("V1")) || list.get(i).get("V1") == null ? "0" : list.get(i).get("V1").toString());
				obj.put("v2", "".equals(list.get(i).get("V2")) || list.get(i).get("V2") == null ? "0" : list.get(i).get("V2").toString());
				obj.put("v3", "".equals(list.get(i).get("V3")) || list.get(i).get("V3") == null ? "0" : list.get(i).get("V3").toString());
				obj.put("v4", "".equals(list.get(i).get("V4")) || list.get(i).get("V4") == null ? "0" : list.get(i).get("V4").toString());
				obj.put("v5", "".equals(list.get(i).get("V5")) || list.get(i).get("V5") == null ? "0" : list.get(i).get("V5").toString());
				obj.put("v6", "".equals(list.get(i).get("V6")) || list.get(i).get("V6") == null ? "0" : list.get(i).get("V6").toString());
				obj.put("v7", "".equals(list.get(i).get("V7")) || list.get(i).get("V7") == null ? "0" : list.get(i).get("V7").toString());
				obj.put("v8", "".equals(list.get(i).get("V8")) || list.get(i).get("V8") == null ? "0" : list.get(i).get("V8").toString());
				obj.put("v9", "".equals(list.get(i).get("V9")) || list.get(i).get("V9") == null ? "0" : list.get(i).get("V9").toString());
				obj.put("v10", "".equals(list.get(i).get("V10")) || list.get(i).get("V10") == null ? "0" : list.get(i).get("V10").toString());
				obj.put("v11", "".equals(list.get(i).get("V11")) || list.get(i).get("V11") == null ? "0" : list.get(i).get("V11").toString());
				obj.put("v12", "".equals(list.get(i).get("V12")) || list.get(i).get("V12") == null ? "0" : list.get(i).get("V12").toString());
				obj.put("v13", "".equals(list.get(i).get("V13")) || list.get(i).get("V13") == null ? "0" : list.get(i).get("V13").toString());
				obj.put("v14", "".equals(list.get(i).get("V14")) || list.get(i).get("V14") == null ? "0" : list.get(i).get("V14").toString());
				json.add(obj);
			}
			response.getWriter().write(getPaixu(json,name).toString());
		}else{
			response.getWriter().write("0");
		}
	}
	
	
	/**
	 * 贫困户土地资源情况-人均占地柱图
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getBfdxHu_10.do")
	public void getBfdxHu_10(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name = request.getParameter("name");//行政区划
		String year = request.getParameter("year");//统计年度
		String q1 = request.getParameter("q1");//旗县分类-国家重点旗县
		String q2 = request.getParameter("q2");//旗县分类-自治区重点旗县
		String q3 = request.getParameter("q3");//旗县分类-革命老区旗县
		String q4 = request.getParameter("q4");//旗县分类-牧业旗县
		String q5 = request.getParameter("q5");//旗县分类-边境旗县
		String t1 = request.getParameter("t1");//脱贫标志-返贫
		String t2 = request.getParameter("t2");//脱贫标志-现贫
		String src = "";
		if(name.equals("全部盟市")){
			name="内蒙古自治区";
		}
		String desc = request.getParameter("desc");
		JSONArray json = new JSONArray();
		String sql="SELECT a1.*,GJZDQX,ZZQZDQX,GMLQQX,MYQX,BJQX FROM("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
				+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='"+name+"'"
				+ ")) a2 LEFT JOIN PKC_1_2_6 a1 ON a1.COM_CODE=A2.COM_CODE ";
		if(t1.equals("0")&&t2.equals("1")){
			src += " WHERE  TYPE = '0' ";
		}else{
			src += " WHERE  TYPE = '1' ";
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
		List<Map>  list = this.getBySqlMapper.findRecords(sql);
		
		if ( list.size() > 0 ) {
			for ( int i = 0 ; i < list.size() ; i ++ ){
				JSONObject obj = new JSONObject();
				obj.put("com_name", "".equals(list.get(i).get("COM_NAME")) || list.get(i).get("COM_NAME") == null ? "" : list.get(i).get("COM_NAME").toString());
				obj.put("z_hu", "".equals(list.get(i).get("Z_HU")) || list.get(i).get("Z_HU") == null ? "0" : list.get(i).get("Z_HU").toString());
				obj.put("v2", "".equals(list.get(i).get("V2")) || list.get(i).get("V2") == null ? "0" : list.get(i).get("V2").toString());
				obj.put("v4", "".equals(list.get(i).get("V4")) || list.get(i).get("V4") == null ? "0" : list.get(i).get("V4").toString());
				obj.put("v6", "".equals(list.get(i).get("V6")) || list.get(i).get("V6") == null ? "0" : list.get(i).get("V6").toString());
				obj.put("v8", "".equals(list.get(i).get("V8")) || list.get(i).get("V8") == null ? "0" : list.get(i).get("V8").toString());
				obj.put("v10", "".equals(list.get(i).get("V10")) || list.get(i).get("V10") == null ? "0" : list.get(i).get("V10").toString());
				obj.put("v12", "".equals(list.get(i).get("V12")) || list.get(i).get("V12") == null ? "0" : list.get(i).get("V12").toString());
				obj.put("v14", "".equals(list.get(i).get("V14")) || list.get(i).get("V14") == null ? "0" : list.get(i).get("V14").toString());
				json.add(obj);
			}
			response.getWriter().write(getPaixu(json,name).toString());
		}else{
			response.getWriter().write("0");
		}
	}
	/**
	 * 贫困户人均收入分组情况(1.2.7)表格
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getBfdxHuc_11.do")
	public void getBfdxHuc_11(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name = request.getParameter("name");//行政区划
		String year = request.getParameter("year");//统计年度
		String q1 = request.getParameter("q1");//旗县分类-国家重点旗县
		String q2 = request.getParameter("q2");//旗县分类-自治区重点旗县
		String q3 = request.getParameter("q3");//旗县分类-革命老区旗县
		String q4 = request.getParameter("q4");//旗县分类-牧业旗县
		String q5 = request.getParameter("q5");//旗县分类-边境旗县
		String t1 = request.getParameter("t1");//脱贫标志-返贫
		String t2 = request.getParameter("t2");//脱贫标志-现贫
		String src = "";
		if(name.equals("全部盟市")){
			name="内蒙古自治区";
		}
		String desc = request.getParameter("desc");
		JSONArray json = new JSONArray();
		String sql="SELECT a1.*,GJZDQX,ZZQZDQX,GMLQQX,MYQX,BJQX FROM("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
				+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='"+name+"'"
				+ ")) a2 LEFT JOIN PKC_1_2_7 a1 ON a1.COM_CODE=A2.COM_CODE ";
		if(t1.equals("0")&&t2.equals("1")){
			src += " WHERE  TYPE = '0' ";
		}else{
			src += " WHERE  TYPE = '1' ";
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
		List<Map>  list = this.getBySqlMapper.findRecords(sql);
		if ( list.size() > 0 ) {
			for ( int i = 0 ; i < list.size() ; i ++ ){
				JSONObject obj = new JSONObject();
				obj.put("com_name", "".equals(list.get(i).get("COM_NAME")) || list.get(i).get("COM_NAME") == null ? "" : list.get(i).get("COM_NAME").toString());
				obj.put("z_hu", "".equals(list.get(i).get("Z_HU")) || list.get(i).get("Z_HU") == null ? "0" : list.get(i).get("Z_HU").toString());
				obj.put("v1", "".equals(list.get(i).get("V1")) || list.get(i).get("V1") == null ? "0" : list.get(i).get("V1").toString());
				obj.put("v3", "".equals(list.get(i).get("V3")) || list.get(i).get("V3") == null ? "0" : list.get(i).get("V3").toString());
				obj.put("v5", "".equals(list.get(i).get("V5")) || list.get(i).get("V5") == null ? "0" : list.get(i).get("V5").toString());
				obj.put("v7", "".equals(list.get(i).get("V7")) || list.get(i).get("V7") == null ? "0" : list.get(i).get("V7").toString());
				obj.put("v9", "".equals(list.get(i).get("V9")) || list.get(i).get("V9") == null ? "0" : list.get(i).get("V9").toString());
				obj.put("v11", "".equals(list.get(i).get("V11")) || list.get(i).get("V11") == null ? "0" : list.get(i).get("V11").toString());
				obj.put("v13", "".equals(list.get(i).get("V13")) || list.get(i).get("V13") == null ? "0" : list.get(i).get("V13").toString());
				json.add(obj);
			}
			response.getWriter().write(getPaixu(json,name).toString());
		}else{
			response.getWriter().write("0");
		}
	}
	/**
	 * 贫困户人均收入分组情况(1.2.7)柱状图
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getBfdxHuc_12.do")
	public void getBfdxHuc_12(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name = request.getParameter("name");//行政区划
		String year = request.getParameter("year");//统计年度
		String q1 = request.getParameter("q1");//旗县分类-国家重点旗县
		String q2 = request.getParameter("q2");//旗县分类-自治区重点旗县
		String q3 = request.getParameter("q3");//旗县分类-革命老区旗县
		String q4 = request.getParameter("q4");//旗县分类-牧业旗县
		String q5 = request.getParameter("q5");//旗县分类-边境旗县
		String t1 = request.getParameter("t1");//脱贫标志-返贫
		String t2 = request.getParameter("t2");//脱贫标志-现贫
		String src = "";
		if(name.equals("全部盟市")){
			name="内蒙古自治区";
		}
		String desc = request.getParameter("desc");
		JSONArray json = new JSONArray();
		String sql="SELECT a1.*,GJZDQX,ZZQZDQX,GMLQQX,MYQX,BJQX FROM("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
				+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='"+name+"'"
				+ ")) a2 LEFT JOIN PKC_1_2_7 a1 ON a1.COM_CODE=A2.COM_CODE ";
		if(t1.equals("0")&&t2.equals("1")){
			src += " WHERE  TYPE = '0' ";
		}else{
			src += " WHERE  TYPE = '1' ";
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
		List<Map>  list = this.getBySqlMapper.findRecords(sql);
		if ( list.size() > 0 ) {
			for ( int i = 0 ; i < list.size() ; i ++ ){
				JSONObject obj = new JSONObject();
				obj.put("com_name", "".equals(list.get(i).get("COM_NAME")) || list.get(i).get("COM_NAME") == null ? "" : list.get(i).get("COM_NAME").toString());
				obj.put("z_hu", "".equals(list.get(i).get("Z_HU")) || list.get(i).get("Z_HU") == null ? "0" : list.get(i).get("Z_HU").toString());
				obj.put("v1", "".equals(list.get(i).get("V1")) || list.get(i).get("V1") == null ? "0" : list.get(i).get("V1").toString());
				obj.put("v3", "".equals(list.get(i).get("V3")) || list.get(i).get("V3") == null ? "0" : list.get(i).get("V3").toString());
				obj.put("v5", "".equals(list.get(i).get("V5")) || list.get(i).get("V5") == null ? "0" : list.get(i).get("V5").toString());
				obj.put("v7", "".equals(list.get(i).get("V7")) || list.get(i).get("V7") == null ? "0" : list.get(i).get("V7").toString());
				obj.put("v9", "".equals(list.get(i).get("V9")) || list.get(i).get("V9") == null ? "0" : list.get(i).get("V9").toString());
				obj.put("v11", "".equals(list.get(i).get("V11")) || list.get(i).get("V11") == null ? "0" : list.get(i).get("V11").toString());
				obj.put("v13", "".equals(list.get(i).get("V13")) || list.get(i).get("V13") == null ? "0" : list.get(i).get("V13").toString());
				json.add(obj);
			}
			response.getWriter().write(json.toString());
		}else{
			response.getWriter().write("0");
		}
	}
	
	/**
	 * 贫困户主要燃料类型(1.2.8)-表格
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getBfdxHuc_13.do")
	public void getBfdxHuc_13(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name = request.getParameter("name");//行政区划
		String year = request.getParameter("year");//统计年度
		String q1 = request.getParameter("q1");//旗县分类-国家重点旗县
		String q2 = request.getParameter("q2");//旗县分类-自治区重点旗县
		String q3 = request.getParameter("q3");//旗县分类-革命老区旗县
		String q4 = request.getParameter("q4");//旗县分类-牧业旗县
		String q5 = request.getParameter("q5");//旗县分类-边境旗县
		String t1 = request.getParameter("t1");//脱贫标志-返贫
		String t2 = request.getParameter("t2");//脱贫标志-现贫
		String src = "";
		if(name.equals("全部盟市")){
			name="内蒙古自治区";
		}
		String desc = request.getParameter("desc");
		JSONArray json = new JSONArray();
		String sql="SELECT a1.*,GJZDQX,ZZQZDQX,GMLQQX,MYQX,BJQX FROM("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
				+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='"+name+"'"
				+ ")) a2 LEFT JOIN PKC_1_2_8 a1 ON a1.COM_CODE=A2.COM_CODE ";
		if(t1.equals("0")&&t2.equals("1")){
			src += " WHERE  TYPE = '0' ";
		}else{
			src += " WHERE  TYPE = '1' ";
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
		List<Map>  list = this.getBySqlMapper.findRecords(sql);
		if ( list.size() > 0 ) {
			for ( int i = 0 ; i < list.size() ; i ++ ){
				JSONObject obj = new JSONObject();
				obj.put("com_name", "".equals(list.get(i).get("COM_NAME")) || list.get(i).get("COM_NAME") == null ? "" : list.get(i).get("COM_NAME").toString());
				obj.put("z_hu", "".equals(list.get(i).get("Z_HU")) || list.get(i).get("Z_HU") == null ? "0" : list.get(i).get("Z_HU").toString());
				obj.put("v1", "".equals(list.get(i).get("V1")) || list.get(i).get("V1") == null ? "0" : list.get(i).get("V1").toString());
				obj.put("v3", "".equals(list.get(i).get("V3")) || list.get(i).get("V3") == null ? "0" : list.get(i).get("V3").toString());
				obj.put("v5", "".equals(list.get(i).get("V5")) || list.get(i).get("V5") == null ? "0" : list.get(i).get("V5").toString());
				obj.put("v7", "".equals(list.get(i).get("V7")) || list.get(i).get("V7") == null ? "0" : list.get(i).get("V7").toString());
				obj.put("v9", "".equals(list.get(i).get("V9")) || list.get(i).get("V9") == null ? "0" : list.get(i).get("V9").toString());
				json.add(obj);
			}
			response.getWriter().write(getPaixu(json,name).toString());
		}else{
			response.getWriter().write("0");
		}
	}
	/**
	 * 贫困户入户路情况 1.2.9 -地图
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getBfdxHuc_14.do")
	public void getBfdxHuc_14(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name = request.getParameter("name");//行政区划
		String year = request.getParameter("year");//统计年度
		String q1 = request.getParameter("q1");//旗县分类-国家重点旗县
		String q2 = request.getParameter("q2");//旗县分类-自治区重点旗县
		String q3 = request.getParameter("q3");//旗县分类-革命老区旗县
		String q4 = request.getParameter("q4");//旗县分类-牧业旗县
		String q5 = request.getParameter("q5");//旗县分类-边境旗县
		String t1 = request.getParameter("t1");//脱贫标志-返贫
		String t2 = request.getParameter("t2");//脱贫标志-现贫
		String src = "";
		if(name.equals("全部盟市")){
			name="内蒙古自治区";
		}
		String desc = request.getParameter("desc");
		JSONArray json = new JSONArray();
		String sql="SELECT a1.*,GJZDQX,ZZQZDQX,GMLQQX,MYQX,BJQX FROM("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
				+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='"+name+"'"
				+ ")) a2 LEFT JOIN PKC_1_2_9 a1 ON a1.COM_CODE=A2.COM_CODE ";
		if(t1.equals("0")&&t2.equals("1")){
			src += " WHERE  TYPE = '0' ";
		}else{
			src += " WHERE  TYPE = '1' ";
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
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		if (list.size()>0){
			for ( int i = 0 ; i < list.size() ; i ++){
				JSONObject obj = new JSONObject();
				obj.put("name", "".equals(list.get(i).get("COM_NAME")) || list.get(i).get("COM_NAME") == null ? "" : list.get(i).get("COM_NAME").toString());
				obj.put("z_hu", "".equals(list.get(i).get("Z_HU")) || list.get(i).get("Z_HU") == null ? "" : list.get(i).get("Z_HU").toString());
				obj.put("value", "".equals(list.get(i).get("V1")) || list.get(i).get("V1") == null ? "" : list.get(i).get("V1").toString());
				obj.put("v2", "".equals(list.get(i).get("V2")) || list.get(i).get("V2") == null ? "" : list.get(i).get("V2").toString());
				obj.put("v3", "".equals(list.get(i).get("V3")) || list.get(i).get("V3") == null ? "" : list.get(i).get("V3").toString());
				obj.put("v4", "".equals(list.get(i).get("V4")) || list.get(i).get("V4") == null ? "" : list.get(i).get("V4").toString());
				obj.put("v5", "".equals(list.get(i).get("V5")) || list.get(i).get("V5") == null ? "" : list.get(i).get("V5").toString());
				json.add(obj);
			}
			response.getWriter().write(getPaixu(json,name).toString());
		}else{
			response.getWriter().write("0");
		}
	}
	/**
	 * 贫困户上年度家庭收入情况统计
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getBfdxHuc_15.do")
	public void getBfdxHuc_15(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name = request.getParameter("name");//行政区划
		String year = request.getParameter("year");//统计年度
		String q1 = request.getParameter("q1");//旗县分类-国家重点旗县
		String q2 = request.getParameter("q2");//旗县分类-自治区重点旗县
		String q3 = request.getParameter("q3");//旗县分类-革命老区旗县
		String q4 = request.getParameter("q4");//旗县分类-牧业旗县
		String q5 = request.getParameter("q5");//旗县分类-边境旗县
		String t1 = request.getParameter("t1");//脱贫标志-返贫
		String t2 = request.getParameter("t2");//脱贫标志-现贫
		String src = "";
		if(name.equals("全部盟市")){
			name="内蒙古自治区";
		}
		String desc = request.getParameter("desc");
		JSONArray json = new JSONArray();
		String sql="SELECT a1.*,GJZDQX,ZZQZDQX,GMLQQX,MYQX,BJQX FROM("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
				+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='"+name+"'"
				+ ")) a2 LEFT JOIN PKC_1_2_10 a1 ON a1.COM_CODE=A2.COM_CODE ";
		if(t1.equals("0")&&t2.equals("1")){
			src += " WHERE  TYPE = '0' ";
		}else{
			src += " WHERE  TYPE = '1' ";
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
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		if (list.size()>0){
			for ( int i = 0 ; i < list.size() ; i ++){
				JSONObject obj = new JSONObject();
				obj.put("name", "".equals(list.get(i).get("COM_NAME")) || list.get(i).get("COM_NAME") == null ? "" : list.get(i).get("COM_NAME").toString());
				obj.put("z_hu", "".equals(list.get(i).get("Z_HU")) || list.get(i).get("Z_HU") == null ? "0" : list.get(i).get("Z_HU").toString());
				obj.put("z_ren", "".equals(list.get(i).get("Z_REN")) || list.get(i).get("Z_REN") == null ? "0" : list.get(i).get("Z_REN").toString());
				obj.put("v1", "".equals(list.get(i).get("V1")) || list.get(i).get("V1") == null ? "0" : list.get(i).get("V1").toString());
				obj.put("v2", "".equals(list.get(i).get("V2")) || list.get(i).get("V2") == null ? "0" : list.get(i).get("V2").toString());
				obj.put("v3", "".equals(list.get(i).get("V3")) || list.get(i).get("V3") == null ? "0" : list.get(i).get("V3").toString());
				obj.put("v4", "".equals(list.get(i).get("V4")) || list.get(i).get("V4") == null ? "0" : list.get(i).get("V4").toString());
				obj.put("v5", "".equals(list.get(i).get("V5")) || list.get(i).get("V5") == null ? "0" : list.get(i).get("V5").toString());
				obj.put("v6", "".equals(list.get(i).get("V6")) || list.get(i).get("V6") == null ? "0" : list.get(i).get("V6").toString());
				obj.put("v7", "".equals(list.get(i).get("V7")) || list.get(i).get("V7") == null ? "0" : list.get(i).get("V7").toString());
				obj.put("v8", "".equals(list.get(i).get("V8")) || list.get(i).get("V8") == null ? "0" : list.get(i).get("V8").toString());
				obj.put("value", "".equals(list.get(i).get("V9")) || list.get(i).get("V9") == null ? "0" : list.get(i).get("V9").toString());
				json.add(obj);
			}
			response.getWriter().write(getPaixu(json,name).toString());
		}else{
			response.getWriter().write("0");
		}
	}
	
	/**
	 * 贫困户与村主干路距离
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getBfdxHu_15.do")
	public void getBfdxHu_15(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name = request.getParameter("name");//行政区划
		String year = request.getParameter("year");//统计年度
		String q1 = request.getParameter("q1");//旗县分类-国家重点旗县
		String q2 = request.getParameter("q2");//旗县分类-自治区重点旗县
		String q3 = request.getParameter("q3");//旗县分类-革命老区旗县
		String q4 = request.getParameter("q4");//旗县分类-牧业旗县
		String q5 = request.getParameter("q5");//旗县分类-边境旗县
		String t1 = request.getParameter("t1");//脱贫标志-返贫
		String t2 = request.getParameter("t2");//脱贫标志-现贫
		String src = "";
		if(name.equals("全部盟市")){
			name="内蒙古自治区";
		}
		String desc = request.getParameter("desc");
		JSONArray json = new JSONArray();
		String sql="SELECT a1.*,GJZDQX,ZZQZDQX,GMLQQX,MYQX,BJQX FROM("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
				+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='"+name+"'"
				+ ")) a2 LEFT JOIN PKC_1_2_15 a1 ON a1.COM_CODE=A2.COM_CODE ";
		if(t1.equals("0")&&t2.equals("1")){
			src += " WHERE  TYPE = '0' ";
		}else{
			src += " WHERE  TYPE = '1' ";
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
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		if (list.size() > 0) {
			for ( int i = 0 ; i < list.size() ; i ++){
				JSONObject obj = new JSONObject();
				obj.put("com_name", "".equals(list.get(i).get("COM_NAME")) || list.get(i).get("COM_NAME") == null ? "" : list.get(i).get("COM_NAME").toString());
				obj.put("z_hu", "".equals(list.get(i).get("Z_HU")) || list.get(i).get("Z_HU") == null ? "" : list.get(i).get("Z_HU").toString());
				obj.put("v1", "".equals(list.get(i).get("V1")) || list.get(i).get("V1") == null ? "" : list.get(i).get("V1").toString());
				obj.put("v3", "".equals(list.get(i).get("V3")) || list.get(i).get("V3") == null ? "" : list.get(i).get("V3").toString());
				obj.put("v5", "".equals(list.get(i).get("V5")) || list.get(i).get("V5") == null ? "" : list.get(i).get("V5").toString());
				obj.put("v7", "".equals(list.get(i).get("V7")) || list.get(i).get("V7") == null ? "" : list.get(i).get("V7").toString());
				obj.put("v9", "".equals(list.get(i).get("V9")) || list.get(i).get("V9") == null ? "" : list.get(i).get("V9").toString());
				obj.put("v11", "".equals(list.get(i).get("V11")) || list.get(i).get("V11") == null ? "" : list.get(i).get("V11").toString());
				json.add(obj);
			}
			response.getWriter().write(getPaixu(json,name).toString());
		}else{
			response.getWriter().write("0");
		}
	}
	/**
	 * 贫困户住房面积(1.2.14)表格
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getBfdxHu_14.do")
	public void getBfdxHu_14(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name = request.getParameter("name");//行政区划
		String year = request.getParameter("year");//统计年度
		String q1 = request.getParameter("q1");//旗县分类-国家重点旗县
		String q2 = request.getParameter("q2");//旗县分类-自治区重点旗县
		String q3 = request.getParameter("q3");//旗县分类-革命老区旗县
		String q4 = request.getParameter("q4");//旗县分类-牧业旗县
		String q5 = request.getParameter("q5");//旗县分类-边境旗县
		String t1 = request.getParameter("t1");//脱贫标志-返贫
		String t2 = request.getParameter("t2");//脱贫标志-现贫
		String src = "";
		if(name.equals("全部盟市")){
			name="内蒙古自治区";
		}
		String desc = request.getParameter("desc");
		JSONArray json = new JSONArray();
		String sql="SELECT a1.*,GJZDQX,ZZQZDQX,GMLQQX,MYQX,BJQX FROM("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
				+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='"+name+"'"
				+ ")) a2 LEFT JOIN PKC_1_2_14 a1 ON a1.COM_CODE=A2.COM_CODE ";
		if(t1.equals("0")&&t2.equals("1")){
			src += " WHERE  TYPE = '0' ";
		}else{
			src += " WHERE  TYPE = '1' ";
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
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		
		if (list.size() > 0 ){
			for(int i = 0 ; i < list.size() ; i ++ ){
				JSONObject obj = new JSONObject();
				obj.put("com_name", "".equals(list.get(i).get("COM_NAME")) || list.get(i).get("COM_NAME") == null ? "" : list.get(i).get("COM_NAME").toString());
				obj.put("z_hu", "".equals(list.get(i).get("Z_HU")) || list.get(i).get("Z_HU") == null ? "0" : list.get(i).get("Z_HU").toString());
				obj.put("v1", "".equals(list.get(i).get("V1")) || list.get(i).get("V1") == null ? "0" : list.get(i).get("V1").toString());
				obj.put("v3", "".equals(list.get(i).get("V3")) || list.get(i).get("V3") == null ? "0" : list.get(i).get("V3").toString());
				obj.put("v5", "".equals(list.get(i).get("V5")) || list.get(i).get("V5") == null ? "0" : list.get(i).get("V5").toString());
				obj.put("v7", "".equals(list.get(i).get("V7")) || list.get(i).get("V7") == null ? "0" : list.get(i).get("V7").toString());
				obj.put("v9", "".equals(list.get(i).get("V9")) || list.get(i).get("V9") == null ? "0" : list.get(i).get("V9").toString());
				obj.put("v11", "".equals(list.get(i).get("V11")) || list.get(i).get("V11") == null ? "0" : list.get(i).get("V11").toString());
				obj.put("v13", "".equals(list.get(i).get("V13")) || list.get(i).get("V13") == null ? "0" : list.get(i).get("V13").toString());
				json.add(obj);
			}
			response.getWriter().write(getPaixu(json,name).toString());
		}else{
			response.getWriter().write("0");
		}
	}
	/**
	 * 贫困户党员户数
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getBfdxHu_13.do")
	public void getBfdxHu_13(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name = request.getParameter("name");//行政区划
		String year = request.getParameter("year");//统计年度
		String q1 = request.getParameter("q1");//旗县分类-国家重点旗县
		String q2 = request.getParameter("q2");//旗县分类-自治区重点旗县
		String q3 = request.getParameter("q3");//旗县分类-革命老区旗县
		String q4 = request.getParameter("q4");//旗县分类-牧业旗县
		String q5 = request.getParameter("q5");//旗县分类-边境旗县
		String t1 = request.getParameter("t1");//脱贫标志-返贫
		String t2 = request.getParameter("t2");//脱贫标志-现贫
		String src = "";
		if(name.equals("全部盟市")){
			name="内蒙古自治区";
		}
		String desc = request.getParameter("desc");
		JSONArray json = new JSONArray();
		String sql="SELECT a1.*,GJZDQX,ZZQZDQX,GMLQQX,MYQX,BJQX FROM("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
				+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='"+name+"'"
				+ ")) a2 LEFT JOIN PKC_1_2_13 a1 ON a1.COM_CODE=A2.COM_CODE ";
		if(t1.equals("0")&&t2.equals("1")){
			src += " WHERE  TYPE = '0' ";
		}else{
			src += " WHERE  TYPE = '1' ";
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
		sql = sql+src+" ORDER BY "+desc;
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		
		if (list.size() > 0 ){
			for(int i = 0 ; i < list.size() ; i ++ ){
				JSONObject obj = new JSONObject();
				obj.put("com_name", "".equals(list.get(i).get("COM_NAME")) || list.get(i).get("COM_NAME") == null ? "" : list.get(i).get("COM_NAME").toString());
				obj.put("z_hu", "".equals(list.get(i).get("Z_HU")) || list.get(i).get("Z_HU") == null ? "" : list.get(i).get("Z_HU").toString());
				obj.put("v1", "".equals(list.get(i).get("V1")) || list.get(i).get("V1") == null ? "" : list.get(i).get("V1").toString());
				obj.put("v2", "".equals(list.get(i).get("V2")) || list.get(i).get("V2") == null ? "" : list.get(i).get("V2").toString());
				json.add(obj);
			}
			if(!desc.equals("a1.V2")){
				response.getWriter().write(getPaixu(json,name).toString());
			}else{
				response.getWriter().write(json.toString());
			}
		}else{
			response.getWriter().write("0");
		}
	}
	/**
	 * 贫困户人口规模情况
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getBfdxHu_12.do")
	public void getBfdxHu_12(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name = request.getParameter("name");//行政区划
		String year = request.getParameter("year");//统计年度
		String q1 = request.getParameter("q1");//旗县分类-国家重点旗县
		String q2 = request.getParameter("q2");//旗县分类-自治区重点旗县
		String q3 = request.getParameter("q3");//旗县分类-革命老区旗县
		String q4 = request.getParameter("q4");//旗县分类-牧业旗县
		String q5 = request.getParameter("q5");//旗县分类-边境旗县
		String t1 = request.getParameter("t1");//脱贫标志-返贫
		String t2 = request.getParameter("t2");//脱贫标志-现贫
		String src = "";
		if(name.equals("全部盟市")){
			name="内蒙古自治区";
		}
		String desc = request.getParameter("desc");
		JSONArray json = new JSONArray();
		String sql="SELECT a1.*,GJZDQX,ZZQZDQX,GMLQQX,MYQX,BJQX FROM("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
				+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='"+name+"'"
				+ ")) a2 LEFT JOIN PKC_1_2_12 a1 ON a1.COM_CODE=A2.COM_CODE ";
		if(t1.equals("0")&&t2.equals("1")){
			src += " WHERE  TYPE = '0' ";
		}else{
			src += " WHERE  TYPE = '1' ";
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
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		if (list.size() > 0) {
			for ( int i = 0 ; i < list.size() ; i ++){
				JSONObject obj = new JSONObject();
				obj.put("com_name", "".equals(list.get(i).get("COM_NAME")) || list.get(i).get("COM_NAME") == null ? "" : list.get(i).get("COM_NAME").toString());
				obj.put("z_hu", "".equals(list.get(i).get("Z_HU")) || list.get(i).get("Z_HU") == null ? "" : list.get(i).get("Z_HU").toString());
				obj.put("v1", "".equals(list.get(i).get("V1")) || list.get(i).get("V1") == null ? "" : list.get(i).get("V1").toString());
				obj.put("v3", "".equals(list.get(i).get("V3")) || list.get(i).get("V3") == null ? "" : list.get(i).get("V3").toString());
				obj.put("v5", "".equals(list.get(i).get("V5")) || list.get(i).get("V5") == null ? "" : list.get(i).get("V5").toString());
				obj.put("v7", "".equals(list.get(i).get("V7")) || list.get(i).get("V7") == null ? "" : list.get(i).get("V7").toString());
				obj.put("v9", "".equals(list.get(i).get("V9")) || list.get(i).get("V9") == null ? "" : list.get(i).get("V9").toString());
				obj.put("v11", "".equals(list.get(i).get("V11")) || list.get(i).get("V11") == null ? "" : list.get(i).get("V11").toString());
				json.add(obj);
			}
			response.getWriter().write(getPaixu(json,name).toString());
		}else{
			response.getWriter().write("0");
		}
	}
	/**
	 * 表格显示：贫困户上年度转移性收入构成情况
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getBfdxHu_11.do")
	public void getBfdxHu_11(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name = request.getParameter("name");//行政区划
		String year = request.getParameter("year");//统计年度
		String q1 = request.getParameter("q1");//旗县分类-国家重点旗县
		String q2 = request.getParameter("q2");//旗县分类-自治区重点旗县
		String q3 = request.getParameter("q3");//旗县分类-革命老区旗县
		String q4 = request.getParameter("q4");//旗县分类-牧业旗县
		String q5 = request.getParameter("q5");//旗县分类-边境旗县
		String t1 = request.getParameter("t1");//脱贫标志-返贫
		String t2 = request.getParameter("t2");//脱贫标志-现贫
		String src = "";
		if(name.equals("全部盟市")){
			name="内蒙古自治区";
		}
		String desc = request.getParameter("desc");
		JSONArray json = new JSONArray();
		String sql="SELECT a1.*,GJZDQX,ZZQZDQX,GMLQQX,MYQX,BJQX FROM("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
				+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='"+name+"'"
				+ ")) a2 LEFT JOIN PKC_1_2_11 a1 ON a1.COM_CODE=A2.COM_CODE ";
		if(t1.equals("0")&&t2.equals("1")){
			src += " WHERE  TYPE = '0' ";
		}else{
			src += " WHERE  TYPE = '1' ";
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
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		if (list.size() > 0) {
			for ( int i = 0 ; i < list.size() ; i ++){
				JSONObject obj = new JSONObject();
				obj.put("com_name", "".equals(list.get(i).get("COM_NAME")) || list.get(i).get("COM_NAME") == null ? "" : list.get(i).get("COM_NAME").toString());
				obj.put("z_hu", "".equals(list.get(i).get("Z_HU")) || list.get(i).get("Z_HU") == null ? "" : list.get(i).get("Z_HU").toString());
				obj.put("v1", "".equals(list.get(i).get("V1")) || list.get(i).get("V1") == null ? "" : list.get(i).get("V1").toString());
				obj.put("v2", "".equals(list.get(i).get("V2")) || list.get(i).get("V2") == null ? "" : list.get(i).get("V2").toString());
				obj.put("v4", "".equals(list.get(i).get("V4")) || list.get(i).get("V4") == null ? "" : list.get(i).get("V4").toString());
				obj.put("v6", "".equals(list.get(i).get("V6")) || list.get(i).get("V6") == null ? "" : list.get(i).get("V6").toString());
				obj.put("v8", "".equals(list.get(i).get("V8")) || list.get(i).get("V8") == null ? "" : list.get(i).get("V8").toString());
				json.add(obj);
			}
			response.getWriter().write(getPaixu(json,name).toString());
		}else{
			response.getWriter().write("0");
		}
	}
	/**
	 * 表格显示：贫困户上年度转移性收入构成情况地图
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getBfdxHu_11_map.do")
	public void getBfdxHu_11_map(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name = request.getParameter("name");//行政区划
		String year = request.getParameter("year");//统计年度
		String q1 = request.getParameter("q1");//旗县分类-国家重点旗县
		String q2 = request.getParameter("q2");//旗县分类-自治区重点旗县
		String q3 = request.getParameter("q3");//旗县分类-革命老区旗县
		String q4 = request.getParameter("q4");//旗县分类-牧业旗县
		String q5 = request.getParameter("q5");//旗县分类-边境旗县
		String t1 = request.getParameter("t1");//脱贫标志-返贫
		String t2 = request.getParameter("t2");//脱贫标志-现贫
		String src = "";
		if(name.equals("全部盟市")){
			name="内蒙古自治区";
		}
		String desc = request.getParameter("desc");
		JSONArray json = new JSONArray();
		String sql="SELECT a1.*,GJZDQX,ZZQZDQX,GMLQQX,MYQX,BJQX FROM("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
				+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='"+name+"'"
				+ ")) a2 LEFT JOIN PKC_1_2_11 a1 ON a1.COM_CODE=A2.COM_CODE ";
		if(t1.equals("0")&&t2.equals("1")){
			src += " WHERE  TYPE = '0' ";
		}else{
			src += " WHERE  TYPE = '1' ";
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
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		if (list.size() > 0) {
			for ( int i = 0 ; i < list.size() ; i ++){
				JSONObject obj = new JSONObject();
				obj.put("name", "".equals(list.get(i).get("COM_NAME")) || list.get(i).get("COM_NAME") == null ? "" : list.get(i).get("COM_NAME").toString());
				obj.put("value", "".equals(list.get(i).get("V1")) || list.get(i).get("V1") == null ? "" : list.get(i).get("V1").toString());
				json.add(obj);
			}
			response.getWriter().write(json.toString());
		}else{
			response.getWriter().write("0");
		}
	}
}
