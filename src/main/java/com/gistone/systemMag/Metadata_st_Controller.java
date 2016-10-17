package com.gistone.systemMag;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

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
public class Metadata_st_Controller extends MultiActionController{
	@Autowired
	private GetBySqlMapper getBySqlMapper;
	
	@RequestMapping("getMetadata_st_List.do")
	public void getMetadata_st_List(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String pageSize = request.getParameter("pageSize");
		String pageNumber = request.getParameter("pageNumber");
		String search = "";
		
		if(request.getParameter("search")!=null&&!request.getParameter("search").equals("")){
			search = request.getParameter("search").trim();
		}
		
		int size = Integer.parseInt(pageSize);
		int number = Integer.parseInt(pageNumber);
		
		int page = number == 0 ? 1 : (number/size)+1;
		
		System.out.println("size="+size+";number="+number+";page="+page);
		
		String count_st_sql = "select count(*) from metadata_st where name_en like '%"+search+"%' or name_cn like '%"+search+"%' or data_type like '%"+search+"%'";
		int total = this.getBySqlMapper.findrows(count_st_sql);
		
		
		String Metadata_st_sql = "select * from metadata_st where (name_en like '%"+search+"%' or name_cn like '%"+search+"%' or data_type like '%"+search+"%') and pkid limit "+number+","+size;
		List<Map> Metadata_st_List = this.getBySqlMapper.findRecords(Metadata_st_sql);
		if(Metadata_st_List.size()>0){
			JSONArray jsa=new JSONArray();
			for(int i = 0;i<Metadata_st_List.size();i++){
				Map Metadata_st_map = Metadata_st_List.get(i);
				JSONObject val = new JSONObject();
				for (Object key : Metadata_st_map.keySet()) {
					val.put(key, Metadata_st_map.get(key));
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
	@RequestMapping("addMetadata_st.do")
	public void addMetadata_st(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String name_en = request.getParameter("name_en").trim();
		String name_cn = request.getParameter("name_cn").trim();
		String data_type = request.getParameter("data_type");
    	
		String add_Sql = "insert into metadata_st(name_en,name_cn,data_type) values('"+name_en+"','"+name_cn+"','"+data_type+"')";
		
		String structure_Sql = "alter table metadata_up add "+name_en+" "+data_type+";";
		
		try{
			this.getBySqlMapper.insert(structure_Sql);
			this.getBySqlMapper.insert(add_Sql);
			
			response.getWriter().write("1");
		}catch (Exception e){
			response.getWriter().write("0");
		}
		
	}
	
	@RequestMapping("upMetadata_st.do")
	public void upMetadata_st(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String pkid = request.getParameter("pkid");
    	String name_en = request.getParameter("name_en").trim();
		String name_cn = request.getParameter("name_cn").trim();
		String data_type = request.getParameter("data_type");
		String table_name = request.getParameter("table_name");
    	
		String up_Sql = "update metadata_st set name_en='"+name_en+"',name_cn='"+name_cn+"',data_type='"+data_type+"' where pkid="+pkid;

		
//		String select_Sql = "select * from metadata_st where pkid="+pkid;
//		SQLAdapter select_st_Adapter = new SQLAdapter(select_Sql);
//		List<Map> select_st_List = this.getBySqlMapper.findRecords(select_st_Adapter);
//		String old_field_name = "";
//		if(select_st_List.size()>0){
//			Map select_st_map = select_st_List.get(0);
//			old_field_name = select_st_map.get("name_en").toString();
//		}
			
		String structure_Sql = "alter table metadata_up change "+table_name+" "+name_en+" "+data_type+";";
		
		
		try{
			this.getBySqlMapper.update(structure_Sql);
			this.getBySqlMapper.update(up_Sql);
			response.getWriter().write("1");
		}catch (Exception e){
			response.getWriter().write("0");
		}
		
	}
	@RequestMapping("delMetadata_st.do")
	public void delMetadata_st(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String pkid = request.getParameter("pkid");
		String table_name = request.getParameter("table_name");
    	
//		String select_Sql = "select * from metadata_st where pkid="+pkid;
//		SQLAdapter select_st_Adapter = new SQLAdapter(select_Sql);
//		List<Map> select_st_List = this.getBySqlMapper.findRecords(select_st_Adapter);
//		String old_field_name = "";
//		if(select_st_List.size()>0){
//			Map select_st_map = select_st_List.get(0);
//			old_field_name = select_st_map.get("name_en").toString();
//		}
		
		String up_Sql = "delete from metadata_st where pkid="+pkid;

		
		String structure_Sql = "alter table metadata_up drop "+table_name+";";

		
		try{
			this.getBySqlMapper.delete(structure_Sql);
			this.getBySqlMapper.delete(up_Sql);
			response.getWriter().write("1");
		}catch (Exception e){
			response.getWriter().write("0");
		}
		
	}
	
	
	
	
	@RequestMapping("getMetadata_List.do")
	public void getMetadata_List(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String pageSize = request.getParameter("pageSize");
		String pageNumber = request.getParameter("pageNumber");
		String search = "";
		
		if(request.getParameter("search")!=null&&!request.getParameter("search").equals("")){
			search = request.getParameter("search").trim();
		}
		
		int size = Integer.parseInt(pageSize);
		int number = Integer.parseInt(pageNumber);
		
		int page = number == 0 ? 1 : (number/size)+1;
		
		String count_st_sql = "select count(*) from metadata_table_st where name_en like '%"+search+"%' or name_cn like '%"+search+"%' or data_type like '%"+search+"%'";

		int total = this.getBySqlMapper.findrows(count_st_sql);
		//System.out.println(count_st_sql);
		
		String Metadata_st_sql = "select * from metadata_table_st where (name_en like '%"+search+"%' or name_cn like '%"+search+"%' or data_type like '%"+search+"%') and pkid limit "+number+","+size;

		List<Map> Metadata_st_List = this.getBySqlMapper.findRecords(Metadata_st_sql);
		if(Metadata_st_List.size()>0){
			JSONArray jsa=new JSONArray();
			for(int i = 0;i<Metadata_st_List.size();i++){
				Map Metadata_st_map = Metadata_st_List.get(i);
				JSONObject val = new JSONObject();
				for (Object key : Metadata_st_map.keySet()) {
					val.put(key, Metadata_st_map.get(key));
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
	@RequestMapping("addMetadata.do")
	public void addMetadata(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String name_en = request.getParameter("name_en").trim();
		String name_cn = request.getParameter("name_cn").trim();
		String data_type = request.getParameter("data_type");
    	
		String add_Sql = "insert into metadata_table_st(name_en,name_cn,data_type) values('"+name_en+"','"+name_cn+"','"+data_type+"')";
		
		String structure_Sql = "alter table metadata_table add "+name_en+" "+data_type+";";
		try{
			this.getBySqlMapper.insert(structure_Sql);
			this.getBySqlMapper.insert(add_Sql);
			
			response.getWriter().write("1");
		}catch (Exception e){
			response.getWriter().write("0");
		}
		
	}
	
	@RequestMapping("upMetadata.do")
	public void upMetadata(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String pkid = request.getParameter("pkid");
    	String name_en = request.getParameter("name_en").trim();
		String name_cn = request.getParameter("name_cn").trim();
		String data_type = request.getParameter("data_type");
		String table_name = request.getParameter("table_name");
    	
		String up_Sql = "update metadata_table_st set name_en='"+name_en+"',name_cn='"+name_cn+"',data_type='"+data_type+"' where pkid="+pkid;

		
		String structure_Sql = "alter table metadata_table change "+table_name+" "+name_en+" "+data_type+";";

		
		try{
			this.getBySqlMapper.update(structure_Sql);
			this.getBySqlMapper.update(up_Sql);
			response.getWriter().write("1");
		}catch (Exception e){
			response.getWriter().write("0");
		}
		
	}
	@RequestMapping("delMetadata.do")
	public void delMetadata(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String pkid = request.getParameter("pkid");
		String table_name = request.getParameter("table_name");
    	
		String up_Sql = "delete from metadata_table_st where pkid="+pkid;
		
		
		String structure_Sql = "alter table metadata_table drop "+table_name+";";

		
		try{
			this.getBySqlMapper.delete(structure_Sql);
			this.getBySqlMapper.delete(up_Sql);
			response.getWriter().write("1");
		}catch (Exception e){
			response.getWriter().write("0");
		}
		
	}
}
