package edu.upm.midas.data.relational.service.impl;

import edu.upm.midas.data.relational.entities.disnetdb.LogQuery;
import edu.upm.midas.data.relational.repository.LogQueryRepository;
import edu.upm.midas.data.relational.service.LogQuery_Service;
import edu.upm.midas.model.user.TransactionHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("logQueryServiceService")
public class LogQueryServiceImpl implements LogQuery_Service {

    @Autowired
    private LogQueryRepository daoLogQuery;


    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public LogQuery findById(String queryId) {
        LogQuery logQuery = daoLogQuery.findById(queryId);
        return logQuery;
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public List<LogQuery> findAllQuery() {
        return daoLogQuery.findAllQuery();
    }

    @Override
    public List<TransactionHistory> findByTokenNative(String token) {
        List<Object[]> transactions = daoLogQuery.findByTokenNative(token);
        List<TransactionHistory> transactionHistories = null;
        if (transactions != null) {
            transactionHistories = new ArrayList<>();
            for (Object[] tran : transactions) {
                TransactionHistory transaction = new TransactionHistory();
                transaction.setTransactionId((String) tran[0]);
                transaction.setRequest((String) tran[1]);
                transaction.setDate((java.sql.Date) tran[2]);
                transaction.setDatetime((Timestamp) tran[3]);
                transaction.setStartDatetime((Timestamp) tran[4]);
                transaction.setEndDatetime((Timestamp) tran[5]);
                transaction.setRuntime((String) tran[6]);
                transaction.setRuntime_milliseconds((String) tran[7]);
                transactionHistories.add(transaction);
            }
        }
        return transactionHistories;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public void save(LogQuery logQuery) {
        daoLogQuery.persist(logQuery);
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public boolean deleteById(String queryId) {
        return false;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public void delete(LogQuery logQuery) {
        daoLogQuery.delete(logQuery);
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public LogQuery update(LogQuery logQuery) {
        return daoLogQuery.update(logQuery);
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public int updateByIdQuery(String queryId) {
        return 0;
    }
}
