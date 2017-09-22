package edu.upm.midas.data.relational.service.impl;
import edu.upm.midas.data.relational.entities.disnetdb.AcademicInfo;
import edu.upm.midas.data.relational.repository.AcademicInfoRepository;
import edu.upm.midas.data.relational.service.AcademicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gerardo on 20/09/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project web_acces_control
 * @className AcademicInfoServiceImpl
 * @see
 */
@Service("academicInfoService")
public class AcademicInfoServiceImpl implements AcademicInfoService {

    @Autowired
    private AcademicInfoRepository daoAcademicInfo;

    @Override
    public AcademicInfo findById(int academicId) {
        return null;
    }

    @Override
    public Object[] findByIdNative(int academicId) {
        return new Object[0];
    }

    @Override
    public List<AcademicInfo> findAllQuery() {
        return null;
    }

    @Override
    public List<Object[]> findAllNative() {
        return null;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public void save(AcademicInfo academicInfo) {
        daoAcademicInfo.persist(academicInfo);
    }

    @Override
    public int insertNative(String institution, int country, String occupation, String interest) {
        return 0;
    }

    @Override
    public boolean deleteById(int academicId) {
        return false;
    }

    @Override
    public void delete(AcademicInfo academicInfo) {

    }

    @Override
    public AcademicInfo update(AcademicInfo academicInfo) {
        return null;
    }

    @Override
    public int updateByIdQuery(AcademicInfo academicInfo) {
        return 0;
    }
}
