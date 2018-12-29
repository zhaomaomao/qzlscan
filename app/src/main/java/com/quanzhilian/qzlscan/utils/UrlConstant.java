package com.quanzhilian.qzlscan.utils;

/**
 * Created by hushouqi on 16/3/15
 */
public class UrlConstant {

    //app 服务根地址(测试)
//    public static final String url_app_service_server = "http://192.168.2.159:8080/zhilian-scm/";
//    public static final String url_app_service_server = "http://www.tcczhy.com/";
    public static final String url_app_service_server = "http://www.qzhilian.com/";

    //app 服务根地址（正式）
//    public static final String url_app_service_server = "http://www.qzlvip.com/";

    //用户登录验证地址
    public static final String url_login_validate = "login/loginIn";
    //退出登录
    public static final String url_logout = "identity/logout?";
    //获取会员信息
    public static final String url_get_member_info = "user/memberInfo";
    //获取仓库数据
    public static String url_get_repository_list = "repository/getRepositoryList";
    //获取单据列表
    public static String url_get_bill_list = "repositoryBill/getBillList";

    //获取单据详情
    public static String url_get_bill_detial = "repositoryBill/getBill";

    //根据产品条码查询产品信息
    public static String url_select_product_by_bar_code = "repositoryProduct/selectByBarCode";

    //获取所有库位
    public static String url_get_repository_position = "repository/getRepositoryPositionByRepositoryId";

    //审核入/出库单
    public static String url_save_bill = "repositoryBill/saveBill";

    //审核单据
    public static String url_approve_bill = "repositoryBill/auditBill";

    //获取提货单列表
    public static String url_get_pickup_list = "orderPickup/getPickupList";

    //获取提货单详情
    public static String url_get_pickup_detail = "orderPickup/getPickupDetail";

    //保存提货单
    public static String url_save_bill_out = "repositoryBill/saveAppBillOut";


    //获取加工取料单列表
    public static String url_get_cutting_list ="repositoryCutting/getCuttingList";
    //获取加工取料单详情
    public static String url_get_cutting_detail ="repositoryCutting/getCuttingDetail";
    //审核加工取料单
    public static String url_audit_cutting ="repository/auditCutting";
    //保存加工取料单
    public static String url_save_cutting_product ="repositoryCutting/saveRepositoryCuttingItemDetail";


    //保存移库数据
    public static String url_save_change_position ="repositoryProduct/changeRepositoryPosition";

    //获取服务器当前地址
    public static String url_get_service_time = "login/time";

    public static String APP_UPDATE_CONFIG ="system/p/getAndroidConfig";



    public static final String app_key = "roiueqo9023jofjlajfdj";
    public static final String app_secret = "akffas7239hrewoqriewqr";


}
