package com.gistone.controller;

import java.io.IOException;
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
	 * @description 
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
		String count_sql = "SELECT count(*) total FROM NEIMENG0117_AC01 PKH LEFT JOIN NEIMENG0117_AB01 "
				+ "PKRK ON PKH.AAC001 = PKRK.AAC001  WHERE PKRK.AAB006 = '01' AND PKH.G_FLAG = '1'";
		String sql = "select * from (select A.*,ROWNUM RN from (SELECT DISTINCT T1.*,s.V3,s.V5,S.V7,S.V9 FROM("
				+ "SELECT PKH.AAC001 PKH_ID,PKH.AAR008 XZQH,PKH.AAR010 OUT_PK_PROPERTY,PKH.G_UPDATE_TIME,PKH.G_FLAG,"
				+ "PKRK.AAB002 HZ_NAME,PKRK.AAB004 HZ_CARD,PKRK.AAB031 HZ_PHONE,PKRK.AAB006 FROM NEIMENG0117_AC01 PKH"
				+ " LEFT JOIN NEIMENG0117_AB01 PKRK ON PKH.AAC001 = PKRK.AAC001  WHERE PKRK.AAB006 = '01' AND "
				+ "PKH.G_FLAG = '1')T1  LEFT JOIN SYS_COM S ON SUBSTR(S.V4,0,4) = SUBSTR(T1.XZQH,0,4) AND SUBSTR(S.V6,0,6)"
				+ " = SUBSTR(T1.XZQH,0,6)  AND SUBSTR(S.V8,0,9) = SUBSTR(T1.XZQH,0,9) AND S.V10 = T1.XZQH  ";

		int size = Integer.parseInt(pageSize);
		int number = Integer.parseInt(pageNumber);
		int page = number == 0 ? 1 : (number/size)+1;
		
		int total = this.getBySqlMapper.findrows(count_sql);
		sql += ")A WHERE ROWNUM<="+size*page+")where rn>"+number+"";
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
						if("".equals(pkhMap.get("OUT_PK_PROPERTY")+"")){
							js.put("OUT_PK_PROPERTY", "未脱贫");
						}else{
							js.put("OUT_PK_PROPERTY", "已脱贫");
						}
					}
				}
				js.put("chakan","<a onclick='chakan_info(\""+pkhMap.get("PKH_ID")+"\")'>查看</a>");
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
	
