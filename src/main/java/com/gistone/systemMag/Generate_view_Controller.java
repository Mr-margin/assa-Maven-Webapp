package com.gistone.systemMag;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.gistone.MyBatis.config.GetBySqlMapper;

@RestController
@RequestMapping
public class Generate_view_Controller extends MultiActionController{
	@Autowired
	private GetBySqlMapper getBySqlMapper;
	
	private String url;
	private String databaseName;
	private String dclass;
	private String user;
	private String password;
	
	//视图列表
	@RequestMapping("get_view_list.do")
	public void get_view_list(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String pageSize = request.getParameter("pageSize");
		String pageNumber = request.getParameter("pageNumber");
		String search = "";
		
		if(request.getParameter("search")!=null&&!request.getParameter("search").equals("")){
			search = request.getParameter("search").trim();
		}
		
		int size = Integer.parseInt(pageSize);
		int number = Integer.parseInt(pageNumber);
		
		int page = number == 0 ? 1 : (number/size)+1;
		
		String count_st_sql = "select count(*) from metadata_view where name_en like '%"+search+"%' or name_cn like '%"+search+"%'";
		int total = this.getBySqlMapper.findrows(count_st_sql);
		
		
		String Metadata_up_sql = "select * from metadata_view where (name_en like '%"+search+"%' or name_cn like '%"+search+"%') and pkid limit "+number+","+size;
		List<Map> Metadata_up_List = this.getBySqlMapper.findRecords(Metadata_up_sql);
		if(Metadata_up_List.size()>0){
			JSONArray jsa=new JSONArray();
			for(int i = 0;i<Metadata_up_List.size();i++){
				Map Metadata_up_map = Metadata_up_List.get(i);
				JSONObject val = new JSONObject();
				for (Object key : Metadata_up_map.keySet()) {
					val.put(key, Metadata_up_map.get(key));
				}
				jsa.add(val);
			}
			
			JSONObject json = new JSONObject();
			json.put("page", page);
			json.put("total", total);
			json.put("rows", jsa); //这里的 rows 和total 的key 是固定的 
			  
			//System.out.println(json.toString());
			response.getWriter().write(json.toString());
		}
		
		
	}
	
	//增加视图
	@RequestMapping("add_view.do")
	public void add_view(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String name_en = request.getParameter("name_en").trim();
		String name_cn = request.getParameter("name_cn").trim();
    	
		//新增数据表
		String add_Sql = "insert into metadata_view(name_en,name_cn) values('"+name_en+"','"+name_cn+"')";
		
		try{
			this.getBySqlMapper.insert(add_Sql);
			response.getWriter().write("1");
		}catch (Exception e){
			response.getWriter().write("0");
		}
		
		
	}
	
	//修改视图
	@RequestMapping("up_view.do")
	public void up_view(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String pkid = request.getParameter("pkid");
    	String name_en = request.getParameter("name_en").trim();
		String name_cn = request.getParameter("name_cn").trim();
		String sql_val = request.getParameter("sql_val").trim();
    	
		//修改数据表名称
		String up_Sql = "update metadata_view set name_en='"+name_en+"',name_cn='"+name_cn+"' where pkid="+pkid;

		
		
		
		try{
			if(sql_val!=null&&!sql_val.equals("")){
				//System.out.println("修改");
				//删除视图
				String structure_Sql = "drop view if exists "+name_en;

				
				//重新创建视图
				String update_sql = "CREATE VIEW "+name_en+" AS "+sql_val;
			
				
				this.getBySqlMapper.update(structure_Sql);
				this.getBySqlMapper.update(update_sql);
			}
			
			this.getBySqlMapper.update(up_Sql);
			response.getWriter().write("1");
		}catch (Exception e){
			response.getWriter().write("0");
		}
		
	}
	
	//删除视图
	@RequestMapping("del_view.do")
	public void del_view(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String pkid = request.getParameter("pkid");
		String name_en = request.getParameter("name_en").trim();
		String sql_val = request.getParameter("sql_val");
		//删除数据表名称
		String up_Sql = "delete from metadata_view where pkid="+pkid;

		
		try{
			if(sql_val!=null&&!sql_val.equals("")){
				//System.out.println("删除");
				//删除视图
				String structure_Sql = "drop view if exists "+name_en;
				
				this.getBySqlMapper.update(structure_Sql);
			}
			this.getBySqlMapper.delete(up_Sql);
			response.getWriter().write("1");
		}catch (Exception e){
			//e.printStackTrace();
			//System.out.println("异常");
			response.getWriter().write("0");
		}
		
	}
	
