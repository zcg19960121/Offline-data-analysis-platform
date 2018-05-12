/**
 * 定义oozie的数据流<br/>
 * 1. 将oozie包下的所有文件(除package-info.java外)上传到/zhcg/transformer/oozie目录下
 * 2. 启动hadoop、hbase、hive、oozie以及自定义的所有服务和mysql。
 * 3. 将cron下的job.properties上传到linux系统，执行oozie job -oozie http://hadoop-work:11000/oozie -config ./job.properties -run
 * 4. oozie部署完成
 * 
 * 注意：mr/lib文件下的lut_transformer-0.0.1-SNAPSHOT.jar请自行maven打包，执行命令为maven clean, maven install即可(之前需要修改代码)
 * 
 * @author gg
 *
 */
package com.lut.oozie;