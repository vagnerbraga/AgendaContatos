package com.vagner.agendacontato;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import com.vagner.agendacontato.adapter.ContactAdapter;
import com.vagner.agendacontato.databinding.ActivityMainBinding;
import com.vagner.agendacontato.interfaces.OnItemClickListener;
import com.vagner.agendacontato.model.Contact;
import com.vagner.agendacontato.ui.CreateEditContactsFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    private ActivityMainBinding binding;
    private ContactAdapter adapter;

    private MainActivityViewModel viewModel;
    private List<Contact> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        this.binding = DataBindingUtil.setContentView(this,  R.layout.activity_main);

        this.binding.toolbar.setTitle("Agenda de Contatos");
        setSupportActionBar(this.binding.toolbar);

        this.binding.fabAdd.setOnClickListener(view -> this.showDialog(null));

        this.adapter = new ContactAdapter(this, this);

        this.binding.rvContact.setLayoutManager(new LinearLayoutManager(this));
        this.binding.rvContact.setItemAnimator(new DefaultItemAnimator());
        this.binding.rvContact.setAdapter(this.adapter);

        this.list = new ArrayList<>();
        this.viewModel.listLiveData.observe(this, contacts -> {
            this.adapter.notifyDataSetChanged(contacts);
            this.list.clear();
            this.list.addAll(contacts);
        });

    }

    private void showDialog(Contact contact) {

        FragmentManager fm = getSupportFragmentManager();

        CreateEditContactsFragment fragment = CreateEditContactsFragment.newInstance(contact);

        fragment.show(fm, CreateEditContactsFragment.class.getSimpleName());

    }

    @Override
    public void onClick(int position) {
        this.showDialog(this.list.get(position));
    }
}