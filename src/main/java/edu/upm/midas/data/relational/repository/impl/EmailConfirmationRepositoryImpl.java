package edu.upm.midas.data.relational.repository.impl;

import edu.upm.midas.data.relational.entities.disnetdb.EmailConfirmation;
import edu.upm.midas.data.relational.entities.disnetdb.EmailConfirmationPK;
import edu.upm.midas.data.relational.repository.AbstractDao;
import edu.upm.midas.data.relational.repository.EmailConfirmationRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 23/9/17.
 */
public class EmailConfirmationRepositoryImpl extends AbstractDao<EmailConfirmationPK, EmailConfirmation>
                                            implements EmailConfirmationRepository{
    @Override
    public EmailConfirmation findById(EmailConfirmationPK emailConfirmationPK) {
        return null;
    }

    @Override
    public Object[] findByIdNative(String emailConfirmation) {
        return new Object[0];
    }

    @Override
    public List<EmailConfirmation> findAllQuery() {
        return null;
    }

    @Override
    public List<Object[]> findAllNative() {
        return null;
    }

    @Override
    public void persist(EmailConfirmation emailConfirmation) {

    }

    @Override
    public int insertNative(String personId, String token, boolean sent, Date sent_date, Date sent_datetime, boolean enabled) {
        return 0;
    }

    @Override
    public boolean deleteById(EmailConfirmationPK emailConfirmationPK) {
        return false;
    }

    @Override
    public void delete(EmailConfirmation emailConfirmation) {

    }

    @Override
    public EmailConfirmation update(EmailConfirmation emailConfirmation) {
        return null;
    }

    @Override
    public int updateEnabled(String personId, String token, boolean enabled, Date date, Date datetime) {
        return 0;
    }

    @Override
    public int updateByIdQuery(EmailConfirmation emailConfirmation) {
        return 0;
    }
}
