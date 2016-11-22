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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.gistone.MyBatis.config.GetBySqlMapper;

@RestController
@RequestMapping
public class AnUserController  extends MultiActionController{
	@Autowired
	private GetBySqlMapper getBySqlMapper;
	
	@RequestMapping("anGetPk.do")
	public ModelAndView anGetPk(HttpServletRequest request,HttpServletResponse response) throws IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String pkid=request.getParameter("pkid");
		String acid="";//aac001
		String code="";//地区编码
//		String sql="select * from da_household a left join da_household_basic b on a.pkid=b.da_household_id "+
//				"LEFT JOIN (SELECT pic_path,pic_pkid from da_pic WHERE pic_type=4 ) c ON a.pkid=c.pic_pkid  where a.pkid="+pkid;
		SW4_Controller sw4 =new SW4_Controller();
		String sql = "select PKID,ACID,V6,V7,V8,V9,V11,V12,V13,V14,V15,V16,V17,V32,V18,V19,SYS_STANDARD,V22,V23,V29,V33,PIC_PATH,CODE from "+
				" (select AAB001 pkid,AAC001 acid,AAB002 v6,AAB003 v7,AAB004 v8,aab007 v11,AAB008 v12,AAB009 v13,AAB017 v14,AAB010 v15,AAB011 v16,AAB012 v17,AAB019 V32 from NM09_AB01 where AAB001='"+pkid+"' AND AAR040='2015') a1"+
				" LEFT JOIN ( SELECT AAB001,AAB013 v18,AAB014 v19 from NM09_AB02 ) a2 on A1.pkid=A2.AAB001 "+
				" LEFT JOIN  ( SELECT AAC001,AAC005 sys_standard,AAC006 v22,AAC007 v23,AAC012 v29,AAC008 v33,AAR008 code from NM09_AC01 where AAR040='2015' ) a3 ON a1.acid=a3.AAC001"+
				" LEFT JOIN  (select COUNT(*) v9,AAC001 from (SELECT aac001,aab002 FROM NM09_AB01  GROUP BY aab002,AAC001 ) GROUP BY AAC001) a5 on a1.acid=a5.AAC001"+
				" LEFT JOIN ( select aab001,PIC_PATH from DA_PIC_CODE ) a4 ON a4.pkid=a1.AAB001 "+
				" GROUP BY PKID,ACID,V6,V7,V8,V11,V12,V13,V14,V15,V16,V17,V32,V18,V19,SYS_STANDARD,V22,V23,V29,V33,v9,PIC_PATH,CODE";
				
				/*"select a.PKID,V2,V3,V4,V5,V6,V7,V9,V11,V12,V13,V14,V15,V16,V17,V18,V19,V22,V23,V28,V29,V30,V31,V 32,V33,c.PIC_PATH from "
						+ "da_household a LEFT JOIN (SELECT pic_path,pic_pkid from da_pic WHERE pic_type=4 ) c ON a.pkid=c.pic_pkid  where a.pkid="+pkid;*/
//		System.out.println(sql);
		List<Map> list=getBySqlMapper.findRecords(sql);
		//户主信息
		JSONArray jsonArray1 =new JSONArray();
		
