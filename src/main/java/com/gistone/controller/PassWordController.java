package com.gistone.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gistone.MyBatis.config.GetBySqlMapper;
import com.gistone.util.Tool;

@RestController
@RequestMapping
public class PassWordController{

	@Autowired
	private GetBySqlMapper getBySqlMapper;

	/**
	 * @method 更新密码
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 张晓翔
	 * @date 2016年8月4日下午4:58:14
	 */
	@RequestMapping("upPassword.do")
	public void upPassword(HttpServletRequest request,HttpServletResponse response) throws Exception{

		String id = request.getParameter("pkid");//获取用户ID
		String password = request.getParameter("password");//获取密码
		password = Tool.md5(password);
		String people_sql = "update sys_user set COL_PASSWORD='"+password+"' where COL_ACCOUNT='"+id+"'";
		try{
			this.getBySqlMapper.update(people_sql);
			response.getWriter().write("1");
		}catch (Exception e){
			response.getWriter().write("0");
		}
	}

	/**
	 * @method 验证修改密码时输入是否和原密码相同
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 张晓翔
	 * @date 2016年8月4日下午4:58:28
	 */
	@RequestMapping("o_password.do")
	public void o_password(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String username=request.getParameter("username");
		String val=request.getParameter("val");

		String sql = "SELECT count(*) FROM sys_user WHERE COL_ACCOUNT='" + username + "' and COL_PASSWORD = '" + Tool.md5(val) + "'";
		int resultSize = getBySqlMapper.findrows(sql);
		if (resultSize == 0){
			response.getWriter().print("0");
		}else{
			response.getWriter().print("1");
		}
	}

	/**
	 * @method 更新密码--将行政编码的前6位进行MD5加密后，当作密码
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
				System.out.println("添加--"+pass+"--成功");
			} catch (Exception e) {
				System.err.println("添加--"+pass+"--失败");
			}
		}
	}
}
