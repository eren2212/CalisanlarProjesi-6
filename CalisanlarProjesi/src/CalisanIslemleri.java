
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.DriverManager;
import java.util.ArrayList;


public class CalisanIslemleri {
    private Connection con =null;
    private Statement statement =null;
    private PreparedStatement preparedStatement = null;
    
    
    public boolean girisYap(String kullanici_adi, String parola){
        String sorgu = "Select * From adminler where username = ? and password = ?";

        try {
            preparedStatement = con.prepareStatement(sorgu);
            preparedStatement.setString(1, kullanici_adi);
            preparedStatement.setString(2, parola);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public void calisanEkle(String ad ,String soyad, String departman ,String maas){
        
        String sorgu = "Insert Into calisanlar (ad,soyad,departman,maas) VALUES (?,?,?,?)";
        
        try {
            preparedStatement= con.prepareStatement(sorgu);
            
            preparedStatement.setString(1, ad);
            preparedStatement.setString(2, soyad);
            preparedStatement.setString(3, departman);
            preparedStatement.setString(4, maas);
            
            preparedStatement.executeUpdate();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    public void calisanlariGuncelle(int id,String ad ,String soyad, String departman ,String maas ){
        
        String sorgu = "Update calisanlar set ad = ?, soyad = ? , departman = ? ,maas = ?  where id = ? ";
        
        try {
            preparedStatement= con.prepareStatement(sorgu);
            
            preparedStatement.setString(1, ad);
            preparedStatement.setString(2, soyad);
            preparedStatement.setString(3, departman);
            preparedStatement.setString(4, maas);
            preparedStatement.setInt(5, id);
            
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public ArrayList<Calisan> calisanlariGetir(){
        
        ArrayList<Calisan> cikti = new ArrayList<Calisan>();
        String sorgu = "Select * From calisanlar";
        
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sorgu);
            
            while(rs.next()){
                int id = rs.getInt("id");
                String ad = rs.getString("ad");
                String soyad = rs.getString("soyad");
                String departman = rs.getString("departman");
                String maas = rs.getString("maas");
                
                cikti.add(new Calisan(id, ad, soyad, departman, maas));
            }
          
            return cikti;
        } catch (SQLException ex) {
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    public CalisanIslemleri(){
        
        String url = "jdbc:mysql://"+ DataBase.host + ":" + DataBase.port + "/" +DataBase.db_ismi+"?useUnicode=true&characterEncoding=utf8";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver Bulunamadi");
        }
        
        try {
            con = DriverManager.getConnection(url, DataBase.kullanici_adi,DataBase.parola);
            System.out.println("Bağlantı başarılı");
            
        } catch (SQLException ex) {
            System.out.println("Bağlantı başarısız");
        }
        
    }
   
}
