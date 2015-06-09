package edu.wisc.portlet.preferred.service;

import org.jvnet.jaxb2_commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.wisc.portlet.preferred.dao.PreferredNameDao;
import edu.wisc.portlet.preferred.form.PreferredName;
import edu.wisc.portlet.preferred.form.PreferredNameExtended;
import edu.wisc.portlet.preferred.service.PreferredNameService;

@Service
public class PreferredNameServiceImpl implements PreferredNameService {

    private PreferredNameDao dao;

    @Autowired
    public void setPreferredNameDao(PreferredNameDao dao) {
        this.dao = dao;
    }

    @Override
    public PreferredNameExtended getPreferredNameAndLegalName(String pvi) {
        return dao.getPreferredNameExtended(pvi);
    }

    @Override
    public PreferredNameExtended getPreferredNameAndLegalName(String pvi, PreferredName pn) {
        PreferredNameExtended preferredNameExtended = dao.getPreferredNameExtended(pvi);
        preferredNameExtended.setFirstName(pn.getFirstName());
        preferredNameExtended.setMiddleName(pn.getMiddleName());
        preferredNameExtended.setLastName(pn.getLastName());
        return preferredNameExtended;
    }

    @Override
    public PreferredName getPreferredName(String pvi) {
        return getPreferredName(pvi, null);
    }

    @Override
    public PreferredName getPreferredName(String pvi, String legalLastName) {
        PreferredName pn = dao.getPreferredName(pvi);
        if (pn == null) {
            pn = new PreferredName();
        }
        if (StringUtils.isEmpty(pn.getLastName())) {
            pn.setLastName(legalLastName);
        }
        return pn;
    }

    @Override
    public String getStatus(PreferredName ldapPn) {
        PreferredName jdbcPn = dao.getPreferredName(ldapPn.getPvi());
        if (StringUtils.isEmpty(ldapPn.getFirstName())
            && StringUtils.isEmpty(ldapPn.getMiddleName())
            && StringUtils.isEmpty(ldapPn.getLastName())
            && (jdbcPn == null
            || (StringUtils.isEmpty(jdbcPn.getFirstName()) && StringUtils
            .isEmpty(jdbcPn.getMiddleName()) && StringUtils.isEmpty(jdbcPn.getLastName()))
            )){
            return "(not set)";
        }else if(jdbcPn == null || 
                (StringUtils.isEmpty(jdbcPn.getFirstName()) && 
                 StringUtils.isEmpty(jdbcPn.getMiddleName()) && 
                 StringUtils.isEmpty(jdbcPn.getLastName()))){ 
            return "(deletion pending)";
        }else{
            //Logic does not detect a last name change only.
            String ldapPreferredName = new StringBuilder().append(ldapPn.getFirstName()).append(ldapPn.getMiddleName()).toString();
            String jdbcPreferredName = new StringBuilder().append(jdbcPn.getFirstName()).append(jdbcPn.getMiddleName()).toString();
            if(ldapPreferredName.equals(jdbcPreferredName)){ 
                return "";
            }
            else {
                return "(change pending)";
            }
        }
    }

    @Override
    public void setPreferredName(PreferredName pn) {
        dao.setPreferredName(pn);

    }

    @Override
    public void updateHideSource(PreferredName pn) {
        dao.updateHideSource(pn);

    }

    @Override
    public void deletePreferredName(String pvi) {
        dao.deletePreferredName(pvi);

    }

    @Override
    public void deletePreferredNameAdmin(String pvi) {
        dao.deletePreferredNameAdmin(pvi);

    }

    @Override
    public String getPviFromNetId(String netId) {
        return dao.getPviFromNetId(netId);
    }
}
