package com.gistone.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gistone.MyBatis.config.GetBySqlMapper;

@RestController
@RequestMapping
@SuppressWarnings({ "rawtypes", "unchecked" })
public class Singleton {

	@Autowired
	private GetBySqlMapper getBySqlMapper;

	/**
	 *  定义录入年份
	 */
	String data_year = "2016";
	/**
	 *  定义文件夹路径
	 */
	String get_data = "G://录入数据库";

	/**
	 *  存储.xls名称
	 */
	private static ArrayList<String> filelist = new ArrayList<String>();

	/**
	 *  存储  pkc   出错表的名称和错误的列
	 */
	private static ArrayList<String> filelist_err = new ArrayList<String>();

	/**
	 *  存储 帮扶人   出错表的名称和错误的列
	 */
	private static ArrayList<String> filelist_err_2 = new ArrayList<String>();

	/**
	 *  存储 坐标      出错表的名称和错误的列
	 */
	private static ArrayList<String> filelist_err_3 = new ArrayList<String>();

	/**
	 *  存储 bfr 每个表的所有信息，作为计算该表总计所用
	 */
	private static ArrayList<String> datainfo = new ArrayList<String>();

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

	/**
	 * @method 格式化String为 String[]
	 * @return
	 * @author 张晓翔
	 * @param string 
	 */
	private String[] geshihua(String string) {
		return string.replace("'", "").split(",");
	}

	/**
	 * @method 计算int占比
	 * @param num_1
	 * @param num_2
	 * @return
	 * @author 张晓翔
	 * @date 2016年11月28日
	 */
	private Object jisuan(int num_1, int num_2) {
		if(num_1==0 || num_2==0){
			return "0";
		}else{
			DecimalFormat df = new DecimalFormat("0.00");//格式化小数  
			return df.format(100*(float)num_2/(float)num_1);//返回的是String类型 
		}
	}

	/**
	 * @method 计算人均
	 * @param float1
	 * @param float2
	 * @return
	 * @author 张晓翔
	 */
	private String jisuan_2(Float float1, Float float2) {
		if(float1==0.0 || float2==0.0){
			return "0";
		}else{
			DecimalFormat df = new DecimalFormat("0.00");//格式化小数  
			return df.format(float2/float1);//返回的是String类型 
		}
	}

	/**
	 * @method 计算乘积
	 * @param string
	 * @param d
	 * @return
	 * @author 张晓翔
	 */
	private double jisuan_3(int intr, Float d) {
		return (intr*d);//返回的是Float类型 
	}

	/**
	 * @method 计算浮点数占比
	 * @param qt
	 * @param hjzyxsr
	 * @return
	 * @author 张晓翔
	 */
	private String jisuan4(float qt, float hjzyxsr) {
		if(qt==0.0 || hjzyxsr==0.0){
			return "0";
		}else{
			DecimalFormat df = new DecimalFormat("0.00");//格式化小数  
			return df.format(100*qt/hjzyxsr);//返回的是String类型 
		}
	}

	/**
	 * @method 将读取到的空值转换成0
	 * @param replace
	 * @return
	 * @author 张晓翔
	 */
	private String chukong(String replace) {
		if(replace.equals("")){
			return "0";
		}else{
			return replace;
		}
	}

	/**
	 * @method 截取字符串后四位
	 * @param file_name
	 * @return
	 * @author 张晓翔
	 */
	private String jqhsw(String file_name) {
		return file_name.substring(file_name.length()-4, file_name.length());
	}

	/**
	 * @method 得到要录入所有的文件的名称集合
	 * @return
	 * @author 张晓翔
	 */
	private ArrayList<String> getdata() {
		if(filelist.size()==0){
			String filePath = get_data;
			getFiles(filePath);
		}
		return filelist;
	}

	/**
	 * @method 通过递归得到某一路径下所有的.xls文件
	 * @return
	 * @author 张晓翔
	 */
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

