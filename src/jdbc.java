import java.sql.*;

public class jdbc {
    private Connection con=null;
    private Statement st=null;
    private PreparedStatement pst=null;
    private String strconinfo;
    private String databasetype;

    public jdbc(String databasetype,String strconinfo) {
        this.strconinfo = strconinfo;
        this.databasetype = databasetype;
    }

    public void setStrconinfo(String strconinfo) {
        this.strconinfo = strconinfo;
    }

    public void close(){
        try{
            pst.close();
            st.close();
            con.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int con(){       //加载驱动，获取连接 0成功，1失败
        if(databasetype.equals("mySQL")){
            try{
                Class.forName("com.mysql.jdbc.Driver");
                con= DriverManager.getConnection(strconinfo);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return 1;
            } catch (SQLException e) {
                e.printStackTrace();
                return 1;
            }
        }
        else if(databasetype.equals("SQL")){
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                con=DriverManager.getConnection(strconinfo);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return 1;
            } catch (SQLException e) {
                e.printStackTrace();
                return 1;
            }
        }
        return 0;
    }

    public int executeModify(String sql){    //执行sql语句实行更改，0成功，1失败
        if(con()!=0){
            return 1;
        }
        try {
            st=con.createStatement();
            st.execute(sql);
            close();
        } catch (SQLException e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    public ResultSet excuteQue(String sql){    //执行查询，返回结果集，失败返回null
        if(con()!=0){                          //一定在主程序中调用close函数，这里调用会导致resultset被关闭1
            return null;
        }
        ResultSet rs=null;
        try{
            st=con.createStatement();
            rs=st.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
}
