import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;

public class ToClient extends Thread {

	private Socket clientSocket;
	private BufferedReader reader;
	private PrintWriter writer;
	private WorkWithOracle workWithOracle = WorkWithOracle.getInstance(getOracleConnection());

	private Connection getOracleConnection() {
		OracleDriverManager driverManager = new OracleDriverManager();
		return driverManager.openOracleConnection();
	}

	public ToClient(Socket clientSocket) throws IOException {
		this.clientSocket = clientSocket;
		reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		writer = new PrintWriter(clientSocket.getOutputStream());
		start();

	}
	@Override
	public void run(){
		try {
			writer = new PrintWriter(clientSocket.getOutputStream(), true);
			reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			boolean isConnect = true;

			while (true) {
				writer.println("1st massage");
				String line = reader.readLine();
				System.out.println(line);
				while (isConnect) {
					line = reader.readLine();
					switch (line) {
						case "0": {
							System.out.println("Работа с " + clientSocket.toString() + " окончена!");
							isConnect = false;
							reader.close();
							writer.close();
						}
						break;
						case "1": {
							ResultSet result = workWithOracle.select();
							while (result.next()) {
								Integer deptno = result.getInt("deptno");
								String dname = result.getString("dname");
								String loc = result.getString("loc");
								System.out.println(String.format("Dept [%d, %s, %s]", deptno, dname, loc));
								writer.println(String.format("Dept [%d, %s, %s]", deptno, dname, loc));
							}
							writer.println("--- ALL ROWS ARE FETCHED ---");
							writer.println("End");
							System.out.println("Данные для - " + clientSocket.toString());
						}
						break;
					}
				}
			}
		}catch (Exception e) {e.getMessage();}
	}
}