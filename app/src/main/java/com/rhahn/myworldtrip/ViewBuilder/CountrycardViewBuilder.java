package com.rhahn.myworldtrip.ViewBuilder;

import android.content.Context;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rhahn.myworldtrip.Activities.CountryActivity;
import com.rhahn.myworldtrip.Activities.TimelineActivity;
import com.rhahn.myworldtrip.DataHandler.Util;
import com.rhahn.myworldtrip.Enum.AttributeType;
import com.rhahn.myworldtrip.R;

import java.util.HashMap;

public class CountrycardViewBuilder {

    private boolean isTablet;
    private Context context;

    public CountrycardViewBuilder(Context context) {
        this.isTablet = Util.isTablet(context);
        this.context = context;
    }

    public void addTextview(LinearLayout linearLayout, HashMap<String, String> attributes, boolean editable) {
        for (String attributeName : attributes.keySet()) {
            buildTextview(linearLayout, attributeName, 0);
        }
        if (editable) {
            addEditableText(linearLayout);
        }
    }

    public void addTextlist(LinearLayout linearLayout, HashMap<String, String> attributes, boolean editable) {
        for (String attributeName : attributes.keySet()) {
            buildTextlist(linearLayout, attributes, attributeName, 0);
        }
    }

    public void addNumberinput(LinearLayout linearLayout, HashMap<String, String> attributes, boolean editable, int i) {
        for (String attributeName : attributes.keySet()) {
            buildNumberinput(linearLayout, attributes, attributeName, 0, i);
        }
        if (editable) {
            addEditableInput(linearLayout, attributes, i);
        }
    }

