 /*************************************************************************
  * Name: Michelle Lam
  * Date: 2/02/17
  *
  * Compilation: javac Tokenizer.java
  * Input Files: siriCommands.txt
  *
  * Description: Analyze a language an AI assistant (ex. Siri) that performs
  *              for you and decide what keywords and values it uses in
  *              its language.
  *************************************************************************/

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

/**
 *
 * @author michelleLam
 */
public class Tokenizer {

    /**
     * @param args the command line arguments
     */
    static Hashtable<String, String> keyword_ht = new Hashtable<String, String>();
    static Hashtable<String, String> comment_ht = new Hashtable<String, String>();
    Hashtable<String, String> value_ht = new Hashtable<String, String>();
    private static void setHashtable(){
        String val_k = "keyword";
        String val_c = "comment";
        keyword_ht.put("call", val_k);
        keyword_ht.put("where", val_k);
        keyword_ht.put("play", val_k);
        keyword_ht.put("remember", val_k);
        keyword_ht.put("what", val_k);
        keyword_ht.put("is", val_c);
        keyword_ht.put("am", val_c);
        keyword_ht.put("are", val_c);
        keyword_ht.put("the", val_c);
        keyword_ht.put("in", val_c);
        keyword_ht.put("at", val_c);
        keyword_ht.put("on", val_c);
        keyword_ht.put("to", val_c);
        keyword_ht.put("my", val_c);
        keyword_ht.put("a", val_c);
        keyword_ht.put("good", val_c);
        keyword_ht.put("of", val_c);
    }

    private static String getType(String str){
        String ret = "";
        str = str.toLowerCase();
        if(keyword_ht.containsKey(str)){
            ret = keyword_ht.get(str);
        }else{
            ret = "value";
        }
        return ret;
    }

    private static int getChar(String str, String val){
        int ret = 0;
        ret = str.indexOf(val) + 1;
        return ret;
    }

    private static int maxLengthInStr(ArrayList<String> Str){
        int largestString = Str.get(0).length();
        int index = 0;

        for(int i = 0; i < Str.size(); i++)
        {
            if(Str.get(i).length() > largestString)
            {
                largestString = Str.get(i).length();
                index = i;
            }
        }
        return largestString;
    }

    private static String addSpace(String str, int len){
        String ret = "";
        if(str.length() >= len){
            return str;
        }else{
            for(int i = 0; i < len - str.length(); i ++){
                ret += " ";
            }
            return str + ret;
        }
    }

    public static void main(String[] args) {
        // TODO code application logic here
        setHashtable();
        String path = args[0];
        Hashtable ht = new Hashtable();
        File file = new File(path);
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        DataInputStream dis = null;

        try {
            fis = new FileInputStream(file);

            // Here BufferedInputStream is added for fast reading.
            bis = new BufferedInputStream(fis);
            dis = new DataInputStream(bis);
            int n = 1;
            while (dis.available() != 0) {

              String command = dis.readLine();
              System.out.println(command);
              command = command.replace(",", "").replace(".", "").replace("?", "");
              String commands[] = command.split(" ");
              ArrayList<String> str_arr = new ArrayList<String>();
              for(int i = 0; i < commands.length; i ++){
                  str_arr.add(commands[i].trim());
              }
              int maxlength = maxLengthInStr(str_arr);
              for(int i = 0; i < commands.length; i ++){
                  String type = addSpace(getType(commands[i]), 7);
                  String value = addSpace(commands[i].trim(), maxlength);
                  String temp = "line " + n + " char " + getChar(command, commands[i]);
                  System.out.println(type + " , " + value + " , " + temp);
              }
              System.out.println("-----------------------");
              n ++;
            }
            fis.close();
            bis.close();
            dis.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not Found.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
