package com.electricpanda.ultimatum.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.electricpanda.ultimatum.interfaces.PactListInteractionListener;
import com.electricpanda.ultimatum.R;
import com.electricpanda.ultimatum.entities.Pact;
import com.electricpanda.ultimatum.misc.AppConstants;
import com.electricpanda.ultimatum.misc.AppUtils;

import java.util.List;

public class PactRecyclerViewAdapter extends
        RecyclerView.Adapter<PactRecyclerViewAdapter.PactHolder> {

    private List<Pact> pacts;
    private Context context;
    private static PactListInteractionListener mListener;

    public PactRecyclerViewAdapter(List<Pact> data, Context c, PactListInteractionListener listener) {
        pacts = data;
        context = c;
        mListener = listener;
    }

    @Override
    public PactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.pact_list_card_view_row,
                parent,
                false);

        PactHolder viewHolder = new PactHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PactHolder holder, int position) {
        holder.position = position;
        holder.pactName = pacts.get(position).getHabit();
        holder.start = AppUtils.convertDateToString(pacts.get(position).getStartDate());
        holder.end = AppUtils.convertDateToString(pacts.get(position).getEndDate());
        holder.partnerName = pacts.get(position).getPartnerName(context);

        holder.pactNameText.setText(pacts.get(position).getHabit());
        holder.startDateText.setText("Start Date: " + holder.start);
        holder.endDatetext.setText("End Date: " + holder.end);

        if (holder.partnerName.equals("") || holder.partnerName == null) {
            holder.partnerNameText.setText("Partner Pending.");
        } else {
            holder.partnerNameText.setText("Partner: " + holder.partnerName);
        }
    }

    public void addItem(Pact user, int index) {
        pacts.add(index, user);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        pacts.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return pacts.size();
    }

    public static class PactHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        int position;
        String pactName;
        String start;
        String end;
        String partnerName;

        TextView pactNameText;
        TextView startDateText;
        TextView endDatetext;
        TextView partnerNameText;

        public PactHolder(View view) {
            super(view);
            pactNameText = (TextView) view.findViewById(R.id.habitText);
            startDateText = (TextView) view.findViewById(R.id.startDateText);
            endDatetext = (TextView) view.findViewById(R.id.endDateText);
            partnerNameText = (TextView) view.findViewById(R.id.partnerText);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onPactListClick(position);
            }
        }
    }
}
