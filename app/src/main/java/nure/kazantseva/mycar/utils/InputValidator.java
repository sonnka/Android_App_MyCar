package nure.kazantseva.mycar.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;


public class InputValidator {

    private Context context;

    public InputValidator(Context context) {
        this.context = context;
    }

    public boolean isInputEditTextFilled(EditText editText) {
        String value = editText.getText().toString().trim();
        if (value.isEmpty()) {
            Toast.makeText(context,"Empty field!",Toast.LENGTH_LONG).show();
            hideKeyboardFrom(editText);
            return false;
        }
        return true;
    }

    public boolean isInputEditTextEmail( EditText editText) {
        String value = editText.getText().toString().trim();
        if (value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            Toast.makeText(context,"Not valid email!",Toast.LENGTH_LONG).show();
            hideKeyboardFrom(editText);
            return false;
        }
        return true;
    }


    private void hideKeyboardFrom(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}