	/**
	 * @method 主操作流程
	 * @throws BiffException
	 * @throws IOException
	 * @author 张晓翔
	 */
	@RequestMapping("readExcel.do")
	public void readExcel(HttpServletRequest request,HttpServletResponse response) throws BiffException,IOException{
		// 创建一个list 用来存储读取的内容
		List list = new ArrayList();
		String table_name = "";// 数据库表名
		String insert_z = "";// sql语句中数据库的列
		String insert_value = "";// 语句中value（）中的值
		String insert_fuzhu = "";//如果excel表中列数大于数据库表中的列数
		String bfr_name = "";//帮扶人姓名
		String bfr_phone = "";//帮扶人电话
		String pkh_name = "";//贫困户名
		String pkh_card = "";//贫困户的证件号

		filelist_err.clear();
		filelist_err_2.clear();
		filelist_err_3.clear();

		Singleton s = new Singleton();
		for (int a = 0; a < s.getdata().size(); a++) {
			list.clear ();

			//依次获取表名
			String file_name = s.getdata().get(a);
			//通过表名，获取数据库表名
			table_name = hq_bm(file_name).get("table_name").toString();
			insert_z = hq_bm(file_name).get("insert_z").toString();

			//读取到了用到的表
			if(!table_name.equals("")){
				//读取excel表数据
				list = duqu_excel(file_name);
				if(list.isEmpty()){
					if(jqhsw(file_name).equals(".xls")){
						filelist_err.add("\r\n(添加失败)-第一个sheet里无数据"+file_name);
					}else{
						filelist_err.add("\r\n(添加失败)-后缀名出错"+file_name);
					}
				}else{
					//清空暂存的整个表数据
					datainfo.clear();
					for (int i = 0; i < list.size(); i++) {
						insert_value = "";
						insert_fuzhu = "";
						String[] str = (String[]) list.get(i);
						String code = "";
						String lat ="";
						String lon ="";
						//对于帮扶人表-生成语句中的value值
						if(table_name.equals("SYS_PERSONAL_HOUSEHOLD_MANY")){
							bfr_name = "";//帮扶人姓名
							bfr_phone = "";//帮扶人电话
							pkh_name = "";//贫困户名
							pkh_card = "";//贫困户的证件号

							bfr_name = str[2];//帮扶人姓名
							bfr_phone = str[13];//帮扶人电话
							pkh_name = str[14];//贫困户名
							pkh_card = str[15];//贫困户的证件号
						}else if(table_name.equals("SYS_COMPANY")){
							//坐标
							code = str[0].replace(" ", "");
							if(!code.equals("")){
								if(str[2].indexOf(",")>-1){
									lat = str[2].split(",")[0];
									lon = str[2].split(",")[1];
								}else{
									lat = str[2];
									lon = str[3];
								}
							}else{
								code="";
								filelist_err_3.add("\r\n无行政区划编码----"+(i+2));
							}
						}else{
							//PKC
							String insert_fz_2 = "";
							//对于PKC表-生成语句中的value值
							for (int j = 0; j < str.length; j++) {
								String str_2 = str[j].replace(" ", "");
								if(j == 0){//验证行政区划代码
									if(!str_2.equals("") && str_2.matches("^\\d{12}$")){
									}else{
										insert_fuzhu += ",3";
										insert_fz_2 += "\r\n第-"+(i+2)+"-行，第-"+(j+1)+"-列";
									}
								}else if(j == 1){//跳过行政区划名称
								}else{//验证需要填的东西
									if(str_2.equals("") || str_2.matches("^\\d+$|^\\d+\\.\\d+$")){
									}else{
										insert_fuzhu += ",2";
										insert_fz_2 += "\r\n第-"+(i+2)+"-行，第-"+(j+1)+"-列";
									}
								}
								if(table_name.equals("PKC_1_1_6")){
									if(str.length == 29){
										if( j==21 || j ==22){//如果1-1-6的列数为29，说明读取到了两个九月和其占比，只留一个

										}else{
											if (j != 0) {
												insert_value += ",";
											}
											insert_value += "'" + str_2 + "'";
										}
									}else{
										if (j != 0) {
											insert_value += ",";
										}
										insert_value += "'" + str_2 + "'";
									}
								}else{
									if(j < insert_z.split(",").length-2){
										if (j != 0) {
											insert_value += ",";
										}
										insert_value += "'" + str_2 + "'";
									}else{
										String sta_1 = str_2.replace(" ","");
										if(!sta_1.equals("")){
											insert_fuzhu += ",1";
										}
									}
								}
							}
							if(insert_fuzhu.indexOf("1")>-1){
								insert_value = insert_fuzhu;
								filelist_err.add("\r\nExcel列数与数据库表列数不对应--》"+table_name +file_name);
								System.err.println(table_name + file_name + "——Excel列数与数据库表列数不对应");
							}
							if(insert_fuzhu.indexOf("2")>-1){
								insert_value = insert_fuzhu;
								filelist_err.add("\r\n数据格式不符合要求--》"+file_name+"-->"+insert_fz_2);
							}
							if(insert_fuzhu.indexOf("3")>-1){
								insert_value = insert_fuzhu;
								filelist_err.add("\r\n行政区划代码存在问题--》"+file_name+"-->"+insert_fz_2);
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
						}else if(table_name.equals("SYS_COMPANY")){
							//对坐标表进行操作
							if(!code.equals("")){
								String upsys_sql = "UPDATE SYS_COMPANY SET LAT='"+lat+"',LON='"+lon+"' WHERE COM_LEVEL=5 AND COM_CODE='"+code+"'";
								String upsys_sql_2 = "UPDATE SYS_COM SET LAT='"+lat+"',LNG='"+lon+"' WHERE V10='"+code+"'";
								try {
									this.getBySqlMapper.update(upsys_sql);
									this.getBySqlMapper.update(upsys_sql_2);
									System.out.println("成功"+(i+2));
								} catch (Exception e) {
									filelist_err_3.add("\r\n保存出错--"+(i+2)+"失败");
									System.err.println("失败");
								}
							}
						}else{
							//对于PKC表-判断数据库表中的列和excel中的列是否相同
							if (insert_z.split(",").length-2 == insert_value.split(",").length) {
								//判断数据库中是否存在了这个行政区划的数据
								String pd_sql = "SELECT * FROM "+table_name+" WHERE "+insert_z.split(",")[1]+"="+insert_value.split(",")[0]+" AND V99 ="+data_year;
								List<Map> list_pd = this.getBySqlMapper.findRecords(pd_sql);
								String sql = "";
								if(list_pd.size()>0){
									sql = "UPDATE "+table_name+" SET ";
									for(int c = 0; c < insert_value.split(",").length; c++){
										if (c != 0) {
											sql += ",";
										}
										sql += insert_z.split(",")[c+1] + "="+insert_value.split(",")[c];
									}
									sql += " WHERE "+insert_z.split(",")[1]+"="+insert_value.split(",")[0]+" AND V99 ="+data_year;
								}else{
									//获取ID
									String cha_sql = "SELECT max(PKID)+1 PKID FROM "+table_name;
									String max_id = this.getBySqlMapper.findRecords(cha_sql).get(0).get("PKID").toString();
									//执行插入语句
									sql = "insert into " + table_name +"(" + insert_z
											+ ",V99,V98) values(" + max_id + "," + insert_value
											+ ",'0',"+data_year+",'5')";
								}
								try {
									this.getBySqlMapper.findRecords(sql);
									//将数据存入
									datainfo.add(insert_value.replace("''", "'0'"));
								} catch (Exception e) {
									e.printStackTrace();
									filelist_err.add("\r\n(录入单条数据失败)--》"+file_name+"--语句为"+sql);
								}

							}
						}
					}
					if(!table_name.equals("SYS_PERSONAL_HOUSEHOLD_MANY") && !table_name.equals("SYS_COMPANY")){
						if(datainfo.isEmpty()){
							filelist_err.add("\r\n(此表读取不到数据)-"+file_name);
						}else{
							String xzqhbm = geshihua(datainfo.get(0))[0];
							if(!xzqhbm.equals("") && xzqhbm.matches("^\\d{12}$")){
								//行政区划编码
								String code = xzqhbm.substring(0, 9)+"000";
								//查询行政区划名称sql语句
								String sql_xzqh ="SELECT COM_NAME,COM_CODE FROM SYS_COMPANY WHERE COM_CODE='"+code+"'";
								//行政区划名称
								String name ="";
								try {
									List<Map> list_pd = this.getBySqlMapper.findRecords(sql_xzqh);
									if(list_pd.size()>0){
										name=list_pd.get(0).get("COM_NAME").toString();
									}else{
										System.err.println("没有该行政区划编码");
										filelist_err.add("\r\n(没有该行政区划编码)-"+file_name+"--"+code);
									}
								} catch (Exception e) {
									e.printStackTrace();
									System.err.println("查询行政区划名称出错");
									filelist_err.add("\r\n(查询行政区划名称出错)-"+file_name+"--"+code);
								}
								//存放的是计算后的乡一级数据
								ArrayList<String> arry = count_r(datainfo,table_name);
								if(arry.isEmpty()){
									filelist_err.add("\r\n(乡级数据计算错误)-"+file_name);
								}else{
									//将乡数据插入到数据库
									//先查询库里有没有这个乡的数据
									String chaxiang_sql = "SELECT * FROM "+table_name+" WHERE "+insert_z.split(",")[1]+"='"+code+"' AND V99="+data_year;
									List<Map> list_cs = this.getBySqlMapper.findRecords(chaxiang_sql);
									String sql = "";
									if(list_cs.size()>0){//有就执行updata
										sql = "UPDATE "+table_name+" SET ";
										for(int c = 0; c < arry.size(); c++){
											if (c != 0) {
												sql += ",";
											}
											sql += insert_z.split(",")[c+3] + "='"+arry.get(c)+"'";
										}
										sql += " WHERE "+insert_z.split(",")[1]+"="+code+" AND V99 ="+data_year;
									}else{
										//获取ID
										String add_xsql = "SELECT max(PKID)+1 PKID FROM "+table_name;
										String max_id_x = this.getBySqlMapper.findRecords(add_xsql).get(0).get("PKID").toString();
										//执行插入语句
										sql = "insert into " + table_name +"(" + insert_z
												+ ",V99,V98) values(" + max_id_x + ",'" + code + "','" +name;
										for(int x=0;x<arry.size();x++){
											sql += "','"+arry.get(x);
										}
										sql += "','0',"+data_year+",'4')";
									}
									try {
										this.getBySqlMapper.findRecords(sql);
										System.out.println("成功!");
									} catch (Exception e) {
										e.printStackTrace();
										System.err.println("失败！ 乡"+file_name+"，语句为：" + sql);
										filelist_err.add("\r\n(添加乡失败)-》-"+file_name+"--"+sql);
									}
								}
							}
						}
					}
				}
			}else{
				System.err.println("读取到了没有用到的表");
			}
		}

		//将获取到的错误生成txt文档--贫困村
		if(filelist_err.size()!=0){
			FileOutputStream fs = new FileOutputStream(new File("g:\\bugpkc-"+getime()+".txt"));
			PrintStream p = new PrintStream(fs);
			p.print(filelist_err);
			p.close();
			System.err.println("PKC表出现错误次数："+filelist_err.size());
		}
		//将获取到的错误生成txt文档--帮扶人
		if(filelist_err_2.size()!=0){
			FileOutputStream fs2 = new FileOutputStream(new File("g:\\bugbfr-"+getime()+".txt"));
			PrintStream p2 = new PrintStream(fs2);
			p2.print(filelist_err_2);
			p2.close();
			System.err.println("bfr表出现错误次数："+filelist_err_2.size());
		}
		//将获取到的错误生成txt文档--坐标
		if(filelist_err_3.size()!=0){
			FileOutputStream fs2 = new FileOutputStream(new File("g:\\bugzuobiao-"+getime()+".txt"));
			PrintStream p2 = new PrintStream(fs2);
			p2.print(filelist_err_3);
			p2.close();
			System.err.println("bfr表出现错误次数："+filelist_err_3.size());
		}
		filelist.clear();

	}

	/**
	 * @method 读取excel
	 * @param file_name
	 * @return
	 * @throws BiffException
	 * @throws IOException
	 * @author 张晓翔
	 */
	private List duqu_excel(String file_name) throws BiffException, IOException {
		List list = new ArrayList();
		Workbook rwb = null;
		Cell cell = null;

		System.out.println("开始读取：" + file_name);
		if(file_name.length()>=4){

			String strs = jqhsw(file_name);//截取后四位
			if(strs.equals(".xls")){
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
					// if(!sheet.getCell(0, i).getContents().equals("")&&!sheet.getCell(1, i).getContents().equals("")){
					// 列数
					for (int j = 0; j < sheet.getColumns(); j++) {
						// 获取第i行，第j列的值
						cell = sheet.getCell(j, i);
						str[j] = cell.getContents().replace("%", "");
					}
					// 把刚获取的列存入list
					list.add(str);
					// }
				}
				stream.close();
			}
		}

		return list;
	}


