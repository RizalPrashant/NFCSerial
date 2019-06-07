package nfcreader.prashantrizal.com.nfcserial;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareUltralight;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private NfcAdapter nfcAdapter;
    TextView textViewInfo;
    Tag tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewInfo = (TextView)findViewById(R.id.info);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null){
            Toast.makeText(this,
                    "NFC NOT supported on this devices!",
                    Toast.LENGTH_LONG).show();
            finish();
        }else if(!nfcAdapter.isEnabled()){
            Toast.makeText(this,
                    "NFC NOT Enabled!",
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        String action = intent.getAction();

        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
            Toast.makeText(this,
                    "Found Serial Number",
                    Toast.LENGTH_SHORT).show();

            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            byte[] tagId = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
            Log.d("Yeah",  "tag ID = " + tag.getId().toString());

            if(tag == null){
                textViewInfo.setText("tag == null");
            }else{
                String tagInfo = "Serial Number : ";
                String t = "";
                //byte[] tagId = tag.getId();
                //for MIfare Ultralight
                //MifareUltralight uTag = MifareUltralight.get(tag);
                //byte[] tagId = {};
               // try {
                  //  uTag.connect();
                   // tagId = uTag.getTag().getId();
               // } catch(IOException e){
                 //   System.out.println("IO exception when connecting");
               // }

//                Toast.makeText(this,tagId.toString(), Toast.LENGTH_SHORT).show();
//                for(int i=0; i<tagId.length; i++){
//                    String serial_value = Integer.toHexString(tagId[i] & 0xFF);
//                    if(serial_value.length() == 1){
//                        serial_value = '0' + serial_value;
//                    }
//                    tagInfo += serial_value + ' ';
//                }
                StringBuilder s = new StringBuilder(2 * tagId.length);
                for (int i = 0; i < tagId.length; ++i) {
                    t = Integer.toHexString(tagId[i]);
                    final int l = t.length();
                    if (l > 2) {
                        s.append(t.substring(l - 2));
                    } else {
                        if (l == 1) {
                            s.append("0");
                        }
                        s.append(t);
                    }
                }
                textViewInfo.setText(tagInfo + s);
            }
        }else{
            Toast.makeText(this,
                    "Scan a NFC card or tag",
                    Toast.LENGTH_SHORT).show();
        }

    }
}
