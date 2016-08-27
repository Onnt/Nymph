package cn.blacard.dbopera.opera;

import java.lang.reflect.Field;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import cn.blacard.dbopera.para.DBConnectPara;

/**
 * <T> T:�����Ƿ���.����������Object ����
 * @author Administrator
 *
 * @param <T>
 */
public class QueryObject<T> extends OperaBase{
	
	private DBConnectPara para;
	public QueryObject(DBConnectPara para){
		this.para = para;
	}

	public QueryObject(){
		super();
	}
	private void openConn(){
		if(this.para == null){
			openConnect();
			if(conn==null){
				log.warning("Conn is NUll");
			}
		}else{
			openConnect(para);
			if(conn==null){
				log.warning("Conn is NUll");
			}
		}
	}
	public List<T> query(String sql, Object[] args, Class clazz) {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// �洢���صļ��϶���
		List<T> list = new ArrayList<T>();
		
		try {
			// ʹ�÷����ȡ��������Ϣ(ʵ���������--��ݿ�����Ӧ����ֶ�)
			Field[] fields = clazz.getDeclaredFields();
			
			Method method = null;

			openConn();
			pstmt = conn.prepareStatement(sql);
			
			
			// ���sql����в�����args����ѭ�����丳ֵ
			if (args != null && args.length > 0) {
				for (int i = 0; i < args.length; i++) {
					pstmt.setObject(i + 1, args[i]);// ����λ�úŴ�1��ʼ
				}
			}
			rs = pstmt.executeQuery();
			// ��ȡ����Ԫ���
			ResultSetMetaData rsmd = rs.getMetaData();//�õ�Ԫ��ݼ�
			// ��ȡһ���ж�����
			int columnCount = rsmd.getColumnCount();//�õ�sql��䵽�ײ�ѯ�˶��ٸ��ֶ�
	
			
			while (rs.next()) {
				// ����һ���¶���
				T obj = (T) clazz.newInstance();//����һ��ʵ��
				
				// ȡ���Ľ�����кŴ�1��ʼ
				for (int i = 1; i <= columnCount; i++) {
					
					String cname = rsmd.getColumnName(i);// ��ȡÿһ��[��ݱ������]����� 
					int ctype = rsmd.getColumnType(i);// ��ȡÿһ��[��ݿ����������]���������
					
					for (Field f : fields) {
						if (cname.equalsIgnoreCase(f.getName()))// ���������ֶ�����ͬ
						{
							// ��ʼ��װ���
							String methodName = "set"
									+ f.getName().substring(0, 1).toUpperCase()
									+ f.getName().substring(1);
							// ����е�typeֵ�����ͽ��д���
							switch (ctype) {
							
							case Types.INTEGER:
								method = clazz.getMethod(methodName,
										Integer.class);
								method.invoke(obj, rs.getInt(i));
								break;
							case Types.DATE:
							case Types.DATALINK:
							case Types.NVARCHAR:
							case Types.VARCHAR:
								method = clazz.getMethod(methodName,
										String.class);
								method.invoke(obj, rs.getString(i));
								break;
							default:method = clazz.getMethod(methodName,
									String.class);
							method.invoke(obj, rs.getString(i));
							}
						}
					}
				}
				list.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(null, pstmt, conn);
		}
		return list;
	}


	// ִ����ɾ�ķ���(update insert delete)
	public int executeSQL(String sql, Object[] args) {
		PreparedStatement pstmt = null;
		int count = 0;
		try {
			openConn();
			if(conn == null) System.out.println("conn . is null");
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			// ѭ����sql����ֵ
			if (args != null && args.length > 0) {
				for (int i = 0; i < args.length; i++) {
					pstmt.setObject(i + 1,args[i]);
				}
			}
			count = pstmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();// ���������쳣������ع�
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			System.out.println("��ݿ�����쳣��");
		} finally {
			closeAll(null, pstmt, conn);
		}
		return count;
	}
	
	// �ر�����
	public void closeAll(ResultSet rs, Statement stmt, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
