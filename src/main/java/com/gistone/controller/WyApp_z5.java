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
public class WyApp_z5 {
	@Autowired
	private GetBySqlMapper getBySqlMapper;

	/**
	 * @method 计算占比
	 * @param num_1
	 * @param num_2
	 * @return
	 * @author 张晓翔
	 */
	public String jisuan(int num_1, int num_2) {
		if(num_1==0 || num_2==0){
			return "0";
		}else{
			DecimalFormat df = new DecimalFormat("0.00");//格式化小数  
			return df.format(100*(float)num_2/(float)num_1);//返回的是String类型 
		}
	}

	/**
	 * @method z5页面 上方的4个值
	 * @param request
	 * @param response
	 * @throws IOException
	 * @author 张晓翔
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("z5top_1.do")
	public void z5top_1(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String code = request.getParameter("code");
		JSONObject object = new JSONObject();
		//人
		String sql = "SELECT * FROM PKC_1_1_8 WHERE V10 ='"+code+"'";
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		int a1 = 0;//人数
		int a2 = 0;//一般贫困户人数
		if(!list.isEmpty()){
			for(int i = 0;i<list.size();i++){
				Map Patient_st_map = list.get(i);
				a1 += Integer.parseInt("".equals(Patient_st_map.get("V2")) || Patient_st_map.get("V2") == null ? "0" : Patient_st_map.get("V2").toString());
				a2 += Integer.parseInt("".equals(Patient_st_map.get("V3")) || Patient_st_map.get("V3") == null ? "0" : Patient_st_map.get("V3").toString());
			}
			object.put("a_1", a1);
			object.put("a_2", a2);
			object.put("a_3", jisuan(a1,a2));
		}
		//户
		String sql_2 = "SELECT * FROM PKC_1_2_1 WHERE COM_CODE ='"+code+"'";
		List<Map> list_2 = this.getBySqlMapper.findRecords(sql_2);
		int b1 = 0;//户数
		int b2 = 0;//一般贫困户数
		if(!list_2.isEmpty()){
			for(int i = 0;i<list_2.size();i++){
				Map Patient_st_map = list_2.get(i);
				b1 += Integer.parseInt("".equals(Patient_st_map.get("Z_HU")) || Patient_st_map.get("Z_HU") == null ? "0" : Patient_st_map.get("Z_HU").toString());
				b2 += Integer.parseInt("".equals(Patient_st_map.get("V1")) || Patient_st_map.get("V1") == null ? "0" : Patient_st_map.get("V1").toString());
			}
			object.put("b_1", b1);
			object.put("b_2", b2);
			object.put("b_3", jisuan(b1,b2));
		}
		//村
		String sql_3 = "SELECT * FROM PKC_1_3_1 WHERE V10 ='"+code+"'";
		List<Map> list_3 = this.getBySqlMapper.findRecords(sql_3);
		String c1 = "";//建档立卡行政村数
		if(!list_3.isEmpty()){
			c1 = list_3.get(0).get("V2").toString();
			object.put("c_1", c1);
		}
		String sql_31 = "SELECT v10,AAD010 FROM("
				+ "SELECT V10 FROM SYS_COM WHERE V6='"+code+"' OR V4='"+code+"')a1 LEFT JOIN("
				+ "SELECT AAD001,AAD010 FROM NM09_AD08 GROUP BY AAD001,AAD010)a2 ON a1.V10=A2.AAD001 WHERE AAD010 IS NOT NULL";
		List<Map> list_31 = this.getBySqlMapper.findRecords(sql_31);
		int c2 = 0;
		if(!list_31.isEmpty()){
			for(int i = 0;i<list_31.size();i++){
				Map Patient_st_map = list_31.get(i);
				c2 += Integer.parseInt("".equals(Patient_st_map.get("AAD010")) || Patient_st_map.get("AAD010") == null ? "0" : Patient_st_map.get("AAD010").toString());
			}
		}
		object.put("c_2", jisuan(c2,a1));
		//国家重点旗县
		String sql_4 = "SELECT * FROM SYS_COMPANY WHERE COM_F_CODE='"+code+"'";
		List<Map> list_4 = this.getBySqlMapper.findRecords(sql_4);
		int d1 = 0;//国家重点旗县
		int d2 = 0;//自治区重点旗县
		int d3 = 0;//革命老区旗县
		int d4 = 0;//牧业旗县
		int d5 = 0;//边境旗县
		if(!list_4.isEmpty()){
			for(int i = 0;i<list_4.size();i++){
				Map Patient_st_map = list_4.get(i);
				d1 += Integer.parseInt("".equals(Patient_st_map.get("GJZDQX")) || Patient_st_map.get("GJZDQX") == null ? "0" : Patient_st_map.get("GJZDQX").toString());
				d2 += Integer.parseInt("".equals(Patient_st_map.get("ZZQZDQX")) || Patient_st_map.get("ZZQZDQX") == null ? "0" : Patient_st_map.get("ZZQZDQX").toString());
				d3 += Integer.parseInt("".equals(Patient_st_map.get("GMLQQX")) || Patient_st_map.get("GMLQQX") == null ? "0" : Patient_st_map.get("GMLQQX").toString());
				d4 += Integer.parseInt("".equals(Patient_st_map.get("MYQX")) || Patient_st_map.get("MYQX") == null ? "0" : Patient_st_map.get("MYQX").toString());
				d5 += Integer.parseInt("".equals(Patient_st_map.get("BJQX")) || Patient_st_map.get("BJQX") == null ? "0" : Patient_st_map.get("BJQX").toString());
			}
			object.put("d_1", d1);
			object.put("d_2", d2);
			object.put("d_3", d3);
			object.put("d_4", d4);
			object.put("d_5", d5);
		}
		response.getWriter().write(object.toString());
	}

	/**
	 * @method 贫困人口分布情况
	 * @param request
	 * @param response
	 * @throws IOException
	 * @author 张晓翔
	 */
	@RequestMapping("z5top_2.do")
	public void z5top_2(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String code = request.getParameter("code");
		String sql = "SELECT a1.V1,a1.V2 FROM PKC_1_1_0 a1 INNER JOIN ("
				+ "SELECT * FROM SYS_COMPANY WHERE COM_F_CODE='"+code+"') a2 ON a1.V10=a2.COM_CODE";
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		JSONObject object = new JSONObject();

		if(!list.isEmpty()){
			for(int i = 0;i<list.size();i++){
				Map stmap = list.get(i);
				if(object.containsKey(stmap.get("V1"))){
					object.put(stmap.get("V1"), String.valueOf(Integer.parseInt(stmap.get("V2").toString()) + Integer.parseInt(object.get(stmap.get("V1")).toString())));
				}else{
					object.put(stmap.get("V1"), stmap.get("V2").toString());
				}
			}
			response.getWriter().write(object.toString());
		}else{
			response.getWriter().write("0");
		}
	}
	
	@RequestMapping("z5top_3.do")
	public void z5top_3(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String code = request.getParameter("code");
		String sql = "select fc1,fc2,fc3,fc4,fc4,fc5,fc6,fc7,fc8 from PKC_2_1_1 c join ("
				+ "select * from SYS_COMPANY WHERE COM_F_CODE='"+code+"') b on c.FC1=b.COM_NAME order by c.PKID";
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
			response.getWriter().write(jsa.toString());
		}else{
			response.getWriter().print("0");
		}
	}
}
