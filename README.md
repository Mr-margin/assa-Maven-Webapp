#说明

Maven导入ojdbc6.jar
①直接在pom.xml中编写代码：
<dependency>
<groupid>com.oracle</groupid>
<artifactid>ojdbc6</artifactid>
<version>11.2.0.1.0</version>
</dependency>

mvn install:install-file -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0.1.0 -Dpackaging=jar -Dfile=D:\ojdbc6.jar



鄂尔多斯市脱贫攻坚信息管理系统

z4 表 AAD302字段是 村级集体收入
zhang_01 AAP011 字段是帮扶工作队 的编码
zhang_02 csr 是村收入，zcgzd是驻村工作队

统计视图说明：
X1：地图显示：下辖行政区划所选类型贫困户的数量
sql:select v1_pkid,v1_name,v1_code,
v2_pkid,v2_name,v2_code,v3_pkid,v3_name,v3_code,v4_pkid,v4_name,v4_code,v5_pkid,v5_name,v5_code,b1,b2,b3,time
 from(
select t5.pkid v1_pkid, t5.com_name v1_name, t5.com_code v1_code, 
t4.pkid v2_pkid, t4.com_name v2_name, t4.com_code v2_code, 
t3.pkid v3_pkid, t3.com_name v3_name, t3.com_code v3_code, 
t2.pkid v4_pkid, t2.com_name v4_name, t2.com_code v4_code, 
t1.pkid v5_pkid, t1.com_name v5_name, t1.com_code v5_code  from sys_company t1 
join sys_company t2 on t1.com_f_pkid=t2.pkid
join sys_company t3 on t2.com_f_pkid=t3.pkid
join sys_company t4 on t3.com_f_pkid=t4.pkid
join sys_company t5 on t4.com_f_pkid=t5.pkid where t1.pkid>1
) d1 LEFT JOIN (
select v1,v2,v3,v4,v5,count(*) b1 from da_household where sys_standard='国家级贫困人口' group by v1,v2,v3,v4,v5
) d2 on d1.v1_name=d2.v1 and d1.v2_name=d2.v2 and d1.v3_name=d2.v3 and d1.v4_name=d2.v4 and d1.v5_name = d2.v5  left  join (
select v1,v2,v3,v4,v5,count(*) b2 from da_household where sys_standard='市级低收入人口' group by v1,v2,v3,v4,v5) d3
on d1.v1_name=d3.v1 and d1.v2_name=d3.v2 and d1.v3_name=d3.v3 and d1.v4_name=d3.v4 and d1.v5_name = d3.v5 left join (
select v1,v2,v3,v4,v5,count(*) b3 from da_household where sys_standard='区级低收入人口' group by v1,v2,v3,v4,v5) d4
on d1.v1_name=d4.v1 and d1.v2_name=d4.v2 and d1.v3_name=d4.v3 and d1.v4_name=d4.v4 and d1.v5_name = d4.v5
 
 X2:统计图：显示 辖区本级的贫困户属性（一般贫困户、低保贫困户、五保贫困户、等）
 sql:select v1_pkid,v1_name,v1_code,
