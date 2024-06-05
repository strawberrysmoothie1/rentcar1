package Network;

import car.VO.CarVO;
import car.dao.CarDAO;
import car.dao.CarDAOImpl;
import member.VO.MemberVO;
import member.dao.MemberDAO;
import member.dao.MemberDAOImpl;
import res.VO.ResVO; // 예약 정보에 대한 VO 클래스 import 추가
import res.dao.ResDAO; // 예약 DAO 클래스 import 추가
import res.dao.ResDAOImpl;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket;
        CarDAO carDAO = new CarDAOImpl();  // CarDAO 구현체 생성
        MemberDAO memberDAO = new MemberDAOImpl();  // MemberDAO 구현체 생성
        ResDAO resDAO = new ResDAOImpl();  // ResDAO 구현체 생성

        try {
            serverSocket = new ServerSocket(0417);  // 포트 0417에서 서버 소켓 생성
            System.out.println("!!! 서버가 준비됐습니다");

            while (true) {
                socket = serverSocket.accept();  // 클라이언트 연결 수락
                System.out.println("클라이언트가 연결됨: " +
                        socket.getInetAddress() + ":" + socket.getPort());

                // 새로운 클라이언트 핸들러 스레드를 생성하고 시작
                new ClientHandler(socket, carDAO, memberDAO, resDAO).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();  // 서버 소켓 닫기
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

// 클라이언트 요청을 처리하는 핸들러 스레드
class ClientHandler extends Thread {
    private Socket socket;  // 클라이언트 소켓
    private CarDAO carDAO;  // CarDAO 객체
    private MemberDAO memberDAO;  // MemberDAO 객체
    private ResDAO resDAO; // ResDAO 객체 추가

    public ClientHandler(Socket socket, CarDAO carDAO, MemberDAO memberDAO, ResDAO resDAO) {
        this.socket = socket;
        this.carDAO = carDAO;
        this.memberDAO = memberDAO;
        this.resDAO = resDAO; // ResDAO 객체 초기화
    }

    @Override
    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());  // 클라이언트로부터 입력 스트림 생성
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {  // 클라이언트로 출력 스트림 생성

            String command = (String) in.readObject();  // 명령어 읽기
            Object data = in.readObject();  // CarVO 또는 MemberVO 객체 읽기

            // 클라이언트가 수행하는 작업을 출력
            System.out.println("클라이언트가 " + command + " 작업을 수행함");

            switch (command) {
                case "listCarInfo":  // 차량 정보 조회
                    CarVO carVO = (CarVO) data;
                    List<CarVO> carList = carDAO.selectCarInfo(carVO);  // DAO를 통해 차량 정보 조회
                    out.writeObject(carList);  // 조회 결과를 클라이언트로 전송
                    break;
                case "regCarInfo":  // 차량 정보 등록
                    carVO = (CarVO) data;
                    carDAO.insertCarInfo(carVO);  // DAO를 통해 차량 정보 등록
                    break;
                case "modCarInfo":  // 차량 정보 수정
                    carVO = (CarVO) data;
                    carDAO.updateCarInfo(carVO);  // DAO를 통해 차량 정보 수정
                    break;
                case "removeCarInfo":  // 차량 정보 삭제
                    carVO = (CarVO) data;
                    carDAO.deleteCarInfo(carVO);  // DAO를 통해 차량 정보 삭제
                    break;
                case "listMember":  // 멤버 정보 조회
                    MemberVO memVO = (MemberVO) data;
                    List<MemberVO> memList = memberDAO.selectMember(memVO);  // DAO를 통해 멤버 정보 조회
                    out.writeObject(memList);  // 조회 결과를 클라이언트로 전송
                    break;
                case "regMember":  // 멤버 정보 등록
                    memVO = (MemberVO) data;
                    memberDAO.insertMember(memVO);  // DAO를 통해 멤버 정보 등록
                    break;
                case "modMember":  // 멤버 정보 수정
                    memVO = (MemberVO) data;
                    memberDAO.updateMember(memVO);  // DAO를 통해 멤버 정보 수정
                    break;
                case "removeMember":  // 멤버 정보 삭제
                    memVO = (MemberVO) data;
                    memberDAO.deleteMember(memVO);  // DAO를 통해 멤버 정보 삭제
                    break;
                case "listResInfo":  // 예약 정보 조회
                    ResVO resVO = (ResVO) data;
                    List<ResVO> resList = resDAO.selectResInfo(resVO);  // DAO를 통해 예약 정보 조회
                    out.writeObject(resList);  // 조회 결과를 클라이언트로 전송
                    break;
                case "regResInfo":  // 예약 정보 등록
                    resVO = (ResVO) data;
                    resDAO.insertResInfo(resVO);  // DAO를 통해 예약 정보 등록
                    break;
                case "modResInfo":  // 예약 정보 수정
                    resVO = (ResVO) data;
                    resDAO.updateResInfo(resVO);  // DAO를 통해 예약 정보 수정
                    break;
                case "cancelResInfo":  // 예약 정보 취소
                    resVO = (ResVO) data;
                    resDAO.deleteResInfo(resVO);  // DAO를 통해 예약 정보 취소
                    break;
                default:
                    System.out.println("알 수 없는 명령: " + command);
            }

        } catch (Exception e) {
            e.printStackTrace();  // 예외 처리
        }
    }
}
