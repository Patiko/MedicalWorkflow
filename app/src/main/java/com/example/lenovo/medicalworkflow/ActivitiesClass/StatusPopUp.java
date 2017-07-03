package com.example.lenovo.medicalworkflow.ActivitiesClass;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.lenovo.medicalworkflow.R;

/**
 * Created by Lenovo on 03.07.2017.
 */
/*

public class StatusPopUp extends Activity {
    ImageView openStatusPopUp;
    Button acceptSubmission, rejectSubmission;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_submission);

        openStatusPopUp = (ImageView) findViewById(R.id.editSubmissionStatus);
        openStatusPopUp.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View a){
                LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popUpView = layoutInflater.inflate(R.layout.edit_status_popup,null);
                final PopupWindow popupWindow = new PopupWindow(
                        popUpView,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                acceptSubmission = (Button)popUpView.findViewById(R.id.acceptButton);
                acceptSubmission.setOnClickListener(new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                         popupWindow.dismiss();
                    }});
                rejectSubmission = (Button)popUpView.findViewById(R.id.rejectButton);
                rejectSubmission.setOnClickListener(new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }});

                popupWindow.showAsDropDown(openStatusPopUp, 50, -30);
            }
        });
    }
}
*/

public class StatusPopUp extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_status_popup);



        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width =dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int)(width*0.7),(int)(height*0.4));

      /*  WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount=0.0f;
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        back_dim_layout.setVisibility(View.VISIBLE);*/



    }

}