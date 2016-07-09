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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.rdiazs.android.myvegs.R;
import org.rdiazs.android.myvegs.entities.Veg;
import org.rdiazs.android.myvegs.vegList.ui.VegListActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Cuadro de diálogo que se muestra antes de eliminar un cultivo, para que el usuario esté seguro
 * de que desea eliminarlo.
 */
public class DeleteVegDialogFragment extends DialogFragment {
    @Bind(R.id.icon)
    ImageView icon;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.message)
    TextView message;
    @Bind(R.id.btnCancel)
    Button btnCancel;
    @Bind(R.id.btnConfirm)
    Button btnConfirm;

    private static Veg veg;

    public static DeleteVegDialogFragment newInstance(Veg newVeg) {
        veg = newVeg;

        DeleteVegDialogFragment deleteVegDialogFragment = new DeleteVegDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("veg", veg);
        deleteVegDialogFragment.setArguments(args);

        return deleteVegDialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager
                .LayoutParams.SOFT_INPUT_MASK_ADJUST);
        dialog.setContentView(R.layout.fragment_delete_veg);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete_veg, container);
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

    @OnClick(R.id.btnCancel)
    public void closeDialog() {
        this.dismiss();
    }

    @OnClick(R.id.btnConfirm)
    public void delete() {
        VegListActivity activity = (VegListActivity) getActivity();

        this.dismiss();

        activity.onUserDeleteVeg(veg);
    }

    /**
     * Establece los valores al cuadro de diálogo.
     */
    private void setValues() {
        icon.setImageResource(R.drawable.ic_delete);

        String strMessage = String.format(this.getString(R.string
                .dialog_delete_veg_message), veg.getName());

        title.setText(getResources().getString(R.string.dialog_delete_veg_title));
        message.setText(strMessage);
    }
}
