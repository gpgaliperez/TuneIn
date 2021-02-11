package com.TuneIn.fragmentos;


import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import androidx.fragment.app.Fragment;

import com.TuneIn.R;

public class ConciertoFragment extends Fragment  {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.first_frag, container, false);

        TextView tv = (TextView) v.findViewById(R.id.textView1);
        tv.setText(getArguments().getString("msg"));
        return v;
    }

    public static ConciertoFragment newInstance(String text) {

        ConciertoFragment f = new ConciertoFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}