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
import com.bat.fractal.catchwordgame7d.datalayer.model.Answer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dangm on 03/18/18.
 */

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerHolder> {

    private final String TAG = AnswerAdapter.class.getSimpleName();
    private Context context;
    private Answer answer;
    private int index;

    public AnswerAdapter(Context context, Answer answer) {
        this.context = context;
        this.answer = answer;
    }

    public void changeAnswer(Answer answer) {
        this.answer = answer;
        this.index = 0;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AnswerAdapter.AnswerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_item, parent,
                false);
        return new AnswerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerHolder holder, int position) {
        Character hiddenChar = answer.getHiddenAnswer().charAt(position);
        if (hiddenChar == Strings.CHAR_SPACE) {
            holder.tvAnswer.setText(Strings.EMPTY);
            holder.tvAnswer.setBackgroundColor(ResourcesCompat.getColor(context.getResources(),
                    android.R.color.transparent, null));
        } else {
            final int length = answer.getUserAnswer().length();
            if (index == answer.getHighLightIndex()) {
                holder.tvAnswer.setBackgroundResource(R.drawable.background_suggest);
            } else {
                holder.tvAnswer.setBackgroundResource(R.drawable.background_char_blue);
            }
            if (length > 0 && index < length) {
                final Character userAnswerChar = answer.getUserAnswer().charAt(index);
                holder.tvAnswer.setText(userAnswerChar.toString());
                index++;
            } else {
                holder.tvAnswer.setText(Strings.EMPTY);
            }
        }
    }

    @Override
    public int getItemCount() {
        return answer.getHiddenAnswer().length();
    }

    public class AnswerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_answer)
        AppCompatTextView tvAnswer;

        public AnswerHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
