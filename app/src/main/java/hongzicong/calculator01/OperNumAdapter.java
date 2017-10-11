package hongzicong.calculator01;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.View;
import java.util.List;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.text.Html;

/**
 * Created by DELL-PC on 2017/10/4.
 */

public class OperNumAdapter extends RecyclerView.Adapter<OperNumAdapter.ViewHolder>{

    private List<OperNum> operNumList;

    public OperNumAdapter(List<OperNum> mOperNumList){
        operNumList=mOperNumList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tempNum;
        public ViewHolder(View view){
            super(view);
            tempNum=(TextView)view.findViewById(R.id.oper_num_item);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.oper_num,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OperNum operNum=operNumList.get(position);
        String temp;
        if(operNum.getOperator().equals("+")){
            temp=new String("<font color='#CD9B9B'>"+operNum.getOperator()+"</font> "+operNum.getTempNum());
        }
        else if(operNum.getOperator().equals("-")){
            temp=new String("<font color='#BFEFFF'>"+operNum.getOperator()+"</font> "+operNum.getTempNum());
        }
        else if(operNum.getOperator().equals("×")){
            temp=new String("<font color='#90EE90'>"+operNum.getOperator()+"</font> "+operNum.getTempNum());
        }
        else if(operNum.getOperator().equals("÷")){
            temp=new String("<font color='#E9967A'>"+operNum.getOperator()+"</font> "+operNum.getTempNum());
        }
        else if(operNum.getOperator().equals("- - - - - - - - - - - - - - - - - - - - -")){
            temp=new String("<font color='#262626'>"+operNum.getOperator()+"</font> "+operNum.getTempNum());
        }
        else if(operNum.getOperator().equals("= ")){
            temp=new String("<font color='#FF0000'>"+operNum.getOperator()+"</font> "+operNum.getTempNum());
        }
        else{
            temp=new String(operNum.getTempNum());
        }
        holder.tempNum.setText(Html.fromHtml(temp));
    }

    @Override
    public int getItemCount() {
        return operNumList.size();
    }
}
