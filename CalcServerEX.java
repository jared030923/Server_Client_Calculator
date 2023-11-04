import java.io.*;
import java.net.*;
import java.util.*;
public class CalcServerEX {
	
	public static String calc(String exp) throws DivideByZeroException{
		StringTokenizer st = new StringTokenizer(exp, " ");
		if (st.countTokens() != 3)
			return "Error : More than 2 tokens are entered.";
		String res = "";
		int op1 = Integer.parseInt(st.nextToken());
		String opcode = st.nextToken();
		int op2 = Integer.parseInt(st.nextToken());
		switch (opcode) {
			case "+":
				res = Integer.toString(op1 + op2);
				break;
			case "-":
				res = Integer.toString(op1 - op2);
				break;
			case "*":
				res = Integer.toString(op1 * op2);
				break;
			case "/":
				if (op2 == 0)
					return "Error : Divide By Zero Exception!";
				res = Integer.toString(op1 / op2);
				break;
			default:
				res = "error";
		}
		return res;
	}
	
	public static void main(String[] args) {
	BufferedReader in = null;
	BufferedWriter out = null;
	ServerSocket listener = null;
	Socket socket = null;
	String res;
	try {
		listener = new ServerSocket(9999); // 서버 소켓 생성
		System.out.println("Waiting for connect.....");
		socket = listener.accept(); // 클라이언트로부터 연결 요청 대기System.out.println("연결되었습니다.");
		in = new BufferedReader(
		new InputStreamReader(socket.getInputStream()));
		out = new BufferedWriter(
		new OutputStreamWriter(socket.getOutputStream()));
		String inputMessage;
		while (true) {
			inputMessage = in.readLine();
			if (inputMessage.equalsIgnoreCase("bye")) {
				System.out.println("Client disconnected.");
				break; // "bye"를 받으면 연결 종료
			}
			System.out.println(inputMessage); // 받은 메시지를 화면에출력
			res = calc(inputMessage); // 계산. 계산 결과는res
			out.write(res + "\n"); // 계산 결과 문자열 전송
			out.flush();
		}
	}
	catch (Exception e) {
		System.out.println(e.getMessage());
	}
	finally {
		try {
			if (socket != null)
				socket.close(); // 통신용 소켓 닫기
			if (listener != null)
				listener.close(); // 서버 소켓 닫기
		}
			catch (IOException e) {
				System.out.println("Exception occured while communicating with client.");
			}
		}
	}
}