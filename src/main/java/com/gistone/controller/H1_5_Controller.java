package com.gistone.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.gistone.MyBatis.config.GetBySqlMapper;

/**
 * 结对帮扶维护
* @ClassName:
* @Description: 
* @author 刘立峰
* @date 2017年3月31日
* @version 1.0
 */


@RestController
@RequestMapping
public class H1_5_Controller {
	@Autowired
	private GetBySqlMapper getBySqlMapper; 
	

	@RequestMapping("getBfgbInfo.do")
	public void getBfgbInfo(HttpServletRequest request,HttpServletResponse response)throws IOException{
		String pageSize = request.getParameter("pageSize");
		String pageNumber = request.getParameter("pageNumber");  
		String count_sql = "select A.* from ( SELECT * FROM (select DISTINCT BFR.G_FLAG,bfr.AAK110 bfr_id,bfr.AAB002 bfr_name,bfr.AAP110 bfdw_id,t3.AAP001 bfrdw_name,bfr.AAR012 dw_phone,t2.bfr_count,	t3.AAR008 xzqh  FROM NEIMENG0117_AK11 bfr  LEFT JOIN  (select BFRJD.AAK110,count(*) bfr_count from NEIMENG0117_AC08 bfrjd WHERE bfrjd.AAC111 = '0' GROUP BY BFRJD.AAK110)t2 on bfr.AAK110 = t2.aak110 LEFT JOIN  (SELECT bfdw.AAP001,bfdw.AAP110,bfdw.AAR008  FROM NEIMENG0117_AP11 bfdw )t3 on t3.AAP110 = bfr.AAP110 )WHERE G_FLAG = '1' )A where 1=1 ";
		String sql = "select * from (select A.*,ROWNUM RN from ( SELECT * FROM (select DISTINCT BFR.G_FLAG,bfr.AAK110 bfr_id,bfr.AAB002 bfr_name,bfr.AAP110 bfdw_id,t3.AAP001 bfrdw_name,bfr.AAR012 dw_phone,t2.bfr_count,t3.AAR008 xzqh "
				+ " FROM NEIMENG0117_AK11 bfr LEFT JOIN "
				+ " (select BFRJD.AAK110,count(*) bfr_count from NEIMENG0117_AC08 bfrjd WHERE bfrjd.AAC111 = '0' GROUP BY BFRJD.AAK110 )t2"
				+ " on bfr.AAK110 = t2.aak110 LEFT JOIN  (SELECT bfdw.AAP001,bfdw.AAP110,bfdw.AAR008  FROM NEIMENG0117_AP11 bfdw )t3 on t3.AAP110 = bfr.AAP110 )WHERE G_FLAG = '1' ";
		int size = Integer.parseInt(pageSize);
		int number = Integer.parseInt(pageNumber);
		int page = number == 0 ? 1 : (number/size)+1;
		
		String bfr_name ="",bfrdw_name="",dw_phone="";
		String sql_end="";
		bfr_name = request.getParameter("bfr_name");
		bfrdw_name = request.getParameter("bfrdw_name");
		dw_phone = request.getParameter("bfr_phone");
		
		
		if(bfr_name!=null&&!bfr_name.equals("")){
			bfr_name = request.getParameter("bfr_name").trim();
			sql_end += "AND bfr_name like '%"+bfr_name+"%' ";
		}
		if(bfrdw_name!=null&&!bfrdw_name.equals("")){
			bfrdw_name = request.getParameter("bfrdw_name").trim();
			sql_end += "AND bfrdw_name like '"+bfrdw_name+"%' ";
		}
		if(dw_phone!=null&&!dw_phone.equals("")){
			dw_phone = request.getParameter("bfr_phone").trim();
			sql_end += "AND dw_phone = '"+dw_phone+"'";
		}
		Map<String,Object> Login_map = (Map)request.getSession().getAttribute("Login_map");//用户的user表内容
		String sys_com_code="";
		long role_id=0;
		if(Login_map.size()>0){
			sys_com_code=Login_map.get("SYS_COM_CODE").toString();
			role_id=Integer.parseInt(Login_map.get("ROLE_ID").toString());
		}
		String xzqu="";
		if(!sys_com_code.equals("")){
			//自治区扶贫办
			if(role_id==2){
				xzqu=sys_com_code.substring(0, 2);
				sql +=" and SUBSTR (xzqh,0,2)='"+xzqu+"' ";
			}else if(role_id==3){//市扶贫办
				xzqu=sys_com_code.substring(0, 4);
				sql +=" and SUBSTR (xzqh,0,4)='"+xzqu+"' ";
			}else if(role_id==4){//县扶贫办
				xzqu=sys_com_code.substring(0, 6);
				sql +=" and SUBSTR (xzqh,0,6)='"+xzqu+"' ";
			}
		}
		int total = this.getBySqlMapper.findRecords(count_sql+sql_end).size();
		sql += ")A WHERE ROWNUM<="+size*page+")where rn>"+number+"";
		List<Map> bfrList = this.getBySqlMapper.findRecords(sql+sql_end); 
		Map bfrMap = new HashMap<>();
		if(bfrList.size()>0){
			JSONArray jsa = new JSONArray();
			for(int i=0;i<bfrList.size();i++){
				bfrMap = bfrList.get(i);
				JSONObject js = new JSONObject();
				for(Object key : bfrMap.keySet()){
					String bfr_id=bfrMap.get("BFR_ID").toString();//帮扶人id
					if(bfrMap.keySet().contains("BFR_COUNT")&&key.equals("BFR_COUNT")){
							js.put((String) key, "<a class=\"btn btn-outline btn-default dim\" onclick=\"PKH_initialization('"+bfr_id+"');\" >"+bfrMap.get(key)+"</a>" );
					}else if(bfrMap.keySet().contains("BFR_COUNT")&&!key.equals("BFR_COUNT")){
						js.put(key, bfrMap.get(key));
					}else if(!bfrMap.keySet().contains("BFR_COUNT")&&!key.equals("BFR_COUNT")){
						js.put("BFR_COUNT", "<a class=\"btn btn-outline btn-default dim\" onclick=\"PKH_initialization('"+bfr_id+"');\" >"+0+"</a>" );
						js.put(key, bfrMap.get(key));
					}
					
				}
				jsa.add(js);
			}
			JSONObject json = new JSONObject();
			json.put("page", page);
			json.put("total", total);
			json.put("rows", jsa); //这里的 rows 和total 的key 是固定的 
			response.getWriter().write(json.toString());
		}else{
			response.getWriter().write(0);
		}
		
		
	}
	
