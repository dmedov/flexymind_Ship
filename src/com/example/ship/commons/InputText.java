package com.example.ship.commons;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;


/**
 * Created with IntelliJ IDEA.
 * User: Vasya
 * Date: 27.05.13
 * Time: 1:49
 * To change this template use File | Settings | File Templates.
 */
public class InputText extends ButtonSprite implements OnClickListener {

    private String title;
    private String inputString;
    private Text text;

    public InputText( float pX, float pY, int rId, Font font, String title) {

        super(pX, pY, A.rm.getLoadedTextureRegion(rId), A.e.getVertexBufferObjectManager());
        this.title = title;
        text = new Text(10, 10, font, "", 256, A.e.getVertexBufferObjectManager());
        inputString = "";
        attachChild(text);
        setOnClickListener(this);
    }

    public String getInputString() {
        return inputString;
    }

    @Override
    public void onClick(ButtonSprite buttonSprite, float v, float v2) {
        A.a.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                final EditText editText = new EditText(A.a);
                editText.setText(inputString);
                editText.setGravity(Gravity.CENTER_HORIZONTAL);

                AlertDialog dialog = buildAlert(editText).create();
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {

                    @Override
                    public void onShow(DialogInterface dialog) {
                        editText.requestFocus();
                        final InputMethodManager imm = (InputMethodManager) A.a.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                    }
                });
                dialog.show();
            }
        });
    }

    private AlertDialog.Builder buildAlert(final EditText editText) {

        AlertDialog.Builder alert = new AlertDialog.Builder(A.a);
        alert.setMessage(title);
        alert.setView(editText);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                inputString = editText.getText().toString();
                text.setText(inputString);
            }
        });
        return alert;
    }

}
