package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import models.Document;

public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/Records";
    private static final String USER = "postgres";
    private static final String PASSWORD = "codex";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void initializeTables() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            // Create users table
            String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "username VARCHAR(255) PRIMARY KEY, " +
                    "password VARCHAR(255) NOT NULL, " +
                    "role VARCHAR(50) NOT NULL" +
                    ")";
            stmt.execute(createUsersTable);

            // Create documents table
            String createDocsTable = "CREATE TABLE IF NOT EXISTS documents (" +
                    "document_id INT PRIMARY KEY, " +
                    "title VARCHAR(255) NOT NULL, " +
                    "author VARCHAR(255) NOT NULL" +
                    ")";
            stmt.execute(createDocsTable);

        } catch (SQLException e) {
            System.err.println("Database Initialization Error: " + e.getMessage());
        }
    }

    public static boolean addUser(String username, String password, String role) {
        String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error adding user: " + e.getMessage());
            return false;
        }
    }

    public static String authenticateAndGetRole(String username, String password) {
        String query = "SELECT role FROM users WHERE username = ? AND password = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("role");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
        }
        return null; // Null indicates authentication failed
    }

    public static boolean insertDocument(Document doc) {
        String query = "INSERT INTO documents (document_id, title, author) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, doc.documentId);
            pstmt.setString(2, doc.title);
            pstmt.setString(3, doc.author);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error inserting document to DB: " + e.getMessage());
            return false;
        }
    }

    public static boolean deleteDocument(int documentId) {
        String query = "DELETE FROM documents WHERE document_id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, documentId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting document from DB: " + e.getMessage());
            return false;
        }
    }

    public static ArrayList<Document> getAllDocuments() {
        ArrayList<Document> docs = new ArrayList<>();
        String query = "SELECT document_id, title, author FROM documents";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("document_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                docs.add(new Document(id, title, author));
            }
        } catch (SQLException e) {
            System.err.println("Error loading documents from DB: " + e.getMessage());
        }
        return docs;
    }
}
