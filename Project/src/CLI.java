import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;

public class CLI {
    public static void main(String[] args) {
            System.out.println("Welcom to my Digital Wallet \n Sorry for Console View ! \n");
             ArrayList<User> Users = new ArrayList<>();
            // json list file

            JSONArray UserList = new JSONArray();
          /*  try (FileWriter file = new FileWriter("Users.json")) {

                file.write(UserList.toJSONString());
                file.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        */
        // read from json file
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("Users.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            UserList = (JSONArray) obj;

            //Iterate over User array
            for(Object object : UserList){
                ///

                Users = new ObjectMapper().readValue(  new ArrayList<String>(json), new TypeReference<List<User>>() {});

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        CLI.run(UserList, Users);
        }

        public static void run(JSONArray UserList, ArrayList<User> Users){
            System.out.println(" If you have account Enter 'yes' ; else Enter 'no' \n *** Notice : \n in any part of app to logout user and back to this page Enter 'exit' \n and to exit and stop the program Enter 'exit -a' \n THANKS FOR CHOOSING US ;) ");
            Scanner command = new Scanner(System.in);

            boolean running = true;
            while (running) {
                switch (command.next()) {
                    case "yes":
                        Scanner scanner = new Scanner(System.in);
                        System.out.println("Username:");
                        System.out.println("Password:");
                        String Username = scanner.next();
                        String Password = scanner.next();
                        User current = new User("","");
                        for (User user : Users){
                            if(user.getUsername().equals(Username)){
                                current = user;
                                break;
                            }
                            System.out.println("There is no such a username ! Try again");
                            CLI.run(UserList, Users);
                        }
                        String currentkey = Password.concat(current.getSalt());
                        JSONObject user1 = new JSONObject();
                        if (current.getEnc_Username() == AES.encrypt(current.getUsername() , currentkey)){
                            CLI.Wallet(current , currentkey , user1, UserList , Users);
                        }
                        else {
                            System.out.println("Password is Wrong!");
                            CLI.run(UserList , Users);
                        }
                        break;
                    case "no":
                        Scanner scanner2 = new Scanner(System.in);
                        System.out.println("Enter new Username:");
                        String newUsername = scanner2.next();
                        String newPassword = CLI.setpass();
                        int SALT_LENGTH_BYTE = 16;
                        byte[] salt = User.getRandomNonce(SALT_LENGTH_BYTE);
                        User user = new User(newUsername , new String(salt));
                        String key = newPassword.concat(user.getSalt());
                        user.setEnc_Username(AES.encrypt(user.getUsername() , key));
                        Users.add(user);

                        // save user object in file :
                        JSONObject UserDetails = new JSONObject();
                        UserDetails.put("Username", user.getUsername());
                        UserDetails.put("Salt", user.getSalt());
                        UserDetails.put("Encrypted Username", user.getEnc_Username());
                        UserDetails.put("Data", user.getData());
                        JSONObject UserObject = new JSONObject();
                        UserObject.put("user", UserDetails);

                        UserList.add(UserObject);
                        CLI.Wallet(user , key , UserObject , UserList , Users);
                        break;
                    case "exit" :
                        System.out.println("You haven't Login yet !");
                        break;
                    case "exit -a":
                        running = false;
                        break;
                    default:
                        System.out.println("Command not recognized!");
                        break;
                }
            }
            command.close();
        }
    public static void Wallet(User user, String key , JSONObject UserObject , JSONArray UserList , ArrayList Users){
        System.out.println(" Welcome to Digital Wallet" + user.getUsername() + "! ");
        System.out.println(" now How Can I help You ? \n To See and Decrypt your Currently data Enter 'see ' \n Or to Set new data or Change data Enter 'set' \n And for Changing your Password Enter 'newpass' ");
        Scanner command = new Scanner(System.in);
        boolean running = true;
        while (running) {
            switch (command.next()) {
                case "see":
                    String Encrypted_data = AES.decrypt(user.getData(), key);
                    System.out.println(Encrypted_data);
                    break;
                case "set":
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Enter your Data :");
                    String newdata = scanner.next();
                    user.setData(AES.encrypt(newdata , key));
                    System.out.println("Your Data Saved Successfully !");

                    break;
                case "newpass":
                    String newpass = CLI.setpass();
                    String newkey = newpass.concat(user.getSalt());
                    String DecData = AES.decrypt(user.getData() , key);
                    String new_Data = AES.encrypt(DecData , newkey);
                    user.setData(new_Data);
                    user.setEnc_Username(AES.encrypt(user.getUsername(), newkey));
                    System.out.println("Your Password Changed Successfully !");
                    break;
                case "exit":
                    running = false;
                    CLI.run(UserList ,Users);
                    break;
                case "exit -a":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Command not recognized!");
                    break;
            }
        }
    }
    public static String setpass() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter new password :");
        String new_pass = scanner.next();
        System.out.println("Confirm password :");
        String confirm_pass = scanner.next();
        if(new_pass.equals(confirm_pass) == true){
            return new_pass;
        }
        else{
            System.out.println("Oops ! they don't math !! try again");
            String pass = CLI.setpass();
            return pass;
        }
    }
}
