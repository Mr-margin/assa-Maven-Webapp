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
/**
 * 项目统计中项目受益模块
* @ClassName:
* @Description: 
* @author 刘立峰
* @date 2017年5月4日
* @version 1.0
 */
@RestController
@RequestMapping
public class WtApp_t2_projectBenefitController {
	@Autowired
	private GetBySqlMapper getBySqlMapper;
	
	
	/**
	 * 得到项目受益户数据
	 * @param request
	 * @param response
	 * @throws IOException
	 *	@author Liulifeng
	 *  @date 2017年5月4日 下午2:40:41
	 */
	@RequestMapping("getProjectBenefitHousehold.do")
	public void getProjectBenefitHousehold(HttpServletRequest request,HttpServletResponse response) throws IOException{
	 try {
		 String com_name = request.getParameter("name");// 行政区划
			if (com_name.equals("全部盟市")) {
				com_name = "内蒙古自治区";
			}
			String sql2="select COM_CODE,COM_LEVEL,PKID from SYS_COMPANY where COM_NAME='"+com_name+"'";
			List<Map> xzqhList = this.getBySqlMapper.findRecords(sql2);
			int com_f_pkid=0;//行政区划父id
			String com_code="150000000000";//行政区划
			int com_level=1;//登陆用户的行政区划级别
			if(xzqhList.size()>0){
				if(!"".equals(xzqhList.get(0).get("COM_CODE"))&&xzqhList.get(0).get("COM_CODE")!=null){
					com_code=(String) xzqhList.get(0).get("COM_CODE");
				}
				if(!"".equals(xzqhList.get(0).get("COM_LEVEL"))&&xzqhList.get(0).get("COM_LEVEL")!=null){
					com_level=Integer.parseInt( xzqhList.get(0).get("COM_LEVEL").toString());
				}
				if(!"".equals(xzqhList.get(0).get("PKID"))&&xzqhList.get(0).get("PKID")!=null){
					com_f_pkid=Integer.parseInt( xzqhList.get(0).get("PKID").toString());
				}
			}
			String sql="";
			if(com_level==1){
				sql="SELECT * FROM (SELECT	s.COM_NAME,	SUBSTR (s.COM_CODE, 0, 4) code	FROM SYS_COMPANY s	WHERE	s.COM_CODE LIKE '"+com_code.substring(0,3)+"%' AND (s.COM_F_pkid = '"+com_f_pkid+"' or s.COM_F_pkid=0)) b LEFT JOIN (SELECT	SUBSTR (a.aar008, 0, 4) xzqh,COUNT (*) AS count_num FROM	NEIMENG0117_CR05 A LEFT JOIN NEIMENG0117_CR01 A2 ON A .ACR010 = A2.ACR010 where A .AAR100 = 1 GROUP BY	SUBSTR (a.aar008, 0, 4)) c on b.CODE=c.xzqh";
			}else if(com_level==2){
				sql="SELECT * FROM (SELECT	s.COM_NAME,	SUBSTR (s.COM_CODE, 0, 6) code	FROM SYS_COMPANY s	WHERE	s.COM_CODE LIKE '"+com_code.substring(0,4)+"%' AND (s.COM_F_pkid = '"+com_f_pkid+"' or s.COM_F_pkid=0)) b LEFT JOIN (SELECT	SUBSTR (a.aar008, 0, 6) xzqh,COUNT (*) AS count_num FROM	NEIMENG0117_CR05 A LEFT JOIN NEIMENG0117_CR01 A2 ON A .ACR010 = A2.ACR010 where A .AAR100 = 1 GROUP BY	SUBSTR (a.aar008, 0, 6)) c on b.CODE=c.xzqh";
			}else if(com_level==3){
				sql="SELECT * FROM (SELECT	s.COM_NAME,	SUBSTR (s.COM_CODE, 0, 9) code	FROM SYS_COMPANY s	WHERE	s.COM_CODE LIKE '"+com_code.substring(0,6)+"%' AND (s.COM_F_pkid = '"+com_f_pkid+"' or s.COM_F_pkid=0)) b LEFT JOIN (SELECT	SUBSTR (a.aar008, 0, 9) xzqh,COUNT (*) AS count_num FROM	NEIMENG0117_CR05 A LEFT JOIN NEIMENG0117_CR01 A2 ON A .ACR010 = A2.ACR010 where A .AAR100 = 1 GROUP BY	SUBSTR (a.aar008, 0, 9)) c on b.CODE=c.xzqh";
			}else if(com_level==4){
				sql="SELECT * FROM (SELECT	s.COM_NAME,	SUBSTR (s.COM_CODE, 0, 12) code	FROM SYS_COMPANY s	WHERE	s.COM_CODE LIKE '"+com_code.substring(0,9)+"%' AND (s.COM_F_pkid = '"+com_f_pkid+"' or s.COM_F_pkid=0)) b LEFT JOIN (SELECT	SUBSTR (a.aar008, 0, 12) xzqh,COUNT (*) AS count_num FROM	NEIMENG0117_CR05 A LEFT JOIN NEIMENG0117_CR01 A2 ON A .ACR010 = A2.ACR010 where A .AAR100 = 1 GROUP BY	SUBSTR (a.aar008, 0, 12)) c on b.CODE=c.xzqh";
			}else if(com_level==5){
				sql="SELECT * FROM (SELECT	s.COM_NAME,	s.COM_CODE code	FROM SYS_COMPANY s	WHERE	s.COM_CODE LIKE '"+com_code+"%' ) b LEFT JOIN (SELECT	a.aar008 xzqh,COUNT (*) AS count_num FROM	NEIMENG0117_CR05 A LEFT JOIN NEIMENG0117_CR01 A2 ON A .ACR010 = A2.ACR010 where A .AAR100 = 1 GROUP BY	a.aar008) c on b.CODE=c.xzqh";
			}
			List<Map> projectBenefitHouseholdList = this.getBySqlMapper.findRecords(sql);
			JSONArray json=new JSONArray();
			if(projectBenefitHouseholdList.size() > 0){
				for(int i=0;i<projectBenefitHouseholdList.size();i++){
					JSONObject object = new JSONObject();
					if(projectBenefitHouseholdList.get(i)!=null){
						Object count_num="".equals(projectBenefitHouseholdList.get(i).get("COUNT_NUM"))||projectBenefitHouseholdList.get(i).get("COUNT_NUM")==null?"":projectBenefitHouseholdList.get(i).get("COUNT_NUM");
						if(!"".equals(count_num)){
							object.put("com_name","".equals(projectBenefitHouseholdList.get(i).get("COM_NAME"))||projectBenefitHouseholdList.get(i).get("COM_NAME")==null?"":projectBenefitHouseholdList.get(i).get("COM_NAME"));
							object.put("count_num",count_num);
							json.add(object);
						}
					}
				}
			}
			response.getWriter().write(json.toString());
		}catch (Exception e) {
		response.getWriter().write('0');
		}
	}
	
	
}


