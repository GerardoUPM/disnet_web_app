package edu.upm.midas.data.relational.service.impl;
import edu.upm.midas.data.relational.entities.disnetdb.Person;
import edu.upm.midas.data.relational.entities.disnetdb.Status;
import edu.upm.midas.data.relational.repository.PersonRepository;
import edu.upm.midas.data.relational.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Created by gerardo on 18/09/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project web_acces_control
 * @className PersonServiceImpl
 * @see
 */
@Service("personService")
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository daoPerson;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Person findById(String personId) {
        return null;
    }

    @Override
    public Person findByStatusNative(String status) {
        return null;
    }

    @Override
    public Person findByNameNative(String nameAndLastName) {
        return null;
    }

    @Override
    public Person findByCreateDate(Date create_date) {
        return null;
    }

    @Override
    public Person findByProfileIdNative(int resourceId) {
        return null;
    }

    @Override
    public Object[] findByIdNative(String personId) {
        return new Object[0];
    }

    @Override
    public Object[] findByIdAndStatusNative(String personId, String status) {
        return daoPerson.findByIdAndStatusNative(personId, status);
    }

    @Override
    public List<Person> findAllQuery() {
        return null;
    }

    @Override
    public List<Object[]> findAllNative() {
        return null;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public void save(Person person) {
        daoPerson.persist(person);
    }

    @Override
    public int insertNative(String personId, String status, String firstName, String lastName, String pwd, String profileId, int academicId, Date createDate) {
        return 0;
    }

    @Override
    public int insertAcademicInfoNative(String institution, int country, String occupation, String interest) {
        return 0;
    }

    @Override
    public int insertBlockNative(String blockId, Date createDate, int seconds, int hour) {
        return 0;
    }

    @Override
    public int insertPersonBlockNative(String personId, String blockId, boolean enabled, int attempts) {
        return 0;
    }

    @Override
    public int insertLoginNative(String loginId, Date createDate, int seconds, int hour) {
        return 0;
    }

    @Override
    public int insertPersonLoginNative(String personId, String loginId, boolean enabled, int attempts) {
        return 0;
    }

    @Override
    public boolean deleteById(String personId) {
        return false;
    }

    @Override
    public void delete(Person person) {

    }

    @Override
    public Person update(Person person) {
        return null;
    }

    @Override
    public int updateByIdQuery(Person person) {
        return 0;
    }
}
