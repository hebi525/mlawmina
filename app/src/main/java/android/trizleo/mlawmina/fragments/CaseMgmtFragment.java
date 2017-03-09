package android.trizleo.mlawmina.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Toast.makeText(getActivity(), "+onActivityResult", Toast.LENGTH_SHORT).show();

    }

    private void initRecyclerView(){
        if(MainActivity.currentUserSession!=null) {
            MyRecyclerAdapter adapter = new MyRecyclerAdapter(getActivity(), (ArrayList<Case>) MainActivity.currentUserSession.getUserCaseList());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
            recyclerView.addOnItemTouchListener(new MyOnItemTouchListener(getActivity(), recyclerView, new RClickListener() {
                @Override
                public void onClick(View view, int position) {
                    //TODO: this should show dialog with case info and controls.
                }

                @Override
                public void onLongClick(View view, int position) {
                    Toast.makeText(getActivity(), "hello "+position, Toast.LENGTH_SHORT).show();


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

        public MyViewHolder(View itemView){
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.case_name);
            textView2 = (TextView) itemView.findViewById(R.id.case_type);
            textView1 = (TextView) itemView.findViewById(R.id.case_date_opened);
        }
    }

    private class MyRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder>{
        private LayoutInflater inflater;
        private ArrayList<Case> itemList;

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
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.textView.setText(itemList.get(position).getName());
            holder.textView1.setText(itemList.get(position).getType());
            holder.textView2.setText("03/02/1995");
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }
    }
}
