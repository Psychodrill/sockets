import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.*;

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

    public void sendMessage(){
        try{
            bfWriter.write(name);
            bfWriter.newLine();
            bfWriter.flush();

            Scanner scanner = new Scanner(System.in);
            while(socket.isConnected()){
                String message = scanner.nextLine();
                bfWriter.write(name+ ": "+ message);
                bfWriter.newLine();
                bfWriter.flush();
            }
        }catch(IOException e){
            closeEverything(socket, bfReader, bfWriter);
        }

    }

    public void listenForMessage(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                String messageFromGroup;
                while(socket.isConnected()){
                    try{
                        messageFromGroup=bfReader.readLine();
                        System.out.println(messageFromGroup);
                    }catch(IOException e){
                        closeEverything(socket, bfReader, bfWriter);
                    }
                }
            }
        }).start();
    }


    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){

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

    public static void main(String[] args)throws IOException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input your name: ");
        String name = scanner.nextLine();
        Socket socket = new Socket("localhost",1300);
        Client client = new Client(socket, name);
        client.listenForMessage();
        client.sendMessage();
    }
}
