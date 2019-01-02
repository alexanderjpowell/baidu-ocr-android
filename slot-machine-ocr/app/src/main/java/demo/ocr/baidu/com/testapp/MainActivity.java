package demo.ocr.baidu.com.testapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AlertDialog;
import android.widget.ProgressBar;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.ui.camera.CameraActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import demo.ocr.baidu.com.testapp.TextParser;

public class MainActivity extends AppCompatActivity {

    private Button button;
    //private TextView textView;
    private boolean hasGotToken = false;
    private static final int REQUEST_CODE_ACCURATE_BASIC = 107;

    private AlertDialog.Builder alertDialog;
    //private ProgressBar progressBar;

    private TextInputEditText progressive1, progressive2, progressive3, progressive4, progressive5, progressive6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alertDialog = new AlertDialog.Builder(this);

        //progressBar = (ProgressBar)findViewById(R.id.progressBar);
        //progressBar.setVisibility(View.INVISIBLE);
        button = (Button)findViewById(R.id.demoButton);
        progressive1 = (TextInputEditText)findViewById(R.id.progressive1);
        progressive2 = (TextInputEditText)findViewById(R.id.progressive2);
        progressive3 = (TextInputEditText)findViewById(R.id.progressive3);
        progressive4 = (TextInputEditText)findViewById(R.id.progressive4);
        progressive5 = (TextInputEditText)findViewById(R.id.progressive5);
        progressive6 = (TextInputEditText)findViewById(R.id.progressive6);

        findViewById(R.id.demoButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()) {
                    return;
                }
                //progressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, com.baidu.ocr.demo.testapp.FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_ACCURATE_BASIC);
            }
        });

        initAccessToken();

        progressive1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT = 2;

                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if(motionEvent.getRawX() >= (progressive1.getRight() - progressive1.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        progressive1.setText("");
                        progressive1.requestFocus();
                        Toast.makeText(getApplicationContext(), "Pressed", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                return false;
            }
        });

        progressive2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT = 2;

                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if(motionEvent.getRawX() >= (progressive2.getRight() - progressive2.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        progressive2.setText("");
                        progressive2.requestFocus();
                        Toast.makeText(getApplicationContext(), "Pressed", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                return false;
            }
        });

        progressive3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT = 2;

                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if(motionEvent.getRawX() >= (progressive3.getRight() - progressive3.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        progressive3.setText("");
                        progressive3.requestFocus();
                        Toast.makeText(getApplicationContext(), "Pressed", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                return false;
            }
        });

        progressive4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT = 2;

                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if(motionEvent.getRawX() >= (progressive4.getRight() - progressive4.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        progressive4.setText("");
                        progressive4.requestFocus();
                        Toast.makeText(getApplicationContext(), "Pressed", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                return false;
            }
        });

        progressive5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT = 2;

                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if(motionEvent.getRawX() >= (progressive5.getRight() - progressive5.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        progressive5.setText("");
                        progressive5.requestFocus();
                        Toast.makeText(getApplicationContext(), "Pressed", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                return false;
            }
        });

        progressive6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT = 2;

                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if(motionEvent.getRawX() >= (progressive6.getRight() - progressive6.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        progressive6.setText("");
                        progressive6.requestFocus();
                        Toast.makeText(getApplicationContext(), "Pressed", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                return false;
            }
        });
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

        if (requestCode == REQUEST_CODE_ACCURATE_BASIC && resultCode == Activity.RESULT_OK) {

            RecognizeService.recAccurateBasic(this, com.baidu.ocr.demo.testapp.FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            //infoPopText(result);
                            //Toast.makeText(getApplicationContext(), "REQUEST_CODE_ACCURATE_BASIC", Toast.LENGTH_SHORT).show();
                            //textView.setText(parseJsonResult(result));

                            Log.d("Result Text", result);

                            List<String> words = convertJsonArrayToList(result);
                            List<String> progressives = parseJsonResult(words);

                            if (progressives.size() == 0) {
                                Toast.makeText(getApplicationContext(), "No Progressive Values Detected.  Try Again.", Toast.LENGTH_LONG).show();
                            } else if (progressives.size() <= 6) {
                                // Expected behavior
                                for (int i = 0; i < progressives.size(); i++) {
                                    if (i == 0) {
                                        progressive1.setText(progressives.get(i));
                                    }
                                    if (i == 1) {
                                        progressive2.setText(progressives.get(i));
                                    }
                                    if (i == 2) {
                                        progressive3.setText(progressives.get(i));
                                    }
                                    if (i == 3) {
                                        progressive4.setText(progressives.get(i));
                                    }
                                    if (i == 4) {
                                        progressive5.setText(progressives.get(i));
                                    }
                                    if (i == 5) {
                                        progressive6.setText(progressives.get(i));
                                    }
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "More than 6 progressives detected", Toast.LENGTH_LONG).show();
                            }
                            //progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OCR.getInstance(this).release();
    }

    // Takes in Json string and returns List<String> with the words in the JsonArray
    private List<String> convertJsonArrayToList(String text) {
        List<String> ret = new ArrayList<String>();
        try {
            JSONObject json = new JSONObject(text);
            JSONArray array = json.getJSONArray("words_result");
            for (int i = 0; i < array.length(); i++) {
                ret.add(array.getJSONObject(i).get("words").toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ret;
    }

    private List<String> parseJsonResult(List<String> words) {

        TextParser parser = new TextParser();
        List<String> progressives = parser.parseText(words);
        return progressives;

        /*String ret = "";
        String formattedString;
        List<String> progressives = new ArrayList<String>();
        List<String> words = new ArrayList<String>();

        try {
            JSONObject json = new JSONObject(text);
            JSONArray array = json.getJSONArray("words_result");
            for (int i = 0; i < array.length(); i++) {
                String element = array.getJSONObject(i).get("words").toString();
                words.add(element);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return progressives;*/
    }

    public void submitOnClick(View view) {
        Toast.makeText(getApplicationContext(), "Progressives submitted successfully", Toast.LENGTH_SHORT).show();
        progressive1.setText("XX.XX");
        progressive2.setText("XX.XX");
        progressive3.setText("XX.XX");
        progressive4.setText("XX.XX");
        progressive5.setText("XX.XX");
        progressive6.setText("XX.XX");
    }
}
