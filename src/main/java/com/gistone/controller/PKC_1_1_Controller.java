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
public class PKC_1_1_Controller {
	@Autowired
	private GetBySqlMapper getBySqlMapper;

	private JSONArray getPaixu(JSONArray jsa, String name) {
		if (name.equals("内蒙古自治区")) {
			JSONArray val = new JSONArray();
			String str[] = { "呼和浩特市", "包头市", "呼伦贝尔市", "兴安盟", "通辽市", "赤峰市",
					"锡林郭勒盟", "乌兰察布市", "鄂尔多斯市", "巴彦淖尔市", "乌海市", "阿拉善盟" };
			for (int k = 0; k < str.length; k++) {
				for (int i = 0; i < jsa.size(); i++) {
					JSONObject jo = JSONObject.fromObject(jsa.get(i));
					if (jo.get("V1").toString().equals(str[k])) {
						val.add(jo);
					}
				}
			}
			return val;
		} else {
			return jsa;
		}
	}

	/**
	 * @method f_1_1_1页面条形图，读取数据库为 PKC_1_1_0
	 * @param request
	 * @param response
	 * @throws IOException
	 * @author 张晓翔
	 * @date 2016年9月23日上午10:04:01
	 */
	@RequestMapping("getPKC_1_1_1.do")
	public void getPKC_1_1_1(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");// 行政区划
		String year = request.getParameter("year");// 统计年度
		String q1 = request.getParameter("q1");// 旗县分类-国家重点旗县
		String q2 = request.getParameter("q2");// 旗县分类-自治区重点旗县
		String q3 = request.getParameter("q3");// 旗县分类-革命老区旗县
		String q4 = request.getParameter("q4");// 旗县分类-牧业旗县
		String q5 = request.getParameter("q5");// 旗县分类-边境旗县
		String t1 = request.getParameter("t1");// 脱贫标志-返贫
		String t2 = request.getParameter("t2");// 脱贫标志-现贫
		String src = "";
		if (name.equals("全部盟市")) {
			name = "内蒙古自治区";
		}
		String sql = "SELECT a1.*,GJZDQX,ZZQZDQX,GMLQQX,MYQX,BJQX FROM("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
				+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='" + name + "'"
				+ ")) a2 LEFT JOIN PKC_1_1_0 a1 ON a1.V1=A2.COM_NAME ";

		if (t1.equals("0") || t2.equals("1")) {
			src += " WHERE  V9 = '0' ";
		} else {
			src += " WHERE  V9 = '1' ";
		}

		if (!name.equals("内蒙古自治区")) {
			if (q1.equals("1") || q2.equals("1") || q3.equals("1")
					|| q4.equals("1") || q5.equals("1")) {
				src += "AND(";

				if (q1.equals("1")) {
					src += "GJZDQX ='1' ";
				}
				if (q2.equals("1")) {
					if (q1.equals("1")) {
						src += " OR ";
					}
					src += " ZZQZDQX ='1'";
				}
				if (q3.equals("1")) {
					if (q1.equals("1") || q2.equals("1")) {
						src += " OR ";
					}
					src += " GMLQQX ='1'";
				}
				if (q4.equals("1")) {
					if (q1.equals("1") || q2.equals("1") || q3.equals("1")) {
						src += " OR ";
					}
					src += " MYQX ='1'";
				}
				if (q5.equals("1")) {
					if (q1.equals("1") || q2.equals("1") || q3.equals("1")
							|| q4.equals("1")) {
						src += " OR ";
					}
					src += " BJQX ='1'";
				}
				src += ")";
			}
		}

		sql = sql + src;
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		JSONArray json = new JSONArray();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map Patient_st_map = list.get(i);
				JSONObject obj = new JSONObject();
				for (Object key : Patient_st_map.keySet()) {
					obj.put(key, Patient_st_map.get(key));
				}
				json.add(obj);
			}
			response.getWriter().write(getPaixu(json, name).toString());
		} else {
			response.getWriter().write("0");
		}
	}

	/**
	 * @method f_1_1_2页面 PKC_1_1_1数据库
	 * @param request
	 * @param response
	 * @throws IOException
	 * @author 张晓翔
	 * @date 2016年9月23日上午10:20:16
	 */
	@RequestMapping("getPKC_1_1_2.do")
	public void getPKC_1_1_2(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");// 行政区划
		String year = request.getParameter("year");// 统计年度
		String q1 = request.getParameter("q1");// 旗县分类-国家重点旗县
		String q2 = request.getParameter("q2");// 旗县分类-自治区重点旗县
		String q3 = request.getParameter("q3");// 旗县分类-革命老区旗县
		String q4 = request.getParameter("q4");// 旗县分类-牧业旗县
		String q5 = request.getParameter("q5");// 旗县分类-边境旗县
		String t1 = request.getParameter("t1");// 脱贫标志-返贫
		String t2 = request.getParameter("t2");// 脱贫标志-现贫
		String src = "";
		if (name.equals("全部盟市")) {
			name = "内蒙古自治区";
		}
		String sql = "SELECT a1.*,GJZDQX,ZZQZDQX,GMLQQX,MYQX,BJQX FROM("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
				+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='" + name + "'"
				+ ")) a2 LEFT JOIN PKC_1_1_1 a1 ON a1.V1=A2.COM_NAME ";

		if (t1.equals("0") || t2.equals("1")) {
			src += " WHERE  V9 = '0' ";
		} else {
			src += " WHERE  V9 = '1' ";
		}

		if (!name.equals("内蒙古自治区")) {
			if (q1.equals("1") || q2.equals("1") || q3.equals("1")
					|| q4.equals("1") || q5.equals("1")) {
				src += "AND(";

				if (q1.equals("1")) {
					src += "GJZDQX ='1' ";
				}
				if (q2.equals("1")) {
					if (q1.equals("1")) {
						src += " OR ";
					}
					src += " ZZQZDQX ='1'";
				}
				if (q3.equals("1")) {
					if (q1.equals("1") || q2.equals("1")) {
						src += " OR ";
					}
					src += " GMLQQX ='1'";
				}
				if (q4.equals("1")) {
					if (q1.equals("1") || q2.equals("1") || q3.equals("1")) {
						src += " OR ";
					}
					src += " MYQX ='1'";
				}
				if (q5.equals("1")) {
					if (q1.equals("1") || q2.equals("1") || q3.equals("1")
							|| q4.equals("1")) {
						src += " OR ";
					}
					src += " BJQX ='1'";
				}
				src += ")";
			}
		}
		sql = sql + src;
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		JSONArray json = new JSONArray();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map Patient_st_map = list.get(i);
				JSONObject obj = new JSONObject();
				for (Object key : Patient_st_map.keySet()) {
					obj.put(key, Patient_st_map.get(key));
				}
				json.add(obj);
			}
			response.getWriter().write(json.toString());
		} else {
			response.getWriter().write("0");
		}
	}

	/**
	 * @method f_1_1_2页面 PKC_1_1_1数据库 右侧的条形图
	 * @param request
	 * @param response
	 * @throws IOException
	 * @author 张晓翔
	 * @date 2016年9月23日下午2:11:55
	 */
	@RequestMapping("getPKC_1_1_2_2.do")
	public void getPKC_1_1_2_2(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");// 行政区划
		String year = request.getParameter("year");// 统计年度
		String q1 = request.getParameter("q1");// 旗县分类-国家重点旗县
		String q2 = request.getParameter("q2");// 旗县分类-自治区重点旗县
		String q3 = request.getParameter("q3");// 旗县分类-革命老区旗县
		String q4 = request.getParameter("q4");// 旗县分类-牧业旗县
		String q5 = request.getParameter("q5");// 旗县分类-边境旗县
		String t1 = request.getParameter("t1");// 脱贫标志-返贫
		String t2 = request.getParameter("t2");// 脱贫标志-现贫
		String src = "";
		if (name.equals("全部盟市")) {
			name = "内蒙古自治区";
		}
		String sql = "SELECT V1,V8_1,GJZDQX,ZZQZDQX,GMLQQX,MYQX,BJQX FROM("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
				+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='" + name + "'"
				+ ")) a2 LEFT JOIN PKC_1_1_1 a1 ON a1.V1=A2.COM_NAME ";

		if (t1.equals("0") || t2.equals("1")) {
			src += " WHERE  V9 = '0' ";
		} else {
			src += " WHERE  V9 = '1' ";
		}

		if (!name.equals("内蒙古自治区")) {
			if (q1.equals("1") || q2.equals("1") || q3.equals("1")
					|| q4.equals("1") || q5.equals("1")) {
				src += "AND(";

				if (q1.equals("1")) {
					src += "GJZDQX ='1' ";
				}
				if (q2.equals("1")) {
					if (q1.equals("1")) {
						src += " OR ";
					}
					src += " ZZQZDQX ='1'";
				}
				if (q3.equals("1")) {
					if (q1.equals("1") || q2.equals("1")) {
						src += " OR ";
					}
					src += " GMLQQX ='1'";
				}
				if (q4.equals("1")) {
					if (q1.equals("1") || q2.equals("1") || q3.equals("1")) {
						src += " OR ";
					}
					src += " MYQX ='1'";
				}
				if (q5.equals("1")) {
					if (q1.equals("1") || q2.equals("1") || q3.equals("1")
							|| q4.equals("1")) {
						src += " OR ";
					}
					src += " BJQX ='1'";
				}
				src += ")";
			}
		}

		src += " ORDER BY V8_1 DESC";
		sql = sql + src;
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		JSONArray json = new JSONArray();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map Patient_st_map = list.get(i);
				JSONObject obj = new JSONObject();
				for (Object key : Patient_st_map.keySet()) {
					obj.put(key, Patient_st_map.get(key));
				}
				json.add(obj);
			}
			response.getWriter().write(json.toString());
		} else {
			response.getWriter().write("0");
		}
	}

	/**
	 * @method f_1_1_2页面 PKC_1_1_1数据库 下方表格
	 * @param request
	 * @param response
	 * @throws IOException
	 * @author 张晓翔
	 * @date 2016年9月23日下午2:31:45
	 */
	@RequestMapping("getPKC_1_1_2_3.do")
	public void getPKC_1_1_2_3(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");// 行政区划
		String year = request.getParameter("year");// 统计年度
		String q1 = request.getParameter("q1");// 旗县分类-国家重点旗县
		String q2 = request.getParameter("q2");// 旗县分类-自治区重点旗县
		String q3 = request.getParameter("q3");// 旗县分类-革命老区旗县
		String q4 = request.getParameter("q4");// 旗县分类-牧业旗县
		String q5 = request.getParameter("q5");// 旗县分类-边境旗县
		String t1 = request.getParameter("t1");// 脱贫标志-返贫
		String t2 = request.getParameter("t2");// 脱贫标志-现贫
		String src = "";
		if (name.equals("全部盟市")) {
			name = "内蒙古自治区";
		}
		String sql = "SELECT a1.*,GJZDQX,ZZQZDQX,GMLQQX,MYQX,BJQX FROM("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
				+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='" + name + "'"
				+ ")) a2 LEFT JOIN PKC_1_1_1 a1 ON a1.V1=A2.COM_NAME ";

		if (t1.equals("0") || t2.equals("1")) {
			src += " WHERE  V9 = '0' ";
		} else {
			src += " WHERE  V9 = '1' ";
		}

		if (!name.equals("内蒙古自治区")) {
			if (q1.equals("1") || q2.equals("1") || q3.equals("1")
					|| q4.equals("1") || q5.equals("1")) {
				src += "AND(";

				if (q1.equals("1")) {
					src += "GJZDQX ='1' ";
				}
				if (q2.equals("1")) {
					if (q1.equals("1")) {
						src += " OR ";
					}
					src += " ZZQZDQX ='1'";
				}
				if (q3.equals("1")) {
					if (q1.equals("1") || q2.equals("1")) {
						src += " OR ";
					}
					src += " GMLQQX ='1'";
				}
				if (q4.equals("1")) {
					if (q1.equals("1") || q2.equals("1") || q3.equals("1")) {
						src += " OR ";
					}
					src += " MYQX ='1'";
				}
				if (q5.equals("1")) {
					if (q1.equals("1") || q2.equals("1") || q3.equals("1")
							|| q4.equals("1")) {
						src += " OR ";
					}
					src += " BJQX ='1'";
				}
				src += ")";
			}
		}
		sql = sql + src;
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		JSONArray json = new JSONArray();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map Patient_st_map = list.get(i);
				JSONObject obj = new JSONObject();
				for (Object key : Patient_st_map.keySet()) {
					obj.put(key, Patient_st_map.get(key));
				}
				json.add(obj);
			}
			response.getWriter().write(getPaixu(json, name).toString());
		} else {
			response.getWriter().write("0");
		}
	}

	/**
	 * @method f_1_1_3页面 条形图
	 * @param request
	 * @param response
	 * @throws IOException
	 * @author 张晓翔
	 * @date 2016年9月23日下午4:23:15
	 */
	@RequestMapping("getPKC_1_1_3.do")
	public void getPKC_1_1_3(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");// 行政区划
		String year = request.getParameter("year");// 统计年度
		String q1 = request.getParameter("q1");// 旗县分类-国家重点旗县
		String q2 = request.getParameter("q2");// 旗县分类-自治区重点旗县
		String q3 = request.getParameter("q3");// 旗县分类-革命老区旗县
		String q4 = request.getParameter("q4");// 旗县分类-牧业旗县
		String q5 = request.getParameter("q5");// 旗县分类-边境旗县
		String t1 = request.getParameter("t1");// 脱贫标志-返贫
		String t2 = request.getParameter("t2");// 脱贫标志-现贫
		String src = "";
		if (name.equals("全部盟市")) {
			name = "内蒙古自治区";
		}
		String sql = "SELECT a1.*,GJZDQX,ZZQZDQX,GMLQQX,MYQX,BJQX FROM("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
				+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='" + name + "'"
				+ ")) a2 LEFT JOIN PKC_1_1_2 a1 ON a1.V1=A2.COM_NAME ";

		if (t1.equals("0") || t2.equals("1")) {
			src += " WHERE  V9 = '0' ";
		} else {
			src += " WHERE  V9 = '1' ";
		}

		if (!name.equals("内蒙古自治区")) {
			if (q1.equals("1") || q2.equals("1") || q3.equals("1")
					|| q4.equals("1") || q5.equals("1")) {
				src += "AND(";

				if (q1.equals("1")) {
					src += "GJZDQX ='1' ";
				}
				if (q2.equals("1")) {
					if (q1.equals("1")) {
						src += " OR ";
					}
					src += " ZZQZDQX ='1'";
				}
				if (q3.equals("1")) {
					if (q1.equals("1") || q2.equals("1")) {
						src += " OR ";
					}
					src += " GMLQQX ='1'";
				}
				if (q4.equals("1")) {
					if (q1.equals("1") || q2.equals("1") || q3.equals("1")) {
						src += " OR ";
					}
					src += " MYQX ='1'";
				}
				if (q5.equals("1")) {
					if (q1.equals("1") || q2.equals("1") || q3.equals("1")
							|| q4.equals("1")) {
						src += " OR ";
					}
					src += " BJQX ='1'";
				}
				src += ")";
			}
		}
		sql = sql + src;
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		JSONArray json = new JSONArray();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map Patient_st_map = list.get(i);
				JSONObject obj = new JSONObject();
				for (Object key : Patient_st_map.keySet()) {
					obj.put(key, Patient_st_map.get(key));
				}
				json.add(obj);
			}
			response.getWriter().write(getPaixu(json, name).toString());
		} else {
			response.getWriter().write("0");
		}
	}

	/**
	 * @method f_1_1_3页面 地图
	 * @param request
	 * @param response
	 * @throws IOException
	 * @author 张晓翔
	 * @date 2016年9月23日下午4:38:57
	 */
	// @RequestMapping("getPKC_1_1_3_3.do")
	// public void getPKC_1_1_3_3(HttpServletRequest request,HttpServletResponse
	// response) throws IOException{
	// String name = request.getParameter("name");//行政区划
	// String tjnd = "";//统计年度
	// String qxfl = "";//旗县分类
	// String tpbz = "0";//脱贫标志
	// String src = "";
	// String sql = "SELECT V1,V4 FROM PKC_1_1_2 WHERE PKID != 13 AND ";
	// if(name.equals("内蒙古自治区")){
	// src += " PKID <20 ";
	// }else{
	//
	// }
	// if(tpbz.equals("0")){
	// src += " AND  V9 = '0' ";
	// }else{
	// src += " AND  V9 = '1' ";
	// }
	// sql = sql+src;
	// List<Map> list = this.getBySqlMapper.findRecords(sql);
	// JSONArray json = new JSONArray();
	// if(list.size() > 0){
	// for(int i = 0 ; i < list.size(); i ++){
	// JSONObject obj = new JSONObject();
	// obj.put("name", list.get(i).get("V1").toString());
	// obj.put("value",Integer.parseInt(list.get(i).get("V4").toString()));
	// json.add(obj);
	// }
	// response.getWriter().write(json.toString());
	// }else{
	// response.getWriter().write("0");
	// }
	// }
	/**
	 * @method f_1_1_4页面 PKC_1_1_3数据库
	 * @param request
	 * @param response
	 * @throws IOException
	 * @author 张晓翔
	 * @date 2016年9月23日下午5:20:19
	 */
	@RequestMapping("getPKC_1_1_4.do")
	public void getPKC_1_1_4(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");// 行政区划
		String year = request.getParameter("year");// 统计年度
		String q1 = request.getParameter("q1");// 旗县分类-国家重点旗县
		String q2 = request.getParameter("q2");// 旗县分类-自治区重点旗县
		String q3 = request.getParameter("q3");// 旗县分类-革命老区旗县
		String q4 = request.getParameter("q4");// 旗县分类-牧业旗县
		String q5 = request.getParameter("q5");// 旗县分类-边境旗县
		String t1 = request.getParameter("t1");// 脱贫标志-返贫
		String t2 = request.getParameter("t2");// 脱贫标志-现贫
		String src = "";
		if (name.equals("全部盟市")) {
			name = "内蒙古自治区";
		}
		String sql = "SELECT a1.*,GJZDQX,ZZQZDQX,GMLQQX,MYQX,BJQX FROM("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
				+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='" + name + "'"
				+ ")) a2 LEFT JOIN PKC_1_1_3 a1 ON a1.V1=A2.COM_NAME ";

		if (t1.equals("0") || t2.equals("1")) {
			src += " WHERE  V9 = '0' ";
		} else {
			src += " WHERE  V9 = '1' ";
		}

		if (!name.equals("内蒙古自治区")) {
			if (q1.equals("1") || q2.equals("1") || q3.equals("1")
					|| q4.equals("1") || q5.equals("1")) {
				src += "AND(";

				if (q1.equals("1")) {
					src += "GJZDQX ='1' ";
				}
				if (q2.equals("1")) {
					if (q1.equals("1")) {
						src += " OR ";
					}
					src += " ZZQZDQX ='1'";
				}
				if (q3.equals("1")) {
					if (q1.equals("1") || q2.equals("1")) {
						src += " OR ";
					}
					src += " GMLQQX ='1'";
				}
				if (q4.equals("1")) {
					if (q1.equals("1") || q2.equals("1") || q3.equals("1")) {
						src += " OR ";
					}
					src += " MYQX ='1'";
				}
				if (q5.equals("1")) {
					if (q1.equals("1") || q2.equals("1") || q3.equals("1")
							|| q4.equals("1")) {
						src += " OR ";
					}
					src += " BJQX ='1'";
				}
				src += ") ORDER BY a1.PKID";
			}
		} else {
			src += " ORDER BY a1.PKID";
		}
		sql = sql + src; // ORDER BY a1.PKID
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		JSONArray json = new JSONArray();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map Patient_st_map = list.get(i);
				JSONObject obj = new JSONObject();
				for (Object key : Patient_st_map.keySet()) {
					obj.put(key, Patient_st_map.get(key));
				}
				json.add(obj);
			}
			response.getWriter().write(getPaixu(json, name).toString());
		} else {
			response.getWriter().write("0");
		}
	}

	/**
	 * @method f_1_1_5页面 PKC_1_1_4数据库
	 * @param request
	 * @param response
	 * @throws IOException
	 * @author 张晓翔
	 * @date 2016年9月23日下午7:19:30
	 */
	@RequestMapping("getPKC_1_1_5.do")
	public void getPKC_1_1_5(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");// 行政区划
		String year = request.getParameter("year");// 统计年度
		String q1 = request.getParameter("q1");// 旗县分类-国家重点旗县
		String q2 = request.getParameter("q2");// 旗县分类-自治区重点旗县
		String q3 = request.getParameter("q3");// 旗县分类-革命老区旗县
		String q4 = request.getParameter("q4");// 旗县分类-牧业旗县
		String q5 = request.getParameter("q5");// 旗县分类-边境旗县
		String t1 = request.getParameter("t1");// 脱贫标志-返贫
		String t2 = request.getParameter("t2");// 脱贫标志-现贫
		String src = "";
		if (name.equals("全部盟市")) {
			name = "内蒙古自治区";
		}
		String sql = "SELECT a1.*,GJZDQX,ZZQZDQX,GMLQQX,MYQX,BJQX FROM("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
				+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='" + name + "'"
				+ ")) a2 LEFT JOIN PKC_1_1_4 a1 ON a1.V1=A2.COM_NAME ";

		if (t1.equals("0") || t2.equals("1")) {
			src += " WHERE  V9 = '0' ";
		} else {
			src += " WHERE  V9 = '1' ";
		}

		if (!name.equals("内蒙古自治区")) {
			if (q1.equals("1") || q2.equals("1") || q3.equals("1")
					|| q4.equals("1") || q5.equals("1")) {
				src += "AND(";

				if (q1.equals("1")) {
					src += "GJZDQX ='1' ";
				}
				if (q2.equals("1")) {
					if (q1.equals("1")) {
						src += " OR ";
					}
					src += " ZZQZDQX ='1'";
				}
				if (q3.equals("1")) {
					if (q1.equals("1") || q2.equals("1")) {
						src += " OR ";
					}
					src += " GMLQQX ='1'";
				}
				if (q4.equals("1")) {
					if (q1.equals("1") || q2.equals("1") || q3.equals("1")) {
						src += " OR ";
					}
					src += " MYQX ='1'";
				}
				if (q5.equals("1")) {
					if (q1.equals("1") || q2.equals("1") || q3.equals("1")
							|| q4.equals("1")) {
						src += " OR ";
					}
					src += " BJQX ='1'";
				}
				src += ")";
			}
		}
		sql = sql + src;
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		JSONArray json = new JSONArray();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map Patient_st_map = list.get(i);
				JSONObject obj = new JSONObject();
				for (Object key : Patient_st_map.keySet()) {
					obj.put(key, Patient_st_map.get(key));
				}
				json.add(obj);
			}
			response.getWriter().write(getPaixu(json, name).toString());
		} else {
			response.getWriter().write("0");
		}
	}

	/**
	 * @method f_1_1_6页面
	 * @param request
	 * @param response
	 * @throws IOException
	 * @author 张晓翔
	 * @date 2016年9月24日下午3:48:07
	 */
	@RequestMapping("getPKC_1_1_6.do")
	public void getPKC_1_1_6(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");// 行政区划
		String year = request.getParameter("year");// 统计年度
		String q1 = request.getParameter("q1");// 旗县分类-国家重点旗县
		String q2 = request.getParameter("q2");// 旗县分类-自治区重点旗县
		String q3 = request.getParameter("q3");// 旗县分类-革命老区旗县
		String q4 = request.getParameter("q4");// 旗县分类-牧业旗县
		String q5 = request.getParameter("q5");// 旗县分类-边境旗县
		String t1 = request.getParameter("t1");// 脱贫标志-返贫
		String t2 = request.getParameter("t2");// 脱贫标志-现贫
		String src = "";
		if (name.equals("全部盟市")) {
			name = "内蒙古自治区";
		}
		String sql = "SELECT a1.*,GJZDQX,ZZQZDQX,GMLQQX,MYQX,BJQX FROM("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
				+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='" + name + "'"
				+ ")) a2 LEFT JOIN PKC_1_1_5 a1 ON a1.V1=A2.COM_NAME ";

		if (t1.equals("0") || t2.equals("1")) {
			src += " WHERE  V9 = '0' ";
		} else {
			src += " WHERE  V9 = '1' ";
		}

		if (!name.equals("内蒙古自治区")) {
			if (q1.equals("1") || q2.equals("1") || q3.equals("1")
					|| q4.equals("1") || q5.equals("1")) {
				src += "AND(";

				if (q1.equals("1")) {
					src += "GJZDQX ='1' ";
				}
				if (q2.equals("1")) {
					if (q1.equals("1")) {
						src += " OR ";
					}
					src += " ZZQZDQX ='1'";
				}
				if (q3.equals("1")) {
					if (q1.equals("1") || q2.equals("1")) {
						src += " OR ";
					}
					src += " GMLQQX ='1'";
				}
				if (q4.equals("1")) {
					if (q1.equals("1") || q2.equals("1") || q3.equals("1")) {
						src += " OR ";
					}
					src += " MYQX ='1'";
				}
				if (q5.equals("1")) {
					if (q1.equals("1") || q2.equals("1") || q3.equals("1")
							|| q4.equals("1")) {
						src += " OR ";
					}
					src += " BJQX ='1'";
				}
				src += ")";
			}
		}
		sql = sql + src;
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		JSONArray json = new JSONArray();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map Patient_st_map = list.get(i);
				JSONObject obj = new JSONObject();
				for (Object key : Patient_st_map.keySet()) {
					obj.put(key, Patient_st_map.get(key));
				}
				json.add(obj);
			}
			response.getWriter().write(getPaixu(json, name).toString());
		} else {
			response.getWriter().write("0");
		}
	}

	/**
	 * @method f_1_1_7页面
	 * @param request
	 * @param response
	 * @throws IOException
	 * @author 张晓翔
	 * @date 2016年9月24日下午4:25:32
	 */
	@RequestMapping("getPKC_1_1_7.do")
	public void getPKC_1_1_7(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");// 行政区划
		String year = request.getParameter("year");// 统计年度
		String q1 = request.getParameter("q1");// 旗县分类-国家重点旗县
		String q2 = request.getParameter("q2");// 旗县分类-自治区重点旗县
		String q3 = request.getParameter("q3");// 旗县分类-革命老区旗县
		String q4 = request.getParameter("q4");// 旗县分类-牧业旗县
		String q5 = request.getParameter("q5");// 旗县分类-边境旗县
		String t1 = request.getParameter("t1");// 脱贫标志-返贫
		String t2 = request.getParameter("t2");// 脱贫标志-现贫
		String src = "";
		if (name.equals("全部盟市")) {
			name = "内蒙古自治区";
		}
		String sql = "SELECT a1.*,GJZDQX,ZZQZDQX,GMLQQX,MYQX,BJQX FROM("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
				+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='" + name + "'"
				+ ")) a2 LEFT JOIN PKC_1_1_6 a1 ON a1.V1=A2.COM_NAME ";

		if (t1.equals("0") || t2.equals("1")) {
			src += " WHERE  V9 = '0' ";
		} else {
			src += " WHERE  V9 = '1' ";
		}

		if (!name.equals("内蒙古自治区")) {
			if (q1.equals("1") || q2.equals("1") || q3.equals("1")
					|| q4.equals("1") || q5.equals("1")) {
				src += "AND(";

				if (q1.equals("1")) {
					src += "GJZDQX ='1' ";
				}
				if (q2.equals("1")) {
					if (q1.equals("1")) {
						src += " OR ";
					}
					src += " ZZQZDQX ='1'";
				}
				if (q3.equals("1")) {
					if (q1.equals("1") || q2.equals("1")) {
						src += " OR ";
					}
					src += " GMLQQX ='1'";
				}
				if (q4.equals("1")) {
					if (q1.equals("1") || q2.equals("1") || q3.equals("1")) {
						src += " OR ";
					}
					src += " MYQX ='1'";
				}
				if (q5.equals("1")) {
					if (q1.equals("1") || q2.equals("1") || q3.equals("1")
							|| q4.equals("1")) {
						src += " OR ";
					}
					src += " BJQX ='1'";
				}
				src += ")";
			}
		}
		sql = sql + src;
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		JSONArray json = new JSONArray();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map Patient_st_map = list.get(i);
				JSONObject obj = new JSONObject();
				for (Object key : Patient_st_map.keySet()) {
					obj.put(key, Patient_st_map.get(key));
				}
				json.add(obj);
			}
			response.getWriter().write(json.toString());
		} else {
			response.getWriter().write("0");
		}
	}

	/**
	 * @method f_1_1_8页面
	 * @param request
	 * @param response
	 * @throws IOException
	 * @author 张晓翔
	 * @date 2016年9月24日下午4:31:47
	 */
	@RequestMapping("getPKC_1_1_8.do")
	public void getPKC_1_1_8(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");// 行政区划
		String year = request.getParameter("year");// 统计年度
		String q1 = request.getParameter("q1");// 旗县分类-国家重点旗县
		String q2 = request.getParameter("q2");// 旗县分类-自治区重点旗县
		String q3 = request.getParameter("q3");// 旗县分类-革命老区旗县
		String q4 = request.getParameter("q4");// 旗县分类-牧业旗县
		String q5 = request.getParameter("q5");// 旗县分类-边境旗县
		String t1 = request.getParameter("t1");// 脱贫标志-返贫
		String t2 = request.getParameter("t2");// 脱贫标志-现贫
		String src = "";
		if (name.equals("全部盟市")) {
			name = "内蒙古自治区";
		}
		String sql = "SELECT a1.*,GJZDQX,ZZQZDQX,GMLQQX,MYQX,BJQX FROM("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
				+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='" + name + "'"
				+ ")) a2 LEFT JOIN PKC_1_1_7 a1 ON a1.V1=A2.COM_NAME ";

		if (t1.equals("0") || t2.equals("1")) {
			src += " WHERE  V9 = '0' ";
		} else {
			src += " WHERE  V9 = '1' ";
		}

		if (!name.equals("内蒙古自治区")) {
			if (q1.equals("1") || q2.equals("1") || q3.equals("1")
					|| q4.equals("1") || q5.equals("1")) {
				src += "AND(";

				if (q1.equals("1")) {
					src += "GJZDQX ='1' ";
				}
				if (q2.equals("1")) {
					if (q1.equals("1")) {
						src += " OR ";
					}
					src += " ZZQZDQX ='1'";
				}
				if (q3.equals("1")) {
					if (q1.equals("1") || q2.equals("1")) {
						src += " OR ";
					}
					src += " GMLQQX ='1'";
				}
				if (q4.equals("1")) {
					if (q1.equals("1") || q2.equals("1") || q3.equals("1")) {
						src += " OR ";
					}
					src += " MYQX ='1'";
				}
				if (q5.equals("1")) {
					if (q1.equals("1") || q2.equals("1") || q3.equals("1")
							|| q4.equals("1")) {
						src += " OR ";
					}
					src += " BJQX ='1'";
				}
				src += ")";
			}
		}
		sql = sql + src;
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		JSONArray json = new JSONArray();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map Patient_st_map = list.get(i);
				JSONObject obj = new JSONObject();
				for (Object key : Patient_st_map.keySet()) {
					obj.put(key, Patient_st_map.get(key));
				}
				json.add(obj);
			}
			response.getWriter().write(json.toString());
		} else {
			response.getWriter().write("0");
		}
	}

	/**
	 * @method f_1_1_9页面
	 * @param request
	 * @param response
	 * @throws IOException
	 * @author 张晓翔
	 * @date 2016年9月24日下午5:09:37
	 */
	@RequestMapping("getPKC_1_1_9.do")
	public void getPKC_1_1_9(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");// 行政区划
		String year = request.getParameter("year");// 统计年度
		String q1 = request.getParameter("q1");// 旗县分类-国家重点旗县
		String q2 = request.getParameter("q2");// 旗县分类-自治区重点旗县
		String q3 = request.getParameter("q3");// 旗县分类-革命老区旗县
		String q4 = request.getParameter("q4");// 旗县分类-牧业旗县
		String q5 = request.getParameter("q5");// 旗县分类-边境旗县
		String t1 = request.getParameter("t1");// 脱贫标志-返贫
		String t2 = request.getParameter("t2");// 脱贫标志-现贫
		String src = "";
		if (name.equals("全部盟市")) {
			name = "内蒙古自治区";
		}
		String sql = "SELECT a1.*,GJZDQX,ZZQZDQX,GMLQQX,MYQX,BJQX FROM("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
				+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='" + name + "'"
				+ ")) a2 LEFT JOIN PKC_1_1_8 a1 ON a1.V1=A2.COM_NAME ";

		if (t1.equals("0") || t2.equals("1")) {
			src += " WHERE  V9 = '0' ";
		} else {
			src += " WHERE  V9 = '1' ";
		}

		if (!name.equals("内蒙古自治区")) {
			if (q1.equals("1") || q2.equals("1") || q3.equals("1")
					|| q4.equals("1") || q5.equals("1")) {
				src += "AND(";

				if (q1.equals("1")) {
					src += "GJZDQX ='1' ";
				}
				if (q2.equals("1")) {
					if (q1.equals("1")) {
						src += " OR ";
					}
					src += " ZZQZDQX ='1'";
				}
				if (q3.equals("1")) {
					if (q1.equals("1") || q2.equals("1")) {
						src += " OR ";
					}
					src += " GMLQQX ='1'";
				}
				if (q4.equals("1")) {
					if (q1.equals("1") || q2.equals("1") || q3.equals("1")) {
						src += " OR ";
					}
					src += " MYQX ='1'";
				}
				if (q5.equals("1")) {
					if (q1.equals("1") || q2.equals("1") || q3.equals("1")
							|| q4.equals("1")) {
						src += " OR ";
					}
					src += " BJQX ='1'";
				}
				src += ")";
			}
		}
		sql = sql + src;
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		JSONArray json = new JSONArray();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map Patient_st_map = list.get(i);
				JSONObject obj = new JSONObject();
				for (Object key : Patient_st_map.keySet()) {
					obj.put(key, Patient_st_map.get(key));
				}
				json.add(obj);
			}
			response.getWriter().write(getPaixu(json, name).toString());
		} else {
			response.getWriter().write("0");
		}
	}

	/**
	 * @method f_1_1_10页面
	 * @param request
	 * @param response
	 * @throws IOException
	 * @author 张晓翔
	 * @date 2016年9月24日下午6:16:01
	 */
	@RequestMapping("getPKC_1_1_10.do")
	public void getPKC_1_1_10(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");// 行政区划
		String year = request.getParameter("year");// 统计年度
		String q1 = request.getParameter("q1");// 旗县分类-国家重点旗县
		String q2 = request.getParameter("q2");// 旗县分类-自治区重点旗县
		String q3 = request.getParameter("q3");// 旗县分类-革命老区旗县
		String q4 = request.getParameter("q4");// 旗县分类-牧业旗县
		String q5 = request.getParameter("q5");// 旗县分类-边境旗县
		String t1 = request.getParameter("t1");// 脱贫标志-返贫
		String t2 = request.getParameter("t2");// 脱贫标志-现贫
		String src = "";
		if (name.equals("全部盟市")) {
			name = "内蒙古自治区";
		}
		String sql = "SELECT a1.*,GJZDQX,ZZQZDQX,GMLQQX,MYQX,BJQX FROM("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
				+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='" + name + "'"
				+ ")) a2 LEFT JOIN PKC_1_1_9 a1 ON a1.V1=A2.COM_NAME ";

		if (t1.equals("0") || t2.equals("1")) {
			src += " WHERE  V9 = '0' ";
		} else {
			src += " WHERE  V9 = '1' ";
		}

		if (!name.equals("内蒙古自治区")) {
			if (q1.equals("1") || q2.equals("1") || q3.equals("1")
					|| q4.equals("1") || q5.equals("1")) {
				src += "AND(";

				if (q1.equals("1")) {
					src += "GJZDQX ='1' ";
				}
				if (q2.equals("1")) {
					if (q1.equals("1")) {
						src += " OR ";
					}
					src += " ZZQZDQX ='1'";
				}
				if (q3.equals("1")) {
					if (q1.equals("1") || q2.equals("1")) {
						src += " OR ";
					}
					src += " GMLQQX ='1'";
				}
				if (q4.equals("1")) {
					if (q1.equals("1") || q2.equals("1") || q3.equals("1")) {
						src += " OR ";
					}
					src += " MYQX ='1'";
				}
				if (q5.equals("1")) {
					if (q1.equals("1") || q2.equals("1") || q3.equals("1")
							|| q4.equals("1")) {
						src += " OR ";
					}
					src += " BJQX ='1'";
				}
				src += ")";
			}
		}
		sql = sql + src;
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		JSONArray json = new JSONArray();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map Patient_st_map = list.get(i);
				JSONObject obj = new JSONObject();
				for (Object key : Patient_st_map.keySet()) {
					obj.put(key, Patient_st_map.get(key));
				}
				json.add(obj);
			}
			response.getWriter().write(getPaixu(json, name).toString());
		} else {
			response.getWriter().write("0");
		}
	}

	/**
	 * @method f_1_1_10页面
	 * @param request
	 * @param response
	 * @throws IOException
	 * @author 张晓翔
	 * @date 2016年9月24日下午6:28:17
	 */
	@RequestMapping("getPKC_1_1_10_3.do")
	public void getPKC_1_1_10_3(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");// 行政区划
		String year = request.getParameter("year");// 统计年度
		String q1 = request.getParameter("q1");// 旗县分类-国家重点旗县
		String q2 = request.getParameter("q2");// 旗县分类-自治区重点旗县
		String q3 = request.getParameter("q3");// 旗县分类-革命老区旗县
		String q4 = request.getParameter("q4");// 旗县分类-牧业旗县
		String q5 = request.getParameter("q5");// 旗县分类-边境旗县
		String t1 = request.getParameter("t1");// 脱贫标志-返贫
		String t2 = request.getParameter("t2");// 脱贫标志-现贫
		String src = "";
		if (name.equals("全部盟市")) {
			name = "内蒙古自治区";
		}
		String sql = "SELECT a1.*,GJZDQX,ZZQZDQX,GMLQQX,MYQX,BJQX FROM("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
				+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='" + name + "'"
				+ ")) a2 LEFT JOIN PKC_1_1_9 a1 ON a1.V1=A2.COM_NAME ";

		if (t1.equals("0") || t2.equals("1")) {
			src += " WHERE  V9 = '0' ";
		} else {
			src += " WHERE  V9 = '1' ";
		}

		if (!name.equals("内蒙古自治区")) {
			if (q1.equals("1") || q2.equals("1") || q3.equals("1")
					|| q4.equals("1") || q5.equals("1")) {
				src += "AND(";

				if (q1.equals("1")) {
					src += "GJZDQX ='1' ";
				}
				if (q2.equals("1")) {
					if (q1.equals("1")) {
						src += " OR ";
					}
					src += " ZZQZDQX ='1'";
				}
				if (q3.equals("1")) {
					if (q1.equals("1") || q2.equals("1")) {
						src += " OR ";
					}
					src += " GMLQQX ='1'";
				}
				if (q4.equals("1")) {
					if (q1.equals("1") || q2.equals("1") || q3.equals("1")) {
						src += " OR ";
					}
					src += " MYQX ='1'";
				}
				if (q5.equals("1")) {
					if (q1.equals("1") || q2.equals("1") || q3.equals("1")
							|| q4.equals("1")) {
						src += " OR ";
					}
					src += " BJQX ='1'";
				}
				src += ")";
			}
		}
		sql = sql + src;
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		JSONArray json = new JSONArray();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map Patient_st_map = list.get(i);
				JSONObject obj = new JSONObject();
				for (Object key : Patient_st_map.keySet()) {
					obj.put("name", Patient_st_map.get("V1"));
					obj.put("value", Patient_st_map.get("V4"));
				}
				json.add(obj);
			}
			response.getWriter().write(json.toString());
		} else {
			response.getWriter().write("0");
		}
	}
}
