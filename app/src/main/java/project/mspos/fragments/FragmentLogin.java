package project.mspos.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import project.mspos.R;
import project.mspos.utils.AlertDialogManager;
import project.mspos.utils.SessionManager;

/**
 * Created by SON on 3/16/2016.
 */
public class FragmentLogin extends Fragment {
    public static final String DEFAULT_EMAIL="abc@gmail.com";
    public static final String DEFAULT_PASS="password";
    EditText editEmail;
    EditText editPassword;
    Button btLogin;
    SessionManager session;
    CheckLogin mCallBack;
    public FragmentLogin(){

    }

    public interface CheckLogin{
        public void setLoggedIn(boolean isLoggedIn);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_login,container,false);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallBack=(CheckLogin)activity;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupView();
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=editEmail.getText()+"";
                String password=editPassword.getText()+"";
                if(email.equals(DEFAULT_EMAIL)&&password.equals(DEFAULT_PASS)){
                    mCallBack.setLoggedIn(true);
                }else {
                    mCallBack.setLoggedIn(false);
                }

            }
        });

    }

    private void setupView() {
        editEmail=(EditText)getActivity().findViewById(R.id.edit_text_email);
        editPassword=(EditText)getActivity().findViewById(R.id.edit_text_password);
        btLogin=(Button)getActivity().findViewById(R.id.button_login);
        session=new SessionManager(getActivity());
        Toast.makeText(getActivity(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
    }
}
