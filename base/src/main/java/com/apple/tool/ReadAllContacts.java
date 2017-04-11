//package com.market.tool;
//
//import android.annotation.TargetApi;
//import android.content.Context;
//import android.database.Cursor;
//import android.os.Build;
//import android.provider.ContactsContract;
//
//import com.market.bean.ReadAllContactsBean;
//
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * Created by zhangliang on 16/6/8. 读取通信录信息
// */
//public class ReadAllContacts {
//
//
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//    public static Set<ReadAllContactsBean> readAllContacts(Context context) {
//        int oldRid = -1;
//        int contactId;
//        Set<ReadAllContactsBean> readAllContactsBeanList = new HashSet<>();
//        Cursor cursor = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI,
//                null, null, null, ContactsContract.Data.RAW_CONTACT_ID);
//        ReadAllContactsBean bean = null;
//        ReadAllContactsBean.Data data;
//        while (cursor.moveToNext()) {
//            contactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Data.RAW_CONTACT_ID));
//            if (oldRid != contactId) {
//                LogUtil.i("contactId", contactId + "");
//                oldRid = contactId;
//                bean = new ReadAllContactsBean();
//                data =new ReadAllContactsBean.Data();
//                bean.setData(data);
//            }
//            assert bean != null;
//            String mimeType = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.MIMETYPE)); // 取得mimetype类型,扩展的数据都在这个类型里面
//            if (ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE.equals(mimeType)) {
//                String real_name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME));
//                bean.setReal_name(real_name);
//            }
//
//            if (ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE.equals(mimeType)) { // 取出组织类型
//                int orgType = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TYPE)); // 单位
//                if (orgType == ContactsContract.CommonDataKinds.Organization.TYPE_CUSTOM) { // if (orgType ==
//                    String company = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY));
//                    bean.setCompany(company);
//                }
//            }
//
//            if (ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE.equals(mimeType)) {
//                int phoneType = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE)); // 手机
//                if (phoneType == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE) {
//                    String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                    bean.setPhone(phoneNumber);
//                }
//
//
//                // 住宅电话
//                if (phoneType == ContactsContract.CommonDataKinds.Phone.TYPE_HOME) {
//                    String homeNum = cursor.getString(cursor
//                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                    bean.getData().setHomeNum(homeNum);
//                }
//                // 单位电话
//                if (phoneType == ContactsContract.CommonDataKinds.Phone.TYPE_WORK) {
//                    String jobNum = cursor.getString(cursor
//                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                    bean.getData().setJobNum(jobNum);
//                }
//            }
//
//            if (ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE.equals(mimeType)) {
//                int emailType = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
//                if (emailType == ContactsContract.CommonDataKinds.Email.TYPE_HOME) {
//                    String email = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
//                    bean.setEmail(email);
//                }
//            }
//
//
//            readAllContactsBeanList.add(bean);
//        }
//        cursor.close();
//        return readAllContactsBeanList;
//
//
//    }
//}
