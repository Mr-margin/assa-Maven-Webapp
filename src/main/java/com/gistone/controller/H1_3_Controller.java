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
 * 帮扶干部维护页面功能
 * @author luoshuai
 *
 */
@RestController
@RequestMapping
public class H1_3_Controller {
	@Autowired
	private GetBySqlMapper getBySqlMapper; 
	/**
	 * 
	 * @description 
	 * @method  getBfgbList
	 * @param　request
	 * @author luoshuai 
	 * @date 2017年3月28日 上午11:54:06
	 *
	 */
	@RequestMapping("getBfgbList.do")
	public void getBfgbList(HttpServletRequest request,HttpServletResponse response)throws IOException{
		String pageSize = request.getParameter("pageSize");
		String pageNumber = request.getParameter("pageNumber");  
		int size = Integer.parseInt(pageSize);
		int number = Integer.parseInt(pageNumber);
		int page = number == 0 ? 1 : (number/size)+1;
		
		String bfr_name ="",bfrdw_name="",dw_phone="";
		String sql_end="";
		bfr_name = request.getParameter("bfr_name2");
		bfrdw_name = request.getParameter("bfrdw_name2");
		dw_phone = request.getParameter("bfr_phone2");
		
		
		if(bfr_name!=null&&!bfr_name.equals("")){
			bfr_name = request.getParameter("bfr_name2").trim();
			sql_end += "AND bfr_name like '%"+bfr_name+"%' ";
		}
		if(bfrdw_name!=null&&!bfrdw_name.equals("")){
			bfrdw_name = request.getParameter("bfrdw_name2").trim();
			sql_end += "AND bfrdw_name like '"+bfrdw_name+"%' ";
		}
		if(dw_phone!=null&&!dw_phone.equals("")){
			dw_phone = request.getParameter("bfr_phone2").trim();
			sql_end += "AND dw_phone = '"+dw_phone+"'";
		}
		String count_sql = "select A.* from ( SELECT * FROM (select DISTINCT BFR.G_FLAG,bfr.AAK110 bfr_id,bfr.AAB002 bfr_name,bfr.AAP110 bfdw_id,t3.AAP001 bfrdw_name,bfr.AAR012 dw_phone,t2.bfr_count  FROM NEIMENG0117_AK11 bfr  LEFT JOIN  (select BFRJD.AAK110,count(*) bfr_count from NEIMENG0117_AC08 bfrjd WHERE bfrjd.AAC111 = '0' GROUP BY BFRJD.AAK110)t2 on bfr.AAK110 = t2.aak110 LEFT JOIN  (SELECT bfdw.AAP001,bfdw.AAP110  FROM NEIMENG0117_AP11 bfdw )t3 on t3.AAP110 = bfr.AAP110 )WHERE G_FLAG = '1' )A where 1=1 ";
		String sql = "select * from (select A.*,ROWNUM RN from ( SELECT * FROM (select DISTINCT BFR.G_FLAG,bfr.AAK110 bfr_id,bfr.AAB002 bfr_name,bfr.AAP110 bfdw_id,t3.AAP001 bfrdw_name,bfr.AAR012 dw_phone,t2.bfr_count "
				+ " FROM NEIMENG0117_AK11 bfr   LEFT JOIN "
				+ " (select BFRJD.AAK110,count(*) bfr_count from NEIMENG0117_AC08 bfrjd WHERE bfrjd.AAC111 = '0' GROUP BY BFRJD.AAK110)t2"
				+ " on bfr.AAK110 = t2.aak110 LEFT JOIN  (SELECT bfdw.AAP001,bfdw.AAP110  FROM NEIMENG0117_AP11 bfdw )t3 on t3.AAP110 = bfr.AAP110 )WHERE G_FLAG = '1')A WHERE 1=1 "+sql_end;
		int total = this.getBySqlMapper.findRecords(count_sql+sql_end).size();
		sql += "and ROWNUM<="+size*page+")where rn>"+number+"";
		List<Map> bfrList = this.getBySqlMapper.findRecords(sql); 
		Map bfrMap = new HashMap<>();
		if(bfrList.size()>0){
			JSONArray jsa = new JSONArray();
			for(int i=0;i<bfrList.size();i++){
				bfrMap = bfrList.get(i);
				JSONObject js = new JSONObject();
				for(Object key : bfrMap.keySet()){
					js.put(key, bfrMap.get(key));
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
	 * 
	 * @description 
	 * @method  addBfgbInfo
	 * @param　request
	 * @author luoshuai 
	 * @date 2017年3月28日 下午 5:36:41
	 *
	 */
	@RequestMapping("addBfgbInfo.do")
	public ModelAndView addBfgbInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		/*String dwid = request.getParameter("dwid");*/
		String form_val = request.getParameter("form_val");
		JSONObject form_json = JSONObject.fromObject(form_val);//表单数据
		
		String bfr_name = "", sex = "", bfr_card = "", bfrdw_name = "", dw_address = "", birthday = "", bfr_zzmm = "", bfr_phone = "", relative = "",
				zhiwei_level="",studying="",jishutechang="",duizhang="",diyi_shuji="",zhucun_duiyuan="",bfrdw_id="",xzqh_id="";
		String where = "";
	
		if(form_json.get("bfr_name")!=null&&!form_json.get("bfr_name").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
			bfr_name = form_json.get("bfr_name").toString();
			where += "AAB002='"+form_json.get("bfr_name")+"',";
		}else{
			where += "AAB002='',";
		}
		
		if(form_json.get("xzqh_id")!=null&&!form_json.get("xzqh_id").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
			xzqh_id = form_json.get("xzqh_id").toString();
			where += "AAR008='"+form_json.get("xzqh_id")+"',";
		}else{
				where += "AAR008='',";
		}
		
			
	/*	if(form_json.get("sex")!=null&&!form_json.get("sex").equals("请选择")){//下拉框，一定有值，但是要筛除“请选择”
			sex = form_json.get("sex").toString();
			where += "AAB003='"+form_json.get("sex")+"',";
		}else{
			if(form_json.get("sex").equals("请选择")){
				where += "AAB003='',";
			}
		}*/
		
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
		
		if(request.getParameter("bfrdw_id")!=null&&!request.getParameter("bfrdw_id").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
			bfrdw_id =request.getParameter("bfrdw_id");
			where += "AAP110='"+request.getParameter("bfrdw_id")+"',";
		}else{
			where += "AAP110='',";
		}
		/*if(form_json.get("ad_bf_dw")!=null&&!form_json.get("ad_bf_dw").equals("请选择")){//下拉框，一定有值，但是要筛除“请选择”
			ad_bf_dw = form_json.get("ad_bf_dw").toString();
			where += "AAP001='"+form_json.get("ad_bf_dw")+"',";
		}else{
			if(form_json.get("ad_bf_dw").equals("请选择")){
				where += "AAP001='',";
			}
		}*/
		
		
		if(form_json.get("dw_address")!=null&&!form_json.get("dw_address").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
			dw_address = form_json.get("dw_address").toString();
			where += "AAR013='"+form_json.get("dw_address")+"',";
		}else{
			where += "AAR013='',";
		}
		
		if(form_json.get("birthday")!=null&&!form_json.get("birthday").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
			birthday = form_json.get("birthday").toString();
/*			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
			String c=sdf.format(current);
			int date = Integer.parseInt(c);*/
			where += "AAB005='"+form_json.get("birthday")+"',";
		}else{
			where += "AAB005='',";
		}
		
		
	/*	if(form_json.get("bfr_zzmm")!=null&&!form_json.get("bfr_zzmm").equals("请选择")){//下拉框，一定有值，但是要筛除“请选择”
			bfr_zzmm = form_json.get("bfr_zzmm").toString();
			where += "AAK033='"+form_json.get("bfr_zzmm")+"',";
		}else{
			if(form_json.get("bfr_zzmm").equals("请选择")){
				where += "AAK033='',";
			}
		}
		*/
		
		if(form_json.get("bfr_phone")!=null&&!form_json.get("bfr_phone").equals("")){//可以为空，如果没有取到值，证明前台为空，数据库需要清空此列
			bfr_phone = form_json.get("bfr_phone").toString();
			where += "AAR012='"+form_json.get("bfr_phone")+"'";
		}else{
			where += "AAR012=''";
		}
		
	/*	
		if(form_json.get("relative")!=null&&!form_json.get("relative").equals("请选择")){//下拉框，一定有值，但是要筛除“请选择”
			relative = form_json.get("relative").toString();
			where += "AAP004='"+form_json.get("relative")+"',";
		}else{
			if(form_json.get("relative").equals("请选择")){
				where += "AAP004='',";
			}
		}*/
		
		/*if(form_json.get("zhiwei_level")!=null&&!form_json.get("zhiwei_level").equals("请选择")){//下拉框，一定有值，但是要筛除“请选择”
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
		*/
		
		String sql = "";
	try{
		if(form_json.get("bfr_id")!=null&&!form_json.get("bfr_id").equals("")){
			/*where += "da_company_id="+dwid;*/
//			if(where.length()>0){
//				where = where.substring(0, where.length()-1);
//			}
			sql = "update NEIMENG0117_AK11 set "+where+" where AAK110="+form_json.get("bfr_id");
			this.getBySqlMapper.update(sql);
			 response.getWriter().write("1");
		}else{
			sql = "select count(*) from NEIMENG0117_AK11 WHERE AAB004 ='"+bfr_card+"'";
			/*List<Map> countList =this.getBySqlMapper.findRecords(sql);*/
		/*	System.out.println(countList.size());*/
			int count = this.getBySqlMapper.findrows(sql);
			 if(count>0){
				 response.getWriter().write("2");
			 }else{
				 long   bfr_id=  System.currentTimeMillis();
					sql = "insert into NEIMENG0117_AK11 (AAK110,AAB002,AAB003,AAB004,AAP001,AAR013,AAB005,AAK033,AAR012,AAP004,AAF031,AAK036,AAK037,AAK031,AAK032,AAK039,G_FLAG,AAP110,AAR008)VALUES "+
							"('"+bfr_id+"','"+bfr_name+"','"+sex+"','"+bfr_card+"','"+bfrdw_name+"','"+dw_address+"','"+birthday+"','"+bfr_zzmm+"','"+bfr_phone+"','"+relative+"',"
									+ "'"+zhiwei_level+"','"+studying+"','"+jishutechang+"','"+duizhang+"','"+diyi_shuji+"','"+zhucun_duiyuan+"','1','"+bfrdw_id+"','"+xzqh_id+"')";
					this.getBySqlMapper.insert(sql);
					 response.getWriter().write("1");
			 }
		}
		
	}catch (Exception e){
		response.getWriter().write("0");
	}

	/*	try{
			
			
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
		}*/
		return null;
	}
	
	/**
	 * 
	 * @description 获得更改帮扶人之前的信息
	 * @method  getUpdateBfrInfo
	 * @param　request
	 * @author luoshuai 
	 * @date 2017年3月29日 上午11:38:27
	 *
	 */
	@RequestMapping("getUpdateBfgbInfo.do")
	public ModelAndView getUpdateBfgbInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String bfr_id = request.getParameter("bfr_id");
		//根据bfr_id查询帮扶人信息 然后再更改页面进行展示
		String sql = "SELECT T1.*,aap001  bfrdw_name FROM (select AAK110 bfr_id,AAB002 bfr_name,AAB003 sex,AAB004 bfr_card,AAP110 bfrdw_id,AAR013 dw_address,AAB005 birthday,AAK033 bfr_zzmm,AAR012 bfr_phone,AAP004 relative,AAF031 zhiwei_level,AAK036 studying,AAK037 jishu_techang,AAK031 duizhang,AAK032 diyi_shuji,AAK039 zhucun_duiyuan,G_FLAG,AAR008 xzqh_id "
				+ " FROM NEIMENG0117_AK11  where  AAK110 ='"+bfr_id+"' )T1"
				+ " LEFT JOIN NEIMENG0117_AP11 ON T1.bfrdw_id = NEIMENG0117_AP11.AAP110";
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
			obj.put("xzqh_id", val.get("XZQH_ID")==null?"":val.get("XZQH_ID"));
			obj.put("bfrdw_id", val.get("BFRDW_ID")==null?"":val.get("BFRDW_ID"));
			obj.put("G_FLAG", val.get("G_FLAG")==null?"":val.get("G_FLAG"));
			json.put("bfgb", obj);
		}
		
		response.getWriter().write(json.toString());
		response.getWriter().close();
		return null;
	
	}
	/**
	 * 
	 * @description 删除帮扶干部信息
	 * @method  deleteBfgbInfo
	 * @param　request
	 * @author luoshuai 
	 * @date 2017年3月29日 下午3:02:15
	 *
	 */
	@RequestMapping("deleteBfgbInfo.do")
	public ModelAndView deleteBfgbInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String bfr_id=request.getParameter("bfr_id");
		String sql="update  NEIMENG0117_AK11 set G_FLAG='0' where AAK110="+bfr_id;
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
	/**
	 * 
	 * @description 获得 帮扶单位
	 * @method  getbangfudanwei
	 * @param　request
	 * @author luoshuai 
	 * @date 2017年3月31日 下午2:54:17
	 *
	 */
	@RequestMapping("getbangfudanwei.do")
	public ModelAndView getbangfudanwei(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String xzqh_id = request.getParameter("xzqh_id").trim();
		String v= request.getParameter("v").trim();
		int t = 0;
		if(v.equals("v2")){
			t = 4;
		}else if(v.equals("v3")){
			t=6;
		}else if(v.equals("v4")){
			t=9;
		}else{
			t=12;
		}
		/*if(xzqh_id!=null&&!xzqh_id.equals("")){
			where = " where t1.sys_company_id="+sys_company_id;
		}*/
		String sql="";
		if(t>0){
			 sql = "select AAP110 DANWEI_ID,AAP001 DANWEI_NAME,AAR011 LINGDAO,AAR012 PHONE from AP11 a WHERE AAR001 LIKE '"+xzqh_id.substring(0, t)+"%' ";
		}else{
			 sql = "select AAP110 DANWEI_ID,AAP001 DANWEI_NAME,AAR011 LINGDAO,AAR012 PHONE from AP11 a WHERE AAR001 LIKE '"+xzqh_id+"%' ";
		}
		List<Map> Patient_st_List = this.getBySqlMapper.findRecords(sql);
		
		JSONArray jsa=new JSONArray();
		if(Patient_st_List.size()>0){
			
			for(int i = 0;i<Patient_st_List.size();i++){
				Map Patient_st_map = Patient_st_List.get(i);
				JSONObject val = new JSONObject();
				for (Object key : Patient_st_map.keySet()) {
					val.put("danwei_id", Patient_st_map.get("DANWEI_ID"));
					
//					if(Patient_st_map.get("v1").toString().length()>15){
//						val.put("v1", Patient_st_map.get("v1").toString().substring(0, 13)+"<br>"+Patient_st_map.get("v1").toString().substring(13));
//					}else{
//						val.put("v1", Patient_st_map.get("v1"));
//					}
					val.put("danwei_name", Patient_st_map.get("DANWEI_NAME"));
					val.put("lingdao", Patient_st_map.get("LINGDAO")==null?"":Patient_st_map.get("LINGDAO"));
					val.put("phone", Patient_st_map.get("PHONE")==null?"":Patient_st_map.get("PHONE"));
					val.put("xzqh_id", xzqh_id);
				}
				jsa.add(val);
			}
		}
		
		JSONObject json = new JSONObject();
		json.put("message", "");
		json.put("value", jsa);
		json.put("code", Patient_st_List.size());
		json.put("redirect", "");
		
		response.getWriter().write(json.toString());
		return null;
	}
	
	/**
	 * 
	 * @description 
	 * @method  getQixianListController
	 * @param　request
	 * @author luoshuai 
	 * @date 2017年3月31日 下午3:12:22
	 *
	 */
	@RequestMapping("getQixianList.do")
	public ModelAndView getQixianList(HttpServletRequest request,HttpServletResponse response) throws IOException{


		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String xzqh_id = request.getParameter("xzqh_id");
		//旗县
		String sql="select * from sys_company c where com_level=3";
		List<Map> list=getBySqlMapper.findRecords(sql);
		
		JSONArray jsonArray =new JSONArray();
		for(Map val:list){
			JSONObject obj=new JSONObject ();
			obj.put("com_code",val.get("COM_CODE")==null?"":val.get("COM_CODE"));
			obj.put("com_name", val.get("COM_NAME")==null?"":val.get("COM_NAME"));
			jsonArray.add(obj);
		}
		response.getWriter().write(jsonArray.toString());
		response.getWriter().close();
		return null;
	}
	
	/**
	 * 
	 * @description 获得行政区划级别
	 * @method  getLevel
	 * @param　request
	 * @author luoshuai 
	 * @date 2017年4月1日 下午4:38:08
	 *
	 */
	@RequestMapping("getLevel.do")
	public ModelAndView getLevel(HttpServletRequest request,HttpServletResponse response) throws IOException{


		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String xzqh_id = request.getParameter("xzqh_id");
		//旗县
		String sql="select COM_LEVEL FROM SYS_COMPANY  WHERE  COM_CODE = '"+xzqh_id+"'";
		List<Map> list=getBySqlMapper.findRecords(sql);
		
		JSONObject obj=new JSONObject ();
		for(Map val:list){
			obj.put("v",val.get("COM_LEVEL")==null?"":val.get("COM_LEVEL"));
		}
		response.getWriter().write(obj.toString());
		response.getWriter().close();
		return null;
	}
	
	
}
