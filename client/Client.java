package TestChat.client;



import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Михаил on 04.11.2016.
 */
    public class Client {

   static DataInputStream in;
   static DataOutputStream out;

    static ViewForChat v;
        public static void main(String[] ar){
            v= new ViewForChat();
            v.outMessage();
            int serverPort = 6666; // здесь обязательно нужно указать порт к которому привязывается сервер.
            String address=v.getId();




            try {
                InetAddress ipAddress = InetAddress.getByName(address.trim()); // создаем объект который отображает вышеописанный IP-адрес.
                Socket socket = new Socket(ipAddress, serverPort); // создаем сокет используя IP-адрес и порт сервера.
                v.refresh("\nSimle CHAT\n");
                InputStream sin = socket.getInputStream();
                OutputStream sout = socket.getOutputStream();
                in = new DataInputStream(sin);
               out = new DataOutputStream(sout);
                new Reveicer().start();
                transmit();
              /*  while (true) {

                        line = v.sendM(); // ждем пока пользователь введет что-то и нажмет кнопку Enter.
                        Thread.sleep(100);
                        if (line == null) continue;
                        out.writeUTF(line); // отсылаем введенную строку текста серверу.
                        out.flush(); // заставляем поток закончить передачу данных.
                        v.sendClear();

                }*/
            } catch (Exception x) {
                x.printStackTrace();
            }
        }
    public static void transmit() throws Exception{
        String line1=null;

        while (true) {
              line1 = v.sendM(); // ждем пока пользователь введет что-то и нажмет кнопку Enter.
                if (line1 == null) continue;
                out.writeUTF(line1); // отсылаем введенную строку текста серверу.
                out.flush(); // заставляем поток закончить передачу данных.
                v.sendClear();
        }

    }

    public static class Reveicer extends Thread
    {
        @Override
        public void run() {

            String line=null;
            while(true) {

                    try {
                        line = in.readUTF();
                        v.refresh(line);
                    } catch (IOException e) {
                    }

            }
        }
    }

    }

