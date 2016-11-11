package com.gistone.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gistone.MyBatis.config.GetBySqlMapper;
/**
 * 扶贫手册-工作台账
 * @author chendong
 *
 */
@RestController
@RequestMapping
public class SW4_Controller{
	
	@Autowired
	private GetBySqlMapper getBySqlMapper;
	
	
	
	//获取字段数据转换方法   type值= 11:民族  7：性别  10:户主关系 12:文化程度 	13:在校生状况  15:劳动技能  16：务工情况 17:务工时间 14：健康状况   32: 是否现役军人  18:是否参加新型农村合作医疗（城乡居民基本医疗保险）
	//19：是否参加新型农村社会养老保险（城乡居民社会养老保险）20:是否参加城镇职工基本养老保险     25:联系电话    	26:开户银行  sys_standard：识别标准     22:贫困户属性（贫困类型）    29:是否军烈属
	//30:是否独生子女户   31:是否双女户      21:脱贫标志(脱贫属性)
	public String mianZhuan(String mid,String type) {
		Map map = new HashMap();
		if (!mid.equals("")) {
			
		
			int a=Integer.parseInt(mid);
			if (type.equals("11")) { //民族
				if (a==99) {
					a=57;
				}
				String[] arrayStr={"汉族","满族", "回族", "蒙古族", "藏族", "维吾尔族", "苗族", "彝族", "壮族", "布依族", "朝鲜族", "侗族", "瑶族", "白族", "土家族", 
						"哈尼族", "哈萨克族", "傣族", "黎族", "僳僳族", "佤族", "畲族", "高山族", "拉祜族","水族","东乡族","纳西族","景颇族","柯尔克孜族","土族",
						"达斡尔族","仫佬族","羌族","布朗族","撒拉族","毛南族","仡佬族","锡伯族","阿昌族","普米族","塔吉克族","怒族","乌孜别克族","俄罗斯族",
						"鄂温克族","德昂族","保安族","裕固族","京族","塔塔尔族","独龙族","鄂伦春族","赫哲族","门巴族","珞巴族","基诺族","其他"};
				for(int i=0;i<58;i++){
					map.put(i, arrayStr[i]);
				}
			}else if (type.equals("7")) { //性别
				if (a==9) {
					a=3;
				}
				String[] arrayStr={"男性","女性","未说明的性别"};
				for(int i=0;i<4;i++){
					map.put(i, arrayStr[i]);
				}
			}else if (type.equals("10")) { //户主关系
				if (a==99) {
					a=21;
				}
				String[] arrayStr={"户主","配偶","之子","之女","之儿媳","之女婿","之孙子","之孙女","之外孙子","之外孙女","之父","之母","之岳父","之岳母","之公公",
									"之婆婆","之祖父","之祖母","之外祖父","之外祖母","其他"};
				for(int i=0;i<22;i++){
					map.put(i, arrayStr[i]);
				}
			}else if (type.equals("12")) { //12:文化程度 
				String[] arrayStr={"文盲或半文盲","小学","初中","高中","大专及以上","学龄前儿童"};
				for(int i=0;i<7;i++){
					map.put(i, arrayStr[i]);
				}
			}else if (type.equals("13")) {  //13:在校生状况
				String[] arrayStr={"非在校生","学前教育","小学","七年级","八年级","九年级","高中一年级","高中二年级","高中三年级","中职一年级","中职二年级","中职三年级",
									"高职一年级","高职二年级","高职三年级","大专及以上"};
				for(int i=0;i<17;i++){
					map.put(i, arrayStr[i]);
				}
			}else if (type.equals("15")) {
				String[] arrayStr={"普通劳动力","技能劳动力","丧失劳动力","无劳动力"};
				for(int i=0;i<5;i++){
					map.put(i, arrayStr[i]);
				}
			}else if (type.equals("16")) {
				if (a==99) {
					a=5;
				}
				String[] arrayStr={"乡（镇）内务工","乡（镇）外县内务工","县外省内务工","省外务工","其他"};
				for(int i=0;i<6;i++){
					map.put(i, arrayStr[i]);
				}
			}else if(type.equals("14")){
				String[] arrayStr={"健康","长期慢性病","患有大病","残疾"};
				for(int i=0;i<5;i++){
					map.put(i, arrayStr[i]);
				}
			}else if (type.equals("32")) {
				if (a==0) {
					return "否";
				}else {
					return "是";
				}
			}else if (type.equals("18")) {
				if (a==0) {
					return "没参加";
				}else {
					return "参加";
				}
			}else if (type.equals("19")) {
				if (a==0) {
					return "没参加";
				}else {
					return "参加";
				}
			}else if (type.equals("20")) {
				if (a==0) {
					return "没参加";
				}else {
					return "参加";
				}
			}else if (type.equals("sys_standard")) {
				String[] arrayStr={"国家标准","省定标准","市定标准"};
				for(int i=0;i<4;i++){
					map.put(i, arrayStr[i]);
				}
			}else if (type.equals("22")) {
				String[] arrayStr={"一般贫困户","低保户","五保户","低保贫困户","低收入农户（是否可以去掉）"};
				for(int i=0;i<6;i++){
					map.put(i, arrayStr[i]);
				}
			}else if (type.equals("23")){
				if (a==99) {
					a=12;
				}
				String[] arrayStr={"因病","因残","因学","因灾","缺土地","缺水","缺技术","缺劳力","缺资金","交通条件落后","自身发展动力不足","其他"};
				for(int i=0;i<13;i++){
					map.put(i, arrayStr[i]);
				}
			}else if (type.equals("33")) {
				String[] arrayStr={"因病","因残","因学","因灾","缺土地","缺水","缺技术","缺劳力","缺资金","交通条件落后","自身发展动力不足","因婚","其他"};
				for(int i=0;i<14;i++){
					map.put(i, arrayStr[i]);
				}
			}else if (type.equals("29")) {
				if (a==0) {
					return "否";
				}else {
					return "是";
				}
			}else if (type.equals("30")) {
				if (a==0) {
					return "否";
				}else {
					return "是";
				}
			}else if (type.equals("31")) {
				if (a==0) {
					return "否";
				}else {
					return "是";
				}
			}else if (type.equals("21")) {
				String[] arrayStr={"未脱贫","脱贫","预脱贫","返贫"};
				for(int i=0;i<5;i++){
					map.put(i, arrayStr[i]);
				}
			}
			else{
				return "";
			}
			
			return (String) map.get(a);
			
		}else {
			return "";
		}
			
	}
	
	
	


	


	
	
	
	/**
	 *  查看贫困户的台账信息
	 * @author chendong
	 * @date 2016年8月5日
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	
	@RequestMapping("getTz_1.do")
	public void getTz_1(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String pkid=request.getParameter("pkid");
		String id ="01";
		mianZhuan(id,"1");
		String sql="select v1,v2,v3,v4,v5,v6,v7,v8,v10,v11,v28,v12,v13,v14,v15,v16,v17,v19,v32,v25,v26,v27,sys_standard,v22,v29,v30,v31,v23,v33 from da_household where pkid="+pkid;
		List<Map> list=this.getBySqlMapper.findRecords(sql);
		//户主信息
		JSONArray jsonArray1 =new JSONArray();
		if(list.size()>0){
			
			for(int i = 0;i<list.size();i++){
				Map Patient_st_map = list.get(i);
				JSONObject val = new JSONObject();
				for (Object key : Patient_st_map.keySet()) {
					val.put(key, Patient_st_map.get(key));
				}
				jsonArray1.add(val);
			}
		}
		//家庭成员
		JSONArray jsonArray2 =new JSONArray();
		String xian_sql="select v6,v7,v8,v10,v11,v28,v12,v13,v14,v15,v16,v17,v32,v19 from da_member where da_household_id="+pkid;
		List<Map> xian_list=getBySqlMapper.findRecords(xian_sql);
		if(xian_list.size()>0){
			for(int i=0;i<xian_list.size();i++){
				if(xian_list.get(i)==null){
					jsonArray2.add("");
				}else{
					Map xian_map=xian_list.get(i);
					JSONObject obj=new JSONObject ();
					for(Object key:xian_map.keySet()){
						obj.put(key, xian_map.get(key));
					}
					jsonArray2.add(obj);
				}
				
			}
		}
		//收入情况
		JSONArray jsonArray5 =new JSONArray();
		String dqsr_sql="SELECT v28,v30,v41,v12,v10,v16,v20,v24,v26,v43,v22,v14 FROM da_current_income where da_household_id="+pkid;
		List<Map> dqsr_list=getBySqlMapper.findRecords(dqsr_sql);
		if(dqsr_list.size()>0){
			for(int i=0;i<dqsr_list.size();i++){
				if(dqsr_list.get(i)==null){
					jsonArray5.add("");
				}else{
					Map dqsr_map=dqsr_list.get(i);
					JSONObject obj=new JSONObject ();
					for(Object key:dqsr_map.keySet()){
						obj.put(key, dqsr_map.get(key));
					}
					jsonArray5.add(obj);
				}
				
			}
		}
	
		
		//生产条件
		JSONArray jsonArray3 =new JSONArray();
		String sc_sql="select v1,v2,v3,v4,v13,v5,v14 FROM da_production where da_household_id="+pkid;
		List<Map> sc_list=getBySqlMapper.findRecords(sc_sql);
		if(sc_list.size()>0){
			for(int i=0;i<sc_list.size();i++){
				if(sc_list.get(i)==null){
					jsonArray3.add("");
				}else{
					Map sc_map=sc_list.get(i);
					JSONObject obj=new JSONObject();
					for(Object key:sc_map.keySet()){
						obj.put(key, sc_map.get(key));
					}
					jsonArray3.add(obj);
				}
			}
		}
		//生活条件
		JSONArray jsonArray4 =new JSONArray();
		String sh_sql="SELECT v5,v7,v6,v1,v8,v9,v10,v11,v12 FROM da_life where da_household_id="+pkid;
		List<Map> sh_list=getBySqlMapper.findRecords(sh_sql);
		if(sh_list.size()>0){
			for(int i=0;i<sh_list.size();i++){
				if(sh_list.get(i)==null){
					jsonArray4.add("");
				}else{
					JSONObject obj=new JSONObject();
					Map sh_map=sh_list.get(i);
					for(Object key:sh_map.keySet()){
						obj.put(key, sh_map.get(key));
					}
					jsonArray4.add(obj);
				}
			}
				
		}
		
		//易地搬迁户需求
		JSONArray jsonArray6 =new JSONArray();
		String ydbq_sql="SELECT * FROM (select v3 vv3,da_household_id from da_life)a  LEFT JOIN da_household_move b  ON a.da_household_id=b.da_household_id WHERE a.da_household_id="+pkid;
		List<Map> ydbq_list=getBySqlMapper.findRecords(ydbq_sql);
		if(ydbq_list.size()>0){
			for(int i=0;i<ydbq_list.size();i++){
				if(ydbq_list.get(i)==null){
					
				}else{
					JSONObject obj=new JSONObject();
					Map ydbq_map=ydbq_list.get(i);
					for(Object key:ydbq_map.keySet()){
						obj.put(key, ydbq_map.get(key));
					}
					jsonArray6.add(obj);
				}
			}
		}
		
		//帮扶人情况
		JSONArray jsonArray7 =new JSONArray();
		String bfr_sql="SELECT b.col_name,b.v1,b.v2,b.v3,t2.v1 as com_name,b.v4,b.v5,b.v6,t2.v2 as v7,b.telephone FROM sys_personal_household_many a "
				+ "LEFT JOIN sys_personal b ON a.sys_personal_id = b.pkid join da_company t2 on b.da_company_id=t2.pkid where a.da_household_id="+pkid;
		List<Map> bfr_list=getBySqlMapper.findRecords(bfr_sql);
		if(bfr_list.size()>0){
			for(int i=0;i<bfr_list.size();i++){
				if(bfr_list.get(i)==null){
					jsonArray7.add("");
				}else{
					JSONObject obj=new JSONObject();
					Map bfr_map=bfr_list.get(i);
					for(Object key:bfr_map.keySet()){
						obj.put(key, bfr_map.get(key));
					}
					jsonArray7.add(obj);
				}
			}
		}
		//生产经营性支出
		JSONArray jsonArray8 =new JSONArray();
		String dqzc_sql="SELECT v2,v4,v6,v8,v10,12,14,16,v18 FROM da_current_expenditure where da_household_id="+pkid;
		List<Map> dqzc_list=getBySqlMapper.findRecords(dqzc_sql);
		if(dqzc_list.size()>0){
			for(int i=0;i<dqzc_list.size();i++){
				if(dqzc_list.get(i)==null){
					jsonArray8.add("");
				}else{
					Map dqzc_map=dqzc_list.get(i);
					JSONObject obj=new JSONObject ();
					for(Object key:dqzc_map.keySet()){
						obj.put(key, dqzc_map.get(key));
					}
					jsonArray8.add(obj);
				}
			}
		}
		response.getWriter().write("{\"data1\":"+jsonArray1.toString() +
				",\"data2\":"+jsonArray2.toString()+",\"data3\":"+jsonArray3.toString()+
				",\"data4\":"+jsonArray4.toString()+",\"data5\":"+jsonArray5.toString()+"," +
				"\"data6\":"+jsonArray6.toString()+",\"data7\":"+jsonArray7.toString()+",\"data8\":"+jsonArray8.toString()+"}");
		response.getWriter().close();
	}
	/**
	 * 台账信息、脱贫计划、帮扶措施、工作台账、贫困收入监测表
	 * @author chendong
	 * @date 2016年8月5日
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("getTz_2.do")
	public void getTz_2(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String pkid=request.getParameter("pkid");
		String year=request.getParameter("year");
		
		//脱贫计划
		JSONArray jsonArray1=new JSONArray();
		String  tp_sql="select * from da_help_plan where da_household_id="+pkid+" and  v1 ='"+year+"'";
		List<Map> tp_list=this.getBySqlMapper.findRecords(tp_sql);
		if(tp_list.size()>0){
			for(int i=0;i<tp_list.size();i++){
				JSONObject obj=new JSONObject();
				Map tp_map=tp_list.get(i);
				for(Object key:tp_map.keySet()){
					obj.put(key,tp_map.get(key));
				}
				jsonArray1.add(obj);
			}
		}
		//帮扶措施
		JSONArray jsonArray2=new JSONArray();
		String bfcs_sql="select * from da_help_tz_measures where da_household_id='"+pkid+"' and  v7='"+year+"'";
		List<Map> bfcs_list=this.getBySqlMapper.findRecords(bfcs_sql);
		if(bfcs_list.size()>0){
			for(int i=0;i<bfcs_list.size();i++){
				JSONObject obj=new JSONObject();
				Map bfcs_map=bfcs_list.get(i);
				for(Object key : bfcs_map.keySet()){
					obj.put(key,bfcs_map.get(key));
				}
				jsonArray2.add(obj);
			}
		}
		
		//工作台账
		JSONArray jsonArray3=new JSONArray();
		String gztz_sql="select * from da_work_tz where da_household_id="+pkid+" and v5="+year;
		List<Map> gztz_list=this.getBySqlMapper.findRecords(gztz_sql);
		if(gztz_list.size()>0){
			for(int i=0;i<gztz_list.size();i++){
				JSONObject obj=new JSONObject();
				Map gztz_map=gztz_list.get(i);
				for(Object key: gztz_map.keySet()){
					obj.put(key, gztz_map.get(key));
				}
				jsonArray3.add(obj);
			}
		}
		response.getWriter().write("{\"data1\":"+jsonArray1.toString()+",\"data2\":"+jsonArray2.toString()+",\"data3\":"+jsonArray3.toString()+"}");
		response.getWriter().close();
	}
	
	/**
	 * 帮扶信息查询的默认页
	 * @author chendong
	 * @date 2016年8月5日
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("getTz_3.do")
	public void getCx_1(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String pageSize = request.getParameter("pageSize");
		String pageNumber = request.getParameter("pageNumber");

		
		String cha_sbbz ="";//识别标准
		String cha_pksx ="";//贫困户属性
		String cha_zpyy ="";//致贫原因
		String cha_mz ="";//户主民族
		String cha_renkou ="";//贫困户人口
		String cha_banqian ="";//是否纳入易地扶贫搬迁 那我能说啥  
		String cha_v6 = "";//户主姓名
		String cha_v8 = "";//身份证号
		String cha_v8_1 = "";//年龄范围
		String nm_qx="";//新加的内蒙区县 新增的
		String com_level="";//获取层级
		String pkid="";//获取id
		String str = "";
		
		com_level = request.getParameter("com_level");
		pkid = request.getParameter("pkid");
		nm_qx = request.getParameter("nm_qx").trim();
		

		str = "a.V"+com_level+"='"+nm_qx+"' and";


	
		if(request.getParameter("cha_v6")!=null&&!request.getParameter("cha_v6").equals("")){
			cha_v6 = request.getParameter("cha_v6").trim();
			str += " a.V6 like '%"+cha_v6+"%' and";
		}
		
		if(request.getParameter("cha_v8")!=null&&!request.getParameter("cha_v8").equals("")){
			cha_v8 = request.getParameter("cha_v8").trim();
			str += " a.V8 like '%"+cha_v8+"%' and";
		}
		if(request.getParameter("cha_v8_1")!=null&&!request.getParameter("cha_v8_1").equals("请选择")){
			cha_v8_1 = request.getParameter("cha_v8_1").trim();
			if(cha_v8_1.equals("大于60岁")){
				str += " LENGTH(a.V8)>=18 and year(now())- year(substr(a.V8,7,8))>=60 and";
			}else if(cha_v8_1.equals("小于16岁")){
				str += " LENGTH(a.V8)>=18 and year(now())- year(substr(a.V8,7,8))<=16 and";
			}else if(cha_v8_1.equals("17岁至59岁")){
				str += " LENGTH(a.V8)>=18 and (year(now())- year(substr(a.V8,7,8))>=17 or year(now())- year(substr(a.V8,7,8))>=59) and";
			}
		}
		if(request.getParameter("cha_sbbz")!=null&&!request.getParameter("cha_sbbz").equals("请选择")&&!"".equals(request.getParameter("cha_sbbz"))){
			cha_sbbz = request.getParameter("cha_sbbz").trim();
			str += " a.SYS_STANDARD='"+cha_sbbz+"' and";
		}
		if(request.getParameter("cha_pksx")!=null&&!request.getParameter("cha_pksx").equals("请选择")&&!"".equals(request.getParameter("cha_pksx"))){
			cha_pksx = request.getParameter("cha_pksx").trim();
			if(cha_pksx.indexOf(",")>0){
				for(int k=0;k<cha_pksx.split(",").length;k++){
					if(k==cha_pksx.split(",").length-1){
						str += " a.V22='"+cha_pksx.split(",")[k]+"' and";
					}else{
						str += " a.V22='"+cha_pksx.split(",")[k]+"' or";
					}
				}
			}else{
				str += " a.V22='"+cha_pksx+"' and";
			}
		}
		if(request.getParameter("cha_zpyy")!=null&&!request.getParameter("cha_zpyy").equals("请选择")&&!"".equals(request.getParameter("cha_zpyy"))){
			cha_zpyy = request.getParameter("cha_zpyy").trim();
			if(cha_zpyy.indexOf(",")>0){
				for(int b=0;b<cha_zpyy.split(",").length;b++){
					if(b==cha_zpyy.split(",").length-1){
						str += " a.V23 like '%"+cha_zpyy.split(",")[b]+"%' and";
					}else{
						str += " a.V23 like '%"+cha_zpyy.split(",")[b]+"%' or";
					}
				}
			}else{
				str += " a.V23 like '%"+cha_zpyy+"%' and";
			}
		}
		if(request.getParameter("cha_mz")!=null&&!request.getParameter("cha_mz").equals("请选择")&&!"".equals(request.getParameter("cha_mz"))){
			cha_mz = request.getParameter("cha_mz").trim();
			str += " a.V11='"+cha_mz+"' and";
		}
		if(request.getParameter("cha_renkou")!=null&&!request.getParameter("cha_renkou").equals("请选择")){
			cha_renkou = request.getParameter("cha_renkou").trim().substring(0,1);
			if("5".equals(cha_renkou)){
				str += " a.V9>=5 and";
			}else{
				str += " a.V9='"+cha_renkou+"' and";
			}
		}
		//如果易地扶贫搬迁条件被选择
		if(request.getParameter("cha_banqian")!=null&&!request.getParameter("cha_banqian").equals("请选择")&&!"".equals(request.getParameter("cha_banqian"))){
			cha_banqian = request.getParameter("cha_banqian").trim();
			if(cha_banqian.indexOf(",")>0){
				for(int t=0;t<cha_banqian.split(",").length;t++){
					if(t==cha_banqian.split(",").length-1){
						str += " a.V21='"+cha_banqian.split(",")[t]+"' and";
					}else{
						str += " a.V21='"+cha_banqian.split(",")[t]+"' or";
					}
				}
			}else{
				str += " a.V21='"+cha_banqian+"' and";
			}
		}
		
		String count_st_sql = "select count(*) from (select a.pkid,a.v1,a.v2,a.v3,a.v4,a.v5 from da_household a where "+str.substring(0, str.length()-3)+") a1 ";
		String people_sql = "select rownum,a1.pkid,v2,v3,v4,v5,v6,v9,v22,v23,v11,sys_standard  from (select a.pkid,a.v2,a.v3,a.v4,a.v5,a.v6,a.v9,a.v22,a.v23,a.v11,a.sys_standard from da_household a where "+str.substring(0, str.length()-3)+") a1";
		
		if(com_level.equals("4") || com_level.equals("5")){
			count_st_sql += " JOIN (select x.com_name as g_name,y.com_name as d_name from sys_company x join sys_company y on x.pkid=y.com_f_pkid where y.pkid="+pkid+" ) t1 on a1.v"+com_level+"=t1.d_name where a1.v"+(Integer.parseInt(com_level)-1)+"=t1.g_name";
			people_sql += " JOIN (select x.com_name as g_name,y.com_name as d_name from sys_company x join sys_company y on x.pkid=y.com_f_pkid where y.pkid="+pkid+" ) t1 on a1.v"+com_level+"=t1.d_name where a1.v"+(Integer.parseInt(com_level)-1)+"=t1.g_name";
		}
		int size = Integer.parseInt(pageSize);
		int number = Integer.parseInt(pageNumber);
		int page = number == 0 ? 1 : (number/size)+1;
		
		//带条件，按照条件查询
		people_sql += "  where rownum>"+number+" and rownum<"+size+"  order by a1.v2,a1.pkid";
//		System.out.println(people_sql);
		int total = this.getBySqlMapper.findrows(count_st_sql);
		List<Map> Patient_st_List = this.getBySqlMapper.findRecords(people_sql);
		if(Patient_st_List.size()>0){
			JSONArray jsa=new JSONArray();
			for(int i = 0;i<Patient_st_List.size();i++){
				Map Patient_st_map = Patient_st_List.get(i);
				JSONObject val = new JSONObject();
				for (Object key : Patient_st_map.keySet()) {
					
					val.put(key, Patient_st_map.get(key));
					
					if(key.toString().equals("V6")){
						val.put(key, "<a onclick='chakan_info(\""+Patient_st_map.get("PKID")+"\")' data-toggle=\"modal\" >"+Patient_st_map.get(key)+"</a>");
					}
					val.put("ewm", "<a onclick=\"show_tupian("+Patient_st_map.get("PKID")+")\" >获取二维码</a>");
					if(key.toString().equals("col_name")){
						if("".equals(Patient_st_map.get("col_name")+"")){
							val.put("col_name", "否");
						}else{
							val.put("col_name", "是");
						}
					}
					//val.put("chakan","<a onclick='chakan_info(\""+Patient_st_map.get("pkid")+"\")'>查看</a>");
				}
				jsa.add(val);
			}
			
			JSONObject json = new JSONObject();
			json.put("page", page);
			json.put("total", total);
			json.put("rows", jsa); //这里的 rows 和total 的key 是固定的 
			response.getWriter().write(json.toString());
		}
	}
	/**
	 * 查找二维码方法
	 * @author 
	 * @date 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("tupian.do")
	public void tupian(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String pkid = request.getParameter("pkid");
		String sql ="select a.pic_path from da_pic a join da_household b on a.pic_pkid = b.pkid where a.pic_pkid="+pkid;
		List<Map> tp_list=this.getBySqlMapper.findRecords(sql);
		JSONArray jsonArray3 = new JSONArray();
		if(tp_list.size()>0){
			for(int i=0;i<tp_list.size();i++){
				JSONObject obj=new JSONObject();
				Map gztz_map=tp_list.get(i);
				for(Object key: gztz_map.keySet()){
					obj.put(key, gztz_map.get(key));
				}
				jsonArray3.add(obj);
			}
		}
		response.getWriter().write("{\"data1\":"+jsonArray3.toString()+"}");
	}

}
