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
 * 项目统计中项目资金模块
* @ClassName:
* @Description: 
* @author 刘立峰
* @date 2017年5月4日
* @version 1.0
 */
@RestController
@RequestMapping
public class WtApp_t2_projectBonusController {
	@Autowired
	private GetBySqlMapper getBySqlMapper;
	
	
	/**
	 * 得到项目资金计划数据
	 * @param request
	 * @param response
	 * @throws IOException
	 *	@author Liulifeng
	 *  @date 2017年5月4日 下午2:40:41
	 */
	@RequestMapping("getProjectBonusPlan.do")
	public void getProjectBonusPlan(HttpServletRequest request,HttpServletResponse response) throws IOException{
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
				sql="SELECT * FROM (SELECT	s.COM_NAME,	SUBSTR (s.COM_CODE, 0, 4) code	FROM SYS_COMPANY s	WHERE	s.COM_CODE LIKE '"+com_code.substring(0,3)+"%' AND (s.COM_F_pkid = '"+com_f_pkid+"' or s.COM_F_pkid=0)) b LEFT JOIN (SELECT	SUBSTR (a.aar008, 0, 4) xzqh,sum(A.ACR006) as budget_amount FROM	NEIMENG0117_CR03 A LEFT JOIN NEIMENG0117_CR01 A2 ON A .ACR010 = A2.ACR010  GROUP BY	SUBSTR (a.aar008, 0, 4)) c on b.CODE=c.xzqh";
			}else if(com_level==2){
				sql="SELECT * FROM (SELECT	s.COM_NAME,	SUBSTR (s.COM_CODE, 0, 6) code	FROM SYS_COMPANY s	WHERE	s.COM_CODE LIKE '"+com_code.substring(0,4)+"%' AND (s.COM_F_pkid = '"+com_f_pkid+"' or s.COM_F_pkid=0)) b LEFT JOIN (SELECT	SUBSTR (a.aar008, 0, 6) xzqh,sum(A.ACR006) as budget_amount FROM	NEIMENG0117_CR03 A LEFT JOIN NEIMENG0117_CR01 A2 ON A .ACR010 = A2.ACR010  GROUP BY	SUBSTR (a.aar008, 0, 6)) c on b.CODE=c.xzqh";
			}else if(com_level==3){
				sql="SELECT * FROM (SELECT	s.COM_NAME,	SUBSTR (s.COM_CODE, 0, 9) code	FROM SYS_COMPANY s	WHERE	s.COM_CODE LIKE '"+com_code.substring(0,6)+"%' AND (s.COM_F_pkid = '"+com_f_pkid+"' or s.COM_F_pkid=0)) b LEFT JOIN (SELECT	SUBSTR (a.aar008, 0, 9) xzqh,sum(A.ACR006) as budget_amount FROM	NEIMENG0117_CR03 A LEFT JOIN NEIMENG0117_CR01 A2 ON A .ACR010 = A2.ACR010  GROUP BY	SUBSTR (a.aar008, 0, 9)) c on b.CODE=c.xzqh";
			}else if(com_level==4){
				sql="SELECT * FROM (SELECT	s.COM_NAME,	SUBSTR (s.COM_CODE, 0, 12) code	FROM SYS_COMPANY s	WHERE	s.COM_CODE LIKE '"+com_code.substring(0,9)+"%' AND (s.COM_F_pkid = '"+com_f_pkid+"' or s.COM_F_pkid=0)) b LEFT JOIN (SELECT	SUBSTR (a.aar008, 0, 12) xzqh,sum(A.ACR006) as budget_amount FROM	NEIMENG0117_CR03 A LEFT JOIN NEIMENG0117_CR01 A2 ON A .ACR010 = A2.ACR010  GROUP BY	SUBSTR (a.aar008, 0, 12)) c on b.CODE=c.xzqh";
			}else if(com_level==5){
				sql="SELECT * FROM (SELECT	s.COM_NAME,	s.COM_CODE code	FROM SYS_COMPANY s	WHERE	s.COM_CODE LIKE '"+com_code+"%' ) b LEFT JOIN (SELECT	a.aar008 xzqh,sum(A.ACR006) as budget_amount FROM	NEIMENG0117_CR03 A LEFT JOIN NEIMENG0117_CR01 A2 ON A .ACR010 = A2.ACR010 where A .AAR100 = 1 GROUP BY	a.aar008) c on b.CODE=c.xzqh";
			}
			//String sql="SELECT A3.com_name,sum(A.ACR006) as budget_amount FROM NEIMENG0117_CR03  A  LEFT JOIN NEIMENG0117_CR01  A2  ON A .ACR010 = A2.ACR010 RIGHT JOIN  ( SELECT 	* FROM SYS_COMPANY WHERE COM_F_PKID = ( SELECT	PKID FROM SYS_COMPANY  WHERE COM_NAME = '"+com_name+"'	)) A3 ON A.aar008 = A3.COM_CODE GROUP BY a3.com_name";
			List<Map> projectBonusPlanList = this.getBySqlMapper.findRecords(sql);
			JSONArray json=new JSONArray();
			if(projectBonusPlanList.size() > 0){
				for(int i=0;i<projectBonusPlanList.size();i++){
					JSONObject object = new JSONObject();
					if(projectBonusPlanList.get(i)!=null){
						Object budget_amount="".equals(projectBonusPlanList.get(i).get("BUDGET_AMOUNT"))||projectBonusPlanList.get(i).get("BUDGET_AMOUNT")==null?"":projectBonusPlanList.get(i).get("BUDGET_AMOUNT");
						if(!"".equals(budget_amount)){
							object.put("com_name","".equals(projectBonusPlanList.get(i).get("COM_NAME"))||projectBonusPlanList.get(i).get("COM_NAME")==null?"":projectBonusPlanList.get(i).get("COM_NAME"));
							object.put("budget_amount",budget_amount);
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
	
	/**
	 * 得到项目资金落实数据
	 * @param request
	 * @param response
	 * @throws IOException
	 *	@author Liulifeng
	 *  @date 2017年5月4日 下午2:40:41
	 */
	@RequestMapping("getProjectBonusPracticable.do")
	public void getProjectBonusPracticable(HttpServletRequest request,HttpServletResponse response) throws IOException{
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
				sql="SELECT * FROM (SELECT	s.COM_NAME,	SUBSTR (s.COM_CODE, 0, 4) code	FROM SYS_COMPANY s	WHERE	s.COM_CODE LIKE '"+com_code.substring(0,3)+"%' AND (s.COM_F_pkid = '"+com_f_pkid+"' or s.COM_F_pkid=0)) b LEFT JOIN (SELECT	SUBSTR (a.aar008, 0, 4) xzqh,sum(a.ACR283) as practicable_capital FROM	NEIMENG0117_CR28 A LEFT JOIN NEIMENG0117_CR01 A2 ON A .ACR010 = A2.ACR010  GROUP BY	SUBSTR (a.aar008, 0, 4)) c on b.CODE=c.xzqh";
			}else if(com_level==2){
				sql="SELECT * FROM (SELECT	s.COM_NAME,	SUBSTR (s.COM_CODE, 0, 6) code	FROM SYS_COMPANY s	WHERE	s.COM_CODE LIKE '"+com_code.substring(0,4)+"%' AND (s.COM_F_pkid = '"+com_f_pkid+"' or s.COM_F_pkid=0)) b LEFT JOIN (SELECT	SUBSTR (a.aar008, 0, 6) xzqh,sum(a.ACR283) as practicable_capital FROM	NEIMENG0117_CR28 A LEFT JOIN NEIMENG0117_CR01 A2 ON A .ACR010 = A2.ACR010  GROUP BY	SUBSTR (a.aar008, 0, 6)) c on b.CODE=c.xzqh";
			}else if(com_level==3){
				sql="SELECT * FROM (SELECT	s.COM_NAME,	SUBSTR (s.COM_CODE, 0, 9) code	FROM SYS_COMPANY s	WHERE	s.COM_CODE LIKE '"+com_code.substring(0,6)+"%' AND (s.COM_F_pkid = '"+com_f_pkid+"' or s.COM_F_pkid=0)) b LEFT JOIN (SELECT	SUBSTR (a.aar008, 0, 9) xzqh,sum(a.ACR283) as practicable_capital FROM	NEIMENG0117_CR28 A LEFT JOIN NEIMENG0117_CR01 A2 ON A .ACR010 = A2.ACR010  GROUP BY	SUBSTR (a.aar008, 0, 9)) c on b.CODE=c.xzqh";
			}else if(com_level==4){
				sql="SELECT * FROM (SELECT	s.COM_NAME,	SUBSTR (s.COM_CODE, 0, 12) code	FROM SYS_COMPANY s	WHERE	s.COM_CODE LIKE '"+com_code.substring(0,9)+"%' AND (s.COM_F_pkid = '"+com_f_pkid+"' or s.COM_F_pkid=0)) b LEFT JOIN (SELECT	SUBSTR (a.aar008, 0, 12) xzqh,sum(a.ACR283) as practicable_capital FROM	NEIMENG0117_CR28 A LEFT JOIN NEIMENG0117_CR01 A2 ON A .ACR010 = A2.ACR010  GROUP BY	SUBSTR (a.aar008, 0, 12)) c on b.CODE=c.xzqh";
			}else if(com_level==5){
				sql="SELECT * FROM (SELECT	s.COM_NAME,	s.COM_CODE code	FROM SYS_COMPANY s	WHERE	s.COM_CODE LIKE '"+com_code+"%' ) b LEFT JOIN (SELECT	a.aar008 xzqh,sum(a.ACR283) as practicable_capital FROM	NEIMENG0117_CR28 A LEFT JOIN NEIMENG0117_CR01 A2 ON A .ACR010 = A2.ACR010 where A .AAR100 = 1 GROUP BY	a.aar008) c on b.CODE=c.xzqh";
			}
			//String sql="select A3.com_name ,sum(a.ACR283) as practicable_capital from NEIMENG0117_CR28 A LEFT JOIN  NEIMENG0117_CR01  A2  ON A .ACR010 = A2.ACR010 RIGHT JOIN  ( SELECT 	* FROM SYS_COMPANY WHERE COM_F_PKID = ( SELECT	PKID FROM SYS_COMPANY  WHERE COM_NAME = '"+com_name+"'	)) A3 ON A.aar008 = A3.com_code GROUP BY a3.com_name ";
			List<Map> projectBonusPracticableList = this.getBySqlMapper.findRecords(sql);
			JSONArray json=new JSONArray();
			if(projectBonusPracticableList.size() > 0){
				for(int i=0;i<projectBonusPracticableList.size();i++){
					JSONObject object = new JSONObject();
					if(projectBonusPracticableList.get(i)!=null){
						Object practicable_capital="".equals(projectBonusPracticableList.get(i).get("PRACTICABLE_CAPITAL"))||projectBonusPracticableList.get(i).get("PRACTICABLE_CAPITAL")==null?"":projectBonusPracticableList.get(i).get("PRACTICABLE_CAPITAL");
						if(!"".equals(practicable_capital)){
							object.put("com_name","".equals(projectBonusPracticableList.get(i).get("COM_NAME"))||projectBonusPracticableList.get(i).get("COM_NAME")==null?"":projectBonusPracticableList.get(i).get("COM_NAME"));
							object.put("practicable_capital",practicable_capital);
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


