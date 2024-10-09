import java.util.Scanner;
public class Decrypter {
    public static void main(String[] args) {
        System.out.println("Welcome to My Decryption App \n ");
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is your Target? for more information write 'help'");
        String command = new String();
        command = scanner.next();
     start:
     do {
         switch (command) {
             case "help":
                 System.out.println("If have an Decryption and your Encryption type is a \"One-O-One Substitution\" without a linear function enter 'D-1' ");
                 System.out.println("If have an Decryption and your Encryption type is a \"One-O-One Substitution\" with a linear function (affine system) enter 'D-2' ");
                 System.out.println("If have an Decryption and your Encryption type is a \"Permutation\" 'D-3' ");
                 System.out.println("Write 'new' to work on new text :)");
                 System.out.println("Write 'q' to exit the program :(");
                 break;
             case "1":
                 System.out.println("Your Encryption type is \"One-O-One Substitution\" without a linear function");
                 System.out.println("First Enter your Encrypted text:");
                 String Encrypted_text = new String();
                 Encrypted_text = scanner.next();
                 Encrypted_text += scanner.nextLine();
                 System.out.println("Now enter the key alphabet like: bqwase ...");
                 String Key = new String();
                 Key = scanner.next();
                 String Decrypted_text = new String();
                 Decrypted_text = Simple_Substitution(Encrypted_text, Key);
                 System.out.println("Decrypted text is :\n >>>"+ Decrypted_text +"\n for more decryption enter 'new' ...");
                 break;
             case "2":
                 System.out.println("Your Encryption type is \"One-O-One Substitution\" with a linear function (affine system)");
                 System.out.println("First Enter your Encrypted text:");
                 String Encrypted_affine = new String();
                 Encrypted_affine = scanner.next();
                 Encrypted_affine += scanner.nextLine();
                 System.out.println("So you have a modular equation in mod 26 like 'C = Pk1 + k2'\nnow enter k1 k2 e.g. : 3 7");
                 System.out.println("***In this program we assume that alphabets is in range (0,25)***");
                 int k1 = scanner.nextInt();
                 int k2 = scanner.nextInt();
                 String Decryoted_affine = Affine_Substitution(Encrypted_affine, k1, k2);
                 System.out.println("Decrypted text is :\n >>>"+ Decryoted_affine +"\n for more decryption enter 'new' ...");
                 break;
             case "3":
                 System.out.println("Oops! \nThis feature is not ready yet!");
                 break;
             case "new":
                 System.out.println("Enter the Decryption type (1 2 3):");
                 break;
             case "q":
                 System.out.println("Thanks for choosing my app!");
                 break start;
             default:
                 System.out.println("Command not found");
         }
         command = scanner.next();
     } while (true);
    }
    public static String Simple_Substitution(String Encrypt_text, String Key){
        char[] alphabets = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        char[] text = Encrypt_text.toCharArray();
        char[] key = Key.toCharArray();
        int i = 0;
        for(char temp : text){
            if(temp == key[0])
                text[i] = 'a';
            else if (temp == key[1])
                text[i] = 'b';
            else if (temp == key[2])
                text[i] = 'c';
            else if (temp == key[3])
                text[i] = 'd';
            else if (temp == key[4])
                text[i] = 'e';
            else if (temp == key[5])
                text[i] = 'f';
            else if (temp == key[6])
                text[i] = 'g';
            else if (temp == key[7])
                text[i] = 'h';
            else if (temp == key[8])
                text[i] = 'i';
            else if (temp == key[9])
                text[i] = 'j';
            else if (temp == key[10])
                text[i] = 'k';
            else if (temp == key[11])
                text[i] = 'l';
            else if (temp == key[12])
                text[i] = 'm';
            else if (temp == key[13])
                text[i] = 'n';
            else if (temp == key[14])
                text[i] = 'o';
            else if (temp == key[15])
                text[i] = 'p';
            else if (temp == key[16])
                text[i] = 'q';
            else if (temp == key[17])
                text[i] = 'r';
            else if (temp == key[18])
                text[i] = 's';
            else if (temp == key[19])
                text[i] = 't';
            else if (temp == key[20])
                text[i] = 'u';
            else if (temp == key[21])
                text[i] = 'v';
            else if (temp == key[22])
                text[i] = 'w';
            else if (temp == key[23])
                text[i] = 'x';
            else if (temp == key[24])
                text[i] = 'y';
            else if(temp == key[25])
                text[i] = 'z';
            i = i + 1;
        }
        String decrypted_text = new String(text);
        return decrypted_text;
    }

    public static String Affine_Substitution (String Encrypted_text, int k1, int k2){
        int N = 26;
        char[] alphabets = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        char[] text = Encrypted_text.toCharArray();
        /// c = pk1 + k2 >> (c - k2)*k1^-1 = p
        int i = 0;
        int j;
        for(char temp : text){
           int flag = 1;
check:     for (j = 0; j < alphabets.length; j++){
                if(temp == alphabets[j]) {
                    flag = 1;
                    break check;
                }
                else
                    flag = 0;
            }
            int index = (((j - (k2 % N)) % N) * modInverse(k1, N)) % N;
            if(flag == 1)
                text[i] = alphabets[index];
            i = i + 1;
        }
        String decrypted_text = new String(text);
        return decrypted_text;
    }
    public static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++)
            if ((a * x) % m == 1)
                return x;
        return 1;
    }
}
