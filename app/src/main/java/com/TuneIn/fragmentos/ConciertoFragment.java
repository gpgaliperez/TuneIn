package com.TuneIn.fragmentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import com.TuneIn.R;
import java.util.ArrayList;

public class ConciertoFragment extends Fragment  {
    private RecyclerView mRecyclerView;
    private ListAdapter mListadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.first_frag, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        /*TextView tv = (TextView) v.findViewById(R.id.textView1);
        tv.setText(getArguments().getString("msg"));
        return v;*/

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        ArrayList<String> data = new ArrayList<String>();
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");
        data.add("Lady Gaga");

        mListadapter = new ListAdapter(data);
        mRecyclerView.setAdapter((Adapter) mListadapter);

        return v;
    }

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{
        private ArrayList<String> dataList;

        public ListAdapter(ArrayList<String> data){
            this.dataList = data;
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            TextView tvConcierto;

            public ViewHolder(View itemView) {
                super(itemView);
                this.tvConcierto = (TextView) itemView.findViewById(R.id.tv_concierto);
            }
        }

        @Override
        public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_prueba, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position){
            holder.tvConcierto.setText(dataList.get(position));

            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v)
                {
                    Toast.makeText(getActivity(), "Item " + position + " is clicked.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount(){
            return dataList.size();
        }
    }

    public static ConciertoFragment newInstance(String text) {

        ConciertoFragment f = new ConciertoFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}