	/**
	 * @method 根据文件名匹配数据库中的表名
	 * @param file_name
	 * @return
	 * @author 张晓翔
	 */
	private  Map<String, String> hq_bm(String file_name) {
		Map<String, String> Smap_f = new HashMap<String, String>();
		Smap_f.put("table_name", "");
		Smap_f.put("insert_z", "");
		if (file_name.indexOf("人口基本信息") > -1) {
			Smap_f.put("table_name", "PKC_1_1_0");
			Smap_f.put("insert_z", "PKID,V10,V1,V2,V3,V3_1,V4,V4_1,V5,V5_1,V6,V6_1,V7,V7_1,V8,V8_1,V9");
		} else if (file_name.indexOf("人口年龄分组") > -1) {
			Smap_f.put("table_name", "PKC_1_1_1");
			Smap_f.put("insert_z", "PKID,V10,V1,V2,V3,V3_1,V4,V4_1,V5,V5_1,V6,V6_1,V7,V7_1,V8,V8_1,V9");
		} else if (file_name.indexOf("人口身体健康情况") > -1) {
			Smap_f.put("table_name", "PKC_1_1_2");
			Smap_f.put("insert_z", "PKID,V10,V1,V2,V3,V3_1,V4,V4_1,V5,V5_1,V6,V6_1,V9");
		} else if (file_name.indexOf("人口文化程度统计") > -1) {
			Smap_f.put("table_name", "PKC_1_1_3");
			Smap_f.put("insert_z", "PKID,V10,V1,V2,V3,V3_1,V4,V4_1,V5,V5_1,V6,V6_1,V7,V7_1,V8,V8_1,V9");
		} else if (file_name.indexOf("人口劳动能力类型") > -1) {
			Smap_f.put("table_name", "PKC_1_1_4");
			Smap_f.put("insert_z", "PKID,V10,V1,V2,V3,V3_1,V4,V4_1,V5,V5_1,V6,V6_1,V9");
		} else if (file_name.indexOf("人口上年度务工状况") > -1) {
			Smap_f.put("table_name", "PKC_1_1_5");
			Smap_f.put("insert_z", "PKID,V10,V1,V2,V3,V3_1,V4,V4_1,V5,V5_1,V6,V6_1,V7,V7_1,V9");
		} else if (file_name.indexOf("人口上年度务工时间") > -1) {
			Smap_f.put("table_name", "PKC_1_1_6");
			Smap_f.put("insert_z", "PKID,V10,V1,V2,V3,V3_1,V4,V4_1,V5,V5_1,V6,V6_1,V7,V7_1,V8,V8_1,V16,V16_1,V11,V11_1,V12,V12_1,V13,V13_1,V14,V14_1,V15,V15_1,V9");
		} else if (file_name.indexOf("人口在校生情况") > -1) {
			Smap_f.put("table_name", "PKC_1_1_7");
			Smap_f.put("insert_z", "PKID,V10,V1,V2,V3,V3_1,V4,V4_1,V5,V5_1,V6,V6_1,V7,V7_1,V8,V8_1,V20,V20_1,V11,V11_1,V12,V12_1,V13,V13_1,V14,V14_1,V15,V15_1,V16,V16_1,V17,V17_1,V18,V18_1,V19,V19_1,V9");
		} else if (file_name.indexOf("人口统计") > -1) {
			if (file_name.indexOf("建档立卡贫困村未脱贫") > -1) {
				Smap_f.put("table_name", "PKC_1_3_2");
				Smap_f.put("insert_z", "PKID,V10,VW1,VW2,VW3,VW4,VW41,VW5,VW51,VW6,VW61,VW7,VW71,COM_PIN");
			}else{
				Smap_f.put("table_name", "PKC_1_1_8");
				Smap_f.put("insert_z", "PKID,V10,V1,V2,V3,V3_1,V4,V4_1,V5,V5_1,V9");
			}
		} else if (file_name.indexOf("人口适龄教育年龄") > -1) {
			Smap_f.put("table_name", "PKC_1_1_9");
			Smap_f.put("insert_z", "PKID,V10,V1,V2,V3,V3_1,V4,V4_1,V5,V5_1,V6,V6_1,V7,V7_1,V8,V8_1,V9");
		} else if (file_name.indexOf("贫困户贫困属性") > -1) {
			Smap_f.put("table_name", "PKC_1_2_1");
			Smap_f.put("insert_z", "PKID,COM_CODE,COM_NAME,Z_HU,Z_REN,V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,V11,V12,TYPE");
		} else if (file_name.indexOf("户主要致贫原因") > -1) {
			Smap_f.put("table_name", "PKC_1_2_2");
			Smap_f.put("insert_z", "PKID,COM_CODE,COM_NAME,Z_HU,V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,V11,V12,V13,V14,V15,V16,V17,V18,V19,V20,V21,V22,TYPE");
		} else if (file_name.indexOf("户其他致贫原因") > -1) {
			Smap_f.put("table_name", "PKC_1_2_3");
			Smap_f.put("insert_z", "PKID,COM_CODE,COM_NAME,Z_HU,V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,V11,V12,V13,V14,V15,V16,V17,V18,V19,V20,V21,V22,V23,V24,V25,V26,TYPE");
		} else if (file_name.indexOf("户帮扶责任人落实情况") > -1) {
			Smap_f.put("table_name", "PKC_1_2_4");
			Smap_f.put("insert_z", "PKID,COM_CODE,COM_NAME,Z_HU,V1,V2,TYPE");
		} else if (file_name.indexOf("生产生活") > -1) {
			Smap_f.put("table_name", "PKC_1_2_5");
			Smap_f.put("insert_z", "PKID,COM_CODE,COM_NAME,Z_HU,V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,V11,V12,V13,TYPE");
		} else if (file_name.indexOf("土地资源") > -1) {
			Smap_f.put("table_name", "PKC_1_2_6");
			Smap_f.put("insert_z", "PKID,COM_CODE,COM_NAME,Z_HU,Z_REN,V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,V11,V12,V13,V14,TYPE");
		} else if (file_name.indexOf("人均收入") > -1) {
			Smap_f.put("table_name", "PKC_1_2_7");
			Smap_f.put("insert_z", "PKID,COM_CODE,COM_NAME,Z_NU,V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,V11,V12,V13,V14,TYPE");
		} else if (file_name.indexOf("主要燃料") > -1) {
			Smap_f.put("table_name", "PKC_1_2_8");
			Smap_f.put("insert_z", "PKID,COM_CODE,COM_NAME,Z_HU,V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,TYPE");
		} else if (file_name.indexOf("入户路") > -1) {
			Smap_f.put("table_name", "PKC_1_2_9");
			Smap_f.put("insert_z", "PKID,COM_CODE,COM_NAME,Z_HU,V1,V2,V3,V4,V5,TYPE");
		} else if (file_name.indexOf("家庭收入") > -1) {
			Smap_f.put("table_name", "PKC_1_2_10");
			Smap_f.put("insert_z", "PKID,COM_CODE,COM_NAME,Z_HU,Z_REN,V1,V2,V3,V4,V5,V6,V7,V8,V9,TYPE");
		} else if (file_name.indexOf("户上年度转移性收入构成情况") > -1) {
			Smap_f.put("table_name", "PKC_1_2_11");
			Smap_f.put("insert_z", "PKID,COM_CODE,COM_NAME,Z_HU,V1,V2,V3,V4,V5,V6,V7,V8,V9,TYPE");
		} else if (file_name.indexOf("贫困户人口规模") > -1) {
			Smap_f.put("table_name", "PKC_1_2_12");
			Smap_f.put("insert_z", "PKID,COM_CODE,COM_NAME,Z_HU,V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,V11,V12,TYPE");
		} else if (file_name.indexOf("户党员情况") > -1) {
			Smap_f.put("table_name", "PKC_1_2_13");
			Smap_f.put("insert_z", "PKID,COM_CODE,COM_NAME,Z_HU,Z_REN,V1,V2,V3,V4,V5,V6,V7,V8,TYPE");
		} else if (file_name.indexOf("户住房面积") > -1) {
			Smap_f.put("table_name", "PKC_1_2_14");
			Smap_f.put("insert_z", "PKID,COM_CODE,COM_NAME,Z_HU,V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,V11,V12,V13,V14,TYPE");
		} else if (file_name.indexOf("户与村主干路距离") > -1) {
			Smap_f.put("table_name", "PKC_1_2_15");
			Smap_f.put("insert_z", "PKID,COM_CODE,COM_NAME,Z_HU,V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,V11,V12,TYPE");
		} else if (file_name.indexOf("贫困村识别标准") > -1) {
			Smap_f.put("table_name", "PKC_1_3_1");
			Smap_f.put("insert_z", "PKID,V10,V1,V2,V3,V31,V4,V41,COM_PIN");
		} else if (file_name.indexOf("贫困村贫困发生率") > -1) {
			Smap_f.put("table_name", "PKC_1_3_3");
			Smap_f.put("insert_z", "PKID,V10,VF1,VF2,VF3,VF4,VF41,VF5,VF51,COM_PIN");
		} else if (file_name.indexOf("贫困人口分类扶持") > -1) {
			if(file_name.indexOf("十个全覆盖") > -1){
				Smap_f.put("table_name", "PKC_2_2_1");
				Smap_f.put("insert_z", "PKID,V10,VG1,VG2,VG3,VG4,VG5,VG51,VG6,VG7,VG71,VG8,VG81,VG9,VG91,VG10,VG11,VG12,VG13,VG14,VG141,VG15,VG151,VG16,VG161,COM_PIN");
			}else{
				Smap_f.put("table_name", "PKC_2_1_1");
				Smap_f.put("insert_z", "PKID,V10,FC1,FC2,FC3,FC4,FC5,FC6,FC7,FC8,COM_PIN");
			}
		} else if (file_name.indexOf("户帮扶责任人统计") > -1) {
			Smap_f.put("table_name", "PKC_3_1_1");
			Smap_f.put("insert_z", "PKID,V10,VB1,VB2,VB3,VH10,COM_PIN");
		} else if (file_name.indexOf("干部帮扶贫困户统计") > -1) {
			Smap_f.put("table_name", "PKC_3_1_2");
			Smap_f.put("insert_z", "PKID,V10,VH0,VH1,VH2,VH3,VH4,VH5,VH6,VH7,VH8,VH9,VH10,COM_PIN");
		} else if (file_name.indexOf("帮扶责任清单") > -1){
			Smap_f.put("table_name", "SYS_PERSONAL_HOUSEHOLD_MANY");
			Smap_f.put("insert_z", "PKID,PERSONAL_NAME,HOUSEHOLD_NAME,PERSONAL_PHONE,HOUSEHOLD_CARD,PASSWORD");
		} else if (file_name.indexOf("村坐标") > -1){
			Smap_f.put("table_name", "SYS_COMPANY");
			Smap_f.put("insert_z", "LAT,LON");
		} else {
			Smap_f.put("table_name", "");
			Smap_f.put("insert_z", "");
		}
		return Smap_f;
	}

