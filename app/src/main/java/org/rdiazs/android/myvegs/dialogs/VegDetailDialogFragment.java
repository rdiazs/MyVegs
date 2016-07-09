package org.rdiazs.android.myvegs.dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.rdiazs.android.myvegs.R;
import org.rdiazs.android.myvegs.entities.Veg;
import org.rdiazs.android.myvegs.libs.base.ImageLoader;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class VegDetailDialogFragment extends DialogFragment {
    @Bind(R.id.image)
    CircleImageView image;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.imageIcon)
    CircleImageView imageIcon;
    @Bind(R.id.germinationTime)
    TextView germinationTime;
    @Bind(R.id.layoutLight)
    RelativeLayout layoutLight;
    @Bind(R.id.layoutWater)
    RelativeLayout layoutWater;
    @Bind(R.id.layoutGround)
    RelativeLayout layoutGround;
    @Bind(R.id.btn_close)
    ImageButton btnClose;

    private static Veg veg;
    private static ImageLoader imageLoader;

    private final int RATING_EMPTY_STARS = 0;
    private final int RATING_HALF_FILLED_STARS = 1;
    private final int RATING_FILLED_STARS = 2;

    public static VegDetailDialogFragment newInstance(Veg newVeg, ImageLoader imgLoader) {
        veg = newVeg;
        imageLoader = imgLoader;

        VegDetailDialogFragment detailDialogFragment = new VegDetailDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("veg", veg);
        detailDialogFragment.setArguments(args);

        return detailDialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager
                .LayoutParams.SOFT_INPUT_MASK_ADJUST);
        dialog.setContentView(R.layout.fragment_veg_detail);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_veg_detail, container);
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
        HashMap<String, Float> necessities = veg.getNecessities();

        // Establecer la imagen y el nombre
        imageLoader.load(image, veg.getImage());
        imageLoader.load(imageIcon, veg.getImage());
        title.setText(veg.getName());

        // Tiempo de germinación
        String strGerminationTime = String.format(
                getString(R.string.veglistdetail_germination_time),
                veg.getGermination());
        int time = veg.getGermination();

        if (time <= 1) {
            strGerminationTime += " " + getString(R.string.day);
        } else {
            strGerminationTime += " " + getString(R.string.days);
        }

        germinationTime.setText(strGerminationTime);

        // Establecer imágenes
        ImageView imgLight = (ImageView) layoutLight.findViewById(R.id.lightNecessity)
                .findViewById(R.id.icon);
        imgLight.setImageResource(R.drawable.ic_sun);

        ImageView imgWater = (ImageView) layoutWater.findViewById(R.id.waterNecessity)
                .findViewById(R.id.icon);
        imgWater.setImageResource(R.drawable.ic_water);

        ImageView imgGround = (ImageView) layoutGround.findViewById(R.id.groundNecessity)
                .findViewById(R.id.icon);
        imgGround.setImageResource(R.drawable.ic_manure);

        // Establecer ratings
        RatingBar ratingLight = (RatingBar) layoutLight.findViewById(R.id.lightNecessity)
                .findViewById(R.id.rating);
        ratingLight.setRating(necessities.get(Veg.LIGHT_KEY));

        RatingBar ratingWater = (RatingBar) layoutWater.findViewById(R.id.waterNecessity)
                .findViewById(R.id.rating);
        ratingWater.setRating(necessities.get(Veg.WATER_KEY));

        RatingBar ratingGround = (RatingBar) layoutGround.findViewById(R.id.groundNecessity)
                .findViewById(R.id.rating);
        ratingGround.setRating(necessities.get(Veg.GROUND_KEY));

        setRatingColors(ratingLight);
        setRatingColors(ratingWater);
        setRatingColors(ratingGround);
    }

    /**
     * Establece los colores para un <code>RatingBar</code>.
     *
     * @param ratingbar <code>RatingBar</code> al que modificar los colores.
     */
    private void setRatingColors(RatingBar ratingbar) {
        LayerDrawable stars = (LayerDrawable) ratingbar.getProgressDrawable();

        stars.getDrawable(RATING_EMPTY_STARS)
                .setColorFilter(getResources().getColor(R.color.colorDivider), PorterDuff.Mode
                        .SRC_ATOP);

        stars.getDrawable(RATING_HALF_FILLED_STARS)
                .setColorFilter(getResources().getColor(R.color.colorDivider), PorterDuff.Mode
                        .SRC_ATOP);

        stars.getDrawable(RATING_FILLED_STARS)
                .setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode
                        .SRC_ATOP);
    }
}
