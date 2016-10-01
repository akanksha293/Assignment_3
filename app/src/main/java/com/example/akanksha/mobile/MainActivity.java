package com.example.akanksha.mobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    EditText editroll,editname,editmarks,editread;
    TextView textView;
    Button btnAdd,btnView,btnUp,btnDel;
    private static final String ABC = "preferences";
    int x=0;


    String filename="myfile.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DatabaseHelper(this);


        editroll=(EditText)findViewById(R.id.editText);
        editname=(EditText)findViewById(R.id.editText2);
        editmarks=(EditText)findViewById(R.id.editText3);
        editread=(EditText)findViewById(R.id.editText4);
        textView=(TextView)findViewById(R.id.textView4);
        btnAdd=(Button)findViewById(R.id.button);
        btnView=(Button)findViewById(R.id.button2);
        btnDel=(Button)findViewById(R.id.button3);
        btnUp=(Button)findViewById(R.id.button4);


      /*
        //restore preferences after destroying app
        settings = this.getPreferences(Context.MODE_PRIVATE);
        n = settings.getString("number","");
        editname.setText(n);*/


        addData();
        showData();
        delData();
        updateData();
    }



    public void addData()
    {
        btnAdd.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                       boolean check = myDB.insertData(editroll.getText().toString(),editname.getText().toString(),editmarks.getText().toString());
                       if(check==true)
                           Toast.makeText(MainActivity.this,"Data inserted",Toast.LENGTH_LONG).show();
                        else
                           Toast.makeText(MainActivity.this,"Data not inserted",Toast.LENGTH_LONG).show();

                    }
                }
        );
        editroll.setText("");
        editname.setText("");
        editmarks.setText("");
    }

    public void showData()
    {
        btnView.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                      Cursor res = myDB.viewData();
                        if(res.getCount()==0){
                            //show message
                            displayMessage("error","No data found");
                            return;
                        }
                        StringBuffer sb= new StringBuffer();
                        while(res.moveToNext()){
                            sb.append("Roll :"+res.getString(0)+"\n");
                            sb.append("Name :"+res.getString(1)+"\n");
                            sb.append("Marks :"+res.getString(2)+"\n\n");

                        }
                        //show data
                        displayMessage("Data",sb.toString());

                    }

                }
        );
    }

    public void delData()
    {
        btnDel.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                      int deletedRows = myDB.deleteData(editroll.getText().toString());
                        if(deletedRows > 0)
                            Toast.makeText(MainActivity.this,"Data deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not deleted",Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    public void updateData()
    {
        btnUp.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        boolean isUpdated = myDB.upData(editroll.getText().toString(),editname.getText().toString(),editmarks.getText().toString());
                        if(isUpdated == true)
                            Toast.makeText(MainActivity.this,"Data updated",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not updated",Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    public void displayMessage(String title,String message)
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setCancelable(true);
        b.setTitle(title);
        b.setMessage(message);
        b.show();
    }



    public void read(View view)      //read from internal storage
    {

        try {
           FileInputStream fileInputStream = openFileInput("mytext.txt");
            InputStreamReader is = new InputStreamReader(fileInputStream);
            BufferedReader br = new BufferedReader(is);
            StringBuffer sb = new StringBuffer();
            String lines;

            while((lines=br.readLine()) != null)
            {
                sb.append(lines+"\n");
            }
            textView.setText(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }



    public void write(View view)     //write to internal storage
    {
        String msg = editread.getText().toString();
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput("mytext.txt",MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fileOutputStream.write(msg.getBytes());
            Toast.makeText(getApplicationContext(),"Text Saved",Toast.LENGTH_LONG).show();
            editread.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public boolean isExternalStorageWritable(){
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            return true;
        }
        return false;
    }

    public boolean isExternalStoreageReadable(){
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
            return true;
        }
        return false;
    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), albumName);
        return file;
    }*/

    public void writeex(View view)  //write to external storage
    {
        String state;
        FileOutputStream fileOutputStream = null;
        state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state))
        {
            Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_LONG).show();
            /*File root = Environment.getExternalStorageDirectory();
            File dir = new File(root.getAbsolutePath()+"/MyAppFile");
            if(!dir.exists())
            {
                Toast.makeText(getApplicationContext(),"2",Toast.LENGTH_LONG).show();
                dir.mkdir();
            }*/
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),filename);
            String message = editread.getText().toString();
            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

            try {
                fileOutputStream = openFileOutput(filename,MODE_PRIVATE);
                fileOutputStream.write(message.getBytes());
                fileOutputStream.close();
                editread.setText("");
            } catch (FileNotFoundException e) {
                Toast.makeText(getApplicationContext(),"5",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(),"6",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }


        }
        else
        {
            Toast.makeText(getApplicationContext(),"SD Card not found",Toast.LENGTH_LONG).show();
        }


    }

    public void readex(View view) //read from external storage
    {
        /*File root = Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath()+"/MyAppFile");*/
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),filename);
        String message;
        try {
            FileInputStream fileInputStream = openFileInput(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            while((message=bufferedReader.readLine()) != null)
            {
                stringBuffer.append(message+"\n");
            }
            textView.setText(stringBuffer.toString());
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }


    }



    public void shared(View view) //storing data
    {

        String n = editname.getText().toString();
        Toast.makeText(getApplicationContext(),n,Toast.LENGTH_LONG).show();
       //storing preferences on pausing
        SharedPreferences settings = getSharedPreferences(ABC,0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("number",n);
        editor.commit(); //commit the changes



    }

    public void load(View view)
    {
        //restore preferences after pausing or stopping app
        SharedPreferences settings = getSharedPreferences(ABC,0);
        String n = settings.getString("number","");
        editname.setText(n);
    }
}