/*	@RequestMapping("addBfgbInfo.do")
	public ModelAndView addBfgbInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String dwid = request.getParameter("dwid");
		String form_val = request.getParameter("form_val");
		JSONObject form_json = JSONObject.fromObject(form_val);//表单数据
		
		String bfr_name = "", sex = "", bfr_card = "", bfrdw_name = "", dw_address = "", birthday = "", bfr_zzmm = "", bfr_phone = "", relative = "",
				zhiwei_level="",studying="",jishutechang="",duizhang="",diyi_shuji="",zhucun_duiyuan="";
		String where = "";
		
		if(form_json.get("bfr_name")!=null&&!form_json.get("bfr_name").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
			bfr_name = form_json.get("bfr_name").toString();
			where += "AAB002='"+form_json.get("bfr_name")+"',";
		}else{
			where += "AAB002='',";
		}
		
		if(form_json.get("sex")!=null&&!form_json.get("sex").equals("请选择")){//下拉框，一定有值，但是要筛除“请选择”
			sex = form_json.get("sex").toString();
			where += "AAB003='"+form_json.get("sex")+"',";
		}else{
			if(form_json.get("sex").equals("请选择")){
				where += "AAB003='',";
			}
		}
		
		if(form_json.get("bfr_card")!=null&&!form_json.get("bfr_card").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
			bfr_card = form_json.get("bfr_card").toString();
			where += "AAB004='"+form_json.get("bfr_card")+"',";
		}else{
			where += "AAB004='',";
		}
		
		if(form_json.get("bfrdw_name")!=null&&!form_json.get("bfrdw_name").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
			bfrdw_name = form_json.get("bfrdw_name").toString();
			where += "AAP001='"+form_json.get("bfrdw_name")+"',";
		}else{
			where += "AAP001='',";
		}
		if(form_json.get("ad_bf_dw")!=null&&!form_json.get("ad_bf_dw").equals("请选择")){//下拉框，一定有值，但是要筛除“请选择”
			ad_bf_dw = form_json.get("ad_bf_dw").toString();
			where += "AAP001='"+form_json.get("ad_bf_dw")+"',";
		}else{
			if(form_json.get("ad_bf_dw").equals("请选择")){
				where += "AAP001='',";
			}
		}
		
		
		if(form_json.get("dw_address")!=null&&!form_json.get("dw_address").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
			dw_address = form_json.get("dw_address").toString();
			where += "AAR013='"+form_json.get("dw_address")+"',";
		}else{
			where += "AAR013='',";
		}
		
		if(form_json.get("birthday")!=null&&!form_json.get("birthday").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
			birthday = form_json.get("birthday").toString();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
			String c=sdf.format(current);
			int date = Integer.parseInt(c);
			where += "AAB005='"+form_json.get("birthday")+"',";
		}else{
			where += "AAB005='',";
		}
		
		
		if(form_json.get("bfr_zzmm")!=null&&!form_json.get("bfr_zzmm").equals("请选择")){//下拉框，一定有值，但是要筛除“请选择”
			bfr_zzmm = form_json.get("bfr_zzmm").toString();
			where += "AAK033='"+form_json.get("bfr_zzmm")+"',";
		}else{
			if(form_json.get("bfr_zzmm").equals("请选择")){
				where += "AAK033='',";
			}
		}
		
		
		if(form_json.get("bfr_phone")!=null&&!form_json.get("bfr_phone").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
			bfr_phone = form_json.get("bfr_phone").toString();
			where += "AAR012='"+form_json.get("bfr_phone")+"',";
		}else{
			where += "AAR012='',";
		}
		
		
		if(form_json.get("relative")!=null&&!form_json.get("relative").equals("请选择")){//下拉框，一定有值，但是要筛除“请选择”
			relative = form_json.get("relative").toString();
			where += "AAP004='"+form_json.get("relative")+"',";
		}else{
			if(form_json.get("relative").equals("请选择")){
				where += "AAP004='',";
			}
		}
		
		if(form_json.get("zhiwei_level")!=null&&!form_json.get("zhiwei_level").equals("请选择")){//下拉框，一定有值，但是要筛除“请选择”
			zhiwei_level = form_json.get("zhiwei_level").toString();
			where += "AAF031='"+form_json.get("zhiwei_level")+"',";
		}else{
			if(form_json.get("zhiwei_level").equals("请选择")){
				where += "AAF031='',";
			}
		}
		
		if(form_json.get("studying")!=null&&!form_json.get("studying").equals("请选择")){//下拉框，一定有值，但是要筛除“请选择”
			studying = form_json.get("studying").toString();
			where += "AAK036='"+form_json.get("studying")+"',";
		}else{
			if(form_json.get("studying").equals("请选择")){
				where += "AAK036='',";
			}
		}
		
		
		if(form_json.get("jishu_techang")!=null&&!form_json.get("jishu_techang").equals("请选择")){//下拉框，一定有值，但是要筛除“请选择”
			jishutechang = form_json.get("jishu_techang").toString();
			where += "AAK037='"+form_json.get("jishu_techang")+"',";
		}else{
			if(form_json.get("jishu_techang").equals("请选择")){
				where += "AAK037='',";
			}
		}
		

		
		if(form_json.get("duizhang")!=null&&!form_json.get("duizhang").equals("请选择")){//下拉框，一定有值，但是要筛除“请选择”
			duizhang = form_json.get("duizhang").toString();
			where += "AAK031='"+form_json.get("duizhang")+"',";
		}else{
			if(form_json.get("duizhang").equals("请选择")){
				where += "AAK031='',";
			}
		}
		
		
		if(form_json.get("diyi_shuji")!=null&&!form_json.get("diyi_shuji").equals("请选择")){//下拉框，一定有值，但是要筛除“请选择”
			diyi_shuji = form_json.get("diyi_shuji").toString();
			where += "AAK032='"+form_json.get("diyi_shuji")+"',";
		}else{
			if(form_json.get("diyi_shuji").equals("请选择")){
				where += "AAK032='',";
			}
		}
		
		if(form_json.get("zhucun_duiyuan")!=null&&!form_json.get("zhucun_duiyuan").equals("请选择")){//下拉框，一定有值，但是要筛除“请选择”
			zhucun_duiyuan = form_json.get("zhucun_duiyuan").toString();
			where += "AAK039='"+form_json.get("zhucun_duiyuan")+"'";
		}else{
			if(form_json.get("zhucun_duiyuan").equals("请选择")){
				where += "AAK039=''";
			}
		}
		
		
		String sql = "";
	try{
		if(form_json.get("bfr_id")!=null&&!form_json.get("bfr_id").equals("")){
			where += "da_company_id="+dwid;
//			if(where.length()>0){
//				where = where.substring(0, where.length()-1);
//			}
			sql = "update NEIMENG0117_AK11 set "+where+" where AAK110="+form_json.get("bfr_id");
			this.getBySqlMapper.update(sql);
			 response.getWriter().write("1");
		}else{
			sql = "select count(*) from NEIMENG0117_AK11 WHERE AAB004 ='"+bfr_card+"'";
			List<Map> countList =this.getBySqlMapper.findRecords(sql);
			System.out.println(countList.size());
			int count = this.getBySqlMapper.findrows(sql);
			 if(count>0){
				 response.getWriter().write("2");
			 }else{
				 long   bfr_id=  System.currentTimeMillis();
					sql = "insert into NEIMENG0117_AK11 (AAK110,AAB002,AAB003,AAB004,AAP001,AAR013,AAB005,AAK033,AAR012,AAP004,AAF031,AAK036,AAK037,AAK031,AAK032,AAK039,G_FLAG)VALUES "+
							"('"+bfr_id+"','"+bfr_name+"','"+sex+"','"+bfr_card+"','"+bfrdw_name+"','"+dw_address+"','"+birthday+"','"+bfr_zzmm+"','"+bfr_phone+"','"+relative+"',"
									+ "'"+zhiwei_level+"','"+studying+"','"+jishutechang+"','"+duizhang+"','"+diyi_shuji+"','"+zhucun_duiyuan+"','1')";
					this.getBySqlMapper.insert(sql);
					 response.getWriter().write("1");
			 }
		}
		
	}catch (Exception e){
		response.getWriter().write("0");
	}

		try{
			
			
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
				String hou_sql = "select max(pkid) from sys_personal where bfr_name='"+bfr_name+"' and da_company_id="+dwid+" order by pkid desc";
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
		}
		return null;
	}
	
	*//**
	 * 
	 * @description 获得更改帮扶人之前的信息
	 * @method  getUpdateBfrInfo
	 * @param　request
	 * @author luoshuai 
	 * @date 2017年3月29日 上午11:38:27
	 *
	 *//*
	@RequestMapping("getUpdateBfgbInfo.do")
	public ModelAndView getUpdateBfgbInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String bfr_id = request.getParameter("bfr_id");
		//根据bfr_id查询帮扶人信息 然后再更改页面进行展示
		String sql = "select AAK110 bfr_id,AAB002 bfr_name,AAB003 sex,AAB004 bfr_card,AAP001 bfrdw_name ,AAR013 dw_address,AAB005 birthday,AAK033 bfr_zzmm,AAR012 bfr_phone,"
				+ "AAP004 relative,AAF031 zhiwei_level,AAK036 studying,AAK037 jishu_techang,AAK031 duizhang,AAK032 diyi_shuji,"
				+ "AAK039 zhucun_duiyuan,G_FLAG FROM NEIMENG0117_AK11 where  AAK110 ="+bfr_id;
		List<Map> list = getBySqlMapper.findRecords(sql);
		JSONObject json = new JSONObject ();
		
		for(Map val:list){
			JSONObject obj=new JSONObject ();
			
			obj.put("bfr_id",val.get("BFR_ID")==null?"":val.get("BFR_ID"));
			obj.put("bfr_name", val.get("BFR_NAME")==null?"":val.get("BFR_NAME"));
			obj.put("sex",val.get("SEX")==null?"":val.get("SEX"));
			obj.put("bfr_card",val.get("BFR_CARD")==null?"":val.get("BFR_CARD"));
			obj.put("bfrdw_name",val.get("BFRDW_NAME")==null?"":val.get("BFRDW_NAME"));
			obj.put("dw_address",val.get("DW_ADDRESS")==null?"":val.get("DW_ADDRESS"));
			obj.put("birthday",val.get("BIRTHDAY")==null?"":val.get("BIRTHDAY"));
			obj.put("bfr_zzmm",val.get("BFR_ZZMM")==null?"":val.get("BFR_ZZMM"));
			obj.put("bfr_phone",val.get("BFR_PHONE")==null?"":val.get("BFR_PHONE"));
			obj.put("relative",val.get("RELATIVE")==null?"":val.get("RELATIVE"));
			obj.put("zhiwei_level", val.get("ZHIWEI_LEVEL")==null?"":val.get("ZHIWEI_LEVEL"));
			obj.put("studying", val.get("STUDYING")==null?"":val.get("STUDYING"));
			obj.put("jishu_techang", val.get("JISHU_TECHANG")==null?"":val.get("JISHU_TECHANG"));
			obj.put("duizhang", val.get("DUIZHANG")==null?"":val.get("DUIZHANG"));
			obj.put("diyi_shuji", val.get("DIYI_SHUJI")==null?"":val.get("DIYI_SHUJI"));
			obj.put("zhucun_duiyuan", val.get("ZHUCUN_DUIYUAN")==null?"":val.get("ZHUCUN_DUIYUAN"));
			obj.put("G_FLAG", val.get("G_FLAG")==null?"":val.get("G_FLAG"));
			json.put("bfgb", obj);
		}
		
		response.getWriter().write(json.toString());
		response.getWriter().close();
		return null;
	
	}
	*//**
	 * 
	 * @description 删除帮扶干部信息
	 * @method  deleteBfgbInfo
	 * @param　request
	 * @author luoshuai 
	 * @date 2017年3月29日 下午3:02:15
	 *
	 *//*
	@RequestMapping("deleteBfgbInfo.do")
	public ModelAndView deleteBfgbInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String bfr_id=request.getParameter("bfr_id");
		String sql="update  NEIMENG0117_AK11 set G_FLAG='0' where AAK110="+bfr_id;
		try{
			this.getBySqlMapper.delete(sql);
			
			HttpSession session = request.getSession();
			JSONObject json = new JSONObject();
			if(session.getAttribute("Login_map")!=null){//验证session不为空
				Map<String,String> Login_map = (Map)session.getAttribute("Login_map");//用户的user表内容
				SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
				String hql1="insert into da_record(record_table,record_pkid,record_type,record_p_t,record_phone,record_name,record_time,record_mou_1,record_mou_2)"+
						" VALUES ('sys_personal',"+pkid+",'删除',2,'','"+Login_map.get("col_account")+"','"+simpleDate.format(new Date())+"','帮扶干部','')";
				SQLAdapter hqlAdapter1 =new SQLAdapter(hql1);
				this.getBySqlMapper.findRecords(hqlAdapter1);
			}
			
			response.getWriter().write("1");
		}catch (Exception e){
			response.getWriter().write("0");
		}
		return null;
	
	}*/
	
}