v2_pkid,v2_name,v2_code,v3_pkid,v3_name,v3_code,v4_pkid,v4_name,v4_code,v5_pkid,v5_name,v5_code,b1,b2,b3,b4,b5,b6,'time'
 from(
select t5.pkid v1_pkid, t5.com_name v1_name, t5.com_code v1_code, 
t4.pkid v2_pkid, t4.com_name v2_name, t4.com_code v2_code, 
t3.pkid v3_pkid, t3.com_name v3_name, t3.com_code v3_code, 
t2.pkid v4_pkid, t2.com_name v4_name, t2.com_code v4_code, 
t1.pkid v5_pkid, t1.com_name v5_name, t1.com_code v5_code from sys_company t1 
join sys_company t2 on t1.com_f_pkid=t2.pkid
join sys_company t3 on t2.com_f_pkid=t3.pkid
join sys_company t4 on t3.com_f_pkid=t4.pkid
join sys_company t5 on t4.com_f_pkid=t5.pkid where t1.pkid>1
) d1 LEFT JOIN (
select v1,v2,v3,v4,v5,count(*) b1  from da_household where v22='一般贫困户' group by v1,v2,v3,v4,v5
) d2 on d1.v1_name=d2.v1 and d1.v2_name=d2.v2 and d1.v3_name=d2.v3 and d1.v4_name=d2.v4 and d1.v5_name = d2.v5 left  join (
select v1,v2,v3,v4,v5,count(*) b2  from da_household where v22='低保户' group by v1,v2,v3,v4,v5
) d3 on d1.v1_name=d3.v1 and d1.v2_name=d3.v2 and d1.v3_name=d3.v3 and d1.v4_name=d3.v4 and d1.v5_name = d3.v5 left  join(
select v1,v2,v3,v4,v5,count(*) b3  from da_household where v22='五保户' group by v1,v2,v3,v4,v5
) d4 on d1.v1_name=d4.v1 and d1.v2_name=d4.v2 and d1.v3_name=d4.v3 and d1.v4_name=d4.v4 and d1.v5_name = d4.v5 left join(
select v1,v2,v3,v4,v5,count(*) b4  from da_household where v22='一般农户' group by v1,v2,v3,v4,v5
) d5 on d1.v1_name=d5.v1 and d1.v2_name=d5.v2 and d1.v3_name=d5.v3 and d1.v4_name=d5.v4 and d1.v5_name = d5.v5 left join (
select v1,v2,v3,v4,v5,count(*) b5  from da_household where v22='低保贫困户' group by v1,v2,v3,v4,v5
) d6 on d1.v1_name=d6.v1 and d1.v2_name=d6.v2 and d1.v3_name=d6.v3 and d1.v4_name=d6.v4 and d1.v5_name = d6.v5 left join(
select v1,v2,v3,v4,v5,count(*) b6  from da_household where v22='五保贫困户' group by v1,v2,v3,v4,v5
) d7 on d1.v1_name=d7.v1 and d1.v2_name=d7.v2 and d1.v3_name=d7.v3 and d1.v4_name=d7.v4 and d1.v5_name = d7.v5

X3:显示下辖行政区划贫困户数字
SQL:select v1_pkid,v1_name,v1_code,
v2_pkid,v2_name,v2_code,v3_pkid,v3_name,v3_code,v4_pkid,v4_name,v4_code,v5_pkid,v5_name,v5_code,b1,'time'
 from(
select t5.pkid v1_pkid, t5.com_name v1_name, t5.com_code v1_code, 
t4.pkid v2_pkid, t4.com_name v2_name, t4.com_code v2_code, 
t3.pkid v3_pkid, t3.com_name v3_name, t3.com_code v3_code, 
t2.pkid v4_pkid, t2.com_name v4_name, t2.com_code v4_code, 
t1.pkid v5_pkid, t1.com_name v5_name, t1.com_code v5_code from sys_company t1 
join sys_company t2 on t1.com_f_pkid=t2.pkid
join sys_company t3 on t2.com_f_pkid=t3.pkid
join sys_company t4 on t3.com_f_pkid=t4.pkid
join sys_company t5 on t4.com_f_pkid=t5.pkid where t1.pkid>1
) d1 LEFT JOIN (
select v1,v2,v3,v4,v5,count(*) b1  from da_household group by v1,v2,v3,v4,v5
) d2 on d1.v1_name=d2.v1 and d1.v2_name=d2.v2 and d1.v3_name=d2.v3 and d1.v4_name=d2.v4 and d1.v5_name = d2.v5 

