package hongzicong.calculator01;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.math.MathContext;
import android.widget.HorizontalScrollView;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button button0;
    private Button dot;
    private Button percent;
    private Button delete;
    private Button add;
    private Button minus;
    private Button multiple;
    private Button divide;
    private Button equal;
    private Button clear;
    private TextView resultNum;
    private TextView tempNum; //tempNum include tempStr and operator
    private StringBuffer resultStr;
    private StringBuffer tempStr; //conclude comma and number ,not conclude sign
    private String operator;
    private HorizontalScrollView scrollText1;
    private HorizontalScrollView scrollText2;
    private List<OperNum> operNumList=new ArrayList<>();
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManger;
    private OperNumAdapter adapter;
    private MathContext mathContext;

    private boolean HASBEENEND=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        Window window=this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.GRAY);
        mathContext=MathContext.DECIMAL64;
        setTempList();
        setAllButton();
        setAllText();
        setAllListener();
    }

    private void setTempList(){
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        layoutManger=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManger);
        adapter=new OperNumAdapter(operNumList);
        recyclerView.setAdapter(adapter);
    }

    private void updateTempList(){
        adapter=new OperNumAdapter(operNumList);
        recyclerView.setAdapter(adapter);
        layoutManger.scrollToPosition(operNumList.size()-1);
    }

    private void setAllButton(){
        button1=(Button)findViewById(R.id.button1);
        button2=(Button)findViewById(R.id.button2);
        button3=(Button)findViewById(R.id.button3);
        button4=(Button)findViewById(R.id.button4);
        button5=(Button)findViewById(R.id.button5);
        button6=(Button)findViewById(R.id.button6);
        button7=(Button)findViewById(R.id.button7);
        button8=(Button)findViewById(R.id.button8);
        button9=(Button)findViewById(R.id.button9);
        button0=(Button)findViewById(R.id.button0);
        dot=(Button)findViewById(R.id.dot);
        percent=(Button)findViewById(R.id.percent);
        add=(Button)findViewById(R.id.add);
        minus=(Button)findViewById(R.id.minus);
        multiple=(Button)findViewById(R.id.multiple);
        divide=(Button)findViewById(R.id.divide);
        equal=(Button)findViewById(R.id.equal);
        delete=(Button)findViewById(R.id.delete);
        clear=(Button)findViewById(R.id.clear);
    }

    private void setAllText(){
        scrollText1=(HorizontalScrollView)findViewById(R.id.scorllText1);
        scrollText2=(HorizontalScrollView)findViewById(R.id.scorllText2);
        resultNum=(TextView)findViewById(R.id.resultNum);
        tempNum=(TextView) findViewById(R.id.tempNum);
        initialText();
    }

    //All listener should manifest in it
    private void setAllListener(){
        setNumListener(button1);
        setNumListener(button2);
        setNumListener(button3);
        setNumListener(button4);
        setNumListener(button5);
        setNumListener(button6);
        setNumListener(button7);
        setNumListener(button8);
        setNumListener(button9);
        setNumListener(button0);
        setClearListener();
        setDeleteListener();
        setDotListener();
        setOperatorListener(add);
        setOperatorListener(minus);
        setOperatorListener(divide);
        setOperatorListener(multiple);
        setPercentListener();
        setEqualListener();
    }

    private void setClearListener(){
        clear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(tempStr.toString().equals("0")&&resultStr.toString().isEmpty()){
                    allClear();
                }
                else{
                    oneClear();
                    clear.setText("AC");
                }
            }
        });
    }

    //C when has enter
    private void oneClear(){
        initialText();
        tempNum.setText(tempStr);
        resultNum.setText(resultStr);
    }

    //AC when not enter any number
    private void allClear(){
        initialText();
        operNumList=new ArrayList<OperNum>();
        updateTempList();
        tempNum.setText(tempStr);
        resultNum.setText(resultStr);
    }

    private void setDeleteListener(){
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(tempStr.length()!=0){
                    tempStr.deleteCharAt(tempStr.length()-1);
                    if(tempStr.length()>3){
                        tempStr=setIntComma(tempStr);
                    }
                }
                else if (!operator.isEmpty()){
                    operator=new String();
                }
                updateTempText();
            }
        });
    }

    private void setDotListener(){
        dot.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(isInt(tempStr)){
                    tempStr.append('.');
                }
                updateTempText();
            }
        });
    }

    private void setOperatorListener(Button mButton){
        final String operatorStr=mButton.getText().toString();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(HASBEENEND){
                    oneClear();
                    HASBEENEND=false;
                }
                //at the begin,change the tempNum
                if (operator.isEmpty()) {
                    OperNum tempOperNum=new OperNum(operator,tempStr);
                    operNumList.add(tempOperNum);
                    operator = new String(operatorStr);
                    resultStr=new StringBuffer(tempStr);
                    tempStr = new StringBuffer();
                    updateTempText();
                    updateTempList();
                    resultNum.setText("= "+resultStr);
                }
                //already has operator
                else{
                    //if there is no number,change the operator and the tempNum
                    if (tempStr.toString().isEmpty()) {
                        operator=new String(operatorStr);
                        tempNum.setText(operatorStr);
                    }
                    //there already has number,operate to the number,change the operator and the tempNum and the result
                    else {
                        operateToNum();
                        OperNum tempOperNum=new OperNum(operator,tempStr);
                        operNumList.add(tempOperNum);
                        operator = new String(operatorStr);
                        tempStr = new StringBuffer();
                        updateTempText();
                        updateTempList();
                        resultStr = setIntComma(resultStr);
                        resultNum.setText("= "+resultStr);
                    }
                }
            }
        });
    }

    private void setNumListener(Button mButton){
        final String num=mButton.getText().toString();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(HASBEENEND){
                    oneClear();
                    HASBEENEND=false;
                }
                if(tempStr.toString().equals("0")){
                    tempStr.deleteCharAt(0);
                    tempStr.append(num);
                }
                else if(isInt(tempStr)&&legalIntTempRange(tempStr,num)){
                    tempStr.append(num);
                    if(tempStr.length()>3){
                        tempStr=setIntComma(tempStr);
                    }
                }
                else if(!isInt(tempStr)&&legalDoubleTempRange(tempStr,num)){
                    tempStr.append(num);
                }
                if(clear.getText().equals("AC")){
                    clear.setText("C");
                }
                updateTempText();
            }
        });
    }

    private void setPercentListener(){
        percent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!tempStr.toString().isEmpty()){
                    BigDecimal tempNumber=new BigDecimal(getNumFromStr(tempStr).toString(),mathContext);
                    tempNumber=tempNumber.divide(BigDecimal.TEN,mathContext);
                    tempNumber=tempNumber.divide(BigDecimal.TEN,mathContext);
                    tempStr=new StringBuffer(tempNumber.toString());
                    updateTempText();
                }
            }
        });
    }

    //clear tempStr and operator
    private void setEqualListener(){
        equal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //while no operator
                if(tempStr.toString().isEmpty()||new BigDecimal(getNumFromStr(tempStr).toString(),mathContext).equals(BigDecimal.ZERO)){
                    return;
                }
                else if(operator.isEmpty()){
                    resultStr=new StringBuffer(tempStr);
                    resultNum.setText("= "+resultStr);
                    resultNum.setTextSize(45);
                    resultNum.setTextColor(Color.BLACK);
                    tempNum.setTextSize(28);
                    tempNum.setTextColor(Color.GRAY);
                }
                else{
                    operateToNum();
                    resultStr = setIntComma(resultStr);
                    resultNum.setText("= "+resultStr);
                    resultNum.setTextSize(45);
                    resultNum.setTextColor(Color.BLACK);
                    tempNum.setTextSize(28);
                    tempNum.setTextColor(Color.GRAY);
                }
                HASBEENEND=true;
            }
        });
    }

    //only clear all comma
    private StringBuffer getNumFromStr(StringBuffer tempText){
        StringBuffer mtempText=new StringBuffer();
        for(int i=0;i<tempText.length();++i){
            if(tempText.charAt(i)!=','){
                mtempText.append(tempText.charAt(i));
            }
        }
        return mtempText;
    }

    private void operateToNum(){

        BigDecimal resultNumber=new BigDecimal(getNumFromStr(resultStr).toString(),mathContext);
        BigDecimal tempNumber=new BigDecimal(getNumFromStr(tempStr).toString(),mathContext);

        switch (operator.charAt(0)){
            case '+':
                resultNumber=resultNumber.add(tempNumber,mathContext);
                break;
            case '-':
                resultNumber=resultNumber.add(tempNumber.negate(),mathContext);
                break;
            case '÷':
                resultNumber=resultNumber.divide(tempNumber,mathContext);
                break;
            case '×':
                resultNumber=resultNumber.multiply(tempNumber,mathContext);
                break;
        }

        resultStr=new StringBuffer(resultNumber.toString());

    }

    private void updateTempText(){
        if(getNumFromStr(tempStr).length()>12){
            tempNum.setTextSize(34);
        }
        else if(getNumFromStr(tempStr).length()>9){
            tempNum.setTextSize(40);
        }
        else if(getNumFromStr(tempStr).length()>6){
            tempNum.setTextSize(45);
        }
        else{
            tempNum.setTextSize(50);
        }
        tempNum.setText(operator+" "+tempStr);
    }

    //change resultNum and operator into empty ,tempNum into zero
    private void initialText(){
        resultStr=new StringBuffer();
        tempStr=new StringBuffer("0");
        operator=new String();
        tempNum.setText(operator+tempStr);
        tempNum.setTextSize(50);
        resultNum.setTextSize(35);
        tempNum.setTextColor(Color.BLACK);
        resultNum.setTextColor(Color.GRAY);
    }

    // determine whether a number is int
    private boolean isInt(StringBuffer tempText){
        for(int i=0;i<tempText.length();++i){
            if(tempText.charAt(i)=='.'){
                return false;
            }
        }
        return true;
    }

    //get the length of a int(not use isInt())
    private int getIntLen(StringBuffer tempText){
        int mLength=0;
        for(int i=0;i<tempText.length();++i){
            if(tempText.charAt(i)<='9'&&tempText.charAt(i)>='0'){
                ++mLength;
            }
        }
        return mLength;
    }

    //tempText must be number without operator except negative and positive
    //when 3,6,9...set int comma
    private StringBuffer setIntComma(StringBuffer tempText){
        if(tempText.length()!=0){
            int signPosition=(tempText.charAt(0)=='+'||tempText.charAt(0)=='-')?1:0;
            ArrayList<Integer> position=new ArrayList<>();
            tempText=clearAllComma(tempText);
            int intLen=tempText.length();
            for(int i=0;i<tempText.length();++i){
                if(tempText.charAt(i)=='.'){
                    intLen=i;
                    break;
                }
            }
            if(intLen-signPosition>3){
                if((intLen-signPosition)%3!=0){
                    position.add((intLen-signPosition)%3+signPosition);
                }
                for(int i=(intLen-signPosition)%3+signPosition+3;i<intLen;i+=3){
                    position.add(i);
                }
                for(int i=0;i<position.size();++i){
                    tempText.insert(position.get(i).intValue()+i,',');
                }
            }
        }
        return tempText;
    }

    //if tempText is empty,return a empty String,can use only to int,return a new copy without comma
    private StringBuffer clearAllComma(StringBuffer tempText){
        StringBuffer mtempText=new StringBuffer();
        for(int i=0;i<tempText.length();++i){
            if(tempText.charAt(i)!=','){
                mtempText.append(tempText.charAt(i));
            }
        }
        return mtempText;
    }

    private boolean legalIntTempRange(StringBuffer tempText,String num){
        if(getIntLen(tempText)<15){
            return true;
        }
        return false;
    }

    private boolean legalDoubleTempRange(StringBuffer tempText,String num){
        if(tempStr.length()<20){
            return true;
        }
        return false;
    }

}