	/**
	 * 根据帮扶人得到帮扶的贫困户
	 * @param request
	 * @param response
	 * @throws IOException
	 *	@author Liulifeng
	 *  @date 2017年3月31日 上午11:30:22
	 */

	@RequestMapping("getPKHByBFR.do")
	public void getPKHByBFR(HttpServletRequest request,HttpServletResponse response)throws IOException{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String pageSize = request.getParameter("pageSize");
		String pageNumber = request.getParameter("pageNumber");  
		String BFR_ID="";//帮扶人id
		if(request.getParameter("BFR_ID")!=null&&!request.getParameter("BFR_ID").equals("")){
			BFR_ID=request.getParameter("BFR_ID").trim();
		}
		
		//根据帮扶人得到贫困户数量
		//String count_sql = "SELECT count(*) total FROM NEIMENG0117_AC08 where AAK110='"+BFR_ID+"'";
		String count_sql =
				 "SELECT count(*)"
				+ " FROM NEIMENG0117_AC01 PKH"
				+ " LEFT JOIN NEIMENG0117_AB01 PKRK ON PKH.AAC001 = PKRK.AAC001  WHERE PKRK.AAB006 = '01' AND "
				+ "PKH.G_FLAG = '1' AND PKH.AAC001 in(select PKHJD.AAC001 from NEIMENG0117_AC08 "
				+ " PKHJD  WHERE PKHJD.AAK110='"+BFR_ID+"' and PKHJD.AAC111='0' )";
		//根据帮扶人得到贫困户信息
		String sql = "select * from (select A.*,ROWNUM RN from ( SELECT * FROM (SELECT DISTINCT T1.*,s.V3,s.V5,S.V7,S.V9 FROM("
				+ "SELECT PKH.AAC001 PKH_ID,PKH.AAR008 XZQH,PKH.AAR010 OUT_PK_PROPERTY,PKH.G_FLAG,"
				+ "PKRK.AAB002 HZ_NAME,PKRK.AAB004 HZ_CARD,PKRK.AAB006 FROM NEIMENG0117_AC01 PKH"
				+ " LEFT JOIN NEIMENG0117_AB01 PKRK ON PKH.AAC001 = PKRK.AAC001  WHERE PKRK.AAB006 = '01' AND "
				+ "PKH.G_FLAG = '1' AND PKH.AAC001 in(select PKHJD.AAC001 from NEIMENG0117_AC08 "
				+ " PKHJD  WHERE PKHJD.AAK110='"+BFR_ID+"' and PKHJD.AAC111='0' ) )T1  LEFT JOIN SYS_COM S ON SUBSTR(S.V4,0,4) = SUBSTR(T1.XZQH,0,4) AND SUBSTR(S.V6,0,6)"
				+ " = SUBSTR(T1.XZQH,0,6)  AND SUBSTR(S.V8,0,9) = SUBSTR(T1.XZQH,0,9) AND S.V10 = T1.XZQH ";

	try {
		int size = Integer.parseInt(pageSize);
		int number = Integer.parseInt(pageNumber);
		int page = number == 0 ? 1 : (number/size)+1;
		
		int total = this.getBySqlMapper.findrows(count_sql);
		sql += ") WHERE ROWNUM<="+size*page+") A )where rn>"+number+"";
		List<Map> bfrList = this.getBySqlMapper.findRecords(sql); 
		Map pkhMap = new HashMap<>();
		JSONObject json = new JSONObject();
		if(bfrList.size()>0){
			JSONArray jsa = new JSONArray();
			for(int i=0;i<bfrList.size();i++){
				pkhMap = bfrList.get(i);
				JSONObject js = new JSONObject();
				for(Object key : pkhMap.keySet()){
					
						js.put(key, pkhMap.get(key));
					
					
					if(key.toString().equals("OUT_PK_PROPERTY")){
						if("".equals(pkhMap.get("OUT_PK_PROPERTY")+"")){
							js.put("OUT_PK_PROPERTY", "未脱贫");
						}else{
							js.put("OUT_PK_PROPERTY", "已脱贫");
						}
					}
				}
				js.put("jiechu","<a onclick='release_relationship(\""+pkhMap.get("PKH_ID")+"\",\""+BFR_ID+"\")'>解除</a>");
				jsa.add(js);
			}
			json.put("page", page);
			json.put("total", total);
			json.put("rows", jsa); //这里的 rows 和total 的key 是固定的 
			response.getWriter().write(json.toString());
		}
	} catch (Exception e) {
		response.getWriter().write("0");
	}
		
	}
	
