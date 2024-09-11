// import java.io.IOException;
// import java.io.OutputStream;
// import java.io.PrintStream;
// import java.net.ServerSocket;
// import java.net.Socket;
// import java.net.UnknownHostException;

// public class ServerExample {

//     public static void main(String[] args){   
//         try{
//             ServerSocket serverSocket = new ServerSocket(1300);
//             Socket socket = serverSocket.accept();

//             OutputStream outputStream= socket.getOutputStream();
//             PrintStream printStream = new PrintStream(outputStream);
//             printStream.println("Hello from server");
//             socket.close();
//             serverSocket.close();

//         }
//         catch(UnknownHostException e){
//             throw new RuntimeException(e);

//         }catch(IOException e){
//             throw new RuntimeException(e);
//         }
//     }
// }
