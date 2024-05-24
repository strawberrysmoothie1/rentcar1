package Network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

// 클라이언트 프로그램의 메인 클래스
public class Client {
    public static void main(String[] args) {
        try {
            String serverIp = "localhost"; // 서버 IP 주소 설정
            Socket socket = new Socket(serverIp, 7777); // 서버에 연결
            System.out.println("서버에 연결됨"); // 서버에 연결되었음을 출력
            Sender sender = new Sender(socket); // 송신자 스레드 생성
            Receiver receiver = new Receiver(socket); // 수신자 스레드 생성

            sender.start(); // 송신자 스레드 시작
            receiver.start(); // 수신자 스레드 시작
        } catch (IOException e) {
            e.printStackTrace(); // IO 예외 발생 시 스택 트레이스 출력
        }
    }
}
