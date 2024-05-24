package Network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

// 서버를 설정하는 메인 클래스
public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket; // 서버 소켓 선언
        Socket socket; // 클라이언트 연결을 위한 소켓 선언

        try {
            // 포트 7777에서 대기하는 서버 소켓 생성
            serverSocket = new ServerSocket(7777);
            System.out.println("!!! 서버가 준비됐습니다"); // 서버가 준비되었음을 알림
            // 클라이언트 연결 수락 (블로킹 호출)
            socket = serverSocket.accept();
            System.out.println("클라이언트가 연결됨: " + socket.getInetAddress() + ":" + socket.getPort());
            // 연결된 클라이언트를 위한 송신자와 수신자 스레드 생성
            Sender sender = new Sender(socket);
            Receiver receiver = new Receiver(socket);

            // 송신자와 수신자 스레드 시작
            sender.start();
            receiver.start();
        } catch (IOException e) {
            e.printStackTrace(); // IO 예외 발생 시 스택 트레이스 출력
        }
    }
}

// 클라이언트에게 메시지를 보내기 위한 스레드 클래스
class Sender extends Thread {
    Socket socket; // 클라이언트 연결을 위한 소켓
    DataOutputStream out; // 클라이언트에게 데이터를 보내기 위한 출력 스트림
    String name; // 클라이언트의 이름 식별자

    // 생성자는 소켓과 출력 스트림을 초기화
    Sender(Socket socket) {
        this.socket = socket;
        try {
            // 소켓으로부터 출력 스트림 생성
            out = new DataOutputStream(socket.getOutputStream());
            // 클라이언트의 IP 주소와 포트 번호를 이용하여 이름 설정
            name = "[" + socket.getInetAddress() + ":" + socket.getPort() + "]";
        } catch (IOException e) {
            e.printStackTrace(); // IO 예외 발생 시 스택 트레이스 출력
        }
    }

    @Override
    public void run() {
        // 콘솔에서 사용자 입력을 읽기 위한 스캐너
        Scanner sc = new Scanner(System.in);
        // 출력 스트림이 null이 아닐 때까지 계속 반복
        while (out != null) {
            try {
                // 사용자 입력을 클라이언트의 이름과 함께 출력 스트림에 전송
                out.writeUTF(name + sc.nextLine());
            } catch (IOException e) {
                e.printStackTrace(); // IO 예외 발생 시 스택 트레이스 출력
            }
        }
    }
}

// 클라이언트로부터 메시지를 받기 위한 스레드 클래스
class Receiver extends Thread {
    Socket socket; // 클라이언트 연결을 위한 소켓
    DataInputStream in; // 클라이언트로부터 데이터를 받기 위한 입력 스트림

    // 생성자는 소켓과 입력 스트림을 초기화
    Receiver(Socket socket) {
        this.socket = socket;
        try {
            // 소켓으로부터 입력 스트림 생성
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace(); // IO 예외 발생 시 스택 트레이스 출력
        }
    }

    @Override
    public void run() {
        // 입력 스트림이 null이 아닐 때까지 계속 반복
        while (in != null) {
            try {
                // 입력 스트림으로부터 메시지를 읽어와서 출력
                System.out.println(in.readUTF());
            } catch (IOException e) {
                e.printStackTrace(); // IO 예외 발생 시 스택 트레이스 출력
            }
        }
    }
}
