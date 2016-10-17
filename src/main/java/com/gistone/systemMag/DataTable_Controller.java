package com.gistone.systemMag;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
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
public class DataTable_Controller{
	@Autowired
	private GetBySqlMapper getBySqlMapper;
	
	private String url;
	private String databaseName;
	private String dclass;
	private String user;
	private String password;
	
	///数据表列
	@RequestMapping("getCol_List.do")
	public void getCol_List(HttpServletRequest request,HttpServletResponse response) throws Exception{

		String table_name = request.getParameter("table_name");
		
		String Metadata_st_sql = "select * from metadata_up where table_name='"+table_name+"'";
		List<Map> Metadata_st_List = this.getBySqlMapper.findRecords(Metadata_st_sql);
		if(Metadata_st_List.size()>0){
			JSONArray jsa=new JSONArray();
			for(int i = 0;i<Metadata_st_List.size();i++){
				Map Metadata_st_map = Metadata_st_List.get(i);
				JSONObject val = new JSONObject();
				
				if(i>10){
					val.put("visible","false");//不隐藏列，设置为false默认不显示
				}
				
				val.put("align","center");//数据对齐方式
				val.put("field",Metadata_st_map.get("name_en"));//列字段名
				val.put("title",Metadata_st_map.get("name_cn"));//栏目标题文本
				val.put("halign","center");//标题对齐方式
				val.put("titleTooltip",Metadata_st_map.get("name_en"));
				val.put("data_type",Metadata_st_map.get("data_type"));
				
				jsa.add(val);
			}
			  
			//System.out.println(jsa.toString());
			response.getWriter().write(jsa.toString());
		}
	}
	
	
	//数据表数据
	@RequestMapping("getData_List.do")
	public void getData_List(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String pageSize = request.getParameter("pageSize");
		String pageNumber = request.getParameter("pageNumber");
		String table_name = request.getParameter("table_name");
		String search = "";
		
		if(request.getParameter("search")!=null&&!request.getParameter("search").equals("")){
			search = request.getParameter("search").trim();
		}
		
		//查询原有名称
		String Metadata_st_sql = "select * from metadata_up where table_name='"+table_name+"'";
		List<Map> select_table_List = this.getBySqlMapper.findRecords(Metadata_st_sql);
		String col_name = "";
		if(select_table_List.size()>0){
			for(int i = 0;i<select_table_List.size();i++){
				Map select_table_map = select_table_List.get(i);
				col_name += select_table_map.get("name_en").toString()+" like '%"+search+"%' or ";
			}
		}
		
		int size = Integer.parseInt(pageSize);
		int number = Integer.parseInt(pageNumber);
		
		int page = number == 0 ? 1 : (number/size)+1;
		
		col_name = col_name.substring(0,col_name.length()-4);
		String count_st_sql = "select count(*) from "+table_name+" where "+col_name;
		int total = this.getBySqlMapper.findrows(count_st_sql);
		
		String Metadata_up_sql = "select * from "+table_name+" where ("+col_name+") and pkid limit "+number+","+size;
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
	
	
	//增加数据
	@RequestMapping("addData.do")
	public void addData(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String form_val = request.getParameter("form_val");//表单数据
		String table_name = request.getParameter("table_name");//属于哪张表格
		
		JSONObject form_json = JSONObject.fromObject(form_val);//表单数据
		
		String title_name = "";
		String val_name = "";
		
		String st_sql = "select * from metadata_up where table_name='"+table_name+"'";
		List<Map> st_List = this.getBySqlMapper.findRecords(st_sql);
		if(st_List.size()>0){
			for(int i = 0;i<st_List.size();i++){
				Map st_map = st_List.get(i);
				
				if(!st_map.get("name_en").equals("pkid")){
					if(form_json.get("add_"+st_map.get("name_en"))!=null&&!form_json.get("add_"+st_map.get("name_en")).equals("")){
						title_name += st_map.get("name_en")+",";
						if(st_map.get("data_type").equals("int(11)")||st_map.get("data_type").equals("float(5,2)")){
							val_name += form_json.get("add_"+st_map.get("name_en"))+",";
						}else{
							val_name += "'"+form_json.get("add_"+st_map.get("name_en"))+"',";
						}
					}
				}
			}
		}
		if(title_name.length()>0&&val_name.length()>0){
			title_name = title_name.substring(0,title_name.length()-1);
			val_name = val_name.substring(0,val_name.length()-1);
			
			//新增数据表
			String add_sql = "insert into "+table_name+"("+title_name+") values("+val_name+")";
			
			try{
				this.getBySqlMapper.insert(add_sql);
				response.getWriter().write("1");
			}catch (Exception e){
				response.getWriter().write("0");
			}
		}
		
	}


	//修改数据
	@RequestMapping("upData.do")
	public void upData(HttpServletRequest request,HttpServletResponse response) throws Exception{
	
		String form_val = request.getParameter("form_val");//表单数据
		String pkid = request.getParameter("pkid");
		String table_name = request.getParameter("table_name");//属于哪张表格
		
		JSONObject form_json = JSONObject.fromObject(form_val);//表单数据
		
		String set_val = "";
		
		String st_sql = "select * from metadata_up where table_name='"+table_name+"'";
		List<Map> st_List = this.getBySqlMapper.findRecords(st_sql);
		if(st_List.size()>0){
			for(int i = 0;i<st_List.size();i++){
				Map st_map = st_List.get(i);
				
				if(!st_map.get("name_en").equals("pkid")){
					
					if(form_json.get("up_"+st_map.get("name_en"))!=null&&!form_json.get("up_"+st_map.get("name_en")).equals("")){
						if(st_map.get("data_type").equals("int(11)")||st_map.get("data_type").equals("float(5,2)")){
							set_val += st_map.get("name_en")+"="+form_json.get("up_"+st_map.get("name_en"))+",";
						}else{
							set_val += st_map.get("name_en")+"='"+form_json.get("up_"+st_map.get("name_en"))+"',";
						}
					}else{
						if(st_map.get("data_type").equals("int(11)")||st_map.get("data_type").equals("float(5,2)")){
							set_val += st_map.get("name_en")+"=0,";
						}else{
							set_val += st_map.get("name_en")+"='',";
						}
					}
				}
			}
		}
		if(set_val.length()>0){
			set_val = set_val.substring(0,set_val.length()-1);
			
			//新增数据表
			String up_sql = "update "+table_name+" set "+set_val+" where pkid="+pkid;
			
			try{
				this.getBySqlMapper.insert(up_sql);
				response.getWriter().write("1");
			}catch (Exception e){
				response.getWriter().write("0");
			}
		}

	}


	//删除数据
	@RequestMapping("delData.do")
	public void delData(HttpServletRequest request,HttpServletResponse response) throws Exception{
	
		String pkid = request.getParameter("pkid");
		String table_name = request.getParameter("table_name");
		
		String up_Sql = "delete from "+table_name+" where pkid="+pkid;
		
		try{
			this.getBySqlMapper.delete(up_Sql);
			response.getWriter().write("1");
		}catch (Exception e){
			response.getWriter().write("0");
		}
		
		
	}
	

	/**
	 * 错误信息处理
	 * @param message 错误信息内容
	 * @return 返回错误信息JSONObject字符串
	 */
	@RequestMapping("getError.do")
	private String getError(String message){  
        JSONObject obj = new JSONObject();  
        obj.put("error", 1);  
        obj.put("message", message);  
        return obj.toString();  
    }
	
	@RequestMapping("ExportData.do")
	public void ExportData(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String table_name = request.getParameter("table_name");
		String name_cn = request.getParameter("name_cn");
		
		// 文件保存目录路径  
        String savePath = request.getServletContext().getRealPath("/")+ "Export/";  
        // 文件保存目录URL  
        String saveUrl = request.getContextPath() + "/Export/";
        // 检查目录  
        File uploadDir = new File(savePath);  
        if (!uploadDir.isDirectory()) {  
        	response.getWriter().write(getError("上传目录不存在。"));  
            
        }
        savePath += "excel/";  
        saveUrl += "excel/";  
        File saveDirFile = new File(savePath);  
        if (!saveDirFile.exists()) {  
            saveDirFile.mkdirs();  
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
        String ymd = sdf.format(new Date());  
        savePath += ymd + "/";  
        saveUrl += ymd + "/";  
        File dirFile = new File(savePath);  
        if (!dirFile.exists()) {  
            dirFile.mkdirs();  
        }
        String newFileName = name_cn + "_" + new Random().nextInt(1000) + ".xls";
        try{
			// 打开文件
            WritableWorkbook book = Workbook.createWorkbook(new File(savePath + newFileName));
            //生成名为“第一页”的工作表，参数0表示这是第一页
            WritableSheet sheet = book.createSheet( " 第一页 " , 0 );
            WritableFont font2 =new WritableFont(WritableFont.createFont("微软雅黑"), 9 ,WritableFont.NO_BOLD);
            WritableCellFormat wcf3 = new WritableCellFormat(font2);//正文样式
            wcf3.setAlignment(Alignment.LEFT);  //左对齐
            wcf3.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中
            wcf3.setBackground(Colour.BLACK);//设置背景颜色
            wcf3.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
            wcf3.setWrap(true);//自动换行
            
            //查询原有名称
    		String Metadata_st_sql = "select * from metadata_up where table_name='"+table_name+"'";
    		List<Map> select_table_List = this.getBySqlMapper.findRecords(Metadata_st_sql);
    		String col_name = "";
    		if(select_table_List.size()>0){
    			for(int i = 0;i<select_table_List.size();i++){
    				Map select_table_map = select_table_List.get(i);
    				sheet.addCell(new Label(i, 0, select_table_map.get("name_cn").toString(), wcf3));//文件的标题列
    				col_name += select_table_map.get("name_en").toString()+",";//sql语句的读取列
    			}
    			col_name = col_name.substring(0, col_name.length()-1);
    		}
            
    		String Metadata_up_sql = "select "+col_name+" from "+table_name;
    		List<Map> Metadata_up_List = this.getBySqlMapper.findRecords(Metadata_up_sql);
    		if(Metadata_up_List.size()>0){
    			 for(int i = 0;i<Metadata_up_List.size();i++){
    				 Map Metadata_up_map = Metadata_up_List.get(i);
    				 int k = 0;
    				 for (Object key : Metadata_up_map.keySet()) {
    					 sheet.addCell(new Label(k, i+1, Metadata_up_map.get(key).toString(), wcf3));
    					 k++;
    				 }
    			 }
    		}
            //写入数据并关闭文件
            book.write();
            book.close();
			JSONObject obj = new JSONObject();
			obj.put("error", 0);
			obj.put("url", saveUrl+newFileName);
			response.getWriter().write(obj.toString());
		}catch (Exception e){
			response.getWriter().write(getError("数据读取异常。"));
		}
		
	}
	
}
