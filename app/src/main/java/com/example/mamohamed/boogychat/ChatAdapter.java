package com.example.mamohamed.boogychat;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by mamohamed on 3/23/2018.
 */


public class ChatAdapter extends BaseAdapter{

    private Activity mActivity;
    private DatabaseReference mDatabaseRef;
    private String mDisplayMsg;
    private ArrayList<DataSnapshot> mSnapShotList;



    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            mSnapShotList.add(dataSnapshot);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    public ChatAdapter(Activity activity, DatabaseReference ref, String name){
        mActivity = activity;
        mDisplayMsg =name;
        mDatabaseRef = ref.child("mesgs");
        mDatabaseRef.addChildEventListener(mListener);
        mSnapShotList = new ArrayList<>();

    }
    private static class  ViewHolder{
        TextView authorName;
        TextView body;
        LinearLayout.LayoutParams params;
    }
    @Override
    public int getCount() {
         /*int[] myArray ={0, -5,100};
        myArray[2] = 200;
*/

        return mSnapShotList.size();
    }

    @Override
    public InstantMessage getItem(int position) {
        DataSnapshot snapshot = mSnapShotList.get(position);
        return snapshot.getValue(InstantMessage.class);

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chat_msg_row,parent,false);

            final ViewHolder holder = new ViewHolder();
            holder.authorName = (TextView) convertView.findViewById(R.id.author);
            holder.body = (TextView) convertView.findViewById(R.id.message);
            holder.params = (LinearLayout.LayoutParams) holder.authorName.getLayoutParams();
            convertView.setTag(holder);
        }

        final InstantMessage message = getItem(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();

        boolean isMe = message.getAuthor().equals(mDisplayMsg);
        setChatRowAppearance(isMe, holder);
        String author = message.getAuthor();
        String msg = message.getAuthor();
        holder.authorName.setText(author);
        holder.authorName.setText(msg);
        return convertView;
    }
    private void setChatRowAppearance(boolean isItMe, ViewHolder holder) {

        if (isItMe) {
            holder.params.gravity = Gravity.END;
            holder.authorName.setTextColor(Color.GREEN);
            holder.body.setBackgroundResource(R.drawable.bubble2);
        } else {
            holder.params.gravity = Gravity.START;
            holder.authorName.setTextColor(Color.BLUE);
            holder.body.setBackgroundResource(R.drawable.bubble1);
        }

        holder.authorName.setLayoutParams(holder.params);
        holder.body.setLayoutParams(holder.params);

    }
    public void cleanUp(){
        mDatabaseRef.removeEventListener(mListener);
    }
}
