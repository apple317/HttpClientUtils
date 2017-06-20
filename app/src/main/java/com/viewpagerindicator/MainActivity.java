package com.jucaicat.market.modules.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.jucaicat.market.R;
import com.jucaicat.market.common.activitys.AppBaseActivity;
import com.jucaicat.market.common.utils.JccToast;
import com.jucaicat.market.common.viewmodels.IActionCallback;
import com.jucaicat.market.databinding.ActivityMainBinding;
import com.jucaicat.market.modules.application.JccApplication;
import com.jucaicat.market.modules.main.asset.TabAssetFragment;
import com.jucaicat.market.modules.main.home.TabHomeFragment;
import com.jucaicat.market.modules.main.product.TabProductFragment;
import com.jucaicat.market.modules.main.user.TabUserFragment;
import com.jucaicat.market.modules.main.viewmodel.TabHostViewModel;
import com.jucaicat.market.modules.other.upgrade.model.UpgradeDTO;
import com.jucaicat.market.modules.other.upgrade.model.UpgradeEvent;
import com.jucaicat.market.modules.other.upgrade.viewmodel.UpgradeViewModel;
import com.jucaicat.market.modules.other.upgrade.widget.AppUpgradeDialog;
import com.wj.common.task.TaskScheduler;
import com.wj.common.task.mode.TaskMode;
import com.wj.common.ui.viewmodel.IViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 吴云海 on 17/3/6.
 *
 */

public class MainActivity extends AppBaseActivity {

    private String[] TabTag = {JccApplication.getInstance().getResources().getString(R.string.tab_home), JccApplication.getInstance().getResources().getString(R.string.tab_product), JccApplication.getInstance().getResources().getString(R.string.tab_asset),JccApplication.getInstance().getResources().getString(R.string.tab_more)};
    private List<String> mTabTags = new ArrayList<>();

    private Integer[] ImgTab = {R.layout.layout_tab_home,R.layout.layout_tab_product,R.layout.layout_tab_asset, R.layout.layout_tab_me};
    private List<Integer> mImgTabs = new ArrayList<>();

    private Class[] ClassTab = {TabHomeFragment.class,TabProductFragment.class,TabAssetFragment.class,TabUserFragment.class};
    private List<Class> mClassTabs = new ArrayList<>();

    TabFragmentHost mTabHost;
    private long exitTime = 0;

    private TabHostViewModel hostViewModel;
    private UpgradeViewModel upgradeViewModel;
    private ActivityMainBinding mainBinding;

    private AppUpgradeDialog upgradeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hostViewModel = new TabHostViewModel(this);
        upgradeViewModel = new UpgradeViewModel(this);
        this.viewModels = new IViewModel[] {hostViewModel, upgradeViewModel};
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        EventBus.getDefault().register(this);
        initView();
        initData();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        initData();
    }

    private void initView(){
        mTabHost = (TabFragmentHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        initTabView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                JccToast.show(getResources().getString(R.string.tip_exsit_app));
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initTabView() {

        for(String string : TabTag){
            mTabTags.add(string);
        }
        for(Class c : ClassTab){
            mClassTabs.add(c);
        }
        for(Integer resId : ImgTab){
            mImgTabs.add(resId);
        }

        Bundle b = new Bundle();
        for (int i = 0; i < mTabTags.size(); i++) {
            View indicator = getIndicatorView(i);
            mTabHost.addTab(mTabHost.newTabSpec(mTabTags.get(i)).setIndicator(indicator), mClassTabs.get(i), b);
        }
        mTabHost.getTabWidget().setDividerDrawable(R.color.colorBlack);
    }

    // 设置tab自定义样式:注意 每一个tab xml子布局的linearlayout 的id必须一样
    private View getIndicatorView(int i) {
        // 找到tabhost的子tab的布局视图
        View v = getLayoutInflater().inflate(mImgTabs.get(i), null);
        TextView tv = (TextView) v.findViewById(R.id.tabText);
        tv.setText(mTabTags.get(i));

        return v;
    }

    public void initData(){
        int homeIndex = 0 ;
        Object object = getIntentValueByKey("homeIndex");
        if (null != object) {
            homeIndex = (int) object;
        }
        if (logger.isDebugEnabled()) {
            logger.error("==============================" + homeIndex);
        }
        mTabHost.setCurrentTabByTag(mTabTags.get(homeIndex));
    }

    @Subscribe
    public void onEventMainThread(Object event) {
        if(event instanceof UpgradeEvent){
            checkAppUpdateInfo();
//            UpgradeEvent upgradeEvent = (UpgradeEvent) event;
//            UpgradeDTO upgradeDTO = upgradeEvent.getUpdateDTO();
//            if(null == upgradeDialog)
//                upgradeDialog = new AppUpgradeDialog(this);
//            upgradeDialog.goShow(upgradeDTO);
        }

    }

    /**
     * 检查更新
     */
    private void checkAppUpdateInfo(){
        upgradeViewModel.checkUpdate(new IActionCallback() {
            @Override
            public void onActionResult(final boolean isSuccessful, final String errorMessage, final Object... object) {
                TaskScheduler.getDefault().perform(new Runnable() {
                    @Override
                    public void run() {
                        if(isSuccessful){
//                            UpgradeEvent event = new UpgradeEvent();
//                            event.setUpdateDTO((UpgradeDTO) object[0]);
//                            EventBus.getDefault().post(event);
                            if(null == upgradeDialog)
                                upgradeDialog = new AppUpgradeDialog(MainActivity.this);
                            upgradeDialog.goShow((UpgradeDTO) object[0]);
                        } else {
                            JccToast.show(errorMessage);
                        }

                    }
                }, TaskMode.MAIN);
            }
        });
    }
}