    public void addMultiline(LinearLayout linearLayout, HashMap<String, String> attributes, int index) {
        final EditText editText = new EditText(linearLayout.getContext());
        final TextView tvHiddenID = createHiddenTextView(linearLayout.getContext(), String.valueOf(index));

        editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editText.setSingleLine(false);
        editText.setLines(10);
        editText.setTextSize(context.getResources().getInteger(R.integer.textSizeElement));
        editText.setText(attributes.get(AttributeType.MULTILINE.toString()));
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    int i = Integer.parseInt(tvHiddenID.getText().toString());
                    if (isTablet) {
                        TimelineActivity timelineActivity = (TimelineActivity) v.getContext();
                        timelineActivity.saveAttributeData(i, AttributeType.MULTILINE.toString(), editText.getText().toString());
                    } else {
                        CountryActivity countryActivity = (CountryActivity) v.getContext();
                        countryActivity.saveAttributeData(i, AttributeType.MULTILINE.toString(), editText.getText().toString());
                    }
                    Toast.makeText(v.getContext().getApplicationContext(), v.getContext().getString(R.string.datasaved), Toast.LENGTH_SHORT).show();
                }
            }
        });
        linearLayout.addView(editText);
    }

    private void buildTextview(LinearLayout linearLayout, String attributeName, int offset) {
        TextView textView;
        textView = new TextView(linearLayout.getContext());
        textView.setTextSize(context.getResources().getInteger(R.integer.textSizeElement));
        String tvText = linearLayout.getContext().getString(R.string.item) + " " + attributeName;
        textView.setText(tvText);
        textView.setGravity(Gravity.START);
        linearLayout.addView(textView, linearLayout.getChildCount() - offset);
    }

    private void buildTextlist(LinearLayout linearLayout, HashMap<String, String> attributes, String attributeName, int offset) {
        LinearLayout horizontalLayout;
        TextView tvFirstValue;
        TextView tvSecondValue;
        horizontalLayout = new LinearLayout(linearLayout.getContext());
        tvFirstValue = new TextView(linearLayout.getContext());
        tvSecondValue = new TextView(linearLayout.getContext());
        tvFirstValue.setTextSize(context.getResources().getInteger(R.integer.textSizeElement));
        tvSecondValue.setTextSize(context.getResources().getInteger(R.integer.textSizeElement));

        horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        tvFirstValue.setGravity(Gravity.START);
        tvFirstValue.setText(Util.getStringValueFromName(attributeName, context));
        tvFirstValue.setLayoutParams(layoutParams);

        tvSecondValue.setText(attributes.get(attributeName));
        tvSecondValue.setGravity(Gravity.START);
        tvSecondValue.setLayoutParams(layoutParams);

        horizontalLayout.addView(tvFirstValue);
        horizontalLayout.addView(tvSecondValue);
        linearLayout.addView(horizontalLayout, linearLayout.getChildCount() - offset);
    }


    private void buildNumberinput(final LinearLayout linearLayout, HashMap<String, String> attributes, String attributeName, int offset, int i) {
        LinearLayout horizontalLayout;
        final TextView tvFirstValue;
        final EditText etSecondValue;
        final TextView tvHiddenID = createHiddenTextView(linearLayout.getContext(), String.valueOf(i));
        horizontalLayout = new LinearLayout(linearLayout.getContext());
        tvFirstValue = new TextView(linearLayout.getContext());
        etSecondValue = new EditText(linearLayout.getContext());
        tvFirstValue.setTextSize(context.getResources().getInteger(R.integer.textSizeElement));
        etSecondValue.setTextSize(context.getResources().getInteger(R.integer.textSizeElement));

        horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

        tvFirstValue.setGravity(Gravity.START);
        String entryName = getValueName(attributeName);
        tvFirstValue.setText(entryName);

        tvFirstValue.setLayoutParams(layoutParams);
        if (attributes.get(attributeName) != null) {
            etSecondValue.setText(attributes.get(attributeName));
        }
        etSecondValue.setGravity(Gravity.START);
        etSecondValue.setLayoutParams(layoutParams);
        etSecondValue.setInputType(InputType.TYPE_CLASS_NUMBER);
        etSecondValue.setImeOptions(EditorInfo.IME_ACTION_DONE);
        etSecondValue.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int j, KeyEvent keyEvent) {
                if (j == EditorInfo.IME_ACTION_DONE) {
                    int i = Integer.parseInt(tvHiddenID.getText().toString());
                    if (isTablet) {
                        TimelineActivity timelineActivity = (TimelineActivity) textView.getContext();
                        timelineActivity.saveAttributeData(i, getEntryName(tvFirstValue.getText().toString()), etSecondValue.getText().toString());
                    } else {
                        CountryActivity countryActivity = (CountryActivity) textView.getContext();
                        countryActivity.saveAttributeData(i, getEntryName(tvFirstValue.getText().toString()), etSecondValue.getText().toString());
                    }
                    Toast.makeText(textView.getContext().getApplicationContext(), textView.getContext().getString(R.string.datasaved), Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        horizontalLayout.addView(tvFirstValue);
        horizontalLayout.addView(etSecondValue);
        horizontalLayout.addView(tvHiddenID);
        linearLayout.addView(horizontalLayout, linearLayout.getChildCount() - offset);
    }

    private void addEditableText(final LinearLayout linearLayout) {
        LinearLayout horizontalLayout;
        final EditText editText;

        horizontalLayout = new LinearLayout(linearLayout.getContext());
        horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        editText = new EditText(linearLayout.getContext());
        editText.setTextSize(context.getResources().getInteger(R.integer.textSizeElement));
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setHint(R.string.newEntry);
        editText.setGravity(Gravity.START);
        editText.setLayoutParams(layoutParams);
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    if (textView.getText().length() > 0)
                        buildTextview(linearLayout, textView.getText().toString(), 1);
                    editText.setHint(R.string.newEntry);
                }
                return false;
            }
        });
        horizontalLayout.addView(editText);
        linearLayout.addView(horizontalLayout);
    }

    private void addEditableInput(final LinearLayout linearLayout, final HashMap<String, String> attributes, int index) {
        LinearLayout horizontalLayout;
        final EditText editText;
        final TextView tvHiddenID = createHiddenTextView(linearLayout.getContext(), String.valueOf(index));

        horizontalLayout = new LinearLayout(linearLayout.getContext());
        horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        editText = new EditText(linearLayout.getContext());
        editText.setTextSize(context.getResources().getInteger(R.integer.textSizeElement));
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setHint(R.string.newEntry);
        editText.setGravity(Gravity.START);
        editText.setLayoutParams(layoutParams);
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    Toast.makeText(linearLayout.getContext(), "Länge: " + linearLayout.getChildCount(), Toast.LENGTH_LONG).show();
                    if (textView.getText().length() > 0) {
                        int j = Integer.parseInt(tvHiddenID.getText().toString());
                        if (isTablet) {
                            TimelineActivity timelineActivity = (TimelineActivity) textView.getContext();
                            buildNumberinput(linearLayout, attributes, textView.getText().toString(), 1, j);
                            timelineActivity.saveAttributeData(j, textView.getText().toString(), "");
                        } else {
                            CountryActivity countryActivity = (CountryActivity) textView.getContext();
                            buildNumberinput(linearLayout, attributes, textView.getText().toString(), 1, j);
                            countryActivity.saveAttributeData(j, textView.getText().toString(), "");
                        }
                    }
                    editText.setText("");
                    editText.setHint(R.string.newEntry);
                }
                return false;
            }
        });

        horizontalLayout.addView(editText);
        linearLayout.addView(horizontalLayout);
    }

    private String getValueName(String value){
        if(value.equals(context.getResources().getResourceEntryName(R.string.dailycost)))
            return context.getString(R.string.dailycost);
        if(value.equals( context.getResources().getResourceEntryName(R.string.dailyNightcost)))
            return context.getString(R.string.dailyNightcost);
        if(value.equals( context.getResources().getResourceEntryName(R.string.flightcost)))
            return context.getString(R.string.flightcost);
        return value;
    }

    private String getEntryName(String value){
        if(value.equals(context.getString(R.string.dailycost)))
            return context.getResources().getResourceEntryName(R.string.dailycost);
        if(value.equals(context.getString(R.string.dailyNightcost)))
            return context.getResources().getResourceEntryName(R.string.dailyNightcost);
        if(value.equals( context.getString(R.string.flightcost)))
            return context.getResources().getResourceEntryName(R.string.flightcost);
        return value;
    }

    private TextView createHiddenTextView(Context context, String value) {
        TextView tvHiddenID = new TextView(context);
        tvHiddenID.setText(value);
        tvHiddenID.setVisibility(View.GONE);
        return tvHiddenID;
    }
}
