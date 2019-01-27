package info.camposha.creatingpercentageprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.view.Gravity.START;


public class PercentageProgressBar extends LinearLayout {

    private RelativeLayout hintRelativeLayout;
    private TextView hintTV;
    private TextView percentageTV;
    private EditText percentageTxt;

    private LinearLayout mWrapPercent;
    private LinearLayout mStartPercent;
    private LinearLayout mEndPercent;

    private boolean showHint;
    private boolean hide_input_percent;

    /**
     * We have two constructors.
     * @param context
     */
    public PercentageProgressBar(Context context) {
        this(context, null);
    }
    public PercentageProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        setGravity(START);
        init(context, attrs);
        this.isInEditMode();
    }

    /**
     * We reference widgets used in this custom percentageProgressBar we
     * are creating.
     */
    private void referenceWidgets(){
        percentageTxt = findViewById(R.id.percentageTxt);
        hintTV = findViewById(R.id.text_hint);
        percentageTV = findViewById(R.id.text_percent);
        mWrapPercent = findViewById(R.id.wrap_percent);
        mStartPercent = findViewById(R.id.start_percent);
        mEndPercent = findViewById(R.id.end_percent);
        hintRelativeLayout = findViewById(R.id.wrap_hint);
    }

    /**
     * We can now use our LayoutInflater class to inflate our Percentage
     * ProgressBar from a layout.
     * @param context
     */
    private void inflatePercentageProgressBar(Context context){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.percentage_progressbar, this, true);
    }

    /**
     * Let's obtain our style from attrs.xml, hold it in a typed array,
     * obtain several attributes like height,percent,hint etc from that
     * typedArray before recyc;ing it.
     * @param context
     * @param attrs
     */
    private void resolveAttributes(Context context,AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
        R.styleable.PercentageProgressBar, 0, 0);

        String hint = typedArray.getString(R.styleable.PercentageProgressBar_hint);
        showHint = typedArray.getBoolean(R.styleable.PercentageProgressBar_showHint,
         false);
        hide_input_percent = typedArray.getBoolean(
            R.styleable.PercentageProgressBar_hideInputPercent, false);

        float height = typedArray.getDimension(
            R.styleable.PercentageProgressBar_heightPercent, 5f);
        int percent = typedArray.getInteger(R.styleable.PercentageProgressBar_percent, 0);

        typedArray.recycle();
        this.referenceWidgets();

        percentageTxt.setHint(hint);
        hintTV.setText(hint);

        if (height > 5) {setHeightPercent(Math.round(height));}
        if (hide_input_percent) {hideInputPercent();}
        if (percent > 0) {setPercent(percent);}
    }

    /**
     * Let's listen to input events to our edittext, thus updating our
     * percentage progressbar
     */
    private void handlePercetTextChangeEvents(){
        percentageTxt.addTextChangedListener(
                new TextWatcher() {
                    @Override public void afterTextChanged(Editable s) {
                        if (s.length() > 0) {
                            showPercent(percentageTxt.getText().toString());
                        } else {
                            showPercent("0");
                        }
                    }
                    @Override public void beforeTextChanged(CharSequence s, int start,
                    int count, int after) {
                    }
                    @Override public void onTextChanged(CharSequence s, int start,
                    int before, int count) {
                    }
                });
    }

    /**
     * Let's invoke the methods we've created above
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        this.inflatePercentageProgressBar(context);
        this.resolveAttributes(context,attrs);
        this.handlePercetTextChangeEvents();
    }

    /**
     * Let's create a method to set progressbar height. Height as in vertical
     * height, like thickness.
     * @param height
     */
    public void setHeightPercent(int height) {
        //LayoutParams is a static class that holds information associated
        //with a given viewgroup. In this case our viewgroup is LinearLayout
        LayoutParams height_params = (LayoutParams) mWrapPercent.getLayoutParams();
        height_params.height = height;
        mWrapPercent.setLayoutParams(height_params);
    }

    /**
     * Let;s create a method to receive a percentage as a string, convert it
     * to integer then show it.
     * @param percentageString
     */
    private void showPercent(String percentageString) {
        //let's turn percentage string to integer
        int percentage = Integer.parseInt(percentageString);
        if (percentage == 0) {
            mWrapPercent.setVisibility(GONE);
            hintRelativeLayout.setVisibility(GONE);
        } else {
            setAnimatePercent(percentage, 0, mStartPercent);
            LayoutParams percent_end = (LayoutParams) mEndPercent.getLayoutParams();
            percent_end.weight = 100;
            mEndPercent.setLayoutParams(percent_end);
            mWrapPercent.setVisibility(VISIBLE);
            if (showHint) {
                hintRelativeLayout.setVisibility(VISIBLE);
            }
        }
    }

    /**
     * Let's create a method to set our percentage to our edittext
     * @param percent
     */
    public void setPercent(int percent) {
        showPercent("" + percent);
        percentageTxt.setText("" + percent);
    }

    /**
     * Let's create a method to set animated percentage to a textview
     * @param percent
     * @param position
     * @param view
     */
    private void setAnimatePercent(final int percent, final int position,
    final LinearLayout view) {
        final LayoutParams percent_start = (LayoutParams) mStartPercent.getLayoutParams();
        final int index = position + 1;
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                if (position < percent) {
                    percent_start.weight = index;
                    view.setLayoutParams(percent_start);
                    percentageTV.setText("" + index + "%");
                    setAnimatePercent(percent, index, view);
                }
            }
        }, 1);
    }

    /**
     * Method to allow us show hint
     */
    public void showHint() {
        this.showHint = true;
    }

    /**
     * Method to allow us hide percent
     */
    public void hideInputPercent() {
        percentageTxt.setVisibility(GONE);
    }
}
//end
