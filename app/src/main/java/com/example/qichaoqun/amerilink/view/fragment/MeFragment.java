package com.example.qichaoqun.amerilink.view.fragment;

import android.content.Intent;
import android.view.View;

import com.allen.library.SuperTextView;
import com.example.qichaoqun.amerilink.R;
import com.example.qichaoqun.amerilink.base.BaseFragment;
import com.example.qichaoqun.amerilink.bean.Setting;
import com.example.qichaoqun.amerilink.presenter.MePresenter;
import com.example.qichaoqun.amerilink.utils.Consts;
import com.example.qichaoqun.amerilink.utils.Utils;
import com.example.qichaoqun.amerilink.view.activity.LanguageActivity;
import com.example.qichaoqun.amerilink.view.activity.MoneyActivity;
import com.example.qichaoqun.amerilink.view.activity.UnitActivity;
import com.example.qichaoqun.amerilink.view.iview.IMeView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author qichaoqun
 * @date 2018/10/5
 */
public class MeFragment extends BaseFragment<IMeView, MePresenter> {

    @BindView(R.id.search_me)
    SuperTextView searchMe;
    @BindView(R.id.login_me)
    SuperTextView loginMe;
    @BindView(R.id.develop_panel_me)
    SuperTextView developPanelMe;
    @BindView(R.id.language_me)
    SuperTextView languageMe;
    @BindView(R.id.money_me)
    SuperTextView moneyMe;
    @BindView(R.id.danwei_me)
    SuperTextView danweiMe;

    private boolean isFirst = true;
    private Utils mUtils;
    private Intent mIntent;

    @Override
    protected int getLayoutId() {
        return R.layout.me_fragment_layout;
    }

    @Override
    protected void initView() {
        mUtils = new Utils(getContext());
        mIntent = new Intent();
    }

    @Override
    protected MePresenter getPresenter() {
        return new MePresenter();
    }

    @Override
    protected void loadingData() {
        if(isFirst && isPrepared){
            isFirst = false;
            //加载设置中的数据
            languageMe.setRightString(mUtils.getSetting(Consts.LANGUAGE));
            moneyMe.setRightString(mUtils.getSetting(Consts.MONEY));
            danweiMe.setRightString(mUtils.getSetting(Consts.DANWEI));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Setting setting) {
        //获取到相应的设置的改变，进行界面中设置内容的改变
        if(Consts.MONEY.equals(setting.getFlag())){
            //改变money设置框中的内容
            moneyMe.setRightString(setting.getSetting());
        }else{
            //改变单位设置框中的内容
            danweiMe.setRightString(setting.getSetting());
        }
    }

    @OnClick({R.id.search_me, R.id.login_me, R.id.develop_panel_me, R.id.language_me, R.id.money_me, R.id.danwei_me})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_me:
                break;
            case R.id.login_me:
                break;
            case R.id.develop_panel_me:
                break;
            case R.id.language_me:
                mIntent.setClass(getContext(),LanguageActivity.class);
                startActivity(mIntent);
                break;
            case R.id.money_me:
                mIntent.setClass(getContext(), MoneyActivity.class);
                startActivity(mIntent);
                break;
            case R.id.danwei_me:
                mIntent.setClass(getContext(), UnitActivity.class);
                startActivity(mIntent);
                break;
            default:
        }
    }
}
