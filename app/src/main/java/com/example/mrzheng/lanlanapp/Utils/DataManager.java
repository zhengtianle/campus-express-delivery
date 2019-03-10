/**
 * 2015-4-2
 */
package com.example.mrzheng.lanlanapp.Utils;

import com.example.mrzheng.lanlanapp.Adapter.LanLanApplication;
import com.example.mrzheng.lanlanapp.Database.DBHelper;
import com.example.mrzheng.lanlanapp.Database.History;
import com.example.mrzheng.lanlanapp.Model.SearchInfo;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



/**
 * @author wcy
 */
public class DataManager {
    private Dao<History, String> mHistoryDao;

    public static DataManager getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        public static final DataManager instance = new DataManager();
    }

    private DataManager() {
        DBHelper dbHelper = new DBHelper(LanLanApplication.getContext());
        try {
            mHistoryDao = dbHelper.getDao(History.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateHistory(SearchInfo searchInfo) {
        try {
            History history;
            if (mHistoryDao.idExists(searchInfo.getPost_id())) {
                history = mHistoryDao.queryForId(searchInfo.getPost_id());
            } else {
                history = new History();
            }
            history.setPost_id(searchInfo.getPost_id());
            history.setCompany_param(searchInfo.getCode());
            history.setCompany_name(searchInfo.getName());
            history.setCompany_icon(searchInfo.getLogo());
            history.setIs_check(searchInfo.getIs_check());
            mHistoryDao.createOrUpdate(history);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<History> getHistoryList() {
        List<History> historyList = new ArrayList<>();
        for (History history : mHistoryDao) {
            historyList.add(0, history);
        }
        return historyList;
    }

    public List<History> getUnCheckList() {
        List<History> unCheckList = new ArrayList<>();
        for (History history : mHistoryDao) {
            if (!history.getIs_check().equals("1")) {
                unCheckList.add(0, history);
            }
        }
        return unCheckList;
    }

    public boolean idExists(String id) {
        try {
            return mHistoryDao.idExists(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void deleteById(String id) {
        try {
            mHistoryDao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getRemark(String id) {
        try {
            if (idExists(id)) {
                History history = mHistoryDao.queryForId(id);
                return history.getRemark();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateRemark(String id, String remark) {
        try {
            History history = mHistoryDao.queryForId(id);
            history.setRemark(remark);
            mHistoryDao.update(history);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
