package hongzicong.calculator01;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by DELL-PC on 2017/10/7.
 */

public class StringAdapter extends RecyclerView.Adapter<StringAdapter.ViewHolder>{

    private List<String> operNumList;

    public StringAdapter(List<String> mOperNumList){
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
    public StringAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.oper_num,parent,false);
        StringAdapter.ViewHolder holder=new StringAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(StringAdapter.ViewHolder holder, int position) {
        String operNum=operNumList.get(position);
        holder.tempNum.setText(Html.fromHtml(operNum));
    }

    @Override
    public int getItemCount() {
        return operNumList.size();
    }
}
