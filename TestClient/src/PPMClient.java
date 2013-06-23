import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;


public class PPMClient {

	private static final int REGISTER_PLAYER=0X010001;//注册玩家
	private static final int LOGIN_PLAYER=0X010002;//登录玩家
	private static final int CHANGE_IMAGE_PLAYER=0X010003;//修改玩家头像
	private static final int TEST_ROBOT=0X010099;//测试机器人
	
	
	private static final int FRIEND_LIST=0X030001;//获取好友列表
	private static final int FRIEND_ADD=0X030002;//添加好友
	private static final int FRIEND_REMOVE=0X030003;//删除好友
	
	
	private static java.util.concurrent.atomic.AtomicInteger index=new AtomicInteger();;
	static long use=System.currentTimeMillis();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		use=System.currentTimeMillis();
		for (int j = 1; j <=1; j++) {
			Runnable run = new Runnable() {
				public void run() {
					for (int i = 1; i <= 100; i++) {
						new PPMClient().createLink();
						System.out.println("完成第"+index+"角色创建!");
						System.out.println("共耗时:"+(System.currentTimeMillis()-use)+"毫秒");
					}
				}
			};
			new Thread(run).start();
		}
		
	}
	/**
	 * @author liuzhigang
	 * 开始建立连接
	 */
    public void createLink(){
    	 HttpURLConnection conn = null;
			try {
				System.out.println("开始建立连接");
					URL url = new URL("http://192.168.0.188:12001/test.lzg");//58.30.47.158
					conn = (HttpURLConnection) url.openConnection();		
					conn.setConnectTimeout(30000);
					conn.setReadTimeout(10000);
					conn.setUseCaches(false);
					conn.setDoInput(true);	
					conn.setDoOutput(true);			
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Charset", "UTF-8");
					
					conn.connect();	
					
					OutputStream out=conn.getOutputStream();
					DataOutputStream dos=new DataOutputStream(out);
					byte[] sendData=send();
					dos.writeInt(sendData.length);
					dos.write(sendData);
					System.out.println("发送数据长度:"+sendData.length);
					out.flush();
					out.close();

					InputStream in=conn.getInputStream();
					int length = in.available();
					byte[] data=new byte[length];
					in.read(data);
					receive(data);
					in.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				conn.disconnect();
			}
    }
    /**
     * @author liuzhigang
     * @return
     * 发送信息
     */
    public byte[] send(){
    	ByteArrayOutputStream baos=new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(baos);
    	try {
			int cmd=REGISTER_PLAYER;			
			dos.writeInt(cmd);
			switch(cmd){
			case REGISTER_PLAYER:
				sendRegister(dos);
				break;
			case LOGIN_PLAYER:
				sendLogin(dos);
				break;
			case CHANGE_IMAGE_PLAYER:
				sendChangeImage(dos);
				break;
			case FRIEND_LIST:
				sendListFriend(dos);
				break;
			case FRIEND_ADD:
				sendAddFriend(dos);
				break;
			case FRIEND_REMOVE:
				sendRemoveFriend(dos);
				break;
			case TEST_ROBOT:
				sendTestRobo(dos);
				break;
			default :
				return new byte[0];
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new byte[0];
		}
    	return baos.toByteArray();
    	
    }
   
    /**
     * @author liuzhigang
     * @return
     * 发送注册请求
     */
    public  void sendRegister(DataOutputStream dos){
    	try {
    		index.getAndIncrement();
			dos.writeUTF("");//imei
			dos.writeUTF("liuzg0008"+index);
			dos.writeUTF("123456");
			dos.writeUTF("男");
			byte[] image=new byte[]{1,2,3,4,5,6};
			dos.writeInt(image.length);
			dos.write(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * @author liuzhigang
     * @param dos
     * 发送登录请求
     */
    public  void sendLogin(DataOutputStream dos){
    	try {
    		dos.writeByte(1);
    		dos.writeUTF("51652f640364190186be207a");
//			dos.writeByte(2);//1.采用id的形式登录 2.采用昵称&密码的形式登录
//			dos.writeUTF("liuzg001");
//			dos.writeUTF("123456");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * @author liuzhigang
     * @param dos
     * 发送修改头像
     */
    public  void sendChangeImage(DataOutputStream dos){
    	try {
			dos.writeUTF("515aa988036401ff1d1c3aa4");//playerID;
			byte[] data=new byte[]{3,3,3,3,3,3,3,3,3,4,4,4,4,4};
			dos.writeInt(data.length);
			dos.write(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * @author liuzhigang
     * @param dos
     *  请求好友列表
     */
   public  void sendListFriend(DataOutputStream dos){
	   try {
		dos.writeUTF("5160d9120364e277597889f7");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   /**
    * @author liuzhigang
    * @param dos
    * 添加好友
    */
   public  void sendAddFriend(DataOutputStream dos){
	   try {
		dos.writeUTF("5160d9120364e277597889f7");//self_id
		   dos.writeUTF("liuzg002");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   /**
    * @author liuzhigang
    * @param dos
    * 发送机器人测试
    */
   public void sendTestRobo(DataOutputStream dos){
	   try {
		   /*
		    * 1.测试查找玩家
		    * 2.创建玩家
		    * 3.更新玩家
		    */
		dos.writeInt(3);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   /**
    * @author liuzhigang
    * @param dos
    * 删除好友
    */
   public  void sendRemoveFriend(DataOutputStream dos){
	   try {
		dos.writeUTF("5160d9120364e277597889f7");//self_id
		   dos.writeUTF("5160d9120364e277597889f7");//remove_friend_id
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
    /**
     * @author liuzhigang
     * @param data
     * 接收信息
     */
    public  void receive(byte[] data){
    	try {
			ByteArrayInputStream bais=new ByteArrayInputStream(data);
			DataInputStream dis=new DataInputStream(bais);
			int realLen=dis.readInt();
			int cmd=dis.readInt();
			switch(cmd){
			case REGISTER_PLAYER:
				receiveRegister(dis);
				break;
			case LOGIN_PLAYER:
				receiveLogin(dis);
				break;
			case CHANGE_IMAGE_PLAYER:
				receiveChangeImage(dis);
				break;
			case FRIEND_LIST:
				receiveListFriend(dis);
				break;
			case FRIEND_ADD:
				receiveAddFriend(dis);
				break;
			case FRIEND_REMOVE:
				receiveRemoveFriend(dis);
				break;
			case TEST_ROBOT:
				receiveTestRobot(dis);
				break;
			}
			System.out.println("收到登录返回信息:realLen="+realLen+",cmd=0x"+Integer.toHexString(cmd));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * @author liuzhigang
     * @param dis
     *  接收注册返回的信息
     */
    public  void receiveRegister(DataInputStream dis){
    	try {
			boolean isSuccess=dis.readBoolean();
			if(isSuccess){//注册成功
				String id=dis.readUTF();
				System.out.println("注册成功,id="+id);
			}else{
				String desc=dis.readUTF();
				System.out.println("注册失败原因是:"+desc);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
    /**
     * @author liuzhigang
     * @param dis
     * 接收登录返回的消息
     */
    public  void receiveLogin(DataInputStream dis){
    	try {
			boolean isLogin=dis.readBoolean();
			if(isLogin){//登录成功
				String id=dis.readUTF();
				System.out.println("登录成功:id="+id);
				int serverCount=dis.readInt();
				System.out.println("共有"+serverCount+"个服务器可供使用");
				for(int index=0;index<serverCount;index++){
					String name=dis.readUTF();
					String type=dis.readUTF();
					String url=dis.readUTF();
					System.out.println("name="+name+",type="+type+",url="+url);
				}
			}else{
				String desc=dis.readUTF();
				System.out.println("登录失败:desc="+desc);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * @author liuzhigang
     * @param dis
     * 接收修改头像的结果
     */
    public  void receiveChangeImage(DataInputStream dis){
    	try {
			boolean isSuccess=dis.readBoolean();
			System.out.println("头像修改成功"+isSuccess);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * @author liuzhigang
     * @param dis
     * 接收好友列表
     */
    public  void receiveListFriend(DataInputStream dis){
    	System.out.println("好友列表信息");
    	try {
			int count=dis.readInt();
			if(count<=0){
				System.out.println("没有好友");
			}
			for(int index=1;index<=count;index++){
				String id=dis.readUTF();
				String name=dis.readUTF();
				String sex=dis.readUTF();
				System.out.println(id+",name="+name+",sex="+sex);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * @author liuzhigang
     * @param dis
     * 添加好友
     */
    public  void receiveAddFriend(DataInputStream dis){
    	try {
			boolean isSuccess=dis.readBoolean();
			String result=dis.readUTF();
			System.out.println("添加好友结果:result="+result+",isSuccess="+isSuccess);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * @author liuzhigang
     * @param dis
     * 删除好友
     */
    public  void receiveRemoveFriend(DataInputStream dis){
    	try {
			boolean isSuccess=dis.readBoolean();
			String result=dis.readUTF();
			System.out.println("删除好友结果:result="+result+",isSuccess="+isSuccess);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * @author liuzhigang
     * @param dis
     * 测试机器人
     */
    public void receiveTestRobot(DataInputStream dis){
    	try {
			boolean isSuccess=dis.readBoolean();
			System.out.println("机器人测试返回:"+isSuccess);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
