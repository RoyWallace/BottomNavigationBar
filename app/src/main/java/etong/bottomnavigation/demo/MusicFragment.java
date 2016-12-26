package etong.bottomnavigation.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import etong.bottomnavigation.lib.BottomNavigationBar;


public class MusicFragment extends Fragment {


    private LinearLayout lineaLayout;

    private BottomNavigationBar navigationBar;

    public MusicFragment() {
        // Required empty public constructor
    }

    public static MusicFragment newInstance() {
        MusicFragment fragment = new MusicFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_music, container, false);
        navigationBar = (BottomNavigationBar) contentView.findViewById(R.id.navigationBar);
        return contentView;
    }

    void setUpNavigationBar(){

    }
}
