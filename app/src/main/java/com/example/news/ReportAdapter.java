package com.example.news;// ReportAdapter.java
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.news.R;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    private Context context;
    private List<Report> reports;

    public ReportAdapter(Context context, List<Report> reports) {
        this.context = context;
        this.reports = reports;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_report, parent, false);
        return new ReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        Report report = reports.get(position);
        holder.bind(report);
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    public class ReportViewHolder extends RecyclerView.ViewHolder {

        TextView damageTypeTextView;
        TextView descriptionTextView;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            damageTypeTextView = itemView.findViewById(R.id.damageTypeTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }

        public void bind(Report report) {
            damageTypeTextView.setText(report.getDamageType());
            descriptionTextView.setText(report.getDescription());
        }
    }
}
