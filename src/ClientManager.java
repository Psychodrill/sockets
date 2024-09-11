import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientManager implements Runnable {

    private Socket socket;
    private BufferedReader bfReader;
    private BufferedWriter bfWriter;
    private String name;
    public static ArrayList<ClientManager> clients = new ArrayList<ClientManager>();

    public ClientManager(Socket socket){
        try{
            this.socket=socket;
            bfWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bfReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            name = bfReader.readLine();
            clients.add(this);
            broadCastMessage("Server: "+ name+" has been connected");

        }catch(IOException e){
            closeEverything(socket, bfReader, bfWriter);
        }
    }

    private void broadCastMessage(String messageToSend) {
       for(ClientManager client: clients){
        try{
            if(!client.name.equals(name)){
                client.bfWriter.write(messageToSend);
                client.bfWriter.newLine();
                client.bfWriter.flush();
            }

        }catch(IOException e){
            closeEverything(socket, bfReader, bfWriter);
        }
       }
    }

    @Override
    public void run() {

        String messageFromClient;
        while(socket.isConnected()){
            try{
                messageFromClient = bfReader.readLine();
                broadCastMessage(messageFromClient);
            }catch(IOException e){
                closeEverything(socket, bfReader, bfWriter);
                break;
            }
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