X4:贫困人口分布   下辖行政区划所选类型贫困人口的数量
SQL: select v1_pkid,v1_name,v1_code,
v2_pkid,v2_name,v2_code,v3_pkid,v3_name,v3_code,v4_pkid,v4_name,v4_code,v5_pkid,v5_name,v5_code,
b1_1,b1_2,b1_3,b1_4,b1_5,b2_1,b2_2,b2_3,b2_4,b2_5,'time'
 from(
select t5.pkid v1_pkid, t5.com_name v1_name, t5.com_code v1_code, 
t4.pkid v2_pkid, t4.com_name v2_name, t4.com_code v2_code, 
t3.pkid v3_pkid, t3.com_name v3_name, t3.com_code v3_code, 
t2.pkid v4_pkid, t2.com_name v4_name, t2.com_code v4_code, 
t1.pkid v5_pkid, t1.com_name v5_name, t1.com_code v5_code  from sys_company t1 
join sys_company t2 on t1.com_f_pkid=t2.pkid
join sys_company t3 on t2.com_f_pkid=t3.pkid
join sys_company t4 on t3.com_f_pkid=t4.pkid
join sys_company t5 on t4.com_f_pkid=t5.pkid where t1.pkid>1
) d1 LEFT JOIN (
select v1,v2,v3,v4,v5,count(*) b1_1 from da_household where sys_standard='国家级贫困人口' and V9='1' group by v1,v2,v3,v4,v5
) d2 on d1.v1_name=d2.v1 and d1.v2_name=d2.v2 and d1.v3_name=d2.v3 and d1.v4_name=d2.v4 and d1.v5_name = d2.v5 LEFT JOIN (
select v1,v2,v3,v4,v5,count(*) b1_2 from da_household where sys_standard='国家级贫困人口' and V9='2' group by v1,v2,v3,v4,v5
) d3 on d1.v1_name=d2.v1 and d1.v2_name=d3.v2 and d1.v3_name=d3.v3 and d1.v4_name=d3.v4 and d1.v5_name = d3.v5  LEFT JOIN (
select v1,v2,v3,v4,v5,count(*) b1_3 from da_household where sys_standard='国家级贫困人口' and V9='3' group by v1,v2,v3,v4,v5
) d4 on d1.v1_name=d4.v1 and d1.v2_name=d4.v2 and d1.v3_name=d4.v3 and d1.v4_name=d4.v4 and d1.v5_name = d4.v5  LEFT JOIN (
select v1,v2,v3,v4,v5,count(*) b1_4 from da_household where sys_standard='国家级贫困人口' and V9='4' group by v1,v2,v3,v4,v5
) d5 on d1.v1_name=d5.v1 and d1.v2_name=d5.v2 and d1.v3_name=d5.v3 and d1.v4_name=d5.v4 and d1.v5_name = d5.v5  LEFT JOIN (
select v1,v2,v3,v4,v5,count(*) b1_5 from da_household where sys_standard='国家级贫困人口' and V9>='5' group by v1,v2,v3,v4,v5
) d6 on d1.v1_name=d6.v1 and d1.v2_name=d6.v2 and d1.v3_name=d6.v3 and d1.v4_name=d6.v4 and d1.v5_name = d6.v5 

LEFT JOIN (
select v1,v2,v3,v4,v5,count(*) b2_1 from da_household where sys_standard='国家级贫困人口' and V9='1' group by v1,v2,v3,v4,v5
) d2_1 on d1.v1_name=d2_1.v1 and d1.v2_name=d2_1.v2 and d1.v3_name=d2_1.v3 and d1.v4_name=d2_1.v4 and d1.v5_name = d2_1.v5 LEFT JOIN (
select v1,v2,v3,v4,v5,count(*) b2_2 from da_household where sys_standard='国家级贫困人口' and V9='2' group by v1,v2,v3,v4,v5
) d3_1 on d1.v1_name=d2_1.v1 and d1.v2_name=d3_1.v2 and d1.v3_name=d3_1.v3 and d1.v4_name=d3_1.v4 and d1.v5_name = d3_1.v5  LEFT JOIN (
select v1,v2,v3,v4,v5,count(*) b2_3 from da_household where sys_standard='国家级贫困人口' and V9='3' group by v1,v2,v3,v4,v5
) d4_1 on d1.v1_name=d4_1.v1 and d1.v2_name=d4_1.v2 and d1.v3_name=d4_1.v3 and d1.v4_name=d4_1.v4 and d1.v5_name = d4_1.v5  LEFT JOIN (
select v1,v2,v3,v4,v5,count(*) b2_4 from da_household where sys_standard='国家级贫困人口' and V9='4' group by v1,v2,v3,v4,v5
) d5_1 on d1.v1_name=d5_1.v1 and d1.v2_name=d5_1.v2 and d1.v3_name=d5_1.v3 and d1.v4_name=d5_1.v4 and d1.v5_name = d5_1.v5  LEFT JOIN (
select v1,v2,v3,v4,v5,count(*) b2_5 from da_household where sys_standard='国家级贫困人口' and V9>='5' group by v1,v2,v3,v4,v5
) d6_1 on d1.v1_name=d6_1.v1 and d1.v2_name=d6_1.v2 and d1.v3_name=d6_1.v3 and d1.v4_name=d6_1.v4 and d1.v5_name = d6_1.v5 

