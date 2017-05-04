package com.gistone.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Border;
import jxl.write.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.Label;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gistone.MyBatis.config.GetBySqlMapper;
import com.gistone.util.QRCodeUtil;
import com.gistone.util.Tool;

@RestController
@RequestMapping
public class WtApp_t1_Controller {
	@Autowired
	private GetBySqlMapper getBySqlMapper;
	/**
	 * 
	 * @description 项目数量统计
	 * @method  getItemCount_WtApp_t1_1_1
	 * @param　request
	 * @author luoshuai 
	 * @date 2017年3月21日 上午11:34:19
	 *
	 */
	@RequestMapping("getItemCount_WtApp_t1_1_1.do")
	public void getItemCount_WtApp_t1_1_1(HttpServletRequest request,HttpServletResponse response) throws IOException{
	 try {
			
		HttpSession session=request.getSession();
		Map Login_map=(Map) session.getAttribute("Login_map");
		String com_code="";//行政区划
		int com_level=0;//登陆用户的行政区划级别
		String com_code2="";
		String com_code3="";
		if(!"".equals(Login_map.get("COM_CODE"))&&Login_map.get("COM_CODE")!=null&&!"xi".equals(Login_map.get("COM_CODE"))){
			com_code=(String) Login_map.get("COM_CODE");
			com_code3=(String) Login_map.get("COM_CODE");
		}else{
			com_code="150000000000";
			com_code3="xi";
		}
		if(!"".equals(Login_map.get("COM_LEVEL"))&&Login_map.get("COM_LEVEL")!=null){
			com_level=Integer.parseInt( Login_map.get("COM_LEVEL").toString());
		}else{
			com_level=1;
		}
		if(com_level==1){
			com_code2=com_code.substring(0,3);
		}else if(com_level==2){
			com_code2=com_code.substring(0,4);
		}else if(com_level==3){
			com_code2=com_code.substring(0,6);
		}else if(com_level==4){
			com_code2=com_code.substring(0,9);
		}else if(com_level==5){
			com_code2=com_code;
		}
		String sql = "select c.ACR005 item_type,count(*) count_num from NEIMENG0117_CR01 c LEFT JOIN SYS_COMPANY a2 on c.AAR008=A2.COM_CODE where (c.AAR100=1 or c.AAR100 is null) and  A2.COM_CODE like '%"+com_code2+"%' and A2.COM_LEVEL!='"+com_level+"' GROUP BY c.ACR005 ORDER BY ITEM_TYPE ASC";
		//String sql = "select a.ACR005 item_type,count(*) count_num from NEIMENG0117_CR01 a RIGHT JOIN  (SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=(SELECT PKID FROM SYS_COMPANY WHERE COM_CODE='"+com_code3+"')) a2 on a.aar008=A2.COM_CODE GROUP BY a2.com_name";
		List<Map> itemList = this.getBySqlMapper.findRecords(sql);
		JSONArray json = new JSONArray();//项目数量统计
		JSONArray json2 = new JSONArray();//资金分布情况
		JSONArray json3 = new JSONArray();//落实资金
		JSONArray json4 = new JSONArray();//落实项目
		JSONArray json5 = new JSONArray();//项目收益情况
		JSONArray json6 = new JSONArray();//资金分类
		if(itemList.size() > 0){
			for(int i=0;i<itemList.size();i++){
				JSONObject object = new JSONObject();
				if(itemList.get(i)!=null){
				object.put("item_type","".equals(itemList.get(i).get("ITEM_TYPE"))||itemList.get(i).get("ITEM_TYPE")==null?"":getItemTypeName(itemList.get(i).get("ITEM_TYPE").toString()));
				object.put("count_num","".equals(itemList.get(i).get("COUNT_NUM"))||itemList.get(i).get("COUNT_NUM")==null?"":itemList.get(i).get("COUNT_NUM"));
				json.add(object);
				}
			}
		}
		String sql2 = "SELECT a2.com_name,SUM (ABR021) AS totalAmount FROM NEIMENG0117_BR05 a RIGHT JOIN  (SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=(SELECT PKID FROM SYS_COMPANY WHERE COM_CODE='"+com_code3+"')) a2 on a.abr035=A2.COM_CODE where a.aar027=1 or a.aar027 is null GROUP BY a2.com_name";
		List<Map> itemList2 = this.getBySqlMapper.findRecords(sql2);
		
		if(itemList2.size() > 0){
			for(int i=0;i<itemList2.size();i++){
				JSONObject object2 = new JSONObject();
				if(itemList2.get(i)!=null){
					Object obj="".equals(itemList2.get(i).get("TOTALAMOUNT"))||itemList2.get(i).get("TOTALAMOUNT")==null?"":itemList2.get(i).get("TOTALAMOUNT");
					if(!"".equals(obj)){
						object2.put("item_type","".equals(itemList2.get(i).get("COM_NAME"))||itemList2.get(i).get("COM_NAME")==null?"":itemList2.get(i).get("COM_NAME"));
						object2.put("totalAmount","".equals(itemList2.get(i).get("TOTALAMOUNT"))||itemList2.get(i).get("TOTALAMOUNT")==null?"":itemList2.get(i).get("TOTALAMOUNT"));
						json2.add(object2);
					}
				}
			}
		}
			String sql3 = "select sum(abr021) as totalMoney ,SUM (abr023) AS ZYtotalMoney,SUM (abr024) AS SJtotalMoney,SUM (abr025)+SUM (abr026) AS DFtotalMoney from NEIMENG0117_BR05 where ABR035 in (select b.ABR035 from NEIMENG0117_BR05  a left JOIN NEIMENG0117_BR06  b  on a.ABR020=b.ABR020 LEFT JOIN SYS_COMPANY  c ON a.ABR035=c.COM_CODE where   b.abr035 is not null  and c.COM_CODE like '%"+com_code2+"%' and c.COM_LEVEL!='"+com_level+"') and (aar027=1 or aar027 is null) ";
			List<Map> itemList3 = this.getBySqlMapper.findRecords(sql3);
			
			if(itemList3.size() > 0 &&itemList3.get(0)!=null){
				JSONObject object = new JSONObject();
					String totalMoney="".equals(itemList3.get(0).get("TOTALMONEY"))||itemList3.get(0).get("TOTALMONEY")==null?"0":itemList3.get(0).get("TOTALMONEY").toString();
					if(!totalMoney.equals("0")){
						String ZYtotalMoney="".equals(itemList3.get(0).get("ZYTOTALMONEY"))||itemList3.get(0).get("ZYTOTALMONEY")==null?"0":itemList3.get(0).get("ZYTOTALMONEY").toString();
						//中央下拨资金
						object.put("ZYtotalMoney",ZYtotalMoney);
//						if(!ZYtotalMoney.equals("0")){
//							Double ZYtotalMoneyPercentage=Double.parseDouble(ZYtotalMoney)/Double.parseDouble(totalMoney)*100;
//							object.put("ZYtotalMoney",ZYtotalMoneyPercentage+"%");
//							object.put("ZYtotalMoney",ZYtotalMoney);
//						}
						//省级下拨资金
						String SJtotalMoney="".equals(itemList3.get(0).get("SJTOTALMONEY"))||itemList3.get(0).get("SJTOTALMONEY")==null?"0":itemList3.get(0).get("SJTOTALMONEY").toString();
						object.put("SJtotalMoney",SJtotalMoney);
//						if(!SJtotalMoney.equals("0")){
////							Double SJtotalMoneyPercentage=Double.parseDouble(SJtotalMoney)/Double.parseDouble(totalMoney)*100;
////							object.put("SJtotalMoney",SJtotalMoneyPercentage+"%");
//							object.put("SJtotalMoney",SJtotalMoney);
//						}
						//地方下拨资金
						String DFtotalMoney="".equals(itemList3.get(0).get("DFTOTALMONEY"))||itemList3.get(0).get("DFTOTALMONEY")==null?"0":itemList3.get(0).get("DFTOTALMONEY").toString();
						object.put("DFtotalMoney",DFtotalMoney);
//						if(!DFtotalMoney.equals("0")){
////							Double DFtotalMoneyPercentage=Double.parseDouble(DFtotalMoney)/Double.parseDouble(totalMoney)*100;
////							object.put("DFtotalMoney",DFtotalMoneyPercentage+"%");
//							object.put("DFtotalMoney",DFtotalMoney);
//						}
					}
					object.put("totalMoney","".equals(itemList3.get(0).get("TOTALMONEY"))||itemList3.get(0).get("TOTALMONEY")==null?"0":itemList3.get(0).get("TOTALMONEY").toString());
				json3.add(object);
			}
			
			String sql4 = "select count(*) as itemType from (select ACR005 from NEIMENG0117_CR01 a LEFT JOIN SYS_COMPANY  c ON a.AAR008=c.COM_CODE where (a.ACR005 is not null or a.ACR005!='') and (a.AAR100=1 or a.AAR100 is null ) and c.COM_CODE like '%"+com_code2+"%' and c.COM_LEVEL!='"+com_level+"' GROUP BY a.ACR005) ";
							
			List<Map> itemList4 = this.getBySqlMapper.findRecords(sql4);
			String sql6 ="select count(*) as itemType from NEIMENG0117_CR01 a LEFT JOIN SYS_COMPANY  c ON a.AAR008=c.COM_CODE where (a.ACR005 is not null or a.ACR005!='') and (a.AAR100=1 or a.AAR100 is null) and c.COM_CODE like '%"+com_code2+"%' and c.COM_LEVEL!='"+com_level+"'";
			List<Map> itemList6 = this.getBySqlMapper.findRecords(sql6);
			if(itemList4.size() > 0&&itemList4.get(0)!=null){
				JSONObject object = new JSONObject();
					object.put("itemType","".equals(itemList4.get(0).get("ITEMTYPE"))||itemList4.get(0).get("ITEMTYPE")==null?"0":itemList4.get(0).get("ITEMTYPE").toString());
				if(itemList6.size() > 0&&itemList6.get(0)!=null){
						object.put("itemType2","".equals(itemList6.get(0).get("ITEMTYPE"))||itemList6.get(0).get("ITEMTYPE")==null?"0":itemList6.get(0).get("ITEMTYPE").toString());
					
				}else{
					object.put("itemType2","0");
				}
				json4.add(object);
			}
			
			String sql5 = "select sum(a.ACR333)as SYPKHS, sum(a.acr334) as SYFPKHS from NEIMENG0117_CR01 a LEFT JOIN SYS_COMPANY  c ON a.AAR008=c.COM_CODE where (a.AAR100=1 or a.AAR100 is null ) and c.COM_CODE like '%"+com_code2+"%' and c.COM_LEVEL!='"+com_level+"'";
			List<Map> itemList5 = this.getBySqlMapper.findRecords(sql5);
	
			if(itemList5.size() > 0&&itemList5.get(0)!=null){
				JSONObject object = new JSONObject();
				String itemType1="";
				String itemType2="";
				boolean isNULL="".equals(itemList5.get(0).get("SYPKHS"))||itemList5.get(0).get("SYPKHS")==null;
				boolean isNULL2="".equals(itemList5.get(0).get("SYFPKHS"))||itemList5.get(0).get("SYFPKHS")==null;
				if(!isNULL){
					itemType1="贫困户";
				}
				if(!isNULL2){
					itemType2="非贫困户";
				}
				object.put("itemType1",itemType1);
				object.put("SYPKHS","".equals(itemList5.get(0).get("SYPKHS"))||itemList5.get(0).get("SYPKHS")==null?"0":itemList5.get(0).get("SYPKHS").toString());
				object.put("itemType2",itemType2);
				object.put("SYFPKHS","".equals(itemList5.get(0).get("SYFPKHS"))||itemList5.get(0).get("SYFPKHS")==null?"0":itemList5.get(0).get("SYFPKHS").toString());
				json5.add(object);
			}
			response.getWriter().write("{\"data1\":"+json.toString()+",\"data2\":"+json2.toString()+",\"data3\":"+json3.toString()+",\"data4\":"+json4.toString()+",\"data5\":"+json5.toString()+",\"data6\":"+json6.toString()+"}");
		}catch (Exception e) {
		response.getWriter().write('0');
		}
	}
	
