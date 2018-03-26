package ctf.green.findtheactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import ctf.green.findtheactivity.R;

public class CheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        TextView textView = (TextView)findViewById(R.id.textView);

        Intent intent = getIntent();
        String flag = intent.getStringExtra("flag");
        if(checkFlag(flag)){
            textView.setText("Congratlation!\n" + flag);
        } else{
            textView.setText("wrong!");
        }

    }



    private boolean checkFlag(String flag){

        char[] flag_byte = {254, 236, 254, 255, 251, 231, 227, 253, 206, 233, 0, 247, 210, 248, 10, 7, 5, 6, 242, 217, 1, 6, 6, 14, 10, 223, 7, 12, 12, 7, 228, 15, 10, 231, 33, 24, 28, 35, 204, 11};

        for(int i=0;i<flag.length();i++){
            if((((flag.charAt(i)^0x99)+i)&0xff) != flag_byte[i]){
                return false;
            }
        }
        return true;

    }

}
