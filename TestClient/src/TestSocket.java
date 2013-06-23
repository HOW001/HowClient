import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;



public class TestSocket {
	public static ThreadPool threadPool;// 线程池
	private int num=0;
	private int localPort;
	public static void main(String str[]) {
		int runs=1;
		if(str.length==1){
			runs=Integer.parseInt(str[0]);
		}
		threadPool = new ThreadPool();
		threadPool.start();// 启动线程池
		TestSocket socket=new TestSocket();
		socket.init(runs);
	    while(true){
	    	try {
				Thread.sleep(1000*30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }	
	}
	public final void init(int runs){
		for(int index=1;index<=runs;index++){
			Runnable run=new Runnable(){
				public void run(){
					TestSocket socket=new TestSocket();
					socket.start();
				}
			};
			threadPool.run(run);
		}
	}
    public void start(){
    	try {
			Socket socket =new Socket("127.0.0.1",60000);
			InputStream in=socket.getInputStream();
			OutputStream out=socket.getOutputStream();
			localPort=socket.getLocalPort();
			byte [] data=send(socket.getLocalPort()+"");
			out.write(data);
			out.flush();
			while(true){
				int len=in.available();
				if(len>=4){
					byte[] ints=new byte[4];
					in.read(ints);
					ByteArray ba=new ByteArray(ints);
					int avlid=ba.readInt();
					if(avlid==19820708){
						 data=new byte[len-4];
						 in.read(data);
						 receive(data,out);
					}
				}
			   Thread.sleep(3000);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * @author liuzg
     * @param data
     * 接收的数据
     */
    public void receive(byte []data,OutputStream out){
    	try {
			ByteArray ba=new ByteArray(data);
			int len=ba.readInt();
			byte[] bytes=ba.readByteArray(len);
			ba=new ByteArray(bytes);
			int sit=ba.readByte();
			int cmd=ba.readInt();
//			int number=ba.readInt();
			String str=ba.readUTF();
			System.out.println("收到信息:str="+str);
			data=send(str);
			out.write(data);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	/**
	 * @author liuzg
	 * @param str
	 * @return
	 * 发送数据
	 */
	public byte[] send(String str){
		if(num>=Integer.MAX_VALUE-10){
			num=0;
			str=localPort+"";
		}
		ByteArray ba=new ByteArray();
		ba.writeByte(1);
		ba.writeInt(0x010001);
		ba.writeUTF(str);
		byte[] data=ba.toArray();
		ba=new ByteArray();
		ba.writeInt(19820708);
		ba.writeInt(data.length);
		ba.writeByteArray(data);
		return ba.toArray();
	}
}
