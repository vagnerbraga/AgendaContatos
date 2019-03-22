package com.vagner.agendacontato.ui;


import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.vagner.agendacontato.R;
import com.vagner.agendacontato.databinding.FragmentCreateEditContactsBinding;
import com.vagner.agendacontato.model.Contact;

import java.io.ByteArrayOutputStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateEditContactsFragment extends DialogFragment {

    private Contact contact;

    private FragmentCreateEditContactsBinding binding;
    private CreateEditViewModel viewModel;

    public CreateEditContactsFragment() { }


    public static CreateEditContactsFragment newInstance(Contact contact){
        CreateEditContactsFragment fragment = new CreateEditContactsFragment();
        fragment.contact = contact;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.viewModel = ViewModelProviders.of(this).get(CreateEditViewModel.class);
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_edit_contacts, container, false);

        this.binding.fabAdd.setOnClickListener(view ->{

            if(this.contact == null)
                this.contact = new Contact();

            this.contact.setName(this.binding.ilName.getEditText().getText().toString());
            this.contact.setPhone(this.binding.ilPhone.getEditText().getText().toString());
            this.contact.setEmail(this.binding.ilEmail.getEditText().getText().toString());



            BitmapDrawable drawable = (BitmapDrawable) this.binding.ivPhoto.getDrawable();
            Bitmap bitmap = drawable.getBitmap();


            ByteArrayOutputStream stream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
            byte[] image=stream.toByteArray();

            String encodedImage = Base64.encodeToString(image, Base64.DEFAULT);

            this.contact.setPhoto(encodedImage);
            this.viewModel.insert(this.contact);
            Toast.makeText(getContext(), "Informações salva", Toast.LENGTH_SHORT).show();
            this.dismiss();


        });

        if(this.contact != null)
            this.fillContact();

        this.binding.ivPhoto.setOnClickListener(onclick ->{

            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivity(intent);
            startActivityForResult(intent, 0);
        });


        return this.binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            Bitmap image = (Bitmap) data.getExtras().get("data");

            this.binding.ivPhoto.setImageBitmap(image);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    private void fillContact(){
        this.binding.ilName.getEditText().setText(this.contact.getName());
        this.binding.ilPhone.getEditText().setText(this.contact.getPhone());
        this.binding.ilEmail.getEditText().setText(this.contact.getEmail());

        if(this.contact.getPhoto() != null && !this.contact.getPhoto().trim().isEmpty()){

            byte[] decodedString = Base64.decode(this.contact.getPhoto(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


            this.binding.ivPhoto.setImageBitmap(decodedByte);
        }
    }
}
