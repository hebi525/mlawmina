package android.trizleo.mlawmina.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.trizleo.mlawmina.R;
import android.trizleo.mlawmina.models.Card;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hebi525 on 25-Feb-17.
 */

public class DashboardFragment extends Fragment {
    private RecyclerView recyclerView;

    public static DashboardFragment newInstance(){
        DashboardFragment fragment = new DashboardFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.card_list);

        initRecyclerView();

        return rootView;
    }

    private void initRecyclerView(){
        MyRecyclerAdapter adapter = new MyRecyclerAdapter(getActivity(), initCardList());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(
                2, getActivity().getResources().getDimensionPixelSize(R.dimen.activity_list_vertical_margin), true));
    }

    private ArrayList<Card> initCardList(){
        ArrayList<Card> list = new ArrayList<>();
        list.add(new Card("Case", 99));
        list.add(new Card("Pending Tasks", 99));
        list.add(new Card("Calendar", 99));
        list.add(new Card("Support Tickets!", 99));
        return list;
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textView;
        public TextView textView1;
        public Button button;

        public MyViewHolder(View itemView){
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.card_img);
            textView = (TextView) itemView.findViewById(R.id.card_num);
            textView1 = (TextView) itemView.findViewById(R.id.card_name);
            button = (Button) itemView.findViewById(R.id.card_details);
        }
    }

    private class MyRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder>{
        private LayoutInflater inflater;
        private ArrayList<Card> itemList;

        public MyRecyclerAdapter(Context context, ArrayList<Card> itemList) {
            this.inflater = LayoutInflater.from(context);
            this.itemList = itemList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View rootView = inflater.inflate(R.layout.item_card, parent, false);

            MyViewHolder holder = new MyViewHolder(rootView);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.textView.setText(itemList.get(position).getCardNum()+"");
            holder.textView1.setText(itemList.get(position).getCardName());
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
