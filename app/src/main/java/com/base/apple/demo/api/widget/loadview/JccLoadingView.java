package com.base.apple.demo.api.widget.loadview;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jucaicat.market.R;

import java.util.Random;

/**
 * Created by terence.wang on 2017/2/27.
 */

public class JccLoadingView extends LinearLayout{

    private ImageView mLoadView;
    private TextView mLoadText;
    private AnimationDrawable frameAnim;

    // 刷新中随机文字
    private static final String[] refreshWords = {
            "每天赚一杯星爸爸，yohoo",
            "传说投资聚财猫的人，智商都超160",
            "投资不仅是一种行为，也是一种哲学",
            "一分耕耘一分收获，一分投资一分回报",
            "蓄积者，天下之大命也。——贾谊",
            "没事儿常来赚赚"
    };

    public JccLoadingView(Context context){
        this(context, null);
    }

    public JccLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_load_header, null, false);
        mLoadView = (ImageView) view.findViewById(R.id.ivLoadImage);
        mLoadText = (TextView) view.findViewById(R.id.tvLoadText);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        addView(view, layoutParams);
        bindData();
    }

    private void bindData(){
        frameAnim = (AnimationDrawable) getResources().getDrawable(R.drawable.fresh_anim);
        mLoadView.setImageDrawable(frameAnim);
//        Glide.with(getContext()).load(R.drawable.gif_pull_load).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mLoadView);

//        mLoadText.setText(getRandomText());
    }

    private String getRandomText(){
        Random random = new Random();
        int index = random.nextInt(refreshWords.length);
        return refreshWords[index];
    }

    //开始动画
    public void startAnimation(){
        if(frameAnim != null){
            mLoadText.setText(getRandomText());
            frameAnim.start();
        }
    }

    //结束动画
    public void stopAnimation(){
        if(frameAnim != null){
            frameAnim.stop();
        }
    }

}