	/**
	 * @method 计算全表，生成这个行政区划所的值
	 * @param datainfo2
	 * @param table_name
	 * @return
	 * @author 张晓翔
	 * @throws IOException 
	 */
	private ArrayList<String> count_r(ArrayList<String> datainfo2, String table_name) throws IOException {
		//最终要返回的sql语句
		ArrayList<String> table_all = new ArrayList<String>();
		//声明集合长度
		int changdu =0;//changdu 为此表中表示 具体人数或户数列 的列数 +2

		switch (table_name) {
		case "PKC_1_1_0":
			changdu = 8;
			table_all = caozuo(changdu,datainfo2);
			break;
		case "PKC_1_1_1":
			changdu = 8;
			table_all = caozuo(changdu,datainfo2);
			break;
		case "PKC_1_1_2":
			changdu = 6;
			table_all = caozuo(changdu,datainfo2);
			break;
		case "PKC_1_1_3":
			changdu = 8;
			table_all = caozuo(changdu,datainfo2);
			break;
		case "PKC_1_1_4":
			changdu = 6;
			table_all = caozuo(changdu,datainfo2);
			break;
		case "PKC_1_1_5":
			changdu = 7;
			table_all = caozuo(changdu,datainfo2);
			break;
		case "PKC_1_1_6":
			changdu = 14;
			table_all = caozuo(changdu,datainfo2);
			break;
		case "PKC_1_1_7":
			changdu = 18;
			table_all = caozuo(changdu,datainfo2);
			break;
		case "PKC_1_1_8":
			changdu = 5;
			table_all = caozuo(changdu,datainfo2);
			break;
		case "PKC_1_1_9":
			changdu = 8;
			table_all = caozuo(changdu,datainfo2);
			break;
		case "PKC_1_2_1":
			table_all = caozuo2_1(datainfo2);
			break;
		case "PKC_1_2_2":
			changdu = 13;
			table_all = caozuo(changdu,datainfo2);
			break;
		case "PKC_1_2_3":
			changdu = 15;
			table_all = caozuo(changdu,datainfo2);
			break;
		case "PKC_1_2_4":
			changdu = 3;
			table_all = caozuo(changdu,datainfo2);
			break;
		case "PKC_1_2_5":
			changdu = 8;
			table_all = caozuo(changdu,datainfo2);
			table_all.add(rjzfmj(datainfo2));//人均住房面积
			break;
		case "PKC_1_2_6":
			table_all = caozuo1_2_6(datainfo2);//土地资源情况
			break;
		case "PKC_1_2_7":
			changdu = 9;
			table_all = caozuo(changdu,datainfo2);
			break;
		case "PKC_1_2_8":
			changdu = 7;
			table_all = caozuo(changdu,datainfo2);
			break;
		case "PKC_1_2_9":
			table_all = caozuo2(datainfo2);
			break;
		case "PKC_1_2_10":
			table_all = caozuo1_2_10(datainfo2);
			break;
		case "PKC_1_2_11":
			table_all = caozuo1_2_11(datainfo2);
			break;
		case "PKC_1_2_12":
			changdu = 8;
			table_all = caozuo(changdu,datainfo2);
			break;
		case "PKC_1_2_13":
			table_all = caozuo1_2_15(datainfo2);
			break;
		case "PKC_1_2_14":
			changdu = 9;
			table_all = caozuo(changdu,datainfo2);
			break;
		case "PKC_1_2_15":
			changdu = 8;
			table_all = caozuo(changdu,datainfo2);
			break;
		case "PKC_1_3_1":
			changdu = 4;
			table_all = caozuo(changdu,datainfo2);
			break;
		case "PKC_1_3_2":
			table_all = caozuo1_3_2(datainfo2);
			break;
		case "PKC_1_3_3":
			table_all = caozuo1_3_3(datainfo2);
			break;
		case "PKC_2_1_1":
			changdu = 8;
			table_all = caozuo_alladd(changdu,datainfo2);
			break;
		case "PKC_2_2_1":
			changdu = 23;
			table_all = caozuo_alladd(changdu,datainfo2);
			break;
		case "PKC_3_1_1":
			table_all = caozuo3_1_1(datainfo2);
			break;
		case "PKC_3_1_2":
			changdu = 11;
			table_all = caozuo_alladd(changdu,datainfo2);
			break;

		default:
			break;
		}

		return table_all;
	}


