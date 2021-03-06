package com.gistone.controller;

import java.io.IOException;
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

import com.gistone.MyBatis.config.GetBySqlMapper;

@RestController
@RequestMapping
public class WyApp_y4 {
	@Autowired
	private GetBySqlMapper getBySqlMapper;
	SW4_Controller sw4=new SW4_Controller();
	/**
	 * 按照村级显示贫困户信息
	 */
	@RequestMapping("getWyApp_y4_1.do")
	public void getWyApp_y4_1(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String code = request.getParameter("code");
		String level = request.getParameter("level");
		String bfrname = request.getParameter("bfrname").trim();
		String pkhname = request.getParameter("pkhname").trim();
		String pageSize = request.getParameter("pageSize");
		String pageNumber = request.getParameter("pageNumber");  
		//通过级别来截取有效行政区划的字段 并且用来查询此行政区划下的旗县或乡或村
		if(level.equals("1")){
			code = code.substring(0, 2);
		}else if(level.equals("2")){
			code = code.substring(0, 4);
		}else if(level.equals("3")){
			code = code.substring(0, 6);
		}else if(level.equals("4")){
			code = code.substring(0, 9);
		}
		String pre_sql = "SELECT * FROM (select A.*,ROWNUM RN from (select v21,v22,v23,v6,v9,pic_path,card,t4.AAC001";
		// and AAR010=0  sql   count_sql 帮扶责任人管理 原查询未脱贫 改为查询全部贫困户信息    2017-4-20 
		//t4.v6=DA_PIC_CODE.HOUSEHOLD_NAME and  去除按姓名比对查询二维码   t4.v6=DA_PIC_CODE.HOUSEHOLD_NAME and
		//统一按贫困户编号进行过滤二维码  t4.v8=DA_PIC_CODE.HOUSEHOLD_CARD  t4.v8=DA_PIC_CODE.HOUSEHOLD_CARD
		String sql = " from  (select NEIMENG0117_AC01.AAC001,NEIMENG0117_AC01.AAR008,AAR010 v21,AAC006 v22,AAC007 v23,AAB002 v6,AAB004 v8 from NEIMENG0117_AC01 join NEIMENG0117_AB01 on NEIMENG0117_AC01.AAC001=NEIMENG0117_AB01.AAC001 where  NEIMENG0117_AC01.AAR040='2016' and AAB006=01 AND AAR100 = '1' and  AAB015='1' and NEIMENG0117_AC01.AAR008 like'"+code+"%' group BY NEIMENG0117_AC01.AAR008,AAR010,AAC006,AAC007,AAB002,AAB004,NEIMENG0117_AC01.AAC001) t4"+
				" left join DA_PIC_CODE on t4.AAC001 = DA_PIC_CODE.AAC001 left join (SELECT AAC001, COUNT(*) v9 FROM NEIMENG0117_AB01 WHERE AAR040='2016' GROUP BY AAC001  ) t5 on T4.AAC001=t5.AAC001 "+
				"  LEFT JOIN (select AAC001,AAB004 card from NEIMENG0117_Ab01 where AAB006='01' and  AAB015='1')w1 ON t4.AAC001=w1.AAC001 ";
		String count_sql ="select v21,v22,v23,v6,v9,pic_path,card,t4.AAC001 from  (select NEIMENG0117_AC01.AAC001,NEIMENG0117_AC01.AAR008,AAR010 v21,AAC006 v22,AAC007 v23,AAB002 v6,AAB004 v8 from NEIMENG0117_AC01 join NEIMENG0117_AB01 on NEIMENG0117_AC01.AAC001=NEIMENG0117_AB01.AAC001 where  NEIMENG0117_AC01.AAR040='2016' and AAB006=01  "
				+ "AND AAR100 = '1' and  AAB015='1' and NEIMENG0117_AC01.AAR008 like'"+code+"%' group BY NEIMENG0117_AC01.AAR008,AAR010,AAC006,AAC007,AAB002,AAB004,NEIMENG0117_AC01.AAC001) t4"+
				" left join DA_PIC_CODE on t4.AAC001 = DA_PIC_CODE.AAC001  left join (SELECT AAC001, COUNT(*) v9 FROM NEIMENG0117_AB01 WHERE AAR040='2016' GROUP BY AAC001  ) t5 on T4.AAC001=t5.AAC001 "+
				"  LEFT JOIN (select AAC001,AAB004 card from NEIMENG0117_Ab01 where AAB006='01' and  AAB015='1')w1 ON t4.AAC001=w1.AAC001 ";
		String last_sql = " GROUP BY v21,v22,v23,v6,v9,pic_path,card,t4.AAC001 ORDER BY v6  ";
		if(pkhname.length()>0 &&bfrname.length()<1){
			last_sql= " GROUP BY v21,v22,v23,v6,v9,pic_path,card,t4.AAC001 HAVING   v6 like '"+pkhname+"%' ORDER BY v6  ";
			
		}
		
		if(bfrname.length()>0&&pkhname.length()<1){
			pre_sql =" SELECT * FROM (select A.*,ROWNUM RN from (select v21,v22,v23,v6,v9,pic_path,card,t4.AAC001,w2.PERSONAL_NAME ";
			last_sql= "left join DA_HELP_VISIT w2 on w2.HOUSEHOLD_CARD = w1.card GROUP BY v21,v22,v23,v6,v9,pic_path,card,t4.AAC001,w2.PERSONAL_NAME HAVING  w2.PERSONAL_NAME LIKE '"+bfrname+"%' ORDER BY v6  ";

		}
		if(bfrname.length()>0&&pkhname.length()>0){
			pre_sql ="SELECT * FROM (select A.*,ROWNUM RN from ( select v21,v22,v23,v6,v9,pic_path,card,t4.AAC001,w2.PERSONAL_NAME ";
			last_sql= "left join DA_HELP_VISIT w2 on w2.HOUSEHOLD_CARD = w1.card GROUP BY v21,v22,v23,v6,v9,pic_path,card,t4.AAC001,w2.PERSONAL_NAME HAVING  w2.PERSONAL_NAME LIKE '"+bfrname+"%' and v6 like '"+pkhname+"%' ORDER BY v6  ";
		}
		int size = Integer.parseInt(pageSize);
		int number = Integer.parseInt(pageNumber);
		int page = number == 0 ? 1 : (number/size)+1;
		sql = pre_sql+sql+last_sql+")A WHERE 1=1 and ROWNUM<="+size*page+")where rn>"+number+"  ";
		count_sql = count_sql+last_sql;
		JSONArray json = new JSONArray();
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		int total = this.getBySqlMapper.findRecords(count_sql).size();
		if (list.size() > 0) {
			for ( int i = 0 ; i < list.size() ; i ++){
				JSONObject obj = new JSONObject();
				obj.put("pkid", "".equals(list.get(i).get("AAC001")) || list.get(i).get("AAC001") == null ? "" : list.get(i).get("AAC001").toString());
				obj.put("v6", "".equals(list.get(i).get("V6")) || list.get(i).get("V6") == null ? "" : list.get(i).get("V6").toString());
				/*obj.put("v9", "".equals(list.get(i).get("V9")) || list.get(i).get("V9") == null ? "" : list.get(i).get("V9").toString());*/
				if(!"".equals(list.get(i).get("V9")) || list.get(i).get("V9") != null){
					obj.put("v9","<i class=\'fa fa-user\'></i> &nbsp;&nbsp;"+list.get(i).get("V9").toString()+"");
				}else{
					obj.put("v9","");
				}
				obj.put("v21", "".equals(list.get(i).get("V21")) || list.get(i).get("V21") == null ? "" :sw4.mianZhuan(list.get(i).get("V21").toString(), "21") );
/*				obj.put("v22", "".equals(list.get(i).get("V22")) || list.get(i).get("V22") == null ? "" :sw4.mianZhuan(list.get(i).get("V22").toString(), "22") );
*/				if(!"".equals(sw4.mianZhuan(list.get(i).get("V22").toString(), "22")) && sw4.mianZhuan(list.get(i).get("V22").toString(), "22")!=null){
					String v22 = sw4.mianZhuan(list.get(i).get("V22").toString(), "22");
					if(v22=="一般贫困户"){
						obj.put("v22", "<span class=\"badge badge-success\">"+v22+"</span>");	
	    			}else if(v22=="低保贫困户"){
	    				obj.put("v22", "<span class=\"badge badge-warning\">"+v22+"</span>");
	    			}else if(v22=="低收入农户"){
	    				obj.put("v22", "<span class=\"badge badge-primary\">"+v22+"</span>");
	    			}else if(v22=="低保户"){
	    				obj.put("v22", "<span class=\"badge badge-info\">"+v22+"</span>");			
	    			}else if(v22=="五保户"){
	    				obj.put("v22", "<span class=\"badge badge-info\">"+v22+"</span>");		
	    			}
				}else{
					obj.put("v22", "<span class=\"badge \">暂无</span>");
				}
				obj.put("v23", "".equals(list.get(i).get("V23")) || list.get(i).get("V23") == null ? "" :sw4.mianZhuan(list.get(i).get("V23").toString(), "23") );
				/*obj.put("card", "".equals(list.get(i).get("CARD")) || list.get(i).get("CARD") == null ? "" : list.get(i).get("CARD").toString());*/
				String cha_sql = "select * from da_help_visit where household_name ='"+list.get(i).get("V6")+"' AND  household_card='"+list.get(i).get("CARD")+"'";
				List cha_list = this.getBySqlMapper.findRecords(cha_sql);
				/*if(item.v22=="一般贫困户"){
    				v22 = "<span class=\"badge badge-success\">"+item.v22+"</span>";
    			}else if(item.v22=="低保贫困户"){
    				v22 = "<span class=\"badge badge-warning\">"+item.v22+"</span>";
    			}else if(item.v22=="低收入农户"){
    				v22 = "<span class=\"badge badge-primary\">"+item.v22+"</span>";
    			}else if(item.v22=="低保户"){
    				v22 = "<span class=\"badge badge-info\">"+item.v22+"</span>";
    			}else if(item.v22=="五保户"){
    				v22 = "<span class=\"badge badge-info\">"+item.v22+"</span>";
    			}else{
    				v22 = "<span class=\"badge\">暂无</span>";
    			}*/
				
				if( cha_list.size() > 0 ) {
					obj.put("riji_info","<a onclick=\"riji_info('"+list.get(i).get("V6").toString()+"','"+ list.get(i).get("CARD").toString()+"');\">点击查看</a>");
				}else {
					obj.put("riji_info","暂无");
				}
				
				//obj.put("riji_info", "".equals(list.get(i).get("COM")) || list.get(i).get("COM") == null ? "0" : list.get(i).get("COM").toString());
				/*obj.put("erweima", "".equals(list.get(i).get("PIC_PATH")) || list.get(i).get("PIC_PATH") == null ? "0" : list.get(i).get("PIC_PATH").toString());*/
				if(!"".equals(list.get(i).get("PIC_PATH")) && list.get(i).get("PIC_PATH") != null){
					obj.put("erweima","<div class=\"gallerys\" style=\"display: none;\"><img src=\""+list.get(i).get("PIC_PATH").toString()+"\" class=\"gallery-pic\"" +
    						" id=\"erwei_pic_"+list.get(i).get("AAC001")+"\" style=\"width:265px;\" onclick=\"$.openPhotoGallery(this)\" /></div>" +
    						"<a onclick=\"erweima_tupian("+list.get(i).get("AAC001")+");\">点击查看</a>");
				}else{
					obj.put("erweima","无");
				}
				json.add(obj);
			}
			JSONObject jso = new JSONObject();
			jso.put("page", page);
			jso.put("total", total);
			jso.put("rows", json); //这里的 rows 和total 的key 是固定的 
			response.getWriter().write(jso.toString());
		}else{
			response.getWriter().write("0");
		}
	}
	
	
	/**
	 * 显示单个贫困户的帮扶日记
	 */
	@RequestMapping("getWyApp_y4_2.do")
	public void getWyApp_y4_2(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		String household_name = request.getParameter("name");
		String household_card = request.getParameter("card");
		String sql = "select household_name,personal_name,v1,v3,random_number from da_help_visit where household_name ='"+household_name+"' AND  household_card='"+household_card+"' ORDER BY V1 DESC ";
//		String sql = "select household_name,personal_name,v1,v3,random_number from da_help_visit where household_name ='"+household_name+"' AND  household_card='"+household_card+"' and v3 is not null and v1 is not null ORDER BY V1 DESC ";
		String pic = "";
		
		JSONArray json = new JSONArray();
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		if (list.size() > 0) {
			for ( int i = 0 ; i < list.size() ; i ++){
				JSONObject obj = new JSONObject();
				obj.put("danwei", "".equals(list.get(i).get("HOUSEHOLD_NAME")) || list.get(i).get("HOUSEHOLD_NAME") == null ? "" : list.get(i).get("HOUSEHOLD_NAME").toString());
				obj.put("col_name", "".equals(list.get(i).get("PERSONAL_NAME")) || list.get(i).get("PERSONAL_NAME") == null ? "" : list.get(i).get("PERSONAL_NAME").toString());
				obj.put("v1", "".equals(list.get(i).get("V1")) || list.get(i).get("V1") == null ? "" : list.get(i).get("V1").toString());
				obj.put("v3", "".equals(list.get(i).get("V3")) || list.get(i).get("V3") == null ? "" : list.get(i).get("V3").toString());
				
				pic = "select pic_path,pkid from da_pic_visit where random_number='"+list.get(i).get("RANDOM_NUMBER")+"'";
				List<Map> pic_list = this.getBySqlMapper.findRecords(pic);
				JSONArray nodes = new JSONArray();
				if(pic_list.size()>0){
					for ( int j = 0 ; j < pic_list.size() ; j ++){
						JSONObject pic_obj = new JSONObject();
						pic_obj.put("path", "".equals(pic_list.get(j).get("PIC_PATH")) || pic_list.get(j).get("PIC_PATH") == null ? "" : pic_list.get(j).get("PIC_PATH").toString());
						pic_obj.put("pkid", "".equals(pic_list.get(j).get("PKID")) || pic_list.get(j).get("PKID") == null ? "" : pic_list.get(j).get("PKID").toString());
						nodes.add(pic_obj);
					}
				}
				obj.put("nodes", nodes);
				json.add(obj);
			}
			response.getWriter().write(json.toString());
		}
	}
	/**
	 * 根据行政区划显示帮扶人和贫困户
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getWyApp_y1_y.do")
	public void getWyApp_y1_y (HttpServletRequest request,HttpServletResponse response) throws IOException {
		String role = request.getParameter("role_id");
		String name = request.getParameter("name");//3
		String code = request.getParameter("code");
		int v1 = Integer.parseInt(role)+1;
		int v3 = Integer.parseInt(role)*2;
		int v2 = v3 -1;
		int v4 = Integer.parseInt(role)*2-2;
		int v5 = Integer.parseInt(role)*2;
		String sql ="select num ,xzqh,bfr from  ";
			sql+= " ( SELECT NUM,AAR00"+v1+" xz,xzqh FROM (  select  COUNT(*) NUM,AAR00"+v1+" from NEIMENG0117_AC01  where AAR100= '1'  and AAR040='2016' and AAR00"+role+"='"+code+"' GROUP BY AAR00"+v1+"  )AA left join ( ";
			sql += "  select v"+v2+" xzqh,v"+v3+" from SYS_COM GROUP BY v"+v2+",v"+v3+" )bb ON AA.AAR00"+v1+"=bb.v"+v3+"  where xzqh is not null)w0 LEFT JOIN (";
			sql +=" select count(AAC001) bfr ,AAR00"+v1+" from (select a.AAC001,AAR00"+v1+" from (select AAC001,AAR00"+v1+" from NEIMENG0117_AC01 where AAR100= '1' and AAR040='2016' ";
			sql +=") a left join (select * from NEIMENG0117_AC08)  b on a.AAC001=b.AAC001 where SUBSTR(b.AAR020, 0, 4) <='2016' AND SUBSTR(b.AAR021, 0, 4) >='2016' ";
			sql+=" )t1 group BY AAR00"+v1+" )w1 on w0.xz=w1.AAR00"+v1+"";
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		JSONArray json = new JSONArray();
		if ( list.size() > 0 ) {
			for ( int i = 0 ; i < list.size() ; i++ ) {
				JSONObject obj = new JSONObject ();
				obj.put("xzqh", "".equals(list.get(i).get("XZQH")) || list.get(i).get("XZQH") == null ? "" : list.get(i).get("XZQH").toString());
				obj.put("num", "".equals(list.get(i).get("NUM")) || list.get(i).get("NUM") == null ? "0" : list.get(i).get("NUM").toString());
				obj.put("bfr", "".equals(list.get(i).get("BFR")) || list.get(i).get("BFR") == null ? "0" : list.get(i).get("BFR").toString());
				json.add(obj);
			}
			
		}
		String l_sql = "select sum(fc2) num from PKC_2_1_1 where v10 in (select v"+v5+" from SYS_COM where v"+v4+"='"+code+"' ) ";
		List<Map> l_list = this.getBySqlMapper.findRecords(l_sql);
		JSONArray json1 = new JSONArray();
		JSONObject obj1 = new JSONObject () ;
		if ( l_list.size() > 0 && l_list.get(0)!=null) {
			obj1.put("liu", "".equals(l_list.get(0).get("NUM")) || l_list.get(0).get("NUM") == null ? "0" : l_list.get(0).get("NUM").toString());
			json1.add(obj1);
		}else{
			obj1.put("liu", "0");
			json1.add(obj1);
		}
		response.getWriter().write("{\"data1\":"+json.toString()+",\"data2\":"+json1.toString()+"}");
	}
	/**
	 * 各省市区划帮扶单位、驻村工作对、驻村干部
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("getWyApp_y1_bf.do")
	public void getWyApp_y1_bf (HttpServletRequest request,HttpServletResponse response ) throws IOException {
		String code = request.getParameter("code");
		String role = request.getParameter("role");//2省 3市 4 县 5乡 6村
		//帮扶单位数
		String dw_sql = "select count(*) num  from (select AP110 from  (select AAR008 from NEIMENG0117_AC01  where AAR100= '1' and AAR040='2016' and AAR00"+role+" ='"+code+"' GROUP BY AAR008  ) a "+
						" LEFT JOIN ( select AAD001,AAP110 from AD07 ) b on a.AAR008=b.AAD001  left join ( "+
						" select AAP110 Ap110,AAP001 from AP11)c ON b.AAP110=c.AP110 where AAD001 is not null and AP110 is not null  GROUP BY AP110)"; 
		List<Map> dw_list = this.getBySqlMapper.findRecords(dw_sql);
		//驻村对
		String zcd_sql = "SELECT count(*) num from (select * from ( "+
						" select AP110,AAP001 from  (select AAR008 from NEIMENG0117_AC01  where AAR100= '1' and AAR040='2016'and AAR00"+role+" ='"+code+"' GROUP BY AAR008   ) a "+
						" LEFT JOIN ( select AAD001,AAP110 from AD07 ) b on a.AAR008=b.AAD001  left join ( "+
						" select AAP110 Ap110,AAP001 from AP11)c ON b.AAP110=c.AP110 where AAD001 is not null and AP110 is not null  GROUP BY AP110,AAP001"+
						") aa LEFT JOIN (select AAP001 p001 from NEIMENG0117_ap01 ) bb on aa.AAP001=bb.p001 where p001 is not null)";
		List<Map> zcd_list = this.getBySqlMapper.findRecords(zcd_sql);
		//驻村干部
		String zcgb_sql = "select count(*) num from (select AAB002,AAK030,p011 from (select * from ("+
							"select AP110,AAP001 from  (select AAR008 from NEIMENG0117_AC01  where AAR100= '1' and AAR040='2016' and AAR00"+role+" ='"+code+"' GROUP BY AAR008   ) a "+
							" LEFT JOIN ( select AAD001,AAP110 from AD07 ) b on a.AAR008=b.AAD001  left join ("+
							" select AAP110 Ap110,AAP001 from AP11)c ON b.AAP110=c.AP110 where AAD001 is not null and AP110 is not null  GROUP BY AP110,AAP001"+
							") aa LEFT JOIN (select AAP001 p001,AAP011 from NEIMENG0117_ap01 ) bb on aa.AAP001=bb.p001 where p001 is not null) w0"+
							" left join (select  AAK030,AAB002,AAP011 p011 from NEIMENG0117_AK03)w1 on w0.AAP011=W1.p011 where p011 is not null)";
		List<Map> zcgb_list = this.getBySqlMapper.findRecords(zcgb_sql);
		JSONArray json = new JSONArray ();
		JSONObject obj = new JSONObject ();
		if( dw_list.size() > 0 ) {
			obj.put("dw", "".equals(dw_list.get(0).get("NUM")) || dw_list.get(0).get("NUM") == null ? "0" : dw_list.get(0).get("NUM").toString());
		} else {
			obj.put("dw", "0");
		}
		if( zcd_list.size() > 0 ) {
			obj.put("zcd", "".equals(zcd_list.get(0).get("NUM")) || zcd_list.get(0).get("NUM") == null ? "0" : zcd_list.get(0).get("NUM").toString());
		} else {
			obj.put("zcd", "0");
		}
		if( zcgb_list.size() > 0 ) {
			obj.put("zcgb", "".equals(zcgb_list.get(0).get("NUM")) || zcgb_list.get(0).get("NUM") == null ? "0" : zcgb_list.get(0).get("NUM").toString());
		} else {
			obj.put("zcgb", "0");
		}
		json.add(obj);
		response.getWriter().write(json.toString());
	}
}