X5:统计图：显示 辖区本级的贫困人口文化程度构成情况（文盲/小学/初中/高中/大专等）
SQL:select v1_pkid,v1_name,v1_code,
v2_pkid,v2_name,v2_code,v3_pkid,v3_name,v3_code,v4_pkid,v4_name,v4_code,v5_pkid,v5_name,v5_code,b1,b2,b3,b4,b5,b6,'time'
 from(
select t5.pkid v1_pkid, t5.com_name v1_name, t5.com_code v1_code, 
t4.pkid v2_pkid, t4.com_name v2_name, t4.com_code v2_code, 
t3.pkid v3_pkid, t3.com_name v3_name, t3.com_code v3_code, 
t2.pkid v4_pkid, t2.com_name v4_name, t2.com_code v4_code, 
t1.pkid v5_pkid, t1.com_name v5_name, t1.com_code v5_code from sys_company t1 
join sys_company t2 on t1.com_f_pkid=t2.pkid
join sys_company t3 on t2.com_f_pkid=t3.pkid
join sys_company t4 on t3.com_f_pkid=t4.pkid
join sys_company t5 on t4.com_f_pkid=t5.pkid where t1.pkid>1
) d1 LEFT JOIN (
select v1,v2,v3,v4,v5,count(*) b1  from da_household where v12='学龄前儿童' group by v1,v2,v3,v4,v5
) d2 on d1.v1_name=d2.v1 and d1.v2_name=d2.v2 and d1.v3_name=d2.v3 and d1.v4_name=d2.v4 and d1.v5_name = d2.v5 left  join (
select v1,v2,v3,v4,v5,count(*) b2  from da_household where v12='文盲或半文盲' group by v1,v2,v3,v4,v5
) d3 on d1.v1_name=d3.v1 and d1.v2_name=d3.v2 and d1.v3_name=d3.v3 and d1.v4_name=d3.v4 and d1.v5_name = d3.v5 left  join(
select v1,v2,v3,v4,v5,count(*) b3  from da_household where v12='小学' group by v1,v2,v3,v4,v5
) d4 on d1.v1_name=d4.v1 and d1.v2_name=d4.v2 and d1.v3_name=d4.v3 and d1.v4_name=d4.v4 and d1.v5_name = d4.v5 left join(
select v1,v2,v3,v4,v5,count(*) b4  from da_household where v12='初中' group by v1,v2,v3,v4,v5
) d5 on d1.v1_name=d5.v1 and d1.v2_name=d5.v2 and d1.v3_name=d5.v3 and d1.v4_name=d5.v4 and d1.v5_name = d5.v5 left join (
select v1,v2,v3,v4,v5,count(*) b5  from da_household where v12='高中' group by v1,v2,v3,v4,v5
) d6 on d1.v1_name=d6.v1 and d1.v2_name=d6.v2 and d1.v3_name=d6.v3 and d1.v4_name=d6.v4 and d1.v5_name = d6.v5 left join(
select v1,v2,v3,v4,v5,count(*) b6  from da_household where v12='大专及以上' group by v1,v2,v3,v4,v5
) d7 on d1.v1_name=d7.v1 and d1.v2_name=d7.v2 and d1.v3_name=d7.v3 and d1.v4_name=d7.v4 and d1.v5_name = d7.v5
X6:数据表格：显示下辖行政区划贫困人口数字
SQL:select v1_pkid,v1_name,v1_code,
v2_pkid,v2_name,v2_code,v3_pkid,v3_name,v3_code,v4_pkid,v4_name,v4_code,v5_pkid,v5_name,v5_code,
b1,b2,b3,b4,b5,'time'
 from(
select t5.pkid v1_pkid, t5.com_name v1_name, t5.com_code v1_code, 
t4.pkid v2_pkid, t4.com_name v2_name, t4.com_code v2_code, 
t3.pkid v3_pkid, t3.com_name v3_name, t3.com_code v3_code, 
t2.pkid v4_pkid, t2.com_name v4_name, t2.com_code v4_code, 
t1.pkid v5_pkid, t1.com_name v5_name, t1.com_code v5_code  from sys_company t1 
join sys_company t2 on t1.com_f_pkid=t2.pkid
join sys_company t3 on t2.com_f_pkid=t3.pkid
join sys_company t4 on t3.com_f_pkid=t4.pkid
join sys_company t5 on t4.com_f_pkid=t5.pkid where t1.pkid>1
) d1 LEFT JOIN (
select v1,v2,v3,v4,v5,count(*) b1 from da_household where V9='1' group by v1,v2,v3,v4,v5
) d2 on d1.v1_name=d2.v1 and d1.v2_name=d2.v2 and d1.v3_name=d2.v3 and d1.v4_name=d2.v4 and d1.v5_name = d2.v5 LEFT JOIN (
select v1,v2,v3,v4,v5,count(*) b2 from da_household where  V9='2' group by v1,v2,v3,v4,v5
) d3 on d1.v1_name=d2.v1 and d1.v2_name=d3.v2 and d1.v3_name=d3.v3 and d1.v4_name=d3.v4 and d1.v5_name = d3.v5  LEFT JOIN (
select v1,v2,v3,v4,v5,count(*) b3 from da_household where V9='3' group by v1,v2,v3,v4,v5
) d4 on d1.v1_name=d4.v1 and d1.v2_name=d4.v2 and d1.v3_name=d4.v3 and d1.v4_name=d4.v4 and d1.v5_name = d4.v5  LEFT JOIN (
select v1,v2,v3,v4,v5,count(*) b4 from da_household where V9='4' group by v1,v2,v3,v4,v5
) d5 on d1.v1_name=d5.v1 and d1.v2_name=d5.v2 and d1.v3_name=d5.v3 and d1.v4_name=d5.v4 and d1.v5_name = d5.v5  LEFT JOIN (
select v1,v2,v3,v4,v5,count(*) b5 from da_household where V9>='5' group by v1,v2,v3,v4,v5
) d6 on d1.v1_name=d6.v1 and d1.v2_name=d6.v2 and d1.v3_name=d6.v3 and d1.v4_name=d6.v4 and d1.v5_name = d6.v5 
X7:贫困户的家庭收入情况 
地图显示：下辖行政区划 贫困户的家庭平均收入
SQL:select v1_pkid,v1_name,v1_code,
v2_pkid,v2_name,v2_code,v3_pkid,v3_name,v3_code,v4_pkid,v4_name,v4_code,v5_pkid,v5_name,v5_code,b1,'time'
 from(
select t5.pkid v1_pkid, t5.com_name v1_name, t5.com_code v1_code, 
t4.pkid v2_pkid, t4.com_name v2_name, t4.com_code v2_code, 
t3.pkid v3_pkid, t3.com_name v3_name, t3.com_code v3_code, 
t2.pkid v4_pkid, t2.com_name v4_name, t2.com_code v4_code, 
t1.pkid v5_pkid, t1.com_name v5_name, t1.com_code v5_code  from sys_company t1 
join sys_company t2 on t1.com_f_pkid=t2.pkid
join sys_company t3 on t2.com_f_pkid=t3.pkid
join sys_company t4 on t3.com_f_pkid=t4.pkid
join sys_company t5 on t4.com_f_pkid=t5.pkid where t1.pkid>1
) d1 LEFT JOIN (
select v1,v2,v3,v4,v5,sum(v24*v9) b1 from da_household group by v1,v2,v3,v4,v5
) d2 on d1.v1_name=d2.v1 and d1.v2_name=d2.v2 and d1.v3_name=d2.v3 and d1.v4_name=d2.v4 and d1.v5_name = d2.v5  

