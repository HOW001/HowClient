import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;


public class TestHttp {
	public static void main(String[] args) {
    	writeInfo();
    }
    public static void writeInfo(){
    	 HttpURLConnection conn = null;
 		try {
 			System.out.println("开始建立连接");
// 			URL url = new URL("http://127.0.0.1:61000/test.lzg?command=0x10001&msg=Hello");
 			URL url = new URL("http://127.0.0.1:12001/test.lzg");
// 				ByteArrayOutputStream baos=new ByteArrayOutputStream();
// 				XMLOutputFactory factory=XMLOutputFactory.newInstance();
// 				XMLStreamWriter writer=factory.createXMLStreamWriter(baos);
// 				writer.writeStartDocument();
// 				writer.writeStartElement("data");
// 					
// 				writer.writeStartElement("command");
// 				writer.writeCharacters("0x010001");
// 				writer.writeEndElement();
// 				
// 				writer.writeStartElement("msg");
// 				writer.writeCharacters("Hello,World!");
// 				writer.writeEndElement();				
// 				
// 				writer.writeEndElement();
// 				writer.writeEndDocument();
// 				writer.flush();
// 				writer.close();
// 	           byte [] sendData=baos.toByteArray();
 				conn = (HttpURLConnection) url.openConnection();
 				
 				conn.setConnectTimeout(30000);
 				conn.setReadTimeout(10000);
 				conn.setUseCaches(false);
 				conn.setDoInput(true);	
 				conn.setDoOutput(true);			
 				conn.setRequestMethod("GET");
 				conn.connect();	
 				
// 				OutputStream out=conn.getOutputStream();
 				
// 				ByteArray ba=new ByteArray();
// 				ba.writeInt(0x10001);
// 				ba.writeUTF("liuzg");
// 				byte[] sendData=ba.toArray();
// 				ba=new ByteArray();
// 				ba.writeInt(sendData.length);
// 				ba.writeByteArray(sendData);
// 				sendData=ba.toArray();
// 				out.write(sendData);
// 				out.flush();

 				InputStream in=conn.getInputStream();
 				int length = in.available();
 				System.out.println("收到数据长度:"+length);
 			InputStreamReader isr=new InputStreamReader(in);
 	   		BufferedReader br=new BufferedReader(isr);
 	   		System.out.println("收到信息");
 	   		String line="";
 	   		while((line=br.readLine())!=null){
 	   			System.out.println(line);
 	   		}
 		} catch (Exception e) {
 			e.printStackTrace();
 		} finally {
 			conn.disconnect();
 		}
    }
    public static void writeInfo2(){
    	try {
			// Post请求的url，与get不同的是不需要带参数
			URL postUrl = new URL("http://127.0.0.1:9001/test.lzg");
			// 打开连接
			HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
			// Output to the connection. Default is
			// false, set to true because post
			// method must write something to the
			// connection
			// 设置是否向connection输出，因为这个是post请求，参数要放在
			// http正文内，因此需要设为true
			connection.setDoOutput(true);
			// Read from the connection. Default is true.
			connection.setDoInput(true);
			// Set the post method. Default is GET
			connection.setRequestMethod("POST");
			// Post cannot use caches
			// Post 请求不能使用缓存
			connection.setUseCaches(false);
			// This method takes effects to
			// every instances of this class.
			// URLConnection.setFollowRedirects是static函数，作用于所有的URLConnection对象。
			// connection.setFollowRedirects(true);
 
			// This methods only
			// takes effacts to this
			// instance.
			// URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
			connection.setInstanceFollowRedirects(true);
			// Set the content type to urlencoded,
			// because we will write
			// some URL-encoded content to the
			// connection. Settings above must be set before connect!
			// 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
			// 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
			// 进行编码
			connection.setRequestProperty("Content-Type",
			        "application/x-www-form-urlencoded");
			// 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
			// 要注意的是connection.getOutputStream会隐含的进行connect。
			connection.connect();
			DataOutputStream out = new DataOutputStream(connection
			        .getOutputStream());
			// The URL-encoded contend
			// 正文，正文内容其实跟get的URL中'?'后的参数字符串一致
			String content = "aaaaaaaaaaaaaa";
			// DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面
			out.writeBytes(content); 
			out.flush();
			out.close(); // flush and close
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));//设置编码,否则中文乱码
			String line="";
			System.out.println("=============================");
			System.out.println("Contents of post request");
			System.out.println("=============================");
			while ((line = reader.readLine()) != null){
			    //line = new String(line.getBytes(), "utf-8");
			    System.out.println(line);
			}
			System.out.println("=============================");
			System.out.println("Contents of post request ends");
			System.out.println("=============================");
			reader.close();
			connection.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
    }
    public static void  writeInfo3(){
    	 HttpURLConnection conn = null;
 		try {
 			System.out.println("开始建立连接");
 			URL url = new URL("http://127.0.0.1:9001/test.lzg?command=0x10001");
 				conn = (HttpURLConnection) url.openConnection();		
 				conn.setConnectTimeout(30000);
 				conn.setReadTimeout(10000);
 				conn.setUseCaches(false);
 				conn.setDoInput(true);	
 				conn.setDoOutput(true);			
 				conn.setRequestMethod("POST");
 				conn.connect();	
 				
 				OutputStream out=conn.getOutputStream();
 				
 				ByteArray ba=new ByteArray();
 				ba.writeByte(1);
 				ba.writeInt(0x10001);
 				ba.writeUTF("liuzg");
 				byte[] sendData=ba.toArray();
 				ba=new ByteArray();
 				ba.writeInt(sendData.length);
 				ba.writeByteArray(sendData);
 				sendData=ba.toArray();
 				out.write(sendData);
 				out.flush();

 				InputStream in=conn.getInputStream();
 				int length = in.available();
 	   		    System.out.println("收到信息");
 	   		    DataInputStream dis=new DataInputStream(in);
 	   		    int len=dis.readInt();
 	   		    int command=dis.readInt();
 	   		    String sss=dis.readUTF();
 	   		    System.out.println(command+sss);
 		} catch (Exception e) {
 			e.printStackTrace();
 		} finally {
 			conn.disconnect();
 		}
    }
}
