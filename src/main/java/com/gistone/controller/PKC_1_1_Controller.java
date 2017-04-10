package com.gistone.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
				+ ")) a2 LEFT JOIN PKC_1_1_0 a1 ON a1.V10=A2.COM_CODE ";

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
	 * 获取日记统计模块数据
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getF_1_1_1.do")
	public void getF_1_1_1(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");// 行政区划
		String sTime = request.getParameter("sTime");//开始时间
		String eTime = request.getParameter("eTime");//结束时间
		int t = 0;//用于截取行政区划code时的长度
		String sqlTj = "select * from (";//拼接的sql条件
		//查询行政区划,获取行政区划code
		String sql = "SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=("
		+ "SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='" + name + "'"
		+ ") ";

		/*--镇    150783015
		SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=(SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='中和镇')
		--旗    1507830
		SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=(SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='扎兰屯市')
		--市    1507
		SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=(SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='呼伦贝尔市')
		--盟    15
		SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=(SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='内蒙古自治区')*/
		List l = new ArrayList();//存储地区名
		List lev = new ArrayList();//存储地区所属层级
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		JSONArray json = new JSONArray();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map Patient_st_map = list.get(i);
				l.add(Patient_st_map.get("COM_NAME"));
				lev.add(Patient_st_map.get("COM_LEVEL"));
				//判断当前下钻层级，截取code
				if(Integer.valueOf(Patient_st_map.get("COM_LEVEL").toString())==2){
					t=4;
				}else if(Integer.valueOf(Patient_st_map.get("COM_LEVEL").toString())==3){
					t=7;
				}else if(Integer.valueOf(Patient_st_map.get("COM_LEVEL").toString())==4){
					t=9;
				}else if(Integer.valueOf(Patient_st_map.get("COM_LEVEL").toString())==5){
					t=12;
				}else{
					t=4;
				}
				if(i<list.size()-1){
					sqlTj+="SELECT	count(*) AS THE_ALL,COUNT (	CASE WHEN TO_CHAR (TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),	'yyyy-mm-dd') = TO_CHAR (SYSDATE, 'yyyy-mm-dd') THEN"
							+ "	'a00' END) the_day,	COUNT (	CASE WHEN TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')> TO_CHAR (trunc(sysdate-7), 'yyyy-mm-dd') "
							+ "and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (sysdate, 'yyyy-mm-dd') THEN	'a01' END	) the_one_week,	COUNT (	CASE	"
							+ "	WHEN TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')> TO_CHAR (trunc(sysdate-14), 'yyyy-mm-dd') and TO_CHAR(TO_DATE "
							+ "(registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (sysdate, 'yyyy-mm-dd') THEN			'a02'		END	) the_two_week,	COUNT (		CASE	"
							+ "	WHEN TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')> TO_CHAR (trunc(sysdate-30), 'yyyy-mm-dd') and TO_CHAR(TO_DATE "
							+ "(registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (sysdate, 'yyyy-mm-dd') THEN			'a03'		END	) the_one_month,	COUNT (		CASE	"
							+ "	WHEN TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')> TO_CHAR (trunc(sysdate-90), 'yyyy-mm-dd') "
							+ "and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (sysdate, 'yyyy-mm-dd') THEN	'a04'	END	) the_three_month,	COUNT (		"
							+ "CASE		WHEN TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')>= TO_CHAR (TO_DATE (	'"+sTime+"',	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')"
							+ " and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (TO_DATE (	'"+eTime+"',	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd') THEN		"
							+ "	'a05'	END	) the_random_date,	count(	CASE	WHEN	ZFTYPE=1	THEN	'a06'"
							+ "	end	) the_type_one,	count(CASE	WHEN	ZFTYPE=2 THEN	'a07'	end	) the_type_two,		"
							+ "count(CASE	WHEN	ZFTYPE=3 THEN 'a08'	end	) the_type_three,	count(	CASE WHEN ZFTYPE=4	THEN 'a09' end) the_type_four,count(	CASE	WHEN	ZFTYPE=5	THEN	'a10'	end	)"
							+ " the_type_five,	count(CASE	WHEN	ZFTYPE=6	THEN	'a11'	end	) the_type_six,	count(CASE		WHEN	ZFTYPE=7				THEN				'a12'				end		) the_type_seven,		count(			CASE				WHEN					ZFTYPE in(1,2,3,4,5,6,7)				THEN				'a13'				end		) the_type_eight FROM	DA_HELP_VISIT where 1=1  and aar008 like '"+Patient_st_map.get("COM_CODE").toString().substring(0, t)+"%' union all ";
				}else{
					sqlTj+="SELECT	count(*) AS THE_ALL,COUNT (	CASE WHEN TO_CHAR (	TO_DATE (registertime,	'yyyy-mm-dd hh24:mi:ss'			),'yyyy-mm-dd') = TO_CHAR (SYSDATE, 'yyyy-mm-dd') THEN	'a00' END"
							+ "	) the_day,	COUNT (	CASE WHEN TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')> TO_CHAR (trunc(sysdate-7), 'yyyy-mm-dd') and TO_CHAR(TO_DATE "
							+ "(registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (sysdate, 'yyyy-mm-dd') THEN			'a01'		END	) the_one_week,	COUNT (	CASE "
							+ "	WHEN TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')> TO_CHAR (trunc(sysdate-14), 'yyyy-mm-dd') and TO_CHAR(TO_DATE"
							+ " (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (sysdate, 'yyyy-mm-dd') THEN	 'a02' END) the_two_week,	"
							+ "COUNT (CASE	WHEN TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')> TO_CHAR (trunc(sysdate-30), 'yyyy-mm-dd') "
							+ "and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (sysdate, 'yyyy-mm-dd') THEN	'a03'	END	) the_one_month,	"
							+ "COUNT (	CASE	WHEN TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')> TO_CHAR (trunc(sysdate-90), 'yyyy-mm-dd') "
							+ "and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (sysdate, 'yyyy-mm-dd') THEN	'a04'	END	) the_three_month,"
							+ "	COUNT (		CASE		WHEN TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')>= TO_CHAR (TO_DATE (	'"+sTime+"',	"
							+ "'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd') and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (TO_DATE (	'"+eTime+"',	"
							+ "'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd') THEN	'a05'	END	) the_random_date,	count(	CASE WHEN	ZFTYPE=1 THEN	'a06'	end	"
							+ ") the_type_one,		count(CASE	WHEN ZFTYPE=2	THEN 'a07'	end	) the_type_two,		"
							+ "count(CASE	WHEN	ZFTYPE=3	THEN	'a08' end) the_type_three,	count(CASE	WHEN ZFTYPE=4	THEN 'a09'	end) "
							+ "the_type_four,count(CASE	WHEN	ZFTYPE=5	THEN 'a10'	end	) the_type_five,count(CASE	WHEN	ZFTYPE=6	THEN	'a11'	end) "
							+ "the_type_six,count(CASE	WHEN	ZFTYPE=7	THEN 'a12'	end) the_type_seven,"
							+ "count(CASE WHEN	ZFTYPE in(1,2,3,4,5,6,7)	THEN 'a13'	end	) the_type_eight FROM	"
							+ "DA_HELP_VISIT where 1=1  and aar008 like '"+Patient_st_map.get("COM_CODE").toString().substring(0, t)+"%' ";
				}
			}
			
			sqlTj+=")";
			List<Map> listAll = this.getBySqlMapper.findRecords(sqlTj);
			for(int a =0;a<listAll.size();a++){
				JSONObject obj = new JSONObject();
				obj.put("V1", l.get(a));
				obj.put("V8", lev.get(a));
				//按日期条件过滤
				obj.put("V0", listAll.get(a).get("THE_ALL"));//所有
				obj.put("V2", listAll.get(a).get("THE_DAY"));//当天
				obj.put("V3", listAll.get(a).get("THE_ONE_WEEK"));//近一周
				obj.put("V4", listAll.get(a).get("THE_TWO_WEEK"));//近两周
				obj.put("V5", listAll.get(a).get("THE_ONE_MONTH"));//近一月
				obj.put("V6", listAll.get(a).get("THE_THREE_MONTH"));//近三月
				obj.put("V7", listAll.get(a).get("THE_RANDOM_DATE"));//近自定义
				
				//按走访类型过滤
				obj.put("V10", listAll.get(a).get("THE_TYPE_ONE"));//其他帮扶活动
				obj.put("V11", listAll.get(a).get("THE_TYPE_TWO"));//了解基本情况
				obj.put("V12", listAll.get(a).get("THE_TYPE_THREE"));//填写扶贫手册
				obj.put("V13", listAll.get(a).get("THE_TYPE_FOUR"));//制定脱贫计划
				obj.put("V14", listAll.get(a).get("THE_TYPE_FIVE"));//落实资金项目
				obj.put("V15", listAll.get(a).get("THE_TYPE_SIX"));//宣传扶贫政策
				obj.put("V16", listAll.get(a).get("THE_TYPE_SEVEN"));//节日假日慰问
				obj.put("V17", listAll.get(a).get("THE_TYPE_EIGHT"));//所有
				
				json.add(obj);
			}
