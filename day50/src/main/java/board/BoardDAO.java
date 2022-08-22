package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import util.Util;

public class BoardDAO {
 
	Connection conn=null;
	PreparedStatement pstmt=null;
	final String sql_selectAll="SELECT * FROM(SELECT * FROM BOARD ORDER BY BID DESC) WHERE ROWNUM <= ?"; 
	// 오라클 사용시 실행순서 유의하며 작성
	final String sql_selectAll_R="SELECT * FROM REPLY WHERE BID=? ORDER BY RID DESC";
	final String sql_insert="INSERT INTO BOARD(BID,MID,MSG) VALUES((SELECT NVL(MAX(BID),0)+1 FROM BOARD),?,?)";
	final String sql_insert_R="INSERT INTO REPLY(RID,MID,BID,RMSG) VALUES((SELECT NVL(MAX(RID),0)+1 FROM REPLY),?,?,?)";
	final String sql_delete="DELETE FROM BOARD WHERE BID=?";
	final String sql_delete_R="DELETE FROM REPLY WHERE RID=?";
	final String sql_update="UPDATE BOARD SET FAVCNT=FAVCNT+1 WHERE BID=?";
	public ArrayList<BoardSet> selectAll(BoardVO bvo){ // 유지보수 용이
		ArrayList<BoardSet> datas=new ArrayList<BoardSet>();
		conn=Util.connect();
		try {
			System.out.println("1");
			pstmt=conn.prepareStatement(sql_selectAll);
			System.out.println("2");
			pstmt.setInt(1, bvo.getCnt());
			System.out.println("4");
			ResultSet rs=pstmt.executeQuery();
			System.out.println("3");
			while(rs.next()) {
				BoardSet bs=new BoardSet();
				BoardVO boardVO=new BoardVO();
				boardVO.setBid(rs.getInt("BID"));
				boardVO.setFavcnt(rs.getInt("FAVCNT"));
				boardVO.setMid(rs.getString("MID"));
				boardVO.setMsg(rs.getString("MSG"));
				boardVO.setRcnt(rs.getInt("RCNT")); ///// rList.size();
				bs.setBoardVO(boardVO);
				
				ArrayList<ReplyVO> rList=new ArrayList<ReplyVO>();
				pstmt=conn.prepareStatement(sql_selectAll_R);
				pstmt.setInt(1, rs.getInt("BID")); // 현재 BID
				ResultSet rs2=pstmt.executeQuery();
				while(rs2.next()) {
					ReplyVO replyVO=new ReplyVO();
					
					replyVO.setBid(rs2.getInt("BID"));
					replyVO.setMid(rs2.getString("MID"));
					replyVO.setRid(rs2.getInt("RID"));
					replyVO.setRmsg(rs2.getString("RMSG"));
					
					rList.add(replyVO);
				}
				bs.setrList(rList);				
				
				datas.add(bs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.disconnect(pstmt, conn);
		}
		return datas;
	}
	public boolean insert(BoardVO bvo) {
		conn=Util.connect();
		try {
			pstmt=conn.prepareStatement(sql_insert);
			pstmt.setString(1, bvo.getMid());
			pstmt.setString(2, bvo.getMsg());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			Util.disconnect(pstmt, conn);
		}
		return true;
	}
	public boolean delete(BoardVO bvo) {
		conn=Util.connect();
		try {
			pstmt=conn.prepareStatement(sql_delete);
			pstmt.setInt(1,bvo.getBid());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			Util.disconnect(pstmt, conn);
		}
		return true;
	}
	public boolean insertR(ReplyVO rvo) {
		conn=Util.connect();
		try {
			pstmt=conn.prepareStatement(sql_insert_R);
			pstmt.setString(1, rvo.getMid());
			pstmt.setInt(2, rvo.getBid());
			pstmt.setString(3, rvo.getRmsg());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			Util.disconnect(pstmt, conn);
		}
		return true;
	}
	public boolean deleteR(ReplyVO rvo) {
		conn=Util.connect();
		try {
			pstmt=conn.prepareStatement(sql_delete_R);
			pstmt.setInt(1, rvo.getRid());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			Util.disconnect(pstmt, conn);
		}
		return true;
	}
	public boolean update(BoardVO bvo) {
		conn=Util.connect();
		try {
			pstmt=conn.prepareStatement(sql_update);
			pstmt.setInt(1, bvo.getBid());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			Util.disconnect(pstmt, conn);
		}
		return true;
	}

}
