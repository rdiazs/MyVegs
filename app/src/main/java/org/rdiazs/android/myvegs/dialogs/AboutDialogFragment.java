package org.rdiazs.android.myvegs.dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.rdiazs.android.myvegs.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Cuadro de diálogo que muestra la información del autor.
 */
public class AboutDialogFragment extends DialogFragment {
    @Bind(R.id.icon)
    ImageView icon;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.message)
    TextView message;
    @Bind(R.id.btn_close)
    ImageButton btn_close;
    @Bind(R.id.image)
    CircleImageView image;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager
                .LayoutParams.SOFT_INPUT_MASK_ADJUST);
        dialog.setContentView(R.layout.fragment_about);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setValues();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn_close)
    public void closeDialog() {
        this.dismiss();
    }

    /**
     * Establece los valores al cuadro de diálogo.
     */
    private void setValues() {
        Glide.with(this)
                .load(getString(R.string.ABOUT_ME_URL))
                .into(image);
        icon.setImageResource(R.drawable.ic_person);
        title.setText(getString(R.string.about_title));
        message.setText(getString(R.string.about_message));
    }
}
