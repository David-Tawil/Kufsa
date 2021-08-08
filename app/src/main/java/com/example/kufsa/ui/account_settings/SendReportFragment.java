package com.example.kufsa.ui.account_settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kufsa.R;
import com.example.kufsa.databinding.FragmentSendReportBinding;

import org.jetbrains.annotations.NotNull;


public class SendReportFragment extends Fragment {

    EditText etTo, etSubject, etMessage;
    Button btSend;
    private FragmentSendReportBinding binding;

    public SendReportFragment() {
        super(R.layout.fragment_send_report);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSendReportBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        etTo = binding.getRoot().findViewById(R.id.et_to);
        etSubject = binding.getRoot().findViewById(R.id.et_subject);
        etMessage = binding.getRoot().findViewById(R.id.et_message);
        btSend = binding.getRoot().findViewById(R.id.bt_Send);

        btSend.setOnClickListener(v -> {
            // Insert all user content into the email about to be sent
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("mailto:" + etTo.getText().toString()));
            intent.putExtra(Intent.EXTRA_SUBJECT, etSubject.getText().toString());
            intent.putExtra(Intent.EXTRA_TEXT, etMessage.getText().toString());
            startActivity(intent);


        });
    }
}