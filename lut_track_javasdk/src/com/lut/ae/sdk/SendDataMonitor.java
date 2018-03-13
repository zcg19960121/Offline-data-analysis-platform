package com.lut.ae.sdk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 发送url数据的监控者，用于启动一个单独的线程来发送数据
 * 
 * @author gg
 *
 */
public class SendDataMonitor {
	// 日志记录对象
	private static final Logger log = Logger.getGlobal();
	// 阻塞队列，用于存储发送的url
	private BlockingQueue<String> queue = new LinkedBlockingQueue<>();
	// 用于单例模式的一个类对象
	private static SendDataMonitor monitor = null;

	private SendDataMonitor() {
		// 私有构造方法，进行单列模式的创建
	}

	/**
	 * 获取单例的monitor对象
	 * 
	 * @return
	 */
	public static SendDataMonitor getSendDataMonitor() {
		if (monitor == null) {
			synchronized (SendDataMonitor.class) {
				if (monitor == null) {
					monitor = new SendDataMonitor();
					Thread thread = new Thread(new Runnable() {

						@Override
						public void run() {
							// 线程中调用具体的处理方法
							SendDataMonitor.monitor.run();
						}
					});
					// 测试的时候，不设置为守护模式
					//thread.setDaemon(true);
					thread.start();
				}
			}
		}
		return monitor;
	}

	/**
	 * 具体执行发送url的方法
	 * 
	 */
	private void run() {
		while (true) {
			try {
				String url = this.queue.take();
				// 调用内部类的方法,正式地发送url
				HttpRequestUtil.sendData(url);
			} catch (Throwable e) {
				log.log(Level.WARNING, "发送url异常", e);
			}
		}
	}

	/**
	 * 添加一个url到队列中去
	 * 
	 * @param url
	 * @throws InterruptedException
	 */
	public static void addSendUrl(String url) throws InterruptedException {
		getSendDataMonitor().queue.put(url);
	}

	public static class HttpRequestUtil {

		public static void sendData(String url) throws IOException {
			HttpURLConnection conn = null;
			BufferedReader in = null;
			try {
				// 创建url对象
				URL obj = new URL(url);
				// 打开url连接
				conn = (HttpURLConnection) obj.openConnection();
				// 设置连接参数
				conn.setConnectTimeout(5000);// 连接过期时间
				conn.setReadTimeout(5000);// 读取数据过期时间
				conn.setRequestMethod("GET");// 设置请求类型为get
				System.out.println("发送url:" + url);
				// 发送连接请求
				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} finally {
				try {
					if (in != null) {
						in.close();
					}
				} catch (Throwable e) {
					// nothing
				}
				try {
					conn.disconnect();
				} catch (Throwable e) {
					// nothing
				}
			}

		}

	}

}