//			System.out.println(sqlTj);
//			System.out.println(getPaixu(json, name).toString());
			response.getWriter().write(getPaixu(json, name).toString());
		} else {
			response.getWriter().write("0");
		}
	}
	/**
	 * 获取日记统计模块数据
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getF_1_2_1.do")
	public void getF_1_2_1(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String str = request.getParameter("str");// 行政区划
		String code = request.getParameter("code");// 行政区划代码
		String sTime = request.getParameter("sTime");//开始时间
		String eTime = request.getParameter("eTime");//结束时间
		String type = request.getParameter("type");
		String sqlTj = "";//拼接的sql条件
		//查询行政区划,获取行政区划code 如果为0 则说明是先加载按日期查的数据
		if(!code.equals("150")){
			sqlTj="SELECT	count(*) AS THE_ALL,COUNT (	CASE WHEN TO_CHAR (TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),	'yyyy-mm-dd') = TO_CHAR (SYSDATE, 'yyyy-mm-dd') THEN	'a00' END) the_day,"
					+ "	COUNT (	CASE WHEN TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')> TO_CHAR (trunc(sysdate-7), 'yyyy-mm-dd') and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (sysdate, 'yyyy-mm-dd') THEN	'a01' END	) the_one_week,"
					+ "	COUNT (	CASE		WHEN TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')> TO_CHAR (trunc(sysdate-14), 'yyyy-mm-dd') and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (sysdate, 'yyyy-mm-dd') THEN			'a02'		END	) the_two_week,	"
					+ "COUNT (		CASE		WHEN TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')> TO_CHAR (trunc(sysdate-30), 'yyyy-mm-dd') and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (sysdate, 'yyyy-mm-dd') THEN			'a03'		END	) the_one_month,	"
					+ "COUNT (		CASE		WHEN TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')> TO_CHAR (trunc(sysdate-90), 'yyyy-mm-dd') and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (sysdate, 'yyyy-mm-dd') THEN	'a04'	END	) the_three_month,	"
					+ "COUNT (		CASE		WHEN TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')>= TO_CHAR (TO_DATE (	'2000-01-01',	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd') and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (sysdate,'yyyy-mm-dd') THEN			'a05'	END	) the_random_date,	"
					+ "count(	CASE	WHEN	ZFTYPE=1	THEN	'a06'	end	) the_type_one,	count(CASE	WHEN	ZFTYPE=2 THEN	'a07'	end	) the_type_two,	"
					+ "	count(CASE	WHEN	ZFTYPE=3 THEN 'a08'	end	) the_type_three,	"
					+ "count(	CASE WHEN ZFTYPE=4	THEN 'a09' end) the_type_four,count(	CASE	WHEN	ZFTYPE=5	THEN	'a10'	end	) the_type_five,	"
					+ "count(CASE	WHEN	ZFTYPE=6	THEN	'a11'	end	) the_type_six,	count(CASE		WHEN	ZFTYPE=7				THEN				'a12'				end		) the_type_seven,	"
					+ "	count(			CASE WHEN	ZFTYPE in(1,2,3,4,5,6,7) THEN	'a13'	end	) the_type_eight FROM	DA_HELP_VISIT where 1=1  and aar008 like '"+code+"%' ";
			if(type.equals("day")){
				sqlTj += " and TO_CHAR (TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),	'yyyy-mm-dd') = TO_CHAR (SYSDATE, 'yyyy-mm-dd') ";
			}else if(type.equals("week")){
				sqlTj +=" and TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')> TO_CHAR (trunc(sysdate-7), 'yyyy-mm-dd') and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (sysdate, 'yyyy-mm-dd')";
			}else if(type.equals("2week")){
				sqlTj +=" and TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')> TO_CHAR (trunc(sysdate-14), 'yyyy-mm-dd') and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (sysdate, 'yyyy-mm-dd')";
			}else if(type.equals("month")){
				sqlTj +=" and TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')> TO_CHAR (trunc(sysdate-30), 'yyyy-mm-dd') and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (sysdate, 'yyyy-mm-dd')";
			}else if(type.equals("3month")){
				sqlTj +=" and TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')> TO_CHAR (trunc(sysdate-90), 'yyyy-mm-dd') and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (sysdate, 'yyyy-mm-dd')";
			}else if(type.equals("rageTime")){
				sqlTj +=" and TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')>= TO_CHAR (TO_DATE (	'"+sTime+"',	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd') and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (TO_DATE (	'"+eTime+"',	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')";
			}
			
		}else if(str.equals("xzqh")){
			sqlTj="SELECT	count(*) AS THE_ALL,COUNT (	CASE WHEN TO_CHAR (TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),	'yyyy-mm-dd') = TO_CHAR (SYSDATE, 'yyyy-mm-dd') THEN	'a00' END) the_day,"
					+ "	COUNT (	CASE WHEN TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')> TO_CHAR (trunc(sysdate-7), 'yyyy-mm-dd') and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (sysdate, 'yyyy-mm-dd') THEN	'a01' END	) the_one_week,"
					+ "	COUNT (	CASE		WHEN TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')> TO_CHAR (trunc(sysdate-14), 'yyyy-mm-dd') and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (sysdate, 'yyyy-mm-dd') THEN			'a02'		END	) the_two_week,	"
					+ "COUNT (		CASE		WHEN TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')> TO_CHAR (trunc(sysdate-30), 'yyyy-mm-dd') and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (sysdate, 'yyyy-mm-dd') THEN			'a03'		END	) the_one_month,	"
					+ "COUNT (		CASE		WHEN TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')> TO_CHAR (trunc(sysdate-90), 'yyyy-mm-dd') and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (sysdate, 'yyyy-mm-dd') THEN	'a04'	END	) the_three_month,	"
					+ "COUNT (		CASE		WHEN TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')>= TO_CHAR (TO_DATE (	'2000-01-01',	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd') and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (sysdate,'yyyy-mm-dd') THEN			'a05'	END	) the_random_date,	"
					+ "count(	CASE	WHEN	ZFTYPE=1	THEN	'a06'	end	) the_type_one,	count(CASE	WHEN	ZFTYPE=2 THEN	'a07'	end	) the_type_two,	"
					+ "	count(CASE	WHEN	ZFTYPE=3 THEN 'a08'	end	) the_type_three,	"
					+ "count(	CASE WHEN ZFTYPE=4	THEN 'a09' end) the_type_four,count(	CASE	WHEN	ZFTYPE=5	THEN	'a10'	end	) the_type_five,	"
					+ "count(CASE	WHEN	ZFTYPE=6	THEN	'a11'	end	) the_type_six,	count(CASE		WHEN	ZFTYPE=7				THEN				'a12'				end		) the_type_seven,	"
					+ "	count(			CASE WHEN	ZFTYPE in(1,2,3,4,5,6,7) THEN	'a13'	end	) the_type_eight FROM	DA_HELP_VISIT where 1=1  and aar008 like '"+code+"%' ";
			if(type.equals("day")){
				sqlTj += " and TO_CHAR (TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),	'yyyy-mm-dd') = TO_CHAR (SYSDATE, 'yyyy-mm-dd') ";
			}else if(type.equals("week")){
				sqlTj +=" and TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')> TO_CHAR (trunc(sysdate-7), 'yyyy-mm-dd') and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (sysdate, 'yyyy-mm-dd')";
			}else if(type.equals("2week")){
				sqlTj +=" and TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')> TO_CHAR (trunc(sysdate-14), 'yyyy-mm-dd') and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (sysdate, 'yyyy-mm-dd')";
			}else if(type.equals("month")){
				sqlTj +=" and TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')> TO_CHAR (trunc(sysdate-30), 'yyyy-mm-dd') and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (sysdate, 'yyyy-mm-dd')";
			}else if(type.equals("3month")){
				sqlTj +=" and TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')> TO_CHAR (trunc(sysdate-90), 'yyyy-mm-dd') and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (sysdate, 'yyyy-mm-dd')";
			}else if(type.equals("rageTime")){
				sqlTj +=" ";
			}
		}else{
			sqlTj = "select * from (SELECT	count(*) AS THE_ALL, COUNT (		CASE		WHEN TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')>= TO_CHAR (TO_DATE (	'"+sTime+"',	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd') and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (TO_DATE (	'"+eTime+"',	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd') THEN			'a05'	END	) the_random_date,count(	CASE	WHEN	ZFTYPE=1	THEN	'a06'	end	) the_type_one,count(CASE	WHEN	ZFTYPE=2 THEN	'a07'	end	) the_type_two,	count(CASE	WHEN	ZFTYPE=3 THEN 'a08'	end	) the_type_three,count(	CASE WHEN ZFTYPE=4	THEN 'a09' end) the_type_four,count(	CASE	WHEN	ZFTYPE=5	THEN	'a10'	end	) the_type_five,	count(CASE	WHEN	ZFTYPE=6	THEN	'a11'	end	) the_type_six,count(CASE		WHEN	ZFTYPE=7				THEN				'a12'				end		) the_type_seven,	count(			CASE WHEN	ZFTYPE in(1,2,3,4,5,6,7) THEN	'a13'	end	) the_type_eight FROM	DA_HELP_VISIT where 1=1  and aar008 like '"+code+"%' and TO_CHAR (TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),	'yyyy-mm-dd') = TO_CHAR (SYSDATE, 'yyyy-mm-dd') )"
					+ "UNION all (SELECT	count(*) AS THE_ALL, COUNT (		CASE		WHEN TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')>= TO_CHAR (TO_DATE (	'"+sTime+"',	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd') and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (TO_DATE (	'"+eTime+"',	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd') THEN			'a05'	END	) the_random_date,count(	CASE	WHEN	ZFTYPE=1	THEN	'a06'	end	) the_type_one,count(CASE	WHEN	ZFTYPE=2 THEN	'a07'	end	) the_type_two,	count(CASE	WHEN	ZFTYPE=3 THEN 'a08'	end	) the_type_three,count(	CASE WHEN ZFTYPE=4	THEN 'a09' end) the_type_four,count(	CASE	WHEN	ZFTYPE=5	THEN	'a10'	end	) the_type_five,	count(CASE	WHEN	ZFTYPE=6	THEN	'a11'	end	) the_type_six,count(CASE		WHEN	ZFTYPE=7				THEN				'a12'				end		) the_type_seven,	count(			CASE WHEN	ZFTYPE in(1,2,3,4,5,6,7) THEN	'a13'	end	) the_type_eight FROM	DA_HELP_VISIT where 1=1  and aar008 like '"+code+"%' and TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')> TO_CHAR (trunc(sysdate-7), 'yyyy-mm-dd') and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (sysdate, 'yyyy-mm-dd'))"
					+ "UNION all (SELECT	count(*) AS THE_ALL, COUNT (		CASE		WHEN TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')>= TO_CHAR (TO_DATE (	'"+sTime+"',	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd') and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (TO_DATE (	'"+eTime+"',	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd') THEN			'a05'	END	) the_random_date,count(	CASE	WHEN	ZFTYPE=1	THEN	'a06'	end	) the_type_one,count(CASE	WHEN	ZFTYPE=2 THEN	'a07'	end	) the_type_two,	count(CASE	WHEN	ZFTYPE=3 THEN 'a08'	end	) the_type_three,count(	CASE WHEN ZFTYPE=4	THEN 'a09' end) the_type_four,count(	CASE	WHEN	ZFTYPE=5	THEN	'a10'	end	) the_type_five,	count(CASE	WHEN	ZFTYPE=6	THEN	'a11'	end	) the_type_six,count(CASE		WHEN	ZFTYPE=7				THEN				'a12'				end		) the_type_seven,	count(			CASE WHEN	ZFTYPE in(1,2,3,4,5,6,7) THEN	'a13'	end	) the_type_eight FROM	DA_HELP_VISIT where 1=1  and aar008 like '"+code+"%' and TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')> TO_CHAR (trunc(sysdate-14), 'yyyy-mm-dd') and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (sysdate, 'yyyy-mm-dd'))"
					+ "UNION all (SELECT	count(*) AS THE_ALL, COUNT (		CASE		WHEN TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')>= TO_CHAR (TO_DATE (	'"+sTime+"',	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd') and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (TO_DATE (	'"+eTime+"',	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd') THEN			'a05'	END	) the_random_date,count(	CASE	WHEN	ZFTYPE=1	THEN	'a06'	end	) the_type_one,count(CASE	WHEN	ZFTYPE=2 THEN	'a07'	end	) the_type_two,	count(CASE	WHEN	ZFTYPE=3 THEN 'a08'	end	) the_type_three,count(	CASE WHEN ZFTYPE=4	THEN 'a09' end) the_type_four,count(	CASE	WHEN	ZFTYPE=5	THEN	'a10'	end	) the_type_five,	count(CASE	WHEN	ZFTYPE=6	THEN	'a11'	end	) the_type_six,count(CASE		WHEN	ZFTYPE=7				THEN				'a12'				end		) the_type_seven,	count(			CASE WHEN	ZFTYPE in(1,2,3,4,5,6,7) THEN	'a13'	end	) the_type_eight FROM	DA_HELP_VISIT where 1=1  and aar008 like '"+code+"%' and TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')> TO_CHAR (trunc(sysdate-30), 'yyyy-mm-dd') and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (sysdate, 'yyyy-mm-dd'))"
					+ "UNION all (SELECT	count(*) AS THE_ALL, COUNT (		CASE		WHEN TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')>= TO_CHAR (TO_DATE (	'"+sTime+"',	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd') and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (TO_DATE (	'"+eTime+"',	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd') THEN			'a05'	END	) the_random_date,count(	CASE	WHEN	ZFTYPE=1	THEN	'a06'	end	) the_type_one,count(CASE	WHEN	ZFTYPE=2 THEN	'a07'	end	) the_type_two,	count(CASE	WHEN	ZFTYPE=3 THEN 'a08'	end	) the_type_three,count(	CASE WHEN ZFTYPE=4	THEN 'a09' end) the_type_four,count(	CASE	WHEN	ZFTYPE=5	THEN	'a10'	end	) the_type_five,	count(CASE	WHEN	ZFTYPE=6	THEN	'a11'	end	) the_type_six,count(CASE		WHEN	ZFTYPE=7				THEN				'a12'				end		) the_type_seven,	count(			CASE WHEN	ZFTYPE in(1,2,3,4,5,6,7) THEN	'a13'	end	) the_type_eight FROM	DA_HELP_VISIT where 1=1  and aar008 like '"+code+"%' and TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')> TO_CHAR (trunc(sysdate-90), 'yyyy-mm-dd') and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (sysdate, 'yyyy-mm-dd'))"
					+ "UNION all (SELECT	count(*) AS THE_ALL, COUNT (		CASE		WHEN TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')>= TO_CHAR (TO_DATE (	'"+sTime+"',	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd') and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (TO_DATE (	'"+eTime+"',	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd') THEN			'a05'	END	) the_random_date,count(	CASE	WHEN	ZFTYPE=1	THEN	'a06'	end	) the_type_one,count(CASE	WHEN	ZFTYPE=2 THEN	'a07'	end	) the_type_two,	count(CASE	WHEN	ZFTYPE=3 THEN 'a08'	end	) the_type_three,count(	CASE WHEN ZFTYPE=4	THEN 'a09' end) the_type_four,count(	CASE	WHEN	ZFTYPE=5	THEN	'a10'	end	) the_type_five,	count(CASE	WHEN	ZFTYPE=6	THEN	'a11'	end	) the_type_six,count(CASE		WHEN	ZFTYPE=7				THEN				'a12'				end		) the_type_seven,	count(			CASE WHEN	ZFTYPE in(1,2,3,4,5,6,7) THEN	'a13'	end	) the_type_eight FROM	DA_HELP_VISIT where 1=1  and aar008 like '"+code+"%' and TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')>= TO_CHAR (TO_DATE (	'"+sTime+"',	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd') and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (TO_DATE (	'"+eTime+"',	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd'))"
					+ "UNION all (SELECT	count(*) AS THE_ALL, COUNT (		CASE		WHEN TO_CHAR(TO_DATE (	registertime,	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd')>= TO_CHAR (TO_DATE (	'"+sTime+"',	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd') and TO_CHAR(TO_DATE (registertime,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd')<= TO_CHAR (TO_DATE (	'"+eTime+"',	'yyyy-mm-dd hh24:mi:ss'	),'yyyy-mm-dd') THEN			'a05'	END	) the_random_date,count(	CASE	WHEN	ZFTYPE=1	THEN	'a06'	end	) the_type_one,count(CASE	WHEN	ZFTYPE=2 THEN	'a07'	end	) the_type_two,	count(CASE	WHEN	ZFTYPE=3 THEN 'a08'	end	) the_type_three,count(	CASE WHEN ZFTYPE=4	THEN 'a09' end) the_type_four,count(	CASE	WHEN	ZFTYPE=5	THEN	'a10'	end	) the_type_five,count(CASE	WHEN	ZFTYPE=6	THEN	'a11'	end	) the_type_six,count(CASE		WHEN	ZFTYPE=7				THEN				'a12'				end		) the_type_seven,	count(			CASE WHEN	ZFTYPE in(1,2,3,4,5,6,7) THEN	'a13'	end	) the_type_eight FROM	DA_HELP_VISIT where 1=1   )";
		
		}
		List<Map> listAll = this.getBySqlMapper.findRecords(sqlTj);
		JSONArray json = new JSONArray();
		if(listAll.size()>0){
					
			for(int a =0;a<listAll.size();a++){
				JSONObject obj = new JSONObject();
				//按走访类型过滤
				obj.put("V10", listAll.get(a).get("THE_TYPE_ONE"));//其他帮扶活动
				obj.put("V11", listAll.get(a).get("THE_TYPE_TWO"));//了解基本情况
				obj.put("V12", listAll.get(a).get("THE_TYPE_THREE"));//填写扶贫手册
				obj.put("V13", listAll.get(a).get("THE_TYPE_FOUR"));//制定脱贫计划
				obj.put("V14", listAll.get(a).get("THE_TYPE_FIVE"));//落实资金项目
				obj.put("V15", listAll.get(a).get("THE_TYPE_SIX"));//宣传扶贫政策
				obj.put("V16", listAll.get(a).get("THE_TYPE_SEVEN"));//节日假日慰问
				obj.put("V17", listAll.get(a).get("THE_TYPE_EIGHT"));//所有
				
				json.add(obj);
			}
			response.getWriter().write(json.toString());
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
				+ ")) a2 LEFT JOIN PKC_1_1_1 a1 ON a1.V10=A2.COM_CODE ";

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
				+ ")) a2 LEFT JOIN PKC_1_1_1 a1 ON a1.V10=A2.COM_CODE ";

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

		src += " ORDER BY TO_BINARY_DOUBLE(V8_1) DESC";
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
				+ ")) a2 LEFT JOIN PKC_1_1_1 a1 ON a1.V10=A2.COM_CODE ";

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
				+ ")) a2 LEFT JOIN PKC_1_1_2 a1 ON a1.V10=A2.COM_CODE ";

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
				+ ")) a2 LEFT JOIN PKC_1_1_3 a1 ON a1.V10=A2.COM_CODE ";

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
				+ ")) a2 LEFT JOIN PKC_1_1_4 a1 ON a1.V10=A2.COM_CODE ";

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
				+ ")) a2 LEFT JOIN PKC_1_1_5 a1 ON a1.V10=A2.COM_CODE ";

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
				+ ")) a2 LEFT JOIN PKC_1_1_6 a1 ON a1.V10=A2.COM_CODE ";

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
				+ ")) a2 LEFT JOIN PKC_1_1_7 a1 ON a1.V10=A2.COM_CODE ";

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
				+ ")) a2 LEFT JOIN PKC_1_1_8 a1 ON a1.V10=A2.COM_CODE ";

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
				+ ")) a2 LEFT JOIN PKC_1_1_9 a1 ON a1.V10=A2.COM_CODE ";

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
				+ ")) a2 LEFT JOIN PKC_1_1_9 a1 ON a1.V10=A2.COM_CODE ";

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