	//视图数据
	@RequestMapping("get_view_data.do")
	public void get_view_data(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String pageSize = request.getParameter("pageSize");
		String pageNumber = request.getParameter("pageNumber");
		String table_name = request.getParameter("table_name");
		
		int size = Integer.parseInt(pageSize);
		int number = Integer.parseInt(pageNumber);
		
		int page = number == 0 ? 1 : (number/size)+1;
		
		try{
			String count_st_sql = "select count(*) from "+table_name;
			int total = this.getBySqlMapper.findrows(count_st_sql);
			
			
			String Metadata_up_sql = "select * from "+table_name+" limit "+number+","+size;
			List<Map> Metadata_up_List = this.getBySqlMapper.findRecords(Metadata_up_sql);
			if(Metadata_up_List.size()>0){
				JSONArray jsa=new JSONArray();
				for(int i = 0;i<Metadata_up_List.size();i++){
					Map Metadata_up_map = Metadata_up_List.get(i);
					JSONObject val = new JSONObject();
					for (Object key : Metadata_up_map.keySet()) {
						val.put(key, Metadata_up_map.get(key));
					}
					jsa.add(val);
				}
				
				JSONObject json = new JSONObject();
				json.put("page", page);
				json.put("total", total);
				json.put("rows", jsa); //这里的 rows 和total 的key 是固定的 
				//System.out.println(json.toString());
				response.getWriter().write(json.toString());
			}
		}catch (Exception e){
			
		}
		
		
	}
	
	//视图数据表应该有哪些列
	@RequestMapping("get_view_col.do")
	public void get_view_col(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String view_name_en = request.getParameter("view_name_en").trim();
		JSONObject result_jo = new JSONObject();
		try{
			//获取绝对路径
//			ServletContext context = getServletContext();
//	        String path = context.getRealPath("/");
//	        if (!path.endsWith(java.io.File.separator)) {
//	        	path = path + java.io.File.separator;
//	        }
//	        getProxool(path);
	        
			String Metadata_up_sql = "select column_name,column_type from information_schema.columns where table_schema='"+databaseName+"' and table_name='"+view_name_en+"'";
			List<Map> Metadata_up_List = this.getBySqlMapper.findRecords(Metadata_up_sql);
			
			if(Metadata_up_List.size()>0){
				JSONArray jsa=new JSONArray();
				for(int i = 0;i<Metadata_up_List.size();i++){
					Map Metadata_st_map = Metadata_up_List.get(i);
					JSONObject val = new JSONObject();
					val.put("align","center");//数据对齐方式
					val.put("halign","center");//标题对齐方式
					val.put("field",Metadata_st_map.get("column_name"));//列字段名
					val.put("title",Metadata_st_map.get("column_name"));//栏目标题文本
					jsa.add(val);
				}
				//System.out.println(jsa.toString());
				result_jo.put("state", "0");
				result_jo.put("result", jsa);
				response.getWriter().write(result_jo.toString());
				
			}else{
				result_jo.put("state", "1");
				result_jo.put("error", "没有视图语句，无法读取数据");
				response.getWriter().write(result_jo.toString());
				
			}
		}catch (Exception e){
			result_jo.put("state", "1");
			result_jo.put("error", "视图语句执行错误，请检查数据");
			response.getWriter().write(result_jo.toString());
			
		}
	}
	
	//执行sql语句时读取数据
	@RequestMapping("get_view_data_implement.do")
	public void get_view_data_implement(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String pageSize = request.getParameter("pageSize");
		String pageNumber = request.getParameter("pageNumber");
		String implement_sql = request.getParameter("implement_sql").trim();
		
		int size = Integer.parseInt(pageSize);
		int number = Integer.parseInt(pageNumber);
		
		int page = number == 0 ? 1 : (number/size)+1;
		
		String count_st_sql = "select count(*) from ("+implement_sql+") view_data_implement";
		int total = this.getBySqlMapper.findrows(count_st_sql);
		
		
		String Metadata_up_sql = "select * from ("+implement_sql+") view_data_implement limit "+number+","+size;
		List<Map> Metadata_up_List = this.getBySqlMapper.findRecords(Metadata_up_sql);
		if(Metadata_up_List.size()>0){
			JSONArray jsa=new JSONArray();
			for(int i = 0;i<Metadata_up_List.size();i++){
				Map Metadata_up_map = Metadata_up_List.get(i);
				JSONObject val = new JSONObject();
				for (Object key : Metadata_up_map.keySet()) {
					val.put(key, Metadata_up_map.get(key));
				}
				jsa.add(val);
			}
			
			JSONObject json = new JSONObject();
			json.put("page", page);
			json.put("total", total);
			json.put("rows", jsa); //这里的 rows 和total 的key 是固定的 
			//System.out.println(json.toString());
			response.getWriter().write(json.toString());
		}
		
		
	}
	
