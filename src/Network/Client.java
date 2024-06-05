package Network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;

import main.RentMainWindow;
import member.exception.MemberException;

public class Client {
    public static void main(String[] args) {
        new Client().start();
    }

    public void start() {
        Socket socket = null;
        try {
            String serverIp = "localhost"; // 서버 IP 주소 설정
            socket = new Socket(serverIp, 0417); // 서버에 연결
            System.out.println("서버에 연결됨"); // 서버에 연결되었음을 출력

            RentMainWindow mainWindow = new RentMainWindow();
            try {
                mainWindow.startFrame();
            } catch (MemberException | IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "오류 발생: " + e.getMessage());
            }

            // 서버와의 연결을 유지하면서 데이터를 주고받는 스레드를 시작합니다.
            new ServerHandler(socket).start();

        } catch (IOException e) {
            e.printStackTrace(); // IO 예외 발생 시 스택 트레이스 출력
        }
    }

    // 서버와의 통신을 처리하는 스레드 클래스
    class ServerHandler extends Thread {
        private Socket socket;

        public ServerHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                // 서버와의 통신을 처리하는 코드를 여기에 작성합니다.
                // 예: InputStream과 OutputStream을 사용하여 데이터 주고받기

                // 아래는 예시 코드입니다:
                 InputStream in = socket.getInputStream();
                 OutputStream out = socket.getOutputStream();

                 while (true) {
                     // 데이터 읽기 및 처리
                      int data = in.read();
                      System.out.println("서버로부터 받은 데이터: " + data);
                 }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (socket != null && !socket.isClosed()) {
                        socket.close();
                        System.out.println("서버 연결 종료");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
