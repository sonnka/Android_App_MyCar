package nure.kazantseva.mycar.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalDate;


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

    public boolean validateDate(String date){
        if(date.length() > 10){
            return false;
        }
        return true;
    }

    public LocalDate convertToLocalDate(String date){
        String temp = date.replace("+ ","").replaceAll("/","-");
        String[] array = temp.split("-");
        if(array[0].length()==1){
            array[0] = "0" + array[0];
        }
        if(array[1].length()==1){
            array[1] = "0" + array[1];
        }
        String result = array[2] + "-" + array[1] + "-" + array[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate resultDate = LocalDate.parse(result);
            return resultDate;
        }
        return null;
    }

    public String convertStringToDateString(String date){
        String temp = date.replace("+ ","").replaceAll("-","/");
        String[] array = temp.split("/");
        for(int i = 0 ; i < array.length; i++){
            String[] str = array[i].split("");
            if(str[0].equals("0")){
                array[i] = str[1];
            }
        }
        String result = array[2] + "/" + array[1] + "/" + array[0];
        return result;
    }

}
