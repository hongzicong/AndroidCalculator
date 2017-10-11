package hongzicong.calculator01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.math.BigDecimal;
import java.math.MathContext;
import android.widget.HorizontalScrollView;
import android.view.WindowManager;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

public class Scientific extends AppCompatActivity {

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
    private Button delete;
    private Button add;
    private Button minus;
    private Button multiple;
    private Button divide;
    private Button equal;
    private Button clear;
    private Button sin;
    private Button cos;
    private Button tan;
    private Button ln;
    private Button lg;
    private Button log2;
    private Button logN;
    private Button pi;
    private Button leftBracket;
    private Button rightBracket;
    private Button e;
    private Button factorButton;
    private Button powButton;
    private Button sqrtButton;
    private Button turn;
    private Button left;
    private Button right;
    private Button degree;
    private EditText resultNum;
    private EditText tempNum;
    private HorizontalScrollView tempScrollView;
    private StringBuffer tempStr;
    private StringBuffer resultStr;
    private List<String> tempNumber=new ArrayList<>();
    private Stack<String> tempOper1=new Stack<>();
    private Stack<BigDecimal> temp=new Stack<>();
    private MathContext mathContext=MathContext.DECIMAL64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scientific);
        setActionBar();
        setAllButton();
        setAllText();
        initialText();
        setAllListener();
    }

    private void setActionBar(){
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        Window window=this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.BLACK);
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
        add=(Button)findViewById(R.id.add);
        minus=(Button)findViewById(R.id.minus);
        multiple=(Button)findViewById(R.id.multiple);
        divide=(Button)findViewById(R.id.divide);
        equal=(Button)findViewById(R.id.equal);
        delete=(Button)findViewById(R.id.delete);
        clear=(Button)findViewById(R.id.clear);
        sin=(Button)findViewById(R.id.sin);
        cos=(Button)findViewById(R.id.cos);
        tan=(Button)findViewById(R.id.tan);
        ln=(Button)findViewById(R.id.ln);
        lg=(Button)findViewById(R.id.lg);
        log2=(Button)findViewById(R.id.log2);
        logN=(Button)findViewById(R.id.logn);
        pi=(Button)findViewById(R.id.pi);
        leftBracket=(Button)findViewById(R.id.leftBracket);
        rightBracket=(Button)findViewById(R.id.rightBracket);
        e=(Button)findViewById(R.id.e);
        factorButton=(Button)findViewById(R.id.factorial);
        powButton=(Button)findViewById(R.id.pow);
        sqrtButton=(Button)findViewById(R.id.sqrt);
        turn=(Button)findViewById(R.id.turn_to_normal);
        right=(Button)findViewById(R.id.right);
        left=(Button)findViewById(R.id.left);
        degree=(Button)findViewById(R.id.degree);
    }

    private void setAllText(){
        resultNum=(EditText) findViewById(R.id.resultNum);
        tempNum=(EditText) findViewById(R.id.tempNum);
        tempScrollView=(HorizontalScrollView)findViewById(R.id.tempScrollView);
    }

    private void setAllListener(){
        setZeroListener(button0);
        setNumListener(button1);
        setNumListener(button2);
        setNumListener(button3);
        setNumListener(button4);
        setNumListener(button5);
        setNumListener(button6);
        setNumListener(button7);
        setNumListener(button8);
        setNumListener(button9);
        setDotListener();
        setTurnListener();
        setOperatorListener(multiple);
        setOperatorListener(add);
        setOperatorListener(minus);
        setOperatorListener(divide);
        setDeleteListener();
        setSinCosTanListener(sin);
        setSinCosTanListener(cos);
        setSinCosTanListener(tan);
        setLnOrLogListener(ln);
        setLnOrLogListener(lg);
        setLnOrLogListener(log2);
        setLogNListener();
        setBracketListener(leftBracket);
        setBracketListener(rightBracket);
        setEorPi(e);
        setEorPi(pi);
        setSqrtButton();
        setLeftListener();
        setRightListener();
        setEqualListener();
        setFactorListener();
        setClearListener();
        setDegreeListener();
        setPowListener();
    }

    private void setTurnListener(){
        turn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setClearListener(){
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialText();
            }
        });
    }

    private void setDotListener(){
        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToTemp(".");
                tempScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        tempScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                    }
                });
            }
        });
    }

    private void setZeroListener(Button mButton){
        mButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                writeToTemp("0");
                tempScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        tempScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                    }
                });
            }
        });
    }

    private void setNumListener(Button mButton){
        final String num=mButton.getText().toString();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToTemp(num);
                tempScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        tempScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                    }
                });
            }
        });
    }

    private void initialText(){
        tempStr=new StringBuffer();
        resultStr=new StringBuffer();
        tempNum.setText("");
        resultNum.setText(resultStr);
    }

    private void setPowListener(){
        powButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToTemp("^(");
                tempScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        tempScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                    }
                });
            }
        });
    }

    private void setOperatorListener(Button mButton){
        final String operator=mButton.getText().toString();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToTemp(operator);
                tempScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        tempScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                    }
                });
            }
        });
    }

    private void setSqrtButton(){
        sqrtButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                writeToTemp("√(");
                tempScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        tempScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                    }
                });
            }
        });
    }

    private void setDeleteListener(){
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(tempStr.length()!=0){
                    tempNum.requestFocus();
                    int position=tempNum.getSelectionStart();
                    tempStr.deleteCharAt(position-1);
                    tempNum.setText(tempStr);
                    tempNum.setSelection(position-1);
                    tempScrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            tempScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                        }
                    });
                }
            }
        });
    }

    private void setSinCosTanListener(Button mButton) {
        final String operator=mButton.getText().toString();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToTemp(operator+"(");
                tempScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        tempScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                    }
                });
            }
        });
    }

    private void setEqualListener(){
        equal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(operateNum(tempStr)){
                    resultNum.setText(" = "+resultStr);
                }
                else{
                    resultNum.setText("Error");
                }
            }
        });
    }

    private void setDegreeListener(){
        degree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToTemp("°");
                tempScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        tempScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                    }
                });
            }
        });
    }

    private void setLnOrLogListener(Button mButton){
        final String operator=mButton.getText().toString();
        mButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                writeToTemp(operator+"(");
                tempScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        tempScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                    }
                });
            }
        });
    }

    private void setLogNListener(){
        logN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToTemp("logN(,");
                tempScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        tempScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                    }
                });
            }
        });
    }

    private void setBracketListener(Button mButton){
        final String bracket=mButton.getText().toString();
        mButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                writeToTemp(bracket);
                tempScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        tempScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                    }
                });
            }
        });
    }

    private void setEorPi(Button mButton){
        final String number=mButton.getText().toString();
        mButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                writeToTemp(number);
                tempScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        tempScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                    }
                });
            }
        });
    }

    private void setCursorToPos(EditText mTempNum,int position){
        mTempNum.requestFocus();
        mTempNum.setSelection(position);
    }

    private void setCursorToEnd(EditText mTempNum){
        setCursorToPos(mTempNum,mTempNum.length());
    }

    private void setLeftListener(){
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempNum.requestFocus();
                int position=tempNum.getSelectionStart();
                if(position!=0){
                    tempNum.setSelection(position-1);
                }
            }
        });
    }

    private void setRightListener(){
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempNum.requestFocus();
                int position=tempNum.getSelectionStart();
                if(position!=tempNum.length()){
                    tempNum.setSelection(position+1);
                }
            }
        });
    }

    private void setFactorListener(){
        factorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToTemp("!");
            }
        });
    }

    private void writeToTemp(String operator){
        if(tempNum.getSelectionStart()==tempNum.length()){
            tempStr.append(operator);
            tempNum.setText(tempStr);
            setCursorToEnd(tempNum);
        }
        else{
            int position=tempNum.getSelectionStart();
            tempStr.insert(position,operator);
            tempNum.setText(tempStr);
            setCursorToPos(tempNum,position+operator.length());
        }
    }

    private boolean operateNum(StringBuffer tempStr){
        try{int i=0;
            while(i!=tempStr.length()){
                if((tempStr.charAt(i)=='.')||(tempStr.charAt(i)>='0'&&tempStr.charAt(i)<='9')){
                    i=getNumber(tempStr,i);
                }
                else if(tempStr.charAt(i)=='s'){
                    if(i+3<tempStr.length()&&tempStr.charAt(i+1)=='i'&&tempStr.charAt(i+2)=='n'&&tempStr.charAt(i+3)=='('){
                        i+=3;
                        tempOper1.add("sin");
                    }
                    else{
                        return false;
                    }
                }
                else if(tempStr.charAt(i)=='c'){
                    if(i+3<tempStr.length()&&tempStr.charAt(i+1)=='o'&&tempStr.charAt(i+2)=='s'&&tempStr.charAt(i+3)=='('){
                        i+=3;
                        tempOper1.add("cos");
                    }
                    else{
                        return false;
                    }
                }
                else if(tempStr.charAt(i)=='t'){
                    if(i+3<tempStr.length()&&tempStr.charAt(i+1)=='a'&&tempStr.charAt(i+2)=='n'&&tempStr.charAt(i+3)=='('){
                        i+=3;
                        tempOper1.add("tan");
                    }
                    else{
                        return false;
                    }
                }
                else if(tempStr.charAt(i)=='!'){
                    i+=1;
                    tempNumber.add("!");
                }
                else if(tempStr.charAt(i)=='l'){
                    if(i+1<tempStr.length()&&tempStr.charAt(i+1)=='n'){
                        i+=2;
                        tempOper1.add("ln");
                    }
                    else if(i+1<tempStr.length()&&tempStr.charAt(i+1)=='g'){
                        i+=2;
                        tempOper1.add("lg");
                    }
                    else if(i+3<tempStr.length()&&tempStr.charAt(i+1)=='o'&&tempStr.charAt(i+2)=='g'&&tempStr.charAt(i+3)=='2'){
                        i+=4;
                        tempOper1.add("log2");
                    }
                    else if(i+4<tempStr.length()&&tempStr.charAt(i+1)=='o'&&tempStr.charAt(i+2)=='g'&&tempStr.charAt(i+3)=='N'){
                        i+=4;
                        tempOper1.add("logN");
                    }
                    else{
                        return false;
                    }
                }
                else if(tempStr.charAt(i)=='('){
                    i+=1;
                    tempOper1.add("(");
                }
                else if(tempStr.charAt(i)=='π'){
                    i+=1;
                    tempNumber.add("π");
                }
                else if(tempStr.charAt(i)=='e'){
                    i+=1;
                    tempNumber.add("e");
                }
                else if(tempStr.charAt(i)=='√'){
                    i+=1;
                    tempOper1.add("√");
                }
                else if(tempStr.charAt(i)=='°'){
                    i+=1;
                    tempNumber.add("°");
                }
                else if(tempStr.charAt(i)=='+'){
                    if(!tempOper1.empty()){
                        String tempOperator=tempOper1.pop();
                        while(!tempOper1.empty()&&(tempOperator.equals("×")||tempOperator.equals("÷")||tempOperator.equals("+")||tempOperator.equals("-"))){
                            tempNumber.add(tempOperator);
                            tempOperator=tempOper1.pop();
                        }
                        tempOper1.add(tempOperator);
                    }
                    tempOper1.add("+");
                    i+=1;
                }
                else if(tempStr.charAt(i)=='-'){
                    if(!tempOper1.empty()){
                        String tempOperator=tempOper1.pop();
                        while(!tempOper1.empty()&&(tempOperator.equals("×")||tempOperator.equals("÷")||tempOperator.equals("+")||tempOperator.equals("-"))){
                            tempNumber.add(tempOperator);
                            tempOperator=tempOper1.pop();
                        }
                        tempOper1.add(tempOperator);
                    }
                    tempOper1.add("-");
                    i+=1;
                }
                else if(tempStr.charAt(i)=='×'){
                    tempOper1.add("×");
                    i+=1;
                }
                else if(tempStr.charAt(i)=='÷'){
                    tempOper1.add("÷");
                    i+=1;
                }
                else if(tempStr.charAt(i)=='^'){
                    i+=1;
                    tempOper1.add("^");
                }
                else if(tempStr.charAt(i)==','){
                    int j=tempOper1.size()-1;
                    for(;j>=0;--j){
                        String top=tempOper1.pop();
                        if(!(top.equals("(")||top.equals(","))){
                            tempNumber.add(top);
                        }
                        else{
                            tempOper1.add(top);
                            break;
                        }
                    }
                    i+=1;
                    tempOper1.add(",");
                }
                else if(tempStr.charAt(i)==')'){
                    int j=tempOper1.size()-1;
                    for(;j>=0;--j){
                        String top=tempOper1.pop();
                        if(!top.equals("(")){
                            tempNumber.add(top);
                        }
                        else{
                            break;
                        }
                    }
                    for(;j>=0;--j){
                        if(!tempOper1.empty()){
                            String top=tempOper1.pop();
                            if(top.equals("^")||top.equals("√")||top.equals("log")||top.equals("ln")||top.equals("tan")||top.equals("cos")||top.equals("sin")){
                                tempNumber.add(top);
                            }
                            else{
                                tempOper1.add(top);
                                break;
                            }
                        }
                    }
                    i+=1;
                }
                else{
                    return false;
                }
            }
            while(!tempOper1.isEmpty()){
                tempNumber.add(tempOper1.pop());
            }

            for(int k=0;k<tempNumber.size();++k){
                System.out.println(tempNumber.get(k));
            }

            for(int k=0;k<tempNumber.size();++k){
                if(tempNumber.get(k).charAt(0)=='.'||(tempNumber.get(k).charAt(0)<='9'&&tempNumber.get(k).charAt(0)>='0')){
                    temp.add(new BigDecimal(tempNumber.get(k),mathContext));
                }
                else if(tempNumber.get(k).equals("+")){
                    BigDecimal a=temp.pop();
                    BigDecimal b=temp.pop();
                    temp.add(a.add(b,mathContext));
                }
                else if(tempNumber.get(k).equals("-")){
                    BigDecimal a=temp.pop();
                    BigDecimal b=temp.pop();
                    temp.add(b.add(a.negate(),mathContext));
                }
                else if(tempNumber.get(k).equals("×")){
                    BigDecimal a=temp.pop();
                    BigDecimal b=temp.pop();
                    temp.add(a.multiply(b,mathContext));
                }
                else if(tempNumber.get(k).equals("÷")){
                    BigDecimal a=temp.pop();
                    BigDecimal b=temp.pop();
                    temp.add(b.divide(a,mathContext));
                }
                else if(tempNumber.get(k).equals("!")){
                    BigDecimal a=temp.pop();
                    temp.add(getFactorial(a));
                }
                else if(tempNumber.get(k).equals("°")){
                    BigDecimal a=temp.pop();
                    a=a.multiply(new BigDecimal(Double.toString(Math.PI),mathContext),mathContext);
                    a=a.divide(new BigDecimal(180,mathContext),mathContext);
                    temp.add(a);
                }
                else if(tempNumber.get(k).equals("tan")){
                    BigDecimal a=temp.pop();
                    a=new BigDecimal(Double.toString(Math.tan(a.doubleValue())),mathContext);
                    temp.add(a);
                }
                else if(tempNumber.get(k).equals("sin")){
                    BigDecimal a=temp.pop();
                    a=new BigDecimal(Double.toString(Math.sin(a.doubleValue())),mathContext);
                    temp.add(a);
                }
                else if(tempNumber.get(k).equals("cos")){
                    BigDecimal a=temp.pop();
                    a=new BigDecimal(Double.toString(Math.cos(a.doubleValue())),mathContext);
                    temp.add(a);
                }
                else if(tempNumber.get(k).equals("π")){
                    temp.add(new BigDecimal(Double.toString(Math.PI),mathContext));
                }
                else if(tempNumber.get(k).equals("e")){
                    temp.add(new BigDecimal(Double.toString(Math.E),mathContext));
                }
                else if(tempNumber.get(k).equals("√")){
                    BigDecimal a=temp.pop();
                    a=new BigDecimal(Double.toString(Math.sqrt(a.doubleValue())),mathContext);
                    temp.add(a);
                }
                else if(tempNumber.get(k).equals("^")){
                    BigDecimal a=temp.pop();
                    BigDecimal b=temp.pop();
                    a=new BigDecimal(Double.toString(Math.pow(b.doubleValue(),a.doubleValue())),mathContext);
                    temp.add(a);
                }
                else if(tempNumber.get(k).equals("lg")){
                    BigDecimal a=temp.pop();
                    BigDecimal b=new BigDecimal(Double.toString(Math.log(a.doubleValue())),mathContext);
                    a=new BigDecimal(Double.toString(Math.log(10)),mathContext);
                    a=b.divide(a,mathContext);
                    temp.add(a);
                }
                else if(tempNumber.get(k).equals("ln")){
                    BigDecimal a=temp.pop();
                    a=new BigDecimal(Double.toString(Math.log(a.doubleValue())),mathContext);
                    temp.add(a);
                }
                else if(tempNumber.get(k).equals("log2")){
                    BigDecimal a=temp.pop();
                    BigDecimal b=new BigDecimal(Double.toString(Math.log(a.doubleValue())),mathContext);
                    a=new BigDecimal(Double.toString(Math.log(2)),mathContext);
                    a=b.divide(a,mathContext);
                    temp.add(a);
                }
                else if(tempNumber.get(k).equals(",")){

                }
                else if(tempNumber.get(k).equals("logN")){
                    BigDecimal a=temp.pop();
                    BigDecimal b=temp.pop();
                    a=new BigDecimal(Double.toString(Math.log(a.doubleValue())),mathContext);
                    b=new BigDecimal(Double.toString(Math.log(b.doubleValue())),mathContext);
                    a=a.divide(b,mathContext);
                    temp.add(a);
                }
            }
            resultStr=new StringBuffer(temp.pop().toString());
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private int getNumber(StringBuffer tempStr,int position){
        int i=position;
        StringBuffer temp=new StringBuffer();
        for(;i<tempStr.length()&&((tempStr.charAt(i)=='.')||(tempStr.charAt(i)>='0'&&tempStr.charAt(i)<='9'));++i){
            temp.append(tempStr.charAt(i));
        }
        tempNumber.add(temp.toString());
        return i;
    }

    private BigDecimal getFactorial(BigDecimal a){
        BigDecimal result=new BigDecimal("1");
        int a_int=a.intValue();
        for(int i=1;i<=a_int;++i){
            result=result.multiply(new BigDecimal(i));
        }
        return result;
    }


}