	//执行sql语句列显示
	@RequestMapping("get_view_implement.do")
	public void get_view_implement(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String sql_val = request.getParameter("implement_sql").trim();
		String temp = sql_val.toLowerCase();//转成小写判断
		JSONObject result_jo = new JSONObject();
		
		if(temp.startsWith("select")){//判断是否是查询语句
			JSONArray jsa=new JSONArray();
			
			String col_str = sql_val.substring(7, temp.indexOf("from")).trim();//获取select与from之间的内容
			
			if(col_str.indexOf("*")>-1){//包含有星号，需要查询后循环获取
				
				try{
					String view_Adapter = "select * from ("+sql_val+") view_0_1 limit 0,1";//将取出的sql执行一遍，用返回的结果集获取列名
					List<Map> view_List = this.getBySqlMapper.findRecords(view_Adapter);
					if(view_List.size()>0){//结果集有数据
						Map view_map = view_List.get(0);
						for (Object key : view_map.keySet()) {
							JSONObject val = new JSONObject();
							val.put("align","center");//数据对齐方式
							val.put("halign","center");//标题对齐方式
							val.put("field",key);//列字段名
							val.put("title",key);//栏目标题文本
							jsa.add(val);
						}
					}else{//结果集没有数据
						result_jo.put("state", "2");
						result_jo.put("prompt", "还是空视图哦");
						response.getWriter().write(result_jo.toString());
						
					}
				}catch (Exception e){
					result_jo.put("state", "1");
					result_jo.put("error", "视图语句执行错误，请检查数据");
					response.getWriter().write(result_jo.toString());
					
				}
				
			}else{//不包含星号，说明写的实际列名
				if(col_str.indexOf(",")>-1){//包含逗号证明有多个列
					String temp2[] = col_str.split(",");
					String result = "";
					for(int j = 0;j<temp2.length;j++){
						JSONObject val = new JSONObject();
						val.put("align","center");//数据对齐方式
						val.put("halign","center");//标题对齐方式
						result = this.col_handle(temp2[j]);
						val.put("field",result);//列字段名
						val.put("title",result);//栏目标题文本
						jsa.add(val);
					}
				}else{//单个列
					String result = this.col_handle(col_str);
					JSONObject val = new JSONObject();
					val.put("align","center");//数据对齐方式
					val.put("halign","center");//标题对齐方式
					val.put("field",result);//列字段名
					val.put("title",result);//栏目标题文本
					jsa.add(val);
				}
			}
			
			result_jo.put("state", "0");
			result_jo.put("result", jsa);
			//System.out.println(result_jo.toString());
			response.getWriter().write(result_jo.toString());
			
		}else{//不是查询语句，返回错误
			result_jo.put("state", "1");
			result_jo.put("error", "视图语句错误，不是查询语句");
			response.getWriter().write(result_jo.toString());
			
		}
	}
	
	/**
	 * 
	 * @param str sql语句中查询的字段名称
	 * @return 经过处理的结果，获取最后显示的列名
	 */
	public static String col_handle(String str){
		
		String result = "";
		if(str.indexOf(" ")>-1){//有空格
			//多个空格替换为一个
			Pattern p = Pattern.compile("\\s+");
			Matcher m = p.matcher(str);
			String w= m.replaceAll(" ");
			result = w.substring(w.lastIndexOf(" ")+1);//截取最后一个空格到
		}else{//没有空格
			result = str;
		}
		
		if(result.indexOf(".")>-1){//点意味着多表关联查询，需要去掉点
			result = result.substring(result.lastIndexOf(".")+1);//取最后一个点到字符串结束
		}
		return result;
	}
	
	//保存sql语句
	@RequestMapping("save_view_sql.do")
	public void save_view_sql(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String sql_val = request.getParameter("sql_val").trim();
		String pkid = request.getParameter("pkid");
		String name_en = request.getParameter("name_en");
		
		//System.out.println(sql_val);
		
		//修改视图sql语句
		String up_Sql = "update metadata_view set sql_val='"+sql_val+"' where pkid="+pkid;

		
		//删除视图
		String structure_Sql = "drop view if exists "+name_en;

		
		//重新创建视图
		String update_sql = "CREATE VIEW "+name_en+" AS "+sql_val;

		try{
			this.getBySqlMapper.update(structure_Sql);
			this.getBySqlMapper.update(update_sql);
			this.getBySqlMapper.update(up_Sql);
			response.getWriter().write("1");
		}catch (Exception e){
			response.getWriter().write("0");
		}
		
		
	}
	
	/**
	 * 通过配置文件，获取数据库连接信息
	 * @param path
	 */
//	private void getProxool(String path) throws Exception{
//		File inputXml=new File(path+"WEB-INF"+File.separator+"proxool.xml");
//		Document document = new SAXReader().read(inputXml);
//		Element root=document.getRootElement();//获取文档的根节点
//		Element element=root.element("proxool");
//		Element driverurl=element.element("driver-url");
//		url = driverurl.getText();
//		int w = url.indexOf("?");
//		int j = url.lastIndexOf("/", w);
//		databaseName = url.substring(j+1, w);
//		Element driverclass=element.element("driver-class");
//		dclass = driverclass.getText();
//		Element driverproperties=element.element("driver-properties");
//		for(Iterator i = driverproperties.elementIterator(); i.hasNext();){
//			Element property = (Element) i.next();
//			Attribute attrName=property.attribute("name");
//			Attribute attrValue=property.attribute("value");
//			if(attrName.getText().equals("user")){
//				user = attrValue.getText();
//			}else if(attrName.getText().equals("password")){
//				password = attrValue.getText();
//			}
//		}
//	}
}