X8:统计图：辖区本级的家庭收入分组
SQL:

X9:数据表格：各分组的贫困户数据
SQL: 


X10:致贫原因
地图：下辖区划的（致贫原因顺位第一位，如因病）的贫困户的分布；
SQL:select v1_pkid,v1_name,v1_code,
v2_pkid,v2_name,v2_code,v3_pkid,v3_name,v3_code,v4_pkid,v4_name,v4_code,v5_pkid,v5_name,v5_code,b1,b2,b3,b4,b5,b6,
b7,b8,b9,b10,b11,b12,b13,b14,'time'
 from(
select t5.pkid v1_pkid, t5.com_name v1_name, t5.com_code v1_code, 
t4.pkid v2_pkid, t4.com_name v2_name, t4.com_code v2_code, 
t3.pkid v3_pkid, t3.com_name v3_name, t3.com_code v3_code, 
t2.pkid v4_pkid, t2.com_name v4_name, t2.com_code v4_code, 
t1.pkid v5_pkid, t1.com_name v5_name, t1.com_code v5_code from sys_company t1 
join sys_company t2 on t1.com_f_pkid=t2.pkid
join sys_company t3 on t2.com_f_pkid=t3.pkid
join sys_company t4 on t3.com_f_pkid=t4.pkid
join sys_company t5 on t4.com_f_pkid=t5.pkid where t1.pkid>1
) d1 LEFT JOIN (
select v1,v2,v3,v4,v5,count(*) b1  from da_household where v23='因病' group by v1,v2,v3,v4,v5
) d2 on d1.v1_name=d2.v1 and d1.v2_name=d2.v2 and d1.v3_name=d2.v3 and d1.v4_name=d2.v4 and d1.v5_name = d2.v5 left  join (
select v1,v2,v3,v4,v5,count(*) b2  from da_household where v23='因残' group by v1,v2,v3,v4,v5
) d3 on d1.v1_name=d3.v1 and d1.v2_name=d3.v2 and d1.v3_name=d3.v3 and d1.v4_name=d3.v4 and d1.v5_name = d3.v5 left  join(
select v1,v2,v3,v4,v5,count(*) b3  from da_household where v23='因学' group by v1,v2,v3,v4,v5
) d4 on d1.v1_name=d4.v1 and d1.v2_name=d4.v2 and d1.v3_name=d4.v3 and d1.v4_name=d4.v4 and d1.v5_name = d4.v5 left join(
select v1,v2,v3,v4,v5,count(*) b4  from da_household where v23='因灾' group by v1,v2,v3,v4,v5
) d5 on d1.v1_name=d5.v1 and d1.v2_name=d5.v2 and d1.v3_name=d5.v3 and d1.v4_name=d5.v4 and d1.v5_name = d5.v5 left join (
select v1,v2,v3,v4,v5,count(*) b5  from da_household where v23='缺土地' group by v1,v2,v3,v4,v5
) d6 on d1.v1_name=d6.v1 and d1.v2_name=d6.v2 and d1.v3_name=d6.v3 and d1.v4_name=d6.v4 and d1.v5_name = d6.v5 left join(
select v1,v2,v3,v4,v5,count(*) b6  from da_household where v23='缺住房' group by v1,v2,v3,v4,v5
) d7 on d1.v1_name=d7.v1 and d1.v2_name=d7.v2 and d1.v3_name=d7.v3 and d1.v4_name=d7.v4 and d1.v5_name = d7.v5 left join(
select v1,v2,v3,v4,v5,count(*) b7  from da_household where v23='缺水' group by v1,v2,v3,v4,v5
) d8 on d1.v1_name=d8.v1 and d1.v2_name=d8.v2 and d1.v3_name=d8.v3 and d1.v4_name=d8.v4 and d1.v5_name = d8.v5 left join(
select v1,v2,v3,v4,v5,count(*) b8  from da_household where v23='缺电' group by v1,v2,v3,v4,v5
) d9 on d1.v1_name=d9.v1 and d1.v2_name=d9.v2 and d1.v3_name=d9.v3 and d1.v4_name=d9.v4 and d1.v5_name = d9.v5 left join(
select v1,v2,v3,v4,v5,count(*) b9  from da_household where v23='缺技术' group by v1,v2,v3,v4,v5
) d10 on d1.v1_name=d10.v1 and d1.v2_name=d10.v2 and d1.v3_name=d10.v3 and d1.v4_name=d10.v4 and d1.v5_name = d10.v5 left join(
select v1,v2,v3,v4,v5,count(*) b10  from da_household where v23='缺劳动力' group by v1,v2,v3,v4,v5
) d11 on d1.v1_name=d11.v1 and d1.v2_name=d11.v2 and d1.v3_name=d11.v3 and d1.v4_name=d11.v4 and d1.v5_name = d11.v5 left join(
select v1,v2,v3,v4,v5,count(*) b11  from da_household where v23='缺资金' group by v1,v2,v3,v4,v5
) d12 on d1.v1_name=d12.v1 and d1.v2_name=d12.v2 and d1.v3_name=d12.v3 and d1.v4_name=d12.v4 and d1.v5_name = d12.v5 left join(
select v1,v2,v3,v4,v5,count(*) b12  from da_household where v23='交通条件落后' group by v1,v2,v3,v4,v5
) d13 on d1.v1_name=d13.v1 and d1.v2_name=d13.v2 and d1.v3_name=d13.v3 and d1.v4_name=d13.v4 and d1.v5_name = d13.v5 left join(
select v1,v2,v3,v4,v5,count(*) b13  from da_household where v23='自身发展动力不足' group by v1,v2,v3,v4,v5
) d14 on d1.v1_name=d14.v1 and d1.v2_name=d14.v2 and d1.v3_name=d14.v3 and d1.v4_name=d14.v4 and d1.v5_name = d14.v5 left join(
select v1,v2,v3,v4,v5,count(*) b14  from da_household where v23='其他' group by v1,v2,v3,v4,v5
) d15 on d1.v1_name=d15.v1 and d1.v2_name=d15.v2 and d1.v3_name=d15.v3 and d1.v4_name=d15.v4 and d1.v5_name = d15.v5 
统计图：本级行政区划的各类致贫原因的顺位排序（由高到低）
数据表格：各行政区划的主要致贫原因 人口数字。
X11:贫困人口健康状况
地图显示：本辖区下级各行政区划大病贫困人口的分布
SQL:select v1_pkid,v1_name,v1_code,
v2_pkid,v2_name,v2_code,v3_pkid,v3_name,v3_code,v4_pkid,v4_name,v4_code,v5_pkid,v5_name,v5_code,b1,b2,b3,b4,'time'
 from(
select t5.pkid v1_pkid, t5.com_name v1_name, t5.com_code v1_code, 
t4.pkid v2_pkid, t4.com_name v2_name, t4.com_code v2_code, 
t3.pkid v3_pkid, t3.com_name v3_name, t3.com_code v3_code, 
t2.pkid v4_pkid, t2.com_name v4_name, t2.com_code v4_code, 
t1.pkid v5_pkid, t1.com_name v5_name, t1.com_code v5_code from sys_company t1 
join sys_company t2 on t1.com_f_pkid=t2.pkid
join sys_company t3 on t2.com_f_pkid=t3.pkid
join sys_company t4 on t3.com_f_pkid=t4.pkid
join sys_company t5 on t4.com_f_pkid=t5.pkid where t1.pkid>1
) d1 LEFT JOIN (
select v1,v2,v3,v4,v5,count(*) b1  from da_household where v14='健康' group by v1,v2,v3,v4,v5
) d2 on d1.v1_name=d2.v1 and d1.v2_name=d2.v2 and d1.v3_name=d2.v3 and d1.v4_name=d2.v4 and d1.v5_name = d2.v5 left  join (
select v1,v2,v3,v4,v5,count(*) b2  from da_household where v14='患有大病' group by v1,v2,v3,v4,v5
) d3 on d1.v1_name=d3.v1 and d1.v2_name=d3.v2 and d1.v3_name=d3.v3 and d1.v4_name=d3.v4 and d1.v5_name = d3.v5 left  join(
select v1,v2,v3,v4,v5,count(*) b3  from da_household where v14='残疾' group by v1,v2,v3,v4,v5
) d4 on d1.v1_name=d4.v1 and d1.v2_name=d4.v2 and d1.v3_name=d4.v3 and d1.v4_name=d4.v4 and d1.v5_name = d4.v5 left join(
select v1,v2,v3,v4,v5,count(*) b4  from da_household where v14='长期慢性病' group by v1,v2,v3,v4,v5
) d5 on d1.v1_name=d5.v1 and d1.v2_name=d5.v2 and d1.v3_name=d5.v3 and d1.v4_name=d5.v4 and d1.v5_name = d5.v5 
统计图：本级贫困人口的健康状况的分组情况。

