import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {
    public static void main(String[] args) throws IOException {
        try(ServerSocket serverSocket = new ServerSocket(8189)) {
            System.out.println("Server is running on the port 8189.");
            Socket socket = serverSocket.accept();
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            System.out.println("Client is connected");
            String msg;
            int count = 0;
            while (true) {
                msg = in.readUTF();

                if(msg.equals("/stat")){
                    out.writeUTF("Number of messages " + count);
                }
                else{
                    count++;
                    System.out.print(msg);
                    out.writeUTF(msg);
                }

            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