	public String getItemTypeName(String code){
		String typeName = "";
		switch(code){
			case "10":
				typeName = "雨露计划";
				break;
			case "20":
				typeName = "易地扶贫搬迁";
				break;
			case "30":
				typeName = "金融扶贫";
				break;
			case "40":
				typeName = "产业扶贫";
				break;
			case "50":
				typeName = "整村推进";
				break;
			default:
				typeName = "其他";
				break;
		}
		return typeName;
	}
	
	/**
	 * 根据点击的行政区划获得资金分布情况
	 * @param request
	 * @param response
	 * @throws IOException
	 *	@author Liulifeng
	 *  @date 2017年5月4日 上午9:42:11
	 */
	@RequestMapping("getCapitalByXZQH.do")
	public void getCapitalByXZQH(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			
			String com_name="";//行政区划
			if(!"".equals(request.getParameter("com_name"))&&request.getParameter("com_name")!=null){
				com_name=request.getParameter("com_name");
			}else{
				com_name="内蒙古自治区";
			}
			String sql2 = "SELECT a2.com_name,SUM (ABR021) AS totalAmount FROM NEIMENG0117_BR05 a RIGHT JOIN  (SELECT * FROM SYS_COMPANY WHERE COM_F_PKID=(SELECT PKID FROM SYS_COMPANY WHERE COM_NAME='"+com_name+"')) a2 on a.abr035=A2.COM_CODE GROUP BY a2.com_name";
			List<Map> itemList2 = this.getBySqlMapper.findRecords(sql2);
			JSONArray json2=new JSONArray();
			if(itemList2.size() > 0){
				for(int i=0;i<itemList2.size();i++){
					JSONObject object2 = new JSONObject();
					if(itemList2.get(i)!=null){
						Object obj="".equals(itemList2.get(i).get("TOTALAMOUNT"))||itemList2.get(i).get("TOTALAMOUNT")==null?"":itemList2.get(i).get("TOTALAMOUNT");
						if(!"".equals(obj)){
							object2.put("item_type","".equals(itemList2.get(i).get("COM_NAME"))||itemList2.get(i).get("COM_NAME")==null?"":itemList2.get(i).get("COM_NAME"));
							object2.put("totalAmount","".equals(itemList2.get(i).get("TOTALAMOUNT"))||itemList2.get(i).get("TOTALAMOUNT")==null?"":itemList2.get(i).get("TOTALAMOUNT"));
							json2.add(object2);
						}
						
					}
				}
			}
			response.getWriter().write("{\"data2\":"+json2.toString()+"}");
		} catch (Exception e) {
			
		}
		
	}
	
	
	
}


