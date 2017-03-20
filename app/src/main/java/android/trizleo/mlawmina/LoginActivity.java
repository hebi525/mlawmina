package android.trizleo.mlawmina;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.trizleo.mlawmina.models.Case;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    public static String APP_DIR = Environment.getExternalStorageDirectory()+"/mlawmina/";
    private String username;
    private String password;

    private EditText etEmail;
    private EditText etPass;
    private Button btnLogin;

    public String tempData = "{\n" +
            "\t\"USER\": [\n" +
            "\t\t{\n" +
            "\t\t\t\"USERNAME\": \"admin\",\n" +
            "\t\t\t\"PASSWORD\": \"lawminaboss\",\n" +
            "\t\t\t\"CASE_LIST\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"CASE_NAME\": \"case_name\",\n" +
            "\t\t\t\t\"CASE_TYPE\": \"case_type\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"CASE_NAME\": \"case_name1\",\n" +
            "\t\t\t\t\"CASE_TYPE\": \"case_type1\"\n" +
            "\t\t\t},\n" +
            "\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"CASE_NAME\": \"case_name2\",\n" +
            "\t\t\t\t\"CASE_TYPE\": \"case_type2\"\n" +
            "\t\t\t},\n" +
            "\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"CASE_NAME\": \"case_name3\",\n" +
            "\t\t\t\t\"CASE_TYPE\": \"case_type3\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"CASE_NAME\": \"case_name4\",\n" +
            "\t\t\t\t\"CASE_TYPE\": \"case_type4\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"CASE_NAME\": \"case_name5\",\n" +
            "\t\t\t\t\"CASE_TYPE\": \"case_type5\"\n" +
            "\t\t\t}\n" +
            "\t\t\t]\n" +
            "\t\t}\n" +
            "\t]\n" +
            "}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText)findViewById(R.id.et_email);
        etPass = (EditText)findViewById(R.id.et_pass);
        btnLogin = (Button)findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Case> caseList = new ArrayList<>();

                // logging in... parsing json TODO: USING TEMPORARY DATA FOR NOW!
                try {
                    JSONObject jsonData = new JSONObject(getData());
                    JSONArray jsonUserList = jsonData.getJSONArray("USER");

                    JSONArray jsonCaseList = null;

                    for(int i = 0; i< jsonUserList.length(); i++) {
                        JSONObject jsonObject1 = jsonUserList.getJSONObject(i);

                        username = jsonObject1.getString("USERNAME");
                        password = jsonObject1.getString("PASSWORD");
                        jsonCaseList = jsonObject1.getJSONArray("CASE_LIST");
                    }

                    for (int i = 0; i < jsonCaseList.length(); i++){
                        JSONObject item = jsonCaseList.getJSONObject(i);

                        String caseName = item.getString("CASE_NAME");
                        String caseType = item.getString("CASE_TYPE");
                        caseList.add(new Case(caseName, 1234, caseType, "default description", "plaintiff", "defendant", "123", "123", 1, null));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if((etEmail.getEditableText().toString()+etPass.getEditableText().toString()).equals(username+password)){

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("USERNAME", username);
                    intent.putExtra("PASSWORD", password);
                    intent.putExtra("CASE_LIST", caseList);

                    startActivity(intent);
                }
                else {
                    Toast.makeText(LoginActivity.this, "incorrect username/password!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public String getData(){
        //// TODO: 12-Mar-17 : TEMPORARY IMPLEMENTATIONS
        File dataFile = new File(APP_DIR+"/data");

        String data = "";

        if(!dataFile.exists()){
            dataFile.mkdir();
        }

        File dataTextFile = new File(dataFile+"/data.txt");

        try {
            FileInputStream fis = new FileInputStream(dataTextFile);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String line = null;
            while((line = br.readLine()) != null){
                data += line;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}
