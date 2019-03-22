package com.vagner.agendacontato.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.sip.SipSession;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vagner.agendacontato.R;
import com.vagner.agendacontato.databinding.ItemAdapterContactLayoutBinding;
import com.vagner.agendacontato.interfaces.OnItemClickListener;
import com.vagner.agendacontato.model.Contact;

import java.util.ArrayList;
import java.util.List;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    private List<Contact> contacts;
    private OnItemClickListener listener;

    public ContactAdapter(Context context, OnItemClickListener listener) {
        this.contacts = new ArrayList<>();
        this.listener = listener;
    }

    public void notifyDataSetChanged(List<Contact> list){
        this.contacts.clear();
        this.contacts.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public MyViewHolder onCreateViewHolder(@android.support.annotation.NonNull ViewGroup viewGroup, int i) {

        ItemAdapterContactLayoutBinding view = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_adapter_contact_layout, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@android.support.annotation.NonNull MyViewHolder myViewHolder, int position) {

        Contact c = this.contacts.get(position);

        myViewHolder.binding.tvId.setText("#"+ String.valueOf(c.getId()));
        myViewHolder.binding.tvName.setText(c.getName());
        myViewHolder.binding.getRoot().setOnClickListener(onClick -> this.listener.onClick(position));

    }

    @Override
    public int getItemCount() {
        return this.contacts.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private ItemAdapterContactLayoutBinding binding;

        public MyViewHolder(ItemAdapterContactLayoutBinding itemView) {

            super(itemView.getRoot());
            this.binding = itemView;
        }
    }

}
