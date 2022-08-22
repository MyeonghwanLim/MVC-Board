package member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.Util;
import member.MemberVO;

public class MemberDAO {
	Connection conn;
	PreparedStatement pstmt;
	final String sql_selectOne="SELECT * FROM MEMBER WHERE MID=? AND MPW=?"; //로그인
	final String sql_insert="INSERT INTO MEMBER VALUES(?,?,?)"; // 회원가입
	final String sql_update="UPDATE MEMBER SET MPW=?,MNAME=? WHERE MID=?"; //회원정보 변경
	final String sql_delete="DELETE FROM MEMBER WHERE MID=? AND MPW=?"; //회원탈외
	public MemberVO selectOne(MemberVO vo) {
		System.out.println("MemberDAO selectOne의 로그: "+vo);
		conn=Util.connect();
		try {
			pstmt=conn.prepareStatement(sql_selectOne);
			pstmt.setString(1, vo.getMid());
			pstmt.setString(2, vo.getMpw());
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				MemberVO data=new MemberVO();
				data.setMid(rs.getString("MID"));
				data.setMname(rs.getString("MNAME"));
				data.setMpw(rs.getString("MPW"));
				
				return data;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.disconnect(pstmt, conn);
		}
		return null;
	}
	public boolean insert(MemberVO vo) {
		System.out.println("MemberDAO insert의 로그: "+vo);
		conn=Util.connect();
		try {
			pstmt=conn.prepareStatement(sql_insert);
			pstmt.setString(1, vo.getMid());
			pstmt.setString(2, vo.getMpw());
			pstmt.setString(3, vo.getMname());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			Util.disconnect(pstmt, conn);
		}
		return true;
	}
	public boolean update(MemberVO vo) {
		System.out.println("MemberDAO update의 로그: "+vo);
		conn=Util.connect();
		try {
			pstmt=conn.prepareStatement(sql_update);
			pstmt.setString(1, vo.getMpw());
			pstmt.setString(2, vo.getMname());
			pstmt.setString(3, vo.getMid());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			Util.disconnect(pstmt, conn);
		}
		return true;
	}
	public boolean delete(MemberVO vo) {
		System.out.println("MemberDAO delete의 로그: "+vo);
		conn=Util.connect();
		try {
			pstmt=conn.prepareStatement(sql_delete);
			pstmt.setString(1, vo.getMid());
			pstmt.setString(2, vo.getMpw());
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
