import java.sql.*;
import java.util.UUID;

public final class WorkWithOracle {

	private static Connection connection;
	private static volatile WorkWithOracle instance;

	private WorkWithOracle(Connection connection) { this.connection = connection; }

	public static WorkWithOracle getInstance(Connection connection){
		if (instance == null) {
			synchronized (WorkWithOracle.class) {
				if (instance == null) {
					instance = new WorkWithOracle(connection);
				}
			}
		}
		return instance;
	}

	public ResultSet select() throws SQLException {
		String query = "SELECT * FROM dept";
		Statement stmt = connection.createStatement();
		ResultSet resultSet = stmt.executeQuery(query);
//		while (resultSet.next()) {
//			Integer deptno = resultSet.getInt("deptno");
//			String dname = resultSet.getString("dname");
//			String loc = resultSet.getString("loc");
//			System.out.println(String.format("Dept [%d, %s, %s]", deptno, dname, loc));
//		}
//		System.out.println("--- ALL ROWS ARE FETCHED ---");
		return resultSet;
	}

	public void insertExample() throws SQLException {
		String query = "INSERT INTO users (user_id, first_name, last_name, age) VALUES (?, ?, ?, ?)";

		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setObject(1, UUID.randomUUID());
		preparedStatement.setString(2, "Jhon");
		preparedStatement.setString(3, "Snow");
		preparedStatement.setInt(4, 100);

		preparedStatement.execute();
		System.out.println("--- INSERTED 1 ROW ---");
	}

	public void updateExample() throws SQLException {
		String query = "UPDATE users SET first_name = ?, last_name = ?, age = ? WHERE user_id = ?";

		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, "Arthas");
		preparedStatement.setString(2, "Menethil");
		preparedStatement.setInt(3, 200);
		preparedStatement.setObject(4, UUID.fromString("6f47de63-d61a-472b-ba8b-bf4365d46122"));

		preparedStatement.execute();
		System.out.println("--- UPDATED 1 ROW ---");
	}

	public void deleteExample() throws SQLException {
		String query = "DELETE FROM users";
		Statement statement = connection.createStatement();
		statement.execute(query);
		System.out.println("--- DELETED ALL ROWS ---");
	}
}