		for(Map val:list){
			JSONObject obj=new JSONObject ();
			acid=val.get("ACID").toString();
			code=val.get("CODE").toString();
			obj.put("pkid",val.get("PKID")==null?"":val.get("PKID"));
			/*obj.put("v2", val.get("V2")==null?"":val.get("V2"));//地区
			obj.put("v3", val.get("V3")==null?"":val.get("V3"));
			obj.put("v4", val.get("V4")==null?"":val.get("V4"));
			obj.put("v5", val.get("V5")==null?"":val.get("V5"));//地区
*/			
			obj.put("v6", val.get("V6")==null?"":val.get("V6"));//姓名
			obj.put("v7",val.get("V7")==null?"":sw4.mianZhuan(val.get("V7").toString(), "7"));//性别
//			obj.put("v8",val.get("V8")==null?"":val.get("V8"));//证件号码
			obj.put("v9",val.get("V9")==null?"":val.get("V9"));//人数
			obj.put("v11",val.get("V11")==null?"":sw4.mianZhuan(val.get("V11").toString(), "11"));//民族
			obj.put("v12",val.get("V12")==null?"":sw4.mianZhuan(val.get("V12").toString(), "12"));//文化程度
			obj.put("v13",val.get("V13")==null?"":sw4.mianZhuan(val.get("V13").toString(), "13"));//在校生状况
			obj.put("v14",val.get("V14")==null?"":sw4.mianZhuan(val.get("V14").toString(), "14"));//健康状况
			obj.put("v15",val.get("V15")==null?"":sw4.mianZhuan(val.get("V15").toString(), "15"));//劳动能力
			obj.put("v16",val.get("V16")==null?"":sw4.mianZhuan(val.get("V16").toString(), "16"));//务工情况
			obj.put("v17",val.get("V17")==null?"":val.get("V17"));//务工时间
			obj.put("v18",val.get("V18")==null?"":sw4.mianZhuan(val.get("V18").toString(), "32"));//是否参加新农合
			obj.put("v19",val.get("V19")==null?"":sw4.mianZhuan(val.get("V19").toString(), "32"));//是否参加新型养老保险
//			obj.put("v21",val.get("V21")==null?"":val.get("V21"));//脱贫属性
//			obj.put("v25",val.get("V25")==null?"":val.get("V25"));//联系电话
//			obj.put("v26",val.get("V26")==null?"":val.get("V26"));//开户银行名称
//			obj.put("v27",val.get("V27")==null?"":val.get("V27"));//银行卡号
			obj.put("sys_standard",val.get("SYS_STANDARD")==null?"":sw4.mianZhuan(val.get("SYS_STANDARD").toString(), "sys_standard"));//识别标准
			obj.put("v22",val.get("V22")==null?"":sw4.mianZhuan(val.get("V22").toString(), "22"));//贫苦户属性
			obj.put("v23",val.get("V23")==null?"":sw4.mianZhuan(val.get("V23").toString(), "23"));//主要致贫原因
			obj.put("v29",val.get("V29")==null?"":sw4.mianZhuan(val.get("V29").toString(), "32"));//是否军烈属
//			obj.put("v30",val.get("V30")==null?"":val.get("V30"));//是否独生子女 新表没字段
//			obj.put("v31",val.get("V31")==null?"":val.get("V31"));//是否双女户
			obj.put("v32",val.get("V32")==null?"":sw4.mianZhuan(val.get("V32").toString(), "32"));//是否现役军人
			obj.put("v33",val.get("V33")==null?"":sw4.mianZhuan(val.get("V33").toString(), "33"));//其他致贫原因
//			obj.put("v28",val.get("V28")==null?"":val.get("V28"));//政治面貌 新表没有
//			obj.put("basic_address",val.get("basic_address")==null?"":val.get("basic_address"));//家庭住址
//			obj.put("basic_explain",val.get("BASIC_EXPLAIN")==null?"":val.get("BASIC_EXPLAIN"));//致贫原因说明
			obj.put("pic_path",val.get("PIC_PATH")==null?"":val.get("PIC_PATH"));//致贫原因说明
			jsonArray1.add(obj);
			
		}
		//家庭成员
		JSONArray jsonArray2 =new JSONArray();
//		String xian_sql="select * from da_member a LEFT JOIN (SELECT pic_path,pic_pkid from da_pic WHERE pic_type=5 ) b ON a.pkid=b.pic_pkid  where a.da_household_id="+pkid;

