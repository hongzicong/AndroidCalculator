package hongzicong.calculator01;

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

}