	/**
	 * @method 计算汇总的主操作
	 * @param datainfo2
	 * @return
	 * @author 张晓翔
	 */
	private ArrayList<String> caozuo(int changdu, ArrayList<String> datainfo2)throws IOException{
		//集合-存结果
		ArrayList Alist =new ArrayList<String>();
		//集合-存人数
		Map<String, Integer> Smap = new HashMap<String, Integer>();
		//集合-存占比
		Map<String, String> Smap_f = new HashMap<String, String>();
		//初始化
		for(int i=1; i<changdu; i++){
			Smap.put("num_"+i, 0);
		}
		//取得这个苏木乡总的数据
		for(int i=0; i<datainfo2.size(); i++){
			//格式化String 为 String[]
			String [] str_sz = geshihua(datainfo2.get(i));
			if(str_sz.length>=changdu){
				for(int y=1; y<changdu; y++){
					int num = Smap.get("num_"+y);
					if(y==1){
						num += Integer.parseInt(chukong(str_sz[2]));
						Smap.put("num_1", num);
					}else{
						num += Integer.parseInt(chukong(str_sz[y+y-1]));
						Smap.put("num_"+y, num);
					}
				}
			}
		}
		//计算出这个嘎查乡总的占比
		if(Smap.get("num_1") != 0){
			for(int i=1; i<changdu-1; i++){
				Smap_f.put("a"+i, jisuan(Smap.get("num_1"),Smap.get("num_"+(i+1))).toString());
			}
		}else{
			for(int i=1; i<changdu-1; i++){
				Smap_f.put("a"+i, "");
			}
		}
		
		//拼接成sql语句
		for(int i=1; i<changdu; i++){
			Alist.add(Smap.get("num_"+(i)).toString());
			if(i<(changdu-1)){
				Alist.add(Smap_f.get("a"+(i)).toString());
			}
		}
		return Alist;
	}

