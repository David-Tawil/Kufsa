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


/**
 * Fragment that initializes the Send report page in the app.
 */
public class SendReportFragment extends Fragment {

    EditText etTo, etSubject, etMessage;
    Button btSend;
    private FragmentSendReportBinding binding;

    /**
     * This method initializes the layout for the page from an XML file.
     */
    public SendReportFragment() {
        super(R.layout.fragment_send_report);
    }

    /**
     * This method creates the view.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * @param inflater           Instantiates a layout XML file into its corresponding View objects.
     * @param container          special view that can contain child views.
     * @param savedInstanceState A mapping from String keys to various Parcelable values..
     * @return outermost view.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSendReportBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * This method binds all buttons and fields so that they will create an email activity from the app user
     * to an admin (in this case my email).
     *
     * @param view               the view we use for this fragment.
     * @param savedInstanceState A mapping from String keys to various Parcelable values..
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
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