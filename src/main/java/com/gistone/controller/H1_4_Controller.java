package com.gistone.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.gistone.MyBatis.config.GetBySqlMapper;


/**
 * 贫困户维护页面功能
 * @author luoshuai
 *
 */
@RestController
@RequestMapping
public class H1_4_Controller {
	@Autowired
	private GetBySqlMapper getBySqlMapper; 
	/**
	 * 
	 * @description 贫困户列表查询
	 * @method  getPKHList
	 * @param　request
	 * @author luoshuai 
	 * @date 2017年3月30日 上午11:14:53
	 *
	 */
	@RequestMapping("getPKHList.do")
	public void getPKHList(HttpServletRequest request,HttpServletResponse response)throws IOException{
		String pageSize = request.getParameter("pageSize");
		String pageNumber = request.getParameter("pageNumber");  
		String v2 ="",v3="",v4="",xzqh="",hz_name="",hz_card="",hz_phone="";
		String sql_end="";
		v2 = request.getParameter("v5");
		v3 = request.getParameter("v6");
		v4 = request.getParameter("v8");
		xzqh = request.getParameter("xzqh2"); 
		hz_name = request.getParameter("hz_name_query");
		hz_card = request.getParameter("hz_card_query");
		hz_phone = request.getParameter("hz_phone_query");
		if(v2!=null&&!v2.equals("请选择")){
			v2 = request.getParameter("v5").trim();
			v2 = v2.substring(0, 4);
			sql_end += "AND xzqh like '"+v2+"%' ";
			
		}
		if(v3!=null&&!v3.equals("")){
			v3 = request.getParameter("v6").trim();
			v3 = v3.substring(0,6 );
			sql_end += "AND xzqh like '"+v3+"%' ";
		}
		if(v4!=null&&!v4.equals("")){
			v4 = request.getParameter("v8").trim();
			v4 = v4.substring(0,9 );
			sql_end += "AND xzqh like '"+v4+"%' ";
		}
		if(xzqh!=null&&!xzqh.equals("")){
			xzqh = request.getParameter("xzqh2").trim();
			sql_end += "AND xzqh like '"+xzqh+"%' ";
		}
		
		if(hz_name!=null&&!hz_name.equals("")){
			hz_name = request.getParameter("hz_name_query").trim();
			sql_end += "AND hz_name like '%"+hz_name+"' ";
		}
		if(hz_card!=null&&!hz_card.equals("")){
			hz_card = request.getParameter("hz_card_query").trim();
			sql_end += "AND hz_card = '"+hz_card+"' ";
		}
		if(hz_phone!=null&&!hz_phone.equals("")){
			hz_phone = request.getParameter("hz_phone_query").trim();
			sql_end += "AND hz_phone = '"+hz_phone+"'";
		}
		String count_sql = "select A.*,ROWNUM RN from (SELECT DISTINCT T1.*,s.V3,s.V5,S.V7,S.V9 FROM("
				+ "SELECT PKH.AAC001 PKH_ID,PKH.AAR008 XZQH,PKH.AAR010 OUT_PK_PROPERTY,PKH.G_UPDATE_TIME,PKH.G_FLAG,"
				+ "PKRK.AAB002 HZ_NAME,PKRK.AAB004 HZ_CARD,PKH.AAR012 HZ_PHONE,PKRK.AAB006 FROM NEIMENG0117_AC01 PKH"
				+ " LEFT JOIN NEIMENG0117_AB01 PKRK ON PKH.AAC001 = PKRK.AAC001  WHERE PKRK.AAB006 = '01' AND "
				+ "PKH.G_FLAG = '1')T1  LEFT JOIN SYS_COM S ON SUBSTR(S.V4,0,4) = SUBSTR(T1.XZQH,0,4) AND SUBSTR(S.V6,0,6)"
				+ " = SUBSTR(T1.XZQH,0,6)  AND SUBSTR(S.V8,0,9) = SUBSTR(T1.XZQH,0,9) AND S.V10 = T1.XZQH )A where 1=1 ";
		String sql = "select * from (select A.*,ROWNUM RN from (SELECT DISTINCT T1.*,s.V3,s.V5,S.V7,S.V9 FROM("
				+ "SELECT PKH.AAC001 PKH_ID,PKH.AAR008 XZQH,PKH.AAR010 OUT_PK_PROPERTY,PKH.G_UPDATE_TIME,PKH.G_FLAG,"
				+ "PKRK.AAB002 HZ_NAME,PKRK.AAB004 HZ_CARD,PKH.AAR012 HZ_PHONE,PKRK.AAB006 FROM NEIMENG0117_AC01 PKH"
				+ " LEFT JOIN NEIMENG0117_AB01 PKRK ON PKH.AAC001 = PKRK.AAC001  WHERE PKRK.AAB006 = '01' AND "
				+ "PKH.G_FLAG = '1')T1  LEFT JOIN SYS_COM S ON SUBSTR(S.V4,0,4) = SUBSTR(T1.XZQH,0,4) AND SUBSTR(S.V6,0,6)"
				+ " = SUBSTR(T1.XZQH,0,6)  AND SUBSTR(S.V8,0,9) = SUBSTR(T1.XZQH,0,9) AND S.V10 = T1.XZQH )A WHERE 1=1  ";

		int size = Integer.parseInt(pageSize);
		int number = Integer.parseInt(pageNumber);
		int page = number == 0 ? 1 : (number/size)+1;
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
				sql +=" and SUBSTR (A.XZQH,0,2)='"+xzqu+"' ";
				count_sql+=" and SUBSTR (A.XZQH,0,2)='"+xzqu+"' ";
			}else if(role_id==3){//市扶贫办
				xzqu=sys_com_code.substring(0, 4);
				sql +=" and SUBSTR (A.XZQH,0,4)='"+xzqu+"' ";
				count_sql+=" and SUBSTR (A.XZQH,0,4)='"+xzqu+"' ";
			}else if(role_id==4){//县扶贫办
				xzqu=sys_com_code.substring(0, 6);
				sql +=" and SUBSTR (A.XZQH,0,6)='"+xzqu+"' ";
				count_sql+=" and SUBSTR (A.XZQH,0,6)='"+xzqu+"' ";
			}
		}
		int total = this.getBySqlMapper.findRecords(count_sql+sql_end).size();
		sql += sql_end+"and ROWNUM<="+size*page+")where rn>"+number+"  ";
		List<Map> bfrList = this.getBySqlMapper.findRecords(sql); 
		Map pkhMap = new HashMap<>();
		if(bfrList.size()>0){
			JSONArray jsa = new JSONArray();
			for(int i=0;i<bfrList.size();i++){
				pkhMap = bfrList.get(i);
				JSONObject js = new JSONObject();
				for(Object key : pkhMap.keySet()){
					js.put(key, pkhMap.get(key));
					if(key.toString().equals("OUT_PK_PROPERTY")){
						if("0".equals(pkhMap.get("OUT_PK_PROPERTY")+"")){
							js.put("OUT_PK_PROPERTY", "未脱贫");
						}else{
							js.put("OUT_PK_PROPERTY", "已脱贫");
						}
					}
				}
				/*js.put("chakan","<a onclick='chakan_info(\""+pkhMap.get("PKH_ID")+"\")'>查看</a>");*/
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
	 * 
	 * @description 添加贫困户及相关信息  更新完之后的保存功能 
	 * @method  addBfgbInfo
	 * @param　request
	 * @author luoshuai 
	 * @date 2017年3月30日 上午10:08:27
	 *
	 */
	@RequestMapping("addPKHInfo.do")
	public ModelAndView addBfgbInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Calendar c = Calendar.getInstance();
		String form_val = request.getParameter("form_val");
		JSONObject form_json = JSONObject.fromObject(form_val);//表单数据
		
		String hz_name = "", hz_card = "", hz_phone= "", out_pk_property= "", xzqh= "";
		String where = "";
		String where2 = "";
		//更新贫困户内容where
		if(form_json.get("hz_phone")!=null&&!form_json.get("hz_phone").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
			hz_phone = form_json.get("hz_phone").toString();
			where += "AAR012='"+form_json.get("hz_phone")+"',";
		}else{
			where += "AAR012='',";
		}
		if(form_json.get("out_pk_property")!=null&&!form_json.get("out_pk_property").equals("请选择")){//下拉框，一定有值，但是要筛除“请选择”
			out_pk_property = form_json.get("out_pk_property").toString();
			where += "AAR010='"+form_json.get("out_pk_property")+"',";
		}else{
			if(form_json.get("out_pk_property").equals("请选择")){
				where += "AAR010='',";
			}
		}
		if(form_json.get("xzqh")!=null&&!form_json.get("xzqh").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
			xzqh = form_json.get("xzqh").toString();
			where += "AAR008='"+form_json.get("xzqh")+"'";
		}else{
			where += "AAR008=''";
		}
		
		//更新贫困户户主内容where2
		if(form_json.get("hz_name")!=null&&!form_json.get("hz_name").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
			hz_name = form_json.get("hz_name").toString();
			where2 += "AAB002='"+form_json.get("hz_name")+"',";
		}else{
			where2 += "AAB002='',";
		}
		
		
		if(form_json.get("hz_card")!=null&&!form_json.get("hz_card").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
			hz_card = form_json.get("hz_card").toString();
			where2 += "AAB004='"+form_json.get("hz_card")+"'";
		}else{
			where2 += "AAB004=''";
		}
		
		
		
		
		
		
		String sql = "";
	try{
		if(form_json.get("pkh_id")!=null&&!form_json.get("pkh_id").equals("")){
			/*where += "da_company_id="+dwid;*/
//			if(where.length()>0){
//				where = where.substring(0, where.length()-1);
//			}
			sql = "update NEIMENG0117_AC01 set "+where+" where AAC001="+form_json.get("pkh_id");
			int flag = this.getBySqlMapper.update(sql);
			sql = "update NEIMENG0117_AB01 set "+where2+" where AAB006='01' AND AAC001="+form_json.get("pkh_id");
			this.getBySqlMapper.update(sql);
			if(flag>0&&this.getBySqlMapper.update(sql)>0){
				response.getWriter().write("1");
			}else{
				response.getWriter().write("0");
			}

			 
		}else{
			sql = "select count(*) from NEIMENG0117_AB01 WHERE AAB004 ='"+hz_card+"' AND AAB006='01'";
			int count = this.getBySqlMapper.findrows(sql);
			 if(count>0){
				 response.getWriter().write("2");
			 }else{
				 long   PKH_ID=  System.currentTimeMillis();
					sql = "insert into NEIMENG0117_AC01 (AAC001,AAR008,AAR012,AAR010,G_FLAG,AAR040)VALUES "+
							"('"+PKH_ID+"','"+xzqh+"','"+hz_phone+"','"+out_pk_property+"','1','"+c.get(Calendar.YEAR)+"')";
					this.getBySqlMapper.insert(sql);
				long pkrk_id = System.currentTimeMillis();
					sql = "insert into NEIMENG0117_AB01 (AAC001,AAB001,AAB002,AAB004,AAR040,AAB006)VALUES "+
							"('"+PKH_ID+"','"+pkrk_id+"','"+hz_name+"','"+hz_card+"','"+c.get(Calendar.YEAR)+"','01' )";
					this.getBySqlMapper.insert(sql);
					 response.getWriter().write("1");
			 }
		}
		
	}catch (Exception e){
		response.getWriter().write("0");
	}

		/*try{
			
			
			if(form_json.get("bfr_id")!=null&&!form_json.get("bfr_id").equals("")){
				HttpSession session = request.getSession();
				JSONObject json = new JSONObject();
				if(session.getAttribute("Login_map")!=null){//验证session不为空
					Map<String,String> Login_map = (Map)session.getAttribute("Login_map");//用户的user表内容
					SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
					String hql1="insert into da_record(record_table,record_pkid,record_type,record_p_t,record_phone,record_name,record_time,record_mou_1,record_mou_2)"+
							" VALUES ('sys_personal',"+form_json.get("pkid")+",'修改',2,'','"+Login_map.get("col_account")+"','"+simpleDate.format(new Date())+"','帮扶干部','')";
					this.getBySqlMapper.findRecords(hql1);
				}
			}else{
				String hou_sql = "select max(pkid) from sys_personal where hz_name='"+hz_name+"' and da_company_id="+dwid+" order by pkid desc";
				SQLAdapter hou_Adapter = new SQLAdapter(hou_sql);
				int da_household_id = this.getBySqlMapper.findrows(hou_Adapter);
				
				HttpSession session = request.getSession();
				JSONObject json = new JSONObject();
				if(session.getAttribute("Login_map")!=null){//验证session不为空
					Map<String,String> Login_map = (Map)session.getAttribute("Login_map");//用户的user表内容
					SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
					String hql1="insert into da_record(record_table,record_pkid,record_type,record_p_t,record_phone,record_name,record_time,record_mou_1,record_mou_2)"+
							" VALUES ('sys_personal',"+da_household_id+",'添加',2,'','"+Login_map.get("col_account")+"','"+simpleDate.format(new Date())+"','帮扶干部','')";
					SQLAdapter hqlAdapter1 =new SQLAdapter(hql1);
					this.getBySqlMapper.findRecords(hqlAdapter1);
				}
			}
			
			response.getWriter().write("1");
		}catch (Exception e){
			response.getWriter().write("0");
		}*/
		return null;
	}
	
	/**
	 * 
	 * @description 获取更新前贫困户信息
	 * @method  getUpdateBfgbInfo
	 * @param　request
	 * @author luoshuai 
	 * @date 2017年3月31日 下午3:08:49
	 *
	 */
	@RequestMapping("updatePKHInfo.do")
	public ModelAndView getUpdateBfgbInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String pkh_id = request.getParameter("PKH_ID");
		String xzqh = "",v2="",v3="",v4="";
		//根据bfr_id查询帮扶人信息 然后再更改页面进行展示
		String sql = "SELECT PKH.AAC001 PKH_ID,PKH.AAR008 XZQH,PKH.AAR010 OUT_PK_PROPERTY,PKH.G_UPDATE_TIME,PKH.G_FLAG,"
				+ " PKRK.AAB002 HZ_NAME,PKRK.AAB004 HZ_CARD,PKH.AAR012 HZ_PHONE,PKRK.AAB006"
				+ " FROM NEIMENG0117_AC01 PKH LEFT JOIN NEIMENG0117_AB01 PKRK ON PKH.AAC001 = PKRK.AAC001  WHERE PKRK.AAB006 = '01' AND PKH.G_FLAG = '1' AND PKH.AAC001 = "+pkh_id;
		List<Map> list = getBySqlMapper.findRecords(sql);
		JSONObject json = new JSONObject ();
		
		for(Map val:list){
			JSONObject obj=new JSONObject ();
			
			obj.put("pkh_id",val.get("PKH_ID")==null?"":val.get("PKH_ID"));
			obj.put("hz_name", val.get("HZ_NAME")==null?"":val.get("HZ_NAME"));
			obj.put("xzqh",val.get("XZQH")==null?"":val.get("XZQH"));
			if(val.get("XZQH")!=null){
				xzqh = val.get("XZQH").toString();
				v2 = xzqh.substring(0, 4)+"00000000";
				v3 = xzqh.substring(0, 6)+"000000";
				v4 = xzqh.substring(0, 9)+"000";
				obj.put("v2", v2);
				obj.put("v3", v3);
				obj.put("v4", v4);
			}
			obj.put("hz_card",val.get("HZ_CARD")==null?"":val.get("HZ_CARD"));
			obj.put("out_pk_property",val.get("OUT_PK_PROPERTY")==null?"":val.get("OUT_PK_PROPERTY"));
			obj.put("hz_phone",val.get("HZ_PHONE")==null?"":val.get("HZ_PHONE"));
			obj.put("G_FLAG", val.get("G_FLAG")==null?"":val.get("G_FLAG"));
			json.put("PKH", obj);
		}
		
		response.getWriter().write(json.toString());
		response.getWriter().close();
		return null;
	
	}
	
	
	/**
	 * 
	 * @description 删除贫困户信息
	 * @method  deleteBfgbInfo
	 * @param　request
	 * @author luoshuai 
	 * @date 2017年3月31日 上午10:09:45
	 *
	 */
	@RequestMapping("deletePKHInfo.do")
	public ModelAndView deletePKHInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String pkh_id=request.getParameter("pkh_id");
		String sql="update  NEIMENG0117_AC01 set G_FLAG='0' where AAC001="+pkh_id;
		try{
			this.getBySqlMapper.delete(sql);
			
			/*HttpSession session = request.getSession();
			JSONObject json = new JSONObject();
			if(session.getAttribute("Login_map")!=null){//验证session不为空
				Map<String,String> Login_map = (Map)session.getAttribute("Login_map");//用户的user表内容
				SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
				String hql1="insert into da_record(record_table,record_pkid,record_type,record_p_t,record_phone,record_name,record_time,record_mou_1,record_mou_2)"+
						" VALUES ('sys_personal',"+pkid+",'删除',2,'','"+Login_map.get("col_account")+"','"+simpleDate.format(new Date())+"','帮扶干部','')";
				SQLAdapter hqlAdapter1 =new SQLAdapter(hql1);
				this.getBySqlMapper.findRecords(hqlAdapter1);
			}*/
			
			response.getWriter().write("1");
		}catch (Exception e){
			response.getWriter().write("0");
		}
		return null;
	
	}
	
}