	/**
	 * @method pkc1_2_1 贫困户属性统计表
	 * @param datainfo2
	 * @return
	 * @author 张晓翔
	 */
	private ArrayList<String> caozuo2_1(ArrayList<String> datainfo2) {
		//集合-存结果
		ArrayList Alist =new ArrayList<String>();
		Integer hushu = 0;//户数
		Integer renshu = 0;//人数
		Integer ybpkhs = 0;//一般贫困户户数
		Integer ybpkrs = 0;//一般贫困户人数
		Integer dbpkhs = 0;//低保贫困户数
		Integer dbpkrs = 0;//低保贫困人数
		Integer wbpkhs = 0;//五保贫困户数
		Integer wbpkrs = 0;//五保贫困人数
		for(int i=0; i<datainfo2.size(); i+=1){
			String [] str_sz = geshihua(datainfo2.get(i));
			if(str_sz.length < 17){
				hushu += Integer.parseInt(chukong(str_sz[2]));
				renshu += Integer.parseInt(chukong(str_sz[3]));
				ybpkhs += Integer.parseInt(chukong(str_sz[4]));
				ybpkrs += Integer.parseInt(chukong(str_sz[6]));
				dbpkhs += Integer.parseInt(chukong(str_sz[8]));
				dbpkrs += Integer.parseInt(chukong(str_sz[10]));
				wbpkhs += Integer.parseInt(chukong(str_sz[12]));
				wbpkrs += Integer.parseInt(chukong(str_sz[14]));
			}
		}
		Alist.add(hushu.toString());
		Alist.add(renshu.toString());
		Alist.add(ybpkhs.toString());
		Alist.add(jisuan(hushu,ybpkhs).toString());
		Alist.add(ybpkrs.toString());
		Alist.add(jisuan(renshu,ybpkrs).toString());
		Alist.add(dbpkhs.toString());
		Alist.add(jisuan(hushu,dbpkhs).toString());
		Alist.add(dbpkrs.toString());
		Alist.add(jisuan(renshu,dbpkrs).toString());
		Alist.add(wbpkhs.toString());
		Alist.add(jisuan(hushu,wbpkhs).toString());
		Alist.add(wbpkrs.toString());
		Alist.add(jisuan(renshu,wbpkrs).toString());
		return Alist;
	}
	/**
	 * @method 人均住房面积
	 * @param datainfo2
	 * @return
	 * @author 张晓翔
	 */
	private String rjzfmj(ArrayList<String> datainfo2) {
		float zfmj = 0;//住房面积
		int cs = 0;//为0的次数
		for(int i = 0; i<datainfo2.size(); i++){
			String [] str_sz = geshihua(datainfo2.get(i));
			zfmj += Float.parseFloat(chukong(str_sz[15]));
			if(Float.parseFloat(chukong(str_sz[15]))==0){
				cs += 1;
			}
		}
		String rj = "";
		if(zfmj==0.0){
			rj ="0";
		}else{
			DecimalFormat df = new DecimalFormat("0.00");//格式化小数  
			rj = df.format(zfmj/(float)(datainfo2.size()-cs));//返回的是String类型 
		}
		return rj;
	} 
	/**
	 * @method 贫困户土地资源情况
	 * @param datainfo2
	 * @return
	 * @author 张晓翔
	 */
	private ArrayList<String> caozuo1_2_6(ArrayList<String> datainfo2) {
		//集合-存结果
		ArrayList Alist =new ArrayList<String>();
		//集合-存人数
		Map<String, Float> Smap = new HashMap<String, Float>();
		//集合-存占比
		Map<String, String> Smap_f = new HashMap<String, String>();
		//初始化
		for(int i=1; i<10; i++){
			Smap.put("num_"+i, (float) 0);
		}
		//取得这个苏木乡总的数据
		for(int i=0; i<datainfo2.size(); i++){
			//格式化String 为 String[]
			String [] str_sz = geshihua(datainfo2.get(i));
			if(str_sz.length>=10){
				for(int y=1; y<10; y++){
					Float num = Smap.get("num_"+y);
					if(y==1 || y==2){
						num += Float.parseFloat(chukong(str_sz[(y+1)]));
						Smap.put("num_"+y, num);
					}else{
						num += Float.parseFloat(chukong(str_sz[y+y-2]));
						Smap.put("num_"+y, num);
					}
				}
			}
		}
		//计算出这个嘎查乡总的占比
		if(Smap.get("num_2") != 0.0){
			for(int i=1; i<8; i++){
				Smap_f.put("a"+i, jisuan_2(Smap.get("num_2") , Smap.get("num_"+(i+2))).toString());
			}
			//拼接成sql语句
			String hu = Smap.get("num_1").toString();
			Alist.add(hu.substring(0, hu.length()-2));
			for(int i=1; i<9; i++){
				if(i==1){
					String ren = Smap.get("num_2").toString();
					Alist.add(ren.substring(0, ren.length()-2));
				}else{
					Alist.add(Smap.get("num_"+(i+1)).toString());
				}
				if(i>1 && i<9){
					Alist.add(Smap_f.get("a"+(i-1)).toString());
				}
			}
		}

		return Alist;
	}

