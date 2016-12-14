package com.gistone.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gistone.MyBatis.config.GetBySqlMapper;
import com.gistone.util.Tool;

@RestController
@RequestMapping
public class Updatapassword {
	@Autowired
	private GetBySqlMapper getBySqlMapper;

	
	 /**
	 * @method 更新密码
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws NoSuchAlgorithmException 
	 */
	@RequestMapping("changepassword.do")
	public void changepassword(HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchAlgorithmException {
		String sql = "SELECT SYS_COM_CODE FROM SYS_USER WHERE PKID<>1 AND PKID<>2";//取出code
		List<Map> list = this.getBySqlMapper.findRecords(sql);
		for(int i=0; i<list.size(); i++){
			String pass = list.get(i).get("SYS_COM_CODE").toString();//得到区划编码
			String password = Tool.md5(pass.substring(0, 6));//加密
			String sql_2 = "UPDATE SYS_USER SET COL_PASSWORD='"+password+"' WHERE SYS_COM_CODE='"+pass+"'";
			String sql_3 = "UPDATE SYS_USER SET COL_ACCOUNT='"+pass.substring(0, 6)+"' WHERE SYS_COM_CODE='"+pass+"'";
			
			String sql_4 = "SELECT PKID FROM SYS_COMPANY WHERE COM_CODE='"+pass.replace(" ", "")+"'";//得到SYS_COMPANY表的PKID
			String opkid = this.getBySqlMapper.findRecords(sql_4).get(0).get("PKID").toString();
			String sql_5 = "UPDATE SYS_USER SET SYS_COMPANY_ID='"+opkid+"' WHERE SYS_COM_CODE='"+pass+"'";
			
			try {
				this.getBySqlMapper.findRecords(sql_2);
				this.getBySqlMapper.findRecords(sql_3);
				this.getBySqlMapper.findRecords(sql_5);
				System.out.println("成功");
			} catch (Exception e) {
				System.err.println("失败");
			}
		}
	}
}