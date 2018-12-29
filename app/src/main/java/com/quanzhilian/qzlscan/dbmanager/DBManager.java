package com.quanzhilian.qzlscan.dbmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.quanzhilian.qzlscan.models.domain.RepositoryProduct;
import com.quanzhilian.qzlscan.models.domain.SimpleOrderPickupItemModel;
import com.quanzhilian.qzlscan.models.domain.SimpleOrderPickupModel;
import com.quanzhilian.qzlscan.models.domain.SimpleRepositoryBillItemDetailModel;
import com.quanzhilian.qzlscan.models.domain.SimpleRepositoryBillItemModel;
import com.quanzhilian.qzlscan.models.domain.SimpleRepositoryBillModel;
import com.quanzhilian.qzlscan.models.domain.SimpleRepositoryCutting;
import com.quanzhilian.qzlscan.models.domain.SimpleRepositoryCuttingDetail;
import com.quanzhilian.qzlscan.models.domain.SimpleRepositoryCuttingItem;
import com.quanzhilian.qzlscan.models.domain.SimpleRepositoryModel;
import com.quanzhilian.qzlscan.models.domain.SimpleRepositoryPositionModel;
import com.quanzhilian.qzlscan.models.enums.EnumQuantityUnit;
import com.quanzhilian.qzlscan.models.enums.EnumQueryType;
import com.quanzhilian.qzlscan.models.sqlmodel.RpositoryBillItemDetailModel;
import com.quanzhilian.qzlscan.models.sqlmodel.RpositoryBillItemModel;
import com.quanzhilian.qzlscan.models.sqlmodel.RpositoryBillModel;
import com.quanzhilian.qzlscan.utils.AppUtils;
import com.quanzhilian.qzlscan.utils.ParseTimeUtil;
import com.quanzhilian.qzlscan.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * Created by yangtm on 2018/1/12.
 */

public class DBManager {
    // 静态引用
    private volatile static DBManager mInstance;
    private DBHelper helper;
    private SQLiteDatabase db;

