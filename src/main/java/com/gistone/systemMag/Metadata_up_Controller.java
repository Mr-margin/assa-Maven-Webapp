package com.gistone.systemMag;

import java.io.File;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.gistone.MyBatis.config.GetBySqlMapper;

@RestController
@RequestMapping
public class Metadata_up_Controller extends MultiActionController{
//	@Autowired
//	private GetBySqlMapper getBySqlMapper;
//	
//	private String url;
//	private String databaseName;
//	private String dclass;
//	private String user;
//	private String password;
//	
//	//表格名称列表
//	@RequestMapping("getMetadata_up_table_List.do")
//	public void getMetadata_up_table_List(HttpServletRequest request,HttpServletResponse response) throws Exception{
//		
//		String pageSize = request.getParameter("pageSize");
//		String pageNumber = request.getParameter("pageNumber");
//		String search = "";
//		
//		if(request.getParameter("search")!=null&&!request.getParameter("search").equals("")){
//			search = request.getParameter("search").trim();
//		}
//		
//		int size = Integer.parseInt(pageSize);
//		int number = Integer.parseInt(pageNumber);
//		
//		int page = number == 0 ? 1 : (number/size)+1;
//		
//		String count_st_sql = "select count(*) from metadata_table where name_en like '%"+search+"%' or name_cn like '%"+search+"%'";
//
//		int total = this.getBySqlMapper.findrows(count_st_sql);
//		
//		
//		String Metadata_up_sql = "select * from metadata_table where (name_en like '%"+search+"%' or name_cn like '%"+search+"%') and pkid limit "+number+","+size;
//		List<Map> Metadata_up_List = this.getBySqlMapper.findRecords(Metadata_up_sql);
//		if(Metadata_up_List.size()>0){
//			JSONArray jsa=new JSONArray();
//			for(int i = 0;i<Metadata_up_List.size();i++){
//				Map Metadata_up_map = Metadata_up_List.get(i);
//				JSONObject val = new JSONObject();
//				for (Object key : Metadata_up_map.keySet()) {
//					val.put(key, Metadata_up_map.get(key));
//				}
//				jsa.add(val);
//			}
//			
//			JSONObject json = new JSONObject();
//			json.put("page", page);
//			json.put("total", total);
//			json.put("rows", jsa); //这里的 rows 和total 的key 是固定的 
//			  
//			//System.out.println(json.toString());
//			response.getWriter().write(json.toString());
//		}
//		
//		
//	}
//	
//	//新建数据表
//	@RequestMapping("addMetadata_up_table.do")
//	public void addMetadata_up_table(HttpServletRequest request,HttpServletResponse response) throws Exception{
//		
//		String form_val = request.getParameter("form_val");
//		JSONObject form_json = JSONObject.fromObject(form_val);//表单数据
//		
//		String title_name = "";
//		String val_name = "";
//		
//		String st_sql = "select * from metadata_table_st";
//		
//		List<Map> st_List = this.getBySqlMapper.findRecords(st_sql);
//		if(st_List.size()>0){
//			for(int i = 0;i<st_List.size();i++){
//				Map st_map = st_List.get(i);
//				if(!st_map.get("name_en").equals("pkid")){//主键列不在添加范围内
//					if(form_json.get("add_"+st_map.get("name_en"))!=null&&!form_json.get("add_"+st_map.get("name_en")).equals("")){
//						title_name += st_map.get("name_en")+",";
//						if(st_map.get("data_type").equals("int(11)")||st_map.get("data_type").equals("float(5,2)")){
//							val_name += form_json.get("add_"+st_map.get("name_en"))+",";
//						}else{
//							val_name += "'"+form_json.get("add_"+st_map.get("name_en"))+"',";
//						}
//					}
//				}
//			}
//		}
//		
//		if(title_name.length()>0&&val_name.length()>0){
//			title_name = title_name.substring(0,title_name.length()-1);
//			val_name = val_name.substring(0,val_name.length()-1);
//			
//			//新增数据表
//			String add_sql = "insert into metadata_table("+title_name+") values("+val_name+")";
//
//			
//			//建新表
//			String structure_Sql = "CREATE TABLE `"+form_json.get("add_name_en")+"` (`pkid` int(11) NOT NULL AUTO_INCREMENT, PRIMARY KEY (`pkid`)) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;";
//	
//			
//			//元数据表增加列
//			String insert_Sql = "insert into metadata_up(name_en,name_cn,data_type,table_name) values('pkid','序号','int(11)','"+form_json.get("add_name_en")+"');";
//
//			
//			try{
//				this.getBySqlMapper.insert(structure_Sql);
//				this.getBySqlMapper.insert(insert_Sql);
//				this.getBySqlMapper.insert(add_sql);
//				
//				response.getWriter().write("1");
//			}catch (Exception e){
//				response.getWriter().write("0");
//			}
//		}
//		
//		
//	}
//	
//	//修改数据表名称
//	@RequestMapping("upMetadata_up_table.do")
//	public void upMetadata_up_table(HttpServletRequest request,HttpServletResponse response) throws Exception{
//		
//		String pkid = request.getParameter("pkid");
//		String table_name = request.getParameter("table_name");
//		
//		String form_val = request.getParameter("form_val");
//		JSONObject form_json = JSONObject.fromObject(form_val);//表单数据
//		
//		String set_val = "";
//		
//		String st_sql = "select * from metadata_table_st";
//		List<Map> st_List = this.getBySqlMapper.findRecords(st_sql);
//		if(st_List.size()>0){
//			for(int i = 0;i<st_List.size();i++){
//				Map st_map = st_List.get(i);
//				
//				if(!st_map.get("name_en").equals("pkid")){
//					if(form_json.get("up_"+st_map.get("name_en"))!=null&&!form_json.get("up_"+st_map.get("name_en")).equals("")){
//						if(st_map.get("data_type").equals("int(11)")||st_map.get("data_type").equals("float(5,2)")){
//							set_val += st_map.get("name_en")+"="+form_json.get("up_"+st_map.get("name_en"))+",";
//						}else{
//							set_val += st_map.get("name_en")+"='"+form_json.get("up_"+st_map.get("name_en"))+"',";
//						}
//					}else{
//						set_val += st_map.get("name_en")+"=null,";
//					}
//				}
//			}
//		}
//		if(set_val.length()>0){
//			set_val = set_val.substring(0,set_val.length()-1);
//			
//			//修改数据表名称
//			String up_Sql = "update metadata_table set "+set_val+" where pkid="+pkid;
//
//			
//			//修改实际表名称
//			String structure_Sql = "alter table "+table_name+" rename "+form_json.get("up_name_en")+";";
//			
//			//修改元数据表中对应列的表名
//			String update_sql = "update metadata_up set table_name='"+form_json.get("up_name_en")+"' where table_name='"+table_name+"';";
//
//			
//			try{
//				this.getBySqlMapper.update(structure_Sql);
//				this.getBySqlMapper.update(update_sql);
//				this.getBySqlMapper.update(up_Sql);
//				
//				response.getWriter().write("1");
//			}catch (Exception e){
//				response.getWriter().write("0");
//			}
//		}
//		
//		
//	}
//	
//	//删除数据表
//	@RequestMapping("delMetadata_up_table.do")
//	public void delMetadata_up_table(HttpServletRequest request,HttpServletResponse response) throws Exception{
//		
//		String pkid = request.getParameter("pkid");
//		String table_name = request.getParameter("table_name");
//    	
//		//删除数据表名称
//		String up_Sql = "delete from metadata_table where pkid="+pkid;
//
//		
//		//删除实际表
//		String structure_Sql = "DROP TABLE "+table_name+";";
//
//		
//		//删除元数据表中对应的列记录
//		String del_sql = "delete from metadata_up where table_name='"+table_name+"';";
//
//		
//		try{
//			this.getBySqlMapper.delete(structure_Sql);
//			this.getBySqlMapper.delete(del_sql);
//			this.getBySqlMapper.delete(up_Sql);
//			response.getWriter().write("1");
//		}catch (Exception e){
//			response.getWriter().write("0");
//		}
//		
//		
//	}
//	
//	//单个数据表内容
//	@RequestMapping("getMetadata_up_col_List.do")
//	public void getMetadata_up_col_List(HttpServletRequest request,HttpServletResponse response) throws Exception{
//		
//		String pageSize = request.getParameter("pageSize");
//		String pageNumber = request.getParameter("pageNumber");
//		String table_name = request.getParameter("table_name");
//		
//		int size = Integer.parseInt(pageSize);//页面大小
//		int number = Integer.parseInt(pageNumber);//当前记录的开始
//		
//		String count_st_sql = "select count(*) from metadata_up where table_name='"+table_name+"'";
//		int total = this.getBySqlMapper.findrows(count_st_sql);
//		
//		if(number>=total){//如果前台要显示的记录数大于或等于总记录数，说明超出了分页范围，需要
//			if(total%size==0){//如果总记录数对页面大小取余为0，需要在总记录数的基础上减一页
//				number = total-size;
//			}else{//如果有余数，用总数减去余数，相当于最后一页
//				number = total-(total%size);
//			}
//		}
//		
//		int page = number == 0 ? 1 : (number/size)+1;
//		
//		String Metadata_up_sql = "select * from metadata_up where table_name='"+table_name+"' and pkid limit "+number+","+size;
//		List<Map> Metadata_up_List = this.getBySqlMapper.findRecords(Metadata_up_sql);
//		if(Metadata_up_List.size()>0){
//			JSONArray jsa=new JSONArray();
//			for(int i = 0;i<Metadata_up_List.size();i++){
//				Map Metadata_up_map = Metadata_up_List.get(i);
//				JSONObject val = new JSONObject();
//				for (Object key : Metadata_up_map.keySet()) {
//					val.put(key, Metadata_up_map.get(key));
//				}
//				jsa.add(val);
//			}
//			
//			JSONObject json = new JSONObject();
//			json.put("page", page);
//			json.put("total", total);
//			json.put("rows", jsa); //这里的 rows 和total 的key 是固定的 
//			  
//			//System.out.println(json.toString());
//			response.getWriter().write(json.toString());
//		}
//		
//		
//	}
//	
//	//新建列
//	@RequestMapping("addMetadata_up_col.do")
//	public void addMetadata_up_col(HttpServletRequest request,HttpServletResponse response) throws Exception{
//		
//		String form_val = request.getParameter("form_val");
//		String table_name = request.getParameter("table_name");
//		
//		JSONObject form_json = JSONObject.fromObject(form_val);//表单数据
//		form_json.put("add_table_name_col", table_name);//属于哪张表格
//		
//		String title_name = "";
//		String val_name = "";
//		
//		String st_sql = "select * from metadata_st";
//		List<Map> st_List = this.getBySqlMapper.findRecords(st_sql);
//		if(st_List.size()>0){
//			for(int i = 0;i<st_List.size();i++){
//				Map st_map = st_List.get(i);
//				
//				if(!st_map.get("name_en").equals("pkid")){
//					if(form_json.get("add_"+st_map.get("name_en")+"_col")!=null&&!form_json.get("add_"+st_map.get("name_en")+"_col").equals("")){
//						title_name += st_map.get("name_en")+",";
//						if(st_map.get("data_type").equals("int(11)")||st_map.get("data_type").equals("float(5,2)")){
//							val_name += form_json.get("add_"+st_map.get("name_en")+"_col")+",";
//						}else{
//							val_name += "'"+form_json.get("add_"+st_map.get("name_en")+"_col")+"',";
//						}
//					}
//				}
//			}
//		}
//		if(title_name.length()>0&&val_name.length()>0){
//			title_name = title_name.substring(0,title_name.length()-1);
//			val_name = val_name.substring(0,val_name.length()-1);
//			
//			//新增数据表
//			String add_sql = "insert into metadata_up("+title_name+") values("+val_name+")";
//			//System.out.println(add_sql);
//
//			
//			//修改实际数据表
//			String structure_Sql = "alter table "+table_name+" add "+form_json.get("add_name_en_col")+" "+form_json.get("add_data_type_col")+";";
//			//System.out.println(structure_Sql);
//
//			
//			try{
//				this.getBySqlMapper.insert(structure_Sql);
//				this.getBySqlMapper.insert(add_sql);
//				
//				response.getWriter().write("1");
//			}catch (Exception e){
//				response.getWriter().write("0");
//			}
//		}
//		
//	}
//	
//	//修改列
//	@RequestMapping("upMetadata_up_col.do")
//	public void upMetadata_up_col(HttpServletRequest request,HttpServletResponse response) throws Exception{
//		
//		String form_val = request.getParameter("form_val");
//		String pkid = request.getParameter("pkid");
//		String col_name = request.getParameter("col_name");
//		String table_name = request.getParameter("table_name");
//		
//		JSONObject form_json = JSONObject.fromObject(form_val);//表单数据
//		form_json.put("up_table_name_col", table_name);//属于哪张表格
//		
//		String set_val = "";
//		
//		String st_sql = "select * from metadata_st";
//
//		List<Map> st_List = this.getBySqlMapper.findRecords(st_sql);
//		if(st_List.size()>0){
//			for(int i = 0;i<st_List.size();i++){
//				Map st_map = st_List.get(i);
//				
//				if(!st_map.get("name_en").equals("pkid")){
//					if(form_json.get("up_"+st_map.get("name_en")+"_col")!=null&&!form_json.get("up_"+st_map.get("name_en")+"_col").equals("")){
//						if(st_map.get("data_type").equals("int(11)")||st_map.get("data_type").equals("float(5,2)")){
//							set_val += st_map.get("name_en")+"="+form_json.get("up_"+st_map.get("name_en")+"_col")+",";
//						}else{
//							set_val += st_map.get("name_en")+"='"+form_json.get("up_"+st_map.get("name_en")+"_col")+"',";
//						}
//					}else{
//						set_val += st_map.get("name_en")+"=null,";
//					}
//				}
//			}
//		}
//		if(set_val.length()>0){
//			set_val = set_val.substring(0,set_val.length()-1);
//			
//			//新增数据表
//			String up_sql = "update metadata_up set "+set_val+" where pkid="+pkid;
//			
//			//修改实际数据表
//			String structure_Sql = "alter table "+table_name+" change "+col_name+" "+form_json.get("up_name_en_col")+" "+form_json.get("up_data_type_col")+";";
//
//			
//			try{
//				this.getBySqlMapper.insert(structure_Sql);
//				this.getBySqlMapper.insert(up_sql);
//				
//				response.getWriter().write("1");
//			}catch (Exception e){
//				response.getWriter().write("0");
//			}
//		}
//		
//		
//	}
//	
//	//删除列
//	@RequestMapping("delMetadata_up_col.do")
//	public void delMetadata_up_col(HttpServletRequest request,HttpServletResponse response) throws Exception{
//		
//		String pkid = request.getParameter("pkid");
//		String col_name = request.getParameter("col_name");
//		String table_name = request.getParameter("table_name");
//		
//		if(!col_name.equals("pkid")){
//			String up_Sql = "delete from metadata_up where pkid="+pkid;
//
//			
//			String structure_Sql = "alter table "+table_name+" drop "+col_name+";";
//			
//			
//			try{
//				this.getBySqlMapper.delete(structure_Sql);
//				this.getBySqlMapper.delete(up_Sql);
//				response.getWriter().write("1");
//			}catch (Exception e){
//				response.getWriter().write("0");
//			}
//		}else{
//			response.getWriter().write("0");
//		}
//		
//		
//	}
//	
//	//获取元数据表应该有哪些列
//	@RequestMapping("getMetadata_up_List.do")
//	public void getMetadata_up_List(HttpServletRequest request,HttpServletResponse response) throws Exception{
//		
//		String Metadata_st_sql = "select * from metadata_st";
//		List<Map> Metadata_st_List = this.getBySqlMapper.findRecords(Metadata_st_sql);
//		if(Metadata_st_List.size()>0){
//			JSONArray jsa=new JSONArray();
//			for(int i = 0;i<Metadata_st_List.size();i++){
//				Map Metadata_st_map = Metadata_st_List.get(i);
//				JSONObject val = new JSONObject();
//				
//				if(Metadata_st_map.get("name_en").equals("name_cn")){
//					val.put("align","left");//数据对齐方式
//				}else{
//					val.put("align","center");//数据对齐方式
//				}
//				val.put("field",Metadata_st_map.get("name_en"));//列字段名
//				val.put("title",Metadata_st_map.get("name_cn"));//栏目标题文本
//				val.put("halign","center");//标题对齐方式
//				val.put("titleTooltip",Metadata_st_map.get("name_en"));
//				val.put("data_type",Metadata_st_map.get("data_type"));
//				
//				jsa.add(val);
//			}
//			  
//			//System.out.println(jsa.toString());
//			response.getWriter().write(jsa.toString());
//		}
//		
//		
//	}
//	
//	
//	//获取元数据表应该有哪些列
//	@RequestMapping("getMetadata_table_List.do")
//	public void getMetadata_table_List(HttpServletRequest request,HttpServletResponse response) throws Exception{
//			
//		String Metadata_st_sql = "select * from metadata_table_st";
//
//		List<Map> Metadata_st_List = this.getBySqlMapper.findRecords(Metadata_st_sql);
//		if(Metadata_st_List.size()>0){
//			JSONArray jsa=new JSONArray();
//			for(int i = 0;i<Metadata_st_List.size();i++){
//				Map Metadata_st_map = Metadata_st_List.get(i);
//				JSONObject val = new JSONObject();
//				
//				if(Metadata_st_map.get("name_en").equals("name_cn")){
//					val.put("align","left");//数据对齐方式
//				}else{
//					val.put("align","center");//数据对齐方式
//				}
//				val.put("field",Metadata_st_map.get("name_en"));//列字段名
//				val.put("title",Metadata_st_map.get("name_cn"));//栏目标题文本
//				val.put("halign","center");//标题对齐方式
//				val.put("titleTooltip",Metadata_st_map.get("name_en"));
//				val.put("data_type",Metadata_st_map.get("data_type"));
//				
//				jsa.add(val);
//			}
//			  
//			//System.out.println(jsa.toString());
//			response.getWriter().write(jsa.toString());
//		}
//		
//		
//	}
//	
//	//检测收到导入的新表
//	@RequestMapping("getRetrieval_table.do")
//	public void getRetrieval_table(HttpServletRequest request,HttpServletResponse response) throws Exception{
//		
////        try{
////        	//获取绝对路径
////    		ServletContext context = getServletContext();
////            String path = context.getRealPath("/");
////            if (!path.endsWith(java.io.File.separator)) {
////            	path = path + java.io.File.separator;
////            }
////            getProxool(path);
////		}catch (Exception e){
////			response.getWriter().write("err");
////			
////		}
//		
//		String retrieval_table = "select b.* from (select *,'"+databaseName+"' table_schema from metadata_table) a RIGHT JOIN";
//		retrieval_table += "(select table_name,table_schema,table_type from information_schema.tables where table_schema='"+databaseName+"' and table_type='base table' and table_name not like 'm%') b ";
//		retrieval_table += "on a.name_en=b.table_name and a.table_schema=b.table_schema where a.name_en is null";
//		
//
//		List<Map> Retrieval_table_List = this.getBySqlMapper.findRecords(retrieval_table);
//		if(Retrieval_table_List.size()>0){
//			for(int i = 0;i<Retrieval_table_List.size();i++){
//				Map Retrieval_table_map = Retrieval_table_List.get(i);
//				String insert_sql = "insert into metadata_table(name_en) values('"+Retrieval_table_map.get("table_name")+"')";
//				try{
//					this.getBySqlMapper.insert(insert_sql);
//				}catch (Exception e){
//					response.getWriter().write("err");
//					
//				}
//				String column_sql = "select column_name,column_type,column_key,extra from information_schema.columns where table_schema='"+databaseName+"' and table_name='"+Retrieval_table_map.get("table_name")+"' ORDER BY ordinal_position";
//
//				List<Map> rcolumn_List = this.getBySqlMapper.findRecords(column_sql);
//				String insert_rcolumn_Sql = "insert into metadata_up(name_en,name_cn,data_type,table_name) values";
//				for(int j = 0;j<rcolumn_List.size();j++){
//					Map rcolumn_map = rcolumn_List.get(j);
//					if(rcolumn_map.get("column_key").equals("PRI")){//是主键
//						
//						String structure_Sql = "alter table "+Retrieval_table_map.get("table_name")+" change "+rcolumn_map.get("column_name")+" pkid int(11) not null auto_increment";
//
//						try{
//							this.getBySqlMapper.insert(structure_Sql);
//						}catch (Exception e){
//							response.getWriter().write("err");
//							
//						}
//						insert_rcolumn_Sql += "('pkid','序号','int(11)','"+Retrieval_table_map.get("table_name")+"'),";
//						
//					}else{
//						insert_rcolumn_Sql += "('"+rcolumn_map.get("column_name")+"',' ','"+rcolumn_map.get("column_type")+"','"+Retrieval_table_map.get("table_name")+"'),";
//					}
//					
//				}
//				insert_rcolumn_Sql = insert_rcolumn_Sql.substring(0,insert_rcolumn_Sql.length()-1);
//				try{
//					this.getBySqlMapper.insert(insert_rcolumn_Sql);
//				}catch (Exception e){
//					response.getWriter().write("err");
//					
//				}
//			}
//			response.getWriter().write(Retrieval_table_List.size()+"");
//			
//		}else{
//			response.getWriter().write("0");
//			
//		}
//	}
//	
//	
//	/**
//	 * 通过配置文件，获取数据库连接信息
//	 * @param path
//	 */
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
