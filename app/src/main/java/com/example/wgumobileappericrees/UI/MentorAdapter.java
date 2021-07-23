package com.example.wgumobileappericrees.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wgumobileappericrees.Entity.Mentor;
import com.example.wgumobileappericrees.R;

import java.util.List;

public class MentorAdapter extends RecyclerView.Adapter<MentorAdapter.MentorViewHolder> {
    class MentorViewHolder extends RecyclerView.ViewHolder {
        private final TextView mentorName;

        private MentorViewHolder(View itemView) {
            super(itemView);
            mentorName = itemView.findViewById(R.id.rvList_mentorName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Mentor currentMentor = mMentors.get(position);
                    Intent intent = new Intent(context, MentorDetailsActivity.class);
                    intent.putExtra("mentorId", currentMentor.getMentorId());
                    intent.putExtra("courseId", currentMentor.getMCourseId());
                    intent.putExtra("mentorName", currentMentor.getMentorName());
                    intent.putExtra("mentorPhone", currentMentor.getMentorPhone());
                    intent.putExtra("mentorEmail", currentMentor.getMentorEmail());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                    notifyDataSetChanged();
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<Mentor> mMentors;

    public MentorAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public MentorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_row_mentor, parent, false);
        return new MentorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MentorViewHolder holder, int position) {
        if (mMentors != null) {
            final Mentor currentMentor = mMentors.get(position);
            holder.mentorName.setText(currentMentor.getMentorName());
        } else {
            holder.mentorName.setText("No Word");
        }
    }

    @Override
    public int getItemCount() {
        if (mMentors != null)
            return mMentors.size();
        else return 0;
    }

    public void setWords(List<Mentor> words) {
        mMentors = words;
        notifyDataSetChanged();
    }
}
