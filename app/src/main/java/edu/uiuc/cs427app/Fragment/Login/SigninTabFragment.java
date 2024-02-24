package edu.uiuc.cs427app.Fragment.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.uiuc.cs427app.Activity.LoginActivity;
import edu.uiuc.cs427app.Activity.MainActivity;
import edu.uiuc.cs427app.R;
import edu.uiuc.cs427app.model.UserModel;
import edu.uiuc.cs427app.services.CityService;
import edu.uiuc.cs427app.services.UserService;
import edu.uiuc.cs427app.util.ThemeUtil;

public class SigninTabFragment extends Fragment {
    EditText usernameTextField, passwordTextField;
    Button signInButton;

    /**
     * Overriding the onCreateView to initialize components and set listeners for user
     * interactions with the components
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return The view
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_signin_tab_fragment, container, false);

        usernameTextField = root.findViewById(R.id.username);
        passwordTextField = root.findViewById(R.id.password);
        signInButton = root.findViewById(R.id.signin_button);

        // onClickListener for signInButton
        // checking if all fields are valid, if yes: redirect to MainActivity else: popup error.
        signInButton.setOnClickListener(view -> {
            String usernameString = usernameTextField.getText().toString().trim();
            String passwordString = passwordTextField.getText().toString().trim();

            // checking if all texts are valid
            if (usernameString.isEmpty() || passwordString.isEmpty()) {
                Log.d("loginModule","Empty Strings");
                ((LoginActivity) getActivity()).showAlertPopup("Invalid Input", "Please enter valid username and password");
                return;
            }

            // initialize user service to access the database
            UserModel user;
            user = UserService.getInstance().getUserByUsername(usernameString);

            // Check if user is not null and password matches with the password in the database.
            if (user != null && user.getPassword().equals(passwordString)) {
                //If auth success redirect to main activity
                String primaryColor =  user.getPrimaryColor(), secondaryColor = user.getSecondaryColor();
                ThemeUtil.getInstance().setPreferredTheme(primaryColor, secondaryColor);

                ((LoginActivity) getActivity()).goToHomeActivity(user);
            } else {
                // when auth fails. (incorrect credentials or user does not exist)
                ((LoginActivity) getActivity()).showAlertPopup("Incorrect Credentials", "User does not exist, please check if you have entered correct credentials.");
            }

        });
        return root;
    }

}
