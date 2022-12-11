package LaurenChristyJSleepRJ.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * ConfirmActivity is an Android activity that displays a dialog to the user
 * and allows them to confirm their action. If the user clicks "Okay", the
 * dialog is dismissed and the user is taken to the AboutMeActivity.
 *
 * @author Lauren Christy Tanudjaja
 * @version 1.0
 */
public class ConfirmActivity extends AppCompatActivity {

    /**
     * Dialog to be displayed to the user.
     */
    private Dialog dialog;

    /**
     * Called when the activity is starting. This is where most initialization
     * should go: calling setContentView(int) to inflate the activity's UI,
     * using findViewById(int) to programmatically interact with widgets in
     * the UI, calling managedQuery(android.net.Uri, String[], String, String[], String)
     * to retrieve cursors for data being displayed, etc.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create the Dialog here
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_confirm);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false); //Optionalhe animations to dialog

        Button Okay = dialog.findViewById(R.id.btn_okay);

        dialog.show();

        Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(ConfirmActivity.this, "Okay", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                Intent move = new Intent(ConfirmActivity.this, AboutMeActivity.class);
                startActivity(move);
            }
        });
    }
}
