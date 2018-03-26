package ctf.green.findtheactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import ctf.green.findtheactivity.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        ContextUtil.setContext(this);

        try {
            AMSHookHelper.hookActivityThreadHandler();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {

        String flag = ((EditText) findViewById(R.id.editText)).getText().toString();

        if (flag.length() != 0) {

            Intent intent = new Intent(this, CheckActivity.class);
            intent.putExtra("flag", flag);
            intent.putExtra("Key", getIntent().getIntExtra("Key", 0));
            startActivity(intent);
        }
    }
    static class ContextUtil{

        private static Context sContext;

        public ContextUtil(){
            sContext = null;
        }

        public static void setContext(Context context){
            sContext = context;
        }

        public static Context getContext(){
            return sContext;
        }

    }

}
