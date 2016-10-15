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
        holder.pactName = pacts.get(position).getHabit();
        holder.pactNameText.setText(pacts.get(position).getHabit());
        holder.position = position;
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
        String pactName;
        TextView pactNameText;
        int position;

        public PactHolder(View view) {
            super(view);
            pactNameText = (TextView) view.findViewById(R.id.text1);
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
