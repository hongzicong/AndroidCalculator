package hongzicong.calculator01;

import android.text.Html;

/**
 * Created by DELL-PC on 2017/10/4.
 */

public class OperNum {

    private String operator;
    private StringBuffer tempNum;

    public OperNum(String mOperator,StringBuffer mTempNum){
        operator=mOperator;
        tempNum=mTempNum;
    }

    public String getOperator(){
        return operator;
    }

    public StringBuffer getTempNum(){
        return tempNum;
    }

    @Override
    public String toString() {
        String temp;
        if(getOperator().equals("+")){
            temp=new String("<font color='#CD9B9B'>"+getOperator()+"</font> "+getTempNum());
        }
        else if(getOperator().equals("-")){
            temp=new String("<font color='#BFEFFF'>"+getOperator()+"</font> "+getTempNum());
        }
        else if(getOperator().equals("×")){
            temp=new String("<font color='#90EE90'>"+getOperator()+"</font> "+getTempNum());
        }
        else if(getOperator().equals("÷")){
            temp=new String("<font color='#E9967A'>"+getOperator()+"</font> "+getTempNum());
        }
        else if(getOperator().equals("- - - - - - - - - - - - - - - - - - - - -")){
            temp=new String("<font color='#262626'>"+getOperator()+"</font> "+getTempNum());
        }
        else if(getOperator().equals("= ")){
            temp=new String("<font color='#FF0000'>"+getOperator()+"</font> "+getTempNum());
        }
        else{
            temp=new String(getTempNum());
        }
        return temp;
    }
}