	/**
	 * 根据地区得到所有贫困户
	 * @param request
	 * @param response
	 * @throws IOException
	 *	@author Liulifeng
	 *  @date 2017年3月31日 上午11:30:22
	 */

	@RequestMapping("getPKHByRegion.do")
	public void getPKHByRegion(HttpServletRequest request,HttpServletResponse response)throws IOException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String v2="";//选择的市
		String v3="";//选择的旗区县
		String v4="";//选择的苏木乡
		String v5="";//选择的嘎查村
		String BFR_ID="";//帮扶干部id
		String XZQH="";//行政区划
		//StringBuilder search=new StringBuilder();
		if(request.getParameter("BFR_ID")!=null&&!request.getParameter("BFR_ID").equals("")){
			BFR_ID=request.getParameter("BFR_ID").trim();
		}
		
		
		if(request.getParameter("v5")!=null&&!request.getParameter("v5").equals("")&&!request.getParameter("v5").equals("null")){
			v5=request.getParameter("v5").trim();
			XZQH=v5;
		}else{
			if(request.getParameter("v4")!=null&&!request.getParameter("v4").equals("")&&!request.getParameter("v4").equals("null")){
				v4=request.getParameter("v4").trim();
				XZQH=v4.substring(0,9);
			}else{
				if(request.getParameter("v3")!=null&&!request.getParameter("v3").equals("")&&!request.getParameter("v3").equals("null")){
					v3=request.getParameter("v3").trim();
					XZQH=v3.substring(0,6);
				}else{
					if(request.getParameter("v2")!=null&&!request.getParameter("v2").equals("")&&!request.getParameter("v2").equals("null")){
						v2=request.getParameter("v2").trim();
						XZQH=v2.substring(0,4);
					}
				}
			}
		}
		
