package com.gistone.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		String sql = "select c.ACR005 item_type,count(*) count_num from NEIMENG0117_CR01 c GROUP BY c.ACR005 ORDER BY ITEM_TYPE ASC";
		List<Map> itemList = this.getBySqlMapper.findRecords(sql);
		JSONArray json = new JSONArray();
		if(itemList.size() > 0){
			for(int i=0;i<itemList.size();i++){
				JSONObject object = new JSONObject();
				object.put("item_type","".equals(itemList.get(i).get("ITEM_TYPE"))||itemList.get(i).get("ITEM_TYPE")==null?"":getItemTypeName(itemList.get(i).get("ITEM_TYPE").toString()));
				object.put("count_num","".equals(itemList.get(i).get("COUNT_NUM"))||itemList.get(i).get("COUNT_NUM")==null?"":itemList.get(i).get("COUNT_NUM"));
				json.add(object);
			}
			String sql2 = "select ACR005,sum(ABR021) as totalAmount  from NEIMENG0117_BR05 GROUP BY ACR005 ORDER BY ACR005";
			List<Map> itemList2 = this.getBySqlMapper.findRecords(sql2);
			JSONArray json2 = new JSONArray();
			if(itemList2.size() > 0){
				for(int i=0;i<itemList2.size();i++){
					JSONObject object2 = new JSONObject();
					String item_type="".equals(itemList2.get(i).get("ACR005"))||itemList2.get(i).get("ACR005")==null?"":getItemTypeName(itemList2.get(i).get("ACR005").toString());
					String totalAmount="".equals(itemList2.get(i).get("TOTALAMOUNT"))||itemList2.get(i).get("TOTALAMOUNT")==null?"":itemList2.get(i).get("TOTALAMOUNT").toString();
					object2.put("item_type",item_type);
					if(item_type.equals("其他")&&!totalAmount.equals("")){
						Double totalAmount3=Double.parseDouble(totalAmount);
						for(int y=1;y<itemList2.size()-i;y++){
							String totalAmount2="".equals(itemList2.get(i+1).get("TOTALAMOUNT"))||itemList2.get(i+1).get("TOTALAMOUNT")==null?"":itemList2.get(i+1).get("TOTALAMOUNT").toString();
							if(!totalAmount2.equals("")){
								totalAmount3+=Double.parseDouble(totalAmount2.toString());
							}
						}
						totalAmount=totalAmount3.toString();
						object2.put("totalAmount",totalAmount);
						json2.add(object2);
						break;
					}else{
						object2.put("totalAmount",totalAmount);
						json2.add(object2);
					}
				}
			}
			response.getWriter().write("{\"data1\":"+json.toString()+",\"data2\":"+json2.toString()+"}");
		}else{
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
}


