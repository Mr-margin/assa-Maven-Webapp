package com.gistone.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gistone.MyBatis.config.GetBySqlMapper;

@RestController
@RequestMapping
public class Singleton {
	@Autowired
	private  GetBySqlMapper getBySqlMapper;
	
	/**
	 * @method 获取当前系统时间
	 * @return	time
	 * @author 张晓翔
	 */
	private static String getime(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String time = dateFormat.format(new Date()).replace("/", "").replace(" ", "-").replace(":", "");//获取当前系统时间time
		return time;
	}

	// 存储.xls名称
	private static ArrayList<String> filelist = new ArrayList<String>();
	// 存储  pkc 出错表的名称和错误的列
	private static ArrayList<String> filelist_err = new ArrayList<String>();
	// 存储 bfr 出错表的名称和错误的列
	private static ArrayList<String> filelist_err_2 = new ArrayList<String>();

	private ArrayList<String> getdata() {
		String filePath = "G://太仆寺旗数据";
		if(filelist.size()==0){
			getFiles(filePath);
		}
		return filelist;
	}

	// 通过递归得到某一路径下所有的.xls文件
	private static void getFiles(String filePath) {
		File root = new File(filePath);
		File[] files = root.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				// 递归调用
				getFiles(file.getAbsolutePath());
			} else {
				filelist.add(file.getAbsolutePath());
			}
		}
	}

	// 读取excel
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("readExcel.do")
	private void readExcel() throws BiffException, IOException {
		// 创建一个list 用来存储读取的内容
		List list = new ArrayList();
		Workbook rwb = null;
		Cell cell = null;
		String table_name = "";// 数据库表名
		String insert_z = "";// sql语句中数据库的列
		String insert_value = "";// 语句中value（）中的值
		String bfr_name = "";//帮扶人姓名
		String bfr_phone = "";//帮扶人电话
		String pkh_name = "";//贫困户名
		String pkh_card = "";//贫困户的证件号

		Singleton s = new Singleton();
		for (int a = 0; a < s.getdata().size(); a++) {
			//for (int a = 0; a < 5; a++) {
			table_name = "";
			insert_z = "";
			rwb=null;
			list.clear ();
			cell=null;
			
			bfr_name = "";//帮扶人姓名
			bfr_phone = "";//帮扶人电话
			pkh_name = "";//贫困户名
			pkh_card = "";//贫困户的证件号
			
			//依次获取表名
			String file_name = s.getdata().get(a);
			if (file_name.indexOf("人口基本信息") > -1) {
				table_name = "PKC_1_1_0";
				insert_z = "PKID,V10,V1,V2,V3,V3_1,V4,V4_1,V5,V5_1,V6,V6_1,V7,V7_1,V8,V8_1,V9";
			} else if (file_name.indexOf("人口年龄分组") > -1) {
				table_name = "PKC_1_1_1";
				insert_z = "PKID,V10,V1,V2,V3,V3_1,V4,V4_1,V5,V5_1,V6,V6_1,V7,V7_1,V8,V8_1,V9";
			} else if (file_name.indexOf("人口身体健康情况") > -1) {
				table_name = "PKC_1_1_2";
				insert_z = "PKID,V10,V1,V2,V3,V3_1,V4,V4_1,V5,V5_1,V6,V6_1,V9";
			} else if (file_name.indexOf("人口文化程度统计") > -1) {
				table_name = "PKC_1_1_3";
				insert_z = "PKID,V10,V1,V2,V3,V3_1,V4,V4_1,V5,V5_1,V6,V6_1,V7,V7_1,V8,V8_1,V9";
			} else if (file_name.indexOf("人口劳动能力类型") > -1) {
				table_name = "PKC_1_1_4";
				insert_z = "PKID,V10,V1,V2,V3,V3_1,V4,V4_1,V5,V5_1,V6,V6_1,V9";
			} else if (file_name.indexOf("人口上年度务工状况") > -1) {
				table_name = "PKC_1_1_5";
				insert_z = "PKID,V10,V1,V2,V3,V3_1,V4,V4_1,V5,V5_1,V6,V6_1,V7,V7_1,V9";
			} else if (file_name.indexOf("人口上年度务工时间") > -1) {
				table_name = "PKC_1_1_6";
				insert_z = "PKID,V10,V1,V2,V3,V3_1,V4,V4_1,V5,V5_1,V6,V6_1,V7,V7_1,V8,V8_1,V16,V16_1,V11,V11_1,V12,V12_1,V13,V13_1,V14,V14_1,V15,V15_1,V9";
			} else if (file_name.indexOf("人口在校生情况") > -1) {
				table_name = "PKC_1_1_7";
				insert_z = "PKID,V10,V1,V2,V3,V3_1,V4,V4_1,V5,V5_1,V6,V6_1,V7,V7_1,V8,V8_1,V20,V20_1,V11,V11_1,V12,V12_1,V13,V13_1,V14,V14_1,V15,V15_1,V16,V16_1,V17,V17_1,V18,V18_1,V19,V19_1,V9";
			} else if (file_name.indexOf("人口统计") > -1) {
				if (file_name.indexOf("建档立卡贫困村未脱贫") > -1) {
					table_name = "PKC_1_3_2";
					insert_z = "PKID,V10,VW1,VW2,VW3,VW4,VW41,VW5,VW51,VW6,VW61,VW7,VW71,COM_PIN";
				}else{
					table_name = "PKC_1_1_8";
					insert_z = "PKID,V10,V1,V2,V3,V3_1,V4,V4_1,V5,V5_1,V9";
				}
			} else if (file_name.indexOf("人口适龄教育年龄") > -1) {
				table_name = "PKC_1_1_9";
				insert_z = "PKID,V10,V1,V2,V3,V3_1,V4,V4_1,V5,V5_1,V6,V6_1,V7,V7_1,V8,V8_1,V9";
			} else if (file_name.indexOf("贫困户贫困属性") > -1) {
				table_name = "PKC_1_2_1";
				insert_z = "PKID,COM_CODE,COM_NAME,Z_HU,Z_REN,V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,V11,V12,TYPE";
			} else if (file_name.indexOf("户主要致贫原因") > -1) {
				table_name = "PKC_1_2_2";
				insert_z = "PKID,COM_CODE,COM_NAME,Z_HU,V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,V11,V12,V13,V14,V15,V16,V17,V18,V19,V20,V21,V22,TYPE";
			} else if (file_name.indexOf("户其他致贫原因") > -1) {
				table_name = "PKC_1_2_3";
				insert_z = "PKID,COM_CODE,COM_NAME,Z_HU,V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,V11,V12,V13,V14,V15,V16,V17,V18,V19,V20,V21,V22,V23,V24,V25,V26,TYPE";
			} else if (file_name.indexOf("户帮扶责任人落实情况") > -1) {
				table_name = "PKC_1_2_4";
				insert_z = "PKID,COM_CODE,COM_NAME,Z_HU,V1,V2,TYPE";
			} else if (file_name.indexOf("生产生活") > -1) {
				table_name = "PKC_1_2_5";
				insert_z = "PKID,COM_CODE,COM_NAME,Z_HU,V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,V11,V12,V13,TYPE";
			} else if (file_name.indexOf("土地资源") > -1) {
				table_name = "PKC_1_2_6";
				insert_z = "PKID,COM_CODE,COM_NAME,Z_HU,Z_REN,V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,V11,V12,V13,V14,TYPE";
			} else if (file_name.indexOf("人均收入") > -1) {
				table_name = "PKC_1_2_7";
				insert_z = "PKID,COM_CODE,COM_NAME,Z_NU,V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,V11,V12,V13,V14,TYPE";
			} else if (file_name.indexOf("主要燃料") > -1) {
				table_name = "PKC_1_2_8";
				insert_z = "PKID,COM_CODE,COM_NAME,Z_HU,V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,TYPE";
			} else if (file_name.indexOf("入户路") > -1) {
				table_name = "PKC_1_2_9";
				insert_z = "PKID,COM_CODE,COM_NAME,Z_HU,V1,V2,V3,V4,V5,TYPE";
			} else if (file_name.indexOf("家庭收入") > -1) {
				table_name = "PKC_1_2_10";
				insert_z = "PKID,COM_CODE,COM_NAME,Z_HU,Z_REN,V1,V2,V3,V4,V5,V6,V7,V8,V9,TYPE";
			} else if (file_name.indexOf("户上年度转移性收入构成情况") > -1) {
				table_name = "PKC_1_2_11";
				insert_z = "PKID,COM_CODE,COM_NAME,Z_HU,V1,V2,V3,V4,V5,V6,V7,V8,V9,TYPE";
			} else if (file_name.indexOf("贫困户人口规模") > -1) {
				table_name = "PKC_1_2_12";
				insert_z = "PKID,COM_CODE,COM_NAME,Z_HU,V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,V11,V12,TYPE";
			} else if (file_name.indexOf("户党员情况") > -1) {
				table_name = "PKC_1_2_13";
				insert_z = "PKID,COM_CODE,COM_NAME,Z_HU,Z_REN,V1,V2,V3,V4,V5,V6,V7,V8,TYPE";
			} else if (file_name.indexOf("户住房面积") > -1) {
				table_name = "PKC_1_2_14";
				insert_z = "PKID,COM_CODE,COM_NAME,Z_HU,V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,V11,V12,V13,V14,TYPE";
			} else if (file_name.indexOf("户与村主干路距离") > -1) {
				table_name = "PKC_1_2_15";
				insert_z = "PKID,COM_CODE,COM_NAME,Z_HU,V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,V11,V12,TYPE";
			} else if (file_name.indexOf("贫困村识别标准") > -1) {
				table_name = "PKC_1_3_1";
				insert_z = "PKID,V10,V1,V2,V3,V31,V4,V41,COM_PIN";
			} else if (file_name.indexOf("贫困村贫困发生率") > -1) {
				table_name = "PKC_1_3_3";
				insert_z = "PKID,V10,VF1,VF2,VF3,VF4,VF41,VF5,VF51,COM_PIN";
			} else if (file_name.indexOf("贫困人口分类扶持") > -1) {
				if(file_name.indexOf("十个全覆盖") > -1){
					table_name = "PKC_2_2_1";
					insert_z = "PKID,V10,VG1,VG2,VG3,VG4,VG5,VG51,VG6,VG7,VG71,VG8,VG81,VG9,VG91,VG10,VG11,VG12,VG13,VG14,VG141,VG15,VG151,VG16,VG161,COM_PIN";
				}else{
					table_name = "PKC_2_1_1";
					insert_z = "PKID,V10,FC1,FC2,FC3,FC4,FC5,FC6,FC7,FC8,COM_PIN";
				}
			} else if (file_name.indexOf("户帮扶责任人统计") > -1) {
				table_name = "PKC_3_1_1";
				insert_z = "PKID,V10,VB1,VB2,VB3,VH10,COM_PIN";
			} else if (file_name.indexOf("干部帮扶贫困户统计") > -1) {
				table_name = "PKC_3_1_2";
				insert_z = "PKID,V10,VH0,VH1,VH2,VH3,VH4,VH5,VH6,VH7,VH8,VH9,VH10,COM_PIN";
			} else if (file_name.indexOf("帮扶责任清单") > -1){
				table_name = "SYS_PERSONAL_HOUSEHOLD_MANY";
				insert_z = "PKID,PERSONAL_NAME,HOUSEHOLD_NAME,PERSONAL_PHONE,HOUSEHOLD_CARD,PASSWORD";
			} else{
				table_name = "";
			}
			//读取到了用到的表
			if(!table_name.equals("")){
				System.out.println("开始读取：" + file_name);
				// 创建输入流
				InputStream stream = new FileInputStream(file_name);
				// 获取Excel文件对象
				rwb = Workbook.getWorkbook(stream);
				// 获取文件的指定工作表 默认的第一个
				Sheet sheet = rwb.getSheet(0);
				// 行数(表头的目录不需要，从1开始)
				for (int i = 1; i < sheet.getRows(); i++) {
					// 创建一个数组 用来存储每一列的值
					String[] str = new String[sheet.getColumns()];
					// 列数
					for (int j = 0; j < sheet.getColumns(); j++) {
						// 获取第i行，第j列的值
						cell = sheet.getCell(j, i);
						str[j] = cell.getContents();
					}
					// 把刚获取的列存入list
					list.add(str);
				}
				for (int i = 0; i < list.size(); i++) {
					insert_value = "";
					String[] str = (String[]) list.get(i);
					//对于帮扶人表-生成语句中的value值
					if(table_name.equals("SYS_PERSONAL_HOUSEHOLD_MANY")){
						bfr_name = str[2];//帮扶人姓名
						bfr_phone = str[13];//帮扶人电话
						pkh_name = str[14];//贫困户名
						pkh_card = str[15];//贫困户的证件号
					}else{
						//对于PKC表-生成语句中的value值
						for (int j = 0; j < str.length; j++) {
							if(table_name.equals("PKC_1_1_6")){
								if(str.length==29){
									if( j==21 || j ==22){//如果1-1-6的列数为29，说明读取到了两个九月和其占比，只留一个

									}else{
										if (j != 0) {
											insert_value += ",";
										}
										insert_value += "'" + str[j] + "'";
									}
								}else{
									if (j != 0) {
										insert_value += ",";
									}
									insert_value += "'" + str[j] + "'";
								}
							}else{
								if (j != 0) {
									insert_value += ",";
								}
								insert_value += "'" + str[j] + "'";
							}
						}
					}
					
					//对帮扶人表进行操作
					if(table_name.equals("SYS_PERSONAL_HOUSEHOLD_MANY")){
						//查询帮扶人电话和贫困户主身份证号
						String bfr_sql_1 = "SELECT * FROM SYS_PERSONAL_HOUSEHOLD_MANY WHERE PERSONAL_PHONE = "+bfr_phone+" AND HOUSEHOLD_CARD = "+pkh_card;
						List<Map> list_bfr = this.getBySqlMapper.findRecords(bfr_sql_1);
						if(list_bfr.size()>0){
							//如果有记录，则有记录，更新此记录帮扶人姓名，户主姓名
							String bfr_sql_2 = "UPDATE SYS_PERSONAL_HOUSEHOLD_MANY SET PERSONAL_NAME = "+bfr_name+",HOUSEHOLD_NAME = "+pkh_name+" WHERE "
									+ " PERSONAL_PHONE = "+bfr_phone+" AND HOUSEHOLD_CARD = "+pkh_card;
							try {
								System.out.println("开始更新"+table_name+"，语句为：" + bfr_sql_2);
								this.getBySqlMapper.update(bfr_sql_2);
							} catch (Exception e) {
								e.printStackTrace();
								System.err.println("更新失败"+bfr_sql_2);
								filelist_err_2.add("(更新失败)-"+table_name+"--"+bfr_sql_2);
							}
						}else {
							//更新所有是这个电话号码人的名字
							String bfr_sql_3 = "UPDATE SYS_PERSONAL_HOUSEHOLD_MANY SET PERSONAL_NAME = "+bfr_name+" WHERE PERSONAL_PHONE = "+bfr_phone;
							try {
								System.out.println("开始更新"+table_name+"，语句为：" + bfr_sql_3);
								this.getBySqlMapper.update(bfr_sql_3);
							} catch (Exception e) {
								e.printStackTrace();
								System.err.println("更新失败"+bfr_sql_3);
								filelist_err_2.add("(更新失败)-"+table_name+"--"+bfr_sql_3);
							}
							//更新所有是这个身份证号贫困户主的名字
							String bfr_sql_4 = "UPDATE SYS_PERSONAL_HOUSEHOLD_MANY SET HOUSEHOLD_NAME = "+pkh_name+" WHERE HOUSEHOLD_CARD = "+pkh_card;
							try {
								System.out.println("开始更新"+table_name+"，语句为：" + bfr_sql_4);
								this.getBySqlMapper.update(bfr_sql_4);
							} catch (Exception e) {
								e.printStackTrace();
								System.err.println("更新失败"+bfr_sql_4);
								filelist_err_2.add("(更新失败)-"+table_name+"--"+bfr_sql_4);
							}
							
							//获取ID
							String cha_sql = "SELECT max(PKID)+1 PKID FROM "+table_name;
							String max_id = this.getBySqlMapper.findRecords(cha_sql).get(0).get("PKID").toString();
							//将此数据插入数据库中
							String bfr_sql_5 = "INSERT INTO SYS_PERSONAL_HOUSEHOLD_MANY(PKID,PERSONAL_NAME,HOUSEHOLD_NAME,PERSONAL_PHONE,HOUSEHOLD_CARD)"
									+ " VALUES("+max_id+","+bfr_name+","+pkh_name+","+bfr_phone+","+pkh_card+")";
							try {
								System.out.println("开始添加"+table_name+"，语句为：" + bfr_sql_5);
								this.getBySqlMapper.insert(bfr_sql_5);
							} catch (Exception e) {
								e.printStackTrace();
								System.err.println("添加失败"+bfr_sql_5);
								filelist_err_2.add("(添加失败)-"+table_name+"--"+bfr_sql_5);
							}
						}
					}else{
						//对于PKC表-判断数据库表中的列和excel中的列是否相同
						if (insert_z.split(",").length-2 == insert_value.split(",").length) {
							//判断数据库中是否存在了这个行政区划的数据
							String pd_sql = "SELECT * FROM "+table_name+" WHERE "+insert_z.split(",")[1]+"="+insert_value.split(",")[0]+" AND V99 ='2016' ";
							List<Map> list_pd = this.getBySqlMapper.findRecords(pd_sql);
							if(list_pd.size()>0){
								String up_sql = "UPDATE "+table_name+" SET ";
								for(int c = 0; c < insert_value.split(",").length; c++){
									if (c != 0) {
										up_sql += ",";
									}
									up_sql += insert_z.split(",")[c+1] + "="+insert_value.split(",")[c];
								}
								up_sql += " WHERE "+insert_z.split(",")[1]+"="+insert_value.split(",")[0]+" AND V99 ='2016' ";
								try {
									System.out.println("开始更新"+table_name+"，语句为：" + up_sql);
									this.getBySqlMapper.update(up_sql);
									System.out.println("---保存成功---");
								} catch (Exception e) {
									e.printStackTrace();
									System.err.println("更新失败"+up_sql);
									filelist_err.add("(更新失败)-"+table_name+"--"+up_sql);
								}
							}else{
								//获取ID
								String cha_sql = "SELECT max(PKID)+1 PKID FROM "+table_name;
								String max_id = this.getBySqlMapper.findRecords(cha_sql).get(0).get("PKID").toString();
								//执行插入语句
								String sql = "insert into " + table_name +"(" + insert_z
										+ ",V99) values(" + max_id + "," + insert_value
										+ ",'0','2016')";
								try {
									System.out.println("开始存入"+table_name+"，语句为：" + sql);
									this.getBySqlMapper.insert(sql);
									System.out.println("---保存成功---");
								} catch (Exception e) {
									e.printStackTrace();
									System.err.println("添加失败"+sql);
									filelist_err.add("(添加失败)-"+table_name+"--"+sql);
								}
							}
						} else {
							System.err.println(table_name + "——Excel列数与数据库表列数不对应");
							filelist_err.add(table_name + "(Excel列数与数据库表列数不对应)");
							System.out.println(insert_z.split(",").length+"-------"+insert_value.split(",").length);
						}
					}
				}
			}else{
				System.err.println("读取到了没有用到的表");
			}
		}
	
		if(filelist_err.size()!=0){
			FileOutputStream fs = new FileOutputStream(new File("g:\\bugpkc-"+getime()+".txt"));
			PrintStream p = new PrintStream(fs);
			p.print(filelist_err);
			p.close();
			System.err.println("PKC表出现错误次数："+filelist_err.size());
		}
		if(filelist_err_2.size()!=0){
			FileOutputStream fs2 = new FileOutputStream(new File("g:\\bugbfr-"+getime()+".txt"));
			PrintStream p2 = new PrintStream(fs2);
			p2.print(filelist_err_2);
			p2.close();
			System.err.println("bfr表出现错误次数："+filelist_err_2.size());
		}
		
	}
}