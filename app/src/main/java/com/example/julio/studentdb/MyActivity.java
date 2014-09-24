package com.example.julio.studentdb;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class MyActivity extends Activity implements OnClickListener{
	EditText editRollno,editName,editMarks;
	Button btnAdd,btnDelete,btnModify,btnView,btnViewAll,btnShowInfo;
	SQLiteDatabase db;
	/** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

		editRollno=(EditText)findViewById(R.id.editRollno);
		editName=(EditText)findViewById(R.id.editName);
		editMarks=(EditText)findViewById(R.id.editMarks);
		btnAdd=(Button)findViewById(R.id.btnAdd);
		btnDelete=(Button)findViewById(R.id.btnDelete);
		btnModify=(Button)findViewById(R.id.btnModify);
		btnView=(Button)findViewById(R.id.btnView);
		btnViewAll=(Button)findViewById(R.id.btnViewAll);
		btnShowInfo=(Button)findViewById(R.id.btnShowInfo);

//		Warning Professor Nitya, is here where the app starts failing !

		btnAdd.setOnClickListener(this);
//		btnDelete.setOnClickListener(this);
//		btnModify.setOnClickListener(this);
//		btnView.setOnClickListener(this);
//		btnViewAll.setOnClickListener(this);
//		btnShowInfo.setOnClickListener(this);
//		db=openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
//		db.execSQL("CREATE TABLE IF NOT EXISTS student(rollno VARCHAR,name VARCHAR,marks VARCHAR);");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_my, container, false);
            return rootView;
        }
    }

	public void onClick(View view)
	{
		if(view==btnAdd)
		{
			if(editRollno.getText().toString().trim().length()==0||
					editName.getText().toString().trim().length()==0||
					editMarks.getText().toString().trim().length()==0)
			{
				showMessage("Error", "Please enter all values");
				return;
			}
			db.execSQL("INSERT INTO student VALUES('"+editRollno.getText()+"','"+editName.getText()+
					"','"+editMarks.getText()+"');");
			showMessage("Success", "Record added");
			clearText();
		}
		if(view==btnDelete)
		{
			if(editRollno.getText().toString().trim().length()==0)
			{
				showMessage("Error", "Please enter Rollno");
				return;
			}
			Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+editRollno.getText()+"'", null);
			if(c.moveToFirst())
			{
				db.execSQL("DELETE FROM student WHERE rollno='"+editRollno.getText()+"'");
				showMessage("Success", "Record Deleted");
			}
			else
			{
				showMessage("Error", "Invalid Rollno");
			}
			clearText();
		}
		if(view==btnModify)
		{
			if(editRollno.getText().toString().trim().length()==0)
			{
				showMessage("Error", "Please enter Rollno");
				return;
			}
			Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+editRollno.getText()+"'", null);
			if(c.moveToFirst())
			{
				db.execSQL("UPDATE student SET name='"+editName.getText()+"',marks='"+editMarks.getText()+
						"' WHERE rollno='"+editRollno.getText()+"'");
				showMessage("Success", "Record Modified");
			}
			else
			{
				showMessage("Error", "Invalid Rollno");
			}
			clearText();
		}
		if(view==btnView)
		{
			if(editRollno.getText().toString().trim().length()==0)
			{
				showMessage("Error", "Please enter Rollno");
				return;
			}
			Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+editRollno.getText()+"'", null);
			if(c.moveToFirst())
			{
				editName.setText(c.getString(1));
				editMarks.setText(c.getString(2));
			}
			else
			{
				showMessage("Error", "Invalid Rollno");
				clearText();
			}
		}
		if(view==btnViewAll)
		{
			Cursor c=db.rawQuery("SELECT * FROM student", null);
			if(c.getCount()==0)
			{
				showMessage("Error", "No records found");
				return;
			}
			StringBuffer buffer=new StringBuffer();
			while(c.moveToNext())
			{
				buffer.append("Rollno: "+c.getString(0)+"\n");
				buffer.append("Name: "+c.getString(1)+"\n");
				buffer.append("Marks: "+c.getString(2)+"\n\n");
			}
			showMessage("Student Details", buffer.toString());
		}
		if(view==btnShowInfo)
		{
			showMessage("Student Management Application", "Developed By Azim");
		}
	}
	public void showMessage(String title,String message)
	{
		Builder builder=new Builder(this);
		builder.setCancelable(true);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.show();
	}
	public void clearText()
	{
		editRollno.setText("");
		editName.setText("");
		editMarks.setText("");
		editRollno.requestFocus();
	}
}
