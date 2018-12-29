package com.quanzhilian.qzlscan.dbmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yangtm on 2018/1/12.
 * <p>
 * SQLite 操作类
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "qzl_scan.db";
    private static final int DATABASE_VERSION = 1;
    public final String BIll_TABLE_NAME = "repository_bill";
    public final String IN_BIll_ITEM_TABLE_NAME = "in_repository_bill_item";
    public final String IN_BIll_ITEM_DETAIL_TABLE_NAME = "in_repository_bill_item_detail";
    public final String QUERY_TIME_TABLE_NAME = "query_time";
    public final String CUTTING_TABLE_NAME = "repository_cutting";//加工单列表
    public final String REPOSITORY = "repository";//仓库表
    public final String REPOSITORY_POSITION = "repository_position";//库位表
    public final String REPOSITORY_PRODUCT = "repository_product";//库存产品

    public DBHelper(Context context) {
        //CursorFactory设置为null,使用默认值
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //数据库第一次被创建时onCreate会被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        createRepositoryBill(db);//创建入库单表
        createInRepositoryBillItem(db);//创建入库单明细表
        createInRepositoryBillItemDetail(db);//创建入库单明细详情表

        createQueryTime(db);
        createRepository(db);//创建仓库表
        createRepositoryPosition(db);//创建仓库库位表

        createRepositoryProduct(db);
    }

    //如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("ALTER TABLE person ADD COLUMN other STRING");
        alterInRepositoryBill(db, newVersion);
    }

    /*=====单据=====*/

    /**
     * 创建单据表
     *
     * @param db
     */
    private void createRepositoryBill(SQLiteDatabase db) {
        //state 1:未扫码入库；2：扫码入库中；3：待入库审核（扫码完成）
        db.execSQL("CREATE TABLE IF NOT EXISTS " + BIll_TABLE_NAME +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "scmId VARCHAR," +
                "repositoryBillId INTEGER," +
                "repositoryBillNo VARCHAR," +
                "relativeCompanyName VARCHAR," +
                "createDate INTEGER," +
                "repositoryName VARCHAR, " +
                "repositoryId INTEGER, " +
                "type INTEGER," +
                "state INT)");
    }

    /**
     * 创建入库单明细
     *
     * @param db
     */
    private void createInRepositoryBillItem(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + IN_BIll_ITEM_TABLE_NAME +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "scmId VARCHAR," +
                "repositoryBillId INTEGER," +
                "repositoryBillItemId INTEGER," +
                "productId INTEGER," +
                "productInfo VARCHAR," +
                "productSku VARCHAR," +
                "productName VARCHAR," +
                "factoryName VARCHAR," +
                "brandName VARCHAR," +
                "gramWeight REAL," +
                "specification VARCHAR," +
                "grade VARCHAR," +
                "quantity REAL," +
                "quantityUnit INTEGER," +
                "price REAL," +
                "priceUnit INTEGER," +
                "scanQuantity REAL," +
                "ton REAL," +
                "type INTEGER," +
                "hasDetail INTEGER)");
    }

    /**
     * 创建入库单明细详情
     *
     * @param db
     */
    private void createInRepositoryBillItemDetail(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + IN_BIll_ITEM_DETAIL_TABLE_NAME +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "scmId VARCHAR," +
                "repositoryBillId INTEGER," +
                "repositoryProductId INTEGER," +
                "productId INTEGER," +
                "repositoryBillItemId INTEGER," +
                "repositoryBillItemDetailId INTEGER," +
                "barCode VARCHAR," +//条码号
                "amountLing REAL," +//件/令
                "amountWeight REAL," +//件重
                "originalBarcode VARCHAR," + //原厂码
                "repositoryPositionId INTEGER," +
                "repositoryPositionName VARCHAR," +
                "type INTEGER," +
                "isFull INTEGER," +
                "state INTEGER)");//扫描状态
    }


    /**
     * 创建查询时间记录表
     * type：
     * 1：查询入库，
     * 2：查询出库；
     * 3：查询加工单
     *
     * @param db
     */
    private void createQueryTime(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + QUERY_TIME_TABLE_NAME +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "scmId VARCHAR," +
                "type INTEGER," +
                "typeName VARCHAR," +
                "repositoryId INTEGER, " +
                "lastSearchDate VARCHAR)");
    }

    /**
     * 创建仓库表
     *
     * @param db
     */
    private void createRepository(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + REPOSITORY +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "scmId VARCHAR," +
                "repositoryId INTEGER," +
                "repositoryCode VARCHAR," +
                "repositoryName VARCHAR)");
    }

    /**
     * 创建仓库库位表
     *
     * @param db
     */
    private void createRepositoryPosition(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + REPOSITORY_POSITION +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "scmId VARCHAR," +
                "repositoryId INTEGER," +
                "repositoryPositionId INTEGER," +
                "code VARCHAR," +
                "name VARCHAR)");
    }

    private void createRepositoryProduct(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + REPOSITORY_PRODUCT +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "scmId VARCHAR," +
                "productId INTEGER," +
                "repositoryProductId INTEGER," +
                "ton REAL," +
                "frozenTon REAL," +
                "repositoryId INTEGER," +
                "repositoryPositionId INTEGER," +
                "parentId INTEGER," +
                "barCode VARCHAR," +
                "barCodeFactory VARCHAR," +
                "ling REAL," +
                "amount REAL," +
                "inUse INTEGER," +
                "repositoryBillId INTEGER," +
                "type INTEGER" +
                ")");
    }

    private void alterInRepositoryBill(SQLiteDatabase db, int newVersion) {

    }
}