    private DBManager(Context context) {
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DBManager getInstance(Context context) {
        DBManager inst = mInstance;
        if (inst == null) {
            synchronized (DBManager.class) {
                inst = mInstance;
                if (inst == null) {
                    inst = new DBManager(context);
                    mInstance = inst;
                }
            }
        }
        return inst;
    }

    //==================入库单数据操作相关方法 begin============================//

    /**
     * add persons
     *
     * @param repositoryBillModelList
     */
    public void addRpositoryBill(List<SimpleRepositoryBillModel> repositoryBillModelList, int type) {
        db.beginTransaction();  //开始事务
        try {
            for (SimpleRepositoryBillModel rpositoryBillModel : repositoryBillModelList) {
                if (rpositoryBillModel.getState() != 1) {
                    // 清除已经审核的单据（包括明细和详情）
                    delBill(rpositoryBillModel.getRepositoryBillId().toString(), type + "");
                } else {
                    // 插入不存在的单据
                    RpositoryBillModel model = getBillById(rpositoryBillModel.getRepositoryBillId().toString(), type + "");
                    if (model == null) {
                        db.execSQL("INSERT INTO " + helper.BIll_TABLE_NAME + " VALUES(null,?,?,?,?,?,?,?,?,?)",
                                new Object[]{
                                        rpositoryBillModel.getScmId(),
                                        rpositoryBillModel.getRepositoryBillId(),
                                        rpositoryBillModel.getRepositoryBillNo(),
                                        rpositoryBillModel.getRelativeCompanyName(),
                                        rpositoryBillModel.getCreateDate().getTime(),
                                        rpositoryBillModel.getRepositoryName(),
                                        rpositoryBillModel.getRepositoryId(),
                                        type,
                                        1});
                    }
                }
            }
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    /**
     * 根据获取到的单据详情插入单据明细表和单据明细详情表
     *
     * @param repositoryBillModel
     */
    public boolean addInRpositoryDetail(SimpleRepositoryBillModel repositoryBillModel, String type) {
        db.beginTransaction();  //开始事务
        try {
            //插入入库明细表
            if (repositoryBillModel.getRepositoryBillItemList() != null && repositoryBillModel.getRepositoryBillItemList().size() > 0) {
                for (SimpleRepositoryBillItemModel rpositoryBillItemModel : repositoryBillModel.getRepositoryBillItemList()) {
                    rpositoryBillItemModel.setScanQuantity(0);
                    if (rpositoryBillItemModel.getRepositoryBillItemDetailList() != null) {
                        for (SimpleRepositoryBillItemDetailModel itemDetailModel : rpositoryBillItemModel.getRepositoryBillItemDetailList()) {
                            String querySql = "SELECT * FROM " + helper.IN_BIll_ITEM_DETAIL_TABLE_NAME + " WHERE barCode = ? and type=? and state = 1";
                            Cursor c = db.rawQuery(querySql, new String[]{itemDetailModel.getBarCode(), type});
                            if (c != null && c.getCount() > 0) {
                                itemDetailModel.setState(1);
                                rpositoryBillItemModel.setScanQuantity(rpositoryBillItemModel.getScanQuantity() + 1);
                            } else {
                                itemDetailModel.setState(0);
                            }
                        }
                    }
                }
                //删除原明细和详情
                ArrayList<RpositoryBillItemModel> rpositoryBillItemModels = new ArrayList<RpositoryBillItemModel>();
                String querySql = "SELECT * FROM " + helper.IN_BIll_ITEM_TABLE_NAME + " WHERE repositoryBillId = ? and type = ?";
                Cursor c = db.rawQuery(querySql, new String[]{repositoryBillModel.getRepositoryBillId().toString(), type});
                int repositoryBillItemIdIndex = c.getColumnIndex("repositoryBillItemId");
                while (c.moveToNext()) {
                    int repositoryBillItemId = c.getInt(repositoryBillItemIdIndex);
                    db.delete(helper.IN_BIll_ITEM_DETAIL_TABLE_NAME, "repositoryBillItemId=? and type = ?", new String[]{repositoryBillItemId + "", type});
                }
                c.close();
                db.delete(helper.IN_BIll_ITEM_TABLE_NAME, "repositoryBillId=? and  type = ?", new String[]{repositoryBillModel.getRepositoryBillId().toString(), type});

                for (SimpleRepositoryBillItemModel rpositoryBillItemModel : repositoryBillModel.getRepositoryBillItemList()) {
                    insertInBillItem(rpositoryBillItemModel, type);
                }
            }
            db.setTransactionSuccessful();  //设置事务成功完成
        } catch (Exception ex) {
            return false;
        } finally {
            db.endTransaction();    //结束事务
        }
        return true;
    }

    /**
     * 插入入库明细信息
     *
     * @param rpositoryBillItemModel
     */
    private void insertInBillItem(SimpleRepositoryBillItemModel rpositoryBillItemModel, String type) {
        try {
            String productInfo = null;
            productInfo = rpositoryBillItemModel.getProductScm().getProductSku() + "/" +
                    rpositoryBillItemModel.getProductScm().getProductName() + "/" +
                    rpositoryBillItemModel.getProductScm().getFactoryName() + "/" +
                    rpositoryBillItemModel.getProductScm().getBrandName() + "/" +
                    rpositoryBillItemModel.getProductScm().getGramWeight() + "g/" +
                    rpositoryBillItemModel.getProductScm().getSpecification();
            if (!StringUtils.isEmpty(rpositoryBillItemModel.getProductScm().getGrade())) {
                productInfo += "/" + rpositoryBillItemModel.getProductScm().getGrade();
            }
            if (rpositoryBillItemModel.getTon() != null) {
                productInfo += "/" + rpositoryBillItemModel.getTon() + "吨";
            }
            db.execSQL("INSERT INTO " + helper.IN_BIll_ITEM_TABLE_NAME + " VALUES(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                    new Object[]{
                            rpositoryBillItemModel.getScmId(),
                            rpositoryBillItemModel.getRepositoryBillId(),
                            rpositoryBillItemModel.getRepositoryBillItemId(),
                            rpositoryBillItemModel.getProductId(),
                            productInfo,
                            rpositoryBillItemModel.getProductScm().getProductSku(),
                            rpositoryBillItemModel.getProductScm().getProductName(),
                            rpositoryBillItemModel.getProductScm().getFactoryName(),
                            rpositoryBillItemModel.getProductScm().getBrandName(),
                            rpositoryBillItemModel.getProductScm().getGramWeight(),
                            rpositoryBillItemModel.getProductScm().getSpecification(),
                            rpositoryBillItemModel.getProductScm().getGrade(),
                            rpositoryBillItemModel.getQuantity(),
                            rpositoryBillItemModel.getQuantityUnit(),
                            rpositoryBillItemModel.getPrice(),
                            rpositoryBillItemModel.getPriceUnit(),
                            rpositoryBillItemModel.getScanQuantity(),
                            rpositoryBillItemModel.getTon(),
                            type,
                            rpositoryBillItemModel.getHasDetail()
                    });
            if (rpositoryBillItemModel.getRepositoryBillItemDetailList() != null && rpositoryBillItemModel.getRepositoryBillItemDetailList().size() > 0) {
                for (SimpleRepositoryBillItemDetailModel rpositoryBillItemDetailModel : rpositoryBillItemModel.getRepositoryBillItemDetailList()) {
                    insertInBillItemDetail(rpositoryBillItemDetailModel, rpositoryBillItemDetailModel.getState().toString(), type);
                }
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * 插入入库明细详情信息
     *
     * @param rpositoryBillItemDetailModel
     */
    private void insertInBillItemDetail(SimpleRepositoryBillItemDetailModel rpositoryBillItemDetailModel, String state, String type) {
        try {
            db.execSQL("INSERT INTO " + helper.IN_BIll_ITEM_DETAIL_TABLE_NAME + " VALUES(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                    new Object[]{
                            rpositoryBillItemDetailModel.getScmId(),
                            rpositoryBillItemDetailModel.getRepositoryBillId(),
                            rpositoryBillItemDetailModel.getRepositoryProductId(),
                            rpositoryBillItemDetailModel.getRepositoryProductId(),
                            rpositoryBillItemDetailModel.getRepositoryBillItemId(),
                            rpositoryBillItemDetailModel.getRepositoryBillItemDetailId(),
                            rpositoryBillItemDetailModel.getBarCode(),
                            rpositoryBillItemDetailModel.getAmountLing(),
                            rpositoryBillItemDetailModel.getAmountWeight(),
                            state,
                            rpositoryBillItemDetailModel.getRepositoryPositionId(),
                            rpositoryBillItemDetailModel.getRepositoryPosition() == null ? "" : rpositoryBillItemDetailModel.getRepositoryPosition().getName(),
                            type,
                            null,
                            state
                    });
        } catch (Exception ex) {
            throw ex;
        }
    }

    //==================入库单数据操作相关方法 end============================//

    //==================提货单（出库单数据操作相关方法）begin============================//

    /**
     * add persons
     *
     * @param repositoryBillModelList
     */
    public void addPickupBill(List<SimpleOrderPickupModel> repositoryBillModelList, int type) {
        db.beginTransaction();  //开始事务
        try {
            for (SimpleOrderPickupModel billModel : repositoryBillModelList) {
                delBill(billModel.getOrderPickupId().toString(), type + "");
//                if (billModel.getState() == 2) {
//                    delBill(billModel.getOrderPickupId().toString(), type + "");
//                } else {
//                    RpositoryBillModel model = getBillById(billModel.getOrderPickupId().toString(), type + "");
//                    if (model == null) {
                db.execSQL("INSERT INTO " + helper.BIll_TABLE_NAME + " VALUES(null,?,?,?,?,?,?,?,?,?)",
                        new Object[]{
                                billModel.getScmId(),
                                billModel.getOrderPickupId(),
                                billModel.getOrderPickupNo(),
                                billModel.getCustomerName(),
                                billModel.getCreateDate().getTime(),
                                billModel.getRepositoryName(),
                                billModel.getRepositoryId(),
                                type,
                                1});
                //}
                //}
            }
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }


    /**
     * 新增提货单明细
     *
     * @param orderPickupItemModels
     */
    public void addPickupItem(List<SimpleOrderPickupItemModel> orderPickupItemModels) {
        db.beginTransaction();  //开始事务
        try {
            String productInfo = null;
            for (SimpleOrderPickupItemModel item : orderPickupItemModels) {
                productInfo = item.getProductScm().getProductSku() + "/" +
                        item.getProductScm().getProductName() + "/" +
                        item.getProductScm().getFactoryName() + "/" +
                        item.getProductScm().getBrandName() + "/" +
                        item.getProductScm().getGramWeight() + "g/" +
                        item.getProductScm().getSpecification() + "/" +
                        item.getQuantity() +
                        EnumQuantityUnit.getValueById(item.getQuantityUnit()).getName();
                if (!StringUtils.isEmpty(item.getProductScm().getGrade())) {
                    productInfo += "/" + item.getProductScm().getGrade();
                }

                //如果有实发信息显示
                if (item.getOldProductId() != null && !item.getProductId().equals(item.getOldProductId())) {
                    productInfo += "/" + item.getOldProductName() + "/" + item.getOldFactoryName() + "/" + item.getOldBrandName() + "/" + item.getOldGramWeight() + "/" + item.getOldSpecification();
                }

                db.execSQL("INSERT INTO " + helper.IN_BIll_ITEM_TABLE_NAME + " VALUES(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                        new Object[]{
                                item.getScmId(),
                                item.getOrderPickupId(),
                                item.getOrderPickupItemId(),
                                item.getProductId(),
                                productInfo,
                                item.getProductScm().getProductSku(),
                                item.getProductScm().getProductName(),
                                item.getProductScm().getFactoryName(),
                                item.getProductScm().getBrandName(),
                                item.getProductScm().getGramWeight(),
                                item.getProductScm().getSpecification(),
                                item.getProductScm().getGrade(),
                                item.getQuantity(),
                                item.getQuantityUnit(),
                                item.getPrice(),
                                item.getPriceUnit(),
                                0,
                                item.getTon(),
                                EnumQueryType.out_repository_bill.getVal(),
                                1
                        });
            }
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    /**
     * 插入入库明细详情信息
     *
     * @param rpositoryBillItemDetailModel
     */
    public int addPickupItemDetail(SimpleRepositoryBillItemDetailModel rpositoryBillItemDetailModel) {
        try {
            //验证是否已存在相同明细：如果已存在就更新
            RepositoryProduct repositoryProductModel = null;
            String querySql = "SELECT * FROM " + helper.IN_BIll_ITEM_DETAIL_TABLE_NAME + " WHERE repositoryProductId = ? and repositoryBillId =?";
            Cursor c = db.rawQuery(querySql, new String[]{rpositoryBillItemDetailModel.getRepositoryProductId().toString(), rpositoryBillItemDetailModel.getRepositoryBillId().toString()});
            if (c != null && c.getCount() > 0) {
                //更新状态为已扫
                ContentValues cv = new ContentValues();
                cv.put("state", 1);
                db.update(helper.IN_BIll_ITEM_DETAIL_TABLE_NAME, cv, "repositoryProductId = ? and repositoryBillId = ?", new String[]{rpositoryBillItemDetailModel.getRepositoryProductId().toString(), rpositoryBillItemDetailModel.getRepositoryBillId().toString()});
                c.close();
                return -1;
            } else {
                db.execSQL("INSERT INTO " + helper.IN_BIll_ITEM_DETAIL_TABLE_NAME + " VALUES(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                        new Object[]{
                                rpositoryBillItemDetailModel.getScmId(),
                                rpositoryBillItemDetailModel.getRepositoryBillId(),
                                rpositoryBillItemDetailModel.getRepositoryProductId(),
                                rpositoryBillItemDetailModel.getProductId(),
                                rpositoryBillItemDetailModel.getRepositoryBillItemId(),
                                rpositoryBillItemDetailModel.getRepositoryBillItemDetailId(),
                                rpositoryBillItemDetailModel.getBarCode(),
                                rpositoryBillItemDetailModel.getAmountLing(),
                                rpositoryBillItemDetailModel.getAmountWeight(),
                                "",
                                rpositoryBillItemDetailModel.getRepositoryPositionId(),
                                rpositoryBillItemDetailModel.getRepositoryPosition() != null ? rpositoryBillItemDetailModel.getRepositoryPosition().getName() : "",
                                EnumQueryType.out_repository_bill.getVal(),
                                rpositoryBillItemDetailModel.getIsFull() ? 1 : 0,
                                1
                        });
            }
            return 0;
        } catch (Exception ex) {
            throw ex;
        }
    }

    //==================提货单（出库单数据操作相关方法）end============================//

    //==================分切单（加工单数据操作相关方法）begin============================//
    public void addMachingBill(List<SimpleRepositoryCutting> repositoryBillModelList, int type) {
        db.beginTransaction();  //开始事务
        try {
            for (SimpleRepositoryCutting billModel : repositoryBillModelList) {
                delBill(billModel.getRepositoryCuttingId().toString(), type + "");
//                if (billModel.getState() == 2) {
//                    delBill(billModel.getRepositoryCuttingId().toString(), type + "");
//                } else {
//                    RpositoryBillModel model = getBillById(billModel.getRepositoryCuttingId().toString(), type + "");
//                    if (model == null) {
                db.execSQL("INSERT INTO " + helper.BIll_TABLE_NAME + " VALUES(null,?,?,?,?,?,?,?,?,?)",
                        new Object[]{
                                billModel.getScmId(),
                                billModel.getRepositoryCuttingId(),
                                billModel.getRepositoryCuttingNo(),
                                billModel.getRelativeCompanyName(),
                                billModel.getCreateDate().getTime(),
                                billModel.getOldRepositoryName(),
                                billModel.getOldRepositoryId(),
                                type,
                                1});
                //}
                //}
            }
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    public boolean addCuttingDetail(List<SimpleRepositoryCuttingItem> simpleRepositoryCuttingItems) {
        db.beginTransaction();  //开始事务
        try {
            //插入入库明细表
            if (simpleRepositoryCuttingItems != null && simpleRepositoryCuttingItems.size() > 0) {
                for (SimpleRepositoryCuttingItem itemModel : simpleRepositoryCuttingItems) {
                    insertCuttingItem(itemModel);
                }
            }
            db.setTransactionSuccessful();  //设置事务成功完成
        } catch (Exception ex) {
            return false;
        } finally {
            db.endTransaction();    //结束事务
        }
        return true;
    }

    /**
     * 插入入库明细信息
     *
     * @param rpositoryBillItemModel
     */
    private void insertCuttingItem(SimpleRepositoryCuttingItem rpositoryBillItemModel) {
        try {
            String productInfo = null;
            productInfo = rpositoryBillItemModel.getOldProductSku() + "/" +
                    rpositoryBillItemModel.getOldProductName() + "/" +
                    //rpositoryBillItemModel.getFactoryName() + "/" +
                    rpositoryBillItemModel.getOldBrandName() + "/" +
                    rpositoryBillItemModel.getOldGramWeight() + "g/" +
                    rpositoryBillItemModel.getOldSpecification();
            if (!StringUtils.isEmpty(rpositoryBillItemModel.getOldGrade())) {
                productInfo += "/" + rpositoryBillItemModel.getOldGrade();
            }
            if (rpositoryBillItemModel.getOldTon() != null) {
                productInfo += "/" + rpositoryBillItemModel.getOldTon() + "吨";
            }
            db.execSQL("INSERT INTO " + helper.IN_BIll_ITEM_TABLE_NAME + " VALUES(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                    new Object[]{
                            rpositoryBillItemModel.getScmId(),
                            rpositoryBillItemModel.getRepositoryCuttingId(),
                            rpositoryBillItemModel.getRepositoryCuttingItemId(),
                            rpositoryBillItemModel.getOldProductId(),
                            productInfo,
                            rpositoryBillItemModel.getOldProductSku(),
                            rpositoryBillItemModel.getOldProductName(),
                            "",
                            rpositoryBillItemModel.getOldBrandName(),
                            rpositoryBillItemModel.getOldGramWeight(),
                            rpositoryBillItemModel.getOldSpecification(),
                            rpositoryBillItemModel.getOldGrade(),
                            rpositoryBillItemModel.getOldQuantity(),
                            rpositoryBillItemModel.getOldQuantityUnit(),
                            0,
                            null,
                            0,
                            rpositoryBillItemModel.getOldTon(),
                            EnumQueryType.maching_bill.getVal(),
                            1
                    });
            if (rpositoryBillItemModel.getRepositoryCuttingDetailList() != null && rpositoryBillItemModel.getRepositoryCuttingDetailList().size() > 0) {
                for (SimpleRepositoryCuttingDetail rpositoryBillItemDetailModel : rpositoryBillItemModel.getRepositoryCuttingDetailList()) {
                    insertCuttingItemDetail(rpositoryBillItemDetailModel, "0");
                }
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * 插入入库明细详情信息
     *
     * @param rpositoryBillItemDetailModel
     */
    public int insertCuttingItemDetail(SimpleRepositoryCuttingDetail rpositoryBillItemDetailModel, String state) {
        try {
            RepositoryProduct repositoryProductModel = null;
            String querySql = "SELECT * FROM " + helper.IN_BIll_ITEM_DETAIL_TABLE_NAME + " WHERE repositoryProductId = ? and repositoryBillId =?";
            Cursor c = db.rawQuery(querySql, new String[]{rpositoryBillItemDetailModel.getRepositoryProductId().toString(), rpositoryBillItemDetailModel.getRepositoryCuttingId().toString()});
            if (c != null && c.getCount() > 0) {
                //更新状态为已扫
                ContentValues cv = new ContentValues();
                cv.put("state", state);
                db.update(helper.IN_BIll_ITEM_DETAIL_TABLE_NAME, cv, "repositoryBillId =? and repositoryProductId = ?", new String[]{rpositoryBillItemDetailModel.getRepositoryCuttingId().toString(), rpositoryBillItemDetailModel.getRepositoryProductId().toString()});
                c.close();
                return -1;
            } else {
                db.execSQL("INSERT INTO " + helper.IN_BIll_ITEM_DETAIL_TABLE_NAME + " VALUES(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                        new Object[]{
                                rpositoryBillItemDetailModel.getScmId(),
                                rpositoryBillItemDetailModel.getRepositoryCuttingId(),
                                rpositoryBillItemDetailModel.getRepositoryProductId(),
                                rpositoryBillItemDetailModel.getProductId(),
                                rpositoryBillItemDetailModel.getRepositoryCuttingItemId(),
                                rpositoryBillItemDetailModel.getRepositoryCuttingDetailId(),
                                rpositoryBillItemDetailModel.getBarCode(),
                                null,
                                rpositoryBillItemDetailModel.getAvailableTon(),
                                "",
                                rpositoryBillItemDetailModel.getRepositoryPositionId(),
                                rpositoryBillItemDetailModel.getRepositoryPositionName(),
                                EnumQueryType.maching_bill.getVal(),
                                null,
                                state
                        });
                return 0;
            }
        } catch (Exception ex) {
            throw ex;
        }
    }
    //==================分切单（加工单数据操作相关方法）end============================//


    //=======================库存产品数据操作相关方法 begin=========================//
    public void insertRepositoryProduct(List<RepositoryProduct> repositoryProductList, String billId, String type) {
        db.beginTransaction();  //开始事务
        try {
            if (repositoryProductList != null && repositoryProductList.size() > 0) {
                for (RepositoryProduct repositoryProduct :
                        repositoryProductList) {
                    db.execSQL("INSERT INTO " + helper.REPOSITORY_PRODUCT + " VALUES(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                            new Object[]{
                                    repositoryProduct.getScmId(),
                                    repositoryProduct.getProductId(),
                                    repositoryProduct.getRepositoryProductId(),
                                    repositoryProduct.getTon(),
                                    repositoryProduct.getFrozenTon(),
                                    repositoryProduct.getRepositoryId(),
                                    repositoryProduct.getRepositoryPositionId(),
                                    repositoryProduct.getParentId(),
                                    repositoryProduct.getBarCode(),
                                    repositoryProduct.getBarCodeFactory(),
                                    repositoryProduct.getLing(),
                                    repositoryProduct.getAmount(),
                                    repositoryProduct.getInUse(),
                                    billId,
                                    type
                            });
                }
            }
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    /**
     * 根据单据编号获取单据信息
     *
     * @param barCode
     * @return
     */
    public RepositoryProduct getRepositoryProductByBarCode(String barCode, String billId, String type) {
        db = helper.getReadableDatabase();
        RepositoryProduct repositoryProductModel = null;
        String querySql = "SELECT * FROM " + helper.REPOSITORY_PRODUCT + " WHERE barCode = ? and type =? and repositoryBillId=?";
        Cursor c = db.rawQuery(querySql, new String[]{barCode, type, billId});
        while (c.moveToNext()) {
            repositoryProductModel = new RepositoryProduct();
            repositoryProductModel.setProductId(c.getInt(c.getColumnIndex("productId")));
            repositoryProductModel.setRepositoryProductId(c.getInt(c.getColumnIndex("repositoryProductId")));
            repositoryProductModel.setTon(c.getDouble(c.getColumnIndex("ton")));
            repositoryProductModel.setFrozenTon(c.getDouble(c.getColumnIndex("frozenTon")));
            repositoryProductModel.setRepositoryId(c.getInt(c.getColumnIndex("repositoryId")));
            repositoryProductModel.setRepositoryPositionId(c.getInt(c.getColumnIndex("repositoryPositionId")));
            repositoryProductModel.setParentId(c.getInt(c.getColumnIndex("parentId")));
            repositoryProductModel.setBarCode(c.getString(c.getColumnIndex("barCode")));
            repositoryProductModel.setBarCodeFactory(c.getString(c.getColumnIndex("barCodeFactory")));
            repositoryProductModel.setLing(c.getDouble(c.getColumnIndex("ling")));
            repositoryProductModel.setAmount(c.getDouble(c.getColumnIndex("amount")));
            repositoryProductModel.setInUse(c.getInt(c.getColumnIndex("inUse")) == 1 ? true : false);
            repositoryProductModel.setType(c.getInt(c.getColumnIndex("type")));
            repositoryProductModel.setRepositoryBillId(c.getInt(c.getColumnIndex("repositoryBillId")));
        }
        c.close();
        return repositoryProductModel;
    }

    /**
     * 查询是否有库存信息
     *
     * @return
     */
    public boolean hasRepositoryProduct(String repositoryBillId, String type) {
        db = helper.getReadableDatabase();
        ArrayList<RpositoryBillItemModel> rpositoryBillItemModels = new ArrayList<RpositoryBillItemModel>();
        String querySql = "SELECT 1 FROM " + helper.REPOSITORY_PRODUCT + " WHERE repositoryBillId = ? and type=? limit 0,1";
        Cursor c = db.rawQuery(querySql, new String[]{repositoryBillId, type});
        if (c != null && c.getCount() > 0) {
            c.close();
            return true;
        } else {
            c.close();
            return false;
        }
    }
    //=======================库存产品数据操作相关方法 end =========================//

    /**
     * update person's age
     *
     * @param
     */
    public void updateBillState(String repositoryBillId, String state, String type) {
        ContentValues cv = new ContentValues();
        cv.put("state", state);
        db.update(helper.BIll_TABLE_NAME, cv, "repositoryBillId = ? and type=?", new String[]{repositoryBillId, type});
    }

    /**
     * 更新单据明细的状态
     *
     * @param repositoryBillItemId
     * @param scanQuantity
     */
    public void updateBillItemState(String repositoryBillItemId, String scanQuantity, String type) {
        ContentValues cv = new ContentValues();

        //查询已扫数量
        int quantity = 0;
        String querySql = "SELECT 1 FROM " + helper.IN_BIll_ITEM_DETAIL_TABLE_NAME + " WHERE repositoryBillItemId = ? and type=? and state = 1";
        Cursor c = db.rawQuery(querySql, new String[]{repositoryBillItemId, type});
        if (c != null) {
            quantity = c.getCount();
        }
        cv.put("scanQuantity", quantity);
        db.update(helper.IN_BIll_ITEM_TABLE_NAME, cv, "repositoryBillItemId =? and type=?", new String[]{repositoryBillItemId, type});
    }

    /**
     * 更新单据明细详情状态、库位、原厂码
     *
     * @param repositoryBillItemDetailId
     * @param state
     */
    public boolean updateBillItemDetailState(String repositoryBillItemDetailId, String type, String state, String positionId, String positionName, String originalBarcode) {
        ContentValues cv = new ContentValues();
        cv.put("state", state);
        cv.put("originalBarcode", originalBarcode);
        cv.put("repositoryPositionId", positionId);
        cv.put("repositoryPositionName", positionName);
        int result = db.update(helper.IN_BIll_ITEM_DETAIL_TABLE_NAME, cv, "repositoryBillItemDetailId = ? and type=?", new String[]{repositoryBillItemDetailId, type});
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 查询单据信息
     *
     * @return List<RpositoryBillModel>
     */
    public RpositoryBillModel queryBill(String scmId, String repositoryId, String type, String repositoryBillId) {
        String querySql = "SELECT * FROM " + helper.BIll_TABLE_NAME + " WHERE scmId = ? and repositoryId = ? and type=? and repositoryBillId = ?";
        Cursor c = db.rawQuery(querySql, new String[]{scmId, repositoryId, type, repositoryBillId});
        RpositoryBillModel rpositoryBillModel = null;
        while (c.moveToNext()) {
            rpositoryBillModel = new RpositoryBillModel();
            rpositoryBillModel.repositoryBillId = c.getInt(c.getColumnIndex("repositoryBillId"));
            rpositoryBillModel.repositoryBillNo = c.getString(c.getColumnIndex("repositoryBillNo"));
            rpositoryBillModel.repositoryName = c.getString(c.getColumnIndex("repositoryName"));
            rpositoryBillModel.repositoryId = c.getInt(c.getColumnIndex("repositoryId"));
            rpositoryBillModel.relativeCompanyName = c.getString(c.getColumnIndex("relativeCompanyName"));
            rpositoryBillModel.createDate = c.getLong(c.getColumnIndex("createDate"));
            rpositoryBillModel.state = c.getInt(c.getColumnIndex("state"));
        }
        c.close();
        return rpositoryBillModel;
    }

    /**
     * 查询所有入库单信息
     *
     * @return List<RpositoryBillModel>
     */
    public List<RpositoryBillModel> queryBillList(String scmId, String repositoryId, String state, String type) {
        ArrayList<RpositoryBillModel> rpositoryBillModels = new ArrayList<RpositoryBillModel>();
        Cursor c = queryBillListTheCursor(scmId, repositoryId, state, type);
        RpositoryBillModel rpositoryBillModel = null;
        while (c.moveToNext()) {
            rpositoryBillModel = new RpositoryBillModel();
            rpositoryBillModel.repositoryBillId = c.getInt(c.getColumnIndex("repositoryBillId"));
            rpositoryBillModel.repositoryBillNo = c.getString(c.getColumnIndex("repositoryBillNo"));
            rpositoryBillModel.repositoryName = c.getString(c.getColumnIndex("repositoryName"));
            rpositoryBillModel.repositoryId = c.getInt(c.getColumnIndex("repositoryId"));
            rpositoryBillModel.relativeCompanyName = c.getString(c.getColumnIndex("relativeCompanyName"));
            rpositoryBillModel.createDate = c.getLong(c.getColumnIndex("createDate"));
            rpositoryBillModel.state = c.getInt(c.getColumnIndex("state"));
            rpositoryBillModels.add(rpositoryBillModel);
        }
        c.close();
        return rpositoryBillModels;
    }

    /**
     * 根据单据编号获取单据信息
     *
     * @param billNo
     * @return
     */
    public RpositoryBillModel getBill(String billNo, String type) {
        db = helper.getReadableDatabase();
        RpositoryBillModel rpositoryBillModel = null;
        String querySql = "SELECT * FROM " + helper.BIll_TABLE_NAME + " WHERE repositoryBillNo = ? and type=?";
        Cursor c = db.rawQuery(querySql, new String[]{billNo, type});
        while (c.moveToNext()) {
            rpositoryBillModel = new RpositoryBillModel();
            rpositoryBillModel.repositoryBillId = c.getInt(c.getColumnIndex("repositoryBillId"));
            rpositoryBillModel.repositoryBillNo = c.getString(c.getColumnIndex("repositoryBillNo"));
            rpositoryBillModel.repositoryName = c.getString(c.getColumnIndex("repositoryName"));
            rpositoryBillModel.relativeCompanyName = c.getString(c.getColumnIndex("relativeCompanyName"));
            rpositoryBillModel.createDate = c.getLong(c.getColumnIndex("createDate"));
            rpositoryBillModel.state = c.getInt(c.getColumnIndex("state"));
        }
        c.close();
        return rpositoryBillModel;
    }

    /**
     * 根据单据编号获取单据信息
     *
     * @param billId
     * @return
     */
    public RpositoryBillModel getBillById(String billId, String type) {
        db = helper.getReadableDatabase();
        RpositoryBillModel rpositoryBillModel = null;
        String querySql = "SELECT * FROM " + helper.BIll_TABLE_NAME + " WHERE repositoryBillId = ? and type =?";
        Cursor c = db.rawQuery(querySql, new String[]{billId, type});
        while (c.moveToNext()) {
            rpositoryBillModel = new RpositoryBillModel();
            rpositoryBillModel.repositoryBillId = c.getInt(c.getColumnIndex("repositoryBillId"));
            rpositoryBillModel.repositoryBillNo = c.getString(c.getColumnIndex("repositoryBillNo"));
            rpositoryBillModel.repositoryName = c.getString(c.getColumnIndex("repositoryName"));
            rpositoryBillModel.relativeCompanyName = c.getString(c.getColumnIndex("relativeCompanyName"));
            rpositoryBillModel.createDate = c.getLong(c.getColumnIndex("createDate"));
            rpositoryBillModel.state = c.getInt(c.getColumnIndex("state"));
        }
        c.close();
        return rpositoryBillModel;
    }

    /**
     * 查询所有入库单信息
     *
     * @return Cursor
     */
    public Cursor queryBillListTheCursor(String scmId, String repositoryId, String states, String type) {
        String placeHolder = "?";
        List<String> strList = new ArrayList<String>();
        strList.add(scmId);
        strList.add(repositoryId);
        strList.add(type);
        placeHolder = makePlaceholders(states.split(",").length);
        for (String state : states.split(",")) {
            strList.add(state);
        }

        Cursor c = db.rawQuery("SELECT * FROM " + helper.BIll_TABLE_NAME + " where scmId = ? and repositoryId = ? and type = ? and state in(" + placeHolder + ") order by createDate desc", strList.toArray(new String[strList.size()]));
        return c;
    }


    /**
     * 查询入库单明细
     *
     * @return
     */
    public boolean hasSaveBillItem(String repositoryBillId, String type) {
        db = helper.getReadableDatabase();
        ArrayList<RpositoryBillItemModel> rpositoryBillItemModels = new ArrayList<RpositoryBillItemModel>();
        String querySql = "SELECT 1 FROM " + helper.IN_BIll_ITEM_TABLE_NAME + " WHERE repositoryBillId = ? and type=? limit 0,1";
        Cursor c = db.rawQuery(querySql, new String[]{repositoryBillId, type});
        if (c != null && c.getCount() > 0) {
            c.close();
            return true;
        } else {
            c.close();
            return false;
        }
    }

    /**
     * 查询入库单明细
     *
     * @return
     */
    public List<RpositoryBillItemModel> queryInBillItemList(String repositoryBillId, String type) {
        db = helper.getReadableDatabase();
        ArrayList<RpositoryBillItemModel> rpositoryBillItemModels = new ArrayList<RpositoryBillItemModel>();
        String querySql = "SELECT * FROM " + helper.IN_BIll_ITEM_TABLE_NAME + " WHERE repositoryBillId = ? and type =?";
        Cursor c = db.rawQuery(querySql, new String[]{repositoryBillId, type});
        RpositoryBillItemModel rpositoryBillItemModel = null;
        int repositoryBillItemIdIndex = c.getColumnIndex("repositoryBillItemId");
        int repositoryBillIdIndex = c.getColumnIndex("repositoryBillId");
        int productInfoIndex = c.getColumnIndex("productInfo");
        int quantityIndex = c.getColumnIndex("quantity");
        int quantityUnitIndex = c.getColumnIndex("quantityUnit");
        int priceIndex = c.getColumnIndex("price");
        int priceUnitIndex = c.getColumnIndex("priceUnit");
        int scanQuantityIndex = c.getColumnIndex("scanQuantity");
        int productIdIndex = c.getColumnIndex("productId");
        int tonIndex = c.getColumnIndex("ton");
        int gramWeightIndex = c.getColumnIndex("gramWeight");
        int hasDetailIndex = c.getColumnIndex("hasDetail");
        int typeIndex = c.getColumnIndex("type");
        while (c.moveToNext()) {
            rpositoryBillItemModel = new RpositoryBillItemModel();
            rpositoryBillItemModel.repositoryBillId = c.getInt(repositoryBillIdIndex);
            rpositoryBillItemModel.repositoryBillItemId = c.getInt(repositoryBillItemIdIndex);
            rpositoryBillItemModel.productInfo = c.getString(productInfoIndex);
            rpositoryBillItemModel.productId = c.getInt(productIdIndex);
            rpositoryBillItemModel.quantity = c.getDouble(quantityIndex);
            rpositoryBillItemModel.scanQuantity = c.getDouble(scanQuantityIndex);
            rpositoryBillItemModel.quantityUnit = c.getInt(quantityUnitIndex);
            rpositoryBillItemModel.price = c.getDouble(priceIndex);
            rpositoryBillItemModel.gramWeight = c.getDouble(gramWeightIndex);
            rpositoryBillItemModel.ton = c.getDouble(tonIndex);
            rpositoryBillItemModel.hasDetail = c.getInt(hasDetailIndex);
            rpositoryBillItemModel.priceUnit = c.getInt(priceUnitIndex);
            rpositoryBillItemModel.type = c.getInt(typeIndex);
            rpositoryBillItemModel.rpositoryBillItemDetailModelList = queryInBillItemDetailList(rpositoryBillItemModel.repositoryBillItemId.toString(), type, null);
            rpositoryBillItemModels.add(rpositoryBillItemModel);
        }
        c.close();
        return rpositoryBillItemModels;
    }

    /**
     * 查询入库单明细及已扫描详情
     *
     * @param repositoryBillId
     * @param type
     * @return
     */
    public List<RpositoryBillItemModel> queryInBillItemAndScanDetailList(String repositoryBillId, String type) {
        db = helper.getReadableDatabase();
        ArrayList<RpositoryBillItemModel> rpositoryBillItemModels = new ArrayList<RpositoryBillItemModel>();
        String querySql = "SELECT * FROM " + helper.IN_BIll_ITEM_TABLE_NAME + " WHERE repositoryBillId = ? and type =?";
        Cursor c = db.rawQuery(querySql, new String[]{repositoryBillId, type});
        RpositoryBillItemModel rpositoryBillItemModel = null;
        int repositoryBillItemIdIndex = c.getColumnIndex("repositoryBillItemId");
        int repositoryBillIdIndex = c.getColumnIndex("repositoryBillId");
        int productInfoIndex = c.getColumnIndex("productInfo");
        int quantityIndex = c.getColumnIndex("quantity");
        int quantityUnitIndex = c.getColumnIndex("quantityUnit");
        int priceIndex = c.getColumnIndex("price");
        int priceUnitIndex = c.getColumnIndex("priceUnit");
        int scanQuantityIndex = c.getColumnIndex("scanQuantity");
        int productIdIndex = c.getColumnIndex("productId");
        int tonIndex = c.getColumnIndex("ton");
        int gramWeightIndex = c.getColumnIndex("gramWeight");
        int hasDetailIndex = c.getColumnIndex("hasDetail");
        int typeIndex = c.getColumnIndex("type");
        while (c.moveToNext()) {
            rpositoryBillItemModel = new RpositoryBillItemModel();
            rpositoryBillItemModel.repositoryBillId = c.getInt(repositoryBillIdIndex);
            rpositoryBillItemModel.repositoryBillItemId = c.getInt(repositoryBillItemIdIndex);
            rpositoryBillItemModel.productInfo = c.getString(productInfoIndex);
            rpositoryBillItemModel.productId = c.getInt(productIdIndex);
            rpositoryBillItemModel.quantity = c.getDouble(quantityIndex);
            rpositoryBillItemModel.scanQuantity = c.getDouble(scanQuantityIndex);
            rpositoryBillItemModel.quantityUnit = c.getInt(quantityUnitIndex);
            rpositoryBillItemModel.price = c.getDouble(priceIndex);
            rpositoryBillItemModel.gramWeight = c.getDouble(gramWeightIndex);
            rpositoryBillItemModel.ton = c.getDouble(tonIndex);
            rpositoryBillItemModel.hasDetail = c.getInt(hasDetailIndex);
            rpositoryBillItemModel.priceUnit = c.getInt(priceUnitIndex);
            rpositoryBillItemModel.type = c.getInt(typeIndex);
            rpositoryBillItemModel.rpositoryBillItemDetailModelList = queryInBillItemDetailList(rpositoryBillItemModel.repositoryBillItemId.toString(), type, "1");
            rpositoryBillItemModels.add(rpositoryBillItemModel);
        }
        c.close();
        return rpositoryBillItemModels;
    }

    /**
     * 根据入库单明细id查询入库单明细详情
     *
     * @param repositoryBillItemId
     * @return
     */
    public List<RpositoryBillItemDetailModel> queryInBillItemDetailList(String repositoryBillItemId, String type, String state) {
        ArrayList<RpositoryBillItemDetailModel> rpositoryBillItemDetailModels = new ArrayList<RpositoryBillItemDetailModel>();
        String querySql = "SELECT * FROM " + helper.IN_BIll_ITEM_DETAIL_TABLE_NAME + " WHERE repositoryBillItemId = ? and type=?";
        if (!StringUtils.isEmpty(state)) {
            querySql += " and state = " + state;
        }
        Cursor c = db.rawQuery(querySql, new String[]{repositoryBillItemId, type});
        RpositoryBillItemDetailModel rpositoryBillItemDetailModel = null;
        int barCodeIndex = c.getColumnIndex("barCode");
        int amountLingIndex = c.getColumnIndex("amountLing");
        int amountWeightIndex = c.getColumnIndex("amountWeight");
        int repositoryProductIdIndex = c.getColumnIndex("repositoryProductId");
        int repositoryPositionIdIndex = c.getColumnIndex("repositoryPositionId");
        int repositoryPositionNameIndex = c.getColumnIndex("repositoryPositionName");
        int stateIndex = c.getColumnIndex("state");
        int originalBarcodeIndex = c.getColumnIndex("originalBarcode");
        int repositoryBillIdIndex = c.getColumnIndex("repositoryBillId");
        int repositoryBillItemIdIndex = c.getColumnIndex("repositoryBillItemId");
        int repositoryBillItemDetailIdIndex = c.getColumnIndex("repositoryBillItemDetailId");
        int isFullIndex = c.getColumnIndex("isFull");
        int typeIndex = c.getColumnIndex("type");
        while (c.moveToNext()) {
            rpositoryBillItemDetailModel = new RpositoryBillItemDetailModel();
            rpositoryBillItemDetailModel.barCode = c.getString(barCodeIndex);
            rpositoryBillItemDetailModel.amountLing = c.getDouble(amountLingIndex);
            rpositoryBillItemDetailModel.amountWeight = c.getDouble(amountWeightIndex);
            rpositoryBillItemDetailModel.repositoryProductId = c.getInt(repositoryProductIdIndex);
            rpositoryBillItemDetailModel.repositoryPositionId = c.getInt(repositoryPositionIdIndex);
            rpositoryBillItemDetailModel.repositoryPositionName = c.getString(repositoryPositionNameIndex);
            rpositoryBillItemDetailModel.state = c.getInt(stateIndex);
            rpositoryBillItemDetailModel.originalBarcode = c.getString(originalBarcodeIndex);
            rpositoryBillItemDetailModel.repositoryBillId = c.getInt(repositoryBillIdIndex);
            rpositoryBillItemDetailModel.repositoryBillItemId = c.getInt(repositoryBillItemIdIndex);
            rpositoryBillItemDetailModel.repositoryBillItemDetailId = c.getInt(repositoryBillItemDetailIdIndex);
            rpositoryBillItemDetailModel.isFull = c.getInt(isFullIndex);
            rpositoryBillItemDetailModel.type = c.getInt(typeIndex);
            rpositoryBillItemDetailModels.add(rpositoryBillItemDetailModel);
        }
        c.close();
        return rpositoryBillItemDetailModels;
    }

    /**
     * 根据入库单明细id查询入库单明细详情
     *
     * @param repositoryBillId
     * @return
     */
    public List<RpositoryBillItemDetailModel> queryInBillItemDetailListByBillId(String repositoryBillId, String type) {
        db = helper.getReadableDatabase();
        ArrayList<RpositoryBillItemDetailModel> rpositoryBillItemDetailModels = new ArrayList<RpositoryBillItemDetailModel>();
        String querySql = "SELECT * FROM " + helper.IN_BIll_ITEM_DETAIL_TABLE_NAME + " WHERE repositoryBillId = ? and type=?";
        Cursor c = db.rawQuery(querySql, new String[]{repositoryBillId, type});
        RpositoryBillItemDetailModel rpositoryBillItemDetailModel = null;
        int barCodeIndex = c.getColumnIndex("barCode");
        int amountLingIndex = c.getColumnIndex("amountLing");
        int amountWeightIndex = c.getColumnIndex("amountWeight");
        int repositoryProductIdIndex = c.getColumnIndex("repositoryProductId");
        int repositoryPositionIdIndex = c.getColumnIndex("repositoryPositionId");
        int repositoryPositionNameIndex = c.getColumnIndex("repositoryPositionName");
        int repositoryBillIdIndex = c.getColumnIndex("repositoryBillId");
        int repositoryBillItemIdIndex = c.getColumnIndex("repositoryBillItemId");
        int repositoryBillItemDetailIdIndex = c.getColumnIndex("repositoryBillItemDetailId");
        int stateIndex = c.getColumnIndex("state");
        int originalBarcodeIndex = c.getColumnIndex("originalBarcode");
        while (c.moveToNext()) {
            rpositoryBillItemDetailModel = new RpositoryBillItemDetailModel();
            rpositoryBillItemDetailModel.barCode = c.getString(barCodeIndex);
            rpositoryBillItemDetailModel.repositoryProductId = c.getInt(repositoryProductIdIndex);
            rpositoryBillItemDetailModel.amountLing = c.getDouble(amountLingIndex);
            rpositoryBillItemDetailModel.amountWeight = c.getDouble(amountWeightIndex);
            rpositoryBillItemDetailModel.repositoryPositionId = c.getInt(repositoryPositionIdIndex);
            rpositoryBillItemDetailModel.repositoryPositionName = c.getString(repositoryPositionNameIndex);
            rpositoryBillItemDetailModel.state = c.getInt(stateIndex);
            rpositoryBillItemDetailModel.originalBarcode = c.getString(originalBarcodeIndex);
            rpositoryBillItemDetailModel.repositoryBillId = c.getInt(repositoryBillIdIndex);
            rpositoryBillItemDetailModel.repositoryBillItemId = c.getInt(repositoryBillItemIdIndex);
            rpositoryBillItemDetailModel.repositoryBillItemDetailId = c.getInt(repositoryBillItemDetailIdIndex);
            rpositoryBillItemDetailModels.add(rpositoryBillItemDetailModel);
        }
        c.close();
        return rpositoryBillItemDetailModels;
    }

    /**
     * 根据条码号查询入库单明细详情
     *
     * @param barCode
     * @return
     */
    public RpositoryBillItemDetailModel queryInBillItmeDetail(String barCode, String type) {
        db = helper.getReadableDatabase();
        RpositoryBillItemDetailModel rpositoryBillItemDetailModel = null;
        String querySql = "SELECT * FROM " + helper.IN_BIll_ITEM_DETAIL_TABLE_NAME + " WHERE barCode = ? and type=?";
        Cursor c = db.rawQuery(querySql, new String[]{barCode, type});
        while (c.moveToNext()) {
            rpositoryBillItemDetailModel = new RpositoryBillItemDetailModel();
            rpositoryBillItemDetailModel.repositoryBillItemDetailId = c.getInt(c.getColumnIndex("repositoryBillItemDetailId"));
            rpositoryBillItemDetailModel.repositoryBillItemId = c.getInt(c.getColumnIndex("repositoryBillItemId"));
            rpositoryBillItemDetailModel.barCode = c.getString(c.getColumnIndex("barCode"));
            rpositoryBillItemDetailModel.amountLing = c.getDouble(c.getColumnIndex("amountLing"));
            rpositoryBillItemDetailModel.amountWeight = c.getDouble(c.getColumnIndex("amountWeight"));
            rpositoryBillItemDetailModel.repositoryPositionName = c.getString(c.getColumnIndex("repositoryPositionName"));
            rpositoryBillItemDetailModel.repositoryPositionId = c.getInt(c.getColumnIndex("repositoryPositionId"));
            rpositoryBillItemDetailModel.originalBarcode = c.getString(c.getColumnIndex("originalBarcode"));
            rpositoryBillItemDetailModel.state = c.getInt(c.getColumnIndex("state"));
        }
        c.close();
        return rpositoryBillItemDetailModel;
    }

    /**
     * 查询入库单明细
     *
     * @param repositoryBillItemId
     * @return
     */
    public RpositoryBillItemModel queryInBillItem(String repositoryBillItemId, String type) {
        RpositoryBillItemModel rpositoryBillItemModel = null;
        String querySql = "SELECT * FROM " + helper.IN_BIll_ITEM_TABLE_NAME + " WHERE repositoryBillItemId = ? and type=?";
        Cursor c = db.rawQuery(querySql, new String[]{repositoryBillItemId, type});
        while (c.moveToNext()) {
            rpositoryBillItemModel = new RpositoryBillItemModel();
            rpositoryBillItemModel.repositoryBillId = c.getInt(c.getColumnIndex("repositoryBillId"));
            rpositoryBillItemModel.repositoryBillItemId = c.getInt(c.getColumnIndex("repositoryBillItemId"));
            rpositoryBillItemModel.productInfo = c.getString(c.getColumnIndex("productInfo"));
            rpositoryBillItemModel.productSku = c.getString(c.getColumnIndex("productSku"));
            rpositoryBillItemModel.factoryName = c.getString(c.getColumnIndex("factoryName"));
            rpositoryBillItemModel.brandName = c.getString(c.getColumnIndex("brandName"));
            rpositoryBillItemModel.productName = c.getString(c.getColumnIndex("productName"));
            rpositoryBillItemModel.gramWeight = c.getDouble(c.getColumnIndex("gramWeight"));
            rpositoryBillItemModel.specification = c.getString(c.getColumnIndex("specification"));
            rpositoryBillItemModel.grade = c.getString(c.getColumnIndex("grade"));
            rpositoryBillItemModel.quantityUnit = c.getInt(c.getColumnIndex("quantityUnit"));
            rpositoryBillItemModel.quantity = c.getDouble(c.getColumnIndex("quantity"));
            rpositoryBillItemModel.ton = c.getDouble(c.getColumnIndex("ton"));
            rpositoryBillItemModel.scanQuantity = c.getDouble(c.getColumnIndex("scanQuantity"));
        }
        c.close();
        return rpositoryBillItemModel;
    }

    /**
     * 根据产品Id查询明细
     *
     * @param productId
     * @return
     */
    public RpositoryBillItemModel queryBillItemByProductId(String repositoryBillId, String productId, String type) {
        RpositoryBillItemModel rpositoryBillItemModel = null;
        String querySql = "SELECT * FROM " + helper.IN_BIll_ITEM_TABLE_NAME + " WHERE repositoryBillId = ? and productId = ? and type=?";
        Cursor c = db.rawQuery(querySql, new String[]{repositoryBillId, productId, type});
        while (c.moveToNext()) {
            rpositoryBillItemModel = new RpositoryBillItemModel();
            rpositoryBillItemModel.repositoryBillId = c.getInt(c.getColumnIndex("repositoryBillId"));
            rpositoryBillItemModel.repositoryBillItemId = c.getInt(c.getColumnIndex("repositoryBillItemId"));
            rpositoryBillItemModel.productInfo = c.getString(c.getColumnIndex("productInfo"));
            rpositoryBillItemModel.productId = c.getInt(c.getColumnIndex("productId"));
            rpositoryBillItemModel.productSku = c.getString(c.getColumnIndex("productSku"));
            rpositoryBillItemModel.factoryName = c.getString(c.getColumnIndex("factoryName"));
            rpositoryBillItemModel.brandName = c.getString(c.getColumnIndex("brandName"));
            rpositoryBillItemModel.productName = c.getString(c.getColumnIndex("productName"));
            rpositoryBillItemModel.gramWeight = c.getDouble(c.getColumnIndex("gramWeight"));
            rpositoryBillItemModel.specification = c.getString(c.getColumnIndex("specification"));
            rpositoryBillItemModel.grade = c.getString(c.getColumnIndex("grade"));
            rpositoryBillItemModel.quantityUnit = c.getInt(c.getColumnIndex("quantityUnit"));
            rpositoryBillItemModel.quantity = c.getDouble(c.getColumnIndex("quantity"));
            rpositoryBillItemModel.ton = c.getDouble(c.getColumnIndex("ton"));
            rpositoryBillItemModel.scanQuantity = c.getDouble(c.getColumnIndex("scanQuantity"));
        }
        c.close();
        return rpositoryBillItemModel;
    }


    /**
     * 只是删除单据
     *
     * @param repositoryBillId
     * @param type
     */
    public void delBillById(String repositoryBillId, String type) {
        db.delete(helper.BIll_TABLE_NAME, "repositoryBillId=? and  type = ?", new String[]{repositoryBillId, type});
    }

    /**
     * 删除单据相关数据
     *
     * @param repositoryBillId
     */
    public void delBill(String repositoryBillId, String type) {
        //删除详情
        ArrayList<RpositoryBillItemModel> rpositoryBillItemModels = new ArrayList<RpositoryBillItemModel>();
        String querySql = "SELECT * FROM " + helper.IN_BIll_ITEM_TABLE_NAME + " WHERE repositoryBillId = ? and type = ?";
        Cursor c = db.rawQuery(querySql, new String[]{repositoryBillId, type});
        int repositoryBillItemIdIndex = c.getColumnIndex("repositoryBillItemId");
        while (c.moveToNext()) {
            int repositoryBillItemId = c.getInt(repositoryBillItemIdIndex);
            db.delete(helper.IN_BIll_ITEM_DETAIL_TABLE_NAME, "repositoryBillItemId=? and type = ?", new String[]{repositoryBillItemId + "", type});
        }
        c.close();
        db.delete(helper.REPOSITORY_PRODUCT, "repositoryBillId=? and type = ?", new String[]{repositoryBillId, type});
        db.delete(helper.IN_BIll_ITEM_TABLE_NAME, "repositoryBillId=? and  type = ?", new String[]{repositoryBillId, type});
        db.delete(helper.BIll_TABLE_NAME, "repositoryBillId=? and  type = ?", new String[]{repositoryBillId, type});
    }

    //删除详情
    public void delBillDetail(String repositoryBillId, String type) {
        db.beginTransaction();  //开始事务
        try {
            db.delete(helper.IN_BIll_ITEM_DETAIL_TABLE_NAME, "repositoryBillId=? and type = ? and state = 1", new String[]{repositoryBillId, type});
            ContentValues cv = new ContentValues();
            int quantity = 0;
            cv.put("scanQuantity", quantity);
            db.update(helper.IN_BIll_ITEM_TABLE_NAME, cv, "repositoryBillId =? and type=?", new String[]{repositoryBillId, type});
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    //删除详情
    public void delBillDetailByItemId(String repositoryBillId, String repositoryBillItemId, String type) {
        db.beginTransaction();  //开始事务
        try {
            db.delete(helper.IN_BIll_ITEM_DETAIL_TABLE_NAME, "repositoryBillId=? and type = ? and repositoryBillItemId = ? and state = 1", new String[]{repositoryBillId, type, repositoryBillItemId});
            ContentValues cv = new ContentValues();
            int quantity = 0;
            cv.put("scanQuantity", quantity);
            db.update(helper.IN_BIll_ITEM_TABLE_NAME, cv, "repositoryBillItemId =? and type=?", new String[]{repositoryBillItemId, type});
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    //删除详情
    public void delBillDetailByRepositoryProductId(String repositoryBillItemId, String repositoryProductId, String type) {
        db.beginTransaction();  //开始事务
        try {
            db.delete(helper.IN_BIll_ITEM_DETAIL_TABLE_NAME, "repositoryBillItemId = ? and repositoryProductId=? and type = ?", new String[]{repositoryBillItemId, repositoryProductId, type});
            ContentValues cv = new ContentValues();
            int quantity = 0;
            String querySql = "SELECT 1 FROM " + helper.IN_BIll_ITEM_DETAIL_TABLE_NAME + " WHERE repositoryBillItemId = ? and type=? and state = 1";
            Cursor c = db.rawQuery(querySql, new String[]{repositoryBillItemId, type});
            if (c != null) {
                quantity = c.getCount();
            }
            cv.put("scanQuantity", quantity);
            db.update(helper.IN_BIll_ITEM_TABLE_NAME, cv, "repositoryBillItemId =? and type=?", new String[]{repositoryBillItemId, type});
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    //更新详情是否整件出
    public void updateBillDetailIsFullByRepositoryProductId(String repositoryBillItemId, String repositoryProductId, String ifFull, String type) {
        db.beginTransaction();  //开始事务
        try {
            ContentValues cv = new ContentValues();
            cv.put("isFull", ifFull);
            db.update(helper.IN_BIll_ITEM_DETAIL_TABLE_NAME, cv, "repositoryBillItemId =? and repositoryProductId=? and type=?", new String[]{repositoryBillItemId, repositoryProductId, type});
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    /**
     * 保存查询时间
     *
     * @param queryTime 查询时间
     * @param queryType 查询类型
     */
    public boolean savaQueryTime(String queryTime, EnumQueryType queryType, String repositoryId, String scmId) {
        try {
            //插入入库明细表
            if (!StringUtils.isEmpty(queryTime)) {
                db.execSQL("INSERT INTO " + helper.QUERY_TIME_TABLE_NAME + " VALUES(null,?,?,?,?,?)",
                        new Object[]{
                                scmId,
                                queryType.getVal(),
                                queryType.getName(),
                                repositoryId,
                                queryTime
                        });
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    /**
     * 更新查询时间
     *
     * @param queryTime
     * @param queryType
     */
    public void updateQueryTime(String queryTime, EnumQueryType queryType, String repositoryId, String scmId) {
        ContentValues cv = new ContentValues();
        cv.put("lastSearchDate", queryTime);
        db.update(helper.QUERY_TIME_TABLE_NAME, cv, "scmId =? and type = ? and repositoryId=?", new String[]{scmId, queryType.getVal() + "", repositoryId});
    }

    /**
     * 获取查询时间
     *
     * @param queryType
     * @return
     */
    public String getLastSearchDate(EnumQueryType queryType, String newQueryTime, String repositoryId, String scmId) {
        String lastSearchDate = null;
        String querySql = "SELECT * FROM " + helper.QUERY_TIME_TABLE_NAME + " WHERE type = ? and repositoryId =?";
        Cursor c = db.rawQuery(querySql, new String[]{queryType.getVal() + "", repositoryId});
        while (c.moveToNext()) {
            lastSearchDate = c.getString(c.getColumnIndex("lastSearchDate"));
        }
        c.close();
        if (StringUtils.isEmpty(lastSearchDate)) {
            savaQueryTime(newQueryTime, queryType, repositoryId, scmId);
        } else {
            updateQueryTime(newQueryTime, queryType, repositoryId, scmId);
        }
        return lastSearchDate;
    }

    /**
     * 新增仓库数据
     *
     * @param repositoryModelList
     */
    public void insertRepository(List<SimpleRepositoryModel> repositoryModelList, String scmId) {
        db.beginTransaction();  //开始事务
        delRepository(scmId);
        delRepositoryPostion(scmId);
        try {
            for (SimpleRepositoryModel rpositoryModel : repositoryModelList) {
                db.execSQL("INSERT INTO " + helper.REPOSITORY + " VALUES(null,?,?,?,?)",
                        new Object[]{
                                rpositoryModel.getScmId(),
                                rpositoryModel.getRepositoryId(),
                                rpositoryModel.getRepositoryCode(),
                                rpositoryModel.getRepositoryName()});
                insertRepositoryPosition(rpositoryModel.getRepositionPositionList(), db);
            }
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    /**
     * 新增仓库库位数据
     *
     * @param repositoryPositionModelList
     */
    public void insertRepositoryPosition(List<SimpleRepositoryPositionModel> repositoryPositionModelList, SQLiteDatabase db) {
        try {
            for (SimpleRepositoryPositionModel positionModel : repositoryPositionModelList) {
                db.execSQL("INSERT INTO " + helper.REPOSITORY_POSITION + " VALUES(null,?,?,?,?,?)",
                        new Object[]{
                                positionModel.getScmId(),
                                positionModel.getRepositoryId(),
                                positionModel.getRepositoryPositionId(),
                                positionModel.getCode(),
                                positionModel.getName()});
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * 删除所有仓库
     *
     * @param scmId
     */
    private void delRepository(String scmId) {
        db.delete(helper.REPOSITORY, "scmId=?", new String[]{scmId});
    }

    /**
     * 删除所有仓库库位
     *
     * @param scmId
     */
    private void delRepositoryPostion(String scmId) {
        db.delete(helper.REPOSITORY_POSITION, "scmId=?", new String[]{scmId});
    }

    public List<SimpleRepositoryModel> queryRepositoryList(String scmId) {
        db = helper.getReadableDatabase();
        ArrayList<SimpleRepositoryModel> repositoryModelList = new ArrayList<SimpleRepositoryModel>();
        String querySql = "SELECT * FROM " + helper.REPOSITORY + " WHERE scmId = ?";
        Cursor c = db.rawQuery(querySql, new String[]{scmId});
        SimpleRepositoryModel repositoryModel = null;
        int repositoryIdIndex = c.getColumnIndex("repositoryId");
        int repositoryNameIndex = c.getColumnIndex("repositoryName");
        int repositoryCodeIndex = c.getColumnIndex("repositoryCode");
        while (c.moveToNext()) {
            repositoryModel = new SimpleRepositoryModel();
            repositoryModel.setRepositoryId(c.getInt(repositoryIdIndex));
            repositoryModel.setRepositoryName(c.getString(repositoryNameIndex));
            repositoryModel.setRepositoryCode(c.getString(repositoryCodeIndex));
            repositoryModel.setRepositionPositionList(queryRepositoryPositionList(repositoryModel.getRepositoryId().toString()));
            repositoryModelList.add(repositoryModel);
        }
        c.close();
        return repositoryModelList;
    }

    /**
     * 查询仓库库位
     *
     * @param repositoryId
     * @return
     */
    public List<SimpleRepositoryPositionModel> queryRepositoryPositionList(String repositoryId) {
        ArrayList<SimpleRepositoryPositionModel> repositoryPostionModelList = new ArrayList<SimpleRepositoryPositionModel>();
        String querySql = "SELECT * FROM " + helper.REPOSITORY_POSITION + " WHERE repositoryId = ?";
        Cursor c = db.rawQuery(querySql, new String[]{repositoryId});
        SimpleRepositoryPositionModel repositoryModel = null;
        int repositoryIdIndex = c.getColumnIndex("repositoryId");
        int repositoryPositionIdIndex = c.getColumnIndex("repositoryPositionId");
        int nameIndex = c.getColumnIndex("name");
        int codeIndex = c.getColumnIndex("code");
        while (c.moveToNext()) {
            repositoryModel = new SimpleRepositoryPositionModel();
            repositoryModel.setRepositoryId(c.getInt(repositoryIdIndex));
            repositoryModel.setRepositoryPositionId(c.getInt(repositoryPositionIdIndex));
            repositoryModel.setName(c.getString(nameIndex));
            repositoryModel.setCode(c.getString(codeIndex));
            repositoryPostionModelList.add(repositoryModel);
        }
        c.close();
        return repositoryPostionModelList;
    }

    /**
     * 查询库位信息
     *
     * @param repositoryPositionId
     * @param scmId
     * @return
     */
    public SimpleRepositoryPositionModel queryRepositoryPositionModel(String repositoryPositionId, String scmId) {
        String querySql = "SELECT * FROM " + helper.REPOSITORY_POSITION + " WHERE repositoryPositionId = ? and scmId = ?";
        Cursor c = db.rawQuery(querySql, new String[]{repositoryPositionId, scmId});
        SimpleRepositoryPositionModel repositoryModel = null;
        int repositoryIdIndex = c.getColumnIndex("repositoryId");
        int repositoryPositionIdIndex = c.getColumnIndex("repositoryPositionId");
        int nameIndex = c.getColumnIndex("name");
        int codeIndex = c.getColumnIndex("code");
        while (c.moveToNext()) {
            repositoryModel = new SimpleRepositoryPositionModel();
            repositoryModel.setRepositoryId(c.getInt(repositoryIdIndex));
            repositoryModel.setRepositoryPositionId(c.getInt(repositoryPositionIdIndex));
            repositoryModel.setName(c.getString(nameIndex));
            repositoryModel.setCode(c.getString(codeIndex));
        }
        c.close();
        return repositoryModel;
    }

    /**
     * 查询库位信息
     *
     * @param code
     * @param scmId
     * @return
     */
    public SimpleRepositoryPositionModel queryRepositoryPositionModelByCode(String code, String scmId) {
        String querySql = "SELECT * FROM " + helper.REPOSITORY_POSITION + " WHERE code = ? and scmId = ?";
        Cursor c = db.rawQuery(querySql, new String[]{code, scmId});
        SimpleRepositoryPositionModel repositoryModel = null;
        int repositoryIdIndex = c.getColumnIndex("repositoryId");
        int repositoryPositionIdIndex = c.getColumnIndex("repositoryPositionId");
        int nameIndex = c.getColumnIndex("name");
        int codeIndex = c.getColumnIndex("code");
        while (c.moveToNext()) {
            repositoryModel = new SimpleRepositoryPositionModel();
            repositoryModel.setRepositoryId(c.getInt(repositoryIdIndex));
            repositoryModel.setRepositoryPositionId(c.getInt(repositoryPositionIdIndex));
            repositoryModel.setName(c.getString(nameIndex));
            repositoryModel.setCode(c.getString(codeIndex));
        }
        c.close();
        return repositoryModel;
    }


    String makePlaceholders(int len) {
        if (len < 1) {
            throw new RuntimeException("No placeholders");
        } else {
            StringBuilder sb = new StringBuilder(len * 2 - 1);
            sb.append("?");
            for (int i = 1; i < len; i++) {
                sb.append(",?");
            }
            return sb.toString();
        }
    }

    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }
}