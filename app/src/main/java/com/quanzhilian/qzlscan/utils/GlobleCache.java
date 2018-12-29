package com.quanzhilian.qzlscan.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.quanzhilian.qzlscan.application.CustomerApplication;
import com.quanzhilian.qzlscan.models.domain.SimpleSessionModel;

/**
 * Created by hushouqi on 16/3/16.
 */
public class GlobleCache {
    private static GlobleCache mCache;
    private static final String CACHE_SESSION_ID = "login_session_id";
    private static final String CACHE_SCM_ID = "scm_id";
    private static final String CACHE_SESSION = "login_session";
    private static final String CACHE_REPOSITORY = "selectedRepository";
    private static final String CACHE_REPOSITORY_ID = "selectedRepositoryId";
    private static final String CACHE_REPOSITORY_NAME = "selectedRepositoryName";
    private static final String CACHE_COMPANY = "company";
    private static final String CACHE_COMPANY_NAME = "companyName";
    private static final String CACHE_OPERATOR = "operator";
    private SharedPreferences mLoginSesssionPrefs;
    private SharedPreferences mLoginSesssionIdPrefs;

    private SharedPreferences repositoryPers;//仓库信息
    private SharedPreferences companyPers;//仓库信息
    // private static Context applicationContext;


    private GlobleCache() {
        mLoginSesssionIdPrefs = CustomerApplication.applicationContext.getSharedPreferences(CACHE_SESSION_ID, Context.MODE_PRIVATE);
        mLoginSesssionPrefs = CustomerApplication.applicationContext.getSharedPreferences(CACHE_SESSION, Context.MODE_PRIVATE);
        repositoryPers = CustomerApplication.applicationContext.getSharedPreferences(CACHE_REPOSITORY, Context.MODE_PRIVATE);
        companyPers =  CustomerApplication.applicationContext.getSharedPreferences(CACHE_COMPANY, Context.MODE_PRIVATE);
    }


    public static GlobleCache getInst() {
        if (mCache == null) {
            synchronized (GlobleCache.class) {
                mCache = new GlobleCache();
            }
        }
        return mCache;
    }

    /*删除sessionId*/
    public void sessionIdDestroy() {
        this.mLoginSesssionIdPrefs.edit().clear().commit();
    }

    /*删除session*/
    public void sessionDestroy() {
        this.mLoginSesssionPrefs.edit().clear().commit();
    }


    /*保存session model*/
    public void storeSession(SimpleSessionModel sessionModel,String scmId) {
        if (sessionModel != null) {
            SharedPreferencesUtils.putObject(this.mLoginSesssionPrefs, CACHE_SESSION, sessionModel);
            SharedPreferencesUtils.putSimpleValue(this.mLoginSesssionIdPrefs, CACHE_SESSION_ID, sessionModel.getSessionId());
            SharedPreferencesUtils.putSimpleValue(this.mLoginSesssionIdPrefs, CACHE_SCM_ID, scmId);
        }
    }

    public String getCacheSessionId() {
        String sessionId = "";
        sessionId = (String) SharedPreferencesUtils.getSimpleValue(mLoginSesssionIdPrefs, CACHE_SESSION_ID, null);
        return sessionId;
    }

    public String getScmId(){
        String scmId = "";
        scmId = (String) SharedPreferencesUtils.getSimpleValue(mLoginSesssionIdPrefs, CACHE_SCM_ID, null);
        if(!TextUtils.isEmpty(scmId)){
            return scmId;
        }else{
            return "-1";
        }
    }

    public SimpleSessionModel getCacheSessionModel() {
        SimpleSessionModel sessionModel = (SimpleSessionModel) SharedPreferencesUtils.getObject(mLoginSesssionPrefs, CACHE_SESSION, null);
        return sessionModel;
    }

    /**
     * 保存企业信息
     */
    public void saveCompany(String companyName, String operator) {
        if (companyName != null && operator != null) {
            SharedPreferencesUtils.putSimpleValue(this.companyPers, CACHE_COMPANY_NAME, companyName);
            SharedPreferencesUtils.putSimpleValue(this.companyPers, CACHE_OPERATOR, operator);
        }
    }

    public String getCacheCompanyName() {
        String companyName = null;
        companyName = (String) SharedPreferencesUtils.getSimpleValue(companyPers, CACHE_COMPANY_NAME, null);
        return companyName;
    }

    public String getCacheOperator() {
        String operator = null;
        operator = (String) SharedPreferencesUtils.getSimpleValue(companyPers, CACHE_OPERATOR, null);
        return operator;
    }



    /**
     * 保存仓库信息
     *
     * @param repositoryId
     * @param repositoryName
     */
    public void saveRepository(String repositoryId, String repositoryName) {
        if (repositoryId != null && repositoryName != null) {
            SharedPreferencesUtils.putSimpleValue(this.repositoryPers, CACHE_REPOSITORY_ID, repositoryId);
            SharedPreferencesUtils.putSimpleValue(this.repositoryPers, CACHE_REPOSITORY_NAME, repositoryName);
        }
    }

    public String getCacheRepositoryId() {
        String repositoryId = null;
        repositoryId = (String) SharedPreferencesUtils.getSimpleValue(repositoryPers, CACHE_REPOSITORY_ID, null);
        return repositoryId;
    }

    public String getCacheRepositoryName() {
        String repositoryName = null;
        repositoryName = (String) SharedPreferencesUtils.getSimpleValue(repositoryPers, CACHE_REPOSITORY_NAME, null);
        return repositoryName;
    }

    /*删除repository*/
    public void repositoryDestroy() {
        this.repositoryPers.edit().clear().commit();
    }

}
