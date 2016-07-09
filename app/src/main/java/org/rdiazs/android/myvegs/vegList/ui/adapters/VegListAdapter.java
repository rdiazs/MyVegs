package org.rdiazs.android.myvegs.vegList.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.rdiazs.android.myvegs.R;
import org.rdiazs.android.myvegs.entities.Veg;
import org.rdiazs.android.myvegs.libs.base.ImageLoader;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * <code>Adapater</code> para la lista de cultivos.
 */
public class VegListAdapter extends RecyclerView.Adapter<VegListAdapter.ViewHolder> {
    private Context context;
    private List<Veg> vegList, items;
    private ImageLoader imageLoader;
    private OnItemClickListener onItemClickListener;

    public VegListAdapter(Context context, List<Veg> vegList, ImageLoader imageLoader,
                          OnItemClickListener onItemClickListener) {
        this.context = context;
        this.vegList = vegList;
        this.imageLoader = imageLoader;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_veg, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Veg currentVeg = vegList.get(position);
        holder.setOnItemClickListener(currentVeg, onItemClickListener);

        imageLoader.load(holder.imgVeg, currentVeg.getImage());

        String strName = String.format(context.getString(R.string.veglist_message_detail_message),
                currentVeg.getName(), currentVeg.getGermination());

        holder.txtName.setText(strName);
    }

    @Override
    public int getItemCount() {
        return vegList.size();
    }

    public ImageLoader getImageLoader() {
        return this.imageLoader;
    }

    public void add(Veg veg) {
        if (veg != null) {
            vegList.add(0, veg);
            notifyDataSetChanged();
        }
    }

    public void update(Veg veg) {
        if (vegList.contains(veg)) {
            vegList.set(vegList.indexOf(veg), veg);

            notifyDataSetChanged();
        }
    }

    public void remove(Veg veg) {
        vegList.remove(veg);
        notifyDataSetChanged();
    }

    public void sortVegsByName() {
        Collections.sort(vegList, new Comparator<Veg>() {
            @Override
            public int compare(Veg v1, Veg v2) {
                return v1.getName().compareTo(v2.getName());
            }
        });
        notifyDataSetChanged();
    }

    public void sortVegsByGerminationTime() {
        Collections.sort(vegList, new Comparator<Veg>() {
            @Override
            public int compare(Veg v1, Veg v2) {
                return v2.getGermination() - v1.getGermination();
            }
        });
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtName)
        TextView txtName;
        @Bind(R.id.imgVeg)
        CircleImageView imgVeg;

        private View view;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            this.view = view;
            context = view.getContext();
        }

        private void setOnItemClickListener(final Veg veg, final OnItemClickListener listener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(veg);
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemLongClick(veg);
                    return true;


//                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//
//                    String strTitle = String.format(context.getString(R.string
//                            .dialog_delete_veg_title), veg.getName());
//
//                    builder.setMessage(strTitle)
//                            .setPositiveButton(
//                                    R.string.dialog_button_ok, new DialogInterface
//                                            .OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            listener.onItemLongClick(veg);
//                                        }
//                                    })
//                            .setNegativeButton(
//                                    R.string.dialog_button_cancel, new DialogInterface
//                                            .OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//
//                                        }
//                                    })
//                            .create()
//                            .show();
//                    return false;
                }
            });
        }
    }
}
