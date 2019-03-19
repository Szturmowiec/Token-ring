import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.MulticastSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class logger{
    public static void main(String args[])
    {
        MulticastSocket socket=null;
        int portNumber=10000;
        Date d=new Date();
        String file="log_"+d.toString();

        try{
            socket=new MulticastSocket(portNumber);
            InetAddress group=InetAddress.getByName("239.192.0.100");
            socket.joinGroup(group);
            byte[] buf=new byte[1024];
            while (true){
              DatagramPacket packet=new DatagramPacket(buf,buf.length);
              socket.receive(packet);
              String received=new String(packet.getData(),0,packet.getLength());

              try(PrintWriter f=new PrintWriter(new FileOutputStream(new File(file),true))){
                f.append(received+"\n");
              }
              catch(Exception e){
                e.printStackTrace();
              }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if (socket!=null){
                socket.close();
            }
        }
    }
}