	/**
	 * @method 贫困户入户路情况
	 * @param datainfo2
	 * @return
	 * @author 张晓翔
	 */
	private ArrayList<String> caozuo2(ArrayList<String> datainfo2) {
		//集合-存结果
		ArrayList Alist =new ArrayList<String>();
		int hu = 0;
		float zgl = (float) 0.0;
		float ntl = 0;
		float ssgl = 0;
		float sngl = 0;
		float lqgl = 0;
		//取得这个苏木乡总的数据
		for(int i=0; i<datainfo2.size(); i++){

			String datainfo[] = geshihua(datainfo2.get(i));
			if(datainfo.length>7){
				hu += Integer.parseInt(datainfo[2].toString());
				zgl += Float.parseFloat(datainfo[3].toString());
				ntl += Float.parseFloat(datainfo[4].toString());
				ssgl += Float.parseFloat(datainfo[5].toString());
				sngl += Float.parseFloat(datainfo[6].toString());
				lqgl += Float.parseFloat(datainfo[7].toString());
			}
		}

		Alist.add(String.valueOf(hu));
		Alist.add(String.valueOf(zgl));
		Alist.add(String.valueOf(ntl));
		Alist.add(String.valueOf(ssgl));
		Alist.add(String.valueOf(sngl));
		Alist.add(String.valueOf(lqgl));
		return Alist;
	}

	/**
	 * @method 上年度家庭收入情况统计表
	 * @param datainfo2
	 * @return
	 * @author 张晓翔
	 * @date 2016年11月28日
	 */
	private ArrayList<String> caozuo1_2_10(ArrayList<String> datainfo2) {
		//集合-存结果
		ArrayList Alist =new ArrayList<String>();
		int hu = 0;
		int ren = 0;
		float nsr = 0;
		float scjyxsr = 0;
		float wgsr = 0;
		float ccxsr = 0;
		float zyxsr = 0;
		float hjscjyxzc = 0;
		float hjcsr = 0;
		float rjcsr = 0;
		//取得这个苏木乡总的数据
		for(int i=0; i<datainfo2.size(); i++){
			//格式化String 为 String[]
			String [] str_sz = geshihua(datainfo2.get(i));
			hu += Integer.parseInt(chukong(str_sz[2]));
			ren += Integer.parseInt(chukong(str_sz[3]));
			nsr += Float.parseFloat(chukong(str_sz[4]));
			scjyxsr += Float.parseFloat(chukong(str_sz[6]));
			wgsr += Float.parseFloat(chukong(str_sz[7]));
			ccxsr += Float.parseFloat(chukong(str_sz[8]));
			zyxsr += Float.parseFloat(chukong(str_sz[9]));
			hjscjyxzc += jisuan_3(Integer.parseInt(chukong(str_sz[2])),Float.parseFloat(chukong(str_sz[10])));
			hjcsr += jisuan_3(Integer.parseInt(chukong(str_sz[2])),Float.parseFloat(chukong(str_sz[11])));
			rjcsr += jisuan_3(Integer.parseInt(chukong(str_sz[2])),Float.parseFloat(chukong(str_sz[12])));
		}
		Alist.add(String.valueOf(hu));
		Alist.add(String.valueOf(ren));
		Alist.add(String.valueOf(nsr));
		Alist.add(String.valueOf(jisuan_2(Float.parseFloat(String.valueOf(hu)), nsr)));;
		Alist.add(String.valueOf(scjyxsr));
		Alist.add(String.valueOf(wgsr));
		Alist.add(String.valueOf(ccxsr));
		Alist.add(String.valueOf(zyxsr));
		Alist.add(String.valueOf(jisuan_2(Float.parseFloat(String.valueOf(hu)), hjscjyxzc)));
		Alist.add(String.valueOf(jisuan_2(Float.parseFloat(String.valueOf(hu)), hjcsr)));
		Alist.add(String.valueOf(jisuan_2(Float.parseFloat(String.valueOf(hu)), rjcsr)));

		return Alist;
	}

	/**
	 * @method 上年度转移性收入
	 * @param datainfo2
	 * @return
	 * @author 张晓翔
	 */
	private ArrayList<String> caozuo1_2_11(ArrayList<String> datainfo2) {
		//集合-存结果
		ArrayList Alist =new ArrayList<String>();
		int hu = 0;
		int hus = 0;
		float hjzyxsr = 0;
		float jhsyj = 0;
		float lqdbj = 0;
		float lqylj = 0;
		float lqstbcj = 0;
		String a1,a2,a3,a4;
		for(int i=0; i<datainfo2.size(); i++){
			//格式化String 为 String[]
			String [] str_sz = geshihua(datainfo2.get(i));
			hus = Integer.parseInt(chukong(str_sz[2]));
			hu += hus;
			hjzyxsr += jisuan_3(hus,Float.parseFloat(chukong(str_sz[3])));
			jhsyj += jisuan_3(hus,Float.parseFloat(chukong(str_sz[4])));
			lqdbj += jisuan_3(hus,Float.parseFloat(chukong(str_sz[6])));
			lqylj += jisuan_3(hus,Float.parseFloat(chukong(str_sz[8])));
			lqstbcj += jisuan_3(hus,Float.parseFloat(chukong(str_sz[10])));
		}
		hjzyxsr = Float.parseFloat(jisuan_2((float) hu,hjzyxsr)) ;
		jhsyj = Float.parseFloat(jisuan_2((float) hu,jhsyj));
		lqdbj = Float.parseFloat(jisuan_2((float) hu,lqdbj));
		lqylj = Float.parseFloat(jisuan_2((float) hu,lqylj));
		lqstbcj = Float.parseFloat(jisuan_2((float) hu,lqstbcj));

		a1 = jisuan4(jhsyj,hjzyxsr);//计划生育
		a2 = jisuan4(lqdbj,hjzyxsr);//领取低保金
		a3 = jisuan4(lqylj,hjzyxsr);//领取养老保险
		a4 = jisuan4(lqstbcj,hjzyxsr);//生态补偿金

		Alist.add(String.valueOf(hu));
		Alist.add(String.valueOf(hjzyxsr));
		Alist.add(String.valueOf(jhsyj));
		Alist.add(a1);
		Alist.add(String.valueOf(lqdbj));
		Alist.add(a2);
		Alist.add(String.valueOf(lqylj));
		Alist.add(a3);
		Alist.add(String.valueOf(lqstbcj));
		Alist.add(a4);

		return Alist;
	}
	/**
	 * @method 贫困户党员情况
	 * @param datainfo2
	 * @return
	 * @author 张晓翔
	 */

