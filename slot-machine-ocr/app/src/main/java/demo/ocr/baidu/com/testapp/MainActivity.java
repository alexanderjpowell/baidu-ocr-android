package demo.ocr.baidu.com.testapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AlertDialog;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.ui.camera.CameraActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView textView;
    private boolean hasGotToken = false;
    private static final int REQUEST_CODE_ACCURATE_BASIC = 107;

    private AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alertDialog = new AlertDialog.Builder(this);

        button = (Button)findViewById(R.id.demoButton);
        textView = (TextView)findViewById(R.id.textView);

        findViewById(R.id.demoButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, com.baidu.ocr.demo.testapp.FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_ACCURATE_BASIC);
            }
        });

        initAccessToken();
    }

    private boolean checkTokenStatus() {
        if (!hasGotToken) {
            Toast.makeText(getApplicationContext(), "Token not found", Toast.LENGTH_LONG).show();
        }
        return hasGotToken;
    }

    private void initAccessToken() {
        OCR.getInstance(this).initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                String token = accessToken.getAccessToken();
                hasGotToken = true;
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                //Toast.makeText(getApplicationContext(), "License mode failed to get token. \n" + error.getMessage(), Toast.LENGTH_LONG).show();
                alertText("License mode failed to get token", error.getMessage());
            }
        }, getApplicationContext());
    }

    private void alertText(final String title, final String message) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("Close", null)
                        .show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initAccessToken();
        } else {
            Toast.makeText(getApplicationContext(), "Requires android.permission.READ_PHONE_STATE", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 识别成功回调，通用文字识别（高精度版）
        if (requestCode == REQUEST_CODE_ACCURATE_BASIC && resultCode == Activity.RESULT_OK) {

            textView.setText("Processing");
            RecognizeService.recAccurateBasic(this, com.baidu.ocr.demo.testapp.FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            //infoPopText(result);
                            //Toast.makeText(getApplicationContext(), "REQUEST_CODE_ACCURATE_BASIC", Toast.LENGTH_SHORT).show();
                            textView.setText(parseJsonResult(result));
                        }
                    });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OCR.getInstance(this).release();
    }

    private String parseJsonResult(String text) {

        String ret = "";

        try {
            JSONObject json = new JSONObject(text);
            JSONArray array = json.getJSONArray("words_result");
            for (int i = 0; i < array.length(); i++) {
                String element = array.getJSONObject(i).get("words").toString();
                ret += element + "\n";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return ret;
    }
}
