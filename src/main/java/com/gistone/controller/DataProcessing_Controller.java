package com.gistone.controller;

import java.io.File;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;

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

import com.gistone.MyBatis.config.GetBySqlMapper;
import com.gistone.util.Tool;

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

@RestController
@RequestMapping
public class DataProcessing_Controller{
	@Autowired
	private GetBySqlMapper getBySqlMapper;
	
	
	//录入帮扶人与贫困户关系数据
	@RequestMapping("sys_personal_household_many.do")
	public void sys_personal_household_many(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String sql_3 = "select * from da_household";
		List<Map> Metadata_st_List = this.getBySqlMapper.findRecords(sql_3);
		for(int i = 0;i<Metadata_st_List.size();i++){
			Map st_map = Metadata_st_List.get(i);
			
			String sql = "select count(*) from (select v9 from da_household where pkid="+st_map.get("pkid")+" union ALL select v9 from da_member where da_household_id="+st_map.get("pkid")+") t1";
			int total = this.getBySqlMapper.findrows(sql);
			System.out.println(sql);
			
			String sql_1 = "update da_household set v9='"+total+"' where pkid="+st_map.get("pkid");
			String sql_2 = "update da_member set v9='"+total+"' where da_household_id="+st_map.get("pkid");
			
			try{
				this.getBySqlMapper.insert(sql_1);
				this.getBySqlMapper.insert(sql_2);
			}catch (Exception e){
				
			}
		}
		System.out.println("完成");
	}
	
