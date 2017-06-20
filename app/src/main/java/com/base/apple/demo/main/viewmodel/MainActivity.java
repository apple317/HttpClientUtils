package com.base.apple.demo.main.viewmodel;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jucaicat.base.http.common.BaseHttpClient;
import com.jucaicat.base.http.listener.HttpCallback;
import com.jucaicat.base.images.AppImageOptions;
import com.jucaicat.base.images.BaseImageLoadingListener;
import com.jucaicat.base.images.ImageLoadImpl;
import com.jucaicat.base.uichoose.common.ChooseClient;
import com.jucaicat.base.uichoose.listener.ChooseListener;
import com.jucaicat.base.utils.DateUtils;
import com.jucaicat.base.utils.SharedPreferencesMgr;
import com.jucaicat.base.utils.UserPreferencesMgr;
import com.jucaicat.lightning.common.BaseBean;
import com.jucaicat.lightning.common.PreferenceKeyInterface;
import com.jucaicat.lightning.main.bean.PiggyBankAccountBean;
import com.jucaicat.lightning.main.http.MainHttpClient;
import com.jucaicat.market.R;
import com.jucaicat.market.activitys.GuideActivity;
import com.jucaicat.market.activitys.PhoneNumberActivity;
import com.jucaicat.market.common.JcmConfFactory;
import com.jucaicat.market.database.DatabaseHelper;
import com.jucaicat.market.database.Popup;
import com.jucaicat.market.database.WeburlH5;
import com.jucaicat.market.dialog.ActivityDialog;
import com.jucaicat.market.dialog.AppUpdateDialog;
import com.jucaicat.market.dialog.MessageDialog;
import com.jucaicat.market.fragment.AssetFragment;
import com.jucaicat.market.fragment.HomePageFragment;
import com.jucaicat.market.fragment.ProductTabFragment;
import com.jucaicat.market.fragment.UserFragment;
import com.jucaicat.market.fragments.ActivityFragment;
import com.jucaicat.market.http.HomeHttpClient;
import com.jucaicat.market.http.OtherHttpClient;
import com.jucaicat.market.http.UserHttpClient;
import com.jucaicat.market.jccbean.AnnouncementBean;
import com.jucaicat.market.jccbean.AppActivityBean;
import com.jucaicat.market.jccbean.BannerBottomBean;
import com.jucaicat.market.jccbean.MessageNewBean;
import com.jucaicat.market.jccbean.SplashBean;
import com.jucaicat.market.jccbean.UpdateBean;
import com.jucaicat.market.jccbean.VipNoticeBean;
import com.jucaicat.market.jccbean.Webh5Bean;
import com.jucaicat.market.utility.AppHttpDate;
import com.jucaicat.market.utility.Constant;
import com.jucaicat.market.utility.DesUtil;
import com.jucaicat.market.utility.Utility;
import com.jucaicat.market.utils.Base64;
import com.jucaicat.market.utils.LogUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;


import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;


public class MainActivity extends JcmBaseActivity {
    public static MainActivity a;
    @Bind(R.id.content)
    FrameLayout content;
    @Bind(R.id.hb)
    public ImageView hb;
    @Bind(R.id.hb_diss)
    public ImageView hbDiss;
    @Bind(R.id.ll_hb)
    RelativeLayout llHb;
    @Bind(R.id.image_home)
    ImageView imageHome;
    @Bind(R.id.txt_foot_home)
    TextView txtFootHome;
    @Bind(R.id.image_product)
    ImageView imageProduct;
    @Bind(R.id.txt_foot_product)
    TextView txtFootProduct;

    @Bind(R.id.layout_active)
    FrameLayout layoutActive;
    @Bind(R.id.image_asset)
    ImageView imageAsset;
    @Bind(R.id.txt_foot_asset)
    TextView txtFootAsset;
    @Bind(R.id.image_more)
    ImageView imageMore;
    @Bind(R.id.user_red)
    ImageView userRed;
    @Bind(R.id.txt_foot_more)
    TextView txtFootMore;
    @Bind(R.id.image_foot_act)
    ImageView imageFootAct;
    @Bind(R.id.txt_foot_act)
    TextView txtFootAct;
    private String msg;

    public boolean isCheck = true;
    private boolean isUpdateChecked = false;
    private long mLastBackTime = 0;
    private static final long TIME_DIFF = 2 * 1000;
    private static Toast toast = null;
    private int connectTime = 1;
    public int currentTab = 1;
    Fragment homeFragment, proFragment, actFragement, assetFragment;
    UserFragment myFragment;
    Dialog noticeDialog;
    ImageView vipBg;
    Button dismiss;
    Button go;
    ActivityDialog dialog;
    AppUpdateDialog appUpdateDialog;
    MessageDialog messageDialog;
    public StringBuilder actBuilder = new StringBuilder();
    public StringBuilder actShowBuilder = new StringBuilder();

    final String GetNewMessageUrl = "getMessageNewUrl";


    public MessageNewBean msgNewBean;

    public boolean isHasHB = false;
    public boolean canClose = false;

    //链接跳转
    public static  String app_link_url="";


    public static  PiggyBankAccountBean accountBean;

