package hk.hku.cs.comp3330project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;


public class BodyStatActivity extends AppCompatActivity {
    private TextView Height_text;
    private TextView Weight_text;
    private TextView BMI_text;
    private TextView Bodyfat_text;
    private TextView date_text;
    private Button Height_button;
    private Button Weight_button;
    private Button Bodyfat_button;
    private double height;
    private double weight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_stat);
        Height_text = (TextView) findViewById(R.id.Height_text);
        Weight_text = (TextView) findViewById(R.id.Weight_text);
        BMI_text = (TextView) findViewById(R.id.BMI_text);
        Bodyfat_text = (TextView) findViewById(R.id.Bodyfat_text);
        date_text = (TextView) findViewById(R.id.date_text);
        Height_button = (Button) findViewById(R.id.Height_button);
        Weight_button = (Button) findViewById(R.id.Weight_button);
        Bodyfat_button = (Button) findViewById(R.id.Bodyfat_button);

        Height_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(BodyStatActivity.this);
                builder.setTitle("Edit height (m) ");
                final EditText input = new EditText(BodyStatActivity.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                builder.setView(input);
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences mydata=getSharedPreferences("mydata", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = mydata.edit();
                        editor.putString("height",input.getText().toString());
                        editor.commit();
                        Height_text.setText("Height : "+input.getText().toString()+" m");

                    }
                });
                builder.setNegativeButton("Cancel   ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }
        });
        Weight_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(BodyStatActivity.this);
                builder.setTitle("Edit Weight (kg) ");
                final EditText input = new EditText(BodyStatActivity.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                builder.setView(input);
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences mydata=getSharedPreferences("mydata", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = mydata.edit();
                        editor.putString("weight",input.getText().toString());
                        editor.commit();
                        Weight_text.setText("Weight : "+input.getText().toString()+" kg");

                    }
                });
                builder.setNegativeButton("Cancel   ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }
        });
        Bodyfat_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(BodyStatActivity.this);
                builder.setTitle("Edit Body Fat (%) ");
                final EditText input = new EditText(BodyStatActivity.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                builder.setView(input);
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences mydata=getSharedPreferences("mydata", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = mydata.edit();
                        editor.putString("bodyfat",input.getText().toString());
                        editor.commit();
                        Bodyfat_text.setText("Body Fat : "+input.getText().toString()+" %");

                    }
                });
                builder.setNegativeButton("Cancel   ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }
        });

    }
}