	/**
	 * 
	 * @param path 文件路径
	 * @param listmap 要写入的数据
	 */
	public void getExportAll(String path, List<Map> listmap){
		try{
			// 打开文件
            WritableWorkbook book = Workbook.createWorkbook( new File(path));
            //生成名为“第一页”的工作表，参数0表示这是第一页
            WritableSheet sheet = book.createSheet( " 第一页 " , 0 );
            WritableFont font2 =new WritableFont(WritableFont.createFont("微软雅黑"), 9 ,WritableFont.NO_BOLD);
            WritableCellFormat wcf3 = new WritableCellFormat(font2);//正文样式
            wcf3.setAlignment(Alignment.LEFT);  //左对齐
            wcf3.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中
            wcf3.setBackground(Colour.BLACK);//设置背景颜色
            wcf3.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
            wcf3.setWrap(true);//自动换行
            //写入数据并关闭文件
            book.write();
            book.close();
			System.out.println("ok");
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	//录入贫困户数据
	@RequestMapping("xls_db_house.do")
	public void xls_db_house(HttpServletRequest request,HttpServletResponse response) throws Exception{
		// 文件保存目录路径  
        String savePath = request.getServletContext().getRealPath("/")+ "orig_data/20160421/市标/乌审旗/1/";
        getAllFile(savePath);
        System.out.println("完成");
	}
	
	/**
	 * 读取文件夹里面的所有文件
	 * @param path String 文件夹路径 如 c:/fqf
	 */
	public void getAllFile(String path) {
		File file = new File(path);//根文件夹
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {//循环根文件夹下所有内容
			
			if (path.endsWith(File.separator)) {//判断path是否以系统指定的分隔符作为结束，兼容多种操作系统
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			
			if (temp.isFile()) {//如果是文件
				//System.out.println(temp.getPath()+"-----"+tempList[i]);
				//文件内容处理
				//get_Company(temp.getPath(), tempList[i], temp.getParentFile());//处理行政区划
				get_household(temp.getPath(), tempList[i], temp.getParentFile());
			}
			if (temp.isDirectory()) {//如果是文件夹，进行递归
				getAllFile(temp.getPath());
			}
		}
	}
	
	
	/**
	 * 先处理行政区划，将所有行政区划入库
	 * @param path 文件路径
	 * @param filename 文件名
	 * @param Parent 父文件名
	 */
	public void get_Company(String path, String filename, File Parent){
		filename = filename.substring(0,filename.indexOf("."));
		String sql = "select * from sys_company where com_level=3 and com_name='"+filename+"'";
		List<Map> Metadata_st_List = this.getBySqlMapper.findRecords(sql);
		if(Metadata_st_List.size()>0){
			Map Metadata_st_map = Metadata_st_List.get(0);
			
			Map<String, String> huzhuname = new HashMap();
			try{
				InputStream is = new FileInputStream(path);
				Workbook wb = Workbook.getWorkbook(is);
				
				Sheet st = wb.getSheet(0);//取第一张工作表
				Cell[] myCells;
				
				//循环行Row
				for (int rowNum = 2; rowNum < st.getRows(); rowNum++) {//从第三行开始循环
					myCells = st.getRow(rowNum);
					if(myCells.length == 0){
				          continue;
			    	}
					
					huzhuname.put(myCells[4].getContents().trim(), myCells[4].getContents().trim());
				}
				
				for(String key : huzhuname.keySet()){
					String add_sql = "insert into sys_company(com_name,com_level,com_f_pkid) values('"+key+"',4,"+Metadata_st_map.get("pkid")+")";
					this.getBySqlMapper.insert(add_sql);
				}
				
				
			}catch(Exception e){
				System.out.println("出现错误---"+Parent.getName()+"----"+filename+"----");
			}
		}else{
			System.out.println("出现未录制的行政区划---"+Parent.getName()+"----"+filename+"----");
		}
	}
	
	
	
	/**
	 * 第一遍先录入户主信息
	 * @param path
	 * @param filename
	 * @param Parent
	 */
	public void get_household(String path, String filename, File Parent){
		
		Map<String, String> cun_name = new HashMap();
		
		//按照文件名读取所有村列表，其实没啥用
		filename = filename.substring(0,filename.indexOf("."));
		String sql = "select y.* from sys_company x, sys_company y where x.pkid=y.com_f_pkid and x.com_level=3 and x.com_name='"+filename+"'";
		List<Map> Metadata_st_List = this.getBySqlMapper.findRecords(sql);
		if(Metadata_st_List.size()>0){
			for(int i = 0;i<Metadata_st_List.size();i++){
				Map Metadata_st_map = Metadata_st_List.get(i);
				cun_name.put(Metadata_st_map.get("com_name").toString(), Metadata_st_map.get("pkid").toString());
			}
		}else{
			System.out.println("出现未录制的行政区划11---"+Parent.getName()+"----"+filename+"----");
		}
		
		//读取文件导入数据库
		Map<String, String> huzhuname = new HashMap();
		try{
			InputStream is = new FileInputStream(path);
			Workbook wb = Workbook.getWorkbook(is);

			Sheet st = wb.getSheet(0);//取第一张工作表
			Cell[] myCells;

			int number = 0;//每户的人数
			int da_household_id = 0;
			int jiatingrenshu = 0;
			//循环行Row
			for (int rowNum = 2; rowNum < st.getRows(); rowNum++) {//从第三行开始循环
				myCells = st.getRow(rowNum);
				if(myCells.length == 0){
					continue;
				}
				
				String values = "";
				for(int cellNum = 0; cellNum < 27; cellNum++){
					if(myCells[cellNum]==null){
						continue;
					}
					values += "'"+myCells[cellNum].getContents().trim()+"',";
				}
				
				if(cun_name.get(myCells[4].getContents().trim())==null){
					System.out.println("出现未录制的行政区划22---"+Parent.getName()+"----"+filename+"----"+myCells[4].getContents().trim());
				}
				
				if(myCells[9].getContents().trim().equals("户主")){
					//全新的一条，开始记录每户人数
					number = Integer.parseInt(myCells[8].getContents().trim());
					
					//System.out.println(myCells[9].getContents().trim()+"      "+myCells[5].getContents().trim()+"    "+myCells[8].getContents().trim());
					values += "'国家级贫困人口'";
					//国家级贫困人口
					//市级低收入人口
					
					//加数据库
					String add_sql = "insert into da_household(v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12,v13,v14,v15,v16,v17,v18,v19,v20,v21,v22,v23,v24,v25,v26,v27,sys_standard) "
							+ "values("+values+")";
					if(add_sql.indexOf("高四")>-1){
//						System.out.println(add_sql);
					}
					jiatingrenshu = 1;//记录家庭人数
				}else{
					jiatingrenshu++;
					
					if(jiatingrenshu<=number){//算上当前的人数，家庭人数应该比记录的人口数少或者等于
						//System.out.println(myCells[9].getContents().trim()+"      "+myCells[5].getContents().trim()+"    "+myCells[8].getContents().trim());
						//加数据库
						values += "'"+da_household_id+"'";
						String add_sql = "insert into da_member(v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12,v13,v14,v15,v16,v17,v18,v19,v20,v21,v22,v23,v24,v25,v26,v27,da_household_id) "
								+ "values("+values+")";
//						this.getBySqlMapper.insertSelective(Metadata_table_Adapter);
						
					}else{
						System.out.println("错误---"+Parent.getName()+"----"+filename+"----"+myCells[4].getContents().trim()+"----"+myCells[5].getContents().trim());
					}
				}

			}
		}catch(Exception e){
			System.out.println("出现错误---"+Parent.getName()+"----"+filename+"----");
			e.printStackTrace();
		}
	}
	
}
