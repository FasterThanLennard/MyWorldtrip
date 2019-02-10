package com.rhahn.myworldtrip.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rhahn.myworldtrip.Data.AttributeData;
import com.rhahn.myworldtrip.Data.MyWorldtripData;
import com.rhahn.myworldtrip.DataHandler.Util;
import com.rhahn.myworldtrip.Enum.AttributeType;
import com.rhahn.myworldtrip.R;
import com.rhahn.myworldtrip.ViewBuilder.CountrycardViewBuilder;

public class CountryCardViewHolder extends RecyclerView.ViewHolder {
    TextView tvAttribute;
    LinearLayout child;
    CountrycardViewBuilder viewBuilder;

    public CountryCardViewHolder(@NonNull View itemView) {
        super(itemView);
        viewBuilder = new CountrycardViewBuilder(itemView.getContext());
        tvAttribute = itemView.findViewById(R.id.tvAttributeName);
        tvAttribute.setTextSize(itemView.getResources().getInteger(R.integer.textSizeHeader));
        child = itemView.findViewById(R.id.llChildcard);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (child.getVisibility() == View.GONE) {
                    child.setVisibility(View.VISIBLE);
                } else {
                    child.setVisibility(View.GONE);
                }
            }
        });
    }

    public void setAttribute(AttributeData attribute, int i) {
        AttributeType type;
        if (viewBuilder == null)
            viewBuilder = new CountrycardViewBuilder(child.getContext());

        tvAttribute.setText(Util.getStringValueFromName(attribute.getName(), child.getContext()));
        type = attribute.getType();
        switch (type) {
            case TEXTVIEW:
                viewBuilder.addTextview(child, attribute.getValues(), attribute.isEditable());
                break;

            case TEXTLIST:
                viewBuilder.addTextlist(child, attribute.getValues(), attribute.isEditable());
                break;

            case NUMBERINPUT:
                viewBuilder.addNumberinput(child, attribute.getValues(), attribute.isEditable(), i);
                break;
            case MULTILINE:
                viewBuilder.addMultiline(child, attribute.getValues(), i);
                break;
        }
    }

}