		String sql = "SELECT B.* FROM (SELECT DISTINCT s.V3,s.V5,S.V7,S.V9,T1.* FROM("
				+ "SELECT PKH.AAC001 PKH_ID,PKH.AAR008 XZQH,PKRK.AAB002 HZ_NAME,PKRK.AAB004 HZ_CARD,PKH.AAR010 OUT_PK_PROPERTY,PKH.G_FLAG,"
				+ "PKRK.AAB006 FROM NEIMENG0117_AC01 PKH"
				+ " LEFT JOIN NEIMENG0117_AB01 PKRK ON PKH.AAC001 = PKRK.AAC001  WHERE PKRK.AAB006 = '01' AND "
				+ "PKH.G_FLAG = '1' AND PKH.AAC001 not in(select PKHJD.AAC001 from NEIMENG0117_AC08 PKHJD  WHERE PKHJD.AAK110='"+BFR_ID+"' and PKHJD.AAC111='0' ))T1  LEFT JOIN SYS_COM S ON SUBSTR(S.V4,0,4) = SUBSTR(T1.XZQH,0,4) AND SUBSTR(S.V6,0,6)"
				+ " = SUBSTR(T1.XZQH,0,6)  AND SUBSTR(S.V8,0,9) = SUBSTR(T1.XZQH,0,9) AND S.V10 =T1.XZQH ) B";
		if(!XZQH.equals("")){
			sql+=" WHERE B.XZQH LIKE '"+XZQH+"%'";
		}
		List<Map> bfrList = this.getBySqlMapper.findRecords(sql); 
		Map pkhMap = new HashMap<>();
		JSONArray jsa = new JSONArray();
		if(bfrList.size()>0){
			for(int i=0;i<bfrList.size();i++){
				pkhMap = bfrList.get(i);
				JSONObject js = new JSONObject();
				for(Object key : pkhMap.keySet()){
						js.put(key, pkhMap.get(key));
					
					if(key.toString().equals("OUT_PK_PROPERTY")){
						if("".equals(pkhMap.get("OUT_PK_PROPERTY")+"")){
							js.put("OUT_PK_PROPERTY", "未脱贫");
						}else{
							js.put("OUT_PK_PROPERTY", "已脱贫");
						}
					}
				}
				jsa.add(js);
			}
			
		}else{
			JSONObject json = new JSONObject();
			json.put("V3", "-");
			json.put("V5", "-");
			json.put("V7", "-");
			json.put("V9", "-");
			json.put("HZ_NAME", "-");
			json.put("HZ_CARD", "-");
			json.put("OUT_PK_PROPERTY", "-");
			jsa.add(json);
		}
		JSONObject json = new JSONObject();
		json.put("value", jsa); //这里的 rows 和total 的key 是固定的 
		response.getWriter().write(json.toString());
	}
	
	/**
	 * 
	 * @description 添加帮扶人与贫困户的关系
	 * @method  addBfgbInfo
	 * @param　request
	 * @author Liulifeng
	 * @date 2017年3月29日 下午3:02:15
	 *
	 */
	@RequestMapping("saveBFCX.do")
	public ModelAndView saveBFCX(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String PKH_ID="";//贫困户id
		String BFR_ID="";//帮扶人id
		
		if(request.getParameter("BFR_ID")!=null&&!request.getParameter("BFR_ID").equals("")){
			BFR_ID=request.getParameter("BFR_ID").trim();
		}
		if(request.getParameter("PKH_ID")!=null&&!request.getParameter("PKH_ID").equals("")){
			PKH_ID=request.getParameter("PKH_ID").trim();
		}
		//关系缔结时间
		Date date=new Date();
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");  
		String AAR100=formatter.format(date);
		long   AAC080=  System.currentTimeMillis();//结对编号
		String sql = "insert into NEIMENG0117_AC08 (AAC080,AAK110,AAC001,AAR100,AAC111) values( "+AAC080+","+BFR_ID+","+PKH_ID+",'"+AAR100+"','0')";
	try{
		this.getBySqlMapper.insert(sql);
		response.getWriter().write("1");
		}catch (Exception e){
			response.getWriter().write("0");
		}
		return null;
	}
	
	
	/**
	 * 
	 * @description 解除帮扶人与贫困户的关系
	 * @method  deleteBfgbInfo
	 * @param　request
	 * @author Liulifeng
	 * @date 2017年3月29日 下午3:02:15
	 *
	 */
	
	@RequestMapping("releaseBFCX.do")
	public ModelAndView releaseBFCX(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String BFR_ID="";//帮扶人id
		String PKH_ID="";//贫困户id
		JSONObject jsonObject=new JSONObject();
		if(request.getParameter("BFR_ID")!=null&&!request.getParameter("BFR_ID").equals("")){
			BFR_ID=request.getParameter("BFR_ID").trim();
		}
		if(request.getParameter("PKH_ID")!=null&&!request.getParameter("PKH_ID").equals("")){
			PKH_ID=request.getParameter("PKH_ID").trim();
		}
		//关系解除时间
		Date date=new Date();
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		String AAR001=formatter.format(date);
		//解除帮扶关系
		String sql="update  NEIMENG0117_AC08 set AAC111='1',AAR001='"+AAR001+"' where AAK110="+BFR_ID+"and AAC001="+PKH_ID+"";
		try{
			this.getBySqlMapper.update(sql);
			jsonObject.put("BFR_ID", BFR_ID);
			jsonObject.put("isSuccess", "1");
			response.getWriter().write(jsonObject.toString());
		}catch (Exception e){
			jsonObject.put("isSuccess", "0");
			response.getWriter().write(jsonObject.toString());
		}
		return null;
	
	}
	
}
