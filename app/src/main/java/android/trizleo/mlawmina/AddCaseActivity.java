package android.trizleo.mlawmina;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.trizleo.mlawmina.fragments.TestFragment;
import android.trizleo.mlawmina.models.Case;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gigamole.navigationtabstrip.NavigationTabStrip;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

public class AddCaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_case);

        Toolbar toolbar = (Toolbar) findViewById(R.id.add_case_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText dateOpened = (EditText) findViewById(R.id.et_dateOpened);
        final EditText limitations = (EditText) findViewById(R.id.et_limitations);

        final EditText caseName = (EditText) findViewById(R.id.et_caseName);
        final EditText caseNumber = (EditText) findViewById(R.id.et_caseNumber);
        final MaterialSpinner caseType = (MaterialSpinner) findViewById(R.id.et_caseType);

        //TODO: this is a temporary implementation until server and db are up
        final String[] caseTypeList = {
                "PRACTICE AREA 1",
                "PRACTICE AREA 2",
                "CRIMINAL"
        };
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, caseTypeList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        caseType.setAdapter(spinnerAdapter);
        final EditText plaintiff = (EditText) findViewById(R.id.et_plaintiff);
        final EditText defendant = (EditText) findViewById(R.id.et_defendant);
        final EditText description = (EditText) findViewById(R.id.et_description);

        Button addCaseBtn = (Button) findViewById(R.id.add_case_btn);

        dateOpened.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(dateOpened);
                Date date = new Date(03, 02, 2017);


                DatePickerDialog dialog = new DatePickerDialog(
                        AddCaseActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateOpened.setText(monthOfYear+"/"+dayOfMonth+"/"+year);
                            }
                        },
                        date.getYear(), date.getMonth(), date.getDay());

                dialog.show();
            }
        });
        limitations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(limitations);
                Date date = new Date(03, 02, 2017);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddCaseActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                limitations.setText(monthOfYear+"/"+dayOfMonth+"/"+year);
                            }
                        },
                        date.getYear(), date.getMonth(), date.getDay());

                dialog.show();
            }
        });

        // Adding of new case
        addCaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.currentUserSession!=null){
                    ArrayList<Case> newCaseList = MainActivity.currentUserSession.getUserCaseList();
                    newCaseList.add(new Case(
                            caseName.getText().toString(),
                            Integer.parseInt(caseNumber.getText().toString()),
                            caseTypeList[caseType.getSelectedItemPosition()-1],
                            description.getText().toString(),
                            plaintiff.getText().toString(),
                            defendant.getText().toString(),
                            limitations.getText().toString(),
                            dateOpened.getText().toString(),
                            0,
                            null
                            ));

                    Toast.makeText(AddCaseActivity.this, "Case created successfully!", Toast.LENGTH_SHORT).show();
                    MainActivity.currentUserSession.updateUserCaseList(newCaseList);
                    onBackPressed();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public void hideSoftKeyboard(View view){
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(AddCaseActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
