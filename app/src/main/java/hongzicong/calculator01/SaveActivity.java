package hongzicong.calculator01;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Window;
import android.view.WindowManager;
import android.support.v7.widget.RecyclerView;
import java.io.*;

import java.util.ArrayList;
import java.util.List;

public class SaveActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManger;
    private StringAdapter adapter;
    private List<String> operNumList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        setActionBar();
        setTempList();
        load();
    }

    private void setActionBar(){
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        Window window=this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.GRAY);
    }

    private void load(){
        FileInputStream in=null;
        BufferedReader reader=null;
        try{
            in=openFileInput("tempData");
            reader=new BufferedReader(new InputStreamReader(in));
            String line="";
            while((line=reader.readLine())!=null){
                operNumList.add(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(reader!=null){
                try{
                    reader.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        updateTempList();
    }

    private void setTempList(){
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        layoutManger=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManger);
        adapter=new StringAdapter(operNumList);
        recyclerView.setAdapter(adapter);
    }

    private void updateTempList(){
        adapter=new StringAdapter(operNumList);
        recyclerView.setAdapter(adapter);
        layoutManger.scrollToPosition(operNumList.size()-1);
    }


}
