package android.trizleo.mlawmina.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.trizleo.mlawmina.AddCaseActivity;
import android.trizleo.mlawmina.MainActivity;
import android.trizleo.mlawmina.MyOnItemTouchListener;
import android.trizleo.mlawmina.R;
import android.trizleo.mlawmina.UserSession;
import android.trizleo.mlawmina.models.Case;
import android.trizleo.mlawmina.models.RClickListener;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hebi525 on 26-Feb-17.
 */

public class CaseMgmtFragment extends Fragment{
    private UserSession userSession;
    private RecyclerView recyclerView;

    public static CaseMgmtFragment newInstance(){
        CaseMgmtFragment fragment = new CaseMgmtFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_case_mgmt, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.case_mgmt_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), AddCaseActivity.class);
                startActivityForResult(intent, 1);

            }
        });

        recyclerView = (RecyclerView) rootView.findViewById(R.id.case_mgmt_list);

        initRecyclerView();

        return rootView;
    }

    // Recreate the fragment to reload list data
    //TODO: must implement swipe to refresh here THIS IS NOT WORKING
    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText(getActivity(), "+onResume", Toast.LENGTH_SHORT).show();
        initRecyclerView();
    }

    private void initRecyclerView(){
        if(MainActivity.currentUserSession!=null) {
            MyRecyclerAdapter adapter = new MyRecyclerAdapter(getActivity(), MainActivity.currentUserSession.getUserCaseList());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.addOnItemTouchListener(new MyOnItemTouchListener(getActivity(), recyclerView, new RClickListener() {
                @Override
                public void onClick(View view, int position) {
                }

                @Override
                public void onLongClick(View view, final int position) {
                    //TODO: YEY IT WORKED, NOW TO ADD ANIMATIONS ON ACCESS CONTROL FOR EACH CASE ITEM!
                    MyRecyclerAdapter adapter = (MyRecyclerAdapter) recyclerView.getAdapter();
                    adapter.notifyItemChanged(adapter.getPreviousToggled());
                    adapter.notifyItemChanged(recyclerView.getChildPosition(view));

                    if(adapter.getPreviousToggled() >= 0) {
                        adapter.getItemList().get(adapter.getPreviousToggled()).isToggled = false;
                    }
                    adapter.getItemList().get(position).isToggled = true;

                    adapter.setPreviousToggled(position);
                }
            }));
        }
        else{
            //TODO: initialize empty list
        }
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public TextView textView1;
        public TextView textView2;
//        public TextView textView3;
//        public TextView textView4;
        public ImageButton imageButton;
        public ImageButton imageButton1;

        public MyViewHolder(View itemView){
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.case_name);
            textView1 = (TextView) itemView.findViewById(R.id.case_type);
            textView2 = (TextView) itemView.findViewById(R.id.case_date_opened);
//            textView3 = (TextView) itemView.findViewById(R.id.case_number);
//            textView4 = (TextView) itemView.findViewById(R.id.case_description);
            imageButton = (ImageButton) itemView.findViewById(R.id.case_btn_delete);
            imageButton1 = (ImageButton) itemView.findViewById(R.id.case_btn_edit);
        }
    }

    private class MyRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder>{
        public int previousToggled = -1;
        private LayoutInflater inflater;
        private ArrayList<Case> itemList;
        public String selectedItems;

        public MyRecyclerAdapter(Context context, ArrayList<Case> itemList) {
            this.inflater = LayoutInflater.from(context);
            this.itemList = itemList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View rootView = inflater.inflate(R.layout.item_case, parent, false);

            MyViewHolder holder = new MyViewHolder(rootView);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.textView.setText(itemList.get(position).getName());
            holder.textView1.setText(itemList.get(position).getType());
            holder.textView2.setText("03/02/1995");
//            holder.textView3.setText(itemList.get(position).getNumber()+"");
//            holder.textView4.setText(itemList.get(position).getDescription());

            if(itemList.get(position).isToggled){
                holder.imageButton.setVisibility(View.VISIBLE);
                holder.imageButton1.setVisibility(View.VISIBLE);

                holder.imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, getItemCount());
                    }
                });
//                holder.textView3.setVisibility(View.VISIBLE);
//                holder.textView4.setVisibility(View.VISIBLE);
            }
            else{
                if(holder.imageButton.getVisibility() == View.VISIBLE){
                    holder.imageButton.setVisibility(View.GONE);
                }if(holder.imageButton1.getVisibility() == View.VISIBLE){
                    holder.imageButton1.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

        public void setPreviousToggled(int previousToggled) {
            this.previousToggled = previousToggled;
        }

        public int getPreviousToggled() {
            return previousToggled;
        }

        public ArrayList<Case> getItemList() {
            return itemList;
        }
    }
}
