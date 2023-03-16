package lab03.eim.systems.cs.pub.ro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class PhoneDialerActivity extends AppCompatActivity {

    private EditText phoneNumberEditText;
    private final CallImageButtonClickListener callImageButtonClickListener = new CallImageButtonClickListener();
    private final HangupImageButtonClickListener hangupImageButtonClickListener = new HangupImageButtonClickListener();
    private final BackspaceImageButtonClickListener backspaceImageButtonClickListener = new BackspaceImageButtonClickListener();
    private final GridButtonClickListener gridButtonClickListener = new GridButtonClickListener();

    private class CallImageButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (ContextCompat.checkSelfPermission(PhoneDialerActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        PhoneDialerActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        Constants.PERMISSION_REQUEST_CALL_PHONE);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNumberEditText.getText().toString()));
                startActivity(intent);
            }
        }
    }

    private class HangupImageButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    private class BackspaceImageButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String phoneNumber = phoneNumberEditText.getText().toString();
            if (phoneNumber.length() > 0) {
                String newPhoneNumber = phoneNumber.substring(0, phoneNumber.length() - 1);
                phoneNumberEditText.setText(newPhoneNumber);
            }
        }
    }

    private class GridButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Button button = (Button) view;
            String newPhoneNumber = phoneNumberEditText.getText().toString() + button.getText().toString();
            phoneNumberEditText.setText(newPhoneNumber);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_dialer);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        phoneNumberEditText = (EditText) findViewById(R.id.phone_number_editText);
        ImageButton callImageButton = (ImageButton) findViewById(R.id.imageButton_call);
        ImageButton hangupImageButton = (ImageButton) findViewById(R.id.imageButton_hangup);
        ImageButton backspaceImageButton = (ImageButton) findViewById(R.id.imageButton_backspace);

        callImageButton.setOnClickListener(callImageButtonClickListener);
        hangupImageButton.setOnClickListener(hangupImageButtonClickListener);
        backspaceImageButton.setOnClickListener(backspaceImageButtonClickListener);

        for (int i = 0; i < Constants.buttonIds.length; i++) {
            Button genericButton = (Button) findViewById(Constants.buttonIds[i]);
            genericButton.setOnClickListener(gridButtonClickListener);
        }
    }
}