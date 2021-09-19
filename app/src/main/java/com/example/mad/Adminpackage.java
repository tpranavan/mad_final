package com.example.mad;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.common.collect.Range;

public class Adminpackage extends AppCompatActivity {
    DBpac db;
    EditText pp1,pp2,pp3,pp4;
    Button add,delete,update,view,logout,search;
    AwesomeValidation awesomeValidation;
    String[] type_of_pac = {"Day Out Package", "Family package"};
    private TextView cst;
    private Button bst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpackage);
        bst = findViewById(R.id.cb);
        cst = findViewById(R.id.ct);

        db=new DBpac(this);

        pp1=(EditText)findViewById(R.id.p1);
        pp2=(EditText)findViewById(R.id.p2);
        pp3=(EditText)findViewById(R.id.p3);
        pp4=(EditText)findViewById(R.id.p4);

        add=findViewById(R.id.add);
        delete=findViewById(R.id.delete);
        update=findViewById(R.id.update);
        view=findViewById(R.id.view);
        search=findViewById(R.id.search);
        logout=findViewById(R.id.log);
        AddData();
        viewAll();
        UpdateData();
        DeleteData();
        SearchData();
        awesomeValidation = new AwesomeValidation( ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.p1, RegexTemplate.NOT_EMPTY, R.string.invalid_packageID);
        awesomeValidation.addValidation(this, R.id.p2, RegexTemplate.NOT_EMPTY, R.string.invalid_packagename);
        awesomeValidation.addValidation(this,R.id.p3, Range.closed(5000,20000),R.string.invalid_packagecost);
        awesomeValidation.addValidation(this, R.id.p4, RegexTemplate.NOT_EMPTY, R.string.invalid_facilities_pac );


        bst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pp3.getText().toString().length() == 0) {
                    pp3.setText("0");
                }
                cst.setText(String.valueOf(calculate()));


            }
        });


        logout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent log=new Intent( Adminpackage.this,Admin.class );

                startActivity( log );
            }
        } );



        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(onItemSelectedListener1);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, type_of_pac);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner1.setAdapter(aa);



    }

    final AdapterView.OnItemSelectedListener onItemSelectedListener1 =
            new AdapterView.OnItemSelectedListener() {

                //Performing action onItemSelected and onNothing selected
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                    String s1 = String.valueOf(type_of_pac[position]);
                    pp2.setText(s1);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }


            };




    public void DeleteData(){
        delete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer deletedRows =db.deleteData(pp1.getText().toString());
                        if(deletedRows >0) {
                            Toast.makeText( Adminpackage.this, "Data deleted", Toast.LENGTH_LONG ).show();
                            clearControls();
                        } else
                            Toast.makeText(Adminpackage.this,"Data Not deleted",Toast.LENGTH_LONG).show();

                    }
                }
        );
    }
    public void UpdateData(){
        update.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isUpdate= db.updateData(pp1.getText().toString(),pp2.getText().toString(),pp3.getText().toString(),pp4.getText().toString());
                        if(isUpdate==true && awesomeValidation.validate()) {
                            Toast.makeText( Adminpackage.this, "Data updated", Toast.LENGTH_LONG ).show();
                            clearControls();
                        } else
                            Toast.makeText(Adminpackage.this,"Data Not updated",Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    public void AddData(){
        add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isInserted=db.insertData(pp1.getText().toString(),pp2.getText().toString(),pp3.getText().toString(),pp4.getText().toString());
                        if(isInserted == true && awesomeValidation.validate()) {
                            Toast.makeText( Adminpackage.this, "Data Inserted", Toast.LENGTH_SHORT ).show();
                            clearControls();
                        } else
                            Toast.makeText(Adminpackage.this,"Data  notInserted",Toast.LENGTH_SHORT).show();


                    }
                }
        );
    }  public void viewAll(){
        view.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.getAllData();
                        Cursor res =db.getAllData();
                        if(res.getCount()==0){
                            showMessage("View is Empty !!!","No Data Found");
                            return;
                        }
                        StringBuffer buffer =new StringBuffer();
                        while(res.moveToNext()){
                            buffer.append("Package ID :"+res.getString(0)+"\n");
                            buffer.append("Package Type :"+res.getString(1)+"\n");
                            buffer.append("Package Cost :"+res.getString(2)+"\n");
                            buffer.append("Package features:"+res.getString(3)+"\n");


                        }
                        showMessage("Package Details",buffer.toString());

                    }
                }
        );
    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
    public void SearchData(){

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor data = db.searchData(pp1.getText().toString());
                if (data.getCount() == 0) {
                    //Show Message
                    showMessage("Error ", "Nothing Found");
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                while (data.moveToNext()) {
                    pp1.setText( data.getString(0));
                    pp2.setText( data.getString(1));
                    pp3.setText( data.getString(2));
                    pp4.setText( data.getString(3));


                }
            }
        });

    }
    private void clearControls(){
        pp1.setText("");
        pp2.setText("");
        pp3.setText("");
        pp4.setText("");


    }

    public float calculate() {
        float rcost1 = 0.25F;
        float rcost2 =  12000;

        float rnum = Integer.parseInt(pp3.getText().toString());


        if(pp3.getText().toString().equalsIgnoreCase("10000")){

           float cal = rcost1 * rnum;
            return cal;


        }
        else{
            float cal = rcost2 * rnum;
            return cal;
        }
    }


}



