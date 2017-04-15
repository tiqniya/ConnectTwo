package com.nkdroid.tinderswipe;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nkdroid.tinderswipe.tindercard.FlingCardListener;
import com.nkdroid.tinderswipe.tindercard.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements FlingCardListener.ActionDownInterface {

    public static MyAppAdapter myAppAdapter;
    public static ViewHolder viewHolder;
    private ArrayList<Data> al;
    private SwipeFlingAdapterView flingContainer;

    public static void removeBackground() {
        viewHolder.background.setVisibility(View.GONE);
        myAppAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        al = new ArrayList<>();
        al.add(new Data("Run 10 miles naked", "Lose $1000"));
        al.add(new Data("Run 20 miles naked", "Lose $2000"));
        al.add(new Data("Run 20 miles naked", "Lose $2000"));
        al.add(new Data("Run 20 miles naked", "Lose $2000"));
        al.add(new Data("Run 20 miles naked", "Lose $2000"));

        myAppAdapter = new MyAppAdapter(al, MainActivity.this);
        flingContainer.setAdapter(myAppAdapter);


        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                al.remove(0);
                myAppAdapter.notifyDataSetChanged();
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject

            }

            @Override
            public void onRightCardExit(Object dataObject) {

                al.remove(0);
                myAppAdapter.notifyDataSetChanged();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

                View view = flingContainer.getSelectedView();
                TextView q1 = (TextView) view.findViewById(R.id.questionOne);
                TextView q2 = (TextView) view.findViewById(R.id.questionTwo);
                if (scrollProgressPercent < 0) {
                    q1.setTextColor(Color.BLUE);
                    q2.setTextColor(Color.BLACK);
                } else if (scrollProgressPercent > 0) {
                    q1.setTextColor(Color.BLACK);
                    q2.setTextColor(Color.RED);
                } else {
                    q1.setTextColor(Color.BLACK);
                    q2.setTextColor(Color.BLACK);
                }
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

                View view = flingContainer.getSelectedView();

                myAppAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onActionDownPerform() {
        Log.e("action", "bingo");
    }

    public static class ViewHolder {
        public static FrameLayout background;
        public TextView qOne;
        public TextView qTwo;
    }

    //in charge of changing the view
    public class MyAppAdapter extends BaseAdapter {

        public List<Data> parkingList;
        public Context context;

        private MyAppAdapter(List<Data> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
        }

        @Override
        public int getCount() {
            return parkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;

            if (rowView == null) {

                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.item, parent, false);
                // configure view holder
                viewHolder = new ViewHolder();
                viewHolder.qOne = (TextView) rowView.findViewById(R.id.questionOne);
                viewHolder.qTwo = (TextView) rowView.findViewById(R.id.questionTwo);
                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.qOne.setText(parkingList.get(position).getQuestionOne() + "");
            viewHolder.qTwo.setText(parkingList.get(position).getQuestionTwo() + "");

            return rowView;
        }
    }
}