		String xian_sql="select  v6,v7,v8,v10,v11,v12,v13,v14,v15,v16,v17,v32,v18,v19 from "+  
				" (SELECT AAC001 FROM NM09_AC01 WHERE AAC001='"+acid+"' AND AAR040='2015' ) a2 "+
				" left join  ( SELECT NM09_AB01.AAB001 pkid,AAC001,NM09_AB01.AAR040,AAB002 v6,AAB003 v7,AAB004 v8,AAB006 v10,AAB007 v11,AAB008 v12,AAB009 v13,AAB010 v15,AAB011 v16,AAB012 v17,AAB017 v14,AAB013 v18,AAB019 v32,AAB014 v19 from  NM09_AB01 join NM09_AB02 on NM09_AB02.AAB001=NM09_AB01.AAB001 where AAB006!='01' ) a1 on a1.AAC001=a2.AAC001 "+ 
				" GROUP BY v6,v7,v8,v10,v11,v12,v13,v14,v15,v16,v17,v32,v18,v19";
				/*"select a.*,b.pic_path from (select PKID,V6,V7,V10,V11,V12,V13,V14,V15,V16,V17,V18,V19,V28,V32 from da_member where da_household_id="+pkid+") a "
						+ "LEFT JOIN (SELECT pic_path,pic_pkid from da_pic WHERE pic_type=5 ) b ON a.pkid=b.pic_pkid";*/
		List<Map> xian_list=this.getBySqlMapper.findRecords(xian_sql);
		for(Map val:xian_list){
			JSONObject obj=new JSONObject ();
			obj.put("cy_pkid",val.get("PKID")==null?"":val.get("PKID"));
			obj.put("cy_v6",val.get("V6")==null?"":val.get("V6"));//姓名
			obj.put("cy_v7",val.get("V7")==null?"":sw4.mianZhuan(val.get("V7").toString(), "7"));//性别
//			obj.put("cy_v8",val.get("V8")==null?"":val.get("V8"));//证件号码
//			obj.put("cy_v9",val.get("V9")==null?"":val.get("V9"));//人数
			obj.put("cy_v10",val.get("V10")==null?"":sw4.mianZhuan(val.get("V10").toString(), "10"));//民族
			obj.put("cy_v11",val.get("V11")==null?"":sw4.mianZhuan(val.get("V11").toString(), "11"));//民族
			obj.put("cy_v12",val.get("V12")==null?"":sw4.mianZhuan(val.get("V12").toString(), "12"));//文化程度
			obj.put("cy_v13",val.get("V13")==null?"":sw4.mianZhuan(val.get("V13").toString(), "13"));//在校生状况
			obj.put("cy_v14",val.get("V14")==null?"":sw4.mianZhuan(val.get("V14").toString(), "14"));//健康状况
			obj.put("cy_v15",val.get("V15")==null?"":sw4.mianZhuan(val.get("V15").toString(), "15"));//劳动能力
			obj.put("cy_v16",val.get("V16")==null?"":sw4.mianZhuan(val.get("V16").toString(), "16"));//务工情况
			obj.put("cy_v17",val.get("V17")==null?"":val.get("V17"));//务工时间
			obj.put("cy_v18",val.get("V18")==null?"":sw4.mianZhuan(val.get("V18").toString(), "32"));//是否参加新农合
			obj.put("cy_v19",val.get("V19")==null?"":sw4.mianZhuan(val.get("V19").toString(), "32"));//是否参加新型养老保险
//			obj.put("cy_v20",val.get("V20")==null?"":val.get("V20"));//是否参加城镇职工基本养老保险	
//			obj.put("cy_v21",val.get("V21")==null?"":val.get("V21"));//脱贫属性
			
			obj.put("cy_v32",val.get("V32")==null?"":sw4.mianZhuan(val.get("V32").toString(), "32"));//是否现役军人
//			obj.put("cy_v28",val.get("V28")==null?"":val.get("V28"));//政治面貌
//			obj.put("cy_pic_path",val.get("PIC_PATH")==null?"":val.get("PIC_PATH"));//头像 // 家庭成员头像暂时没有
			jsonArray2.add(obj);
		}
		
		//所在地区
		JSONArray jsonArray4 =new JSONArray();
		String dq_sql="select sheng,shi,xian,xiang,cun from (select com_name cun,com_f_pkid from SYS_COMPANY where com_code='"+code+"')a left join"+ 
				"(select pkid,com_f_pkid,com_name xiang from SYS_COMPANY ) b ON a.com_f_pkid=b.pkid left join "+
					"(select pkid,com_f_pkid,com_name xian from SYS_COMPANY )c ON b.com_f_pkid= c.pkid left join "+
						 "(select pkid,com_f_pkid,com_name shi from SYS_COMPANY )d ON c.com_f_pkid = d.pkid left join "+
						 "(select pkid,com_name sheng from SYS_COMPANY )e ON d.com_f_pkid=e.pkid";
				