    @Override
    protected void onSuccess(String content, BaseHttpClient client, Object parse) {
        super.onSuccess(content, client, parse);
        switch (client.getUrlIdentifier()) {
//            case "piggyBankAccount":
//                PiggyBankAccountBean bankAccountBean = (PiggyBankAccountBean) parse;
//                if (bankAccountBean == null) return;
//                if (bankAccountBean != null &&bankAccountBean.getData()!=null&& bankAccountBean.getRetCode() >0) {
//                    accountBean=bankAccountBean;
//                    UserPreferencesMgr.setLong(PreferenceKeyInterface.Preference_User_AssetTotal, (long) bankAccountBean.getData().getTotalAsset());
//                    UserPreferencesMgr.setInt(PreferenceKeyInterface.Preference_User_OpenStatue, bankAccountBean.getData().getOpenProcess());
//                    UserPreferencesMgr.setBoolean(PreferenceKeyInterface.Preference_Setting_Password, bankAccountBean.getData().isExistPayPassword());
//                    UserPreferencesMgr.setBoolean(PreferenceKeyInterface.Preference_User_HasInvested, bankAccountBean.getData().isHasInvested());
//                    UserPreferencesMgr.setBoolean(PreferenceKeyInterface.Preference_User_HasRecharged, bankAccountBean.getData().isHasRecharged());
//                }
//                break;
            case "queryH5Config":
                Webh5Bean webh5Bean = (Webh5Bean) parse;
                try {
                    if (webh5Bean != null && webh5Bean.getRetCode() == 999999) {
                        for (int i = 0; i < webh5Bean.getData().getH5PageDataList().size(); i++) {
                            WeburlH5 weburlH5 = new WeburlH5();
                            weburlH5.setUpdateTime(webh5Bean.getData().getLastUpdateTime());
                            weburlH5.setUrl(webh5Bean.getData().getH5PageDataList().get(i).getH5Url());
                            weburlH5.setUrlKey(webh5Bean.getData().getH5PageDataList().get(i).getH5Name());
                            WeburlH5 weburlH51 = DatabaseHelper.getInstance(getApplicationContext()).isHasWeburlH5(webh5Bean.getData().getH5PageDataList().get(i).getH5Name());
                            if (weburlH51 != null) {
                                weburlH5.setId(weburlH51.getId());
                                DatabaseHelper.getInstance(getApplicationContext()).updateWeburlH5(weburlH5);
                            } else {
                                DatabaseHelper.getInstance(getApplicationContext()).insertWeburlH5(weburlH5);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case GetNewMessageUrl:
                MessageNewBean messageNewBean = (MessageNewBean) parse;
                if (messageNewBean != null && messageNewBean.getRetCode() == 999999) {
                    msgNewBean = messageNewBean;
                    myFragment = myFragment == null ? (new UserFragment()) : myFragment;
                    int countMsg = 0;
                    if (messageNewBean.getData().getSysMsgCount() > 0 || messageNewBean.getData().getTradeMsgCount() > 0) {
                        countMsg = messageNewBean.getData().getSysMsgCount() + messageNewBean.getData().getTradeMsgCount();
                    }
                    if (DatabaseHelper.getInstance(getApplicationContext()).isHasUnreadMessage() > 0) {
                        countMsg = countMsg + DatabaseHelper.getInstance(getApplicationContext()).isHasUnreadMessage();
                    }
                    if (countMsg > 0) {
                        userRed.setVisibility(View.VISIBLE);
                        myFragment.isHasMsg(true, countMsg);
                        return;
                    }
                    userRed.setVisibility(View.GONE);
                    myFragment.isHasMsg(false, 0);
                }
                break;
            case Constant.ANNOUNCEMENT_ACTIVITY:
                AnnouncementBean announcementBean = (AnnouncementBean) parse;
                if (announcementBean != null && announcementBean.getResult() != null) {
                    if (announcementBean.getResult().getIs_changed() == 1) {
                        if (appUpdateDialog != null && appUpdateDialog.isShowing()) {
                            return;
                        }
                        if (noticeDialog != null && noticeDialog.isShowing()) {
                            return;
                        }
                        if (dialog != null && dialog.isShowing()) {
                            return;
                        }
                        if (currentTab != 1) {
                            return;
                        }
                        messageDialog = new MessageDialog(this, R.style.MyDialog,
                                announcementBean.getResult().getTitle(),
                                announcementBean.getResult().getUrl());
                        messageDialog.show();
                        UserPreferencesMgr.setString("change_flag", announcementBean.getResult().getChange_flag());
                    }
                }
                break;
            case Constant.VIP_NOTICE_CALL_BACK:
                VipNoticeBean vipNoticeBeanCall = (VipNoticeBean) parse;
                if (vipNoticeBeanCall.getStatus_code() > 0) {
                } else {
                    Utility.getToast(vipNoticeBeanCall.getRet_msg(), a);
                }
                break;
            case Constant.VIP_UP_GRADE_NOTICE:
                VipNoticeBean vipNoticeBean = (VipNoticeBean) parse;
                if (vipNoticeBean.getStatus_code() > 0) {
                    if (vipNoticeBean.getStatus_code() == 4000) {
                        switch (vipNoticeBean.getLevel_id()) {
                            case 2:
                                vipBg.setBackgroundResource(R.drawable.vip1);
                                if (SharedPreferencesMgr.getBoolean("hasNewPopup", false) && (currentTab == 4 ||
                                        currentTab == 5)) {
                                } else {
                                    if (appUpdateDialog != null && appUpdateDialog.isShowing()) {
                                        return;
                                    }
                                    if (dialog != null && dialog.isShowing()) {
                                        return;
                                    }
                                    if (messageDialog != null && messageDialog.isShowing()) {
                                        return;
                                    }
                                    noticeDialog.show();
                                    UserHttpClient.getInstance().getVipNoticeCall(this, vipNoticeBean.getId(), httpClick);
                                }
                                break;
                            case 3:
                                vipBg.setBackgroundResource(R.drawable.vip2);
                                if (SharedPreferencesMgr.getBoolean("hasNewPopup", false) && (currentTab == 4 ||
                                        currentTab == 5)) {
                                } else {
                                    if (appUpdateDialog != null && appUpdateDialog.isShowing()) {
                                        return;
                                    }
                                    if (dialog != null && dialog.isShowing()) {
                                        return;
                                    }
                                    if (messageDialog != null && messageDialog.isShowing()) {
                                        return;
                                    }
                                    noticeDialog.show();
                                    UserHttpClient.getInstance().getVipNoticeCall(this, vipNoticeBean.getId(), httpClick);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                } else {
                    Utility.getToast(vipNoticeBean.getRet_msg(), a);
                }
                break;
            case Constant.APP_GET_ACTIVITY:
                final AppActivityBean activityBean = (AppActivityBean) parse;
                if (activityBean != null && activityBean.getRetCode() == 999999 && activityBean.getData().size() > 0) {
                    DatabaseHelper databaseHelper = DatabaseHelper.getInstance(getApplicationContext());
                    if (activityBean.getData().get(0).getParam().getDisplayNum() == 1) {
                        if (databaseHelper.isSaved(activityBean.getData().get(0).getPopUpId())) {
                            return;
                        }
                    }
                    if (activityBean.getData().get(0).getParam().getDisplayNum() == 2) {
                        List<Popup> list = databaseHelper.getPopupList(activityBean.getData().get(0).getPopUpId());
                        if (list.size() > 0) {
                            Popup popupEntity = list.get(0);
                            if (DateUtils.isSameDate(popupEntity.getDisplayTime(), System.currentTimeMillis())) {
                                return;
                            }
                        }
                    }
                    if (activityBean.getData().get(0).getParam().getDisplayNum() == 3) {
                        if (actShowBuilder.toString().contains(activityBean.getData().get(0).getPopUpId() + "")) {
                            return;
                        }
                    }
                    if (currentTab != 1) {
                        return;
                    }
                    ImageLoadImpl.getInstance(getApplicationContext()).loadImage(activityBean.getData().get(0).getParam().getPictureUrl(), new BaseImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String imageUri, View view) {
                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view, int failReason) {
                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            if (appUpdateDialog != null && appUpdateDialog.isShowing()) {
                                return;
                            }
                            if (noticeDialog != null && noticeDialog.isShowing()) {
                                return;
                            }
                            if (messageDialog != null && messageDialog.isShowing()) {
                                return;
                            }
                            if (!actBuilder.toString().contains(activityBean.getData().get(0).getPopUpId() + "")) {
                                dialog = new ActivityDialog(MainActivity.this);
                                dialog.goShow(activityBean);
                            }
                        }

                        @Override
                        public void onLoadingCancelled(String imageUri, View view) {

                        }
                    });
                }
                break;
            case Constant.APP_UPDATE:
                UpdateBean updateBean = (UpdateBean) parse;
                if (updateBean != null && updateBean.getRetCode() == 999999) {
                    if ("1".equals(updateBean.getData().getFlag() + "") || "0".equals(updateBean.getData().getFlag() + "")) {
                        if (!isUpdateChecked) {
                            if (dialog != null && dialog.isShowing()) {
                                return;
                            }
                            if (noticeDialog != null && noticeDialog.isShowing()) {
                                return;
                            }
                            if (messageDialog != null && messageDialog.isShowing()) {
                                return;
                            }
                            if (currentTab != 1) {
                                return;
                            }
                            appUpdateDialog = new AppUpdateDialog(this);
                            appUpdateDialog.goShow(updateBean);
                            isUpdateChecked = true;
                        }
                    }
                }
                break;
            case Constant.BANNER_ACTIVITY:
                BannerBottomBean bannerBottomBean = (BannerBottomBean) parse;
                ImageLoadImpl imgLoad = ImageLoadImpl.getInstance(getApplicationContext());
                if (bannerBottomBean != null && bannerBottomBean.getRetCode() == 107000) {
                    BannerBottomBean.Data data = bannerBottomBean.getData();
                    if (data == null)
                        return;
                    ArrayList<BannerBottomBean.CommonButton> commonButtons = data.getCommonButtons();
                    BannerBottomBean.ActivityButton activityButton = data.getActivityButton();
                    for (int i = 0; i < commonButtons.size(); i++) {
                        imgLoad.loadImage(commonButtons.get(i).getIcon(), null, null);
                        imgLoad.loadImage(commonButtons.get(i).getPressIcon(), null, null);
                        SharedPreferencesMgr.setString("tab_icon_normal" + i, commonButtons.get(i).getIcon());
                        SharedPreferencesMgr.setString("tab_icon_pass" + i, commonButtons.get(i).getPressIcon());
                        SharedPreferencesMgr.setString("tab_txt_normal" + i, commonButtons.get(i).getName());
                        SharedPreferencesMgr.setString("tab_txt_pass" + i, commonButtons.get(i).getPressName());
                    }
                    imgLoad.loadImage(activityButton.getIcon(), null, null);
                    imgLoad.loadImage(activityButton.getPressIcon(), null, null);
                    if (activityButton != null) {
                        SharedPreferencesMgr.setString("tab_icon_normal" + 4, activityButton.getIcon());
                        SharedPreferencesMgr.setString("tab_icon_pass" + 4, activityButton.getPressIcon());
                        SharedPreferencesMgr.setString("tab_txt_normal" + 4, activityButton.getName());
                        SharedPreferencesMgr.setString("tab_txt_pass" + 4, activityButton.getPressName());
                        SharedPreferencesMgr.setInt("size", commonButtons.size());
                        SharedPreferencesMgr.setString("pageTitle", activityButton.getPageTitle());
                        SharedPreferencesMgr.setInt("needLogin", activityButton.getNeedLogin());
                        SharedPreferencesMgr.setString("pageUrl", activityButton.getPageUrl());
                        SharedPreferencesMgr.setString("forwardTitle", activityButton.getForwardTitle());
                        SharedPreferencesMgr.setInt("hasActivityButton", data.getHasActivityButton());
                        SharedPreferencesMgr.setString("forwardUrl", activityButton.getForwardUrl());
                    }
                    onUpdateTabStatue(currentTab);
                }
                break;
        }
    }

    @Override
    protected void onError(Throwable error, BaseHttpClient client) {
        super.onError(error, client);
        switch (client.getUrlIdentifier()) {
            case Constant.VIP_NOTICE_CALL_BACK:
                Utility.getToast(getString(R.string.hint_network_not_connected), a);
                break;
            case Constant.VIP_UP_GRADE_NOTICE:
                //toFailure();
                break;
        }
    }

    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
        DatabaseHelper.getInstance(getApplicationContext()).deletePopuped();
        a = this;
        app.setActivity(this);
        int page = getIntent().getIntExtra("page", 1);
        currentTab = page;
        OtherHttpClient.getInstance().onUpdate(app.getVerCode() + "", httpClick);
        HomeHttpClient.getInstance().getAnnouncement(httpClick);
        OtherHttpClient.getInstance().onAppActivity(httpClick);
        HomeHttpClient.getInstance().getHomeWebH5(httpClick, 0 + "");
        switchTab(currentTab);
        HomeHttpClient.getInstance().getTabData(httpClick);
        AppHttpDate.getInvitationShareData(a);//邀请好友分享数据
        addGuidePage();
        otherBanner();
        initUmengRegistrar();
    }

    @Override
    protected void initLisitener() {
        super.initLisitener();
        try{
            dismiss.setOnClickListener(click);
            go.setOnClickListener(click);
            hbDiss.setOnClickListener(click);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * app内部跳转链接
     */
    void appLink() {

        if(!app_link_url.trim().equals("")){
            ChooseClient.getBaseClient().newBuilder()
                    .url(app_link_url)
                    .build().execute(this, new ChooseListener(){
                @Override
                public void success(String url, int statue) {
                    app_link_url="";
                    if(statue==0){
                        if(url.contains("/other/membercenter")){
                            String id = DesUtil.encrypt(DesUtil.decrypt(String.valueOf(Utility.getUserId(getApplicationContext()))));
                            Intent intent3 = new Intent(getApplicationContext(), com.jucaicat.market.activitys.WebViewActivity.class);
                            WeburlH5 weburlH53 = DatabaseHelper.getInstance(getApplicationContext()).getWeburlH5("memberCenter");
                            String safeness3 = "";
                            if (weburlH53 != null && weburlH53.getUrl() != null) {
                                safeness3 = weburlH53.getUrl() + String.format("?user_id=%s&os_type=android", URLEncoder.encode(id));
                            } else {
                                safeness3 = String.format("http://apise.jucaicat.com/jccv5/UserAssetVipInfo211" + "?user_id=%s&os_type=android", URLEncoder.encode(id));
                            }
                            intent3.putExtra("title", "会员中心");
                            intent3.putExtra("url", safeness3);
                            intent3.putExtra("page", 45);
                            startActivity(intent3);
                        }else if(url.trim().toString().startsWith("http://")||url.trim().toString().startsWith("https://")){
                            Intent intent = new Intent(getApplicationContext(), com.jucaicat.market.activity.WebViewActivity.class);
                            intent.putExtra("url", url);
                            intent.putExtra("title", "");
                            startActivity(intent);
                        }
                    }
                }
            });
        }


        if (getIntent().getStringExtra("app_external_link") != null && !getIntent().getStringExtra("app_external_link").equals("")) {
            try {
                String url = getIntent().getStringExtra("app_external_link");
                Uri uriSchme = Uri.parse(url);
                boolean userIsLogin = UserPreferencesMgr.getBoolean("USER_IS_LOGIN", false);
                setIntent(new Intent());
                if (uriSchme.getQueryParameter("targeturl") != null) {
                    String param = uriSchme.getQueryParameter("targeturl");
                    String paramDecode = new String(Base64.decode(param));
                    int pageIndex = -1;
                    if (paramDecode.contains(JcmConfFactory.UI_ROUTER_URL_PRODUCT_PAGE)) {
                        pageIndex = 2;
                    } else if (paramDecode.contains(JcmConfFactory.UI_ROUTER_URL_VENUE_PAGE)) {
                        pageIndex = 3;
                    }
                    if (pageIndex != -1) {
                        currentTab = pageIndex;
                        switchTab(currentTab);
                        return;
                    }
                    Uri uriSchmeTarget = Uri.parse(paramDecode);
                    if (uriSchmeTarget.getQueryParameter("needlogin") != null) {
                        if (uriSchmeTarget.getQueryParameter("needlogin").equals("1")) {
                            paramDecode = paramDecode.replace("&needlogin=" + uriSchmeTarget.getQueryParameter("needlogin"), "");
                            paramDecode = paramDecode.replace("needlogin=" + uriSchmeTarget.getQueryParameter("needlogin") + "&", "");
                            if(uriSchmeTarget.getQueryParameterNames().size()==1){
                                paramDecode = paramDecode.replace("?needlogin=" + uriSchmeTarget.getQueryParameter("needlogin"), "");
                            }
                            if (!userIsLogin) {
                                Intent intent = new Intent(getApplicationContext(), PhoneNumberActivity.class);
                                intent.putExtra("app_external_login_url", paramDecode);
                                startActivityForResult(intent, PhoneNumberActivity.FLG_APPLINK_PHONE_NUMBER);
                                return;
                            }
                        }
                    }
                    ChooseClient.getBaseClient().newBuilder()
                            .url(paramDecode)
                            .build().execute(this, new ChooseListener(){
                        @Override
                        public void success(String url, int statue) {
                            if(statue==0){
                                if(url.contains("/other/membercenter")){
                                    String id = DesUtil.encrypt(DesUtil.decrypt(String.valueOf(Utility.getUserId(getApplicationContext()))));
                                    Intent intent3 = new Intent(getApplicationContext(), com.jucaicat.market.activitys.WebViewActivity.class);
                                    WeburlH5 weburlH53 = DatabaseHelper.getInstance(getApplicationContext()).getWeburlH5("memberCenter");
                                    String safeness3 = "";
                                    if (weburlH53 != null && weburlH53.getUrl() != null) {
                                        safeness3 = weburlH53.getUrl() + String.format("?user_id=%s&os_type=android", URLEncoder.encode(id));
                                    } else {
                                        safeness3 = String.format("http://apise.jucaicat.com/jccv5/UserAssetVipInfo211" + "?user_id=%s&os_type=android", URLEncoder.encode(id));
                                    }
                                    intent3.putExtra("title", "会员中心");
                                    intent3.putExtra("url", safeness3);
                                    intent3.putExtra("page", 45);
                                    startActivity(intent3);
                                }else if(url.trim().toString().startsWith("http://")||url.trim().toString().startsWith("https://")){
                                    Intent intent = new Intent(getApplicationContext(), com.jucaicat.market.activity.WebViewActivity.class);
                                    intent.putExtra("url", url);
                                    intent.putExtra("title", "");
                                    startActivity(intent);
                                }
                            }
                        }
                    });
                }
            } catch (Exception localException) {
                localException.printStackTrace();
            }
        }
    }


    public void onResume() {
        appLink();
        super.onResume();
        MobclickAgent.onResume(this);
        boolean userIsLogin = UserPreferencesMgr.getBoolean("USER_IS_LOGIN", false);
        if (!userIsLogin) {
            userRed.setVisibility(View.GONE);
            try {
                if (myFragment != null && currentTab == 5) {
                    myFragment.isHasMsg(false, 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            if (!UserPreferencesMgr.getBoolean(PreferenceKeyInterface.Preference_Has_BaseData, false)) {
                MainHttpClient.getInstance().baseHomeData(new HttpCallback() {
                    @Override
                    public void success(String content, BaseHttpClient client, Object parse) {
                        BaseBean baseBean = (BaseBean) parse;
                        if (baseBean.getRetCode() > 0) {
                            UserPreferencesMgr.setBoolean(PreferenceKeyInterface.Preference_Has_BaseData, true);
                        }
                    }

                    @Override
                    public void error(Throwable error, BaseHttpClient client) {

                    }
                });
            }
            try {
                userRed.setVisibility(DatabaseHelper.getInstance(getApplicationContext()).isHasUnreadMessage() > 0 ? View.VISIBLE : View.GONE);
                int msgNumber = DatabaseHelper.getInstance(getApplicationContext()).isHasUnreadMessage();
                myFragment = myFragment == null ? (new UserFragment()) : myFragment;
                myFragment.isHasMsg(msgNumber > 0 ? true : false, msgNumber);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected View initView(Bundle bundle) {
        noticeDialog = Utility.getLevelUpDialog(this);
        noticeDialog.setCanceledOnTouchOutside(false);//设置弹框点击外部不消失
        dismiss = (Button) noticeDialog.findViewById(R.id.dismiss);
        go = (Button) noticeDialog.findViewById(R.id.go);
        vipBg = (ImageView) noticeDialog.findViewById(R.id.iv_dialog_bg);
        return getLayoutInflater().inflate(R.layout.activity_main, null);
    }


    /**
     * 替换底部fragment
     */
    private void replaceContent(Fragment fragment, String tabId) {
        try {
            FragmentTransaction transaction = this.getSupportFragmentManager()
                    .beginTransaction();
            transaction.add(R.id.content, fragment, tabId);
            transaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void onUpdateTabStatue(int tabPosition) {
        imageHome.setSelected(tabPosition == 1 ? true : false);
        imageProduct.setSelected(tabPosition == 2 ? true : false);
        imageAsset.setSelected(tabPosition == 4 ? true : false);
        imageMore.setSelected(tabPosition == 5 ? true : false);
        txtFootHome.setSelected(tabPosition == 1 ? true : false);
        txtFootProduct.setSelected(tabPosition == 2 ? true : false);
        txtFootAsset.setSelected(tabPosition == 4 ? true : false);
        txtFootMore.setSelected(tabPosition == 5 ? true : false);
        txtFootAct.setSelected(tabPosition == 3 ? true : false);
        imageFootAct.setSelected(tabPosition == 3 ? true : false);
        if (currentTab == 1) {
            // AppHttpDate.getFloatPage(a, hb, hbDiss);//优酷活动
            hbDiss.setVisibility(canClose ? View.VISIBLE : View.GONE);
            hb.setVisibility(isHasHB ? View.VISIBLE : View.GONE);
        } else {
            hb.setVisibility(View.GONE);
            hbDiss.setVisibility(View.GONE);
        }
        layoutActive.setVisibility(SharedPreferencesMgr.getInt("hasActivityButton", 0) == 1 ? View.VISIBLE : View.GONE);
        ImageLoadImpl imgLoad = ImageLoadImpl.getInstance(getApplicationContext());
        if (SharedPreferencesMgr.getInt("hasActivityButton", 0) == 1) {
            String actVal = txtFootAct.isSelected() ? SharedPreferencesMgr.getString("tab_txt_pass" + 4, "") : SharedPreferencesMgr.getString("tab_txt_normal" + 4, "");
            // txtFootAct.setText(actVal.trim().equals("") ? txtFootAct.getText().toString() : actVal);
            imgLoad.displayImage(imageFootAct.isSelected() ? SharedPreferencesMgr.getString("tab_icon_pass" + 4, "") :
                    SharedPreferencesMgr.getString("tab_icon_normal" + 4, ""), imageFootAct, null
            );
        }

        String homeVal = txtFootHome.isSelected() ? SharedPreferencesMgr.getString("tab_txt_pass" + 0, "") : SharedPreferencesMgr.getString("tab_txt_normal" + 0, "");
        txtFootHome.setText(homeVal.trim().equals("") ? txtFootHome.getText().toString() : homeVal);
        String proVal = txtFootProduct.isSelected() ? SharedPreferencesMgr.getString("tab_txt_pass" + 1, "") : SharedPreferencesMgr.getString("tab_txt_normal" + 1, "");
        txtFootProduct.setText(proVal.trim().equals("") ? txtFootProduct.getText().toString() : proVal);
        String assetVal = txtFootAsset.isSelected() ? SharedPreferencesMgr.getString("tab_txt_pass" + 2, "") : SharedPreferencesMgr.getString("tab_txt_normal" + 2, "");
        txtFootAsset.setText(assetVal.trim().equals("") ? txtFootAsset.getText().toString() : assetVal);
        String meVal = txtFootMore.isSelected() ? SharedPreferencesMgr.getString("tab_txt_pass" + 3, "") : SharedPreferencesMgr.getString("tab_txt_normal" + 3, "");
        txtFootMore.setText(meVal.trim().equals("") ? txtFootMore.getText().toString() : meVal);
        imgLoad.displayImage(imageHome.isSelected() ? SharedPreferencesMgr.getString("tab_icon_pass" + 0, "") :
                SharedPreferencesMgr.getString("tab_icon_normal" + 0, ""), imageHome, new AppImageOptions().build()
                .showImageOnLoading(imageHome.isSelected() ? R.drawable.tab_icon_home_selected : R.drawable.tab_icon_home).build());
        imgLoad.displayImage(imageProduct.isSelected() ? SharedPreferencesMgr.getString("tab_icon_pass" + 1, "") :
                SharedPreferencesMgr.getString("tab_icon_normal" + 1, ""), imageProduct, new AppImageOptions().build()
                .showImageOnLoading(imageProduct.isSelected() ? R.drawable.tab_icon_product_selected : R.drawable.tab_icon_product).build());
        imgLoad.displayImage(imageAsset.isSelected() ? SharedPreferencesMgr.getString("tab_icon_pass" + 2, "") :
                SharedPreferencesMgr.getString("tab_icon_normal" + 2, ""), imageAsset, new AppImageOptions().build()
                .showImageOnLoading(imageAsset.isSelected() ? R.drawable.tab_icon_asset_selected : R.drawable.tab_icon_asset).build());
        imgLoad.displayImage(imageMore.isSelected() ? SharedPreferencesMgr.getString("tab_icon_pass" + 3, "") :
                SharedPreferencesMgr.getString("tab_icon_normal" + 3, ""), imageMore, new AppImageOptions().build()
                .showImageOnLoading(imageMore.isSelected() ? R.drawable.tab_icon_more_selected : R.drawable.tab_icon_more).build());
    }

    public Handler handler = new Handler();

    private void initUmengRegistrar() {
        startScreenBroadcastReceiver(this);
    }

    private void otherBanner() {
        String isBanner = getIntent().getStringExtra("isBanner");
        if ("isBanner".equals(isBanner)) {
            SplashBean.Data appDateBeanShareMap = (SplashBean.Data) getIntent().getSerializableExtra("appDateBeanShareMap");
            int userId = getIntent().getIntExtra("user_id", 0);
            if (null != appDateBeanShareMap) {
                LogUtil.i("appDateBeanShareMap", appDateBeanShareMap.toString());
                Intent intent = new Intent(this, com.jucaicat.market.activity.WebViewActivity.class);
                intent.putExtra("title","");
                intent.putExtra("url", String.format(appDateBeanShareMap.getWap_url() + "?user_id=%d&app_market_id=%d", userId, Constant.getAppMarketId()));
                startActivity(intent);
            }
        }
    }

    private void addGuidePage() {
        // 添加启动引导页
        SharedPreferences sharedPreferences = this.getSharedPreferences("GUIDE_NEW", Context.MODE_PRIVATE);
        String isGuidePage = sharedPreferences.getString("isGuidePage", "N");
        if (!"N".equals(isGuidePage)) {
            sharedPreferences.edit().clear().commit();
            SharedPreferencesMgr.setString("isGuidePage", "Y");
            return;
        }
        if ("N".equals(SharedPreferencesMgr.getString("isGuidePage", "N"))) {
            SharedPreferencesMgr.setString("isGuidePage", "Y");
            Intent intent1 = new Intent(this, GuideActivity.class);
            startActivity(intent1);
        }
    }

    @Override
    protected void onDestroy() {
        try {
            stopScreenBroadcastReceiver(a);
            super.onDestroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * app活动跳主会场
     */
    public void onShowActivityTab() {
        currentTab = 3;
        SharedPreferencesMgr.setInt("hasActivityButton", 1);
        layoutActive.setVisibility(View.VISIBLE);
        onUpdateTabStatue(currentTab);
        actFragement = new ActivityFragment();
        replaceContent(actFragement, "tab" + currentTab);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long now = new Date().getTime();
            if (now - mLastBackTime < TIME_DIFF) {
                if (null != toast) {
                    toast.cancel();
                    toast = null;
                }
                finish();
            } else {
                mLastBackTime = now;
                toast = Utility.getToast1("再按一次返回键退出聚财猫", this);
                toast.show();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    // 检测有没有消息
    public void updateMessagecout() {
        Bundle bundle = new Bundle();
        bundle.putString("urlIdentifier", GetNewMessageUrl);
        UserHttpClient.getInstance().getMessageNewCount(bundle, httpClick);
    }

    public void toFailure() {
        //是否开启防灾
        if (Constant.IS_OPEN) {
            if (connectTime == 1) {
                Constant.API_BASE_URL = "http://42.120.63.122/jccv5/";
            } else if (connectTime == 2) {
                Constant.API_BASE_URL = "http://backup.jucaicat.com/jccv5/";
            } else if (connectTime == 3) {
                Constant.API_BASE_URL = "http://apise.jucaicat.com/jccv5/";
                connectTime = 1;
            }
            connectTime++;
        }
    }


    @OnClick({R.id.layout_home, R.id.layout_product, R.id.layout_active, R.id.image_activity, R.id.layout_asset, R.id.layout_more})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_home:
                switchTab(1);
                break;
            case R.id.layout_product:
                switchTab(2);
                break;
            case R.id.image_activity:
            case R.id.layout_active:
                switchTab(3);
                break;
            case R.id.layout_asset:
                switchTab(4);
                break;
            case R.id.layout_more:
                switchTab(5);
                break;
        }
    }

    @Override
    public void treatClickEvent(View v) {
        super.treatClickEvent(v);
        switch (v.getId()) {
            case R.id.dismiss:
                noticeDialog.dismiss();
                break;
            case R.id.go:
                String id = DesUtil.encrypt(DesUtil.decrypt(String.valueOf(Utility.getUserId(getApplicationContext()))));
                Intent intent3 = new Intent(getApplicationContext(), com.jucaicat.market.activitys.WebViewActivity.class);
                WeburlH5 weburlH53 = DatabaseHelper.getInstance(getApplicationContext()).getWeburlH5("memberCenter");
                String safeness3 = "";
                if (weburlH53 != null && weburlH53.getUrl() != null) {
                    safeness3 = weburlH53.getUrl() + String.format("?user_id=%s&os_type=android", URLEncoder.encode(id));
                } else {
                    safeness3 = String.format("http://apise.jucaicat.com/jccv5/UserAssetVipInfo211" + "?user_id=%s&os_type=android", URLEncoder.encode(id));
                }
                intent3.putExtra("title", "会员中心");
                intent3.putExtra("url", safeness3);
                intent3.putExtra("page", 45);
                startActivity(intent3);
                break;
            case R.id.hb_diss:
                isCheck = false;
                llHb.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean userIsLogin = UserPreferencesMgr.getBoolean("USER_IS_LOGIN", false);
        if (resultCode == 30) {
            switchTab(2);
            return;
        }
        if (requestCode > 1 || requestCode < 10) {
            if (userIsLogin) {
                updateMessagecout();
                //用于活动弹出框登录
                if (null != dialog && dialog.isShowing()) {
                    dialog.loginCall();
                }
            }
        }
    }

    /**
     * 切换菜单tab
     */
    public void switchTab(int tabIndex) {
        homeFragment = homeFragment == null ? (new HomePageFragment()) : homeFragment;
        proFragment = proFragment == null ? (new ProductTabFragment()) : proFragment;
        actFragement = actFragement == null ? (new ActivityFragment()) : actFragement;
        assetFragment = assetFragment == null ? (new AssetFragment()) : assetFragment;
        myFragment = myFragment == null ? (new UserFragment()) : myFragment;
        if (UserPreferencesMgr.getBoolean("USER_IS_LOGIN", false)) {
            updateMessagecout();
        }
        currentTab = tabIndex;
        onUpdateTabStatue(currentTab);
    //    MainHttpClient.getInstance().piggyBankAccount(httpClick);
        UserHttpClient.getInstance().getVipNotice(getApplicationContext(), httpClick);
        switch (tabIndex) {
            case 1:
                homeFragment = getSupportFragmentManager().findFragmentByTag("tab" + currentTab);
                if (homeFragment == null) {
                    homeFragment = new HomePageFragment();
                    replaceContent(homeFragment, "tab" + currentTab);
                }

                getSupportFragmentManager().beginTransaction()
                        .show(homeFragment)
                        .hide(proFragment)
                        .hide(actFragement)
                        .hide(assetFragment)
                        .hide(myFragment)
                        .commitAllowingStateLoss();
                break;
            case 2:
                proFragment = getSupportFragmentManager().findFragmentByTag("tab" + currentTab);
                if (proFragment == null) {
                    proFragment = new ProductTabFragment();
                    replaceContent(proFragment, "tab" + currentTab);
                }
                getSupportFragmentManager().beginTransaction()
                        .show(proFragment)
                        .hide(homeFragment)
                        .hide(actFragement)
                        .hide(assetFragment)
                        .hide(myFragment)
                        .commitAllowingStateLoss();
                break;
            case 3:
                actFragement = getSupportFragmentManager().findFragmentByTag("tab" + currentTab);
                if (actFragement == null) {
                    actFragement = new ActivityFragment();
                    replaceContent(actFragement, "tab" + currentTab);
                }
                getSupportFragmentManager().beginTransaction()
                        .show(actFragement)
                        .hide(homeFragment)
                        .hide(proFragment)
                        .hide(assetFragment)
                        .hide(myFragment)
                        .commitAllowingStateLoss();
                break;
            case 4:
                assetFragment = getSupportFragmentManager().findFragmentByTag("tab" + currentTab);
                if (assetFragment == null) {
                    assetFragment = new AssetFragment();
                    replaceContent(assetFragment, "tab" + currentTab);
                } else {
                    assetFragment.onResume();
                }
                getSupportFragmentManager().beginTransaction()
                        .show(assetFragment)
                        .hide(homeFragment)
                        .hide(proFragment)
                        .hide(actFragement)
                        .hide(myFragment)
                        .commitAllowingStateLoss();
                break;
            case 5:
                myFragment = (UserFragment) getSupportFragmentManager().findFragmentByTag("tab" + currentTab);
                if (myFragment == null) {
                    myFragment = new UserFragment();
                    replaceContent(myFragment, "tab" + currentTab);
                }
                getSupportFragmentManager().beginTransaction()
                        .show(myFragment)
                        .hide(homeFragment)
                        .hide(proFragment)
                        .hide(assetFragment)
                        .hide(actFragement)
                        .commitAllowingStateLoss();
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        try {
            if (getIntent().getExtras().getString("apple_url") != null) {
                int pageIndex = -1;
                switch (getIntent().getExtras().getString("apple_url")) {
                    case JcmConfFactory.UI_ROUTER_URL_HOME_PAGE:
                        pageIndex = 1;
                        break;
                    case JcmConfFactory.UI_ROUTER_URL_PRODUCT_PAGE:
                        pageIndex = 2;
                        break;
                    case JcmConfFactory.UI_ROUTER_URL_VENUE_PAGE:
                        pageIndex = 3;
                        break;
                    case JcmConfFactory.UI_ROUTER_URL_ASSETS_PAGE:
                        pageIndex = 4;
                        break;
                    case JcmConfFactory.UI_ROUTER_URL_MY_PAGE:
                        pageIndex = 5;
                        break;
                }
                if (pageIndex == 3 && layoutActive.getVisibility() != View.VISIBLE) {
                    return;
                }
                currentTab = pageIndex;
                switchTab(currentTab);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

