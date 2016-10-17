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
//		String sql="select * from da_household a left join da_household_basic b on a.pkid=b.da_household_id "+
//				"LEFT JOIN (SELECT pic_path,pic_pkid from da_pic WHERE pic_type=4 ) c ON a.pkid=c.pic_pkid  where a.pkid="+pkid;
		
		String sql = "select a.PKID,V2,V3,V4,V5,V6,V7,V9,V11,V12,V13,V14,V15,V16,V17,V18,V19,V22,V23,V28,V29,V30,V31,V32,V33,c.PIC_PATH from "
				+ "da_household a LEFT JOIN (SELECT pic_path,pic_pkid from da_pic WHERE pic_type=4 ) c ON a.pkid=c.pic_pkid  where a.pkid="+pkid;
//		System.out.println(sql);
		List<Map> list=getBySqlMapper.findRecords(sql);
		//户主信息
		JSONArray jsonArray1 =new JSONArray();
		
		for(Map val:list){
			JSONObject obj=new JSONObject ();
			obj.put("pkid",val.get("PKID")==null?"":val.get("PKID"));
			obj.put("v2", val.get("V2")==null?"":val.get("V2"));
			obj.put("v3", val.get("V3")==null?"":val.get("V3"));
			obj.put("v4", val.get("V4")==null?"":val.get("V4"));
			obj.put("v5", val.get("V5")==null?"":val.get("V5"));
			
			obj.put("v6", val.get("V6")==null?"":val.get("V6"));//姓名
			obj.put("v7",val.get("V7")==null?"":val.get("V7"));//性别
//			obj.put("v8",val.get("V8")==null?"":val.get("V8"));//证件号码
			obj.put("v9",val.get("V9")==null?"":val.get("V9"));//人数
			obj.put("v11",val.get("V11")==null?"":val.get("V11"));//民族
			obj.put("v12",val.get("V12")==null?"":val.get("V12"));//文化程度
			obj.put("v13",val.get("V13")==null?"":val.get("V13"));//在校生状况
			obj.put("v14",val.get("V14")==null?"":val.get("V14"));//健康状况
			obj.put("v15",val.get("V15")==null?"":val.get("V15"));//劳动能力
			obj.put("v16",val.get("V16")==null?"":val.get("V16"));//务工情况
			obj.put("v17",val.get("V17")==null?"":val.get("V17"));//务工时间
			obj.put("v18",val.get("V18")==null?"":val.get("V18"));//是否参加新农合
			obj.put("v19",val.get("V19")==null?"":val.get("V19"));//是否参加新型养老保险
//			obj.put("v21",val.get("V21")==null?"":val.get("V21"));//脱贫属性
//			obj.put("v25",val.get("V25")==null?"":val.get("V25"));//联系电话
//			obj.put("v26",val.get("V26")==null?"":val.get("V26"));//开户银行名称
//			obj.put("v27",val.get("V27")==null?"":val.get("V27"));//银行卡号
			obj.put("sys_standard",val.get("SYS_STANDARD")==null?"":val.get("SYS_STANDARD"));//识别标准
			obj.put("v22",val.get("V22")==null?"":val.get("V22"));//贫苦户属性
			obj.put("v23",val.get("V23")==null?"":val.get("V23"));//主要致贫原因
			obj.put("v29",val.get("V29")==null?"":val.get("V29"));//是否军烈属
			obj.put("v30",val.get("V30")==null?"":val.get("V30"));//是否独生子女
			obj.put("v31",val.get("V31")==null?"":val.get("V31"));//是否双女户
			obj.put("v32",val.get("V32")==null?"":val.get("V32"));//是否现役军人
			obj.put("v33",val.get("V33")==null?"":val.get("V33"));//其他致贫原因
			obj.put("v28",val.get("V28")==null?"":val.get("V28"));//政治面貌
//			obj.put("basic_address",val.get("basic_address")==null?"":val.get("basic_address"));//家庭住址
//			obj.put("basic_explain",val.get("BASIC_EXPLAIN")==null?"":val.get("BASIC_EXPLAIN"));//致贫原因说明
			obj.put("pic_path",val.get("PIC_PATH")==null?"":val.get("PIC_PATH"));//致贫原因说明
			jsonArray1.add(obj);
		}
		//家庭成员
		JSONArray jsonArray2 =new JSONArray();
//		String xian_sql="select * from da_member a LEFT JOIN (SELECT pic_path,pic_pkid from da_pic WHERE pic_type=5 ) b ON a.pkid=b.pic_pkid  where a.da_household_id="+pkid;

		String xian_sql="select a.*,b.pic_path from (select PKID,V6,V7,V10,V11,V12,V13,V14,V15,V16,V17,V18,V19,V28,V32 from da_member where da_household_id="+pkid+") a "
				+ "LEFT JOIN (SELECT pic_path,pic_pkid from da_pic WHERE pic_type=5 ) b ON a.pkid=b.pic_pkid";
		List<Map> xian_list=this.getBySqlMapper.findRecords(xian_sql);
		for(Map val:xian_list){
			JSONObject obj=new JSONObject ();
			obj.put("cy_pkid",val.get("PKID")==null?"":val.get("PKID"));
			obj.put("cy_v6",val.get("V6")==null?"":val.get("V6"));//姓名
			obj.put("cy_v7",val.get("V7")==null?"":val.get("V7"));//性别
//			obj.put("cy_v8",val.get("V8")==null?"":val.get("V8"));//证件号码
//			obj.put("cy_v9",val.get("V9")==null?"":val.get("V9"));//人数
			obj.put("cy_v10",val.get("V10")==null?"":val.get("V10"));//与户主关系
			obj.put("cy_v11",val.get("V11")==null?"":val.get("V11"));//民族
			obj.put("cy_v12",val.get("V12")==null?"":val.get("V12"));//文化程度
			obj.put("cy_v13",val.get("V13")==null?"":val.get("V13"));//在校状况
			obj.put("cy_v14",val.get("V14")==null?"":val.get("V14"));//健康状况
			obj.put("cy_v15",val.get("V15")==null?"":val.get("V15"));//劳动力
			obj.put("cy_v16",val.get("V16")==null?"":val.get("V16"));//务工状态
			obj.put("cy_v17",val.get("V17")==null?"":val.get("V17"));//务工时间
			obj.put("cy_v18",val.get("V18")==null?"":val.get("V18"));//是否参加新农合
			obj.put("cy_v19",val.get("V19")==null?"":val.get("V19"));//是否参加新型养老保险
//			obj.put("cy_v20",val.get("V20")==null?"":val.get("V20"));//是否参加城镇职工基本养老保险	
//			obj.put("cy_v21",val.get("V21")==null?"":val.get("V21"));//脱贫属性
			
			obj.put("cy_v32",val.get("V32")==null?"":val.get("V32"));//是否现役军人
			obj.put("cy_v28",val.get("V28")==null?"":val.get("V28"));//政治面貌
			obj.put("cy_pic_path",val.get("PIC_PATH")==null?"":val.get("PIC_PATH"));//头像
			jsonArray2.add(obj);
		}
		//走访情况
		JSONArray jsonArray3 =new JSONArray();
//		String zoufang_sql = "select pkid,v1,v2,v3,'da_help_visit' as da_type from da_help_visit where da_household_id="+pkid+" order by v1 desc";
		
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
		}
		response.getWriter().write("{\"data1\":"+jsonArray1.toString() +
				",\"data2\":"+jsonArray2.toString()+",\"data3\":"+jsonArray3.toString()+"}");
		response.getWriter().close();
		return null;
	}
}