数据表格：各健康状况分组的数据。

X12:贫困人口务工情况
地图显示：下辖行政区划 贫困户的长期务工人口分布
SQL:select v1_pkid,v1_name,v1_code,
v2_pkid,v2_name,v2_code,v3_pkid,v3_name,v3_code,v4_pkid,v4_name,v4_code,v5_pkid,v5_name,v5_code,b1,b2,b3,'time'
 from(
select t5.pkid v1_pkid, t5.com_name v1_name, t5.com_code v1_code, 
t4.pkid v2_pkid, t4.com_name v2_name, t4.com_code v2_code, 
t3.pkid v3_pkid, t3.com_name v3_name, t3.com_code v3_code, 
t2.pkid v4_pkid, t2.com_name v4_name, t2.com_code v4_code, 
t1.pkid v5_pkid, t1.com_name v5_name, t1.com_code v5_code from sys_company t1 
join sys_company t2 on t1.com_f_pkid=t2.pkid
join sys_company t3 on t2.com_f_pkid=t3.pkid
join sys_company t4 on t3.com_f_pkid=t4.pkid
join sys_company t5 on t4.com_f_pkid=t5.pkid where t1.pkid>1
) d1 LEFT JOIN (
select v1,v2,v3,v4,v5,count(*) b1  from da_household where v17 <=3 group by v1,v2,v3,v4,v5
) d2 on d1.v1_name=d2.v1 and d1.v2_name=d2.v2 and d1.v3_name=d2.v3 and d1.v4_name=d2.v4 and d1.v5_name = d2.v5 left  join (
select v1,v2,v3,v4,v5,count(*) b2  from da_household where v17 <=6 group by v1,v2,v3,v4,v5
) d3 on d1.v1_name=d3.v1 and d1.v2_name=d3.v2 and d1.v3_name=d3.v3 and d1.v4_name=d3.v4 and d1.v5_name = d3.v5 left  join(
select v1,v2,v3,v4,v5,count(*) b3  from da_household where v17 >=12 group by v1,v2,v3,v4,v5
) d4 on d1.v1_name=d4.v1 and d1.v2_name=d4.v2 and d1.v3_name=d4.v3 and d1.v4_name=d4.v4 and d1.v5_name = d4.v5 


X13:统计图：辖区本级的各类务工人口构成。
SQL:
