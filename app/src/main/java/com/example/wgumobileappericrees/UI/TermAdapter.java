package com.example.wgumobileappericrees.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wgumobileappericrees.Entity.Term;
import com.example.wgumobileappericrees.R;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {
    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termName;

        private TermViewHolder(View itemView) {
            super(itemView);
            termName = itemView.findViewById(R.id.rvList_termName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Term currentTerm = mTerms.get(position);
                    Intent intent = new Intent(context, TermDetailsActivity.class);
                    intent.putExtra("termId", currentTerm.getTermId());
                    intent.putExtra("termName", currentTerm.getTermName());
                    intent.putExtra("termStart", currentTerm.getTermStart().toString());
                    intent.putExtra("termEnd", currentTerm.getTermEnd().toString());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                    notifyDataSetChanged();
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<Term> mTerms;

    public TermAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_row_term, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TermViewHolder holder, int position) {
        if (mTerms != null) {
            /*final*/ Term currentTerm = mTerms.get(position);
            holder.termName.setText(currentTerm.getTermName());
        } else {
            holder.termName.setText("No Word");
        }
    }

    public void setWords(List<Term> words) {
        mTerms = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTerms != null)
            return mTerms.size();
        else return 0;
    }
}