	private ArrayList<String> caozuo1_2_15(ArrayList<String> datainfo2) {
		//集合-存结果
		ArrayList Alist =new ArrayList<String>();
		int hu = 0, ren = 0, dyhs = 0, dyrs = 0, ybdyhs = 0, ybdyrs = 0;

		for(int i=0; i<datainfo2.size(); i++){
			String [] str_sz = geshihua(datainfo2.get(i));
			hu += Integer.parseInt(chukong(str_sz[2]));
			ren += Integer.parseInt(chukong(str_sz[3]));
			dyhs += Integer.parseInt(chukong(str_sz[4]));
			dyrs += Integer.parseInt(chukong(str_sz[6]));
			ybdyhs += Integer.parseInt(chukong(str_sz[8]));
			ybdyrs += Integer.parseInt(chukong(str_sz[10]));
		}
		Alist.add(String.valueOf(hu));
		Alist.add(String.valueOf(ren));
		Alist.add(String.valueOf(dyhs));
		Alist.add(jisuan(hu,dyhs));
		Alist.add(String.valueOf(dyrs));
		Alist.add(jisuan(ren,dyrs));
		Alist.add(String.valueOf(ybdyhs));
		Alist.add(jisuan(hu,ybdyhs));
		Alist.add(String.valueOf(ybdyrs));
		Alist.add(jisuan(ren,ybdyrs));
		return Alist;
	}

	/**
	 * @method 村人口统计表
	 * @param datainfo2
	 * @return
	 * @author 张晓翔
	 */
	private ArrayList<String> caozuo1_3_2(ArrayList<String> datainfo2) {
		//集合-存结果
		ArrayList Alist =new ArrayList<String>();
		int a1 = 0 , a2 = 0, a3 = 0, a4 = 0, a5 = 0, a6 = 0;
		for(int i=0; i<datainfo2.size(); i++){
			String [] str_sz = geshihua(datainfo2.get(i));
			a1 += Integer.parseInt(chukong(str_sz[2]));
			a2 += Integer.parseInt(chukong(str_sz[3]));
			a3 += Integer.parseInt(chukong(str_sz[4]));
			a4 += Integer.parseInt(chukong(str_sz[6]));
			a5 += Integer.parseInt(chukong(str_sz[8]));
			a6 += Integer.parseInt(chukong(str_sz[10]));
		}
		Alist.add(String.valueOf(a1));
		Alist.add(String.valueOf(a2));
		Alist.add(String.valueOf(a3));
		Alist.add(jisuan(a3,a1));
		Alist.add(String.valueOf(a4));
		Alist.add(jisuan(a4,a2));
		Alist.add(String.valueOf(a5));
		Alist.add(jisuan(a5,a1));
		Alist.add(String.valueOf(a6));
		Alist.add(jisuan(a6,a2));
		return Alist;
	}

	/**
	 * @method 村贫困发生率
	 * @param datainfo2
	 * @return
	 * @author 张晓翔
	 */
	private ArrayList<String> caozuo1_3_3(ArrayList<String> datainfo2) {
		//集合-存结果
		ArrayList Alist =new ArrayList<String>();
		int a1 = 0 , a2 = 0, a3 = 0, a4 = 0;
		for(int i=0; i<datainfo2.size(); i++){
			String [] str_sz = geshihua(datainfo2.get(i));
			a1 += Integer.parseInt(chukong(str_sz[2]));
			a2 += Integer.parseInt(chukong(str_sz[3]));
			a3 += Integer.parseInt(chukong(str_sz[4]));
			a4 += Integer.parseInt(chukong(str_sz[6]));
		}
		Alist.add(String.valueOf(a1));
		Alist.add(String.valueOf(a2));
		Alist.add(String.valueOf(a3));
		Alist.add(jisuan(a2,a3));
		Alist.add(String.valueOf(a4));
		Alist.add(jisuan(a2,a3));
		return Alist;
	}

	/**
	 * @method 全都是相加
	 * @param changdu
	 * @param datainfo2
	 * @return
	 * @author 张晓翔
	 */
	private ArrayList<String> caozuo_alladd(int changdu, ArrayList<String> datainfo2) {
		//集合-存结果
		ArrayList Alist =new ArrayList<String>();
		//集合-存人数
		Map<String, Float> Smap = new HashMap<String, Float>();
		//初始化
		for(int i=1; i<changdu; i++){
			Smap.put("num_"+i, (float) 0);
		}
		//取得这个苏木乡总的数据
		for(int i=0; i<datainfo2.size(); i++){
			//格式化String 为 String[]
			String [] str_sz = geshihua(datainfo2.get(i));
			if(str_sz.length>=changdu){
				for(int y=1; y<changdu; y++){
					float num = Smap.get("num_"+y);
					num += Float.parseFloat(chukong(str_sz[(y+1)]));
					Smap.put("num_"+y,(float)num);
				}
			}
		}
		//拼接成sql语句
		for(int i=1; i<changdu; i++){
			Alist.add(String.valueOf(Smap.get("num_"+i)).replace(".0", ""));
		}
		return Alist;
	}

	/**
	 * @method 帮扶责任人统计表
	 * @param datainfo2
	 * @return
	 * @author 张晓翔
	 */
	private ArrayList<String> caozuo3_1_1(ArrayList<String> datainfo2) {
		//集合-存结果
		ArrayList Alist =new ArrayList<String>();
		int a1 = 0, a2 = 0;
		for(int i=0; i<datainfo2.size(); i++){
			String [] str_sz = geshihua(datainfo2.get(i));
			a1 += Integer.parseInt(chukong(str_sz[2]));
			a2 += Integer.parseInt(chukong(str_sz[3]));
		}
		Alist.add(String.valueOf(a1));
		Alist.add(String.valueOf(a2));
		Alist.add("100");
		return Alist;
	}
}