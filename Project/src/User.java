import java.security.SecureRandom;

public class User {
    private String Username ;
    private String Enc_Username ;
    private String Salt ;
    private String Data ;
    public User(String Username , String Salt ){
        this.Username = Username;
        this.Salt = Salt;
    }
    public void setData(String Data){
        this.Data = Data;
    }
    public static byte[] getRandomNonce(int numBytes) {
        byte[] nonce = new byte[numBytes];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }
    public void setEnc_Username(String Enc_Username){
        this.Enc_Username = Enc_Username ;
    }
    public String getEnc_Username(){
        return Enc_Username;
    }
    public String getUsername(){
        return Username;
    }
    public String getSalt(){
        return Salt;
    }
    public String getData(){
        return Data;
    }
}
