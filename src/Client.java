import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Client {

        private Socket socket;
    private BufferedReader bfReader;
    private BufferedWriter bfWriter;
    private String name;
    

    public Client(Socket socket, String userName){
        this.socket=socket;
        name = userName;
        try{
            
            bfWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bfReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));

           
        }catch(IOException e){
            closeEverything(socket, bfReader, bfWriter);
        }
    }

    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        removeClient();
        try{
            if(bufferedReader!=null){
                bufferedReader.close();
            }
            if(bufferedWriter!=null){
                bufferedWriter.close();
            }
            if(socket!=null){
                socket.close();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public void removeClient(){
        clients.remove(this);
        broadCastMessage("Server: "+ name+" has been disconnected");
    }
}
