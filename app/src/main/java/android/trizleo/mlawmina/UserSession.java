package android.trizleo.mlawmina;

import android.content.Context;
import android.os.Bundle;
import android.trizleo.mlawmina.models.Case;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hebi525 on 08-Mar-17.
 */

//TODO: CHANGES TO THE USERSESSION OBJECT MUST REFLECT TO DATA.TXT
public class UserSession implements Serializable {


    private Context c;
    private String username;
    private String password;
    private ArrayList<Case> userCaseList = new ArrayList<>();

    //TODO: dont use this! temporary implementation
    public UserSession(Context c, String username, String password) {

        this.c = c;
        this.username = username;
        this.password = password;
    }

    public ArrayList<Case> getUserCaseList() {
        return userCaseList;
    }

    public void updateUserCaseList(ArrayList<Case> userCaseList){

        this.userCaseList = userCaseList;

        /*String previousData = "";

        String dataTextPath = LoginActivity.APP_DIR+"data/data.txt";

        //TODO: FIX: data.txt content is immediately replaced by empty values after this executes
        try {

            FileInputStream fis = new FileInputStream(dataTextPath);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            //FileWriter fw = new FileWriter(dataTextPath);
            //BufferedWriter bw = new BufferedWriter(fw);


            Toast.makeText(c, "u ppl are shit "br.readLine(), Toast.LENGTH_SHORT).show();
            String line;
            while((line = br.readLine()) != null){
                previousData += line;
            }

            Toast.makeText(c, previousData, Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
