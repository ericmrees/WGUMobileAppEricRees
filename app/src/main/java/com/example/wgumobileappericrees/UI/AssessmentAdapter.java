package com.example.wgumobileappericrees.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.wgumobileappericrees.Entity.Assessment;
import com.example.wgumobileappericrees.R;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentName;

        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentName = itemView.findViewById(R.id.rvList_assessmentName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Assessment currentAssessment = mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetailsActivity.class);
                    intent.putExtra("assessmentId", currentAssessment.getAssessmentId());
                    intent.putExtra("courseId", currentAssessment.getACourseId());
                    intent.putExtra("assessmentName", currentAssessment.getAssessmentName());
                    intent.putExtra("assessmentStart", currentAssessment.getAssessmentStart().toString());
                    intent.putExtra("assessmentEnd", currentAssessment.getAssessmentEnd().toString());
                    intent.putExtra("type", currentAssessment.getType());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                    notifyDataSetChanged();
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<Assessment> mAssessments;

    public AssessmentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public AssessmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_row_assessment, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AssessmentViewHolder holder, int position) {
        if (mAssessments != null) {
            final Assessment currentAssessment = mAssessments.get(position);
            holder.assessmentName.setText(currentAssessment.getAssessmentName());
        } else {
            holder.assessmentName.setText("No Word");
        }
    }

    @Override
    public int getItemCount() {
        if (mAssessments != null)
            return mAssessments.size();
        else return 0;
    }

    public void setWords(List<Assessment> words) {
        mAssessments = words;
        notifyDataSetChanged();
    }
}
