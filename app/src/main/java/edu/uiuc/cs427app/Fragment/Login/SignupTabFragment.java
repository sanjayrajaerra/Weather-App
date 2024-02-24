package edu.uiuc.cs427app.Fragment.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import edu.uiuc.cs427app.Activity.LoginActivity;
import edu.uiuc.cs427app.Activity.ThemeActivity;
import edu.uiuc.cs427app.R;
import edu.uiuc.cs427app.model.CityModel;
import edu.uiuc.cs427app.model.UserModel;
import edu.uiuc.cs427app.services.CityService;
import edu.uiuc.cs427app.services.UserService;
import edu.uiuc.cs427app.util.ThemeUtil;

public class SignupTabFragment extends Fragment {
    private static final int SELECT_THEME_REQUEST_CODE = 0;
    EditText passwordTextField, usernameTextField, confirmPasswordTextField;
    Button signUpButton, selectThemeButton;

    static UserService theUserService;
    static CityService theCityService;

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
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_signup_tab_fragment, container, false);

        passwordTextField = root.findViewById(R.id.signup_password);
        usernameTextField = root.findViewById(R.id.signup_username);
        confirmPasswordTextField = root.findViewById(R.id.confirm_password);
        signUpButton = root.findViewById(R.id.signup_button);
        selectThemeButton = root.findViewById(R.id.select_theme_button);

        // onClickListener for signUpButton
        // checks if all inputs are valid, if yes: register user, update theme and move to MainActivity.
        signUpButton.setOnClickListener(view -> {
            String passwordString = passwordTextField.getText().toString().trim();
            String nameString = usernameTextField.getText().toString().trim();
            String confirmPasswordString = confirmPasswordTextField.getText().toString().trim();

            //Initialize DB services
            theUserService = UserService.getInstance();
            theCityService = CityService.getInstance();

            // check for empty texts
            if (passwordString.isEmpty() || nameString.isEmpty() || confirmPasswordString.isEmpty()) {
                ((LoginActivity) getActivity()).showAlertPopup("Invalid Input", "Please enter valid username and password");
                return;
            }

            // check for confirm password
            if (!passwordString.equals(confirmPasswordString)) {
                ((LoginActivity) getActivity()).showAlertPopup("Invalid Input", "Password does not match, please re-enter your password");
                return;
            }

            // checking if user has selected a theme before registering.
            if (!ThemeUtil.getInstance().hasUserSelected) {
                ((LoginActivity) getActivity()).showAlertPopup("Select your preferred theme", "Please select a theme you like");
                return;
            }

            // check if user already exists
            if(theUserService.getUserByUsername(nameString) != null){
                ((LoginActivity) getActivity()).showAlertPopup("Username exists", "Username already exists, please try another username");
                return;
            }

            UserModel new_user = new UserModel(nameString,passwordString,false,new ArrayList<CityModel>());

            String primaryColor = ThemeUtil.getPreferredColors().get(0);
            String secondaryColor = ThemeUtil.getPreferredColors().get(1);

            new_user.setPrimaryColor(primaryColor);
            new_user.setSecondaryColor(secondaryColor);

            //Add User to DB
            theUserService.addUser(new_user);


            //Check if the user is added to the database
            if (theUserService.getUserByUsername(nameString) != null) {
                ThemeUtil.getInstance().setPreferredTheme(primaryColor, secondaryColor);
                ((LoginActivity) getActivity()).showAlertPopup("Success", "Registered Successfully, Please sign-in");
            } else {
                // when auth fails. (some db issue or user already exists)
                ((LoginActivity) getActivity()).showAlertPopup("Error", "An error occurred while registering the user.");
            }
        });

        // onCLickListener for selectThemeButton
        // redirect user to ThemeActivity allowing to select preferred theme.
        selectThemeButton.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), ThemeActivity.class);
            startActivityForResult(intent, SELECT_THEME_REQUEST_CODE);
        });
        return root;
    }

    // onActivityResult for callback when user selects a preferred theme.
    // triggered when user confirm it's selection.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (SELECT_THEME_REQUEST_CODE) : {
                if (resultCode == Activity.RESULT_OK) {
                    ArrayList<String> selectedColors = data.getStringArrayListExtra("colors");
                    ThemeUtil.getInstance().setPreferredTheme(selectedColors.get(0), selectedColors.get(1));
                    ThemeUtil.getInstance().hasUserSelected = true;

                    getActivity().recreate();
                }
                break;
            }
        }
    }
}


