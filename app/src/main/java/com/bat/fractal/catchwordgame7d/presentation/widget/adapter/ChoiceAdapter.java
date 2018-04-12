package com.bat.fractal.catchwordgame7d.presentation.widget.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bat.fractal.catchwordgame7d.R;
import com.bat.fractal.catchwordgame7d.common.Strings;
import com.bat.fractal.catchwordgame7d.presentation.listener.ChoiceClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by phant on 22-03-18.
 */

public class ChoiceAdapter extends RecyclerView.Adapter<ChoiceAdapter.ChoiceHolder> {

    private final String TAG = ChoiceAdapter.class.getSimpleName();
    private Context context;
    private String answer;
    private ChoiceClickListener listener;

    public ChoiceAdapter(Context context, String answer, ChoiceClickListener listener) {
        this.context = context;
        this.answer = answer;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChoiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.choice_item, parent,
                false);
        return new ChoiceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChoiceHolder holder, int position) {
        Character c = answer.charAt(position);
        if (c != Strings.CHAR_SPACE) {
            holder.tvAnswer.setText(c.toString());
            holder.tvAnswer.setBackgroundResource(R.drawable.background_char_white);
            holder.tvAnswer.setOnClickListener(v -> listener.clickChoice(c.toString()));
        }else{
            holder.tvAnswer.setBackgroundColor(ResourcesCompat.getColor(context.getResources(),
                    android.R.color.transparent, null));
        }
    }

    @Override
    public int getItemCount() {
        return answer.length();
    }

    public class ChoiceHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_answer)
        AppCompatTextView tvAnswer;

        public ChoiceHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}