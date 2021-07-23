package com.example.wgumobileappericrees.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.wgumobileappericrees.Entity.Course;
import com.example.wgumobileappericrees.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseName;

        private CourseViewHolder(View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.rvList_courseName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Course currentCourse = mCourses.get(position);
                    Intent intent = new Intent(context, CourseDetailsActivity.class);
                    intent.putExtra("courseId", currentCourse.getCourseId());
                    intent.putExtra("termId", currentCourse.getCTermId());
                    intent.putExtra("courseName", currentCourse.getCourseName());
                    intent.putExtra("courseStart", currentCourse.getCourseStart().toString());
                    intent.putExtra("courseEnd", currentCourse.getCourseEnd().toString());
                    intent.putExtra("status", currentCourse.getStatus());
                    intent.putExtra("courseNote", currentCourse.getCourseNote());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<Course> mCourses;

    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_row_course, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        if (mCourses != null) {
            /*final*/ Course currentCourse = mCourses.get(position);
            holder.courseName.setText(currentCourse.getCourseName());
        } else {
            holder.courseName.setText("No Word");
        }
    }

    public void setWords(List<Course> words) {
        mCourses = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCourses != null)
            return mCourses.size();
        else return 0;
    }
}