package kr.or.ksmart.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

@Repository
public class BoardDao { // Data Access Object
    // �ۼ��� �޼���
    public int updateBoard(Board board) {
        Connection connection = null;
        PreparedStatement statement = null;
        int row = 0;
        try {
            connection = this.getConnection();
            String sql = "UPDATE board2 SET board_title=?, board_content=? WHERE board_no=? AND board_pw=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, board.getBoardTitle());
            statement.setString(2, board.getBoardContent());
            statement.setInt(3, board.getBoardNo());
            statement.setString(4, board.getBoardPw());
            row = statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("���� �߻�");
        } finally {
            try {statement.close();} catch(Exception e){}
            try {connection.close();} catch(Exception e){}
        } 
        return row;
    }
    
    
    // �۹�ȣ�� ���н����带 �Է¹޾� �Ѱ��� �Խñ� ����
    public int deleteBoard(int boardNo, String boardPw) {
        Connection connection = null;
        PreparedStatement statement = null;
        int row = 0;
        try {
            connection = this.getConnection();
            String sql = "DELETE FROM board2 WHERE board_no=? AND board_pw=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, boardNo);
            statement.setString(2, boardPw);
            row = statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("���� �߻�");
        } finally {
            try {statement.close();} catch(Exception e){}
            try {connection.close();} catch(Exception e){}
        } 
        return row;
    }
    
    // �Ѱ��� �Խñ� ���뺸��
    public Board getBoard(int boardNo) {
        Board board = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = this.getConnection();
            String sql = "SELECT board_title, board_content, board_user, board_date FROM board2 WHERE board_no=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, boardNo);
            rs = statement.executeQuery();
            if(rs.next()) {
                board = new Board();
                board.setBoardNo(boardNo);
                board.setBoardTitle(rs.getString("board_title"));
                board.setBoardContent(rs.getString("board_content"));
                board.setBoardUser(rs.getString("board_user"));
                board.setBoardDate(rs.getString("board_date"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("���� �߻�");
        } finally {
            try {rs.close();} catch(Exception e){}
            try {statement.close();} catch(Exception e){}
            try {connection.close();} catch(Exception e){}
        }   
        return board;
    }
    
    // �Խñ� ���
    public ArrayList<Board> getBoardList(int currentPage, int pagePerRow) {
        ArrayList<Board> list = new ArrayList<Board>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = this.getConnection();
            String listSql = "SELECT board_no, board_title, board_user, board_date FROM board2 ORDER BY board_date DESC LIMIT ?, ?";
            statement = connection.prepareStatement(listSql);
            statement.setInt(1, (currentPage-1)*pagePerRow); 
            statement.setInt(2, pagePerRow); 
            rs = statement.executeQuery();
            while(rs.next()) {
                Board board = new Board();
                board.setBoardNo(rs.getInt("board_no"));
                board.setBoardTitle(rs.getString("board_title"));
                board.setBoardUser(rs.getString("board_user"));
                board.setBoardDate(rs.getString("board_date"));
                list.add(board);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("���� �߻�");
        } finally {
            try {rs.close();} catch(Exception e){}
            try {statement.close();} catch(Exception e){}
            try {connection.close();} catch(Exception e){}
        }
        return list;
    }
    
    // ��ü �� ī��Ʈ
    public int getBoardCount() {
        int count = 0; 
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = this.getConnection();
            String totalSql = "SELECT COUNT(*) FROM board2"; // board���̺��� ��ü���� ���� ��ȯ
            statement = connection.prepareStatement(totalSql);
            rs = statement.executeQuery();
            if(rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("���� �߻�");
        } finally {
            try {rs.close();} catch(Exception e){}
            try {statement.close();} catch(Exception e){}
            try {connection.close();} catch(Exception e){}
        }
        return count;
    }
    
    // �۾��� �޼���
    public int insertBoard(Board board) {
        Connection connection = null;
        PreparedStatement statement = null;
        int row = 0;
        try {
            connection = this.getConnection();
            String sql = "INSERT INTO board2(board_pw, board_title, board_content, board_user, board_date) values(?,?,?,?,now())";
            statement = connection.prepareStatement(sql);
            statement.setString(1,board.getBoardPw());
            statement.setString(2,board.getBoardTitle());
            statement.setString(3,board.getBoardContent());
            statement.setString(4,board.getBoardUser());
            row = statement.executeUpdate(); // insert ������ ����
        } catch(Exception e) {
            e.printStackTrace();
            System.out.print("���� �߻� insertBoard");
        } finally {
            try {statement.close();} catch(Exception e){}
            try {connection.close();} catch(Exception e){}
        }
        return row;
    }
    
    // �޼��� ���� ����̺� �ε��� Connection�� ���ϴ� �ڵ尡 �ߺ��Ǿ� �ϳ��� �޼���� �����Ͽ���
    public Connection getConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        String dbUrl = "jdbc:mysql://127.0.0.1:3306/board_psm?useUnicode=true&characterEncoding=utf-8";
        String dbUser = "psm11id";
        String dbPw = "psm11pw";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPw);
        return connection;
    }
}
