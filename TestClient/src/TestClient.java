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



public class TestClient {

	private static final int REGISTER_PLAYER = 0X0D0001;// 注册玩家

	private static final int GATHER_SELL = 0X0D000B;// 合售包
	
	private static final int LIST_GINGER=0x0d0401;//姜饼人列表

	private static java.util.concurrent.atomic.AtomicInteger index = new AtomicInteger();;
	static long use = System.currentTimeMillis();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		use = System.currentTimeMillis();

		new TestClient().createLink();

	}

	/**
	 * @author liuzhigang 开始建立连接
	 */
	public void createLink() {
		HttpURLConnection conn = null;
		try {
			System.out.println("开始建立连接");
			URL url = new URL("http://192.168.0.243:12001/test.lzg");// 58.30.47.158
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(10000);
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Charset", "UTF-8");

			conn.connect();

			OutputStream out = conn.getOutputStream();
			DataOutputStream dos = new DataOutputStream(out);
			byte[] sendData = send();
			dos.writeInt(sendData.length);
			dos.write(sendData);
			System.out.println("发送数据长度:" + sendData.length);
			out.flush();
			out.close();

			InputStream in = conn.getInputStream();
			int length = in.available();
			byte[] data = new byte[length];
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
	 * @return 发送信息
	 */
	public byte[] send() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			int cmd = LIST_GINGER;
			dos.writeInt(cmd);
			switch (cmd) {
			case REGISTER_PLAYER:
				sendRegister(dos);
				break;
			case GATHER_SELL:
				sendGatherSell(dos);
				break;
			case LIST_GINGER:
				sendGingerList(dos);
				break;
			default:
				return new byte[0];
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new byte[0];
		}
		return baos.toByteArray();

	}
    public void sendGingerList(DataOutputStream dos){
    	 try {
    			dos.writeUTF("liuzg");
    			dos.writeUTF("test");
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    }
	public void sendGatherSell(DataOutputStream dos) {
       try {
		dos.writeUTF("liuzg");
		dos.writeUTF("test");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

	/**
	 * @author liuzhigang
	 * @return 发送注册请求
	 */
	public void sendRegister(DataOutputStream dos) {
		try {
			index.getAndIncrement();
			dos.writeUTF("");// imei
			dos.writeUTF("liuzg0008" + index);
			dos.writeUTF("123456");
			dos.writeUTF("男");
			byte[] image = new byte[] { 1, 2, 3, 4, 5, 6 };
			dos.writeInt(image.length);
			dos.write(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @author liuzhigang
	 * @param data
	 *            接收信息
	 */
	public void receive(byte[] data) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(data);
			DataInputStream dis = new DataInputStream(bais);
			int realLen = dis.readInt();
			int cmd = dis.readInt();
			switch (cmd) {
			case REGISTER_PLAYER:
				receiveRegister(dis);
				break;
			case GATHER_SELL:
				receiveGatherSell(dis);
				break;
			case LIST_GINGER:
				receiveGingerList(dis);
				break;
			}
			System.out.println("收到返回信息:realLen=" + realLen + ",cmd=0x"
					+ Integer.toHexString(cmd));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @author liuzhigang
	 * @param dis
	 */
    public void receiveGingerList(DataInputStream dis){
    	try {
			int size=dis.readInt();
			System.out.println("姜饼人数量:"+size);
			for(int i=1;i<=size;i++){
				String id=dis.readUTF();
				String name=dis.readUTF();
				System.out.println("id="+id+",name="+name);
				dis.readUTF();
				dis.readUTF();
				dis.readUTF();
				dis.readInt();
				dis.readUTF();
				dis.readInt();
				dis.readUTF();
				dis.readInt();
				dis.readUTF();
				dis.readInt();
				dis.readUTF();
				dis.readInt();
				dis.readUTF();
				dis.readInt();
				dis.readUTF();
				dis.readInt();
				dis.readUTF();
				dis.readInt();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	/**
	 * 
	 * @param dis
	 */
	public void receiveGatherSell(DataInputStream dis) {
		try {
			int size = dis.readInt();
			System.out.println("共收到合售包:" + size);
			for(int index=1;index<=size;index++){
//			BA.WRITEUTF(DATA._ID);// GM数据库使用
			String id=dis.readUTF();
//			ba.writeUTF(data.nick_name);// 名字
			String nick_name=dis.readUTF();
//			ba.writeUTF(data.gathers_sell_desc);// 道具描述
			String gathers_sell_desc=dis.readUTF();
//			ba.writeUTF(data.package_icon);// 合售包图标的ID
			String package_icon=dis.readUTF();
//			ba.writeInt(data.unlock_stage);// 解锁关卡
			int unlock_stage=dis.readInt();
//			ba.writeInt(data.currency);// 银币 2.金币
			int currency=dis.readInt();
//			ba.writeInt(data.price);// 价格
			int price=dis.readInt();
//			ba.writeInt(data.tab);// 物品类型 1.道具 2.材料
			int tab=dis.readInt();
//			ba.writeInt(data.order);// 物品显示顺序,0为下架
			int order=dis.readInt();
//			ba.writeInt(data.label);// 物品标签,0为无标签
			int label=dis.readInt();
			int num =dis.readInt();
			System.out.println("id:="+id+","+nick_name+","+gathers_sell_desc+","+package_icon+","+unlock_stage+","+currency+","+price
					+","+tab+","+order+","+label+","+num);
			for (int i=1;i<=num;i++) {
//				ba.writeUTF(entry.getValue().getItem_index());// 合售包内的物品ID
				String item_index=dis.readUTF();
//				ba.writeInt(entry.getValue().getNum());// 合售包内的物品数量
				int item_num=dis.readInt();
			}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @author liuzhigang
	 * @param dis
	 *            接收注册返回的信息
	 */
	public void receiveRegister(DataInputStream dis) {
		try {

			// boolean isSuccess=dis.readBoolean();
			// if(isSuccess){//注册成功
			// String id=dis.readUTF();
			// System.out.println("注册成功,id="+id);
			// }else{
			// String desc=dis.readUTF();
			// System.out.println("注册失败原因是:"+desc);
			// }
			int size = dis.readInt();
			System.out.println("共收到物品:" + size);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