		//"SELECT v5,v7,v6,v1,v8,v9,v10,v11,v12 FROM da_life where da_household_id="+pkid;
		List<Map> dq_list=getBySqlMapper.findRecords(dq_sql);
		if(dq_list.size()>0){
			for(int i=0;i<dq_list.size();i++){
				
				JSONObject obj=new JSONObject();
				obj.put("SHENG", "".equals(dq_list.get(i).get("SHENG")) || dq_list.get(i).get("SHENG") == null ? "" : dq_list.get(i).get("SHENG").toString());
				obj.put("SHI", "".equals(dq_list.get(i).get("SHI")) || dq_list.get(i).get("SHI") == null ? "" : dq_list.get(i).get("SHI").toString());
				obj.put("XIAN", "".equals(dq_list.get(i).get("XIAN")) || dq_list.get(i).get("XIAN") == null ? "" : dq_list.get(i).get("XIAN").toString());
				obj.put("XIANG", "".equals(dq_list.get(i).get("XIANG")) || dq_list.get(i).get("XIANG") == null ? "" : dq_list.get(i).get("XIANG").toString());
				obj.put("CUN", "".equals(dq_list.get(i).get("CUN")) || dq_list.get(i).get("CUN") == null ? "" : dq_list.get(i).get("CUN").toString());
				
				jsonArray4.add(obj);
				
			}
				
		}
		
		//走访情况
		JSONArray jsonArray3 =new JSONArray();
/*//	String zoufang_sql = "select pkid,v1,v2,v3,'da_help_visit' as da_type from da_help_visit where da_household_id="+pkid+" order by v1 desc";
		
		String zoufang_sql = "select t1.pkid,t1.v1,t2.col_name as v2,t1.v3,'da_help_visit' as da_type from da_help_visit t1 join SYS_PERSONAL t2 on "
				+ " t1.SYS_PERSONAL_ID=t2.pkid where da_household_id="+pkid+" order by t1.v1 desc";
		List<Map> sjz_list=getBySqlMapper.findRecords(zoufang_sql);
		for(Map val:sjz_list){
			JSONObject sjz_obj=new JSONObject ();
			sjz_obj.put("pkid",val.get("PKID")==null?"":val.get("PKID"));
			sjz_obj.put("v1",val.get("V1")==null?"":val.get("V1"));
			sjz_obj.put("v2",val.get("V2")==null?"":val.get("V2"));
			sjz_obj.put("v3",val.get("V3")==null?"":val.get("V3"));
			sjz_obj.put("da_type",val.get("DA_TYPE")==null?"":val.get("DA_TYPE"));
			String results=val.get("DA_TYPE").toString();
			
			if(results.equals("da_help_visit")){
//				String hql="SELECT group_concat(pic_path order by pic_path separator ',') pie FROM da_pic WHERE pic_pkid="+val.get("pkid")+" AND pic_type=2";
//				String hql = "select t1.pic_pkid,listagg(to_char(t1.pic_path),',') within group(ORDER BY pic_pkid) as pie "
//						+ "FROM (select pic_pkid,pic_path from da_pic WHERE pic_pkid="+val.get("pkid")+" AND pic_type=2) t1 group by t1.pic_pkid";
				
				String hql = "select pic_pkid,pic_path FROM da_pic WHERE pic_pkid="+val.get("PKID")+" AND pic_type=2";
				List<Map> sjzf_list=getBySqlMapper.findRecords(hql);
				if(sjzf_list.size()>0){
					JSONArray jsonArray4 =new JSONArray();
					
					for(Map val1:sjzf_list){
						JSONObject pic=new JSONObject ();
						pic.put("pie_path",val1.get("PIC_PATH")==null?"":val1.get("PIC_PATH"));
						jsonArray4.add(pic);
					}
					sjz_obj.put("pie",jsonArray4);
					
//					if(null==sjzf_list.get(0)){
//						sjz_obj.put("pie","");
//					}else{
//						for(Map val1:sjzf_list){
//							sjz_obj.put("pie",val1.get("PIE")==null?"":val1.get("PIE"));
//						}
//					}
				}else{
					sjz_obj.put("pie","");
				}
				
			}
//			System.out.println(sjz_obj.toString());
			jsonArray3.add(sjz_obj);
		}*/
		response.getWriter().write("{\"data1\":"+jsonArray1.toString() +
				",\"data2\":"+jsonArray2.toString()+",\"data3\":"+jsonArray3.toString()+",\"data4\":"+jsonArray4.toString()+"}");
		response.getWriter().close();
		return null;
	}
}
